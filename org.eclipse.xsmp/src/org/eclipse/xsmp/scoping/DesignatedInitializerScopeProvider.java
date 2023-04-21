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
package org.eclipse.xsmp.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.DesignatedInitializer;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.Structure;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractScopeProvider;
import org.eclipse.xtext.scoping.impl.MapBasedScope;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A scope provider that manage inheritance
 *
 * @author daveluy
 */
@Singleton
class DesignatedInitializerScopeProvider extends AbstractScopeProvider
{

  @Inject
  XsmpUtil xsmpUtil;

  /**
   * {@inheritDoc}
   */
  @Override
  public IScope getScope(EObject context, EReference reference)
  {
    if (context instanceof DesignatedInitializer
            && context.eContainer() instanceof CollectionLiteral)
    {

      final var e = (Expression) context.eContainer();
      final var type = xsmpUtil.getType(e);
      if (type instanceof Structure)
      {
        final var fields = Scopes.scopedElementsFor(xsmpUtil.getFields((Structure) type));

        return MapBasedScope.createScope(IScope.NULLSCOPE, fields);
      }
    }
    return IScope.NULLSCOPE;
  }

}