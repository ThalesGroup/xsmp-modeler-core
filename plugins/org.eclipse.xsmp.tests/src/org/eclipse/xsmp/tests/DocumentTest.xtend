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

import java.util.Date
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xsmp.model.xsmp.XsmpFactory
import org.eclipse.xsmp.model.xsmp.XsmpPackage
import org.eclipse.xsmp.model.xsmp.impl.DocumentImplCustom
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

import static org.junit.jupiter.api.Assertions.*

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatInjectorProvider)
class DocumentTest {

    static class Document extends DocumentImplCustom {
    }

    @Test
    def void checkVersion() {

        var elem = new Document

        elem.version = "1.0.0"
        assertEquals('''/** @version 1.0.0 */'''.toString, elem.metadatum.documentation)

        elem.version = null
        assertEquals(null, elem.metadatum.documentation)
    }

    @Test
    def void checkDate() {

        var elem = XsmpFactory.eINSTANCE.createCatalogue

        var date = new Date()
        elem.date = date
        assertEquals(
            '''/** @date «EcoreUtil.convertToString(XsmpPackage.Literals.DOCUMENT__DATE.getEAttributeType(), date)» */'''.
                toString, elem.metadatum.documentation)

        elem.date = null
        assertEquals(null, elem.metadatum.documentation)
    }

    @Test
    def void checkTitle() {

        var elem = XsmpFactory.eINSTANCE.createCatalogue

        elem.title = "A title"
        assertEquals("A title", elem.title)
        assertEquals('''/** @title A title */'''.toString, elem.metadatum.documentation)

        elem.title = null
        assertEquals(null, elem.title)
        assertEquals(null, elem.metadatum.documentation)
    }

    @Test
    def void checkCreator() {

        var elem = XsmpFactory.eINSTANCE.createCatalogue

        elem.creator += "User1"
        assertEquals(#["User1"], elem.creator)
        assertEquals('''/** @creator User1 */'''.toString, elem.metadatum.documentation)

        elem.creator += "User2"
        assertEquals(#["User1", "User2"], elem.creator)
        assertEquals('''
        /**
         * @creator User1
         * @creator User2
         */'''.toString, elem.metadatum.documentation)

        elem.creator.remove(0)
        assertEquals(#["User2"], elem.creator)
        assertEquals('''/** @creator User2 */'''.toString, elem.metadatum.documentation)

        elem.creator.clear
        assertEquals(#[], elem.creator)
        assertEquals(null, elem.metadatum.documentation)
    }
}
