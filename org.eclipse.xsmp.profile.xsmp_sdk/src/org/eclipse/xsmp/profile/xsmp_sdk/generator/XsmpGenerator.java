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
package org.eclipse.xsmp.profile.xsmp_sdk.generator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.generator.cpp.CppGenerator;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Generates code from your model files on save. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@Singleton
public final class XsmpGenerator extends AbstractGenerator
{

  @Inject
  private CppGenerator generator;

  @Override
  public void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
  {
    generator.doGenerate(input, fsa, context);
  }
}