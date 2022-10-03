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
public class IntegerLiteralImplCustom extends IntegerLiteralImpl
{

  /**
   * {@inheritDoc}
   */
  @Override
  public BigInteger getValue()
  {
    if (text == null)
    {
      return null;
    }
    var withoutUnderscore = text.replace("'", "");

    // remove trailing suffix
    if (withoutUnderscore.endsWith("ll") || withoutUnderscore.endsWith("LL"))
    {
      withoutUnderscore = withoutUnderscore.substring(0, withoutUnderscore.length() - 2);
    }
    else if (withoutUnderscore.endsWith("l") || withoutUnderscore.endsWith("L"))
    {
      withoutUnderscore = withoutUnderscore.substring(0, withoutUnderscore.length() - 1);
    }
    if (withoutUnderscore.endsWith("u") || withoutUnderscore.endsWith("U"))
    {
      withoutUnderscore = withoutUnderscore.substring(0, withoutUnderscore.length() - 1);
    }

    if (withoutUnderscore.startsWith("0x") || withoutUnderscore.startsWith("0X"))
    {
      return new BigInteger(withoutUnderscore.substring(2), 16);
    }
    if (withoutUnderscore.startsWith("0b") || withoutUnderscore.startsWith("0B"))
    {
      return new BigInteger(withoutUnderscore.substring(2), 2);
    }
    if (withoutUnderscore.startsWith("0"))
    {
      return new BigInteger(withoutUnderscore, 8);
    }
    return new BigInteger(withoutUnderscore);

  }

  @Override
  public Expression solve(ValidationMessageAcceptor acceptor)
  {
    return duplicateEObject(EcoreUtil.copy(this), this);
  }

  @Override
  public BigDecimal doGetDecimal(ValidationMessageAcceptor acceptor)
  {
    try
    {
      return new BigDecimal(getValue());
    }
    catch (final NumberFormatException e)
    {
      acceptor.acceptError(e.getMessage(), ExpressionSolver.getTarget(this),
              XcataloguePackage.Literals.INTEGER_LITERAL__TEXT, -1, "NumberFormatException");
      return null;
    }
  }

  @Override
  public BigInteger doGetInteger(ValidationMessageAcceptor acceptor)
  {
    try
    {
      return getValue();
    }
    catch (final NumberFormatException e)
    {
      acceptor.acceptError(e.getMessage(), ExpressionSolver.getTarget(this),
              XcataloguePackage.Literals.INTEGER_LITERAL__TEXT, -1, "NumberFormatException");
      return null;
    }
  }
} // IntegerLiteralImplCustom
