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

public final class Char8 extends AbstractPrimitiveType<Char8>
{
  private final byte value;

  public static final Char8 ZERO = new Char8((char) 0);

  public static final Char8 MIN_VALUE = new Char8((char) Byte.MIN_VALUE);

  public static final Char8 MAX_VALUE = new Char8((char) Byte.MAX_VALUE);

  @Override
  public PrimitiveTypeKind getPrimitiveTypeKind()
  {
    return PrimitiveTypeKind.CHAR8;
  }

  private Char8(char value)
  {
    this.value = (byte) value;
  }

  public static Char8 valueOf(char value)
  {
    return new Char8(value);
  }

  public String getValue()
  {
    return switch (value)
    {
      case 0x07 -> "\\a";
      case 0x08 -> "\\b";
      case 0x0c -> "\\f";
      case 0x0b -> "\\v";
      case '\\' -> "\\\\";
      case '\n' -> "\\n";
      case '\r' -> "\\r";
      case '\t' -> "\\t";
      default -> value >= 32 && value <= 126 ? Character.toString(value)
                      : "\\" + Integer.toOctalString(value);
    };
  }

  @Override
  public String toString()
  {
    return "'" + getValue() + "'";
  }

  @Override
  public BigDecimal bigDecimalValue()
  {
    return BigDecimal.valueOf(value);
  }

  /**
   * Returns a hash code for this {@code Char8}.
   *
   * @return a hash code value for this object, equal to the
   *         primitive {@code int} value represented by this
   *         {@code Char8} object.
   */
  @Override
  public int hashCode()
  {
    return value;
  }

  /**
   * Compares this object to the specified object. The result is
   * {@code true} if and only if the argument is not
   * {@code null} and is a {@code Char8} object that
   * contains the same {@code char} value as this object.
   *
   * @param obj
   *          the object to compare with.
   * @return {@code true} if the objects are the same;
   *         {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Char8)
    {
      return value == ((Char8) obj).value;
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
    return this;
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
    return Int8.valueOf(value);
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