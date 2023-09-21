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

import java.util.Set;

import org.eclipse.lsp4j.WorkspaceFolder;
import org.eclipse.xsmp.ide.workspace.XsmpProjectConfig;
import org.eclipse.xtext.ide.server.MultiRootWorkspaceConfigFactory;
import org.eclipse.xtext.workspace.WorkspaceConfig;

public class XsmpMultiProjectWorkspaceConfigFactory extends MultiRootWorkspaceConfigFactory
{

  @Override
  protected void addProjectsForWorkspaceFolder(WorkspaceConfig workspaceConfig,
          WorkspaceFolder workspaceFolder, Set<String> existingNames)
  {
    if (workspaceFolder != null && workspaceFolder.getUri() != null)
    {

      final var project = new XsmpProjectConfig(getUriExtensions().toUri(workspaceFolder.getUri()),
              getUniqueProjectName(workspaceFolder.getName(), existingNames), workspaceConfig);

      workspaceConfig.addProject(project);
    }
  }

}
