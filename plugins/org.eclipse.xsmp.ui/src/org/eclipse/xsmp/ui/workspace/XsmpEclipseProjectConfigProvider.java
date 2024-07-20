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
package org.eclipse.xsmp.ui.workspace;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.ui.internal.XsmpActivator;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;
import org.eclipse.xtext.ui.resource.XtextResourceSetProvider;
import org.eclipse.xtext.ui.workspace.EclipseProjectConfigProvider;

import com.google.inject.Singleton;

@Singleton
public class XsmpEclipseProjectConfigProvider extends EclipseProjectConfigProvider
{
  private static final Logger log = Logger.getLogger(XsmpEclipseProjectConfigProvider.class);

  private static final QualifiedName key = new QualifiedName(
          XsmpEclipseProjectConfigProvider.class.getName(),
          XsmpEclipseProjectConfigProvider.class.getSimpleName());

  @Override
  public IXsmpProjectConfig getProjectConfig(ResourceSet context)
  {
    if (super.getProjectConfig(context) instanceof final IXsmpProjectConfig xsmpProjectConfig)
    {
      return xsmpProjectConfig;
    }

    return null;
  }

  private XsmpEclipseProjectConfig getProjectConfig(IProject eclipseProject)
  {
    if (eclipseProject == null)
    {
      return null;
    }
    try
    {
      return (XsmpEclipseProjectConfig) eclipseProject.getSessionProperty(key);
    }
    catch (final CoreException e)
    {
      return null;
    }
  }

  @Override
  public XsmpEclipseProjectConfig createProjectConfig(IProject eclipseProject)
  {
    var config = getProjectConfig(eclipseProject);
    if (config == null)
    {
      config = initProjectConfig(eclipseProject);
    }

    return config;
  }

  public void resetProjectConfig(IProject eclipseProject)
  {
    if (eclipseProject.isAccessible())
    {
      try
      {
        eclipseProject.setSessionProperty(key, null);
      }
      catch (final CoreException e)
      {
        log.error(e);
      }
    }
  }

  private XsmpEclipseProjectConfig initProjectConfig(IProject eclipseProject)
  {
    if (eclipseProject == null)
    {
      return new XsmpEclipseProjectConfig(eclipseProject, this, null);
    }

    final var file = eclipseProject.getFile(XsmpConstants.XSMP_PROJECT_FILENAME);

    if (!file.exists())
    {
      return new XsmpEclipseProjectConfig(eclipseProject, this, null);
    }

    final var injector = XsmpActivator.getInstance()
            .getInjector(XsmpActivator.ORG_ECLIPSE_XSMP_XSMPPROJECT);

    final var provider = injector.getInstance(XtextResourceSetProvider.class);
    final var rs = provider.get(eclipseProject);
    final var uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
    final var resource = rs.getResource(uri, true);

    final var project = (Project) resource.getContents().get(0);
    final var config = new XsmpEclipseProjectConfig(eclipseProject, this, project);

    if (eclipseProject.isAccessible())
    {
      try
      {
        eclipseProject.setSessionProperty(key, config);
      }
      catch (final CoreException e)
      {
        log.error(e);
      }
    }
    return config;
  }

}
