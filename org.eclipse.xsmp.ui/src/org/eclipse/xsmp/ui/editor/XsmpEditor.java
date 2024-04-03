/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ui.editor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.extension.IExtensionManager;
import org.eclipse.xsmp.ui.workspace.XsmpEclipseProjectConfigProvider;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * A specialized Editor that reinject members with the Profile provider if
 * provided
 */
public class XsmpEditor extends XtextEditor
{

  @Inject
  private XsmpEclipseProjectConfigProvider configurationProvider;

  @Inject
  private IExtensionManager extensionManager;

  private Injector getInjector(IProject project)
  {
    final var config = configurationProvider.createProjectConfig(project);
    if (config != null)
    {
      return extensionManager.getInjectorForProfile(config.getProfile());
    }
    return null;
  }

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException
  {

    // in case of a file editor input (file inside a project), get the Profile
    // injector and re-inject all the members to use specific content assist, quick fix, ...
    if (input instanceof final IFileEditorInput fileInput)
    {
      final var project = fileInput.getFile().getProject();

      if (!project.getFile(XsmpConstants.XSMP_PROJECT_FILENAME).exists())
      {
        final var createProjectFile = MessageDialog.openQuestion(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                "Missing xsmp.project configuration File",
                "The xsmp.project file does not exist at the root of the project. Do you want to create and edit it?");
        if (createProjectFile)
        {
          createXsmpProjectFile(project);
        }
      }

      final var injector = getInjector(project);
      if (injector != null)
      {
        injector.injectMembers(this);
      }
    }
    super.init(site, input);

  }

  private void createXsmpProjectFile(IProject project) throws PartInitException
  {
    try
    {
      final var file = project.getFile(XsmpConstants.XSMP_PROJECT_FILENAME);
      final var contentBuilder = new StringBuilder();

      contentBuilder.append("/** ").append(project.getName()).append(" project description */\n");
      contentBuilder.append("project \"").append(project.getName()).append("\"\n");
      contentBuilder.append("\n");
      contentBuilder.append("\n");
      contentBuilder.append("// Source folder containing modeling file(s)\n");
      contentBuilder.append("source \"smdl\"\n");
      contentBuilder.append("\n");
      contentBuilder.append("\n");
      contentBuilder.append("// Uncomment one of the available profiles\n");
      contentBuilder.append("//profile \"org.eclipse.xsmp.profile.xsmp-sdk\"\n");
      contentBuilder.append("//profile \"org.eclipse.xsmp.profile.esa-cdk\"\n");
      contentBuilder.append("\n");
      contentBuilder.append("\n");
      contentBuilder.append("// Comment/Uncomment the tools you would like to use\n");
      contentBuilder.append("tool \"org.eclipse.xsmp.tool.smp\"\n");
      contentBuilder.append("//tool \"org.eclipse.xsmp.tool.python\"\n");
      contentBuilder.append("\n");
      contentBuilder.append("\n");

      contentBuilder.append("// Project dependencies\n");
      contentBuilder.append(
              "// TODO: Remove dependencies which are not required by your modeling files.\n");

      for (final var dep : project.getDescription().getDynamicReferences())
      {
        if (!"org.eclipse.xsmp.lib".equals(dep.getName()))
        {
          contentBuilder.append("dependency \"").append(dep.getName()).append("\"\n");
        }
      }
      contentBuilder.append("\n");
      final var content = contentBuilder.toString();

      final InputStream stream = new ByteArrayInputStream(content.getBytes());
      file.create(stream, true, null);

      IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), file);
    }
    catch (

    final CoreException e)
    {
      e.printStackTrace();
    }
  }
}
