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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.ide.workspace.XsmpProjectConfigProvider;
import org.eclipse.xsmp.profile.esa_cdk.EsaCdkStandaloneSetup;
import org.eclipse.xsmp.profile.xsmp_sdk.XsmpSdkStandaloneSetup;
import org.eclipse.xsmp.tool.python.PythonStandaloneSetup;
import org.eclipse.xsmp.tool.smp.SmpStandaloneSetup;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IGeneratorContext;

import com.google.inject.Inject;

public class XsmpGenerator extends AbstractGenerator
{
  private final IGenerator2 smpGenerator;

  private final IGenerator2 esaCdkGenerator;

  private final IGenerator2 xsmpSdkGenerator;

  private final IGenerator2 pythonGenerator;

  public XsmpGenerator()
  {
    smpGenerator = new SmpStandaloneSetup().createInjector().getInstance(IGenerator2.class);
    esaCdkGenerator = new EsaCdkStandaloneSetup().createInjector().getInstance(IGenerator2.class);
    xsmpSdkGenerator = new XsmpSdkStandaloneSetup().createInjector().getInstance(IGenerator2.class);
    pythonGenerator = new PythonStandaloneSetup().createInjector().getInstance(IGenerator2.class);
  }

  @Inject
  private XsmpProjectConfigProvider configProvider;

  @Override
  public void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
  {

    final var config = configProvider.getProjectConfig(input.getResourceSet());
    if (config != null)
    {
      switch (config.getProfile())
      {
        case "esa-cdk":
          esaCdkGenerator.doGenerate(input, fsa, context);
          break;
        case "xsmp-sdk":
          xsmpSdkGenerator.doGenerate(input, fsa, context);
          break;
        default:
          break;
      }
      if (config.getTools().contains("smp"))
      {
        smpGenerator.doGenerate(input, fsa, context);
      }
      if (config.getTools().contains("python"))
      {
        pythonGenerator.doGenerate(input, fsa, context);
      }
    }
  }

}
