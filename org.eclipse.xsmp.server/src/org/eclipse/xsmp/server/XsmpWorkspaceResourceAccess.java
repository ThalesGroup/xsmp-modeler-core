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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.util.Exceptions;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

public class XsmpWorkspaceResourceAccess implements IReferenceFinder.IResourceAccess
{

  private final XsmpWorkspaceManager workspaceManager;

  /**
   * @param workspaceManager
   *          the workspace manager
   */
  public XsmpWorkspaceResourceAccess(XsmpWorkspaceManager workspaceManager)
  {
    this.workspaceManager = workspaceManager;
  }

  @Override
  public <R> R readOnly(URI targetURI, IUnitOfWork<R, ResourceSet> work)
  {
    return workspaceManager.doRead(targetURI, (document, resource) -> {
      if (resource == null)
      {
        return null;
      }
      try
      {
        return work.exec(resource.getResourceSet());
      }
      catch (final Exception e)
      {
        return Exceptions.throwUncheckedException(e);
      }
    });
  }

}