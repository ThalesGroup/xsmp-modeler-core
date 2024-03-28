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

import org.eclipse.emf.common.util.URI;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.ide.workspace.XsmpProjectConfigProvider;
import org.eclipse.xtext.ide.server.MultiProjectWorkspaceConfigFactory;
import org.eclipse.xtext.workspace.WorkspaceConfig;

import com.google.inject.Inject;

public class XsmpProjectConfigFactory extends MultiProjectWorkspaceConfigFactory
{

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
      Files.walkFileTree(Paths.get(baseFile.toURI()), new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
          throws IOException
        {
          if (attrs.isSymbolicLink())
          {
            return FileVisitResult.SKIP_SUBTREE;
          }
          final var xsmpProjectFile = dir.resolve(XsmpConstants.XSMP_PROJECT_FILENAME);
          if (Files.exists(xsmpProjectFile))
          {
            workspaceConfig.addProject(projectConfigProvider.createProjectConfig(
                    URI.createFileURI(xsmpProjectFile.toString()), workspaceConfig));
            return FileVisitResult.SKIP_SUBTREE;
          }
          return super.preVisitDirectory(dir, attrs);
        }
      });
    }
    catch (final IOException e)
    {
      e.printStackTrace();
    }

  }

}
