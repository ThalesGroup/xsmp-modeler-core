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

import java.util.function.Function;
import java.util.regex.Pattern;

import org.eclipse.xsmp.model.xsmp.Array;
import org.eclipse.xsmp.model.xsmp.CollectionLiteral;
import org.eclipse.xsmp.model.xsmp.DesignatedInitializer;
import org.eclipse.xsmp.model.xsmp.EmptyExpression;
import org.eclipse.xsmp.model.xsmp.Enumeration;
import org.eclipse.xsmp.model.xsmp.Expression;
import org.eclipse.xsmp.model.xsmp.Float;
import org.eclipse.xsmp.model.xsmp.KeywordExpression;
import org.eclipse.xsmp.model.xsmp.String;
import org.eclipse.xsmp.model.xsmp.Structure;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xsmp.util.EnumerationLiteral;
import org.eclipse.xsmp.util.Int16;
import org.eclipse.xsmp.util.Int32;
import org.eclipse.xsmp.util.Int64;
import org.eclipse.xsmp.util.Int8;
import org.eclipse.xsmp.util.PrimitiveType;
import org.eclipse.xsmp.util.Solver.SolverException;
import org.eclipse.xsmp.util.UInt16;
import org.eclipse.xsmp.util.UInt32;
import org.eclipse.xsmp.util.UInt64;
import org.eclipse.xsmp.util.UInt8;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.inject.Inject;

