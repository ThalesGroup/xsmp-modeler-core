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

import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xtext.resource.XtextResource;

/**
 * @author daveluy
 */
public class NamedElementWithMultiplicityImplCustom extends NamedElementWithMultiplicityImpl
{

  /**
   * Helper function to retrieve the long value of an expression
   *
   * @param e
   *          an Expression
   * @return the long value of the expression
   */
  private long getValue(Expression e)
  {
    final var resource = eResource();

    if (resource instanceof XtextResource)
    {
      final var xtextResource = (XtextResource) resource;

      final var xsmpUtil = xtextResource.getResourceServiceProvider().get(XsmpUtil.class);

      final var value = xsmpUtil.getInteger(e);
      if (value != null)
      {
        return value.longValue();
      }
    }
    throw new UnsupportedOperationException("Cannot retrieve the Expression value");
  }

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

    return getValue(multiplicity.getLower());
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
      return multiplicity.isAux() ? -1 : getValue(multiplicity.getLower());
    }
    return getValue(multiplicity.getUpper());
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
