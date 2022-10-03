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

import static java.util.Collections.singletonList;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.xcatalogue.Component;
import org.eclipse.xsmp.xcatalogue.Interface;
import org.eclipse.xsmp.xcatalogue.ItemWithBase;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractScopeProvider;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportScope;
import org.eclipse.xtext.scoping.impl.MultimapBasedSelectable;
import org.eclipse.xtext.scoping.impl.SelectableBasedScope;
import org.eclipse.xtext.util.IResourceScopeCache;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A scope provider that manage inheritance
 *
 * @author daveluy
 */
@Singleton
class ItemWithBaseScopeProvider extends AbstractScopeProvider
{

  @Inject
  private IResourceScopeCache cache;

  protected ImportScope createImportScope(IScope parent, List<ImportNormalizer> namespaceResolvers,
          ISelectable importFrom, EClass type, boolean ignoreCase)
  {
    if (importFrom == null)
    {
      throw new NullPointerException("importFrom");
    }

    return new ImportScope(namespaceResolvers, parent, importFrom, type, ignoreCase);
  }

  protected ImportNormalizer doCreateImportNormalizer(QualifiedName importedNamespace,
          boolean wildcard, boolean ignoreCase)
  {
    return new ImportNormalizer(importedNamespace, wildcard, ignoreCase);
  }

  protected ISelectable getAllDescriptions(final EObject base)
  {
    return cache.get(base, base.eResource(), () -> internalGetAllDescriptions(base));
  }

  @SuppressWarnings("unchecked")
  private Iterable<NamedElement> getBases(EObject context)
  {

    Iterable<NamedElement> bases = null;
    if (context instanceof Interface)
    {
      bases = (Iterable<NamedElement>) context.eGet(XcataloguePackage.Literals.INTERFACE__BASE);
    }
    else if (context instanceof Component)
    {
      bases = (Iterable<NamedElement>) context
              .eGet(XcataloguePackage.Literals.COMPONENT__INTERFACE);
    }

    if (context instanceof ItemWithBase)
    {
      final var baseType = (NamedElement) ((ItemWithBase) context).getBase();
      if (baseType != null)
      {

        if (bases == null)
        {
          bases = Collections.singleton(baseType);
        }
        else
        {
          bases = Iterables.concat(bases, Collections.singleton(baseType));
        }
      }
    }
    if (bases == null)
    {
      bases = Collections.emptyList();
    }
    return bases;

  }

  protected IScope getLocalElementsScope(IScope result, ISelectable selectable, EObject context,
          EReference reference, QualifiedName name)
  {

    if (context instanceof NamedElementWithMembers)
    {

      final var ignoreCase = false;
      if (name.isEmpty())
      {
        result = SelectableBasedScope.createScope(result, selectable,
                d -> d.getQualifiedName().getSegmentCount() == 1, reference.getEReferenceType(),
                ignoreCase);
      }
      else
      {
        final List<ImportNormalizer> localNormalizer = singletonList(
                doCreateImportNormalizer(name, true, ignoreCase));

        result = createImportScope(result, localNormalizer, selectable,
                reference.getEReferenceType(), ignoreCase);
      }
    }
    return result;
  }

  // Get all the members of the current object and from its parents
  private Iterable<IEObjectDescription> getMembers(EObject context, QualifiedName name)
  {
    Iterable<IEObjectDescription> members = Collections.emptyList();

    for (final NamedElement baseType : getBases(context))
    {
      if (!baseType.eIsProxy())
      {
        members = Iterables.concat(members, getMembers(baseType, name.append(baseType.getName())));
      }
    }

    if (context instanceof NamedElementWithMembers)
    {
      final Iterable<IEObjectDescription> localMembers = Scopes.scopedElementsFor(
              Iterables.filter(((NamedElementWithMembers) context).getMember(), NamedElement.class),
              m -> name.append(m.getName()));
      members = Iterables.concat(members, localMembers);
    }
    return members;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IScope getScope(EObject context, EReference reference)
  {
    context = EcoreUtil2.getContainerOfType(context, Type.class);
    if (context != null)
    {
      final var selectable = getAllDescriptions(context);
      final var parent = SelectableBasedScope.createScope(IScope.NULLSCOPE, selectable,
              reference.getEReferenceType(), false);
      return internalGetScope(parent, selectable, context, reference, QualifiedName.EMPTY);
    }
    return IScope.NULLSCOPE;
  }

  protected ISelectable internalGetAllDescriptions(final EObject base)
  {
    return new MultimapBasedSelectable(getMembers(base, QualifiedName.EMPTY));
  }

  protected IScope internalGetScope(IScope result, ISelectable selectable, EObject context,
          EReference reference, QualifiedName name)
  {

    for (final NamedElement baseType : getBases(context))
    {
      if (!baseType.eIsProxy())
      {
        result = internalGetScope(result, selectable, baseType, reference,
                name.append(baseType.getName()));
      }
    }

    return getLocalElementsScope(result, selectable, context, reference, name);
  }

}