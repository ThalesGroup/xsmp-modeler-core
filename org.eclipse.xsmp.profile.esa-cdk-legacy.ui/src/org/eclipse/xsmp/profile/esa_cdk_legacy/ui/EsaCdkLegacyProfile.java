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
package org.eclipse.xsmp.profile.esa_cdk_legacy.ui;

import org.eclipse.xsmp.profile.esa_cdk_legacy.AbstractEsaCdkLegacyProfile;

import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class EsaCdkLegacyProfile extends AbstractEsaCdkLegacyProfile
{

  @Override
  public Injector getInjector(String languageName)
  {
    final var activator = EsaCdkLegacyActivator.getInstance();
    return activator != null ? activator.getInjector(languageName) : null;
  }
}
