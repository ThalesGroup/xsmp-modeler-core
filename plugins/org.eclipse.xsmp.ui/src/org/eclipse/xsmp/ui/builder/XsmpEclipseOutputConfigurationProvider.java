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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.xsmp.extension.IExtensionManager;
import org.eclipse.xsmp.ui.workspace.XsmpEclipseProjectConfigProvider;
import org.eclipse.xtext.builder.EclipseOutputConfigurationProvider;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfiguration;

import com.google.inject.Inject;

/**
 * A specialized OutputConfigurationProvider that retrieve output configurations from profile and
 * tools
 */
public class XsmpEclipseOutputConfigurationProvider extends EclipseOutputConfigurationProvider
{

  private final OutputConfiguration defaultOutput;

  @Inject
  public XsmpEclipseOutputConfigurationProvider(IOutputConfigurationProvider delegate)
  {
    super(delegate);
    defaultOutput = new OutputConfiguration(IFileSystemAccess.DEFAULT_OUTPUT);
    defaultOutput.setDescription("Default output");
    defaultOutput.setOutputDirectory("./....tmp");
  }

  @Inject
  private XsmpEclipseProjectConfigProvider configurationProvider;

  @Inject
  private IExtensionManager extensionManager;

  @Override
  public Set<OutputConfiguration> getOutputConfigurations(IProject project)
  {
    final Set<OutputConfiguration> outputConfigurations = new LinkedHashSet<>();
    outputConfigurations.add(defaultOutput);

    final var config = configurationProvider.createProjectConfig(project);
    if (config != null)
    {
      final var provider = extensionManager.getInstanceForProfile(config.getProfile(),
              IOutputConfigurationProvider.class);
      if (provider != null)
      {
        outputConfigurations.addAll(provider.getOutputConfigurations());
      }
      final var providers = extensionManager.getInstancesForTools(config.getTools(),
              IOutputConfigurationProvider.class);
      providers.forEach(p -> outputConfigurations.addAll(p.getOutputConfigurations()));
    }
    return outputConfigurations;
  }
}
