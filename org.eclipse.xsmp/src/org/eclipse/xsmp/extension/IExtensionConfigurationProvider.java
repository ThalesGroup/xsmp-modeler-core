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
package org.eclipse.xsmp.extension;

import java.util.Collection;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;

/**
 * @author daveluy
 */
public interface IExtensionConfigurationProvider
{
  /**
   * Get the additional Generators from the specified context
   *
   * @param context
   * @return the Collection of additional generators
   */
  Collection<IGenerator2> getGenerators(Resource context);

  /**
   * Get the additional configuration from the specified context
   *
   * @param context
   * @return the Collection of additional configurations
   */
  Collection<IOutputConfigurationProvider> getOutputConfigurationProviders(Resource context);

  /**
   * Get the additional EValidators from the specified context
   *
   * @param context
   * @return the Collection of additional validators
   */
  Collection<EValidator> getValidators(Resource context);
}
