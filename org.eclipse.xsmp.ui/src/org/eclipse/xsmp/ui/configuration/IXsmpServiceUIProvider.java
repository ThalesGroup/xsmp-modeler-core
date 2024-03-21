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
package org.eclipse.xsmp.ui.configuration;

import org.eclipse.core.resources.IProject;
import org.eclipse.xsmp.services.IXsmpServiceProvider;

import com.google.inject.ImplementedBy;
import com.google.inject.Injector;

@ImplementedBy(XsmpServiceUIProvider.class)
public interface IXsmpServiceUIProvider extends IXsmpServiceProvider
{

  Injector getInjector(IProject project);

  /**
   * Check whether the current module is enabled for this project
   *
   * @param project
   *          the project
   * @return true if this Profile/Tool is enabled
   */
  boolean isEnabledFor(IProject project);
}
