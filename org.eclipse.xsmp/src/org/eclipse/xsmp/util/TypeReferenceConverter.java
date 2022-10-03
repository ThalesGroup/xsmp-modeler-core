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

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Singleton;

/**
 * helper class that return the expected feature Type instead of the generic one used in grammar
 *
 * @author daveluy
 */
@Singleton
public class TypeReferenceConverter
{

  private static final Map<EReference, EClass> converters = ImmutableMap
          .<EReference, EClass> builder()
          // add the entries
          .put(XcataloguePackage.Literals.ARRAY__ITEM_TYPE, XcataloguePackage.Literals.VALUE_TYPE)
          .put(XcataloguePackage.Literals.ATTRIBUTE_TYPE__TYPE,
                  XcataloguePackage.Literals.VALUE_TYPE)
          .put(XcataloguePackage.Literals.ATTRIBUTE__TYPE,
                  XcataloguePackage.Literals.ATTRIBUTE_TYPE)
          .put(XcataloguePackage.Literals.CLASS__BASE, XcataloguePackage.Literals.CLASS)
          .put(XcataloguePackage.Literals.COMPONENT__BASE, XcataloguePackage.Literals.COMPONENT)
          .put(XcataloguePackage.Literals.COMPONENT__INTERFACE,
                  XcataloguePackage.Literals.INTERFACE)
          .put(XcataloguePackage.Literals.CONSTANT__TYPE, XcataloguePackage.Literals.SIMPLE_TYPE)
          .put(XcataloguePackage.Literals.ASSOCIATION__TYPE,
                  XcataloguePackage.Literals.LANGUAGE_TYPE)
          .put(XcataloguePackage.Literals.CONTAINER__TYPE,
                  XcataloguePackage.Literals.REFERENCE_TYPE)
          .put(XcataloguePackage.Literals.CONTAINER__DEFAULT_COMPONENT,
                  XcataloguePackage.Literals.COMPONENT)
          .put(XcataloguePackage.Literals.FIELD__TYPE, XcataloguePackage.Literals.VALUE_TYPE)
          .put(XcataloguePackage.Literals.FLOAT__PRIMITIVE_TYPE,
                  XcataloguePackage.Literals.PRIMITIVE_TYPE)
          .put(XcataloguePackage.Literals.INTEGER__PRIMITIVE_TYPE,
                  XcataloguePackage.Literals.PRIMITIVE_TYPE)
          .put(XcataloguePackage.Literals.INTERFACE__BASE, XcataloguePackage.Literals.INTERFACE)
          .put(XcataloguePackage.Literals.OPERATION__RAISED_EXCEPTION,
                  XcataloguePackage.Literals.EXCEPTION)
          .put(XcataloguePackage.Literals.PARAMETER__TYPE, XcataloguePackage.Literals.LANGUAGE_TYPE)
          .put(XcataloguePackage.Literals.PROPERTY__TYPE, XcataloguePackage.Literals.LANGUAGE_TYPE)
          .put(XcataloguePackage.Literals.PROPERTY__SET_RAISES,
                  XcataloguePackage.Literals.EXCEPTION)
          .put(XcataloguePackage.Literals.PROPERTY__GET_RAISES,
                  XcataloguePackage.Literals.EXCEPTION)
          .put(XcataloguePackage.Literals.REFERENCE__INTERFACE,
                  XcataloguePackage.Literals.INTERFACE)
          .put(XcataloguePackage.Literals.VALUE_REFERENCE__TYPE,
                  XcataloguePackage.Literals.VALUE_TYPE)
          .put(XcataloguePackage.Literals.EVENT_TYPE__EVENT_ARGS,
                  XcataloguePackage.Literals.SIMPLE_TYPE)
          .put(XcataloguePackage.Literals.EVENT_SINK__TYPE, XcataloguePackage.Literals.EVENT_TYPE)
          .put(XcataloguePackage.Literals.EVENT_SOURCE__TYPE, XcataloguePackage.Literals.EVENT_TYPE)
          // build the map
          .build();

  /**
   * @param feature
   * @return the feature type
   */
  public EClass convert(EReference feature)
  {
    return converters.getOrDefault(feature, feature.getEReferenceType());
  }

}
