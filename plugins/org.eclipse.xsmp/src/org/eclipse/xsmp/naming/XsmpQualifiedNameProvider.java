/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.naming;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.model.xsmp.Container;
import org.eclipse.xsmp.model.xsmp.Document;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.Reference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The qualified name converter for Xsmp elements
 *
 * @author daveluy
 */
@Singleton
public class XsmpQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl
{

  @Inject
  private IResourceScopeCache cache;

  @Inject
  private IQualifiedNameConverter converter;

  /**
   * Computes the fully qualified name for the given object, if any.
   */
  protected QualifiedName computeFullyQualifiedName(final NamedElement obj)
  {
    var name = obj.getName();
    if (name == null || name.isEmpty())
    {
      return QualifiedName.EMPTY;
    }
    if (obj instanceof Container || obj instanceof Reference)
    {
      name = "$" + name;
    }

    final var parent = EcoreUtil2.getContainerOfType(obj.eContainer(), NamedElement.class);

    if (parent instanceof Document || parent == null)
    {
      return converter.toQualifiedName(name);
    }

    return getFullyQualifiedName(parent).append(name);

  }

  protected QualifiedName computeFullyQualifiedName(final EObject obj)
  {
    if (obj instanceof final NamedElement ne)
    {
      return computeFullyQualifiedName(ne);
    }

    return null;
  }

  /**
   * Tries to obtain the FQN of the given object from the {@link #cache}. If it is absent, it
   * computes a new name.
   */
  @Override
  public QualifiedName getFullyQualifiedName(final EObject obj)
  {
    if (obj != null)
    {
      return cache.get(Tuples.pair(obj, "fqn"), obj.eResource(),
              () -> computeFullyQualifiedName(obj));
    }

    return null;
  }

}
