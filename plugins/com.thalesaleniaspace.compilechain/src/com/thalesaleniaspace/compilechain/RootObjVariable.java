/*******************************************************************************
* Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package com.thalesaleniaspace.compilechain;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.variableresolvers.PathVariableResolver;

public class RootObjVariable extends PathVariableResolver
{

  public static final String NAME = "ROOT_OBJ"; //$NON-NLS-1$

  public RootObjVariable()
  {
    // nothing to do.
  }

  @Override
  public String[] getVariableNames(String variable, IResource resource)
  {
    return new String[]{NAME };
  }

  @Override
  public String getValue(String variable, IResource resource)
  {
    final var path = System.getenv(NAME);
    if (path != null)
    {
      return "file:" + path;
    }
    return null;
  }
}
