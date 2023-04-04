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
package org.eclipse.xsmp.tool.smp.xlink.impl;

import java.util.Map.Entry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;

public class SimpleLinkRefImplCustom extends SimpleLinkRefImpl
{
  /** The HTTP URI scheme. */
  private static final String SCHEME_HTTP = "http"; //$NON-NLS-1$

  @Override
  public EObject eResolveProxy(InternalEObject proxy)
  {

    final var resource = eResource();
    if (resource != null)
    {
      final var rs = resource.getResourceSet();
      if (rs != null)
      {
        final var uri = rs.getURIConverter().getURIMap().get(proxy.eProxyURI().trimFragment());

        if (uri != null)
        {
          proxy.eSetProxyURI(uri.appendFragment(proxy.eProxyURI().fragment()));
        }

      }
    }

    return super.eResolveProxy(proxy);
  }

  @Override
  public String getHref()
  {
    final var ref = getReference();
    if (ref != null)
    {
      if (ref.eIsProxy())
      {
        // return just the URI that the (unresolved) proxy points to
        return ((InternalEObject) ref).eProxyURI().toString();

      }
      final var refResource = ref.eResource();
      final var locResource = eResource();
      if (locResource != null && locResource.equals(refResource))
      {
        // reference within the same document
        final var id = EcoreUtil.getID(ref);
        return "#" + id;
      }
      if (refResource == null)
      {
        // last fall back: we don't have a resource: return the stringified reference
        return ref.toString();
      }
      // reference to another document
      var uri = refResource.getURI();

      String document;
      if (SCHEME_HTTP.equals(uri.scheme()))
      {
        // keep well-known URIs untouched
        document = uri.toString();
      }
      else
      {
        // default is to use the filename only
        document = uri.lastSegment();
      }

      // try to deresolve using the registered URI converters (e.g. file://... -> http://...)
      final var uriMap = refResource.getResourceSet().getURIConverter().getURIMap();
      for (final Entry<URI, URI> entry : uriMap.entrySet())
      {
        final var curUri = entry.getValue();
        if (uri.equals(curUri))
        {
          uri = entry.getKey();
          document = uri.toString();
        }
      }

      // try to deresolve using the registered URI handler (e.g. file://... -> http://...)
      if (locResource instanceof XMLResource)
      {
        final var xmlResource = (XMLResource) locResource;
        final var uriHandlerObj = xmlResource.getDefaultLoadOptions()
                .get(XMLResource.OPTION_URI_HANDLER);
        if (uriHandlerObj instanceof XMLResource.URIHandler)
        {
          final var uriHandler = (XMLResource.URIHandler) uriHandlerObj;

          final var deresolved = uriHandler.deresolve(uri);
          if (!uri.equals(deresolved))
          {
            uri = deresolved;
            document = uri.toString();
          }
        }
      }

      // add ID
      final var id = EcoreUtil.getID(ref);
      return document + "#" + id;
    }

    return null;
  }

} // SimpleLinkRefImplCustom
