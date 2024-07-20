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

import java.util.Collections;
import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.xsmp.ui.workspace.XsmpEclipseProjectConfigProvider;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.builder.BuilderParticipant;
import org.eclipse.xtext.builder.impl.XtextBuilder;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.resource.IResourceDescription.Delta;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class XsmpprojectBuilderParticipant extends BuilderParticipant
{

  @Inject
  private XsmpEclipseProjectConfigProvider configProvider;

  private static final QualifiedName configKey = new QualifiedName(
          XsmpprojectBuilderParticipant.class.getName() + ".config", "config");

  @SuppressWarnings({"deprecation" })
  @Override
  protected void handleChangedContents(Delta delta, IBuildContext context, IFileSystemAccess access)
    throws CoreException
  {
    if (!getResourceServiceProvider().canHandle(delta.getUri()))
    {
      return;
    }
    final var resource = context.getResourceSet().getResource(delta.getUri(), true);
    saveResourceStorage(resource, access);

    final var project = context.getBuiltProject();

    final var oldConfig = (IXsmpProjectConfig) project.getSessionProperty(configKey);
    final var config = configProvider.createProjectConfig(project);
    // ignore changes that does not affect the config
    if (Objects.equals(config, oldConfig))
    {
      return;
    }

    project.setSessionProperty(configKey, config);

    // update project dependencies. It will trigger a rebuild of this project when a dependency
    // changes
    final var description = project.getDescription();
    description.setDynamicReferences(config.getDependencies().stream()
            .map(name -> ResourcesPlugin.getWorkspace().getRoot().getProject(name))
            .toArray(s -> new IProject[s]));

    final var pm = new NullProgressMonitor();
    project.setDescription(description, pm);

    // trigger a full build (xtext builder only)
    project.build(IncrementalProjectBuilder.FULL_BUILD, XtextBuilder.BUILDER_ID,
            Collections.emptyMap(), pm);
  }

}
