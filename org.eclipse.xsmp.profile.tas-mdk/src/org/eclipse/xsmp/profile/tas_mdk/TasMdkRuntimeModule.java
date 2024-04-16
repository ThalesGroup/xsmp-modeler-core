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
package org.eclipse.xsmp.profile.tas_mdk;

import org.eclipse.xsmp.XsmpcatRuntimeModule;
import org.eclipse.xsmp.generator.cpp.CatalogueGenerator;
import org.eclipse.xsmp.generator.cpp.CppCopyrightNoticeProvider;
import org.eclipse.xsmp.generator.cpp.member.ContainerGenerator;
import org.eclipse.xsmp.generator.cpp.member.EntryPointGenerator;
import org.eclipse.xsmp.generator.cpp.member.EventSinkGenerator;
import org.eclipse.xsmp.generator.cpp.member.EventSourceGenerator;
import org.eclipse.xsmp.generator.cpp.member.FieldGenerator;
import org.eclipse.xsmp.generator.cpp.member.ReferenceGenerator;
import org.eclipse.xsmp.generator.cpp.type.ArrayGenerator;
import org.eclipse.xsmp.generator.cpp.type.ComponentGenerator;
import org.eclipse.xsmp.generator.cpp.type.StringGenerator;
import org.eclipse.xsmp.generator.cpp.type.StructureGenerator;
import org.eclipse.xsmp.profile.tas_mdk.generator.TasMdkGenerator;
import org.eclipse.xsmp.profile.tas_mdk.generator.TasMdkOutputConfigurationProvider;
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.TasMdkCatalogueGenerator;
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.TasMdkCopyrightProvider;
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.member.TasMdkContainerGenerator;
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.member.TasMdkEntryPointGenerator;
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.member.TasMdkEventSinkGenerator;
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.member.TasMdkEventSourceGenerator;
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.member.TasMdkFieldGenerator;
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.member.TasMdkReferenceGenerator;
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.type.TasMdkArrayGenerator;
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.type.TasMdkComponentGenerator;
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.type.TasMdkStringGenerator;
import org.eclipse.xsmp.profile.tas_mdk.generator.cpp.type.TasMdkStructureGenerator;
import org.eclipse.xsmp.profile.tas_mdk.validation.TasMdkIssueCodes;
import org.eclipse.xsmp.profile.tas_mdk.validation.TasMdkValidator;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension
 * registry.
 */
public class TasMdkRuntimeModule extends XsmpcatRuntimeModule
{
  @Override
  public Class< ? extends IGenerator2> bindIGenerator2()
  {
    return TasMdkGenerator.class;
  }

  @SingletonBinding(eager = true)
  public Class< ? extends TasMdkValidator> bindTasMdkValidator()
  {
    return TasMdkValidator.class;
  }

  @Override
  public Class< ? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider()
  {
    return TasMdkOutputConfigurationProvider.class;
  }

  @Override
  public Class< ? extends ConfigurableIssueCodesProvider> bindConfigurableIssueCodesProvider()
  {
    return TasMdkIssueCodes.class;
  }

  public Class< ? extends EntryPointGenerator> bindEntryPointGenerator()
  {
    return TasMdkEntryPointGenerator.class;
  }

  public Class< ? extends EventSinkGenerator> bindEventSinkGenerator()
  {
    return TasMdkEventSinkGenerator.class;
  }

  public Class< ? extends EventSourceGenerator> bindEventSourceGenerator()
  {
    return TasMdkEventSourceGenerator.class;
  }

  public Class< ? extends FieldGenerator> bindFieldGenerator()
  {
    return TasMdkFieldGenerator.class;
  }

  public Class< ? extends ReferenceGenerator> bindReferenceGenerator()
  {
    return TasMdkReferenceGenerator.class;
  }

  public Class< ? extends ContainerGenerator> bindConatinerGenerator()
  {
    return TasMdkContainerGenerator.class;
  }

  public Class< ? extends ArrayGenerator> bindArrayGenerator()
  {
    return TasMdkArrayGenerator.class;
  }

  public Class< ? extends ComponentGenerator> bindComponentGenerator()
  {
    return TasMdkComponentGenerator.class;
  }

  public Class< ? extends StringGenerator> bindStringGenerator()
  {
    return TasMdkStringGenerator.class;
  }

  public Class< ? extends StructureGenerator> bindStructureGenerator()
  {
    return TasMdkStructureGenerator.class;
  }

  public Class< ? extends CppCopyrightNoticeProvider> bindCopyrightProvider()
  {
    return TasMdkCopyrightProvider.class;
  }

  public Class< ? extends CatalogueGenerator> bindCatalogueGenerator()
  {
    return TasMdkCatalogueGenerator.class;
  }
}
