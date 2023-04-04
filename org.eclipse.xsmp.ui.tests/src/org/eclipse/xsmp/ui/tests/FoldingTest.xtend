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
package org.eclipse.xsmp.ui.tests

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.ui.testing.AbstractFoldingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(XsmpUiInjectorProvider)
class FoldingTest extends AbstractFoldingTest {

    @Test
    def complex() {
        '''
            catalogue c
            
            
            
            [>/**
             * a description
             */<]
            [>namespace ns1
            {
            [>    namespace ns2 
                {
            [>        model M1
                    {
                        field Bool field = 4
                    }<]
                }<]
            }<]
        '''.testFoldingRegions
    }

    @Test def multi_line_comment() {
        '''
            [>/*
               * multi line comment
               */<]
            catalogue c
        '''.testFoldingRegions
    }

    @Test def documentation() {
        '''
            [>/**
               * Documentation
               */<]
            catalogue c
        '''.testFoldingRegions
    }
}
