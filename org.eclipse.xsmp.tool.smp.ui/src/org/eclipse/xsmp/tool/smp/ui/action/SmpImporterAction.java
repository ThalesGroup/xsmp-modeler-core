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
package org.eclipse.xsmp.tool.smp.ui.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.ui.CommonUIPlugin;
import org.eclipse.emf.common.ui.dialogs.DiagnosticDialog;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xsmp.tool.smp.importer.SmpImporter;
import org.eclipse.xsmp.tool.smp.util.SmpResourceFactoryImpl;
import org.eclipse.xsmp.tool.smp.util.SmpURIConverter;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.validation.CancelableDiagnostician;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Action to convert an smpcat file in xsmpcat
 */
public class SmpImporterAction extends AbstractHandler
{

  private static final Logger log = Logger.getLogger(SmpImporterAction.class);

  @Inject
  private Provider<JavaIoFileSystemAccess> fsaProvider;

  @Inject
  private SmpImporter importer;

  @Inject
  private CancelableDiagnostician diagnostician;

  @Inject
  private SmpURIConverter uriConverter;

  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException
  {

    final var selection = HandlerUtil.getCurrentStructuredSelection(event);
    @SuppressWarnings("unchecked")
    final List<IFile> files = selection.toList();
    if (files != null)
    {
      final var shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

      for (final IFile model : files)
      {

        try
        {
          final var modelURI = URI.createPlatformResourceURI(model.getFullPath().toString(), true);

          final ResourceSet rs = new ResourceSetImpl();
          rs.setURIConverter(uriConverter);
          rs.getResourceFactoryRegistry().getProtocolToFactoryMap().put("http",
                  new SmpResourceFactoryImpl());
          final var resource = rs.getResource(modelURI, true);
          EcoreUtil.resolveAll(resource);
          final var errors = resource.getErrors();
          final var warnings = resource.getWarnings();

          if (!errors.isEmpty() || !warnings.isEmpty())
          {

            final var errorMsg = new StringBuilder();
            errorMsg.append("The following errors/warnings occured: \n");
            errors.forEach(e -> errorMsg.append("Error: Line " + e.getLine() + " Column "
                    + e.getColumn() + " Message: " + e.getMessage() + "\n"));
            warnings.forEach(e -> errorMsg.append("Warning: Line " + e.getLine() + " Column "
                    + e.getColumn() + " Message: " + e.getMessage() + "\n"));

            errorMsg.append("\nDo you want to continue the conversion ?");
            if (!MessageDialog.openQuestion(shell,
                    "Error loading model " + resource.getURI().toString() + ".",
                    errorMsg.toString()))
            {
              continue;
            }
          }
          // validate the resource
          final var diag = diagnostician.validate(resource.getContents().get(0));

          if (diag.getSeverity() != org.eclipse.emf.common.util.Diagnostic.OK
                  && Window.CANCEL == DiagnosticDialog.openProblem(shell,
                          EMFEditUIPlugin.INSTANCE.getString("_UI_ValidationProblems_title"),
                          EMFEditUIPlugin.INSTANCE.getString("_UI_ValidationProblems_message"),
                          diag))
          {
            continue;
          }
          final var fsa = fsaProvider.get();

          final var targetFile = model.getLocation().removeFileExtension()
                  .addFileExtension("xsmpcat").toFile();
          if (targetFile.exists() && !MessageDialog.openConfirm(shell,
                  "File " + targetFile.getPath() + " already exist",
                  "Do you want to override the existing file ?"))
          {
            continue;
          }

          fsa.setOutputPath(model.getParent().getLocation().toFile().getAbsolutePath());
          final IRunnableWithProgress operation = monitor -> {
            try
            {
              importer.doGenerate(resource, fsa);
            }
            finally
            {
              try
              {
                model.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
              }
              catch (final CoreException e)
              {
                log.error(e);
              }

            }
          };

          PlatformUI.getWorkbench().getProgressService().run(true, true, operation);

          MessageDialog.openInformation(shell, "Conversion successfull",
                  "File " + model.getName() + " successfully converted to " + targetFile.getName());
        }
        catch (final InterruptedException e)
        {
          Thread.currentThread().interrupt();
        }
        catch (final Exception e)
        {
          log.error(e);
          DiagnosticDialog.openProblem(shell, CommonUIPlugin.INSTANCE.getString("_UI_Error_label"),
                  "Error during conversion of " + model.getName(), BasicDiagnostic.toDiagnostic(e));
        }
      }
    }
    return null;
  }
}