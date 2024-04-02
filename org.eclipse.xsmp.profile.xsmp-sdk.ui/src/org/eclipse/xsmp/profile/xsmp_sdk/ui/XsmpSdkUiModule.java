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
package org.eclipse.xsmp.profile.xsmp_sdk.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xsmp.extension.IProfile;
import org.eclipse.xsmp.ui.XsmpcatUiModule;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
public class XsmpSdkUiModule extends XsmpcatUiModule
{

  public XsmpSdkUiModule(AbstractUIPlugin plugin)
  {
    super(plugin);
  }

  public Class< ? extends IProfile> bindIProfile()
  {
    return XsmpSdkProfile.class;
  }
}
