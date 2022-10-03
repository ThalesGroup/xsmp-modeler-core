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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.util.ExpressionSolver;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public abstract class ExpressionImplCustom extends ExpressionImpl
{

  protected <T extends EObject> T duplicateEObject(T newEObject, EObject ref)
  {

    newEObject.eAdapters().add(new ExpressionSolver.ExpressionRefAdapter(ref));
    return newEObject;
  }

  @Override
  public Boolean getBoolean(ValidationMessageAcceptor acceptor)
  {
    return solve(acceptor).doGetBoolean(acceptor);
  }

  @Override
  public Boolean doGetBoolean(ValidationMessageAcceptor acceptor)
  {
    if (acceptor != null)
    {
      acceptor.acceptError(eClass().getName() + " is not convertible to a Boolean type.",
              ExpressionSolver.getTarget(this), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              "unsupported_operation", (String[]) null);
    }
    return null;
  }

  @Override
  public String getString(ValidationMessageAcceptor acceptor)
  {
    return solve(acceptor).doGetString(acceptor);
  }

  @Override
  public String doGetString(ValidationMessageAcceptor acceptor)
  {
    if (acceptor != null)
    {
      acceptor.acceptError(eClass().getName() + " is not convertible to a String type.",
              ExpressionSolver.getTarget(this), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              "unsupported_operation", (String[]) null);
    }
    return null;
  }

  @Override
  public BigDecimal getDecimal(ValidationMessageAcceptor acceptor)
  {
    return solve(acceptor).doGetDecimal(acceptor);
  }

  @Override
  public BigDecimal doGetDecimal(ValidationMessageAcceptor acceptor)
  {
    if (acceptor != null)
    {
      acceptor.acceptError(eClass().getName() + " is not convertible to a Decimal type.",
              ExpressionSolver.getTarget(this), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              "unsupported_operation", (String[]) null);
    }
    return null;
  }

  @Override
  public BigInteger getInteger(ValidationMessageAcceptor acceptor)
  {
    return solve(acceptor).doGetInteger(acceptor);
  }

  @Override
  public BigInteger doGetInteger(ValidationMessageAcceptor acceptor)
  {
    if (acceptor != null)
    {
      acceptor.acceptError(eClass().getName() + " is not convertible to an Integral type.",
              ExpressionSolver.getTarget(this), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              "unsupported_operation", (String[]) null);
    }
    return null;
  }

  @Override
  public EnumerationLiteral getEnum(ValidationMessageAcceptor acceptor)
  {

    return solve(acceptor).doGetEnum(acceptor);
  }

  @Override
  public EnumerationLiteral doGetEnum(ValidationMessageAcceptor acceptor)
  {
    if (acceptor != null)
    {
      acceptor.acceptError(eClass().getName() + " is not an Enumeration Literal.",
              ExpressionSolver.getTarget(this), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              "unsupported_operation", (String[]) null);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Expression solve(ValidationMessageAcceptor acceptor)
  {

    acceptor.acceptError("Unsupported Expression '" + eClass().getName() + "'",
            ExpressionSolver.getTarget(this), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
            "unsupported_operation", (String[]) null);

    return duplicateEObject(EcoreUtil.copy(this), this);

  }

} // ExpressionImpl
