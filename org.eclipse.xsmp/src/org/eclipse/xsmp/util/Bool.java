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

import java.math.BigDecimal;

public final class Bool extends AbstractPrimitiveType<Bool>
{
  private final boolean value;

  public static final Bool FALSE = new Bool(false);

  public static final Bool TRUE = new Bool(true);

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.BOOL;
  }

  private Bool(boolean value)
  {
    this.value = value;
  }

  public static Bool valueOf(boolean value)
  {
    return value ? TRUE : FALSE;
  }

  @Override
  public String toString()
  {
    return Boolean.toString(value);
  }

  public boolean getValue()
  {
    return value;
  }

  public static Bool logicalOr(Bool left, Bool right)
  {
    return Bool.valueOf(Boolean.logicalOr(left.value, right.value));
  }

  public static Bool logicalAnd(Bool left, Bool right)
  {
    return Bool.valueOf(Boolean.logicalAnd(left.value, right.value));
  }

  @Override
  public Bool boolValue()
  {
    return this;
  }

  @Override
  public Char8 char8Value()
  {
    return Char8.valueOf((char) (value ? 1 : 0));
  }

  @Override
  public Float32 float32Value()
  {
    return Float32.valueOf(value ? 1.f : 0.f);
  }

  @Override
  public Float64 float64Value()
  {
    return Float64.valueOf(value ? 1. : 0.);
  }

  @Override
  public Int8 int8Value()
  {
    return Int8.valueOf((byte) (value ? 1 : 0));
  }

  @Override
  public Int16 int16Value()
  {
    return Int16.valueOf((short) (value ? 1 : 0));
  }

  @Override
  public Int32 int32Value()
  {
    return Int32.valueOf(value ? 1 : 0);
  }

  @Override
  public Int64 int64Value()
  {
    return Int64.valueOf(value ? 1L : 0L);
  }

  @Override
  public UInt8 uint8Value()
  {
    return UInt8.valueOf((byte) (value ? 1 : 0));
  }

  @Override
  public UInt16 uint16Value()
  {
    return UInt16.valueOf((short) (value ? 1 : 0));
  }

  @Override
  public UInt32 uint32Value()
  {
    return UInt32.valueOf(value ? 1 : 0);
  }

  @Override
  public UInt64 uint64Value()
  {
    return UInt64.valueOf(value ? 1L : 0L);
  }

  /**
   * Returns a hash code for this {@code Bool}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code int} value represented by this
   *         {@code Bool} object.
   */
  @Override
  public int hashCode()
  {
    return Boolean.hashCode(value);
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code Bool} object that
   * contains the same {@code boolean} value as this object.
   *
   * @param obj
   *          the object to compare with.
   * @return {@code true} if the objects are the same;
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Bool)
    {
      return value == ((Bool) obj).value;
    }
    return false;
  }

  @Override
  public BigDecimal bigDecimalValue()
  {
    return value ? BigDecimal.ONE : BigDecimal.ZERO;
  }
}