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
import org.eclipse.xsmp.model.xsmp.XsmpPackage;

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
          .put(XsmpPackage.Literals.ARRAY__ITEM_TYPE, XsmpPackage.Literals.VALUE_TYPE)
          .put(XsmpPackage.Literals.ATTRIBUTE_TYPE__TYPE, XsmpPackage.Literals.VALUE_TYPE)
          .put(XsmpPackage.Literals.ATTRIBUTE__TYPE, XsmpPackage.Literals.ATTRIBUTE_TYPE)
          .put(XsmpPackage.Literals.CLASS__BASE, XsmpPackage.Literals.CLASS)
          .put(XsmpPackage.Literals.COMPONENT__BASE, XsmpPackage.Literals.COMPONENT)
          .put(XsmpPackage.Literals.COMPONENT__INTERFACE, XsmpPackage.Literals.INTERFACE)
          .put(XsmpPackage.Literals.CONSTANT__TYPE, XsmpPackage.Literals.SIMPLE_TYPE)
          .put(XsmpPackage.Literals.ASSOCIATION__TYPE, XsmpPackage.Literals.LANGUAGE_TYPE)
          .put(XsmpPackage.Literals.CONTAINER__TYPE, XsmpPackage.Literals.REFERENCE_TYPE)
          .put(XsmpPackage.Literals.CONTAINER__DEFAULT_COMPONENT, XsmpPackage.Literals.COMPONENT)
          .put(XsmpPackage.Literals.FIELD__TYPE, XsmpPackage.Literals.VALUE_TYPE)
          .put(XsmpPackage.Literals.FLOAT__PRIMITIVE_TYPE, XsmpPackage.Literals.PRIMITIVE_TYPE)
          .put(XsmpPackage.Literals.INTEGER__PRIMITIVE_TYPE, XsmpPackage.Literals.PRIMITIVE_TYPE)
          .put(XsmpPackage.Literals.INTERFACE__BASE, XsmpPackage.Literals.INTERFACE)
          .put(XsmpPackage.Literals.OPERATION__RAISED_EXCEPTION, XsmpPackage.Literals.EXCEPTION)
          .put(XsmpPackage.Literals.PARAMETER__TYPE, XsmpPackage.Literals.LANGUAGE_TYPE)
          .put(XsmpPackage.Literals.PROPERTY__TYPE, XsmpPackage.Literals.VALUE_TYPE)
          .put(XsmpPackage.Literals.PROPERTY__SET_RAISES, XsmpPackage.Literals.EXCEPTION)
          .put(XsmpPackage.Literals.PROPERTY__GET_RAISES, XsmpPackage.Literals.EXCEPTION)
          .put(XsmpPackage.Literals.REFERENCE__INTERFACE, XsmpPackage.Literals.INTERFACE)
          .put(XsmpPackage.Literals.VALUE_REFERENCE__TYPE, XsmpPackage.Literals.VALUE_TYPE)
          .put(XsmpPackage.Literals.EVENT_TYPE__EVENT_ARGS, XsmpPackage.Literals.SIMPLE_TYPE)
          .put(XsmpPackage.Literals.EVENT_SINK__TYPE, XsmpPackage.Literals.EVENT_TYPE)
          .put(XsmpPackage.Literals.EVENT_SOURCE__TYPE, XsmpPackage.Literals.EVENT_TYPE)
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
