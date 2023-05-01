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
import org.eclipse.xsmp.xcatalogue.Enumeration

class EnumerationGenerator extends AbstractTypeGenerator<Enumeration> {

    override protected generateHeaderBody(Enumeration it) {
        '''
            «comment()»
            using «name» = «nameGen»;
        '''
    }

    override protected generateHeaderGenBody(Enumeration it, boolean useGenPattern) {
        '''
            «comment»
            enum class «name(useGenPattern)» : ::Smp::Int32 {
                «FOR l : literal SEPARATOR ", "»
                    «l.comment»
                    «l.name» = «l.value.doGenerateExpression()»
                «ENDFOR»
            };
            
            «uuidDeclaration»
            
            void _Register_«name»(::Smp::Publication::ITypeRegistry* registry);
            
            const std::map<«name(useGenPattern)», std::string> «name»_name_map = {
                «FOR l : literal SEPARATOR ", "»{ «name(useGenPattern)»::«l.name», "«l.name»" }«ENDFOR»
            };
            
            const std::map<«name(useGenPattern)», std::string> «name»_descr_map = {
                «FOR l : literal SEPARATOR ", "»{ «name(useGenPattern)»::«l.name», «l.description()» }«ENDFOR»
            };
        '''
    }

    override protected generateSourceGenBody(Enumeration it, boolean useGenPattern) {
        '''
            void _Register_«name»(::Smp::Publication::ITypeRegistry* registry) {
                ::Smp::Publication::IEnumerationType* typeState = registry->AddEnumerationType(
                    "«name»", // name
                    «description()», // description
                    «uuid()», // UUID
                    sizeof(«name») // Size
                    );
            
                // Register the Literals of the Enumeration
                «FOR l : literal»
                    typeState->AddLiteral("«l.name»", «l.description()», «l.value.getInt32()»);
                «ENDFOR»
                }
                «uuidDefinition»
        '''
    }

    override collectIncludes(Enumeration type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)
        type.literal.forEach[it.value.include(acceptor)]
    }

    override protected collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.userHeader("Smp/PrimitiveTypes.h")
        acceptor.systemHeader("map")
    }

}
