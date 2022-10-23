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
package org.eclipse.xsmp.ui.tests;

import org.eclipse.xsmp.ui.XsmpcatUIPlugin;
import org.eclipse.xsmp.ui.internal.XsmpActivator;
import org.eclipse.xtext.testing.IInjectorProvider;

import com.google.inject.Injector;

public class XsmpUiInjectorProvider implements IInjectorProvider
{

  @Override
  public Injector getInjector()
  {
    return XsmpcatUIPlugin.getInstance().getInjector(XsmpActivator.ORG_ECLIPSE_XSMP_XSMPCAT);
  }

}
