/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
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

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.ui.extension.ExtensionManager;
import org.eclipse.xtext.builder.nature.ToggleXtextNatureCommand;
import org.eclipse.xtext.ui.editor.IXtextEditorCallback;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.util.DontAskAgainDialogs;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class XsmpProjectAddingEditorCallback extends IXtextEditorCallback.NullImpl
{
  private static final Logger log = Logger.getLogger(XsmpProjectAddingEditorCallback.class);

  private static final String ADD_XSMP_PROJECT = "add_xsmp_project";

  private @Inject DontAskAgainDialogs dialogs;

  @Inject
  private ToggleXtextNatureCommand toggleNature;

  @Override
  public void afterCreatePartControl(XtextEditor editor)
  {

    final var resource = editor.getResource();

    // check that xtext project nature is set and xsmp.project exists
    if (resource != null
            && (!resource.getProject().getFile(XsmpConstants.XSMP_PROJECT_FILENAME).exists()
                    || !toggleNature.hasNature(resource.getProject()))
            && resource.getProject().isAccessible() && !resource.getProject().isHidden())

    {
      final var project = resource.getProject();

      final var title = "Configure XSMP";
      final var message = "Do you want to convert '" + project.getName() + "' to an XSMP project?";
      var convertProject = false;
      if (MessageDialogWithToggle.PROMPT.equals(dialogs.getUserDecision(ADD_XSMP_PROJECT)))
      {
        final var userAnswer = dialogs.askUser(message, title, ADD_XSMP_PROJECT,
                editor.getEditorSite().getShell());
        if (userAnswer == IDialogConstants.YES_ID)
        {
          convertProject = true;
        }
        else if (userAnswer == IDialogConstants.CANCEL_ID)
        {
          return;
        }
      }
      else if (MessageDialogWithToggle.ALWAYS.equals(dialogs.getUserDecision(ADD_XSMP_PROJECT)))
      {
        convertProject = true;
      }
      if (convertProject)
      {
        if (!toggleNature.hasNature(project))
        {
          toggleNature.toggleNature(project);
        }
        if (!project.getFile(XsmpConstants.XSMP_PROJECT_FILENAME).exists())
        {
          createXsmpProjectFile(project);
        }
      }
    }
  }

  @Inject
  private ExtensionManager extensions;

  private void createXsmpProjectFile(IProject project)
  {
    try
    {
      final var file = project.getFile(XsmpConstants.XSMP_PROJECT_FILENAME);
      final var contentBuilder = new StringBuilder();

      contentBuilder.append("/** ").append(project.getName()).append(" Project Configuration */\n");
      contentBuilder.append("project \"").append(project.getName()).append("\"\n");
      contentBuilder.append("\n");
      contentBuilder.append("\n");
      contentBuilder.append("// Source folder containing modeling file(s)\n");
      contentBuilder.append("source \"smdl\"\n");
      contentBuilder.append("\n");
      contentBuilder.append("\n");

      for (final var profile : extensions.getProfiles())
      {
        contentBuilder.append("\n");
        contentBuilder.append("\n");
        contentBuilder.append("// uncomment to use ").append(profile.getDescription()).append("\n");
        contentBuilder.append("//profile \"" + profile.getId() + "\"\n");
      }
      for (final var tool : extensions.getTools())
      {
        contentBuilder.append("\n");
        contentBuilder.append("\n");
        contentBuilder.append("// uncomment to use ").append(tool.getDescription()).append("\n");
        contentBuilder.append("//tool \"" + tool.getId() + "\"\n");
      }

      contentBuilder.append("// Project dependencies\n");

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
    catch (final CoreException e)
    {
      log.error("Error creating XSMP Project Configuration file", e);
    }
  }
}
