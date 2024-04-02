/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.extension;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xtext.ISetup;

import com.google.common.collect.Maps;
import com.google.inject.Injector;

public abstract class Extension implements IExtension
{
  public static final String ORG_ECLIPSE_XSMP_XSMPCORE = XsmpConstants.ORG_ECLIPSE_XSMP_XSMPCORE;

  public static final String ORG_ECLIPSE_XSMP_XSMPCAT = XsmpConstants.ORG_ECLIPSE_XSMP_XSMPCAT;

  private final Map<String, Injector> injectors = Collections
          .synchronizedMap(Maps.<String, Injector> newHashMapWithExpectedSize(1));

  /**
   * Get the list of supported languages supported by this extension
   *
   * @return supported languages
   */
  protected abstract Collection<String> getSupportedLanguages();

  protected void initialize()
  {
    getSupportedLanguages().forEach(this::getInjector);
  }

  @Override
  public Injector getInjector(String language)
  {
    synchronized (injectors)
    {
      return injectors.computeIfAbsent(language, l -> {
        final var setup = createSetup(l);
        if (setup != null)
        {
          return setup.createInjectorAndDoEMFRegistration();
        }
        return null;
      });
    }
  }

  protected ISetup createSetup(String language)
  {
    throw new UnsupportedOperationException(language);
  }
}
