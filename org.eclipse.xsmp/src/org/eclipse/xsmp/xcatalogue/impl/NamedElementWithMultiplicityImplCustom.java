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

import org.eclipse.xsmp.util.Solver;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;

/**
 * @author daveluy
 */
public class NamedElementWithMultiplicityImplCustom extends NamedElementWithMultiplicityImpl
{

  @Override
  public long getLower()
  {
    if (optional)
    {
      return 0;
    }
    if (multiplicity == null)
    {
      return 1;
    }
    if (multiplicity.getLower() == null && multiplicity.getUpper() == null)
    {
      return multiplicity.isAux() ? 1 : 0;
    }
    return Solver.INSTANCE.getInteger(multiplicity.getLower()).longValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getUpper()
  {
    if (optional || multiplicity == null)
    {
      return 1;
    }
    if (multiplicity.getLower() == null && multiplicity.getUpper() == null)
    {
      return -1;
    }
    if (multiplicity.getUpper() == null)
    {
      return multiplicity.isAux() ? -1
              : Solver.INSTANCE.getInteger(multiplicity.getLower()).longValue();
    }
    return Solver.INSTANCE.getInteger(multiplicity.getUpper()).longValue();
  }

  private void setMultiplicity(long lower, long upper)
  {

    if (lower == 0 && upper == 1)
    {
      setOptional(true);
      setMultiplicity(null);
    }
    else if (lower == 1 && upper == 1)
    {
      setOptional(false);
      setMultiplicity(null);
    }
    else if (lower == upper)
    {
      setOptional(false);
      setMultiplicity(XcatalogueFactory.eINSTANCE.createMultiplicity());
      final var l = XcatalogueFactory.eINSTANCE.createIntegerLiteral();
      l.setText(Long.toString(lower));
      multiplicity.setLower(l);
    }
    else if (upper < 0)
    {
      setOptional(false);
      setMultiplicity(XcatalogueFactory.eINSTANCE.createMultiplicity());
      if (lower == 0)
      {

        multiplicity.setAux(false);
      }
      else if (lower == 1)
      {
        multiplicity.setAux(true);
      }
      else
      {
        multiplicity.setAux(true);
        final var l = XcatalogueFactory.eINSTANCE.createIntegerLiteral();
        l.setText(Long.toString(lower));
        multiplicity.setLower(l);
      }
    }
    else
    {
      setOptional(false);
      setMultiplicity(XcatalogueFactory.eINSTANCE.createMultiplicity());
      final var l = XcatalogueFactory.eINSTANCE.createIntegerLiteral();
      l.setText(Long.toString(lower));
      multiplicity.setLower(l);
      final var u = XcatalogueFactory.eINSTANCE.createIntegerLiteral();
      u.setText(Long.toString(upper));
      multiplicity.setUpper(u);
    }

  }

  @Override
  public void setLower(long lower)
  {
    setMultiplicity(lower, getUpper());
  }

  @Override
  public void setUpper(long upper)
  {
    setMultiplicity(getLower(), upper);
  }

} // NamedElementWithMultiplicityImplCustom
