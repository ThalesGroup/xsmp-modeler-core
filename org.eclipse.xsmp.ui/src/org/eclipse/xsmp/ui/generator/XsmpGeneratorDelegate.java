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
package org.eclipse.xsmp.ui.generator;

import java.util.function.Consumer;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.ui.extension.ExtensionManager;
import org.eclipse.xsmp.ui.resource.XsmpProjectByResourceProvider;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * A generator that retrieve active profile and tools and run the generators
 */
public class XsmpGeneratorDelegate extends GeneratorDelegate
{

  @Inject
  @Named(Constants.LANGUAGE_NAME)
  private String languageName;

  @Inject
  private IPreferenceStoreAccess preferenceStoreAccess;

  @Inject
  private XsmpProjectByResourceProvider projectProvider;

  private void apply(Resource input, Consumer<IGenerator2> action)
  {
    final var project = projectProvider.getProjectContext(input);
    final var preferenceStore = preferenceStoreAccess.getContextPreferenceStore(project);

    final var profile = ExtensionManager.getProfile(preferenceStore);
    if (profile != null)
    {
      final var injector = profile.getInjector(languageName);
      final var generator = injector.getInstance(IGenerator2.class);

      action.accept(generator);
    }

    final var tools = ExtensionManager.getTools(preferenceStore);
    for (final var tool : tools)
    {
      final var injector = tool.getInjector(languageName);
      final var generator = injector.getInstance(IGenerator2.class);
      action.accept(generator);
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
