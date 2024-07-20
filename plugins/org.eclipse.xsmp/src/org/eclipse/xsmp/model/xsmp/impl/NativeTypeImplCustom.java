/**
 * *******************************************************************************
 * * Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
 * *
 * * All rights reserved. This program and the accompanying materials
 * * are made available under the terms of the Eclipse Public License 2.0
 * * which accompanies this distribution, and is available at
 * * https://www.eclipse.org/legal/epl-2.0/
 * *
 * * SPDX-License-Identifier: EPL-2.0
 * ******************************************************************************
 */
package org.eclipse.xsmp.model.xsmp.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;

public class NativeTypeImplCustom extends NativeTypeImpl
{

  @Override
  public String getType()
  {
    return getFeature(XsmpPackage.Literals.NATIVE_TYPE__TYPE, TYPE_EDEFAULT);
  }

  @Override
  public void setType(String newType)
  {
    setFeature(XsmpPackage.Literals.NATIVE_TYPE__TYPE, newType);
  }

  @Override
  public String getNamespace()
  {
    return getFeature(XsmpPackage.Literals.NATIVE_TYPE__NAMESPACE, NAMESPACE_EDEFAULT);
  }

  @Override
  public void setNamespace(String newNamespace)
  {
    setFeature(XsmpPackage.Literals.NATIVE_TYPE__NAMESPACE, newNamespace);
  }

  @Override
  public String getLocation()
  {

    return getFeature(XsmpPackage.Literals.NATIVE_TYPE__LOCATION, LOCATION_EDEFAULT);
  }

  @Override
  public void setLocation(String newLocation)
  {

    setFeature(XsmpPackage.Literals.NATIVE_TYPE__LOCATION, newLocation);
  }

  @Override
  protected EAttribute getFeature(String name)
  {
    return switch (name)
    {
      case "type" -> XsmpPackage.Literals.NATIVE_TYPE__TYPE;
      case "location" -> XsmpPackage.Literals.NATIVE_TYPE__LOCATION;
      case "namespace" -> XsmpPackage.Literals.NATIVE_TYPE__NAMESPACE;
      default -> super.getFeature(name);
    };
  }

} // NativeTypeImplCustom
