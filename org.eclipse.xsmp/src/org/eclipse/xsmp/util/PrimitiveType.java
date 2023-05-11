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

public interface PrimitiveType extends Comparable<PrimitiveType>
{
  /**
   * Convert this to the given Primitive Type Kind.
   *
   * @param kind
   *          the target Primitive Type Kind
   * @return the converted Primitive Type
   */
  PrimitiveType convert(PrimitiveTypeKind kind);

  /**
   * Get the Primitive Type Kind of this element.
   *
   * @return the Primitive Type Kind
   */
  PrimitiveTypeKind getPrimitiveTypeKind();

  /**
   * Get the raw value.
   *
   * @return the raw value
   */
  Object getValue();

  /**
   * Convert this to an EnumerationLiteral {@code ((EnumerationLiteral)this)}.
   *
   * @return {@code (EnumerationLiteral)this}
   */
  EnumerationLiteral enumerationLiteralValue();

  /**
   * Convert this to a Bool {@code ((Bool)this)}.
   *
   * @return {@code (Bool)this}
   */
  Bool boolValue();

  /**
   * Convert this to a Char8 {@code ((Char8)this)}.
   *
   * @return {@code (Char8)this}
   */
  Char8 char8Value();

  /**
   * Convert this to a Float32 {@code ((Float32)this)}.
   *
   * @return {@code (Float32)this}
   */
  Float32 float32Value();

  /**
   * Convert this to a Float64 {@code ((Float64)this)}.
   *
   * @return {@code (Float64)this}
   */
  Float64 float64Value();

  /**
   * Convert this to an Int8 {@code ((Int8)this)}.
   *
   * @return {@code (Int8)this}
   */
  Int8 int8Value();

  /**
   * Convert this to an Int16 {@code ((Int16)this)}.
   *
   * @return {@code (Int16)this}
   */
  Int16 int16Value();

  /**
   * Convert this to an Int32 {@code ((Int32)this)}.
   *
   * @return {@code (Int32)this}
   */
  Int32 int32Value();

  /**
   * Convert this to an Int64 {@code ((Int64)this)}.
   *
   * @return {@code (Int64)this}
   */
  Int64 int64Value();

  /**
   * Convert this to a String8 {@code ((String8)this)}.
   *
   * @return {@code (String8)this}
   */
  String8 string8Value();

  /**
   * Convert this to a Duration {@code ((Duration)this)}.
   *
   * @return {@code (Duration)this}
   */
  Duration durationValue();

  /**
   * Convert this to a DateTime {@code ((DateTime)this)}.
   *
   * @return {@code (DateTime)this}
   */
  DateTime dateTimeValue();

  /**
   * Convert this to an UInt8 {@code ((UInt8)this)}.
   *
   * @return {@code (UInt8)this}
   */
  UInt8 uint8Value();

  /**
   * Convert this to an UInt16 {@code ((UInt16)this)}.
   *
   * @return {@code (UInt16)this}
   */
  UInt16 uint16Value();

  /**
   * Convert this to an UInt32 {@code ((UInt32)this)}.
   *
   * @return {@code (UInt32)this}
   */
  UInt32 uint32Value();

  /**
   * Convert this to an UInt64 {@code ((UInt64)this)}.
   *
   * @return {@code (UInt64)this}
   */
  UInt64 uint64Value();

  /**
   * Returns a PrimitiveType whose value is {@code (!this)}.
   *
   * @return {@code !this}
   */
  Bool not();

  /**
   * Returns a PrimitiveType whose value is {@code (~this)}.
   *
   * @return {@code ~this}
   */
  PrimitiveType unaryComplement();

  /**
   * Returns a PrimitiveType whose value is {@code (+this)}.
   *
   * @return {@code +this}
   */
  PrimitiveType plus();

  /**
   * Returns a PrimitiveType whose value is {@code (-this)}.
   *
   * @return {@code -this}
   */
  PrimitiveType negate();

