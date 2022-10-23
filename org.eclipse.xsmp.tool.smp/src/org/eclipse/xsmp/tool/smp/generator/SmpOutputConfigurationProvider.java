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
package org.eclipse.xsmp.tool.smp.generator;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;

/**
 * Default configuration for output folders used in the generator
 *
 * @author daveluy
 */
public class SmpOutputConfigurationProvider extends OutputConfigurationProvider
{

  public static final String SMDL_GEN = "SMDL-GEN";

  @Override
  public Set<OutputConfiguration> getOutputConfigurations()
  {
    final var outputs = new HashSet<OutputConfiguration>();

    final var defaultOutput = new OutputConfiguration(SMDL_GEN);
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

}