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
package org.eclipse.xsmp.tests

import org.eclipse.xsmp.xcatalogue.impl.TypeImplCustom
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

import static org.junit.jupiter.api.Assertions.*

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatInjectorProvider)
class TypeTest {

	static class Type extends TypeImplCustom {
	}

	@Test
	def void checkUuid() {

		var elem = new Type

		elem.uuid = "aaaa"
		assertEquals("aaaa", elem.uuid)
		assertEquals('''/** @uuid aaaa */'''.toString, elem.metadatum.documentation)

		elem.uuid = null
		assertEquals(null, elem.uuid)
		assertEquals(null, elem.metadatum.documentation)
	}
}
