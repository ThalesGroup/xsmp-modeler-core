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
package org.eclipse.xsmp.profile.esa_cdk.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xsmp.ui.XsmpConstants;
import org.eclipse.xsmp.ui.XsmpcatUiModule;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
public class EsaCdkUiModule extends XsmpcatUiModule
{

  public EsaCdkUiModule(AbstractUIPlugin plugin)
  {
    super(plugin);
  }

  @Override
  public void configureExtensionName(Binder binder)
  {
    binder.bind(String.class).annotatedWith(Names.named(XsmpConstants.EXTENSION_NAME))
            .toInstance("org.eclipse.xsmp.profile.esa_cdk");
  }
}
