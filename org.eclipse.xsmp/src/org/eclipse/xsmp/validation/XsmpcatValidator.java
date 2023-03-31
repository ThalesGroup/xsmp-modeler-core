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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
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
import org.eclipse.xsmp.util.ElementUtil;
import org.eclipse.xsmp.util.Solver;
import org.eclipse.xsmp.util.TypeReferenceConverter;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.Array;
import org.eclipse.xsmp.xcatalogue.Association;
import org.eclipse.xsmp.xcatalogue.Attribute;
import org.eclipse.xsmp.xcatalogue.AttributeType;
import org.eclipse.xsmp.xcatalogue.Catalogue;
import org.eclipse.xsmp.xcatalogue.CharacterLiteral;
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.Component;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Container;
import org.eclipse.xsmp.xcatalogue.EntryPoint;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.EventSink;
import org.eclipse.xsmp.xcatalogue.EventSource;
import org.eclipse.xsmp.xcatalogue.EventType;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Interface;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.NamedElementWithMultiplicity;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.PrimitiveType;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.Reference;
import org.eclipse.xsmp.xcatalogue.StringLiteral;
import org.eclipse.xsmp.xcatalogue.Structure;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.ValueReference;
import org.eclipse.xsmp.xcatalogue.ValueType;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xsmp.xcatalogue.impl.NamedElementImplCustom;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
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
  protected ElementUtil elementUtil;

  @Inject
  private TypeReferenceConverter typeReferenceConverter;

  protected Solver solver = new Solver(this);

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
  protected XsmpUtil typeUtil;

  @Inject
  protected IQualifiedNameProvider qualifiedNameProvider;

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

  private void checkChar(Expression e)
  {
    if (e instanceof CharacterLiteral)
    {
      final var chr = solver.getString(e);
      if (chr.length() != 1)
      {
        acceptError("Invalid Character: " + chr, e, null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
      }
    }
    else
    {
      // check byte value
      solver.getInteger(e, INT8_MIN, INT8_MAX);
    }

  }

  private void checkDateTime(Expression e)
  {
    if (e instanceof StringLiteral)
    {
      try
      {
        Instant.parse(XsmpUtil.getString((StringLiteral) e));
      }
      catch (final Exception ex)
      {
        acceptError("Invalid DateTime format: " + ex.getMessage(), e, null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
      }
    }
    else
    {
      // check Date Time in ns
      solver.getInteger(e, INT64_MIN, INT64_MAX);
    }

  }

  private void checkDuration(Expression e)
  {
    if (e instanceof StringLiteral)
    {
      try
      {
        Duration.parse(XsmpUtil.getString((StringLiteral) e));
      }
      catch (final Exception ex)
      {
        acceptError("Invalid Duration format: " + ex.getMessage(), e, null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
      }
    }
    else
    {
      solver.getInteger(e, INT64_MIN, INT64_MAX);
    }
  }

  private void checkEnumeration(Enumeration type, Expression e)
  {
    solver.getEnum(e, type);
  }

  private void checkFloat(org.eclipse.xsmp.xcatalogue.Float type, Expression e)
  {
    solver.getDecimal(e, getMin(type), getMax(type), type.isMinInclusive(), type.isMaxInclusive());
  }

  private void checkString(org.eclipse.xsmp.xcatalogue.String type, Expression e)
  {

    final var value = solver.getString(e);
    if (type.getLength() == null)
    {
      return;
    }

    final var length = solver.getInteger(type.getLength(), BigInteger.ZERO, INT64_MAX);

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
      return solver.getDecimal(type.getMaximum());
    }
    return FLOAT64_MAX;
  }

  private BigDecimal getMin(org.eclipse.xsmp.xcatalogue.Float type)
  {
    if (type.getMinimum() != null)
    {
      return solver.getDecimal(type.getMinimum());
    }
    return FLOAT64_MAX.negate();
  }

  protected void check(Type type, Expression e)
  {
    if (e == null || type == null || type.eIsProxy())
    {
      return;
    }
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
        checkStructure((Structure) type, e);
        break;
      default:
        acceptError("Unsupported Type " + type.eClass().getName(), e, null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "invalid_type");
        break;
    }
  }

  private void checkArray(Array type, Expression e)
  {
    if (type.getSize() != null)
    {
      if (e instanceof CollectionLiteral)
      {
        final var size = solver.getInteger(type.getSize(), BigInteger.ZERO, INT64_MAX);

        if (size == null)
        {
          return;
        }

        final var s = size.longValue();

        final var values = (CollectionLiteral) e;

        // check correct number of elements
        if (values.getElements().size() <= s)
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
    solver.getInteger(e, getMin(type), getMax(type));
  }

  private void checkPrimitiveType(PrimitiveType type, Expression e)
  {
    switch (typeUtil.getPrimitiveType(type))
    {
      case BOOL:
        solver.getBoolean(e);
        break;
      case CHAR8:
        checkChar(e);
        break;
      case DATE_TIME:
        checkDateTime(e);
        break;
      case DURATION:
        checkDuration(e);
        break;
      case FLOAT32:
        solver.getDecimal(e, FLOAT32_MIN, FLOAT32_MAX, true, true);
        break;
      case FLOAT64:
        solver.getDecimal(e, FLOAT64_MIN, FLOAT64_MAX, true, true);
        break;
      case INT16:
        solver.getInteger(e, INT16_MIN, INT16_MAX);
        break;
      case INT32:
        solver.getInteger(e, INT32_MIN, INT32_MAX);
        break;
      case INT64:
        solver.getInteger(e, INT64_MIN, INT64_MAX);
        break;
      case INT8:
        solver.getInteger(e, INT8_MIN, INT8_MAX);
        break;
      case STRING8:
        solver.getString(e);
        break;
      case UINT16:
        solver.getInteger(e, UINT16_MIN, UINT16_MAX);
        break;
      case UINT32:
        solver.getInteger(e, UINT32_MIN, UINT32_MAX);
        break;
      case UINT64:
        solver.getInteger(e, UINT64_MIN, UINT64_MAX);
        break;
      case UINT8:
        solver.getInteger(e, UINT8_MIN, UINT8_MAX);
        break;
      default:
        break;
    }
  }

  private void checkStructure(Structure type, Expression e)
  {
    if (e instanceof CollectionLiteral)
    {
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
          check(fields.get(i).getType(), values.getElements().get(i));
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
      return solver.getInteger(type.getMaximum());
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
      return solver.getInteger(type.getMinimum());
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

  protected void checkTypeReference(Type type, EObject source, EReference feature)
  {
    checkTypeReference(type, source, feature, -1);
  }

  public static final QualifiedName operatorKind = QualifiedName.create("Attributes",
          "OperatorKind");

  public static final QualifiedName fieldUpdateKind = QualifiedName.create("Attributes",
          "FieldUpdateKind");

  protected void checkTypeReference(Type type, EObject source, EReference feature, int index)
  {

    doCheckTypeReference(type, source, feature, index);
    final var fqn = qualifiedNameProvider.getFullyQualifiedName(type);
    // check that these specifics types are not referred
    if (operatorKind.equals(fqn) || fieldUpdateKind.equals(fqn))
    {
      error("Cannot refers to type " + fqn + ".", source, feature, index,
              XsmpcatIssueCodesProvider.INVALID_TYPE_REFERENCE);
    }

  }

  protected void doCheckTypeReference(Type type, EObject source, EReference feature, int index)
  {
    if (type != null && !type.eIsProxy())
    {
      // check that the referenced type is visible
      final var minVisibility = XsmpUtil.getMinVisibility(type, source);
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

        error("The " + type.eClass().getName() + " "
                + qualifiedNameProvider.getFullyQualifiedName(type) + " cannot be "
                + (startWithVowel(fea) ? "an " : "a ") + fea + " of "
                + qualifiedNameProvider.getFullyQualifiedName(source) + "; "
                + (startWithVowel(fea) ? "an " : "a ") + fea + " must be "
                + (startWithVowel(expectedType.getName()) ? "an " : "a ") + expectedType.getName()
                + " Type.", feature, index, XsmpcatIssueCodesProvider.INVALID_TYPE_REFERENCE);
      }

    }

  }

  protected void checkFieldReferenceVisibility(Field field, EObject source,
          EStructuralFeature feature, int index)
  {

    final var minVisibility = XsmpUtil.getMinVisibility(field, source);
    if (field.getRealVisibility().getValue() > minVisibility.getValue())
    {
      error("The Field " + field.getName() + " is not visible.", source, feature, index,
              XsmpcatIssueCodesProvider.HIDDEN_ELEMENT, field.getName(), feature.getName(),
              minVisibility.getName());
    }

  }

  @Check
  protected void checkArray(Array elem)
  {

    // check size > 0
    if (elem.getSize() != null)
    {
      solver.getInteger(elem.getSize(), BigInteger.ZERO, INT64_MAX);

      if (elem.getSize() instanceof CollectionLiteral)
      {
        error("Cannot use brackets for array size.", XcataloguePackage.Literals.ARRAY__SIZE);
      }
    }
    var type = elem.getItemType();

    checkTypeReference(type, elem, XcataloguePackage.Literals.ARRAY__ITEM_TYPE);
    while (true)
    {

      if (type == elem)
      {
        error("Recursive Array Type.", XcataloguePackage.Literals.ARRAY__ITEM_TYPE);
        break;
      }
      if (!(type instanceof Array))
      {
        break;
      }
      type = ((Array) type).getItemType();
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

    checkTypeReference(elem.getType(), elem, XcataloguePackage.Literals.ATTRIBUTE__TYPE);

    if (elem.getType() instanceof AttributeType)
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
    if (XsmpUtil.isRecursive(elem, elem.getBase()))
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
                  + member.eClass().eClass().getName() + ".",
                  XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MEMBERS__MEMBER, i,
                  XsmpcatIssueCodesProvider.INVALID_TYPE_REFERENCE);
          break;
      }

    }

  }

  @Check
  protected void checkComponent(Component elem)
  {
    final var base = elem.getBase();

    // check recursive base
    if (XsmpUtil.isRecursive(elem, elem.getBase()))
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
  }

  @Check
  protected void checkConstant(Constant f)
  {

    if (f.getValue() == null)
    {
      error("A Constant must have an initialization value.",
              XcataloguePackage.Literals.CONSTANT__VALUE);
    }

    // check the constant value
    check(f.getType(), f.getValue());

    checkTypeReference(f.getType(), f, XcataloguePackage.Literals.CONSTANT__TYPE);

  }

  @Check
  protected void checkAssociation(Association elem)
  {
    checkTypeReference(elem.getType(), elem, XcataloguePackage.Literals.ASSOCIATION__TYPE);
  }

  @Check
  protected void checkContainer(Container f)
  {

    if (f.getDefaultComponent() != null && f.getType() instanceof Component
            && !XsmpUtil.isRecursive((Component) f.getType(), f.getDefaultComponent()))
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
      final var field = f.getInput().get(i);

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
      final var value = solver.getInteger(l.getValue(), INT32_MIN, INT32_MAX);

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
    // check the field value
    check(f.getType(), f.getDefault());

    checkTypeReference(f.getType(), f, XcataloguePackage.Literals.FIELD__TYPE);
    // TODO check no String8 type
  }

  @Check
  protected void checkFloat(org.eclipse.xsmp.xcatalogue.Float elem)
  {
    // check type
    checkTypeReference(elem.getPrimitiveType(), elem,
            XcataloguePackage.Literals.FLOAT__PRIMITIVE_TYPE);
    final var kind = typeUtil.getPrimitiveType(elem);
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

    final var min = solver.getDecimal(elem.getMinimum());
    final var max = solver.getDecimal(elem.getMaximum());

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
    checkTypeReference(elem.getPrimitiveType(), elem,
            XcataloguePackage.Literals.INTEGER__PRIMITIVE_TYPE);
    final var kind = typeUtil.getPrimitiveType(elem);

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
          baseTypeMin = UINT16_MIN;
          baseTypeMax = UINT16_MAX;
          break;
        case UINT32:
          baseTypeMin = UINT32_MIN;
          baseTypeMax = UINT32_MAX;
          break;
        case UINT64:
          baseTypeMin = UINT64_MIN;
          baseTypeMax = UINT64_MAX;
          break;
        case UINT8:
          baseTypeMin = UINT8_MIN;
          baseTypeMax = UINT8_MAX;
          break;
        default:
          error("Expecting an Integral Type, got " + kind.name() + ".",
                  XcataloguePackage.Literals.INTEGER__PRIMITIVE_TYPE);
          return;
      }
    }
    final var min = solver.getInteger(elem.getMinimum(), baseTypeMin, baseTypeMax);
    final var max = solver.getInteger(elem.getMaximum(), baseTypeMin, baseTypeMax);

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
      if (XsmpUtil.isRecursive(elem, bases.get(i)))
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

    if (!VALID_ID_PATTERN.matcher(elem.getName()).matches())
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

    // an element cannot be both byPointer and ByReference
    if (elementUtil.isByPointer(elem) && elementUtil.isByReference(elem))
    {
      error("An element cannot have both ByPointer and ByReference attributes.",
              XcataloguePackage.Literals.NAMED_ELEMENT__METADATUM);
    }

  }

  @Check
  protected void checkOperation(final Operation op)
  {

    // check the constructor name
    if (Objects.equals(op.getName(), ((NamedElement) op.eContainer()).getName())
            && !elementUtil.isConstructor(op))
    {
      error("Only a Constructor can have the name of the containing Type.",
              XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
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

    if (p.getDefault() != null && !(p.getType() instanceof ValueType))
    {
      error("Only ValueType type can have a default value.",
              XcataloguePackage.Literals.PARAMETER__DEFAULT);
    }

    checkTypeReference(p.getType(), p, XcataloguePackage.Literals.PARAMETER__TYPE);

  }

  @Check
  protected void checkPrimitiveType(PrimitiveType elem)
  {
    if (typeUtil.getPrimitiveType(elem) == null)
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
    solver.getInteger(elem.getLength(), BigInteger.ZERO, INT64_MAX);

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
    // TODO check no recursive field

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

}
