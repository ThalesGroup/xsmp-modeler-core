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
import java.util.function.Supplier;

public abstract class AbstractPrimitiveType<T extends AbstractPrimitiveType<T>>
        implements PrimitiveType
{

  @Override
  public String toString()
  {
    return getClass().getSimpleName() + "(" + getValue() + ")";
  }

  @Override
  public EnumerationLiteral enumerationLiteralValue()
  {
    throw new UnsupportedOperationException("Could not convert \"" + this.getClass().getSimpleName()
            + "\" to \"EnumerationLiteral\".");
  }

  @Override
  public Bool boolValue()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"Bool\".");
  }

  @Override
  public Char8 char8Value()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"Char8\".");
  }

  @Override
  public Float32 float32Value()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"Float32\".");
  }

  @Override
  public Float64 float64Value()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"Float64\".");
  }

  @Override
  public Int8 int8Value()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"Int8\".");
  }

  @Override
  public Int16 int16Value()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"Int16\".");
  }

  @Override
  public Int32 int32Value()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"Int32\".");
  }

  @Override
  public Int64 int64Value()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"Int64\".");
  }

  @Override
  public String8 string8Value()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"String8\".");
  }

  @Override
  public Duration durationValue()
  {
    return Duration.valueOf(int64Value().getValue());
  }

  @Override
  public DateTime dateTimeValue()
  {
    return DateTime.valueOf(int64Value().getValue());
  }

  @Override
  public UInt8 uint8Value()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"UInt8\".");
  }

  @Override
  public UInt16 uint16Value()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"UInt16\".");
  }

  @Override
  public UInt32 uint32Value()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"UInt32\".");
  }

  @Override
  public UInt64 uint64Value()
  {
    throw new UnsupportedOperationException(
            "Could not convert \"" + this.getClass().getSimpleName() + "\" to \"UInt64\".");
  }

  protected Bool doLogicalOr(T other)
  {
    throw new UnsupportedOperationException();
  }

  protected Bool doLogicalAnd(T other)
  {
    throw new UnsupportedOperationException();
  }

  protected PrimitiveType doOr(T other)
  {
    throw new UnsupportedOperationException();
  }

  protected PrimitiveType doAnd(T other)
  {
    throw new UnsupportedOperationException();
  }

  protected PrimitiveType doXor(T other)
  {
    throw new UnsupportedOperationException();
  }

  protected PrimitiveType doAdd(T other)
  {
    throw new UnsupportedOperationException();
  }

  protected PrimitiveType doSubtract(T other)
  {
    throw new UnsupportedOperationException();
  }

  protected PrimitiveType doDivide(T other)
  {
    throw new UnsupportedOperationException();
  }

  protected PrimitiveType doMultiply(T other)
  {
    throw new UnsupportedOperationException();
  }

  protected PrimitiveType doRemainder(T other)
  {
    throw new UnsupportedOperationException();
  }

  protected PrimitiveType doShiftLeft(T offset)
  {
    throw new UnsupportedOperationException();
  }

  protected PrimitiveType doShiftRight(T offset)
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public Bool not()
  {
    throw new UnsupportedOperationException(
            "Unary \"operator!\" does not support \"" + getClass().getSimpleName() + "\" operand.");
  }

  @Override
  public PrimitiveType unaryComplement()
  {
    throw new UnsupportedOperationException(
            "Unary \"operator~\" does not support \"" + getClass().getSimpleName() + "\" operand.");
  }

  @Override
  public PrimitiveType plus()
  {
    throw new UnsupportedOperationException(
            "Unary \"operator+\" does not support \"" + getClass().getSimpleName() + "\" operand.");
  }

  @Override
  public PrimitiveType negate()
  {
    throw new UnsupportedOperationException(
            "Unary \"operator-\" does not support \"" + getClass().getSimpleName() + "\" operand.");
  }

  protected int doCompareTo(T other)
  {
    throw new UnsupportedOperationException(
            "Comparison \"operator==/!=/</>/<=/>=\" does not support \""
                    + getClass().getSimpleName() + "\" operands.");
  }

  protected <R extends AbstractPrimitiveType<R>> R convert(Supplier<R> func)
  {
    final var result = func.get();

    // check that conversion does not changes value
    if (float64Value().compareTo(result) != 0)
    {
      throw new UnsupportedOperationException("error: conversion from \""
              + this.getClass().getSimpleName() + "\" to \"" + result.getClass().getSimpleName()
              + "\" change value from \"" + getValue() + "\" to \"" + result.getValue() + "\".");
    }
    return result;
  }

  @Override
  public PrimitiveType convert(PrimitiveTypeKind kind)
  {
    switch (kind)
    {
      case BOOL:
        return boolValue();
      case CHAR8:
        return char8Value();
      case DATE_TIME:
        return dateTimeValue();
      case DURATION:
        return durationValue();
      case ENUM:
        return enumerationLiteralValue();
      case FLOAT32:
        return float32Value();
      case FLOAT64:
        return float64Value();
      case INT16:
        return int16Value();
      case INT32:
        return int32Value();
      case INT64:
        return int64Value();
      case INT8:
        return int8Value();
      case STRING8:
        return string8Value();
      case UINT16:
        return uint16Value();
      case UINT32:
        return uint32Value();
      case UINT64:
        return uint64Value();
      case UINT8:
        return uint8Value();
      case NONE:
        break;
    }

    throw new UnsupportedOperationException("Could not convert " + this + " to " + kind.label);

  }

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
        return func.apply(this.uint64Value(), (UInt64) right);
      case DATE_TIME:
      case DURATION:
      case INT64:
        return func.apply(this.int64Value(), (Int64) right);
      case UINT32:
        return func.apply(this.uint32Value(), (UInt32) right);
      case INT32:
        return func.apply(this.int32Value(), (Int32) right);
      default: // promote everything else to int32
        return func.apply(this.int32Value(), right.int32Value());
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public int compareTo(PrimitiveType other)
  {
    return promote(other, AbstractPrimitiveType::doCompareTo);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Bool logicalOr(PrimitiveType val)
  {
    return promote(val, AbstractPrimitiveType::doLogicalOr);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Bool logicalAnd(PrimitiveType val)
  {
    return promote(val, AbstractPrimitiveType::doLogicalAnd);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PrimitiveType or(PrimitiveType val)
  {
    return promote(val, AbstractPrimitiveType::doOr);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PrimitiveType and(PrimitiveType val)
  {
    return promote(val, AbstractPrimitiveType::doAnd);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PrimitiveType xor(PrimitiveType val)
  {
    return promote(val, AbstractPrimitiveType::doXor);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PrimitiveType add(PrimitiveType val)
  {
    return promote(val, AbstractPrimitiveType::doAdd);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PrimitiveType subtract(PrimitiveType val)
  {
    return promote(val, AbstractPrimitiveType::doSubtract);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PrimitiveType divide(PrimitiveType val)
  {
    return promote(val, AbstractPrimitiveType::doDivide);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PrimitiveType multiply(PrimitiveType val)
  {
    return promote(val, AbstractPrimitiveType::doMultiply);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PrimitiveType remainder(PrimitiveType val)
  {
    return promote(val, AbstractPrimitiveType::doRemainder);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PrimitiveType shiftLeft(PrimitiveType n)
  {
    return promote(n, AbstractPrimitiveType::doShiftLeft);
  }

  @Override
  @SuppressWarnings("unchecked")
  public PrimitiveType shiftRight(PrimitiveType n)
  {
    return promote(n, AbstractPrimitiveType::doShiftRight);
  }

  @Override
  public abstract boolean equals(Object obj);

  @Override
  public abstract int hashCode();
}