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
import org.eclipse.xtext.ui.testing.AbstractHoverTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(XsmpUiInjectorProvider)
class HoverTest extends AbstractHoverTest {

	@BeforeEach
	override void setUp() throws Exception {
		super.setUp()
		 XsmpcatProjectUtil.createProject(projectName)

	}


	@Test def hover_over_catalogue() {
		'''
			/** description */
			catalogue name
		'''.hasHoverOver("name", '''description''')
	}

}
