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
package org.eclipse.xsmp.workspace;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;

public interface IXsmpProjectConfig extends IProjectConfig
{

  String getProfile();

  Collection<String> getTools();

  Collection<String> getDependencies();

  /** @return a set of all include folders */
  Set< ? extends ISourceFolder> getIncludeFolders();

  /**
   * Finds the source folder that physically contains this member or null if none was found.
   */
  ISourceFolder findIncludeFolderContaining(URI member);

}
