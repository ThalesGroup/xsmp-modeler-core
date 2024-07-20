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
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.ui.CommonUIPlugin;
import org.eclipse.emf.common.ui.dialogs.DiagnosticDialog;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.IProgressConstants2;
import org.eclipse.xsmp.tool.smp.importer.SmpImporter;
import org.eclipse.xsmp.tool.smp.util.SmpResourceFactoryImpl;
import org.eclipse.xsmp.tool.smp.util.SmpResourceSet;
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
  private Provider<SmpResourceSet> resourceSetProvider;

  @Inject
  Shell shell;

  class Visitor implements IResourceProxyVisitor
  {
    private final URIConverter uriConverter;

    public Visitor(URIConverter uriConverter)
    {
      this.uriConverter = uriConverter;
    }

    @Override
    public boolean visit(IResourceProxy proxy)
    {

      if (proxy.getName().endsWith(".smpcat"))
      {
        uriConverter.getURIMap().put(URI.createURI(proxy.getName()),
                URI.createPlatformResourceURI(proxy.requestFullPath().toString(), true));
        return false;
      }

      return true;
    }
  }

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

      final var workbench = PlatformUI.getWorkbench();
      final var progressService = workbench.getProgressService();
      final Job job = new ImportJob("Xsmpcat conversion", files);
      job.setPriority(Job.LONG);
      job.setProperty(IProgressConstants2.SHOW_IN_TASKBAR_ICON_PROPERTY, Boolean.TRUE);
      job.setUser(true);
      progressService.showInDialog(shell, job);
      job.schedule();
    }
    return null;
  }

  class ImportJob extends Job
  {

    private final List<IFile> files;

    public ImportJob(String name, List<IFile> files)
    {
      super(name);
      this.files = files;
    }

    @Override
    protected IStatus run(IProgressMonitor monitor)
    {

      final ResourceSet rs = resourceSetProvider.get();

      try
      {
        ResourcesPlugin.getWorkspace().getRoot().accept(new Visitor(rs.getURIConverter()),
                IResource.FILE);
      }
      catch (final CoreException e1)
      {
        // ignore
      }
      rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("smpcat",
              new SmpResourceFactoryImpl());

      monitor.beginTask("", files.size() * 100);
      for (final IFile model : files)
      {

        try
        {
          final var modelURI = URI.createPlatformResourceURI(model.getFullPath().toString(), true);

          monitor.subTask("Loading " + model.getName() + " ...");

          final var resource = rs.getResource(modelURI, true);
          monitor.worked(30);

          if (monitor.isCanceled())
          {
            return Status.CANCEL_STATUS;
          }
          monitor.subTask("Resolving dependencies ...");
          EcoreUtil.resolveAll(resource);
          monitor.worked(10);

          if (monitor.isCanceled())
          {
            return Status.CANCEL_STATUS;
          }
          final var errors = resource.getErrors();
          final var warnings = resource.getWarnings();

          if (!errors.isEmpty() || !warnings.isEmpty())
          {
            class PromtDialog implements Runnable
            {
              private boolean ok;

              @Override
              public void run()
              {
                final var msg = new StringBuilder();
                msg.append("The following errors/warnings occured: \n");
                errors.forEach(e -> msg.append("Error: Line " + e.getLine() + " Column "
                        + e.getColumn() + " Message: " + e.getMessage() + "\n"));
                warnings.forEach(e -> msg.append("Warning: Line " + e.getLine() + " Column "
                        + e.getColumn() + " Message: " + e.getMessage() + "\n"));

                msg.append("\nDo you want to continue the conversion ?");
                ok = MessageDialog.openQuestion(shell,
                        "Error loading model " + resource.getURI().toString() + ".",
                        msg.toString());
              }
            }

            final var dialog = new PromtDialog();
            Display.getDefault().syncExec(dialog);
            if (!dialog.ok)
            {
              continue;
            }
          }
          // validate the resource

          monitor.subTask("Validating the Catalogue ...");
          final var diag = diagnostician.validate(resource.getContents().get(0));
          monitor.worked(30);
          if (diag.getSeverity() != org.eclipse.emf.common.util.Diagnostic.OK)
          {
            class PromtDialog implements Runnable
            {
              private boolean ok;

              @Override
              public void run()
              {
                ok = Window.OK == DiagnosticDialog.openProblem(shell,
                        "The Catalogue file is invalid",
                        "Do you want to continue the Xsmpcat conversion ?", diag);
              }
            }

            final var dialog = new PromtDialog();
            Display.getDefault().syncExec(dialog);
            if (!dialog.ok)
            {
              continue;
            }
          }

          if (monitor.isCanceled())
          {
            return Status.CANCEL_STATUS;
          }

          final var fsa = fsaProvider.get();

          final var targetFile = model.getLocation().removeFileExtension()
                  .addFileExtension("xsmpcat").toFile();
          if (targetFile.exists())
          {

            class PromtDialog implements Runnable
            {
              private boolean ok;

              @Override
              public void run()
              {
                ok = MessageDialog.openConfirm(shell,
                        "File " + targetFile.getPath() + " already exist",
                        "Do you want to override the existing file ?");
              }
            }

            final var dialog = new PromtDialog();
            Display.getDefault().syncExec(dialog);
            if (!dialog.ok)
            {
              continue;
            }
          }

          fsa.setOutputPath(model.getParent().getLocation().toFile().getAbsolutePath());

          monitor.subTask("Converting the Catalogue ...");

          importer.doGenerate(resource, fsa);

          monitor.worked(30);

          model.getParent().refreshLocal(IResource.DEPTH_ONE, monitor);
        }
        catch (final Exception e)
        {
          SmpImporterAction.log.error(e);
          Display.getDefault()
                  .asyncExec(() -> DiagnosticDialog.openProblem(shell,
                          CommonUIPlugin.INSTANCE.getString("_UI_Error_label"),
                          "Error during conversion of " + model.getName(),
                          BasicDiagnostic.toDiagnostic(e)));
        }

        if (monitor.isCanceled())
        {
          return Status.CANCEL_STATUS;
        }
      }
      monitor.done();
      return Status.OK_STATUS;
    }

  }
}