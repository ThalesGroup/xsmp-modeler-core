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
package org.eclipse.xsmp.ide.contentassist;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.util.XsmpprojectUtil;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpprojectReferenceFilter implements IReferenceFilter
{

  @Inject
  protected XsmpUtil xsmpUtil;

  private final Map<EReference, Function<EObject, Predicate<IEObjectDescription>>> builders = ImmutableMap
          .<EReference, Function<EObject, Predicate<IEObjectDescription>>> builder()
          // add the entries
          .put(XsmpPackage.Literals.PROJECT_REFERENCE__PROJECT,
                  model -> p -> model.eContainer() instanceof final Project project
                          && getDependencies(p).noneMatch(d -> project.getName().equals(d))
                          && project.getReferencedProjects().stream()
                                  .noneMatch(t -> t.getName().equals(p.getName().toString())))
          .put(XsmpPackage.Literals.TOOL_REFERENCE__TOOL,
                  model -> p -> model.eContainer() instanceof final Project project
                          && project.getTools().stream()
                                  .noneMatch(t -> t.getName().equals(p.getName().toString())))
          // build the map
          .build();

  @Inject
  private XsmpprojectUtil util;

  protected Stream<String> getDependencies(IEObjectDescription d)
  {
    final var obj = d.getEObjectOrProxy();

    if (obj instanceof final Project project)
    {
      if (obj.eIsProxy())
      {
        return Arrays.stream(d.getUserData("dependencies").split(","));
      }
      return util.getDependencies(project).stream().map(Project::getName);
    }
    return Stream.of();

  }

  @Override
  public Predicate<IEObjectDescription> getFilter(EObject model, EReference reference)
  {

    final var filterBuilder = builders.get(reference);
    if (filterBuilder != null)
    {
      return filterBuilder.apply(model);
    }
    return Predicates.alwaysTrue();
  }
}
