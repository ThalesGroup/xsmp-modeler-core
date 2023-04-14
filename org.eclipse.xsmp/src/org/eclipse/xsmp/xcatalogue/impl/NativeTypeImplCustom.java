/**
 * *******************************************************************************
 * * Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
 * *
 * * All rights reserved. This program and the accompanying materials
 * * are made available under the terms of the Eclipse Public License 2.0
 * * which accompanies this distribution, and is available at
 * * https://www.eclipse.org/legal/epl-2.0/
 * *
 * * SPDX-License-Identifier: EPL-2.0
 * ******************************************************************************
 */
package org.eclipse.xsmp.xcatalogue.impl;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

public class NativeTypeImplCustom extends NativeTypeImpl
{

  @Override
  public String getType()
  {
    return getFeature(XcataloguePackage.Literals.NATIVE_TYPE__TYPE, TYPE_EDEFAULT);
  }

  @Override
  public void setType(String newType)
  {
    setFeature(XcataloguePackage.Literals.NATIVE_TYPE__TYPE, newType);
  }

  @Override
  public String getNamespace()
  {
    return getFeature(XcataloguePackage.Literals.NATIVE_TYPE__NAMESPACE, NAMESPACE_EDEFAULT);
  }

  @Override
  public void setNamespace(String newNamespace)
  {
    setFeature(XcataloguePackage.Literals.NATIVE_TYPE__NAMESPACE, newNamespace);
  }

  @Override
  public String getLocation()
  {

    return getFeature(XcataloguePackage.Literals.NATIVE_TYPE__LOCATION, LOCATION_EDEFAULT);
  }

  @Override
  public void setLocation(String newLocation)
  {

    setFeature(XcataloguePackage.Literals.NATIVE_TYPE__LOCATION, newLocation);
  }

  @Override
  protected EStructuralFeature getFeature(String name)
  {

    switch (name)
    {
      case "type":
        return XcataloguePackage.Literals.NATIVE_TYPE__TYPE;
      case "location":
        return XcataloguePackage.Literals.NATIVE_TYPE__LOCATION;
      case "namespace":
        return XcataloguePackage.Literals.NATIVE_TYPE__NAMESPACE;
      default:
        return super.getFeature(name);
    }

  }

} // NativeTypeImplCustom
