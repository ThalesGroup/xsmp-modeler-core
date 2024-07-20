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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.ui.workspace.XsmpEclipseProjectConfigProvider;
import org.eclipse.xtext.ui.containers.AbstractProjectsStateHelper;
import org.eclipse.xtext.workspace.ISourceFolder;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class XsmpProjectsStateHelper extends AbstractProjectsStateHelper
{

  private static final Logger log = Logger.getLogger(XsmpProjectsStateHelper.class);

  @Inject
  private Provider<XsmpEclipseProjectConfigProvider> provider;

  public String initHandle(URI uri)
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
  protected boolean isAccessibleXtextProject(IProject p)
  {
    return super.isAccessibleXtextProject(p)
            && p.getFile(XsmpConstants.XSMP_PROJECT_FILENAME).exists();
  }

  public Collection<URI> initContainedURIs(String containerHandle)
  {
    try
    {
      final var projectPath = new Path(null, containerHandle).makeAbsolute();
      if (projectPath.segmentCount() != 1)
      {
        return Collections.emptyList();
      }
      final var project = getWorkspaceRoot().getProject(containerHandle);
      if (project != null && isAccessibleXtextProject(project))
      {

        final var settings = provider.get().createProjectConfig(project);
        if (settings != null)
        {
          final Set<URI> result = new HashSet<>();
          settings.getSourceFolders().forEach(source -> collectEntries(project, source, result));
          return result;
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

  protected void collectEntries(IProject project, ISourceFolder source, Set<URI> entries)
  {
    final var path = source.getName();
    if (path.isEmpty())
    {
      entries.addAll(getMapper().getAllEntries(project).keySet());
    }
    else
    {
      final var f = project.getFolder(path);
      if (f.exists())
      {
        entries.addAll(getMapper().getAllEntries(f).keySet());
      }
      else
      {
        final var file = project.getFile(path);
        if (file.exists())
        {
          entries.add(getMapper().getUri(file));
        }
      }
    }
  }

  public List<String> initVisibleHandles(String handle)
  {
    try
    {
      final var project = getWorkspaceRoot().getProject(handle);
      if (isAccessibleXtextProject(project))
      {

        final var settings = provider.get().createProjectConfig(project);
        if (settings != null)
        {
          final List<String> result = Lists.newArrayList();
          result.add(handle);
          for (final var dependency : settings.getDependencies())
          {
            if (isAccessibleXtextProject(getWorkspaceRoot().getProject(dependency)))
            {
              result.add(dependency);
            }
          }
          return result;
        }
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
}
