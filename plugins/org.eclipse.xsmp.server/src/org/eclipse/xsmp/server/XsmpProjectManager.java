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

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.server.build.XsmpIncrementalBuilder;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.build.BuildRequest;
import org.eclipse.xtext.build.IncrementalBuilder;
import org.eclipse.xtext.build.IndexState;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.ide.server.UriExtensions;
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

  @Inject
  private UriExtensions uriExtensions;

  private IndexState indexState = new IndexState();

  private URI baseDir;

  private Procedure2< ? super URI, ? super Iterable<Issue>> issueAcceptor;

  private Provider<Map<String, ResourceDescriptionsData>> indexProvider;

  private IExternalContentSupport.IExternalContentProvider openedDocumentsContentProvider;

  private XtextResourceSet resourceSet;

  private ProjectDescription projectDescription;

  private IProjectConfig projectConfig;

  // list of URI defined in the project before a call to refreshProjectConfig
  private final List<URI> oldUris = new ArrayList<>();

  private final List<URI> oldDependenciesUris = new ArrayList<>();

  public void initialize(ProjectDescription description, IProjectConfig projectConfig,
          Procedure2< ? super URI, ? super Iterable<Issue>> acceptor,
          IExternalContentSupport.IExternalContentProvider openedDocumentsContentProvider,
          Provider<Map<String, ResourceDescriptionsData>> indexProvider)
  {
    projectDescription = description;
    this.projectConfig = projectConfig;
    baseDir = projectConfig.getPath();
    issueAcceptor = acceptor;
    this.openedDocumentsContentProvider = openedDocumentsContentProvider;
    this.indexProvider = indexProvider;
  }

  /**
   * Collect sources for all input projects
   *
   * @param projects
   *          list of projects
   * @param allUris
   *          collected uris
   */
  private void collectUris(IProjectConfig project, List<URI> allUris)
  {

    if (project.getPath() != null)
    {
      final var file = new File(project.getPath().toFileString());
      final var path = Paths.get(file.getAbsoluteFile().toURI());

      final var uri = uriExtensions
              .toEmfUri(path.resolve(XsmpConstants.XSMP_PROJECT_FILENAME).toUri());
      allUris.add(uri);
    }

    for (final ISourceFolder srcFolder : project.getSourceFolders())
    {
      allUris.addAll(srcFolder.getAllResources(fileSystemScanner));
    }

  }

  /**
   * Initial build of this project.
   */
  public IncrementalBuilder.Result doInitialBuild(CancelIndicator cancelIndicator,
          Collection<IProjectConfig> dependencies)
  {
    // collect all uris of dependencies
    final List<URI> allDependenciesUris = new ArrayList<>();
    dependencies.forEach(d -> collectUris(d, allDependenciesUris));

    // keep only URI that should be deleted
    oldDependenciesUris.removeAll(allDependenciesUris);

    return doBuild(allDependenciesUris, oldDependenciesUris, Collections.emptyList(),
            cancelIndicator, false, true);
  }

  public IncrementalBuilder.Result doInitialBuild(CancelIndicator cancelIndicator)
  {
    // collect all uris of current project
    final List<URI> allUris = new ArrayList<>();
    collectUris(projectConfig, allUris);

    // keep only URI that should be deleted
    oldUris.removeAll(allUris);

    return doBuild(allUris, oldUris, Collections.emptyList(), cancelIndicator, false, false);
  }

  /**
   * Build this project.
   */
  public IncrementalBuilder.Result doBuild(List<URI> dirtyFiles, List<URI> deletedFiles,
          List<IResourceDescription.Delta> externalDeltas, CancelIndicator cancelIndicator,
          boolean shouldGenerate, boolean indexOnly)
  {
    final var request = newBuildRequest(dirtyFiles, deletedFiles, externalDeltas, cancelIndicator,
            indexOnly);
    final var result = incrementalBuilder.build(request,
            languagesRegistry::getResourceServiceProvider, shouldGenerate);

    indexState = result.getIndexState();
    resourceSet = request.getResourceSet();
    indexProvider.get().put(projectDescription.getName(), indexState.getResourceDescriptions());
    return result;
  }

  /**
   * Creates a new build request for this project.
   */
  protected BuildRequest newBuildRequest(List<URI> changedFiles, List<URI> deletedFiles,
          List<IResourceDescription.Delta> externalDeltas, CancelIndicator cancelIndicator,
          boolean indexOnly)
  {
    final var result = new BuildRequest();
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
    result.setIndexOnly(indexOnly);
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

  public void refreshProjectConfig(IProjectConfig newProjectConfig)
  {
    // store current URIs
    oldDependenciesUris.clear();
    if (projectConfig instanceof final IXsmpProjectConfig xsmpConfig)
    {
      xsmpConfig.getAllDependencies().forEach(d -> collectUris(d, oldDependenciesUris));
    }

    oldUris.clear();
    collectUris(projectConfig, oldUris);

    projectConfig = newProjectConfig;
    baseDir = newProjectConfig.getPath();
    resourceSet = null;
    // update projectDescription with updated dependencies
    if (newProjectConfig instanceof final IXsmpProjectConfig cfg)
    {
      projectDescription.setDependencies(new ArrayList<>(cfg.getDependencies()));
    }
  }

}
