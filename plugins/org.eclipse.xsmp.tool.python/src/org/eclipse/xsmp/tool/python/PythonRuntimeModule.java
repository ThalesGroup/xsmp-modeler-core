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
package org.eclipse.xsmp.tool.python;

import org.eclipse.xsmp.XsmpcatRuntimeModule;
import org.eclipse.xsmp.tool.python.generator.PythonGenerator;
import org.eclipse.xsmp.tool.python.generator.PythonOutputConfigurationProvider;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
public class PythonRuntimeModule extends XsmpcatRuntimeModule
{
  // @SingletonBinding(eager = true)
  // public Class< ? extends PythonValidator> bindXsmpSdkValidator()
  // {
  // return PythonValidator.class;
  // }

  @Override
  public Class< ? extends IGenerator2> bindIGenerator2()
  {
    return PythonGenerator.class;
  }

  @Override
  public Class< ? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider()
  {
    return PythonOutputConfigurationProvider.class;
  }
}
