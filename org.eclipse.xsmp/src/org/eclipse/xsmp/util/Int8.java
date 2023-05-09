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

public final class Int8 extends AbstractPrimitiveType<Int8>
{
  public static final Int8 ZERO = new Int8((byte) 0);

  public static final Int8 MIN_VALUE = new Int8(Byte.MIN_VALUE);

  public static final Int8 MAX_VALUE = new Int8(Byte.MAX_VALUE);

  private final byte value;

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.INT8;
  }

  private Int8(byte value)
  {
    this.value = value;
  }

  public static Int8 valueOf(byte value)
  {
    return new Int8(value);
  }

  @Override
  public Byte getValue()
  {
    return value;
  }

  /**
   * Returns a hash code for this {@code Int8}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code byte} value represented by this
   *         {@code Int8} object.
   */
  @Override
  public int hashCode()
  {
    return value;
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code Int8} object that
   * contains the same {@code byte} value as this object.
   *
   * @param obj
   *          the object to compare with.
   * @return {@code true} if the objects are the same;
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Int8)
    {
      return value == ((Int8) obj).value;
    }
    return false;
  }

  @Override
  public Bool boolValue()
  {
    return Bool.valueOf(value != (byte) 0);
  }

  @Override
  public Char8 char8Value()
  {
    return Char8.valueOf((char) value);
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
    return this;
  }

  @Override
  public Int16 int16Value()
  {
    return Int16.valueOf(value);
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
    return convert(() -> UInt8.valueOf(value));
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