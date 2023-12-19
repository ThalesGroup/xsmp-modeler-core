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
package org.eclipse.xsmp.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.xsmp.XsmpcoreRuntimeModule;
import org.eclipse.xsmp.XsmpcoreStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class XsmpcoreIdeSetup extends XsmpcoreStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new XsmpcoreRuntimeModule(), new XsmpcoreIdeModule()));
	}
	
}
