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

import java.util.UUID;

import com.google.inject.Inject;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A renderer to render an UUID an to generate a new one.
 */
public class UuidControlRenderer extends TextControlSWTRenderer
{

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
  public UuidControlRenderer(VControl vElement, ViewModelContext viewContext,
          ReportService reportService, EMFFormsDatabinding emfFormsDatabinding,
          EMFFormsLabelProvider emfFormsLabelProvider,
          VTViewTemplateProvider vtViewTemplateProvider, EMFFormsEditSupport emfFormsEditSupport)
  {
    super(vElement, viewContext, reportService, emfFormsDatabinding, emfFormsLabelProvider,
            vtViewTemplateProvider, emfFormsEditSupport);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Control createSWTControl(Composite parent)
  {
    final var main = new Composite(parent, SWT.NONE);
    GridLayoutFactory.fillDefaults().numColumns(2).applyTo(main);
    GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).applyTo(main);
    final var control = super.createSWTControl(main);
    final var button = new Button(main, SWT.PUSH);
    button.setText("Generate"); //$NON-NLS-1$
    button.addSelectionListener(new SelectionAdapter() {
      @SuppressWarnings("unchecked")
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        try
        {
          getModelValue().setValue(UUID.randomUUID().toString());
        }
        catch (final DatabindingFailedException ex)
        {
          getReportService().report(new DatabindingFailedReport(ex));
        }
      }
    });
    return control;
  }

}
