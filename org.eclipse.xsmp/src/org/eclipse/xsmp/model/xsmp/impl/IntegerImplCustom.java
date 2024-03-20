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
package org.eclipse.xsmp.model.xsmp.impl;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;

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
    return getFeature(XsmpPackage.Literals.INTEGER__UNIT, UNIT_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUnit(String newUnit)
  {

    setFeature(XsmpPackage.Literals.INTEGER__UNIT, newUnit);
  }

  @Override
  protected EStructuralFeature getFeature(String name)
  {
    if ("unit".equals(name))
    {
      return XsmpPackage.Literals.INTEGER__UNIT;
    }
    return super.getFeature(name);
  }
} // IntegerImplCustom
