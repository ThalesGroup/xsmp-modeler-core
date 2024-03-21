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
package org.eclipse.xsmp.tool.smp.cli;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.resource.IResourceDescription.Manager;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.validation.IResourceValidator;

import com.google.inject.Inject;

public class SmpcatResourceServiceProvider implements IResourceServiceProvider
{

  private static final String SMPCAT_FILE_EXTENSION = "smpcat";

  @Inject
  IResourceServiceProvider delegate;

  @Override
  public IResourceValidator getResourceValidator()
  {
    return delegate.getResourceValidator();
  }

  @Override
  public Manager getResourceDescriptionManager()
  {
    return delegate.getResourceDescriptionManager();
  }

  @Override
  public org.eclipse.xtext.resource.IContainer.Manager getContainerManager()
  {
    return delegate.getContainerManager();
  }

  @Override
  public boolean canHandle(URI uri)
  {
    return SMPCAT_FILE_EXTENSION.equals(uri.fileExtension());
  }

  @Override
  public IEncodingProvider getEncodingProvider()
  {
    return delegate.getEncodingProvider();
  }

  @Override
  public <T> T get(Class<T> t)
  {
    return delegate.get(t);
  }

}
