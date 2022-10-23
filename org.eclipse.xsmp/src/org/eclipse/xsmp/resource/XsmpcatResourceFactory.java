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
package org.eclipse.xsmp.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.configuration.IConfigurationProvider;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

public class XsmpcatResourceFactory implements IResourceFactory
{

  @Inject
  private IConfigurationProvider configurationProvider;

  @Override
  public Resource createResource(URI uri)
  {
    final var xtextResource = configurationProvider.getInstance(uri, XtextResource.class);
    xtextResource.setURI(uri);
    return xtextResource;
  }

}
