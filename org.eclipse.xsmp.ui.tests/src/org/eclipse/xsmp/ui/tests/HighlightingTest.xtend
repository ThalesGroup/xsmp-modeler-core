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

import com.google.inject.Inject
import org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingConfiguration
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.ui.testing.AbstractHighlightingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

import static extension org.eclipse.xtext.ui.testing.util.JavaProjectSetupUtil.createJavaProject

@ExtendWith(InjectionExtension)
@InjectWith(XsmpUiInjectorProvider)
class HighlightingTest extends AbstractHighlightingTest {

	@Inject extension XsmpcatHighlightingConfiguration

	@BeforeEach override void setUp() throws Exception {
		super.setUp
		projectName.createJavaProject

	}

	@Test def catalogue_keyword() {
		'''
			catalogue c
		'''.testHighlighting("catalogue", keywordTextStyle)
	}

	@Test def namespace_keyword() {
		'''
			catalogue c
			namespace ns {}
		'''.testHighlighting("namespace", keywordTextStyle)
	}

	@Test def single_line_comment() {
		'''
			// A comment
			catalogue c
		'''.testHighlighting("// A comment", commentTextStyle)
	}

	@Test def multi_line_comment() {
		'''
			/*
			 * A multi line comment
			 */
			catalogue c
		'''.testHighlighting('''
			/*
			 * A multi line comment
			 */
		''', commentTextStyle)
	}

	@Test def documentation() {
		'''
			/**
			 * A documentation
			 */
			catalogue c
		'''.testHighlighting('''
			/**
			 * A documentation
			 */
		''', documentationTextStyle)
	}

	@Test def documentation_tag() {
		'''
			/**
			 * A documentation
			 * @deprecated 
			 */
			catalogue c
		'''.testHighlighting("@deprecated", documentationTagTextStyle)
	}

}
