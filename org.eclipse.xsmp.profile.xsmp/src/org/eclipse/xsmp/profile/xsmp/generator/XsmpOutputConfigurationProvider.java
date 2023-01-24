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
package org.eclipse.xsmp.profile.xsmp.generator;

import java.util.Set;

import org.eclipse.xsmp.generator.XsmpcatOutputConfigurationProvider;
import org.eclipse.xsmp.generator.cpp.CppOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfiguration;

import com.google.inject.Inject;

/**
 * Default configuration for output folders used in the generator
 */
public class XsmpOutputConfigurationProvider extends XsmpcatOutputConfigurationProvider
{

  @Inject
  private CppOutputConfigurationProvider cppOutputConfigurationProvider;

  @Override
  public Set<OutputConfiguration> getOutputConfigurations()
  {
    return cppOutputConfigurationProvider.getOutputConfigurations();
  }
}