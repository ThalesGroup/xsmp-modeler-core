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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Document;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Interface;
import org.eclipse.xsmp.xcatalogue.ItemWithBase;
import org.eclipse.xsmp.xcatalogue.NamedElement;
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
import org.eclipse.xsmp.xcatalogue.ValueReference;
import org.eclipse.xsmp.xcatalogue.ValueType;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.VisibilityKind;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
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
  private IQualifiedNameConverter qualifiedNameConverter;

  @Inject
  protected IResourceScopeCache cache;

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

  public static PrimitiveTypeKind getPrimitiveType(IEObjectDescription d)
  {
    return getPrimitiveType(d.getQualifiedName());
  }

  public static PrimitiveTypeKind getPrimitiveType(QualifiedName qfn)
  {
    return primitiveTypeKinds.getOrDefault(qfn, null);
  }

  public static EObject getRootBase(ItemWithBase elem)
  {

    final Set<ItemWithBase> visited = new HashSet<>();

    while (visited.add(elem))
    {
      final var base = elem.getBase();

      if (!(base instanceof ItemWithBase))
      {
        return base == null ? elem : base;
      }
      elem = (ItemWithBase) base;
    }

    return elem;
  }

  public QualifiedName getRootBase(IEObjectDescription o)
  {

    final var obj = o.getEObjectOrProxy();
    if (obj.eIsProxy())
    {

      final var base = o.getUserData("rootBase");
      if (base != null)
      {
        return qualifiedNameConverter.toQualifiedName(base);
      }
    }
    else if (obj instanceof ItemWithBase)
    {
      final var base = getRootBase((ItemWithBase) obj);

      return qualifiedNameProvider.getFullyQualifiedName(base);
    }
    return o.getQualifiedName();
  }

  public static VisibilityKind getVisibility(IEObjectDescription o)
  {

    final var obj = o.getEObjectOrProxy();
    if (obj.eIsProxy())
    {
      try
      {
        return VisibilityKind.valueOf(o.getUserData("visibility"));
      }
      catch (final Exception e)
      {
        // ignore
      }
    }
    else if (obj instanceof VisibilityElement)
    {
      return ((VisibilityElement) obj).getRealVisibility();
    }
    return VisibilityKind.PUBLIC;
  }

  public static boolean isDeprecated(IEObjectDescription o)
  {

    final var obj = o.getEObjectOrProxy();
    if (obj.eIsProxy())
    {
      try
      {
        return Boolean.parseBoolean(o.getUserData("deprecated"));
      }
      catch (final Exception e)
      {
        // ignore
      }
    }
    else if (obj instanceof NamedElement)
    {
      return ((NamedElement) obj).isDeprecated();
    }
    return false;
  }

  public static boolean isRecursive(Interface elem, EObject base)
  {
    if (base instanceof Interface)
    {
      final Set<EObject> visited = new HashSet<>();
      visited.add(base);

      visitAllBases((Interface) base, visited);
      return visited.contains(elem);

    }
    return false;
  }

  public static boolean isRecursive(ItemWithBase elem, EObject base)
  {
    if (base instanceof ItemWithBase)
    {
      final Set<EObject> visited = new HashSet<>();
      visited.add(elem);

      while (base instanceof ItemWithBase)
      {
        if (!visited.add(base))
        {
          return true;
        }
        base = ((ItemWithBase) base).getBase();
      }
    }
    return false;
  }

  public static VisibilityKind getMinVisibility(Type type, EObject from)
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

  public static boolean isVisibleFrom(Type type, EObject from)
  {
    if (type != null && !type.eIsProxy())
    {

      return doIsVisibleFrom(type, from);

    }
    return true;
  }

  private static boolean doIsVisibleFrom(Type type, EObject from)
  {

    return (type.getRealVisibility() != VisibilityKind.PRIVATE
            || EcoreUtil.isAncestor(type.eContainer(), from))
            && (type.getRealVisibility() != VisibilityKind.PROTECTED
                    || from.eResource() == type.eResource());
  }

  public static boolean isVisibleFrom(Field field, EObject from)
  {
    final var elemContainer = EcoreUtil2.getContainerOfType(from, Type.class);

    final var fieldContainer = EcoreUtil2.getContainerOfType(field, Type.class);

    return field.getRealVisibility() != VisibilityKind.PRIVATE || elemContainer == fieldContainer;

  }

  public static VisibilityKind getMinVisibility(Field field, EObject from)
  {
    if (field != null && !field.eIsProxy() && (field.getRealVisibility() != VisibilityKind.PRIVATE
            || !EcoreUtil.isAncestor(field.eContainer(), from)))
    {
      return VisibilityKind.PROTECTED;
    }

    return VisibilityKind.PRIVATE;
  }

  public static boolean isVisibleFrom(IEObjectDescription p, EObject from)
  {
    final var obj = p.getEObjectOrProxy();
    if (!obj.eIsProxy())
    {
      if (obj instanceof Type)
      {
        return doIsVisibleFrom((Type) obj, from);
      }
      if (obj instanceof Field)
      {
        return isVisibleFrom((Field) obj, from);
      }
    }

    return "public".equals(p.getUserData("visibility"));

  }

  public static Type resolve(Type type)
  {
    if (type instanceof ValueReference)
    {
      return resolve(((ValueReference) type).getType());
    }
    return type;
  }

  private static void visitAllBases(Interface i, Set<EObject> visited)
  {

    for (final Type b : i.getBase())
    {
      if (visited.add(b) && b instanceof Interface)
      {
        visitAllBases((Interface) b, visited);
      }
    }

  }

  public PrimitiveTypeKind getPrimitiveType(Type type)
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
        return PrimitiveTypeKind.NONE;
    }

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

  public static boolean isSimpleType(Type t)
  {
    return t instanceof SimpleType;
  }

  public static boolean isSimpleArrayType(Type t)
  {
    return t instanceof Array && isSimpleType(((Array) t).getItemType());
  }

  public static boolean isArrayType(Type t)
  {
    return t instanceof Array && !isSimpleType(((Array) t).getItemType());
  }

  public static String getString(StringLiteral t)
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

  public static String getString(CharacterLiteral t)
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

  public static String getUnescapedChar(CharacterLiteral t)
  {
    return translateEscapes(getString(t));
  }

  public static String getUnescapedString(StringLiteral t)
  {
    return translateEscapes(getString(t));
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
  public static Collection<Catalogue> dependentPackages(Catalogue t)
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

  public static int year(Document doc)
  {
    if (doc.getDate() != null)
    {
      return LocalDate.ofInstant(doc.getDate().toInstant(), ZoneId.systemDefault()).getYear();
    }
    return LocalDate.now().getYear();
  }

  public Attribute attribute(NamedElement o, QualifiedName id)
  {
    return o.getMetadatum().getMetadata().stream()
            .filter(it -> it.getType() != null
                    && id.equals(qualifiedNameProvider.getFullyQualifiedName(it.getType())))
            .findFirst().orElse(null);
  }

  protected Expression attributeValue(NamedElement o, QualifiedName id)
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
    return cache.get(Tuples.pair(o, id), o.eResource(),
            () -> Solver.INSTANCE.getBoolean(attributeValue(o, id)));
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
      try
      {
        return Boolean.parseBoolean(o.getUserData("static"));
      }
      catch (final Exception e)
      {
        // ignore
      }
    }
    else if (obj instanceof NamedElement)
    {
      return isStatic((NamedElement) obj);
    }
    return false;
  }

  public boolean isStatic(NamedElement o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_Static, false);
  }

  public boolean isConst(NamedElement o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_Const, false);
  }

  public boolean isMutable(NamedElement o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_Mutable, false);
  }

  public boolean isByPointer(NamedElement o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_ByPointer, false);
  }

  public boolean isByReference(NamedElement o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_ByReference, false);
  }

  public boolean isAbstract(NamedElement o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_Abstract,
            () -> o.eContainer() instanceof Interface);
  }

  public boolean isVirtual(NamedElement o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_Virtual,
            () -> o.eContainer() instanceof ReferenceType);

  }

  public boolean isConstructor(Operation o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_Constructor, false);
  }

  public boolean isByPointer(Parameter o)
  {

    return kind(o) == ArgKind.BY_PTR;
  }

  public boolean isByReference(Parameter o)
  {

    return kind(o) == ArgKind.BY_REF;
  }

  public boolean isForcible(Field o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_Forcible, false);
  }

  public boolean isFailure(Field o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_Failure, false);
  }

  public boolean isConstGetter(Property o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_ConstGetter, false);
  }

  public boolean isNoConstructor(Type o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_NoConstructor, false);
  }

  public boolean isNoDestructor(Type o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_NoDestructor, false);
  }

  public boolean isSimpleArray(Array o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_SimpleArray, false);
  }

  public boolean isConst(Parameter o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_Const, () -> {
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
    });
  }

  public boolean isByPointer(Association o)
  {
    return attributeBoolValue(o, QualifiedNames.Attributes_ByPointer,
            () -> o.getType() instanceof ReferenceType);
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

  public ArgKind kind(Parameter o)
  {
    var kind = kind(o.getDirection(), o.getType());

    final var byPtr = attributeBoolValue(o, QualifiedNames.Attributes_ByPointer);

    if (byPtr.isPresent())
    {
      if (byPtr.get() == Boolean.TRUE)
      {
        kind = ArgKind.BY_PTR;
      }
      else if (kind == ArgKind.BY_PTR)
      {
        kind = ArgKind.BY_VALUE;
      }

    }
    final var byReference = attributeBoolValue(o, QualifiedNames.Attributes_ByReference);

    if (byReference.isPresent())
    {
      if (byReference.get() == Boolean.TRUE)
      {
        kind = ArgKind.BY_REF;
      }
      else if (kind == ArgKind.BY_REF)
      {
        kind = ArgKind.BY_VALUE;
      }
    }
    return kind;
  }

  public enum ArgKind
  {
    BY_VALUE, BY_PTR, BY_REF
  }

  public VisibilityKind getVisibility(EObject t)
  {
    if (t instanceof VisibilityElement)
    {
      return ((VisibilityElement) t).getRealVisibility();
    }
    return VisibilityKind.PUBLIC;
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
        if (clazz.getBase() instanceof Structure)
        {
          fields.addAll(0, getAssignableFields((Structure) clazz.getBase()));
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
      if (clazz.getBase() instanceof Structure)
      {
        return Iterables.concat(getFields((Structure) clazz.getBase()), fields);
      }
    }
    return fields;

  }

  public boolean isInvokable(Operation op)
  {
    return cache.get(Tuples.pair(op, "isInvokable"), op.eResource(), () -> {
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
    });

  }

  private Type findType(Expression e)
  {
    final var parent = e.eContainer();
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
        final var type = getType(collection);
        if (type instanceof Array)
        {
          return ((Array) type).getItemType();
        }
        if (type instanceof Structure)
        {
          final var fields = getAssignableFields((Structure) type);
          final var index = collection.getElements().indexOf(e);
          if (index >= 0 && index < fields.size())
          {
            return fields.get(index).getType();
          }
        }
        return null;
      }
      default:
        return parent instanceof Expression ? getType((Expression) parent) : null;
    }
  }

  public Type getType(Expression e)
  {
    return cache.get(Tuples.pair(e, "Type"), e.eResource(), () -> findType(e));
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
}
