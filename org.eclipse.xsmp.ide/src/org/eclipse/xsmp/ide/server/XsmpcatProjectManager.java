/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.build.IncrementalBuilder.Result;
import org.eclipse.xtext.ide.server.ProjectManager;
import org.eclipse.xtext.resource.IExternalContentSupport.IExternalContentProvider;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

import com.google.inject.Provider;

public class XsmpcatProjectManager extends ProjectManager
{

  IProjectConfig projectConfig;

  @Override
  public void initialize(ProjectDescription description, IProjectConfig projectConfig,
          Procedure2< ? super URI, ? super Iterable<Issue>> acceptor,
          IExternalContentProvider openedDocumentsContentProvider,
          Provider<Map<String, ResourceDescriptionsData>> indexProvider,
          CancelIndicator cancelIndicator)
  {
    super.initialize(description, projectConfig, acceptor, openedDocumentsContentProvider,
            indexProvider, cancelIndicator);
    this.projectConfig = projectConfig;
  }

  @Override
  public Result doInitialBuild(CancelIndicator cancelIndicator)
  {
    final List<URI> allUris = new ArrayList<>();
    for (final ISourceFolder srcFolder : super.getProjectConfig().getSourceFolders())
    {
      allUris.addAll(srcFolder.getAllResources(fileSystemScanner));
    }

    // Include ECSS-SMP library
    final var url = getClass().getResource("/org/eclipse/xsmp/lib/ecss.smp.xsmpcat");
    allUris.add(URI.createURI(url.toString()));

    return super.doBuild(allUris, Collections.emptyList(), Collections.emptyList(),
            cancelIndicator);
  }
}
