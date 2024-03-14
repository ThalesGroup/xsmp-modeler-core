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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.model.xsmp.Simulator;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.IDerivedStateComputer;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.util.Triple;

import com.google.inject.Inject;

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
    return true;
  }

  public static class DerivedStateComputer implements IDerivedStateComputer
  {
    private static final QualifiedName IModel = QualifiedName.create("Smp", "IModel");

    private static final QualifiedName IService = QualifiedName.create("Smp", "IService");

    @Inject
    private IGlobalScopeProvider globalScope;

    private Type resolve(DerivedStateAwareResource resource, QualifiedName qfn)
    {

      final var scope = globalScope.getScope(resource, XsmpPackage.Literals.CONTAINER__TYPE, null);
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

      final var iModel = resolve(resource, IModel);
      final var iService = resolve(resource, IService);

      resource.getAllContents().forEachRemaining(obj -> {
        if (obj instanceof final Simulator sim)
        {
          sim.getModels().setType(iModel);
          sim.getServices().setType(iService);
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
