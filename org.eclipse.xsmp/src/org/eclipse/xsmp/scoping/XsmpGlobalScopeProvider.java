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

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.SelectableBasedScope;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpGlobalScopeProvider extends DefaultGlobalScopeProvider
{
  @Inject
  private IResourceDescription.Manager descriptionManager;

  @Inject
  private IResourceFactory resourceFactory;

  private IResourceDescription xsmpcatDescription = null;

  private IResourceDescription getXsmpcatDescription()
  {
    if (xsmpcatDescription == null)
    {
      final var url = getClass().getResource("/org/eclipse/xsmp/lib/ecss.smp.xsmpcat");

      if (url == null)
      {
        throw new IllegalStateException("Unable to load ecss.smp.xsmpcat");
      }

      final var uri = URI.createURI(url.toString());

      final var resource = resourceFactory.createResource(uri);
      try
      {
        resource.load(url.openStream(), null);
      }
      catch (final IOException e)
      {
        throw new IllegalStateException("Unable to load ecss.smp.xsmpcat");
      }
      xsmpcatDescription = descriptionManager.getResourceDescription(resource);
    }
    return xsmpcatDescription;
  }

  @Override
  protected IScope getScope(final Resource context, boolean ignoreCase, EClass type,
          Predicate<IEObjectDescription> filter)
  {
    return getScope(SelectableBasedScope.createScope(IScope.NULLSCOPE, getXsmpcatDescription(),
            filter, type, ignoreCase), context, ignoreCase, type, filter);
  }

}