/**
 * This class contains custom validation rules.
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class XsmpcoreValidator extends AbstractXsmpcoreValidator
{

  @Inject
  protected XsmpUtil xsmpUtil;

  protected static final Pattern UUID_PATTERN = java.util.regex.Pattern
          .compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

  protected void checkExpression(Type type, Expression e)
  {
    checkExpression(type, e, false);
  }

  protected void checkExpression(Type type, Expression e, boolean byPointer)
  {
    if (e == null || type == null || type.eIsProxy())
    {
      return;
    }
    if (e instanceof final DesignatedInitializer de)
    {
      e = de.getExpr();
    }

    if (byPointer)
    {
      if (!(e instanceof KeywordExpression) && !(e instanceof EmptyExpression))
      {
        acceptError("Expecting a pointer, got " + e.getClass().getSimpleName() + ".", e, null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
      }
    }
    else if (e instanceof KeywordExpression)
    {
      acceptError("Expecting a value, got a keyword.", e, null,
              ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
    }
    else if (e instanceof EmptyExpression)
    {
      acceptError("Missing expression.", e, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              "invalid_type");
    }
    else
    {
      switch (type.eClass().getClassifierID())
      {
        case XsmpPackage.ARRAY:
          checkExpression((Array) type, e);
          break;
        case XsmpPackage.ENUMERATION:
          checkExpression((Enumeration) type, e);
          break;
        case XsmpPackage.FLOAT:
          checkExpression((Float) type, e);
          break;
        case XsmpPackage.INTEGER:
          checkExpression((org.eclipse.xsmp.model.xsmp.Integer) type, e);
          break;
        case XsmpPackage.PRIMITIVE_TYPE:
          checkExpression((org.eclipse.xsmp.model.xsmp.PrimitiveType) type, e);
          break;
        case XsmpPackage.STRING:
          checkExpression((String) type, e);
          break;
        case XsmpPackage.STRUCTURE, XsmpPackage.CLASS, XsmpPackage.EXCEPTION:
          checkExpression((Structure) type, e);
          break;
        default:
          // ignore other types
          break;
      }
    }
  }

  private void checkExpression(Enumeration type, Expression e)
  {
    try
    {
      final var literal = xsmpUtil.getEnumerationLiteral(e);
      final var value = safeExpression(e, v -> v);

      if (value != null && !(value instanceof EnumerationLiteral))
      {
        final var fqn = xsmpUtil.fqn(literal).toString();
        warning("Should use the EnumerationLiteral " + fqn, e, null,
                XsmpcatIssueCodesProvider.ENUMERATION_LITERAL_RECOMMANDED, fqn);
      }

    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
  }

  private Integer compareTo(Expression left, Expression right, Type type)
  {
    if (left != null && right != null)
    {
      try
      {
        return xsmpUtil.getSolver().getValue(left, type)
                .compareTo(xsmpUtil.getSolver().getValue(right, type));
      }
      catch (final SolverException ex)
      {
        error(ex.getMessage(), ex.getExpression(), null);
      }
      catch (final Exception ex)
      {
        // ignore
      }
    }
    return null;
  }

  protected void checkExpression(Array type, Expression e)
  {
    if (type.getSize() != null)
    {
      if (e instanceof final CollectionLiteral values)
      {
        final var size = safeExpression(type.getSize(), PrimitiveType::int64Value, Int64.ZERO,
                Int64.MAX_VALUE);

        if (size == null)
        {
          return;
        }

        final var s = size.getValue();

        // check correct number of elements
        if (values.getElements().size() == 1
                && values.getElements().get(0) instanceof EmptyExpression)
        {
          // OK
        }
        else if (values.getElements().size() <= s)
        {
          // check each array field
          values.getElements().stream().forEach(j -> checkExpression(type.getItemType(), j));

          if (s != values.getElements().size() && !values.getElements().isEmpty())
          {
            acceptInfo("Partial initialization, the array type expects " + s + " elements.", e,
                    null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
          }
        }
        else
        {
          acceptError("Expecting " + s + " elements, got " + values.getElements().size() + ".", e,
                  null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
        }
      }
      else
      {
        acceptError("Expecting a Collection Literal, got " + e.eClass().getName() + ".", e, null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
      }

    }

  }

  private PrimitiveType getMax(org.eclipse.xsmp.model.xsmp.Integer type)
  {
    if (type.getMaximum() != null)
    {
      return uncheckedExpression(type.getMaximum());
    }

    final var kind = xsmpUtil.getPrimitiveTypeKind(type);
    return switch (kind)
    {
      case INT16 -> Int16.MAX_VALUE;
      case INT32 -> Int32.MAX_VALUE;
      case UINT64 -> UInt64.MAX_VALUE;
      case INT64 -> Int64.MAX_VALUE;
      case INT8 -> Int8.MAX_VALUE;
      case UINT16 -> UInt16.MAX_VALUE;
      case UINT32 -> UInt32.MAX_VALUE;
      case UINT8 -> UInt8.MAX_VALUE;
      default -> null;
    };
  }

  private PrimitiveType getMin(org.eclipse.xsmp.model.xsmp.Integer type)
  {
    if (type.getMinimum() != null)
    {
      return uncheckedExpression(type.getMinimum());
    }

    final var kind = xsmpUtil.getPrimitiveTypeKind(type);
    return switch (kind)
    {
      case INT16 -> Int16.MIN_VALUE;
      case INT32 -> Int32.MIN_VALUE;
      case INT64 -> Int64.MIN_VALUE;
      case INT8 -> Int8.MIN_VALUE;
      case UINT16 -> UInt16.MIN_VALUE;
      case UINT32 -> UInt32.MIN_VALUE;
      case UINT64 -> UInt64.MIN_VALUE;
      case UINT8 -> UInt8.MIN_VALUE;
      default -> null;
    };
  }

  private void checkExpression(org.eclipse.xsmp.model.xsmp.Integer type, Expression e)
  {
    safeExpression(e, v -> v, getMin(type), getMax(type));
  }

  private void checkExpression(org.eclipse.xsmp.model.xsmp.PrimitiveType type, Expression e)
  {
    try
    {
      xsmpUtil.getSolver().getValue(e, type);
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
    catch (final Exception ex)
    {
      error(ex.getMessage(), e, null);
    }

  }

  private void checkExpression(Structure type, Expression e)
  {
    if (e instanceof final CollectionLiteral values)
    {
      final var fields = xsmpUtil.getAssignableFields(type);

      final var size = values.getElements().size();
      // check correct number of elements
      if (size == 1 && values.getElements().get(0) instanceof EmptyExpression)
      {
        // OK
      }
      else if (size <= fields.size())
      {
        // check each field
        for (var i = 0; i < size - 1; ++i)
        {
          checkExpression(fields.get(i).getType(), values.getElements().get(i));
        }
        final var last = size - 1;
        if (last >= 0 && !(values.getElements().get(last) instanceof EmptyExpression))
        {
          checkExpression(fields.get(last).getType(), values.getElements().get(last));
        }
        if (size != fields.size() && size != 0)
        {
          acceptInfo("Partial initialization. Expecting " + fields.size() + " elements.", e, null,
                  ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
        }
      }
      else
      {
        acceptError("Expecting " + fields.size() + " elements, got " + size + " value(s).", e, null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
      }
    }
    else
    {
      acceptError("Expecting a Collection Literal, got " + e.eClass().getName() + ".", e, null,
              ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
    }
  }

  private void checkExpression(org.eclipse.xsmp.model.xsmp.Float type, Expression e)
  {

    final var value = safeExpression(e, type);

    if (value != null)
    {
      final var minInclusive = type.isMinInclusive();
      final var maxInclusive = type.isMaxInclusive();
      final var minCmp = compareTo(e, type.getMinimum(), type);
      final var maxCmp = compareTo(e, type.getMaximum(), type);
      if (minCmp != null && (minInclusive ? minCmp < 0 : minCmp <= 0)
              || maxCmp != null && (maxInclusive ? maxCmp > 0 : maxCmp >= 0))
      {
        final var min = uncheckedExpression(type.getMinimum());
        final var max = uncheckedExpression(type.getMaximum());

        acceptError(
                "Value " + value + " is not in range " + (min != null ? min : "*")
                        + (minInclusive ? " ." : " <") + "." + (maxInclusive ? ". " : "< ")
                        + (max != null ? max : "*") + ".",
                e, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
                XsmpcatIssueCodesProvider.INVALID_VALUE_RANGE);
      }
    }
  }

  <R extends PrimitiveType> R uncheckedExpression(Expression e, Function<PrimitiveType, R> supplier)
  {
    try
    {
      return supplier.apply(xsmpUtil.getSolver().getValue(e));
    }
    catch (final Exception ex)
    {
      error(ex.getMessage(), e, null);
    }
    return null;
  }

  PrimitiveType uncheckedExpression(Expression e)
  {
    try
    {
      return xsmpUtil.getSolver().getValue(e);
    }
    catch (final Exception ex)
    {
      // ignore
    }
    return null;
  }

  PrimitiveType safeExpression(Expression e)
  {
    try
    {
      return xsmpUtil.getSolver().getValue(e);
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
    catch (final Exception ex)
    {
      error(ex.getMessage(), e, null);
    }
    return null;
  }

  PrimitiveType safeExpression(Expression e, Type type)
  {
    try
    {
      return xsmpUtil.getSolver().getValue(e, type);
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
    catch (final Exception ex)
    {
      error(ex.getMessage(), e, null);
    }
    return null;
  }

  <R extends PrimitiveType> R safeExpression(Expression e, Function<PrimitiveType, R> supplier)
  {
    try
    {
      return supplier.apply(xsmpUtil.getSolver().getValue(e));
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
    catch (final Exception ex)
    {
      error(ex.getMessage(), e, null);
    }
    return null;
  }

  <R extends PrimitiveType> R safeExpression(Expression e, Function<PrimitiveType, R> supplier,
          PrimitiveType min, PrimitiveType max)
  {
    try
    {
      final var value = supplier.apply(xsmpUtil.getSolver().getValue(e));

      if (value != null && (min != null && value.compareTo(min) < 0
              || max != null && value.compareTo(max) > 0))
      {
        acceptError("Integral value " + value + " is not in range " + min + " ... " + max + ".", e,
                null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
                XsmpcatIssueCodesProvider.INVALID_VALUE_RANGE);
      }
      return value;
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
    catch (final Exception ex)
    {
      error(ex.getMessage(), e, null);
    }
    return null;
  }

  private void checkExpression(org.eclipse.xsmp.model.xsmp.String type, Expression e)
  {

    if (type.getLength() == null)
    {
      return;
    }

    final var length = safeExpression(type.getLength(), PrimitiveType::int64Value);

    if (length == null)
    {
      return;
    }
    final var value = safeExpression(e, PrimitiveType::string8Value);

    if (value != null && value.getValue().length() > length.getValue())
    {
      acceptError(
              "The String length cannot exceed " + length + " characters, got "
                      + value.getValue().length() + ".",
              e, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
    }

  }
}
