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
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.xcatalogue.Array;
import org.eclipse.xsmp.xcatalogue.Association;
import org.eclipse.xsmp.xcatalogue.Attribute;
import org.eclipse.xsmp.xcatalogue.AttributeType;
import org.eclipse.xsmp.xcatalogue.Catalogue;
import org.eclipse.xsmp.xcatalogue.CharacterLiteral;
import org.eclipse.xsmp.xcatalogue.Class;
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.Component;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Document;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Float;
import org.eclipse.xsmp.xcatalogue.Interface;
import org.eclipse.xsmp.xcatalogue.ItemWithBase;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers;
import org.eclipse.xsmp.xcatalogue.NativeType;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.ParameterDirectionKind;
import org.eclipse.xsmp.xcatalogue.PrimitiveType;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.ReferenceType;
import org.eclipse.xsmp.xcatalogue.SimpleType;
import org.eclipse.xsmp.xcatalogue.StringLiteral;
import org.eclipse.xsmp.xcatalogue.Structure;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.ValueType;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.VisibilityKind;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.CompositeNodeWithSemanticElement;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptionsProvider;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.Tuples;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Utility class for Xsmpcat
 *
 * @author daveluy
 */
public class XsmpUtil
{

  @Inject
  private IQualifiedNameProvider qualifiedNameProvider;

  @Inject
  protected IResourceScopeCache cache;

  @Inject
  protected IResourceDescriptionsProvider resourceDescriptionProvider;

  @Inject
  protected Solver solver;

  public Solver getSolver()
  {
    return solver;
  }

  public enum OperatorKind
  {
    /** No operator */
    NONE,
    /** Positive value */
    POSITIVE,
    /** Negative value */
    NEGATIVE,
    /** Assign new value */
    ASSIGN,
    /** Add value */
    ADD,
    /** Subtract value */
    SUBTRACT,
    /** Multiply with value */
    MULTIPLY,
    /** Divide by value */
    DIVIDE,
    /** Remainder of division */
    REMAINDER,
    /** Is greater than */
    GREATER,
    /** Is less than */
    LESS,
    /** Is equal */
    EQUAL,
    /** Is not greater than */
    NOT_GREATER,
    /** Is not less than */
    NOT_LESS,
    /** Is not equal */
    NOT_EQUAL,
    /** Index into array */
    INDEXER,
    /** Sum of instance and another value */
    SUM,
    /** Difference between instance and another value */
    DIFFERENCE,
    /** Product of instance and another value */
    PRODUCT,
    /** Quotient of instance and another value */
    QUOTIENT,
    /** Remainder of instance divided by another value */
    MODULE
  }

  /**
   * Enumeration with SMP primitive type kinds
   */
  public enum PrimitiveTypeKind
  {
    BOOL("Bool"), CHAR8("Char8"), DATE_TIME("DateTime"), DURATION("Duration"), FLOAT32(
            "Float32"), FLOAT64("Float64"), INT8("Int8"), INT16("Int16"), INT32("Int32"), INT64(
                    "Int64"), STRING8("String8"), UINT8("UInt8"), UINT16("UInt16"), UINT32(
                            "UInt32"), UINT64("UInt64"), ENUM("Int32"), NONE("None");

    public final String label;

    PrimitiveTypeKind(String label)
    {
      this.label = label;
    }
  }

  private static final Map<QualifiedName, PrimitiveTypeKind> primitiveTypeKinds = ImmutableMap
          .<QualifiedName, PrimitiveTypeKind> builder()
          .put(QualifiedNames.Smp_Char8, PrimitiveTypeKind.CHAR8)
          .put(QualifiedNames.Smp_String8, PrimitiveTypeKind.STRING8)
          .put(QualifiedNames.Smp_Float32, PrimitiveTypeKind.FLOAT32)
          .put(QualifiedNames.Smp_Float64, PrimitiveTypeKind.FLOAT64)
          .put(QualifiedNames.Smp_Int8, PrimitiveTypeKind.INT8)
          .put(QualifiedNames.Smp_UInt8, PrimitiveTypeKind.UINT8)
          .put(QualifiedNames.Smp_Int16, PrimitiveTypeKind.INT16)
          .put(QualifiedNames.Smp_UInt16, PrimitiveTypeKind.UINT16)
          .put(QualifiedNames.Smp_Int32, PrimitiveTypeKind.INT32)
          .put(QualifiedNames.Smp_UInt32, PrimitiveTypeKind.UINT32)
          .put(QualifiedNames.Smp_Int64, PrimitiveTypeKind.INT64)
          .put(QualifiedNames.Smp_UInt64, PrimitiveTypeKind.UINT64)
          .put(QualifiedNames.Smp_Bool, PrimitiveTypeKind.BOOL)
          .put(QualifiedNames.Smp_DateTime, PrimitiveTypeKind.DATE_TIME)
          .put(QualifiedNames.Smp_Duration, PrimitiveTypeKind.DURATION).build();

