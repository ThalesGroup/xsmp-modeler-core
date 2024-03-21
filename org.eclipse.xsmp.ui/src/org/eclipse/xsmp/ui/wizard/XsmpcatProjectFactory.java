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
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.pde.core.project.IBundleProjectDescription;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.util.PluginProjectFactory;

import com.google.common.collect.Lists;

public class XsmpcatProjectFactory extends PluginProjectFactory
{
  private String profile = null;

  private List<String> tools = null;

  protected void addXsmpNatures()
  {
    getProjectNatures().add(JavaCore.NATURE_ID);
    getProjectNatures().add(IBundleProjectDescription.PLUGIN_NATURE);
    getProjectNatures().add(XtextProjectHelper.NATURE_ID);
    getBuilderIds().add(JavaCore.BUILDER_ID);
    getBuilderIds().add(XtextProjectHelper.BUILDER_ID);
    getBuilderIds().add("org.eclipse.pde.ManifestBuilder");
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

    getRequiredBundles().add("org.eclipse.xsmp.lib");

    getFolders().add("smdl");
    getFolders().add("smdl-gen");
    setProjectDefaultCharset("UTF-8");

  }

  public XsmpcatProjectFactory setProfile(String profile)
  {
    this.profile = profile;
    return this;
  }

  public List<String> getTools()
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
    createCproject(project, subMonitor.newChild(1));
    createProjectPreference(project, subMonitor.newChild(1));
  }

  protected void createProjectPreference(IProject project, IProgressMonitor progressMonitor)
    throws CoreException
  {

    if (profile == null && tools == null)
    {
      return;
    }

    final var content = new StringBuilder("eclipse.preferences.version=1\n");
    if (profile != null)
    {
      content.append("profile=" + profile + "\n");
    }

    if (tools != null)
    {
      content.append("tools=" + tools.stream().collect(Collectors.joining(",")) + "\n");
    }

    final var settings = project.getFolder(".settings");
    final var subMonitor = SubMonitor.convert(progressMonitor, 2);
    try
    {
      if (settings.exists())
      {
        settings.delete(false, progressMonitor);
      }
      settings.create(false, true, subMonitor.newChild(1));
      createFile("org.eclipse.xsmp.Xsmpcat.prefs", settings, content.toString(),
              subMonitor.newChild(1));
    }
    finally
    {
      subMonitor.done();
    }
  }

  protected void createCproject(IProject project, SubMonitor newChild)
  {
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
