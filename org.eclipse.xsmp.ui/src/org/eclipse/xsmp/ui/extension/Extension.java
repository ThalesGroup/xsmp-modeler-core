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
package org.eclipse.xsmp.ui.extension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xsmp.ui.XsmpcatConstants;
import org.eclipse.xsmp.ui.XsmpcatUIPlugin;
import org.eclipse.xsmp.ui.editor.model.XsmpPreferenceAccess;
import org.eclipse.xsmp.ui.internal.XsmpActivator;

import com.google.inject.Injector;

public class Extension
{
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
        Logger.getLogger(Extension.class).error(cfg, e);
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

    return ids.stream().map(Extension::getTool).filter(Objects::nonNull)
            .collect(Collectors.toList());

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
    profiles.put(XsmpcatConstants.DEFAULT_PROFILE_NAME, new Extension(
            XsmpcatConstants.DEFAULT_PROFILE_NAME, "Default", "",
            XsmpcatUIPlugin.getInstance().getInjector(XsmpActivator.ORG_ECLIPSE_XSMP_XSMPCAT)));

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
        Logger.getLogger(Extension.class).error(cfg, e);
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
    var id = preferenceStore.getString(XsmpPreferenceAccess.PREF_PROFILE).strip();
    if (id.isEmpty() && !preferenceStore.contains(XsmpPreferenceAccess.PREF_PROFILE))
    {
      id = preferenceStore.getString(XsmpPreferenceAccess.PREF_MDK).strip();
    }
    return id;
  }

  public static Extension getProfile(IPreferenceStore preferenceStore)
  {
    final var id = getProfileId(preferenceStore);

    return getProfile(id);
  }

  private final String id;

  private final String name;

  private final String description;

  private final Injector injector;

  public Extension(String id, String name, String description, Injector injector)
  {
    this.id = id;
    this.name = name;
    this.description = description;
    this.injector = injector;
  }

  public Extension(IConfigurationElement e) throws InvalidRegistryObjectException, CoreException
  {
    this(e.getAttribute("id"), e.getAttribute("name"), e.getAttribute("decription"),
            (Injector) e.createExecutableExtension("injector"));
  }

  /**
   * @return the id
   */
  public String getId()
  {
    return id;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @return the description
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * @return the injector
   */
  public Injector getInjector()
  {
    return injector;
  }

}