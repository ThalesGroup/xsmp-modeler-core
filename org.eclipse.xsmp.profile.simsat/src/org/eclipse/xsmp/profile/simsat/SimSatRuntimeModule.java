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
package org.eclipse.xsmp.profile.simsat;

import org.eclipse.xsmp.XsmpcatExtensionRuntimeModule;
import org.eclipse.xsmp.generator.cpp.CatalogueGenerator;
import org.eclipse.xsmp.generator.cpp.CppCopyrightNoticeProvider;
import org.eclipse.xsmp.generator.cpp.member.ContainerGenerator;
import org.eclipse.xsmp.generator.cpp.member.EntryPointGenerator;
import org.eclipse.xsmp.generator.cpp.member.EventSinkGenerator;
import org.eclipse.xsmp.generator.cpp.member.EventSourceGenerator;
import org.eclipse.xsmp.generator.cpp.member.ReferenceGenerator;
import org.eclipse.xsmp.generator.cpp.type.ArrayGenerator;
import org.eclipse.xsmp.generator.cpp.type.ComponentGenerator;
import org.eclipse.xsmp.generator.cpp.type.StringGenerator;
import org.eclipse.xsmp.profile.simsat.generator.SimSatGenerator;
import org.eclipse.xsmp.profile.simsat.generator.SimSatOutputConfigurationProvider;
import org.eclipse.xsmp.profile.simsat.generator.cpp.SimSatCatalogueGenerator;
import org.eclipse.xsmp.profile.simsat.generator.cpp.SimSatCopyrightProvider;
import org.eclipse.xsmp.profile.simsat.generator.cpp.member.SimSatContainerGenerator;
import org.eclipse.xsmp.profile.simsat.generator.cpp.member.SimSatEntryPointGenerator;
import org.eclipse.xsmp.profile.simsat.generator.cpp.member.SimSatEventSinkGenerator;
import org.eclipse.xsmp.profile.simsat.generator.cpp.member.SimSatEventSourceGenerator;
import org.eclipse.xsmp.profile.simsat.generator.cpp.member.SimSatReferenceGenerator;
import org.eclipse.xsmp.profile.simsat.generator.cpp.type.SimSatArrayGenerator;
import org.eclipse.xsmp.profile.simsat.generator.cpp.type.SimSatComponentGenerator;
import org.eclipse.xsmp.profile.simsat.generator.cpp.type.SimSatStringGenerator;
import org.eclipse.xsmp.profile.simsat.validation.SimSatValidator;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.service.SingletonBinding;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension
 * registry.
 */
public class SimSatRuntimeModule extends XsmpcatExtensionRuntimeModule
{

  @Override
  public Class< ? extends IGenerator2> bindIGenerator2()
  {
    return SimSatGenerator.class;
  }

  @SingletonBinding(eager = true)
  public Class< ? extends SimSatValidator> bindXsmpValidator()
  {
    return SimSatValidator.class;
  }

  @Override
  public Class< ? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider()
  {
    return SimSatOutputConfigurationProvider.class;
  }

  public Class< ? extends EntryPointGenerator> bindEntryPointGenerator()
  {
    return SimSatEntryPointGenerator.class;
  }

  public Class< ? extends EventSinkGenerator> bindEventSinkGenerator()
  {
    return SimSatEventSinkGenerator.class;
  }

  public Class< ? extends EventSourceGenerator> bindEventSourceGenerator()
  {
    return SimSatEventSourceGenerator.class;
  }

  public Class< ? extends ReferenceGenerator> bindReferenceGenerator()
  {
    return SimSatReferenceGenerator.class;
  }

  public Class< ? extends ContainerGenerator> bindConatinerGenerator()
  {
    return SimSatContainerGenerator.class;
  }

  public Class< ? extends ComponentGenerator> bindComponentGenerator()
  {
    return SimSatComponentGenerator.class;
  }

  public Class< ? extends CatalogueGenerator> bindCatalogueGenerator()
  {
    return SimSatCatalogueGenerator.class;
  }

  public Class< ? extends ArrayGenerator> bindArrayGenerator()
  {
    return SimSatArrayGenerator.class;
  }

  public Class< ? extends StringGenerator> bindStringGenerator()
  {
    return SimSatStringGenerator.class;
  }

  public Class< ? extends CppCopyrightNoticeProvider> bindCopyrightProvider()
  {
    return SimSatCopyrightProvider.class;
  }

}
