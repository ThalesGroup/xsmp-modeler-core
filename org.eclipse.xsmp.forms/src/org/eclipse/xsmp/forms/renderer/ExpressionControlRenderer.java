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
package org.eclipse.xsmp.forms.renderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.SimpleControlSWTControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorFactory;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorModelAccess;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

/**
 * A renderer to render an xtext Expression.
 */
@SuppressWarnings("restriction")
public class ExpressionControlRenderer extends SimpleControlSWTControlSWTRenderer
        implements FocusListener
{

  private static final String RESOURCESET_KEY = "RESOURCESET_KEY";

  /**
   * Default constructor.
   *
   * @param vElement
   *          the view model element to be rendered
   * @param viewContext
   *          the view context
   * @param reportService
   *          The {@link ReportService}
   * @param emfFormsDatabinding
   *          The {@link EMFFormsDatabinding}
   * @param emfFormsLabelProvider
   *          The {@link EMFFormsLabelProvider}
   * @param vtViewTemplateProvider
   *          The {@link VTViewTemplateProvider}
   * @param emfFormsEditSupport
   *          The {@link EMFFormsEditSupport}
   */
  @Inject
  public ExpressionControlRenderer(VControl vElement, ViewModelContext viewContext,
          ReportService reportService, EMFFormsDatabinding emfFormsDatabinding,
          EMFFormsLabelProvider emfFormsLabelProvider,
          VTViewTemplateProvider vtViewTemplateProvider, EMFFormsEditSupport emfFormsEditSupport)
  {
    super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider,
            vtViewTemplateProvider);
  }

  private EmbeddedEditorModelAccess partialEditor;

  private EmbeddedEditor editor;

  @Override
  protected void rootDomainModelChanged() throws DatabindingFailedException
  {
    super.rootDomainModelChanged();

    changeContext(false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Control createSWTControl(Composite parent)
  {
    final var resource = (XtextResource) getViewModelContext().getDomainModel().eResource();

    // get or create a resourceSet shared between all EmbeddedEditor
    var resourceSet = (ResourceSet) getViewModelContext().getContextValue(RESOURCESET_KEY);

    if (resourceSet == null)
    {
      final var resourceSetProvider = resource.getResourceServiceProvider()
              .get(IResourceSetProvider.class);
      resourceSet = resourceSetProvider.get(null);
      getViewModelContext().putContextValue(RESOURCESET_KEY, resourceSet);
    }

    var editorResource = (XtextResource) resourceSet.getResource(resource.getURI(), false);

    if (editorResource == null)
    {
      editorResource = (XtextResource) resourceSet.createResource(resource.getURI());
    }

    final var toto = editorResource;

    // create the editor
    final var factory = editorResource.getResourceServiceProvider()
            .get(EmbeddedEditorFactory.class);
    editor = factory.newEditor(() -> toto).showErrorAndWarningAnnotations()
            .withStyle(SWT.SINGLE | SWT.BORDER).withParent(parent);
    editor.getViewer().getTextWidget().addFocusListener(this);

    partialEditor = editor.createPartialEditor(true);
    changeContext(false);

    return editor.getViewer().getControl();
  }

  private void changeContext(boolean hasFocus)
  {
    try
    {
      if (getModelValue().getValue() != null)
      {
        final var document = editor.getDocument();

        final var model = getResourceAsText(
                (XtextResource) getViewModelContext().getDomainModel().eResource());
        document.set(model);

        final var node = document.readOnly(state -> {
          final var editablePart = (EObject) state
                  .getEObject(EcoreUtil.getURI(getViewModelContext().getDomainModel()).fragment())
                  .eGet(getFeature());
          return NodeModelUtils.findActualNodeFor(editablePart);
        });

        partialEditor.updateModel(model.substring(0, node.getOffset()),
                model.substring(node.getOffset(), node.getEndOffset()),
                model.substring(node.getEndOffset()));
      }
      else if (hasFocus)
      {

        final var document = editor.getDocument();
        var model = getResourceAsText(
                (XtextResource) getViewModelContext().getDomainModel().eResource());
        document.set(model);

        document.modify(state -> {

          final var fakeExpression = XcatalogueFactory.eINSTANCE.createBooleanLiteral();
          final var editablePart = state
                  .getEObject(EcoreUtil.getURI(getViewModelContext().getDomainModel()).fragment());
          editablePart.eSet(getFeature(), fakeExpression);
          return fakeExpression;
        });
        final var node = document.readOnly(state -> {
          final var editablePart = (EObject) state
                  .getEObject(EcoreUtil.getURI(getViewModelContext().getDomainModel()).fragment())
                  .eGet(getFeature());
          return NodeModelUtils.findActualNodeFor(editablePart);
        });

        model = document.readOnly(state -> state.getParseResult().getRootNode().getText());
        document.set(model);
        partialEditor.updateModel(model.substring(0, node.getOffset()), "",
                model.substring(node.getEndOffset()));

      }
    }
    catch (final Exception e)
    {
      getReportService().report(new RenderingFailedReport(e));
    }
  }

  private String getResourceAsText(XtextResource resource)
  {
    final var stream = new ByteArrayOutputStream();
    try
    {
      resource.save(stream, null);

      return stream.toString();
    }
    catch (final IOException e)
    {

      getReportService().report(new RenderingFailedReport(e));
      return "";
    }

  }

  @Override
  public void focusGained(FocusEvent e)
  {
    changeContext(true);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void focusLost(FocusEvent event)
  {
    try
    {

      final var value = editor.getDocument()
              .readOnly(state -> ((EObject) state
                      .getEObject(
                              EcoreUtil.getURI(getViewModelContext().getDomainModel()).fragment())
                      .eGet(getFeature())));

      if (value == null)
      {
        getModelValue().setValue(null);
        editor.getDocument().modify(state -> {
          final var editablePart = state
                  .getEObject(EcoreUtil.getURI(getViewModelContext().getDomainModel()).fragment());
          editablePart.eSet(getFeature(), null);
          return true;
        });
      }
      else
      {
        getModelValue().setValue(EcoreUtil.copy(value));
      }
    }
    catch (final Exception e)
    {
      getReportService().report(new RenderingFailedReport(e));
    }

  }

  @Override
  protected Binding[] createBindings(Control control) throws DatabindingFailedException
  {
    return new Binding[]{};
  }

  @Override
  protected String getUnsetText()
  {
    return "";
  }

}
