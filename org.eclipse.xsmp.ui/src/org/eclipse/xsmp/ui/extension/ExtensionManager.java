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
package org.eclipse.xsmp.ui.extension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xsmp.ui.XsmpConstants;
import org.eclipse.xsmp.ui.editor.model.XsmpPreferenceAccess;

public class ExtensionManager
{

  private ExtensionManager()
  {
    // private constructor
  }

  private static final Map<String, Extension> tools = new HashMap<>();
  static
  {
    for (final IConfigurationElement cfg : Platform.getExtensionRegistry()
            .getConfigurationElementsFor("org.eclipse.xsmp.ui.tool"))
    {
      try
      {
        final var ext = new Extension(cfg);
        tools.put(ext.getId(), ext);
      }
      catch (final Exception e)
      {
        Logger.getLogger(ExtensionManager.class).error(cfg, e);
      }
    }
  }

  public static List<Extension> getTools()
  {
    return new ArrayList<>(tools.values());
  }

  public static Extension getTool(String id)
  {
    return tools.get(id);
  }

  public static Collection<String> getToolsIds(IPreferenceStore preferenceStore)
  {
    return getIds(preferenceStore.getString(XsmpPreferenceAccess.PREF_TOOLS));
  }

  public static List<Extension> getTools(IPreferenceStore preferenceStore)
  {
    final var ids = getIds(preferenceStore.getString(XsmpPreferenceAccess.PREF_TOOLS));

    return ids.stream().map(ExtensionManager::getTool).filter(Objects::nonNull).toList();

  }

  public static Collection<String> getDefaultToolsIds(IPreferenceStore preferenceStore)
  {

    return getIds(preferenceStore.getDefaultString(XsmpPreferenceAccess.PREF_TOOLS));
  }

  private static Collection<String> getIds(String value)
  {
    final var ids = value.split(",");
    final var result = new ArrayList<String>();
    for (var id : ids)
    {
      id = id.strip();
      if (id.isEmpty())
      {
        continue;
      }
      result.add(id);
    }
    return result;
  }

  private static final Map<String, Extension> profiles = new HashMap<>();
  static
  {
    // add default profile
    profiles.put(XsmpConstants.DEFAULT_PROFILE_NAME,
            new Extension(XsmpConstants.DEFAULT_PROFILE_NAME, "Default", ""));

    for (final IConfigurationElement cfg : Platform.getExtensionRegistry()
            .getConfigurationElementsFor("org.eclipse.xsmp.ui.profile"))
    {
      try
      {
        final var ext = new Extension(cfg);

        profiles.put(ext.getId(), ext);
      }
      catch (final Exception e)
      {
        Logger.getLogger(ExtensionManager.class).error(cfg, e);
      }
    }

  }

  public static List<Extension> getProfiles()
  {
    return new ArrayList<>(profiles.values());
  }

  public static Extension getProfile(String id)
  {
    return profiles.get(id);
  }

  public static String getProfileId(IPreferenceStore preferenceStore)
  {
    String id;
    if (preferenceStore.contains(XsmpPreferenceAccess.PREF_MDK))
    {
      id = preferenceStore.getString(XsmpPreferenceAccess.PREF_MDK).strip();
    }
    else
    {
      id = preferenceStore.getString(XsmpPreferenceAccess.PREF_PROFILE).strip();
    }
    return id;
  }

  public static Extension getProfile(IPreferenceStore preferenceStore)
  {
    final var id = getProfileId(preferenceStore);

    return getProfile(id);
  }

}
