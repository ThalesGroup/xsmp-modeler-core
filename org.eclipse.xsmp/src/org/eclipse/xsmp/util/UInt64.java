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

import java.math.BigInteger;
import java.util.function.BiFunction;

import com.google.common.primitives.UnsignedLong;

public final class UInt64 extends AbstractPrimitiveType<UInt64>
{
  public static final UInt64 ZERO = new UInt64(UnsignedLong.ZERO);

  public static final UInt64 ONE = new UInt64(UnsignedLong.ONE);

  public static final UInt64 MIN_VALUE = ZERO;

  public static final UInt64 MAX_VALUE = new UInt64(UnsignedLong.MAX_VALUE);

  private final UnsignedLong value;

  @Override
  protected <R> R promote(PrimitiveType right,
          @SuppressWarnings("rawtypes") BiFunction<AbstractPrimitiveType, AbstractPrimitiveType, R> func)
  {
    switch (right.getPrimitiveTypeKind())
    {
      case FLOAT64:
        return func.apply(this.float64Value(), (Float64) right);
      case FLOAT32:
        return func.apply(this.float32Value(), (Float32) right);
      case UINT64:
        return func.apply(this, (UInt64) right);
      default:
        return func.apply(this, right.uint64Value());
    }
  }

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.UINT64;
  }

  private UInt64(UnsignedLong value)
  {
    this.value = value;
  }

  public static UInt64 valueOf(String value, int radix)
  {
    return new UInt64(UnsignedLong.valueOf(value, radix));
  }

  private static UInt64 valueOf(UnsignedLong value)
  {
    return new UInt64(value);
  }

  public static UInt64 valueOf(long value)
  {
    return new UInt64(UnsignedLong.fromLongBits(value));
  }

  @Override
  public BigInteger getValue()
  {
    return value.bigIntegerValue();
  }

  /**
   * Returns a hash code for this {@code UInt64}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code UnsignedLong} value represented by this
   *         {@code UInt64} object.
   */
  @Override
  public int hashCode()
  {
    return value.hashCode();
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code UInt64} object that
   * contains the same {@code UnsignedLong} value as this object.
   *
   * @param obj
   *          the object to compare with.
   * @return {@code true} if the objects are the same;
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof UInt64)
    {
      return value.equals(((UInt64) obj).value);
    }
    return false;
  }

  @Override
  protected Bool doLogicalOr(UInt64 other)
  {

    return Bool.valueOf(value.compareTo(UnsignedLong.ZERO) != 0
            || other.value.compareTo(UnsignedLong.ZERO) != 0);
  }

  @Override
  protected Bool doLogicalAnd(UInt64 other)
  {
    return Bool.valueOf(value.compareTo(UnsignedLong.ZERO) != 0
            && other.value.compareTo(UnsignedLong.ZERO) != 0);
  }

  @Override
  protected AbstractPrimitiveType< ? > doOr(UInt64 other)
  {
    return valueOf(UnsignedLong.fromLongBits(value.longValue() | other.value.longValue()));
  }

  @Override
  protected AbstractPrimitiveType< ? > doAnd(UInt64 other)
  {
    return valueOf(UnsignedLong.fromLongBits(value.longValue() & other.value.longValue()));
  }

  @Override
  protected AbstractPrimitiveType< ? > doXor(UInt64 other)
  {
    return valueOf(UnsignedLong.fromLongBits(value.longValue() ^ other.value.longValue()));
  }

  @Override
  protected AbstractPrimitiveType< ? > doAdd(UInt64 other)
  {
    return valueOf(value.plus(other.value));
  }

  @Override
  protected AbstractPrimitiveType< ? > doSubtract(UInt64 other)
  {
    return valueOf(value.minus(other.value));
  }

  @Override
  protected AbstractPrimitiveType< ? > doDivide(UInt64 other)
  {
    return valueOf(value.dividedBy(other.value));
  }

  @Override
  protected AbstractPrimitiveType< ? > doMultiply(UInt64 other)
  {
    return valueOf(value.times(other.value));
  }

  @Override
  protected AbstractPrimitiveType< ? > doRemainder(UInt64 other)
  {
    return valueOf(value.mod(other.value));
  }

  @Override
  protected AbstractPrimitiveType< ? > doShiftLeft(UInt64 offset)
  {
    return valueOf(UnsignedLong.fromLongBits(value.longValue() << offset.value.longValue()));
  }

  @Override
  protected AbstractPrimitiveType< ? > doShiftRight(UInt64 offset)
  {
    return valueOf(UnsignedLong.fromLongBits(value.longValue() >> offset.value.longValue()));
  }

  @Override
  public Bool not()
  {
    return Bool.valueOf(value.compareTo(UnsignedLong.ZERO) != 0);
  }

  @Override
  public AbstractPrimitiveType< ? > unaryComplement()
  {
    return valueOf(UnsignedLong.valueOf(~value.longValue()));
  }

  @Override
  public AbstractPrimitiveType< ? > plus()
  {
    return this;
  }

  @Override
  public AbstractPrimitiveType< ? > negate()
  {
    return valueOf(UnsignedLong.fromLongBits(-value.longValue()));
  }

  @Override
  protected int doCompareTo(UInt64 other)
  {
    return value.compareTo(other.value);
  }

  @Override
  public Bool boolValue()
  {
    return Bool.valueOf(value.compareTo(UnsignedLong.ZERO) != 0);
  }

  @Override
  public Char8 char8Value()
  {
    return convert(() -> Char8.valueOf((char) value.byteValue()));
  }

  @Override
  public Float32 float32Value()
  {
    return Float32.valueOf(value.floatValue());
  }

  @Override
  public Float64 float64Value()
  {
    return Float64.valueOf(value.doubleValue());
  }

  @Override
  public Int8 int8Value()
  {
    return convert(() -> Int8.valueOf(value.byteValue()));
  }

  @Override
  public Int16 int16Value()
  {
    return convert(() -> Int16.valueOf(value.shortValue()));
  }

  @Override
  public Int32 int32Value()
  {
    return convert(() -> Int32.valueOf(value.intValue()));
  }

  @Override
  public Int64 int64Value()
  {
    return convert(() -> Int64.valueOf(value.longValue()));
  }

  @Override
  public UInt8 uint8Value()
  {
    return convert(() -> UInt8.valueOf(value.byteValue()));
  }

  @Override
  public UInt16 uint16Value()
  {
    return convert(() -> UInt16.valueOf(value.shortValue()));
  }

  @Override
  public UInt32 uint32Value()
  {
    return convert(() -> UInt32.valueOf(value.intValue()));
  }

  @Override
  public UInt64 uint64Value()
  {
    return this;
  }
}