/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ui.resource;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xsmp.ui.workspace.XsmpEclipseProjectConfigProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.ui.resource.DefaultResourceUIServiceProvider;

import com.google.inject.Inject;

public class XsmpResourceUIServiceProvider extends DefaultResourceUIServiceProvider
{
  @Inject
  public XsmpResourceUIServiceProvider(IResourceServiceProvider delegate)
  {
    super(delegate);
  }

  @Inject
  private XsmpEclipseProjectConfigProvider configProvider;

  @Override
  public boolean canHandle(URI uri, IStorage storage)
  {
    final var result = super.canHandle(uri, storage);

    // only handle local files that are in source folders
    if (result && storage instanceof final IResource file)
    {
      final var config = configProvider.createProjectConfig(file.getProject());

      if (config != null)
      {

        return config.getSourceFolders().isEmpty()
                || config.findSourceFolderContaining(uri) != null;
      }
      return false;
    }

    return result;
  }

}
