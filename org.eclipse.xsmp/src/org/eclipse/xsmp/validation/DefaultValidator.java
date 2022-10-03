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
import org.eclipse.xsmp.util.ElementUtil;
import org.eclipse.xsmp.util.TypeReferenceConverter;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.Array;
import org.eclipse.xsmp.xcatalogue.Association;
import org.eclipse.xsmp.xcatalogue.Attribute;
import org.eclipse.xsmp.xcatalogue.AttributeType;
import org.eclipse.xsmp.xcatalogue.Catalogue;
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
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Interface;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.NamedElementWithMultiplicity;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.PrimitiveType;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.Reference;
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
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This class contains custom validation rules. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@Singleton
@ComposedChecks(validators = {UniqueElementValidator.class })
public class DefaultValidator extends AbstractXsmpcatValidator
{
  @Override
  public final void register(EValidatorRegistrar registrar)
  {
    // do not register this validator. job is done in XsmpcatValidator
  }

  private static final Set<String> reservedKeywordsSet = ImmutableSet.<String> builder().add(

          "alignas", "alignof", "and", "and_eq", "asm",
          // atomic_cancel (TM TS)
          // atomic_commit (TM TS)
          // atomic_noexcept (TM TS)
          "auto", "bitand", "bitor", "bool", "break", "case", "catch", "char", "char8_t", // (since
                                                                                          // C++20)
          "char16_t", "char32_t", "class", "compl", "concept", // (since C++20)
          "const", "consteval", // (since C++20)
          "constexpr", // (since C++11)
          "constinit", // (since C++20)
          "const_cast", "continue", "co_await", // (since C++20)
          "co_return", // (since C++20)
          "co_yield", // (since C++20)
          "decltype", // (since C++11)
          "default", "delete", "do", "double", "dynamic_cast", "else", "enum", "explicit", "export",
          "extern", "false", "float", "for", "friend", "goto", "if", "inline", "int", "long",
          "mutable", "namespace", "new", "noexcept", // (since C++11)
          "not", "not_eq", "nullptr", // (since C++11)
          "operator", "or", "or_eq", "private", "protected", "public",
          // reflexpr (reflection TS)

          "register", "reinterpret_cast", "requires", // (since C++20)
          "return", "short", "signed", "sizeof", "static", "static_assert", // (since C++11)
          "static_cast", "struct", "switch",
          // synchronized (TM TS)
          "template", "this", "thread_local", // (since C++11)
          "throw", "true", "try", "typedef", "typeid", "typename", "union", "unsigned", "using",
          "virtual", "void", "volatile", "wchar_t", "while", "xor", "xor_eq").build();

  private final ModifierValidator associationElementModifierValidator = new ModifierValidator(
          newArrayList("public", "protected", "private"));

  private final ModifierValidator classModifierValidator = new ModifierValidator(
          newArrayList("public", "protected", "private", "abstract"));

  private final ModifierValidator constantElementModifierValidator = new ModifierValidator(
          newArrayList("public", "protected", "private"));

  private final ModifierValidator constantInInterfaceOrStructureElementModifierValidator = new ModifierValidator(
          newArrayList());

  @Inject
  private IResourceScopeCache cache;

  @Inject
  protected ElementUtil elementUtil;

  @Inject
  protected ExpressionValidator expressionValidator;

  @Inject
  private TypeReferenceConverter typeReferenceConverter;

  private final ModifierValidator fieldElementModifierValidator = new ModifierValidator(
          newArrayList("public", "protected", "private", "input", "output", "transient"));

  private final ModifierValidator fieldInStructModifierValidator = new ModifierValidator(
          newArrayList("input", "output", "transient"));

  private final ModifierValidator operationElementModifierValidator = new ModifierValidator(
          newArrayList("public", "protected", "private"));

  private final ModifierValidator operationInInterfaceElementModifierValidator = new ModifierValidator(
          newArrayList());

  private final ModifierValidator propertyElementModifierValidator = new ModifierValidator(
          newArrayList("public", "protected", "private", "readWrite", "readOnly", "writeOnly"));

