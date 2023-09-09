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
package org.eclipse.xsmp.validation;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.service.IXsmpcatServiceProvider;

import com.google.inject.Inject;

/**
 * An Xsmpcat validator that is active only when the extension is enabled
 *
 * @author yannick
 */
public class AbstractXsmpcatContextValidator extends AbstractXsmpcatValidator
{
  private final String isResponsible = getClass().getCanonicalName() + ".isResponsible";

  @Inject
  private IXsmpcatServiceProvider xsmpcatServiceProvider;

  /**
   * Cache the result in the context map
   */
  @Override
  protected boolean isResponsible(Map<Object, Object> context, EObject eObject)
  {

    var responsible = context != null ? (Boolean) context.get(isResponsible) : null;
    if (responsible == null)
    {
      responsible = super.isResponsible(context, eObject)
              && xsmpcatServiceProvider.isEnabledFor(eObject.eResource());

      if (context != null)
      {
        context.put(isResponsible, responsible);
      }
    }
    return responsible;
  }

}
