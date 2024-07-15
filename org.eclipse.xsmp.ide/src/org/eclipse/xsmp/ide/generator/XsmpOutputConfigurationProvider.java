/*******************************************************************************
* Copyright (C) 2023-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.generator;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xsmp.extension.IExtensionManager;
import org.eclipse.xsmp.ide.workspace.XsmpProjectConfig;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.workspace.IProjectConfigProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpOutputConfigurationProvider extends OutputConfigurationProvider
{
  @Inject
  private IProjectConfigProvider configProvider;

  @Inject
  private IExtensionManager extensionManager;

  private final Map<String, Set<OutputConfiguration>> profileConfigurations = new HashMap<>();

  private final Map<String, Set<OutputConfiguration>> toolConfigurations = new HashMap<>();

  private Set<OutputConfiguration> getProfileOutputConfigurations(String profile)
  {
    if (profile == null || profile.isEmpty())
    {
      return Collections.emptySet();
    }
    return profileConfigurations.computeIfAbsent(profile, p -> {
      final var provider = extensionManager.getInstanceForProfile(p,
              IOutputConfigurationProvider.class);
      if (provider == null)
      {
        return Collections.emptySet();
      }
      return provider.getOutputConfigurations();
    });
  }

  private Set<OutputConfiguration> getToolOutputConfigurations(String tool)
  {
    if (tool == null || tool.isEmpty())
    {
      return Collections.emptySet();
    }
    return toolConfigurations.computeIfAbsent(tool, t -> {
      final var provider = extensionManager.getInstanceForTool(t,
              IOutputConfigurationProvider.class);
      if (provider == null)
      {
        return Collections.emptySet();
      }
      return provider.getOutputConfigurations();
    });
  }

  @Override
  public Set<OutputConfiguration> getOutputConfigurations(ResourceSet context)
  {

    final Set<OutputConfiguration> configurations = new HashSet<>();
    final var config = configProvider.getProjectConfig(context);

    if (config instanceof XsmpProjectConfig cfg)
    {
      configurations.addAll(getProfileOutputConfigurations(cfg.getProfile()));
      for (final var tool : cfg.getTools())
      {
        configurations.addAll(getToolOutputConfigurations(tool));
      }
    }
    return configurations;
  }
}
