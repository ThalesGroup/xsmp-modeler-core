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
package org.eclipse.xsmp;

import org.eclipse.xsmp.conversion.XsmpValueConverterService;
import org.eclipse.xsmp.documentation.XsmpEObjectDocumentationProvider;
import org.eclipse.xsmp.formatting2.XsmpcoreFormatter;
import org.eclipse.xsmp.naming.XsmpQualifiedNameProvider;
import org.eclipse.xsmp.resource.XsmpasbResource;
import org.eclipse.xsmp.scoping.XsmpImportedNamespaceScopeProvider;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IDerivedStateComputer;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension
 * registry.
 */
public class XsmpasbRuntimeModule extends AbstractXsmpasbRuntimeModule
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

  @Override
  public Class< ? extends IQualifiedNameProvider> bindIQualifiedNameProvider()
  {
    return XsmpQualifiedNameProvider.class;
  }

  public Class< ? extends IIndentationInformation> bindIIndentationInformation()
  {
    return XsmpcoreFormatter.IndentationInformation.class;
  }

  @Override
  public Class< ? extends XtextResource> bindXtextResource()
  {
    return XsmpasbResource.class;
  }

  public Class< ? extends IDerivedStateComputer> bindIDerivedStateComputer()
  {
    return XsmpasbResource.DerivedStateComputer.class;
  }

  public Class< ? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider()
  {
    return XsmpEObjectDocumentationProvider.class;
  }
}
