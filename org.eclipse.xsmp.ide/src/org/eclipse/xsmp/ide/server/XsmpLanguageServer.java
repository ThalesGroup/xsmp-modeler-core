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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensOptions;
import org.eclipse.lsp4j.CodeLensParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeNotebookDocumentParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams;
import org.eclipse.lsp4j.DidCloseNotebookDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenNotebookDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveNotebookDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentHighlightParams;
import org.eclipse.lsp4j.DocumentOnTypeFormattingParams;
import org.eclipse.lsp4j.DocumentRangeFormattingParams;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.ExecuteCommandCapabilities;
import org.eclipse.lsp4j.ExecuteCommandOptions;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.FoldingRange;
import org.eclipse.lsp4j.FoldingRangeCapabilities;
import org.eclipse.lsp4j.FoldingRangeRequestParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.InitializedParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PrepareRenameDefaultBehavior;
import org.eclipse.lsp4j.PrepareRenameParams;
import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.RenameCapabilities;
import org.eclipse.lsp4j.RenameOptions;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.SemanticTokens;
import org.eclipse.lsp4j.SemanticTokensLegend;
import org.eclipse.lsp4j.SemanticTokensParams;
import org.eclipse.lsp4j.SemanticTokensWithRegistrationOptions;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureHelpOptions;
import org.eclipse.lsp4j.SignatureHelpParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.WorkspaceFoldersOptions;
import org.eclipse.lsp4j.WorkspaceServerCapabilities;
import org.eclipse.lsp4j.WorkspaceSymbol;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethodProvider;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Either3;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.NotebookDocumentService;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.eclipse.xsmp.ide.hover.XsmpHoverService;
import org.eclipse.xsmp.ide.server.XsmpBuildManager.Buildable;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.ide.server.ICapabilitiesContributor;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.ILanguageServerAccess.IBuildListener;
import org.eclipse.xtext.ide.server.ILanguageServerExtension;
import org.eclipse.xtext.ide.server.ILanguageServerShutdownAndExitHandler;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.ide.server.codelens.ICodeLensResolver;
import org.eclipse.xtext.ide.server.codelens.ICodeLensService;
import org.eclipse.xtext.ide.server.commands.ExecutableCommandRegistry;
import org.eclipse.xtext.ide.server.concurrent.RequestManager;
import org.eclipse.xtext.ide.server.contentassist.ContentAssistService;
import org.eclipse.xtext.ide.server.folding.FoldingRangeService;
import org.eclipse.xtext.ide.server.formatting.FormattingService;
import org.eclipse.xtext.ide.server.hover.IHoverService;
import org.eclipse.xtext.ide.server.occurrences.IDocumentHighlightService;
import org.eclipse.xtext.ide.server.rename.IRenameService2;
import org.eclipse.xtext.ide.server.semantictokens.SemanticTokensService;
import org.eclipse.xtext.ide.server.signatureHelp.ISignatureHelpService;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolService;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;
import org.eclipse.xtext.ide.server.symbol.IDocumentSymbolService;
import org.eclipse.xtext.ide.server.symbol.WorkspaceSymbolService;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.util.BufferedCancelIndicator;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