  private static final Map<QualifiedName, OperatorKind> operatorKinds = ImmutableMap
          .<QualifiedName, OperatorKind> builder()
          .put(QualifiedNames.Attributes_OperatorKind_Add, OperatorKind.ADD)
          .put(QualifiedNames.Attributes_OperatorKind_Assign, OperatorKind.ASSIGN)
          .put(QualifiedNames.Attributes_OperatorKind_Difference, OperatorKind.DIFFERENCE)
          .put(QualifiedNames.Attributes_OperatorKind_Divide, OperatorKind.DIVIDE)
          .put(QualifiedNames.Attributes_OperatorKind_Equal, OperatorKind.EQUAL)
          .put(QualifiedNames.Attributes_OperatorKind_Greater, OperatorKind.GREATER)
          .put(QualifiedNames.Attributes_OperatorKind_Indexer, OperatorKind.INDEXER)
          .put(QualifiedNames.Attributes_OperatorKind_Less, OperatorKind.LESS)
          .put(QualifiedNames.Attributes_OperatorKind_Module, OperatorKind.MODULE)
          .put(QualifiedNames.Attributes_OperatorKind_Multiply, OperatorKind.MULTIPLY)
          .put(QualifiedNames.Attributes_OperatorKind_Negative, OperatorKind.NEGATIVE)
          .put(QualifiedNames.Attributes_OperatorKind_None, OperatorKind.NONE)
          .put(QualifiedNames.Attributes_OperatorKind_NotEqual, OperatorKind.NOT_EQUAL)
          .put(QualifiedNames.Attributes_OperatorKind_NotGreater, OperatorKind.NOT_GREATER)
          .put(QualifiedNames.Attributes_OperatorKind_NotLess, OperatorKind.NOT_LESS)
          .put(QualifiedNames.Attributes_OperatorKind_Positive, OperatorKind.POSITIVE)
          .put(QualifiedNames.Attributes_OperatorKind_Product, OperatorKind.PRODUCT)
          .put(QualifiedNames.Attributes_OperatorKind_Quotient, OperatorKind.QUOTIENT)
          .put(QualifiedNames.Attributes_OperatorKind_Remainder, OperatorKind.REMAINDER)
          .put(QualifiedNames.Attributes_OperatorKind_Subtract, OperatorKind.SUBTRACT)
          .put(QualifiedNames.Attributes_OperatorKind_Sum, OperatorKind.SUM).build();

  public PrimitiveTypeKind getPrimitiveType(IEObjectDescription d)
  {
    return getPrimitiveType(d.getQualifiedName());
  }

  public PrimitiveTypeKind getPrimitiveType(QualifiedName qfn)
  {
    return primitiveTypeKinds.getOrDefault(qfn, PrimitiveTypeKind.NONE);
  }

  public VisibilityKind getVisibility(IEObjectDescription o)
  {

    final var obj = o.getEObjectOrProxy();
    if (obj.eIsProxy())
    {
      final var visibility = o.getUserData("visibility");

      // if the user data is not set, it means that the object is public
      return visibility != null ? VisibilityKind.valueOf(visibility) : VisibilityKind.PUBLIC;
    }

    return getVisibility(obj);
  }

  public VisibilityKind getVisibility(EObject t)
  {
    if (t instanceof VisibilityElement)
    {
      return ((VisibilityElement) t).getRealVisibility();
    }
    // by default other objects are public
    return VisibilityKind.PUBLIC;
  }

