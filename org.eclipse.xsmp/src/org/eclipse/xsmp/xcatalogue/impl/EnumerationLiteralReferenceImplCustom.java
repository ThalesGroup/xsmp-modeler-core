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
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

/**
 * @author daveluy
 */
public class EnumerationLiteralReferenceImplCustom extends EnumerationLiteralReferenceImpl
{
  @Override
  public Expression solve(ValidationMessageAcceptor acceptor)
  {
    if (getValue() instanceof Constant)
    {
      return duplicateEObject(EcoreUtil.copy(((Constant) getValue()).getValue().solve(null)), this);
    }
    if (getValue() instanceof EnumerationLiteral
            && EcoreUtil2.getContainerOfType(this, Enumeration.class) != null)
    {
      return ((EnumerationLiteral) getValue()).getValue().solve(null);
    }

    return duplicateEObject(EcoreUtil.copy(this), this);
  }

  @Override
  public EnumerationLiteral doGetEnum(ValidationMessageAcceptor acceptor)
  {
    if (getValue() instanceof EnumerationLiteral)
    {
      return (EnumerationLiteral) getValue();
    }
    return super.doGetEnum(acceptor);
  }

} // EnumerationLiteralReferenceImplCustom
