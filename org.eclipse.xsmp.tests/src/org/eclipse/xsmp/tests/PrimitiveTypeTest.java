package org.eclipse.xsmp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.eclipse.xsmp.util.Bool;
import org.eclipse.xsmp.util.Char8;
import org.eclipse.xsmp.util.Int16;
import org.eclipse.xsmp.util.Int32;
import org.eclipse.xsmp.util.Int64;
import org.eclipse.xsmp.util.Int8;
import org.eclipse.xsmp.util.UInt16;
import org.eclipse.xsmp.util.UInt32;
import org.eclipse.xsmp.util.UInt64;
import org.eclipse.xsmp.util.UInt8;
import org.junit.jupiter.api.Test;

class PrimitiveTypeTest
{

  @Test
  void testLogicalOperators()
  {

    assertEquals(Bool.FALSE, Bool.FALSE.logicalOr(Bool.FALSE));
    assertEquals(Bool.TRUE, Bool.TRUE.logicalOr(Bool.FALSE));
    assertEquals(Bool.TRUE, Bool.FALSE.logicalOr(Bool.TRUE));
    assertEquals(Bool.TRUE, Bool.TRUE.logicalOr(Bool.TRUE));
    assertEquals(Bool.FALSE, Bool.FALSE.logicalAnd(Bool.FALSE));
    assertEquals(Bool.FALSE, Bool.TRUE.logicalAnd(Bool.FALSE));
    assertEquals(Bool.FALSE, Bool.FALSE.logicalAnd(Bool.TRUE));
    assertEquals(Bool.TRUE, Bool.TRUE.logicalAnd(Bool.TRUE));

    assertEquals(Bool.TRUE, Bool.FALSE.not());
    assertEquals(Bool.FALSE, Bool.TRUE.not());

  }

  @Test
  void testChar8()
  {
    // Test conversion from char to Int8
    assertEquals((char) 42, Char8.valueOf((char) 42).getValue());

    // Test conversion from UInt8 to Bool
    assertEquals(Bool.TRUE, Char8.valueOf((char) 42).boolValue());
    assertEquals(Bool.FALSE, Char8.valueOf((char) 0).boolValue());

    // Test promotion to Int32 for unary operators
    assertEquals(Int32.valueOf(-42), Char8.valueOf((char) 42).negate());
    assertEquals(Int32.valueOf(42), Char8.valueOf((char) 42).plus());
    assertEquals(Int32.valueOf(-43), Char8.valueOf((char) 42).unaryComplement());

    // Test promotion to Int32 for binary operators
    assertEquals(Int32.valueOf(23), Char8.valueOf((char) 42).add(Char8.valueOf((char) -19)));
    assertEquals(Int32.valueOf(84), Char8.valueOf((char) 42).multiply(Char8.valueOf((char) 2)));
    assertEquals(Int32.valueOf(2), Char8.valueOf((char) 42).divide(Char8.valueOf((char) 20)));
    assertEquals(Int32.valueOf(22), Char8.valueOf((char) 42).subtract(Char8.valueOf((char) 20)));
    assertEquals(Int32.valueOf(2), Char8.valueOf((char) 42).remainder(Char8.valueOf((char) 20)));
    assertEquals(Int32.valueOf(-84), Char8.valueOf((char) -42).shiftLeft(Char8.valueOf((char) 1)));
    assertEquals(Int32.valueOf(-21), Char8.valueOf((char) -42).shiftRight(Char8.valueOf((char) 1)));
    assertEquals(Int32.valueOf(59), Char8.valueOf((char) 42).or(Char8.valueOf((char) 19)));
    assertEquals(Int32.valueOf(2), Char8.valueOf((char) 42).and(Char8.valueOf((char) 2)));
    assertEquals(Int32.valueOf(56), Char8.valueOf((char) 42).xor(Char8.valueOf((char) 18)));

    // Test compareTo
    assertEquals(0, Char8.valueOf((char) 42).compareTo(Char8.valueOf((char) 42)));
    assertEquals(1, Char8.valueOf((char) 42).compareTo(Char8.valueOf((char) 19)));
    assertEquals(-1, Char8.valueOf((char) 42).compareTo(Char8.valueOf((char) 99)));

  }

  @Test
  void testInt8()
  {
    // Test conversion from byte to Int8
    assertEquals((byte) 42, Int8.valueOf((byte) 42).getValue());

    // Test conversion from UInt8 to Bool
    assertEquals(Bool.TRUE, Int8.valueOf((byte) 42).boolValue());
    assertEquals(Bool.FALSE, Int8.valueOf((byte) 0).boolValue());

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

  }

  @Test
  void testInt16()
  {
    // Test conversion from short to Int16
    assertEquals((short) 42, Int16.valueOf((short) 42).getValue());

    // Test conversion from Int16 to Bool
    assertEquals(Bool.TRUE, Int16.valueOf((short) 42).boolValue());
    assertEquals(Bool.FALSE, Int16.valueOf((short) 0).boolValue());

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

  }

