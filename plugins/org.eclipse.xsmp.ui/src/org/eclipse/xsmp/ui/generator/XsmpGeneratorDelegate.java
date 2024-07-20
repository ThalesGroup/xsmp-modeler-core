/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ui.generator;

import java.util.function.Consumer;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.extension.IExtensionManager;
import org.eclipse.xsmp.ui.workspace.XsmpEclipseProjectConfigProvider;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IGeneratorContext;

import com.google.inject.Inject;

/**
 * A generator that retrieve active profile and tools and run the generators
 */
public class XsmpGeneratorDelegate extends GeneratorDelegate
{

  @Inject
  private IExtensionManager extensionManager;

  @Inject
  private XsmpEclipseProjectConfigProvider configurationProvider;

  private void apply(Resource input, Consumer<IGenerator2> action)
  {

    final var config = configurationProvider.getProjectConfig(input.getResourceSet());
    if (config != null)
    {
      final var generator = extensionManager.getInstanceForProfile(config.getProfile(),
              IGenerator2.class);
      if (generator != null)
      {
        action.accept(generator);
      }
      final var generators = extensionManager.getInstancesForTools(config.getTools(),
              IGenerator2.class);
      generators.forEach(action);

    }
  }

  @Override
  public void generate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
  {
    apply(input, generator -> {
      try
      {
        generator.beforeGenerate(input, fsa, context);
        generator.doGenerate(input, fsa, context);
      }
      finally
      {
        generator.afterGenerate(input, fsa, context);
      }
    });
  }

  @Override
  public void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
  {
    apply(input, generator -> generator.doGenerate(input, fsa, context));
  }

  @Override
  public void beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
  {
    apply(input, generator -> generator.beforeGenerate(input, fsa, context));
  }

  @Override
  public void afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
  {
    apply(input, generator -> generator.afterGenerate(input, fsa, context));
  }

}
