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
package org.eclipse.xsmp.model.xsmp.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;

/**
 * Implements generated methods
 *
 * @author daveluy
 */
public class ClassImplCustom extends ClassImpl
{

  private static final String ABSTRACT_KEYWORD = "abstract";

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAbstract()
  {
    return getModifiers().indexOf(ABSTRACT_KEYWORD) >= 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAbstract(boolean newAbstract)
  {
    final var oldAbstract = isAbstract();

    if (oldAbstract && !newAbstract)
    {
      getModifiers().remove(ABSTRACT_KEYWORD);
    }
    else if (!oldAbstract && newAbstract)
    {
      getModifiers().add(ABSTRACT_KEYWORD);
    }

    if (eNotificationRequired())
    {
      eNotify(new ENotificationImpl(this, Notification.SET, XsmpPackage.CLASS__ABSTRACT,
              oldAbstract, newAbstract));
    }

  }

} // ClassImplCustom
