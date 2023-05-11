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

public final class Int16 extends AbstractPrimitiveType<Int16>
{
  public static final Int16 ZERO = new Int16((short) 0);

  public static final Int16 ONE = new Int16((short) 1);

  public static final Int16 MIN_VALUE = new Int16(Short.MIN_VALUE);

  public static final Int16 MAX_VALUE = new Int16(Short.MAX_VALUE);

  private final short value;

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.INT16;
  }

  private Int16(short value)
  {
    this.value = value;
  }

  public static Int16 valueOf(short value)
  {
    return new Int16(value);
  }

  @Override
  public Short getValue()
  {
    return value;
  }

  /**
   * Returns a hash code for this {@code Int16}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code short} value represented by this
   *         {@code Int16} object.
   */
  @Override
  public int hashCode()
  {
    return value;
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code Int16} object that
   * contains the same {@code short} value as this object.
   *
   * @param obj
   *          the object to compare with.
   * @return {@code true} if the objects are the same;
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Int16)
    {
      return value == ((Int16) obj).value;
    }
    return false;
  }

  @Override
  public String toString()
  {
    return Short.toString(value);
  }

  @Override
  public BigDecimal bigDecimalValue()
  {
    return BigDecimal.valueOf(value);
  }

  @Override
  public Bool boolValue()
  {
    return Bool.valueOf(value != (short) 0);
  }

  @Override
  public Char8 char8Value()
  {
    return convert(() -> Char8.valueOf((char) value));
  }

  @Override
  public Float32 float32Value()
  {
    return Float32.valueOf(value);
  }

  @Override
  public Float64 float64Value()
  {
    return Float64.valueOf(value);
  }

  @Override
  public Int8 int8Value()
  {
    return convert(() -> Int8.valueOf((byte) value));

  }

  @Override
  public Int16 int16Value()
  {
    return this;
  }

  @Override
  public Int32 int32Value()
  {

    return Int32.valueOf(value);
  }

  @Override
  public Int64 int64Value()
  {
    return Int64.valueOf(value);
  }

  @Override
  public UInt8 uint8Value()
  {
    return convert(() -> UInt8.valueOf((byte) value));
  }

  @Override
  public UInt16 uint16Value()
  {
    return convert(() -> UInt16.valueOf(value));
  }

  @Override
  public UInt32 uint32Value()
  {
    return convert(() -> UInt32.valueOf(value));
  }

  @Override
  public UInt64 uint64Value()
  {
    return convert(() -> UInt64.valueOf(value));
  }
}