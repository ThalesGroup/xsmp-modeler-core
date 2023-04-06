package org.eclipse.xsmp.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Map;
import java.util.Optional;
import java.util.function.DoubleUnaryOperator;

import org.eclipse.xsmp.validation.XsmpcatIssueCodesProvider;
import org.eclipse.xsmp.xcatalogue.BinaryOperation;
import org.eclipse.xsmp.xcatalogue.BooleanLiteral;
import org.eclipse.xsmp.xcatalogue.BuiltInConstant;
import org.eclipse.xsmp.xcatalogue.BuiltInExpression;
import org.eclipse.xsmp.xcatalogue.BuiltInFunction;
import org.eclipse.xsmp.xcatalogue.CharacterLiteral;
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteralReference;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.FloatingLiteral;
import org.eclipse.xsmp.xcatalogue.IntegerLiteral;
import org.eclipse.xsmp.xcatalogue.ParenthesizedExpression;
import org.eclipse.xsmp.xcatalogue.StringLiteral;
import org.eclipse.xsmp.xcatalogue.UnaryOperation;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.AbstractValidationMessageAcceptor;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.common.collect.ImmutableMap;

public class Solver
{
  public static final Solver INSTANCE = new Solver();

  private final ValidationMessageAcceptor acceptor;

  public Solver()
  {
    acceptor = new AbstractValidationMessageAcceptor() {

    };
  }

