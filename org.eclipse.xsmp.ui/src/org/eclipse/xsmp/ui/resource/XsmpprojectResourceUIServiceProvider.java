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
package org.eclipse.xsmp.ui.resource;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.ui.resource.DefaultResourceUIServiceProvider;

import com.google.inject.Inject;

public class XsmpprojectResourceUIServiceProvider extends DefaultResourceUIServiceProvider
{
  @Inject
  public XsmpprojectResourceUIServiceProvider(IResourceServiceProvider delegate)
  {
    super(delegate);
  }

  @Override
  public boolean canHandle(URI uri, IStorage storage)
  {
    final var result = super.canHandle(uri, storage);

    // only handle xsmp.project at the project root
    if (result && storage instanceof final IResource file)
    {
      return file.getParent() instanceof IProject;
    }
    return result;
  }

}
