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
package org.eclipse.xsmp.xcatalogue.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.util.ExpressionSolver;
import org.eclipse.xsmp.xcatalogue.BooleanLiteral;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.FloatingLiteral;
import org.eclipse.xsmp.xcatalogue.IntegerLiteral;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 * @author daveluy
 */
public class BinaryOperationImplCustom extends BinaryOperationImpl
{
  @Override
  public Expression solve(ValidationMessageAcceptor acceptor)
  {
    final var leftOperand = getLeftOperand() != null ? getLeftOperand().solve(acceptor) : null;
    final var rightOperand = getRightOperand() != null ? getRightOperand().solve(acceptor) : null;
    if (rightOperand == null || leftOperand == null)
    {
      return null;
      // return duplicateEObject(EcoreUtil.copy(op), op);
    }

    Object result;
    try
    {
      switch (getFeature())
      {
        case "||":
          result = logicalOr(leftOperand, rightOperand);
          break;
        case "&&":
          result = logicalAnd(leftOperand, rightOperand);
          break;
        case "|":
          result = bitwiseOr(leftOperand, rightOperand);
          break;
        case "&":
          result = bitwiseAnd(leftOperand, rightOperand);
          break;
        case "^":
          result = bitwiseXor(leftOperand, rightOperand);
          break;
        case "==":
          result = compareTo(leftOperand, rightOperand) == 0;
          break;
        case "!=":
          result = compareTo(leftOperand, rightOperand) != 0;
          break;
        case "<=":
          result = compareTo(leftOperand, rightOperand) <= 0;
          break;
        case ">=":
          result = compareTo(leftOperand, rightOperand) >= 0;
          break;
        case "<":
          result = compareTo(leftOperand, rightOperand) < 0;
          break;
        case ">":
          result = compareTo(leftOperand, rightOperand) > 0;
          break;
        case "+":
          result = add(leftOperand, rightOperand);
          break;
        case "-":
          result = substract(leftOperand, rightOperand);
          break;
        case "/":
          result = divide(leftOperand, rightOperand);
          break;
        case "*":
          result = multiply(leftOperand, rightOperand);
          break;
        case "%":
          result = remainder(leftOperand, rightOperand);
          break;
        case "<<":
          result = bitwiseLeft(leftOperand, rightOperand);
          break;
        case ">>":
          result = bitwiseRight(leftOperand, rightOperand);
          break;
        default:
          result = null;
      }
    }
    catch (final Exception e)
    {

      if (acceptor != null)
      {
        acceptor.acceptError(e.getMessage(), ExpressionSolver.getTarget(this), null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation",
                (String[]) null);
      }
      return duplicateEObject(EcoreUtil.copy(this), this);
    }
    if (result == null && acceptor != null)
    {
      acceptor.acceptError(
              "Binary Operator '" + getFeature() + "' not supported on "
                      + leftOperand.eClass().getName() + " and " + rightOperand.eClass().getName()
                      + " type.",
              ExpressionSolver.getTarget(this), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              "unsupported_operation", (String[]) null);
    }
    return createExpression(result, this);
  }

  protected Expression createExpression(Object obj, Expression ref)
  {
    if (obj instanceof BigInteger)
    {
      final var e = duplicateEObject(XcatalogueFactory.eINSTANCE.createIntegerLiteral(), ref);
      e.setText(obj.toString());
      return e;
    }
    if (obj instanceof BigDecimal)
    {
      final var e = duplicateEObject(XcatalogueFactory.eINSTANCE.createFloatingLiteral(), ref);
      e.setText(obj.toString());
      return e;
    }
    if (obj instanceof Boolean)
    {
      final var e = duplicateEObject(XcatalogueFactory.eINSTANCE.createBooleanLiteral(), ref);
      e.setIsTrue((Boolean) obj);
      return e;
    }
    if (obj instanceof NamedElement)
    {
      final var e = duplicateEObject(
              XcatalogueFactory.eINSTANCE.createEnumerationLiteralReference(), ref);
      e.setValue((EnumerationLiteral) obj);
      return e;
    }

    return duplicateEObject(EcoreUtil.copy(ref), ref);

  }

