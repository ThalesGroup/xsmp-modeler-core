/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.ide.build.XsmpBuildRequest;
import org.eclipse.xsmp.ide.build.XsmpIncrementalBuilder;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.build.IncrementalBuilder;
import org.eclipse.xtext.build.IndexState;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IExternalContentSupport;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.workspace.ProjectConfigAdapter;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class XsmpProjectManager
{

  @Inject
  protected XsmpIncrementalBuilder incrementalBuilder;

  @Inject
  protected Provider<SynchronizedXtextResourceSet> resourceSetProvider;

  @Inject
  protected IResourceServiceProvider.Registry languagesRegistry;

  @Inject
  protected IFileSystemScanner fileSystemScanner;

  @Inject
  protected IExternalContentSupport externalContentSupport;

  private IndexState indexState = new IndexState();

  private URI baseDir;

  private Procedure2< ? super URI, ? super Iterable<Issue>> issueAcceptor;

  private Provider<Map<String, ResourceDescriptionsData>> indexProvider;

  private IExternalContentSupport.IExternalContentProvider openedDocumentsContentProvider;

  private XtextResourceSet resourceSet;

  private ProjectDescription projectDescription;

  private IProjectConfig projectConfig;

  public void initialize(ProjectDescription description, IProjectConfig projectConfig,
          Procedure2< ? super URI, ? super Iterable<Issue>> acceptor,
          IExternalContentSupport.IExternalContentProvider openedDocumentsContentProvider,
          Provider<Map<String, ResourceDescriptionsData>> indexProvider,
          CancelIndicator cancelIndicator)
  {
    projectDescription = description;
    this.projectConfig = projectConfig;
    baseDir = projectConfig.getPath();
    issueAcceptor = acceptor;
    this.openedDocumentsContentProvider = openedDocumentsContentProvider;
    this.indexProvider = indexProvider;
  }

  public void setProjectConfig(IXsmpProjectConfig newProjectConfig, CancelIndicator cancelIndicator)
  {

    projectConfig = newProjectConfig;
    doInitialBuild(cancelIndicator);
  }

  /**
   * Initial build of this project.
   */
  public IncrementalBuilder.Result doInitialBuild(CancelIndicator cancelIndicator)
  {

    final List<URI> allUris = new ArrayList<>();

    if (getProjectConfig().getPath() != null)
    {
      allUris.add(getProjectConfig().getPath().appendSegment(XsmpConstants.XSMP_PROJECT_FILENAME));
    }

    for (final ISourceFolder srcFolder : getProjectConfig().getSourceFolders())
    {
      allUris.addAll(srcFolder.getAllResources(fileSystemScanner));
    }

    return doBuild(allUris, Collections.emptyList(), Collections.emptyList(), cancelIndicator,
            true);

  }

  /**
   * Build this project.
   */
  public IncrementalBuilder.Result doBuild(List<URI> dirtyFiles, List<URI> deletedFiles,
          List<IResourceDescription.Delta> externalDeltas, CancelIndicator cancelIndicator,
          boolean shouldGenerate)
  {
    final var request = newBuildRequest(dirtyFiles, deletedFiles, externalDeltas, cancelIndicator,
            shouldGenerate);
    final var result = incrementalBuilder.build(request,
            languagesRegistry::getResourceServiceProvider);
    indexState = result.getIndexState();
    resourceSet = request.getResourceSet();
    indexProvider.get().put(projectDescription.getName(), indexState.getResourceDescriptions());
    return result;
  }

  /**
   * Creates a new build request for this project.
   */
  protected XsmpBuildRequest newBuildRequest(List<URI> changedFiles, List<URI> deletedFiles,
          List<IResourceDescription.Delta> externalDeltas, CancelIndicator cancelIndicator,
          boolean shouldGenerate)
  {
    final var result = new XsmpBuildRequest();
    result.setBaseDir(baseDir);
    result.setState(new IndexState(indexState.getResourceDescriptions().copy(),
            indexState.getFileMappings().copy()));
    result.setResourceSet(createFreshResourceSet(result.getState().getResourceDescriptions()));
    result.setDirtyFiles(changedFiles);
    result.setDeletedFiles(deletedFiles);
    result.setExternalDeltas(externalDeltas);
    result.setAfterValidate((var uri, var issues) -> {
      issueAcceptor.apply(uri, issues);
      return true;
    });
    result.setCancelIndicator(cancelIndicator);
    result.setIndexOnly(projectConfig.isIndexOnly());
    result.setShouldGenerate(shouldGenerate);
    return result;
  }

  /**
   * Create and configure a new resource set for this project.
   */
  public XtextResourceSet createNewResourceSet(ResourceDescriptionsData newIndex)
  {
    final var result = resourceSetProvider.get();
    projectDescription.attachToEmfObject(result);
    ProjectConfigAdapter.install(result, projectConfig);
    final var index = new ChunkedResourceDescriptions(indexProvider.get(), result);
    index.setContainer(projectDescription.getName(), newIndex);
    externalContentSupport.configureResourceSet(result, openedDocumentsContentProvider);
    return result;
  }

  /**
   * Create an empty resource set.
   */
  protected XtextResourceSet createFreshResourceSet(ResourceDescriptionsData newIndex)
  {
    if (resourceSet == null)
    {
      resourceSet = createNewResourceSet(newIndex);
    }
    else
    {
      final var resDescs = ChunkedResourceDescriptions.findInEmfObject(resourceSet);
      for (final Map.Entry<String, ResourceDescriptionsData> entry : indexProvider.get().entrySet())
      {
        resDescs.setContainer(entry.getKey(), entry.getValue());
      }
      resDescs.setContainer(projectDescription.getName(), newIndex);
    }
    return resourceSet;
  }

  /**
   * Create and configure a new live resource set for this project.
   */
  public XtextResourceSet createLiveScopeResourceSet()
  {
    final var rs = createNewResourceSet(getIndexState().getResourceDescriptions());
    rs.getLoadOptions().put(ResourceDescriptionsProvider.LIVE_SCOPE, true);
    return rs;
  }

  /**
   * Get the resource with the given URI.
   */
  public Resource getResource(URI uri)
  {
    if (resourceSet == null)
    {
      return null;
    }
    final var resource = resourceSet.getResource(uri, true);
    resource.getContents();
    return resource;
  }

  public void reportProjectIssue(String message, String code, Severity severity)
  {
    final var result = new Issue.IssueImpl();
    result.setMessage(message);
    result.setCode(code);
    result.setSeverity(severity);
    result.setUriToProblem(baseDir);
    issueAcceptor.apply(baseDir, List.of(result));
  }

  public IndexState getIndexState()
  {
    return indexState;
  }

  protected void setIndexState(IndexState indexState)
  {
    this.indexState = indexState;
  }

  public URI getBaseDir()
  {
    return baseDir;
  }

  protected Procedure2< ? super URI, ? super Iterable<Issue>> getIssueAcceptor()
  {
    return issueAcceptor;
  }

  protected Provider<Map<String, ResourceDescriptionsData>> getIndexProvider()
  {
    return indexProvider;
  }

  protected IExternalContentSupport.IExternalContentProvider getOpenedDocumentsContentProvider()
  {
    return openedDocumentsContentProvider;
  }

  public XtextResourceSet getResourceSet()
  {
    return resourceSet;
  }

  public ProjectDescription getProjectDescription()
  {
    return projectDescription;
  }

  public IProjectConfig getProjectConfig()
  {
    return projectConfig;
  }

  public void aboutToRemoveFromWorkspace()
  {
    for (final ISourceFolder srcFolder : projectConfig.getSourceFolders())
    {
      for (final URI resourceURI : srcFolder.getAllResources(fileSystemScanner))
      {
        issueAcceptor.apply(resourceURI, Collections.emptyList());
      }
    }
  }

}
