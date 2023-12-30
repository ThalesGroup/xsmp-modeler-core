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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.ui.editor.ProblemEditorPart;
import org.eclipse.emf.common.ui.viewer.ColumnViewerInformationControlToolTipSupport;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.DecoratingColumLabelProvider;
import org.eclipse.emf.edit.ui.provider.DelegatingStyledCellLabelProvider;
import org.eclipse.emf.edit.ui.provider.DiagnosticDecorator;
import org.eclipse.emf.edit.ui.provider.UnwrappingSelectionProvider;
import org.eclipse.emf.edit.ui.util.EditUIMarkerHelper;
import org.eclipse.emf.edit.ui.util.FindAndReplaceTarget;
import org.eclipse.emf.edit.ui.util.IRevertablePart;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.xsmp.ui.XsmpUIPlugin;
import org.eclipse.xsmp.ui.configuration.IXsmpServiceUIProvider;
import org.eclipse.xsmp.xcatalogue.util.XcatalogueAdapterFactory;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

public class XsmpcatFormEditor extends MultiPageEditorPart implements IEditingDomainProvider,
        ISelectionProvider, IMenuListener, IViewerProvider, IGotoMarker, IRevertablePart
{

  /**
   * This keeps track of the editing domain that is used to track all changes to the model. <!--
   * begin-user-doc -->
   */
  protected XtextAdapterFactoryEditingDomain editingDomain;

  /**
   * This is the one adapter factory used for providing views of the model.
   */
  protected ComposedAdapterFactory adapterFactory;

  /**
   * This is the content outline page.
   */
  protected IContentOutlinePage contentOutlinePage;

  /**
   * This is a kludge...
   */
  protected IStatusLineManager contentOutlineStatusLineManager;

  /**
   * This is the content outline page's viewer.
   */
  protected TreeViewer contentOutlineViewer;

  /**
   * This is the property sheet page.
   */
  protected EMFFormsPropertySheetPage propertySheetPage;

  /**
   * This is the viewer that shadows the selection in the content outline. The parent relation must
   * be correctly defined for this to work.
   */
  protected TreeViewer selectionViewer;

  /**
   * This keeps track of the active content viewer, which may be either one of the viewers in the
   * pages or the content outline viewer.
   */
  protected Viewer currentViewer;

  /**
   * This listens to which ever viewer is active.
   */
  protected ISelectionChangedListener selectionChangedListener;

  /**
   * This keeps track of all the {@link org.eclipse.jface.viewers.ISelectionChangedListener}s that
   * are listening to this editor.
   */
  protected Collection<ISelectionChangedListener> selectionChangedListeners = new ArrayList<>();

  /**
   * This keeps track of the selection of the editor as a whole.
   */
  protected ISelection editorSelection = StructuredSelection.EMPTY;

  /**
   * The MarkerHelper is responsible for creating workspace resource markers presented in Eclipse's
   * Problems View.
   */
  protected MarkerHelper markerHelper = new EditUIMarkerHelper();

  /**
   * This listens for when the outline becomes active
   */
  protected IPartListener partListener = new IPartListener() {
    @Override
    public void partActivated(IWorkbenchPart p)
    {
      if (p instanceof ContentOutline)
      {
        if (((ContentOutline) p).getCurrentPage() == contentOutlinePage)
        {
          getActionBarContributor().setActiveEditor(XsmpcatFormEditor.this);

          setCurrentViewer(contentOutlineViewer);
        }
      }
      else if (p instanceof PropertySheet)
      {
        if (propertySheetPage == ((PropertySheet) p).getCurrentPage())
        {
          getActionBarContributor().setActiveEditor(XsmpcatFormEditor.this);
          handleActivate();
        }
      }
      else if (p == XsmpcatFormEditor.this)
      {
        handleActivate();
      }
    }

    @Override
    public void partBroughtToTop(IWorkbenchPart p)
    {
      // Ignore.
    }

    @Override
    public void partClosed(IWorkbenchPart p)
    {
      // Ignore.
    }

    @Override
    public void partDeactivated(IWorkbenchPart p)
    {
      // Ignore.
    }

    @Override
    public void partOpened(IWorkbenchPart p)
    {
      // Ignore.
    }
  };

  /**
   * Resources that have been removed since last activation.
   */
  protected Collection<Resource> removedResources = new ArrayList<>();

  /**
   * Resources that have been changed since last activation.
   */
  protected Collection<Resource> changedResources = new ArrayList<>();

  /**
   * Resources that have been saved.
   */
  protected Collection<Resource> savedResources = new ArrayList<>();

  /**
   * Map to store the diagnostic associated with a resource.
   */
  protected Map<Resource, Diagnostic> resourceToDiagnosticMap = new LinkedHashMap<>();

  /**
   * Controls whether the problem indication should be updated.
   */
  protected boolean updateProblemIndication = true;

  /**
   * Adapter used to update the problem indication when resources are demanded loaded. <!--
   * begin-user-doc -->
   */
  protected EContentAdapter problemIndicationAdapter = new EContentAdapter() {
    protected boolean dispatching;

    @Override
    public void notifyChanged(Notification notification)
    {
      if (notification.getNotifier() instanceof Resource)
      {
        switch (notification.getFeatureID(Resource.class))
        {
          case Resource.RESOURCE__IS_LOADED:
          case Resource.RESOURCE__ERRORS:
          case Resource.RESOURCE__WARNINGS:
          {
            final var resource = (Resource) notification.getNotifier();
            final var diagnostic = analyzeResourceProblems(resource, null);
            if (diagnostic.getSeverity() != Diagnostic.OK)
            {
              resourceToDiagnosticMap.put(resource, diagnostic);
            }
            else
            {
              resourceToDiagnosticMap.remove(resource);
            }
            dispatchUpdateProblemIndication();
            break;
          }
          default:
            break;
        }
      }
      else
      {
        super.notifyChanged(notification);
      }
    }

    protected void dispatchUpdateProblemIndication()
    {
      if (updateProblemIndication && !dispatching)
      {
        dispatching = true;
        getSite().getShell().getDisplay().asyncExec(() -> {
          dispatching = false;
          doUpdateProblemIndication();
        });
      }
    }

    @Override
    protected void setTarget(Resource target)
    {
      basicSetTarget(target);
    }

    @Override
    protected void unsetTarget(Resource target)
    {
      basicUnsetTarget(target);
      resourceToDiagnosticMap.remove(target);
      dispatchUpdateProblemIndication();
    }
  };

  /**
   * This listens for workspace changes.
   */
  protected IResourceChangeListener resourceChangeListener = event -> {
    final var delta = event.getDelta();
    try
    {
      class ResourceDeltaVisitor implements IResourceDeltaVisitor
      {
        protected ResourceSet resourceSet = editingDomain.getResourceSet();

        protected Collection<Resource> changedResources = new ArrayList<>();

        protected Collection<Resource> removedResources = new ArrayList<>();

        @Override
        public boolean visit(final IResourceDelta delta)
        {
          if (delta.getResource().getType() == IResource.FILE)
          {
            if (delta.getKind() == IResourceDelta.REMOVED
                    || delta.getKind() == IResourceDelta.CHANGED)
            {
              final var resource = resourceSet.getResource(
                      URI.createPlatformResourceURI(delta.getFullPath().toString(), true), false);
              if (resource != null)
              {
                if (delta.getKind() == IResourceDelta.REMOVED)
                {
                  removedResources.add(resource);
                }
                else
                {
                  if ((delta.getFlags() & IResourceDelta.MARKERS) != 0)
                  {
                    DiagnosticDecorator.DiagnosticAdapter.update(resource, markerHelper
                            .getMarkerDiagnostics(resource, (IFile) delta.getResource(), false));
                  }
                  if ((delta.getFlags() & IResourceDelta.CONTENT) != 0
                          && !savedResources.remove(resource))
                  {
                    changedResources.add(resource);
                  }
                }
              }
            }
            return false;
          }

          return true;
        }

        public Collection<Resource> getChangedResources()
        {
          return changedResources;
        }

        public Collection<Resource> getRemovedResources()
        {
          return removedResources;
        }
      }

      final var visitor = new ResourceDeltaVisitor();
      delta.accept(visitor);

      if (!visitor.getRemovedResources().isEmpty())
      {
        getSite().getShell().getDisplay().asyncExec(() -> {
          removedResources.addAll(visitor.getRemovedResources());
          if (!isDirty())
          {
            getSite().getPage().closeEditor(this, false);
          }
        });
      }

      if (!visitor.getChangedResources().isEmpty())
      {
        getSite().getShell().getDisplay().asyncExec(() -> {
          changedResources.addAll(visitor.getChangedResources());
          if (getSite().getPage().getActiveEditor() == this)
          {
            handleActivate();
          }
        });
      }
    }
    catch (final CoreException exception)
    {
      // XsmpUIPlugin.getInstance().log(exception);
    }
  };

  /**
   * Handles activation of the editor or it's associated views.
   */
  protected void handleActivate()
  {
    // Recompute the read only state.
    //
    if (editingDomain.getResourceToReadOnlyMap() != null)
    {
      editingDomain.getResourceToReadOnlyMap().clear();

      // Refresh any actions that may become enabled or disabled.
      //
      setSelection(getSelection());
    }

    if (!removedResources.isEmpty())
    {
      if (handleDirtyConflict())
      {
        getSite().getPage().closeEditor(XsmpcatFormEditor.this, false);
      }
      else
      {
        removedResources.clear();
        changedResources.clear();
        savedResources.clear();
      }
    }
    else if (!changedResources.isEmpty())
    {
      changedResources.removeAll(savedResources);
      handleChangedResources();
      changedResources.clear();
      savedResources.clear();
    }
  }

  /**
   * Handles what to do with changed resources on activation.
   */
  protected void handleChangedResources()
  {
    if (!changedResources.isEmpty() && (!isDirty() || handleDirtyConflict()))
    {
      final var resourceSet = editingDomain.getResourceSet();
      if (isDirty())
      {
        changedResources.addAll(resourceSet.getResources());
      }
      editingDomain.getCommandStack().flush();

      updateProblemIndication = false;
      for (final Resource resource : changedResources)
      {
        if (resource.isLoaded())
        {
          resource.unload();
          try
          {
            resource.load(resourceSet.getLoadOptions());
          }
          catch (final IOException exception)
          {
            if (!resourceToDiagnosticMap.containsKey(resource))
            {
              resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
            }
          }
        }
      }

      if (AdapterFactoryEditingDomain.isStale(editorSelection))
      {
        setSelection(StructuredSelection.EMPTY);
      }

      updateProblemIndication = true;
      doUpdateProblemIndication();
    }
  }

  /**
   * Updates the problems indication with the information described in the specified diagnostic.
   */
  protected void doUpdateProblemIndication()
  {
    if (updateProblemIndication)
    {
      final var diagnostic = new BasicDiagnostic(Diagnostic.OK, "org.eclipse.xsmp.editor", 0, null,
              new Object[]{editingDomain.getResourceSet() });
      for (final Diagnostic childDiagnostic : resourceToDiagnosticMap.values())
      {
        if (childDiagnostic.getSeverity() != Diagnostic.OK)
        {
          diagnostic.add(childDiagnostic);
        }
      }

      var lastEditorPage = getPageCount() - 1;
      if (lastEditorPage >= 0 && getEditor(lastEditorPage) instanceof ProblemEditorPart)
      {
        ((ProblemEditorPart) getEditor(lastEditorPage)).setDiagnostic(diagnostic);
        if (diagnostic.getSeverity() != Diagnostic.OK)
        {
          setActivePage(lastEditorPage);
        }
      }
      else if (diagnostic.getSeverity() != Diagnostic.OK)
      {
        final var problemEditorPart = new ProblemEditorPart();
        problemEditorPart.setDiagnostic(diagnostic);
        problemEditorPart.setMarkerHelper(markerHelper);
        try
        {
          lastEditorPage++;
          addPage(lastEditorPage, problemEditorPart, getEditorInput());
          setPageText(lastEditorPage, problemEditorPart.getPartName());
          setActivePage(lastEditorPage);
          showTabs();
        }
        catch (final PartInitException exception)
        {
          XsmpUIPlugin.getInstance().getLog().error("", exception);
        }
      }

      if (markerHelper.hasMarkers(editingDomain.getResourceSet()))
      {
        try
        {
          markerHelper.updateMarkers(diagnostic);
        }
        catch (final CoreException exception)
        {
          XsmpUIPlugin.getInstance().getLog().error("", exception);
        }
      }
    }
  }

  /**
   * Shows a dialog that asks if conflicting changes should be discarded.
   */
  protected boolean handleDirtyConflict()
  {
    return MessageDialog.openQuestion(getSite().getShell(), getString("_UI_FileConflict_label"),
            getString("_WARN_FileConflict"));
  }

  @Inject
  private IXsmpServiceUIProvider configurationProvider;

  /**
   * This creates a model editor.
   */

  public XsmpcatFormEditor()
  {
    // Create an adapter factory that yields item providers.
    //
    adapterFactory = new ComposedAdapterFactory(
            ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

    adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
    adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

    // Create the command stack that will notify this editor as commands are executed.
    //
    final BasicCommandStack commandStack = new BasicCommandStack() {
      @Override
      public void execute(Command command)
      {
        // Cancel live validation before executing a command that will trigger a new round of
        // validation.
        //
        if (!(command instanceof AbstractCommand.NonDirtying))
        {
          DiagnosticDecorator.cancel(editingDomain);
        }
        super.execute(command);
      }
    };

    // Add a listener to set the most recent command's affected objects to be the selection of the
    // viewer with focus.
    //
    commandStack.addCommandStackListener(event -> getContainer().getDisplay().asyncExec(() -> {
      firePropertyChange(IEditorPart.PROP_DIRTY);

      // Try to select the affected objects.
      //
      final var mostRecentCommand = ((CommandStack) event.getSource()).getMostRecentCommand();
      if (mostRecentCommand != null)
      {
        setSelectionToViewer(mostRecentCommand.getAffectedObjects());
      }
      if (propertySheetPage == null)
      {
        // nothing to do
      }
      else
        if (propertySheetPage.getControl() == null || propertySheetPage.getControl().isDisposed())
      {
        propertySheetPage = null;
      }
      else
      {
        propertySheetPage.refresh();
      }

    }));

    // Create the editing domain with a special command stack.
    //
    editingDomain = new XtextAdapterFactoryEditingDomain(adapterFactory, commandStack);
  }

  /**
   * This sets the selection into whichever viewer is active.
   */
  public void setSelectionToViewer(Collection< ? > collection)
  {
    final Collection< ? > theSelection = collection;
    // Make sure it's okay.
    //
    if (theSelection != null && !theSelection.isEmpty())
    {
      final Runnable runnable = () -> {
        // Try to select the items in the current content viewer of the editor.
        //
        if (currentViewer != null)
        {
          currentViewer.setSelection(new StructuredSelection(theSelection.toArray()), true);
        }
      };
      getSite().getShell().getDisplay().asyncExec(runnable);
    }
  }

  /**
   * This returns the editing domain as required by the {@link IEditingDomainProvider} interface.
   * This is important for implementing the static methods of {@link AdapterFactoryEditingDomain}
   * and for supporting {@link org.eclipse.emf.edit.ui.action.CommandAction}.
   */
  @Override
  public EditingDomain getEditingDomain()
  {
    return editingDomain;
  }

  /**
   *
   */
  public class ReverseAdapterFactoryContentProvider extends AdapterFactoryContentProvider
  {
    /**
     *
     */
    public ReverseAdapterFactoryContentProvider(AdapterFactory adapterFactory)
    {
      super(adapterFactory);
    }

    /**
     *
     */
    @Override
    public Object[] getElements(Object object)
    {
      final var parent = super.getParent(object);
      return (parent == null ? Collections.emptySet() : Collections.singleton(parent)).toArray();
    }

    /**
     *
     */
    @Override
    public Object[] getChildren(Object object)
    {
      return getElements(object);
    }

    /**
     *
     */
    @Override
    public boolean hasChildren(Object object)
    {
      final var parent = super.getParent(object);
      return parent != null;
    }

    /**
     *
     */
    @Override
    public Object getParent(Object object)
    {
      return null;
    }
  }

  /**
   * This makes sure that one content viewer, either for the current page or the outline view, if it
   * has focus, is the current one.
   */
  public void setCurrentViewer(Viewer viewer)
  {
    // If it is changing...
    //
    if (currentViewer != viewer)
    {
      if (selectionChangedListener == null)
      {
        // Create the listener on demand.
        //
        selectionChangedListener = selectionChangedEvent -> setSelection(
                selectionChangedEvent.getSelection());
      }

      // Stop listening to the old one.
      //
      if (currentViewer != null)
      {
        currentViewer.removeSelectionChangedListener(selectionChangedListener);
      }

      // Start listening to the new one.
      //
      if (viewer != null)
      {
        viewer.addSelectionChangedListener(selectionChangedListener);
      }

      // Remember it.
      //
      currentViewer = viewer;

      // Set the editors selection based on the current viewer's selection.
      //
      setSelection(
              currentViewer == null ? StructuredSelection.EMPTY : currentViewer.getSelection());
    }
  }

  /**
   * This returns the viewer as required by the {@link IViewerProvider} interface. <!--
   * begin-user-doc -->
   */
  @Override
  public Viewer getViewer()
  {
    return currentViewer;
  }

  /**
   * This creates a context menu for the viewer and adds a listener as well registering the menu for
   * extension.
   */
  protected void createContextMenuFor(StructuredViewer viewer)
  {
    final var contextMenu = new MenuManager("#PopUp");
    contextMenu.add(new Separator("additions"));
    contextMenu.setRemoveAllWhenShown(true);
    contextMenu.addMenuListener(this);
    final var menu = contextMenu.createContextMenu(viewer.getControl());
    viewer.getControl().setMenu(menu);
    getSite().registerContextMenu(contextMenu, new UnwrappingSelectionProvider(viewer));

    final var dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
    final Transfer[] transfers = {
      LocalTransfer.getInstance(),
      LocalSelectionTransfer.getTransfer(),
      FileTransfer.getInstance() };
    viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer));
    viewer.addDropSupport(dndOperations, transfers,
            new EditingDomainViewerDropAdapter(editingDomain, viewer));
  }

  private void initializeEditingDomain(IEditorInput input)
  {

    IProject project = null;
    if (input instanceof IFileEditorInput)
    {
      project = ((IFileEditorInput) input).getFile().getProject();

    }
    configurationProvider.getInjector(project).injectMembers(editingDomain);
  }

  /**
   * This is the method called to load a resource into the editing domain's resource set based on
   * the editor's input.
   */
  public void createModel()
  {
    final var input = getEditorInput();
    initializeEditingDomain(input);

    final var resource = (XtextResource) editingDomain.createResource(input);
    Exception exception = null;
    try
    {
      resource.load(editingDomain.getResourceSet().getLoadOptions());
    }
    catch (final IOException e)
    {
      exception = e;
    }

    final var diagnostic = analyzeResourceProblems(resource, exception);
    if (diagnostic.getSeverity() != Diagnostic.OK)
    {
      resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
    }
    editingDomain.getResourceSet().eAdapters().add(problemIndicationAdapter);

    adapterFactory.addAdapterFactory(
            resource.getResourceServiceProvider().get(XcatalogueAdapterFactory.class));

  }

  /**
   * Returns a diagnostic describing the errors and warnings listed in the resource and the
   * specified exception (if any).
   */
  public Diagnostic analyzeResourceProblems(Resource resource, Exception exception)
  {
    final var hasErrors = !resource.getErrors().isEmpty();
    if (hasErrors || !resource.getWarnings().isEmpty())
    {
      final var basicDiagnostic = new BasicDiagnostic(
              hasErrors ? Diagnostic.ERROR : Diagnostic.WARNING, "org.eclipse.xsmp.editor", 0,
              getString("_UI_CreateModelError_message", resource.getURI()),
              new Object[]{exception == null ? (Object) resource : exception });
      basicDiagnostic.merge(EcoreUtil.computeDiagnostic(resource, true));
      return basicDiagnostic;
    }
    if (exception != null)
    {
      return new BasicDiagnostic(Diagnostic.ERROR, "org.eclipse.xsmp.editor", 0,
              getString("_UI_CreateModelError_message", resource.getURI()),
              new Object[]{exception });
    }
    return Diagnostic.OK_INSTANCE;
  }

  @Inject
  private ILabelProvider labelProvider;

  /**
   * This is the method used by the framework to install your own controls.
   */
  @Override
  public void createPages()
  {
    // Creates the model from the editor input
    //
    createModel();

    // Only creates the other pages if there is something that can be edited
    //
    if (!getEditingDomain().getResourceSet().getResources().isEmpty())
    {
      // Create a page for the selection tree view.
      //
      final var filteredTree = new FilteredTree(getContainer(), SWT.MULTI, new PatternFilter(),
              true, true);
      selectionViewer = filteredTree.getViewer();

      setCurrentViewer(selectionViewer);

      selectionViewer.setUseHashlookup(true);
      selectionViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
      selectionViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(
              new DecoratingColumLabelProvider.StyledLabelProvider(
                      /*
                       * new AdapterFactoryLabelProvider.StyledLabelProvider(adapterFactory,
                       * selectionViewer)
                       */ labelProvider,
                      new DiagnosticDecorator.Styled(editingDomain, selectionViewer,
                              XsmpUIPlugin.getInstance().getDialogSettings()))));

      final var resource = editingDomain.getResourceSet().getResources().get(0);

      selectionViewer.setInput(resource);
      selectionViewer.setSelection(new StructuredSelection(resource.getContents().get(0)), true);

      new AdapterFactoryTreeEditor(selectionViewer.getTree(), adapterFactory);
      new ColumnViewerInformationControlToolTipSupport(selectionViewer,
              new DiagnosticDecorator.Styled.EditingDomainLocationListener(editingDomain,
                      selectionViewer));

      createContextMenuFor(selectionViewer);
      final var pageIndex = addPage(filteredTree);
      setPageText(pageIndex, getString("_UI_SelectionPage_label"));

      getSite().getShell().getDisplay().asyncExec(() -> {
        if (!getContainer().isDisposed())
        {
          setActivePage(0);
        }
      });
    }

    // Ensures that this editor will only display the page's tab
    // area if there are more than one page
    //
    getContainer().addControlListener(new ControlAdapter() {
      boolean guard = false;

      @Override
      public void controlResized(ControlEvent event)
      {
        if (!guard)
        {
          guard = true;
          hideTabs();
          guard = false;
        }
      }
    });

    getSite().getShell().getDisplay().asyncExec(this::doUpdateProblemIndication);
  }

  /**
   * If there is just one page in the multi-page editor part, this hides the single tab at the
   * bottom.
   */
  protected void hideTabs()
  {
    if (getPageCount() <= 1)
    {
      setPageText(0, "");
      if (getContainer() instanceof CTabFolder)
      {
        final var point = getContainer().getSize();
        final var clientArea = getContainer().getClientArea();
        getContainer().setSize(point.x, 2 * point.y - clientArea.height - clientArea.y);
      }
    }
  }

  /**
   * If there is more than one page in the multi-page editor part, this shows the tabs at the
   * bottom.
   */
  protected void showTabs()
  {
    if (getPageCount() > 1)
    {
      setPageText(0, getString("_UI_SelectionPage_label"));
      if (getContainer() instanceof CTabFolder)
      {
        final var point = getContainer().getSize();
        final var clientArea = getContainer().getClientArea();
        getContainer().setSize(point.x, clientArea.height + clientArea.y);
      }
    }
  }

  /**
   * This is used to track the active viewer.
   */
  @Override
  protected void pageChange(int pageIndex)
  {
    super.pageChange(pageIndex);

    if (contentOutlinePage != null)
    {
      handleContentOutlineSelection(contentOutlinePage.getSelection());
    }
  }

  /**
   * This is how the framework determines which interfaces we implement.
   */
  @Override
  public <T> T getAdapter(Class<T> key)
  {
    if (key.equals(IContentOutlinePage.class))
    {
      return showOutlineView() ? key.cast(getContentOutlinePage()) : null;
    }
    if (key.equals(IPropertySheetPage.class))
    {
      return key.cast(getPropertySheetPage());
    }
    if (key.equals(IGotoMarker.class))
    {
      return key.cast(this);
    }
    if (key.equals(IFindReplaceTarget.class))
    {
      return FindAndReplaceTarget.getAdapter(key, this, XsmpUIPlugin.getInstance());
    }
    return super.getAdapter(key);
  }

  /**
   * This accesses a cached version of the content outliner.
   */
  public IContentOutlinePage getContentOutlinePage()
  {
    if (contentOutlinePage == null)
    {
      // The content outline is just a tree.
      //
      class MyContentOutlinePage extends ContentOutlinePage
      {
        @Override
        public void createControl(Composite parent)
        {
          super.createControl(parent);
          contentOutlineViewer = getTreeViewer();
          contentOutlineViewer.addSelectionChangedListener(this);

          // Set up the tree viewer.
          //
          contentOutlineViewer.setUseHashlookup(true);
          contentOutlineViewer
                  .setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
          contentOutlineViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(
                  new DecoratingColumLabelProvider.StyledLabelProvider(
                          new AdapterFactoryLabelProvider.StyledLabelProvider(adapterFactory,
                                  contentOutlineViewer),
                          new DiagnosticDecorator.Styled(editingDomain, contentOutlineViewer,
                                  XsmpUIPlugin.getInstance().getDialogSettings()))));
          contentOutlineViewer.setInput(editingDomain.getResourceSet());

          new ColumnViewerInformationControlToolTipSupport(contentOutlineViewer,
                  new DiagnosticDecorator.Styled.EditingDomainLocationListener(editingDomain,
                          contentOutlineViewer));

          // Make sure our popups work.
          //
          createContextMenuFor(contentOutlineViewer);

          if (!editingDomain.getResourceSet().getResources().isEmpty())
          {
            // Select the root object in the view.
            //
            contentOutlineViewer.setSelection(
                    new StructuredSelection(editingDomain.getResourceSet().getResources().get(0)),
                    true);
          }
        }

        @Override
        public void makeContributions(IMenuManager menuManager, IToolBarManager toolBarManager,
                IStatusLineManager statusLineManager)
        {
          super.makeContributions(menuManager, toolBarManager, statusLineManager);
          contentOutlineStatusLineManager = statusLineManager;
        }

        @Override
        public void setActionBars(IActionBars actionBars)
        {
          super.setActionBars(actionBars);
          getActionBarContributor().shareGlobalActions(this, actionBars);
        }
      }

      contentOutlinePage = new MyContentOutlinePage();

      // Listen to selection so that we can handle it is a special way.
      //
      contentOutlinePage.addSelectionChangedListener(
              event -> handleContentOutlineSelection(event.getSelection()));
    }

    return contentOutlinePage;
  }

  /**
   * This accesses a cached version of the property sheet.
   */
  public IPropertySheetPage getPropertySheetPage()
  {
    propertySheetPage = new EMFFormsPropertySheetPage(editingDomain) {

      @Override
      public void setActionBars(IActionBars actionBars)
      {
        getActionBarContributor().shareGlobalActions(this, actionBars);
      }

    };

    return propertySheetPage;
  }

  /**
   * This deals with how we want selection in the outliner to affect the other views. <!--
   * begin-user-doc -->
   */
  public void handleContentOutlineSelection(ISelection selection)
  {
    if (selectionViewer != null && !selection.isEmpty()
            && selection instanceof IStructuredSelection)
    {
      final Iterator< ? > selectedElements = ((IStructuredSelection) selection).iterator();
      if (selectedElements.hasNext())
      {
        // Get the first selected element.
        //
        final Object selectedElement = selectedElements.next();

        final var selectionList = new ArrayList<>();
        selectionList.add(selectedElement);
        while (selectedElements.hasNext())
        {
          selectionList.add(selectedElements.next());
        }

        // Set the selection to the widget.
        //
        selectionViewer.setSelection(new StructuredSelection(selectionList));
      }
    }
  }

  /**
   * This is for implementing {@link IEditorPart} and simply tests the command stack. <!--
   * begin-user-doc -->
   */
  @Override
  public boolean isDirty()
  {
    return ((BasicCommandStack) editingDomain.getCommandStack()).isSaveNeeded();
  }

  /**
   * This is for implementing {@link IRevertablePart}.
   */
  @Override
  public void doRevert()
  {
    DiagnosticDecorator.cancel(editingDomain);

    final var resourceSet = editingDomain.getResourceSet();
    final List<Resource> resources = resourceSet.getResources();
    final List<Resource> unloadedResources = new ArrayList<>();
    updateProblemIndication = false;
    for (var i = 0; i < resources.size(); ++i)
    {
      final var resource = resources.get(i);
      if (resource.isLoaded())
      {
        resource.unload();
        unloadedResources.add(resource);
      }
    }

    resourceToDiagnosticMap.clear();
    for (final Resource resource : unloadedResources)
    {
      try
      {
        resource.load(resourceSet.getLoadOptions());
      }
      catch (final IOException exception)
      {
        if (!resourceToDiagnosticMap.containsKey(resource))
        {
          resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
        }
      }
    }

    editingDomain.getCommandStack().flush();

    if (AdapterFactoryEditingDomain.isStale(editorSelection))
    {
      setSelection(StructuredSelection.EMPTY);
    }

    updateProblemIndication = true;
    doUpdateProblemIndication();
  }

  /**
   * This is for implementing {@link IEditorPart} and simply saves the model file. <!--
   * begin-user-doc -->
   */
  @Override
  public void doSave(IProgressMonitor progressMonitor)
  {
    // Save only resources that have actually changed.
    //
    final Map<Object, Object> saveOptions = new HashMap<>();
    saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
            Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
    saveOptions.put(Resource.OPTION_LINE_DELIMITER, Resource.OPTION_LINE_DELIMITER_UNSPECIFIED);

    // Do the work within an operation because this is a long running activity that modifies the
    // workbench.
    //
    final WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
      // This is the method that gets invoked when the operation runs.
      //
      @Override
      public void execute(IProgressMonitor monitor)
      {
        // Save the resources to the file system.
        //
        var first = true;
        final List<Resource> resources = editingDomain.getResourceSet().getResources();
        for (final Resource resource : resources)
        {
          if (first && !editingDomain.isReadOnly(resource))
          {
            try
            {
              final var timeStamp = resource.getTimeStamp();
              resource.save(saveOptions);
              if (resource.getTimeStamp() != timeStamp)
              {
                savedResources.add(resource);
              }
            }
            catch (final Exception exception)
            {
              resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
            }
            first = false;
          }
        }
      }
    };

    updateProblemIndication = false;
    try
    {
      // This runs the options, and shows progress.
      //
      new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);

      // Refresh the necessary state.
      //
      ((BasicCommandStack) editingDomain.getCommandStack()).saveIsDone();
      firePropertyChange(IEditorPart.PROP_DIRTY);
    }
    catch (final InterruptedException exception)
    {
      // Something went wrong that shouldn't.
      //
      XsmpUIPlugin.getInstance().getLog().error("", exception);
      Thread.currentThread().interrupt();
    }
    catch (final Exception exception)
    {
      // Something went wrong that shouldn't.
      //
      XsmpUIPlugin.getInstance().getLog().error("", exception);
    }
    updateProblemIndication = true;
    doUpdateProblemIndication();
  }

  /**
   * This returns whether something has been persisted to the URI of the specified resource. The
   * implementation uses the URI converter from the editor's resource set to try to open an input
   * stream.
   */
  protected boolean isPersisted(Resource resource)
  {
    var result = false;
    try
    {
      final var stream = editingDomain.getResourceSet().getURIConverter()
              .createInputStream(resource.getURI());

      result = true;
      stream.close();

    }
    catch (final IOException e)
    {
      // Ignore
    }
    return result;
  }

  /**
   * This always returns true because it is not currently supported.
   */
  @Override
  public boolean isSaveAsAllowed()
  {
    return true;
  }

  /**
   * This also changes the editor's input.
   */
  @Override
  public void doSaveAs()
  {
    final var saveAsDialog = new SaveAsDialog(getSite().getShell());
    saveAsDialog.open();
    final var path = saveAsDialog.getResult();
    if (path != null)
    {
      final var file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
      if (file != null)
      {
        doSaveAs(URI.createPlatformResourceURI(file.getFullPath().toString(), true),
                new FileEditorInput(file));
      }
    }
  }

  /**
   *
   */
  protected void doSaveAs(URI uri, IEditorInput editorInput)
  {
    editingDomain.getResourceSet().getResources().get(0).setURI(uri);
    setInputWithNotify(editorInput);
    setPartName(editorInput.getName());
    final var progressMonitor = getActionBars().getStatusLineManager() != null
            ? getActionBars().getStatusLineManager().getProgressMonitor()
            : new NullProgressMonitor();
    doSave(progressMonitor);
  }

  /**
   *
   */
  @Override
  public void gotoMarker(IMarker marker)
  {
    final List< ? > targetObjects = markerHelper.getTargetObjects(editingDomain, marker);
    if (!targetObjects.isEmpty())
    {
      setSelectionToViewer(targetObjects);
    }
  }

  /**
   * This is called during startup.
   */
  @Override
  public void init(IEditorSite site, IEditorInput editorInput)
  {
    setSite(site);
    setInputWithNotify(editorInput);
    setPartName(editorInput.getName());
    site.setSelectionProvider(this);
    site.getPage().addPartListener(partListener);
    ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener,
            IResourceChangeEvent.POST_CHANGE);
  }

  /**
   *
   */
  @Override
  public void setFocus()
  {
    getControl(getActivePage()).setFocus();
  }

  /**
   * This implements {@link org.eclipse.jface.viewers.ISelectionProvider}.
   */
  @Override
  public void addSelectionChangedListener(ISelectionChangedListener listener)
  {
    selectionChangedListeners.add(listener);
  }

  /**
   * This implements {@link org.eclipse.jface.viewers.ISelectionProvider}.
   */
  @Override
  public void removeSelectionChangedListener(ISelectionChangedListener listener)
  {
    selectionChangedListeners.remove(listener);
  }

  /**
   * This implements {@link org.eclipse.jface.viewers.ISelectionProvider} to return this editor's
   * overall selection.
   */
  @Override
  public ISelection getSelection()
  {
    return editorSelection;
  }

  /**
   * This implements {@link org.eclipse.jface.viewers.ISelectionProvider} to set this editor's
   * overall selection. Calling this result will notify the listeners.
   */
  @Override
  public void setSelection(ISelection selection)
  {
    editorSelection = selection;

    for (final ISelectionChangedListener listener : selectionChangedListeners)
    {
      listener.selectionChanged(new SelectionChangedEvent(this, selection));
    }
    setStatusLineManager(selection);
  }

  /**
   *
   */
  public void setStatusLineManager(ISelection selection)
  {
    final var statusLineManager = currentViewer != null && currentViewer == contentOutlineViewer
            ? contentOutlineStatusLineManager
            : getActionBars().getStatusLineManager();

    if (statusLineManager != null)
    {
      if (selection instanceof IStructuredSelection)
      {
        final Collection< ? > collection = ((IStructuredSelection) selection).toList();
        switch (collection.size())
        {
          case 0:
          {
            statusLineManager.setMessage(getString("_UI_NoObjectSelected"));
            break;
          }
          case 1:
          {
            final var text = new AdapterFactoryItemDelegator(adapterFactory)
                    .getText(collection.iterator().next());
            statusLineManager.setMessage(getString("_UI_SingleObjectSelected", text));
            break;
          }
          default:
          {
            statusLineManager.setMessage(
                    getString("_UI_MultiObjectSelected", Integer.toString(collection.size())));
            break;
          }
        }
      }
      else
      {
        statusLineManager.setMessage("");
      }
    }
  }

  /**
   * This looks up a string in the plugin's plugin.properties file.
   */
  private static String getString(String key)
  {
    return XsmpUIPlugin.getInstance().getString(key);
  }

  /**
   * This looks up a string in plugin.properties, making a substitution.
   */
  private static String getString(String key, Object s1)
  {
    return XsmpUIPlugin.getInstance().getString(key, new Object[]{s1 });
  }

  /**
   * This implements {@link org.eclipse.jface.action.IMenuListener} to help fill the context menus
   * with contributions from the Edit menu.
   */
  @Override
  public void menuAboutToShow(IMenuManager menuManager)
  {
    ((IMenuListener) getEditorSite().getActionBarContributor()).menuAboutToShow(menuManager);
  }

  /**
   *
   */
  public EditingDomainActionBarContributor getActionBarContributor()
  {
    return (EditingDomainActionBarContributor) getEditorSite().getActionBarContributor();
  }

  /**
   *
   */
  public IActionBars getActionBars()
  {
    return getActionBarContributor().getActionBars();
  }

  /**
   *
   */
  public AdapterFactory getAdapterFactory()
  {
    return adapterFactory;
  }

  /**
   *
   */
  @Override
  public void dispose()
  {
    updateProblemIndication = false;

    ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);

    getSite().getPage().removePartListener(partListener);

    adapterFactory.dispose();

    if (getActionBarContributor().getActiveEditor() == this)
    {
      getActionBarContributor().setActiveEditor(null);
    }
    if (propertySheetPage != null)
    {
      propertySheetPage.dispose();
    }

    if (contentOutlinePage != null)
    {
      contentOutlinePage.dispose();
    }

    super.dispose();
  }

  /**
   * Returns whether the outline view should be presented to the user.
   */
  protected boolean showOutlineView()
  {
    return false;
  }
}