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
package org.eclipse.xsmp.ui.cdt;

import java.util.regex.Pattern;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IName;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.index.IIndexBinding;
import org.eclipse.cdt.core.index.IIndexName;
import org.eclipse.cdt.core.index.IndexFilter;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.internal.core.model.ext.ICElementHandle;
import org.eclipse.cdt.internal.ui.viewsupport.CElementLabels;
import org.eclipse.cdt.internal.ui.viewsupport.IndexUI;
import org.eclipse.cdt.ui.CDTUITools;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.xsmp.model.xsmp.Document;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.generator.trace.FileOpener;
import org.eclipse.xtext.ui.generator.trace.OpenOppositeFileHandler;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;

/**
 * An handler that open with cdt the generated code for a given EObject (Type,
 * Field, ...)
 */
@SuppressWarnings("restriction")
public class OpenCppFileHandler extends OpenOppositeFileHandler
{

  @Inject
  private IQualifiedNameProvider qualifiedNameProvider;

  @Inject
  protected EObjectAtOffsetHelper eObjectAtOffsetHelper;

  protected static ICElementHandle getCElementForName(ICProject project, IIndex index,
          IName declName)
    throws CoreException
  {
    if (declName instanceof IIndexName)
    {
      final var indexName = (IIndexName) declName;
      return IndexUI.getCElementForName(project, index, indexName);
    }
    if (declName instanceof IASTName)
    {
      final var astName = (IASTName) declName;
      return IndexUI.getCElementForName(project, index, astName);
    }
    return null;
  }

  @Override
  protected void collectOpeners(IEditorPart editor, IAcceptor<FileOpener> acceptor)
  {
    try
    {
      final var xtextEditor = (XtextEditor) editor;
      if (xtextEditor != null)
      {

        final var selection = (ITextSelection) xtextEditor.getSelectionProvider().getSelection();
        xtextEditor.getDocument().readOnly(new IUnitOfWork.Void<XtextResource>() {
          @Override
          public void process(XtextResource state) throws Exception
          {
            final var target = eObjectAtOffsetHelper.resolveElementAt(state, selection.getOffset());
            if (target != null)
            {
              collectOpeners(CoreModel.getDefault().getCModel().getCProject(
                      xtextEditor.getResource().getProject().getName()), target, acceptor);
            }
          }
        });
      }
    }
    catch (final Exception e)
    {
      // ignore
    }
  }

  protected void collectOpeners(ICProject project, EObject target, IAcceptor<FileOpener> acceptor)
  {
    IIndex index = null;

    // we need a read-lock on the index
    try
    {

      index = CCorePlugin.getIndexManager().getIndex(project);
      index.acquireReadLock();
      try
      {
        target = EcoreUtil2.getContainerOfType(target, NamedElement.class);
        // collect the opener for the selected target.
        // if no opener found, try with the parent object
        while (target != null && !collectOpeners(project, index, target, acceptor))
        {
          target = EcoreUtil2.getContainerOfType(target.eContainer(), NamedElement.class);
        }
      }
      finally
      {
        index.releaseReadLock();

      }
    }
    catch (final CoreException e)
    {
      CUIPlugin.log(e);
    }
    catch (final InterruptedException e)
    {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Get the patterns to query the CDT index. Types have a special treatment
   * because they can be defined as <type> and <type>Gen
   *
   * @param target
   * @return
   */
  protected Pattern[] getPatterns(EObject target)
  {
    final var fqn = qualifiedNameProvider.getFullyQualifiedName(target);
    final var type = EcoreUtil2.getContainerOfType(target, Type.class);
    var indexGen = -1;

    if (type != null)
    {
      indexGen = qualifiedNameProvider.getFullyQualifiedName(type).getSegmentCount() - 1;
    }
    final var cfqn = new Pattern[fqn.getSegmentCount()];
    for (var i = 0; i < fqn.getSegmentCount(); i++)
    {
      if (i == indexGen)
      {
        cfqn[i] = Pattern.compile(fqn.getSegment(i) + "(Gen)?");
      }
      else
      {
        cfqn[i] = Pattern.compile(fqn.getSegment(i));
      }
    }
    return cfqn;
  }

  protected boolean collectOpeners(ICProject project, IIndex index, EObject target,
          IAcceptor<FileOpener> acceptor)
    throws CoreException
  {

    if (target instanceof Document)
    {
      return true;
    }

    // find bindings for name
    final var bindings = index.findBindings(getPatterns(target), true, IndexFilter.ALL,
            new NullProgressMonitor());
    var found = false;
    // find references for each binding
    for (final IIndexBinding b : bindings)
    {
      // Convert bindings to CElements.
      final IName[] declNames = index.findNames(b,
              IIndex.FIND_DEFINITIONS | IIndex.SEARCH_ACROSS_LANGUAGE_BOUNDARIES);

      for (final IName name : declNames)
      {
        acceptor.accept(new ElementOpener(getCElementForName(project, index, name)));
        found = true;
      }
    }
    // if no definition found, find a declaration
    if (!found)
    {
      for (final IIndexBinding b : bindings)
      {
        // Convert bindings to CElements.
        final IName[] declNames = index.findNames(b,

                IIndex.FIND_DECLARATIONS | IIndex.SEARCH_ACROSS_LANGUAGE_BOUNDARIES);

        for (final IName name : declNames)
        {
          acceptor.accept(new ElementOpener(getCElementForName(project, index, name)));
          found = true;
        }
      }
    }
    return found;
  }

  private static class ElementOpener extends FileOpener
  {
    private final ICElement handle;

    public ElementOpener(ICElement element)
    {
      handle = element;
    }

    @Override
    public void open(IWorkbenchPage page)
    {
      try
      {
        CDTUITools.openInEditor(handle);
      }
      catch (PartInitException | CModelException e)
      {
        CUIPlugin.log(e);
      }
    }

    @Override
    public String getQualifiedLabel()
    {
      return CElementLabels.getElementLabel(handle,
              CElementLabels.M_PARAMETER_TYPES | CElementLabels.M_EXCEPTIONS
                      | CElementLabels.PROJECT_POST_QUALIFIED | CElementLabels.F_PRE_TYPE_SIGNATURE
                      | CElementLabels.M_APP_RETURNTYPE);
    }

    @Override
    public String getShortLabel()
    {
      return handle.getPath().toString();
    }

    @Override
    public ImageDescriptor getImageDescriptor()
    {
      final var workbenchAdapter = adapt(handle, IWorkbenchAdapter.class);
      return workbenchAdapter != null ? workbenchAdapter.getImageDescriptor(handle) : null;
    }
  }
}
