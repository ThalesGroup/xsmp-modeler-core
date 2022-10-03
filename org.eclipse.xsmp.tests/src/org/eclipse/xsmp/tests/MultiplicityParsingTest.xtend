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

import com.google.inject.Inject
import org.eclipse.xsmp.xcatalogue.Catalogue
import org.eclipse.xsmp.xcatalogue.Model
import org.eclipse.xsmp.xcatalogue.Namespace
import org.eclipse.xsmp.xcatalogue.Reference
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatInjectorProvider)
class MultiplicityParsingTest {
	@Inject
	ParseHelper<Catalogue> catalogueParseHelper

	@Test
	def void check() {
		val result = catalogueParseHelper.parse(
	'''
			catalogue test
			
			namespace ns
			{
				model  t
				{
					reference i ref0
					reference i[] ref1
					reference i[*] ref2
					reference i[+] ref3
					reference i[5] ref4
					reference i[3 ... 5] ref5
					reference i[2 ... *] ref6
					reference i? ref7
					
				}
			}
			
		''')
		Assertions.assertNotNull(result)
		val errors = result.eResource.errors
		Assertions.assertTrue(errors.isEmpty, '''Unexpected errors: «errors.join(", ")»''')
		val model = (result.member.get(0) as Namespace).member.get(0) as Model

		val ref0 = model.member.get(0) as Reference

		Assertions.assertEquals(1, ref0.getLower());
		Assertions.assertEquals(1, ref0.getUpper());

		val ref1 = model.member.get(1) as Reference

		Assertions.assertEquals(0, ref1.getLower());
		Assertions.assertEquals(-1, ref1.getUpper());

		val ref2 = model.member.get(2) as Reference

		Assertions.assertEquals(0, ref2.getLower());
		Assertions.assertEquals(-1, ref2.getUpper());

		val ref3 = model.member.get(3) as Reference

		Assertions.assertEquals(1, ref3.getLower());
		Assertions.assertEquals(-1, ref3.getUpper());

		val ref4 = model.member.get(4) as Reference

		Assertions.assertEquals(5, ref4.getLower());
		Assertions.assertEquals(5, ref4.getUpper());

		val ref5 = model.member.get(5) as Reference

		Assertions.assertEquals(3, ref5.getLower());
		Assertions.assertEquals(5, ref5.getUpper());

		val ref6 = model.member.get(6) as Reference

		Assertions.assertEquals(2, ref6.getLower());
		Assertions.assertEquals(-1, ref6.getUpper());

		val ref7 = model.member.get(7) as Reference

		Assertions.assertEquals(0, ref7.getLower());
		Assertions.assertEquals(1, ref7.getUpper());
	}

}
