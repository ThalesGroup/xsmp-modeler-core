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
public class FieldImplCustom extends FieldImpl
{

  private static final String INPUT_KEYWORD = "input";

  private static final String OUTPUT_KEYWORD = "output";

  private static final String TRANSIENT_KEYWORD = "transient";

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInput()
  {
    return getModifiers().indexOf(INPUT_KEYWORD) >= 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isOutput()
  {
    return getModifiers().indexOf(OUTPUT_KEYWORD) >= 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTransient()
  {
    return getModifiers().indexOf(TRANSIENT_KEYWORD) >= 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setInput(boolean newInput)
  {
    final var oldInput = isInput();

    if (oldInput && !newInput)
    {
      getModifiers().remove(INPUT_KEYWORD);
    }
    else if (!oldInput && newInput)
    {
      getModifiers().add(INPUT_KEYWORD);
    }

    if (eNotificationRequired())
    {
      eNotify(new ENotificationImpl(this, Notification.SET, XsmpPackage.FIELD__INPUT, oldInput,
              newInput));
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOutput(boolean newOutput)
  {
    final var oldOutput = isOutput();

    if (oldOutput && !newOutput)
    {
      getModifiers().remove(OUTPUT_KEYWORD);
    }
    else if (!oldOutput && newOutput)
    {
      getModifiers().add(OUTPUT_KEYWORD);
    }

    if (eNotificationRequired())
    {
      eNotify(new ENotificationImpl(this, Notification.SET, XsmpPackage.FIELD__OUTPUT, oldOutput,
              newOutput));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTransient(boolean newTransient)
  {
    final var oldTransient = isTransient();

    if (oldTransient && !newTransient)
    {
      getModifiers().remove(TRANSIENT_KEYWORD);
    }
    else if (!oldTransient && newTransient)
    {
      getModifiers().add(TRANSIENT_KEYWORD);
    }

    if (eNotificationRequired())
    {
      eNotify(new ENotificationImpl(this, Notification.SET, XsmpPackage.FIELD__TRANSIENT,
              oldTransient, newTransient));
    }
  }

} // FieldImplCustom
