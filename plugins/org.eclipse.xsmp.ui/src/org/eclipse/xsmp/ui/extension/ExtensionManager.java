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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.xsmp.extension.IExtensionManager;
import org.eclipse.xsmp.extension.IProfile;
import org.eclipse.xsmp.extension.ITool;
import org.eclipse.xtext.Constants;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ExtensionManager implements IExtensionManager
{
  private static final Map<String, ITool> tools = loadTools();

  private static Map<String, ITool> loadTools()
  {
    final var result = new HashMap<String, ITool>();
    for (final IConfigurationElement cfg : Platform.getExtensionRegistry()
            .getConfigurationElementsFor("org.eclipse.xsmp.ui.tool"))
    {
      try
      {
        final var ext = (ITool) cfg.createExecutableExtension("class");
        result.put(ext.getId(), ext);
      }
      catch (final Exception e)
      {
        Logger.getLogger(ExtensionManager.class).error(cfg, e);
      }
    }
    return result;
  }

  @Override
  public Collection<ITool> getTools()
  {
    return tools.values();
  }

  @Override
  public ITool getTool(String id)
  {
    if (id == null)
    {
      return null;
    }
    return tools.get(id);
  }

  private static final Map<String, IProfile> profiles = loadProfiles();

  private static Map<String, IProfile> loadProfiles()
  {
    final var result = new HashMap<String, IProfile>();
    for (final IConfigurationElement cfg : Platform.getExtensionRegistry()
            .getConfigurationElementsFor("org.eclipse.xsmp.ui.profile"))
    {
      try
      {
        final var ext = (IProfile) cfg.createExecutableExtension("class");

        result.put(ext.getId(), ext);
      }
      catch (final Exception e)
      {
        Logger.getLogger(ExtensionManager.class).error(cfg, e);
      }
    }
    return result;
  }

  @Override
  public Collection<IProfile> getProfiles()
  {
    return profiles.values();
  }

  @Override
  public IProfile getProfile(String id)
  {
    if (id == null)
    {
      return null;
    }
    return profiles.get(id);
  }

  @Inject
  @Named(Constants.LANGUAGE_NAME)
  private String languageName;

  @Override
  public String getLanguageName()
  {
    return languageName;
  }

}