public class XsmpLanguageServer
        implements LanguageServer, WorkspaceService, TextDocumentService, NotebookDocumentService,
        LanguageClientAware, Endpoint, JsonRpcMethodProvider, ILanguageServerAccess.IBuildListener
{

  private static final Logger LOG = Logger.getLogger(XsmpLanguageServer.class);

  @Inject
  private RequestManager requestManager;

  @Inject
  private WorkspaceSymbolService workspaceSymbolService;

  @Inject
  private UriExtensions uriExtensions;

  @Inject
  private IResourceServiceProvider.Registry languagesRegistry;

  @Inject
  private ExecutableCommandRegistry commandRegistry;

  @Inject
  private ILanguageServerShutdownAndExitHandler shutdownAndExitHandler;

  @Inject
  private SemanticTokensService semanticTokensService;

  private XsmpWorkspaceManager workspaceManager;

  private InitializeParams initializeParams;

  private InitializeResult initializeResult;

  private XsmpWorkspaceResourceAccess resourceAccess;

  private LanguageClient client;

  private ConcurrentMap<String, JsonRpcMethod> supportedMethods;

  private final CompletableFuture<InitializedParams> initialized = new CompletableFuture<>();

  private final Multimap<String, Endpoint> extensionProviders = LinkedListMultimap.create();

  @Inject
  public void setWorkspaceManager(XsmpWorkspaceManager manager)
  {
    workspaceManager = manager;
    resourceAccess = new XsmpWorkspaceResourceAccess(workspaceManager);
  }

  private Set< ? extends IResourceServiceProvider> getAllLanguages()
  {
    // provide a stable order
    final Map<String, IResourceServiceProvider> sorted = new TreeMap<>();
    for (final String ext : languagesRegistry.getExtensionToFactoryMap().keySet())
    {
      sorted.put(ext,
              languagesRegistry.getResourceServiceProvider(URI.createURI("synth:///file." + ext)));
    }
    return ImmutableSet.copyOf(sorted.values());
  }

  @Override
  public CompletableFuture<InitializeResult> initialize(InitializeParams params)
  {
    if (initializeParams != null)
    {
      throw new IllegalStateException("This language server has already been initialized.");
    }
    if (languagesRegistry.getExtensionToFactoryMap().isEmpty())
    {
      throw new IllegalStateException(
              "No Xtext languages have been registered. Please make sure you have added the languages\'s setup class in \'/META-INF/services/org.eclipse.xtext.ISetup\'");
    }
    initializeParams = params;

    final var result = new InitializeResult();

    result.setCapabilities(createServerCapabilities(params));
    access.addBuildListener(this);
    return requestManager.runWrite(() -> {

      var workspaceFolders = params.getWorkspaceFolders();
      if (workspaceFolders == null)
      {
        workspaceFolders = Collections.emptyList();
      }
      workspaceManager.initialize(workspaceFolders, this::publishDiagnostics,
              CancelIndicator.NullImpl);

      return result;
    }, (cancelIndicator, it) -> it).thenApply(it -> initializeResult = it);
  }

  /**
   * Configure the server capabilities for this instance.
   *
   * @param params
   *          the initialization parameters
   * @return the server capabilities
   */
  protected ServerCapabilities createServerCapabilities(InitializeParams params)
  {
    final var serverCapabilities = new ServerCapabilities();
    serverCapabilities.setHoverProvider(true);
    serverCapabilities.setDefinitionProvider(true);
    serverCapabilities.setReferencesProvider(true);
    serverCapabilities.setDocumentSymbolProvider(true);
    serverCapabilities.setWorkspaceSymbolProvider(true);
    final Set< ? extends IResourceServiceProvider> allLanguages = getAllLanguages();
    if (allLanguages.stream()
            .anyMatch(serviceProvider -> serviceProvider.get(ICodeLensService.class) != null))
    {
      final var codeLensOptions = new CodeLensOptions();
      codeLensOptions.setResolveProvider(allLanguages.stream()
              .anyMatch(serviceProvider -> serviceProvider.get(ICodeLensResolver.class) != null));
      serverCapabilities.setCodeLensProvider(codeLensOptions);
    }
    serverCapabilities.setCodeActionProvider(allLanguages.stream()
            .anyMatch(serviceProvider -> serviceProvider.get(ICodeActionService2.class) != null));

    serverCapabilities.setSignatureHelpProvider(new SignatureHelpOptions(List.of("(", ",")));
    serverCapabilities.setTextDocumentSync(TextDocumentSyncKind.Incremental);
    final var legend = new SemanticTokensLegend(semanticTokensService.getTokenTypes(),
            semanticTokensService.getTokenModifiers());
    final var semanticTokensWithRegistrationOptions = new SemanticTokensWithRegistrationOptions(
            legend);
    semanticTokensWithRegistrationOptions.setFull(true);
    semanticTokensWithRegistrationOptions.setRange(false);
    serverCapabilities.setSemanticTokensProvider(semanticTokensWithRegistrationOptions);
    final var completionOptions = new CompletionOptions();
    completionOptions.setResolveProvider(false);
    completionOptions.setTriggerCharacters(List.of("."));
    serverCapabilities.setCompletionProvider(completionOptions);
    serverCapabilities.setDocumentFormattingProvider(true);
    serverCapabilities.setDocumentRangeFormattingProvider(true);
    serverCapabilities.setDocumentHighlightProvider(true);
    ClientCapabilities clientCapabilities = null;
    if (params != null)
    {
      clientCapabilities = params.getCapabilities();
    }
    TextDocumentClientCapabilities textDocument = null;
    if (clientCapabilities != null)
    {
      textDocument = clientCapabilities.getTextDocument();
    }
    RenameCapabilities rename = null;
    FoldingRangeCapabilities folding = null;
    if (textDocument != null)
    {
      rename = textDocument.getRename();
      folding = textDocument.getFoldingRange();
    }
    if (folding != null)
    {
      serverCapabilities.setFoldingRangeProvider(allLanguages.stream()
              .anyMatch(serviceProvider -> serviceProvider.get(FoldingRangeService.class) != null));
    }
    Boolean prepareSupport = null;
    if (rename != null)
    {
      prepareSupport = rename.getPrepareSupport();
    }
    final var clientPrepareSupport = Objects.equal(Boolean.TRUE, prepareSupport);
    if (clientPrepareSupport && allLanguages.stream()
            .anyMatch(serviceProvider -> serviceProvider.get(IRenameService2.class) != null))
    {
      final var renameOptions = new RenameOptions();
      renameOptions.setPrepareProvider(true);
      serverCapabilities.setRenameProvider(Either.<Boolean, RenameOptions> forRight(renameOptions));
    }
    else
    {
      serverCapabilities.setRenameProvider(Either.forLeft(allLanguages.stream()
              .anyMatch(serviceProvider -> serviceProvider.get(IRenameService2.class) != null)));
    }
    WorkspaceClientCapabilities workspace = null;
    if (clientCapabilities != null)
    {
      workspace = clientCapabilities.getWorkspace();
    }
    ExecuteCommandCapabilities executeCommand = null;
    if (workspace != null)
    {
      executeCommand = workspace.getExecuteCommand();
      if (workspace.getWorkspaceFolders() == Boolean.TRUE)
      {
        final var workspaceFoldersOptions = new WorkspaceFoldersOptions();
        workspaceFoldersOptions.setSupported(true);
        workspaceFoldersOptions.setChangeNotifications(true);
        serverCapabilities.setWorkspace(new WorkspaceServerCapabilities(workspaceFoldersOptions));
      }
    }
    if (executeCommand != null)
    {
      commandRegistry.initialize(allLanguages, clientCapabilities, client);
      final var executeCommandOptions = new ExecuteCommandOptions();
      executeCommandOptions.setCommands(commandRegistry.getCommands());
      serverCapabilities.setExecuteCommandProvider(executeCommandOptions);
    }

    for (final IResourceServiceProvider language : allLanguages)
    {
      final var capabilitiesContributor = language.get(ICapabilitiesContributor.class);
      if (capabilitiesContributor != null)
      {
        capabilitiesContributor.contribute(serverCapabilities, params);
      }
    }
    return serverCapabilities;
  }

  @Override
  public void initialized(InitializedParams params)
  {
    initialized.complete(params);
  }

  @Override
  public void connect(LanguageClient client)
  {
    this.client = client;
  }

  @Override
  public void exit()
  {
    shutdownAndExitHandler.exit();
  }

  @Override
  public CompletableFuture<Object> shutdown()
  {
    shutdownAndExitHandler.shutdown();
    return CompletableFuture.completedFuture(new Object());
  }

  @Override
  public TextDocumentService getTextDocumentService()
  {
    return this;
  }

  @Override
  public WorkspaceService getWorkspaceService()
  {
    return this;
  }

  @Override
  public NotebookDocumentService getNotebookDocumentService()
  {
    return this;
  }

  @Override
  public void didOpen(DidOpenTextDocumentParams params)
  {
    runBuildable(() -> toBuildable(params));
  }

  /**
   * Evaluate the params and deduce the respective build command.
   */
  protected Buildable toBuildable(DidOpenTextDocumentParams params)
  {
    final var textDocument = params.getTextDocument();
    return workspaceManager.didOpen(getURI(textDocument), textDocument.getVersion(),
            textDocument.getText());
  }

  @Override
  public void didChange(DidChangeTextDocumentParams params)
  {
    runBuildable(() -> toBuildable(params));
  }

  /**
   * Evaluate the params and deduce the respective build command.
   */
  protected Buildable toBuildable(DidChangeTextDocumentParams params)
  {
    final var textDocument = params.getTextDocument();
    return workspaceManager.didChangeTextDocumentContent(getURI(textDocument),
            textDocument.getVersion(), params.getContentChanges());
  }

  @Override
  public void didClose(DidCloseTextDocumentParams params)
  {
    runBuildable(() -> toBuildable(params));
  }

  /**
   * Evaluate the params and deduce the respective build command.
   */
  protected Buildable toBuildable(DidCloseTextDocumentParams params)
  {
    return workspaceManager.didClose(getURI(params.getTextDocument()));
  }

  @Override
  public void didSave(DidSaveTextDocumentParams params)
  {

    runBuildable(() -> toBuildable(params));
  }

  /**
   * Evaluate the params and deduce the respective build command.
   */
  protected Buildable toBuildable(DidSaveTextDocumentParams params)
  {

    return workspaceManager.didChangeFiles(List.of(getURI(params.getTextDocument())),
            Collections.emptyList(), true);
  }

  @Override
  public void didChangeWatchedFiles(DidChangeWatchedFilesParams params)
  {
    runBuildable(() -> toBuildable(params));
  }

  /**
   * Evaluate the params and deduce the respective build command.
   */
  protected Buildable toBuildable(DidChangeWatchedFilesParams params)
  {
    final List<URI> dirtyFiles = new ArrayList<>();
    final List<URI> deletedFiles = new ArrayList<>();
    params.getChanges().stream()
            .map(fileEvent -> Pair.of(uriExtensions.toUri(fileEvent.getUri()), fileEvent.getType()))
            .filter(pair -> !workspaceManager.isDocumentOpen(pair.getKey())).forEach(pair -> {
              if (pair.getValue() == FileChangeType.Deleted)
              {
                deletedFiles.add(pair.getKey());
              }
              else
              {
                dirtyFiles.add(pair.getKey());
              }
            });
    return workspaceManager.didChangeFiles(dirtyFiles, deletedFiles, false);
  }

  /**
   * Compute a buildable and run the build in a write action
   *
   * @param newBuildable
   *          the factory for the buildable.
   */
  protected void runBuildable(Supplier< ? extends Buildable> newBuildable)
  {
    requestManager.runWrite(newBuildable::get,
            (cancelIndicator, buildable) -> buildable.build(cancelIndicator));
  }

  @Override
  public void didChangeConfiguration(DidChangeConfigurationParams params)
  {
    requestManager.runWrite(() -> {
      for (final var project : workspaceManager.getProjectManagers())
      {
        project.doInitialBuild(CancelIndicator.NullImpl);
      }
      return null;
    }, (a, b) -> null);

  }

  @Override
  public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params)
  {
    requestManager.runWrite(() -> {
      workspaceManager.didChangeWorkspaceFolders(params, CancelIndicator.NullImpl);
      return null;
    }, (a, b) -> null);
  }

  private void publishDiagnostics(URI uri, Iterable< ? extends Issue> issues)
  {
    initialized.thenAccept(initParams -> {
      final var publishDiagnosticsParams = new PublishDiagnosticsParams();
      publishDiagnosticsParams.setUri(uriExtensions.toUriString(uri));
      publishDiagnosticsParams.setDiagnostics(toDiagnostics(issues));
      client.publishDiagnostics(publishDiagnosticsParams);
    });
  }

  /**
   * Convert the given issues to diagnostics. Does not return any issue with severity
   * {@link Severity#IGNORE ignore}
   * by default.
   */
  protected List<Diagnostic> toDiagnostics(Iterable< ? extends Issue> issues)
  {
    final List<Diagnostic> result = new ArrayList<>();
    for (final Issue issue : issues)
    {
      if (issue.getSeverity() != Severity.IGNORE)
      {
        result.add(toDiagnostic(issue));
      }
    }
    return result;
  }

  /**
   * Convert the given issue to a diagnostic.
   */
  protected Diagnostic toDiagnostic(Issue issue)
  {
    final var result = new Diagnostic();
    result.setCode(issue.getCode());
    result.setData(issue.getData());
    result.setMessage(issue.getMessage());
    result.setSeverity(toDiagnosticSeverity(issue.getSeverity()));

    // line and column numbers in LSP are 0-based
    final var start = new Position(Math.max(0, issue.getLineNumber() - 1),
            Math.max(0, issue.getColumn() - 1));
    final var end = new Position(Math.max(0, issue.getLineNumberEnd() - 1),
            Math.max(0, issue.getColumnEnd() - 1));
    result.setRange(new Range(start, end));
    return result;
  }

  /**
   * Convert the severity to a lsp {@link DiagnosticSeverity}.
   * Defaults to severity {@link DiagnosticSeverity#Hint hint}.
   */
  protected DiagnosticSeverity toDiagnosticSeverity(Severity severity)
  {
    return switch (severity)
    {
      case ERROR -> DiagnosticSeverity.Error;
      case IGNORE -> DiagnosticSeverity.Hint;
      case INFO -> DiagnosticSeverity.Information;
      case WARNING -> DiagnosticSeverity.Warning;
      default -> DiagnosticSeverity.Hint;
    };
  }

  @Override
  public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(
          CompletionParams params)
  {
    return requestManager.runRead(cancelIndicator -> completion(cancelIndicator, params));
  }

  /**
   * Compute the completion items.
   */
  protected Either<List<CompletionItem>, CompletionList> completion(
          CancelIndicator originalCancelIndicator, CompletionParams params)
  {
    final var uri = getURI(params);
    final var contentAssistService = getService(uri, ContentAssistService.class);
    if (contentAssistService == null)
    {
      return Either.forRight(new CompletionList());
    }
    final var cancelIndicator = new BufferedCancelIndicator(originalCancelIndicator);
    return Either.forRight(workspaceManager.doRead(uri, (doc, res) -> contentAssistService
            .createCompletionList(doc, res, params, cancelIndicator)));
  }

  /**
   * Obtain the URI from the given parameters.
   */
  protected URI getURI(TextDocumentPositionParams params)
  {
    return getURI(params.getTextDocument());
  }

  /**
   * Obtain the URI from the given identifier.
   */
  protected URI getURI(TextDocumentIdentifier documentIdentifier)
  {
    return uriExtensions.toUri(documentIdentifier.getUri());
  }

  /**
   * Obtain the URI from the given document item.
   */
  protected URI getURI(TextDocumentItem documentItem)
  {
    return uriExtensions.toUri(documentItem.getUri());
  }

  @Override
  public CompletableFuture<Either<List< ? extends Location>, List< ? extends LocationLink>>> definition(
          DefinitionParams params)
  {
    return requestManager.runRead(cancelIndicator -> definition(params, cancelIndicator));
  }

  /**
   * Compute the definition. Executed in a read request.
   */
  protected Either<List< ? extends Location>, List< ? extends LocationLink>> definition(
          DefinitionParams params, CancelIndicator cancelIndicator)
  {
    return Either.forLeft(definition(cancelIndicator, params));
  }

  /**
   * Compute the definition.
   */
  protected List< ? extends Location> definition(CancelIndicator cancelIndicator,
          DefinitionParams params)
  {
    final var uri = getURI(params);
    final var documentSymbolService = getService(uri, DocumentSymbolService.class);
    if (documentSymbolService == null)
    {
      return Collections.emptyList();
    }
    return workspaceManager.doRead(uri, (doc, res) -> documentSymbolService.getDefinitions(doc, res,
            params, resourceAccess, cancelIndicator));
  }

  @Override
  public CompletableFuture<List< ? extends Location>> references(ReferenceParams params)
  {
    return requestManager.runRead(cancelIndicator -> references(params, cancelIndicator));
  }

  /**
   * Compute the references. Executed in read request.
   */
  protected List< ? extends Location> references(ReferenceParams params,
          CancelIndicator cancelIndicator)
  {
    final var uri = getURI(params);
    final var documentSymbolService = getService(uri, DocumentSymbolService.class);
    if (documentSymbolService == null)
    {
      return Collections.emptyList();
    }
    return workspaceManager.doRead(uri,
            (document, resource) -> documentSymbolService.getReferences(document, resource, params,
                    resourceAccess, workspaceManager.getIndex(), cancelIndicator));
  }

  @Override
  public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(
          DocumentSymbolParams params)
  {
    return requestManager.runRead(cancelIndicator -> Lists
            .transform(documentSymbol(params, cancelIndicator), Either::forRight));
  }

  /**
   * Compute the symbol information. Executed in a read request.
   */
  protected List<DocumentSymbol> documentSymbol(DocumentSymbolParams params,
          CancelIndicator cancelIndicator)
  {
    final var uri = getURI(params.getTextDocument());
    final var documentSymbolService = getIDocumentSymbolService(getResourceServiceProvider(uri));
    if (documentSymbolService == null)
    {
      return Collections.emptyList();
    }
    return workspaceManager.doRead(uri, (document, resource) -> documentSymbolService
            .getSymbols(document, resource, params, cancelIndicator));
  }

  protected IDocumentSymbolService getIDocumentSymbolService(
          IResourceServiceProvider serviceProvider)
  {
    if (serviceProvider == null)
    {
      return null;
    }
    if (isHierarchicalDocumentSymbolSupport())
    {
      return serviceProvider.get(HierarchicalDocumentSymbolService.class);
    }
    return serviceProvider.get(DocumentSymbolService.class);
  }

  /**
   * {@code true} if the {@code TextDocumentClientCapabilities} explicitly declares the hierarchical
   * document symbol
   * support at LS initialization time. Otherwise, false.
   */
  protected boolean isHierarchicalDocumentSymbolSupport()
  {
    final var capabilities = initializeParams.getCapabilities();
    if (capabilities == null)
    {
      return false;
    }
    final var textDocument = capabilities.getTextDocument();
    if (textDocument == null)
    {
      return false;
    }
    final var documentSymbol = textDocument.getDocumentSymbol();
    if (documentSymbol == null)
    {
      return false;
    }
    final var hierarchicalDocumentSymbolSupport = documentSymbol
            .getHierarchicalDocumentSymbolSupport();
    if (hierarchicalDocumentSymbolSupport == null)
    {
      return false;
    }
    return hierarchicalDocumentSymbolSupport;
  }

  @Override
  public CompletableFuture<Either<List< ? extends SymbolInformation>, List< ? extends WorkspaceSymbol>>> symbol(
          WorkspaceSymbolParams params)
  {
    return requestManager
            .runRead(cancelIndicator -> Either.forRight(symbol(params, cancelIndicator)));
  }

  /**
   * Compute the symbol information. Executed in a read request.
   */
  protected List< ? extends WorkspaceSymbol> symbol(WorkspaceSymbolParams params,
          CancelIndicator cancelIndicator)
  {
    return workspaceSymbolService.getSymbols(params.getQuery(), resourceAccess,
            workspaceManager.getIndex(), cancelIndicator);
  }

  @Override
  public CompletableFuture<Hover> hover(HoverParams params)
  {
    return requestManager.runRead(cancelIndicator -> hover(params, cancelIndicator));
  }

  /**
   * Compute the hover. Executed in a read request.
   */
  protected Hover hover(HoverParams params, CancelIndicator cancelIndicator)
  {
    final var uri = getURI(params);
    final var hoverService = getService(uri, XsmpHoverService.class);
    if (hoverService == null)
    {
      return IHoverService.EMPTY_HOVER;
    }
    return workspaceManager.<Hover> doRead(uri, (document, resource) -> hoverService.hover(document,
            resource, params, cancelIndicator));
  }

  @Override
  public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved)
  {
    return CompletableFuture.<CompletionItem> completedFuture(unresolved);
  }

  @Override
  public CompletableFuture<SignatureHelp> signatureHelp(SignatureHelpParams params)
  {
    return requestManager.runRead(cancelIndicator -> signatureHelp(params, cancelIndicator));
  }

  /**
   * Compute the signature help. Executed in a read request.
   */
  protected SignatureHelp signatureHelp(SignatureHelpParams params, CancelIndicator cancelIndicator)
  {
    final var uri = getURI(params);
    final var helper = getService(uri, ISignatureHelpService.class);
    if (helper == null)
    {
      return ISignatureHelpService.EMPTY;
    }
    return workspaceManager.doRead(uri,
            (doc, resource) -> helper.getSignatureHelp(doc, resource, params, cancelIndicator));
  }

  @Override
  public CompletableFuture<List< ? extends DocumentHighlight>> documentHighlight(
          DocumentHighlightParams params)
  {
    return requestManager.runRead(cancelIndicator -> documentHighlight(params, cancelIndicator));
  }

  /**
   * Compute the document highlights. Executed in a read request.
   */
  protected List< ? extends DocumentHighlight> documentHighlight(DocumentHighlightParams params,
          CancelIndicator cancelIndicator)
  {
    final var uri = getURI(params);
    final var service = getService(uri, IDocumentHighlightService.class);
    if (service == null)
    {
      return Collections.emptyList();
    }
    return workspaceManager.doRead(uri, (doc, resource) -> service.getDocumentHighlights(doc,
            resource, params, cancelIndicator));
  }

  @Override
  public CompletableFuture<List<Either<Command, CodeAction>>> codeAction(CodeActionParams params)
  {
    return requestManager.runRead(cancelIndicator -> codeAction(params, cancelIndicator));
  }

  /**
   * Compute the code action commands. Executed in a read request.
   */
  protected List<Either<Command, CodeAction>> codeAction(CodeActionParams params,
          CancelIndicator cancelIndicator)
  {
    final var uri = getURI(params.getTextDocument());
    final var serviceProvider = getResourceServiceProvider(uri);
    final var codeActionService = getService(serviceProvider, ICodeActionService2.class);
    if (codeActionService == null)
    {
      return Collections.emptyList();
    }
    final var options = new ICodeActionService2.Options();
    options.setURI(params.getTextDocument().getUri());
    options.setLanguageServerAccess(access);
    options.setCodeActionParams(params);
    options.setCancelIndicator(cancelIndicator);
    final var actions = codeActionService.getCodeActions(options);
    if (actions != null)
    {
      return actions;
    }
    return Collections.emptyList();
  }

  /**
   * Put the document uri into the data of the given code lenses.
   */
  protected void installURI(List< ? extends CodeLens> codeLenses, String uri)
  {
    for (final CodeLens lens : codeLenses)
    {
      final var data = lens.getData();
      if (data != null)
      {
        lens.setData(Arrays.asList(uri, lens.getData()));
      }
      else
      {
        lens.setData(uri);
      }
    }
  }

  /**
   * Remove the document uri from the data of the given code lense.
   */
  protected URI uninstallURI(CodeLens lens)
  {
    URI result = null;
    final var data = lens.getData();
    if (data instanceof String)
    {
      result = URI.createURI(data.toString());
      lens.setData(null);
    }
    else if (data instanceof final List< ? > l)
    {
      result = URI.createURI(l.get(0).toString());
      lens.setData(l.get(1));
    }
    return result;
  }

  @Override
  public CompletableFuture<List< ? extends CodeLens>> codeLens(CodeLensParams params)
  {
    return requestManager.runRead(cancelIndicator -> codeLens(params, cancelIndicator));
  }

  /**
   * Compute the code lenses. Executed in a read request.
   */
  protected List< ? extends CodeLens> codeLens(CodeLensParams params,
          CancelIndicator cancelIndicator)
  {
    final var uri = getURI(params.getTextDocument());
    final var codeLensService = getService(uri, ICodeLensService.class);
    if (codeLensService == null)
    {
      return Collections.emptyList();
    }
    return workspaceManager.doRead(uri, (document, resource) -> {
      final List< ? extends CodeLens> result = codeLensService.computeCodeLenses(document, resource,
              params, cancelIndicator);
      installURI(result, uri.toString());
      return result;
    });
  }

  @Override
  public CompletableFuture<CodeLens> resolveCodeLens(CodeLens unresolved)
  {
    final var uri = uninstallURI(unresolved);
    if (uri == null)
    {
      return CompletableFuture.completedFuture(unresolved);
    }
    return requestManager
            .runRead(cancelIndicator -> resolveCodeLens(uri, unresolved, cancelIndicator));
  }

  /**
   * Resolve the given code lens. Executed in a read request.
   */
  protected CodeLens resolveCodeLens(URI uri, CodeLens unresolved, CancelIndicator cancelIndicator)
  {
    final var resolver = getService(uri, ICodeLensResolver.class);
    if (resolver == null)
    {
      return unresolved;
    }
    return workspaceManager.doRead(uri, (document, resource) -> resolver.resolveCodeLens(document,
            resource, unresolved, cancelIndicator));
  }

  @Override
  public CompletableFuture<List< ? extends TextEdit>> formatting(DocumentFormattingParams params)
  {
    return requestManager.runRead(cancelIndicator -> formatting(params, cancelIndicator));
  }

  /**
   * Create the text edits for the formatter. Executed in a read request.
   */
  protected List< ? extends TextEdit> formatting(DocumentFormattingParams params,
          CancelIndicator cancelIndicator)
  {
    final var uri = getURI(params.getTextDocument());
    final var formatterService = getService(uri, FormattingService.class);
    if (formatterService == null)
    {
      return Collections.emptyList();
    }
    return workspaceManager.doRead(uri, (document, resource) -> formatterService.format(document,
            resource, params, cancelIndicator));
  }

  @Override
  public CompletableFuture<List< ? extends TextEdit>> rangeFormatting(
          DocumentRangeFormattingParams params)
  {
    return requestManager.runRead(cancelIndicator -> rangeFormatting(params, cancelIndicator));
  }

  /**
   * Create the text edits for the formatter. Executed in a read request.
   */
  protected List< ? extends TextEdit> rangeFormatting(DocumentRangeFormattingParams params,
          CancelIndicator cancelIndicator)
  {
    final var uri = getURI(params.getTextDocument());
    final var formatterService = getService(uri, FormattingService.class);
    if (formatterService == null)
    {
      return Collections.emptyList();
    }
    return workspaceManager.doRead(uri, (document, resource) -> formatterService.format(document,
            resource, params, cancelIndicator));
  }

  /**
   * @param uri
   *          the current URI
   * @param type
   *          the type of the service
   * @return the service instance or null if the language does not exist or if it does not expose
   *         the service.
   */
  protected <S> S getService(URI uri, Class<S> type)
  {
    return getService(getResourceServiceProvider(uri), type);
  }

  /**
   * @param <Service>
   *          the type of the service
   * @param resourceServiceProvider
   *          the resource service provider. May be null
   * @param type
   *          the type of the service
   * @return the service instance or null if not available.
   */
  protected <S> S getService(IResourceServiceProvider resourceServiceProvider, Class<S> type)
  {
    if (resourceServiceProvider == null)
    {
      return null;
    }
    return resourceServiceProvider.get(type);
  }

  @Override
  public CompletableFuture<Object> executeCommand(ExecuteCommandParams params)
  {
    return requestManager.runRead(cancelIndicator -> executeCommand(params, cancelIndicator));
  }

  /**
   * Execute the command. Runs in a read request.
   */
  protected Object executeCommand(ExecuteCommandParams params, CancelIndicator cancelIndicator)
  {
    return commandRegistry.executeCommand(params, access, cancelIndicator);
  }

  @Override
  public CompletableFuture<List< ? extends TextEdit>> onTypeFormatting(
          DocumentOnTypeFormattingParams params)
  {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }

  @Override
  public CompletableFuture<WorkspaceEdit> rename(RenameParams renameParams)
  {
    return requestManager.runRead(cancelIndicator -> rename(renameParams, cancelIndicator));
  }

  /**
   * Compute the rename edits. Executed in a read request.
   */
  protected WorkspaceEdit rename(RenameParams renameParams, CancelIndicator cancelIndicator)
  {
    final var uri = getURI(renameParams.getTextDocument());

    final var resourceServiceProvider = getResourceServiceProvider(uri);
    final var renameService2 = getService(resourceServiceProvider, IRenameService2.class);
    if (renameService2 != null)
    {
      final var options = new IRenameService2.Options();
      options.setLanguageServerAccess(access);
      options.setRenameParams(renameParams);
      options.setCancelIndicator(cancelIndicator);
      return renameService2.rename(options);
    }
    return new WorkspaceEdit();
  }

  /**
   * @param uri
   *          the current URI
   * @return the resource service provider or null.
   */
  protected IResourceServiceProvider getResourceServiceProvider(URI uri)
  {
    return languagesRegistry.getResourceServiceProvider(uri);
  }

  /**
   *
   */
  @Override
  public CompletableFuture<Either3<Range, PrepareRenameResult, PrepareRenameDefaultBehavior>> prepareRename(
          PrepareRenameParams params)
  {
    return requestManager.runRead(cancelIndicator -> prepareRename(params, cancelIndicator));
  }

  /**
   * Prepare the rename operation. Executed in a read request.
   */
  protected Either3<Range, PrepareRenameResult, PrepareRenameDefaultBehavior> prepareRename(
          PrepareRenameParams params, CancelIndicator cancelIndicator)
  {
    final var uri = getURI(params);
    final var renameService = getService(uri, IRenameService2.class);
    if (renameService == null)
    {
      throw new UnsupportedOperationException();
    }
    final var options = new IRenameService2.PrepareRenameOptions();
    options.setLanguageServerAccess(access);
    options.setParams(params);
    options.setCancelIndicator(cancelIndicator);
    return renameService.prepareRename(options);
  }

  @Override
  public CompletableFuture<List<FoldingRange>> foldingRange(FoldingRangeRequestParams params)
  {
    return requestManager.runRead(cancelIndicator -> foldingRange(params, cancelIndicator));
  }

  protected List<FoldingRange> foldingRange(FoldingRangeRequestParams params,
          CancelIndicator cancelIndicator)
  {
    final var uri = getURI(params.getTextDocument());
    final var foldingRangeService = getService(uri, FoldingRangeService.class);
    if (foldingRangeService == null)
    {
      return Lists.newArrayList();
    }
    return workspaceManager.doRead(uri, (document, resource) -> foldingRangeService
            .createFoldingRanges(document, resource, cancelIndicator));
  }

  @Override
  public void notify(String method, Object parameter)
  {
    for (final Endpoint endpoint : extensionProviders.get(method))
    {
      try
      {
        endpoint.notify(method, parameter);
      }
      catch (final UnsupportedOperationException e)
      {
        if (e != ILanguageServerExtension.NOT_HANDLED_EXCEPTION)
        {
          throw e;
        }
      }
    }
  }

  @Override
  public CompletableFuture< ? > request(String method, Object parameter)
  {
    if (!extensionProviders.containsKey(method))
    {
      throw new UnsupportedOperationException("The json request \'" + method + "\' is unknown.");
    }
    for (final Endpoint endpoint : extensionProviders.get(method))
    {
      try
      {
        return endpoint.request(method, parameter);
      }
      catch (final UnsupportedOperationException e)
      {
        if (e != ILanguageServerExtension.NOT_HANDLED_EXCEPTION)
        {
          throw e;
        }
      }
    }
    return null;
  }

  @Override
  public Map<String, JsonRpcMethod> supportedMethods()
  {
    if (supportedMethods != null)
    {
      return supportedMethods;
    }
    synchronized (extensionProviders)
    {
      final ConcurrentMap<String, JsonRpcMethod> supportedMethods = new ConcurrentHashMap<>(
              ServiceEndpoints.getSupportedMethods(getClass()));
      final Map<String, JsonRpcMethod> extensions = new LinkedHashMap<>();
      for (final IResourceServiceProvider resourceServiceProvider : getAllLanguages())
      {
        final var ext = resourceServiceProvider.get(ILanguageServerExtension.class);
        if (ext != null)
        {
          ext.initialize(access);
          final var supportedExtensions = ext instanceof JsonRpcMethodProvider
                  ? ((JsonRpcMethodProvider) ext).supportedMethods()
                  : ServiceEndpoints.getSupportedMethods(ext.getClass());
          for (final Map.Entry<String, JsonRpcMethod> entry : supportedExtensions.entrySet())
          {
            if (supportedMethods.containsKey(entry.getKey()))
            {
              LOG.error("The json rpc method \'" + entry.getKey()
                      + "\' can not be an extension as it is already defined in the LSP standard.");
            }
            else
            {
              final var existing = extensions.put(entry.getKey(), entry.getValue());
              if (existing != null && !Objects.equal(existing, entry.getValue()))
              {
                LOG.error("An incompatible LSP extension \'" + entry.getKey()
                        + "\' has already been registered. Using 1 ignoring 2. \n1 : " + existing
                        + " \n2 : " + entry.getValue());
                extensions.put(entry.getKey(), existing);
              }
              else
              {
                final var endpoint = ServiceEndpoints.toEndpoint(ext);
                extensionProviders.put(entry.getKey(), endpoint);
                supportedMethods.put(entry.getKey(), entry.getValue());
              }
            }
          }
        }
      }
      this.supportedMethods = supportedMethods;
      return supportedMethods;
    }
  }

  private final ILanguageServerAccess access = new ILanguageServerAccess() {
    @Override
    public <T> CompletableFuture<T> doRead(String uri,
            Function<ILanguageServerAccess.Context, T> function)
    {
      return requestManager.runRead(cancelIndicator -> workspaceManager.doRead(
              uriExtensions.toUri(uri),
              (document,
                      resource) -> function.apply(new ILanguageServerAccess.Context(resource,
                              document, workspaceManager.isDocumentOpen(resource.getURI()),
                              cancelIndicator))));
    }

    @Override
    public void addBuildListener(ILanguageServerAccess.IBuildListener listener)
    {
      workspaceManager.addBuildListener(listener);
    }

    @Override
    public void removeBuildListener(ILanguageServerAccess.IBuildListener listener)
    {
      workspaceManager.removeBuildListener(listener);
    }

    @Override
    public LanguageClient getLanguageClient()
    {
      return client;
    }

    @Override
    public ResourceSet newLiveScopeResourceSet(URI uri)
    {
      final var projectManager = workspaceManager.getProjectManager(uri);
      return projectManager.createLiveScopeResourceSet();
    }

    @Override
    public InitializeParams getInitializeParams()
    {
      return initializeParams;
    }

    @Override
    public <T> CompletableFuture<T> doReadIndex(
            Function< ? super ILanguageServerAccess.IndexContext, ? extends T> function)
    {
      return requestManager
              .runRead(cancelIndicator -> function.apply(new ILanguageServerAccess.IndexContext(
                      workspaceManager.getIndex(), cancelIndicator)));
    }

    @Override
    public InitializeResult getInitializeResult()
    {
      return initializeResult;
    }

    @Override
    public <T extends Object> T doSyncRead(String uri,
            Function<ILanguageServerAccess.Context, T> function)
    {
      return workspaceManager.doRead(uriExtensions.toUri(uri),
              (document,
                      resource) -> function.apply(new ILanguageServerAccess.Context(resource,
                              document, workspaceManager.isDocumentOpen(resource.getURI()),
                              CancelIndicator.NullImpl)));
    }
  };

  /**
   * @deprecated please register a {@link IBuildListener} directly through
   *             {@link ILanguageServerAccess#addBuildListener(IBuildListener)}
   */
  @Override
  @Deprecated
  public void afterBuild(List<IResourceDescription.Delta> deltas)
  {
  }

  protected ILanguageServerAccess getLanguageServerAccess()
  {
    return access;
  }

  protected LanguageClient getLanguageClient()
  {
    return client;
  }

  protected ExecutableCommandRegistry getCommandRegistry()
  {
    return commandRegistry;
  }

  protected Multimap<String, Endpoint> getExtensionProviders()
  {
    return ImmutableMultimap.copyOf(extensionProviders);
  }

  protected Map<String, JsonRpcMethod> getSupportedMethods()
  {
    return ImmutableMap.copyOf(supportedMethods);
  }

  protected IResourceServiceProvider.Registry getLanguagesRegistry()
  {
    return languagesRegistry;
  }

  protected IReferenceFinder.IResourceAccess getWorkspaceResourceAccess()
  {
    return resourceAccess;
  }

  protected XsmpWorkspaceManager getWorkspaceManager()
  {
    return workspaceManager;
  }

  protected WorkspaceSymbolService getWorkspaceSymbolService()
  {
    return workspaceSymbolService;
  }

  public RequestManager getRequestManager()
  {
    return requestManager;
  }

  @Override
  public void didOpen(DidOpenNotebookDocumentParams params)
  {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }

  @Override
  public void didChange(DidChangeNotebookDocumentParams params)
  {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }

  @Override
  public void didSave(DidSaveNotebookDocumentParams params)
  {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }

  @Override
  public void didClose(DidCloseNotebookDocumentParams params)
  {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }

  @Override
  public CompletableFuture<SemanticTokens> semanticTokensFull(final SemanticTokensParams params)
  {
    return getRequestManager()
            .runRead(cancelIndicator -> semanticTokensFull(params, cancelIndicator));
  }

  @Beta
  protected SemanticTokens semanticTokensFull(final SemanticTokensParams params,
          final CancelIndicator cancelIndicator)
  {
    final var uri = getURI(params.getTextDocument());
    return getWorkspaceManager().doRead(uri, (doc, res) -> semanticTokensService
            .semanticTokensFull(doc, res, params, cancelIndicator));
  }
}
