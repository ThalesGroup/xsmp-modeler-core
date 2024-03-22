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

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.model.xsmp.BinaryOperation;
import org.eclipse.xsmp.model.xsmp.BooleanLiteral;
import org.eclipse.xsmp.model.xsmp.BuiltInConstant;
import org.eclipse.xsmp.model.xsmp.BuiltInExpression;
import org.eclipse.xsmp.model.xsmp.BuiltInFunction;
import org.eclipse.xsmp.model.xsmp.CharacterLiteral;
import org.eclipse.xsmp.model.xsmp.CollectionLiteral;
import org.eclipse.xsmp.model.xsmp.Constant;
import org.eclipse.xsmp.model.xsmp.DesignatedInitializer;
import org.eclipse.xsmp.model.xsmp.EmptyExpression;
import org.eclipse.xsmp.model.xsmp.Enumeration;
import org.eclipse.xsmp.model.xsmp.Expression;
import org.eclipse.xsmp.model.xsmp.FloatingLiteral;
import org.eclipse.xsmp.model.xsmp.IntegerLiteral;
import org.eclipse.xsmp.model.xsmp.NamedElementReference;
import org.eclipse.xsmp.model.xsmp.ParenthesizedExpression;
import org.eclipse.xsmp.model.xsmp.SimpleType;
import org.eclipse.xsmp.model.xsmp.StringLiteral;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.model.xsmp.UnaryOperation;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.Tuples;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Solver
{
  @Inject
  private XsmpUtil xsmpUtil;

  @Inject
  private IResourceScopeCache cache;

  public static class SolverException extends RuntimeException
  {
    private static final long serialVersionUID = 6052092630728022867L;

    private final transient Expression expression;

    public SolverException(Expression expression, String msg)
    {
      super(msg);
      this.expression = expression;
    }

    public Expression getExpression()
    {
      return expression;
    }

  }

  protected PrimitiveType doGetValue(BinaryOperation e) throws SolverException
  {

    final var leftOperand = doGetValue(e.getLeftOperand());

    final var rightOperand = doGetValue(e.getRightOperand());

    try
    {
      return switch (e.getFeature())
      {
        case "||" -> leftOperand.logicalOr(rightOperand);
        case "&&" -> leftOperand.logicalAnd(rightOperand);
        case "|" -> leftOperand.or(rightOperand);
        case "&" -> leftOperand.and(rightOperand);
        case "^" -> leftOperand.xor(rightOperand);
        case "==" -> Bool.valueOf(leftOperand.compareTo(rightOperand) == 0);
        case "!=" -> Bool.valueOf(leftOperand.compareTo(rightOperand) != 0);
        case "<=" -> Bool.valueOf(leftOperand.compareTo(rightOperand) <= 0);
        case ">=" -> Bool.valueOf(leftOperand.compareTo(rightOperand) >= 0);
        case "<" -> Bool.valueOf(leftOperand.compareTo(rightOperand) < 0);
        case ">" -> Bool.valueOf(leftOperand.compareTo(rightOperand) > 0);
        case "+" -> leftOperand.add(rightOperand);
        case "-" -> leftOperand.subtract(rightOperand);
        case "/" -> leftOperand.divide(rightOperand);
        case "*" -> leftOperand.multiply(rightOperand);
        case "%" -> leftOperand.remainder(rightOperand);
        case "<<" -> leftOperand.shiftLeft(rightOperand);
        case ">>" -> leftOperand.shiftRight(rightOperand);
        default -> throw new UnsupportedOperationException();
      };
    }
    catch (final UnsupportedOperationException ex)
    {
      throw new SolverException(e,
              "Binary \"operator" + e.getFeature() + "\" does not support \""
                      + leftOperand.getClass().getSimpleName() + "\" and \""
                      + rightOperand.getClass().getSimpleName() + "\" operands.");
    }
    catch (final Exception ex)
    {
      throw new SolverException(e, "error: " + ex.getMessage());
    }

  }

  protected PrimitiveType doGetValue(BuiltInConstant e) throws SolverException
  {
    final var solver = constantMappings.get(e.getName());
    if (solver == null)
    {
      throw new SolverException(e, "unknown constant: " + e.getName());
    }
    return solver.solve(e);

  }

  protected PrimitiveType doGetValue(BuiltInFunction e) throws SolverException
  {
    final var solver = functionMappings.get(e.getName());
    if (solver == null)
    {
      throw new SolverException(e, "unknown function: " + e.getName());
    }

    return solver.solve(e);
  }

  protected PrimitiveType doGetValue(UnaryOperation e) throws SolverException
  {
    final var value = doGetValue(e.getOperand());

    try
    {
      return switch (e.getFeature())
      {
        case "!" -> value.not();
        case "~" -> value.unaryComplement();
        case "-" -> value.negate();
        case "+" -> value.plus();
        default -> throw new UnsupportedOperationException();
      };
    }
    catch (final UnsupportedOperationException ex)
    {
      throw new SolverException(e, "no match for \" operator" + e.getFeature()
              + " \" (operand type is \" " + value.getClass().getSimpleName() + " \").");
    }
    catch (final Exception ex)
    {
      throw new SolverException(e, ex.getMessage());
    }
  }

  protected PrimitiveType doGetValue(NamedElementReference e) throws SolverException
  {
    final var value = e.getValue();
    if (value != null && !value.eIsProxy())
    {

      if (EcoreUtil.isAncestor(value, e))
      {
        throw new SolverException(e, "the value is recursive.");
      }
      if (value instanceof final Constant cst)
      {
        return getValue(cst.getValue(), cst.getType());
      }
      if (value instanceof org.eclipse.xsmp.model.xsmp.EnumerationLiteral)
      {
        return EnumerationLiteral.valueOf((org.eclipse.xsmp.model.xsmp.EnumerationLiteral) value);
      }
      throw new SolverException(e, "invalid reference type \"" + value.getClass().getSimpleName()
              + "\", only Contant and EnumerationLiteral are supported.");
    }
    throw new SolverException(e, "Unresolved reference.");
  }

  protected PrimitiveType doGetValue(CollectionLiteral e) throws SolverException
  {
    final var elements = e.getElements();

    if (elements.size() == 1)
    {

      final var type = xsmpUtil.getType(e);
      if (type instanceof SimpleType)
      {
        final var expression = elements.get(0);
        if (!(expression instanceof EmptyExpression))
        {
          return getValue(elements.get(0));
        }
        switch (xsmpUtil.getPrimitiveTypeKind(type))
        {
          case BOOL:
            return Bool.FALSE;
          case CHAR8:
            return Char8.ZERO;
          case DATE_TIME:
            return DateTime.ZERO;
          case DURATION:
            return Duration.ZERO;
          case ENUM:
            return Int32.ZERO;
          case FLOAT32:
            return Float32.ZERO;
          case FLOAT64:
            return Float64.ZERO;
          case INT16:
            return Int16.ZERO;
          case INT32:
            return Int32.ZERO;
          case INT64:
            return Int64.ZERO;
          case INT8:
            return Int8.ZERO;
          case STRING8:
            return String8.valueOf("");
          case UINT16:
            return UInt16.ZERO;
          case UINT32:
            return UInt32.ZERO;
          case UINT64:
            return UInt64.ZERO;
          case UINT8:
            return UInt8.ZERO;
          case NONE:
            break;
        }
      }
    }
    throw new SolverException(e, "SimpleType requires only one element.");
  }

  protected Char8 doGetValue(CharacterLiteral e) throws SolverException
  {
    final var value = e.getValue();
    try
    {
      // remove quotes and escape sequences
      final var v = XsmpUtil.translateEscapes(value.substring(1, value.length() - 1));
      if (v.length() > 1)
      {
        throw new UnsupportedOperationException("Invalid char length.");
      }
      return Char8.valueOf(v.charAt(0));
    }
    catch (final Exception ex)
    {
      throw new SolverException(e, ex.getMessage());
    }
  }

  protected String8 doGetValue(StringLiteral e) throws SolverException
  {
    try
    {
      // remove encoding prefix, quotes and escape sequences
      return String8.valueOf(e.getValue().stream().map(XsmpUtil::StringLiteralToString)
              .collect(Collectors.joining()));
    }
    catch (final Exception ex)
    {
      throw new SolverException(e, ex.getMessage());
    }

  }

  protected PrimitiveType doGetValue(FloatingLiteral e)
  {
    final var text = e.getText();

    final var withoutSeparator = text.replace("'", "");
    final var lastChar = withoutSeparator.charAt(withoutSeparator.length() - 1);
    if (lastChar == 'F' || lastChar == 'f')
    {
      return Float32.valueOf(withoutSeparator.substring(0, withoutSeparator.length() - 1));
    }

    return Float64.valueOf(withoutSeparator);
  }

  private PrimitiveType parseIntegral(String text, int radix, boolean isLong, boolean isUnsigned)
  {
    if (isUnsigned)
    {
      if (!isLong)
      {
        try
        {
          return UInt32.valueOf(text, radix);
        }
        catch (final NumberFormatException ex)
        {
          // ignore try to parse as UnsignedLong
        }
      }

      try
      {
        return UInt64.valueOf(text, radix);
      }
      catch (final NumberFormatException ex)
      {
        throw new UnsupportedOperationException(
                "\'" + text + "\' is not parsable as UnsignedLong.");
      }

    }

    if (!isLong)
    {
      try
      {
        return Int32.valueOf(text, radix);
      }
      catch (final NumberFormatException ex)
      {
        // ignore try to parse as Long
      }
    }

    try
    {
      return Int64.valueOf(text, radix);
    }
    catch (final NumberFormatException ex)
    {
      throw new UnsupportedOperationException("\'" + text + "\' is not parsable as Long.");
    }
  }

  protected PrimitiveType doGetValue(IntegerLiteral e) throws SolverException
  {
    final var text = e.getText();

    var withoutSeparator = text.replace("'", "");
    var isLong = false;
    var isUnsigned = false;

    var lastChar = withoutSeparator.charAt(withoutSeparator.length() - 1);
    // remove trailing suffix
    if (lastChar == 'U' || lastChar == 'u')
    {
      withoutSeparator = withoutSeparator.substring(0, withoutSeparator.length() - 1);
      lastChar = withoutSeparator.charAt(withoutSeparator.length() - 1);
      isUnsigned = true;
    }
    if (lastChar == 'L' || lastChar == 'l')
    {
      withoutSeparator = withoutSeparator.substring(0, withoutSeparator.length() - 1);
      lastChar = withoutSeparator.charAt(withoutSeparator.length() - 1);
      isLong = true;
    }
    if (lastChar == 'U' || lastChar == 'u')
    {
      withoutSeparator = withoutSeparator.substring(0, withoutSeparator.length() - 1);
      isUnsigned = true;
    }
    int radix;

    if (withoutSeparator.startsWith("0X") || withoutSeparator.startsWith("0x"))
    {
      radix = 16;
      withoutSeparator = withoutSeparator.substring(2);
    }
    else if (withoutSeparator.startsWith("0B") || withoutSeparator.startsWith("0b"))
    {
      radix = 2;
      withoutSeparator = withoutSeparator.substring(2);
    }
    else if (withoutSeparator.startsWith("0"))
    {
      radix = 8;
    }
    else
    {
      radix = 10;
    }
    try
    {
      return parseIntegral(withoutSeparator, radix, isLong, isUnsigned);
    }
    catch (final UnsupportedOperationException ex)
    {
      throw new SolverException(e, ex.getMessage());
    }
  }

  protected PrimitiveType doGetValue(Expression e) throws SolverException
  {

    try
    {
      switch (e.eClass().getClassifierID())
      {
        case XsmpPackage.BINARY_OPERATION:
          return doGetValue((BinaryOperation) e);
        case XsmpPackage.BOOLEAN_LITERAL:
          return Bool.valueOf(((BooleanLiteral) e).isIsTrue());
        case XsmpPackage.BUILT_IN_CONSTANT:
          return doGetValue((BuiltInConstant) e);
        case XsmpPackage.BUILT_IN_FUNCTION:
          return doGetValue((BuiltInFunction) e);
        case XsmpPackage.CHARACTER_LITERAL:
          return doGetValue((CharacterLiteral) e);
        case XsmpPackage.COLLECTION_LITERAL:
          return doGetValue((CollectionLiteral) e);
        case XsmpPackage.NAMED_ELEMENT_REFERENCE:
          return doGetValue((NamedElementReference) e);
        case XsmpPackage.FLOATING_LITERAL:
          return doGetValue((FloatingLiteral) e);
        case XsmpPackage.INTEGER_LITERAL:
          return doGetValue((IntegerLiteral) e);
        case XsmpPackage.PARENTHESIZED_EXPRESSION:
          return doGetValue(((ParenthesizedExpression) e).getExpr());
        case XsmpPackage.DESIGNATED_INITIALIZER:
          return doGetValue(((DesignatedInitializer) e).getExpr());
        case XsmpPackage.STRING_LITERAL:
          return doGetValue((StringLiteral) e);
        case XsmpPackage.UNARY_OPERATION:
          return doGetValue((UnaryOperation) e);
        // case XsmpPackage.KEYWORD_EXPRESSION:
        // return e;
        // case XsmpPackage.EMPTY_EXPRESSION:
        // return null;
        default:
          break;
      }
    }
    catch (final SolverException ex)
    {
      // re-throw the exception
      throw ex;
    }
    catch (final Exception ex)
    {
      throw new SolverException(e, ex.getMessage());
    }

    throw new SolverException(e, "Unsupported expression: " + e.getClass().getSimpleName());
  }

  public PrimitiveType getValue(Expression e, Type type) throws SolverException
  {
    if (e == null)
    {
      return null;
    }
    try
    {
      return getValue(e).convert(xsmpUtil.getPrimitiveTypeKind(type));
    }
    catch (final SolverException ex)
    {
      throw ex;
    }
    catch (final Exception ex)
    {
      throw new SolverException(e, ex.getMessage());
    }
  }

  /**
   * Get the value of an expression converted to the specified kind
   *
   * @param e
   *          the expression
   * @param kind
   *          the target primitive type kind
   * @return the value of the expression
   * @throws SolverException
   *           if the expression cannot be solved
   */
  public PrimitiveType getValue(Expression e, PrimitiveTypeKind kind) throws SolverException
  {

    if (e == null)
    {
      return null;
    }
    try
    {
      return getValue(e).convert(kind);
    }
    catch (final SolverException ex)
    {
      throw ex;
    }
    catch (final Exception ex)
    {
      throw new SolverException(e, ex.getMessage());
    }

  }

  /**
   * Get the value of an expression
   *
   * @param e
   *          the expression
   * @return the value of the expression
   * @throws SolverException
   *           if the expression cannot be solved
   */
  public PrimitiveType getValue(Expression e) throws SolverException
  {
    if (e == null)
    {
      return null;
    }
    return cache.get(Tuples.pair(e, "Value"), e.eResource(), () -> doGetValue(e));
  }

  public EnumerationLiteral getValue(Expression e, Enumeration enumeration)
  {
    final var value = getValue(e);
    if (value instanceof final EnumerationLiteral literal)
    {
      if (!EcoreUtil.isAncestor(enumeration, literal.getValue()))
      {
        throw new SolverException(e, "Literal " + literal.getValue().getName() + " is not of type "
                + enumeration.getName());
      }

      return literal;
    }

    final var integer = value.int32Value().getValue();

    final var literal = enumeration.getLiteral().stream()
            .filter(l -> integer == getValue(l.getValue()).int32Value().getValue()).findFirst();
    if (literal.isPresent())
    {
      return EnumerationLiteral.valueOf(literal.get());
    }

    throw new SolverException(e, "No Enumeration Literal with value \" " + integer + " \" found.");

  }

  /**
   * @author daveluy
   * @param <T>
   */
  public interface BuiltInSolver<T extends BuiltInExpression>
  {
    /**
     * @param builtIn
     * @param acceptor
     * @return the solved expression
     */
    PrimitiveType solve(T builtIn) throws SolverException;

    /**
     * @return the builtin documentation
     */
    String getDocumentation();
  }

  static class BuiltInConstantSolver implements BuiltInSolver<BuiltInConstant>
  {

    private final String doc;

    private final PrimitiveType value;

    BuiltInConstantSolver(PrimitiveType value, String doc)
    {
      this.doc = doc;
      this.value = value;
    }

    @Override
    public PrimitiveType solve(BuiltInConstant cst)
    {
      return value;
    }

    @Override
    public String getDocumentation()
    {
      return doc;
    }
  }

  /**
   * the mapping for builtin constants
   */
  public final Map<String, BuiltInSolver<BuiltInConstant>> constantMappings = ImmutableMap
          .<String, BuiltInSolver<BuiltInConstant>> builder()
          .put("PI", new BuiltInConstantSolver(Float64.valueOf(Math.PI),
                  "The double value that is closer than any other to pi, the ratio of the circumference of a circle to its diameter."))
          .put("E", new BuiltInConstantSolver(Float64.valueOf(Math.E),
                  "The double value that is closer than any other to e, the base of the natural logarithms."))
          .build();

  private class UnaryFunction implements BuiltInSolver<BuiltInFunction>
  {

    private final String doc;

    private final Function<Double, PrimitiveType> op;

    UnaryFunction(Function<Double, PrimitiveType> op, String doc)
    {
      this.doc = doc;
      this.op = op;
    }

    @Override
    public PrimitiveType solve(BuiltInFunction function) throws SolverException
    {
      if (function.getParameter().size() == 1)
      {

        final var value = doGetValue(function.getParameter().get(0));

        try
        {
          return op.apply(value.float64Value().getValue());
        }
        catch (final Exception e)
        {
          throw new SolverException(function, e.getMessage());
        }

      }
      throw new SolverException(function, "function expect only one parameter.");
    }

    @Override
    public String getDocumentation()
    {
      return doc;
    }
  }

  /**
   * the mapping for builtin functions
   */
  public final Map<String, BuiltInSolver<BuiltInFunction>> functionMappings = ImmutableMap
          .<String, BuiltInSolver<BuiltInFunction>> builder()
          .put("cos",
                  new UnaryFunction(v -> Float64.valueOf(Math.cos(v)),
                          "Returns the trigonometric cosine of an angle."))
          .put("sin",
                  new UnaryFunction(v -> Float64.valueOf(Math.sin(v)),
                          "Returns the trigonometric sine of an angle."))
          .put("tan",
                  new UnaryFunction(v -> Float64.valueOf(Math.tan(v)),
                          "Returns the trigonometric tangent of an angle."))
          .put("acos", new UnaryFunction(v -> Float64.valueOf(Math.acos(v)),
                  "Returns the arc cosine of a value; the returned angle is in the range 0.0 through pi."))
          .put("asin", new UnaryFunction(v -> Float64.valueOf(Math.asin(v)),
                  "Returns the arc sine of a value; the returned angle is in the range -pi/2 through pi/2."))
          .put("atan", new UnaryFunction(v -> Float64.valueOf(Math.atan(v)),
                  "Returns the arc tangent of a value; the returned angle is in the range -pi/2 through pi/2."))
          .put("cosh", new UnaryFunction(v -> Float64.valueOf(Math.cosh(v)),
                  "Returns the hyperbolic cosine of a double value. The hyperbolic cosine of x is defined to be (ex + e-x)/2 where e is Euler's number."))
          .put("sinh", new UnaryFunction(v -> Float64.valueOf(Math.sinh(v)),
                  "Returns the hyperbolic sine of a double value. The hyperbolic sine of x is defined to be (ex - e-x)/2 where e is Euler's number. "))
          .put("tanh", new UnaryFunction(v -> Float64.valueOf(Math.tanh(v)),
                  "Returns the hyperbolic tangent of a double value. The hyperbolic tangent of x is defined to be (ex - e-x)/ (ex + e-x), in other words, sinh(x)/cosh(x). Note that the absolute value of the exact tanh is always less than 1. "))
          .put("exp",
                  new UnaryFunction(v -> Float64.valueOf(Math.exp(v)),
                          "Returns Euler's number e raised to the power of a double value."))
          .put("log",
                  new UnaryFunction(v -> Float64.valueOf(Math.log(v)),
                          "Returns the natural logarithm (base e) of a double value."))
          .put("log10",
                  new UnaryFunction(v -> Float64.valueOf(Math.log10(v)),
                          "Returns the base 10 logarithm of a double value."))
          .put("expm1", new UnaryFunction(v -> Float64.valueOf(Math.expm1(v)),
                  "Returns ex -1. Note that for values of x near 0, the exact sum of expm1(x) + 1 is much closer to the true result of ex than exp(x)."))
          .put("log1p", new UnaryFunction(v -> Float64.valueOf(Math.log1p(v)),
                  "Returns the natural logarithm of the sum of the argument and 1. Note that for small values x, the result of log1p(x) is much closer to the true result of ln(1 + x) than the floating-point evaluation of log(1.0+x)."))
          .put("sqrt",
                  new UnaryFunction(v -> Float64.valueOf(Math.sqrt(v)),
                          "Returns the correctly rounded positive square root of a double value."))
          .put("ceil", new UnaryFunction(v -> Float64.valueOf(Math.ceil(v)),
                  "Returns the smallest (closest to negative infinity) double value that is greater than or equal to the argument and is equal to a mathematical integer. "))
          .put("floor", new UnaryFunction(v -> Float64.valueOf(Math.floor(v)),
                  "Returns the largest (closest to positive infinity) double value that is less than or equal to the argument and is equal to a mathematical integer. "))
          .put("abs", new UnaryFunction(v -> Float64.valueOf(Math.abs(v)),
                  "Returns the absolute value of a double value. If the argument is not negative, the argument is returned. If the argument is negative, the negation of the argument is returned."))
          // for float types
          .put("cosf",
                  new UnaryFunction(v -> Float32.valueOf((float) Math.cos(v)),
                          "Returns the trigonometric cosine of an angle."))
          .put("sinf",
                  new UnaryFunction(v -> Float32.valueOf((float) Math.sin(v)),
                          "Returns the trigonometric sine of an angle."))
          .put("tanf",
                  new UnaryFunction(v -> Float32.valueOf((float) Math.tan(v)),
                          "Returns the trigonometric tangent of an angle."))
          .put("acosf", new UnaryFunction(v -> Float32.valueOf((float) Math.acos(v)),
                  "Returns the arc cosine of a value; the returned angle is in the range 0.0 through pi."))
          .put("asinf", new UnaryFunction(v -> Float32.valueOf((float) Math.asin(v)),
                  "Returns the arc sine of a value; the returned angle is in the range -pi/2 through pi/2."))
          .put("atanf", new UnaryFunction(v -> Float32.valueOf((float) Math.atan(v)),
                  "Returns the arc tangent of a value; the returned angle is in the range -pi/2 through pi/2."))
          .put("coshf", new UnaryFunction(v -> Float32.valueOf((float) Math.cosh(v)),
                  "Returns the hyperbolic cosine of a float value. The hyperbolic cosine of x is defined to be (ex + e-x)/2 where e is Euler's number."))
          .put("sinhf", new UnaryFunction(v -> Float32.valueOf((float) Math.sinh(v)),
                  "Returns the hyperbolic sine of a float value. The hyperbolic sine of x is defined to be (ex - e-x)/2 where e is Euler's number. "))
          .put("tanhf", new UnaryFunction(v -> Float32.valueOf((float) Math.tanh(v)),
                  "Returns the hyperbolic tangent of a float value. The hyperbolic tangent of x is defined to be (ex - e-x)/ (ex + e-x), in other words, sinh(x)/cosh(x). Note that the absolute value of the exact tanh is always less than 1. "))

          .put("expf",
                  new UnaryFunction(v -> Float32.valueOf((float) Math.exp(v)),
                          "Returns Euler's number e raised to the power of a float value."))
          .put("logf",
                  new UnaryFunction(v -> Float32.valueOf((float) Math.log(v)),
                          "Returns the natural logarithm (base e) of a float value."))
          .put("log10f",
                  new UnaryFunction(v -> Float32.valueOf((float) Math.log10(v)),
                          "Returns the base 10 logarithm of a float value."))
          .put("expm1f", new UnaryFunction(v -> Float32.valueOf((float) Math.expm1(v)),
                  "Returns ex -1. Note that for values of x near 0, the exact sum of expm1(x) + 1 is much closer to the true result of ex than exp(x)."))
          .put("log1pf", new UnaryFunction(v -> Float32.valueOf((float) Math.log1p(v)),
                  "Returns the natural logarithm of the sum of the argument and 1. Note that for small values x, the result of log1p(x) is much closer to the true result of ln(1 + x) than the floating-point evaluation of log(1.0+x)."))
          .put("sqrtf",
                  new UnaryFunction(v -> Float32.valueOf((float) Math.sqrt(v)),
                          "Returns the correctly rounded positive square root of a float value."))
          .put("ceilf", new UnaryFunction(v -> Float32.valueOf((float) Math.ceil(v)),
                  "Returns the smallest (closest to negative infinity) float value that is greater than or equal to the argument and is equal to a mathematical integer. "))
          .put("floorf", new UnaryFunction(v -> Float32.valueOf((float) Math.floor(v)),
                  "Returns the largest (closest to positive infinity) float value that is less than or equal to the argument and is equal to a mathematical integer. "))
          .put("absf", new UnaryFunction(v -> Float32.valueOf((float) Math.abs(v)),
                  "Returns the absolute value of a float value. If the argument is not negative, the argument is returned. If the argument is negative, the negation of the argument is returned."))

          .build();

}
