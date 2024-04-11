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
package org.eclipse.xsmp.ui.workspace;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.ui.workspace.EclipseProjectConfig;
import org.eclipse.xtext.ui.workspace.EclipseProjectConfigProvider;
import org.eclipse.xtext.ui.workspace.EclipseSourceFolder;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

public class XsmpEclipseProjectConfig extends EclipseProjectConfig implements IXsmpProjectConfig
{
  private final Set<ISourceFolder> sourceFolders = new HashSet<>();

  private final String profile;

  private final Collection<String> tools;

  private final Collection<String> dependencies;

  public XsmpEclipseProjectConfig(IProject eclipseProject,
          EclipseProjectConfigProvider projectConfigProvider, Project project)
  {
    super(eclipseProject, projectConfigProvider);
    if (project != null)
    {
      profile = project.getProfile();
      tools = project.getTools();
      dependencies = project.getReferencedProjects();
      project.getSources().forEach(this::addSourceFolder);
    }
    else
    {
      profile = "";
      tools = Collections.emptyList();
      dependencies = Collections.emptyList();
    }
  }

  @Override
  public Set< ? extends ISourceFolder> getSourceFolders()
  {
    return sourceFolders;
  }

  private ISourceFolder addSourceFolder(String relativePath)
  {
    final var sourceFolder = new EclipseSourceFolder(getProject(),
            ".".equals(relativePath) ? "" : relativePath);
    sourceFolders.add(sourceFolder);
    return sourceFolder;
  }

  @Override
  public String getProfile()
  {
    return profile;
  }

  @Override
  public Collection<String> getTools()
  {
    return tools;
  }

  @Override
  public Collection<String> getDependencies()
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
  public String toString()
  {
    return new ToStringBuilder(this).addAllFields().toString();
  }

  @Override
  public Set< ? extends ISourceFolder> getIncludeFolders()
  {
    return Collections.emptySet();
  }

  @Override
  public ISourceFolder findIncludeFolderContaining(URI member)
  {
    return null;
  }
}
