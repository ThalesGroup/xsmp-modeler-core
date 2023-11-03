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
package org.eclipse.xsmp.ide.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.xsmp.ide.server.XsmpBuildManager.Buildable;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.IMultiRootWorkspaceConfigFactory;
import org.eclipse.xtext.ide.server.IProjectDescriptionFactory;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.IExternalContentSupport;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.IWorkspaceConfig;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class XsmpWorkspaceManager
{

  private static final Logger LOG = Logger.getLogger(XsmpWorkspaceManager.class);

  @Inject
  private Provider<XsmpProjectManager> projectManagerProvider;

  @Inject
  private IMultiRootWorkspaceConfigFactory workspaceConfigFactory;

  @Inject
  private IProjectDescriptionFactory projectDescriptionFactory;

  @Inject
  private UriExtensions uriExtensions;

  private XsmpBuildManager buildManager;

  private List<WorkspaceFolder> workspaceFolders = Collections.emptyList();

  private Procedure2< ? super URI, ? super Iterable<Issue>> issueAcceptor;

  private IWorkspaceConfig workspaceConfig;

  private final Map<String, XsmpProjectManager> projectName2ProjectManager = new HashMap<>();

  private final List<ILanguageServerAccess.IBuildListener> buildListeners = new CopyOnWriteArrayList<>();

  private final Map<String, ResourceDescriptionsData> fullIndex = new HashMap<>();

  private final Map<URI, Document> openDocuments = createOpenDocuments();

  /**
   * Add the listener to this workspace.
   *
   * @param listener
   *          the new listener.
   */
  public void addBuildListener(ILanguageServerAccess.IBuildListener listener)
  {
    buildListeners.add(listener);
  }

  /**
   * Removes a build listener if it was previously registered
   */
  public void removeBuildListener(ILanguageServerAccess.IBuildListener listener)
  {
    buildListeners.remove(listener);
  }

  private final IExternalContentSupport.IExternalContentProvider openedDocumentsContentProvider = new IExternalContentSupport.IExternalContentProvider() {
    @Override
    public IExternalContentSupport.IExternalContentProvider getActualContentProvider()
    {
      return this;
    }

    @Override
    public String getContent(URI uri)
    {
      final var document = openDocuments.get(uri);
      if (document != null)
      {
        return document.getContents();
      }
      return null;
    }

    @Override
    public boolean hasContent(URI uri)
    {
      return isDocumentOpen(uri);
    }
  };

  /**
   * Inject the build manager and establish circular dependency.
   *
   * @param buildManager
   *          the build manager.
   */
  @Inject
  public void setBuildManager(XsmpBuildManager buildManager)
  {
    buildManager.setWorkspaceManager(this);
    this.buildManager = buildManager;
  }

  /**
   * Initialize a workspace at the given location.
   *
   * @param baseDir
   *          the location
   * @param issueAcceptor
   *          the issue acceptor
   * @param cancelIndicator
   *          allows to cancel the initialization
   */
  public void initialize(URI baseDir,
          Procedure2< ? super URI, ? super Iterable<Issue>> issueAcceptor,
          CancelIndicator cancelIndicator)
  {
    final var workspaceFolder = new WorkspaceFolder(uriExtensions.toUriString(baseDir), "");
    initialize(Collections.singletonList(workspaceFolder), issueAcceptor, cancelIndicator);
  }

  /**
   * Initialize a workspace with the given workspace folders.
   *
   * @param workspaceFolders
   *          the list of workspace root folders
   * @param issueAcceptor
   *          the issue acceptor
   * @param cancelIndicator
   *          allows to cancel the initialization
   */
  public void initialize(List<WorkspaceFolder> workspaceFolders,
          Procedure2< ? super URI, ? super Iterable<Issue>> issueAcceptor,
          CancelIndicator cancelIndicator)
  {
    this.workspaceFolders = new ArrayList<>(workspaceFolders);
    this.issueAcceptor = issueAcceptor;
    refreshWorkspaceConfig(cancelIndicator);
  }

  protected List<WorkspaceFolder> getWorkspaceFolders()
  {
    return workspaceFolders;
  }

  /**
   * Creates the open document map and returns it, never {@code null}.
   */
  protected Map<URI, Document> createOpenDocuments()
  {
    return new HashMap<>();
  }

  /**
   * @return the open document map, never {@code null}.
   */
  protected Map<URI, Document> getOpenDocuments()
  {
    return openDocuments;
  }

  /**
   * Updates the workspace folders and refreshes the workspace.
   */
  public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params,
          CancelIndicator cancelIndicator)
  {
    final var event = params.getEvent();
    final Map<String, WorkspaceFolder> uri2workspaceFolder = new HashMap<>();
    workspaceFolders.forEach(it -> uri2workspaceFolder.put(it.getUri(), it));
    event.getRemoved().forEach(it -> uri2workspaceFolder.remove(it.getUri()));
    event.getAdded().forEach(it -> {
      if (!uri2workspaceFolder.containsKey(it.getUri()))
      {
        uri2workspaceFolder.put(it.getUri(), it);
      }
    });
    workspaceFolders = new ArrayList<>(uri2workspaceFolder.values());
    refreshWorkspaceConfig(cancelIndicator);
  }

  protected IWorkspaceConfig createWorkspaceConfig()
  {
    return workspaceConfigFactory.getWorkspaceConfig(workspaceFolders);
  }

  /**
   * Refresh the workspace.
   */
  protected void refreshWorkspaceConfig(CancelIndicator cancelIndicator)
  {
    final var newWorkspaceConfig = createWorkspaceConfig();
    setWorkspaceConfig(newWorkspaceConfig);
    final List<ProjectDescription> newProjects = new ArrayList<>();
    final var projectNames = projectName2ProjectManager.keySet();
    final Set<String> remainingProjectNames = new HashSet<>(projectNames);
    for (final IProjectConfig projectConfig : getWorkspaceConfig().getProjects())
    {
      if (projectName2ProjectManager.containsKey(projectConfig.getName()))
      {
        remainingProjectNames.remove(projectConfig.getName());
      }
      else
      {
        final var projectManager = projectManagerProvider.get();
        final var projectDescription = projectDescriptionFactory
                .getProjectDescription(projectConfig);
        projectManager.initialize(projectDescription, projectConfig, issueAcceptor,
                openedDocumentsContentProvider, () -> fullIndex, cancelIndicator);
        projectName2ProjectManager.put(projectDescription.getName(), projectManager);
        newProjects.add(projectDescription);
      }
    }
    for (final String deletedProject : remainingProjectNames)
    {
      final var projectManager = projectName2ProjectManager.remove(deletedProject);
      projectManager.aboutToRemoveFromWorkspace();
      fullIndex.remove(deletedProject);
    }
    afterBuild(buildManager.doInitialBuild(newProjects, cancelIndicator));
  }

  /**
   * @return the workspace configuration
   * @throws ResponseErrorException
   *           if the workspace is not yet initialized
   */
  protected IWorkspaceConfig getWorkspaceConfig() throws ResponseErrorException
  {
    if (workspaceConfig == null)
    {
      final var error = new ResponseError(ResponseErrorCode.ServerNotInitialized,
              "Workspace has not been initialized yet.", null);
      throw new ResponseErrorException(error);
    }
    return workspaceConfig;
  }

  /**
   * @param workspaceConfig
   *          the new workspace configuration.
   */
  protected void setWorkspaceConfig(IWorkspaceConfig workspaceConfig)
  {
    this.workspaceConfig = workspaceConfig;
  }

  /**
   * Callback after a build was performend
   */
  protected void afterBuild(List<IResourceDescription.Delta> deltas)
  {
    for (final ILanguageServerAccess.IBuildListener listener : buildListeners)
    {
      listener.afterBuild(deltas);
    }
  }

  /**
   * Announce dirty and deleted files and provide means to start a build.
   *
   * @param dirtyFiles
   *          the dirty files
   * @param deletedFiles
   *          the deleted files
   * @return a build command that can be triggered
   */
  public Buildable didChangeFiles(List<URI> dirtyFiles, List<URI> deletedFiles,
          boolean shouldGenerate)
  {
    final var buildable = buildManager.submit(dirtyFiles, deletedFiles, shouldGenerate);
    return cancelIndicator -> {
      final var deltas = buildable.build(cancelIndicator);
      afterBuild(deltas);
      return deltas;
    };
  }

  /**
   * Returns the current index.
   */
  public IResourceDescriptions getIndex()
  {
    return new ChunkedResourceDescriptions(fullIndex);
  }

  /**
   * Returns the project that contains the given URI.
   *
   * @param uri
   *          the contained uri
   * @return the project base uri.
   */
  public URI getProjectBaseDir(URI uri)
  {
    final var projectConfig = getProjectConfig(uri);
    if (projectConfig != null)
    {
      return projectConfig.getPath();
    }
    return null;
  }

  /**
   * @param uri
   *          the contained uri
   * @return the project manager.
   */
  public XsmpProjectManager getProjectManager(URI uri)
  {
    final var projectConfig = getProjectConfig(uri);
    String name = null;
    if (projectConfig != null)
    {
      name = projectConfig.getName();
    }
    return getProjectManager(name);
  }

  /**
   * Find the project that contains the uri.
   */
  protected IProjectConfig getProjectConfig(URI uri)
  {
    return getWorkspaceConfig().findProjectContaining(uri);
  }

  /**
   * Return the project manager for the project with the given name.
   *
   * @param projectName
   *          the project name
   * @return the project manager
   */
  public XsmpProjectManager getProjectManager(String projectName)
  {
    return projectName2ProjectManager.get(projectName);
  }

  /**
   * Return all project managers.
   *
   * @return all project managers.
   */
  public List<XsmpProjectManager> getProjectManagers()
  {
    return ImmutableList.copyOf(projectName2ProjectManager.values());
  }

  /**
   * As opposed to {@link TextEdit}[] the positions in the edits of a
   * {@link DidChangeTextDocumentParams} refer to the
   * state after applying the preceding edits. See
   * https://microsoft.github.io/language-server-protocol/specification#textedit-1 and
   * https://github.com/microsoft/vscode/issues/23173#issuecomment-289378160 for details.
   * In particular, this has to be taken into account when undoing the deletion of multiple
   * characters at the end of a
   * line.
   *
   * @param version
   *          unused
   */
  public XsmpBuildManager.Buildable didChangeTextDocumentContent(URI uri, Integer version,
          Iterable<TextDocumentContentChangeEvent> changes)
  {
    final var contents = openDocuments.get(uri);
    if (contents == null)
    {
      LOG.error("The document " + uri + " has not been opened.");
      return Buildable.NO_BUILD;
    }
    openDocuments.put(uri, contents.applyTextDocumentChanges(changes));
    return didChangeFiles(List.of(uri), Collections.emptyList(), false);
  }

  /**
   * Mark the given document as open and build it.
   */
  public List<IResourceDescription.Delta> didOpen(URI uri, Integer version, String contents,
          CancelIndicator cancelIndicator)
  {
    return didOpen(uri, version, contents).build(cancelIndicator);
  }

  /**
   * Mark the given document as open.
   */
  public XsmpBuildManager.Buildable didOpen(URI uri, Integer version, String contents)
  {
    openDocuments.put(uri, new Document(version, contents));
    return Buildable.NO_BUILD;
  }

  /**
   * Mark the given document as closed.
   */
  public XsmpBuildManager.Buildable didClose(URI uri)
  {
    openDocuments.remove(uri);

    return Buildable.NO_BUILD;
  }

  /**
   * Return true if the given resource still exists.
   */
  protected boolean exists(URI uri)
  {
    final var projectManager = getProjectManager(uri);
    if (projectManager != null)
    {
      final var rs = projectManager.getResourceSet();
      if (rs != null)
      {
        return rs.getURIConverter().exists(uri, null);
      }
    }
    return false;
  }

  /**
   * Find the resource and the document with the given URI and perform a a read operation.
   */
  public <T> T doRead(URI uri,
          Function2< ? super Document, ? super XtextResource, ? extends T> work)
  {
    final Pair< ? super Document, ? super XtextResource> pair = read(uri);
    if (pair != null)
    {
      final var doc = (Document) pair.getFirst();
      final var resource = (XtextResource) pair.getSecond();
      return work.apply(doc, resource);
    }
    return work.apply(null, null);
  }

  /**
   * Find the resource and the document with the given URI.
   */
  public Pair< ? super Document, ? super XtextResource> read(URI uri)
  {
    final var resourceURI = uri.trimFragment();
    final var projectMnr = getProjectManager(resourceURI);
    if (projectMnr != null)
    {
      final var resource = projectMnr.getResource(resourceURI);
      if (resource instanceof final XtextResource xtextResource)
      {
        final var doc = getDocument(xtextResource);
        return Tuples.pair(doc, xtextResource);
      }
    }
    return null;
  }

  /**
   * Find the document for the given resource.
   *
   * @param resource
   *          the resource.
   * @return the document
   */
  protected Document getDocument(XtextResource resource)
  {
    final var doc = openDocuments.get(resource.getURI());
    if (doc != null)
    {
      return doc;
    }
    final var text = resource.getParseResult().getRootNode().getText();
    return new Document(1, text);
  }

  /**
   * Return true if there is a open document with the givne URI.
   *
   * @param uri
   *          the URI
   */
  public boolean isDocumentOpen(URI uri)
  {
    return openDocuments.containsKey(uri);
  }

}
