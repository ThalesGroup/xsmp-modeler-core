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

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.model.xsmp.Array;
import org.eclipse.xsmp.model.xsmp.Association;
import org.eclipse.xsmp.model.xsmp.Attribute;
import org.eclipse.xsmp.model.xsmp.AttributeType;
import org.eclipse.xsmp.model.xsmp.Catalogue;
import org.eclipse.xsmp.model.xsmp.Class;
import org.eclipse.xsmp.model.xsmp.CollectionLiteral;
import org.eclipse.xsmp.model.xsmp.Component;
import org.eclipse.xsmp.model.xsmp.Constant;
import org.eclipse.xsmp.model.xsmp.Document;
import org.eclipse.xsmp.model.xsmp.Enumeration;
import org.eclipse.xsmp.model.xsmp.EnumerationLiteral;
import org.eclipse.xsmp.model.xsmp.Expression;
import org.eclipse.xsmp.model.xsmp.Field;
import org.eclipse.xsmp.model.xsmp.Float;
import org.eclipse.xsmp.model.xsmp.Interface;
import org.eclipse.xsmp.model.xsmp.ItemWithBase;
import org.eclipse.xsmp.model.xsmp.Model;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers;
import org.eclipse.xsmp.model.xsmp.NativeType;
import org.eclipse.xsmp.model.xsmp.Operation;
import org.eclipse.xsmp.model.xsmp.Parameter;
import org.eclipse.xsmp.model.xsmp.ParameterDirectionKind;
import org.eclipse.xsmp.model.xsmp.PrimitiveType;
import org.eclipse.xsmp.model.xsmp.Property;
import org.eclipse.xsmp.model.xsmp.ReferenceType;
import org.eclipse.xsmp.model.xsmp.Service;
import org.eclipse.xsmp.model.xsmp.SimpleType;
import org.eclipse.xsmp.model.xsmp.Structure;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.model.xsmp.ValueType;
import org.eclipse.xsmp.model.xsmp.VisibilityElement;
import org.eclipse.xsmp.model.xsmp.VisibilityKind;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
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

  public PrimitiveTypeKind getPrimitiveTypeKind(IEObjectDescription d)
  {
    return getPrimitiveTypeKind(d.getQualifiedName());
  }

  public PrimitiveTypeKind getPrimitiveTypeKind(QualifiedName qfn)
  {
    return primitiveTypeKinds.getOrDefault(qfn, PrimitiveTypeKind.NONE);
  }

  public PrimitiveTypeKind getPrimitiveTypeKind(Type type)
  {
    if (type != null)
    {
      switch (type.eClass().getClassifierID())
      {
        case XsmpPackage.PRIMITIVE_TYPE:
          return getPrimitiveTypeKind((PrimitiveType) type);
        case XsmpPackage.FLOAT:
          return getPrimitiveTypeKind((org.eclipse.xsmp.model.xsmp.Float) type);
        case XsmpPackage.INTEGER:
          return getPrimitiveTypeKind((org.eclipse.xsmp.model.xsmp.Integer) type);
        case XsmpPackage.ENUMERATION:
          return PrimitiveTypeKind.ENUM;
        case XsmpPackage.STRING:
          return PrimitiveTypeKind.STRING8;
        default:
          break;
      }
    }
    return PrimitiveTypeKind.NONE;
  }

  public PrimitiveTypeKind getPrimitiveTypeKind(PrimitiveType type)
  {
    return getPrimitiveTypeKind(fqn(type));
  }

  public PrimitiveTypeKind getPrimitiveTypeKind(org.eclipse.xsmp.model.xsmp.Float type)
  {

    final var primitiveType = type.getPrimitiveType();
    return primitiveType != null ? getPrimitiveTypeKind(primitiveType) : PrimitiveTypeKind.FLOAT64;

  }

  public PrimitiveTypeKind getPrimitiveTypeKind(org.eclipse.xsmp.model.xsmp.Integer type)
  {

    final var primitiveType = type.getPrimitiveType();
    return primitiveType != null ? getPrimitiveTypeKind(primitiveType) : PrimitiveTypeKind.INT32;

  }

  public VisibilityKind getVisibility(IEObjectDescription o)
  {

    final var obj = o.getEObjectOrProxy();
    if (obj.eIsProxy())
    {
      final var visibility = o.getUserData("visibility");

      // if the user data is not set, it means that the object is public
      return visibility != null ? VisibilityKind.getByName(visibility) : VisibilityKind.PUBLIC;
    }

    return getVisibility(obj);
  }

  public VisibilityKind getVisibility(EObject t)
  {
    if (t instanceof final VisibilityElement ve)
    {
      return ve.getRealVisibility();
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
    if (obj instanceof final NamedElement ne)
    {
      return ne.isDeprecated();
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
                .getExportedObjects(XsmpPackage.Literals.TYPE, base, false).iterator();
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
    return switch (derived.eClass().getClassifierID())
    {
      case XsmpPackage.INTERFACE -> isBaseOf(base, (Interface) derived);
      case XsmpPackage.MODEL -> isBaseOf(base, (Model) derived);
      case XsmpPackage.SERVICE -> isBaseOf(base, (Service) derived);
      case XsmpPackage.CLASS, XsmpPackage.EXCEPTION -> isBaseOf(base, (Class) derived);
      default -> false;
    };
  }

  protected boolean isBaseOf(EObject base, Interface derived)
  {
    return base == derived || derived.getBase().stream().anyMatch(b -> isBaseOf(base, b));
  }

  protected boolean isBaseOf(EObject base, Model derived)
  {
    return QualifiedNames.Smp_IModel.equals(fqn(base)) || isBaseOf(base, (Component) derived);
  }

  protected boolean isBaseOf(EObject base, Service derived)
  {
    return QualifiedNames.Smp_IService.equals(fqn(base)) || isBaseOf(base, (Component) derived);
  }

  protected boolean isBaseOf(EObject base, Component derived)
  {
    return base == derived || QualifiedNames.Smp_IComponent.equals(fqn(base))
            || derived.getBase() != null && isBaseOf(base, derived.getBase())
            || derived.getInterface().stream().anyMatch(b -> isBaseOf(base, b));
  }

  protected boolean isBaseOf(EObject base, Class derived)
  {
    return base == derived || derived.getBase() != null && isBaseOf(base, derived.getBase());
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

    if (container instanceof final ItemWithBase parent)
    {
      final var base = parent.getBase();
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
      if (obj instanceof final Type type)
      {
        return isVisibleFrom(type, from);
      }
      if (obj instanceof final VisibilityElement ve)
      {
        return isVisibleFrom(ve, from);
      }
      if (obj instanceof EnumerationLiteral)
      {
        return isVisibleFrom((Type) obj.eContainer(), from);
      }
    }
    return getVisibility(p) == VisibilityKind.PUBLIC;

  }

  /**
   * return the full qualified name of the element
   *
   * @param t
   *          the element
   * @return the QualifiedName
   */
  public QualifiedName fqn(EObject t)
  {
    return qualifiedNameProvider.getFullyQualifiedName(t);
  }

  public String escape(String s)
  {
    return s.replace("\t", "\\t").replace("\b", "\\b").replace("\n", "\\n").replace("\r", "\\r")
            .replace("\f", "\\f").replace("\'", "\\'").replace("\"", "\\\"");
  }

  public static String translateEscapes(String s) throws IllegalArgumentException
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
          case '\'', '\"', '\\':
            // as is
            break;
          case '?':
            ch = 0x3f;
            break;
          case '0', '1', '2', '3', '4', '5', '6', '7':
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
  public List<Catalogue> dependentPackages(Catalogue t)
  {
    final var dependencies = new HashSet<Catalogue>();
    // collect all referenced Catalogue of interest
    t.eAllContents().forEachRemaining(it -> {
      if (it instanceof final Field field)
      {
        dependencies.add(EcoreUtil2.getContainerOfType(field.getType(), Catalogue.class));
      }
      else if (it instanceof final Property property)
      {
        dependencies.add(EcoreUtil2.getContainerOfType(property.getType(), Catalogue.class));
      }
      else if (it instanceof final ItemWithBase item)
      {
        dependencies.add(EcoreUtil2.getContainerOfType(item.getBase(), Catalogue.class));
      }
      else if (it instanceof final Array array)
      {
        dependencies.add(EcoreUtil2.getContainerOfType(array.getItemType(), Catalogue.class));
      }

    });
    // current Catalogue is not a dependency
    dependencies.remove(t);
    final Comparator<String> c = Comparator.nullsFirst(String::compareTo);
    // Remove ecss_smp_smp and sort dependencies by name
    return dependencies.stream().filter(it -> it != null && !"ecss_smp_smp".equals(it.getName()))
            .sorted((a, b) -> c.compare(a.getName(), b.getName())).toList();
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
    final var v = attributeValue(o, id);
    if (v != null)
    {
      return Optional.of(getBoolean(v));
    }
    return Optional.empty();
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
    if (obj instanceof final NamedElement ne)
    {
      return isStatic(ne);
    }
    return false;
  }

  public OperatorKind getOperatorKind(NamedElement o)
  {
    final var id = QualifiedNames.Attributes_Operator;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> {
      final var attr = attributeValue(o, id);
      if (attr != null)
      {
        final var literal = getEnumerationLiteral(attr);
        if (literal != null)
        {
          return operatorKinds.getOrDefault(fqn(literal), OperatorKind.NONE);
        }
      }
      return OperatorKind.NONE;
    });
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
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id,
            () -> o.getType() instanceof ReferenceType && !isByReference(o)));
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
            () -> kind(o.getDirection(), o.getType()) == ArgKind.BY_PTR
                    && !attributeBoolValue(o, QualifiedNames.Attributes_ByReference, false)));
  }

  public boolean isByReference(Property o)
  {
    final var id = QualifiedNames.Attributes_ByReference;
    return cache.get(Tuples.pair(o, id), o.eResource(), () -> attributeBoolValue(o, id, false));
  }

  public boolean isByReference(Parameter o)
  {
    final var id = QualifiedNames.Attributes_ByReference;
    return cache.get(Tuples.pair(o, id), o.eResource(),
            () -> attributeBoolValue(o, id,
                    () -> kind(o.getDirection(), o.getType()) == ArgKind.BY_REF
                            && !attributeBoolValue(o, QualifiedNames.Attributes_ByPointer, false)));

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
    return cache.get(Tuples.pair(o, id), o.eResource(),
            () -> attributeBoolValue(o, id, () -> switch (o.getDirection())
            {
              case IN -> !(o.getType() instanceof ValueType);
              case RETURN, OUT, INOUT -> false;
              default -> false;
            }));

  }

  protected ArgKind kind(ParameterDirectionKind direction, Type type)
  {
    if (type instanceof ReferenceType)
    {
      return switch (direction)
      {
        case IN -> ArgKind.BY_REF;
        case INOUT, OUT, RETURN -> ArgKind.BY_PTR;
      };
    }

    if (type instanceof NativeType || type instanceof ValueType)
    {
      return switch (direction)
      {
        case IN, RETURN -> ArgKind.BY_VALUE;
        case INOUT, OUT -> ArgKind.BY_PTR;
      };
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
      if (structure instanceof final org.eclipse.xsmp.model.xsmp.Class clazz)
      {
        final var base = clazz.getBase();
        if (base instanceof final Structure struct && !isBaseOf(struct, clazz))
        {
          fields.addAll(0, getAssignableFields(struct));
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

    if (structure instanceof final org.eclipse.xsmp.model.xsmp.Class clazz)
    {
      final var base = clazz.getBase();
      if (base instanceof final Structure struct && !isBaseOf(struct, clazz))
      {
        return Iterables.concat(getFields(struct), fields);
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

    if (visited instanceof final Array array)
    {
      return isRecursiveType(type, array.getItemType());
    }
    if (visited instanceof final Structure struct)
    {
      for (final var field : getFields(struct))
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

  public boolean isInvokable(Property property)
  {
    return property.getType() instanceof SimpleType;
  }

  private Type findPrimitiveType(EObject e, QualifiedName name)
  {

    final var resource = e.eResource();
    if (resource != null)
    {
      final var rs = resource.getResourceSet();
      if (rs != null)
      {
        final var it = resourceDescriptionProvider.getResourceDescriptions(rs)
                .getExportedObjects(XsmpPackage.Literals.PRIMITIVE_TYPE, name, false).iterator();
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
    return switch (parent.eClass().getClassifierID())
    {
      case XsmpPackage.FIELD -> ((Field) parent).getType();
      case XsmpPackage.CONSTANT -> ((Constant) parent).getType();
      case XsmpPackage.ASSOCIATION -> ((Association) parent).getType();
      case XsmpPackage.PARAMETER -> ((Parameter) parent).getType();
      case XsmpPackage.STRING, XsmpPackage.ARRAY, XsmpPackage.MULTIPLICITY -> findPrimitiveType(
              parent, QualifiedNames.Smp_Int64);
      case XsmpPackage.FLOAT -> {
        final var type = ((Float) parent).getPrimitiveType();
        yield type != null ? type : findPrimitiveType(parent, QualifiedNames.Smp_Float64);
      }
      case XsmpPackage.INTEGER -> {
        final var type = ((org.eclipse.xsmp.model.xsmp.Integer) parent).getPrimitiveType();
        yield type != null ? type : findPrimitiveType(parent, QualifiedNames.Smp_Int32);
      }
      case XsmpPackage.ENUMERATION_LITERAL -> findPrimitiveType(parent, QualifiedNames.Smp_Int32);
      case XsmpPackage.ENUMERATION -> (Type) parent;
      case XsmpPackage.ATTRIBUTE -> {
        final var type = ((Attribute) parent).getType();
        yield type instanceof final AttributeType attr ? attr.getType() : type;
      }
      case XsmpPackage.ATTRIBUTE_TYPE -> ((AttributeType) parent).getType();
      case XsmpPackage.COLLECTION_LITERAL -> {
        final var collection = (CollectionLiteral) parent;
        yield getType(collection);
      }
      default -> parent instanceof final Expression expr ? getType(expr) : null;
    };
  }

  private Type findType(Expression e)
  {
    final var parent = e.eContainer();

    final var type = getType(parent);

    if (parent instanceof final CollectionLiteral collection)
    {
      if (type instanceof final Array array)
      {
        final var index = collection.getElements().indexOf(e);

        final var size = getInt64(array.getSize());

        if (size > index)
        {
          return ((Array) type).getItemType();
        }
        return null;
      }
      if (type instanceof final Structure struct)
      {
        final var fields = getAssignableFields(struct);
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

  private Type getType(EObject e)
  {
    if (e != null)
    {
      try
      {
        return cache.get(Tuples.pair(e, "Type"), e.eResource(), () -> findType(e));
      }
      catch (final Exception ex)
      {
        // ignore
      }
    }
    return null;
  }

  public Type getType(Expression e)
  {
    if (e != null)
    {
      try
      {
        return cache.get(Tuples.pair(e, "Type"), e.eResource(), () -> findType(e));
      }
      catch (final Exception ex)
      {
        // ignore
      }
    }
    return null;
  }

  private Field findField(Expression e)
  {
    final var parent = e.eContainer();
    switch (parent.eClass().getClassifierID())
    {
      case XsmpPackage.FIELD:
        return (Field) parent;

      case XsmpPackage.COLLECTION_LITERAL:
      {
        final var collection = (CollectionLiteral) parent;
        final var type = getType(collection);

        if (type instanceof final Structure struct)
        {
          final var fields = getAssignableFields(struct);
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
        final var elem = n.getSemanticElement();
        if (elem instanceof final Expression expr)
        {
          last = expr;
        }
      }
      if (node == n && last != null)
      {
        return getField(last);
      }

    }
    return null;
  }

  public Type getType(INode node, EObject context)
  {
    Expression last = null;
    final var parent = node.getParent();
    for (final var n : parent.getAsTreeIterable())
    {
      if (n instanceof CompositeNodeWithSemanticElement)
      {
        final var elem = n.getSemanticElement();
        if (elem instanceof final Expression expr)
        {
          last = expr;
        }
      }

      if (node == n)
      {
        if (last == null)
        {
          return getType(context);
        }
        return getType(last);
      }

    }
    return null;
  }

  public EnumerationLiteral getEnumerationLiteral(Expression e)
  {
    final var type = getType(e);
    if (type instanceof final Enumeration en)
    {
      return solver.getValue(e, en).getValue();
    }
    return null;
  }

  public boolean getBoolean(Expression e)
  {
    return solver.getValue(e).boolValue().getValue();
  }

  public String getString8(Expression e)
  {
    return solver.getValue(e).string8Value().getValue();
  }

  public long getDuration(Expression e)
  {
    return solver.getValue(e).durationValue().getValue();
  }

  public long getDateTime(Expression e)
  {
    return solver.getValue(e).dateTimeValue().getValue();
  }

  public double getFloat64(Expression e)
  {
    return solver.getValue(e).float64Value().getValue();
  }

  public float getFloat32(Expression e)
  {
    return solver.getValue(e).float32Value().getValue();
  }

  public BigInteger getUInt64(Expression e)
  {
    return solver.getValue(e).uint64Value().getValue();
  }

  public long getInt64(Expression e)
  {
    return solver.getValue(e).int64Value().getValue();
  }

  public long getUInt32(Expression e)
  {
    return solver.getValue(e).uint32Value().getValue();
  }

  public int getInt32(Expression e)
  {
    return solver.getValue(e).int32Value().getValue();
  }

  public int getUInt16(Expression e)
  {
    return solver.getValue(e).uint16Value().getValue();
  }

  public short getInt16(Expression e)
  {
    return solver.getValue(e).int16Value().getValue();
  }

  public short getUInt8(Expression e)
  {
    return solver.getValue(e).uint8Value().getValue();
  }

  public byte getInt8(Expression e)
  {
    return solver.getValue(e).int8Value().getValue();
  }

  public String getChar8(Expression e)
  {
    return solver.getValue(e).char8Value().getValue();
  }

  public org.eclipse.xsmp.util.PrimitiveType getValue(Expression e)
  {
    return solver.getValue(e);
  }

  public static String StringLiteralToString(String literal)
  {
    return literal.startsWith("u8") ? translateEscapes(literal.substring(3, literal.length() - 1))
            : translateEscapes(literal.substring(1, literal.length() - 1));
  }
}
