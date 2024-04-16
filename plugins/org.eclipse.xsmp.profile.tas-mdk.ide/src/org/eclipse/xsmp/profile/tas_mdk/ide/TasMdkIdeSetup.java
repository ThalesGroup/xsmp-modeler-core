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
package org.eclipse.xsmp.profile.tas_mdk.ide;

import org.eclipse.xsmp.profile.tas_mdk.TasMdkRuntimeModule;
import org.eclipse.xsmp.profile.tas_mdk.TasMdkStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class TasMdkIdeSetup extends TasMdkStandaloneSetup
{

  @Override
  public Injector createInjector()
  {
    return Guice.createInjector(Modules2.mixin(new TasMdkRuntimeModule(), new TasMdkIdeModule()));
  }

}
