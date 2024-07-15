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

import org.eclipse.xsmp.model.xsmp.IncludePath;
import org.eclipse.xsmp.model.xsmp.ProfileReference;
import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.model.xsmp.ProjectReference;
import org.eclipse.xsmp.model.xsmp.SourcePath;
import org.eclipse.xsmp.model.xsmp.Tool;
import org.eclipse.xsmp.model.xsmp.ToolReference;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.ComposedChecks;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;
import org.eclipse.xtext.workspace.IProjectConfigProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This class contains custom validation rules.
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@Singleton
@ComposedChecks(validators = {XsmpprojectUniqueElementValidator.class })
public class XsmpprojectValidator extends AbstractXsmpprojectValidator
{
  @Inject
  private IProjectConfigProvider configProvider;

  @Check
  protected void checkProfile(Project p)
  {

    // check no duplicated profile
    ProfileReference profile = null;
    for (final var member : p.getMember())
    {
      if (member instanceof final ProfileReference ref)
      {
        if (profile == null)
        {
          profile = ref;
        }
        else
        {
          acceptError("A profile is already defined.", ref, null, INSIGNIFICANT_INDEX, null);
        }
      }
    }
  }

  @Check
  protected void checkSources(Project p)
  {
    final var visitedSources = new HashSet<String>();
    p.getMember().stream().filter(SourcePath.class::isInstance).map(SourcePath.class::cast)
            .forEach(source -> {
              final var name = source.getName();

              if (!visitedSources.add(name))
              {
                acceptError("Duplicated Source path '" + name + "'.", source, null,
                        INSIGNIFICANT_INDEX, null);
              }
              if (name.startsWith("/"))
              {
                acceptError(
                        "Source path '" + source.getName() + "' is not relative to project path.",
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
            });
  }

  @Check
  protected void checkIncludes(IncludePath p)
  {
    error("Include path are deprecated. Support will be dropped in next release.", null);
  }

  @Check
  protected void checkDependencies(Project p)
  {

    final var visitedDependencies = new HashSet<Project>();
    // check no duplicate dependency and different that current project
    p.getMember().stream().filter(ProjectReference.class::isInstance)
            .map(ProjectReference.class::cast).forEach(dependency -> {
              final var project = dependency.getProject();

              if (!visitedDependencies.add(project))
              {
                acceptError("Duplicated dependency '" + dependency.getName() + "'.", dependency,
                        null, INSIGNIFICANT_INDEX, null);
              }
            });
    // check no cyclic dependency
    p.getMember().stream().filter(ProjectReference.class::isInstance)
            .map(ProjectReference.class::cast).forEach(dependency -> {
              if (hasCyclicDependencies(p, dependency.getProject(), visitedDependencies))
              {
                acceptError("Cyclic dependency detected '" + dependency.getName() + "'.",
                        dependency, null, INSIGNIFICANT_INDEX, null);
              }
            });
  }

  @Check
  protected void checkTools(Project p)
  {

    final var visitedTools = new HashSet<Tool>();
    p.getMember().stream().filter(ToolReference.class::isInstance).map(ToolReference.class::cast)
            .forEach(toolReference -> {
              final var tool = toolReference.getTool();
              if (!visitedTools.add(tool))
              {
                acceptError("Duplicated tool '" + tool.getName() + "'.", toolReference, null,
                        INSIGNIFICANT_INDEX, null);
              }
            });
  }

  @Check
  protected void checkProject(Project p)
  {
    final var resource = p.eResource();

    if (resource == null)
    {
      return;
    }
    final var rs = resource.getResourceSet();

    if (rs == null)
    {
      return;
    }

    final var config = configProvider.getProjectConfig(rs);

    if (!config.getName().equals(p.getName()))
    {
      error("'" + p.getName() + "' does not match with project name '" + config.getName() + "'.",
              XsmpPackage.Literals.NAMED_ELEMENT__NAME);
    }

  }

  private boolean hasCyclicDependencies(Project project, Project dependency, Set<Project> visited)
  {
    return dependency.getMember().stream().filter(ProjectReference.class::isInstance)
            .map(ProjectReference.class::cast)
            .anyMatch(ref -> project.equals(ref.getProject()) || visited.add(ref.getProject())
                    && hasCyclicDependencies(project, ref.getProject(), visited));
  }

}
