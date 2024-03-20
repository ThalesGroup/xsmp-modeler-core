/**
 * *******************************************************************************
 * * Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
 * *
 * * All rights reserved. This program and the accompanying materials
 * * are made available under the terms of the Eclipse Public License 2.0
 * * which accompanies this distribution, and is available at
 * * https://www.eclipse.org/legal/epl-2.0/
 * *
 * * SPDX-License-Identifier: EPL-2.0
 * ******************************************************************************
 */
package org.eclipse.xsmp.model.xsmp.impl;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Event Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.xsmp.model.xsmp.impl.EventSourceImplCustom#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.xsmp.model.xsmp.impl.EventSourceImplCustom#isSinglecast
 * <em>Singlecast</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EventSourceImplCustom extends EventSourceImpl
{

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSinglecast()
  {

    return getFeature(XsmpPackage.Literals.EVENT_SOURCE__SINGLECAST, SINGLECAST_EDEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSinglecast(boolean newSinglecast)
  {

    setFeature(XsmpPackage.Literals.EVENT_SOURCE__SINGLECAST, newSinglecast);
  }

  @Override
  protected EStructuralFeature getFeature(String name)
  {
    if ("singlecast".equals(name))
    {
      return XsmpPackage.Literals.EVENT_SOURCE__SINGLECAST;
    }
    return super.getFeature(name);

  }

} // EventSourceImplCustom
