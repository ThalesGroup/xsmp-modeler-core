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
package org.eclipse.xsmp.configuration;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class DefaultConfigurationProvider implements IConfigurationProvider
{

  @Inject
  Injector injector;

  @Override
  public <T> T getInstance(URI context, Class<T> clazz)
  {
    return injector.getInstance(clazz);
  }

  @Override
  public boolean isEnabledFor(Resource context)
  {
    return true;
  }

}
