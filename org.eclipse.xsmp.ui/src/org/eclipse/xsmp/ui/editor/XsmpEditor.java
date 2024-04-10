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

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
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

      final var injector = getInjector(project);
      if (injector != null)
      {
        injector.injectMembers(this);
      }
    }
    super.init(site, input);

  }

}
