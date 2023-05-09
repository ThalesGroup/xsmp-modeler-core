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
package org.eclipse.xsmp.ui;

import org.osgi.framework.BundleContext;

/**
 * This is the central singleton for the Xsmpcat UI plugin.
 */
public final class XsmpcatUIPlugin extends AbstractXsmpcatUIPlugin
{
  private static XsmpcatUIPlugin instance;

  private static void setInstance(XsmpcatUIPlugin instance)
  {
    XsmpcatUIPlugin.instance = instance;
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

  public static XsmpcatUIPlugin getInstance()
  {
    return instance;
  }

}
