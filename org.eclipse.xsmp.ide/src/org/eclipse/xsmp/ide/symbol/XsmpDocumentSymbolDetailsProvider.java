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
package org.eclipse.xsmp.ide.symbol;

import static org.eclipse.xsmp.model.xsmp.XsmpPackage.PROFILE_REFERENCE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.PROJECT_REFERENCE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.SOURCE_FOLDER;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.TOOL_REFERENCE;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.model.xsmp.VisibilityElement;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolDetailsProvider;

public class XsmpDocumentSymbolDetailsProvider extends DocumentSymbolDetailsProvider
{

  @Override
  public String getDetails(EObject object)
  {
    final var builder = new StringBuilder();

    if (object instanceof final VisibilityElement ve && ve.getVisibility() != null)
    {
      builder.append(ve.getRealVisibility().getLiteral()).append(" ");
    }

    switch (object.eClass().getClassifierID())
    {
      case SOURCE_FOLDER -> builder.append("Source");
      case TOOL_REFERENCE -> builder.append("Tool");
      case PROFILE_REFERENCE -> builder.append("Profile");
      case PROJECT_REFERENCE -> builder.append("Dependency");
      default -> builder.append(object.eClass().getName());
    }

    return builder.toString();
  }
}
