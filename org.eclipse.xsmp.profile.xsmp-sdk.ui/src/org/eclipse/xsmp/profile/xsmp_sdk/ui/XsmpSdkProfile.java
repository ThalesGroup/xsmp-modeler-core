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
package org.eclipse.xsmp.profile.xsmp_sdk.ui;

import org.eclipse.xsmp.profile.xsmp_sdk.AbstractXsmpSdkProfile;

import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class XsmpSdkProfile extends AbstractXsmpSdkProfile
{
  @Override
  public Injector getInjector(String languageName)
  {
    final var activator = XsmpSdkActivator.getInstance();
    return activator != null ? activator.getInjector(languageName) : null;
  }
}
