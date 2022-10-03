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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xsmp.xcatalogue.PlatformMapping;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

public class NativeTypeImplCustom extends NativeTypeImpl
{

  /**
   * {@inheritDoc}
   */
  @Override
  public EList<PlatformMapping> getPlatform()
  {
    return getFeature(XcataloguePackage.Literals.NATIVE_TYPE__PLATFORM);
  }

  @Override
  protected EStructuralFeature getFeature(String name)
  {
    switch (name)
    {
      case "platform":
        return XcataloguePackage.Literals.NATIVE_TYPE__PLATFORM;
      default:
        return super.getFeature(name);
    }
  }

} // NativeTypeImplCustom
