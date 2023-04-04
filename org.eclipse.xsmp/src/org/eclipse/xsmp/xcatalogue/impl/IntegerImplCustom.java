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
package org.eclipse.xsmp.xcatalogue.impl;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public class IntegerImplCustom extends IntegerImpl
{

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUnit()
  {
    return getFeature(XcataloguePackage.Literals.INTEGER__UNIT, UNIT_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUnit(String newUnit)
  {

    setFeature(XcataloguePackage.Literals.INTEGER__UNIT, newUnit);
  }

  @Override
  protected EStructuralFeature getFeature(String name)
  {
    if ("unit".equals(name))
    {
      return XcataloguePackage.Literals.INTEGER__UNIT;
    }
    return super.getFeature(name);
  }
} // IntegerImplCustom
