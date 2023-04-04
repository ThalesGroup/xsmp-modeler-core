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

import org.eclipse.xsmp.generator.cpp.AbstractTypeFileGenerator
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.xcatalogue.String

class StringGenerator extends AbstractTypeFileGenerator<String> {

    override protected generateHeaderBody(String t) {
        '''
            «t.comment()»
            using «t.name» = «t.genName»;
        '''
    }

    override protected generateSourceBody(String type) {
    }

    override protected generateHeaderGenBody(String t, boolean useGenPattern) {
        '''
            «t.comment»
            struct «t.name(useGenPattern)» 
            { 
                ::Smp::Char8 internalString[«t.length.doGenerateExpression(t, t)» + 1];
            };
            
            «t.uuidDeclaration»
            
            void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry);
        '''
    }

    override protected generateSourceGenBody(String t, boolean useGenPattern) {
        '''
            void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry) {
                    registry->AddStringType(
                        "«t.name»",  //Name
                        «t.description()»,   //description
                        «t.uuidQfn»,  //UUID
                        «t.length.doGenerateExpression(t, t)»);  //length of the String
                }
        '''
    }

    override collectIncludes(String type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)
        type.length?.include(acceptor)
    }

    override protected collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.mdkHeader("Smp/PrimitiveTypes.h")
    }

}
