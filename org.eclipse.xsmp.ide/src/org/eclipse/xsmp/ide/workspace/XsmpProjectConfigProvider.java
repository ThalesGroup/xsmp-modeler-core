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
package org.eclipse.xsmp.ide.workspace;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.workspace.IProjectConfigProvider;
import org.eclipse.xtext.workspace.ProjectConfigAdapter;
import org.eclipse.xtext.workspace.UnknownProjectConfig;

public class XsmpProjectConfigProvider implements IProjectConfigProvider
{
  @Override
  public IXsmpProjectConfig getProjectConfig(ResourceSet context)
  {
    final var adapter = ProjectConfigAdapter.findInEmfObject(context);
    final var config = adapter != null ? adapter.getProjectConfig() : null;

    if (config == null || config instanceof UnknownProjectConfig)
    {
      return null;
    }

    if (config instanceof IXsmpProjectConfig)
    {
      return (IXsmpProjectConfig) config;
    }
    // return a config with default values
    return null;
  }

}
