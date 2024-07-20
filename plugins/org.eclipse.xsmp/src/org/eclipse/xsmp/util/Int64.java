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
import java.util.function.BiFunction;

public class Int64 extends AbstractPrimitiveType<Int64>
{
  public static final Int64 ZERO = new Int64(0);

  public static final Int64 ONE = new Int64(1);

  public static final Int64 MIN_VALUE = new Int64(Long.MIN_VALUE);

  public static final Int64 MAX_VALUE = new Int64(Long.MAX_VALUE);

  protected final long value;

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.INT64;
  }

  @Override
  protected <R> R promote(PrimitiveType right,
          @SuppressWarnings("rawtypes") BiFunction<AbstractPrimitiveType, AbstractPrimitiveType, R> func)
  {
    return switch (right.getPrimitiveTypeKind())
    {
      case FLOAT64 -> func.apply(float64Value(), (Float64) right);
      case FLOAT32 -> func.apply(float32Value(), (Float32) right);
      case UINT64 -> func.apply(uint64Value(), (UInt64) right);
      case DATE_TIME, DURATION, INT64 -> func.apply(this, (Int64) right);
      default -> func.apply(this, right.int64Value());
    };
  }

  protected Int64(long value)
  {
    this.value = value;
  }

  public static Int64 valueOf(String value, int radix)
  {
    return new Int64(Long.parseLong(value, radix));
  }

  public static Int64 valueOf(long value)
  {
    return new Int64(value);
  }

  public long getValue()
  {
    return value;
  }

  /**
   * Returns a hash code for this {@code Int64}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code long} value represented by this
   *         {@code Int64} object.
   */
  @Override
  public int hashCode()
  {
    return Long.hashCode(value);
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code Int64} object that
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
    if (obj instanceof final Int64 int64)
    {
      return value == int64.value;
    }
    return false;
  }

  @Override
  public BigDecimal bigDecimalValue()
  {
    return BigDecimal.valueOf(value);
  }

  @Override
  public String toString()
  {
    return value + "L";
  }

  @Override
  protected Int64 doOr(Int64 other)
  {
    return valueOf(value | other.value);
  }

  @Override
  protected Int64 doAnd(Int64 other)
  {
    return valueOf(value & other.value);
  }

  @Override
  protected Int64 doXor(Int64 other)
  {
    return valueOf(value ^ other.value);
  }

  @Override
  protected Int64 doAdd(Int64 other)
  {
    return valueOf(value + other.value);
  }

  @Override
  protected Int64 doSubtract(Int64 other)
  {
    return valueOf(value - other.value);
  }

  @Override
  protected Int64 doDivide(Int64 other)
  {
    return valueOf(value / other.value);
  }

  @Override
  protected Int64 doMultiply(Int64 other)
  {
    return valueOf(value * other.value);
  }

  @Override
  protected Int64 doRemainder(Int64 other)
  {
    return valueOf(value % other.value);
  }

  @Override
  protected Int64 doShiftLeft(Int64 offset)
  {
    return valueOf(value << offset.value);
  }

  @Override
  protected Int64 doShiftRight(Int64 offset)
  {
    return valueOf(value >> offset.value);
  }

  @Override
  public Int64 unaryComplement()
  {
    return valueOf(~value);
  }

  @Override
  public Int64 plus()
  {
    return this;
  }

  @Override
  public Int64 negate()
  {
    return valueOf(-value);
  }

  @Override
  public Bool boolValue()
  {
    return Bool.valueOf(value != 0L);
  }

  @Override
  public Char8 char8Value()
  {
    return convert(() -> Char8.valueOf((char) value));
  }

  @Override
  public Float32 float32Value()
  {
    return convert(() -> Float32.valueOf(value));
  }

  @Override
  public Float64 float64Value()
  {
    return convert(() -> Float64.valueOf(value));
  }

  @Override
  public Int8 int8Value()
  {
    return convert(() -> Int8.valueOf((byte) value));
  }

  @Override
  public Int16 int16Value()
  {
    return convert(() -> Int16.valueOf((short) value));
  }

  @Override
  public Int32 int32Value()
  {
    return convert(() -> Int32.valueOf((int) value));
  }

  @Override
  public Int64 int64Value()
  {
    return this;
  }

  @Override
  public UInt8 uint8Value()
  {

    return convert(() -> UInt8.valueOf((byte) value));
  }

  @Override
  public UInt16 uint16Value()
  {
    return convert(() -> UInt16.valueOf((short) value));
  }

  @Override
  public UInt32 uint32Value()
  {
    return convert(() -> UInt32.valueOf((int) value));
  }

  @Override
  public UInt64 uint64Value()
  {
    return convert(() -> UInt64.valueOf(value));
  }
}