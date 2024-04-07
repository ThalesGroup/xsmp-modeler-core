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
package org.eclipse.xsmp.validation;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.model.xsmp.Tool;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.inject.Singleton;

/**
 * This class contains custom validation rules.
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@Singleton
// @ComposedChecks(validators = {UniqueElementValidator.class })
public class XsmpprojectValidator extends AbstractXsmpprojectValidator
{

  @Check
  protected void checkProject(Project p)
  {
    final var visitedSources = new HashSet<String>();
    for (final var source : p.getSources())
    {
      final var name = source.getName();

      if (!visitedSources.add(name))
      {
        acceptError("Duplicated Source path '" + name + "'.", source, null, INSIGNIFICANT_INDEX,
                null);
      }
      if (name.startsWith("/"))
      {
        acceptError("Source path '" + source.getName() + "' is not relative to project path.",
                source, XsmpPackage.Literals.SOURCE_PATH__NAME,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
      }
      if (name.startsWith("../") || "..".equals(name))
      {
        acceptError(
                "Source path '" + source.getName()
                        + "' is not contained within the project directory.",
                source, XsmpPackage.Literals.SOURCE_PATH__NAME,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
      }
    }
    final var visitedIncludes = new HashSet<String>();
    for (final var include : p.getIncludes())
    {
      final var name = include.getName();

      if (!visitedIncludes.add(name))
      {
        acceptError("Duplicated Include path '" + name + "'.", include, null, INSIGNIFICANT_INDEX,
                null);
      }
      if (name.startsWith("/"))
      {
        acceptError("Include path '" + include.getName() + "' is not relative to project path.",
                include, XsmpPackage.Literals.SOURCE_PATH__NAME,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
      }
      if (name.startsWith("../") || "..".equals(name))
      {
        acceptError(
                "Include path '" + include.getName()
                        + "' is not contained within the project directory.",
                include, XsmpPackage.Literals.SOURCE_PATH__NAME,
                ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
      }
    }
    final var visitedDependencies = new HashSet<Project>();
    // check no duplicate dependency and different that current project
    for (final var dependency : p.getReferencedProjects())
    {
      final var project = dependency.getProject();

      if (!visitedDependencies.add(project))
      {
        acceptError("Duplicated dependency '" + dependency.getName() + "'.", dependency, null,
                INSIGNIFICANT_INDEX, null);
      }
    }
    // check no cyclic dependency
    for (final var dependency : p.getReferencedProjects())
    {
      if (hasCyclicDependencies(p, dependency.getProject(), visitedDependencies))
      {
        acceptError("Cyclic dependency detected '" + dependency.getName() + "'.", dependency, null,
                INSIGNIFICANT_INDEX, null);
      }

    }

    final var visitedTools = new HashSet<Tool>();
    for (final var toolReference : p.getTools())
    {
      final var tool = toolReference.getTool();

      if (!visitedTools.add(tool))
      {
        acceptError("Duplicated tool '" + tool.getName() + "'.", toolReference, null,
                INSIGNIFICANT_INDEX, null);
      }
    }
  }

  private boolean hasCyclicDependencies(Project project, Project dependency, Set<Project> visited)
  {
    for (final var ref : dependency.getReferencedProjects())
    {
      if (project.equals(ref.getProject()) || visited.add(ref.getProject())
              && hasCyclicDependencies(project, ref.getProject(), visited))
      {
        return true;
      }

    }

    return false;
  }

}