  private final ModifierValidator propertyInInterfaceElementModifierValidator = new ModifierValidator(
          newArrayList("readWrite", "readOnly", "writeOnly"));

  @Inject
  protected IResourceServiceProvider resourceServiceprovider;

  private final ModifierValidator typeModifierValidator = new ModifierValidator(
          newArrayList("public", "protected", "private"));

  @Inject
  protected XsmpUtil typeUtil;

  @Inject
  protected IQualifiedNameProvider qualifiedNameProvider;

  /**
   * {@inheritDoc}
   */
  @Override
  public void addIssue(String message, EObject source, EStructuralFeature feature, int index,
          String issueCode, String... issueData)
  {
    super.addIssue(message, source, feature, index, issueCode, issueData);
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
              XsmpcatIssueCodesProvider.INVALID_MEMBER_TYPE);
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
                + " Type.", feature, index, XsmpcatIssueCodesProvider.INVALID_MEMBER_TYPE);
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
    ExpressionValidator.checkIntegralRange(BigInteger.ZERO, ExpressionValidator.INT64_MAX,
            elem.getSize() != null ? elem.getSize().solve(this) : null, this);

    if (elem.getSize() instanceof CollectionLiteral)
    {
      error("Cannot use brackets for array size.", XcataloguePackage.Literals.ARRAY__SIZE);
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
    expressionValidator.check(elem.getType(), elem.getDefault(), this);
    for (var i = 0; i < elem.getUsage().size(); ++i)
    {
      if (!validUsages.contains(elem.getUsage().get(i)))
      {
        warning("Invalid usage.", XcataloguePackage.Literals.ATTRIBUTE_TYPE__USAGE, i);
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
      // check the attribute value
      expressionValidator.check(type.getType(), elem.getValue(), this);
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
              XsmpcatIssueCodesProvider.INVALID_MEMBER_TYPE);
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
                  XsmpcatIssueCodesProvider.INVALID_MEMBER_TYPE);
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
                  XsmpcatIssueCodesProvider.INVALID_MEMBER_TYPE);
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
    expressionValidator.check(f.getType(), f.getValue(), this);

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

    final var multiplicity = element.getMultiplicity();
    long lower;
    long upper;
    if (element.isOptional()) // ?
    {
      lower = 0;
      upper = 1;
    }
    else if (multiplicity == null) // empty
    {
      lower = 1;
      upper = 1;
    }
    else if (multiplicity.getLower() == null && multiplicity.getUpper() == null) // [] | [*] | [+]
    {
      lower = multiplicity.isAux() ? 1 : 0;
      upper = -1;
    }
    else if (multiplicity.getUpper() == null) // [INT] | [INT ... *]
    {
      final var value = multiplicity.getLower().getInteger(null).longValue();
      lower = value;
      upper = multiplicity.isAux() ? -1 : value;
    }
    else // [INT ... INT]
    {
      lower = multiplicity.getLower().getInteger(null).longValue();
      upper = multiplicity.getUpper().getInteger(null).longValue();
    }

    if (lower < 0)
    {
      error("Lower bound shall be a positive number or 0.", multiplicity,
              XcataloguePackage.Literals.MULTIPLICITY__LOWER);
    }
    if (upper != -1 && upper < lower)
    {
      error("Lower bound shall be less or equal to the upper bound, if present.\nUpper bound shall be -1 or larger or equal to the lower bound.",
              multiplicity, XcataloguePackage.Literals.MULTIPLICITY__LOWER);
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

      final var value = l.getValue().solve(this);
      ExpressionValidator.checkIntegralRange(ExpressionValidator.INT32_MIN,
              ExpressionValidator.INT32_MAX, value, this);
      if (l.getValue() instanceof CollectionLiteral)
      {
        error("Cannot use brackets for enumeration literal value.", l,
                XcataloguePackage.Literals.ENUMERATION_LITERAL__VALUE);
      }
      if (!values.add(value.getInteger(null)))
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
    expressionValidator.check(f.getType(), f.getDefault(), this);

    checkTypeReference(f.getType(), f, XcataloguePackage.Literals.FIELD__TYPE);
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
        case Float64:
        case Float32:
          break;
        default:
          error("Expecting a Floating Point Type, got " + kind.name() + ".",
                  XcataloguePackage.Literals.FLOAT__PRIMITIVE_TYPE);
          break;
      }
    }

    final var min = elem.getMinimum() != null ? elem.getMinimum().getDecimal(this) : null;
    final var max = elem.getMaximum() != null ? elem.getMaximum().getDecimal(this) : null;

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

    final var min = elem.getMinimum() != null ? elem.getMinimum().getInteger(this) : null;
    final var max = elem.getMaximum() != null ? elem.getMaximum().getInteger(this) : null;

    if (min != null && max != null && min.compareTo(max) > 0)
    {
      error("Minimum shall be less or equal than Maximum.",
              XcataloguePackage.Literals.INTEGER__MINIMUM);
    }
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
        case Int16:
          baseTypeMin = ExpressionValidator.INT16_MIN;
          baseTypeMax = ExpressionValidator.INT16_MAX;
          break;
        case Int32:
          baseTypeMin = ExpressionValidator.INT32_MIN;
          baseTypeMax = ExpressionValidator.INT32_MAX;
          break;
        case Int64:
          baseTypeMin = ExpressionValidator.INT64_MIN;
          baseTypeMax = ExpressionValidator.INT64_MAX;
          break;
        case Int8:
          baseTypeMin = ExpressionValidator.INT8_MIN;
          baseTypeMax = ExpressionValidator.INT8_MAX;
          break;
        case UInt16:
          baseTypeMin = ExpressionValidator.UINT16_MIN;
          baseTypeMax = ExpressionValidator.UINT16_MAX;
          break;
        case UInt32:
          baseTypeMin = ExpressionValidator.UINT32_MIN;
          baseTypeMax = ExpressionValidator.UINT32_MAX;
          break;
        case UInt64:
          baseTypeMin = ExpressionValidator.UINT64_MIN;
          baseTypeMax = ExpressionValidator.UINT64_MAX;
          break;
        case UInt8:
          baseTypeMin = ExpressionValidator.UINT8_MIN;
          baseTypeMax = ExpressionValidator.UINT8_MAX;
          break;
        default:
          error("Expecting an Integral Type, got " + kind.name() + ".",
                  XcataloguePackage.Literals.INTEGER__PRIMITIVE_TYPE);
          return;
      }
    }

    if (elem.getMinimum() != null)
    {
      ExpressionValidator.checkIntegralRange(baseTypeMin, baseTypeMax,
              elem.getMinimum().solve(this), this);
    }
    if (elem.getMaximum() != null)
    {
      ExpressionValidator.checkIntegralRange(baseTypeMin, baseTypeMax,
              elem.getMaximum().solve(this), this);
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
                XsmpcatIssueCodesProvider.INVALID_MEMBER_TYPE);
      }
      if (!visited.add(bases.get(i)))
      {
        error("Duplicate Interface.", XcataloguePackage.Literals.INTERFACE__BASE, i,
                XsmpcatIssueCodesProvider.INVALID_MEMBER_TYPE);
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
                  XsmpcatIssueCodesProvider.INVALID_MEMBER_TYPE);
          break;
      }
    }
  }

  @Check
  protected void checkNamedElement(NamedElementImplCustom elem)
  {
    elem.check(getChain());

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
        error("Duplicate annotation of non-repeatable type. Only annotation types marked @allowMultiple can be used multiple times at one target.",
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
        error("This annotation is disallowed for this location.", elem.getMetadatum(),
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
    ExpressionValidator.checkIntegralRange(BigInteger.ZERO, ExpressionValidator.INT64_MAX,
            elem.getLength() != null ? elem.getLength().solve(this) : null, this);

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
                    XsmpcatIssueCodesProvider.INVALID_MEMBER_TYPE);
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
