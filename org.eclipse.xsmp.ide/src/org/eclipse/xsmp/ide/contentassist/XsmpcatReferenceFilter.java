/*******************************************************************************
* Copyright (C) 2020-2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.contentassist;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.model.xsmp.AttributeType;
import org.eclipse.xsmp.model.xsmp.Expression;
import org.eclipse.xsmp.model.xsmp.Field;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.VisibilityKind;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xsmp.util.PrimitiveTypeKind;
import org.eclipse.xsmp.util.QualifiedNames;
import org.eclipse.xsmp.util.TypeReferenceConverter;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpcatReferenceFilter implements IReferenceFilter
{

  @Inject
  protected XsmpUtil xsmpUtil;

  private final Map<EReference, Function<EObject, Predicate<IEObjectDescription>>> builders = ImmutableMap
          .<EReference, Function<EObject, Predicate<IEObjectDescription>>> builder()
          // add the entries
          .put(XsmpPackage.Literals.CLASS__BASE,
                  model -> p -> isValidTypeReference(model, XsmpPackage.Literals.CLASS__BASE, p)
                          && !xsmpUtil.isBaseOf(model, p.getEObjectOrProxy()))
          .put(XsmpPackage.Literals.FLOAT__PRIMITIVE_TYPE,
                  model -> p -> floatingPointPrimitiveSet
                          .contains(xsmpUtil.getPrimitiveTypeKind(p)))
          .put(XsmpPackage.Literals.INTEGER__PRIMITIVE_TYPE,
                  model -> p -> integerPrimitiveSet.contains(xsmpUtil.getPrimitiveTypeKind(p)))
          .put(XsmpPackage.Literals.INTERFACE__BASE,
                  model -> p -> isValidTypeReference(model, XsmpPackage.Literals.INTERFACE__BASE, p)
                          && !xsmpUtil.isBaseOf(model, p.getEObjectOrProxy()))
          .put(XsmpPackage.Literals.EVENT_TYPE__EVENT_ARGS,
                  model -> p -> isValidTypeReference(model,
                          XsmpPackage.Literals.EVENT_TYPE__EVENT_ARGS, p))
          .put(XsmpPackage.Literals.EVENT_SINK__TYPE,
                  model -> p -> isValidTypeReference(model, XsmpPackage.Literals.EVENT_SINK__TYPE,
                          p))
          .put(XsmpPackage.Literals.ARRAY__ITEM_TYPE,
                  model -> p -> isValidTypeReference(model, XsmpPackage.Literals.ARRAY__ITEM_TYPE,
                          p))
          .put(XsmpPackage.Literals.EVENT_SOURCE__TYPE,
                  model -> p -> isValidTypeReference(model, XsmpPackage.Literals.EVENT_SOURCE__TYPE,
                          p))
          .put(XsmpPackage.Literals.VALUE_REFERENCE__TYPE,
                  model -> p -> isValidTypeReference(model,
                          XsmpPackage.Literals.VALUE_REFERENCE__TYPE, p))
          .put(XsmpPackage.Literals.FIELD__TYPE,
                  model -> p -> PrimitiveTypeKind.STRING8 != xsmpUtil.getPrimitiveTypeKind(p)
                          && isValidTypeReference(model, XsmpPackage.Literals.FIELD__TYPE, p))
          .put(XsmpPackage.Literals.ENTRY_POINT__INPUT,
                  model -> p -> isValidTypeReference(model, XsmpPackage.Literals.ENTRY_POINT__INPUT,
                          p) && ((Field) p.getEObjectOrProxy()).isInput())
          .put(XsmpPackage.Literals.ENTRY_POINT__OUTPUT,
                  model -> p -> isValidTypeReference(model,
                          XsmpPackage.Literals.ENTRY_POINT__OUTPUT, p)
                          && ((Field) p.getEObjectOrProxy()).isOutput())
          .put(XsmpPackage.Literals.ATTRIBUTE_TYPE__TYPE,
                  model -> p -> isValidTypeReference(model,
                          XsmpPackage.Literals.ATTRIBUTE_TYPE__TYPE, p))
          .put(XsmpPackage.Literals.CONSTANT__TYPE,
                  model -> p -> isValidTypeReference(model, XsmpPackage.Literals.CONSTANT__TYPE, p))
          .put(XsmpPackage.Literals.ASSOCIATION__TYPE,
                  model -> p -> isValidTypeReference(model, XsmpPackage.Literals.ASSOCIATION__TYPE,
                          p))
          .put(XsmpPackage.Literals.CONTAINER__TYPE,
                  model -> p -> isValidTypeReference(model, XsmpPackage.Literals.CONTAINER__TYPE,
                          p))
          .put(XsmpPackage.Literals.CONTAINER__DEFAULT_COMPONENT,
                  model -> p -> isValidTypeReference(model,
                          XsmpPackage.Literals.CONTAINER__DEFAULT_COMPONENT, p))
          .put(XsmpPackage.Literals.OPERATION__RAISED_EXCEPTION,
                  model -> p -> isValidTypeReference(model,
                          XsmpPackage.Literals.OPERATION__RAISED_EXCEPTION, p))
          .put(XsmpPackage.Literals.PARAMETER__TYPE,
                  model -> p -> isValidTypeReference(model, XsmpPackage.Literals.PARAMETER__TYPE,
                          p))
          .put(XsmpPackage.Literals.PROPERTY__TYPE,
                  model -> p -> isValidTypeReference(model, XsmpPackage.Literals.PROPERTY__TYPE, p))
          .put(XsmpPackage.Literals.PROPERTY__ATTACHED_FIELD,
                  model -> p -> isValidTypeReference(model,
                          XsmpPackage.Literals.PROPERTY__ATTACHED_FIELD, p)) // TODO filter
                                                                             // compatible
                                                                             // types
          .put(XsmpPackage.Literals.PROPERTY__GET_RAISES,
                  model -> p -> isValidTypeReference(model,
                          XsmpPackage.Literals.PROPERTY__GET_RAISES, p))
          .put(XsmpPackage.Literals.PROPERTY__SET_RAISES,
                  model -> p -> isValidTypeReference(model,
                          XsmpPackage.Literals.PROPERTY__SET_RAISES, p))
          .put(XsmpPackage.Literals.REFERENCE__INTERFACE,
                  model -> p -> isValidTypeReference(model,
                          XsmpPackage.Literals.REFERENCE__INTERFACE, p))
          .put(XsmpPackage.Literals.COMPONENT__INTERFACE,
                  model -> p -> isValidTypeReference(model,
                          XsmpPackage.Literals.COMPONENT__INTERFACE, p))
          .put(XsmpPackage.Literals.COMPONENT__BASE,
                  model -> p -> isValidTypeReference(model, XsmpPackage.Literals.COMPONENT__BASE, p)
                          && !xsmpUtil.isBaseOf(model, p.getEObjectOrProxy()))
          .put(XsmpPackage.Literals.ATTRIBUTE__TYPE, model -> p -> {
            final var elem = EcoreUtil2.getContainerOfType(model, NamedElement.class);

            if (elem != null
                    && isValidTypeReference(model, XsmpPackage.Literals.ATTRIBUTE__TYPE, p))
            {
              final var elemUsages = Stream
                      .concat(Stream.of(elem.eClass().getName()),
                              elem.eClass().getEAllSuperTypes().stream().map(EClass::getName))
                      .collect(Collectors.toList());

              // filter allow multiple
              if (!allowMuliple(p) && elem.getMetadatum().getMetadata().stream().anyMatch(a -> {
                final var type = a.getType();
                return type != null && xsmpUtil.fqn(type).equals(p.getQualifiedName());
              }))
              {
                return false;
              }

              return getUsages(p).anyMatch(elemUsages::contains);
            }
            return false;
          })
          .put(XsmpPackage.Literals.DESIGNATED_INITIALIZER__FIELD,
                  model -> p -> xsmpUtil.getField((Expression) model) == EcoreUtil
                          .resolve(p.getEObjectOrProxy(), model))
          .put(XsmpPackage.Literals.NAMED_ELEMENT_REFERENCE__VALUE,
                  model -> p -> EcoreUtil2.getContainerOfType(model, NamedElement.class) != p
                          .getEObjectOrProxy())
          // build the map
          .build();

  private static final Set<PrimitiveTypeKind> floatingPointPrimitiveSet = ImmutableSet
          .<PrimitiveTypeKind> builder().add(PrimitiveTypeKind.FLOAT32, PrimitiveTypeKind.FLOAT64)
          .build();

  private static final Set<PrimitiveTypeKind> integerPrimitiveSet = ImmutableSet
          .<PrimitiveTypeKind> builder()
          .add(PrimitiveTypeKind.INT8, PrimitiveTypeKind.INT16, PrimitiveTypeKind.INT32,
                  PrimitiveTypeKind.INT64, PrimitiveTypeKind.UINT8, PrimitiveTypeKind.UINT16,
                  PrimitiveTypeKind.UINT32, PrimitiveTypeKind.UINT64)
          .build();

  @Inject
  private TypeReferenceConverter typeReferenceConverter;

  protected Stream<String> getUsages(IEObjectDescription d)
  {
    final var obj = d.getEObjectOrProxy();

    if (obj instanceof AttributeType)
    {
      if (obj.eIsProxy())
      {
        return Arrays.stream(d.getUserData("usage").split(" "));
      }
      return ((AttributeType) obj).getUsage().stream();
    }
    return Stream.of();

  }

  protected boolean isValidDesignatedField(IEObjectDescription d)
  {
    return xsmpUtil.getVisibility(d) == VisibilityKind.PUBLIC && !xsmpUtil.isStatic(d);
  }

  protected boolean allowMuliple(IEObjectDescription d)
  {
    final var obj = d.getEObjectOrProxy();

    if (obj instanceof AttributeType)
    {
      if (obj.eIsProxy())
      {
        return Boolean.parseBoolean(d.getUserData("allowMultiple"));
      }
      return ((AttributeType) obj).isAllowMultiple();
    }
    return false;
  }

  protected boolean isValidTypeReference(EObject eObject, EReference reference,
          IEObjectDescription p)
  {
    if (!typeReferenceConverter.convert(reference).isInstance(p.getEObjectOrProxy())
            || !xsmpUtil.isVisibleFrom(p, eObject))
    {
      return false;
    }
    final var fqn = p.getQualifiedName();
    if (fqn.equals(QualifiedNames.Attributes_FieldUpdateKind)
            || fqn.equals(QualifiedNames.Attributes_OperatorKind))
    {
      return false;
    }

    if (reference.isMany())
    {
      final var values = (List< ? >) eObject.eGet(reference);
      if (values.contains(p.getEObjectOrProxy()))
      {
        return false;
      }
    }
    return true;
  }

  @Override
  public Predicate<IEObjectDescription> getFilter(EObject model, EReference reference)
  {

    final var filterBuilder = builders.get(reference);
    if (filterBuilder != null)
    {
      return filterBuilder.apply(model);
    }
    return Predicates.alwaysTrue();
  }
}