  protected Number add(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    Number result;
    if (left instanceof BigInteger && right instanceof BigInteger)
    {
      result = ((BigInteger) left).add((BigInteger) right);
    }
    else if (left instanceof BigInteger && right instanceof BigDecimal)
    {
      result = new BigDecimal((BigInteger) left, MathContext.DECIMAL64).add((BigDecimal) right);
    }
    else if (left instanceof BigDecimal && right instanceof BigInteger)
    {
      result = ((BigDecimal) left).add(new BigDecimal((BigInteger) right, MathContext.DECIMAL64));
    }
    else if (left instanceof BigDecimal && right instanceof BigDecimal)
    {
      result = ((BigDecimal) left).add((BigDecimal) right);
    }
    else
    {
      result = null;
    }

    return result;
  }

  protected Object bitwiseAnd(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    Object result;
    if (left instanceof BigInteger && right instanceof BigInteger)
    {
      result = ((BigInteger) left).and((BigInteger) right);
    }
    else
    {
      result = null;
    }

    return result;
  }

  protected Object bitwiseLeft(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    Object result;
    if (left instanceof BigInteger && right instanceof BigInteger)
    {
      result = ((BigInteger) left).shiftLeft(((BigInteger) right).intValueExact());
    }
    else
    {
      result = null;
    }

    return result;
  }

  protected Object bitwiseOr(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    Object result;
    if (left instanceof BigInteger && right instanceof BigInteger)
    {
      result = ((BigInteger) left).or((BigInteger) right);
    }
    else
    {
      result = null;
    }

    return result;
  }

  protected Object bitwiseRight(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    Object result;
    if (left instanceof BigInteger && right instanceof BigInteger)
    {
      result = ((BigInteger) left).shiftRight(((BigInteger) right).intValueExact());
    }
    else
    {
      result = null;
    }

    return result;
  }

  protected Object bitwiseXor(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    Object result;
    if (left instanceof BigInteger && right instanceof BigInteger)
    {
      result = ((BigInteger) left).xor((BigInteger) right);
    }
    else
    {
      result = null;
    }

    return result;
  }

  protected int compareTo(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    int result;
    if (left instanceof BigInteger && right instanceof BigInteger)
    {
      result = ((BigInteger) left).compareTo((BigInteger) right);
    }
    else if (left instanceof BigInteger && right instanceof BigDecimal)
    {
      result = new BigDecimal((BigInteger) left).compareTo((BigDecimal) right);
    }
    else if (left instanceof BigDecimal && right instanceof BigInteger)
    {
      result = ((BigDecimal) left).compareTo(new BigDecimal((BigInteger) right));
    }
    else if (left instanceof BigDecimal && right instanceof BigDecimal)
    {
      result = ((BigDecimal) left).compareTo((BigDecimal) right);
    }
    else if (left instanceof Boolean && right instanceof Boolean)
    {
      result = ((Boolean) left).compareTo((Boolean) right);
    }
    else
    {
      throw new UnsupportedOperationException("cannot compare " + left + " and " + right);
    }

    return result;
  }

  protected Object logicalAnd(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    Object result;
    if (left instanceof Boolean && right instanceof Boolean)
    {
      result = (Boolean) left && (Boolean) right;
    }
    else
    {
      result = null;
    }

    return result;
  }

  protected Object logicalOr(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    Object result;
    if (left instanceof Boolean && right instanceof Boolean)
    {
      result = (Boolean) left || (Boolean) right;
    }
    else
    {
      result = null;
    }

    return result;
  }

