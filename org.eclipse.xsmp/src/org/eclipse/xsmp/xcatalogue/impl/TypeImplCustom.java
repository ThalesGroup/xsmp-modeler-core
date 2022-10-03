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

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

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

    return getFeature(XcataloguePackage.Literals.TYPE__UUID, UUID_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUuid(String newUuid)
  {
    setFeature(XcataloguePackage.Literals.TYPE__UUID, newUuid);

  }

  @Override
  protected EStructuralFeature getFeature(String name)
  {
    switch (name)
    {
      case "uuid":
        return XcataloguePackage.Literals.TYPE__UUID;
      default:
        return super.getFeature(name);
    }
  }
} // TypeImpl
