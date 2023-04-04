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
import org.eclipse.xsmp.documentation.CopyrightNoticeProvider
import org.eclipse.xsmp.xcatalogue.Catalogue
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatInjectorProvider)
class CopyrightNoticeProviderTest {
    @Inject
    ParseHelper<Catalogue> catalogueParseHelper

    @Inject
    CopyrightNoticeProvider provider

    @Test
    def void none() {
        val result = catalogueParseHelper.parse(
    '''
            /**
             * Catalogue Test
             * 
             * @creator daveluy
             * @date 2021-09-30T06:32:34Z
             */
            catalogue Test
        ''')

        var cp = provider.getCopyrightNotice(result.eResource)

        Assertions.assertEquals(null, cp)

    }

    @Test
    def void multiline() {
        val result = catalogueParseHelper.parse(
    '''
            /*******
             * 
            copyright
             * 
             *notice
             * 
             ***********/
            /* comment */
            /**
             * Catalogue Test
             * 
             * @creator daveluy
             * @date 2021-09-30T06:32:34Z
             */
            catalogue Test
        ''')

        var cp = provider.getCopyrightNotice(result.eResource)

        Assertions.assertEquals('''copyright

notice'''.toString, cp)

    }

    @Test
    def void multilineWithPrefix() {
        val result = catalogueParseHelper.parse(
    '''
            /* 
            * 
             * copyright
            
             * notice
             *
             */
            // a comment
            /**
             * Catalogue Test
             * 
             * @creator daveluy
             * @date 2021-09-30T06:32:34Z
             */
            catalogue Test
        ''')

        var cp = provider.getCopyrightNotice(result.eResource, "// ")

        Assertions.assertEquals('''// copyright
// 
// notice'''.toString, cp)

    }

    @Test
    def void singleline() {
        val result = catalogueParseHelper.parse(
    '''
            //
            // copyright
            //
            // notice
            //
            /* comment */
            /**
             * Catalogue Test
             * 
             * @creator daveluy
             * @date 2021-09-30T06:32:34Z
             */
            catalogue Test
        ''')

        var cp = provider.getCopyrightNotice(result.eResource)

        Assertions.assertEquals('''copyright

notice'''.toString, cp)

    }

    @Test
    def void singlelineWithPrefix() {
        val result = catalogueParseHelper.parse(
    '''
            //
            // copyright
            //
            // notice
            //
            
            // comment
            
            /**
             * Catalogue Test
             * 
             * @creator daveluy
             * @date 2021-09-30T06:32:34Z
             */
            catalogue Test
        ''')

        var cp = provider.getCopyrightNotice(result.eResource, "// ")

        Assertions.assertEquals('''// copyright
// 
// notice'''.toString, cp)

    }
}
