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
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.xcatalogue.Array;
import org.eclipse.xsmp.xcatalogue.Catalogue;
import org.eclipse.xsmp.xcatalogue.Document;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Interface;
import org.eclipse.xsmp.xcatalogue.ItemWithBase;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.PrimitiveType;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.SimpleType;
import org.eclipse.xsmp.xcatalogue.StringLiteral;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.ValueReference;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.VisibilityKind;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.ImmutableMap;
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

  /**
   * Enumeration with SMP primitive type kinds
   */
  public enum PrimitiveTypeKind
  {
    BOOL, CHAR8, DATE_TIME, DURATION, FLOAT32, FLOAT64, INT8, INT16, INT32, INT64, STRING8, UINT8, UINT16, UINT32, UINT64;
  }

  private static final Map<QualifiedName, PrimitiveTypeKind> primitiveTypeKinds = ImmutableMap
          .<QualifiedName, PrimitiveTypeKind> builder()
          .put(QualifiedName.create("Smp", "Char8"), PrimitiveTypeKind.CHAR8)
          .put(QualifiedName.create("Smp", "String8"), PrimitiveTypeKind.STRING8)
          .put(QualifiedName.create("Smp", "Float32"), PrimitiveTypeKind.FLOAT32)
          .put(QualifiedName.create("Smp", "Float64"), PrimitiveTypeKind.FLOAT64)
          .put(QualifiedName.create("Smp", "Int8"), PrimitiveTypeKind.INT8)
          .put(QualifiedName.create("Smp", "UInt8"), PrimitiveTypeKind.UINT8)
          .put(QualifiedName.create("Smp", "Int16"), PrimitiveTypeKind.INT16)
          .put(QualifiedName.create("Smp", "UInt16"), PrimitiveTypeKind.UINT16)
          .put(QualifiedName.create("Smp", "Int32"), PrimitiveTypeKind.INT32)
          .put(QualifiedName.create("Smp", "UInt32"), PrimitiveTypeKind.UINT32)
          .put(QualifiedName.create("Smp", "Int64"), PrimitiveTypeKind.INT64)
          .put(QualifiedName.create("Smp", "UInt64"), PrimitiveTypeKind.UINT64)
          .put(QualifiedName.create("Smp", "Bool"), PrimitiveTypeKind.BOOL)
          .put(QualifiedName.create("Smp", "DateTime"), PrimitiveTypeKind.DATE_TIME)
          .put(QualifiedName.create("Smp", "Duration"), PrimitiveTypeKind.DURATION).build();

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
        return PrimitiveTypeKind.INT32;
      case XcataloguePackage.STRING:
        return PrimitiveTypeKind.STRING8;
      default:
        return null;
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

  public QualifiedName fqn(NamedElement t)
  {
    if (t != null)
    {
      final var qfn = qualifiedNameProvider.getFullyQualifiedName(t);
      if (qfn != null)
      {
        return qfn;
      }
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
      return doc.getDate().getYear();
    }
    return LocalDate.now().getYear();
  }
}
