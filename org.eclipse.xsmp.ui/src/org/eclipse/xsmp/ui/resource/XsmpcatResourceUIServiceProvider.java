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

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.core.JavaModelManager;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.ui.resource.DefaultResourceUIServiceProvider;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class XsmpcatResourceUIServiceProvider extends DefaultResourceUIServiceProvider
{
  @Inject
  public XsmpcatResourceUIServiceProvider(IResourceServiceProvider delegate)
  {
    super(delegate);
  }

  @Override
  public boolean canHandle(URI uri, IStorage storage)
  {
    var result = super.canHandle(uri, storage);
    // only handle local files that are on the classpath
    if (result && storage instanceof IResource)
    {
      final var file = (IResource) storage;

      result = JavaModelManager.determineIfOnClasspath(file,
              JavaCore.create(file.getProject())) != null;
    }

    return result;
  }

}
