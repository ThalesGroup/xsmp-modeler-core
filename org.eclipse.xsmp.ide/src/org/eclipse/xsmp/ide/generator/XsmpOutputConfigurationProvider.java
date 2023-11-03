/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.generator;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xsmp.ide.workspace.XsmpProjectConfigProvider;
import org.eclipse.xsmp.profile.esa_cdk.EsaCdkStandaloneSetup;
import org.eclipse.xsmp.profile.xsmp_sdk.XsmpSdkStandaloneSetup;
import org.eclipse.xsmp.tool.python.PythonStandaloneSetup;
import org.eclipse.xsmp.tool.smp.SmpStandaloneSetup;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfiguration;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpOutputConfigurationProvider
        extends org.eclipse.xtext.generator.OutputConfigurationProvider
{
  @Inject
  private XsmpProjectConfigProvider configProvider;

  private final Set<OutputConfiguration> smpOutputConfigurations;

  private final Set<OutputConfiguration> esaCdkOutputConfigurations;

  private final Set<OutputConfiguration> xsmpSdkOutputConfigurations;

  private final Set<OutputConfiguration> pythonOutputConfigurations;

  public XsmpOutputConfigurationProvider()
  {

    smpOutputConfigurations = new SmpStandaloneSetup().createInjector()
            .getInstance(IOutputConfigurationProvider.class).getOutputConfigurations();
    esaCdkOutputConfigurations = new EsaCdkStandaloneSetup().createInjector()
            .getInstance(IOutputConfigurationProvider.class).getOutputConfigurations();
    xsmpSdkOutputConfigurations = new XsmpSdkStandaloneSetup().createInjector()
            .getInstance(IOutputConfigurationProvider.class).getOutputConfigurations();
    pythonOutputConfigurations = new PythonStandaloneSetup().createInjector()
            .getInstance(IOutputConfigurationProvider.class).getOutputConfigurations();
  }

  @Override
  public Set<OutputConfiguration> getOutputConfigurations(ResourceSet context)
  {

    final Set<OutputConfiguration> configurations = new HashSet<>();
    final var config = configProvider.getProjectConfig(context);

    if (config != null)
    {

      switch (config.getProfile())
      {
        case "esa-cdk":
          configurations.addAll(esaCdkOutputConfigurations);
          break;
        case "xsmp-sdk":
          configurations.addAll(xsmpSdkOutputConfigurations);
          break;
        default:
          break;
      }
      if (config.getTools().contains("smp"))
      {
        configurations.addAll(smpOutputConfigurations);
      }
      if (config.getTools().contains("python"))
      {
        configurations.addAll(pythonOutputConfigurations);
      }

    }
    return configurations;
  }
}