  public boolean isDeprecated(IEObjectDescription o)
  {

    final var obj = o.getEObjectOrProxy();
    if (obj.eIsProxy())
    {
      return Boolean.parseBoolean(o.getUserData("deprecated"));
    }
    if (obj instanceof NamedElement)
    {
      return ((NamedElement) obj).isDeprecated();
    }
    return false;
  }

  public boolean isBaseOf(QualifiedName base, EObject derived)
  {
    final var resource = derived.eResource();
    if (resource != null)
    {
      final var rs = resource.getResourceSet();
      if (rs != null)
      {
        final var it = resourceDescriptionProvider.getResourceDescriptions(rs)
                .getExportedObjects(XcataloguePackage.Literals.TYPE, base, false).iterator();
        if (it.hasNext())
        {
          return isBaseOf(it.next().getEObjectOrProxy(), derived);
        }
      }
    }
    return false;
  }

  public boolean isBaseOf(EObject base, EObject derived)
  {
    if (derived instanceof Interface)
    {
      return isBaseOf(base, (Interface) derived);
    }
    if (derived instanceof Component)
    {
      return isBaseOf(base, (Component) derived);
    }
    if (derived instanceof Class)
    {
      return isBaseOf(base, (Class) derived);
    }

    return false;
  }

  protected boolean isBaseOf(EObject base, Interface derived)
  {
    return base == derived || derived.getBase().stream().anyMatch(b -> isBaseOf(base, b));
  }

  protected boolean isBaseOf(EObject base, Component derived)
  {
    return base == derived || isBaseOf(base, derived.getBase())
            || derived.getInterface().stream().anyMatch(b -> isBaseOf(base, b));
  }

  protected boolean isBaseOf(EObject base, Class derived)
  {
    return base == derived || isBaseOf(base, derived.getBase());
  }

  public VisibilityKind getMinVisibility(Type type, EObject from)
  {
    if (type != null && !type.eIsProxy())
    {

      if (type.getRealVisibility() == VisibilityKind.PRIVATE
              && EcoreUtil.isAncestor(type.eContainer(), from))
      {
        return VisibilityKind.PRIVATE;
      }
      if (from.eResource() == type.eResource())
      {
        return VisibilityKind.PROTECTED;
      }
      return VisibilityKind.PUBLIC;

    }

    return VisibilityKind.PRIVATE;
  }

  protected boolean isVisibleFrom(Type type, EObject from)
  {

    return (type.getRealVisibility() != VisibilityKind.PRIVATE
            || EcoreUtil.isAncestor(type.eContainer(), from))
            && (type.getRealVisibility() != VisibilityKind.PROTECTED
                    || from.eResource() == type.eResource());
  }

  protected boolean isVisibleFrom(VisibilityElement field, EObject from)
  {
    return getMinVisibility(field, from).getValue() >= field.getRealVisibility().getValue();
  }

  private static VisibilityKind getMinVisibility(VisibilityElement fieldOrConstant, EObject from,
          VisibilityKind min)
  {

    final var container = EcoreUtil2.getContainerOfType(from, NamedElementWithMembers.class);

    if (EcoreUtil.isAncestor(container, fieldOrConstant))
    {
      return min;
    }

    if (container instanceof ItemWithBase)
    {
      final var base = ((ItemWithBase) container).getBase();
      if (base != null)
      {
        return getMinVisibility(fieldOrConstant, base, VisibilityKind.PROTECTED);
      }
    }

    return VisibilityKind.PUBLIC;
  }

  public VisibilityKind getMinVisibility(VisibilityElement fieldOrConstant, EObject from)
  {
    if (fieldOrConstant != null && !fieldOrConstant.eIsProxy())
    {
      return getMinVisibility(fieldOrConstant, from, VisibilityKind.PRIVATE);
    }

    return VisibilityKind.PRIVATE;
  }

  public boolean isVisibleFrom(IEObjectDescription p, EObject from)
  {
    final var obj = p.getEObjectOrProxy();
    if (!obj.eIsProxy())
    {
      if (obj instanceof Type)
      {
        return isVisibleFrom((Type) obj, from);
      }
      if (obj instanceof VisibilityElement)
      {
        return isVisibleFrom((VisibilityElement) obj, from);
      }
      if (obj instanceof EnumerationLiteral)
      {
        return isVisibleFrom((Type) obj.eContainer(), from);
      }
    }
    return getVisibility(p) == VisibilityKind.PUBLIC;

  }

