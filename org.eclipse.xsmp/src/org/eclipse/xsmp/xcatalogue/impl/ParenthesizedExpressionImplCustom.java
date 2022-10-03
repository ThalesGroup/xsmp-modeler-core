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

import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.ParenthesizedExpression;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 * @author daveluy
 */
public class ParenthesizedExpressionImplCustom extends ParenthesizedExpressionImpl
{
  @Override
  public Expression solve(ValidationMessageAcceptor acceptor)
  {
    return ((ParenthesizedExpression) this).getExpr().solve(acceptor);
  }

} // ParenthesizedExpressionImplCustom
