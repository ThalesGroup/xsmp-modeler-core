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
package org.eclipse.xsmp.naming;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.xcatalogue.Document;
import org.eclipse.xsmp.xcatalogue.NamedElement;
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
public class XsmpcatQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl
{

  @Inject
  private IResourceScopeCache cache;

  @Inject
  private IQualifiedNameConverter converter;

  /**
   * Computes the fully qualified name for the given object, if any.
   */
  protected QualifiedName computeFullyQualifiedName(EObject obj)
  {
    if (!(obj instanceof NamedElement))
    {
      return null;
    }
    final var name = ((NamedElement) obj).getName();
    if (name == null || name.isEmpty())
    {
      return null;
    }
    final var qualifiedNameFromConverter = converter.toQualifiedName(name);
    while ((obj = obj.eContainer()) != null)
    {
      if (obj instanceof Document)
      {
        break;
      }
      final var parentsQualifiedName = getFullyQualifiedName(obj);
      if (parentsQualifiedName != null)
      {
        return parentsQualifiedName.append(qualifiedNameFromConverter);
      }
    }
    return qualifiedNameFromConverter;
  }

  /**
   * Tries to obtain the FQN of the given object from the {@link #cache}. If it is absent, it
   * computes a new name.
   */
  @Override
  public QualifiedName getFullyQualifiedName(final EObject obj)
  {
    if (obj == null)
    {
      return QualifiedName.EMPTY;
    }
    return cache.get(Tuples.pair(obj, "fqn"), obj.eResource(),
            () -> computeFullyQualifiedName(obj));
  }

}
