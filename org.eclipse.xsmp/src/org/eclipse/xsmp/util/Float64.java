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

import java.util.function.BiFunction;

public final class Float64 extends AbstractPrimitiveType<Float64>
{
  public static final Float64 ZERO = new Float64(0.0);

  private final double value;

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.FLOAT64;
  }

  @Override
  protected <R> R promote(PrimitiveType right,
          @SuppressWarnings("rawtypes") BiFunction<AbstractPrimitiveType, AbstractPrimitiveType, R> func)
  {
    return func.apply(this, right.float64Value());
  }

  private Float64(double value)
  {
    this.value = value;
  }

  public static Float64 valueOf(double value)
  {
    return new Float64(value);
  }

  public static Float64 valueOf(String value)
  {
    return new Float64(Double.parseDouble(value));
  }

  @Override
  public Double getValue()
  {
    return value;
  }

  @Override
  public String toString()
  {
    return Double.toString(value);
  }

  /**
   * Returns a hash code for this {@code Float64}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code int} value represented by this
   *         {@code Float64} object.
   */
  @Override
  public int hashCode()
  {
    return Double.hashCode(value);
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code Float64} object that
   * contains the same {@code double} value as this object.
   *
   * @param obj
   *          the object to compare with.
   * @return {@code true} if the objects are the same;
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Float64)
    {
      return value == ((Float64) obj).value;
    }
    return false;
  }

  @Override
  protected Float64 doAdd(Float64 other)
  {
    return valueOf(value + other.value);
  }

  @Override
  protected Float64 doSubtract(Float64 other)
  {
    return valueOf(value - other.value);
  }

  @Override
  protected Float64 doDivide(Float64 other)
  {
    return valueOf(value / other.value);
  }

  @Override
  protected Float64 doMultiply(Float64 other)
  {
    return valueOf(value * other.value);
  }

  @Override
  public Float64 plus()
  {
    return this;
  }

  @Override
  public Float64 negate()
  {
    return valueOf(-value);
  }

  @Override
  public Bool boolValue()
  {
    return Bool.valueOf(value != 0.);
  }

  @Override
  public Char8 char8Value()
  {
    return convert(() -> Char8.valueOf((char) value));
  }

  @Override
  public Float32 float32Value()
  {
    return convert(() -> Float32.valueOf((float) value));
  }

  @Override
  public Float64 float64Value()
  {
    return this;
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