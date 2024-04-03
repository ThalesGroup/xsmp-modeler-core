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
package org.eclipse.xsmp.scoping;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.extension.IExtensionManager;
import org.eclipse.xsmp.model.xsmp.XsmpFactory;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.SelectableBasedScope;

import com.google.common.base.Predicate;
import com.google.inject.Inject;

public class XsmpprojectGlobalScopeProvider extends DefaultGlobalScopeProvider
{
  @Inject
  private IResourceDescription.Manager resourceDescriptionManager;

  @Inject
  private IExtensionManager extensionManager;

  private IResourceDescription description = null;

  private Resource resource;

  /**
   * Initialize a scope with all available extensions
   *
   * @param ignoreCase
   * @param type
   * @param filter
   * @return
   */
  private IScope getParentScope(boolean ignoreCase, EClass type,
          Predicate<IEObjectDescription> filter)
  {
    if (description == null)
    {
      resource = new ResourceImpl(XsmpConstants.XSMP_PROJECT_EXTENSIONS_URI);

      for (final var profile : extensionManager.getProfiles())
      {
        final var p = XsmpFactory.eINSTANCE.createProfile();
        p.setName(profile.getId());
        p.setDescription(profile.getDescription());

        resource.getContents().add(p);

      }
      for (final var tool : extensionManager.getTools())
      {
        final var p = XsmpFactory.eINSTANCE.createTool();
        p.setName(tool.getId());
        p.setDescription(tool.getDescription());
        resource.getContents().add(p);
      }

      description = resourceDescriptionManager.getResourceDescription(resource);

    }
    if (resource.getContents().isEmpty())
    {
      return IScope.NULLSCOPE;
    }
    return SelectableBasedScope.createScope(IScope.NULLSCOPE, description, filter, type,
            ignoreCase);
  }

  @Override
  protected IScope getScope(final Resource context, boolean ignoreCase, EClass type,
          Predicate<IEObjectDescription> filter)
  {
    return getScope(getParentScope(ignoreCase, type, filter), context, ignoreCase, type, filter);
  }

}
