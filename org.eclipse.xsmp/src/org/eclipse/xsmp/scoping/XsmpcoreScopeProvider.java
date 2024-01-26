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

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.FilteringScope;

import com.google.common.collect.ImmutableSet;

/**
 * This class contains custom scoping description.
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
public class XsmpcoreScopeProvider extends AbstractXsmpcoreScopeProvider
{

  private static final Set<EClass> elementReferences = ImmutableSet.<EClass> builder()
          .add(XcataloguePackage.Literals.CONSTANT)
          .add(XcataloguePackage.Literals.ENUMERATION_LITERAL).build();

  @Override
  public IScope getScope(EObject context, EReference reference)
  {

    if (XcataloguePackage.Literals.NAMED_ELEMENT_REFERENCE__VALUE.equals(reference))
    {
      return new FilteringScope(super.getScope(context, reference),
              o -> elementReferences.contains(o.getEClass()));
    }

    return super.getScope(context, reference);
  }

}
