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

public final class UInt8 extends AbstractPrimitiveType<UInt8>
{
  private static final byte UINT8_MAX = (byte) 0xff;

  public static final UInt8 ZERO = new UInt8((byte) 0);

  public static final UInt8 MIN_VALUE = ZERO;

  public static final UInt8 MAX_VALUE = new UInt8(UINT8_MAX);

  private final byte value;

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.UINT8;
  }

  private UInt8(byte value)
  {
    this.value = value;
  }

  public static UInt8 valueOf(byte value)
  {
    return new UInt8(value);
  }

  public short getValue()
  {
    return (short) Byte.toUnsignedInt(value);
  }

  /**
   * Returns a hash code for this {@code UInt8}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code byte} value represented by this
   *         {@code UInt8} object.
   */
  @Override
  public int hashCode()
  {
    return Byte.toUnsignedInt(value);
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code UInt8} object that
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
    if (obj instanceof UInt8)
    {
      return value == ((UInt8) obj).value;
    }
    return false;
  }

  @Override
  public String toString()
  {
    return Byte.toUnsignedInt(value) + "U";
  }

  @Override
  public BigDecimal bigDecimalValue()
  {
    return BigDecimal.valueOf(Byte.toUnsignedInt(value));
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

    return Float32.valueOf(Byte.toUnsignedInt(value));
  }

  @Override
  public Float64 float64Value()
  {
    return Float64.valueOf(Byte.toUnsignedInt(value));
  }

  @Override
  public Int8 int8Value()
  {
    return convert(() -> Int8.valueOf(value));
  }

  @Override
  public Int16 int16Value()
  {
    return Int16.valueOf((short) Byte.toUnsignedInt(value));
  }

  @Override
  public Int32 int32Value()
  {
    return Int32.valueOf(Byte.toUnsignedInt(value));
  }

  @Override
  public Int64 int64Value()
  {
    return Int64.valueOf(Byte.toUnsignedInt(value));
  }

  @Override
  public UInt8 uint8Value()
  {
    return this;
  }

  @Override
  public UInt16 uint16Value()
  {
    return UInt16.valueOf((short) Byte.toUnsignedInt(value));
  }

  @Override
  public UInt32 uint32Value()
  {
    return UInt32.valueOf(Byte.toUnsignedInt(value));
  }

  @Override
  public UInt64 uint64Value()
  {
    return UInt64.valueOf(Byte.toUnsignedLong(value));
  }
}