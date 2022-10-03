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
package org.eclipse.xsmp.util;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.xcatalogue.BuiltInConstant;
import org.eclipse.xsmp.xcatalogue.BuiltInExpression;
import org.eclipse.xsmp.xcatalogue.BuiltInFunction;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.common.collect.ImmutableMap;

/**
 * @author daveluy
 */
public class ExpressionSolver
{

  /**
   * @author daveluy
   * @param <T>
   */
  public interface Solver<T extends BuiltInExpression>
  {
    /**
     * @param builtIn
     * @param acceptor
     * @return the solved expression
     */
    Expression solve(T builtIn, ValidationMessageAcceptor acceptor);

    /**
     * @return the builtin documentation
     */
    String getDocumentation();
  }

  private static class Constant implements Solver<BuiltInConstant>
  {

    private final String doc;

    private final Expression value;

    Constant(double value, String doc)
    {
      this.doc = doc;
      this.value = createDecimal(value);
    }

    @Override
    public Expression solve(BuiltInConstant cst, ValidationMessageAcceptor acceptor)
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
  public static final Map<String, Solver<BuiltInConstant>> constantMappings = ImmutableMap
          .<String, Solver<BuiltInConstant>> builder()
          .put("PI", new Constant(Math.PI,
                  "The double value that is closer than any other to pi, the ratio of the circumference of a circle to its diameter."))
          .put("E", new Constant(Math.E,
                  "The double value that is closer than any other to e, the base of the natural logarithms."))
          .build();

  private static class UnaryFunction implements Solver<BuiltInFunction>
  {

    private final String doc;

    private final DoubleUnaryOperator op;

    UnaryFunction(DoubleUnaryOperator op, String doc)
    {
      this.doc = doc;
      this.op = op;
    }

    @Override
    public Expression solve(BuiltInFunction function, ValidationMessageAcceptor acceptor)
    {
      if (checkParameterCount(function, acceptor, 1))
      {
        final var value = function.getParameter().get(0).getDecimal(acceptor);
        if (value != null)
        {
          try
          {

            final Double val = op.applyAsDouble(value.doubleValue());
            if (!val.isNaN())
            {
              return createDecimal(val);
            }
            acceptor.acceptError("The return value of " + function.getName() + " is NaN.",
                    getTarget(function), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null,
                    (String[]) null);
          }
          catch (final Exception e)
          {

            acceptor.acceptError(e.getLocalizedMessage(), getTarget(function), null,
                    ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null, (String[]) null);
          }
        }
      }
      return null;
    }

    @Override
    public String getDocumentation()
    {
      return doc;
    }

    private static boolean checkParameterCount(BuiltInFunction function,
            ValidationMessageAcceptor acceptor, int count)
    {
      if (function.getParameter().size() != count)
      {
        acceptor.acceptError(
                function.getName() + " takes " + count + " parameter" + (count == 1 ? "" : "s")
                        + ", got " + function.getParameter().size(),
                ExpressionSolver.getTarget(function), null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation",
                (String[]) null);
        return false;
      }
      return true;
    }
  }

