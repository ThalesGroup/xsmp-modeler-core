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
import java.math.BigInteger;
import java.util.function.BiFunction;

public final class Int32 extends AbstractPrimitiveType<Int32>
{
  public static final Int32 ZERO = new Int32(0);

  public static final Int32 ONE = new Int32(1);

  public static final Int32 MIN_VALUE = new Int32(Integer.MIN_VALUE);

  public static final Int32 MAX_VALUE = new Int32(Integer.MAX_VALUE);

  private final int value;

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.INT32;
  }

  @Override
  protected <R> R promote(PrimitiveType right,
          @SuppressWarnings("rawtypes") BiFunction<AbstractPrimitiveType, AbstractPrimitiveType, R> func)
  {
    switch (right.getPrimitiveTypeKind())
    {
      case FLOAT64:
        return func.apply(float64Value(), (Float64) right);
      case FLOAT32:
        return func.apply(float32Value(), (Float32) right);
      case UINT64:
        return func.apply(uint64Value(), (UInt64) right);
      case DATE_TIME:
      case DURATION:
      case INT64:
        return func.apply(int64Value(), (Int64) right);
      case UINT32:
        return func.apply(uint32Value(), (UInt32) right);
      case INT32:
        return func.apply(this, (Int32) right);
      default:
        return func.apply(this, right.int32Value());
    }
  }

  private Int32(int value)
  {
    this.value = value;
  }

  public static Int32 valueOf(String value, int radix)
  {
    return new Int32(Integer.parseInt(value, radix));
  }

  public static Int32 valueOf(int value)
  {
    return new Int32(value);
  }

  public int getValue()
  {
    return value;
  }

  /**
   * Returns a hash code for this {@code Int32}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code int} value represented by this
   *         {@code Int32} object.
   */
  @Override
  public int hashCode()
  {
    return value;
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code Int32} object that
   * contains the same {@code int} value as this object.
   *
   * @param obj
   *          the object to compare with.
   * @return {@code true} if the objects are the same;
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Int32)
    {
      return value == ((Int32) obj).value;
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
    return Integer.toString(value);
  }

  @Override
  protected Int32 doOr(Int32 other)
  {
    return valueOf(value | other.value);
  }

  @Override
  protected Int32 doAnd(Int32 other)
  {
    return valueOf(value & other.value);
  }

  @Override
  protected Int32 doXor(Int32 other)
  {
    return valueOf(value ^ other.value);
  }

  @Override
  protected Int32 doAdd(Int32 other)
  {
    return valueOf(value + other.value);
  }

  @Override
  protected Int32 doSubtract(Int32 other)
  {
    return valueOf(value - other.value);
  }

  @Override
  protected Int32 doDivide(Int32 other)
  {
    return valueOf(value / other.value);
  }

  @Override
  protected Int32 doMultiply(Int32 other)
  {
    return valueOf(value * other.value);
  }

  @Override
  protected Int32 doRemainder(Int32 other)
  {
    return valueOf(value % other.value);
  }

  @Override
  protected Int32 doShiftLeft(Int32 offset)
  {
    try
    {
      return valueOf(BigInteger.valueOf(value).shiftLeft(offset.value).intValueExact());
    }
    catch (final ArithmeticException ex)
    {
      throw new UnsupportedOperationException("Shift overflow.");
    }
  }

  @Override
  protected Int32 doShiftRight(Int32 offset)
  {
    return valueOf(value >> offset.value);
  }

  @Override
  public Int32 unaryComplement()
  {
    return valueOf(~value);
  }

  @Override
  public Int32 plus()
  {
    return this;
  }

  @Override
  public Int32 negate()
  {
    return valueOf(-value);
  }

  @Override
  public Bool boolValue()
  {
    return Bool.valueOf(value != 0);
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
    return convert(() -> Int16.valueOf((short) value));
  }

  @Override
  public Int32 int32Value()
  {
    return this;
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
    return convert(() -> UInt16.valueOf((short) value));
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