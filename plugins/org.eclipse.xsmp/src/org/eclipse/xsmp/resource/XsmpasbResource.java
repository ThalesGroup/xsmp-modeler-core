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
package org.eclipse.xsmp.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.model.xsmp.Simulator;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.IDerivedStateComputer;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.util.Triple;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A customized resource that do not cache unresolvable proxies and resolve all links
 */
public class XsmpasbResource extends DerivedStateAwareResource
{

  @Override
  protected boolean isUnresolveableProxyCacheable(Triple<EObject, EReference, INode> triple)
  {
    // do not cache unresolveable proxy
    return false;
  }

  @Override
  public boolean isEagerLinking()
  {
    // force resource resolution after linking (it helps the content assist)
    return false;
  }

  @Singleton
  public static class DerivedStateComputer implements IDerivedStateComputer
  {
    @Inject
    private IResourceServiceProvider.Registry registry;

    private Type model;

    private Type getModel()
    {
      if (model == null)
      {
        model = resolve(QualifiedName.create("Smp", "IModel"));
      }
      return model;
    }

    private Type service;

    private Type getService()
    {
      if (service == null)
      {
        service = resolve(QualifiedName.create("Smp", "IService"));
      }
      return service;
    }

    private Type resolve(QualifiedName qfn)
    {
      final var resourceProvider = registry
              .getResourceServiceProvider(URI.createURI("test.xsmpcat"));
      if (resourceProvider == null)
      {
        return null;
      }
      final var globalScope = resourceProvider.get(IGlobalScopeProvider.class);
      final var scope = globalScope.getScope(null, XsmpPackage.Literals.CONTAINER__TYPE, null);
      final var elem = scope.getSingleElement(qfn);

      if (elem != null)
      {
        final var obj = elem.getEObjectOrProxy();
        if (obj instanceof final Type type)
        {
          return type;
        }
      }

      return null;
    }

    @Override
    public void installDerivedState(DerivedStateAwareResource resource, boolean preLinkingPhase)
    {
      if (preLinkingPhase)
      {
        return;
      }

      resource.getAllContents().forEachRemaining(obj -> {
        if (obj instanceof final Simulator sim)
        {
          sim.getModels().setType(getModel());
          sim.getServices().setType(getService());
        }
      });

    }

    @Override
    public void discardDerivedState(DerivedStateAwareResource resource)
    {
      resource.getAllContents().forEachRemaining(obj -> {
        if (obj instanceof final Simulator sim)
        {
          sim.getModels().setType(null);
          sim.getServices().setType(null);
        }
      });
    }

  }
}
