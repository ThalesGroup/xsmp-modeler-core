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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.util.ExpressionSolver;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 * @author daveluy
 */
public class BuiltInConstantImplCustom extends BuiltInConstantImpl
{
  @Override
  public Expression solve(ValidationMessageAcceptor acceptor)
  {
    final var solver = ExpressionSolver.constantMappings.get(getName());
    if (solver == null)
    {
      acceptor.acceptError("Unknown builtin constant '" + getName() + "'",
              ExpressionSolver.getTarget(this), null, ValidationMessageAcceptor.INSIGNIFICANT_INDEX,
              "unsupported_operation", (String[]) null);
      return duplicateEObject(EcoreUtil.copy(this), this);
    }
    return solver.solve(this, acceptor);

  }

} // BuiltInConstantImplCustom
