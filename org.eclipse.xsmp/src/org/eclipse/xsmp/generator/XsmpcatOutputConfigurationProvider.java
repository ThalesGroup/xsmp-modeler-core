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
package org.eclipse.xsmp.generator;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;

/**
 * Default configuration for output folders used in the generator
 *
 * @author daveluy
 */
public class XsmpcatOutputConfigurationProvider extends OutputConfigurationProvider
{

  // @Inject(optional = true)
  // private IExtensionConfigurationProvider extensionProvider;

  @Override
  public Set<OutputConfiguration> getOutputConfigurations()
  {
    final var outputs = new HashSet<OutputConfiguration>();

    final var defaultOutput = new OutputConfiguration(IFileSystemAccess.DEFAULT_OUTPUT);
    defaultOutput.setDescription("Generated Model Folder");
    defaultOutput.setOutputDirectory("./smdl-gen");
    defaultOutput.setOverrideExistingResources(true);
    defaultOutput.setCreateOutputDirectory(true);
    defaultOutput.setCleanUpDerivedResources(true);
    defaultOutput.setSetDerivedProperty(true);
    defaultOutput.setKeepLocalHistory(true);
    defaultOutput.setCanClearOutputDirectory(true);

    outputs.add(defaultOutput);

    return outputs;
  }

  @Override
  public Set<OutputConfiguration> getOutputConfigurations(Resource context)
  {
    // extensionProvider.getOutputConfigurationProviders(context);

    return super.getOutputConfigurations(context);
  }

}