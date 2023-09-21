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
package org.eclipse.xsmp.ide.workspace;

import java.util.List;

import org.eclipse.xtext.workspace.IProjectConfig;

public interface IXsmpProjectConfig extends IProjectConfig
{
  List<String> getDependencies();

  String getProfile();

  List<String> getTools();

  void refresh();

  boolean shouldGenerate();
}
