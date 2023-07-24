/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.server;

import org.eclipse.xtext.ide.server.ServerLauncher;

public class XsmpcatServerLauncher extends ServerLauncher
{

  public static void main(String[] args)
  {
    ServerLauncher.launch(XsmpcatServerLauncher.class.getName(), args, new XsmpcatServerModule());
  }
}
