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
package org.eclipse.xsmp.profile.xsmp;

import org.eclipse.xsmp.XsmpcatExtensionRuntimeModule;
import org.eclipse.xsmp.generator.XsmpcatGenerator;
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
import org.eclipse.xsmp.profile.xsmp.generator.XsmpGenerator;
import org.eclipse.xsmp.profile.xsmp.generator.XsmpOutputConfigurationProvider;
import org.eclipse.xsmp.profile.xsmp.generator.cpp.XsmpCatalogueGenerator;
import org.eclipse.xsmp.profile.xsmp.generator.cpp.XsmpCopyrightProvider;
import org.eclipse.xsmp.profile.xsmp.generator.cpp.member.XsmpContainerGenerator;
import org.eclipse.xsmp.profile.xsmp.generator.cpp.member.XsmpEntryPointGenerator;
import org.eclipse.xsmp.profile.xsmp.generator.cpp.member.XsmpEventSinkGenerator;
import org.eclipse.xsmp.profile.xsmp.generator.cpp.member.XsmpEventSourceGenerator;
import org.eclipse.xsmp.profile.xsmp.generator.cpp.member.XsmpFieldGenerator;
import org.eclipse.xsmp.profile.xsmp.generator.cpp.member.XsmpReferenceGenerator;
import org.eclipse.xsmp.profile.xsmp.generator.cpp.type.XsmpArrayGenerator;
import org.eclipse.xsmp.profile.xsmp.generator.cpp.type.XsmpComponentGenerator;
import org.eclipse.xsmp.profile.xsmp.generator.cpp.type.XsmpStringGenerator;
import org.eclipse.xsmp.profile.xsmp.generator.cpp.type.XsmpStructureGenerator;
import org.eclipse.xsmp.profile.xsmp.validation.XsmpValidator;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.service.SingletonBinding;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension
 * registry.
 */
public class XsmpRuntimeModule extends XsmpcatExtensionRuntimeModule
{

  @Override
  public void configureIGenerator2Delegate(Binder binder)
  {
    binder.bind(IGenerator2.class).annotatedWith(Names.named(XsmpcatGenerator.NAMED_DELEGATE))
            .to(XsmpGenerator.class);
  }

  @SingletonBinding(eager = true)
  public Class< ? extends XsmpValidator> bindXsmpValidator()
  {
    return XsmpValidator.class;
  }

  @Override
  public Class< ? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider()
  {
    return XsmpOutputConfigurationProvider.class;
  }

  public Class< ? extends EntryPointGenerator> bindEntryPointGenerator()
  {
    return XsmpEntryPointGenerator.class;
  }

  public Class< ? extends EventSinkGenerator> bindEventSinkGenerator()
  {
    return XsmpEventSinkGenerator.class;
  }

  public Class< ? extends EventSourceGenerator> bindEventSourceGenerator()
  {
    return XsmpEventSourceGenerator.class;
  }

  public Class< ? extends ReferenceGenerator> bindReferenceGenerator()
  {
    return XsmpReferenceGenerator.class;
  }

  public Class< ? extends ContainerGenerator> bindConatinerGenerator()
  {
    return XsmpContainerGenerator.class;
  }

  public Class< ? extends ComponentGenerator> bindComponentGenerator()
  {
    return XsmpComponentGenerator.class;
  }

  public Class< ? extends CatalogueGenerator> bindCatalogueGenerator()
  {
    return XsmpCatalogueGenerator.class;
  }

  public Class< ? extends ArrayGenerator> bindArrayGenerator()
  {
    return XsmpArrayGenerator.class;
  }

  public Class< ? extends StructureGenerator> bindStructureGenerator()
  {
    return XsmpStructureGenerator.class;
  }

  public Class< ? extends FieldGenerator> bindFieldGenerator()
  {
    return XsmpFieldGenerator.class;
  }

  public Class< ? extends StringGenerator> bindStringGenerator()
  {
    return XsmpStringGenerator.class;
  }

  public Class< ? extends CppCopyrightNoticeProvider> bindCopyrightProvider()
  {
    return XsmpCopyrightProvider.class;
  }

}
