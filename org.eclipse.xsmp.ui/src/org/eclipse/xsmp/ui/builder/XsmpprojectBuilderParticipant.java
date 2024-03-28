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
package org.eclipse.xsmp.ui.builder;

import static org.eclipse.xtext.builder.impl.BuildManagerAccess.findBuilder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.xsmp.ui.workspace.XsmpEclipseProjectConfigProvider;
import org.eclipse.xtext.builder.BuilderParticipant;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.resource.IResourceDescription.Delta;

import com.google.inject.Inject;

public class XsmpprojectBuilderParticipant extends BuilderParticipant
{

  @Inject
  private XsmpEclipseProjectConfigProvider configProvider;

  boolean ignore = false;

  @SuppressWarnings("deprecation")
  @Override
  protected void handleChangedContents(Delta delta, IBuildContext context, IFileSystemAccess access)
    throws CoreException
  {

    if (!getResourceServiceProvider().canHandle(delta.getUri()))
    {
      return;
    }
    if (ignore)
    {
      ignore = false;
      return;
    }
    ignore = true;

    final var project = context.getBuiltProject();

    final var config = configProvider.createProjectConfig(context.getBuiltProject());
    final var referencedProjects = config.getDependencies().stream()
            .map(name -> ResourcesPlugin.getWorkspace().getRoot().getProject(name))
            .toArray(s -> new IProject[s]);

    final var description = project.getDescription();
    description.setDynamicReferences(referencedProjects);

    project.setDescription(description, new NullProgressMonitor());

    @SuppressWarnings("restriction")
    final var builder = findBuilder(project);
    if (builder != null)
    {
      builder.forgetLastBuiltState();
    }
    context.needRebuild(project);
  }

}
