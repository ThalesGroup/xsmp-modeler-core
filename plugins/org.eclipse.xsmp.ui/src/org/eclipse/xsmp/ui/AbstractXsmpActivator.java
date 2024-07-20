/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ui;

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.ui.plugin.AbstractUIPlugin;
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
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass.
 */
public class AbstractXsmpActivator extends AbstractUIPlugin
{

  public static final String PLUGIN_ID = "org.eclipse.xsmp.ui";

  public static final String ORG_ECLIPSE_XSMP_XSMPCORE = XsmpActivator.ORG_ECLIPSE_XSMP_XSMPCORE;

  public static final String ORG_ECLIPSE_XSMP_XSMPCAT = XsmpActivator.ORG_ECLIPSE_XSMP_XSMPCAT;

  private static final Logger logger = Logger.getLogger(AbstractXsmpActivator.class);

  private final Map<String, Injector> injectors = Collections
          .synchronizedMap(Maps.<String, Injector> newHashMapWithExpectedSize(1));

  @Override
  public void stop(BundleContext context) throws Exception
  {
    injectors.clear();
    super.stop(context);
  }

  public Injector getInjector(String language)
  {
    synchronized (injectors)
    {
      return injectors.computeIfAbsent(language, this::createInjector);
    }
  }

  protected Injector createInjector(String language)
  {
    try
    {
      final var runtimeModule = getRuntimeModule(language);
      final var sharedStateModule = getSharedStateModule();
      final var uiModule = getUiModule(language);
      final var mergedModule = Modules2.mixin(runtimeModule, sharedStateModule, uiModule);
      return Guice.createInjector(mergedModule);
    }
    catch (final Exception e)
    {
      logger.error("Failed to create injector for " + language);
      logger.error(e.getMessage(), e);
      throw new RuntimeException("Failed to create injector for " + language, e);
    }
  }

  protected com.google.inject.Module getRuntimeModule(String grammar)
  {
    if (ORG_ECLIPSE_XSMP_XSMPCORE.equals(grammar))
    {
      return new XsmpcoreRuntimeModule();
    }
    if (ORG_ECLIPSE_XSMP_XSMPCAT.equals(grammar))
    {
      return new XsmpcatRuntimeModule();
    }
    throw new IllegalArgumentException(grammar);
  }

  protected com.google.inject.Module getUiModule(String grammar)
  {
    if (ORG_ECLIPSE_XSMP_XSMPCORE.equals(grammar))
    {
      return new XsmpcoreUiModule(this);
    }
    if (ORG_ECLIPSE_XSMP_XSMPCAT.equals(grammar))
    {
      return new XsmpcatUiModule(this);
    }
    throw new IllegalArgumentException(grammar);
  }

  protected com.google.inject.Module getSharedStateModule()
  {
    return new SharedStateModule();
  }

}
