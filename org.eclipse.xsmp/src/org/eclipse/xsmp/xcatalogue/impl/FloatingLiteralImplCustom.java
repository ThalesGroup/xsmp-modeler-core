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
package org.eclipse.xsmp.xcatalogue.impl;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public class FloatingLiteralImplCustom extends FloatingLiteralImpl
{

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal getValue()
  {

    if (text == null)
    {
      return null;
    }
    final var withoutSeparator = text.replace("'", "");

    if (withoutSeparator.endsWith("f") || withoutSeparator.endsWith("F"))
    {
      return new BigDecimal(withoutSeparator.substring(0, withoutSeparator.length() - 1),
              MathContext.DECIMAL32);
    }

    if (withoutSeparator.endsWith("l") || withoutSeparator.endsWith("L"))
    {
      return new BigDecimal(withoutSeparator.substring(0, withoutSeparator.length() - 1),
              MathContext.DECIMAL128);
    }
    return new BigDecimal(withoutSeparator, MathContext.DECIMAL64);

  }

} // FloatingLiteralImplCustom
