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
package org.eclipse.xsmp.ui.containers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.ui.workspace.XsmpEclipseProjectConfigProvider;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.containers.AbstractAllContainersState;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class XsmpprojectProjectsState extends AbstractAllContainersState
{

  private static final Logger log = Logger.getLogger(XsmpprojectProjectsState.class);

  @Inject
  private com.google.inject.Provider<XsmpEclipseProjectConfigProvider> provider;

  @Override
  protected String doInitHandle(URI uri)
  {
    if (!uri.isPlatform())
    {
      return null;
    }
    final var file = getWorkspaceRoot().getFile(new Path(uri.toPlatformString(true)));
    if (file == null)
    {
      return null;
    }
    final var project = file.getProject();

    return project.getName();
  }

  @Override
  protected Collection<URI> doInitContainedURIs(String containerHandle)
  {
    try
    {
      final var projectPath = new Path(null, containerHandle).makeAbsolute();
      if (projectPath.segmentCount() != 1)
      {
        return Collections.emptySet();
      }
      final var project = getWorkspaceRoot().getProject(containerHandle);
      if (project != null && isAccessibleXtextProject(project))
      {

        final var file = getMapper()
                .getUri(project.getFile(XsmpConstants.XSMP_PROJECT_FILENAME + ""));
        if (file != null)
        {
          return Collections.singleton(file);
        }

      }
    }
    catch (final IllegalArgumentException e)
    {
      if (log.isDebugEnabled())
      {
        log.debug("Cannot init contained URIs for containerHandle '" + containerHandle + "'", e);
      }
    }
    return Collections.emptyList();
  }

  @Override
  protected List<String> doInitVisibleHandles(String handle)
  {
    try
    {
      final var project = getWorkspaceRoot().getProject(handle);
      if (isAccessibleXtextProject(project))
      {
        final List<String> result = Lists.newArrayList();

        for (final var dependency : getWorkspaceRoot().getProjects())
        {
          if (isAccessibleXtextProject(dependency))
          {
            result.add(dependency.getName());
          }
        }

        return result;
      }
    }
    catch (final IllegalArgumentException e)
    {
      if (log.isDebugEnabled())
      {
        log.debug("Cannot init visible handles for containerHandle '" + handle + "'", e);
      }
    }
    return Collections.emptyList();
  }

  private boolean isAccessibleXtextProject(IProject p)
  {
    return p != null && XtextProjectHelper.hasNature(p) && XtextProjectHelper.hasBuilder(p);
  }

  @Override
  protected boolean isAffectingContainerState(IResourceDelta delta)
  {
    final var resource = delta.getResource();

    if (resource.getParent() instanceof final IProject project
            && XsmpConstants.XSMP_PROJECT_FILENAME.equals(resource.getName()))
    {
      provider.get().resetProjectConfig(project);
      return true;
    }

    return super.isAffectingContainerState(delta);
  }
}
