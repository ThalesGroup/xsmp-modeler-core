/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.model.xsmp.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public class AttributeTypeImplCustom extends AttributeTypeImpl
{

  /**
   * {@inheritDoc}
   */
  @Override
  public EList<String> getUsage()
  {
    return getFeature(XsmpPackage.Literals.ATTRIBUTE_TYPE__USAGE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAllowMultiple()
  {

    return getFeature(XsmpPackage.Literals.ATTRIBUTE_TYPE__ALLOW_MULTIPLE, ALLOW_MULTIPLE_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAllowMultiple(boolean newAllowMultiple)
  {

    setFeature(XsmpPackage.Literals.ATTRIBUTE_TYPE__ALLOW_MULTIPLE, newAllowMultiple);
  }

  @Override
  protected EAttribute getFeature(String name)
  {
    return switch (name)
    {
      case "usage" -> XsmpPackage.Literals.ATTRIBUTE_TYPE__USAGE;
      case "allowMultiple" -> XsmpPackage.Literals.ATTRIBUTE_TYPE__ALLOW_MULTIPLE;
      default -> super.getFeature(name);
    };
  }

} // AttributeTypeImplCustom
