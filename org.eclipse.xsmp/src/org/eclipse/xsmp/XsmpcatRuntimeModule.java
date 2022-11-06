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
package org.eclipse.xsmp;

import org.eclipse.xsmp.validation.XsmpcatValidator;
import org.eclipse.xtext.service.SingletonBinding;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension
 * registry.
 */
public class XsmpcatRuntimeModule extends XsmpcatExtensionRuntimeModule
{

  @SingletonBinding(eager = true)
  public Class< ? extends XsmpcatValidator> bindXsmpcatValidator()
  {
    return XsmpcatValidator.class;
  }

}
