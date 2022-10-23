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
package org.eclipse.xsmp.ui.tests

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.ui.testing.AbstractQuickfixTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.^extension.ExtendWith

import static extension org.eclipse.xtext.ui.testing.util.JavaProjectSetupUtil.createJavaProject

@ExtendWith(InjectionExtension)
@InjectWith(XsmpUiInjectorProvider)
class QuickfixTest extends AbstractQuickfixTest {

	@BeforeAll def void setup() throws Exception {

		projectName.createJavaProject
	}



}
