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
package org.eclipse.xsmp.ui;

import org.eclipse.xsmp.ui.containers.XsmpProjectsState;
import org.eclipse.xsmp.ui.containers.XsmpProjectsStateHelper;
import org.eclipse.xsmp.ui.containers.XsmpprojectProjectsState;

import com.google.inject.Binder;
import com.google.inject.Module;

public class XsmpSharedStateModule implements Module
{

  @Override
  public void configure(Binder binder)
  {
    binder.bind(XsmpProjectsStateHelper.class);
    binder.bind(XsmpProjectsState.class);
    binder.bind(XsmpprojectProjectsState.class);
  }

}
