/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp;

import org.eclipse.xsmp.conversion.XsmpValueConverterService;
import org.eclipse.xsmp.documentation.XsmpEObjectDocumentationProvider;
import org.eclipse.xsmp.formatting2.XsmpcoreFormatter;
import org.eclipse.xsmp.generator.XsmpcatOutputConfigurationProvider;
import org.eclipse.xsmp.naming.XsmpQualifiedNameProvider;
import org.eclipse.xsmp.resource.XsmpcatResource;
import org.eclipse.xsmp.resource.XsmpcatResourceDescriptionStrategy;
import org.eclipse.xsmp.scoping.XsmpGlobalScopeProvider;
import org.eclipse.xsmp.scoping.XsmpImportedNamespaceScopeProvider;
import org.eclipse.xsmp.validation.XsmpcatDiagnosticConverter;
import org.eclipse.xsmp.validation.XsmpcatIssueCodesProvider;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider;
import org.eclipse.xtext.validation.IDiagnosticConverter;
import org.eclipse.xtext.workspace.IProjectConfigProvider;
import org.eclipse.xtext.workspace.ProjectConfigProvider;

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
            .to(XsmpImportedNamespaceScopeProvider.class);
  }

  @Override
  public Class< ? extends IValueConverterService> bindIValueConverterService()
  {
    return XsmpValueConverterService.class;
  }

  public Class< ? extends IIndentationInformation> bindIIndentationInformation()
  {
    return XsmpcoreFormatter.IndentationInformation.class;
  }

  /**
   * @return the IEObjectDocumentationProvider class
   */
  public Class< ? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider()
  {
    return XsmpEObjectDocumentationProvider.class;
  }

  @Override
  public Class< ? extends IQualifiedNameProvider> bindIQualifiedNameProvider()
  {
    return XsmpQualifiedNameProvider.class;
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

  public Class< ? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider()
  {
    return XsmpcatOutputConfigurationProvider.class;
  }

  @Override
  public Class< ? extends XtextResource> bindXtextResource()
  {
    return XsmpcatResource.class;
  }

  @Override
  public Class< ? extends IGlobalScopeProvider> bindIGlobalScopeProvider()
  {
    return XsmpGlobalScopeProvider.class;
  }

  public Class< ? extends IProjectConfigProvider> bindIProjectConfigProvider()
  {
    return ProjectConfigProvider.class;
  }
}
