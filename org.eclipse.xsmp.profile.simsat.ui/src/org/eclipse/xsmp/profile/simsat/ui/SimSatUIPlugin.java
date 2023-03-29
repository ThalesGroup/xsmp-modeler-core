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
package org.eclipse.xsmp.profile.simsat.ui;

import org.eclipse.xsmp.profile.simsat.SimSatRuntimeModule;
import org.eclipse.xsmp.ui.AbstractXsmpcatUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * This is the central singleton for the SIMSAT Profile UI plugin.
 */
public final class SimSatUIPlugin extends AbstractXsmpcatUIPlugin
{
  public static final String PLUGIN_ID = "org.eclipse.xsmp.profile.simsat.ui";

  private static SimSatUIPlugin instance;

  public static SimSatUIPlugin getInstance()
  {
    return instance;
  }

  @Override
  public void start(BundleContext context) throws Exception
  {
    super.start(context);
    instance = this;
  }

  @Override
  public void stop(BundleContext context) throws Exception
  {
    instance = null;
    super.stop(context);
  }

  @Override
  protected com.google.inject.Module getRuntimeModule()
  {
    return new SimSatRuntimeModule();
  }

  @Override
  protected com.google.inject.Module getUiModule()
  {
    return new SimSatUiModule(this);
  }

}
