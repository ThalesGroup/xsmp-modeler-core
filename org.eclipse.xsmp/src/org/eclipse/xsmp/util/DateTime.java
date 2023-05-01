/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.util;

public final class DateTime extends Int64
{
  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.DATE_TIME;
  }

  protected DateTime(long value)
  {
    super(value);
  }

  public static DateTime valueOf(long value)
  {
    return new DateTime(value);
  }

  /**
   * Returns a hash code for this {@code DateTime}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code int} value represented by this
   *         {@code DateTime} object.
   */
  @Override
  public int hashCode()
  {
    return super.hashCode() + 1;
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code DateTime} object that
   * contains the same {@code long} value as this object.
   *
   * @param obj
   *          the object to compare with.
   * @return {@code true} if the objects are the same;
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof DateTime)
    {
      return value == ((DateTime) obj).value;
    }
    return false;
  }
}
