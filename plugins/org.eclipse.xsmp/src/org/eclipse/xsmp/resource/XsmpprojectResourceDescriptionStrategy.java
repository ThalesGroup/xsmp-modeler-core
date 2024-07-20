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
package org.eclipse.xsmp.resource;

import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.util.XsmpprojectUtil;
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
public class XsmpprojectResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy
{
  @Inject
  private XsmpprojectUtil util;

  private static final Logger LOG = Logger.getLogger(XsmpprojectResourceDescriptionStrategy.class);

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
        if (eObject instanceof final Project project)
        {
          acceptor.accept(
                  EObjectDescription.create(qualifiedName, eObject, createLazyUserData(project)));
        }
        else
        {
          acceptor.accept(EObjectDescription.create(qualifiedName, eObject));
        }
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
   *          the project
   * @return the user data
   */
  protected Map<String, String> createLazyUserData(final Project eObject)
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
   * @param project
   *          the project
   * @param builder
   */
  private void createUserData(Project project, Builder<String, String> builder)
  {

    // save the deprecated state
    if (project.isDeprecated())
    {
      builder.put("deprecated", Boolean.toString(true));
    }

    final var dependencies = util.getDependencies(project);
    builder.put("dependencies",
            dependencies.stream().map(Project::getName).collect(Collectors.joining(",")));

  }

}