  @Test
  void testUInt8()
  {
    // Test conversion from byte to UInt8
    assertEquals((short) 255, UInt8.valueOf((byte) -1).getValue());

    // Test conversion from UInt8 to Bool
    assertEquals(Bool.TRUE, UInt8.valueOf((byte) 42).boolValue());
    assertEquals(Bool.FALSE, UInt8.valueOf((byte) 0).boolValue());

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

  }

  @Test
  void testUInt16()
  {
    // Test conversion from short to UInt16
    assertEquals(65535, UInt16.valueOf((short) -1).getValue());

    // Test conversion from UInt16 to Bool
    assertEquals(Bool.TRUE, UInt16.valueOf((short) 42).boolValue());
    assertEquals(Bool.FALSE, UInt16.valueOf((short) 0).boolValue());

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

  }

  @Test
  void testInt32()
  {
    // Test conversion from int to Int32
    assertEquals(42, Int32.valueOf(42).getValue());

    // Test conversion from Int32 to Bool
    assertEquals(Bool.TRUE, Int32.valueOf(42).boolValue());
    assertEquals(Bool.FALSE, Int32.valueOf(0).boolValue());

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

  }

  @Test
  void testUInt32()
  {
    // Test conversion from int to UInt32
    assertEquals(0xffff_ffffL, UInt32.valueOf(-1).getValue());

    // Test conversion from UInt32 to Bool
    assertEquals(Bool.TRUE, UInt32.valueOf(42).boolValue());
    assertEquals(Bool.FALSE, UInt32.valueOf(0).boolValue());

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

  }

  @Test
  void testInt64()
  {
    // Test conversion from long to Int64
    assertEquals(42L, Int64.valueOf(42L).getValue());

    // Test conversion from Int64 to Bool
    assertEquals(Bool.TRUE, Int64.valueOf(42L).boolValue());
    assertEquals(Bool.FALSE, Int64.valueOf(0).boolValue());

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
    assertEquals(Int64.valueOf(-84L), Int64.valueOf(-42L).shiftLeft(Int64.valueOf(1)));
    assertEquals(Int64.valueOf(-21L), Int64.valueOf(-42L).shiftRight(Int64.valueOf(1)));
    assertEquals(Int64.valueOf(59L), Int64.valueOf(42L).or(Int64.valueOf(19L)));
    assertEquals(Int64.valueOf(2L), Int64.valueOf(42L).and(Int64.valueOf(2L)));
    assertEquals(Int64.valueOf(56L), Int64.valueOf(42L).xor(Int64.valueOf(18L)));

    // Test compareTo
    assertEquals(0, Int64.valueOf(42L).compareTo(Int64.valueOf(42L)));
    assertEquals(1, Int64.valueOf(42L).compareTo(Int64.valueOf(19L)));
    assertEquals(-1, Int64.valueOf(42L).compareTo(Int64.valueOf(99L)));

  }

  @Test
  void testUInt64()
  {
    // Test conversion from long to UInt64
    assertEquals(BigInteger.valueOf(42), UInt64.valueOf(42L).getValue());

    // Test conversion from UInt64 to Bool
    assertEquals(Bool.TRUE, UInt64.valueOf(42L).boolValue());
    assertEquals(Bool.FALSE, UInt64.valueOf(0).boolValue());

    // Test unary operators
    assertEquals(UInt64.valueOf(42), UInt64.valueOf(-42L).negate());
    assertEquals(UInt64.valueOf(42), UInt64.valueOf(42L).plus());
    assertEquals(UInt64.valueOf(-43), UInt64.valueOf(42).unaryComplement());

    // Test binary operators
    assertEquals(UInt64.valueOf(52L), UInt64.valueOf(42L).add(UInt64.valueOf(10L)));
    assertEquals(UInt64.valueOf(84L), UInt64.valueOf(42L).multiply(UInt64.valueOf(2L)));
    assertEquals(UInt64.valueOf(2L), UInt64.valueOf(42L).divide(UInt64.valueOf(20L)));
    assertEquals(UInt64.valueOf(22L), UInt64.valueOf(42L).subtract(UInt64.valueOf(20L)));
    assertEquals(UInt64.valueOf(2L), UInt64.valueOf(42L).remainder(UInt64.valueOf(20L)));
    assertEquals(UInt64.valueOf(16L), UInt64.valueOf(8L).shiftLeft(UInt64.valueOf(1L)));
    assertEquals(UInt64.valueOf(16L), UInt64.valueOf(32L).shiftRight(UInt64.valueOf(1L)));
    assertEquals(UInt64.valueOf(59L), UInt64.valueOf(42L).or(UInt64.valueOf(19L)));
    assertEquals(UInt64.valueOf(2L), UInt64.valueOf(42L).and(UInt64.valueOf(2L)));
    assertEquals(UInt64.valueOf(56L), UInt64.valueOf(42L).xor(UInt64.valueOf(18L)));

    // Test compareTo
    assertEquals(0, UInt64.valueOf(42L).compareTo(UInt64.valueOf(42L)));
    assertEquals(1, UInt64.valueOf(42L).compareTo(UInt64.valueOf(19L)));
    assertEquals(-1, UInt64.valueOf(42L).compareTo(UInt64.valueOf(99L)));

  }

}
