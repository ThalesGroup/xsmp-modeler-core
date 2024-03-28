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
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(XsmpcatInjectorProvider)
class XsmpcatParsingTest {
    @Inject
    ParseHelper<Catalogue> catalogueParseHelper
    @Inject extension ValidationTestHelper

    @Test
    def void loadModel() {
        val result = catalogueParseHelper.parse(
    '''
            /**
             * Catalogue Test
             * 
             * @creator daveluy
             * @date 2021-09-30T06:32:34Z
             */
            catalogue Test
            
            namespace Smp
            {
                /**
                 * @uuid 77aa1773-da1e-4130-9997-64c859efe11a
                 */
                public primitive Duration
            
                /**
             * @uuid 77aa1773-da1e-4130-9997-64c859efe11b
             */
                public primitive DateTime
                
                /**
                 * @uuid 77aa1773-da1e-4130-9997-64c859efe110
                 */
                public primitive Bool
                
                
                /**
                 * @uuid 77aa1773-da1e-4130-9997-64c859efe111
                 */
                public primitive Int32
                
                /**
                 * @uuid 77aa1773-da1e-4130-9997-64c859efe112
                 */
                public primitive Int16
                
                
                /**
                 * @uuid 77aa1773-da1e-4130-9997-64c859efe113
                 */
                public primitive Float32
                
            }
            /**
             * Namespace test
             */
            namespace Test
            {
                /**
                 * Nested namespace
                 */
                namespace nestedNamespace
                {
                }
            
                /**
                 * @uuid ead293e5-4f21-4a0d-b74d-ccf416b67a4c
                 */
                enum Enum
                {
                    l1 = 0,
                    l2 = 2
                }
            
                /**
                 * @uuid f3de8138-0585-4c03-bac3-32393fec8078
                 */
                event event extends Bool
            
                /**
                 * @uuid e963ea4d-c8c8-4b73-be2d-5b2e3be7e1ab
                 */
                float aFloat extends Float32 in * ... 5
            
                /**
                 * @uuid ce772076-afe9-4f60-be20-78633b076741
                 */
                integer integer extends Int32 in 0 ... 1
                
                /**
                   * @uuid cd2f3dd1-bcc0-49aa-994d-bd823aedad4e
                   */
                string string [10]
                
                /**
                   * @uuid a66513f6-ff90-4cdd-b20d-ab0a445ec2bb
                   */
                array myArray = Bool[5]
                
            
                /**
                 * Class Type
                 * @uuid 77aa1773-da1e-4130-9997-64c859efe11d
                 */
                class Class
                {
                    association Bool association
                    constant Bool constant = true
                    def void operation (in Int16 in, inout Int16 inout, out Int16 out)
                    field Bool field
                    field Bool field_with_default_value = true
                    
                    constant Duration d = -1
                    constant DateTime dt = -1
                    property Bool property
                    readOnly property Bool r_property
                    readWrite property Bool rw_property
                    writeOnly property Bool w_property -> field
                }
            
                
            }
        ''')
        Assertions.assertNotNull(result)
        val errors = result.eResource.errors
        Assertions.assertTrue(errors.isEmpty, '''Unexpected errors: «errors.join(", ")»''')

        result.assertNoErrors
    }

}
