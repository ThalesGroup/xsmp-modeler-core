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
package org.eclipse.xsmp.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.xtext.util.DisposableRegistry;
import org.eclipse.xtext.util.IDisposable;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class XsmpExecutorServiceProvider implements Provider<ExecutorService>, IDisposable
{

  private final Map<String, ExecutorService> instanceCache = Collections
          .synchronizedMap(new HashMap<String, ExecutorService>());

  @Inject
  public void registerTo(DisposableRegistry disposableRegistry)
  {
    disposableRegistry.register(this);
  }

  @Override
  public ExecutorService get()
  {
    return get(null);
  }

  public ExecutorService get(String key)
  {
    return instanceCache.computeIfAbsent(key, this::createInstance);
  }

  protected ExecutorService createInstance(String key)
  {
    return Executors.newCachedThreadPool();
  }

  @Override
  public void dispose()
  {
    synchronized (instanceCache)
    {
      for (final ExecutorService executorService : instanceCache.values())
      {
        executorService.shutdown();
      }
      instanceCache.clear();
    }
  }
}