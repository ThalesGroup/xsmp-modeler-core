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
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatInjectorProvider)
class FloatingParsingTest {
    @Inject
    ParseHelper<Catalogue> catalogueParseHelper

    def void check(CharSequence value) {
        val result = catalogueParseHelper.parse(
    '''
            catalogue test
            
            namespace ns
            {
                interface t
                {
                    constant Float64 c = «value» +
                                        «value»f + «value»F +
                                        «value»l+ «value»L 
                }
            }
            
        ''')
        Assertions.assertNotNull(result)
        val errors = result.eResource.errors
        Assertions.assertTrue(errors.isEmpty, '''Unexpected errors: «errors.join(", ")»''')
    }

    @Test
    def void checkDecimalLiteral() {

        // FRACTIONALCONSTANT:    DIGITSEQUENCE? '.' DIGITSEQUENCE    | DIGITSEQUENCE '.'
        // DIGITSEQUENCE:     DIGIT ('\''? DIGIT)*
        check("0.0")
        check("0123456.0123456")
        check(".0123456")
        check("0123456.")

        // with separator
        check("01'23'45'6.01'2'345'6")
        check(".01'23'456")
        check("0'12345'6.")

        // with exponent
        // EXPONENTPART:    'e' SIGN? DIGITSEQUENCE  | 'E' SIGN? DIGITSEQUENCE
        check("0.e1234")
        check("0.e+1234")
        check("0.e-1234")

        check("0.e12'34")
        check("0.e+12'34")
        check("0.e-1'234")

        check("0.E1234")
        check("0.E+1234")
        check("0.E-1234")

        check("0.E12'34")
        check("0.E+12'34")
        check("0.E-1'234")

        // DIGITSEQUENCE EXPONENTPART FLOATINGSUFFIX?
        check("01234E1234")
        check("01'23'4E12'34")

    }

}
