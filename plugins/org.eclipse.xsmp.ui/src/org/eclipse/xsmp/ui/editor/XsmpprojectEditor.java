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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.xtext.ui.editor.XtextEditor;

/**
 * A specialized Editor with a custom title
 */
public class XsmpprojectEditor extends XtextEditor
{
  @Override
  protected void doSetInput(IEditorInput input) throws CoreException
  {
    super.doSetInput(input);
    if (input instanceof final IFileEditorInput fileInput)
    {
      setPartName(fileInput.getFile().getProject().getName());
    }
  }

}
