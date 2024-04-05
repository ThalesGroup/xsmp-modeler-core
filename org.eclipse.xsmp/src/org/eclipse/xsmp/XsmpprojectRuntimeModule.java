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

import org.eclipse.xsmp.documentation.XsmpEObjectDocumentationProvider;
import org.eclipse.xsmp.generator.XsmpOutputConfigurationProvider;
import org.eclipse.xsmp.resource.XsmpprojectResourceDescriptionStrategy;
import org.eclipse.xsmp.resource.XsmpprojectResourceServiceProvider;
import org.eclipse.xsmp.scoping.XsmpprojectGlobalScopeProvider;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension
 * registry.
 */
public class XsmpprojectRuntimeModule extends AbstractXsmpprojectRuntimeModule
{

  public Class< ? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider()
  {
    return XsmpEObjectDocumentationProvider.class;
  }

  @Override
  public Class< ? extends IGlobalScopeProvider> bindIGlobalScopeProvider()
  {
    return XsmpprojectGlobalScopeProvider.class;
  }

  public Class< ? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy()
  {
    return XsmpprojectResourceDescriptionStrategy.class;
  }

  public Class< ? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider()
  {
    return XsmpOutputConfigurationProvider.class;
  }

  public Class< ? extends IResourceServiceProvider> bindIResourceServiceProvider()
  {
    return XsmpprojectResourceServiceProvider.class;
  }
}
