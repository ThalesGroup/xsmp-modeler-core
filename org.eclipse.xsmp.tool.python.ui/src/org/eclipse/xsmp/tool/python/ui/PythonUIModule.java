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
package org.eclipse.xsmp.tool.python.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xsmp.extension.ITool;
import org.eclipse.xsmp.ui.XsmpcatUiModule;

public class PythonUIModule extends XsmpcatUiModule
{

  public PythonUIModule(AbstractUIPlugin plugin)
  {
    super(plugin);
  }

  public Class< ? extends ITool> bindITool()
  {
    return PythonTool.class;
  }
}
