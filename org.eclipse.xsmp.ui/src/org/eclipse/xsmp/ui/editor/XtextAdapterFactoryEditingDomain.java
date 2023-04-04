/*******************************************************************************
* Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ui.editor;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.xtext.resource.IExternalContentSupport;
import org.eclipse.xtext.resource.IExternalContentSupport.IExternalContentProvider;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.IResourceServiceProviderExtension;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.persistence.ResourceStorageLoadable;
import org.eclipse.xtext.resource.persistence.ResourceStorageProviderAdapter;
import org.eclipse.xtext.resource.persistence.SourceLevelURIsAdapter;
import org.eclipse.xtext.resource.persistence.StorageAwareResource;
import org.eclipse.xtext.ui.editor.DirtyStateManager;
import org.eclipse.xtext.ui.editor.IDirtyStateManager;
import org.eclipse.xtext.ui.resource.IResourceSetInitializer;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;
import org.eclipse.xtext.ui.workspace.EclipseProjectConfigProvider;
import org.eclipse.xtext.workspace.ProjectConfigAdapter;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

@SuppressWarnings("restriction")
class XtextAdapterFactoryEditingDomain extends AdapterFactoryEditingDomain
{

  @Inject
  private IResourceFactory resourceFactory;

  @Inject(optional = true)
  private IWorkspace workspace;

  @Inject
  private EclipseProjectConfigProvider projectConfigProvider;

  private ImmutableList< ? extends IResourceSetInitializer> initializers = ImmutableList.of();

  @Inject
  private void setContributions(ISharedStateContributionRegistry contributionRegistry)
  {
    initializers = contributionRegistry.getContributedInstances(IResourceSetInitializer.class);
  }

  @Inject
  private IExternalContentSupport externalContentSupport;

  @Inject
  private IExternalContentProvider externalContentProvider;

  @Inject
  private IResourceServiceProvider resourceServiceProvider;

  @Inject
  private IDirtyStateManager dirtyStateManager;

  public XtextAdapterFactoryEditingDomain(AdapterFactory adapterFactory, CommandStack commandStack)
  {
    super(adapterFactory, commandStack, (ResourceSet) null);
    this.resourceSet = new XtextAdapterFactoryEditingDomainResourceSet();
  }

  protected class XtextAdapterFactoryEditingDomainResourceSet extends SynchronizedXtextResourceSet
          implements IEditingDomainProvider
  {

    @Override
    public EditingDomain getEditingDomain()
    {
      return XtextAdapterFactoryEditingDomain.this;
    }

  }

  /**
   * Only the first resource is editable
   */
  @Override
  public boolean isReadOnly(Resource resource)
  {
    final var first = getResourceSet().getResources().get(0);
    return first != resource || super.isReadOnly(resource);
  }

  public Resource createResource(IEditorInput editorInput)
  {
    try
    {
      if (editorInput instanceof IStorageEditorInput)
      {
        final var storage = ((IStorageEditorInput) editorInput).getStorage();
        final var result = createResource(storage);
        if (result != null)
        {
          return result;
        }
      }
      else if (editorInput instanceof IURIEditorInput)
      {
        final var result = createResource(((IURIEditorInput) editorInput).getURI());
        if (result != null)
        {
          return result;
        }
      }
    }
    catch (final CoreException e)
    {
      throw new WrappedException(e);
    }
    throw new IllegalArgumentException("Couldn't create EMF Resource for input " + editorInput);
  }

  private Resource createResource(java.net.URI uri)
  {
    final var resourceSet = getResourceSet((IStorage) null);
    final var emfUri = URI.createURI(uri.toString());
    configureResourceSet(resourceSet, emfUri);
    final var resource = (XtextResource) resourceFactory.createResource(emfUri);
    resourceSet.getResources().add(resource);
    return resource;
  }

  protected Resource createResource(IStorage storage)
  {
    final var resourceSet = getResourceSet(storage);
    final var uri = URI.createPlatformResourceURI(storage.getFullPath().toString(), true);
    configureResourceSet(resourceSet, uri);
    var uriForResource = uri;
    if (!uri.isPlatform())
    {
      uriForResource = resourceSet.getURIConverter().normalize(uri);
    }
    final var resource = (XtextResource) resourceFactory.createResource(uriForResource);
    resourceSet.getResources().add(resource);
    return resource;
  }

  protected ResourceSet getResourceSet(/* @Nullable */ IStorage storage)
  {
    if (storage instanceof IFile)
    {
      return getResourceSet(((IFile) storage).getProject());
    }
    if (workspace != null && storage != null)
    {
      final var path = storage.getFullPath();
      if (path != null && !path.isEmpty())
      {
        final var firstSegment = path.segment(0);
        if (firstSegment != null)
        {
          final var project = workspace.getRoot().getProject(firstSegment);
          if (project.isAccessible())
          {
            return getResourceSet(project);
          }
        }
      }
    }
    return getResourceSet((IProject) null);
  }

  protected ResourceSet getResourceSet(IProject project)
  {
    final var set = resourceSet;
    if (project != null)
    {
      ProjectConfigAdapter.install(set, projectConfigProvider.createProjectConfig(project));
    }
    for (var i = 0; i < initializers.size(); i++)
    {
      initializers.get(i).initialize(set, project);
    }
    return set;
  }

  protected void configureResourceSet(ResourceSet resourceSet, URI primaryURI)
  {
    externalContentSupport.configureResourceSet(resourceSet, externalContentProvider);
    if (!(resourceServiceProvider instanceof IResourceServiceProviderExtension)
            || ((IResourceServiceProviderExtension) resourceServiceProvider).isSource(primaryURI))
    {
      SourceLevelURIsAdapter.setSourceLevelUris(resourceSet, Collections.singleton(primaryURI));
      resourceSet.eAdapters().add(new ResourceStorageProviderAdapter() {

        @Override
        public ResourceStorageLoadable getResourceStorageLoadable(StorageAwareResource resource)
        {
          if (!dirtyStateManager.hasContent(resource.getURI()))
          {
            return null;
          }
          return ((DirtyStateManager) dirtyStateManager)
                  .getResourceStorageLoadable(resource.getURI());
        }
      });
    }
  }

  /**
   * Checks whether syntax validation should be disabled for {@code uri}.
   * This is called when creating a resource for {@code uri}.
   * e.g. when we're creating a resource for an external file
   *
   * @param uri
   *          the URI to the file we want to check
   * @since 2.16
   */
  protected boolean isValidationDisabled(java.net.URI uri)
  {
    return true;
  }

  /**
   * @since 2.5
   */
  protected boolean isValidationDisabled(URI uri, IStorage storage)
  {
    return false;
  }

  /**
   * @since 2.4
   */
  protected boolean isValidationDisabled(IStorage storage)
  {
    return isValidationDisabled(null, storage);
  }
}