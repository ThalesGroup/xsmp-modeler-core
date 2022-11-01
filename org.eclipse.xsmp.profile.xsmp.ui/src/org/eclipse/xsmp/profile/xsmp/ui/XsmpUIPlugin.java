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
package org.eclipse.xsmp.profile.xsmp.ui;

import org.eclipse.xsmp.profile.xsmp.XsmpRuntimeModule;
import org.eclipse.xsmp.ui.AbstractXsmpcatUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * This is the central singleton for the Xsmp Tool Chain UI plugin.
 */
public final class XsmpUIPlugin extends AbstractXsmpcatUIPlugin
{
  public static final String PLUGIN_ID = "org.eclipse.xsmp.profile.xsmp.ui";

  private static XsmpUIPlugin instance;

  public static XsmpUIPlugin getInstance()
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
    return new XsmpRuntimeModule();
  }

  @Override
  protected com.google.inject.Module getUiModule()
  {
    return new XsmpUiModule(this);
  }

}
