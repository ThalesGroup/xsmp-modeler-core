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
package org.eclipse.xsmp.ui.outline;

import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;

/**
 * Customization of the default outline structure. See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#outline
 */
public class XsmpprojectOutlineTreeProvider extends DefaultOutlineTreeProvider
{

  protected void _createChildren(DocumentRootNode parentNode, Project doc)
  {

    createEStructuralFeatureNode(parentNode, doc, XsmpPackage.Literals.NAMED_ELEMENT__NAME,
            imageDispatcher.invoke(doc), textDispatcher.invoke(doc), true);

    for (final var source : doc.getSourceFolders())
    {
      createNode(parentNode, source);
    }

    createNode(parentNode, doc.getProfile());

    for (final var tool : doc.getTools())
    {
      createNode(parentNode, tool);
    }

    for (final var dependency : doc.getReferencedProjects())
    {
      createNode(parentNode, dependency);
    }
  }

}
