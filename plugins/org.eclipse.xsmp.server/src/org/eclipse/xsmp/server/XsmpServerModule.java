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
package org.eclipse.xsmp.server;

import java.util.concurrent.ExecutorService;

import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.xtext.ide.ExecutorServiceProvider;
import org.eclipse.xtext.ide.server.IMultiRootWorkspaceConfigFactory;
import org.eclipse.xtext.ide.server.IProjectDescriptionFactory;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.containers.ProjectDescriptionBasedContainerManager;

import com.google.inject.AbstractModule;

public class XsmpServerModule extends AbstractModule
{

  @Override
  protected void configure()
  {
    binder().bind(ExecutorService.class).toProvider(ExecutorServiceProvider.class);

    bind(LanguageServer.class).to(XsmpLanguageServer.class);
    bind(IResourceServiceProvider.Registry.class)
            .toProvider(XsmpResourceServiceProviderServiceLoader.class);
    bind(IMultiRootWorkspaceConfigFactory.class).to(XsmpProjectConfigFactory.class);
    bind(IProjectDescriptionFactory.class).to(XsmpProjectDescriptionFactory.class);
    bind(IContainer.Manager.class).to(ProjectDescriptionBasedContainerManager.class);

  }

}
