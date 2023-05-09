package org.eclipse.xsmp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.xsmp.util.Bool;
import org.eclipse.xsmp.util.Char8;
import org.eclipse.xsmp.util.Float32;
import org.eclipse.xsmp.util.Float64;
import org.eclipse.xsmp.util.Int16;
import org.eclipse.xsmp.util.Int32;
import org.eclipse.xsmp.util.Int64;
import org.eclipse.xsmp.util.Int8;
import org.eclipse.xsmp.util.PrimitiveType;
import org.eclipse.xsmp.util.UInt16;
import org.eclipse.xsmp.util.UInt32;
import org.eclipse.xsmp.util.UInt64;
import org.eclipse.xsmp.util.UInt8;
import org.junit.jupiter.api.Test;

class SolverTest
{
  @Test
  void testInt8()
  {
    // Test conversion from byte to Int8
    assertEquals((byte) 42, Int8.valueOf((byte) 42).getValue());

    // Test conversion from Int8 to Int32
    assertEquals(Int32.valueOf(42), Int8.valueOf((byte) 42).int32Value());

    // Test promotion to Int32 for unary operators
    assertEquals(Int32.valueOf(-42), Int8.valueOf((byte) 42).negate());
    assertEquals(Int32.valueOf(42), Int8.valueOf((byte) 42).plus());
    assertEquals(Int32.valueOf(-43), Int8.valueOf((byte) 42).unaryComplement());

    // Test promotion to Int32 for binary operators
    assertEquals(Int32.valueOf(23), Int8.valueOf((byte) 42).add(Int8.valueOf((byte) -19)));
    assertEquals(Int32.valueOf(84), Int8.valueOf((byte) 42).multiply(Int8.valueOf((byte) 2)));
    assertEquals(Int32.valueOf(2), Int8.valueOf((byte) 42).divide(Int8.valueOf((byte) 20)));
    assertEquals(Int32.valueOf(22), Int8.valueOf((byte) 42).subtract(Int8.valueOf((byte) 20)));
    assertEquals(Int32.valueOf(2), Int8.valueOf((byte) 42).remainder(Int8.valueOf((byte) 20)));
    assertEquals(Int32.valueOf(-84), Int8.valueOf((byte) -42).shiftLeft(Int8.valueOf((byte) 1)));
    assertEquals(Int32.valueOf(-21), Int8.valueOf((byte) -42).shiftRight(Int8.valueOf((byte) 1)));
    assertEquals(Int32.valueOf(59), Int8.valueOf((byte) 42).or(Int8.valueOf((byte) 19)));
    assertEquals(Int32.valueOf(2), Int8.valueOf((byte) 42).and(Int8.valueOf((byte) 2)));
    assertEquals(Int32.valueOf(56), Int8.valueOf((byte) 42).xor(Int8.valueOf((byte) 18)));

    // Test compareTo
    assertEquals(0, Int8.valueOf((byte) 42).compareTo(Int8.valueOf((byte) 42)));
    assertEquals(1, Int8.valueOf((byte) 42).compareTo(Int8.valueOf((byte) 19)));
    assertEquals(-1, Int8.valueOf((byte) 42).compareTo(Int8.valueOf((byte) 99)));

    // Test not operator
    assertEquals(Bool.FALSE, Int8.valueOf((byte) 42).not());
  }

  @Test
  void testInt16()
  {
    // Test conversion from short to Int16
    assertEquals((short) 42, Int16.valueOf((short) 42).getValue());

    // Test conversion from Int16 to Int32
    assertEquals(Int32.valueOf(42), Int16.valueOf((short) 42).int32Value());

    // Test promotion to Int32 for unary operators
    assertEquals(Int32.valueOf(-42), Int16.valueOf((short) 42).negate());
    assertEquals(Int32.valueOf(42), Int16.valueOf((short) 42).plus());
    assertEquals(Int32.valueOf(-43), Int16.valueOf((short) 42).unaryComplement());

    // Test promotion to Int32 for binary operators
    assertEquals(Int32.valueOf(23), Int16.valueOf((short) 42).add(Int16.valueOf((short) -19)));
    assertEquals(Int32.valueOf(84), Int16.valueOf((short) 42).multiply(Int16.valueOf((short) 2)));
    assertEquals(Int32.valueOf(2), Int16.valueOf((short) 42).divide(Int16.valueOf((short) 20)));
    assertEquals(Int32.valueOf(22), Int16.valueOf((short) 42).subtract(Int16.valueOf((short) 20)));
    assertEquals(Int32.valueOf(2), Int16.valueOf((short) 42).remainder(Int16.valueOf((short) 20)));
    assertEquals(Int32.valueOf(-84),
            Int16.valueOf((short) -42).shiftLeft(Int16.valueOf((short) 1)));
    assertEquals(Int32.valueOf(-21),
            Int16.valueOf((short) -42).shiftRight(Int16.valueOf((short) 1)));
    assertEquals(Int32.valueOf(59), Int16.valueOf((short) 42).or(Int16.valueOf((short) 19)));
    assertEquals(Int32.valueOf(2), Int16.valueOf((short) 42).and(Int16.valueOf((short) 2)));
    assertEquals(Int32.valueOf(56), Int16.valueOf((short) 42).xor(Int16.valueOf((short) 18)));

    // Test compareTo
    assertEquals(0, Int16.valueOf((short) 42).compareTo(Int16.valueOf((short) 42)));
    assertEquals(1, Int16.valueOf((short) 42).compareTo(Int16.valueOf((short) 19)));
    assertEquals(-1, Int16.valueOf((short) 42).compareTo(Int16.valueOf((short) 99)));

    // Test not operator
    assertEquals(Bool.FALSE, Int16.valueOf((short) 42).not());
  }

  @Test
  void testUInt8()
  {
    // Test conversion from byte to UInt8
    assertEquals((short) 255, UInt8.valueOf((byte) -1).getValue());

    // Test conversion from UInt8 to Int32
    assertEquals(Int32.valueOf(42), UInt8.valueOf((byte) 42).int32Value());

    // Test promotion to Int32 for unary operators
    assertEquals(Int32.valueOf(-250), UInt8.valueOf((byte) 250).negate());
    assertEquals(Int32.valueOf(42), UInt8.valueOf((byte) 42).plus());
    assertEquals(Int32.valueOf(-6), UInt8.valueOf((byte) 5).unaryComplement());

    // Test promotion to Int32 for binary operators
    assertEquals(Int32.valueOf(279), UInt8.valueOf((byte) 42).add(UInt8.valueOf((byte) -19)));
    assertEquals(Int32.valueOf(84), UInt8.valueOf((byte) 42).multiply(UInt8.valueOf((byte) 2)));
    assertEquals(Int32.valueOf(2), UInt8.valueOf((byte) 42).divide(UInt8.valueOf((byte) 20)));
    assertEquals(Int32.valueOf(22), UInt8.valueOf((byte) 42).subtract(UInt8.valueOf((byte) 20)));
    assertEquals(Int32.valueOf(2), UInt8.valueOf((byte) 42).remainder(UInt8.valueOf((byte) 20)));
    assertEquals(Int32.valueOf(6291456),
            UInt8.valueOf((byte) 6).shiftLeft(UInt8.valueOf((byte) 20)));
    assertEquals(Int32.valueOf(21), UInt8.valueOf((byte) 42).shiftRight(UInt8.valueOf((byte) 1)));
    assertEquals(Int32.valueOf(59), UInt8.valueOf((byte) 42).or(UInt8.valueOf((byte) 19)));
    assertEquals(Int32.valueOf(2), UInt8.valueOf((byte) 42).and(UInt8.valueOf((byte) 2)));
    assertEquals(Int32.valueOf(56), UInt8.valueOf((byte) 42).xor(UInt8.valueOf((byte) 18)));

    // Test compareTo
    assertEquals(0, UInt8.valueOf((byte) 42).compareTo(UInt8.valueOf((byte) 42)));
    assertEquals(1, UInt8.valueOf((byte) 42).compareTo(UInt8.valueOf((byte) 19)));
    assertEquals(-1, UInt8.valueOf((byte) 42).compareTo(UInt8.valueOf((byte) 99)));

    // Test not operator
    assertEquals(Bool.FALSE, UInt8.valueOf((byte) 42).not());
  }

  @Test
  void testUInt16()
  {
    // Test conversion from short to UInt16
    assertEquals(65535, UInt16.valueOf((short) -1).getValue());

    // Test conversion from UInt16 to Int32
    assertEquals(Int32.valueOf(42), UInt16.valueOf((short) 42).int32Value());

    // Test promotion to Int32 for unary operators
    assertEquals(Int32.valueOf(-42), UInt16.valueOf((short) 42).negate());
    assertEquals(Int32.valueOf(42), UInt16.valueOf((short) 42).plus());
    assertEquals(Int32.valueOf(-43), UInt16.valueOf((short) 42).unaryComplement());

    // Test promotion to Int32 for binary operators
    assertEquals(Int32.valueOf(65559), UInt16.valueOf((short) 42).add(UInt16.valueOf((short) -19)));
    assertEquals(Int32.valueOf(84), UInt16.valueOf((short) 42).multiply(UInt16.valueOf((short) 2)));
    assertEquals(Int32.valueOf(2), UInt16.valueOf((short) 42).divide(UInt16.valueOf((short) 20)));
    assertEquals(Int32.valueOf(22),
            UInt16.valueOf((short) 42).subtract(UInt16.valueOf((short) 20)));
    assertEquals(Int32.valueOf(2),
            UInt16.valueOf((short) 42).remainder(UInt16.valueOf((short) 20)));
    assertEquals(Int32.valueOf(130988),
            UInt16.valueOf((short) -42).shiftLeft(UInt16.valueOf((short) 1)));
    assertEquals(Int32.valueOf(32747),
            UInt16.valueOf((short) -42).shiftRight(UInt16.valueOf((short) 1)));
    assertEquals(Int32.valueOf(59), UInt16.valueOf((short) 42).or(UInt16.valueOf((short) 19)));
    assertEquals(Int32.valueOf(2), UInt16.valueOf((short) 42).and(UInt16.valueOf((short) 2)));
    assertEquals(Int32.valueOf(56), UInt16.valueOf((short) 42).xor(UInt16.valueOf((short) 18)));

    // Test compareTo
    assertEquals(0, UInt16.valueOf((short) 42).compareTo(UInt16.valueOf((short) 42)));
    assertEquals(1, UInt16.valueOf((short) 42).compareTo(UInt16.valueOf((short) 19)));
    assertEquals(-1, UInt16.valueOf((short) 42).compareTo(UInt16.valueOf((short) 99)));

    // Test not operator
    assertEquals(Bool.FALSE, UInt16.valueOf((short) 42).not());
  }

  @Test
  void testInt32()
  {
    // Test conversion from int to Int32
    assertEquals(42, Int32.valueOf(42).getValue());

    // Test unary operators
    assertEquals(Int32.valueOf(-42), Int32.valueOf(42).negate());
    assertEquals(Int32.valueOf(42), Int32.valueOf(42).plus());
    assertEquals(Int32.valueOf(-43), Int32.valueOf(42).unaryComplement());

    // Test binary operators
    assertEquals(Int32.valueOf(23), Int32.valueOf(42).add(Int32.valueOf(-19)));
    assertEquals(Int32.valueOf(84), Int32.valueOf(42).multiply(Int32.valueOf(2)));
    assertEquals(Int32.valueOf(2), Int32.valueOf(42).divide(Int32.valueOf(20)));
    assertEquals(Int32.valueOf(22), Int32.valueOf(42).subtract(Int32.valueOf(20)));
    assertEquals(Int32.valueOf(2), Int32.valueOf(42).remainder(Int32.valueOf(20)));
    assertEquals(Int32.valueOf(-84), Int32.valueOf(-42).shiftLeft(Int32.valueOf(1)));
    assertEquals(Int32.valueOf(-21), Int32.valueOf(-42).shiftRight(Int32.valueOf(1)));
    assertEquals(Int32.valueOf(59), Int32.valueOf(42).or(Int32.valueOf(19)));
    assertEquals(Int32.valueOf(2), Int32.valueOf(42).and(Int32.valueOf(2)));
    assertEquals(Int32.valueOf(56), Int32.valueOf(42).xor(Int32.valueOf(18)));

    // Test compareTo
    assertEquals(0, Int32.valueOf(42).compareTo(Int32.valueOf(42)));
    assertEquals(1, Int32.valueOf(42).compareTo(Int32.valueOf(19)));
    assertEquals(-1, Int32.valueOf(42).compareTo(Int32.valueOf(99)));

    // Test not operator
    assertEquals(Bool.FALSE, Int32.valueOf(42).not());
  }

