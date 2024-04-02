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
package org.eclipse.xsmp.server;

import java.util.ServiceLoader;

import org.eclipse.xsmp.ide.extension.ExtensionManager;
import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider.Registry;
import org.eclipse.xtext.resource.impl.ResourceServiceProviderRegistryImpl;

import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class XsmpResourceServiceProviderServiceLoader implements Provider<Registry>
{

  private final ServiceLoader<ISetup> setupLoader = ServiceLoader.load(ISetup.class);

  private final Registry registry = loadRegistry();

  private Registry loadRegistry()
  {
    final var registry = new ResourceServiceProviderRegistryImpl();
    ExtensionManager.initialize();
    for (final var cp : setupLoader)
    {
      register(cp, registry);
    }

    return registry;
  }

  private Injector register(ISetup cp, Registry registry)
  {
    final var injector = cp.createInjectorAndDoEMFRegistration();

    final var resourceServiceProvider = injector.getInstance(IResourceServiceProvider.class);
    final var extensionProvider = injector.getInstance(FileExtensionProvider.class);
    final var primaryFileExtension = extensionProvider.getPrimaryFileExtension();
    for (final String ext : extensionProvider.getFileExtensions())
    {
      if (registry.getExtensionToFactoryMap().containsKey(ext))
      {
        if (primaryFileExtension.equals(ext))
        {
          registry.getExtensionToFactoryMap().put(ext, resourceServiceProvider);
        }
      }
      else
      {
        registry.getExtensionToFactoryMap().put(ext, resourceServiceProvider);
      }
    }
    return injector;
  }

  @Override
  public IResourceServiceProvider.Registry get()
  {
    return registry;
  }
}
