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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.configuration.IConfigurationProvider;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IGeneratorContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Generates code from your model files on save. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@Singleton
public final class XsmpcatGenerator extends AbstractGenerator
{

  public static final String NAMED_DELEGATE = "org.eclipse.xsmp.generator.XsmpcatGenerator.delegate";

  @Inject
  @Named(NAMED_DELEGATE)
  private IGenerator2 delegate;

  @Inject
  private IConfigurationProvider configurationProvider;

  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa,
          final IGeneratorContext context)
  {
    if (configurationProvider.isEnabledFor(resource))
    {
      delegate.doGenerate(resource, fsa, context);
    }
  }

  public static class NullGenerator extends AbstractGenerator
  {

    @Override
    public void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
    {
      // do nothing
    }

  }
}