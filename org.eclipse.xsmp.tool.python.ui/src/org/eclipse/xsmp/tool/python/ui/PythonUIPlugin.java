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

import org.eclipse.xsmp.tool.python.PythonRuntimeModule;
import org.eclipse.xsmp.ui.AbstractXsmpcatUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.inject.Module;

/**
 * This is the central singleton for the Smp Tool UI plugin.
 */
public final class PythonUIPlugin extends AbstractXsmpcatUIPlugin {
	public static final String PLUGIN_ID = "org.eclipse.xsmp.tool.python.ui";

	private static PythonUIPlugin instance;

	public static PythonUIPlugin getInstance() {
		return instance;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		instance = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		super.stop(context);
	}

	@Override
	protected com.google.inject.Module getRuntimeModule() {
		return new PythonRuntimeModule();
	}

	@Override
	protected Module getUiModule() {
		return new PythonUIModule(this);
	}
}