  /**
   * the mapping for builtin functions
   */
  public static final Map<String, Solver<BuiltInFunction>> functionMappings = ImmutableMap
          .<String, Solver<BuiltInFunction>> builder()
          .put("cos", new UnaryFunction(Math::cos, "Returns the trigonometric cosine of an angle."))
          .put("sin", new UnaryFunction(Math::sin, "Returns the trigonometric sine of an angle."))
          .put("tan",
                  new UnaryFunction(Math::tan, "Returns the trigonometric tangent of an angle."))
          .put("acos", new UnaryFunction(Math::acos,
                  "Returns the arc cosine of a value; the returned angle is in the range 0.0 through pi."))
          .put("asin", new UnaryFunction(Math::asin,
                  "Returns the arc sine of a value; the returned angle is in the range -pi/2 through pi/2."))
          .put("atan", new UnaryFunction(Math::atan,
                  "Returns the arc tangent of a value; the returned angle is in the range -pi/2 through pi/2."))
          .put("cosh", new UnaryFunction(Math::cosh,
                  "Returns the hyperbolic cosine of a double value. The hyperbolic cosine of x is defined to be (ex + e-x)/2 where e is Euler's number."))
          .put("sinh", new UnaryFunction(Math::sinh,
                  "Returns the hyperbolic sine of a double value. The hyperbolic sine of x is defined to be (ex - e-x)/2 where e is Euler's number. "))
          .put("tanh", new UnaryFunction(Math::tanh,
                  "Returns the hyperbolic tangent of a double value. The hyperbolic tangent of x is defined to be (ex - e-x)/ (ex + e-x), in other words, sinh(x)/cosh(x). Note that the absolute value of the exact tanh is always less than 1. "))
          .put("exp",
                  new UnaryFunction(Math::exp,
                          "Returns Euler's number e raised to the power of a double value."))
          .put("log",
                  new UnaryFunction(Math::log,
                          "Returns the natural logarithm (base e) of a double value."))
          .put("log10",
                  new UnaryFunction(Math::log10,
                          "Returns the base 10 logarithm of a double value."))
          .put("expm1", new UnaryFunction(Math::expm1,
                  "Returns ex -1. Note that for values of x near 0, the exact sum of expm1(x) + 1 is much closer to the true result of ex than exp(x)."))
          .put("log1p", new UnaryFunction(Math::log1p,
                  "Returns the natural logarithm of the sum of the argument and 1. Note that for small values x, the result of log1p(x) is much closer to the true result of ln(1 + x) than the floating-point evaluation of log(1.0+x)."))
          .put("sqrt",
                  new UnaryFunction(Math::sqrt,
                          "Returns the correctly rounded positive square root of a double value."))
          .put("ceil", new UnaryFunction(Math::ceil,
                  "Returns the smallest (closest to negative infinity) double value that is greater than or equal to the argument and is equal to a mathematical integer. "))
          .put("floor", new UnaryFunction(Math::floor,
                  "Returns the largest (closest to positive infinity) double value that is less than or equal to the argument and is equal to a mathematical integer. "))
          .put("abs", new UnaryFunction(Math::abs,
                  "Returns the absolute value of a double value. If the argument is not negative, the argument is returned. If the argument is negative, the negation of the argument is returned."))
          // for float types
          .put("cosf",
                  new UnaryFunction(Math::cos, "Returns the trigonometric cosine of an angle."))
          .put("sinf", new UnaryFunction(Math::sin, "Returns the trigonometric sine of an angle."))
          .put("tanf",
                  new UnaryFunction(Math::tan, "Returns the trigonometric tangent of an angle."))
          .put("acosf", new UnaryFunction(Math::acos,
                  "Returns the arc cosine of a value; the returned angle is in the range 0.0 through pi."))
          .put("asinf", new UnaryFunction(Math::asin,
                  "Returns the arc sine of a value; the returned angle is in the range -pi/2 through pi/2."))
          .put("atanf", new UnaryFunction(Math::atan,
                  "Returns the arc tangent of a value; the returned angle is in the range -pi/2 through pi/2."))
          .put("coshf", new UnaryFunction(Math::cosh,
                  "Returns the hyperbolic cosine of a float value. The hyperbolic cosine of x is defined to be (ex + e-x)/2 where e is Euler's number."))
          .put("sinhf", new UnaryFunction(Math::sinh,
                  "Returns the hyperbolic sine of a float value. The hyperbolic sine of x is defined to be (ex - e-x)/2 where e is Euler's number. "))
          .put("tanhf", new UnaryFunction(Math::tanh,
                  "Returns the hyperbolic tangent of a float value. The hyperbolic tangent of x is defined to be (ex - e-x)/ (ex + e-x), in other words, sinh(x)/cosh(x). Note that the absolute value of the exact tanh is always less than 1. "))

          .put("expf",
                  new UnaryFunction(Math::exp,
                          "Returns Euler's number e raised to the power of a float value."))
          .put("logf",
                  new UnaryFunction(Math::log,
                          "Returns the natural logarithm (base e) of a float value."))
          .put("log10f",
                  new UnaryFunction(Math::log10, "Returns the base 10 logarithm of a float value."))
          .put("expm1f", new UnaryFunction(Math::expm1,
                  "Returns ex -1. Note that for values of x near 0, the exact sum of expm1(x) + 1 is much closer to the true result of ex than exp(x)."))
          .put("log1pf", new UnaryFunction(Math::log1p,
                  "Returns the natural logarithm of the sum of the argument and 1. Note that for small values x, the result of log1p(x) is much closer to the true result of ln(1 + x) than the floating-point evaluation of log(1.0+x)."))
          .put("sqrtf",
                  new UnaryFunction(Math::sqrt,
                          "Returns the correctly rounded positive square root of a float value."))
          .put("ceilf", new UnaryFunction(Math::ceil,
                  "Returns the smallest (closest to negative infinity) float value that is greater than or equal to the argument and is equal to a mathematical integer. "))
          .put("floorf", new UnaryFunction(Math::floor,
                  "Returns the largest (closest to positive infinity) float value that is less than or equal to the argument and is equal to a mathematical integer. "))
          .put("absf", new UnaryFunction(Math::abs,
                  "Returns the absolute value of a float value. If the argument is not negative, the argument is returned. If the argument is negative, the negation of the argument is returned."))

          .build();

  private static Expression createDecimal(Double value)
  {
    final var expr = XcatalogueFactory.eINSTANCE.createFloatingLiteral();

    expr.setText(BigDecimal.valueOf(value).toString());
    return expr;

  }

  /**
   * @author daveluy
   */
  public static class ExpressionRefAdapter extends AdapterImpl
  {

    private final EObject ref;

    /**
     * @param ref
     */
    public ExpressionRefAdapter(EObject ref)
    {
      this.ref = ref;
    }

    /**
     * @return the referenced Expresion
     */
    public EObject getRef()
    {
      return ref;
    }
  }

  /**
   * @param o
   * @return the target Expression
   */
  public static EObject getTarget(EObject o)
  {

    for (final Adapter adapter : o.eAdapters())
    {
      if (adapter instanceof ExpressionRefAdapter)
      {
        return getTarget(((ExpressionRefAdapter) adapter).getRef());
      }
    }

    return o;

  }

  private ExpressionSolver()
  {
    // hidden constructor
  }
}
