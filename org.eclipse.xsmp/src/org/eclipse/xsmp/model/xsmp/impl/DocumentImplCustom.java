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

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;

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
    return getFeature(XsmpPackage.Literals.DOCUMENT__CREATOR);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getDate()
  {
    return getFeature(XsmpPackage.Literals.DOCUMENT__DATE, DATE_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle()
  {
    return getFeature(XsmpPackage.Literals.DOCUMENT__TITLE, TITLE_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getVersion()
  {
    return getFeature(XsmpPackage.Literals.DOCUMENT__VERSION, VERSION_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDate(Date newDate)
  {
    setFeature(XsmpPackage.Literals.DOCUMENT__DATE, newDate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String newTitle)
  {
    setFeature(XsmpPackage.Literals.DOCUMENT__TITLE, newTitle);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVersion(String newVersion)
  {
    setFeature(XsmpPackage.Literals.DOCUMENT__VERSION, newVersion);
  }

  @Override
  protected EAttribute getFeature(String name)
  {
    return switch (name)
    {
      case "version" -> XsmpPackage.Literals.DOCUMENT__VERSION;
      case "title" -> XsmpPackage.Literals.DOCUMENT__TITLE;
      case "date" -> XsmpPackage.Literals.DOCUMENT__DATE;
      case "creator" -> XsmpPackage.Literals.DOCUMENT__CREATOR;
      default -> super.getFeature(name);
    };
  }

} // DocumentImplCustom
