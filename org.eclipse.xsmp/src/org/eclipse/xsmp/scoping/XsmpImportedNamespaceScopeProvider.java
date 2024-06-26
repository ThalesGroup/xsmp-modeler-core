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
package org.eclipse.xsmp.scoping;

import static java.util.Collections.singletonList;

import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.model.xsmp.CollectionLiteral;
import org.eclipse.xsmp.model.xsmp.Component;
import org.eclipse.xsmp.model.xsmp.Interface;
import org.eclipse.xsmp.model.xsmp.Structure;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xsmp.util.QualifiedNames;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractGlobalScopeDelegatingScopeProvider;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportScope;
import org.eclipse.xtext.scoping.impl.MultimapBasedSelectable;
import org.eclipse.xtext.scoping.impl.ScopeBasedSelectable;
import org.eclipse.xtext.scoping.impl.SelectableBasedScope;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.Tuples;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpImportedNamespaceScopeProvider extends AbstractGlobalScopeDelegatingScopeProvider
{

  @Inject
  private IResourceScopeCache cache;

  @Inject
  private IQualifiedNameConverter qualifiedNameConverter;

  @Inject
  private IQualifiedNameProvider qualifiedNameProvider;

  @Inject
  private XsmpUtil xsmpUtil;

  /**
   * Create a new {@link ImportNormalizer} for the given namespace.
   *
   * @param namespace
   *          the namespace.
   * @param ignoreCase
   *          <code>true</code> if the resolver should be case insensitive.
   * @return a new {@link ImportNormalizer} or <code>null</code> if the namespace cannot be
   *         converted to a valid qualified name.
   */
  protected ImportNormalizer createImportedNamespaceResolver(final String namespace,
          boolean ignoreCase)
  {
    if (Strings.isEmpty(namespace))
    {
      return null;
    }
    final var importedNamespace = qualifiedNameConverter.toQualifiedName(namespace);
    if (importedNamespace == null || importedNamespace.isEmpty())
    {
      return null;
    }
    final var hasWildcard = ignoreCase
            ? importedNamespace.getLastSegment().equalsIgnoreCase(getWildcard())
            : importedNamespace.getLastSegment().equals(getWildcard());
    if (hasWildcard)
    {
      if (importedNamespace.getSegmentCount() <= 1)
      {
        return null;
      }
      return doCreateImportNormalizer(importedNamespace.skipLast(1), true, ignoreCase);
    }
    return doCreateImportNormalizer(importedNamespace, false, ignoreCase);
  }

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

  protected ISelectable getAllDescriptions(final Resource resource)
  {
    return cache.get("internalGetAllDescriptions", resource,
            () -> internalGetAllDescriptions(resource));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IScope getGlobalScope(final Resource context, final EReference reference)
  {
    final var globalScope = super.getGlobalScope(context, reference, null);
    return SelectableBasedScope.createScope(globalScope, getAllDescriptions(context),
            reference.getEReferenceType(), isIgnoreCase(reference));
  }

  /**
   * @param ignoreCase
   *          {@code true} if the import normalizer should use case insensitive compare logic.
   */
  protected List<ImportNormalizer> getImplicitImports(boolean ignoreCase)
  {
    return Lists.<ImportNormalizer> newArrayList(
            doCreateImportNormalizer(QualifiedNames.Smp, true, ignoreCase),
            doCreateImportNormalizer(QualifiedNames.Attributes, true, ignoreCase));
  }

  protected IScope getListScope(IScope parent, IScope globalScope, EObject context,
          EReference feature, EReference reference, boolean resolve)
  {
    var result = parent;
    final var uri = EcoreUtil.getURI(context).trimFragment();

    @SuppressWarnings("unchecked")
    final var items = (BasicEList<EObject>) context.eGet(feature, resolve);

    // iterate on items in reverse order
    for (var i = items.size(); i-- > 0;)
    {
      var localResolve = resolve;
      var item = localResolve ? items.get(i) : items.basicGet(i);
      if (!resolve && item.eIsProxy() && !uri.equals(EcoreUtil.getURI(item).trimFragment()))
      {
        item = items.get(i);
        localResolve = true;
      }

      if (!item.eIsProxy() && !xsmpUtil.isBaseOf(context, item))
      {
        result = getLocalElementsScope(result, globalScope, item, reference, localResolve);
      }
    }

    return result;
  }

  protected EObject resolve(EObject context, EReference feature, boolean resolve)
  {
    var eObject = (EObject) context.eGet(feature, resolve);
    if (eObject != null && !resolve && eObject.eIsProxy() && !EcoreUtil.getURI(eObject)
            .trimFragment().equals(EcoreUtil.getURI(context).trimFragment()))
    {
      eObject = EcoreUtil.resolve(eObject, context);
    }
    return eObject;
  }

  protected IScope getScope(IScope parent, IScope globalScope, EObject context, EReference feature,
          EReference reference, boolean resolve)
  {
    var result = parent;

    var eObject = (EObject) context.eGet(feature, resolve);
    if (eObject != null)
    {

      var localResolve = resolve;
      if (!resolve && eObject.eIsProxy() && !EcoreUtil.getURI(eObject).trimFragment()
              .equals(EcoreUtil.getURI(context).trimFragment()))
      {
        eObject = EcoreUtil.resolve(eObject, context);
        localResolve = true;
      }
      if (!eObject.eIsProxy() && !xsmpUtil.isBaseOf(context, eObject))
      {
        result = getLocalElementsScope(result, globalScope, eObject, reference, localResolve);
      }
    }
    return result;
  }

  protected IScope getComponentScope(IScope parent, IScope globalScope, Component context,
          EReference reference, boolean resolve)
  {
    final var result = getListScope(parent, globalScope, context,
            XsmpPackage.Literals.COMPONENT__INTERFACE, reference, resolve);

    return getScope(result, globalScope, context, XsmpPackage.Literals.COMPONENT__BASE, reference,
            resolve);
  }

  protected IScope getInterfaceScope(IScope parent, IScope globalScope, Interface context,
          EReference reference, boolean resolve)
  {
    return getListScope(parent, globalScope, context, XsmpPackage.Literals.INTERFACE__BASE,
            reference, resolve);

  }

  protected IScope getClassScope(IScope parent, IScope globalScope,
          org.eclipse.xsmp.model.xsmp.Class context, EReference reference, boolean resolve)
  {
    return getScope(parent, globalScope, context, XsmpPackage.Literals.CLASS__BASE, reference,
            resolve);
  }

  IScope getDesignatedInitializerFieldScope(IScope globalScope, Type type, boolean resolve)
  {
    return cache.get(Tuples.create(type, "DesignatedInitializerFieldScope"), type.eResource(),
            () -> getLocalElementsScope(globalScope, globalScope, type,
                    XsmpPackage.Literals.DESIGNATED_INITIALIZER__FIELD, resolve));
  }

  protected IScope getLocalElementsScope(IScope parent, IScope globalScope, EObject context,
          EReference reference, boolean resolve)
  {
    var result = parent;
    final var name = getQualifiedNameOfLocalElement(context);
    final var ignoreCase = isIgnoreCase(reference);
    final var resourceOnlySelectable = getAllDescriptions(context.eResource());

    switch (context.eClass().getClassifierID())
    {
      case XsmpPackage.CLASS, XsmpPackage.EXCEPTION:
        result = getClassScope(result, globalScope, (org.eclipse.xsmp.model.xsmp.Class) context,
                reference, resolve);
        break;
      case XsmpPackage.INTERFACE:
        result = getInterfaceScope(result, globalScope, (Interface) context, reference, resolve);
        break;
      case XsmpPackage.MODEL, XsmpPackage.SERVICE:
        result = getComponentScope(result, globalScope, (Component) context, reference, resolve);
        break;
      case XsmpPackage.DESIGNATED_INITIALIZER:
      {
        final var container = context.eContainer();
        if (container instanceof final CollectionLiteral collection
                && reference == XsmpPackage.Literals.DESIGNATED_INITIALIZER__FIELD)
        {
          final var type = xsmpUtil.getType(collection);
          if (type instanceof Structure)
          {
            result = getDesignatedInitializerFieldScope(globalScope, type,
                    resolve || type.eResource() != context.eResource());
          }
        }
        break;
      }
      case XsmpPackage.ARRAY:
      {
        final var type = resolve(context, XsmpPackage.Literals.ARRAY__ITEM_TYPE, resolve);
        if (type != null && !type.eIsProxy())
        {
          result = getLocalElementsScope(result, globalScope, type, reference,
                  resolve || type.eResource() != context.eResource());
        }
        break;
      }
      case XsmpPackage.FIELD:
      {
        final var type = resolve(context, XsmpPackage.Literals.FIELD__TYPE, resolve);
        if (type != null && !type.eIsProxy())
        {
          result = getLocalElementsScope(result, globalScope, type, reference,
                  resolve || type.eResource() != context.eResource());
        }
        break;
      }
      default:
        break;
    }

    // local element
    if (name != null && !name.isEmpty())
    {

      final List<ImportNormalizer> localNormalizer = singletonList(
              doCreateImportNormalizer(name, true, ignoreCase));

      result = createImportScope(result, localNormalizer, resourceOnlySelectable,
              reference.getEReferenceType(), ignoreCase);

    }

    return result;
  }

  /**
   * @return the QualifiedNameConverter
   */
  public IQualifiedNameConverter getQualifiedNameConverter()
  {
    return qualifiedNameConverter;
  }

  protected QualifiedName getQualifiedNameOfLocalElement(final EObject context)
  {
    return qualifiedNameProvider.getFullyQualifiedName(context);
  }

  /**
   * @return the QualifiedNameProvider
   */
  public IQualifiedNameProvider getQualifiedNameProvider()
  {
    return qualifiedNameProvider;
  }

  protected IScope getResourceScope(IScope globalScope, EReference reference)
  {
    var result = globalScope;
    final ISelectable globalScopeSelectable = new ScopeBasedSelectable(result);

    // implicit imports (i.e. Smp.*, Attributes.*)
    final var normalizers = getImplicitImports(isIgnoreCase(reference));
    if (!normalizers.isEmpty())
    {
      result = createImportScope(result, normalizers, globalScopeSelectable,
              reference.getEReferenceType(), isIgnoreCase(reference));
    }

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IScope getScope(EObject context, EReference reference)
  {
    if (context == null)
    {
      throw new NullPointerException("context");
    }
    if (context.eResource() == null)
    {
      throw new IllegalArgumentException("context must be contained in a resource");
    }

    final var globalScope = getGlobalScope(context.eResource(), reference);
    return internalGetScope(globalScope, globalScope, context, reference);
  }

  private static final String WILDCARD = "*";

  /**
   * @return the wildcard token
   */
  public String getWildcard()
  {
    return WILDCARD;
  }

  protected ISelectable internalGetAllDescriptions(final Resource resource)
  {
    final Iterable<EObject> allContents = () -> EcoreUtil.getAllContents(resource, false);
    final Iterable<IEObjectDescription> allDescriptions = Scopes.scopedElementsFor(allContents,
            qualifiedNameProvider);
    return new MultimapBasedSelectable(allDescriptions);
  }

  protected IScope internalGetScope(IScope parent, IScope globalScope, EObject context,
          EReference reference)
  {

    IScope result;

    final var container = context.eContainer();
    if (container == null)
    {
      if (parent != globalScope)
      {
        throw new IllegalStateException("the parent should be the global scope");
      }
      result = getResourceScope(globalScope, reference);
    }
    else
    {
      result = internalGetScope(parent, globalScope, container, reference);
    }

    return getLocalElementsScope(result, globalScope, context, reference, false);
  }
}
