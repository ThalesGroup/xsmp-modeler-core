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
package org.eclipse.xsmp.tool.python.generator;

import java.util.Set;

import org.eclipse.xsmp.generator.XsmpcatOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfiguration;

/**
 * Default configuration for output folders used in the generator
 *
 */
public class PythonOutputConfigurationProvider extends XsmpcatOutputConfigurationProvider {

	public static final String PYTHON = "PYTHON";

	@Override
	public Set<OutputConfiguration> getOutputConfigurations() {
		final var outputs = super.getOutputConfigurations();

		final var pythonOutputConfiguration = new OutputConfiguration(PYTHON);
		pythonOutputConfiguration.setDescription("Generated Python Source Folder");
		pythonOutputConfiguration.setOutputDirectory("./python");
		pythonOutputConfiguration.setOverrideExistingResources(true);
		pythonOutputConfiguration.setCreateOutputDirectory(true);
		pythonOutputConfiguration.setCleanUpDerivedResources(true);
		pythonOutputConfiguration.setSetDerivedProperty(true);
		pythonOutputConfiguration.setKeepLocalHistory(false);

		outputs.add(pythonOutputConfiguration);

		return outputs;
	}

}