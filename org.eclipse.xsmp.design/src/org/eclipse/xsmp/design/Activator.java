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
package org.eclipse.xsmp.design;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{
  // The plug-in ID
  public static final String PLUGIN_ID = "org.eclipse.xsmp.design";

  private final Set<Viewpoint> viewpoints = new HashSet<>();

  @Override
  public void start(BundleContext context) throws Exception
  {
    super.start(context);
    viewpoints.addAll(ViewpointRegistry.getInstance()
            .registerFromPlugin(PLUGIN_ID + "/description/xsmp.odesign"));
  }

  @Override
  public void stop(BundleContext context) throws Exception
  {

    for (final Viewpoint viewpoint : viewpoints)
    {
      ViewpointRegistry.getInstance().disposeFromPlugin(viewpoint);
    }
    viewpoints.clear();

    super.stop(context);
  }

}