  protected Number multiply(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    Number result;
    if (left instanceof BigInteger && right instanceof BigInteger)
    {
      result = ((BigInteger) left).multiply((BigInteger) right);
    }
    else if (left instanceof BigInteger && right instanceof BigDecimal)
    {
      result = new BigDecimal((BigInteger) left, MathContext.DECIMAL64).multiply((BigDecimal) right,
              MathContext.DECIMAL64);
    }
    else if (left instanceof BigDecimal && right instanceof BigInteger)
    {
      result = ((BigDecimal) left).multiply(
              new BigDecimal((BigInteger) right, MathContext.DECIMAL64), MathContext.DECIMAL64);
    }
    else if (left instanceof BigDecimal && right instanceof BigDecimal)
    {
      result = ((BigDecimal) left).multiply((BigDecimal) right, MathContext.DECIMAL64);
    }
    else
    {
      result = null;
    }

    return result;
  }

  protected Number remainder(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    Number result;
    if (left instanceof BigInteger && right instanceof BigInteger)
    {
      result = ((BigInteger) left).remainder((BigInteger) right);
    }
    else if (left instanceof BigInteger && right instanceof BigDecimal)
    {
      result = new BigDecimal((BigInteger) left, MathContext.DECIMAL64)
              .remainder((BigDecimal) right, MathContext.DECIMAL64);
    }
    else if (left instanceof BigDecimal && right instanceof BigInteger)
    {
      result = ((BigDecimal) left).remainder(
              new BigDecimal((BigInteger) right, MathContext.DECIMAL64), MathContext.DECIMAL64);
    }
    else if (left instanceof BigDecimal && right instanceof BigDecimal)
    {
      result = ((BigDecimal) left).remainder((BigDecimal) right, MathContext.DECIMAL64);
    }
    else
    {
      result = null;
    }

    return result;
  }

  protected Number substract(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    Number result;
    if (left instanceof BigInteger && right instanceof BigInteger)
    {
      result = ((BigInteger) left).subtract((BigInteger) right);
    }
    else if (left instanceof BigInteger && right instanceof BigDecimal)
    {
      result = new BigDecimal((BigInteger) left, MathContext.DECIMAL64).subtract((BigDecimal) right,
              MathContext.DECIMAL64);
    }
    else if (left instanceof BigDecimal && right instanceof BigInteger)
    {
      result = ((BigDecimal) left).subtract(
              new BigDecimal((BigInteger) right, MathContext.DECIMAL64), MathContext.DECIMAL64);
    }
    else if (left instanceof BigDecimal && right instanceof BigDecimal)
    {
      result = ((BigDecimal) left).subtract((BigDecimal) right, MathContext.DECIMAL64);
    }
    else
    {
      result = null;
    }

    return result;
  }

  protected Number divide(Expression leftOperand, Expression rightOperand)
  {

    final var left = getValue(leftOperand);
    final var right = getValue(rightOperand);

    Number result;
    if (left instanceof BigInteger && right instanceof BigInteger)
    {
      result = ((BigInteger) left).divide((BigInteger) right);
    }
    else if (left instanceof BigInteger && right instanceof BigDecimal)
    {
      result = new BigDecimal((BigInteger) left, MathContext.DECIMAL64).divide((BigDecimal) right,
              MathContext.DECIMAL64);
    }
    else if (left instanceof BigDecimal && right instanceof BigInteger)
    {
      result = ((BigDecimal) left).divide(new BigDecimal((BigInteger) right, MathContext.DECIMAL64),
              MathContext.DECIMAL64);
    }
    else if (left instanceof BigDecimal && right instanceof BigDecimal)
    {
      result = ((BigDecimal) left).divide((BigDecimal) right, MathContext.DECIMAL64);
    }
    else
    {
      result = null;
    }

    return result;
  }

  protected Object getValue(Expression e)
  {
    switch (e.eClass().getClassifierID())
    {
      case XcataloguePackage.FLOATING_LITERAL:
        return ((FloatingLiteral) e).getValue();
      case XcataloguePackage.INTEGER_LITERAL:
        return ((IntegerLiteral) e).getValue();
      case XcataloguePackage.BOOLEAN_LITERAL:
        return ((BooleanLiteral) e).isIsTrue();
      default:
        return null;
    }

  }
} // BinaryOperationImplCustom
