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
package org.eclipse.xsmp.ui.mdk;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;

import com.google.inject.Injector;

public class MdkHelper
{
  public static MdkHelper INSTANCE = new MdkHelper();

  private final String MDK_SETTING = "mdk";

  private final IConfigurationElement[] MDK_EXTENSIONS;

  private final Map<String, Injector> extensionMap = new HashMap<>();

  private MdkHelper()
  {
    MDK_EXTENSIONS = Platform.getExtensionRegistry()
            .getConfigurationElementsFor("org.eclipse.xsmp.ui.mdk");

    for (final IConfigurationElement e : MDK_EXTENSIONS)
    {
      try
      {
        final var injector = (Injector) e.createExecutableExtension("injector");
        if (injector != null)
        {
          extensionMap.put(e.getAttribute("id"), injector);
        }

      }
      catch (final Exception e1)
      {
        e1.printStackTrace();
      }
    }
  }

  public String getSetting()
  {
    return MDK_SETTING;
  }

  public IConfigurationElement[] getExtensions()
  {
    return MDK_EXTENSIONS;
  }

  public String getDefaultValue()
  {
    return "undefined";
  }

  public Injector getExtension(IPreferenceStore preferenceStore)
  {
    return getExtension(preferenceStore.getString(getSetting()));
  }

  public Injector getExtension(String id)
  {
    return extensionMap.get(id);
  }

  public <T> T getInstanceOrDefault(IPreferenceStore preferenceStore, Class<T> clazz,
          T defaultInstance)
  {
    final var ext = getExtension(preferenceStore);
    if (ext == null)
    {
      return defaultInstance;
    }
    return ext.getInstance(clazz);
  }

  public String getTitle(IProject project)
  {
    return MDK_SETTING;

  }

}
