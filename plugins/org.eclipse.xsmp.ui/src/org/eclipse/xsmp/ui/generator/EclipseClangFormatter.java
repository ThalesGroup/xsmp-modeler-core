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
package org.eclipse.xsmp.ui.generator;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xsmp.generator.ClangFormatter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class EclipseClangFormatter extends ClangFormatter
{
  @Inject
  private IWorkspaceRoot root;

  @Override
  public String toFileString(URI uri)
  {
    if (uri.isPlatform())
    {
      return root.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)))
              .getLocation().toString();
    }

    return super.toFileString(uri);
  }

}
