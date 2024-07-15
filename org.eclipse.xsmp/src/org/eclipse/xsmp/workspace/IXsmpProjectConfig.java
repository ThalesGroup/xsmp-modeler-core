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
package org.eclipse.xsmp.workspace;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.xtext.workspace.IProjectConfig;

public interface IXsmpProjectConfig extends IProjectConfig
{
  String getProfile();

  Collection<String> getTools();

  Collection<String> getDependencies();

  default Collection<IProjectConfig> getAllDependencies()
  {
    final Set<IProjectConfig> dependencies = new HashSet<>();
    collectProjectDependencies(this, dependencies);
    dependencies.remove(this);
    return dependencies;
  }

  /**
   * Collect all project dependencies. The root project is also included
   *
   * @param project
   *          root project
   * @param dependencies
   *          list of dependencies including root project
   */
  private static void collectProjectDependencies(IProjectConfig project,
          Set<IProjectConfig> dependencies)
  {
    if (project != null && dependencies.add(project)
            && project instanceof final IXsmpProjectConfig xsmpProject)
    {
      for (final var dep : xsmpProject.getDependencies())
      {
        collectProjectDependencies(project.getWorkspaceConfig().findProjectByName(dep),
                dependencies);
      }
    }
  }
}
