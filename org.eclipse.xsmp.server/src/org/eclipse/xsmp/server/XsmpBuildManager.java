/*******************************************************************************
* Copyright (C) 2023-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.ide.server.TopologicalSorter;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class XsmpBuildManager
{
  /**
   * The resources that are about to be build.
   */
  protected static class ProjectBuildData
  {
    private final List<URI> dirtyFiles;

    private final List<URI> deletedFiles;

    public ProjectBuildData(List<URI> dirtyFiles, List<URI> deletedFiles)
    {
      this.dirtyFiles = dirtyFiles;
      this.deletedFiles = deletedFiles;
    }

    @Override
    public int hashCode()
    {
      return Objects.hash(deletedFiles, dirtyFiles);
    }

    @Override
    public boolean equals(Object obj)
    {
      if (this == obj)
      {
        return true;
      }
      if (obj == null || getClass() != obj.getClass())
      {
        return false;
      }
      final var other = (ProjectBuildData) obj;

      return Objects.equals(deletedFiles, other.deletedFiles)
              && Objects.equals(dirtyFiles, other.dirtyFiles);
    }

    @Override
    public String toString()
    {
      final var b = new ToStringBuilder(this);
      b.add("dirtyFiles", dirtyFiles);
      b.add("deletedFiles", deletedFiles);
      return b.toString();
    }

    public List<URI> getDirtyFiles()
    {
      return dirtyFiles;
    }

    public List<URI> getDeletedFiles()
    {
      return deletedFiles;
    }
  }

  /**
   * A handle that can be used to trigger a build.
   */
  public interface Buildable
  {
    /**
     * Run the build
     */
    List<IResourceDescription.Delta> build(CancelIndicator cancelIndicator);

    /**
     * No build is going to happen.
     */
    Buildable NO_BUILD = cancelIndicator -> Collections.emptyList();
  }

  /**
   * Issue key for cyclic dependencies
   */
  public static final String CYCLIC_PROJECT_DEPENDENCIES = XsmpBuildManager.class.getName()
          + ".cyclicProjectDependencies";

  private XsmpWorkspaceManager workspaceManager;

  @Inject
  private Provider<TopologicalSorter> sorterProvider;

  private final LinkedHashSet<URI> dirtyFiles = new LinkedHashSet<>();

  private final LinkedHashSet<URI> deletedFiles = new LinkedHashSet<>();

  private List<IResourceDescription.Delta> unreportedDeltas = new ArrayList<>();

  /**
   * Enqueue the given file collections.
   *
   * @return a buildable.
   */
  public Buildable submit(List<URI> dirtyFiles, List<URI> deletedFiles, boolean shouldGenerate)
  {
    queue(this.dirtyFiles, deletedFiles, dirtyFiles);
    queue(this.deletedFiles, dirtyFiles, deletedFiles);
    if (shouldGenerate)
    {
      // perform the build now to be sure that files are generated on save action
      final var deltas = internalBuild(CancelIndicator.NullImpl, true);
      return indicator -> deltas;
    }

    return indicator -> internalBuild(indicator, false);
  }

  /**
   * Update the contents of the given set.
   */
  protected void queue(Set<URI> files, Collection<URI> toRemove, Collection<URI> toAdd)
  {
    files.removeAll(toRemove);
    files.addAll(toAdd);
  }

  Map<ProjectDescription, Collection<ProjectDescription>> dependencyToProjects = new HashMap<>();

  /**
   * Run an initial build
   *
   * @return the delta.
   */
  public List<IResourceDescription.Delta> doInitialBuild(List<ProjectDescription> descriptions,
          CancelIndicator indicator)
  {
    final Map<IProjectConfig, Collection<IProjectConfig>> projectToDependencies = new HashMap<>();

    final var sortedDescriptions = sortByDependencies(descriptions);
    final List<IResourceDescription.Delta> result = new ArrayList<>();
    for (final ProjectDescription description : sortedDescriptions)
    {
      final var project = workspaceManager.getProjectManager(description.getName());
      final var dependencies = new ArrayList<IProjectConfig>();
      final var config = project.getProjectConfig();
      IXsmpProjectConfig.collectProjectDependencies(config, dependencies);
      projectToDependencies.put(config, dependencies);
      final var partialresult = project.doInitialBuild(indicator, dependencies);
      result.addAll(partialresult.getAffectedResources());
    }
    // initialize depedencyToProjects
    dependencyToProjects.clear();
    for (final var dependency : workspaceManager.getProjectManagers())
    {
      final var projects = new HashSet<ProjectDescription>();
      for (final var entry : projectToDependencies.entrySet())
      {
        if (entry.getValue().contains(dependency.getProjectConfig()))
        {
          projects.add(workspaceManager.getProjectManager(entry.getKey().getName())
                  .getProjectDescription());
        }
      }
      dependencyToProjects.put(dependency.getProjectDescription(), projects);
    }

    return result;
  }

  @Inject
  protected IFileSystemScanner fileSystemScanner;

  /**
   * Run the build on all projects.
   */
  protected List<IResourceDescription.Delta> internalBuild(CancelIndicator cancelIndicator,
          boolean shouldGenerate)
  {
    final List<URI> allDirty = new ArrayList<>(dirtyFiles);
    final Multimap<ProjectDescription, URI> project2dirty = HashMultimap.create();
    for (final URI dirty : allDirty)
    {
      final var projectManager = workspaceManager.getProjectManager(dirty);
      dependencyToProjects.get(projectManager.getProjectDescription())
              .forEach(d -> project2dirty.put(d, dirty));

    }
    final Multimap<ProjectDescription, URI> project2deleted = HashMultimap.create();
    for (final URI deleted : deletedFiles)
    {
      final var projectManager = workspaceManager.getProjectManager(deleted);

      dependencyToProjects.get(projectManager.getProjectDescription())
              .forEach(d -> project2deleted.put(d, deleted));

    }

    final var sortedDescriptions = sortByDependencies(
            Sets.union(project2dirty.keySet(), project2deleted.keySet()));

    for (final ProjectDescription it : sortedDescriptions)
    {
      final var projectManager = workspaceManager.getProjectManager(it.getName());
      final List<URI> projectDirty = new ArrayList<>(project2dirty.get(it));
      final List<URI> projectDeleted = new ArrayList<>(project2deleted.get(it));
      final var partialResult = projectManager.doBuild(projectDirty, projectDeleted,
              unreportedDeltas, cancelIndicator, shouldGenerate);
      FluentIterable.from(partialResult.getAffectedResources())
              .transform(IResourceDescription.Delta::getUri).copyInto(allDirty);
      dirtyFiles.removeAll(projectDirty);
      deletedFiles.removeAll(projectDeleted);
      mergeWithUnreportedDeltas(partialResult.getAffectedResources());
    }
    final var result = unreportedDeltas;
    unreportedDeltas = new ArrayList<>();
    return result;
  }

  protected void mergeWithUnreportedDeltas(List<IResourceDescription.Delta> newDeltas)
  {
    if (unreportedDeltas.isEmpty())
    {
      unreportedDeltas.addAll(newDeltas);
    }
    else
    {
      final Map<URI, IResourceDescription.Delta> unreportedByUri = IterableExtensions
              .toMap(unreportedDeltas, IResourceDescription.Delta::getUri);
      for (final IResourceDescription.Delta newDelta : newDeltas)
      {
        final var unreportedDelta = unreportedByUri.get(newDelta.getUri());
        if (unreportedDelta == null)
        {
          unreportedDeltas.add(newDelta);
        }
        else
        {
          unreportedDeltas.remove(unreportedDelta);
          final var oldDescription = unreportedDelta.getOld();
          final var newDescription = newDelta.getNew();
          unreportedDeltas.add(new DefaultResourceDescriptionDelta(oldDescription, newDescription));
        }
      }
    }
  }

  /**
   * Get a sorted list of projects to be build.
   */
  protected List<ProjectDescription> sortByDependencies(
          Iterable<ProjectDescription> projectDescriptions)
  {
    return sorterProvider.get().sortByDependencies(projectDescriptions,
            it -> reportDependencyCycle(workspaceManager.getProjectManager(it.getName())));
  }

  protected void reportDependencyCycle(XsmpProjectManager manager)
  {
    manager.reportProjectIssue("Project has cyclic dependencies", CYCLIC_PROJECT_DEPENDENCIES,
            Severity.ERROR);
  }

  public void setWorkspaceManager(XsmpWorkspaceManager workspaceManager)
  {
    this.workspaceManager = workspaceManager;
  }
}