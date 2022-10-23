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
package org.eclipse.xsmp.tool.smp.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;

import com.google.inject.Singleton;

@Singleton
public class SmpURIConverter extends ExtensibleURIConverterImpl
{

  public SmpURIConverter()
  {
    final var uriMap = getURIMap();
    uriMap.put(URI.createURI("http://www.esa.int/2008/02/Smdl/Catalogue"),
            URI.createURI("http://www.ecss.nl/smp/2019/Smdl/Catalogue"));

    uriMap.put(URI.createURI("http://www.esa.int/2008/02/Core/Types"),
            URI.createURI("http://www.ecss.nl/smp/2019/Core/Types"));

    uriMap.put(URI.createURI("http://www.esa.int/2008/02/Core/Elements"),
            URI.createURI("http://www.ecss.nl/smp/2019/Core/Elements"));

    // try to map ecss.smp.smpcat and ecss.smp.smppkg if present
    final var smpcatUrl = getClass().getResource("/org/eclipse/xsmp/tool/smp/lib/ecss.smp.smpcat");
    if (smpcatUrl != null)
    {
      final var smpcatUri = URI.createURI(smpcatUrl.toString());
      uriMap.put(URI.createURI("http://www.esa.int/2008/02/Smdl"), smpcatUri);
      uriMap.put(URI.createURI("http://www.esa.int/2019/Smdl"), smpcatUri);
      uriMap.put(URI.createURI("ecss.smp.smpcat"), smpcatUri);
    }
    final var smppkgUrl = getClass().getResource("/org/eclipse/xsmp/tool/smp/lib/ecss.smp.smppkg");
    if (smppkgUrl != null)
    {
      uriMap.put(URI.createURI("ecss.smp.smppkg"), URI.createURI(smppkgUrl.toString()));
    }

  }

  @Override
  public URI normalize(URI uri)
  {
    if (uri.isFile())
    {
      return URI.createURI(uri.lastSegment());
    }
    return super.normalize(uri);
  }
}
