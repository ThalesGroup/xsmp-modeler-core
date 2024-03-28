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
import java.util.Objects;

import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.model.xsmp.ProjectReference;
import org.eclipse.xsmp.model.xsmp.ToolReference;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.workspace.FileProjectConfig;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

public class XsmpProjectConfig extends FileProjectConfig implements IXsmpProjectConfig
{

  private final String profile;

  private final List<String> tools;

  private final List<String> dependencies;

  public XsmpProjectConfig(Project project, IWorkspaceConfig workspaceConfig)
  {
    super(EcoreUtil2.getNormalizedResourceURI(project).trimSegments(1), project.getName(),
            workspaceConfig);

    profile = project.getProfile() != null ? project.getProfile().getName() : "";
    tools = project.getTools().stream().map(ToolReference::getName).toList();
    dependencies = project.getReferencedProjects().stream().map(ProjectReference::getName)
            .filter(Objects::nonNull).toList();
    project.getSourceFolders().forEach(folder -> addSourceFolder(folder.getName()));
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
