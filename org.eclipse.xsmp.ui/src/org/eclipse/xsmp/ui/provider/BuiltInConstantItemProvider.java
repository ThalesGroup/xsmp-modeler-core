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

import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.xsmp.xcatalogue.BuiltInConstant;

import com.google.inject.Inject;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.xsmp.xcatalogue.BuiltInConstant} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 */
public class BuiltInConstantItemProvider extends BuiltInExpressionItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   */
  @Inject
  public BuiltInConstantItemProvider(XcatalogueItemProviderAdapterFactory adapterFactory)
  {
    super(adapterFactory);
  }

  /**
   * This returns the label styled text for the adapted class. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   */
  @Override
  public Object getStyledText(Object object)
  {
    final var label = ((BuiltInConstant) object).getName();
    final var styledLabel = new StyledString();
    if (label == null || label.length() == 0)
    {
      styledLabel.append(getString("_UI_BuiltInConstant_type"),
              StyledString.Style.QUALIFIER_STYLER);
    }
    else
    {
      styledLabel.append(getString("_UI_BuiltInConstant_type"), StyledString.Style.QUALIFIER_STYLER)
              .append(" " + label);
    }
    return styledLabel;
  }

}
