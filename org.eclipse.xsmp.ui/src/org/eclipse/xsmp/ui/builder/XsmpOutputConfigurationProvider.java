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
import org.eclipse.xsmp.ui.extension.ExtensionManager;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.builder.EclipseOutputConfigurationProvider;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfiguration;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * A specialized OutputConfigurationProvider that retrieve output configurations from profile and
 * tools
 */
public class XsmpOutputConfigurationProvider extends EclipseOutputConfigurationProvider
{
  @Inject
  @Named(Constants.LANGUAGE_NAME)
  private String languageName;

  @Inject
  public XsmpOutputConfigurationProvider(IOutputConfigurationProvider delegate)
  {
    super(delegate);
  }

  @Override
  public Set<OutputConfiguration> getOutputConfigurations(IProject project)
  {
    final var preferenceStore = getPreferenceStoreAccess().getContextPreferenceStore(project);
    final var profile = ExtensionManager.getProfile(preferenceStore);
    final Set<OutputConfiguration> outputConfigurations = new LinkedHashSet<>();
    if (profile != null)
    {
      final var injector = profile.getInjector(languageName);
      final var provider = injector.getInstance(IOutputConfigurationProvider.class);
      outputConfigurations.addAll(provider.getOutputConfigurations());
    }

    final var tools = ExtensionManager.getTools(preferenceStore);
    for (final var tool : tools)
    {
      final var injector = tool.getInjector(languageName);
      final var provider = injector.getInstance(IOutputConfigurationProvider.class);
      outputConfigurations.addAll(provider.getOutputConfigurations());
    }
    return outputConfigurations;
  }
}
