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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.xsmp.xcatalogue.AccessKind;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public class PropertyImplCustom extends PropertyImpl
{

  private AccessKind findAccess()
  {

    for (final String m : getModifiers())
    {
      final var v = AccessKind.get(m);
      if (v != null)
      {
        return v;
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccessKind getAccess()
  {
    final var v = findAccess();
    if (v != null)
    {
      return v;
    }

    return ACCESS_EDEFAULT;
  }

  @Override
  public boolean isSetAccess()
  {
    return findAccess() != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCategory()
  {
    return getFeature(XcataloguePackage.Literals.PROPERTY__CATEGORY, CATEGORY_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAccess(AccessKind newAccess)
  {
    final var oldAccess = findAccess();

    final var oldAccessESet = oldAccess != null;
    var index = 0;
    if (oldAccess != null)
    {
      index = getModifiers().indexOf(oldAccess.getName());
      getModifiers().remove(index);
    }

    if (newAccess != ACCESS_EDEFAULT)
    {
      getModifiers().add(index, newAccess.getName());
    }

    if (eNotificationRequired())
    {
      eNotify(new ENotificationImpl(this, Notification.SET,
              XcataloguePackage.VISIBILITY_ELEMENT__VISIBILITY, oldAccess, newAccess,
              !oldAccessESet));
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void unsetAccess()
  {
    final var oldAccess = getAccess();

    final var oldAccessESet = oldAccess != null;
    if (oldAccessESet)
    {
      getModifiers().remove(oldAccess.getName());
      if (eNotificationRequired())
      {
        eNotify(new ENotificationImpl(this, Notification.UNSET, XcataloguePackage.PROPERTY__ACCESS,
                oldAccess, null, oldAccessESet));
      }
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCategory(String newCategory)
  {
    setFeature(XcataloguePackage.Literals.PROPERTY__CATEGORY, newCategory);
  }

  @Override
  protected EStructuralFeature getFeature(String name)
  {
    if ("category".equals(name))
    {
      return XcataloguePackage.Literals.PROPERTY__CATEGORY;
    }
    return super.getFeature(name);
  }
} // PropertyImplCustom
