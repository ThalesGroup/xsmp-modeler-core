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
package org.eclipse.xsmp.lib;

import java.net.URL;

import org.eclipse.emf.common.util.URI;

public class ECSSModelLibrary
{
  public static final URL XSMPCAT_URL = get("ecss.smp.xsmpcat");

  public static final URL SMPCAT_URL = get("ecss.smp.smpcat");

  public static final URL SMPPKG_URL = get("ecss.smp.smppkg");

  public static final URI XSMPCAT_URI = URI.createURI(XSMPCAT_URL.toString());

  public static final URI SMPCAT_URI = URI.createURI(SMPCAT_URL.toString());

  public static final URI SMPPKG_URI = URI.createURI(SMPPKG_URL.toString());

  /**
   * Hide constructor.
   */
  private ECSSModelLibrary()
  {
  }

  /**
   * Returns a resource from this bundle.
   *
   * @param path
   *          file path, relative src/main/resources.
   * @return the resource
   */
  private static URL get(final String path)
  {

    final var url = ECSSModelLibrary.class.getResource(path);

    return url;
  }
}
