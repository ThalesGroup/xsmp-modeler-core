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
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.xsmp.xcatalogue.VisibilityKind;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public abstract class VisibilityElementImplCustom extends VisibilityElementImpl
{

  /**
   * Get the default visibility of the element
   *
   * @return the default visibility of the element
   */
  protected VisibilityKind getDefaultVisibility()
  {

    final var container = eContainer();
    if (container != null)
    {
      switch (container.eClass().getClassifierID())
      {
        case XcataloguePackage.INTERFACE:
        case XcataloguePackage.STRUCTURE:
          return VisibilityKind.PUBLIC;

        default:
          return VisibilityKind.PRIVATE;
      }
    }

    return VisibilityKind.PUBLIC;
  }

  @Override
  public boolean isUseVisibility()
  {
    final var container = eContainer();

    if (container != null)
    {
      switch (container.eClass().getClassifierID())
      {
        case XcataloguePackage.INTERFACE:
        case XcataloguePackage.STRUCTURE:
          return false;

        default:
          break;
      }
    }

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VisibilityKind getRealVisibility()
  {

    final var v = getVisibility();
    if (v != null)
    {
      return v;
    }

    return getDefaultVisibility();
  }

  /**
   * Find the visibility in the modifiers list
   *
   * @return the visibility or null
   */
  @Override
  public VisibilityKind getVisibility()
  {

    for (final String m : getModifiers())
    {
      final var v = VisibilityKind.get(m);
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
  public boolean isSetVisibility()
  {

    return getVisibility() != null;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVisibility(VisibilityKind newVisibility)
  {
    final var oldVisibility = getVisibility();

    final var oldVisibilityESet = oldVisibility != null;

    var index = 0;
    if (oldVisibilityESet)
    {
      index = getModifiers().indexOf(oldVisibility.getName());
      getModifiers().remove(index);
    }

    if (newVisibility != null)
    {
      getModifiers().add(index, newVisibility.getName());
    }

    if (eNotificationRequired())
    {
      eNotify(new ENotificationImpl(this, Notification.SET,
              XcataloguePackage.VISIBILITY_ELEMENT__VISIBILITY, oldVisibility, newVisibility,
              !oldVisibilityESet));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void unsetVisibility()
  {
    final var oldVisibility = getVisibility();

    final var oldVisibilityESet = oldVisibility != null;
    if (oldVisibilityESet)
    {
      getModifiers().remove(oldVisibility.getName());
      if (eNotificationRequired())
      {
        eNotify(new ENotificationImpl(this, Notification.UNSET,
                XcataloguePackage.VISIBILITY_ELEMENT__VISIBILITY, oldVisibility, null,
                oldVisibilityESet));
      }
    }

  }

} // VisibilityElementImpl
