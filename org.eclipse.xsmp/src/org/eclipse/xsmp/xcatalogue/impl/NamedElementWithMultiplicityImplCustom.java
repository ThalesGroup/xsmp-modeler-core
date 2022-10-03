/**
 * *******************************************************************************
 * * Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
 * *
 * * All rights reserved. This program and the accompanying materials
 * * are made available under the terms of the Eclipse Public License 2.0
 * * which accompanies this distribution, and is available at
 * * https://www.eclipse.org/legal/epl-2.0/
 * *
 * * SPDX-License-Identifier: EPL-2.0
 * ******************************************************************************
 */
package org.eclipse.xsmp.xcatalogue.impl;

/**
 * @author daveluy
 */
public class NamedElementWithMultiplicityImplCustom extends NamedElementWithMultiplicityImpl
{
  /*
   * long parseLong(String text)
   * {
   * var withoutUnderscore = text.replace("'", "");
   * // remove trailing suffix
   * if (withoutUnderscore.endsWith("ll") || withoutUnderscore.endsWith("LL"))
   * {
   * withoutUnderscore = withoutUnderscore.substring(0, withoutUnderscore.length() - 2);
   * }
   * else if (withoutUnderscore.endsWith("l") || withoutUnderscore.endsWith("L"))
   * {
   * withoutUnderscore = withoutUnderscore.substring(0, withoutUnderscore.length() - 1);
   * }
   * if (withoutUnderscore.endsWith("u") || withoutUnderscore.endsWith("U"))
   * {
   * withoutUnderscore = withoutUnderscore.substring(0, withoutUnderscore.length() - 1);
   * }
   * if (withoutUnderscore.startsWith("0x") || withoutUnderscore.startsWith("0X"))
   * {
   * return new BigInteger(withoutUnderscore.substring(2), 16).longValue();
   * }
   * if (withoutUnderscore.startsWith("0b") || withoutUnderscore.startsWith("0B"))
   * {
   * return new BigInteger(withoutUnderscore.substring(2), 2).longValue();
   * }
   * if (withoutUnderscore.startsWith("0"))
   * {
   * return new BigInteger(withoutUnderscore, 8).longValue();
   * }
   * return new BigInteger(withoutUnderscore).longValue();
   * }
   */

  /**
   * {@inheritDoc}
   */
  @Override
  public long getLower()
  {
    if (optional)
    {
      return 0;
    }
    if (multiplicity == null)
    {
      return 1;
    }
    if (multiplicity.getLower() == null && multiplicity.getUpper() == null)
    {
      return multiplicity.isAux() ? 1 : 0;
    }
    return multiplicity.getLower().getInteger(null).longValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getUpper()
  {
    if (optional || multiplicity == null)
    {
      return 1;
    }
    if (multiplicity.getLower() == null && multiplicity.getUpper() == null)
    {
      return -1;
    }
    if (multiplicity.getUpper() == null)
    {
      return multiplicity.isAux() ? -1 : multiplicity.getLower().getInteger(null).longValue();
    }
    return multiplicity.getUpper().getInteger(null).longValue();
  }
  /*
   * private void set(long lower, long upper)
   * {
   * if (upper == 1)
   * {
   * if (lower == 0)
   * {
   * // --> ?
   * setMultiplicity(null);
   * setOptional(true);
   * }
   * else
   * {
   * if (lower == 1)
   * {
   * // --> empty
   * setMultiplicity(null);
   * }
   * setOptional(false);
   * }
   * }
   * else if (upper == -1)
   * {
   * if (lower == 0 || lower == 1)
   * {
   * // --> [*] or []
   * }
   * else
   * {
   * // --> [lower ... *]
   * }
   * }
   * else if (upper == lower)
   * {
   * // --> [lower]
   * }
   * else
   * {
   * // --> [lower ... upper]
   * }
   * throw new UnsupportedOperationException();
   * }
   */

} // NamedElementWithMultiplicityImplCustom
