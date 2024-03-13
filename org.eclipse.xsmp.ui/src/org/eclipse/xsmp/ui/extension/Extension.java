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
package org.eclipse.xsmp.ui.extension;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.xsmp.ui.internal.XsmpActivator;

import com.google.inject.Injector;

public final class Extension
{
  private final String id;

  private final String name;

  private final String description;

  private final ExtensionProvider provider;

  public Extension(IConfigurationElement e) throws InvalidRegistryObjectException, CoreException
  {
    this(e.getAttribute("id"), e.getAttribute("name"), e.getAttribute("description"),
            (ExtensionProvider) e.createExecutableExtension("provider"));
  }

  public Extension(String id, String name, String description, ExtensionProvider provider)
  {
    this.id = id;
    this.name = name;
    this.description = description;
    this.provider = provider;
  }

  public Extension(String id, String name, String description)
  {
    this(id, name, description, languageName -> {
      final var activator = XsmpActivator.getInstance();
      return activator != null ? activator.getInjector(languageName) : null;
    });
  }

  public String getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public Injector getInjector(String languageName)
  {
    return provider.getInjector(languageName);
  }

}
