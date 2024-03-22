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

import com.google.common.primitives.UnsignedInteger;

public final class UInt32 extends AbstractPrimitiveType<UInt32>
{
  public static final UInt32 ZERO = new UInt32(UnsignedInteger.ZERO);

  public static final UInt32 ONE = new UInt32(UnsignedInteger.ONE);

  public static final UInt32 MIN_VALUE = ZERO;

  public static final UInt32 MAX_VALUE = new UInt32(UnsignedInteger.MAX_VALUE);

  private final UnsignedInteger value;

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.UINT32;
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
      case DATE_TIME, DURATION, INT64 -> func.apply(int64Value(), (Int64) right);
      case UINT32 -> func.apply(this, (UInt32) right);
      default -> func.apply(this, right.uint32Value());
    };
  }

  private UInt32(UnsignedInteger value)
  {
    this.value = value;
  }

  public static UInt32 valueOf(String value, int radix)
  {
    return new UInt32(UnsignedInteger.valueOf(value, radix));
  }

  private static UInt32 valueOf(UnsignedInteger value)
  {
    return new UInt32(value);
  }

  public static UInt32 valueOf(int value)
  {
    return new UInt32(UnsignedInteger.fromIntBits(value));
  }

  public long getValue()
  {
    return value.longValue();
  }

  @Override
  public String toString()
  {
    return value.toString() + "U";
  }

  @Override
  public BigDecimal bigDecimalValue()
  {
    return BigDecimal.valueOf(value.longValue());
  }

  /**
   * Returns a hash code for this {@code UInt32}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code UnsignedInteger} value represented by this
   *         {@code UInt32} object.
   */
  @Override
  public int hashCode()
  {
    return value.hashCode();
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code UInt32} object that
   * contains the same {@code UnsignedInteger} value as this object.
   *
   * @param obj
   *          the object to compare with.
   * @return {@code true} if the objects are the same;
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof UInt32)
    {
      return value.equals(((UInt32) obj).value);
    }
    return false;
  }

  @Override
  protected AbstractPrimitiveType< ? > doOr(UInt32 other)
  {
    return valueOf(UnsignedInteger.fromIntBits(value.intValue() | other.value.intValue()));
  }

  @Override
  protected AbstractPrimitiveType< ? > doAnd(UInt32 other)
  {
    return valueOf(UnsignedInteger.fromIntBits(value.intValue() & other.value.intValue()));
  }

  @Override
  protected AbstractPrimitiveType< ? > doXor(UInt32 other)
  {
    return valueOf(UnsignedInteger.fromIntBits(value.intValue() ^ other.value.intValue()));
  }

  @Override
  protected AbstractPrimitiveType< ? > doAdd(UInt32 other)
  {
    return valueOf(value.plus(other.value));
  }

  @Override
  protected AbstractPrimitiveType< ? > doSubtract(UInt32 other)
  {
    return valueOf(value.minus(other.value));
  }

  @Override
  protected AbstractPrimitiveType< ? > doDivide(UInt32 other)
  {
    return valueOf(value.dividedBy(other.value));
  }

  @Override
  protected AbstractPrimitiveType< ? > doMultiply(UInt32 other)
  {
    return valueOf(value.times(other.value));
  }

  @Override
  protected AbstractPrimitiveType< ? > doRemainder(UInt32 other)
  {
    return valueOf(value.mod(other.value));
  }

  @Override
  protected AbstractPrimitiveType< ? > doShiftLeft(UInt32 offset)
  {
    return valueOf(UnsignedInteger.fromIntBits(value.intValue() << offset.value.intValue()));
  }

  @Override
  protected AbstractPrimitiveType< ? > doShiftRight(UInt32 offset)
  {
    return valueOf(UnsignedInteger.fromIntBits(value.intValue() >> offset.value.intValue()));
  }

  @Override
  public AbstractPrimitiveType< ? > unaryComplement()
  {
    return valueOf(UnsignedInteger.fromIntBits(~value.intValue()));
  }

  @Override
  public AbstractPrimitiveType< ? > plus()
  {
    return this;
  }

  @Override
  public AbstractPrimitiveType< ? > negate()
  {
    return valueOf(UnsignedInteger.fromIntBits(-value.intValue()));
  }

  @Override
  public Bool boolValue()
  {
    return Bool.valueOf(value.compareTo(UnsignedInteger.ZERO) != 0);
  }

  @Override
  public Char8 char8Value()
  {
    return convert(() -> Char8.valueOf((char) value.byteValue()));

  }

  @Override
  public Float32 float32Value()
  {
    return convert(() -> Float32.valueOf(value.floatValue()));
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
    return Int64.valueOf(value.longValue());
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
    return this;
  }

  @Override
  public UInt64 uint64Value()
  {
    return UInt64.valueOf(value.longValue());
  }
}