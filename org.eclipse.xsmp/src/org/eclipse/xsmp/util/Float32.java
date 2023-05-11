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

public final class Float32 extends AbstractPrimitiveType<Float32>
{
  public static final Float32 ZERO = new Float32(0.0f);

  private final float value;

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.FLOAT32;
  }

  private Float32(float value)
  {
    this.value = value;
  }

  @Override
  protected <R> R promote(PrimitiveType right,
          @SuppressWarnings("rawtypes") BiFunction<AbstractPrimitiveType, AbstractPrimitiveType, R> func)
  {
    return switch (right.getPrimitiveTypeKind())
    {
      case FLOAT64 -> func.apply(this.float64Value(), (Float64) right);
      case FLOAT32 -> func.apply(this, (Float32) right);
      default -> func.apply(this, right.float32Value());
    };
  }

  public static Float32 valueOf(float value)
  {
    return new Float32(value);
  }

  public static Float32 valueOf(String value)
  {
    return new Float32(Float.parseFloat(value));
  }

  public float getValue()
  {
    return value;
  }

  @Override
  public String toString()
  {
    return Float.toString(value) + "f";
  }

  @Override
  public BigDecimal bigDecimalValue()
  {
    return BigDecimal.valueOf(value);
  }

  /**
   * Returns a hash code for this {@code Float32}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code int} value represented by this
   *         {@code Float32} object.
   */
  @Override
  public int hashCode()
  {
    return Float.hashCode(value);
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code Float32} object that
   * contains the same {@code float} value as this object.
   *
   * @param obj
   *          the object to compare with.
   * @return {@code true} if the objects are the same;
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Float32)
    {
      return value == ((Float32) obj).value;
    }
    return false;
  }

  @Override
  protected Float32 doAdd(Float32 other)
  {
    return valueOf(value + other.value);
  }

  @Override
  protected Float32 doSubtract(Float32 other)
  {
    return valueOf(value - other.value);
  }

  @Override
  protected Float32 doDivide(Float32 other)
  {
    return valueOf(value / other.value);
  }

  @Override
  protected Float32 doMultiply(Float32 other)
  {
    return valueOf(value * other.value);
  }

  @Override
  public Float32 plus()
  {
    return this;
  }

  @Override
  public Float32 negate()
  {
    return valueOf(-value);
  }

  @Override
  public Bool boolValue()
  {
    return Bool.valueOf(value != 0.f);
  }

  @Override
  public Char8 char8Value()
  {
    return convert(() -> Char8.valueOf((char) value));
  }

  @Override
  public Float32 float32Value()
  {
    return this;
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
    return convert(() -> Int32.valueOf((int) value));
  }

  @Override
  public Int64 int64Value()
  {
    return convert(() -> Int64.valueOf((long) value));
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
    return convert(() -> UInt64.valueOf((long) value));
  }
}