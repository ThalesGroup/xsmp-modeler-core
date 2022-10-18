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
package org.eclipse.xsmp;

import org.eclipse.xsmp.conversion.ValueConverterService;
import org.eclipse.xsmp.documentation.XsmpcatEObjectDocumentationProvider;
import org.eclipse.xsmp.generator.XsmpcatGenerator;
import org.eclipse.xsmp.generator.XsmpcatOutputConfigurationProvider;
import org.eclipse.xsmp.naming.QualifiedNameProvider;
import org.eclipse.xsmp.resource.XsmpcatResourceDescriptionStrategy;
import org.eclipse.xsmp.scoping.XsmpcatImportedNamespaceScopeProvider;
import org.eclipse.xsmp.validation.DefaultValidator;
import org.eclipse.xsmp.validation.XsmpcatDiagnosticConverter;
import org.eclipse.xsmp.validation.XsmpcatIssueCodesProvider;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.validation.CompositeEValidator;
import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider;
import org.eclipse.xtext.validation.IDiagnosticConverter;
import org.eclipse.xtext.validation.impl.ConcreteSyntaxEValidator;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension
 * registry.
 */
public class XsmpcatRuntimeModule extends AbstractXsmpcatRuntimeModule
{
  @Override
  public void configureIScopeProviderDelegate(Binder binder)
  {
    binder.bind(IScopeProvider.class)
            .annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE))
            .to(XsmpcatImportedNamespaceScopeProvider.class);
  }

  public void configureValidators(Binder binder)
  {
    binder.bind(Boolean.class).annotatedWith(Names.named(CompositeEValidator.USE_EOBJECT_VALIDATOR))
            .toInstance(true);
  }

  @Override
  public Class< ? extends IGenerator2> bindIGenerator2()
  {
    return XsmpcatGenerator.class;
  }

  /**
   * @return the ConcreteSyntaxEValidator class
   */
  @SingletonBinding(eager = true)
  public Class< ? extends ConcreteSyntaxEValidator> bindConcreteSyntaxEValidator()
  {
    return ConcreteSyntaxEValidator.class;
  }

  @Override
  public Class< ? extends IValueConverterService> bindIValueConverterService()
  {
    return ValueConverterService.class;
  }

  /**
   * @return the IEObjectDocumentationProvider class
   */
  public Class< ? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider()
  {
    return XsmpcatEObjectDocumentationProvider.class;
  }

  /**
   * @return the IOutputConfigurationProvider class
   */
  public Class< ? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider()
  {
    return XsmpcatOutputConfigurationProvider.class;
  }

  @Override
  public Class< ? extends IQualifiedNameProvider> bindIQualifiedNameProvider()
  {
    return QualifiedNameProvider.class;
  }

  /**
   * @return the IDefaultResourceDescriptionStrategy class
   */
  public Class< ? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy()
  {
    return XsmpcatResourceDescriptionStrategy.class;
  }

  /**
   * @return the IDiagnosticConverter class
   */
  public Class< ? extends IDiagnosticConverter> bindIDiagnosticConverter()
  {
    return XsmpcatDiagnosticConverter.class;
  }

  public Class< ? extends ConfigurableIssueCodesProvider> bindConfigurableIssueCodesProvider()
  {
    return XsmpcatIssueCodesProvider.class;
  }

  /**
   * @return the Xsmpcat DefaultValidator class
   */
  public Class< ? extends DefaultValidator> bindEValidator()
  {
    return DefaultValidator.class;
  }
}
