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

public class ProfileReferenceImplCustom extends ProfileReferenceImpl
{
  @Override
  public String getName()
  {
    if (profile != null)
    {
      if (profile.eIsProxy())
      {
        final var nodes = NodeModelUtils.findNodesForFeature(this,
                XsmpPackage.Literals.PROFILE_REFERENCE__PROFILE);
        if (nodes.size() == 1)
        {
          final var text = NodeModelUtils.getTokenText(nodes.get(0));
          return text.substring(1, text.length() - 1);
        }
      }
      return profile.getName();
    }
    return "<invalid>";
  }
} // ProfileReferenceImplCustom
