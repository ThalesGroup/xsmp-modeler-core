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

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xsmp.model.xsmp.Document;
import org.eclipse.xsmp.model.xsmp.ProfileReference;
import org.eclipse.xsmp.model.xsmp.ProjectReference;
import org.eclipse.xsmp.model.xsmp.SourceFolder;
import org.eclipse.xsmp.model.xsmp.ToolReference;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

/**
 * Provides labels for EObjects.
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#label-provider
 */
public class XsmpprojectLabelProvider extends DefaultEObjectLabelProvider
{

  @Inject
  public XsmpprojectLabelProvider(AdapterFactoryLabelProvider delegate)
  {
    super(delegate);
  }

  String text(Document ele)
  {
    return ele.getName();
  }

  String image(Document ele)
  {
    return "full/obj16/Project.png";
  }

  String text(SourceFolder ele)
  {
    return ele.getName();
  }

  String image(SourceFolder ele)
  {
    return "full/obj16/SourceFolder.png";
  }

  String text(ProjectReference ele)
  {
    return ele.getName();
  }

  String image(ProjectReference ele)
  {
    return "full/obj16/ProjectReference.png";
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

  String image(ToolReference ele)
  {
    return "full/obj16/ToolReference.png";
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

  String image(ProfileReference ele)
  {
    return "full/obj16/ProfileReference.png";
  }
}
