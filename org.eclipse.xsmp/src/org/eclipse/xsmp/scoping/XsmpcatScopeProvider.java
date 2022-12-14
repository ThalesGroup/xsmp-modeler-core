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

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.impl.FilteringScope;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;

/**
 * This class contains custom scoping description. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping on how and when to
 * use it.
 */
public class XsmpcatScopeProvider extends AbstractXsmpcatScopeProvider
{

  @Inject
  private ItemWithBaseScopeProvider baseScopeProvider;

  private final Map<EReference, IScopeProvider> scopes = ImmutableMap
          .<EReference, IScopeProvider> builder()
          .put(XcataloguePackage.Literals.ENUMERATION_LITERAL_REFERENCE__VALUE,
                  this::getLiteralReferenceScope)
          .put(XcataloguePackage.Literals.PROPERTY__ATTACHED_FIELD, this::getTypeScope)
          .put(XcataloguePackage.Literals.ENTRY_POINT__INPUT, this::getTypeScope)
          .put(XcataloguePackage.Literals.ENTRY_POINT__OUTPUT, this::getTypeScope).build();

  private IScope getTypeScope(EObject context, EReference reference)
  {
    return baseScopeProvider.getScope(context, reference);
  }

  private IScope getLiteralReferenceScope(EObject context, EReference reference)
  {
    return new FilteringScope(super.getScope(context, reference),
            elem -> elem.getEObjectOrProxy() instanceof EnumerationLiteral
                    || elem.getEObjectOrProxy() instanceof Constant);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IScope getScope(EObject context, EReference reference)
  {
    final var scopeProvider = scopes.get(reference);
    if (scopeProvider != null)
    {
      return scopeProvider.getScope(context, reference);
    }
    return super.getScope(context, reference);
  }

}
