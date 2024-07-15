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
package org.eclipse.xsmp.ide.workspace;

import java.util.List;

import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.workspace.FileProjectConfig;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

public class XsmpProjectConfig extends FileProjectConfig implements IXsmpProjectConfig
{

  private final String profile;

  private final List<String> tools;

  private final List<String> dependencies;

  /**
   * Ensure that the project name is valid
   *
   * @param project
   * @return the valid project name
   */
  private static String getName(Project project, IWorkspaceConfig workspaceConfig)
  {
    var name = project.getName();
    if (name == null || project.getName().isEmpty())
    {
      name = "<invalid_project>";
    }

    return getUniqueProjectName(name, workspaceConfig);
  }

  private static String getUniqueProjectName(String proposal, IWorkspaceConfig workspaceConfig)
  {
    if (workspaceConfig.findProjectByName(proposal) == null)
    {
      return proposal;
    }
    for (var count = 1; true; ++count)
    {
      if (workspaceConfig.findProjectByName(proposal + count) == null)
      {
        return proposal + count;
      }
    }
  }

  public XsmpProjectConfig(Project project, IWorkspaceConfig workspaceConfig)
  {
    super(project.eResource().getURI().trimSegments(1), getName(project, workspaceConfig),
            workspaceConfig);
    profile = project.getProfile();
    tools = project.getTools();
    dependencies = project.getReferencedProjects();

    project.getSources().forEach(this::addSourceFolder);
  }

  @Override
  public String getProfile()
  {
    return profile;
  }

  @Override
  public List<String> getTools()
  {
    return tools;
  }

  @Override
  public List<String> getDependencies()
  {
    return dependencies;
  }

  @Override
  public boolean equals(Object obj)
  {
    return super.equals(obj);
  }

  @Override
  public int hashCode()
  {
    return super.hashCode();
  }

}
