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
package org.eclipse.xsmp.tool.python.ui;

import org.eclipse.xsmp.ui.internal.XsmpActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.google.inject.Injector;

public class PythonExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory
{

  @Override
  protected Bundle getBundle()
  {
    return FrameworkUtil.getBundle(PythonUIPlugin.class);
  }

  @Override
  protected Injector getInjector()
  {
    final var activator = PythonUIPlugin.getInstance();
    return activator != null ? activator.getInjector(XsmpActivator.ORG_ECLIPSE_XSMP_XSMPCAT) : null;
  }

}