  /**
   * Returns a Bool whose value is {@code (this || val)}.
   *
   * @param val
   *          value to be logically OR'ed with this PrimitiveType.
   * @return {@code this || val}
   */
  Bool logicalOr(PrimitiveType val);

  /**
   * Returns a Bool whose value is {@code (this && val)}.
   *
   * @param val
   *          value to be logically AND'ed with this PrimitiveType.
   * @return {@code this && val}
   */
  Bool logicalAnd(PrimitiveType val);

  /**
   * Returns a PrimitiveType whose value is {@code (this | val)}. (This method
   * returns a negative PrimitiveType if and only if either this or val is
   * negative.)
   *
   * @param val
   *          value to be OR'ed with this PrimitiveType.
   * @return {@code this | val}
   */
  PrimitiveType or(PrimitiveType val);

  /**
   * Returns a PrimitiveType whose value is {@code (this & val)}. (This
   * method returns a negative PrimitiveType if and only if this and val are
   * both negative.)
   *
   * @param val
   *          value to be AND'ed with this PrimitiveType.
   * @return {@code this & val}
   */
  PrimitiveType and(PrimitiveType val);

  /**
   * Returns a PrimitiveType whose value is {@code (this ^ val)}. (This method
   * returns a negative PrimitiveType if and only if exactly one of this and
   * val are negative.)
   *
   * @param val
   *          value to be XOR'ed with this PrimitiveType.
   * @return {@code this ^ val}
   */
  PrimitiveType xor(PrimitiveType val);

  /**
   * Returns a PrimitiveType whose value is {@code (this + val)}.
   *
   * @param val
   *          value to be added to this PrimitiveType.
   * @return {@code this + val}
   */
  PrimitiveType add(PrimitiveType val);

  /**
   * Returns a PrimitiveType whose value is {@code (this - val)}.
   *
   * @param val
   *          value to be subtracted from this PrimitiveType.
   * @return {@code this - val}
   */
  PrimitiveType subtract(PrimitiveType val);

  /**
   * Returns a PrimitiveType whose value is {@code (this / val)}.
   *
   * @param val
   *          value by which this PrimitiveType is to be divided.
   * @return {@code this / val}
   * @throws ArithmeticException
   *           if {@code val} is zero.
   */
  PrimitiveType divide(PrimitiveType val);

  /**
   * Returns a PrimitiveType whose value is {@code (this * val)}.
   *
   * @param val
   *          value to be multiplied by this PrimitiveType.
   * @return {@code this * val}
   */
  PrimitiveType multiply(PrimitiveType val);

  /**
   * Returns a PrimitiveType whose value is {@code (this % val)}.
   *
   * @param val
   *          value by which this PrimitiveType is to be divided, and the
   *          remainder computed.
   * @return {@code this % val}
   * @throws ArithmeticException
   *           if {@code val} is zero.
   */
  PrimitiveType remainder(PrimitiveType val);

  /**
   * Returns a PrimitiveType whose value is {@code (this << n)}.
   * The shift distance, {@code n}, may be negative, in which case
   * this method performs a right shift.
   * (Computes <code>floor(this * 2<sup>n</sup>)</code>.)
   *
   * @param n
   *          shift distance, in bits.
   * @return {@code this << n}
   * @see #shiftRight
   */
  PrimitiveType shiftLeft(PrimitiveType n);

  /**
   * Returns a PrimitiveType whose value is {@code (this >> n)}. Sign
   * extension is performed. The shift distance, {@code n}, may be
   * negative, in which case this method performs a left shift.
   * (Computes <code>floor(this / 2<sup>n</sup>)</code>.)
   *
   * @param n
   *          shift distance, in bits.
   * @return {@code this >> n}
   * @see #shiftLeft
   */
  PrimitiveType shiftRight(PrimitiveType n);

  BigDecimal bigDecimalValue();
}