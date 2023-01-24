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
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

import com.google.inject.Singleton;

/**
 * Generates code from your model files on save. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@Singleton
public final class XsmpcatGenerator extends AbstractGenerator
{

  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess2 fsa,
          final IGeneratorContext context)
  {
    // nothing to do by default
  }

}