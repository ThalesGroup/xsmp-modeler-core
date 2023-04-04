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

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public abstract class DocumentImplCustom extends DocumentImpl
{

  /**
   * {@inheritDoc}
   */
  @Override
  public EList<String> getCreator()
  {
    return getFeature(XcataloguePackage.Literals.DOCUMENT__CREATOR);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getDate()
  {
    return getFeature(XcataloguePackage.Literals.DOCUMENT__DATE, DATE_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle()
  {
    return getFeature(XcataloguePackage.Literals.DOCUMENT__TITLE, TITLE_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getVersion()
  {
    return getFeature(XcataloguePackage.Literals.DOCUMENT__VERSION, VERSION_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDate(Date newDate)
  {
    setFeature(XcataloguePackage.Literals.DOCUMENT__DATE, newDate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String newTitle)
  {
    setFeature(XcataloguePackage.Literals.DOCUMENT__TITLE, newTitle);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVersion(String newVersion)
  {
    setFeature(XcataloguePackage.Literals.DOCUMENT__VERSION, newVersion);
  }

  @Override
  protected EStructuralFeature getFeature(String name)
  {
    switch (name)
    {
      case "version":
        return XcataloguePackage.Literals.DOCUMENT__VERSION;
      case "title":
        return XcataloguePackage.Literals.DOCUMENT__TITLE;
      case "date":
        return XcataloguePackage.Literals.DOCUMENT__DATE;
      case "creator":
        return XcataloguePackage.Literals.DOCUMENT__CREATOR;
      default:
        return super.getFeature(name);
    }
  }

} // DocumentImplCustom
