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

import java.math.BigInteger;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public class IntegerLiteralImplCustom extends IntegerLiteralImpl
{

  /**
   * {@inheritDoc}
   */
  @Override
  public BigInteger getValue()
  {
    if (text == null)
    {
      return null;
    }
    var withoutSeparator = text.replace("'", "");

    // remove trailing suffix
    if (withoutSeparator.endsWith("ll") || withoutSeparator.endsWith("LL"))
    {
      withoutSeparator = withoutSeparator.substring(0, withoutSeparator.length() - 2);
    }
    else if (withoutSeparator.endsWith("l") || withoutSeparator.endsWith("L"))
    {
      withoutSeparator = withoutSeparator.substring(0, withoutSeparator.length() - 1);
    }
    if (withoutSeparator.endsWith("u") || withoutSeparator.endsWith("U"))
    {
      withoutSeparator = withoutSeparator.substring(0, withoutSeparator.length() - 1);
    }

    if (withoutSeparator.startsWith("0x") || withoutSeparator.startsWith("0X"))
    {
      return new BigInteger(withoutSeparator.substring(2), 16);
    }
    if (withoutSeparator.startsWith("0b") || withoutSeparator.startsWith("0B"))
    {
      return new BigInteger(withoutSeparator.substring(2), 2);
    }
    if (withoutSeparator.startsWith("0"))
    {
      return new BigInteger(withoutSeparator, 8);
    }
    return new BigInteger(withoutSeparator);

  }

} // IntegerLiteralImplCustom
