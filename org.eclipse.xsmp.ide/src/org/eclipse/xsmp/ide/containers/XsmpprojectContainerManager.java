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
package org.eclipse.xsmp.ide.containers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.ProjectDescriptionBasedContainerManager;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.workspace.IProjectConfigProvider;

import com.google.inject.Inject;

public class XsmpprojectContainerManager
        extends ProjectDescriptionBasedContainerManager
{
  @Inject
  private IProjectConfigProvider configProvider;

  @Override
  public List<IContainer> getVisibleContainers(IResourceDescription desc,
          IResourceDescriptions resourceDescriptions)
  {
    final var descriptions = getChunkedResourceDescriptions(resourceDescriptions);
    if (descriptions == null)
    {
      throw new IllegalArgumentException("Expected " + ChunkedResourceDescriptions.class.getName());
    }
    final List<IContainer> allContainers = new ArrayList<>();

    final var config = configProvider.getProjectConfig(descriptions.getResourceSet());
    if (config != null)
    {
      for (final var project : config.getWorkspaceConfig().getProjects())
      {
        allContainers.add(createContainer(resourceDescriptions, descriptions, project.getName()));
      }
    }
    return allContainers;
  }

}
