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
package org.eclipse.xsmp.ui.provider;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a {@link org.eclipse.xsmp.xcatalogue.ValueType}
 * object.
 */
public class ValueTypeItemProvider extends LanguageTypeItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   */
  @Inject
  public ValueTypeItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
  {
    super(adapterFactory);
  }

}
