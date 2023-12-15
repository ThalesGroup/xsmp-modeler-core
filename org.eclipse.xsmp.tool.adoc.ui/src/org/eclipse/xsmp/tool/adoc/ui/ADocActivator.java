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
package org.eclipse.xsmp.tool.adoc.ui;

import org.eclipse.xsmp.tool.adoc.ADocRuntimeModule;
import org.eclipse.xsmp.ui.AbstractXsmpActivator;
import org.osgi.framework.BundleContext;

/**
 * This is the central singleton for the ADoc UI plugin.
 */
public final class ADocActivator extends AbstractXsmpActivator
{

  public static final String PLUGIN_ID = "org.eclipse.xsmp.tool.adoc.ui";

  private static ADocActivator instance;

  public static ADocActivator getInstance()
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
  protected com.google.inject.Module getRuntimeModule(String grammar)
  {
    if (ORG_ECLIPSE_XSMP_XSMPCAT.equals(grammar))
    {
      return new ADocRuntimeModule();
    }
    return super.getRuntimeModule(grammar);
  }

  @Override
  protected com.google.inject.Module getUiModule(String grammar)
  {
    if (ORG_ECLIPSE_XSMP_XSMPCAT.equals(grammar))
    {
      return new ADocUIModule(this);
    }
    return super.getUiModule(grammar);
  }
}
