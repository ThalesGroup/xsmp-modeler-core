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
package org.eclipse.xsmp.generator.cpp.type

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.xcatalogue.String

class StringGenerator extends AbstractTypeGenerator<String> {

    override protected generateHeaderBody(String it) {
        '''
            «comment()»
            using «name» = «nameGen»;
        '''
    }

    override protected generateHeaderGenBody(String it, boolean useGenPattern) {
        '''
            «comment»
            struct «name(useGenPattern)» 
            { 
                ::Smp::Char8 internalString[«length.doGenerateExpression()» + 1];
            };
            
            «uuidDeclaration»
            
            void _Register_«name»(::Smp::Publication::ITypeRegistry* registry);
        '''
    }

    override protected generateSourceGenBody(String it, boolean useGenPattern) {
        '''
            void _Register_«name»(::Smp::Publication::ITypeRegistry* registry) {
                    registry->AddStringType(
                        "«name»", //Name
                        «description()», // Description
                        «uuid()», // UUID
                        «length.doGenerateExpression()» // Length of the String
                        );
                }
                «uuidDefinition»
        '''
    }

    override collectIncludes(String it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)
        length?.include(acceptor)
    }

    override protected collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.userHeader("Smp/PrimitiveTypes.h")
    }

}
