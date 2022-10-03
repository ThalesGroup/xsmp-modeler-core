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
package org.eclipse.xsmp.ui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.xsmp.ui.internal.XsmpActivator;

/**
 * This is the central singleton for the Xsmpcat UI plugin.
 */
public final class XsmpcatUIPlugin extends XsmpActivator implements ResourceLocator
{
  /**
   * The EMF plug-in APIs are all delegated to this helper, so that code can be shared by plug-in
   * implementations with a different platform base class (e.g. Plugin).
   */
  protected EMFPlugin.InternalHelper helper;

  /**
   * Default constructor
   */
  public XsmpcatUIPlugin()
  {

    helper = new EMFPlugin.InternalHelper(this);
  }

  public static XsmpcatUIPlugin getInstance()
  {
    return (XsmpcatUIPlugin) XsmpActivator.getInstance();
  }

  @Override
  public URL getBaseURL()
  {
    return helper.getBaseURL();
  }

  @Override
  public Object getImage(String key)
  {
    try
    {
      return doGetImage(key);
    }
    catch (final MalformedURLException exception)
    {
      throw new WrappedException(exception);
    }
    catch (final IOException exception)
    {
      throw new MissingResourceException(CommonPlugin.INSTANCE
              .getString("_UI_StringResourceNotFound_exception", new Object[]{key }),
              getClass().getName(), key);
    }
  }

  /**
   * Does the work of fetching the image associated with the key. It ensures that the image exists.
   *
   * @param key
   *          the key of the image to fetch.
   * @exception IOException
   *              if an image doesn't exist.
   * @return the description of the image associated with the key.
   */
  protected Object doGetImage(String key) throws IOException
  {
    return helper.getImage(key);
  }

  @Override
  public String getString(String key)
  {
    return helper.getString(key, true);
  }

  @Override
  public String getString(String key, boolean translate)
  {
    return helper.getString(key, translate);
  }

  @Override
  public String getString(String key, Object[] substitutions)
  {
    return helper.getString(key, substitutions, true);
  }

  @Override
  public String getString(String key, Object[] substitutions, boolean translate)
  {
    return helper.getString(key, substitutions, translate);
  }

}
