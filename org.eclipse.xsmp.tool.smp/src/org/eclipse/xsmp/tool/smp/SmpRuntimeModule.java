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
package org.eclipse.xsmp.tool.smp;

import org.eclipse.xsmp.XsmpcatExtensionRuntimeModule;
import org.eclipse.xsmp.tool.smp.generator.SmpGenerator;
import org.eclipse.xsmp.tool.smp.generator.SmpOutputConfigurationProvider;
import org.eclipse.xsmp.tool.smp.validation.SmpValidator;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.service.SingletonBinding;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension
 * registry.
 */
public class SmpRuntimeModule extends XsmpcatExtensionRuntimeModule
{
  @Override
  public Class< ? extends IGenerator2> bindIGenerator2()
  {
    return SmpGenerator.class;
  }

  @SingletonBinding(eager = true)
  public Class< ? extends SmpValidator> bindSmpValidator()
  {
    return SmpValidator.class;
  }

  @Override
  public Class< ? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider()
  {
    return SmpOutputConfigurationProvider.class;
  }
}