  @Test
  void testUInt32()
  {
    // Test conversion from int to UInt32
    assertEquals(0xffff_ffffL, UInt32.valueOf(-1).getValue());

    // Test conversion from UInt32 to Int32
    assertEquals(Int32.valueOf(42), UInt32.valueOf(42).int32Value());

    // Test unary operators
    assertEquals(UInt32.valueOf(42), UInt32.valueOf(-42).negate());
    assertEquals(UInt32.valueOf(42), UInt32.valueOf(42).plus());
    assertEquals(UInt32.valueOf(41), UInt32.valueOf(-42).unaryComplement());

    // Test binary operators
    assertEquals(UInt32.valueOf(0), UInt32.valueOf(42).add(UInt32.valueOf(-42)));
    assertEquals(UInt32.valueOf(84), UInt32.valueOf(42).multiply(UInt32.valueOf(2)));
    assertEquals(UInt32.valueOf(2), UInt32.valueOf(42).divide(UInt32.valueOf(20)));
    assertEquals(UInt32.valueOf(22), UInt32.valueOf(42).subtract(UInt32.valueOf(20)));
    assertEquals(UInt32.valueOf(2), UInt32.valueOf(42).remainder(UInt32.valueOf(20)));
    assertEquals(UInt32.valueOf(84), UInt32.valueOf(42).shiftLeft(UInt32.valueOf(1)));
    assertEquals(UInt32.valueOf(21), UInt32.valueOf(42).shiftRight(UInt32.valueOf(1)));
    assertEquals(UInt32.valueOf(59), UInt32.valueOf(42).or(UInt32.valueOf(19)));
    assertEquals(UInt32.valueOf(2), UInt32.valueOf(42).and(UInt32.valueOf(2)));
    assertEquals(UInt32.valueOf(56), UInt32.valueOf(42).xor(UInt32.valueOf(18)));

    // Test compareTo
    assertEquals(0, UInt32.valueOf(42).compareTo(UInt32.valueOf(42)));
    assertEquals(1, UInt32.valueOf(42).compareTo(UInt32.valueOf(19)));
    assertEquals(-1, UInt32.valueOf(42).compareTo(UInt32.valueOf(99)));

    // Test not operator
    assertEquals(Bool.FALSE, UInt32.valueOf(42).not());
  }

  @Test
  void testInt64()
  {
    // Test conversion from long to Int64
    assertEquals(42L, Int64.valueOf(42L).getValue());

    // Test conversion from Int64 to Int32
    assertEquals(Int32.valueOf(42), Int64.valueOf(42L).int32Value());

    // Test unary operators
    assertEquals(Int64.valueOf(-42L), Int64.valueOf(42L).negate());
    assertEquals(Int64.valueOf(42L), Int64.valueOf(42L).plus());
    assertEquals(Int64.valueOf(-43L), Int64.valueOf(42L).unaryComplement());

    // Test binary operators
    assertEquals(Int64.valueOf(23L), Int64.valueOf(42L).add(Int64.valueOf(-19L)));
    assertEquals(Int64.valueOf(84L), Int64.valueOf(42L).multiply(Int64.valueOf(2L)));
    assertEquals(Int64.valueOf(2L), Int64.valueOf(42L).divide(Int64.valueOf(20L)));
    assertEquals(Int64.valueOf(22L), Int64.valueOf(42L).subtract(Int64.valueOf(20L)));
    assertEquals(Int64.valueOf(2L), Int64.valueOf(42L).remainder(Int64.valueOf(20L)));
    assertEquals(Int64.valueOf(-84L), Int64.valueOf(-42L).shiftLeft(Int8.valueOf((byte) 1)));
    assertEquals(Int64.valueOf(-21L), Int64.valueOf(-42L).shiftRight(Int8.valueOf((byte) 1)));
    assertEquals(Int64.valueOf(59L), Int64.valueOf(42L).or(Int64.valueOf(19L)));
    assertEquals(Int64.valueOf(2L), Int64.valueOf(42L).and(Int64.valueOf(2L)));
    assertEquals(Int64.valueOf(56L), Int64.valueOf(42L).xor(Int64.valueOf(18L)));

    // Test compareTo
    assertEquals(0, Int64.valueOf(42L).compareTo(Int64.valueOf(42L)));
    assertEquals(1, Int64.valueOf(42L).compareTo(Int64.valueOf(19L)));
    assertEquals(-1, Int64.valueOf(42L).compareTo(Int64.valueOf(99L)));

    // Test not operator
    assertEquals(Bool.FALSE, Int64.valueOf(42L).not());
  }

  void verify(PrimitiveType expected, PrimitiveType value)
  {
    assertEquals(expected.getClass(), value.getClass());
    assertEquals(expected, value);
  }

  PrimitiveType logicalOr(PrimitiveType left, PrimitiveType right)
  {
    return left.logicalOr(right);
  }

  PrimitiveType logicalAnd(PrimitiveType left, PrimitiveType right)
  {
    return left.logicalAnd(right);
  }

  PrimitiveType bitwiseXor(PrimitiveType left, PrimitiveType right)
  {
    return left.xor(right);
  }

  PrimitiveType bitwiseAnd(PrimitiveType left, PrimitiveType right)
  {
    return left.and(right);
  }

  PrimitiveType bitwiseOr(PrimitiveType left, PrimitiveType right)
  {
    return left.or(right);
  }

  PrimitiveType addition(PrimitiveType left, PrimitiveType right)
  {
    return left.add(right);
  }

  PrimitiveType subtraction(PrimitiveType left, PrimitiveType right)
  {
    return left.subtract(right);
  }

  PrimitiveType divide(PrimitiveType left, PrimitiveType right)
  {
    return left.divide(right);
  }

  PrimitiveType multiply(PrimitiveType left, PrimitiveType right)
  {
    return left.multiply(right);
  }

  PrimitiveType remainder(PrimitiveType left, PrimitiveType right)
  {
    return left.remainder(right);
  }

