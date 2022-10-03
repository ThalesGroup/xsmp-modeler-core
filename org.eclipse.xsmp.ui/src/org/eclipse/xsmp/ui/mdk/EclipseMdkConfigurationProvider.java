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

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;
import org.eclipse.xtext.ui.resource.ProjectByResourceProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * A specialization of the IMdkConfigurationUIProvider to use in Eclipse environment
 */
public class EclipseMdkConfigurationProvider implements IMdkConfigurationUIProvider
{

  @Inject
  private IPreferenceStoreAccess preferenceStoreAccess;

  @Inject
  private ProjectByResourceProvider projectProvider;

  @Override
  public Injector getInjector(IProject project)
  {
    final var store = preferenceStoreAccess.getContextPreferenceStore(project);

    return MdkHelper.INSTANCE.getExtension(store);
  }

  @Override
  public <T> T getInstance(Resource context, Class<T> clazz)
  {
    return getInstance(projectProvider.getProjectContext(context), clazz);
  }

  @Override
  public <T> T getInstance(IProject project, Class<T> clazz)
  {
    final var injector = getInjector(project);
    if (injector == null)
    {
      return null;
    }
    return injector.getInstance(clazz);
  }

}
