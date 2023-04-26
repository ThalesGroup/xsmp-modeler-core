package org.eclipse.xsmp.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.function.DoubleUnaryOperator;

import org.eclipse.xsmp.xcatalogue.BinaryOperation;
import org.eclipse.xsmp.xcatalogue.BooleanLiteral;
import org.eclipse.xsmp.xcatalogue.BuiltInConstant;
import org.eclipse.xsmp.xcatalogue.BuiltInExpression;
import org.eclipse.xsmp.xcatalogue.BuiltInFunction;
import org.eclipse.xsmp.xcatalogue.CharacterLiteral;
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.DesignatedInitializer;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.FloatingLiteral;
import org.eclipse.xsmp.xcatalogue.IntegerLiteral;
import org.eclipse.xsmp.xcatalogue.NamedElementReference;
import org.eclipse.xsmp.xcatalogue.ParenthesizedExpression;
import org.eclipse.xsmp.xcatalogue.SimpleType;
import org.eclipse.xsmp.xcatalogue.StringLiteral;
import org.eclipse.xsmp.xcatalogue.UnaryOperation;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.Tuples;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Solver
{
  public static final BigInteger INT16_MAX = BigInteger.valueOf(Short.MAX_VALUE);

  public static final BigInteger INT16_MIN = BigInteger.valueOf(Short.MIN_VALUE);

  public static final BigInteger INT32_MAX = BigInteger.valueOf(Integer.MAX_VALUE);

  public static final BigInteger INT32_MIN = BigInteger.valueOf(Integer.MIN_VALUE);

  public static final BigInteger INT64_MAX = BigInteger.valueOf(Long.MAX_VALUE);

  public static final BigInteger INT64_MIN = BigInteger.valueOf(Long.MIN_VALUE);

  public static final BigInteger INT8_MAX = BigInteger.valueOf(Byte.MAX_VALUE);

  public static final BigInteger INT8_MIN = BigInteger.valueOf(Byte.MIN_VALUE);

  public static final BigInteger ZERO = BigInteger.ZERO;

  public static final BigInteger UINT8_MAX = BigInteger.valueOf(0xff);

  public static final BigInteger UINT16_MAX = BigInteger.valueOf(0xffff);

  public static final BigInteger UINT32_MAX = BigInteger.valueOf(0xffffffffL);

  public static final BigInteger UINT64_MAX = new BigInteger("ffffffffffffffff", 16);

  public static final BigDecimal FLOAT32_MAX = BigDecimal.valueOf(Float.MAX_VALUE);

  public static final BigDecimal FLOAT32_MIN = FLOAT32_MAX.negate();

  public static final BigDecimal FLOAT64_MAX = BigDecimal.valueOf(Double.MAX_VALUE);

  public static final BigDecimal FLOAT64_MIN = FLOAT64_MAX.negate();

  @Inject
  protected XsmpUtil xsmpUtil;

  @Inject
  protected IResourceScopeCache cache;

  public static class SolverException extends RuntimeException
  {
    private static final long serialVersionUID = 9137810865804865010L;

    private final Expression expression;

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

  protected int compareTo(Object left, Object right)
  {
    if (left instanceof BigInteger && right instanceof BigInteger)
    {
      return ((BigInteger) left).compareTo((BigInteger) right);
    }
    if (left instanceof BigInteger && right instanceof BigDecimal)
    {
      return new BigDecimal((BigInteger) left).compareTo((BigDecimal) right);
    }
    if (left instanceof BigDecimal && right instanceof BigInteger)
    {
      return ((BigDecimal) left).compareTo(new BigDecimal((BigInteger) right));
    }
    if (left instanceof BigDecimal && right instanceof BigDecimal)
    {
      return ((BigDecimal) left).compareTo((BigDecimal) right);
    }
    if (left instanceof Boolean && right instanceof Boolean)
    {
      return ((Boolean) left).compareTo((Boolean) right);
    }
    throw new UnsupportedOperationException();
  }

  private static Class< ? > getType(Object left, Object right)
  {
    if (left.getClass() == Boolean.class)
    {
      if (right.getClass() == Boolean.class)
      {
        return Boolean.class;
      }
      if (right.getClass() == BigInteger.class)
      {
        return BigInteger.class;
      }
      if (right.getClass() == BigDecimal.class)
      {
        return BigDecimal.class;
      }
    }
    else if (left.getClass() == BigInteger.class)
    {
      if (right.getClass() == Boolean.class || right.getClass() == BigInteger.class)
      {
        return BigInteger.class;
      }
      if (right.getClass() == BigDecimal.class)
      {
        return BigDecimal.class;
      }
    }
    else if (left.getClass() == BigDecimal.class && (right.getClass() == Boolean.class
            || right.getClass() == BigInteger.class || right.getClass() == BigDecimal.class))
    {
      return BigDecimal.class;
    }
    throw new UnsupportedOperationException();
  }

  @SuppressWarnings("unchecked")
  private static <T> T convert(Object value, Class<T> clazz)
  {
    if (clazz == Boolean.class)
    {
      return (T) value;
    }
    if (clazz == BigInteger.class)
    {
      if (value instanceof Boolean)
      {
        return (T) (Boolean.TRUE.equals(value) ? BigInteger.ONE : BigInteger.ZERO);
      }
      return (T) value;
    }
    if (clazz == BigDecimal.class)
    {
      if (value instanceof Boolean)
      {
        return (T) (Boolean.TRUE.equals(value) ? BigDecimal.ONE : BigDecimal.ZERO);
      }
      if (value instanceof BigInteger)
      {
        return (T) new BigDecimal((BigInteger) value, MathContext.DECIMAL64);
      }
      return (T) value;
    }
    throw new UnsupportedOperationException();
  }

  Object logicalOr(Object leftOperand, Object rightOperand)
  {
    final var clazz = getType(leftOperand, rightOperand);

    final var left = convert(leftOperand, clazz);
    final var right = convert(rightOperand, clazz);

    if (clazz == Boolean.class)
    {
      return Boolean.TRUE.equals(left) || Boolean.TRUE.equals(right);
    }

    if (clazz == BigInteger.class)
    {
      return ((BigInteger) left).compareTo(BigInteger.ZERO) != 0
              || ((BigInteger) right).compareTo(BigInteger.ZERO) != 0;
    }

    if (clazz == BigDecimal.class)
    {
      return ((BigDecimal) left).compareTo(BigDecimal.ZERO) != 0
              || ((BigDecimal) right).compareTo(BigDecimal.ZERO) != 0;
    }
    throw new UnsupportedOperationException();
  }

  Object logicalAnd(Object leftOperand, Object rightOperand)
  {
    final var clazz = getType(leftOperand, rightOperand);

    final var left = convert(leftOperand, clazz);
    final var right = convert(rightOperand, clazz);

    if (clazz == Boolean.class)
    {
      return Boolean.TRUE.equals(left) && Boolean.TRUE.equals(right);
    }

    if (clazz == BigInteger.class)
    {
      return ((BigInteger) left).compareTo(BigInteger.ZERO) != 0
              && ((BigInteger) right).compareTo(BigInteger.ZERO) != 0;
    }

    if (clazz == BigDecimal.class)
    {
      return ((BigDecimal) left).compareTo(BigDecimal.ZERO) != 0
              && ((BigDecimal) right).compareTo(BigDecimal.ZERO) != 0;
    }
    throw new UnsupportedOperationException();
  }

  Object bitwiseOr(Object leftOperand, Object rightOperand)
  {

    final var clazz = getType(leftOperand, rightOperand);

    final var left = convert(leftOperand, clazz);
    final var right = convert(rightOperand, clazz);

    if (clazz == Boolean.class)
    {
      return Boolean.TRUE.equals(left) || Boolean.TRUE.equals(right);
    }

    if (clazz == BigInteger.class)
    {
      return ((BigInteger) left).or((BigInteger) right);
    }
    throw new UnsupportedOperationException();
  }

  Object bitwiseAnd(Object leftOperand, Object rightOperand)
  {

    final var clazz = getType(leftOperand, rightOperand);

    final var left = convert(leftOperand, clazz);
    final var right = convert(rightOperand, clazz);

    if (clazz == Boolean.class)
    {
      return Boolean.TRUE.equals(left) && Boolean.TRUE.equals(right);
    }

    if (clazz == BigInteger.class)
    {
      return ((BigInteger) left).and((BigInteger) right);
    }
    throw new UnsupportedOperationException();
  }

  Object bitwiseXor(Object leftOperand, Object rightOperand)
  {

    final var clazz = getType(leftOperand, rightOperand);

    final var left = convert(leftOperand, clazz);
    final var right = convert(rightOperand, clazz);

    if (clazz == Boolean.class)
    {
      return !left.equals(right);
    }

    if (clazz == BigInteger.class)
    {
      return ((BigInteger) left).xor((BigInteger) right);
    }
    throw new UnsupportedOperationException();
  }

  Object addition(Object leftOperand, Object rightOperand)
  {
    final var clazz = getType(leftOperand, rightOperand);

    final var left = convert(leftOperand, clazz);
    final var right = convert(rightOperand, clazz);

    if (clazz == Boolean.class)
    {
      return convert(left, BigInteger.class).add(convert(right, BigInteger.class));
    }

    if (clazz == BigInteger.class)
    {
      return ((BigInteger) left).add((BigInteger) right);
    }

    if (clazz == BigDecimal.class)
    {
      return ((BigDecimal) left).add((BigDecimal) right);
    }
    throw new UnsupportedOperationException();
  }

  Object subtraction(Object leftOperand, Object rightOperand)
  {
    final var clazz = getType(leftOperand, rightOperand);

    final var left = convert(leftOperand, clazz);
    final var right = convert(rightOperand, clazz);

    if (clazz == Boolean.class)
    {
      return convert(left, BigInteger.class).subtract(convert(right, BigInteger.class));
    }

    if (clazz == BigInteger.class)
    {
      return ((BigInteger) left).subtract((BigInteger) right);
    }

    if (clazz == BigDecimal.class)
    {
      return ((BigDecimal) left).subtract((BigDecimal) right);
    }
    throw new UnsupportedOperationException();
  }

  Object divide(Object leftOperand, Object rightOperand)
  {
    final var clazz = getType(leftOperand, rightOperand);

    final var left = convert(leftOperand, clazz);
    final var right = convert(rightOperand, clazz);

    if (clazz == Boolean.class)
    {
      return convert(left, BigInteger.class).divide(convert(right, BigInteger.class));
    }
    if (clazz == BigInteger.class)
    {
      return ((BigInteger) left).divide((BigInteger) right);
    }
    if (clazz == BigDecimal.class)
    {
      return ((BigDecimal) left).divide((BigDecimal) right, MathContext.DECIMAL64);
    }
    throw new UnsupportedOperationException();
  }

  Object multiply(Object leftOperand, Object rightOperand)
  {

    final var clazz = getType(leftOperand, rightOperand);

    final var left = convert(leftOperand, clazz);
    final var right = convert(rightOperand, clazz);

    if (clazz == Boolean.class)
    {
      return convert(left, BigInteger.class).multiply(convert(right, BigInteger.class));
    }

    if (clazz == BigInteger.class)
    {
      return ((BigInteger) left).multiply((BigInteger) right);
    }

    if (clazz == BigDecimal.class)
    {
      return ((BigDecimal) left).multiply((BigDecimal) right);
    }
    throw new UnsupportedOperationException();
  }

  Object remainder(Object leftOperand, Object rightOperand)

  {

    final var clazz = getType(leftOperand, rightOperand);

    final var left = convert(leftOperand, clazz);
    final var right = convert(rightOperand, clazz);

    if (clazz == Boolean.class)
    {
      return convert(left, BigInteger.class).remainder(convert(right, BigInteger.class));
    }
    if (clazz == BigInteger.class)
    {
      return ((BigInteger) left).remainder((BigInteger) right);
    }
    if (clazz == BigDecimal.class)
    {
      return ((BigDecimal) left).remainder((BigDecimal) right);
    }
    throw new UnsupportedOperationException();
  }

  Object shiftLeft(Object leftOperand, Object rightOperand)
  {

    final var clazz = getType(leftOperand, rightOperand);

    final var left = convert(leftOperand, clazz);
    final var right = convert(rightOperand, clazz);

    if (clazz == Boolean.class)
    {
      return convert(left, BigInteger.class).shiftLeft(convert(right, BigInteger.class).intValue());
    }

    if (clazz == BigInteger.class)
    {
      return ((BigInteger) left).shiftLeft(((BigInteger) right).intValue());
    }

    throw new UnsupportedOperationException();
  }

  Object shiftRight(Object leftOperand, Object rightOperand)
  {
    final var clazz = getType(leftOperand, rightOperand);

    final var left = convert(leftOperand, clazz);
    final var right = convert(rightOperand, clazz);

    if (clazz == Boolean.class)
    {
      return convert(left, BigInteger.class)
              .shiftRight(convert(right, BigInteger.class).intValue());
    }

    if (clazz == BigInteger.class)
    {
      return ((BigInteger) left).shiftRight(((BigInteger) right).intValue());
    }
    throw new UnsupportedOperationException();
  }

  protected Object getValue(BinaryOperation e)
  {

    final var leftOperand = doGetValue(e.getLeftOperand());
    if (leftOperand == null)
    {
      return null;
    }

    final var rightOperand = doGetValue(e.getRightOperand());
    if (rightOperand == null)
    {
      return null;
    }

    try
    {
      switch (e.getFeature())
      {
        case "||":
          return logicalOr(leftOperand, rightOperand);
        case "&&":
          return logicalAnd(leftOperand, rightOperand);
        case "|":
          return bitwiseOr(leftOperand, rightOperand);
        case "&":
          return bitwiseAnd(leftOperand, rightOperand);
        case "^":
          return bitwiseXor(leftOperand, rightOperand);
        case "==":
          return compareTo(leftOperand, rightOperand) == 0;
        case "!=":
          return compareTo(leftOperand, rightOperand) != 0;
        case "<=":
          return compareTo(leftOperand, rightOperand) <= 0;
        case ">=":
          return compareTo(leftOperand, rightOperand) >= 0;
        case "<":
          return compareTo(leftOperand, rightOperand) < 0;
        case ">":
          return compareTo(leftOperand, rightOperand) > 0;
        case "+":
          return addition(leftOperand, rightOperand);
        case "-":
          return subtraction(leftOperand, rightOperand);
        case "/":
          return divide(leftOperand, rightOperand);
        case "*":
          return multiply(leftOperand, rightOperand);
        case "%":
          return remainder(leftOperand, rightOperand);
        case "<<":
          return shiftLeft(leftOperand, rightOperand);
        case ">>":
          return shiftRight(leftOperand, rightOperand);
        default:
          break;
      }
    }
    catch (final UnsupportedOperationException ex)
    {
      throw new UnsupportedOperationException("no match for « operator" + e.getFeature()
              + " » (operand types are « " + leftOperand.getClass().getSimpleName() + " » and «"
              + rightOperand.getClass().getSimpleName() + " »).");
    }
    catch (final Exception ex)
    {
      throw new SolverException(e, ex.getMessage());
    }

    throw new SolverException(e,
            "unknown « operator" + e.getFeature() + " » (operand types are « "
                    + leftOperand.getClass().getSimpleName() + " » and «"
                    + rightOperand.getClass().getSimpleName() + " »).");
  }

  protected Object getValue(BuiltInConstant e)
  {
    final var solver = constantMappings.get(e.getName());
    if (solver == null)
    {
      throw new SolverException(e, "unknown constant: " + e.getName());
    }
    return solver.solve(e);

  }

  protected Object getValue(BuiltInFunction e)
  {
    final var solver = functionMappings.get(e.getName());
    if (solver == null)
    {
      throw new SolverException(e, "unknown function: " + e.getName());
    }

    return solver.solve(e);
  }

  protected Object getValue(UnaryOperation e)
  {
    final var value = doGetValue(e.getOperand());
    if (value == null)
    {
      return null;
    }

    switch (e.getFeature())
    {
      case "!":
        if (value instanceof Boolean)
        {
          return !Boolean.TRUE.equals(value);
        }
        if (value instanceof BigDecimal)
        {
          return ((BigDecimal) value).doubleValue() == 0.0;
        }
        if (value instanceof BigInteger)
        {
          return BigInteger.ZERO.equals(value);
        }
        break;
      case "~":
        if (value instanceof BigInteger)
        {
          return ((BigInteger) value).not();
        }
        break;
      case "-":
        if (value instanceof Boolean)
        {
          return Boolean.TRUE.equals(value) ? BigInteger.ONE.negate() : BigInteger.ZERO;
        }
        if (value instanceof BigDecimal)
        {
          return ((BigDecimal) value).negate();
        }
        if (value instanceof BigInteger)
        {
          return ((BigInteger) value).negate();
        }
        break;
      case "+":
        if (value instanceof Boolean)
        {
          return Boolean.TRUE.equals(value) ? BigInteger.ONE : BigInteger.ZERO;
        }
        if (value instanceof BigDecimal || value instanceof BigInteger)
        {
          return value;
        }
        break;
      default:
        break;
    }
    throw new SolverException(e, "no match for « operator" + e.getFeature()
            + " » (operand type is « " + value.getClass().getSimpleName() + " »).");
  }

  protected Object getValue(NamedElementReference e)
  {
    final var value = e.getValue();
    if (value == null || value.eIsProxy())
    {
      return null;
    }
    if (value instanceof Constant)
    {
      return getValue(((Constant) value).getValue());
    }
    if (value instanceof EnumerationLiteral)
    {
      if (EcoreUtil2.getContainerOfType(e, Enumeration.class) != null)
      {
        return getValue(((EnumerationLiteral) value).getValue());
      }
      return value;
    }
    throw new SolverException(e, "invalid reference type « " + value.getClass().getSimpleName()
            + " »), only Contant and EnumerationLiteral are supported.");
  }

  protected Object getValue(CollectionLiteral e)
  {
    final var elements = e.getElements();

    if (elements.size() == 1 && xsmpUtil.getType(e) instanceof SimpleType)
    {
      return getValue(elements.get(0));
    }
    throw new SolverException(e, "SimpleType requires only one element.");
  }

  protected Object getValue(CharacterLiteral e)
  {
    try
    {
      return XsmpUtil.getString(e);
    }
    catch (final Exception ex)
    {
      throw new SolverException(e, ex.getMessage());
    }
  }

  protected Object getValue(StringLiteral e)
  {
    try
    {
      return XsmpUtil.getUnescapedString(e);
    }
    catch (final Exception ex)
    {
      throw new SolverException(e, ex.getMessage());
    }
  }

  private Object doGetValue(Expression e)
  {
    if (e == null)
    {
      return null;
    }

    try
    {
      switch (e.eClass().getClassifierID())
      {
        case XcataloguePackage.BINARY_OPERATION:
          return getValue((BinaryOperation) e);
        case XcataloguePackage.BOOLEAN_LITERAL:
          return ((BooleanLiteral) e).isIsTrue();
        case XcataloguePackage.BUILT_IN_CONSTANT:
          return getValue((BuiltInConstant) e);
        case XcataloguePackage.BUILT_IN_FUNCTION:
          return getValue((BuiltInFunction) e);
        case XcataloguePackage.CHARACTER_LITERAL:
          return getValue((CharacterLiteral) e);
        case XcataloguePackage.COLLECTION_LITERAL:
          return getValue((CollectionLiteral) e);
        case XcataloguePackage.NAMED_ELEMENT_REFERENCE:
          return getValue((NamedElementReference) e);
        case XcataloguePackage.FLOATING_LITERAL:
          return ((FloatingLiteral) e).getValue();
        case XcataloguePackage.INTEGER_LITERAL:
          return ((IntegerLiteral) e).getValue();
        case XcataloguePackage.PARENTHESIZED_EXPRESSION:
          return doGetValue(((ParenthesizedExpression) e).getExpr());
        case XcataloguePackage.DESIGNATED_INITIALIZER:
          return doGetValue(((DesignatedInitializer) e).getExpr());
        case XcataloguePackage.STRING_LITERAL:
          return getValue((StringLiteral) e);
        case XcataloguePackage.UNARY_OPERATION:
          return getValue((UnaryOperation) e);
        case XcataloguePackage.KEYWORD_EXPRESSION:
          return e;
        case XcataloguePackage.EMPTY_EXPRESSION:
          return null;
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

  public Object getValue(Expression e)
  {
    if (e == null)
    {
      return null;
    }

    return cache.get(Tuples.pair(e, "Value"), e.eResource(), () -> doGetValue(e));
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
    Object solve(T builtIn);

    /**
     * @return the builtin documentation
     */
    String getDocumentation();
  }

  static class BuiltInConstantSolver implements BuiltInSolver<BuiltInConstant>
  {

    private final String doc;

    private final Object value;

    BuiltInConstantSolver(Object value, String doc)
    {
      this.doc = doc;
      this.value = value;
    }

    @Override
    public Object solve(BuiltInConstant cst)
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
          .put("PI", new BuiltInConstantSolver(BigDecimal.valueOf(Math.PI),
                  "The double value that is closer than any other to pi, the ratio of the circumference of a circle to its diameter."))
          .put("E", new BuiltInConstantSolver(BigDecimal.valueOf(Math.E),
                  "The double value that is closer than any other to e, the base of the natural logarithms."))
          .build();

  private class UnaryFunction implements BuiltInSolver<BuiltInFunction>
  {

    private final String doc;

    private final DoubleUnaryOperator op;

    UnaryFunction(DoubleUnaryOperator op, String doc)
    {
      this.doc = doc;
      this.op = op;
    }

    @Override
    public Object solve(BuiltInFunction function)
    {
      if (function.getParameter().size() == 1)
      {

        final var value = xsmpUtil.getDecimal(function.getParameter().get(0));
        if (value != null)
        {
          try
          {
            final Double val = op.applyAsDouble(value.doubleValue());

            if (val.isNaN())
            {
              throw new SolverException(function, "result is not a number.");

            }
            if (val.isInfinite())
            {
              throw new SolverException(function, "result is infinite.");

            }
            return BigDecimal.valueOf(val);
          }
          catch (final Exception e)
          {
          }
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

  public BigInteger getInteger(Expression e)
  {
    final var value = getValue(e);
    if (value != null)
    {
      if (value instanceof Boolean)
      {
        return Boolean.TRUE.equals(value) ? BigInteger.ONE : BigInteger.ZERO;
      }
      if (value instanceof BigInteger)
      {
        return (BigInteger) value;
      }
      if (value instanceof BigDecimal)
      {
        return ((BigDecimal) value).toBigInteger();
      }

      throw new SolverException(e,
              "Could not convert " + value.getClass().getSimpleName() + " to Integer.");
    }

    return null;
  }

  public BigDecimal getDecimal(Expression e)
  {
    final var value = getValue(e);
    if (value != null)
    {
      if (value instanceof BigDecimal)
      {
        return (BigDecimal) value;
      }
      if (value instanceof BigInteger)
      {
        return new BigDecimal((BigInteger) value);
      }
      if (value instanceof Boolean)
      {
        return Boolean.TRUE.equals(value) ? BigDecimal.ONE : BigDecimal.ZERO;
      }

      throw new SolverException(e,
              "Could not convert " + value.getClass().getSimpleName() + " to Decimal.");
    }
    return null;
  }

  public EnumerationLiteral getEnumerationLiteral(Expression e, Enumeration expectedType)
  {
    final var value = getValue(e);
    if (value instanceof EnumerationLiteral)
    {
      return (EnumerationLiteral) value;
    }

    final var integer = getInteger(e);

    if (integer != null)
    {
      final var literal = expectedType.getLiteral().stream()
              .filter(l -> integer.equals(getInteger(l.getValue()))).findFirst();

      if (literal.isPresent())
      {

        return literal.get();
      }
      throw new SolverException(e,
              "Could not convert " + integer + " to Enumeration " + expectedType.getName() + ".");
    }

    return null;
  }

  public BigInteger getEnumValue(Expression e)
  {
    final var value = getValue(e);
    if (value instanceof EnumerationLiteral)
    {
      final var literal = (EnumerationLiteral) value;

      return getInteger(literal.getValue());
    }

    return getInteger(e);
  }

  public Optional<Boolean> getBoolean(Expression e)
  {
    final var value = getValue(e);
    if (value != null)
    {
      if (value instanceof Boolean)
      {
        return Optional.of((Boolean) value);
      }
      if (value instanceof BigDecimal)
      {
        return Optional.of(((BigDecimal) value).doubleValue() != 0.);
      }
      if (value instanceof BigInteger)
      {
        return Optional.of(((BigInteger) value).compareTo(BigInteger.ZERO) != 0);
      }
    }
    return Optional.empty();
  }

  public String getString(Expression e)
  {
    final var value = getValue(e);
    if (value instanceof String)
    {
      return (String) value;
    }

    return null;
  }

  public String getChar(Expression e)
  {
    final var value = getValue(e);
    if (value != null)
    {
      if (value instanceof String)
      {
        final var chr = (String) value;
        if (XsmpUtil.translateEscapes(chr).length() != 1)
        {
          throw new SolverException(e, "Invalid Character length: " + chr);
        }

        return chr;
      }

      if (value instanceof BigInteger)
      {
        return Character.toString(((BigInteger) value).byteValue());
      }
      throw new SolverException(e,
              "Could not convert " + value.getClass().getSimpleName() + " to Char.");

    }
    return null;
  }

  public BigInteger getDuration(Expression e)
  {
    if (e instanceof StringLiteral)
    {
      try
      {
        return BigInteger.valueOf(Duration.parse(XsmpUtil.getString((StringLiteral) e)).getNano());
      }
      catch (final Exception ex)
      {
        throw new SolverException(e, ex.getMessage());
      }

    }
    return getInteger(e);

  }

  public BigInteger getDateTime(Expression e)
  {
    if (!(e instanceof StringLiteral))
    {
      // check Date Time in ns
      return getInteger(e);
    }
    try
    {
      final var i = Instant.parse(XsmpUtil.getString((StringLiteral) e));
      return BigInteger.valueOf(i.getEpochSecond()).multiply(BigInteger.valueOf(1_000_000_000))
              .add(BigInteger.valueOf(i.getNano()));
    }
    catch (final Exception ex)
    {
      throw new SolverException(e, ex.getMessage());
    }
  }
}