  public PrimitiveTypeKind getPrimitiveType(Type type)
  {
    if (type != null)
    {
      switch (type.eClass().getClassifierID())
      {
        case XcataloguePackage.PRIMITIVE_TYPE:
          return getPrimitiveType((PrimitiveType) type);
        case XcataloguePackage.FLOAT:
          return getPrimitiveType((org.eclipse.xsmp.xcatalogue.Float) type);
        case XcataloguePackage.INTEGER:
          return getPrimitiveType((org.eclipse.xsmp.xcatalogue.Integer) type);
        case XcataloguePackage.ENUMERATION:
          return PrimitiveTypeKind.ENUM;
        case XcataloguePackage.STRING:
          return PrimitiveTypeKind.STRING8;
        default:
          break;
      }
    }
    return PrimitiveTypeKind.NONE;
  }

  public PrimitiveTypeKind getPrimitiveType(PrimitiveType type)
  {
    return getPrimitiveType(fqn(type));
  }

  public PrimitiveTypeKind getPrimitiveType(org.eclipse.xsmp.xcatalogue.Float type)
  {

    final var primitiveType = type.getPrimitiveType();
    return primitiveType != null ? getPrimitiveType(primitiveType) : PrimitiveTypeKind.FLOAT64;

  }

  public PrimitiveTypeKind getPrimitiveType(org.eclipse.xsmp.xcatalogue.Integer type)
  {

    final var primitiveType = type.getPrimitiveType();
    return primitiveType != null ? getPrimitiveType(primitiveType) : PrimitiveTypeKind.INT32;

  }

  /**
   * return the full qualified name of the element
   *
   * @param t
   *          the element
   * @return the QualifiedName
   */
  public QualifiedName fqn(NamedElement t)
  {
    if (t != null)
    {
      return qualifiedNameProvider.getFullyQualifiedName(t);
    }
    return QualifiedName.EMPTY;
  }

  public String getString(StringLiteral t)
  {
    var value = t.getValue();
    // remove ENCODINGPREFIX: 'u8' | 'u' | 'U' | 'L'
    switch (value.charAt(0))
    {
      case 'u':
        if (value.charAt(1) == '8')
        {
          value = value.substring(1);
        }
        break;
      case 'U':
      case 'L':
        value = value.substring(1);
        break;
      default:
        break;
    }
    // remove quotes
    return value.substring(1, value.length() - 1);
  }

  public String getString(CharacterLiteral t)
  {
    var value = t.getValue();
    // remove ENCODINGPREFIX: 'u' | 'U' | 'L'
    switch (value.charAt(0))
    {
      case 'u':
      case 'U':
      case 'L':
        value = value.substring(1);
        break;
      default:
        break;
    }
    // remove quotes
    return value.substring(1, value.length() - 1);
  }

  public String getUnescapedChar(CharacterLiteral t)
  {
    return translateEscapes(getString(t));
  }

  public String getUnescapedString(StringLiteral t)
  {
    return translateEscapes(getString(t));
  }

  public String translateEscapes(String s) throws IllegalArgumentException
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

  /**
   * Return the list of all direct dependent packages
   */
  public Collection<Catalogue> dependentPackages(Catalogue t)
  {
    final var dependencies = new HashSet<Catalogue>();
    // collect all referenced Catalogue of interest
    t.eAllContents().forEachRemaining(it -> {
      if (it instanceof Field)
      {
        dependencies.add(EcoreUtil2.getContainerOfType(((Field) it).getType(), Catalogue.class));
      }
      else if (it instanceof Property)
      {
        dependencies.add(EcoreUtil2.getContainerOfType(((Property) it).getType(), Catalogue.class));
      }
      else if (it instanceof ItemWithBase)
      {
        dependencies
                .add(EcoreUtil2.getContainerOfType(((ItemWithBase) it).getBase(), Catalogue.class));
      }
      else if (it instanceof Array)
      {
        dependencies
                .add(EcoreUtil2.getContainerOfType(((Array) it).getItemType(), Catalogue.class));
      }

    });
    // current Catalogue is not a dependency
    dependencies.remove(t);
    final Comparator<String> c = Comparator.nullsFirst(String::compareTo);
    // Remove ecss_smp_smp and sort dependencies by name
    return dependencies.stream().filter(it -> it != null && !"ecss_smp_smp".equals(it.getName()))
            .sorted((a, b) -> c.compare(a.getName(), b.getName())).collect(Collectors.toList());
  }

