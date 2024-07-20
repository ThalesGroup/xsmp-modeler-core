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
import java.util.Objects;

import org.eclipse.xtext.Constants;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

@ImplementedBy(IExtensionManager.NullExtensionManager.class)
public interface IExtensionManager
{
  IProfile getProfile(String id);

  String getLanguageName();

  Collection<IProfile> getProfiles();

  ITool getTool(String id);

  Collection<ITool> getTools();

  default Injector getInjectorForProfile(String id)
  {
    final var profile = getProfile(id);
    if (profile == null)
    {
      return null;
    }

    return profile.getInjector(getLanguageName());
  }

  default <T> T getInstanceForProfile(String id, Class<T> type)
  {
    final var injector = getInjectorForProfile(id);

    if (injector == null)
    {
      return null;
    }

    return injector.getInstance(type);
  }

  default Injector getInjectorForTool(String id)
  {
    final var tool = getTool(id);
    if (tool == null)
    {
      return null;
    }

    return tool.getInjector(getLanguageName());
  }

  default <T> T getInstanceForTool(String id, Class<T> type)
  {
    final var injector = getInjectorForTool(id);

    if (injector == null)
    {
      return null;
    }

    return injector.getInstance(type);
  }

  default <T> Collection<T> getInstancesForTools(Collection<String> ids, Class<T> type)
  {
    return ids.stream().map(id -> getInstanceForTool(id, type)).filter(Objects::nonNull).toList();
  }

  public static class NullExtensionManager implements IExtensionManager
  {
    @Inject
    @Named(Constants.LANGUAGE_NAME)
    private String languageName;

    @Override
    public IProfile getProfile(String id)
    {
      return null;
    }

    @Override
    public String getLanguageName()
    {
      return languageName;
    }

    @Override
    public Collection<IProfile> getProfiles()
    {
      return Collections.emptyList();
    }

    @Override
    public ITool getTool(String id)
    {
      return null;
    }

    @Override
    public Collection<ITool> getTools()
    {
      return Collections.emptyList();
    }

  }
}
