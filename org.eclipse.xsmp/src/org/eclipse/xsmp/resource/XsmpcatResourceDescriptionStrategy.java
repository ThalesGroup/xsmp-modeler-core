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
package org.eclipse.xsmp.resource;

import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.AttributeType;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.VisibilityKind;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IAcceptor;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author daveluy
 */
@Singleton
public class XsmpcatResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy
{

  private static final Logger LOG = Logger.getLogger(XsmpcatResourceDescriptionStrategy.class);

  @Inject
  private XsmpUtil xsmpUtil;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor)
  {
    if (getQualifiedNameProvider() == null)
    {
      return false;
    }
    try
    {
      final var qualifiedName = getQualifiedNameProvider().getFullyQualifiedName(eObject);
      if (qualifiedName != null)
      {
        acceptor.accept(
                EObjectDescription.create(qualifiedName, eObject, createLazyUserData(eObject)));
      }
    }
    catch (final Exception exc)
    {
      LOG.error(exc.getMessage(), exc);
    }
    return true;
  }

  /**
   * Create a lazy user data in order to avoid cyclic linking exception
   *
   * @param eObject
   * @return the user data
   */
  protected Map<String, String> createLazyUserData(final EObject eObject)
  {
    return new ForwardingMap<>() {
      private Map<String, String> delegate;

      @Override
      protected Map<String, String> delegate()
      {
        if (delegate == null)
        {
          final Builder<String, String> userData = ImmutableMap.builder();
          createUserData(eObject, userData);
          delegate = userData.build();
        }
        return delegate;
      }
    };
  }

  /**
   * Create the specific user data
   *
   * @param eObject
   *          the input object
   * @param builder
   */
  private void createUserData(EObject eObject, Builder<String, String> builder)
  {

    // save the deprecated state
    if (eObject instanceof final NamedElement elem && elem.isDeprecated())
    {
      builder.put("deprecated", Boolean.toString(true));
    }

    if (eObject instanceof final Field field)
    {
      if (xsmpUtil.isStatic(field))
      {
        builder.put("static", Boolean.toString(true));
      }
      if (xsmpUtil.isFailure(field))
      {
        builder.put("failure", Boolean.toString(true));
      }
      if (xsmpUtil.isForcible(field))
      {
        builder.put("forcible", Boolean.toString(true));
      }
      if (field.isInput())
      {
        builder.put("input", Boolean.toString(field.isInput()));
      }
      if (field.isOutput())
      {
        builder.put("output", Boolean.toString(field.isOutput()));
      }
    }

    // save the visibility of the VisibilityElement
    if (eObject instanceof final VisibilityElement elem)
    {
      final var visibility = elem.getRealVisibility();
      if (visibility != VisibilityKind.PUBLIC)
      {
        builder.put("visibility", visibility.getName());
      }
    }

    if (eObject instanceof final Type type)
    {
      final var uuid = type.getUuid();
      // save the uuif of the Type
      if (uuid != null && !uuid.isEmpty())
      {
        builder.put("uuid", uuid.toLowerCase());
      }
      if (eObject instanceof final AttributeType attribute)
      {
        // save the usage of the AttributeType
        builder.put("usage", attribute.getUsage().stream().collect(Collectors.joining(" ")));
        builder.put("allowMultiple", Boolean.toString(attribute.isAllowMultiple()));
      }
    }
    if (eObject instanceof final Operation op)
    {
      // save the signature of the Operation
      builder.put("sig", getSignature(op));
    }
  }

  /**
   * Compute the signature of an Operation
   *
   * @param op
   *          the input Operation
   * @return the signature
   */
  private String getSignature(Operation op)
  {
    return op.getParameter().stream().map(this::getSignature).collect(Collectors.joining(","));

  }

  /**
   * Get the signature of a parameter
   *
   * @param p
   *          the input Parameter
   * @return the signature of the parameter
   */
  private String getSignature(Parameter p)
  {
    final var s = new StringBuilder();

    final var type = p.getType();
    if (type != null && !type.eIsProxy())
    {
      if (xsmpUtil.isConst(p))
      {
        s.append("const ");
      }

      s.append(getQualifiedNameProvider().getFullyQualifiedName(type).toString());

      if (xsmpUtil.isByPointer(p))
      {
        s.append("*");
      }
      if (xsmpUtil.isByReference(p))
      {
        s.append("&");
      }

    }

    return s.toString();
  }
}