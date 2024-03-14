/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.model.xsmp.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xsmp.model.xsmp.XsmpFactory;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;

public class SimulatorImplCustom extends SimulatorImpl
{

  protected SimulatorImplCustom()
  {
    initModels();
    initServices();
  }

  private void initModels()
  {

    final var newModels = XsmpFactory.eINSTANCE.createContainer();

    newModels.setName("Models");
    newModels.setMultiplicity(XsmpFactory.eINSTANCE.createMultiplicity());
    newModels.setDescription("Models collection of the simulator");

    NotificationChain msgs = null;

    msgs = ((InternalEObject) newModels).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - XsmpPackage.SIMULATOR__MODELS, null, msgs);

    msgs = basicSetModels(newModels, msgs);
    if (msgs != null)
    {
      msgs.dispatch();
    }

  }

  private void initServices()
  {

    final var newServices = XsmpFactory.eINSTANCE.createContainer();

    newServices.setName("Services");
    newServices.setDescription("Services collection of the simulator");
    newServices.setMultiplicity(XsmpFactory.eINSTANCE.createMultiplicity());

    NotificationChain msgs = null;

    msgs = ((InternalEObject) newServices).eInverseAdd(this,
            EOPPOSITE_FEATURE_BASE - XsmpPackage.SIMULATOR__SERVICES, null, msgs);

    msgs = basicSetServices(newServices, msgs);
    if (msgs != null)
    {
      msgs.dispatch();
    }

  }

} // SimulatorImplCustom
