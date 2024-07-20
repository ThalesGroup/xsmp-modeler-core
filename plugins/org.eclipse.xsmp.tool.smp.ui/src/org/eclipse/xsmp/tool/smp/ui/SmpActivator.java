/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.tool.smp.ui;

import org.eclipse.xsmp.tool.smp.SmpRuntimeModule;
import org.eclipse.xsmp.ui.AbstractXsmpActivator;
import org.osgi.framework.BundleContext;

/**
 * This is the central singleton for the Smp Tool UI plugin.
 */
public final class SmpActivator extends AbstractXsmpActivator
{
  public static final String PLUGIN_ID = "org.eclipse.xsmp.tool.smp.ui";

  private static SmpActivator instance;

  public static SmpActivator getInstance()
  {
    return instance;
  }

  private static void setInstance(SmpActivator instance)
  {
    SmpActivator.instance = instance;
  }

  @Override
  public void start(BundleContext context) throws Exception
  {
    super.start(context);
    setInstance(this);
  }

  @Override
  public void stop(BundleContext context) throws Exception
  {
    setInstance(null);
    super.stop(context);
  }

  @Override
  protected com.google.inject.Module getRuntimeModule(String grammar)
  {
    if (ORG_ECLIPSE_XSMP_XSMPCAT.equals(grammar))
    {
      return new SmpRuntimeModule();
    }
    return super.getRuntimeModule(grammar);
  }

  @Override
  protected com.google.inject.Module getUiModule(String grammar)
  {
    if (ORG_ECLIPSE_XSMP_XSMPCAT.equals(grammar))
    {
      return new SmpUIModule(this);
    }
    return super.getUiModule(grammar);
  }

}
