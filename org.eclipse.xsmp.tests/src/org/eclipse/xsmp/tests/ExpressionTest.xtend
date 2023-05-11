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
import org.eclipse.xsmp.util.Solver

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatInjectorProvider)
class ExpressionTest extends Solver {
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
    def void checkBuiltinConstant() {
        assertEquals(Math.PI, xsmpUtil.getFloat64(parse('''$PI''')))
        assertEquals(Math.E, xsmpUtil.getFloat64(parse('''$E''')))
 
    }

    @Test
    def void checkBuiltinFunction() {
        assertEquals(1.0, xsmpUtil.getFloat64(parse('''$cos(0)''')))
        assertEquals(0, xsmpUtil.getFloat64(parse('''$cos($PI/2)''')), 0.0001)
     
    }

    @Test
    def void checkString() {
        assertEquals("test", xsmpUtil.getString8(parse('''"test"''')))
        assertEquals("with escape \n", xsmpUtil.getString8(parse('''"with escape \n"''')))
        assertEquals("", xsmpUtil.getString8(parse('''""''')))
    }

    @Test
    def void checkCharacter() {
        assertEquals("c", xsmpUtil.getChar8(parse("'c'")))
        assertEquals("\\n", xsmpUtil.getChar8(parse("'\\n'")))
    }

    @Test
    @Disabled
    def void checkSingleCollection() {
        assertEquals(Boolean.TRUE, xsmpUtil.getBoolean(parse('''{true}''')))
        assertEquals(Boolean.FALSE, xsmpUtil.getBoolean(parse('''{false&&true}''')))

        assertEquals(BigInteger.ZERO, xsmpUtil.getInt32(parse('''{0-0}''')))
        assertEquals(BigInteger.ONE, xsmpUtil.getInt32(parse('''{1+0}''')))

        assertEquals(0.0, xsmpUtil.getFloat64(parse('''{0.0-0}''')))
        assertEquals(1.0, xsmpUtil.getFloat64(parse('''{1.0+0}''')))
    }


}
