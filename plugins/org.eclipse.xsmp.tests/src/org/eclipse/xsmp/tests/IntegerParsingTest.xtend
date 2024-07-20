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
import org.eclipse.xsmp.model.xsmp.Catalogue
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatInjectorProvider)
class IntegerParsingTest {
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
                    constant UInt64 c = «value» +
                                        «value»u + «value»U +
                                        «value»l+ «value»L + 
                                        «value»ul + «value»uL + 
                                        «value»Ul +«value»UL +
                                        «value»lu + «value»Lu + 
                                        «value»lU +«value»LU
                }
            }
            
        ''')
        Assertions.assertNotNull(result)
        val errors = result.eResource.errors
        Assertions.assertTrue(errors.isEmpty, '''Unexpected errors: «errors.join(", ")»''')
    }

    def void checkTimeSuffix(CharSequence value) {
        val result = catalogueParseHelper.parse(
    '''
            catalogue test
            
            namespace ns
            {
                interface t
                {
                    constant UInt64 c = «value» +
                                        «value»ns + «value»us +
                                        «value»ms+ «value»s + «value»mn + «value»h +
                                        «value»d + «value»y
                }
            }
            
        ''')
        Assertions.assertNotNull(result)
        val errors = result.eResource.errors
        Assertions.assertTrue(errors.isEmpty, '''Unexpected errors: «errors.join(", ")»''')
    }

    @Test
    def void checkDecimalLiteral() {
        // NONZERODIGIT ('\''? DIGIT)*
        check("1")
        check("2")
        check("3")
        check("4")
        check("5")
        check("6")
        check("7")
        check("8")
        check("9")

        // with >1 digits
        check("10")
        check("20")
        check("30")
        check("40")
        check("50")
        check("60")
        check("70")
        check("80")
        check("90")

        // with separator
        check("1'0")
        check("2'0")
        check("3'0")
        check("4'0")
        check("5'0")
        check("6'0")
        check("7'0")
        check("8'0")
        check("9'0")

        // ********** with time suffix *********
        checkTimeSuffix("1")
        checkTimeSuffix("2")
        checkTimeSuffix("3")
        checkTimeSuffix("4")
        checkTimeSuffix("5")
        checkTimeSuffix("6")
        checkTimeSuffix("7")
        checkTimeSuffix("8")
        checkTimeSuffix("9")

        // with >1 digits
        checkTimeSuffix("10")
        checkTimeSuffix("20")
        checkTimeSuffix("30")
        checkTimeSuffix("40")
        checkTimeSuffix("50")
        checkTimeSuffix("60")
        checkTimeSuffix("70")
        checkTimeSuffix("80")
        checkTimeSuffix("90")

        // with separator
        checkTimeSuffix("1'0")
        checkTimeSuffix("2'0")
        checkTimeSuffix("3'0")
        checkTimeSuffix("4'0")
        checkTimeSuffix("5'0")
        checkTimeSuffix("6'0")
        checkTimeSuffix("7'0")
        checkTimeSuffix("8'0")
        checkTimeSuffix("9'0")
    }

    @Test
    def void checkBinaryLiteral() {
        // ('0b' | '0B') BINARYDIGIT ('\''? BINARYDIGIT)*
        check("0b0")
        check("0b1")
        check("0B0")
        check("0B1")

        // with 4 digits
        check("0b0111")
        check("0b1111")
        check("0B0111")
        check("0B1111")
        // with separator
        check("0b01'11")
        check("0b11'11")
        check("0B01'11")
        check("0B11'11")
    }

    @Test
    def void checkOctalLiteral() {
        // '0' ('\''? OCTALDIGIT)*
        check("0")
        check("01")
        check("02")
        check("03")
        check("04")
        check("05")
        check("06")
        check("07")

        // with separator
        check("0'0")
        check("0'1")
        check("0'2")
        check("0'3")
        check("0'4")
        check("0'5")
        check("0'6")
        check("0'7")

        // ********** with time suffix *********
        checkTimeSuffix("0")
        checkTimeSuffix("01")
        checkTimeSuffix("02")
        checkTimeSuffix("03")
        checkTimeSuffix("04")
        checkTimeSuffix("05")
        checkTimeSuffix("06")
        checkTimeSuffix("07")

        // with separator
        checkTimeSuffix("0'0")
        checkTimeSuffix("0'1")
        checkTimeSuffix("0'2")
        checkTimeSuffix("0'3")
        checkTimeSuffix("0'4")
        checkTimeSuffix("0'5")
        checkTimeSuffix("0'6")
        checkTimeSuffix("0'7")
    }

    @Test
    def void checkHexedecimalLiteral() {
        // ('0x' | '0X') HEXADECIMALDIGIT ('\''? HEXADECIMALDIGIT)*
        check("0x0")
        check("0x1")
        check("0x2")
        check("0x3")
        check("0x4")
        check("0x5")
        check("0x6")
        check("0x7")
        check("0x8")
        check("0x9")
        check("0xa")
        check("0xb")
        check("0xc")
        check("0xd")
        check("0xe")
        check("0xf")
        check("0xA")
        check("0xB")
        check("0xC")
        check("0xD")
        check("0xE")
        check("0xF")

        check("0X0")
        check("0X1")
        check("0X2")
        check("0X3")
        check("0X4")
        check("0X5")
        check("0X6")
        check("0X7")
        check("0X8")
        check("0X9")
        check("0Xa")
        check("0Xb")
        check("0Xc")
        check("0Xd")
        check("0Xe")
        check("0Xf")
        check("0XA")
        check("0XB")
        check("0XC")
        check("0XD")
        check("0XE")
        check("0XF")

        // with >1 digits
        check("0x00")
        check("0x10")
        check("0x20")
        check("0x30")
        check("0x40")
        check("0x50")
        check("0x60")
        check("0x70")
        check("0x80")
        check("0x90")
        check("0xa0")
        check("0xb0")
        check("0xc0")
        check("0xd0")
        check("0xe0")
        check("0xf0")
        check("0xA0")
        check("0xB0")
        check("0xC0")
        check("0xD0")
        check("0xE0")
        check("0xF0")

        check("0X00")
        check("0X10")
        check("0X20")
        check("0X30")
        check("0X40")
        check("0X50")
        check("0X60")
        check("0X70")
        check("0X80")
        check("0X90")
        check("0Xa0")
        check("0Xb0")
        check("0Xc0")
        check("0Xd0")
        check("0Xe0")
        check("0Xf0")
        check("0XA0")
        check("0XB0")
        check("0XC0")
        check("0XD0")
        check("0XE0")
        check("0XF0")
        // with separator
        check("0x0'0")
        check("0x1'0")
        check("0x2'0")
        check("0x3'0")
        check("0x4'0")
        check("0x5'0")
        check("0x6'0")
        check("0x7'0")
        check("0x8'0")
        check("0x9'0")
        check("0xa'0")
        check("0xb'0")
        check("0xc'0")
        check("0xd'0")
        check("0xe'0")
        check("0xf'0")
        check("0xA'0")
        check("0xB'0")
        check("0xC'0")
        check("0xD'0")
        check("0xE'0")
        check("0xF'0")

        check("0X0'0")
        check("0X1'0")
        check("0X2'0")
        check("0X3'0")
        check("0X4'0")
        check("0X5'0")
        check("0X6'0")
        check("0X7'0")
        check("0X8'0")
        check("0X9'0")
        check("0Xa'0")
        check("0Xb'0")
        check("0Xc'0")
        check("0Xd'0")
        check("0Xe'0")
        check("0Xf'0")
        check("0XA'0")
        check("0XB'0")
        check("0XC'0")
        check("0XD'0")
        check("0XE'0")
        check("0XF'0")
    }
}
