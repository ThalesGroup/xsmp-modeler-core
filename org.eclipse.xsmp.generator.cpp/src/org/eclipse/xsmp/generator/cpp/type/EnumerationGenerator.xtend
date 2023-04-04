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
import org.eclipse.xsmp.util.Solver
import org.eclipse.xsmp.xcatalogue.Enumeration

class EnumerationGenerator extends AbstractTypeFileGenerator<Enumeration> {

    override protected generateHeaderBody(Enumeration t) {
        '''
            «t.comment()»
            using «t.name» = «t.genName»;
        '''
    }

    override protected generateSourceBody(Enumeration type) {
    }

    override protected generateHeaderGenBody(Enumeration t, boolean useGenPattern) {
        '''
            «t.comment»
            enum class «t.name(useGenPattern)» : ::Smp::Int32 {
                «FOR l : t.literal SEPARATOR ", "»
                    «l.comment»
                    «l.name» = «l.value.doGenerateExpression(t, t)»
                «ENDFOR»
            };
            
            «t.uuidDeclaration»
            
            void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry);
            
            const std::map<«t.name», std::string> «t.name»_name_map = {
                «FOR l : t.literal SEPARATOR ", "»{ «t.name(useGenPattern)»::«l.name», "«l.name»" }«ENDFOR»
            };
            
            const std::map<«t.name», std::string> «t.name»_descr_map = {
                «FOR l : t.literal SEPARATOR ", "»{ «t.name(useGenPattern)»::«l.name», «l.description()» }«ENDFOR»
            };
        '''
    }

    override protected generateSourceGenBody(Enumeration t, boolean useGenPattern) {
        '''
            void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry) {
                ::Smp::Publication::IEnumerationType* typeState = registry->AddEnumerationType(
                    "«t.name»",  // name
                    «t.description()»,   // description
                    «t.uuidQfn»,  // UUID
                    sizeof(«t.name»));
            
                // Register the Literals of the Enumeration
                «FOR l : t.literal»
                    typeState->AddLiteral("«l.name»", «l.description()», «Solver.INSTANCE.getInteger(l.value)»);
                «ENDFOR»
                }
        '''
    }

    override collectIncludes(Enumeration type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)
        type.literal.forEach[it.value.include(acceptor)]
    }

    override protected collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.mdkHeader("Smp/PrimitiveTypes.h")
        acceptor.systemHeader("map")
    }

}