  @Test
  void testLogicalOr()
  {
    // Double
    verify(Bool.FALSE, Float64.valueOf(0.0).logicalOr(Float64.valueOf(0.0)));
    verify(Bool.TRUE, Float64.valueOf(1.0).logicalOr(Float64.valueOf(0.0)));
    verify(Bool.TRUE, Float64.valueOf(0.0).logicalOr(Float64.valueOf(1.0)));
    verify(Bool.TRUE, Float64.valueOf(1.0).logicalOr(Float64.valueOf(1.0)));

    verify(Bool.FALSE, Float64.valueOf(0.0).logicalOr(Float32.valueOf(0.0f)));
    verify(Bool.TRUE, Float32.valueOf(1.0f).logicalOr(Float64.valueOf(0.0)));
    verify(Bool.TRUE, Float32.valueOf(1.0f).logicalOr(Float64.valueOf(1.0)));

    verify(Bool.FALSE, Float64.valueOf(0.0).logicalOr(UInt64.ZERO));
    verify(Bool.TRUE, UInt64.MAX_VALUE.logicalOr(Float64.valueOf(0.0)));
    verify(Bool.TRUE, UInt64.MAX_VALUE.logicalOr(Float64.valueOf(1.0)));

    verify(Bool.FALSE, Float64.valueOf(0.0).logicalOr(Int64.ZERO));
    verify(Bool.TRUE, Int64.ONE.logicalOr(Float64.valueOf(0.0)));
    verify(Bool.TRUE, Int64.ONE.logicalOr(Float64.valueOf(1.0)));

    verify(Bool.FALSE, Float64.valueOf(0.0).logicalOr(UInt32.ZERO));
    verify(Bool.TRUE, UInt32.MAX_VALUE.logicalOr(Float64.valueOf(0.0)));
    verify(Bool.TRUE, UInt32.MAX_VALUE.logicalOr(Float64.valueOf(1.0)));

    verify(Bool.FALSE, Float64.valueOf(0.0).logicalOr(Int32.ZERO));
    verify(Bool.TRUE, Int32.ONE.logicalOr(Float64.valueOf(0.0)));
    verify(Bool.TRUE, Int32.ONE.logicalOr(Float64.valueOf(1.0)));

    verify(Bool.FALSE, Float64.valueOf(0.0).logicalOr(Int16.ZERO));
    verify(Bool.TRUE, Int16.ONE.logicalOr(Float64.valueOf(0.0)));
    verify(Bool.TRUE, Int16.ONE.logicalOr(Float64.valueOf(1.0)));

    verify(Bool.FALSE, Float64.valueOf(0.0).logicalOr(Char8.valueOf('\0')));
    verify(Bool.TRUE, Char8.valueOf('\1').logicalOr(Float64.valueOf(0.0)));
    verify(Bool.TRUE, Char8.valueOf('\1').logicalOr(Float64.valueOf(1.0)));

    verify(Bool.FALSE, Float64.valueOf(0.0).logicalOr(Bool.FALSE));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Float64.valueOf(0.0)));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Float64.valueOf(1.0)));

    // Float
    verify(Bool.FALSE, Float32.valueOf(0.0f).logicalOr(Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalOr(Float32.valueOf(1.0f), Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalOr(Float32.valueOf(1.0f), Float32.valueOf(1.0f)));

    verify(Bool.FALSE, Float32.valueOf(0.0f).logicalOr(UInt64.ZERO));
    verify(Bool.TRUE, logicalOr(UInt64.MAX_VALUE, Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalOr(UInt64.MAX_VALUE, Float32.valueOf(1.0f)));

    verify(Bool.FALSE, Float32.valueOf(0.0f).logicalOr(Int64.ZERO));
    verify(Bool.TRUE, logicalOr(Int64.ONE, Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalOr(Int64.ONE, Float32.valueOf(1.0f)));

    verify(Bool.FALSE, Float32.valueOf(0.0f).logicalOr(UInt32.ZERO));
    verify(Bool.TRUE, logicalOr(UInt32.MAX_VALUE, Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalOr(UInt32.MAX_VALUE, Float32.valueOf(1.0f)));

    verify(Bool.FALSE, Float32.valueOf(0.0f).logicalOr(Int32.ZERO));
    verify(Bool.TRUE, logicalOr(Int32.ONE, Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalOr(Int32.ONE, Float32.valueOf(1.0f)));

    verify(Bool.FALSE, Float32.valueOf(0.0f).logicalOr(Int16.ZERO));
    verify(Bool.TRUE, logicalOr(Int16.ONE, Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalOr(Int16.ONE, Float32.valueOf(1.0f)));

    verify(Bool.FALSE, Float32.valueOf(0.0f).logicalOr(Char8.valueOf('\0')));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), Float32.valueOf(1.0f)));

    verify(Bool.FALSE, Float32.valueOf(0.0f).logicalOr(Bool.FALSE));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Float32.valueOf(0.0f)));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Float32.valueOf(1.0f)));

    // UInt64
    verify(Bool.FALSE, logicalOr(UInt64.ZERO, UInt64.ZERO));
    verify(Bool.TRUE, logicalOr(UInt64.MAX_VALUE, UInt64.ZERO));
    verify(Bool.TRUE, logicalOr(UInt64.MAX_VALUE, UInt64.ONE));

    verify(Bool.FALSE, logicalOr(UInt64.ZERO, Int64.ZERO));
    verify(Bool.TRUE, logicalOr(Int64.ONE, UInt64.ZERO));
    verify(Bool.TRUE, logicalOr(Int64.ONE, UInt64.ONE));

    verify(Bool.FALSE, logicalOr(UInt64.ZERO, UInt32.ZERO));
    verify(Bool.TRUE, logicalOr(UInt32.MAX_VALUE, UInt64.ZERO));
    verify(Bool.TRUE, logicalOr(UInt32.MAX_VALUE, UInt64.ONE));

    verify(Bool.FALSE, logicalOr(UInt64.ZERO, Int32.ZERO));
    verify(Bool.TRUE, logicalOr(Int32.ONE, UInt64.ZERO));
    verify(Bool.TRUE, logicalOr(Int32.ONE, UInt64.ONE));

    verify(Bool.FALSE, logicalOr(UInt64.ZERO, Int16.ZERO));
    verify(Bool.TRUE, logicalOr(Int16.ONE, UInt64.ZERO));
    verify(Bool.TRUE, logicalOr(Int16.ONE, UInt64.ONE));

    verify(Bool.FALSE, logicalOr(UInt64.ZERO, Char8.valueOf('\0')));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), UInt64.ZERO));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), UInt64.ONE));

    verify(Bool.FALSE, logicalOr(UInt64.ZERO, Bool.FALSE));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(UInt64.ZERO));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(UInt64.ONE));

    // Long
    verify(Bool.FALSE, logicalOr(Int64.ZERO, Int64.ZERO));
    verify(Bool.TRUE, logicalOr(Int64.ONE, Int64.ZERO));
    verify(Bool.TRUE, logicalOr(Int64.ONE, Int64.ONE));

    verify(Bool.FALSE, logicalOr(Int64.ZERO, UInt32.ZERO));
    verify(Bool.TRUE, logicalOr(UInt32.MAX_VALUE, Int64.ZERO));
    verify(Bool.TRUE, logicalOr(UInt32.MAX_VALUE, Int64.ONE));

    verify(Bool.FALSE, logicalOr(Int64.ZERO, Int32.ZERO));
    verify(Bool.TRUE, logicalOr(Int32.ONE, Int64.ZERO));
    verify(Bool.TRUE, logicalOr(Int32.ONE, Int64.ONE));

    verify(Bool.FALSE, logicalOr(Int64.ZERO, Int16.ZERO));
    verify(Bool.TRUE, logicalOr(Int16.ONE, Int64.ZERO));
    verify(Bool.TRUE, logicalOr(Int16.ONE, Int64.ONE));

    verify(Bool.FALSE, logicalOr(Int64.ZERO, Char8.valueOf('\0')));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), Int64.ZERO));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), Int64.ONE));

    verify(Bool.FALSE, logicalOr(Int64.ZERO, Bool.FALSE));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Int64.ZERO));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Int64.ONE));

    // UInt32
    verify(Bool.FALSE, logicalOr(UInt32.ZERO, UInt32.ZERO));
    verify(Bool.TRUE, logicalOr(UInt32.MAX_VALUE, UInt32.ZERO));
    verify(Bool.TRUE, logicalOr(UInt32.MAX_VALUE, UInt32.ONE));

    verify(Bool.FALSE, logicalOr(UInt32.ZERO, Int32.ZERO));
    verify(Bool.TRUE, logicalOr(Int32.ONE, UInt32.ZERO));
    verify(Bool.TRUE, logicalOr(Int32.ONE, UInt32.ONE));

    verify(Bool.FALSE, logicalOr(UInt32.ZERO, Int16.ZERO));
    verify(Bool.TRUE, logicalOr(Int16.ONE, UInt32.ZERO));
    verify(Bool.TRUE, logicalOr(Int16.ONE, UInt32.ONE));

    verify(Bool.FALSE, logicalOr(UInt32.ZERO, Char8.valueOf('\0')));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), UInt32.ZERO));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), UInt32.ONE));

    verify(Bool.FALSE, logicalOr(UInt32.ZERO, Bool.FALSE));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(UInt32.ZERO));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(UInt32.ONE));

    // Integer
    verify(Bool.FALSE, logicalOr(Int32.ZERO, Int32.ZERO));
    verify(Bool.TRUE, logicalOr(Int32.ONE, Int32.ZERO));
    verify(Bool.TRUE, logicalOr(Int32.ONE, Int32.ONE));

    verify(Bool.FALSE, logicalOr(Int32.ZERO, Int16.ZERO));
    verify(Bool.TRUE, logicalOr(Int16.ONE, Int32.ZERO));
    verify(Bool.TRUE, logicalOr(Int16.ONE, Int32.ONE));

    verify(Bool.FALSE, logicalOr(Int32.ZERO, Char8.valueOf('\0')));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), Int32.ZERO));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), Int32.ONE));

    verify(Bool.FALSE, logicalOr(Int32.ZERO, Bool.FALSE));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Int32.ZERO));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Int32.ONE));

    // Short
    verify(Bool.FALSE, logicalOr(Int16.ZERO, Int16.ZERO));
    verify(Bool.TRUE, logicalOr(Int16.ONE, Int16.ZERO));
    verify(Bool.TRUE, logicalOr(Int16.ONE, Int16.ONE));

    verify(Bool.FALSE, logicalOr(Int16.ZERO, Char8.valueOf('\0')));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), Int16.ZERO));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), Int16.ONE));

    verify(Bool.FALSE, logicalOr(Int16.ZERO, Bool.FALSE));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Int16.ZERO));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Int16.ONE));

    // Character
    verify(Bool.FALSE, logicalOr(Char8.valueOf('\0'), Char8.valueOf('\0')));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), Char8.valueOf('\0')));
    verify(Bool.TRUE, logicalOr(Char8.valueOf('\1'), Char8.valueOf('\1')));

    verify(Bool.FALSE, logicalOr(Char8.valueOf('\0'), Bool.FALSE));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Char8.valueOf('\0')));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Char8.valueOf('\1')));

    // Boolean
    verify(Bool.FALSE, logicalOr(Bool.FALSE, Bool.FALSE));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Bool.FALSE));
    verify(Bool.TRUE, Bool.TRUE.logicalOr(Bool.TRUE));
  }

  @Test
  void testLogicalAnd()
  {
    // Double
    verify(Bool.FALSE, logicalAnd(Float64.valueOf(0.0), Float64.valueOf(0.0)));
    verify(Bool.FALSE, logicalAnd(Float64.valueOf(1.0), Float64.valueOf(0.0)));
    verify(Bool.FALSE, logicalAnd(Float64.valueOf(0.0), Float64.valueOf(1.0)));
    verify(Bool.TRUE, logicalAnd(Float64.valueOf(1.0), Float64.valueOf(1.0)));

    verify(Bool.FALSE, logicalAnd(Float64.valueOf(0.0), Float32.valueOf(0.0f)));
    verify(Bool.FALSE, logicalAnd(Float32.valueOf(1.0f), Float64.valueOf(0.0)));
    verify(Bool.TRUE, logicalAnd(Float32.valueOf(1.0f), Float64.valueOf(1.0)));

    verify(Bool.FALSE, logicalAnd(Float64.valueOf(0.0), UInt64.ZERO));
    verify(Bool.FALSE, logicalAnd(UInt64.MAX_VALUE, Float64.valueOf(0.0)));
    verify(Bool.TRUE, logicalAnd(UInt64.MAX_VALUE, Float64.valueOf(1.0)));

    verify(Bool.FALSE, logicalAnd(Float64.valueOf(0.0), Int64.ZERO));
    verify(Bool.FALSE, logicalAnd(Int64.ONE, Float64.valueOf(0.0)));
    verify(Bool.TRUE, logicalAnd(Int64.ONE, Float64.valueOf(1.0)));

    verify(Bool.FALSE, logicalAnd(Float64.valueOf(0.0), UInt32.ZERO));
    verify(Bool.FALSE, logicalAnd(UInt32.MAX_VALUE, Float64.valueOf(0.0)));
    verify(Bool.TRUE, logicalAnd(UInt32.MAX_VALUE, Float64.valueOf(1.0)));

    verify(Bool.FALSE, logicalAnd(Float64.valueOf(0.0), Int32.ZERO));
    verify(Bool.FALSE, logicalAnd(Int32.ONE, Float64.valueOf(0.0)));
    verify(Bool.TRUE, logicalAnd(Int32.ONE, Float64.valueOf(1.0)));

    verify(Bool.FALSE, logicalAnd(Float64.valueOf(0.0), Char8.valueOf('\0')));
    verify(Bool.FALSE, logicalAnd(Char8.valueOf('\1'), Float64.valueOf(0.0)));
    verify(Bool.TRUE, logicalAnd(Char8.valueOf('\1'), Float64.valueOf(1.0)));

    verify(Bool.FALSE, logicalAnd(Float64.valueOf(0.0), Bool.FALSE));
    verify(Bool.FALSE, logicalAnd(Bool.TRUE, Float64.valueOf(0.0)));
    verify(Bool.TRUE, logicalAnd(Bool.TRUE, Float64.valueOf(1.0)));

    // Float
    verify(Bool.FALSE, logicalAnd(Float32.valueOf(0.0f), Float32.valueOf(0.0f)));
    verify(Bool.FALSE, logicalAnd(Float32.valueOf(1.0f), Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalAnd(Float32.valueOf(1.0f), Float32.valueOf(1.0f)));

    verify(Bool.FALSE, logicalAnd(Float32.valueOf(0.0f), UInt64.ZERO));
    verify(Bool.FALSE, logicalAnd(UInt64.MAX_VALUE, Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalAnd(UInt64.MAX_VALUE, Float32.valueOf(1.0f)));

    verify(Bool.FALSE, logicalAnd(Float32.valueOf(0.0f), Int64.ZERO));
    verify(Bool.FALSE, logicalAnd(Int64.ONE, Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalAnd(Int64.ONE, Float32.valueOf(1.0f)));

    verify(Bool.FALSE, logicalAnd(Float32.valueOf(0.0f), UInt32.ZERO));
    verify(Bool.FALSE, logicalAnd(UInt32.MAX_VALUE, Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalAnd(UInt32.MAX_VALUE, Float32.valueOf(1.0f)));

    verify(Bool.FALSE, logicalAnd(Float32.valueOf(0.0f), Int32.ZERO));
    verify(Bool.FALSE, logicalAnd(Int32.ONE, Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalAnd(Int32.ONE, Float32.valueOf(1.0f)));

    verify(Bool.FALSE, logicalAnd(Float32.valueOf(0.0f), Char8.valueOf('\0')));
    verify(Bool.FALSE, logicalAnd(Char8.valueOf('\1'), Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalAnd(Char8.valueOf('\1'), Float32.valueOf(1.0f)));

    verify(Bool.FALSE, logicalAnd(Float32.valueOf(0.0f), Bool.FALSE));
    verify(Bool.FALSE, logicalAnd(Bool.TRUE, Float32.valueOf(0.0f)));
    verify(Bool.TRUE, logicalAnd(Bool.TRUE, Float32.valueOf(1.0f)));

    // UInt64
    verify(Bool.FALSE, logicalAnd(UInt64.ZERO, UInt64.ZERO));
    verify(Bool.FALSE, logicalAnd(UInt64.MAX_VALUE, UInt64.ZERO));
    verify(Bool.TRUE, logicalAnd(UInt64.MAX_VALUE, UInt64.ONE));

    verify(Bool.FALSE, logicalAnd(UInt64.ZERO, Int64.ZERO));
    verify(Bool.FALSE, logicalAnd(Int64.ONE, UInt64.ZERO));
    verify(Bool.TRUE, logicalAnd(Int64.ONE, UInt64.ONE));

    verify(Bool.FALSE, logicalAnd(UInt64.ZERO, UInt32.ZERO));
    verify(Bool.FALSE, logicalAnd(UInt32.MAX_VALUE, UInt64.ZERO));
    verify(Bool.TRUE, logicalAnd(UInt32.MAX_VALUE, UInt64.ONE));

    verify(Bool.FALSE, logicalAnd(UInt64.ZERO, Int32.ZERO));
    verify(Bool.FALSE, logicalAnd(Int32.ONE, UInt64.ZERO));
    verify(Bool.TRUE, logicalAnd(Int32.ONE, UInt64.ONE));

    verify(Bool.FALSE, logicalAnd(UInt64.ZERO, Char8.valueOf('\0')));
    verify(Bool.FALSE, logicalAnd(Char8.valueOf('\1'), UInt64.ZERO));
    verify(Bool.TRUE, logicalAnd(Char8.valueOf('\1'), UInt64.ONE));

    verify(Bool.FALSE, logicalAnd(UInt64.ZERO, Bool.FALSE));
    verify(Bool.FALSE, logicalAnd(Bool.TRUE, UInt64.ZERO));
    verify(Bool.TRUE, logicalAnd(Bool.TRUE, UInt64.ONE));

    // Long
    verify(Bool.FALSE, logicalAnd(Int64.ZERO, Int64.ZERO));
    verify(Bool.FALSE, logicalAnd(Int64.ONE, Int64.ZERO));
    verify(Bool.TRUE, logicalAnd(Int64.ONE, Int64.ONE));

    verify(Bool.FALSE, logicalAnd(Int64.ZERO, UInt32.ZERO));
    verify(Bool.FALSE, logicalAnd(UInt32.MAX_VALUE, Int64.ZERO));
    verify(Bool.TRUE, logicalAnd(UInt32.MAX_VALUE, Int64.ONE));

    verify(Bool.FALSE, logicalAnd(Int64.ZERO, Int32.ZERO));
    verify(Bool.FALSE, logicalAnd(Int32.ONE, Int64.ZERO));
    verify(Bool.TRUE, logicalAnd(Int32.ONE, Int64.ONE));

    verify(Bool.FALSE, logicalAnd(Int64.ZERO, Char8.valueOf('\0')));
    verify(Bool.FALSE, logicalAnd(Char8.valueOf('\1'), Int64.ZERO));
    verify(Bool.TRUE, logicalAnd(Char8.valueOf('\1'), Int64.ONE));

    verify(Bool.FALSE, logicalAnd(Int64.ZERO, Bool.FALSE));
    verify(Bool.FALSE, logicalAnd(Bool.TRUE, Int64.ZERO));
    verify(Bool.TRUE, logicalAnd(Bool.TRUE, Int64.ONE));

    // UInt32
    verify(Bool.FALSE, logicalAnd(UInt32.ZERO, UInt32.ZERO));
    verify(Bool.FALSE, logicalAnd(UInt32.MAX_VALUE, UInt32.ZERO));
    verify(Bool.TRUE, logicalAnd(UInt32.MAX_VALUE, UInt32.ONE));

    verify(Bool.FALSE, logicalAnd(UInt32.ZERO, Int32.ZERO));
    verify(Bool.FALSE, logicalAnd(Int32.ONE, UInt32.ZERO));
    verify(Bool.TRUE, logicalAnd(Int32.ONE, UInt32.ONE));

    verify(Bool.FALSE, logicalAnd(UInt32.ZERO, Char8.valueOf('\0')));
    verify(Bool.FALSE, logicalAnd(Char8.valueOf('\1'), UInt32.ZERO));
    verify(Bool.TRUE, logicalAnd(Char8.valueOf('\1'), UInt32.ONE));

    verify(Bool.FALSE, logicalAnd(UInt32.ZERO, Bool.FALSE));
    verify(Bool.FALSE, logicalAnd(Bool.TRUE, UInt32.ZERO));
    verify(Bool.TRUE, logicalAnd(Bool.TRUE, UInt32.ONE));

    // Integer
    verify(Bool.FALSE, logicalAnd(Int32.ZERO, Int32.ZERO));
    verify(Bool.FALSE, logicalAnd(Int32.ONE, Int32.ZERO));
    verify(Bool.TRUE, logicalAnd(Int32.ONE, Int32.ONE));

    verify(Bool.FALSE, logicalAnd(Int32.ZERO, Char8.valueOf('\0')));
    verify(Bool.FALSE, logicalAnd(Char8.valueOf('\1'), Int32.ZERO));
    verify(Bool.TRUE, logicalAnd(Char8.valueOf('\1'), Int32.ONE));

    verify(Bool.FALSE, logicalAnd(Int32.ZERO, Bool.FALSE));
    verify(Bool.FALSE, logicalAnd(Bool.TRUE, Int32.ZERO));
    verify(Bool.TRUE, logicalAnd(Bool.TRUE, Int32.ONE));

    // Character
    verify(Bool.FALSE, logicalAnd(Char8.valueOf('\0'), Char8.valueOf('\0')));
    verify(Bool.FALSE, logicalAnd(Char8.valueOf('\1'), Char8.valueOf('\0')));
    verify(Bool.TRUE, logicalAnd(Char8.valueOf('\1'), Char8.valueOf('\1')));

    verify(Bool.FALSE, logicalAnd(Char8.valueOf('\0'), Bool.FALSE));
    verify(Bool.FALSE, logicalAnd(Bool.TRUE, Char8.valueOf('\0')));
    verify(Bool.TRUE, logicalAnd(Bool.TRUE, Char8.valueOf('\1')));

    // Boolean
    verify(Bool.FALSE, logicalAnd(Bool.FALSE, Bool.FALSE));
    verify(Bool.FALSE, logicalAnd(Bool.TRUE, Bool.FALSE));
    verify(Bool.TRUE, logicalAnd(Bool.TRUE, Bool.TRUE));
  }

  @Test
  void testBitwiseOr()
  {

    // UInt64
    verify(UInt64.ZERO, UInt64.ZERO.or(UInt64.ZERO));
    verify(UInt64.MAX_VALUE, UInt64.MAX_VALUE.or(UInt64.ZERO));
    verify(UInt64.MAX_VALUE, bitwiseOr(UInt64.MAX_VALUE, UInt64.ONE));

    verify(UInt64.ZERO, bitwiseOr(UInt64.ZERO, Int64.ZERO));
    verify(UInt64.ONE, bitwiseOr(Int64.ONE, UInt64.ZERO));
    verify(UInt64.ONE, bitwiseOr(Int64.ONE, UInt64.ONE));

    verify(UInt64.ZERO, bitwiseOr(UInt64.ZERO, UInt32.ZERO));
    verify(UInt64.ONE, bitwiseOr(UInt32.ONE, UInt64.ZERO));
    verify(UInt64.ONE, bitwiseOr(UInt32.ONE, UInt64.ONE));

    verify(UInt64.ZERO, bitwiseOr(UInt64.ZERO, Int32.ZERO));
    verify(UInt64.ONE, bitwiseOr(Int32.ONE, UInt64.ZERO));
    verify(UInt64.ONE, bitwiseOr(Int32.ONE, UInt64.ONE));

    verify(UInt64.ZERO, bitwiseOr(UInt64.ZERO, Char8.valueOf('\0')));
    verify(UInt64.ONE, bitwiseOr(Char8.valueOf('\1'), UInt64.ZERO));
    verify(UInt64.ONE, bitwiseOr(Char8.valueOf('\1'), UInt64.ONE));

    verify(UInt64.ZERO, bitwiseOr(UInt64.ZERO, Bool.FALSE));
    verify(UInt64.ONE, bitwiseOr(Bool.TRUE, UInt64.ZERO));
    verify(UInt64.ONE, bitwiseOr(Bool.TRUE, UInt64.ONE));

    // Long
    verify(Int64.ZERO, bitwiseOr(Int64.ZERO, Int64.ZERO));
    verify(Int64.ONE, bitwiseOr(Int64.ONE, Int64.ZERO));
    verify(Int64.ONE, bitwiseOr(Int64.ONE, Int64.ONE));

    verify(Int64.ZERO, bitwiseOr(Int64.ZERO, UInt32.ZERO));
    verify(Int64.valueOf(UInt32.MAX_VALUE.getValue()), bitwiseOr(UInt32.MAX_VALUE, Int64.ZERO));
    verify(Int64.valueOf(UInt32.MAX_VALUE.getValue()), bitwiseOr(UInt32.MAX_VALUE, Int64.ONE));

    verify(Int64.ZERO, bitwiseOr(Int64.ZERO, Int32.ZERO));
    verify(Int64.ONE, bitwiseOr(Int32.ONE, Int64.ZERO));
    verify(Int64.ONE, bitwiseOr(Int32.ONE, Int64.ONE));

    verify(Int64.ZERO, bitwiseOr(Int64.ZERO, Char8.valueOf('\0')));
    verify(Int64.ONE, bitwiseOr(Char8.valueOf('\1'), Int64.ZERO));
    verify(Int64.ONE, bitwiseOr(Char8.valueOf('\1'), Int64.ONE));

    verify(Int64.ZERO, bitwiseOr(Int64.ZERO, Bool.FALSE));
    verify(Int64.ONE, bitwiseOr(Bool.TRUE, Int64.ZERO));
    verify(Int64.ONE, bitwiseOr(Bool.TRUE, Int64.ONE));

    // UInt32
    verify(UInt32.ZERO, bitwiseOr(UInt32.ZERO, UInt32.ZERO));
    verify(UInt32.MAX_VALUE, bitwiseOr(UInt32.MAX_VALUE, UInt32.ZERO));
    verify(UInt32.MAX_VALUE, bitwiseOr(UInt32.MAX_VALUE, UInt32.ONE));

    verify(UInt32.ZERO, bitwiseOr(UInt32.ZERO, Int32.ZERO));
    verify(UInt32.ONE, bitwiseOr(Int32.ONE, UInt32.ZERO));
    verify(UInt32.ONE, bitwiseOr(Int32.ONE, UInt32.ONE));

    verify(UInt32.ZERO, bitwiseOr(UInt32.ZERO, Char8.valueOf('\0')));
    verify(UInt32.ONE, bitwiseOr(Char8.valueOf('\1'), UInt32.ZERO));
    verify(UInt32.ONE, bitwiseOr(Char8.valueOf('\1'), UInt32.ONE));

    verify(UInt32.ZERO, bitwiseOr(UInt32.ZERO, Bool.FALSE));
    verify(UInt32.ONE, bitwiseOr(Bool.TRUE, UInt32.ZERO));
    verify(UInt32.ONE, bitwiseOr(Bool.TRUE, UInt32.ONE));

    // Integer
    verify(Int32.ZERO, bitwiseOr(Int32.ZERO, Int32.ZERO));
    verify(Int32.ONE, bitwiseOr(Int32.ONE, Int32.ZERO));
    verify(Int32.ONE, bitwiseOr(Int32.ONE, Int32.ONE));

    verify(Int32.ZERO, bitwiseOr(Int32.ZERO, Char8.valueOf('\0')));
    verify(Int32.ONE, bitwiseOr(Char8.valueOf('\1'), Int32.ZERO));
    verify(Int32.ONE, bitwiseOr(Char8.valueOf('\1'), Int32.ONE));

    verify(Int32.ZERO, bitwiseOr(Int32.ZERO, Bool.FALSE));
    verify(Int32.ONE, bitwiseOr(Bool.TRUE, Int32.ZERO));
    verify(Int32.ONE, bitwiseOr(Bool.TRUE, Int32.ONE));

    // Character
    verify(Int32.ZERO, bitwiseOr(Char8.valueOf('\0'), Char8.valueOf('\0')));
    verify(Int32.ONE, bitwiseOr(Char8.valueOf('\1'), Char8.valueOf('\0')));
    verify(Int32.ONE, bitwiseOr(Char8.valueOf('\1'), Char8.valueOf('\1')));

    verify(Int32.ZERO, bitwiseOr(Char8.valueOf('\0'), Bool.FALSE));
    verify(Int32.ONE, bitwiseOr(Bool.TRUE, Char8.valueOf('\0')));
    verify(Int32.ONE, bitwiseOr(Bool.TRUE, Char8.valueOf('\1')));

    // Boolean
    verify(Int32.ZERO, bitwiseOr(Bool.FALSE, Bool.FALSE));
    verify(Int32.ONE, bitwiseOr(Bool.TRUE, Bool.FALSE));
    verify(Int32.ONE, bitwiseOr(Bool.TRUE, Bool.TRUE));
  }

  @Test
  void testBitwiseAnd()
  {

    // UInt64
    verify(UInt64.ZERO, bitwiseAnd(UInt64.ZERO, UInt64.ZERO));
    verify(UInt64.ZERO, bitwiseAnd(UInt64.MAX_VALUE, UInt64.ZERO));
    verify(UInt64.ONE, bitwiseAnd(UInt64.MAX_VALUE, UInt64.ONE));

    verify(UInt64.ZERO, bitwiseAnd(UInt64.ZERO, Int64.ZERO));
    verify(UInt64.ZERO, bitwiseAnd(Int64.ONE, UInt64.ZERO));
    verify(UInt64.ONE, bitwiseAnd(Int64.valueOf(101L), UInt64.ONE));

    verify(UInt64.ZERO, bitwiseAnd(UInt64.ZERO, UInt32.ZERO));
    verify(UInt64.ZERO, bitwiseAnd(UInt32.ONE, UInt64.ZERO));
    verify(UInt64.ONE, bitwiseAnd(UInt32.ONE, UInt64.ONE));

    verify(UInt64.ZERO, bitwiseAnd(UInt64.ZERO, Int32.ZERO));
    verify(UInt64.ZERO, bitwiseAnd(Int32.ONE, UInt64.ZERO));
    verify(UInt64.ONE, bitwiseAnd(Int32.ONE, UInt64.ONE));

    verify(UInt64.ZERO, bitwiseAnd(UInt64.ZERO, Char8.valueOf('\0')));
    verify(UInt64.ZERO, bitwiseAnd(Char8.valueOf('\1'), UInt64.ZERO));
    verify(UInt64.ONE, bitwiseAnd(Char8.valueOf('\1'), UInt64.ONE));

    verify(UInt64.ZERO, bitwiseAnd(UInt64.ZERO, Bool.FALSE));
    verify(UInt64.ZERO, bitwiseAnd(Bool.TRUE, UInt64.ZERO));
    verify(UInt64.ONE, bitwiseAnd(Bool.TRUE, UInt64.ONE));

    // Long
    verify(Int64.ZERO, bitwiseAnd(Int64.ZERO, Int64.ZERO));
    verify(Int64.ZERO, bitwiseAnd(Int64.ONE, Int64.ZERO));
    verify(Int64.ONE, bitwiseAnd(Int64.ONE, Int64.ONE));

    verify(Int64.ZERO, bitwiseAnd(Int64.ZERO, UInt32.ZERO));
    verify(Int64.ZERO, bitwiseAnd(UInt32.MAX_VALUE, Int64.ZERO));
    verify(Int64.ONE, bitwiseAnd(UInt32.MAX_VALUE, Int64.ONE));

    verify(Int64.ZERO, bitwiseAnd(Int64.ZERO, Int32.ZERO));
    verify(Int64.ZERO, bitwiseAnd(Int32.ONE, Int64.ZERO));
    verify(Int64.ONE, bitwiseAnd(Int32.ONE, Int64.ONE));

    verify(Int64.ZERO, bitwiseAnd(Int64.ZERO, Char8.valueOf('\0')));
    verify(Int64.ZERO, bitwiseAnd(Char8.valueOf('\1'), Int64.ZERO));
    verify(Int64.ONE, bitwiseAnd(Char8.valueOf('\1'), Int64.ONE));

    verify(Int64.ZERO, bitwiseAnd(Int64.ZERO, Bool.FALSE));
    verify(Int64.ZERO, bitwiseAnd(Bool.TRUE, Int64.ZERO));
    verify(Int64.ONE, bitwiseAnd(Bool.TRUE, Int64.ONE));

    // UInt32
    verify(UInt32.ZERO, bitwiseAnd(UInt32.ZERO, UInt32.ZERO));
    verify(UInt32.ZERO, bitwiseAnd(UInt32.MAX_VALUE, UInt32.ZERO));
    verify(UInt32.ONE, bitwiseAnd(UInt32.MAX_VALUE, UInt32.ONE));

    verify(UInt32.ZERO, bitwiseAnd(UInt32.ZERO, Int32.ZERO));
    verify(UInt32.ZERO, bitwiseAnd(Int32.ONE, UInt32.ZERO));
    verify(UInt32.ONE, bitwiseAnd(Int32.ONE, UInt32.ONE));

    verify(UInt32.ZERO, bitwiseAnd(UInt32.ZERO, Char8.valueOf('\0')));
    verify(UInt32.ZERO, bitwiseAnd(Char8.valueOf('\1'), UInt32.ZERO));
    verify(UInt32.ONE, bitwiseAnd(Char8.valueOf('\1'), UInt32.ONE));

    verify(UInt32.ZERO, bitwiseAnd(UInt32.ZERO, Bool.FALSE));
    verify(UInt32.ZERO, bitwiseAnd(Bool.TRUE, UInt32.ZERO));
    verify(UInt32.ONE, bitwiseAnd(Bool.TRUE, UInt32.ONE));

    // Integer
    verify(Int32.ZERO, bitwiseAnd(Int32.ZERO, Int32.ZERO));
    verify(Int32.ZERO, bitwiseAnd(Int32.ONE, Int32.ZERO));
    verify(Int32.ONE, bitwiseAnd(Int32.ONE, Int32.ONE));

    verify(Int32.ZERO, bitwiseAnd(Int32.ZERO, Char8.valueOf('\0')));
    verify(Int32.ZERO, bitwiseAnd(Char8.valueOf('\1'), Int32.ZERO));
    verify(Int32.ONE, bitwiseAnd(Char8.valueOf('\1'), Int32.ONE));

    verify(Int32.ZERO, bitwiseAnd(Int32.ZERO, Bool.FALSE));
    verify(Int32.ZERO, bitwiseAnd(Bool.TRUE, Int32.ZERO));
    verify(Int32.ONE, bitwiseAnd(Bool.TRUE, Int32.ONE));

    // Character
    verify(Int32.ZERO, bitwiseAnd(Char8.valueOf('\0'), Char8.valueOf('\0')));
    verify(Int32.ZERO, bitwiseAnd(Char8.valueOf('\1'), Char8.valueOf('\0')));
    verify(Int32.ONE, bitwiseAnd(Char8.valueOf('\1'), Char8.valueOf('\1')));

    verify(Int32.ZERO, bitwiseAnd(Char8.valueOf('\0'), Bool.FALSE));
    verify(Int32.ZERO, bitwiseAnd(Bool.TRUE, Char8.valueOf('\0')));
    verify(Int32.ONE, bitwiseAnd(Bool.TRUE, Char8.valueOf('\1')));

    // Boolean
    verify(Int32.ZERO, bitwiseAnd(Bool.FALSE, Bool.FALSE));
    verify(Int32.ZERO, bitwiseAnd(Bool.TRUE, Bool.FALSE));
    verify(Int32.ONE, bitwiseAnd(Bool.TRUE, Bool.TRUE));
  }

  @Test
  void testBitwiseXor()
  {

    // UInt64
    verify(UInt64.ZERO, bitwiseXor(UInt64.ZERO, UInt64.ZERO));
    verify(UInt64.MAX_VALUE, bitwiseXor(UInt64.MAX_VALUE, UInt64.ZERO));
    verify(UInt64.ZERO, bitwiseXor(UInt64.ONE, UInt64.ONE));

    verify(UInt64.ZERO, bitwiseXor(UInt64.ZERO, Int64.ZERO));
    verify(UInt64.ONE, bitwiseXor(Int64.ONE, UInt64.ZERO));
    verify(UInt64.ZERO, bitwiseXor(Int64.ONE, UInt64.ONE));

    verify(UInt64.ZERO, bitwiseXor(UInt64.ZERO, UInt32.ZERO));
    verify(UInt64.ONE, bitwiseXor(UInt32.ONE, UInt64.ZERO));
    verify(UInt64.ZERO, bitwiseXor(UInt32.ONE, UInt64.ONE));

    verify(UInt64.ZERO, bitwiseXor(UInt64.ZERO, Int32.ZERO));
    verify(UInt64.ONE, bitwiseXor(Int32.ONE, UInt64.ZERO));
    verify(UInt64.ZERO, bitwiseXor(Int32.ONE, UInt64.ONE));

    verify(UInt64.ZERO, bitwiseXor(UInt64.ZERO, Char8.valueOf('\0')));
    verify(UInt64.ONE, bitwiseXor(Char8.valueOf('\1'), UInt64.ZERO));
    verify(UInt64.ZERO, bitwiseXor(Char8.valueOf('\1'), UInt64.ONE));

    verify(UInt64.ZERO, bitwiseXor(UInt64.ZERO, Bool.FALSE));
    verify(UInt64.ONE, bitwiseXor(Bool.TRUE, UInt64.ZERO));
    verify(UInt64.ZERO, bitwiseXor(Bool.TRUE, UInt64.ONE));

    // Long
    verify(Int64.ZERO, bitwiseXor(Int64.ZERO, Int64.ZERO));
    verify(Int64.ONE, bitwiseXor(Int64.ONE, Int64.ZERO));
    verify(Int64.ZERO, bitwiseXor(Int64.ONE, Int64.ONE));

    verify(Int64.ZERO, bitwiseXor(Int64.ZERO, UInt32.ZERO));
    verify(Int64.ONE, bitwiseXor(UInt32.ONE, Int64.ZERO));
    verify(Int64.ZERO, bitwiseXor(UInt32.ONE, Int64.ONE));

    verify(Int64.ZERO, bitwiseXor(Int64.ZERO, Int32.ZERO));
    verify(Int64.ONE, bitwiseXor(Int32.ONE, Int64.ZERO));
    verify(Int64.ZERO, bitwiseXor(Int32.ONE, Int64.ONE));

    verify(Int64.ZERO, bitwiseXor(Int64.ZERO, Char8.valueOf('\0')));
    verify(Int64.ONE, bitwiseXor(Char8.valueOf('\1'), Int64.ZERO));
    verify(Int64.ZERO, bitwiseXor(Char8.valueOf('\1'), Int64.ONE));

    verify(Int64.ZERO, bitwiseXor(Int64.ZERO, Bool.FALSE));
    verify(Int64.ONE, bitwiseXor(Bool.TRUE, Int64.ZERO));
    verify(Int64.ZERO, bitwiseXor(Bool.TRUE, Int64.ONE));

    // UInt32
    verify(UInt32.ZERO, bitwiseXor(UInt32.ZERO, UInt32.ZERO));
    verify(UInt32.MAX_VALUE, bitwiseXor(UInt32.MAX_VALUE, UInt32.ZERO));
    verify(UInt32.ZERO, bitwiseXor(UInt32.ONE, UInt32.ONE));

    verify(UInt32.ZERO, bitwiseXor(UInt32.ZERO, Int32.ZERO));
    verify(UInt32.ONE, bitwiseXor(Int32.ONE, UInt32.ZERO));
    verify(UInt32.ZERO, bitwiseXor(Int32.ONE, UInt32.ONE));

    verify(UInt32.ZERO, bitwiseXor(UInt32.ZERO, Char8.valueOf('\0')));
    verify(UInt32.ONE, bitwiseXor(Char8.valueOf('\1'), UInt32.ZERO));
    verify(UInt32.ZERO, bitwiseXor(Char8.valueOf('\1'), UInt32.ONE));

    verify(UInt32.ZERO, bitwiseXor(UInt32.ZERO, Bool.FALSE));
    verify(UInt32.ONE, bitwiseXor(Bool.TRUE, UInt32.ZERO));
    verify(UInt32.ZERO, bitwiseXor(Bool.TRUE, UInt32.ONE));

    // Integer
    verify(Int32.ZERO, bitwiseXor(Int32.ZERO, Int32.ZERO));
    verify(Int32.ONE, bitwiseXor(Int32.ONE, Int32.ZERO));
    verify(Int32.ZERO, bitwiseXor(Int32.ONE, Int32.ONE));

    verify(Int32.ZERO, bitwiseXor(Int32.ZERO, Char8.valueOf('\0')));
    verify(Int32.ONE, bitwiseXor(Char8.valueOf('\1'), Int32.ZERO));
    verify(Int32.ZERO, bitwiseXor(Char8.valueOf('\1'), Int32.ONE));

    verify(Int32.ZERO, bitwiseXor(Int32.ZERO, Bool.FALSE));
    verify(Int32.ONE, bitwiseXor(Bool.TRUE, Int32.ZERO));
    verify(Int32.ZERO, bitwiseXor(Bool.TRUE, Int32.ONE));

    // Character
    verify(Int32.ZERO, bitwiseXor(Char8.valueOf('\0'), Char8.valueOf('\0')));
    verify(Int32.ONE, bitwiseXor(Char8.valueOf('\1'), Char8.valueOf('\0')));
    verify(Int32.ZERO, bitwiseXor(Char8.valueOf('\1'), Char8.valueOf('\1')));

    verify(Int32.ZERO, bitwiseXor(Char8.valueOf('\0'), Bool.FALSE));
    verify(Int32.ONE, bitwiseXor(Bool.TRUE, Char8.valueOf('\0')));
    verify(Int32.ZERO, bitwiseXor(Bool.TRUE, Char8.valueOf('\1')));

    // Boolean
    verify(Int32.ZERO, bitwiseXor(Bool.FALSE, Bool.FALSE));
    verify(Int32.ONE, bitwiseXor(Bool.TRUE, Bool.FALSE));
    verify(Int32.ZERO, bitwiseXor(Bool.TRUE, Bool.TRUE));
  }

  @Test
  void testAddition()
  {
    // Double
    verify(Float64.valueOf(0.0), addition(Float64.valueOf(0.0), Float64.valueOf(0.0)));
    verify(Float64.valueOf(1.0), addition(Float64.valueOf(1.0), Float64.valueOf(0.0)));
    verify(Float64.valueOf(1.0), addition(Float64.valueOf(0.0), Float64.valueOf(1.0)));
    verify(Float64.valueOf(2.0), addition(Float64.valueOf(1.0), Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), addition(Float64.valueOf(0.0), Float32.valueOf(0.0f)));
    verify(Float64.valueOf(1.0), addition(Float32.valueOf(1.0f), Float64.valueOf(0.0)));
    verify(Float64.valueOf(2.0), addition(Float32.valueOf(1.0f), Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), addition(Float64.valueOf(0.0), UInt64.ZERO));
    verify(Float64.valueOf(1.0), addition(UInt64.ONE, Float64.valueOf(0.0)));
    verify(Float64.valueOf(2.0), addition(UInt64.ONE, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), addition(Float64.valueOf(0.0), Int64.ZERO));
    verify(Float64.valueOf(1.0), addition(Int64.ONE, Float64.valueOf(0.0)));
    verify(Float64.valueOf(2.0), addition(Int64.ONE, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), addition(Float64.valueOf(0.0), UInt32.ZERO));
    verify(Float64.valueOf(1.0), addition(UInt32.ONE, Float64.valueOf(0.0)));
    verify(Float64.valueOf(2.0), addition(UInt32.ONE, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), addition(Float64.valueOf(0.0), Int32.ZERO));
    verify(Float64.valueOf(1.0), addition(Int32.ONE, Float64.valueOf(0.0)));
    verify(Float64.valueOf(2.0), addition(Int32.ONE, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), addition(Float64.valueOf(0.0), Char8.valueOf('\0')));
    verify(Float64.valueOf(1.0), addition(Char8.valueOf('\1'), Float64.valueOf(0.0)));
    verify(Float64.valueOf(2.0), addition(Char8.valueOf('\1'), Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), addition(Float64.valueOf(0.0), Bool.FALSE));
    verify(Float64.valueOf(1.0), addition(Bool.TRUE, Float64.valueOf(0.0)));
    verify(Float64.valueOf(2.0), addition(Bool.TRUE, Float64.valueOf(1.0)));

    // Float
    verify(Float32.valueOf(0.0f), addition(Float32.valueOf(0.0f), Float32.valueOf(0.0f)));
    verify(Float32.valueOf(1.0f), addition(Float32.valueOf(1.0f), Float32.valueOf(0.0f)));
    verify(Float32.valueOf(2.0f), addition(Float32.valueOf(1.0f), Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), addition(Float32.valueOf(0.0f), UInt64.ZERO));
    verify(Float32.valueOf(1.0f), addition(UInt64.ONE, Float32.valueOf(0.0f)));
    verify(Float32.valueOf(2.0f), addition(UInt64.ONE, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), addition(Float32.valueOf(0.0f), Int64.ZERO));
    verify(Float32.valueOf(1.0f), addition(Int64.ONE, Float32.valueOf(0.0f)));
    verify(Float32.valueOf(2.0f), addition(Int64.ONE, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), addition(Float32.valueOf(0.0f), UInt32.ZERO));
    verify(Float32.valueOf(1.0f), addition(UInt32.ONE, Float32.valueOf(0.0f)));
    verify(Float32.valueOf(2.0f), addition(UInt32.ONE, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), addition(Float32.valueOf(0.0f), Int32.ZERO));
    verify(Float32.valueOf(1.0f), addition(Int32.ONE, Float32.valueOf(0.0f)));
    verify(Float32.valueOf(2.0f), addition(Int32.ONE, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), addition(Float32.valueOf(0.0f), Char8.valueOf('\0')));
    verify(Float32.valueOf(1.0f), addition(Char8.valueOf('\1'), Float32.valueOf(0.0f)));
    verify(Float32.valueOf(2.0f), addition(Char8.valueOf('\1'), Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), addition(Float32.valueOf(0.0f), Bool.FALSE));
    verify(Float32.valueOf(1.0f), addition(Bool.TRUE, Float32.valueOf(0.0f)));
    verify(Float32.valueOf(2.0f), addition(Bool.TRUE, Float32.valueOf(1.0f)));

    // UInt64
    verify(UInt64.ZERO, addition(UInt64.ZERO, UInt64.ZERO));
    verify(UInt64.MAX_VALUE, addition(UInt64.MAX_VALUE, UInt64.ZERO));
    verify(UInt64.valueOf(2), addition(UInt64.ONE, UInt64.ONE));

    verify(UInt64.ZERO, addition(UInt64.ZERO, Int64.ZERO));
    verify(UInt64.ONE, addition(Int64.ONE, UInt64.ZERO));
    verify(UInt64.valueOf(2), addition(Int64.ONE, UInt64.ONE));

    verify(UInt64.ZERO, addition(UInt64.ZERO, UInt32.ZERO));
    verify(UInt64.ONE, addition(UInt32.ONE, UInt64.ZERO));
    verify(UInt64.valueOf(2), addition(UInt32.ONE, UInt64.ONE));

    verify(UInt64.ZERO, addition(UInt64.ZERO, Int32.ZERO));
    verify(UInt64.ONE, addition(Int32.ONE, UInt64.ZERO));
    verify(UInt64.valueOf(2), addition(Int32.ONE, UInt64.ONE));

    verify(UInt64.ZERO, addition(UInt64.ZERO, Char8.valueOf('\0')));
    verify(UInt64.ONE, addition(Char8.valueOf('\1'), UInt64.ZERO));
    verify(UInt64.valueOf(2), addition(Char8.valueOf('\1'), UInt64.ONE));

    verify(UInt64.ZERO, addition(UInt64.ZERO, Bool.FALSE));
    verify(UInt64.ONE, addition(Bool.TRUE, UInt64.ZERO));
    verify(UInt64.valueOf(2), addition(Bool.TRUE, UInt64.ONE));

    // Long
    verify(Int64.ZERO, addition(Int64.ZERO, Int64.ZERO));
    verify(Int64.ONE, addition(Int64.ONE, Int64.ZERO));
    verify(Int64.valueOf(2L), addition(Int64.ONE, Int64.ONE));

    verify(Int64.ZERO, addition(Int64.ZERO, UInt32.ZERO));
    verify(Int64.ONE, addition(UInt32.ONE, Int64.ZERO));
    verify(Int64.valueOf(2L), addition(UInt32.ONE, Int64.ONE));

    verify(Int64.ZERO, addition(Int64.ZERO, Int32.ZERO));
    verify(Int64.ONE, addition(Int32.ONE, Int64.ZERO));
    verify(Int64.valueOf(2L), addition(Int32.ONE, Int64.ONE));

    verify(Int64.ZERO, addition(Int64.ZERO, Char8.valueOf('\0')));
    verify(Int64.ONE, addition(Char8.valueOf('\1'), Int64.ZERO));
    verify(Int64.valueOf(2L), addition(Char8.valueOf('\1'), Int64.ONE));

    verify(Int64.ZERO, addition(Int64.ZERO, Bool.FALSE));
    verify(Int64.ONE, addition(Bool.TRUE, Int64.ZERO));
    verify(Int64.valueOf(2L), addition(Bool.TRUE, Int64.ONE));

    // UInt32
    verify(UInt32.ZERO, addition(UInt32.ZERO, UInt32.ZERO));
    verify(UInt32.MAX_VALUE, addition(UInt32.MAX_VALUE, UInt32.ZERO));
    verify(UInt32.valueOf(2), addition(UInt32.ONE, UInt32.ONE));

    verify(UInt32.ZERO, addition(UInt32.ZERO, Int32.ZERO));
    verify(UInt32.ONE, addition(Int32.ONE, UInt32.ZERO));
    verify(UInt32.valueOf(2), addition(Int32.ONE, UInt32.ONE));

    verify(UInt32.ZERO, addition(UInt32.ZERO, Char8.valueOf('\0')));
    verify(UInt32.ONE, addition(Char8.valueOf('\1'), UInt32.ZERO));
    verify(UInt32.valueOf(2), addition(Char8.valueOf('\1'), UInt32.ONE));

    verify(UInt32.ZERO, addition(UInt32.ZERO, Bool.FALSE));
    verify(UInt32.ONE, addition(Bool.TRUE, UInt32.ZERO));
    verify(UInt32.valueOf(2), addition(Bool.TRUE, UInt32.ONE));

    // Integer
    verify(Int32.ZERO, addition(Int32.ZERO, Int32.ZERO));
    verify(Int32.ONE, addition(Int32.ONE, Int32.ZERO));
    verify(Int32.valueOf(2), addition(Int32.ONE, Int32.ONE));

    verify(Int32.ZERO, addition(Int32.ZERO, Char8.valueOf('\0')));
    verify(Int32.ONE, addition(Char8.valueOf('\1'), Int32.ZERO));
    verify(Int32.valueOf(2), addition(Char8.valueOf('\1'), Int32.ONE));

    verify(Int32.ZERO, addition(Int32.ZERO, Bool.FALSE));
    verify(Int32.ONE, addition(Bool.TRUE, Int32.ZERO));
    verify(Int32.valueOf(2), addition(Bool.TRUE, Int32.ONE));

    // Character
    verify(Int32.ZERO, addition(Char8.valueOf('\0'), Char8.valueOf('\0')));
    verify(Int32.ONE, addition(Char8.valueOf('\1'), Char8.valueOf('\0')));
    verify(Int32.valueOf(2), addition(Char8.valueOf('\1'), Char8.valueOf('\1')));

    verify(Int32.ZERO, addition(Char8.valueOf('\0'), Bool.FALSE));
    verify(Int32.ONE, addition(Bool.TRUE, Char8.valueOf('\0')));
    verify(Int32.valueOf(2), addition(Bool.TRUE, Char8.valueOf('\1')));

    // Boolean
    verify(Int32.ZERO, addition(Bool.FALSE, Bool.FALSE));
    verify(Int32.ONE, addition(Bool.TRUE, Bool.FALSE));
    verify(Int32.valueOf(2), addition(Bool.TRUE, Bool.TRUE));
  }

  @Test
  void testSubtraction()
  {
    // Double
    verify(Float64.valueOf(0.0), subtraction(Float64.valueOf(0.0), Float64.valueOf(0.0)));
    verify(Float64.valueOf(1.0), subtraction(Float64.valueOf(1.0), Float64.valueOf(0.0)));
    verify(Float64.valueOf(-1.0), subtraction(Float64.valueOf(0.0), Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), subtraction(Float64.valueOf(1.0), Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), subtraction(Float64.valueOf(0.0), Float32.valueOf(0.0f)));
    verify(Float64.valueOf(1.0), subtraction(Float32.valueOf(1.0f), Float64.valueOf(0.0)));
    verify(Float64.valueOf(0.0), subtraction(Float32.valueOf(1.0f), Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), subtraction(Float64.valueOf(0.0), UInt64.ZERO));
    verify(Float64.valueOf(1.0), subtraction(UInt64.ONE, Float64.valueOf(0.0)));
    verify(Float64.valueOf(0.0), subtraction(UInt64.ONE, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), subtraction(Float64.valueOf(0.0), Int64.ZERO));
    verify(Float64.valueOf(1.0), subtraction(Int64.ONE, Float64.valueOf(0.0)));
    verify(Float64.valueOf(0.0), subtraction(Int64.ONE, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), subtraction(Float64.valueOf(0.0), UInt32.ZERO));
    verify(Float64.valueOf(1.0), subtraction(UInt32.ONE, Float64.valueOf(0.0)));
    verify(Float64.valueOf(0.0), subtraction(UInt32.ONE, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), subtraction(Float64.valueOf(0.0), Int32.ZERO));
    verify(Float64.valueOf(1.0), subtraction(Int32.ONE, Float64.valueOf(0.0)));
    verify(Float64.valueOf(0.0), subtraction(Int32.ONE, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), subtraction(Float64.valueOf(0.0), Char8.valueOf('\0')));
    verify(Float64.valueOf(1.0), subtraction(Char8.valueOf('\1'), Float64.valueOf(0.0)));
    verify(Float64.valueOf(0.0), subtraction(Char8.valueOf('\1'), Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), subtraction(Float64.valueOf(0.0), Bool.FALSE));
    verify(Float64.valueOf(1.0), subtraction(Bool.TRUE, Float64.valueOf(0.0)));
    verify(Float64.valueOf(0.0), subtraction(Bool.TRUE, Float64.valueOf(1.0)));

    // Float
    verify(Float32.valueOf(0.0f), subtraction(Float32.valueOf(0.0f), Float32.valueOf(0.0f)));
    verify(Float32.valueOf(1.0f), subtraction(Float32.valueOf(1.0f), Float32.valueOf(0.0f)));
    verify(Float32.valueOf(0.0f), subtraction(Float32.valueOf(1.0f), Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), subtraction(Float32.valueOf(0.0f), UInt64.ZERO));
    verify(Float32.valueOf(1.0f), subtraction(UInt64.ONE, Float32.valueOf(0.0f)));
    verify(Float32.valueOf(0.0f), subtraction(UInt64.ONE, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), subtraction(Float32.valueOf(0.0f), Int64.ZERO));
    verify(Float32.valueOf(1.0f), subtraction(Int64.ONE, Float32.valueOf(0.0f)));
    verify(Float32.valueOf(0.0f), subtraction(Int64.ONE, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), subtraction(Float32.valueOf(0.0f), UInt32.ZERO));
    verify(Float32.valueOf(1.0f), subtraction(UInt32.ONE, Float32.valueOf(0.0f)));
    verify(Float32.valueOf(0.0f), subtraction(UInt32.ONE, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), subtraction(Float32.valueOf(0.0f), Int32.ZERO));
    verify(Float32.valueOf(1.0f), subtraction(Int32.ONE, Float32.valueOf(0.0f)));
    verify(Float32.valueOf(0.0f), subtraction(Int32.ONE, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), subtraction(Float32.valueOf(0.0f), Char8.valueOf('\0')));
    verify(Float32.valueOf(1.0f), subtraction(Char8.valueOf('\1'), Float32.valueOf(0.0f)));
    verify(Float32.valueOf(0.0f), subtraction(Char8.valueOf('\1'), Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), subtraction(Float32.valueOf(0.0f), Bool.FALSE));
    verify(Float32.valueOf(1.0f), subtraction(Bool.TRUE, Float32.valueOf(0.0f)));
    verify(Float32.valueOf(0.0f), subtraction(Bool.TRUE, Float32.valueOf(1.0f)));

    // UInt64
    verify(UInt64.ZERO, subtraction(UInt64.ZERO, UInt64.ZERO));
    verify(UInt64.MAX_VALUE, subtraction(UInt64.MAX_VALUE, UInt64.ZERO));
    verify(UInt64.ZERO, subtraction(UInt64.ONE, UInt64.ONE));

    verify(UInt64.ZERO, subtraction(UInt64.ZERO, Int64.ZERO));
    verify(UInt64.ONE, subtraction(Int64.ONE, UInt64.ZERO));
    verify(UInt64.ZERO, subtraction(Int64.ONE, UInt64.ONE));

    verify(UInt64.ZERO, subtraction(UInt64.ZERO, UInt32.ZERO));
    verify(UInt64.ONE, subtraction(UInt32.ONE, UInt64.ZERO));
    verify(UInt64.ZERO, subtraction(UInt32.ONE, UInt64.ONE));

    verify(UInt64.ZERO, subtraction(UInt64.ZERO, Int32.ZERO));
    verify(UInt64.ONE, subtraction(Int32.ONE, UInt64.ZERO));
    verify(UInt64.ZERO, subtraction(Int32.ONE, UInt64.ONE));

    verify(UInt64.ZERO, subtraction(UInt64.ZERO, Char8.valueOf('\0')));
    verify(UInt64.ONE, subtraction(Char8.valueOf('\1'), UInt64.ZERO));
    verify(UInt64.ZERO, subtraction(Char8.valueOf('\1'), UInt64.ONE));

    verify(UInt64.ZERO, subtraction(UInt64.ZERO, Bool.FALSE));
    verify(UInt64.ONE, subtraction(Bool.TRUE, UInt64.ZERO));
    verify(UInt64.ZERO, subtraction(Bool.TRUE, UInt64.ONE));

    // Long
    verify(Int64.ZERO, subtraction(Int64.ZERO, Int64.ZERO));
    verify(Int64.ONE, subtraction(Int64.ONE, Int64.ZERO));
    verify(Int64.ZERO, subtraction(Int64.ONE, Int64.ONE));

    verify(Int64.ZERO, subtraction(Int64.ZERO, UInt32.ZERO));
    verify(Int64.ONE, subtraction(UInt32.ONE, Int64.ZERO));
    verify(Int64.ZERO, subtraction(UInt32.ONE, Int64.ONE));

    verify(Int64.ZERO, subtraction(Int64.ZERO, Int32.ZERO));
    verify(Int64.ONE, subtraction(Int32.ONE, Int64.ZERO));
    verify(Int64.ZERO, subtraction(Int32.ONE, Int64.ONE));

    verify(Int64.ZERO, subtraction(Int64.ZERO, Char8.valueOf('\0')));
    verify(Int64.ONE, subtraction(Char8.valueOf('\1'), Int64.ZERO));
    verify(Int64.ZERO, subtraction(Char8.valueOf('\1'), Int64.ONE));

    verify(Int64.ZERO, subtraction(Int64.ZERO, Bool.FALSE));
    verify(Int64.ONE, subtraction(Bool.TRUE, Int64.ZERO));
    verify(Int64.ZERO, subtraction(Bool.TRUE, Int64.ONE));

    // UInt32
    verify(UInt32.ZERO, subtraction(UInt32.ZERO, UInt32.ZERO));
    verify(UInt32.MAX_VALUE, subtraction(UInt32.MAX_VALUE, UInt32.ZERO));
    verify(UInt32.ZERO, subtraction(UInt32.ONE, UInt32.ONE));

    verify(UInt32.ZERO, subtraction(UInt32.ZERO, Int32.ZERO));
    verify(UInt32.ONE, subtraction(Int32.ONE, UInt32.ZERO));
    verify(UInt32.ZERO, subtraction(Int32.ONE, UInt32.ONE));

    verify(UInt32.ZERO, subtraction(UInt32.ZERO, Char8.valueOf('\0')));
    verify(UInt32.ONE, subtraction(Char8.valueOf('\1'), UInt32.ZERO));
    verify(UInt32.ZERO, subtraction(Char8.valueOf('\1'), UInt32.ONE));

    verify(UInt32.ZERO, subtraction(UInt32.ZERO, Bool.FALSE));
    verify(UInt32.ONE, subtraction(Bool.TRUE, UInt32.ZERO));
    verify(UInt32.ZERO, subtraction(Bool.TRUE, UInt32.ONE));

    // Integer
    verify(Int32.ZERO, subtraction(Int32.ZERO, Int32.ZERO));
    verify(Int32.ONE, subtraction(Int32.ONE, Int32.ZERO));
    verify(Int32.ZERO, subtraction(Int32.ONE, Int32.ONE));

    verify(Int32.ZERO, subtraction(Int32.ZERO, Char8.valueOf('\0')));
    verify(Int32.ONE, subtraction(Char8.valueOf('\1'), Int32.ZERO));
    verify(Int32.ZERO, subtraction(Char8.valueOf('\1'), Int32.ONE));

    verify(Int32.ZERO, subtraction(Int32.ZERO, Bool.FALSE));
    verify(Int32.ONE, subtraction(Bool.TRUE, Int32.ZERO));
    verify(Int32.ZERO, subtraction(Bool.TRUE, Int32.ONE));

    // Character
    verify(Int32.ZERO, subtraction(Char8.valueOf('\0'), Char8.valueOf('\0')));
    verify(Int32.ONE, subtraction(Char8.valueOf('\1'), Char8.valueOf('\0')));
    verify(Int32.ZERO, subtraction(Char8.valueOf('\1'), Char8.valueOf('\1')));

    verify(Int32.ZERO, subtraction(Char8.valueOf('\0'), Bool.FALSE));
    verify(Int32.ONE, subtraction(Bool.TRUE, Char8.valueOf('\0')));
    verify(Int32.ZERO, subtraction(Bool.TRUE, Char8.valueOf('\1')));

    // Boolean
    verify(Int32.ZERO, subtraction(Bool.FALSE, Bool.FALSE));
    verify(Int32.ONE, subtraction(Bool.TRUE, Bool.FALSE));
    verify(Int32.ZERO, subtraction(Bool.TRUE, Bool.TRUE));
  }

  @Test
  void testDivide()
  {
    // Double
    verify(Float64.valueOf(0.0), divide(Float64.valueOf(0.0), Float64.valueOf(1.0)));
    verify(Float64.valueOf(1.0), divide(Float64.valueOf(1.0), Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), divide(Float64.valueOf(0.0), Float32.valueOf(1.0f)));
    verify(Float64.valueOf(1.0), divide(Float32.valueOf(1.0f), Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), divide(Float32.valueOf(0.0f), Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), divide(Float64.valueOf(0.0), UInt64.ONE));
    verify(Float64.valueOf(1.0), divide(UInt64.ONE, Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), divide(UInt64.ZERO, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), divide(Float64.valueOf(0.0), Int64.ONE));
    verify(Float64.valueOf(1.0), divide(Int64.ONE, Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), divide(Int64.ZERO, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), divide(Float64.valueOf(0.0), UInt32.ONE));
    verify(Float64.valueOf(1.0), divide(UInt32.ONE, Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), divide(UInt32.ZERO, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), divide(Float64.valueOf(0.0), Int32.ONE));
    verify(Float64.valueOf(1.0), divide(Int32.ONE, Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), divide(Int32.ZERO, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), divide(Float64.valueOf(0.0), Char8.valueOf('\1')));
    verify(Float64.valueOf(1.0), divide(Char8.valueOf('\1'), Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), divide(Char8.valueOf('\0'), Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), divide(Float64.valueOf(0.0), Bool.TRUE));
    verify(Float64.valueOf(1.0), divide(Bool.TRUE, Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), divide(Bool.FALSE, Float64.valueOf(1.0)));

    // Float
    verify(Float32.valueOf(0.0f), divide(Float32.valueOf(0.0f), Float32.valueOf(1.0f)));
    verify(Float32.valueOf(1.0f), divide(Float32.valueOf(1.0f), Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), divide(Float32.valueOf(0.0f), UInt64.ONE));
    verify(Float32.valueOf(1.0f), divide(UInt64.ONE, Float32.valueOf(1.0f)));
    verify(Float32.valueOf(0.0f), divide(UInt64.ZERO, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), divide(Float32.valueOf(0.0f), Int64.ONE));
    verify(Float32.valueOf(1.0f), divide(Int64.ONE, Float32.valueOf(1.0f)));
    verify(Float32.valueOf(0.0f), divide(Int64.ZERO, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), divide(Float32.valueOf(0.0f), UInt32.ONE));
    verify(Float32.valueOf(1.0f), divide(UInt32.ONE, Float32.valueOf(1.0f)));
    verify(Float32.valueOf(0.0f), divide(UInt32.ZERO, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), divide(Float32.valueOf(0.0f), Int32.ONE));
    verify(Float32.valueOf(1.0f), divide(Int32.ONE, Float32.valueOf(1.0f)));
    verify(Float32.valueOf(0.0f), divide(Int32.ZERO, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), divide(Float32.valueOf(0.0f), Char8.valueOf('\1')));
    verify(Float32.valueOf(1.0f), divide(Char8.valueOf('\1'), Float32.valueOf(1.0f)));
    verify(Float32.valueOf(0.0f), divide(Char8.valueOf('\0'), Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), divide(Float32.valueOf(0.0f), Bool.TRUE));
    verify(Float32.valueOf(1.0f), divide(Bool.TRUE, Float32.valueOf(1.0f)));
    verify(Float32.valueOf(0.0f), divide(Bool.FALSE, Float32.valueOf(1.0f)));

    // UInt64
    verify(UInt64.ZERO, divide(UInt64.ZERO, UInt64.ONE));
    verify(UInt64.MAX_VALUE, divide(UInt64.MAX_VALUE, UInt64.ONE));
    verify(UInt64.ONE, divide(UInt64.ONE, UInt64.ONE));

    verify(UInt64.ZERO, divide(UInt64.ZERO, Int64.ONE));
    verify(UInt64.ONE, divide(Int64.ONE, UInt64.ONE));
    verify(UInt64.ZERO, divide(Int64.ZERO, UInt64.ONE));

    verify(UInt64.ZERO, divide(UInt64.ZERO, UInt32.ONE));
    verify(UInt64.ONE, divide(UInt32.ONE, UInt64.ONE));
    verify(UInt64.ZERO, divide(UInt32.ZERO, UInt64.ONE));

    verify(UInt64.ZERO, divide(UInt64.ZERO, Int32.ONE));
    verify(UInt64.ONE, divide(Int32.ONE, UInt64.ONE));
    verify(UInt64.ZERO, divide(Int32.ZERO, UInt64.ONE));

    verify(UInt64.ZERO, divide(UInt64.ZERO, Char8.valueOf('\1')));
    verify(UInt64.ONE, divide(Char8.valueOf('\1'), UInt64.ONE));
    verify(UInt64.ZERO, divide(Char8.valueOf('\0'), UInt64.ONE));

    verify(UInt64.ZERO, divide(UInt64.ZERO, Bool.TRUE));
    verify(UInt64.ONE, divide(Bool.TRUE, UInt64.ONE));
    verify(UInt64.ZERO, divide(Bool.FALSE, UInt64.ONE));

    // Long
    verify(Int64.ZERO, divide(Int64.ZERO, Int64.ONE));
    verify(Int64.ONE, divide(Int64.ONE, Int64.ONE));

    verify(Int64.ZERO, divide(Int64.ZERO, UInt32.ONE));
    verify(Int64.ONE, divide(UInt32.ONE, Int64.ONE));
    verify(Int64.ZERO, divide(UInt32.ZERO, Int64.ONE));

    verify(Int64.ZERO, divide(Int64.ZERO, Int32.ONE));
    verify(Int64.ONE, divide(Int32.ONE, Int64.ONE));
    verify(Int64.ZERO, divide(Int32.ZERO, Int64.ONE));

    verify(Int64.ZERO, divide(Int64.ZERO, Char8.valueOf('\1')));
    verify(Int64.ONE, divide(Char8.valueOf('\1'), Int64.ONE));
    verify(Int64.ZERO, divide(Char8.valueOf('\0'), Int64.ONE));

    verify(Int64.ZERO, divide(Int64.ZERO, Bool.TRUE));
    verify(Int64.ONE, divide(Bool.TRUE, Int64.ONE));
    verify(Int64.ZERO, divide(Bool.FALSE, Int64.ONE));

    // UInt32
    verify(UInt32.ZERO, divide(UInt32.ZERO, UInt32.ONE));
    verify(UInt32.MAX_VALUE, divide(UInt32.MAX_VALUE, UInt32.ONE));
    verify(UInt32.ZERO, divide(UInt32.ZERO, UInt32.ONE));

    verify(UInt32.ZERO, divide(UInt32.ZERO, Int32.ONE));
    verify(UInt32.ONE, divide(Int32.ONE, UInt32.ONE));
    verify(UInt32.ZERO, divide(Int32.ZERO, UInt32.ONE));

    verify(UInt32.ZERO, divide(UInt32.ZERO, Char8.valueOf('\1')));
    verify(UInt32.ONE, divide(Char8.valueOf('\1'), UInt32.ONE));
    verify(UInt32.ZERO, divide(Char8.valueOf('\0'), UInt32.ONE));

    verify(UInt32.ZERO, divide(UInt32.ZERO, Bool.TRUE));
    verify(UInt32.ONE, divide(Bool.TRUE, UInt32.ONE));
    verify(UInt32.ZERO, divide(Bool.FALSE, UInt32.ONE));

    // Integer
    verify(Int32.ZERO, divide(Int32.ZERO, Int32.ONE));
    verify(Int32.ONE, divide(Int32.ONE, Int32.ONE));

    verify(Int32.ZERO, divide(Int32.ZERO, Char8.valueOf('\1')));
    verify(Int32.ONE, divide(Char8.valueOf('\1'), Int32.ONE));
    verify(Int32.ZERO, divide(Char8.valueOf('\0'), Int32.ONE));

    verify(Int32.ZERO, divide(Int32.ZERO, Bool.TRUE));
    verify(Int32.ONE, divide(Bool.TRUE, Int32.ONE));
    verify(Int32.ZERO, divide(Bool.FALSE, Int32.ONE));

    // Character
    verify(Int32.ZERO, divide(Char8.valueOf('\0'), Char8.valueOf('\1')));
    verify(Int32.ONE, divide(Char8.valueOf('\1'), Char8.valueOf('\1')));

    verify(Int32.ZERO, divide(Char8.valueOf('\0'), Bool.TRUE));
    verify(Int32.ONE, divide(Bool.TRUE, Char8.valueOf('\1')));
    verify(Int32.ZERO, divide(Bool.FALSE, Char8.valueOf('\1')));

    // Boolean
    verify(Int32.ZERO, divide(Bool.FALSE, Bool.TRUE));
    verify(Int32.ONE, divide(Bool.TRUE, Bool.TRUE));
  }

  @Test
  void testMultiply()
  {
    // Double
    verify(Float64.valueOf(0.0), multiply(Float64.valueOf(0.0), Float64.valueOf(1.0)));
    verify(Float64.valueOf(1.0), multiply(Float64.valueOf(1.0), Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), multiply(Float64.valueOf(0.0), Float32.valueOf(1.0f)));
    verify(Float64.valueOf(1.0), multiply(Float32.valueOf(1.0f), Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), multiply(Float32.valueOf(0.0f), Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), multiply(Float64.valueOf(0.0), UInt64.ONE));
    verify(Float64.valueOf(1.0), multiply(UInt64.ONE, Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), multiply(UInt64.ZERO, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), multiply(Float64.valueOf(0.0), Int64.ONE));
    verify(Float64.valueOf(1.0), multiply(Int64.ONE, Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), multiply(Int64.ZERO, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), multiply(Float64.valueOf(0.0), UInt32.ONE));
    verify(Float64.valueOf(1.0), multiply(UInt32.ONE, Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), multiply(UInt32.ZERO, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), multiply(Float64.valueOf(0.0), Int32.ONE));
    verify(Float64.valueOf(1.0), multiply(Int32.ONE, Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), multiply(Int32.ZERO, Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), multiply(Float64.valueOf(0.0), Char8.valueOf('\1')));
    verify(Float64.valueOf(1.0), multiply(Char8.valueOf('\1'), Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), multiply(Char8.valueOf('\0'), Float64.valueOf(1.0)));

    verify(Float64.valueOf(0.0), multiply(Float64.valueOf(0.0), Bool.TRUE));
    verify(Float64.valueOf(1.0), multiply(Bool.TRUE, Float64.valueOf(1.0)));
    verify(Float64.valueOf(0.0), multiply(Bool.FALSE, Float64.valueOf(1.0)));

    // Float
    verify(Float32.valueOf(0.0f), multiply(Float32.valueOf(0.0f), Float32.valueOf(1.0f)));
    verify(Float32.valueOf(1.0f), multiply(Float32.valueOf(1.0f), Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), multiply(Float32.valueOf(0.0f), UInt64.ONE));
    verify(Float32.valueOf(1.0f), multiply(UInt64.ONE, Float32.valueOf(1.0f)));
    verify(Float32.valueOf(0.0f), multiply(UInt64.ZERO, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), multiply(Float32.valueOf(0.0f), Int64.ONE));
    verify(Float32.valueOf(1.0f), multiply(Int64.ONE, Float32.valueOf(1.0f)));
    verify(Float32.valueOf(0.0f), multiply(Int64.ZERO, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), multiply(Float32.valueOf(0.0f), UInt32.ONE));
    verify(Float32.valueOf(1.0f), multiply(UInt32.ONE, Float32.valueOf(1.0f)));
    verify(Float32.valueOf(0.0f), multiply(UInt32.ZERO, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), multiply(Float32.valueOf(0.0f), Int32.ONE));
    verify(Float32.valueOf(1.0f), multiply(Int32.ONE, Float32.valueOf(1.0f)));
    verify(Float32.valueOf(0.0f), multiply(Int32.ZERO, Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), multiply(Float32.valueOf(0.0f), Char8.valueOf('\1')));
    verify(Float32.valueOf(1.0f), multiply(Char8.valueOf('\1'), Float32.valueOf(1.0f)));
    verify(Float32.valueOf(0.0f), multiply(Char8.valueOf('\0'), Float32.valueOf(1.0f)));

    verify(Float32.valueOf(0.0f), multiply(Float32.valueOf(0.0f), Bool.TRUE));
    verify(Float32.valueOf(1.0f), multiply(Bool.TRUE, Float32.valueOf(1.0f)));
    verify(Float32.valueOf(0.0f), multiply(Bool.FALSE, Float32.valueOf(1.0f)));

    // UInt64
    verify(UInt64.ZERO, multiply(UInt64.ZERO, UInt64.ONE));
    verify(UInt64.MAX_VALUE, multiply(UInt64.MAX_VALUE, UInt64.ONE));
    verify(UInt64.ONE, multiply(UInt64.ONE, UInt64.ONE));

    verify(UInt64.ZERO, multiply(UInt64.ZERO, Int64.ONE));
    verify(UInt64.ONE, multiply(Int64.ONE, UInt64.ONE));
    verify(UInt64.ZERO, multiply(Int64.ZERO, UInt64.ONE));

    verify(UInt64.ZERO, multiply(UInt64.ZERO, UInt32.ONE));
    verify(UInt64.ONE, multiply(UInt32.ONE, UInt64.ONE));
    verify(UInt64.ZERO, multiply(UInt32.ZERO, UInt64.ONE));

    verify(UInt64.ZERO, multiply(UInt64.ZERO, Int32.ONE));
    verify(UInt64.ONE, multiply(Int32.ONE, UInt64.ONE));
    verify(UInt64.ZERO, multiply(Int32.ZERO, UInt64.ONE));

    verify(UInt64.ZERO, multiply(UInt64.ZERO, Char8.valueOf('\1')));
    verify(UInt64.ONE, multiply(Char8.valueOf('\1'), UInt64.ONE));
    verify(UInt64.ZERO, multiply(Char8.valueOf('\0'), UInt64.ONE));

    verify(UInt64.ZERO, multiply(UInt64.ZERO, Bool.TRUE));
    verify(UInt64.ONE, multiply(Bool.TRUE, UInt64.ONE));
    verify(UInt64.ZERO, multiply(Bool.FALSE, UInt64.ONE));

    // Long
    verify(Int64.ZERO, multiply(Int64.ZERO, Int64.ONE));
    verify(Int64.ONE, multiply(Int64.ONE, Int64.ONE));

    verify(Int64.ZERO, multiply(Int64.ZERO, UInt32.ONE));
    verify(Int64.ONE, multiply(UInt32.ONE, Int64.ONE));
    verify(Int64.ZERO, multiply(UInt32.ZERO, Int64.ONE));

    verify(Int64.ZERO, multiply(Int64.ZERO, Int32.ONE));
    verify(Int64.ONE, multiply(Int32.ONE, Int64.ONE));
    verify(Int64.ZERO, multiply(Int32.ZERO, Int64.ONE));

    verify(Int64.ZERO, multiply(Int64.ZERO, Char8.valueOf('\1')));
    verify(Int64.ONE, multiply(Char8.valueOf('\1'), Int64.ONE));
    verify(Int64.ZERO, multiply(Char8.valueOf('\0'), Int64.ONE));

    verify(Int64.ZERO, multiply(Int64.ZERO, Bool.TRUE));
    verify(Int64.ONE, multiply(Bool.TRUE, Int64.ONE));
    verify(Int64.ZERO, multiply(Bool.FALSE, Int64.ONE));

    // UInt32
    verify(UInt32.ZERO, multiply(UInt32.ZERO, UInt32.ONE));
    verify(UInt32.MAX_VALUE, multiply(UInt32.MAX_VALUE, UInt32.ONE));
    verify(UInt32.ZERO, multiply(UInt32.ZERO, UInt32.ONE));

    verify(UInt32.ZERO, multiply(UInt32.ZERO, Int32.ONE));
    verify(UInt32.ONE, multiply(Int32.ONE, UInt32.ONE));
    verify(UInt32.ZERO, multiply(Int32.ZERO, UInt32.ONE));

    verify(UInt32.ZERO, multiply(UInt32.ZERO, Char8.valueOf('\1')));
    verify(UInt32.ONE, multiply(Char8.valueOf('\1'), UInt32.ONE));
    verify(UInt32.ZERO, multiply(Char8.valueOf('\0'), UInt32.ONE));

    verify(UInt32.ZERO, multiply(UInt32.ZERO, Bool.TRUE));
    verify(UInt32.ONE, multiply(Bool.TRUE, UInt32.ONE));
    verify(UInt32.ZERO, multiply(Bool.FALSE, UInt32.ONE));

    // Integer
    verify(Int32.ZERO, multiply(Int32.ZERO, Int32.ONE));
    verify(Int32.ONE, multiply(Int32.ONE, Int32.ONE));

    verify(Int32.ZERO, multiply(Int32.ZERO, Char8.valueOf('\1')));
    verify(Int32.ONE, multiply(Char8.valueOf('\1'), Int32.ONE));
    verify(Int32.ZERO, multiply(Char8.valueOf('\0'), Int32.ONE));

    verify(Int32.ZERO, multiply(Int32.ZERO, Bool.TRUE));
    verify(Int32.ONE, multiply(Bool.TRUE, Int32.ONE));
    verify(Int32.ZERO, multiply(Bool.FALSE, Int32.ONE));

    // Character
    verify(Int32.ZERO, multiply(Char8.valueOf('\0'), Char8.valueOf('\1')));
    verify(Int32.ONE, multiply(Char8.valueOf('\1'), Char8.valueOf('\1')));

    verify(Int32.ZERO, multiply(Char8.valueOf('\0'), Bool.TRUE));
    verify(Int32.ONE, multiply(Bool.TRUE, Char8.valueOf('\1')));
    verify(Int32.ZERO, multiply(Bool.FALSE, Char8.valueOf('\1')));

    // Boolean
    verify(Int32.ZERO, multiply(Bool.FALSE, Bool.TRUE));
    verify(Int32.ONE, multiply(Bool.TRUE, Bool.TRUE));
  }

  @Test
  void testRemainder()
  {

    // UInt64
    verify(UInt64.ZERO, remainder(UInt64.ZERO, UInt64.ONE));
    verify(UInt64.ONE, remainder(UInt64.valueOf(3), UInt64.valueOf(2)));
    verify(UInt64.ZERO, remainder(UInt64.ONE, UInt64.ONE));

  }

}
