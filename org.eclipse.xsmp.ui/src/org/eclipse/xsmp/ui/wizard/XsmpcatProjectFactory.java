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
package org.eclipse.xsmp.ui.wizard;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xsmp.extension.IProfile;
import org.eclipse.xsmp.extension.ITool;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.util.PluginProjectFactory;

import com.google.common.collect.Lists;

public class XsmpcatProjectFactory extends PluginProjectFactory
{
  private IProfile profile = null;

  private List<ITool> tools = null;

  protected void addXsmpNatures()
  {
    getProjectNatures().add(XtextProjectHelper.NATURE_ID);
    getBuilderIds().add(XtextProjectHelper.BUILDER_ID);
  }

  protected void addCdtNatures()
  {
    getProjectNatures().add("org.eclipse.cdt.core.cnature");
    getProjectNatures().add("org.eclipse.cdt.core.ccnature");
  }

  public XsmpcatProjectFactory()
  {
    setWithPluginXml(false);

    addXsmpNatures();
    addCdtNatures();

    getFolders().add("smdl");
    setProjectDefaultCharset("UTF-8");

  }

  public XsmpcatProjectFactory setProfile(IProfile profile)
  {
    this.profile = profile;
    return this;
  }

  public List<ITool> getTools()
  {
    if (tools == null)
    {
      tools = Lists.newArrayList();
    }
    return tools;
  }

  @Override
  protected void enhanceProject(IProject project, SubMonitor subMonitor, Shell shell)
    throws CoreException
  {
    super.enhanceProject(project, subMonitor, shell);

    createProjectPreference(project, subMonitor.newChild(1));
  }

  protected void createProjectPreference(IProject project, IProgressMonitor progressMonitor)
  {

    final var content = new StringBuilder();

    content.append("/** XSMP Project configuration for ").append(project.getName()).append(" */\n");
    content.append("project \"").append(project.getName()).append("\"\n");
    content.append("\n");
    content.append("// project relative paths containing modeling file(s)\n");
    content.append("source \"smdl\"\n");
    if (profile != null)
    {
      content.append("\n");
      content.append("\n");
      content.append("// use ").append(profile.getDescription()).append("\n");
      content.append("profile \"" + profile.getId() + "\"\n");
    }

    if (tools != null)
    {
      for (final var tool : tools)
      {
        if (tool != null)
        {
          content.append("\n");
          content.append("\n");
          content.append("// use ").append(tool.getDescription()).append("\n");
          content.append("tool \"" + tool.getId() + "\"\n");
        }
      }
    }
    content.append("\n");
    content.append("\n");
    content.append("\n");

    content.append("// If your project needs types from outside sources,\n");
    content.append("// you can include them by adding project dependencies.\n");
    content.append("// For example: dependency \"otherProject\"\n");
    content.append("//              dependency \"otherProject2\"\n");

    final var subMonitor = SubMonitor.convert(progressMonitor, 2);
    try
    {
      createFile("xsmp.project", project, content.toString(), subMonitor.newChild(1));
    }
    finally
    {
      subMonitor.done();
    }
  }

  public void addLink(String name, IPath location)
  {
    addContributor((project, fileWriter) -> {
      final var newFolder = project.getFolder(name);
      try
      {
        newFolder.createLink(location, IResource.ALLOW_MISSING_LOCAL, new NullProgressMonitor());
      }
      catch (final CoreException e)
      {
        // ignore
      }
    });
  }
}
