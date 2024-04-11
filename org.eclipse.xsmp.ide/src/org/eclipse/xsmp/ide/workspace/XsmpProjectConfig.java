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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.workspace.FileProjectConfig;
import org.eclipse.xtext.workspace.FileSourceFolder;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

public class XsmpProjectConfig extends FileProjectConfig implements IXsmpProjectConfig
{

  private final String profile;

  private final List<String> tools;

  private final List<String> dependencies;

  private final Set<FileSourceFolder> includeFolders = new HashSet<>();

  /**
   * Ensure that the project name is valid
   *
   * @param project
   * @return the valid project name
   */
  private static String getName(Project project)
  {
    final var name = project.getName();
    if (name == null || project.getName().isEmpty())
    {
      return "<invalid_project>";
    }
    return name;
  }

  public XsmpProjectConfig(Project project, IWorkspaceConfig workspaceConfig)
  {
    super(EcoreUtil2.getNormalizedResourceURI(project).trimSegments(1), getName(project),
            workspaceConfig);

    profile = project.getProfile();
    tools = project.getTools();
    dependencies = project.getReferencedProjects();

    project.getSources().forEach(this::addSourceFolder);
    project.getIncludes().forEach(this::addIncludeFolder);
  }

  public FileSourceFolder addIncludeFolder(String relativePath)
  {
    final var includeFolder = new FileSourceFolder(this, relativePath);
    includeFolders.add(includeFolder);
    return includeFolder;
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

  @Override
  public Set< ? extends ISourceFolder> getIncludeFolders()
  {
    return includeFolders;
  }

  @Override
  public ISourceFolder findIncludeFolderContaining(URI member)
  {
    return findFirst(includeFolders, f -> f.contains(member));
  }

}
