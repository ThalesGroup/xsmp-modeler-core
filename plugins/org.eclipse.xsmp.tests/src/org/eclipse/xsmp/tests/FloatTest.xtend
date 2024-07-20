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
class FloatTest {

    @Test
    def void checkUnit() {

        var elem = XsmpFactory.eINSTANCE.createFloat

        elem.unit = "V"
        assertEquals("V", elem.unit)
        assertEquals('''/** @unit V */'''.toString, elem.metadatum.documentation)

        elem.unit = null
        assertEquals(null, elem.unit)
        assertEquals(null, elem.metadatum.documentation)
    }

}
