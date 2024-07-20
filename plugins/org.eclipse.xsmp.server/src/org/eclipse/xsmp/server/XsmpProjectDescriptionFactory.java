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
package org.eclipse.xsmp.server;

import java.util.ArrayList;

import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.ide.server.DefaultProjectDescriptionFactory;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.workspace.IProjectConfig;

public class XsmpProjectDescriptionFactory extends DefaultProjectDescriptionFactory
{
  @Override
  public ProjectDescription getProjectDescription(IProjectConfig config)
  {
    final var description = super.getProjectDescription(config);

    if (config instanceof final IXsmpProjectConfig cfg)
    {
      description.setDependencies(new ArrayList<>(cfg.getDependencies()));
    }

    return description;
  }
}
