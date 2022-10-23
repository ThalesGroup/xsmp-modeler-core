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
package org.eclipse.xsmp.ui.configuration;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.XsmpcatConstants;
import org.eclipse.xsmp.ui.extension.Extension;
import org.eclipse.xsmp.ui.resource.XsmpProjectByResourceProvider;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

/**
 * A specialization of the IConfigurationUIProvider to use in Eclipse environment
 */
public class XsmpcatConfigurationUIProvider implements IConfigurationUIProvider
{
  @Inject
  @Named(XsmpcatConstants.EXTENSION_NAME)
  private String extensionName;

  @Inject
  private IPreferenceStoreAccess preferenceStoreAccess;

  @Inject
  private XsmpProjectByResourceProvider projectProvider;

  @Inject
  private Injector injector;

  @Override
  public Injector getInjector(IProject project)
  {
    final var preferenceStore = preferenceStoreAccess.getContextPreferenceStore(project);

    final var id = Extension.getProfileId(preferenceStore);

    final var profile = Extension.getProfile(id);

    if (profile != null)
    {
      final var profileInjector = profile.getInjector();

      if (profileInjector != null)
      {
        return profileInjector;
      }
    }
    return injector;
  }

  @Override
  public <T> T getInstance(URI context, Class<T> clazz)
  {
    return getInstance(projectProvider.getProjectContext(context), clazz);
  }

  protected <T> T getInstance(IProject project, Class<T> clazz)
  {
    return getInjector(project).getInstance(clazz);
  }

  @Override
  public boolean isEnabledFor(Resource context)
  {
    final var project = projectProvider.getProjectContext(context);
    final var preferenceStore = preferenceStoreAccess.getContextPreferenceStore(project);

    return extensionName.equals(Extension.getProfileId(preferenceStore))
            || Extension.getToolsIds(preferenceStore).contains(extensionName);
  }

}
