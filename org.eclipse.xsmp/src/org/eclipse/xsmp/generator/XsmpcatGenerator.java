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
package org.eclipse.xsmp.generator;

import java.util.function.Consumer;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.extension.IExtensionConfigurationProvider;
import org.eclipse.xsmp.mdk.IMdkConfigurationProvider;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IGeneratorContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Generates code from your model files on save. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@Singleton
public final class XsmpcatGenerator extends AbstractGenerator
{

  @Inject(optional = true)
  private IExtensionConfigurationProvider extensionProvider;

  @Inject(optional = true)
  private IMdkConfigurationProvider mdkProvider;

  private void apply(final Resource input, Consumer<IGenerator2> consumer)
  {
    if (mdkProvider != null)
    {
      final var generator = mdkProvider.getInstance(input, IGenerator2.class);
      if (generator != null)
      {
        consumer.accept(generator);
      }
    }
    if (extensionProvider != null)
    {
      extensionProvider.getGenerators(input).forEach(consumer::accept);
    }
  }

  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa,
          final IGeneratorContext context)
  {
    apply(resource, g -> g.doGenerate(resource, fsa, context));
  }

  @Override
  public void beforeGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
  {
    apply(input, g -> g.beforeGenerate(input, fsa, context));
  }

  @Override
  public void afterGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
  {
    apply(input, g -> g.afterGenerate(input, fsa, context));
  }

}