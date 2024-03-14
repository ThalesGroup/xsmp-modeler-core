/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
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
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xsmp.model.xsmp.Assembly

@ExtendWith(InjectionExtension)
@InjectWith(XsmpasbInjectorProvider)
class XsmpasbParsingTest {
    @Inject
    ParseHelper<Assembly> parseHelper

    @Test
    def void loadModel() {
        val result = parseHelper.parse('''
            assembly assembly
            
            namespace Test
            {
                factory MyFactory extends "uuid"
                {
                    set "field" = 5
                }
                factory MyFactory2 extends MyFactory
                {
                    set "field" = 5
                }
                simulator sim
                {
                }
                
                simulator sim2 extends sim
                {
                    
                }
            }
        ''')
        Assertions.assertNotNull(result)
        val errors = result.eResource.errors
        Assertions.assertTrue(errors.isEmpty, '''Unexpected errors: «errors.join(", ")»''')
    }
}
