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
package org.eclipse.xsmp.ui.labeling;

import org.eclipse.xsmp.model.xsmp.IncludePath;
import org.eclipse.xsmp.model.xsmp.ProfileReference;
import org.eclipse.xsmp.model.xsmp.ProjectReference;
import org.eclipse.xsmp.model.xsmp.SourcePath;
import org.eclipse.xsmp.model.xsmp.ToolReference;

/**
 * Provides labels for EObjects.
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#label-provider
 */
public class XsmpprojectLabelProvider extends XsmpLabelProvider
{

  String text(SourcePath ele)
  {
    if (ele.getName() == null || ele.getName().isEmpty())
    {
      return ".";
    }
    return ele.getName();
  }

  String text(IncludePath ele)
  {
    if (ele.getName() == null || ele.getName().isEmpty())
    {
      return ".";
    }
    return ele.getName();
  }

  String text(ProjectReference ele)
  {
    return ele.getName();
  }

  String text(ToolReference ele)
  {
    final var tool = ele.getTool();
    if (tool == null || tool.eIsProxy())
    {
      return ele.getName();
    }
    return tool.getDescription();
  }

  String text(ProfileReference ele)
  {
    final var profile = ele.getProfile();
    if (profile == null || profile.eIsProxy())
    {
      return ele.getName();
    }
    return profile.getDescription();
  }

}
