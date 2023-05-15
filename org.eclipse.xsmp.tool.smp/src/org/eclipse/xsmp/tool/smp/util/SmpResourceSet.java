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
package org.eclipse.xsmp.tool.smp.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.google.inject.Inject;

/**
 * A specialized ResourceSet to load smpcat resources.
 */
public class SmpResourceSet extends ResourceSetImpl
{

  @Inject
  public SmpResourceSet(SmpURIConverter smpUriConverter)
  {
    setURIConverter(smpUriConverter);
    getResourceFactoryRegistry().getExtensionToFactoryMap().put("smpcat",
            new SmpResourceFactoryImpl());
  }

  @Override
  public Resource getResource(URI uri, boolean loadOnDemand)
  {
    getURIConverter().getURIMap().put(URI.createURI(uri.lastSegment()), uri);
    return super.getResource(uri, loadOnDemand);
  }
}