  public int year(Document doc)
  {
    if (doc.getDate() != null)
    {
      return LocalDate.ofInstant(doc.getDate().toInstant(), ZoneId.systemDefault()).getYear();
    }
    return LocalDate.now().getYear();
  }

  protected Attribute attribute(NamedElement o, QualifiedName id)
  {
    return o.getMetadatum().getMetadata().stream()
            .filter(it -> it.getType() != null
                    && id.equals(qualifiedNameProvider.getFullyQualifiedName(it.getType())))
            .findFirst().orElse(null);
  }

  public Expression attributeValue(NamedElement o, QualifiedName id)
  {
    final var attribute = attribute(o, id);
    if (attribute == null)
    {
      return null;
    }
    if (attribute.getValue() == null)
    {
      return ((AttributeType) attribute.getType()).getDefault();
    }
    return attribute.getValue();
  }

  protected Optional<Boolean> attributeBoolValue(NamedElement o, QualifiedName id)
  {
    return getBoolean(attributeValue(o, id));
  }

  protected boolean attributeBoolValue(NamedElement o, QualifiedName id, boolean defaultValue)
  {
    return attributeBoolValue(o, id).orElse(defaultValue);
  }

  protected boolean attributeBoolValue(NamedElement o, QualifiedName id,
          Supplier<Boolean> defaultValue)
  {
    return attributeBoolValue(o, id).orElseGet(defaultValue);
  }

  public boolean isStatic(IEObjectDescription o)
  {
    final var obj = o.getEObjectOrProxy();
    if (obj.eIsProxy())
    {
      return Boolean.parseBoolean(o.getUserData("static"));
    }
    if (obj instanceof NamedElement)
    {
      return isStatic((NamedElement) obj);
    }
    return false;
  }

