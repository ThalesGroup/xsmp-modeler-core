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

import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.xsmp.util.Solver.FLOAT32_MAX;
import static org.eclipse.xsmp.util.Solver.FLOAT32_MIN;
import static org.eclipse.xsmp.util.Solver.FLOAT64_MAX;
import static org.eclipse.xsmp.util.Solver.FLOAT64_MIN;
import static org.eclipse.xsmp.util.Solver.INT16_MAX;
import static org.eclipse.xsmp.util.Solver.INT16_MIN;
import static org.eclipse.xsmp.util.Solver.INT32_MAX;
import static org.eclipse.xsmp.util.Solver.INT32_MIN;
import static org.eclipse.xsmp.util.Solver.INT64_MAX;
import static org.eclipse.xsmp.util.Solver.INT64_MIN;
import static org.eclipse.xsmp.util.Solver.INT8_MAX;
import static org.eclipse.xsmp.util.Solver.INT8_MIN;
import static org.eclipse.xsmp.util.Solver.UINT16_MAX;
import static org.eclipse.xsmp.util.Solver.UINT32_MAX;
import static org.eclipse.xsmp.util.Solver.UINT64_MAX;
import static org.eclipse.xsmp.util.Solver.UINT8_MAX;
import static org.eclipse.xsmp.util.Solver.ZERO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xsmp.services.XsmpcatGrammarAccess;
import org.eclipse.xsmp.util.QualifiedNames;
import org.eclipse.xsmp.util.Solver.SolverException;
import org.eclipse.xsmp.util.TypeReferenceConverter;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.util.XsmpUtil.OperatorKind;
import org.eclipse.xsmp.xcatalogue.Array;
import org.eclipse.xsmp.xcatalogue.Association;
import org.eclipse.xsmp.xcatalogue.Attribute;
import org.eclipse.xsmp.xcatalogue.AttributeType;
import org.eclipse.xsmp.xcatalogue.Catalogue;
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.Component;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Container;
import org.eclipse.xsmp.xcatalogue.DesignatedInitializer;
import org.eclipse.xsmp.xcatalogue.EmptyExpression;
import org.eclipse.xsmp.xcatalogue.EntryPoint;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.EventSink;
import org.eclipse.xsmp.xcatalogue.EventSource;
import org.eclipse.xsmp.xcatalogue.EventType;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Interface;
import org.eclipse.xsmp.xcatalogue.KeywordExpression;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.NamedElementReference;
import org.eclipse.xsmp.xcatalogue.NamedElementWithMultiplicity;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.PrimitiveType;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.Reference;
import org.eclipse.xsmp.xcatalogue.SimpleType;
import org.eclipse.xsmp.xcatalogue.Structure;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.ValueReference;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.VisibilityKind;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xsmp.xcatalogue.impl.NamedElementImplCustom;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.ComposedChecks;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This class contains custom validation rules. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@Singleton
@ComposedChecks(validators = {UniqueElementValidator.class })
public class XsmpcatValidator extends AbstractXsmpcatValidator
{

  // regex for named element name
  public static final Pattern VALID_ID_PATTERN = Pattern.compile("[a-zA-Z]\\w*");

  private static final Set<String> reservedKeywordsSet = ImmutableSet.<String> builder().add(
          "alignas", "alignof", "and", "and_eq", "asm", "auto", "bitand", "bitor", "bool", "break",
          "case", "catch", "char", "char8_t", "char16_t", "char32_t", "class", "compl", "concept",
          "const", "consteval", "constexpr", "constinit", "const_cast", "continue", "co_await",
          "co_return", "co_yield", "decltype", "default", "delete", "do", "double", "dynamic_cast",
          "else", "enum", "explicit", "export", "extern", "false", "float", "for", "friend", "goto",
          "if", "inline", "int", "long", "mutable", "namespace", "new", "noexcept", "not", "not_eq",
          "nullptr", "operator", "or", "or_eq", ModifierValidator.PRIVATE_MODIFIER,
          ModifierValidator.PROTECTED_MODIFIER, ModifierValidator.PUBLIC_MODIFIER, "register",
          "reinterpret_cast", "requires", "return", "short", "signed", "sizeof", "static",
          "static_assert", "static_cast", "struct", "switch", "template", "this", "thread_local",
          "throw", "true", "try", "typedef", "typeid", "typename", "union", "unsigned", "using",
          "virtual", "void", "volatile", "wchar_t", "while", "xor", "xor_eq").build();

  private final ModifierValidator associationElementModifierValidator = new ModifierValidator(
          newArrayList(ModifierValidator.PUBLIC_MODIFIER, ModifierValidator.PROTECTED_MODIFIER,
                  ModifierValidator.PRIVATE_MODIFIER));

  private final ModifierValidator classModifierValidator = new ModifierValidator(
          newArrayList(ModifierValidator.PUBLIC_MODIFIER, ModifierValidator.PROTECTED_MODIFIER,
                  ModifierValidator.PRIVATE_MODIFIER, ModifierValidator.ABSTRACT_MODIFIER));

  private final ModifierValidator constantElementModifierValidator = new ModifierValidator(
          newArrayList(ModifierValidator.PUBLIC_MODIFIER, ModifierValidator.PROTECTED_MODIFIER,
                  ModifierValidator.PRIVATE_MODIFIER));

  private final ModifierValidator constantInInterfaceOrStructureElementModifierValidator = new ModifierValidator(
          newArrayList());

  @Inject
  private IResourceScopeCache cache;

  @Inject
  private TypeReferenceConverter typeReferenceConverter;

  private final ModifierValidator fieldElementModifierValidator = new ModifierValidator(
          newArrayList(ModifierValidator.PUBLIC_MODIFIER, ModifierValidator.PROTECTED_MODIFIER,
                  ModifierValidator.PRIVATE_MODIFIER, "input", "output", "transient"));

  private final ModifierValidator fieldInStructModifierValidator = new ModifierValidator(
          newArrayList("input", "output", "transient"));

  private final ModifierValidator operationElementModifierValidator = new ModifierValidator(
          newArrayList(ModifierValidator.PUBLIC_MODIFIER, ModifierValidator.PROTECTED_MODIFIER,
                  ModifierValidator.PRIVATE_MODIFIER));

  private final ModifierValidator operationInInterfaceElementModifierValidator = new ModifierValidator(
          newArrayList());

  private final ModifierValidator propertyElementModifierValidator = new ModifierValidator(
          newArrayList(ModifierValidator.PUBLIC_MODIFIER, ModifierValidator.PROTECTED_MODIFIER,
                  ModifierValidator.PRIVATE_MODIFIER, "readWrite", "readOnly", "writeOnly"));

