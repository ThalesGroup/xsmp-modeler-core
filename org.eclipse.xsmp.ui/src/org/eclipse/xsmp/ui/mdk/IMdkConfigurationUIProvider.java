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
package org.eclipse.xsmp.ui.mdk;

import org.eclipse.core.resources.IProject;
import org.eclipse.xsmp.mdk.IMdkConfigurationProvider;

import com.google.inject.Injector;

public interface IMdkConfigurationUIProvider extends IMdkConfigurationProvider
{

  <T> T getInstance(IProject project, Class<T> clazz);

  Injector getInjector(IProject project);

}
