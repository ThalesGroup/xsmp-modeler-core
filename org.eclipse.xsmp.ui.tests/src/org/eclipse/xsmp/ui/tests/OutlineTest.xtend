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

import org.eclipse.core.runtime.CoreException
import org.eclipse.jdt.core.IJavaProject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.ui.testing.AbstractOutlineTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatUiInjectorProvider)
class OutlineTest extends AbstractOutlineTest {

	@BeforeEach
	override void setUp() throws Exception {
		super.setUp()

	}

	override IJavaProject createjavaProject(String projectName) throws CoreException {
		return XsmpcatProjectUtil.createProject(projectName)
	}

	@AfterEach
	override tearDown() throws Exception {
		super.tearDown()
	}


	
	@Test def void testOutline() throws Exception {
		'''
/** @date 2022-10-12T09:07:56Z */
catalogue catalogue


namespace ns
{
	/** @uuid 5dd75656-6456-4bb2-be92-ad118d358b7c */
	public enum Enum
	{
		L1 = 0,
		L2 = 1
	}

	/**
	 * Deprecated type
	 * 
	 * @deprecated
	 * @uuid b51a06b9-f4ea-4564-9f23-a0adab973040
	 */
	float Float extends Float32

	/** @uuid 9f77d979-ce53-4bef-a872-fe9a721b6d38 */
	integer Integer extends UInt8

	/** @uuid 7c3ee119-841e-4c5b-b520-a0a75e63b71c */
	event Event extends Int8
	
	/** @uuid 3a7e4a73-b465-4c8a-92d7-cf9673440105 */
	array Array = Int8[10]

	/** @uuid a9b35f18-5ca9-4de8-bb3c-485f500ddfec */
	interface Interface
	{
		constant Int16 Cst = 0
		def Bool Operation (Int32 a, out Int64 b)
		def void VoidOperation ()
		property Bool Property
	}


	/**
	 * a model
	 * 
	 * @uuid f62fc385-ffc9-4bc6-8952-95c89ece0948
	 */
	@NoDestructor
	model Model
	{
		@View(ViewKind.VK_All)
		field Float field = Interface.Cst + $cos($PI / 2)

		container IComponent[5] container
		reference IComponent[*] reference

		eventsink Event esi
		eventsource Event eso
	}
}

    '''.assertAllLabels(
			'''
				catalogue
				ns
				  Enum
				    L1
				    L2
				  Float : Smp::Float32
				  Integer : Smp::UInt8
				  Event : Smp::Int8
				  Array : Smp::Int8[10]
				  Interface
				    Cst : Smp::Int16
				    Operation(Smp::Int32, Smp::Int64*) : Smp::Bool
				    VoidOperation() : void
				    Property : Smp::Bool
				  Model
				    field : ns::Float
				    container : Smp::IComponent[5]
				    reference : Smp::IComponent[*]
				    esi : ns::Event
				    eso : ns::Event
			'''
		)
	}

}
