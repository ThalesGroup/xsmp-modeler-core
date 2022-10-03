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
import org.eclipse.xsmp.xcatalogue.BooleanLiteral;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.FloatingLiteral;
import org.eclipse.xsmp.xcatalogue.IntegerLiteral;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 * @author daveluy
 */
public class UnaryOperationImplCustom extends UnaryOperationImpl
{
  @Override
  public Expression solve(ValidationMessageAcceptor acceptor)
  {

    if (getOperand() != null)
    {
      final var operand = getOperand().solve(acceptor);

      final var classId = operand.eClass().getClassifierID();
      switch (getFeature())
      {
        case "!":

          final var bool = duplicateEObject(XcatalogueFactory.eINSTANCE.createBooleanLiteral(),
                  this);
          switch (classId)
          {
            case XcataloguePackage.BOOLEAN_LITERAL:
              bool.setIsTrue(!((BooleanLiteral) operand).isIsTrue());
              return bool;
            case XcataloguePackage.FLOATING_LITERAL:
              bool.setIsTrue(((FloatingLiteral) operand).getValue().equals(BigDecimal.ZERO));

              return bool;
            case XcataloguePackage.INTEGER_LITERAL:
              bool.setIsTrue(((IntegerLiteral) operand).getValue().equals(BigInteger.ZERO));

              return bool;
            default:
              break;
          }
          break;
        case "~":

          switch (classId)
          {
            case XcataloguePackage.INTEGER_LITERAL:
              final var integer = duplicateEObject(
                      XcatalogueFactory.eINSTANCE.createIntegerLiteral(), this);
              integer.setText(((IntegerLiteral) operand).getValue().not().toString());
              return integer;
            default:
              break;
          }
          break;
        case "-":
          switch (classId)
          {

            case XcataloguePackage.FLOATING_LITERAL:

              final var decimal = duplicateEObject(
                      XcatalogueFactory.eINSTANCE.createFloatingLiteral(), this);
              decimal.setText(((FloatingLiteral) operand).getValue().negate().toString());
              return decimal;
            case XcataloguePackage.INTEGER_LITERAL:

              final var integer = duplicateEObject(
                      XcatalogueFactory.eINSTANCE.createIntegerLiteral(), this);
              integer.setText(((IntegerLiteral) operand).getValue().negate().toString());
              return integer;
            default:
              break;
          }

          break;
        case "+":
          switch (classId)
          {
            case XcataloguePackage.FLOATING_LITERAL:
            case XcataloguePackage.INTEGER_LITERAL:
              return duplicateEObject(EcoreUtil.copy(operand), operand);
            default:
              break;
          }
          break;
        default:
          break;
      }

      if (acceptor != null)
      {
        acceptor.acceptError(
                "Unary Operator '" + getFeature() + "' not supported on "
                        + operand.eClass().getName() + " type.",
                ExpressionSolver.getTarget(this), null,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, "unsupported_operation",
                (String[]) null);
      }
    }
    return duplicateEObject(EcoreUtil.copy(this), this);
  }
} // UnaryOperationImplCustom
