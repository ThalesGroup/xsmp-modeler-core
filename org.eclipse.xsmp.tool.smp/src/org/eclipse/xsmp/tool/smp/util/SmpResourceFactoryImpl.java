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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

public class SmpResourceFactoryImpl extends ResourceFactoryImpl
{
  /**
   * The URI handler responsible for resolving/deresolving URI's in SMDL documents
   */
  private final XMLResource.URIHandler uriHandler = new URIHandlerImpl() {
    @Override
    public URI resolve(URI uri)
    {
      return uri;
    }
  };

  @Override
  public Resource createResource(URI uri)
  {
    final XMLResource result = new XMLResourceImpl(uri);
    result.getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

    result.getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

    result.getDefaultSaveOptions().put(XMLResource.OPTION_URI_HANDLER, uriHandler);

    result.getDefaultLoadOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE,
            Boolean.FALSE);

    result.getDefaultSaveOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE,
            Boolean.FALSE);

    result.getDefaultLoadOptions().put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);

    result.getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA,
            new SmpExtendedMetaData());

    result.getDefaultLoadOptions().put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
    result.getDefaultLoadOptions().put(XMLResource.OPTION_URI_HANDLER, uriHandler);
    return result;
  }

} // SmpResourceFactoryImpl
