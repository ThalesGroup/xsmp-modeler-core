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
package org.eclipse.xsmp.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

/**
 * Provides labels for EObjects. See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#label-provider
 */
public class XsmpcatLabelProvider extends DefaultEObjectLabelProvider
{

  private final AdapterFactoryLabelProvider delegate;

  @Inject
  public XsmpcatLabelProvider(AdapterFactoryLabelProvider delegate)
  {
    super(delegate);
    this.delegate = delegate;
  }

  @Override
  public StyledString getStyledText(Object element)
  {
    StyledString styledText;
    if (delegate != null)
    {
      styledText = delegate.getStyledText(element);
      if (styledText != null)
      {
        return styledText;
      }
    }
    styledText = convertToStyledString(doGetText(element));
    if (styledText != null)
    {
      return styledText;
    }
    return getDefaultStyledText();
  }
}
