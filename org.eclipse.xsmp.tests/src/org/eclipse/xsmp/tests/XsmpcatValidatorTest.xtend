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
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.xsmp.xcatalogue.Catalogue
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

import static org.eclipse.xsmp.validation.XsmpcatIssueCodesProvider.*
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.*
import static org.junit.jupiter.api.Assertions.*

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatInjectorProvider)
class XsmpcatValidatorTest {
    @Inject ParseHelper<Catalogue> parsehelper
    @Inject extension ValidationTestHelper
    @Inject
    Provider<XtextResourceSet> resourceSetProvider;

    def Catalogue parse(CharSequence chr) {
        var cat = parsehelper.parse(chr);
        parsehelper.parse(getClass().getResource("/org/eclipse/xsmp/lib/ecss.smp.xsmpcat").openStream,
            URI.createURI("ecss.smp.xsmpcat"), null, cat.eResource.resourceSet)
        return cat
    }

    @Test
    def void checkValid() {
        var resourceSet = resourceSetProvider.get
        parsehelper.parse(getClass().getResource("/org/eclipse/xsmp/lib/ecss.smp.xsmpcat").openStream,
            URI.createURI("ecss.smp.xsmpcat"), null, resourceSet)

        parsehelper.parse(getClass().getResource("testValid.xsmpcat").openStream,
            URI.createURI("testValid.xsmpcat"), null, resourceSet).assertNoErrors
    }

    @Test
    def void checkNameISReservedKeyword() {
        val model = '''
            catalogue Test
            namespace constexpr
            {}
        '''
        model.parse => [
            assertNumberOfIssues(1)
            assertError(NAMESPACE, NAME_IS_RESERVED_KEYWORD, model.indexOf("constexpr"), 9,
                "An Element Name shall not be an ISO/ANSI C++ keyword.")
        ]
    }

    @Test
    def void checkNameISInvalid() {
        val model = '''
            catalogue Test
            namespace _invalid
            {}
        '''
        model.parse => [
            assertNumberOfIssues(1)
            assertError(NAMESPACE, NAME_IS_INVALID, model.indexOf("_invalid"), 8,
                "An Element Name shall only contain letters, digits, and the underscore.")
        ]
    }

    @Test
    def void checkArraySize() {
        val model = '''
            catalogue Test
            namespace ns
            {
                /** @uuid d8beb14e-594e-44df-a90c-e42908dc8192 */
                array anArray = Int8[-10]
            }
        '''
        model.parse => [
            assertNumberOfIssues(1)
            assertError(UNARY_OPERATION, INVALID_VALUE_RANGE, model.indexOf("-10"), 3,
                "Integral value -10 is not in range 0 ... 9223372036854775807.")
        ]

    }

    private def assertNumberOfIssues(Catalogue domainModel, int expectedNumberOfIssues) {
        assertEquals(expectedNumberOfIssues, domainModel.validate.size)
    }
}