  private final ModifierValidator propertyInInterfaceElementModifierValidator = new ModifierValidator(
          newArrayList("readWrite", "readOnly", "writeOnly"));

  @Inject
  protected IResourceServiceProvider resourceServiceprovider;

  private final ModifierValidator typeModifierValidator = new ModifierValidator(
          newArrayList(ModifierValidator.PUBLIC_MODIFIER, ModifierValidator.PROTECTED_MODIFIER,
                  ModifierValidator.PRIVATE_MODIFIER));

  @Inject
  protected XsmpUtil xsmpUtil;

  @Inject
  protected IQualifiedNameProvider qualifiedNameProvider;

  private void checkEnumeration(Enumeration type, Expression e)
  {
    try
    {
      final var l = xsmpUtil.getSolver().getEnumerationLiteral(e, type);

      final var v = xsmpUtil.getSolver().getValue(e);
      if (l != null && l != v)
      {
        warning("Should use the EnumerationLiteral " + xsmpUtil.fqn(l).toString(), e, null);
      }
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
  }

  BigDecimal getDecimal(Expression e)
  {
    try
    {
      return xsmpUtil.getSolver().getDecimal(e);
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
    return null;
  }

  BigInteger getDuration(Expression e)
  {
    try
    {
      return xsmpUtil.getSolver().getDuration(e);
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
    return null;
  }

  BigInteger getDateTime(Expression e)
  {
    try
    {
      return xsmpUtil.getSolver().getDateTime(e);
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
    return null;
  }

  String getChar(Expression e)
  {
    try
    {
      return xsmpUtil.getSolver().getChar(e);
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
    return null;
  }

  private BigDecimal getDecimal(Expression e, BigDecimal min, BigDecimal max, boolean minInclusive,
          boolean maxInclusive)
  {
    final var value = getDecimal(e);

    if (value != null && min != null && max != null
            && ((minInclusive ? value.compareTo(min) < 0 : value.compareTo(min) <= 0)
                    || (maxInclusive ? value.compareTo(max) > 0 : value.compareTo(max) >= 0)))
    {
      acceptError(
              "Decimal value " + value + " is not in range " + min + (minInclusive ? "." : "<")
                      + "." + (maxInclusive ? "." : "<") + max + ".",
              e, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              XsmpcatIssueCodesProvider.INVALID_VALUE_RANGE);
    }
    return value;
  }

  public void checkBoolean(Expression e)
  {
    try
    {
      final var value = xsmpUtil.getSolver().getValue(e);
      if (value != null)
      {
        if (value instanceof Boolean)
        {
          return;
        }
        if (value instanceof BigDecimal)
        {
          acceptWarning(
                  "Narrowing convertion of \"" + value + "\" from \"Decimal\" to \"Boolean\".", e,
                  null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
                  XsmpcatIssueCodesProvider.NARROWING_CONVERSION);
          return;
        }
        if (value instanceof BigInteger)
        {
          if (!BigInteger.ZERO.equals(value) && !BigInteger.ONE.equals(value))
          {
            acceptWarning(
                    "Narrowing convertion of \"" + value + "\" from \"Integer\" to \"Boolean\".", e,
                    null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
                    XsmpcatIssueCodesProvider.NARROWING_CONVERSION);
          }

          return;
        }

        acceptError("Could not convert " + value.getClass().getSimpleName() + " to Boolean.", e,
                null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
                XsmpcatIssueCodesProvider.INVALID_VALUE_CONVERSION);
      }
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
  }

  private void checkFloat(org.eclipse.xsmp.xcatalogue.Float type, Expression e)
  {
    getDecimal(e, getMin(type), getMax(type), type.isMinInclusive(), type.isMaxInclusive());
  }

  String getString(Expression e)
  {
    try
    {

      return xsmpUtil.getSolver().getString(e);
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
    return null;
  }

  BigInteger getInteger(Expression e)
  {
    try
    {

      return xsmpUtil.getSolver().getInteger(e);
    }
    catch (final SolverException ex)
    {
      error(ex.getMessage(), ex.getExpression(), null);
    }
    return null;
  }

  public BigInteger getInteger(Expression e, BigInteger min, BigInteger max)
  {
    final var value = getInteger(e);

    if (value != null
            && (min != null && value.compareTo(min) < 0 || max != null && value.compareTo(max) > 0))
    {
      acceptError("Integral value " + value + " is not in range " + min + " ... " + max + ".", e,
              null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              XsmpcatIssueCodesProvider.INVALID_VALUE_RANGE);
    }
    return value;
  }

  private void checkString(org.eclipse.xsmp.xcatalogue.String type, Expression e)
  {

    final var value = xsmpUtil.getSolver().getString(e);

    if (type.getLength() == null)
    {
      return;
    }

    final var length = getInteger(type.getLength(), BigInteger.ZERO, INT64_MAX);

    if (length == null)
    {
      return;
    }

    if (value.length() > length.intValue())
    {
      acceptError(
              "The String length cannot exceed " + length + " characters, got " + value.length()
                      + ".",
              e, null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
    }

  }

  private BigDecimal getMax(org.eclipse.xsmp.xcatalogue.Float type)
  {
    if (type.getMaximum() != null)
    {
      return getDecimal(type.getMaximum());
    }
    return FLOAT64_MAX;
  }

  private BigDecimal getMin(org.eclipse.xsmp.xcatalogue.Float type)
  {
    if (type.getMinimum() != null)
    {
      return getDecimal(type.getMinimum());
    }
    return FLOAT64_MAX.negate();
  }

  protected void check(Type type, Expression e)
  {
    check(type, e, false);
  }

  protected void check(Type type, Expression e, boolean byPointer)
  {
    if (e == null || type == null || type.eIsProxy())
    {
      return;
    }
    if (e instanceof DesignatedInitializer)
    {
      e = ((DesignatedInitializer) e).getExpr();
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
        case XcataloguePackage.ARRAY:
          checkArray((Array) type, e);
          break;
        case XcataloguePackage.ENUMERATION:
          checkEnumeration((Enumeration) type, e);
          break;
        case XcataloguePackage.FLOAT:
          checkFloat((org.eclipse.xsmp.xcatalogue.Float) type, e);
          break;
        case XcataloguePackage.INTEGER:
          checkInteger((org.eclipse.xsmp.xcatalogue.Integer) type, e);
          break;
        case XcataloguePackage.PRIMITIVE_TYPE:
          checkPrimitiveType((PrimitiveType) type, e);
          break;
        case XcataloguePackage.STRING:
          checkString((org.eclipse.xsmp.xcatalogue.String) type, e);
          break;
        case XcataloguePackage.STRUCTURE:
        case XcataloguePackage.CLASS:
        case XcataloguePackage.EXCEPTION:
          checkStructure((Structure) type, e);
          break;
        default:
          // ignore other types
          break;
      }
    }
  }

  protected void checkPtr(Type type, Expression e)
  {
    if (e == null || type == null || type.eIsProxy())
    {
      return;
    }
    if (!(e instanceof KeywordExpression))
    {
      acceptError("Expecting a pointer, got " + e.getClass().getSimpleName() + ".", e, null,
              ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
    }
  }

  private void checkArray(Array type, Expression e)
  {
    if (type.getSize() != null)
    {
      if (e instanceof CollectionLiteral)
      {
        final var size = getInteger(type.getSize(), BigInteger.ZERO, INT64_MAX);

        if (size == null)
        {
          return;
        }

        final var s = size.longValue();

        final var values = (CollectionLiteral) e;

        // check correct number of elements
        if (values.getElements().size() == 1
                && values.getElements().get(0) instanceof EmptyExpression)
        {
          // OK
        }
        else if (values.getElements().size() <= s)
        {
          // check each array field
          values.getElements().stream().forEach(j -> check(type.getItemType(), j));

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

  private void checkInteger(org.eclipse.xsmp.xcatalogue.Integer type, Expression e)
  {
    getInteger(e, getMin(type), getMax(type));
  }

  private void checkPrimitiveType(PrimitiveType type, Expression e)
  {
    switch (xsmpUtil.getPrimitiveType(type))
    {
      case BOOL:
        checkBoolean(e);
        break;
      case CHAR8:
        getChar(e);
        break;
      case DATE_TIME:
        getDateTime(e);
        break;
      case DURATION:
        getDuration(e);
        break;
      case FLOAT32:
        getDecimal(e, FLOAT32_MIN, FLOAT32_MAX, true, true);
        break;
      case FLOAT64:
        getDecimal(e, FLOAT64_MIN, FLOAT64_MAX, true, true);
        break;
      case INT16:
        getInteger(e, INT16_MIN, INT16_MAX);
        break;
      case INT32:
        getInteger(e, INT32_MIN, INT32_MAX);
        break;
      case INT64:
        getInteger(e, INT64_MIN, INT64_MAX);
        break;
      case INT8:
        getInteger(e, INT8_MIN, INT8_MAX);
        break;
      case STRING8:
        getString(e);
        break;
      case UINT16:
        getInteger(e, ZERO, UINT16_MAX);
        break;
      case UINT32:
        getInteger(e, ZERO, UINT32_MAX);
        break;
      case UINT64:
        getInteger(e, ZERO, UINT64_MAX);
        break;
      case UINT8:
        getInteger(e, ZERO, UINT8_MAX);
        break;
      default:
        break;
    }
  }

  private void checkStructure(Structure type, Expression e)
  {
    if (e instanceof CollectionLiteral)
    {

      final var fields = xsmpUtil.getAssignableFields(type);

      final var values = (CollectionLiteral) e;

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
          check(fields.get(i).getType(), values.getElements().get(i));
        }
        final var last = size - 1;
        if (last >= 0 && !(values.getElements().get(last) instanceof EmptyExpression))
        {
          check(fields.get(last).getType(), values.getElements().get(last));
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

  private BigInteger getMax(org.eclipse.xsmp.xcatalogue.Integer type)
  {
    if (type.getMaximum() != null)
    {
      return getInteger(type.getMaximum());
    }

    switch (xsmpUtil.getPrimitiveType(type))
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
      return getInteger(type.getMinimum());
    }

    switch (xsmpUtil.getPrimitiveType(type))
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
        return ZERO;
      default:
        return null;
    }
  }

  boolean startWithVowel(String word)
  {

    switch (word.charAt(0))
    {
      case 'a':
      case 'e':
      case 'i':
      case 'o':
      case 'u':
      case 'A':
      case 'E':
      case 'I':
      case 'O':
      case 'U':
        return true;
      default:
        return false;
    }
  }

  protected boolean checkTypeReference(Type type, EObject source, EReference feature)
  {
    return checkTypeReference(type, source, feature, -1);
  }

  protected boolean checkTypeReference(Type type, EObject source, EReference feature, int index)
  {
    if (doCheckTypeReference(type, source, feature, index))
    {
      final var fqn = qualifiedNameProvider.getFullyQualifiedName(type);
      // check that these specifics types are not referred
      if (QualifiedNames.Attributes_OperatorKind.equals(fqn)
              || QualifiedNames.Attributes_FieldUpdateKind.equals(fqn))
      {
        error("Cannot refers to type " + fqn + ".", source, feature, index,
                XsmpcatIssueCodesProvider.INVALID_TYPE_REFERENCE);
      }
      return true;
    }
    return false;
  }

  protected boolean doCheckTypeReference(Type type, EObject source, EReference feature, int index)
  {
    var result = false;
    if (type != null && !type.eIsProxy())
    {
      // check that the referenced type is visible
      final var minVisibility = xsmpUtil.getMinVisibility(type, source);
      if (type.getRealVisibility().getValue() > minVisibility.getValue())
      {
        error("The " + type.eClass().getName() + " "
                + qualifiedNameProvider.getFullyQualifiedName(type) + " is not visible.", source,
                feature, index, XsmpcatIssueCodesProvider.HIDDEN_ELEMENT, type.getName(),
                feature.getName(), minVisibility.getName());
      }
      // check if the type is deprecated
      if (type.isDeprecated())
      {
        warning("The " + type.eClass().getName() + " "
                + qualifiedNameProvider.getFullyQualifiedName(type) + " is deprecated.", source,
                feature, index);
      }

      final EClassifier expectedType = typeReferenceConverter.convert(feature);
      if (!expectedType.isInstance(type))
      {
        final var fea = feature.getName();

        warning("The " + type.eClass().getName() + " "
                + qualifiedNameProvider.getFullyQualifiedName(type) + " cannot be the " + fea
                + " of " + qualifiedNameProvider.getFullyQualifiedName(source).getLastSegment()
                + "; expecting " + (startWithVowel(expectedType.getName()) ? "an " : "a ")
                + expectedType.getName() + " Type.", feature, index,
                XsmpcatIssueCodesProvider.INVALID_TYPE_REFERENCE);
      }
      else
      {
        result = true;
      }

    }
    return result;
  }

  protected void checkFieldReferenceVisibility(Field field, EObject source,
          EStructuralFeature feature, int index)
  {

    final var minVisibility = xsmpUtil.getMinVisibility(field, source);
    if (field.getRealVisibility().getValue() > minVisibility.getValue())
    {
      error("The Field " + field.getName() + " is not visible.", source, feature, index,
              XsmpcatIssueCodesProvider.HIDDEN_ELEMENT, field.getName(), feature.getName(),
              minVisibility.getName());
    }

  }

  @Inject
  XsmpcatGrammarAccess ga;

  @Check
  protected void checkArray(Array elem)
  {

    // check size is positive
    if (elem.getSize() != null)
    {
      getInteger(elem.getSize(), BigInteger.ZERO, INT64_MAX);

      if (elem.getSize() instanceof CollectionLiteral)
      {
        error("Cannot use brackets for array size.", XcataloguePackage.Literals.ARRAY__SIZE);
      }
    }
    final var type = elem.getItemType();

    if (checkTypeReference(type, elem, XcataloguePackage.Literals.ARRAY__ITEM_TYPE))
    {
      if (xsmpUtil.isRecursiveType(elem, type))
      {
        error("Recursive Array Type.", XcataloguePackage.Literals.ARRAY__ITEM_TYPE);
      }

      // check that for a SimpleArray the type is valid
      if (xsmpUtil.isSimpleArray(elem) && !(elem.getItemType() instanceof SimpleType))
      {
        error("A SimpleArray requires a SimpleType.", XcataloguePackage.Literals.ARRAY__ITEM_TYPE);
      }
    }
    // "using" keyword is deprecated
    final var node = NodeModelUtils.findActualNodeFor(elem);
    for (final var n : node.getAsTreeIterable())
    {
      if (n.getGrammarElement() == ga.getNamespaceMemberAccess().getUsingKeyword_3_7_2_0_1())
      {
        addIssue("'using' keyword is deprecated. Replace with 'array'.", elem, n.getOffset(),
                n.getLength(), XsmpcatIssueCodesProvider.DEPRECATED_MODEL_PART);
      }
    }

  }

  private static Set<String> validUsages = XcataloguePackage.eINSTANCE.getEClassifiers().stream()
          .filter(c -> c instanceof EClass
                  && XcataloguePackage.Literals.NAMED_ELEMENT.isSuperTypeOf((EClass) c))
          .map(ENamedElement::getName).collect(Collectors.toSet());

  @Check
  protected void checkAttributeType(AttributeType elem)
  {
    doCheckTypeReference(elem.getType(), elem, XcataloguePackage.Literals.ATTRIBUTE_TYPE__TYPE, -1);

    if (elem.getDefault() == null)
    {
      warning("Default value is missing.", XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
    }
    else
    {
      check(elem.getType(), elem.getDefault());
    }
    final Set<String> visitedUsages = new HashSet<>();
    for (var i = 0; i < elem.getUsage().size(); ++i)
    {
      if (!validUsages.contains(elem.getUsage().get(i)))
      {
        warning("Invalid usage.", XcataloguePackage.Literals.ATTRIBUTE_TYPE__USAGE, i);
      }
      if (!visitedUsages.add(elem.getUsage().get(i)))
      {
        warning("Duplicated usage.", XcataloguePackage.Literals.ATTRIBUTE_TYPE__USAGE, i);
      }
    }
  }

  @Check
  protected void checkAttribute(Attribute elem)
  {

    if (checkTypeReference(elem.getType(), elem, XcataloguePackage.Literals.ATTRIBUTE__TYPE))
    {
      final var type = (AttributeType) elem.getType();

      if (type.getDefault() == null && elem.getValue() == null)
      {
        error("A value is required.", XcataloguePackage.Literals.ATTRIBUTE__VALUE);
      }
      else
      {
        // check the attribute value
        check(type.getType(), elem.getValue());
      }
    }
  }

  @Check
  protected void checkClassAndException(org.eclipse.xsmp.xcatalogue.Class elem)
  {
    final var base = elem.getBase();

    // check recursive base
    if (xsmpUtil.isBaseOf(elem, elem.getBase()))
    {
      error("A " + elem.eClass().getName() + " cannot extends from a derived Type.",
              XcataloguePackage.Literals.CLASS__BASE,
              XsmpcatIssueCodesProvider.INVALID_TYPE_REFERENCE);
    }

    checkTypeReference(base, elem, XcataloguePackage.Literals.CLASS__BASE);

    // check members type
    final var members = elem.getMember();
    final var nbMembers = members.size();

    for (var i = 0; i < nbMembers; ++i)
    {
      final var member = members.get(i);
      switch (member.eClass().getClassifierID())
      {
        case XcataloguePackage.CONSTANT:
          constantElementModifierValidator.checkModifiers((VisibilityElement) member, this);
          break;
        case XcataloguePackage.FIELD:
          fieldElementModifierValidator.checkModifiers((VisibilityElement) member, this);
          break;
        case XcataloguePackage.OPERATION:
          operationElementModifierValidator.checkModifiers((VisibilityElement) member, this);
          break;
        case XcataloguePackage.PROPERTY:
          propertyElementModifierValidator.checkModifiers((VisibilityElement) member, this);
          break;
        case XcataloguePackage.ASSOCIATION:
          associationElementModifierValidator.checkModifiers((VisibilityElement) member, this);
          break;
        default:
          error(elem.eClass().getName()
                  + " shall only contain Properties, Operations, Associations, Constants and Fields. Got  "
                  + member.eClass().getName() + ".",
                  XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER, i,
                  XsmpcatIssueCodesProvider.INVALID_TYPE_REFERENCE);
          break;
      }
    }

    if (!elem.isAbstract() && elem.getMember().stream()
            .anyMatch(m -> m instanceof NamedElement && xsmpUtil.isAbstract((NamedElement) m)))
    {
      error("The " + elem.eClass().getName() + " shall be abstract.",
              XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
    }

  }

  @Check
  protected void checkComponent(Component elem)
  {
    final var base = elem.getBase();

    // check recursive base
    if (xsmpUtil.isBaseOf(elem, elem.getBase()))
    {
      error("A " + elem.eClass().getName() + " cannot extends a derived Type.",
              XcataloguePackage.Literals.COMPONENT__BASE);
    }

    checkTypeReference(base, elem, XcataloguePackage.Literals.COMPONENT__BASE);

    // check interfaces
    final var interfaces = elem.getInterface();
    final var nbInterfaces = interfaces.size();
    final Set<Type> visited = new HashSet<>();
    for (var i = 0; i < nbInterfaces; ++i)
    {
      final var interf = interfaces.get(i);
      if (!visited.add(interf))
      {
        error("Duplicate Interface.", XcataloguePackage.Literals.COMPONENT__INTERFACE, i);
      }

      checkTypeReference(interfaces.get(i), elem, XcataloguePackage.Literals.COMPONENT__INTERFACE,
              i);
    }

    final var members = elem.getMember();
    final var nbMembers = members.size();

    for (var i = 0; i < nbMembers; ++i)
    {
      final var member = members.get(i);
      switch (member.eClass().getClassifierID())
      {
        case XcataloguePackage.CONSTANT:
          constantElementModifierValidator.checkModifiers((VisibilityElement) member, this);
          break;
        case XcataloguePackage.FIELD:
          fieldElementModifierValidator.checkModifiers((VisibilityElement) member, this);
          break;
        case XcataloguePackage.ENTRY_POINT:
        case XcataloguePackage.EVENT_SINK:
        case XcataloguePackage.EVENT_SOURCE:
        case XcataloguePackage.CONTAINER:
        case XcataloguePackage.REFERENCE:
          break;
        case XcataloguePackage.OPERATION:
          operationElementModifierValidator.checkModifiers((VisibilityElement) member, this);
          break;
        case XcataloguePackage.PROPERTY:
          propertyElementModifierValidator.checkModifiers((VisibilityElement) member, this);
          break;
        case XcataloguePackage.ASSOCIATION:
          associationElementModifierValidator.checkModifiers((VisibilityElement) member, this);
          break;
        default:
          error("A " + elem.eClass().getName()
                  + " shall only contain Constants, Properties, Association, Operations, Fields, EntryPoints, EventSink, EventSource, Container and Reference. Got  "
                  + member.eClass().getName() + ".",
                  XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER, i,
                  XsmpcatIssueCodesProvider.INVALID_TYPE_REFERENCE);
          break;
      }

    }
    if (!elem.isAbstract() && elem.getMember().stream()
            .anyMatch(m -> m instanceof NamedElement && xsmpUtil.isAbstract((NamedElement) m)))
    {
      error("The " + elem.eClass().getName() + " shall be abstract.",
              XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
    }
  }

  @Check
  protected void checkConstant(Constant f)
  {

    if (f.getValue() == null)
    {
      error("A Constant must have an initialization value.",
              XcataloguePackage.Literals.CONSTANT__VALUE);
    }

    if (checkTypeReference(f.getType(), f, XcataloguePackage.Literals.CONSTANT__TYPE))
    {
      // check the constant value
      check(f.getType(), f.getValue());
    }

  }

  @Check
  protected void checkAssociation(Association elem)
  {
    checkTypeReference(elem.getType(), elem, XcataloguePackage.Literals.ASSOCIATION__TYPE);

    check(elem.getType(), elem.getDefault(), xsmpUtil.isByPointer(elem));
  }

  @Check
  protected void checkContainer(Container f)
  {

    if (f.getDefaultComponent() != null && f.getType() instanceof Component
            && !xsmpUtil.isBaseOf(f.getType(), f.getDefaultComponent()))
    {
      error(f.getType().getName() + " is not a base of " + f.getDefaultComponent().getName(),
              XcataloguePackage.Literals.CONTAINER__DEFAULT_COMPONENT);
    }

    checkTypeReference(f.getType(), f, XcataloguePackage.Literals.CONTAINER__TYPE);

    checkTypeReference(f.getDefaultComponent(), f,
            XcataloguePackage.Literals.CONTAINER__DEFAULT_COMPONENT);

  }

  @Check
  protected void checkMultiplicity(NamedElementWithMultiplicity element)
  {

    final var lower = element.getLower();

    if (lower < 0)
    {
      error("Lower bound shall be a positive number or 0.", element.getMultiplicity(),
              XcataloguePackage.Literals.MULTIPLICITY__LOWER);
    }
    final var upper = element.getUpper();
    if (upper != -1 && upper < lower)
    {
      error("Lower bound shall be less or equal to the upper bound, if present.\nUpper bound shall be -1 or larger or equal to the lower bound.",
              element.getMultiplicity(), XcataloguePackage.Literals.MULTIPLICITY__LOWER);
    }

  }

  @Check
  protected void checkCatalogue(Catalogue doc)
  {
    if (!resourceServiceprovider.canHandle(doc.eResource().getURI()))
    {
      warning("This document is not supported.", XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
    }
  }

  @Check
  protected void checkEntryPoint(EntryPoint f)
  {

    for (var i = 0; i < f.getInput().size(); ++i)
    {

      final var field = f.getInput().get(i);

      checkFieldReferenceVisibility(field, f, XcataloguePackage.Literals.ENTRY_POINT__INPUT, i);
      if (!field.isInput())
      {
        error("Field is not an Input.", XcataloguePackage.Literals.ENTRY_POINT__INPUT, i);
      }
    }
    for (var i = 0; i < f.getOutput().size(); ++i)
    {
      final var field = f.getOutput().get(i);

      checkFieldReferenceVisibility(field, f, XcataloguePackage.Literals.ENTRY_POINT__OUTPUT, i);
      if (!field.isOutput())
      {
        error("Field is not an Output.", XcataloguePackage.Literals.ENTRY_POINT__OUTPUT, i);
      }
    }

  }

  @Check
  protected void checkEnumeration(Enumeration elem)
  {

    final Set<BigInteger> values = new HashSet<>();

    if (elem.getLiteral().isEmpty())
    {
      error("An Enumeration shall contains at least one literal.",
              XcataloguePackage.Literals.ENUMERATION__LITERAL);
    }

    for (final EnumerationLiteral l : elem.getLiteral())
    {

      if (l.getValue() instanceof CollectionLiteral)
      {
        error("Cannot use brackets for enumeration literal value.", l,
                XcataloguePackage.Literals.ENUMERATION_LITERAL__VALUE);
      }
      final var value = getInteger(l.getValue(), INT32_MIN, INT32_MAX);

      if (value != null && !values.add(value))
      {
        error("Enumeration Literal Values shall be unique within an Enumeration.", l,
                XcataloguePackage.Literals.ENUMERATION_LITERAL__VALUE,
                XsmpcatIssueCodesProvider.DUPLICATE_ENUMERATION_VALUE);
      }

    }
  }

  @Check
  protected void checkField(Field f)
  {

    if (checkTypeReference(f.getType(), f, XcataloguePackage.Literals.FIELD__TYPE))
    {
      // check the field value
      check(f.getType(), f.getDefault());

      if (QualifiedNames.Smp_String8.equals(xsmpUtil.fqn(f.getType())))
      {
        error("The type Smp.String8 cannot be used with fields. ",
                XcataloguePackage.Literals.FIELD__TYPE);
      }
    }

  }

  @Check
  protected void checkFloat(org.eclipse.xsmp.xcatalogue.Float elem)
  {
    // check type
    if (checkTypeReference(elem.getPrimitiveType(), elem,
            XcataloguePackage.Literals.FLOAT__PRIMITIVE_TYPE))
    {
      final var kind = xsmpUtil.getPrimitiveType(elem);
      if (kind != null)
      {
        switch (kind)
        {
          case FLOAT64:
          case FLOAT32:
            break;
          default:
            error("Expecting a Floating Point Type, got " + kind.name() + ".",
                    XcataloguePackage.Literals.FLOAT__PRIMITIVE_TYPE);
            break;
        }
      }
    }

    final var min = getDecimal(elem.getMinimum());
    final var max = getDecimal(elem.getMaximum());

    if (min != null && max != null)
    {
      final Boolean minInclusive = elem.isMinInclusive();
      final Boolean maxInclusive = elem.isMaxInclusive();

      if (min.compareTo(max) >= 0 && (min.compareTo(max) > 0 || !minInclusive || !maxInclusive))
      {
        error("Minimum shall be less than Maximum.", XcataloguePackage.Literals.FLOAT__MINIMUM);
      }
    }

  }

  @Check
  protected void checkInteger(org.eclipse.xsmp.xcatalogue.Integer elem)
  {

    // check type
    if (checkTypeReference(elem.getPrimitiveType(), elem,
            XcataloguePackage.Literals.INTEGER__PRIMITIVE_TYPE))
    {
      final var kind = xsmpUtil.getPrimitiveType(elem);

      BigInteger baseTypeMin = null;
      BigInteger baseTypeMax = null;
      if (kind != null)
      {
        switch (kind)
        {
          case INT16:
            baseTypeMin = INT16_MIN;
            baseTypeMax = INT16_MAX;
            break;
          case INT32:
            baseTypeMin = INT32_MIN;
            baseTypeMax = INT32_MAX;
            break;
          case INT64:
            baseTypeMin = INT64_MIN;
            baseTypeMax = INT64_MAX;
            break;
          case INT8:
            baseTypeMin = INT8_MIN;
            baseTypeMax = INT8_MAX;
            break;
          case UINT16:
            baseTypeMin = ZERO;
            baseTypeMax = UINT16_MAX;
            break;
          case UINT32:
            baseTypeMin = ZERO;
            baseTypeMax = UINT32_MAX;
            break;
          case UINT64:
            baseTypeMin = ZERO;
            baseTypeMax = UINT64_MAX;
            break;
          case UINT8:
            baseTypeMin = ZERO;
            baseTypeMax = UINT8_MAX;
            break;
          default:
            error("Expecting an Integral Type, got " + kind.name() + ".",
                    XcataloguePackage.Literals.INTEGER__PRIMITIVE_TYPE);
            return;
        }
      }
      getInteger(elem.getMinimum(), baseTypeMin, baseTypeMax);
      getInteger(elem.getMaximum(), baseTypeMin, baseTypeMax);
    }

    final var min = getInteger(elem.getMinimum());
    final var max = getInteger(elem.getMaximum());

    if (min != null && max != null && min.compareTo(max) > 0)
    {
      error("Minimum shall be less or equal than Maximum.",
              XcataloguePackage.Literals.INTEGER__MINIMUM);
    }

  }

  @Check
  protected void checkInterface(Interface elem)
  {

    final var bases = elem.getBase();
    final var nbBases = bases.size();
    final Set<Type> visited = new HashSet<>();
    for (var i = 0; i < nbBases; ++i)
    {
      if (xsmpUtil.isBaseOf(elem, bases.get(i)))
      {
        error("An Interface cannot extends a derived Type.",
                XcataloguePackage.Literals.INTERFACE__BASE, i,
                XsmpcatIssueCodesProvider.INVALID_TYPE_REFERENCE);
      }
      if (!visited.add(bases.get(i)))
      {
        error("Duplicate Interface.", XcataloguePackage.Literals.INTERFACE__BASE, i,
                XsmpcatIssueCodesProvider.INVALID_TYPE_REFERENCE);
      }

      checkTypeReference(bases.get(i), elem, XcataloguePackage.Literals.INTERFACE__BASE, i);
    }

    final var members = elem.getMember();
    final var nbMembers = members.size();
    for (var i = 0; i < nbMembers; ++i)
    {
      final var member = members.get(i);
      switch (member.eClass().getClassifierID())
      {
        case XcataloguePackage.CONSTANT:
          constantInInterfaceOrStructureElementModifierValidator
                  .checkModifiers((VisibilityElement) member, this);
          break;
        case XcataloguePackage.OPERATION:
          operationInInterfaceElementModifierValidator.checkModifiers((VisibilityElement) member,
                  this);
          break;
        case XcataloguePackage.PROPERTY:
          propertyInInterfaceElementModifierValidator.checkModifiers((VisibilityElement) member,
                  this);
          break;
        default:
          error("An Interface shall only contain Constants, Properties and Operations. Got  "
                  + member.eClass().getName() + ".",
                  XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER, i,
                  XsmpcatIssueCodesProvider.INVALID_TYPE_REFERENCE);
          break;
      }
    }
  }

  @Check
  protected void checkNamedElement(NamedElementImplCustom elem)
  {
    elem.check(getChain());

    if (elem.getName() != null && !VALID_ID_PATTERN.matcher(elem.getName()).matches())
    {
      error("An Element Name shall only contain letters, digits, and the underscore.",
              XcataloguePackage.Literals.NAMED_ELEMENT__NAME,
              XsmpcatIssueCodesProvider.NAME_IS_INVALID);
    }

    if (elem.eContainingFeature() != XcataloguePackage.Literals.OPERATION__RETURN_PARAMETER
            && reservedKeywordsSet.contains(elem.getName()))
    {
      error("An Element Name shall not be an ISO/ANSI C++ keyword.",
              XcataloguePackage.Literals.NAMED_ELEMENT__NAME,
              XsmpcatIssueCodesProvider.NAME_IS_RESERVED_KEYWORD);
    }
    // check the metadatum
    final var members = elem.getMetadatum().getMetadata();
    final var nbMembers = members.size();
    final var eClass = elem.eClass();
    final List<String> elemUsages = cache.get(eClass, eClass.eResource(),
            () -> Stream
                    .concat(Stream.of(eClass.getName()),
                            eClass.getEAllSuperTypes().stream().map(EClass::getName))
                    .collect(Collectors.toList()));

    final Set<AttributeType> visitedTypes = new HashSet<>();
    for (var i = 0; i < nbMembers; ++i)
    {
      final var metadata = members.get(i);
      if (!(metadata.getType() instanceof AttributeType))
      {
        continue;
      }
      final var type = (AttributeType) metadata.getType();

      if (type == null || type.eIsProxy())
      {
        continue;
      }

      if (!type.isAllowMultiple() && !visitedTypes.add(type))
      {
        warning("Duplicate annotation of non-repeatable type. Only annotation types marked @allowMultiple can be used multiple times at one target.",
                elem.getMetadatum(), XcataloguePackage.Literals.METADATUM__METADATA, i);
      }
      var usageOk = false;
      for (final String usage : type.getUsage())
      {
        if (elemUsages.contains(usage))
        {
          usageOk = true;
          break;
        }
      }
      if (!usageOk)
      {
        warning("This annotation is disallowed for this location.", elem.getMetadatum(),
                XcataloguePackage.Literals.METADATUM__METADATA, i);
      }
    }

  }

  @Check
  protected void checkOperation(final Operation op)
  {

    // check the constructor name
    if (Objects.equals(op.getName(), ((NamedElement) op.eContainer()).getName())
            && !xsmpUtil.isConstructor(op))
    {
      error("Only a Constructor can have the name of the containing Type.",
              XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
    }

    if (xsmpUtil.isConstructor(op))
    {
      if (xsmpUtil.getOperatorKind(op) != OperatorKind.NONE)
      {
        error("Operator and Constructor attributes cannot be both set at the same time for a given Operation element as they are mutually exclusive.",
                XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
      }
      if (op.getReturnParameter() != null)
      {
        error("A Constructor cannot have a return parameter.",
                XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
      }
    }

    if (xsmpUtil.isStatic(op))
    {
      if (op.eContainer() instanceof Interface)
      {
        warning("An Operation of an Interface shall not be static.",
                XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
      }
      if (xsmpUtil.isVirtual(op))
      {
        error("An Operation cannot be both Static and Virtual.",
                XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
      }
      if (xsmpUtil.isAbstract(op))
      {
        error("An Operation cannot be both Static and Abstract.",
                XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
      }
      if (xsmpUtil.isConst(op))
      {
        error("An Operation cannot be both Static and Const.",
                XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
      }
    }
    // check that default value of parameters are provided
    var requireDefaultValue = false;
    for (final Parameter p : op.getParameter())
    {
      if (p.getDefault() != null)
      {
        requireDefaultValue = true;
      }
      else if (requireDefaultValue)
      {
        error("Parameter requires a default vallue.", p,
                XcataloguePackage.Literals.PARAMETER__DEFAULT);
      }

    }
    // check exceptions
    final var raisedException = op.getRaisedException();
    final var nbRaisedExceptions = raisedException.size();
    for (var i = 0; i < nbRaisedExceptions; ++i)
    {
      checkTypeReference(raisedException.get(i), op,
              XcataloguePackage.Literals.OPERATION__RAISED_EXCEPTION, i);
    }
  }

  @Check
  protected void checkParameter(Parameter p)
  {

    if (checkTypeReference(p.getType(), p, XcataloguePackage.Literals.PARAMETER__TYPE))
    {
      check(p.getType(), p.getDefault(), xsmpUtil.isByPointer(p));

    }
    // an element cannot be both byPointer and ByReference
    if (xsmpUtil.isByPointer(p) && xsmpUtil.isByReference(p))
    {
      error("A Parameter cannot be both ByPointer and ByReference.",
              XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
    }
  }

  @Check
  protected void checkPrimitiveType(PrimitiveType elem)
  {
    if (xsmpUtil.getPrimitiveType(elem) == null)
    {
      error("Unknown Primitive Type.", XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
    }
  }

  @Check
  protected void checkProperty(Property p)
  {

    // check that field is in the same class or a base class

    final var field = p.getAttachedField();
    if (field != null)
    {

      checkFieldReferenceVisibility(field, p, XcataloguePackage.Literals.PROPERTY__ATTACHED_FIELD,
              -1);
      if (p.getType() != field.getType())
      {
        error(p.getType().getName() + " is not a base of " + field.getType().getName(),
                XcataloguePackage.Literals.PROPERTY__ATTACHED_FIELD);
      }

      if (xsmpUtil.isStatic(p) && !xsmpUtil.isStatic(field))
      {
        error("The field is not static.", XcataloguePackage.Literals.PROPERTY__ATTACHED_FIELD);
      }
    }
    // an element cannot be both byPointer and ByReference
    if (xsmpUtil.isByPointer(p) && xsmpUtil.isByReference(p))
    {
      error("A Property cannot be both ByPointer and ByReference.",
              XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
    }

    if (xsmpUtil.isStatic(p))
    {
      if (p.eContainer() instanceof Interface)
      {
        error("A Property of an Interface shall not be static.",
                XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
      }
      if (xsmpUtil.isVirtual(p))
      {
        error("A Property cannot be both Static and Virtual.",
                XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
      }
      if (xsmpUtil.isAbstract(p))
      {
        error("A Property cannot be both Static and Abstract.",
                XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
      }
      if (xsmpUtil.isConst(p))
      {
        error("A Property cannot be both Static and Const.",
                XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
      }
    }

    checkTypeReference(p.getType(), p, XcataloguePackage.Literals.PROPERTY__TYPE);

    // check set exceptions
    final var setRaises = p.getSetRaises();
    final var nbSetRaises = setRaises.size();
    for (var i = 0; i < nbSetRaises; ++i)
    {
      checkTypeReference(setRaises.get(i), p, XcataloguePackage.Literals.PROPERTY__SET_RAISES, i);
    }

    // check get exceptions
    final var getRaises = p.getGetRaises();
    final var nbGetRaises = getRaises.size();
    for (var i = 0; i < nbGetRaises; ++i)
    {
      checkTypeReference(getRaises.get(i), p, XcataloguePackage.Literals.PROPERTY__GET_RAISES, i);
    }

  }

  @Check
  protected void checkReference(Reference f)
  {
    checkTypeReference(f.getInterface(), f, XcataloguePackage.Literals.REFERENCE__INTERFACE);
  }

  @Check
  protected void checkValueReference(ValueReference elem)
  {
    checkTypeReference(elem.getType(), elem, XcataloguePackage.Literals.VALUE_REFERENCE__TYPE);
  }

  @Check
  protected void checkEventType(EventType elem)
  {
    checkTypeReference(elem.getEventArgs(), elem,
            XcataloguePackage.Literals.EVENT_TYPE__EVENT_ARGS);
  }

  @Check
  protected void checkEventSink(EventSink elem)
  {
    checkTypeReference(elem.getType(), elem, XcataloguePackage.Literals.EVENT_SINK__TYPE);
  }

  @Check
  protected void checkEventSource(EventSource elem)
  {
    checkTypeReference(elem.getType(), elem, XcataloguePackage.Literals.EVENT_SOURCE__TYPE);
  }

  @Check
  protected void checkString(org.eclipse.xsmp.xcatalogue.String elem)
  {
    // check size > 0
    getInteger(elem.getLength(), BigInteger.ZERO, INT64_MAX);

    if (elem.getLength() instanceof CollectionLiteral)
    {
      error("Cannot use brackets for string length.", XcataloguePackage.Literals.STRING__LENGTH);
    }
  }

  @Check
  protected void checkStructure(Structure elem)
  {
    if (elem.eClass() == XcataloguePackage.Literals.STRUCTURE)
    {
      final var members = elem.getMember();
      final var nbMembers = members.size();

      // check members type and visibility
      for (var i = 0; i < nbMembers; ++i)
      {
        final var member = members.get(i);
        switch (member.eClass().getClassifierID())
        {
          case XcataloguePackage.FIELD:
            fieldInStructModifierValidator.checkModifiers((VisibilityElement) member, this);
            break;
          case XcataloguePackage.CONSTANT:
            constantInInterfaceOrStructureElementModifierValidator
                    .checkModifiers((VisibilityElement) member, this);
            break;
          default:
            error("A Structure shall only contain Constants and Fields. Got  "
                    + member.eClass().getName() + ".",
                    XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER, i,
                    XsmpcatIssueCodesProvider.INVALID_TYPE_REFERENCE);
            break;
        }
      }
    }

    for (final var member : xsmpUtil.getFields(elem))
    {
      if (member instanceof Field && xsmpUtil.isRecursiveType(elem, member.getType()))
      {
        error("Recursive Field Type.", member, XcataloguePackage.Literals.FIELD__TYPE);
      }
    }
  }

  @Check(CheckType.NORMAL)
  protected void checkTypeNormal(Type p)
  {
    // check that the UUID is not null
    if (p.getUuid() == null)
    {
      error("Missing Type UUID.", XcataloguePackage.Literals.NAMED_ELEMENT__NAME,
              XsmpcatIssueCodesProvider.INVALID_UUID);
    }
  }

  private static final Pattern UUID_PATTERN = java.util.regex.Pattern
          .compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

  @Check
  protected void checkType(Type p)
  {

    // check that the UUID is valid
    if (p.getUuid() != null && !UUID_PATTERN.matcher(p.getUuid()).matches())
    {
      error("The UUID is invalid.", XcataloguePackage.Literals.TYPE__UUID,
              XsmpcatIssueCodesProvider.INVALID_UUID);
    }

    // check type modifiers
    switch (p.eClass().getClassifierID())
    {
      case XcataloguePackage.CLASS:
      case XcataloguePackage.EXCEPTION:
      case XcataloguePackage.MODEL:
      case XcataloguePackage.SERVICE:
        classModifierValidator.checkModifiers(p, this);
        break;
      default:
        typeModifierValidator.checkModifiers(p, this);
        break;
    }
  }

  @Check
  protected void checkDesignatedInitializer(DesignatedInitializer p)
  {
    final var field = p.getField();
    if (field != null && !field.eIsProxy())
    {
      if (field.getRealVisibility() != VisibilityKind.PUBLIC)
      {
        error("This field is not public.", XcataloguePackage.Literals.DESIGNATED_INITIALIZER__FIELD,
                XsmpcatIssueCodesProvider.HIDDEN_ELEMENT, field.getName(), "field",
                VisibilityKind.PUBLIC.getName());
      }
      if (xsmpUtil.isStatic(field))
      {
        error("Cannot assign a static field.",
                XcataloguePackage.Literals.DESIGNATED_INITIALIZER__FIELD);
      }
      final var expectedField = xsmpUtil.getField(p);
      if (expectedField != field && expectedField != null)
      {
        error("Wrong field name, expecting " + expectedField.getName(),
                XcataloguePackage.Literals.DESIGNATED_INITIALIZER__FIELD);
      }
    }
  }

  @Check
  protected void checkNamedElementReference(NamedElementReference literal)
  {

    final EObject value = literal.getValue();
    if (value instanceof EnumerationLiteral)
    {
      final var type = (Type) value.eContainer();
      final var minVisibility = xsmpUtil.getMinVisibility(type, literal);
      if (type.getRealVisibility().getValue() > minVisibility.getValue())
      {
        error("The " + type.eClass().getName() + " "
                + qualifiedNameProvider.getFullyQualifiedName(type) + " is not visible.", literal,
                XcataloguePackage.Literals.NAMED_ELEMENT_REFERENCE__VALUE, -1,
                XsmpcatIssueCodesProvider.HIDDEN_ELEMENT, type.getName(),
                XcataloguePackage.Literals.NAMED_ELEMENT_REFERENCE__VALUE.getName(),
                minVisibility.getName());
      }
    }
    else if (value instanceof Constant)
    {
      final var elem = (VisibilityElement) value;
      // check that the constant is visible
      final var minVisibility = xsmpUtil.getMinVisibility(elem, literal);
      if (elem.getRealVisibility().getValue() > minVisibility.getValue())
      {
        error("The " + elem.eClass().getName() + " "
                + qualifiedNameProvider.getFullyQualifiedName(elem) + " is not visible.", literal,
                XcataloguePackage.Literals.NAMED_ELEMENT_REFERENCE__VALUE, -1,
                XsmpcatIssueCodesProvider.HIDDEN_ELEMENT, elem.getName(),
                XcataloguePackage.Literals.NAMED_ELEMENT_REFERENCE__VALUE.getName(),
                minVisibility.getName());
      }
    }
  }
}
