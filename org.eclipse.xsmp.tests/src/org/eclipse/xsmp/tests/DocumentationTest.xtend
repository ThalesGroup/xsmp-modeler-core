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

import org.eclipse.xsmp.documentation.Documentation
import org.eclipse.xsmp.model.xsmp.XsmpFactory
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

import static org.junit.jupiter.api.Assertions.*

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatInjectorProvider)
class DocumentationTest {

    def Documentation createDocumentation(CharSequence value) {
        var cat = XsmpFactory.eINSTANCE.createMetadatum()

        cat.documentation = value.toString
        return cat.xsmpcatdoc
    }

    @Test
    def void checkEmptyDocumentation() {

        assertTrue('''/***/'''.createDocumentation.tags.empty)
        assertTrue('''/*********/'''.createDocumentation.tags.empty)

        assertTrue('''/** */'''.createDocumentation.tags.empty)
        assertTrue('''/** ****/'''.createDocumentation.tags.empty)
        assertTrue('''
            /** 
             *     
                   *     
                   *  
                   */
        '''.createDocumentation.tags.empty)
    }

    @Test
    def void checkDocummentationWithDescription() {

        for (input : #{
            '''/**a*/''',
            '''/**a****/''',
            '''/**    a      */''',
            '''/** 
                  *  
                   *    a
                   *
                   *   */''',
            '''/** 
                  *  
                   *    a    
                   *
                   *   */'''
        }) {
            val doc = input.createDocumentation;
            assertEquals(1, doc.tags.size)
            val tag = doc.tags.get(0)
            assertEquals(1, tag.fragments.size)
            assertNull(tag.getTagName(doc))
            assertEquals("a", tag.fragments.get(0).getText(doc))
        }

        for (input : #{
            '''/**a b*/''',
            '''/**a b****/''',
            '''/**    a b   */''',
            '''/** 
                  *  
                   *    a b
                   
                   *   */'''
        }) {
            val doc = input.createDocumentation;
            assertEquals(1, doc.tags.size)
            val tag = doc.tags.get(0)
            assertEquals(1, tag.fragments.size)
            assertNull(tag.getTagName(doc))
            assertEquals("a b", tag.fragments.get(0).getText(doc))
        }

        for (input : #{
            '''/**a 
            b*/''',
            '''/**a 
            b****/''',
            '''/**    a 
               b   */''',
            '''/** 
                  *  
                   *    a
                    *b
                   
                   *   */'''
        }) {
            val doc = input.createDocumentation;
            assertEquals(1, doc.tags.size)
            val tag = doc.tags.get(0)
            assertEquals(2, tag.fragments.size)
            assertNull(tag.getTagName(doc))
            assertEquals("a", tag.fragments.get(0).getText(doc))
            assertEquals("b", tag.fragments.get(1).getText(doc))
        }
    }

    @Test
    def void checkDocummentationWithTag() {

        for (input : #{
            '''/**@*/''',
            '''/**@****/''',
            '''/**    @      */''',
            '''/** 
                  *  
                   *    @
                   *
                   *   */''',
            '''/** 
                  *  
                   *    @    
                   *
                   *   */'''
        }) {
            val doc = input.createDocumentation;
            assertEquals(1, doc.tags.size)
            val tag = doc.tags.get(0)
            assertTrue(tag.fragments.empty)
            assertEquals("@", tag.getTagName(doc))
        }

        for (input : #{
            '''/**@a*/''',
            '''/**@a****/''',
            '''/**    @a   */''',
            '''/** 
                  *  
                   *    @a 
                   
                   *   */'''
        }) {
            val doc = input.createDocumentation;
            assertEquals(1, doc.tags.size)
            val tag = doc.tags.get(0)
            assertTrue(tag.fragments.empty)
            assertEquals("@a", tag.getTagName(doc))
        }

        for (input : #{
            '''/**@a b*/''',
            '''/**@a b****/''',
            '''/**    @a  b */''',
            '''/** 
                  *  
                   *    @a    b
                   
                   *   */'''
        }) {
            val doc = input.createDocumentation;
            assertEquals(1, doc.tags.size)
            val tag = doc.tags.get(0)
            assertEquals(1, tag.fragments.size)
            assertEquals("@a", tag.getTagName(doc))
            assertEquals("b", tag.fragments.get(0).getText(doc))
        }

    }

    @Test
    def void checkDocummentation() {

        for (input : #{
            '''/**desc1
                  *      desc2    
                  desc3
        *          @tag1    
                  @tag2   tdesc1
                  tdesc2
            
            */'''
        }) {
            val doc = input.createDocumentation;
            assertEquals(3, doc.tags.size)
            val tag = doc.tags.get(0)
            assertNull(tag.getTagName(doc))
            assertEquals(3, tag.fragments.size)
            assertEquals("desc1", tag.fragments.get(0).getText(doc))
            assertEquals("desc2", tag.fragments.get(1).getText(doc))
            assertEquals("desc3", tag.fragments.get(2).getText(doc))

            val tag1 = doc.tags.get(1)
            assertEquals(0, tag1.fragments.size)
            assertEquals("@tag1", tag1.getTagName(doc))
            // assertEquals("tdesc1", tag1.fragments.get(0).getText(doc))
            // assertEquals("tdesc2", tag1.fragments.get(1).getText(doc))
            val tag2 = doc.tags.get(2)
            assertEquals(2, tag2.fragments.size)
            assertEquals("@tag2", tag2.getTagName(doc))
            assertEquals("tdesc1", tag2.fragments.get(0).getText(doc))
            assertEquals("tdesc2", tag2.fragments.get(1).getText(doc))
        }

    }
}
