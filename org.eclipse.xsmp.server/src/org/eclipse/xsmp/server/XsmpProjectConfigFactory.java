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

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.ide.workspace.XsmpProjectConfigProvider;
import org.eclipse.xtext.ide.server.MultiProjectWorkspaceConfigFactory;
import org.eclipse.xtext.workspace.WorkspaceConfig;

import com.google.inject.Inject;

public class XsmpProjectConfigFactory extends MultiProjectWorkspaceConfigFactory
{

  private final class ProjectVisitor extends SimpleFileVisitor<Path>
  {
    private final WorkspaceConfig workspaceConfig;

    private final Predicate<Path> filter;

    private ProjectVisitor(WorkspaceConfig workspaceConfig, Predicate<Path> filter)
    {
      this.workspaceConfig = workspaceConfig;
      this.filter = filter;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
    {
      if (attrs.isSymbolicLink())
      {
        return FileVisitResult.SKIP_SUBTREE;
      }
      final var xsmpProjectFile = dir.resolve(XsmpConstants.XSMP_PROJECT_FILENAME);
      if (filter.test(dir) && Files.exists(xsmpProjectFile))
      {
        final var config = projectConfigProvider.createProjectConfig(
                URI.createFileURI(xsmpProjectFile.toString()), workspaceConfig);
        workspaceConfig.addProject(config);
        if (!config.getIncludeFolders().isEmpty())
        {
          Files.walkFileTree(Paths.get(config.getPath().path()),
                  new ProjectVisitor(workspaceConfig, p -> !p.equals(dir) && config
                          .findIncludeFolderContaining(URI.createFileURI(p.toString())) != null));
        }

        return FileVisitResult.SKIP_SUBTREE;
      }
      return super.preVisitDirectory(dir, attrs);
    }
  }

  @Inject
  private XsmpProjectConfigProvider projectConfigProvider;

  @Override
  public void findProjects(WorkspaceConfig workspaceConfig, URI uri)
  {
    if (uri == null)
    {
      return;
    }
    final var baseFile = new File(uri.toFileString());
    if (!baseFile.isDirectory())
    {
      return;
    }
    try
    {
      Files.walkFileTree(Paths.get(baseFile.toURI()),
              new ProjectVisitor(workspaceConfig, p -> true));
    }
    catch (final IOException e)
    {
      e.printStackTrace();
    }

  }

}
