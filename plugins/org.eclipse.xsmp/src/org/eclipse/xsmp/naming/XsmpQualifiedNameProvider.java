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

import static org.eclipse.xsmp.model.xsmp.XsmpPackage.Literals.PATH__SEGMENT;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.model.xsmp.AbstractPath;
import org.eclipse.xsmp.model.xsmp.Container;
import org.eclipse.xsmp.model.xsmp.Document;
import org.eclipse.xsmp.model.xsmp.Expression;
import org.eclipse.xsmp.model.xsmp.Instance;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.Path;
import org.eclipse.xsmp.model.xsmp.Reference;
import org.eclipse.xsmp.model.xsmp.StringPath;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
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
  private XsmpUtil xsmpUtil;

  @Inject
  private IQualifiedNameConverter converter;

  protected QualifiedName processIndexes(QualifiedName result, String baseName,
          List<Expression> indexes)
  {
    for (final var index : indexes)
    {
      try
      {
        baseName += "[" + xsmpUtil.getInt64(index) + "]";
      }
      catch (final Exception e)
      {
        baseName += "[<invalid>]";
      }
    }
    return result.append(baseName);
  }

  protected QualifiedName computeFullyQualifiedName(final AbstractPath path)
  {

    final var parent = path.eContainer();

    final var result = getFullyQualifiedName(parent instanceof AbstractPath ? parent
            : EcoreUtil2.getContainerOfType(parent, NamedElement.class));

    if (result != null)
    {
      switch (path.eClass().getClassifierID())
      {
        case XsmpPackage.PATH:
        {
          /*
           * final var segment = (NamedElement) path.eGet(PATH__SEGMENT, false);
           * if (segment != null && !segment.eIsProxy())
           * {
           * return processIndexes(result, segment.getName(), ((Path) path).getIndex());
           * }
           */
          final var nodes = NodeModelUtils.findNodesForFeature(path, PATH__SEGMENT);
          if (nodes.size() == 1)
          {
            return processIndexes(result, NodeModelUtils.getTokenText(nodes.get(0)),
                    ((Path) path).getIndex());
          }
          return result;
        }
        case XsmpPackage.STRING_PATH:
        {
          final var p = (StringPath) path;
          final var name = XsmpUtil.stringLiteralToString(p.getName());

          if (!name.isEmpty())
          {
            return result.append(converter.toQualifiedName(name));
          }
          break;
        }

        default:
          break;
      }
    }

    return null;

  }

  /**
   * Computes the fully qualified name for the given object, if any.
   */
  protected QualifiedName computeFullyQualifiedName(final Instance obj)
  {
    final var name = obj.getName();
    if (name == null || name.isEmpty())
    {
      return QualifiedName.EMPTY;
    }

    final var declaration = obj.getDeclaration();
    if (declaration != null)
    {
      final var path = getFullyQualifiedName(XsmpUtil.getLastSegment(declaration.getContainer()));
      if (path != null && !path.isEmpty())
      {
        // remove the container from the path
        return path.skipLast(1).append(name);
      }

    }
    return null;
  }

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

    if (obj instanceof final Instance ins)
    {
      return computeFullyQualifiedName(ins);
    }

    if (obj instanceof final NamedElement ne)
    {
      return computeFullyQualifiedName(ne);
    }

    if (obj instanceof final AbstractPath path)
    {
      return computeFullyQualifiedName(path);
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
