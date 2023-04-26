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
import java.math.BigInteger
import org.eclipse.xsmp.util.XsmpUtil
import org.eclipse.xsmp.xcatalogue.Catalogue
import org.eclipse.xsmp.xcatalogue.Constant
import org.eclipse.xsmp.xcatalogue.Expression
import org.eclipse.xsmp.xcatalogue.Interface
import org.eclipse.xsmp.xcatalogue.Namespace
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

import static org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatInjectorProvider)
class SolverTest {
    @Inject
    ParseHelper<Catalogue> catalogueParseHelper
    @Inject
    protected XsmpUtil xsmpUtil

    def Expression parse(CharSequence value) {
        val result = catalogueParseHelper.parse(
    '''
            catalogue test
            
            namespace ns
            {
                interface t
                {
                    constant Float64 c = «value»
                    constant Float32 c2 = 1.0
                }
            }
            
        ''')
        assertNotNull(result)

        return ((((result.eResource.contents.get(0) as Catalogue).member.get(0) as Namespace).member.
            get(0) as Interface).member.get(0) as Constant).value
    }

    @Test
    def void checkBoolean() {

        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''true''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''false''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''+true''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''-false''')).get)

        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''!false''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''!true''')).get)

        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''false==false''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''false<=false''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''false>=false''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''false!=true''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''false==true''')).get)

        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''false&&false''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''false&&true''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''true&&false''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''true&&true''')).get)

        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''false||false''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''false||true''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''true||false''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''true||true''')).get)

        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''false&false''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''false&true''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''true&false''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''true&true''')).get)

        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''false|false''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''false|true''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''true|false''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''true|true''')).get)

        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''false^false''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''false^true''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''true^false''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''true^true''')).get)

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''false+false''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''false+true''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''true+false''')))
        assertEquals(BigInteger.valueOf(2), xsmpUtil.getInteger(parse('''true+true''')))

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''false-false''')))
        assertEquals(BigInteger.ONE.negate, xsmpUtil.getInteger(parse('''false-true''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''true-false''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''true-true''')))

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''false*false''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''false*true''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''true*false''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''true*true''')))

        assertEquals(null, xsmpUtil.getInteger(parse('''false/false''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''false/true''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''true/false''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''true/true''')))

        assertEquals(null, xsmpUtil.getInteger(parse('''false%false''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''false%true''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''true%false''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''true%true''')))

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''false<<false''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''false<<true''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''true<<false''')))
        assertEquals(BigInteger.valueOf(2), xsmpUtil.getInteger(parse('''true<<true''')))

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''false>>false''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''false>>true''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''true>>false''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''true>>true''')))
    }

    @Test
    def void checkInteger() {

        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''+10''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''0''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''-5''')).get)

        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''!0''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''!10''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''!-5''')).get)

        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''0==0''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''0<=5''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''10>=5''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''8!=9''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''4==5''')).get)

        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''0&&0''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''0&&10''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''10&&0''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''20&&-5''')).get)

        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''0||0''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''0||42''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''-47||0''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''10||20''')).get)

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''0&0''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''0&10''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''42&0''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''3&1''')))

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''0|0''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''0|1''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''1|0''')))
        assertEquals(BigInteger.valueOf(3), xsmpUtil.getInteger(parse('''2|1''')))

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''3^3''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''3^2''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''2^3''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''1^0''')))

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''0+0''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''0+1''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''1+0''')))
        assertEquals(BigInteger.valueOf(2), xsmpUtil.getInteger(parse('''1+1''')))

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''10-10''')))
        assertEquals(BigInteger.ONE.negate, xsmpUtil.getInteger(parse('''9-10''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''10-9''')))

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''0*0''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''0*10''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''10*0''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''1*1''')))

        assertEquals(null, xsmpUtil.getInteger(parse('''10/0''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''0/10''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''2/2''')))

        assertEquals(null, xsmpUtil.getInteger(parse('''5%0''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''0%5''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''5%4''')))

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''0<<0''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''0<<2''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''1<<0''')))
        assertEquals(BigInteger.valueOf(2), xsmpUtil.getInteger(parse('''1<<1''')))

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''0>>0''')))
        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''0>>2''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''1>>0''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''2>>1''')))
    }

    @Test
    def void checkDecimal() {

        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''+10.1''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''+0.0''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''-5.1''')).get)

        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''!0.0''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''!10.1''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''!-5.4''')).get)

        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''0.0==-0.0''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''0.0f==-0.0''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''0.0l==-0.0''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''0<=5.0''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''+10.1>=+5''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''8.7!=9''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''4.1==5''')).get)

        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''0.&&0.''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''0.&&(10.5)''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''-10.2&&-0.0''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''20.0&&-5.2''')).get)

        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''0.||0.''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''(0.||42.1)''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''-47.1||0.''')).get)
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''10.2||20.2''')).get)

        assertEquals(null, xsmpUtil.getInteger(parse('''0.&0''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''0&10.''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''42.&0''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''3.&1.''')))

        assertEquals(null, xsmpUtil.getInteger(parse('''0.|0''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''0|1.''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''1.|0''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''2.|1''')))

        assertEquals(null, xsmpUtil.getInteger(parse('''3.^3''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''3^2.''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''2.^3''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''1.^0.''')))

        assertEquals(0.0, xsmpUtil.getDecimal(parse('''0.+0.''')).doubleValue)
        assertEquals(1.0, xsmpUtil.getDecimal(parse('''0+1.''')).doubleValue)
        assertEquals(1.0, xsmpUtil.getDecimal(parse('''1+0.''')).doubleValue)
        assertEquals(2.0, xsmpUtil.getDecimal(parse('''1.+1''')).doubleValue)

        assertEquals(0.0, xsmpUtil.getDecimal(parse('''10.-10''')).doubleValue)
        assertEquals(-1.0, xsmpUtil.getDecimal(parse('''9.-10''')).doubleValue)
        assertEquals(1.0, xsmpUtil.getDecimal(parse('''10.-9.''')).doubleValue)

        assertEquals(0.0, xsmpUtil.getDecimal(parse('''0*0.''')).doubleValue)
        assertEquals(0.0, xsmpUtil.getDecimal(parse('''0*10.''')).doubleValue)
        assertEquals(0.0, xsmpUtil.getDecimal(parse('''10.*0''')).doubleValue)
        assertEquals(1.0, xsmpUtil.getDecimal(parse('''1.*1.''')).doubleValue)

        assertEquals(null, xsmpUtil.getDecimal(parse('''10./0''')))
        assertEquals(0.0, xsmpUtil.getDecimal(parse('''0./10''')).doubleValue)
        assertEquals(1.0, xsmpUtil.getDecimal(parse('''2/2.''')).doubleValue)

        assertEquals(null, xsmpUtil.getDecimal(parse('''5.%0''')))
        assertEquals(0.0, xsmpUtil.getDecimal(parse('''0.%5.''')).doubleValue)
        assertEquals(1.0, xsmpUtil.getDecimal(parse('''5.%4.''')).doubleValue)

        assertEquals(null, xsmpUtil.getInteger(parse('''0.<<0''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''0<<2.''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''1.<<0''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''1.<<1.''')))

        assertEquals(null, xsmpUtil.getInteger(parse('''0.>>0''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''0>>2.''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''1.>>0''')))
        assertEquals(null, xsmpUtil.getInteger(parse('''2.>>1.''')))
    }

    @Test
    def void checkBuiltinConstant() {
        assertEquals(Math.PI, xsmpUtil.getDecimal(parse('''$PI''')).doubleValue)
        assertEquals(Math.E, xsmpUtil.getDecimal(parse('''$E''')).doubleValue)
        assertEquals(null, xsmpUtil.getDecimal(parse('''$UNKNOWN''')))
    }

    @Test
    def void checkBuiltinFunction() {
        assertEquals(1.0, xsmpUtil.getDecimal(parse('''$cos(0)''')).doubleValue)
        assertEquals(0, xsmpUtil.getDecimal(parse('''$cos($PI/2''')).doubleValue, 0.0001)
        assertEquals(null, xsmpUtil.getDecimal(parse('''$log(0)''')))
    }

    @Test
    def void checkString() {
        assertEquals("test", xsmpUtil.getString(parse('''"test"''')))
        assertEquals("with escape \n", xsmpUtil.getString(parse('''"with escape \n"''')))
        assertEquals("", xsmpUtil.getString(parse('''""''')))
    }

    @Test
    def void checkCharacter() {
        assertEquals("c", xsmpUtil.getChar(parse("'c'")))
        assertEquals("\\n", xsmpUtil.getChar(parse("'\\n'")))
        assertEquals(null, xsmpUtil.getChar(parse("'\\UNKNOWN'")))
    }

    @Test
    @Disabled
    def void checkSingleCollection() {
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''{true}''')).get)
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''{false&&true}''')).get)

        assertEquals(BigInteger.ZERO, xsmpUtil.getInteger(parse('''{0-0}''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInteger(parse('''{1+0}''')))

        assertEquals(0.0, xsmpUtil.getDecimal(parse('''{0.0-0}''')).doubleValue)
        assertEquals(1.0, xsmpUtil.getDecimal(parse('''{1.0+0}''')).doubleValue)
    }

    @Test
    def void checkConstantReference() {

        assertEquals(1.0, xsmpUtil.getDecimal(parse('''c2''')).doubleValue)
    }
}
