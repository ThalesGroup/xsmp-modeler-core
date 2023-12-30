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
package org.eclipse.xsmp.tool.python.ui;

import org.eclipse.xsmp.ui.extension.ExtensionProvider;

import com.google.inject.Injector;

public class PythonExtension implements ExtensionProvider
{

  @Override
  public Injector getInjector(String languageName)
  {
    final var activator = PythonUIPlugin.getInstance();
    return activator != null ? activator.getInjector(languageName) : null;
  }
}
