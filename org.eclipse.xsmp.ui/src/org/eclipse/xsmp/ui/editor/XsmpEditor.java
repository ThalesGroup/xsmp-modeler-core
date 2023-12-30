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

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.xsmp.ui.configuration.IXsmpServiceUIProvider;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.google.inject.Inject;

/**
 * A specialized Editor that reinject members with the Profile provider if
 * provided
 */
public class XsmpEditor extends XtextEditor
{
  @Inject
  private IXsmpServiceUIProvider configurationProvider;

  @Override
  public void init(IEditorSite site, IEditorInput input) throws PartInitException
  {
    // in case of a file editor input (file inside a project), get the Profile
    // injector and re-inject all the members to use specific content assist, quick fix, ...
    if (input instanceof IFileEditorInput)
    {
      configurationProvider.getInjector(((IFileEditorInput) input).getFile().getProject())
              .injectMembers(this);

    }
    super.init(site, input);
  }

}
