/*******************************************************************************
* Copyright (C) 2023-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.generator;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.extension.IExtensionManager;
import org.eclipse.xsmp.ide.workspace.XsmpProjectConfigProvider;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IGeneratorContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpGeneratorDelegate extends GeneratorDelegate
{

  @Inject
  private IExtensionManager extensionManager;

  @Inject
  private XsmpProjectConfigProvider configProvider;

  private final Map<String, IGenerator2> profileGenerators = new HashMap<>();

  private final Map<String, IGenerator2> toolGenerators = new HashMap<>();

  private static class EmptyGenerator extends AbstractGenerator
  {
    @Override
    public void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
    {
      // nothing to do
    }

    private static final EmptyGenerator INSTANCE = new EmptyGenerator();
  }

  private IGenerator2 getProfileGenerator(String profile)
  {
    if (profile == null || profile.isEmpty())
    {
      return EmptyGenerator.INSTANCE;
    }
    return profileGenerators.computeIfAbsent(profile, p -> {

      final var generator = extensionManager.getInstanceForProfile(p, IGenerator2.class);
      if (generator == null)
      {
        return EmptyGenerator.INSTANCE;
      }
      return generator;
    });
  }

  private IGenerator2 getToolGenerator(String tool)
  {
    if (tool == null || tool.isEmpty())
    {
      return EmptyGenerator.INSTANCE;
    }
    return toolGenerators.computeIfAbsent(tool, t -> {

      final var generator = extensionManager.getInstanceForTool(t, IGenerator2.class);

      if (generator == null)
      {
        return EmptyGenerator.INSTANCE;
      }
      return generator;
    });
  }

  @Override
  public void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
  {
    final var config = configProvider.getProjectConfig(input.getResourceSet());
    if (config != null)
    {
      getProfileGenerator(config.getProfile()).doGenerate(input, fsa, context);
      config.getTools().forEach(tool -> getToolGenerator(tool).doGenerate(input, fsa, context));
    }
  }

}
