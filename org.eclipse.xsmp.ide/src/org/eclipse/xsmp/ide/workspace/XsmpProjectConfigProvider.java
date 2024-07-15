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
package org.eclipse.xsmp.ide.workspace;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.workspace.IWorkspaceConfig;
import org.eclipse.xtext.workspace.ProjectConfigProvider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class XsmpProjectConfigProvider extends ProjectConfigProvider
{
  @Override
  public IXsmpProjectConfig getProjectConfig(ResourceSet context)
  {
    return super.getProjectConfig(context) instanceof final IXsmpProjectConfig xsmpConfig
            ? xsmpConfig
            : null;
  }

  @Inject
  private Provider<XtextResourceSet> resourceSetProvider;

  public IXsmpProjectConfig createProjectConfig(URI uri, IWorkspaceConfig workspaceConfig)
  {
    final var rs = resourceSetProvider.get();
    final var resource = rs.getResource(uri, true);

    final var project = (Project) resource.getContents().get(0);
    return new XsmpProjectConfig(project, workspaceConfig);
  }

}
