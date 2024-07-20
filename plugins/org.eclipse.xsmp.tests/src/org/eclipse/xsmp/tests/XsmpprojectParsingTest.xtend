/*******************************************************************************
 * Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
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
import org.eclipse.xsmp.model.xsmp.Project
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(XsmpprojectInjectorProvider)
class XsmpprojectParsingTest {
    @Inject
    ParseHelper<Project> parseHelper

    @Test
    def void loadModel() {
        val result = parseHelper.parse('''
            /**
             * a project description
             */
            project "TestProject"
            
            
            dependency "dependency1"
            
            tool "org.eclipse.xsmp.tool.smp"
            profile "org.eclipse.xsmp.profile.xsmp-sdk"
            
            
            dependency "dependency2"
            source "smdl/test"
            
            tool "org.eclipse.xsmp.tool.python"
            
            dependency "dependency3"
        ''')
        Assertions.assertNotNull(result)
        val errors = result.eResource.errors
        Assertions.assertTrue(errors.isEmpty, '''Unexpected errors: «errors.join(", ")»''')
    }
}
