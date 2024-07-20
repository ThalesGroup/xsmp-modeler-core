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
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xtext.ui.containers.AbstractAllContainersState;

import com.google.inject.Inject;

/**
 * A specific a Projects state for XSMP that is initialized by default by xsmp.project file.
 * In case the xsmp.project is not found it fallback on Java Projects state to be backward
 * compatible with previous version
 */
public class XsmpProjectsState extends AbstractAllContainersState
{
  @Inject
  private XsmpProjectsStateHelper projectsHelper;

  @Override
  protected String doInitHandle(URI uri)
  {
    return projectsHelper.initHandle(uri);

  }

  @Override
  protected Collection<URI> doInitContainedURIs(String containerHandle)
  {
    return projectsHelper.initContainedURIs(containerHandle);
  }

  @Override
  protected List<String> doInitVisibleHandles(String handle)
  {
    return projectsHelper.initVisibleHandles(handle);
  }

  @Override
  protected boolean isAffectingContainerState(IResourceDelta delta)
  {
    final var resource = delta.getResource();
    return super.isAffectingContainerState(delta) || resource.getParent() instanceof IProject
            && XsmpConstants.XSMP_PROJECT_FILENAME.equals(resource.getName());
  }
}
