/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.tool.adoc;

import org.eclipse.xsmp.XsmpcatRuntimeModule;
import org.eclipse.xsmp.tool.adoc.generator.ADocGenerator;
import org.eclipse.xsmp.tool.adoc.generator.ADocOutputConfigurationProvider;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
public class ADocRuntimeModule extends XsmpcatRuntimeModule
{

  @Override
  public Class< ? extends IGenerator2> bindIGenerator2()
  {
    return ADocGenerator.class;
  }

  @Override
  public Class< ? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider()
  {
    return ADocOutputConfigurationProvider.class;
  }
}
