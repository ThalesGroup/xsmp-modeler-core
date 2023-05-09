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

public final class UInt16 extends AbstractPrimitiveType<UInt16>
{
  private static final short UINT16_MAX = (short) 0xffff;

  public static final UInt16 ZERO = new UInt16((short) 0);

  public static final UInt16 MIN_VALUE = ZERO;

  public static final UInt16 MAX_VALUE = new UInt16(UINT16_MAX);

  private final short value;

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.UINT16;
  }

  private UInt16(short value)
  {
    this.value = value;
  }

  public static UInt16 valueOf(short value)
  {
    return new UInt16(value);
  }

  @Override
  public Integer getValue()
  {
    return Short.toUnsignedInt(value);
  }

  /**
   * Returns a hash code for this {@code UInt16}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code short} value represented by this
   *         {@code UInt16} object.
   */
  @Override
  public int hashCode()
  {
    return Short.toUnsignedInt(value);
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is an {@code UInt16} object that
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
    if (obj instanceof UInt16)
    {
      return value == ((UInt16) obj).value;
    }
    return false;
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
    return Float32.valueOf(Short.toUnsignedInt(value));
  }

  @Override
  public Float64 float64Value()
  {
    return Float64.valueOf(Short.toUnsignedInt(value));
  }

  @Override
  public Int8 int8Value()
  {
    return convert(() -> Int8.valueOf((byte) Short.toUnsignedInt(value)));
  }

  @Override
  public Int16 int16Value()
  {
    return convert(() -> Int16.valueOf((short) Short.toUnsignedInt(value)));
  }

  @Override
  public Int32 int32Value()
  {
    return Int32.valueOf(Short.toUnsignedInt(value));
  }

  @Override
  public Int64 int64Value()
  {
    return Int64.valueOf(Short.toUnsignedInt(value));
  }

  @Override
  public UInt8 uint8Value()
  {
    return convert(() -> UInt8.valueOf((byte) value));
  }

  @Override
  public UInt16 uint16Value()
  {
    return this;
  }

  @Override
  public UInt32 uint32Value()
  {
    return UInt32.valueOf(Short.toUnsignedInt(value));
  }

  @Override
  public UInt64 uint64Value()
  {
    return UInt64.valueOf(Short.toUnsignedInt(value));
  }
}