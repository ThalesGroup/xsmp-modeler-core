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

import java.util.Set;

import org.eclipse.xsmp.generator.XsmpOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfiguration;

/**
 * Default configuration for output folders used in the generator
 *
 * @author daveluy
 */
public class SmpOutputConfigurationProvider extends XsmpOutputConfigurationProvider
{

  public static final String SMDL_GEN = "SMDL-GEN";

  @Override
  public Set<OutputConfiguration> getOutputConfigurations()
  {
    final var outputs = super.getOutputConfigurations();

    final var smdlOutout = new OutputConfiguration(SMDL_GEN);
    smdlOutout.setDescription("Generated Model Folder");
    smdlOutout.setOutputDirectory("./smdl-gen");
    smdlOutout.setOverrideExistingResources(true);
    smdlOutout.setCreateOutputDirectory(true);
    smdlOutout.setCleanUpDerivedResources(true);
    smdlOutout.setSetDerivedProperty(true);
    smdlOutout.setKeepLocalHistory(true);
    smdlOutout.setCanClearOutputDirectory(true);

    outputs.add(smdlOutout);

    return outputs;
  }

}