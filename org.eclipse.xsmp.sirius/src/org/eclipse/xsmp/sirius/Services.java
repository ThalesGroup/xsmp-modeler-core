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
package org.eclipse.xsmp.sirius;

import java.util.UUID;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;

/**
 * The services class used by VSM.
 */
public class Services
{

  /**
   * See
   * http://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.sirius.doc%2Fdoc%2Findex.html&cp=24
   * for documentation on how to write service methods.
   */

  public String randomUUID(EObject self)
  {

    return UUID.randomUUID().toString();
  }

  public EObject openTextEditor(EObject any)
  {
    if (any != null && any.eResource() instanceof XtextResource && any.eResource().getURI() != null)
    {

      final var fileURI = any.eResource().getURI().toPlatformString(true);
      final var workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileURI));
      if (workspaceFile != null)
      {
        final var page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        try
        {
          final var openEditor = IDE.openEditor(page, workspaceFile, true);
          if (openEditor instanceof AbstractTextEditor)
          {
            final var node = NodeModelUtils.findActualNodeFor(any);
            if (node != null)
            {
              final var offset = node.getOffset();
              final var length = node.getTotalEndOffset() - offset;
              ((AbstractTextEditor) openEditor).selectAndReveal(offset, length);
            }
          }

          // editorInput.
        }
        catch (final PartInitException e)
        {
          // Put your exception handler here if you wish to.
        }
      }
    }
    return any;
  }

  public String label(EObject context)
  {

    final var resource = context.eResource();
    if (resource instanceof XtextResource)
    {
      final var xtextResource = (XtextResource) resource;

      final var labelProvider = xtextResource.getResourceServiceProvider()
              .get(ILabelProvider.class);

      return /* context.eClass().getName() + " " + */ labelProvider.getText(context);
    }
    return null;
  }

}
