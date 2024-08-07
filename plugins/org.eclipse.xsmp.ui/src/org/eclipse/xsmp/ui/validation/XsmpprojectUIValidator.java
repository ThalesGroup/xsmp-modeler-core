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
package org.eclipse.xsmp.ui.validation;

import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.model.xsmp.SourcePath;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xsmp.validation.AbstractXsmpprojectValidator;
import org.eclipse.xtext.ui.resource.ProjectByResourceProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.inject.Inject;

public class XsmpprojectUIValidator extends AbstractXsmpprojectValidator
{

  @Inject
  private ProjectByResourceProvider projectByResourceProvider;

  @Check
  protected void checkProject(Project p)
  {
    // check that project name match with eclipse
    final var project = projectByResourceProvider.getProjectContext(p.eResource());

    p.getMember().stream().filter(SourcePath.class::isInstance).map(SourcePath.class::cast)
            .forEach(source -> {
              try
              {
                final var name = source.getName();
                if (!".".equals(name) && !project.getFolder(name).exists()
                        && !project.getFile(name).exists())
                {
                  acceptError("Source '" + source.getName() + "' does not exist.", source,
                          XsmpPackage.Literals.SOURCE_PATH__NAME,
                          ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
                }
              }
              catch (final IllegalArgumentException e)
              {
                acceptError("Source '" + source.getName() + "' is invalid.", source,
                        XsmpPackage.Literals.SOURCE_PATH__NAME,
                        ValidationMessageAcceptor.INSIGNIFICANT_INDEX, null);
              }
            });
  }

}
