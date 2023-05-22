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
package org.eclipse.xsmp.profile.xsmp_sdk;

import org.eclipse.xsmp.XsmpcatExtensionRuntimeModule;
import org.eclipse.xsmp.generator.cpp.CatalogueGenerator;
import org.eclipse.xsmp.generator.cpp.CppCopyrightNoticeProvider;
import org.eclipse.xsmp.generator.cpp.GeneratorUtil;
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
import org.eclipse.xsmp.profile.xsmp_sdk.generator.XsmpSdkGenerator;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.XsmpSdkGeneratorExtension;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.XsmpSdkOutputConfigurationProvider;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.XsmpSdkCatalogueGenerator;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.XsmpSdkCopyrightProvider;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.member.XsmpSdkContainerGenerator;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.member.XsmpSdkEntryPointGenerator;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.member.XsmpSdkEventSinkGenerator;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.member.XsmpSdkEventSourceGenerator;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.member.XsmpSdkFieldGenerator;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.member.XsmpSdkReferenceGenerator;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.type.XsmpSdkArrayGenerator;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.type.XsmpSdkComponentGenerator;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.type.XsmpSdkStringGenerator;
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.type.XsmpSdkStructureGenerator;
import org.eclipse.xsmp.profile.xsmp_sdk.validation.XsmpSdkValidator;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.service.SingletonBinding;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension
 * registry.
 */
public class XsmpSdkRuntimeModule extends XsmpcatExtensionRuntimeModule
{
  @Override
  public Class< ? extends IGenerator2> bindIGenerator2()
  {
    return XsmpSdkGenerator.class;
  }

  @SingletonBinding(eager = true)
  public Class< ? extends XsmpSdkValidator> bindXsmpValidator()
  {
    return XsmpSdkValidator.class;
  }

  public Class< ? extends GeneratorUtil> bindGeneratorExtension()
  {
    return XsmpSdkGeneratorExtension.class;
  }

  @Override
  public Class< ? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider()
  {
    return XsmpSdkOutputConfigurationProvider.class;
  }

  public Class< ? extends EntryPointGenerator> bindEntryPointGenerator()
  {
    return XsmpSdkEntryPointGenerator.class;
  }

  public Class< ? extends EventSinkGenerator> bindEventSinkGenerator()
  {
    return XsmpSdkEventSinkGenerator.class;
  }

  public Class< ? extends EventSourceGenerator> bindEventSourceGenerator()
  {
    return XsmpSdkEventSourceGenerator.class;
  }

  public Class< ? extends ReferenceGenerator> bindReferenceGenerator()
  {
    return XsmpSdkReferenceGenerator.class;
  }

  public Class< ? extends ContainerGenerator> bindConatinerGenerator()
  {
    return XsmpSdkContainerGenerator.class;
  }

  public Class< ? extends ComponentGenerator> bindComponentGenerator()
  {
    return XsmpSdkComponentGenerator.class;
  }

  public Class< ? extends CatalogueGenerator> bindCatalogueGenerator()
  {
    return XsmpSdkCatalogueGenerator.class;
  }

  public Class< ? extends ArrayGenerator> bindArrayGenerator()
  {
    return XsmpSdkArrayGenerator.class;
  }

  public Class< ? extends StructureGenerator> bindStructureGenerator()
  {
    return XsmpSdkStructureGenerator.class;
  }

  public Class< ? extends FieldGenerator> bindFieldGenerator()
  {
    return XsmpSdkFieldGenerator.class;
  }

  public Class< ? extends StringGenerator> bindStringGenerator()
  {
    return XsmpSdkStringGenerator.class;
  }

  public Class< ? extends CppCopyrightNoticeProvider> bindCopyrightProvider()
  {
    return XsmpSdkCopyrightProvider.class;
  }

}
