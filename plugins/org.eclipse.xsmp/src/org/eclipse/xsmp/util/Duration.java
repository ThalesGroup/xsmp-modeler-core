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

public final class Duration extends Int64
{
  public static final Duration ZERO = new Duration(0);

  public static final Duration ONE = new Duration(1);

  public static final Duration MIN_VALUE = new Duration(Long.MIN_VALUE);

  public static final Duration MAX_VALUE = new Duration(Long.MAX_VALUE);

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.DURATION;
  }

  protected Duration(long value)
  {
    super(value);
  }

  public static Duration valueOf(long value)
  {
    return new Duration(value);
  }

  /**
   * Returns a hash code for this {@code Duration}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code int} value represented by this
   *         {@code Duration} object.
   */
  @Override
  public int hashCode()
  {
    return super.hashCode() + 2;
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code Duration} object that
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
    if (obj instanceof final Duration duration)
    {
      return value == duration.value;
    }
    return false;
  }
}
