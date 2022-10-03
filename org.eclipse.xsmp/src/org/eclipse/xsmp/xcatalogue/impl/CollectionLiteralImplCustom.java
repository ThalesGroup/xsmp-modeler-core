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

import org.eclipse.xsmp.util.ExpressionSolver;
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 * @author daveluy
 */
public class CollectionLiteralImplCustom extends CollectionLiteralImpl
{
  @Override
  public Expression solve(ValidationMessageAcceptor acceptor)
  {
    final var c = duplicateEObject(XcatalogueFactory.eINSTANCE.createCollectionLiteral(),
            (CollectionLiteral) this);
    ((CollectionLiteral) this).getElements().stream().map(elem -> elem.solve(acceptor))
            .filter(e -> e != null).forEachOrdered(c.getElements()::add);
    return c;
  }

  @Override
  public Boolean doGetBoolean(ValidationMessageAcceptor acceptor)
  {
    switch (getElements().size())
    {
      case 0:
        return Boolean.FALSE;
      case 1:
        return getElements().get(0).doGetBoolean(acceptor);
      default:
        acceptor.acceptError("Expecting only one element, got " + getElements().size(),
                ExpressionSolver.getTarget(this), null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation",
                (String[]) null);
        return null;
    }
  }

  @Override
  public BigDecimal doGetDecimal(ValidationMessageAcceptor acceptor)
  {
    switch (getElements().size())
    {
      case 0:
        return BigDecimal.ZERO;
      case 1:
        return getElements().get(0).doGetDecimal(acceptor);
      default:
        acceptor.acceptError("Expecting only one element, got " + getElements().size(),
                ExpressionSolver.getTarget(this), null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation",
                (String[]) null);
        return null;
    }
  }

  @Override
  public BigInteger doGetInteger(ValidationMessageAcceptor acceptor)
  {
    switch (getElements().size())
    {
      case 0:
        return BigInteger.ZERO;
      case 1:
        return getElements().get(0).doGetInteger(acceptor);
      default:
        acceptor.acceptError("Expecting only one element, got " + getElements().size(),
                ExpressionSolver.getTarget(this), null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation",
                (String[]) null);
        return null;
    }
  }

  @Override
  public String doGetString(ValidationMessageAcceptor acceptor)
  {
    switch (getElements().size())
    {
      case 0:
        return "";
      case 1:
        return getElements().get(0).doGetString(acceptor);
      default:
        acceptor.acceptError("Expecting only one element, got " + getElements().size(),
                ExpressionSolver.getTarget(this), null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation",
                (String[]) null);
        return null;
    }
  }

  @Override
  public EnumerationLiteral doGetEnum(ValidationMessageAcceptor acceptor)
  {
    switch (getElements().size())
    {
      case 0:
        return null;
      case 1:
        return getElements().get(0).doGetEnum(acceptor);
      default:
        acceptor.acceptError("Expecting only one element, got " + getElements().size(),
                ExpressionSolver.getTarget(this), null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation",
                (String[]) null);
        return null;
    }
  }

} // CollectionLiteralImplCustom
