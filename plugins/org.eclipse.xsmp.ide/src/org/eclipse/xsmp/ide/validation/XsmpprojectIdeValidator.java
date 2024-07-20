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
package org.eclipse.xsmp.ide.validation;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.xsmp.model.xsmp.Project;
import org.eclipse.xsmp.model.xsmp.SourcePath;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xsmp.validation.AbstractXsmpprojectValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

public class XsmpprojectIdeValidator extends AbstractXsmpprojectValidator
{

  @Check
  protected void checkProject(Project p)
  {
    final var baseUri = p.eResource().getURI();

    final var path = Paths.get(baseUri.toFileString()).getParent();

    p.getMember().stream().filter(SourcePath.class::isInstance).map(SourcePath.class::cast)
            .forEach(source -> {
              try
              {
                final var name = source.getName();
                if (!".".equals(name) && !Files.exists(path.resolve(name)))
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
