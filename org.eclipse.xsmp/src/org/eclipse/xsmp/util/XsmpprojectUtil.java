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
package org.eclipse.xsmp.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.model.xsmp.ProjectReference;

public class XsmpprojectUtil
{

  public Set<Project> getDependencies(Project project)
  {
    final var result = new HashSet<Project>();
    collectDependencies(result, project);
    return result;
  }

  private void collectDependencies(Set<Project> dependencies, Project project)
  {
    if (project != null && dependencies.add(project))
    {
      project.getMember().stream().filter(ProjectReference.class::isInstance)
              .map(m -> ((ProjectReference) m).getProject())
              .forEach(p -> collectDependencies(dependencies, p));
    }
  }
}
