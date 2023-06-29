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

import org.eclipse.xtext.ide.server.ProjectManager;
import org.eclipse.xtext.ide.server.ServerModule;

public class XsmpcatServerModule extends ServerModule
{

  @Override
  protected void configure()
  {
    super.configure();
    bind(ProjectManager.class).to(XsmpcatProjectManager.class);
  }
}
