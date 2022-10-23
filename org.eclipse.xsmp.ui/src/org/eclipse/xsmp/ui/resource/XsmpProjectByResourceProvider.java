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
package org.eclipse.xsmp.ui.resource;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.common.types.xtext.ui.JdtAwareProjectByResourceProvider;

public class XsmpProjectByResourceProvider extends JdtAwareProjectByResourceProvider
{
  /**
   * Return the project context for the given URI.
   */
  public IProject getProjectContext(URI uri)
  {
    if (uri.isPlatformResource())
    {
      final var projectName = URI.decode(uri.segment(1));
      final var project = getWorkspace().getRoot().getProject(projectName);
      if (project.exists() && project.isAccessible())
      {
        return project;
      }
    }
    return null;
  }
}
