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

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xsmp.util.QualifiedNames;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.Component;
import org.eclipse.xsmp.xcatalogue.ImportDeclaration;
import org.eclipse.xsmp.xcatalogue.ImportSection;
import org.eclipse.xsmp.xcatalogue.Interface;
import org.eclipse.xsmp.xcatalogue.Structure;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.resource.XtextResource;
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

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @author Sebastian Zarnekow - Improved support for nested types in connection with imports
 */
@Singleton
public class XsmpcatImportedNamespaceScopeProvider
        extends AbstractGlobalScopeDelegatingScopeProvider
{

  @Inject
  private IResourceScopeCache cache;

  @Inject
  private XsmpcatImportsConfiguration importsConfiguration;

  @Inject
  private IQualifiedNameConverter qualifiedNameConverter;

  @Inject
  private IQualifiedNameProvider qualifiedNameProvider;

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

  protected List<ImportNormalizer> getImportedNamespaceResolvers(final EObject context,
          final boolean ignoreCase)
  {
    return cache.get(Tuples.create(context, ignoreCase, "imports"), context.eResource(),
            () -> internalGetImportedNamespaceResolvers(context, ignoreCase));
  }

  protected List<ImportNormalizer> getImportedNamespaceResolvers(ImportSection importSection,
          boolean ignoreCase)
  {
    final List<ImportDeclaration> importDeclarations = importSection.getImportDeclarations();
    final List<ImportNormalizer> result = Lists
            .newArrayListWithExpectedSize(importDeclarations.size());
    for (final ImportDeclaration imp : importDeclarations)
    {

      final var value = imp.getImportedNamespace();
      if (value != null && !value.eIsProxy())
      {
        final var typeName = qualifiedNameConverter
                .toString(qualifiedNameProvider.getFullyQualifiedName(value).append(getWildcard()));
        final var resolver = createImportedNamespaceResolver(typeName, ignoreCase);
        if (resolver != null)
        {
          result.add(resolver);
        }
      }
      else if (imp.getImportedType() != null && !imp.getImportedType().eIsProxy())
      {
        final var typeName = qualifiedNameConverter
                .toString(qualifiedNameProvider.getFullyQualifiedName(imp.getImportedType()));
        final var resolver = createImportedNamespaceResolver(typeName, ignoreCase);
        if (resolver != null)
        {
          result.add(resolver);
        }
      }

    }
    return result;
  }

  protected XsmpcatImportsConfiguration getImportsConfiguration()
  {
    return importsConfiguration;
  }

  protected Object getKey(Notifier context, EReference reference)
  {
    return Tuples.create(XsmpcatScopeProvider.class, context, reference);
  }

  @Inject
  XsmpUtil xsmpUtil;

  protected IScope getComponentScope(IScope parent, IScope globalScope, Component context,
          EReference reference)
  {
    var result = parent;

    @SuppressWarnings("unchecked")
    final var interfaces = (EObjectResolvingEList<EObject>) context
            .eGet(XcataloguePackage.Literals.COMPONENT__INTERFACE, false);

    // iterate on interfaces in reverse order
    for (var i = interfaces.size(); i-- > 0;)
    {
      final var inter = interfaces.basicGet(i);
      if (!inter.eIsProxy())
      {
        result = getLocalElementsScope(result, globalScope, inter, reference);
      }
    }

    // take base in last
    final var base = (EObject) context.eGet(XcataloguePackage.Literals.COMPONENT__BASE, false);
    if (base != null && !base.eIsProxy())
    {
      result = getLocalElementsScope(result, globalScope, base, reference);
    }

    return result;
  }

  protected IScope getInterfaceScope(IScope parent, IScope globalScope, Interface context,
          EReference reference)
  {
    var result = parent;
    @SuppressWarnings("unchecked")
    final var bases = (EObjectResolvingEList<EObject>) context
            .eGet(XcataloguePackage.Literals.INTERFACE__BASE, false);

    // iterate on bases in reverse order
    for (var i = bases.size(); i-- > 0;)
    {
      final var base = bases.basicGet(i);
      if (!base.eIsProxy())
      {
        result = getLocalElementsScope(result, globalScope, base, reference);
      }
    }

    return result;
  }

  protected IScope getClassScope(IScope parent, IScope globalScope,
          org.eclipse.xsmp.xcatalogue.Class context, EReference reference)
  {
    var result = parent;
    final var base = (EObject) context.eGet(XcataloguePackage.Literals.CLASS__BASE, false);
    if (base != null && !base.eIsProxy())
    {
      result = getLocalElementsScope(result, globalScope, base, reference);
    }

    return result;
  }

  IScope getDesignatedInitializerFieldScope(IScope globalScope, Type type)
  {
    return cache.get(Tuples.create(type, "DesignatedInitializerFieldScope"), type.eResource(),
            () -> getLocalElementsScope(globalScope, globalScope, type,
                    XcataloguePackage.Literals.DESIGNATED_INITIALIZER__FIELD));
  }

  protected IScope getLocalElementsScope(IScope parent, IScope globalScope, EObject context,
          EReference reference)
  {
    var result = parent;
    final var name = getQualifiedNameOfLocalElement(context);
    final var ignoreCase = isIgnoreCase(reference);
    final var resourceOnlySelectable = getAllDescriptions(context.eResource());
    final ISelectable globalScopeSelectable = new ScopeBasedSelectable(globalScope);

    // imports
    final var explicitImports = getImportedNamespaceResolvers(context, ignoreCase);
    if (!explicitImports.isEmpty())
    {
      result = createImportScope(result, explicitImports, globalScopeSelectable,
              reference.getEReferenceType(), ignoreCase);
    }

    switch (context.eClass().getClassifierID())
    {
      case XcataloguePackage.CLASS:
      case XcataloguePackage.EXCEPTION:
        result = getClassScope(parent, globalScope, (org.eclipse.xsmp.xcatalogue.Class) context,
                reference);
        break;

      case XcataloguePackage.INTERFACE:
        result = getInterfaceScope(parent, globalScope, (Interface) context, reference);
        break;
      case XcataloguePackage.MODEL:
      case XcataloguePackage.SERVICE:
        result = getComponentScope(parent, globalScope, (Component) context, reference);
        break;
      case XcataloguePackage.DESIGNATED_INITIALIZER:
      {
        final var container = context.eContainer();
        if (container instanceof CollectionLiteral
                && reference == XcataloguePackage.Literals.DESIGNATED_INITIALIZER__FIELD)
        {
          final var type = xsmpUtil.getType((CollectionLiteral) container);
          if (type instanceof Structure)
          {
            result = getDesignatedInitializerFieldScope(globalScope, type);
          }
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
   * @return the XsmpcatQualifiedNameProvider
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

  protected List<ImportNormalizer> internalGetImportedNamespaceResolvers(EObject context,
          boolean ignoreCase)
  {
    if (EcoreUtil.getRootContainer(context) != context)
    {
      return Collections.emptyList();
    }
    final var importSection = importsConfiguration
            .getImportSection((XtextResource) context.eResource());
    if (importSection != null)
    {
      return getImportedNamespaceResolvers(importSection, ignoreCase);
    }
    return Collections.emptyList();
  }

  protected IScope internalGetScope(IScope parent, IScope globalScope, EObject context,
          EReference reference)
  {
    if (context instanceof ImportDeclaration)
    {
      return globalScope;
    }
    IScope result;
    if (context.eContainer() == null)
    {
      if (parent != globalScope)
      {
        throw new IllegalStateException("the parent should be the global scope");
      }
      result = getResourceScope(globalScope, reference);
    }
    else
    {
      result = internalGetScope(parent, globalScope, context.eContainer(), reference);
    }

    return getLocalElementsScope(result, globalScope, context, reference);
  }
}