  public OperatorKind getOperatorKind(NamedElement o)
  {
    final var id = QualifiedNames.Attributes_Operator;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> operatorKinds
            .getOrDefault(fqn(getEnumerationLiteral(attributeValue(o, id))), OperatorKind.NONE));
  }

  public boolean isStatic(NamedElement o)
  {
    final var id = QualifiedNames.Attributes_Static;
    return cache.get(Tuples.pair(o, id), o.eResource(),
            () -> !isConstructor(o) && attributeBoolValue(o, id, false));
  }

  public boolean isConst(NamedElement o)
  {
    final var id = QualifiedNames.Attributes_Const;
    return cache.get(Tuples.pair(o, id), o.eResource(),
            () -> !isConstructor(o) && attributeBoolValue(o, id, false));
  }

  public boolean isMutable(NamedElement o)
  {
    final var id = QualifiedNames.Attributes_Mutable;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id, false));
  }

  public boolean isAbstract(NamedElement o)
  {
    final var id = QualifiedNames.Attributes_Abstract;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> !isConstructor(o) && !isStatic(o)
            && (o.eContainer() instanceof Interface || attributeBoolValue(o, id, false)));
  }

  public boolean isVirtual(NamedElement o)
  {
    // if the element is abstract then it is virtual too
    final var id = QualifiedNames.Attributes_Virtual;
    return cache.get(Tuples.pair(o, id), o.eResource(),
            () -> !isConstructor(o) && (isAbstract(o) || attributeBoolValue(o, id,
                    () -> o.eContainer() instanceof ReferenceType && !isStatic(o))));
  }

  public boolean isConstructor(NamedElement o)
  {
    final var id = QualifiedNames.Attributes_Constructor;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id, false));
  }

  public boolean isByPointer(Property o)
  {
    final var id = QualifiedNames.Attributes_ByPointer;
    return cache.get(Tuples.pair(o, id), o.eResource(),
            () -> attributeBoolValue(o, id, () -> o.getType() instanceof ReferenceType));
  }

  public boolean isByPointer(Association o)
  {
    final var id = QualifiedNames.Attributes_ByPointer;
    return cache.get(Tuples.pair(o, id), o.eResource(),
            () -> attributeBoolValue(o, id, () -> o.getType() instanceof ReferenceType));
  }

  public boolean isByPointer(Parameter o)
  {
    final var id = QualifiedNames.Attributes_ByPointer;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id,
            () -> kind(o.getDirection(), o.getType()) == ArgKind.BY_PTR));
  }

  public boolean isByReference(Property o)
  {
    final var id = QualifiedNames.Attributes_ByReference;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id, false));
  }

  public boolean isByReference(Parameter o)
  {
    final var id = QualifiedNames.Attributes_ByReference;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id,
            () -> kind(o.getDirection(), o.getType()) == ArgKind.BY_REF));

  }

  public boolean isForcible(Field o)
  {
    final var id = QualifiedNames.Attributes_Forcible;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id, false));
  }

  public boolean isFailure(Field o)
  {
    final var id = QualifiedNames.Attributes_Failure;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id, false));
  }

  public boolean isConstGetter(Property o)
  {
    final var id = QualifiedNames.Attributes_ConstGetter;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id, false));
  }

  public boolean isNoConstructor(Type o)
  {
    final var id = QualifiedNames.Attributes_NoConstructor;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id, false));
  }

  public boolean isNoDestructor(Type o)
  {
    final var id = QualifiedNames.Attributes_NoDestructor;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id, false));
  }

  public boolean isSimpleArray(Array o)
  {
    final var id = QualifiedNames.Attributes_SimpleArray;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id, false));
  }

  public boolean isConst(Parameter o)
  {
    final var id = QualifiedNames.Attributes_Const;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id, () -> {
      switch (o.getDirection())
      {
        case IN:
          return !(o.getType() instanceof ValueType);
        case RETURN:
        case OUT:
        case INOUT:
        default:
          return false;
      }
    }));

  }

  protected ArgKind kind(ParameterDirectionKind direction, Type type)
  {
    if (type instanceof ReferenceType)
    {
      switch (direction)
      {
        case IN:
          return ArgKind.BY_REF;
        case INOUT:
        case OUT:
        case RETURN:
          return ArgKind.BY_PTR;
        default:
          throw new UnsupportedOperationException();
      }
    }

    if (type instanceof NativeType || type instanceof ValueType)
    {
      switch (direction)
      {
        case IN:
        case RETURN:
          return ArgKind.BY_VALUE;
        case INOUT:
        case OUT:
          return ArgKind.BY_PTR;
        default:
          throw new UnsupportedOperationException();
      }
    }
    return ArgKind.BY_VALUE;
  }

  public enum ArgKind
  {
    BY_VALUE, BY_PTR, BY_REF
  }

  // get all public & non-static fields
  public List<Field> getAssignableFields(Structure structure)
  {

    return cache.get(Tuples.pair(structure, "assignableFields"), structure.eResource(), () -> {
      final var fields = structure.getMember().stream().filter(Field.class::isInstance)
              .map(Field.class::cast)
              .filter(it -> getVisibility(it) == VisibilityKind.PUBLIC && !isStatic(it))
              .collect(Collectors.toList());
      if (structure instanceof org.eclipse.xsmp.xcatalogue.Class)
      {
        final var clazz = (org.eclipse.xsmp.xcatalogue.Class) structure;
        final var base = clazz.getBase();
        if (base instanceof Structure && !isBaseOf(base, clazz))
        {
          fields.addAll(0, getAssignableFields((Structure) base));
        }
      }
      return fields;
    });

  }

  // get all non-static fields
  public Iterable<Field> getFields(Structure structure)
  {

    final var fields = Iterables.filter(Iterables.filter(structure.getMember(), Field.class),
            it -> !isStatic(it));

    if (structure instanceof org.eclipse.xsmp.xcatalogue.Class)
    {
      final var clazz = (org.eclipse.xsmp.xcatalogue.Class) structure;
      final var base = clazz.getBase();
      if (base instanceof Structure && !isBaseOf(base, clazz))
      {
        return Iterables.concat(getFields((Structure) base), fields);
      }
    }
    return fields;

  }

  /**
   * Check that a type is not recursive.
   * A type is recursive if one of its sub element type is the root type.
   *
   * @param type
   *          the root type
   * @param visited
   *          the visited type
   * @return true if the type is recursive
   */
  public boolean isRecursiveType(Type type, Type visited)
  {
    if (type == visited)
    {
      return true;
    }

    if (visited instanceof Array)
    {
      return isRecursiveType(type, ((Array) visited).getItemType());
    }
    if (visited instanceof Structure)
    {
      for (final var field : getFields((Structure) visited))
      {
        if (isRecursiveType(type, field.getType()))
        {
          return true;
        }
      }
    }

    return false;
  }

  protected boolean computeIsInvokable(Operation op)
  {
    if (op.getReturnParameter() != null
            && !(op.getReturnParameter().getType() instanceof SimpleType))
    {
      return false;
    }
    for (final var param : op.getParameter())
    {
      if (!(param.getType() instanceof ValueType))
      {
        return false;
      }
    }
    return true;
  }

  public boolean isInvokable(Operation op)
  {
    return cache.get(Tuples.pair(op, "isInvokable"), op.eResource(), () -> computeIsInvokable(op));
  }

  private Type findType(EObject e, QualifiedName name)
  {

    final var resource = e.eResource();
    if (resource != null)
    {
      final var rs = resource.getResourceSet();
      if (rs != null)
      {
        final var it = resourceDescriptionProvider.getResourceDescriptions(rs)
                .getExportedObjects(XcataloguePackage.Literals.PRIMITIVE_TYPE, name, false)
                .iterator();
        if (it.hasNext())
        {
          return (Type) it.next().getEObjectOrProxy();
        }
      }

    }
    return null;
  }

  private Type findType(EObject parent)
  {

    switch (parent.eClass().getClassifierID())
    {
      case XcataloguePackage.FIELD:
        return ((Field) parent).getType();
      case XcataloguePackage.CONSTANT:
        return ((Constant) parent).getType();
      case XcataloguePackage.ASSOCIATION:
        return ((Association) parent).getType();
      case XcataloguePackage.PARAMETER:
        return ((Parameter) parent).getType();
      case XcataloguePackage.STRING:
      case XcataloguePackage.ARRAY:
      case XcataloguePackage.MULTIPLICITY:
        return findType(parent, QualifiedNames.Smp_Int64);
      case XcataloguePackage.FLOAT:
      {
        final var type = ((Float) parent).getPrimitiveType();
        return type != null ? type : findType(parent, QualifiedNames.Smp_Float64);
      }
      case XcataloguePackage.INTEGER:
      {
        final var type = ((org.eclipse.xsmp.xcatalogue.Integer) parent).getPrimitiveType();
        return type != null ? type : findType(parent, QualifiedNames.Smp_Int32);
      }
      case XcataloguePackage.ENUMERATION_LITERAL:
        return findType(parent, QualifiedNames.Smp_Int32);
      case XcataloguePackage.ENUMERATION:
        return (Type) parent;
      case XcataloguePackage.ATTRIBUTE:
      {
        final var type = ((Attribute) parent).getType();
        return type instanceof AttributeType ? ((AttributeType) type).getType() : type;
      }
      case XcataloguePackage.ATTRIBUTE_TYPE:
        return ((AttributeType) parent).getType();
      case XcataloguePackage.COLLECTION_LITERAL:
      {
        final var collection = (CollectionLiteral) parent;
        return getType(collection);
      }
      default:
        return parent instanceof Expression ? getType((Expression) parent) : null;
    }
  }

  private Type findType(Expression e)
  {
    final var parent = e.eContainer();

    final var type = getType(parent);

    if (parent instanceof CollectionLiteral)
    {
      if (type instanceof Array)
      {

        final var collection = (CollectionLiteral) parent;
        final var index = collection.getElements().indexOf(e);
        final var array = (Array) type;
        final var size = getInteger(array.getSize());

        if (size != null && size.compareTo(BigInteger.valueOf(index)) > 0)
        {
          return ((Array) type).getItemType();
        }
        return null;
      }
      if (type instanceof Structure)
      {
        final var collection = (CollectionLiteral) parent;
        final var fields = getAssignableFields((Structure) type);
        final var index = collection.getElements().indexOf(e);
        if (index >= 0 && index < fields.size())
        {
          return fields.get(index).getType();
        }
        return null;
      }
    }
    return type;
  }

  public Type getType(EObject e)
  {
    if (e != null)
    {
      return cache.get(Tuples.pair(e, "Type"), e.eResource(), () -> findType(e));
    }
    return null;
  }

  public Type getType(Expression e)
  {
    if (e != null)
    {
      return cache.get(Tuples.pair(e, "Type"), e.eResource(), () -> findType(e));
    }
    return null;
  }

  private Field findField(Expression e)
  {
    final var parent = e.eContainer();
    switch (parent.eClass().getClassifierID())
    {
      case XcataloguePackage.FIELD:
        return (Field) parent;

      case XcataloguePackage.COLLECTION_LITERAL:
      {
        final var collection = (CollectionLiteral) parent;
        final var type = getType(collection);

        if (type instanceof Structure)
        {
          final var fields = getAssignableFields((Structure) type);
          final var index = collection.getElements().indexOf(e);
          if (index >= 0 && index < fields.size())
          {
            return fields.get(index);
          }
        }
        return null;
      }
      default:
        return null;
    }
  }

  public Field getField(Expression e)
  {
    return cache.get(Tuples.pair(e, "Field"), e.eResource(), () -> findField(e));
  }

  public Field getField(INode node)
  {
    Expression last = null;
    final var parent = node.getParent();
    for (final var n : parent.getAsTreeIterable())
    {
      if (n instanceof CompositeNodeWithSemanticElement)
      {
        final var elem = ((CompositeNodeWithSemanticElement) n).getSemanticElement();
        if (elem instanceof Expression)
        {
          last = (Expression) elem;
        }
      }
      if (node == n && last != null)
      {
        return getField(last);
      }

    }
    return null;
  }

  public Type getType(INode node)
  {
    Expression last = null;
    final var parent = node.getParent();
    for (final var n : parent.getAsTreeIterable())
    {
      if (n instanceof CompositeNodeWithSemanticElement)
      {
        final var elem = ((CompositeNodeWithSemanticElement) n).getSemanticElement();
        if (elem instanceof Expression)
        {
          last = (Expression) elem;
        }
      }

      if (node == n)
      {
        if (last == null)
        {
          return getType(node.getSemanticElement());
        }
        return getType(last);
      }

    }
    return null;
  }

  public BigInteger getInteger(Expression e)
  {
    try
    {
      return solver.getInteger(e);
    }
    catch (final Exception ex)
    {
      // ignore
      return null;
    }
  }

  public BigDecimal getDecimal(Expression e)
  {
    try
    {
      return solver.getDecimal(e);
    }
    catch (final Exception ex)
    {
      // ignore
      return null;
    }
  }

  public EnumerationLiteral getEnumerationLiteral(Expression e)
  {
    final var type = getType(e);
    if (type instanceof Enumeration)
    {
      try
      {
        return solver.getEnumerationLiteral(e, (Enumeration) type);
      }
      catch (final Exception ex)
      {
        // ignore
      }
    }
    return null;
  }

  public BigInteger getEnumValue(Expression e)
  {
    try
    {
      return solver.getEnumValue(e);
    }
    catch (final Exception ex)
    {
      // ignore
      return null;
    }
  }

  public Optional<Boolean> getBoolean(Expression e)
  {
    try
    {
      return solver.getBoolean(e);
    }
    catch (final Exception ex)
    {
      // ignore
      return Optional.empty();
    }
  }

  public String getString(Expression e)
  {
    try
    {
      return solver.getString(e);
    }
    catch (final Exception ex)
    {
      // ignore
      return null;
    }
  }

  public String getChar(Expression e)
  {
    try
    {
      return solver.getChar(e);
    }
    catch (final Exception ex)
    {
      // ignore
      return null;
    }
  }

  public BigInteger getDuration(Expression e)
  {
    try
    {
      return solver.getDuration(e);
    }
    catch (final Exception ex)
    {
      // ignore
      return null;
    }
  }

  public BigInteger getDateTime(Expression e)
  {
    try
    {
      return solver.getDateTime(e);
    }
    catch (final Exception ex)
    {
      // ignore
      return null;
    }
  }
}
