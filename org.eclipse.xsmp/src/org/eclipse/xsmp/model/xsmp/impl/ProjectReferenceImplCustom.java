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

package org.eclipse.xsmp.model.xsmp.impl;

import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

public class ProjectReferenceImplCustom extends ProjectReferenceImpl
{
  @Override
  public String getName()
  {
    if (project != null)
    {
      if (project.eIsProxy())
      {
        final var nodes = NodeModelUtils.findNodesForFeature(this,
                XsmpPackage.Literals.PROJECT_REFERENCE__PROJECT);
        if (nodes.size() == 1)
        {
          final var text = NodeModelUtils.getTokenText(nodes.get(0));
          return text.substring(1, text.length() - 1);
        }
      }
      return project.getName();
    }
    return "<invalid>";
  }
} // ProjectReferenceImplCustom
