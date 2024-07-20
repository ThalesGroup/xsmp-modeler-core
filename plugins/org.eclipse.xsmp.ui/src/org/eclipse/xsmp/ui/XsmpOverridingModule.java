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
package org.eclipse.xsmp.ui;

import org.eclipse.xsmp.ui.workspace.XsmpEclipseProjectConfigProvider;
import org.eclipse.xtext.ui.workspace.EclipseProjectConfigProvider;

import com.google.inject.Binder;
import com.google.inject.Module;

public class XsmpOverridingModule implements Module
{

  @Override
  public void configure(Binder binder)
  {
    // this is required by XtextResourceSetProvider to retrieve the specialized project config
    // provider
    binder.bind(EclipseProjectConfigProvider.class).to(XsmpEclipseProjectConfigProvider.class);
  }

}
