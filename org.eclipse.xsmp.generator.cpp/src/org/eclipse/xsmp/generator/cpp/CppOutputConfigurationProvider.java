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
package org.eclipse.xsmp.generator.cpp;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;

/**
 * Default configuration for output folders used in the generator
 */
public class CppOutputConfigurationProvider extends OutputConfigurationProvider
{
  public static final String SRC = "CPP-SRC";

  public static final String SRC_GEN = "CPP-SRC-GEN";

  public static final String INCLUDE = "CPP-INCLUDE";

  public static final String INCLUDE_GEN = "CPP-INCLUDE-GEN";

  @Override
  public Set<OutputConfiguration> getOutputConfigurations()
  {
    final var outputs = new HashSet<OutputConfiguration>();

    final var srcOutput = new OutputConfiguration(SRC);
    srcOutput.setDescription("Source Folder");
    srcOutput.setOutputDirectory("./src");
    srcOutput.setOverrideExistingResources(false);
    srcOutput.setCreateOutputDirectory(true);
    srcOutput.setCleanUpDerivedResources(false);
    srcOutput.setSetDerivedProperty(false);
    srcOutput.setKeepLocalHistory(true);

    final var includeOutput = new OutputConfiguration(INCLUDE);
    includeOutput.setDescription("Include Folder");
    includeOutput.setOutputDirectory("./src");
    includeOutput.setOverrideExistingResources(false);
    includeOutput.setCreateOutputDirectory(true);
    includeOutput.setCleanUpDerivedResources(false);
    includeOutput.setSetDerivedProperty(false);
    includeOutput.setKeepLocalHistory(true);

    final var srcGenOutput = new OutputConfiguration(SRC_GEN);
    srcGenOutput.setDescription("Generated Sources Folder");
    srcGenOutput.setOutputDirectory("./src-gen");
    srcGenOutput.setOverrideExistingResources(true);
    srcGenOutput.setCreateOutputDirectory(true);
    srcGenOutput.setCleanUpDerivedResources(true);
    srcGenOutput.setSetDerivedProperty(true);
    srcGenOutput.setKeepLocalHistory(true);
    srcGenOutput.setCanClearOutputDirectory(true);

    final var includeGenOutput = new OutputConfiguration(INCLUDE_GEN);
    includeGenOutput.setDescription("Generated Includes Folder");
    includeGenOutput.setOutputDirectory("./src-gen");
    includeGenOutput.setOverrideExistingResources(true);
    includeGenOutput.setCreateOutputDirectory(true);
    includeGenOutput.setCleanUpDerivedResources(true);
    includeGenOutput.setSetDerivedProperty(true);
    includeGenOutput.setKeepLocalHistory(true);
    includeGenOutput.setCanClearOutputDirectory(true);

    outputs.add(srcOutput);
    outputs.add(srcGenOutput);
    outputs.add(includeOutput);
    outputs.add(includeGenOutput);

    return outputs;
  }
}