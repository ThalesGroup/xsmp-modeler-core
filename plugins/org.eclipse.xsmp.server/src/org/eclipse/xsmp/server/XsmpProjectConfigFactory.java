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

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.ide.workspace.XsmpProjectConfig;
import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.ide.server.MultiProjectWorkspaceConfigFactory;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.workspace.IWorkspaceConfig;
import org.eclipse.xtext.workspace.WorkspaceConfig;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class XsmpProjectConfigFactory extends MultiProjectWorkspaceConfigFactory
{
  @Inject
  private Provider<XtextResourceSet> resourceSetProvider;

  @Inject
  private UriExtensions uriExtensions;

  private static final Logger LOG = Logger.getLogger(XsmpProjectConfigFactory.class);

  public IXsmpProjectConfig createProjectConfig(URI uri, IWorkspaceConfig workspaceConfig)
  {
    final var rs = resourceSetProvider.get();
    final var resource = rs.getResource(uri, true);

    final var project = (Project) resource.getContents().get(0);
    return new XsmpProjectConfig(project, workspaceConfig);
  }

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
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
        {
          if (XsmpConstants.XSMP_PROJECT_FILENAME.equals(file.getFileName().toString()))
          {
            workspaceConfig.addProject(
                    createProjectConfig(uriExtensions.toEmfUri(file.toUri()), workspaceConfig));
          }
          return FileVisitResult.CONTINUE;
        }
      });
    }
    catch (final IOException e)
    {
      LOG.error(e);
    }

  }

}
