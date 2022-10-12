/*******************************************************************************
* Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.validation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.xsmp.util.ExpressionSolver;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.Array;
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.PrimitiveType;
import org.eclipse.xsmp.xcatalogue.StringLiteral;
import org.eclipse.xsmp.xcatalogue.Structure;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.ValueReference;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ExpressionValidator
{

  public static final BigInteger INT16_MAX = BigInteger.valueOf(Short.MAX_VALUE);

  public static final BigInteger INT16_MIN = BigInteger.valueOf(Short.MIN_VALUE);

  public static final BigInteger INT32_MAX = BigInteger.valueOf(Integer.MAX_VALUE);

  public static final BigInteger INT32_MIN = BigInteger.valueOf(Integer.MIN_VALUE);

  public static final BigInteger INT64_MAX = BigInteger.valueOf(Long.MAX_VALUE);

  public static final BigInteger INT64_MIN = BigInteger.valueOf(Long.MIN_VALUE);

  public static final BigInteger INT8_MAX = BigInteger.valueOf(Byte.MAX_VALUE);

  public static final BigInteger INT8_MIN = BigInteger.valueOf(Byte.MIN_VALUE);

  public static final BigInteger UINT16_MAX = BigInteger.valueOf(0xffff);

  public static final BigInteger UINT16_MIN = BigInteger.ZERO;

  public static final BigInteger UINT32_MAX = BigInteger.valueOf(0xffffffffL);

  public static final BigInteger UINT32_MIN = BigInteger.ZERO;

  public static final BigInteger UINT64_MAX = new BigInteger("ffffffffffffffff", 16);

  public static final BigInteger UINT64_MIN = BigInteger.ZERO;

  public static final BigInteger UINT8_MAX = BigInteger.valueOf(0xff);

  public static final BigInteger UINT8_MIN = BigInteger.ZERO;

  public static final BigDecimal FLOAT32_MAX = BigDecimal.valueOf(Float.MAX_VALUE);

  public static final BigDecimal FLOAT32_MIN = FLOAT32_MAX.negate();

  public static final BigDecimal FLOAT64_MAX = BigDecimal.valueOf(Double.MAX_VALUE);

  public static final BigDecimal FLOAT64_MIN = FLOAT64_MAX.negate();

  private static void checkBool(Expression e, ValidationMessageAcceptor acceptor)
  {
    e.doGetBoolean(acceptor);
  }

  private static void checkChar(Expression e, ValidationMessageAcceptor acceptor)
  {

    switch (e.eClass().getClassifierID())
    {
      case XcataloguePackage.BINARY_OPERATION:
      case XcataloguePackage.UNARY_OPERATION:
      case XcataloguePackage.CHARACTER_LITERAL:
        break;
      default:
        // check byte value
        checkIntegralRange(Byte.MIN_VALUE, Byte.MAX_VALUE, e, acceptor);
    }
  }

  private static void checkDateTime(Expression e, ValidationMessageAcceptor acceptor)
  {

    switch (e.eClass().getClassifierID())
    {
      case XcataloguePackage.BINARY_OPERATION:
      case XcataloguePackage.UNARY_OPERATION:
        break;
      case XcataloguePackage.STRING_LITERAL:
        try
        {
          EcoreUtil.createFromString(XMLTypePackage.Literals.DATE_TIME,
                  XsmpUtil.getString((StringLiteral) e));
        }
        catch (final Exception ex)
        {

          if (acceptor != null)
          {
            acceptor.acceptError("Invalid DateTime format: " + ex.getMessage(),
                    ExpressionSolver.getTarget(e), null,
                    ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
          }
        }
        break;
      default:
        // check Date Time in ns
        checkIntegralRange(INT64_MIN, INT64_MAX, e, acceptor);
    }
  }

  private static BigDecimal checkDecimalRange(BigDecimal min, BigDecimal max, boolean minInclusive,
          boolean maxInclusive, Expression e, ValidationMessageAcceptor acceptor)
  {

    if (e == null)
    {
      return null;
    }
    final var value = e.doGetDecimal(acceptor);

    if (acceptor != null && value != null && min != null && max != null
            && ((minInclusive ? value.compareTo(min) < 0 : value.compareTo(min) <= 0)
                    || (maxInclusive ? value.compareTo(max) > 0 : value.compareTo(max) >= 0)))
    {
      acceptor.acceptError("Decimal value " + value + " is not in range " + min + "..." + max + ".",
              ExpressionSolver.getTarget(e), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              "invalid_range", (String[]) null);
    }
    return value;
  }

  private static void checkDuration(Expression e, ValidationMessageAcceptor acceptor)
  {

    switch (e.eClass().getClassifierID())
    {
      case XcataloguePackage.BINARY_OPERATION:
      case XcataloguePackage.UNARY_OPERATION:
        break;
      case XcataloguePackage.STRING_LITERAL:
        try
        {
          EcoreUtil.createFromString(XMLTypePackage.Literals.DURATION,
                  XsmpUtil.getString((StringLiteral) e));
        }
        catch (final Exception ex)
        {

          if (acceptor != null)
          {
            acceptor.acceptError("Invalid Duration format: " + ex.getMessage(),
                    ExpressionSolver.getTarget(e), null,
                    ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
          }
        }
        break;
      default:
        // check duration in ns
        checkIntegralRange(INT64_MIN, INT64_MAX, e, acceptor);
    }
  }

  private static void checkEnumeration(Enumeration type, Expression e,
          ValidationMessageAcceptor acceptor)
  {

    final var literal = e.getEnum(acceptor);
    if (literal != null && literal.eContainer() != type && acceptor != null)
    {
      acceptor.acceptError("Incompatible enumeration Type: expecting " + type.getName(),
              ExpressionSolver.getTarget(e), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              "invalid_type", (String[]) null);
    }

  }

  private static void checkFloat(org.eclipse.xsmp.xcatalogue.Float type, Expression e,
          ValidationMessageAcceptor acceptor)
  {
    switch (e.eClass().getClassifierID())
    {
      case XcataloguePackage.BINARY_OPERATION:
      case XcataloguePackage.UNARY_OPERATION:
        break;
      case XcataloguePackage.FLOATING_LITERAL:
      case XcataloguePackage.INTEGER_LITERAL:

        checkDecimalRange(getMin(type), getMax(type), type.isMinInclusive(), type.isMaxInclusive(),
                e, acceptor);
        break;
      default:
        if (acceptor != null)
        {
          acceptor.acceptError("Expecting a Decimal value, got " + e.eClass().getName() + ".",
                  ExpressionSolver.getTarget(e), null,
                  ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
        }

    }
  }

  public static BigInteger checkIntegralRange(BigInteger min, BigInteger max, Expression e,
          ValidationMessageAcceptor acceptor)
  {
    if (e == null)
    {
      return null;
    }
    final var value = e.doGetInteger(acceptor);

    if (acceptor != null && value != null && min != null && max != null
            && (value.compareTo(min) < 0 || value.compareTo(max) > 0))
    {
      acceptor.acceptError(
              "Integral value " + value + " is not in range " + min + " ... " + max + ".",
              ExpressionSolver.getTarget(e), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              "invalid_range", (String[]) null);
    }
    return value;
  }

  private static BigInteger checkIntegralRange(long min, long max, Expression e,
          ValidationMessageAcceptor acceptor)
  {
    return checkIntegralRange(BigInteger.valueOf(min), BigInteger.valueOf(max), e, acceptor);
  }

  private static void checkString(Expression e, ValidationMessageAcceptor acceptor)
  {

    switch (e.eClass().getClassifierID())
    {
      case XcataloguePackage.BINARY_OPERATION:
      case XcataloguePackage.UNARY_OPERATION:
      case XcataloguePackage.STRING_LITERAL:
        break;
      default:
        if (acceptor != null)
        {
          acceptor.acceptError("Expecting a String value, got " + e.eClass().getName() + ".",
                  ExpressionSolver.getTarget(e), null,
                  ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
        }

    }
  }

  private static void checkString(org.eclipse.xsmp.xcatalogue.String type, Expression e,
          ValidationMessageAcceptor acceptor)
  {

    if (type.getLength() != null)
    {

      switch (e.eClass().getClassifierID())
      {
        case XcataloguePackage.BINARY_OPERATION:
        case XcataloguePackage.UNARY_OPERATION:
          break;
        case XcataloguePackage.STRING_LITERAL:

          final var size = checkIntegralRange(0, Long.MAX_VALUE,
                  type.getLength() != null ? type.getLength().solve(acceptor) : null, acceptor);

          if (size == null)
          {
            break;
          }

          final var value = translateEscapes(XsmpUtil.getString((StringLiteral) e));

          if (value.length() > size.intValue() && acceptor != null)
          {
            acceptor.acceptError(
                    "The String length cannot exceed " + size + ", got " + value.length()
                            + " characters.",
                    ExpressionSolver.getTarget(e), null,
                    ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
          }

          break;
        default:
          if (acceptor != null)
          {
            acceptor.acceptError("Expecting a String Literal, got " + e.eClass().getName() + ".",
                    ExpressionSolver.getTarget(e), null,
                    ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
          }

      }
    }
  }

  private static String translateEscapes(String s)
  {
    if (s.isEmpty())
    {
      return s;
    }
    final var chars = s.toCharArray();
    final var length = chars.length;
    var from = 0;
    var to = 0;
    while (from < length)
    {
      var ch = chars[from];
      from++;
      if (ch == '\\')
      {
        ch = from < length ? chars[from++] : '\0';
        switch (ch)
        {
          case 'a':
            ch = 0x07;
            break;
          case 'b':
            ch = 0x08;
            break;
          case 'f':
            ch = 0x0c;
            break;
          case 'n':
            ch = 0x0a;
            break;
          case 'r':
            ch = 0x0d;
            break;
          case 't':
            ch = 0x09;
            break;
          case 'v':
            ch = 0x0b;
            break;
          case '\'':
          case '\"':
          case '\\':
            // as is
            break;
          case '?':
            ch = 0x3f;
            break;
          case '0':
          case '1':
          case '2':
          case '3':
          case '4':
          case '5':
          case '6':
          case '7':
            final var limit = Integer.min(from + (ch <= '3' ? 2 : 1), length);
            var code = ch - '0';
            while (from < limit)
            {
              ch = chars[from];
              if (ch < '0' || '7' < ch)
              {
                break;
              }
              from++;
              code = code << 3 | ch - '0';
            }
            ch = (char) code;
            break;
          case 'X':
            // TODO
            break;
          default:
          {
            final var msg = String.format("Invalid escape sequence: \\%c \\\\u%04X", ch, (int) ch);
            throw new IllegalArgumentException(msg);
          }
        }
      }

      chars[to] = ch;
      to++;
    }

    return new String(chars, 0, to);
  }

  private static BigDecimal getMax(org.eclipse.xsmp.xcatalogue.Float type)
  {
    if (type.getMaximum() != null)
    {
      return type.getMaximum().getDecimal(null);
    }
    return BigDecimal.valueOf(Double.MAX_VALUE);

  }

  private static BigDecimal getMin(org.eclipse.xsmp.xcatalogue.Float type)
  {
    if (type.getMinimum() != null)
    {
      return type.getMinimum().getDecimal(null);
    }
    return BigDecimal.valueOf(Double.MAX_VALUE).negate();

  }

  @Inject
  private XsmpUtil typeUtil;

  public void check(Type type, Expression e, ValidationMessageAcceptor acceptor)
  {

    if (e != null && type != null && !type.eIsProxy())
    {
      final var solved = e.solve(acceptor);
      if (solved != null)
      {
        checkSolvedExpression(type, solved, acceptor);
      }
    }
  }

  private void checkArray(Array type, Expression e, ValidationMessageAcceptor acceptor)
  {

    if (type.getSize() != null)
    {

      switch (e.eClass().getClassifierID())
      {
        case XcataloguePackage.BINARY_OPERATION:
        case XcataloguePackage.UNARY_OPERATION:
          break;
        case XcataloguePackage.COLLECTION_LITERAL:

          final var size = checkIntegralRange(0, Long.MAX_VALUE,
                  type.getSize() != null ? type.getSize().solve(acceptor) : null, acceptor);

          if (size == null)
          {
            break;
          }

          final var s = size.longValue();

          final var values = (CollectionLiteral) e;

          // check correct number of elements
          if (values.getElements().size() <= s)
          {
            // check each array field
            values.getElements().stream()
                    .forEach(j -> checkSolvedExpression(type.getItemType(), j, acceptor));

            if (s != values.getElements().size() && !values.getElements().isEmpty()
                    && acceptor != null)
            {
              acceptor.acceptInfo("Partial initialization.", ExpressionSolver.getTarget(e), null,
                      ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type",
                      (String[]) null);
            }
          }
          else if (acceptor != null)
          {
            acceptor.acceptError(
                    "Expecting " + s + " elements, got " + values.getElements().size()
                            + " value(s).",
                    ExpressionSolver.getTarget(e), null,
                    ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
          }

          break;
        default:
          if (acceptor != null)
          {
            acceptor.acceptError(
                    "Expecting a Collection Literal, got " + e.eClass().getName() + ".",
                    ExpressionSolver.getTarget(e), null,
                    ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
          }

      }
    }
  }

  private void checkInteger(org.eclipse.xsmp.xcatalogue.Integer type, Expression e,
          ValidationMessageAcceptor acceptor)
  {

    switch (e.eClass().getClassifierID())
    {
      case XcataloguePackage.BINARY_OPERATION:
      case XcataloguePackage.UNARY_OPERATION:
        break;
      case XcataloguePackage.INTEGER_LITERAL:
      case XcataloguePackage.FLOATING_LITERAL:
        checkIntegralRange(getMin(type), getMax(type), e, acceptor);
        break;
      default:
        if (acceptor != null)
        {
          acceptor.acceptError("Expecting an Integral value, got " + e.eClass().getName() + ".",
                  ExpressionSolver.getTarget(e), null,
                  ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
        }

    }
  }

  private void checkPrimitiveType(PrimitiveType type, Expression e,
          ValidationMessageAcceptor acceptor)
  {
    switch (typeUtil.getPrimitiveType(type))
    {
      case BOOL:
        checkBool(e, acceptor);
        break;
      case CHAR8:
        checkChar(e, acceptor);
        break;
      case DATE_TIME:
        checkDateTime(e, acceptor);
        break;
      case DURATION:
        checkDuration(e, acceptor);
        break;
      case FLOAT32:
        checkDecimalRange(FLOAT32_MIN, FLOAT32_MAX, true, true, e, acceptor);
        break;
      case FLOAT64:
        checkDecimalRange(FLOAT64_MIN, FLOAT64_MAX, true, true, e, acceptor);
        break;
      case INT16:
        checkIntegralRange(INT16_MIN, INT16_MAX, e, acceptor);
        break;
      case INT32:
        checkIntegralRange(INT32_MIN, INT32_MAX, e, acceptor);
        break;
      case INT64:
        checkIntegralRange(INT64_MIN, INT64_MAX, e, acceptor);
        break;
      case INT8:
        checkIntegralRange(INT8_MIN, INT8_MAX, e, acceptor);
        break;
      case STRING8:
        checkString(e, acceptor);
        break;
      case UINT16:
        checkIntegralRange(UINT16_MIN, UINT16_MAX, e, acceptor);
        break;
      case UINT32:
        checkIntegralRange(UINT32_MIN, UINT32_MAX, e, acceptor);
        break;
      case UINT64:
        checkIntegralRange(UINT64_MIN, UINT64_MAX, e, acceptor);
        break;
      case UINT8:
        checkIntegralRange(UINT8_MIN, UINT8_MAX, e, acceptor);
        break;
      default:
        break;
    }
  }

  private void checkSolvedExpression(Type type, Expression e, ValidationMessageAcceptor acceptor)
  {

    switch (type.eClass().getClassifierID())
    {
      case XcataloguePackage.VALUE_REFERENCE:
        checkSolvedExpression(((ValueReference) type).getType(), e, acceptor);
        break;
      case XcataloguePackage.ARRAY:
        checkArray((Array) type, e, acceptor);
        break;
      case XcataloguePackage.ENUMERATION:
        checkEnumeration((Enumeration) type, e, acceptor);
        break;
      case XcataloguePackage.FLOAT:
        checkFloat((org.eclipse.xsmp.xcatalogue.Float) type, e, acceptor);
        break;
      case XcataloguePackage.INTEGER:
        checkInteger((org.eclipse.xsmp.xcatalogue.Integer) type, e, acceptor);
        break;
      case XcataloguePackage.PRIMITIVE_TYPE:
        checkPrimitiveType((PrimitiveType) type, e, acceptor);
        break;
      case XcataloguePackage.STRING:
        checkString((org.eclipse.xsmp.xcatalogue.String) type, e, acceptor);
        break;
      case XcataloguePackage.STRUCTURE:
        checkStructure((Structure) type, e, acceptor);
        break;

      default:
        if (acceptor != null)
        {
          acceptor.acceptError("Unsupported Type " + type.eClass().getName(),
                  ExpressionSolver.getTarget(e), null,
                  ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
        }
        break;
    }
  }

  private void checkStructure(Structure type, Expression e, ValidationMessageAcceptor acceptor)
  {

    switch (e.eClass().getClassifierID())
    {
      case XcataloguePackage.BINARY_OPERATION:
      case XcataloguePackage.UNARY_OPERATION:
        break;
      case XcataloguePackage.COLLECTION_LITERAL:

        final List<Field> fields = type.getMember().stream().filter(Field.class::isInstance)
                .map(Field.class::cast).collect(Collectors.toList());
        final var values = (CollectionLiteral) e;

        final var size = values.getElements().size();
        // check correct number of elements
        if (size <= fields.size())
        {
          // check each field
          for (var i = 0; i < size; ++i)
          {
            checkSolvedExpression(fields.get(i).getType(), values.getElements().get(i), acceptor);
          }
          if (size != fields.size() && size != 0 && acceptor != null)
          {
            acceptor.acceptInfo("Partial initialization. Expecting " + fields.size() + " elements.",
                    ExpressionSolver.getTarget(e), null,
                    ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
          }
        }
        else if (acceptor != null)
        {
          acceptor.acceptError(
                  "Expecting " + fields.size() + " elements, got " + size + " value(s).",
                  ExpressionSolver.getTarget(e), null,
                  ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
        }

        break;
      default:
        if (acceptor != null)
        {
          acceptor.acceptError("Expecting a Collection Literal, got " + e.eClass().getName() + ".",
                  ExpressionSolver.getTarget(e), null,
                  ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type", (String[]) null);
        }

    }

  }

  private BigInteger getMax(org.eclipse.xsmp.xcatalogue.Integer type)
  {
    if (type.getMaximum() != null)
    {
      return type.getMaximum().getInteger(null);
    }

    switch (typeUtil.getPrimitiveType(type))
    {
      case INT16:
        return INT16_MAX;
      case INT32:
        return INT32_MAX;
      case INT64:
        return INT64_MAX;
      case INT8:
        return INT8_MAX;
      case UINT16:
        return UINT16_MAX;
      case UINT32:
        return UINT32_MAX;
      case UINT64:
        return UINT64_MAX;
      case UINT8:
        return UINT8_MAX;
      default:
        return null;
    }
  }

  private BigInteger getMin(org.eclipse.xsmp.xcatalogue.Integer type)
  {
    if (type.getMinimum() != null)
    {
      return type.getMinimum().getInteger(null);
    }

    switch (typeUtil.getPrimitiveType(type))
    {
      case INT16:
        return INT16_MIN;
      case INT32:
        return INT32_MIN;
      case INT64:
        return INT64_MIN;
      case INT8:
        return INT8_MIN;
      case UINT16:
      case UINT32:
      case UINT64:
      case UINT8:
        return UINT16_MIN;
      default:

        return null;
    }

  }

}
