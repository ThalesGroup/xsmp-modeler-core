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
package org.eclipse.xsmp.profile.esa_cdk_legacy.generator;

import java.util.Collections;
import java.util.Set;

import org.eclipse.xsmp.generator.XsmpOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfiguration;

/**
 * Default configuration for output folders used in the generator
 */
public class EsaCdkLegacyOutputConfigurationProvider extends XsmpOutputConfigurationProvider
{

  public static final String SRC = "CPP-SRC";

  @Override
  public Set<OutputConfiguration> getOutputConfigurations()
  {
    final var srcOutput = new OutputConfiguration(SRC);
    srcOutput.setDescription("Source Folder");
    srcOutput.setOutputDirectory("./src");
    srcOutput.setOverrideExistingResources(true);
    srcOutput.setCreateOutputDirectory(true);
    srcOutput.setCleanUpDerivedResources(false);
    srcOutput.setSetDerivedProperty(false);
    srcOutput.setKeepLocalHistory(true);

    return Collections.singleton(srcOutput);

  }
}