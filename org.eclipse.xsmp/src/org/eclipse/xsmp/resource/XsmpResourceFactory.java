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
import org.eclipse.xsmp.services.IXsmpServiceProvider;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

public class XsmpResourceFactory implements IResourceFactory
{

  @Inject
  private IXsmpServiceProvider xsmpServiceProvider;

  @Override
  public Resource createResource(URI uri)
  {
    final var xtextResource = xsmpServiceProvider.getInstance(uri, XtextResource.class);
    xtextResource.setURI(uri);
    return xtextResource;
  }

}
