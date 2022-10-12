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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.util.ExpressionSolver;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public class FloatingLiteralImplCustom extends FloatingLiteralImpl
{

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal getValue()
  {

    if (text == null)
    {
      return null;
    }
    final var withoutUnderscore = text.replace("'", "");

    if (withoutUnderscore.endsWith("f") || withoutUnderscore.endsWith("F"))
    {
      return new BigDecimal(withoutUnderscore.substring(0, withoutUnderscore.length() - 1),
              MathContext.DECIMAL32);
    }

    if (withoutUnderscore.endsWith("l") || withoutUnderscore.endsWith("L"))
    {
      return new BigDecimal(withoutUnderscore.substring(0, withoutUnderscore.length() - 1),
              MathContext.DECIMAL128);
    }
    return new BigDecimal(withoutUnderscore, MathContext.DECIMAL64);

  }

  @Override
  public Expression solve(ValidationMessageAcceptor acceptor)
  {
    return duplicateEObject(EcoreUtil.copy(this), this);
  }

  @Override
  public BigDecimal getDecimal(ValidationMessageAcceptor acceptor)
  {
    return doGetDecimal(acceptor);
  }

  @Override
  public BigDecimal doGetDecimal(ValidationMessageAcceptor acceptor)
  {
    try
    {
      return getValue();
    }
    catch (final NumberFormatException e)
    {
      if (acceptor != null)
      {
        acceptor.acceptError(e.getMessage(), ExpressionSolver.getTarget(this),
                XcataloguePackage.Literals.FLOATING_LITERAL__TEXT, -1, "NumberFormatException");
      }
      return null;
    }
  }

  @Override
  public BigInteger getInteger(ValidationMessageAcceptor acceptor)
  {
    return doGetInteger(acceptor);

  }

  @Override
  public BigInteger doGetInteger(ValidationMessageAcceptor acceptor)
  {

    if (acceptor != null)
    {
      acceptor.acceptWarning("Narrowing convertion from Decimal to Integral Number.",
              ExpressionSolver.getTarget(this), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              "unsupported_operation", (String[]) null);
    }
    try
    {
      return getValue().toBigInteger();
    }
    catch (final NumberFormatException e)
    {
      if (acceptor != null)
      {
        acceptor.acceptError(e.getMessage(), ExpressionSolver.getTarget(this),
                XcataloguePackage.Literals.FLOATING_LITERAL__TEXT, -1, "NumberFormatException");
      }
      return null;
    }

  }
} // DecimalLiteralImpl
