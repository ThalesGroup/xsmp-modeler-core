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

import com.google.inject.ImplementedBy;

/**
 * @author daveluy
 */
@ImplementedBy(DefaultConfigurationProvider.class)
public interface IConfigurationProvider
{
  /**
   * Get an instance of clazz<T> from the specified context
   *
   * @param <T>
   * @param context
   * @param clazz
   * @return the instance of type clazz from the specified context
   */
  <T> T getInstance(URI context, Class<T> clazz);

  /**
   * @param context
   *          the context resource
   * @return true if this Profile/Tool is enabled
   */
  boolean isEnabledFor(Resource context);

}
