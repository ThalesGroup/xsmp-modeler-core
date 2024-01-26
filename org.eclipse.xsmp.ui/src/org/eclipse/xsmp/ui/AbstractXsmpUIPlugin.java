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
import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.xsmp.XsmpcatRuntimeModule;
import org.eclipse.xsmp.XsmpcoreRuntimeModule;
import org.eclipse.xsmp.ui.internal.XsmpActivator;
import org.eclipse.xtext.ui.shared.SharedStateModule;
import org.eclipse.xtext.util.Modules2;
import org.osgi.framework.BundleContext;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * This is the central singleton for the Xsmpcat UI plugin.
 */
public class AbstractXsmpUIPlugin extends EclipseUIPlugin
{

  private static final Logger logger = Logger.getLogger(AbstractXsmpUIPlugin.class);

  private final Map<String, Injector> injectors = Collections
          .synchronizedMap(Maps.<String, Injector> newHashMapWithExpectedSize(1));

  @Override
  public void stop(BundleContext context) throws Exception
  {
    injectors.clear();
    super.stop(context);
  }

  public final Injector getInjector(String language)
  {
    synchronized (injectors)
    {
      var injector = injectors.get(language);
      if (injector == null)
      {
        injector = createInjector(language);
        injectors.put(language, injector);
      }
      return injector;
    }
  }

  private Injector createInjector(String language)
  {
    try
    {
      return switch (language)
      {
        case XsmpActivator.ORG_ECLIPSE_XSMP_XSMPCAT ->
        {
          final var runtimeModule = getXsmpcatRuntimeModule();
          final var sharedStateModule = getSharedStateModule();
          final var uiModule = getXsmpcatUiModule();
          final var mergedModule = Modules2.mixin(runtimeModule, sharedStateModule, uiModule);
          yield Guice.createInjector(mergedModule);
        }
        case XsmpActivator.ORG_ECLIPSE_XSMP_XSMPCORE ->
        {
          final var runtimeModule = getXsmpcoreRuntimeModule();
          final var sharedStateModule = getSharedStateModule();
          final var uiModule = getXsmpcoreUiModule();
          final var mergedModule = Modules2.mixin(runtimeModule, sharedStateModule, uiModule);
          yield Guice.createInjector(mergedModule);
        }
        default -> throw new IllegalArgumentException(language);
      };
    }
    catch (final Exception e)
    {
      logger.error("Failed to create injector for " + language);
      logger.error(e.getMessage(), e);
      throw new RuntimeException("Failed to create injector for " + language, e);
    }
  }

  protected com.google.inject.Module getXsmpcatRuntimeModule()
  {
    return new XsmpcatRuntimeModule();
  }

  protected com.google.inject.Module getXsmpcatUiModule()
  {
    return new XsmpcatUiModule(this);
  }

  protected com.google.inject.Module getXsmpcoreRuntimeModule()
  {
    return new XsmpcoreRuntimeModule();
  }

  protected com.google.inject.Module getXsmpcoreUiModule()
  {
    return new XsmpcoreUiModule(this);
  }

  private com.google.inject.Module getSharedStateModule()
  {
    return new SharedStateModule();
  }

  @Override
  protected Object doGetImage(String key) throws IOException
  {
    var image = super.doGetImage(key);
    if (image == null && !equals(XsmpUIPlugin.getInstance()))
    {
      image = XsmpUIPlugin.getInstance().getImage(key);
    }
    return image;
  }
}