  public Solver(ValidationMessageAcceptor acceptor)
  {
    this.acceptor = acceptor;
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

    return null;
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
    if (clazz == null)
    {
      throw new UnsupportedOperationException();
    }

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
    if (clazz == null)
    {
      throw new UnsupportedOperationException();
    }

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
    if (clazz == null)
    {
      throw new UnsupportedOperationException();
    }

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
    if (clazz == null)
    {
      throw new UnsupportedOperationException();
    }

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
    if (clazz == null)
    {
      throw new UnsupportedOperationException();
    }

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
    if (clazz == null)
    {
      throw new UnsupportedOperationException();
    }

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
    if (clazz == null)
    {
      throw new UnsupportedOperationException();
    }

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

  Object divide(Object leftOperand, Object rightOperand) throws ArithmeticException
  {
    final var clazz = getType(leftOperand, rightOperand);
    if (clazz == null)
    {
      throw new UnsupportedOperationException();
    }

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
    if (clazz == null)
    {
      throw new UnsupportedOperationException();
    }

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

  Object remainder(Object leftOperand, Object rightOperand) throws ArithmeticException
  {
    final var clazz = getType(leftOperand, rightOperand);
    if (clazz == null)
    {
      throw new UnsupportedOperationException();
    }

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
    if (clazz == null)
    {
      throw new UnsupportedOperationException();
    }

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
    if (clazz == null)
    {
      throw new UnsupportedOperationException();
    }

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

    final var leftOperand = getValue(e.getLeftOperand());
    if (leftOperand == null)
    {
      return null;
    }

    final var rightOperand = getValue(e.getRightOperand());
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
    catch (final ArithmeticException ex)
    {
      acceptor.acceptError(ex.getLocalizedMessage(), e, null,
              ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation");
      return null;
    }
    catch (final UnsupportedOperationException ex)
    {
      // ignore
    }
    acceptor.acceptError(
            "Operator " + e.getFeature() + " does not supports "
                    + leftOperand.getClass().getSimpleName() + " and "
                    + rightOperand.getClass().getSimpleName() + " operands.",
            e, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation");
    return null;
  }

  protected Object getValue(BuiltInConstant e)
  {
    final var solver = constantMappings.get(e.getName());
    if (solver == null)
    {
      acceptor.acceptError("Unknown builtin constant '" + e.getName() + "'", e, null,
              ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation");
      return null;
    }
    return solver.solve(e, this);

  }

  protected Object getValue(BuiltInFunction e)
  {
    final var solver = functionMappings.get(e.getName());
    if (solver == null)
    {
      acceptor.acceptError("Unknown builtin function '" + e.getName() + "'", e, null,
              ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation",
              (String[]) null);

      return null;
    }

    return solver.solve(e, this);
  }

  protected Object getValue(UnaryOperation e)
  {
    final var value = getValue(e.getOperand());
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

    acceptor.acceptError(
            "Unary Operator " + e.getFeature() + " does not supports " + value.getClass().getName()
                    + " operand.",
            e, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation");
    return null;
  }

  protected Object getValue(EnumerationLiteralReference e)
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
    throw new UnsupportedOperationException();
  }

  protected Object getValue(CollectionLiteral e)
  {
    final var elements = e.getElements();
    if (elements.isEmpty())
    {
      return null;
    }
    if (elements.size() == 1)
    {
      return getValue(elements.get(0));
    }
    acceptor.acceptError("Expecting only one element, got " + elements.size(), e, null,
            ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation");
    return null;
  }

  protected Object getValue(CharacterLiteral e)
  {
    try
    {
      return XsmpUtil.getUnescapedChar(e);
    }
    catch (final Exception ex)
    {
      acceptor.acceptError("Invalid escape sequence: " + ex.getLocalizedMessage(), e, null,
              ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation");
    }
    return null;
  }

  protected Object getValue(StringLiteral e)
  {
    try
    {
      return XsmpUtil.getUnescapedString(e);
    }
    catch (final Exception ex)
    {
      acceptor.acceptError("Invalid escape sequence: " + ex.getLocalizedMessage(), e, null,
              ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation");
    }
    return null;
  }

  private static class Nullptr
  {
    public static Nullptr INSTANCE = new Nullptr();
  }

  public Object getValue(Expression e)
  {
    if (e == null)
    {
      return null;
    }
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
      case XcataloguePackage.ENUMERATION_LITERAL_REFERENCE:
        return getValue((EnumerationLiteralReference) e);
      case XcataloguePackage.FLOATING_LITERAL:
        return ((FloatingLiteral) e).getValue();
      case XcataloguePackage.INTEGER_LITERAL:
        return ((IntegerLiteral) e).getValue();
      case XcataloguePackage.PARENTHESIZED_EXPRESSION:
        return getValue(((ParenthesizedExpression) e).getExpr());
      case XcataloguePackage.STRING_LITERAL:
        return getValue((StringLiteral) e);
      case XcataloguePackage.UNARY_OPERATION:
        return getValue((UnaryOperation) e);
      case XcataloguePackage.NULLPTR_EXPRESSION:
        return Nullptr.INSTANCE;
      default:
        throw new UnsupportedOperationException();
    }
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
    Object solve(T builtIn, Solver acceptor);

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
    public Object solve(BuiltInConstant cst, Solver solver)
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
  public static final Map<String, BuiltInSolver<BuiltInConstant>> constantMappings = ImmutableMap
          .<String, BuiltInSolver<BuiltInConstant>> builder()
          .put("PI", new BuiltInConstantSolver(BigDecimal.valueOf(Math.PI),
                  "The double value that is closer than any other to pi, the ratio of the circumference of a circle to its diameter."))
          .put("E", new BuiltInConstantSolver(BigDecimal.valueOf(Math.E),
                  "The double value that is closer than any other to e, the base of the natural logarithms."))
          .build();

  private static class UnaryFunction implements BuiltInSolver<BuiltInFunction>
  {

    private final String doc;

    private final DoubleUnaryOperator op;

    UnaryFunction(DoubleUnaryOperator op, String doc)
    {
      this.doc = doc;
      this.op = op;
    }

    @Override
    public Object solve(BuiltInFunction function, Solver solver)
    {
      if (checkParameterCount(function, solver, 1))
      {

        final var value = solver.getDecimal(function.getParameter().get(0));
        if (value != null)
        {
          try
          {
            final Double val = op.applyAsDouble(value.doubleValue());

            if (val.isNaN())
            {
              solver.acceptor.acceptError("The return value of " + function.getName() + " is NaN.",
                      function, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
              return null;
            }
            if (val.isInfinite())
            {
              solver.acceptor.acceptError(
                      "The return value of " + function.getName() + " is infinite.", function, null,
                      ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
              return null;
            }
            return BigDecimal.valueOf(val);
          }
          catch (final Exception e)
          {

            solver.acceptor.acceptError(e.getLocalizedMessage(), function, null,
                    ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
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

    private static boolean checkParameterCount(BuiltInFunction function, Solver acceptor, int count)
    {
      if (function.getParameter().size() != count)
      {
        acceptor.acceptor.acceptError(
                function.getName() + " takes " + count + " parameter" + (count == 1 ? "" : "s")
                        + ", got " + function.getParameter().size(),
                function, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
                "unsupported_operation");
        return false;
      }
      return true;
    }
  }

  /**
   * the mapping for builtin functions
   */
  public static final Map<String, BuiltInSolver<BuiltInFunction>> functionMappings = ImmutableMap
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
      acceptor.acceptWarning("Narrowing convertion from Decimal to Integral Number.", e, null,
              ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              XsmpcatIssueCodesProvider.NARROWING_CONVERSION);
      return ((BigDecimal) value).toBigInteger();
    }
    if (value != null)
    {
      acceptor.acceptError("Could not convert " + value.getClass().getSimpleName() + " to Integer.",
              e, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              XsmpcatIssueCodesProvider.INVALID_VALUE_CONVERSION);
    }

    return null;
  }

  public BigInteger getInteger(Expression e, BigInteger min, BigInteger max)
  {
    final var value = getInteger(e);

    if (value != null && min != null && max != null
            && (value.compareTo(min) < 0 || value.compareTo(max) > 0))
    {
      acceptor.acceptError(
              "Integral value " + value + " is not in range " + min + " ... " + max + ".", e, null,
              ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              XsmpcatIssueCodesProvider.INVALID_VALUE_RANGE);
    }
    return value;
  }

  public BigDecimal getDecimal(Expression e)
  {
    final var value = getValue(e);
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
    if (value != null)
    {
      acceptor.acceptError("Could not convert " + value.getClass().getSimpleName() + " to Decimal.",
              e, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              XsmpcatIssueCodesProvider.INVALID_VALUE_CONVERSION);
    }
    return null;
  }

  public BigDecimal getDecimal(Expression e, BigDecimal min, BigDecimal max, boolean minInclusive,
          boolean maxInclusive)
  {
    final var value = getDecimal(e);

    if (value != null && min != null && max != null
            && ((minInclusive ? value.compareTo(min) < 0 : value.compareTo(min) <= 0)
                    || (maxInclusive ? value.compareTo(max) > 0 : value.compareTo(max) >= 0)))
    {
      acceptor.acceptError(
              "Decimal value " + value + " is not in range " + min + (minInclusive ? "." : "<")
                      + "." + (maxInclusive ? "." : "<") + max + ".",
              e, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              XsmpcatIssueCodesProvider.INVALID_VALUE_RANGE);
    }
    return value;
  }

  public EnumerationLiteral getEnum(Expression e, Enumeration type)
  {
    final var value = getValue(e);
    if (value instanceof EnumerationLiteral)
    {
      final var literal = (EnumerationLiteral) value;
      if (literal.eContainer() != type)
      {
        acceptor.acceptError("Incompatible enumeration Type: expecting " + type.getName(), e, null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
      }
      return literal;
    }

    final var integer = getInteger(e);

    if (integer != null)
    {
      final var literal = type.getLiteral().stream()
              .filter(l -> integer.equals(Solver.INSTANCE.getInteger(l.getValue()))).findFirst();

      if (literal.isPresent())
      {
        return literal.get();
      }

      acceptor.acceptError(
              "Could not convert " + integer + " to Enumeration " + type.getName() + ".", e, null,
              ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              XsmpcatIssueCodesProvider.INVALID_VALUE_CONVERSION);
    }

    return null;
  }

  public Optional<Boolean> getBoolean(Expression e)
  {
    final var value = getValue(e);
    if (value instanceof Boolean)
    {
      return Optional.of((Boolean) value);
    }
    if (value instanceof BigDecimal)
    {
      acceptor.acceptWarning(
              "Narrowing convertion of \"" + value + "\" from \"double\" to \"bool\".", e, null,
              ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              XsmpcatIssueCodesProvider.NARROWING_CONVERSION);
      return Optional.of(((BigDecimal) value).doubleValue() != 0.);
    }
    if (value instanceof BigInteger)
    {
      if (!BigInteger.ZERO.equals(value) && !BigInteger.ONE.equals(value))
      {
        acceptor.acceptWarning(
                "Narrowing convertion of \"" + value + "\" from \"int\" to \"bool\".", e, null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
                XsmpcatIssueCodesProvider.NARROWING_CONVERSION);
      }

      return Optional.of(((BigInteger) value).compareTo(BigInteger.ZERO) != 0);
    }
    if (value != null)
    {
      acceptor.acceptError("Could not convert " + value.getClass().getSimpleName() + " to Boolean.",
              e, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              XsmpcatIssueCodesProvider.INVALID_VALUE_CONVERSION);
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
    if (value != null)
    {
      acceptor.acceptError("Could not convert " + value.getClass().getSimpleName() + " to String.",
              e, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              XsmpcatIssueCodesProvider.INVALID_VALUE_CONVERSION);
    }
    return null;
  }
}
