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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.util.PluginProjectFactory;

public class XsmpcatProjectFactory extends PluginProjectFactory
{
  public XsmpcatProjectFactory()
  {
    setWithPluginXml(false);

    getProjectNatures().add(JavaCore.NATURE_ID);
    getProjectNatures().add("org.eclipse.pde.PluginNature");
    getProjectNatures().add(XtextProjectHelper.NATURE_ID);
    getProjectNatures().add("org.eclipse.cdt.core.cnature");
    getProjectNatures().add("org.eclipse.cdt.core.ccnature");
    getProjectNatures().add("org.eclipse.cdt.managedbuilder.core.managedBuildNature");
    getProjectNatures().add("org.eclipse.cdt.managedbuilder.core.ScannerConfigNature");

    getBuilderIds().add(JavaCore.BUILDER_ID);
    getBuilderIds().add(XtextProjectHelper.BUILDER_ID);
    getBuilderIds().add("org.eclipse.pde.ManifestBuilder");
    getBuilderIds().add("org.eclipse.cdt.managedbuilder.core.ScannerConfigBuilder");

    getRequiredBundles().add("org.eclipse.xsmp.lib");

    getFolders().add("smdl");
    getFolders().add("smdl-gen");

  }

  @Override
  protected void enhanceProject(IProject project, SubMonitor subMonitor, Shell shell)
    throws CoreException
  {
    super.enhanceProject(project, subMonitor, shell);
    createCproject(project, subMonitor.newChild(1));
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
