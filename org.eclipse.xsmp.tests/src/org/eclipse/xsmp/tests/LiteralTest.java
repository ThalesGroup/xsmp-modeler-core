package org.eclipse.xsmp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.junit.jupiter.api.Test;

class LiteralTest
{
  /* --------- Integer --------- */

  void checkIntegers(String val, BigInteger expected)
  {
    checkInteger(val, expected);
    checkInteger(val + "ll", expected);
    checkInteger(val + "LL", expected);
    checkInteger(val + "l", expected);
    checkInteger(val + "L", expected);
    checkInteger(val + "u", expected);
    checkInteger(val + "U", expected);
    checkInteger(val + "ull", expected);
    checkInteger(val + "uLL", expected);
    checkInteger(val + "ul", expected);
    checkInteger(val + "uL", expected);
  }

  void checkInteger(String val, BigInteger expected)
  {
    final var i = XcatalogueFactory.eINSTANCE.createIntegerLiteral();

    i.setText(val);
    assertEquals(expected, i.getValue());
  }

  void testDecimal()
  {
    // Non-zero digit
    checkIntegers("1", BigInteger.valueOf(1));
    checkIntegers("2", BigInteger.valueOf(2));
    checkIntegers("3", BigInteger.valueOf(3));
    checkIntegers("4", BigInteger.valueOf(4));
    checkIntegers("5", BigInteger.valueOf(5));
    checkIntegers("6", BigInteger.valueOf(6));
    checkIntegers("7", BigInteger.valueOf(7));
    checkIntegers("8", BigInteger.valueOf(8));
    checkIntegers("9", BigInteger.valueOf(9));

    // Non-zero digit larger than 1
    checkIntegers("10", BigInteger.valueOf(10));
    checkIntegers("20", BigInteger.valueOf(20));
    checkIntegers("30", BigInteger.valueOf(30));
    checkIntegers("40", BigInteger.valueOf(40));
    checkIntegers("50", BigInteger.valueOf(50));
    checkIntegers("60", BigInteger.valueOf(60));
    checkIntegers("70", BigInteger.valueOf(70));
    checkIntegers("80", BigInteger.valueOf(80));
    checkIntegers("90", BigInteger.valueOf(90));

    // Non-zero digit with separations
    checkIntegers("100'000'000", BigInteger.valueOf(100000000));
    checkIntegers("100'000000", BigInteger.valueOf(100000000));
    checkIntegers("100000'000", BigInteger.valueOf(100000000));
    checkIntegers("100000000", BigInteger.valueOf(100000000));
  }

  void testOctal()
  {
    checkIntegers("01", new BigInteger("1", 8));
    checkIntegers("02", new BigInteger("2", 8));
    checkIntegers("03", new BigInteger("3", 8));
    checkIntegers("04", new BigInteger("4", 8));
    checkIntegers("05", new BigInteger("5", 8));
    checkIntegers("06", new BigInteger("6", 8));
    checkIntegers("07", new BigInteger("7", 8));

    checkIntegers("0175", new BigInteger("175", 8));
    checkIntegers("0175", BigInteger.valueOf(125));
  }

  void testHexa()
  {
    // With a lowercase x
    checkIntegers("0xFFFF", new BigInteger("FFFF", 16));
    checkIntegers("0xFFFF", new BigInteger("ffff", 16));
    checkIntegers("0xffff", new BigInteger("FFFF", 16));
    checkIntegers("0xffff", new BigInteger("ffff", 16));
    checkIntegers("0x7D", new BigInteger("7D", 16));
    checkIntegers("0x7D", BigInteger.valueOf(125));

    // With a capital X
    checkIntegers("0XFFFF", new BigInteger("FFFF", 16));
    checkIntegers("0XFFFF", new BigInteger("ffff", 16));
    checkIntegers("0Xffff", new BigInteger("FFFF", 16));
    checkIntegers("0Xffff", new BigInteger("ffff", 16));
    checkIntegers("0X7D", BigInteger.valueOf(125));
  }

  void testBinary()
  {
    // With a lowercase b
    checkIntegers("0b1111", new BigInteger("1111", 2));
    checkIntegers("0b1001", new BigInteger("1001", 2));
    checkIntegers("0b1111", BigInteger.valueOf(15));
    checkIntegers("0b1001", BigInteger.valueOf(9));

    // With a capital B
    checkIntegers("0B1111", new BigInteger("1111", 2));
    checkIntegers("0B1001", new BigInteger("1001", 2));
    checkIntegers("0B1111", BigInteger.valueOf(15));
    checkIntegers("0B1001", BigInteger.valueOf(9));
  }

  @Test
  void testInteger()
  {
    testDecimal();
    testOctal();
    testHexa();
    testBinary();
  }

  /* --------- Float --------- */

  void checkFloats(String val, String expected)
  {
    // The expected must be the number without the separations

    checkFloat(val, new BigDecimal(expected, MathContext.DECIMAL64));
    checkFloat(val + "f", new BigDecimal(expected, MathContext.DECIMAL32));
    checkFloat(val + "F", new BigDecimal(expected, MathContext.DECIMAL32));
    checkFloat(val + "l", new BigDecimal(expected, MathContext.DECIMAL128));
    checkFloat(val + "L", new BigDecimal(expected, MathContext.DECIMAL128));
  }

  void checkFloat(String val, BigDecimal expected)
  {
    final var i = XcatalogueFactory.eINSTANCE.createFloatingLiteral();

    i.setText(val);
    assertEquals(expected, i.getValue());
  }

  @Test
  void testFloat()
  {
    // FRACTIONALCONSTANT: DIGITSEQUENCE? '.' DIGITSEQUENCE | DIGITSEQUENCE '.'
    // DIGITSEQUENCE: DIGIT ('\''? DIGIT)*

    checkFloats("0.0", "0.0");
    checkFloats("0123456.0123456", "0123456.0123456");
    checkFloats(".0123456", "0.0123456");
    checkFloats("0123456.", "0123456.");

    // with separator
    checkFloats("01'23'45'6.01'2'345'6", "0123456.0123456");
    checkFloats(".01'23'456", "0.0123456");
    checkFloats("0'12345'6.", "0123456.");

    // with exponent
    // EXPONENTPART: 'e' SIGN? DIGITSEQUENCE | 'E' SIGN? DIGITSEQUENCE

    checkFloats("0.e1234", "0.e1234");
    checkFloats("0.e+1234", "0.e+1234");
    checkFloats("0.e-1234", "0.e-1234");

    checkFloats("0.e12'34", "0.e1234");
    checkFloats("0.e+12'34", "0.e+1234");
    checkFloats("0.e-1'234", "0.e-1234");

    checkFloats("0.E1234", "0.E1234");
    checkFloats("0.E+1234", "0.E+1234");
    checkFloats("0.E-1234", "0.E-1234");

    checkFloats("0.E12'34", "0.E1234");
    checkFloats("0.E+12'34", "0.E+1234");
    checkFloats("0.E-1'234", "0.E-1234");

    // DIGITSEQUENCE EXPONENTPART FLOATINGSUFFIX?

    checkFloats("01234E1234", "01234E1234");
    checkFloats("01'23'4E12'34", "01234E1234");
  }

}
