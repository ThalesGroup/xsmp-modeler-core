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
import org.eclipse.xsmp.xcatalogue.RangeKind;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public class FloatImplCustom extends FloatImpl
{

  private static final int MAX_MASK = 2;

  private static final int MIN_MASK = 1;

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUnit()
  {
    return getFeature(XcataloguePackage.Literals.FLOAT__UNIT, UNIT_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMaxInclusive()
  {
    return (getRange().getValue() & MAX_MASK) != 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMinInclusive()
  {
    return (getRange().getValue() & MIN_MASK) != 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaxInclusive(boolean newMaxInclusive)
  {
    setRange(RangeKind.get(newMaxInclusive ? getRange().getValue() | MAX_MASK
            : getRange().getValue() & ~MAX_MASK));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMinInclusive(boolean newMinInclusive)
  {
    setRange(RangeKind.get(newMinInclusive ? getRange().getValue() | MIN_MASK
            : getRange().getValue() & ~MIN_MASK));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUnit(String newUnit)
  {
    setFeature(XcataloguePackage.Literals.FLOAT__UNIT, newUnit);
  }

  @Override
  protected EStructuralFeature getFeature(String name)
  {
    switch (name)
    {
      case "unit":
        return XcataloguePackage.Literals.FLOAT__UNIT;
      default:
        return super.getFeature(name);
    }
  }
} // FloatImpl
