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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public abstract class TypeImplCustom extends TypeImpl
{

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUuid()
  {

    return getFeature(XsmpPackage.Literals.TYPE__UUID, UUID_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUuid(String newUuid)
  {
    setFeature(XsmpPackage.Literals.TYPE__UUID, newUuid);

  }

  @Override
  protected EAttribute getFeature(String name)
  {
    if ("uuid".equals(name))
    {
      return XsmpPackage.Literals.TYPE__UUID;
    }
    return super.getFeature(name);
  }
} // TypeImplCustom
