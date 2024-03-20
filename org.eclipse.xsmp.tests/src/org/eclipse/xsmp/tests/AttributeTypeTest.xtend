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

import org.eclipse.xsmp.model.xsmp.XsmpFactory
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

import static org.junit.jupiter.api.Assertions.*

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatInjectorProvider)
class AttributeTypeTest {

    @Test
    def void checkAllowMultiple() {

        var elem = XsmpFactory.eINSTANCE.createAttributeType

        elem.allowMultiple = true
        assertEquals(true, elem.allowMultiple)
        assertEquals('''/** @allowMultiple */'''.toString, elem.metadatum.documentation)

        elem.allowMultiple = false
        assertEquals(null, elem.metadatum.documentation)
    }

    @Test
    def void checkUsage() {

        var elem = XsmpFactory.eINSTANCE.createAttributeType

        elem.usage += "Operation"
        assertEquals(#["Operation"], elem.usage)
        assertEquals('''/** @usage Operation */'''.toString, elem.metadatum.documentation)

        elem.usage += "Field"
        assertEquals(#["Operation", "Field"], elem.usage)
        assertEquals('''
        /**
         * @usage Operation
         * @usage Field
         */'''.toString, elem.metadatum.documentation)

        elem.usage.remove(0)
        assertEquals(#["Field"], elem.usage)
        assertEquals('''/** @usage Field */'''.toString, elem.metadatum.documentation)

        elem.usage.clear
        assertEquals(#[], elem.usage)
        assertEquals(null, elem.metadatum.documentation)
    }
}
