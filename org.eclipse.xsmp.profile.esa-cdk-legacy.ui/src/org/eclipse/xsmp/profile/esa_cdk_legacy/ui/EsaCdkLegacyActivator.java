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
package org.eclipse.xsmp.profile.esa_cdk_legacy.ui;

import org.eclipse.xsmp.profile.esa_cdk_legacy.EsaCdkLegacyRuntimeModule;
import org.eclipse.xsmp.ui.AbstractXsmpActivator;
import org.osgi.framework.BundleContext;

/**
 * This is the central singleton for the A CDK Profile UI plugin.
 */
public final class EsaCdkLegacyActivator extends AbstractXsmpActivator
{
  public static final String PLUGIN_ID = "org.eclipse.xsmp.profile.esa_cdk_legacy.ui";

  private static EsaCdkLegacyActivator instance;

  private static void setInstance(EsaCdkLegacyActivator instance)
  {
    EsaCdkLegacyActivator.instance = instance;
  }

  public static EsaCdkLegacyActivator getInstance()
  {
    return instance;
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
      return new EsaCdkLegacyRuntimeModule();
    }
    return super.getRuntimeModule(grammar);
  }

  @Override
  protected com.google.inject.Module getUiModule(String grammar)
  {
    if (ORG_ECLIPSE_XSMP_XSMPCAT.equals(grammar))
    {
      return new EsaCdkLegacyUiModule(this);
    }
    return super.getUiModule(grammar);
  }

}
