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
import org.eclipse.xsmp.xcatalogue.Array

class ArrayGenerator extends AbstractTypeGenerator<Array> {

    override protected generateHeaderBody(Array t) {
        '''
            «t.comment()»
            using «t.name» = «t.genName»;
        '''
    }


    override protected generateHeaderGenBody(Array t, boolean useGenPattern) {
        '''
            «t.comment»
            struct «t.name(useGenPattern)» 
            { 
                ::«t.itemType.fqn.toString("::")» internalArray[«t.size.doGenerateExpression()»];
            };
            
            «t.uuidDeclaration»
            
            void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry);
        '''
    }

    override protected generateSourceGenBody(Array t, boolean useGenPattern) {
        '''
            void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry) {
                    registry->AddArrayType(
                        "«t.name»",  //Name
                        «t.description()»,   //description
                        «t.uuidQfn»,  //UUID
                        «t.itemType.uuidQfn»,
                        sizeof(::«t.itemType.fqn.toString("::")»),
                        «t.size.doGenerateExpression()», // size of the array
                        «t.isSimpleArray»);   // is simple array
                }
                «t.uuidDefinition»
        '''
    }

    override collectIncludes(Array type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)
        acceptor.include(type.itemType)
        type.size.include(acceptor)
    }

    override protected collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.mdkHeader("Smp/PrimitiveTypes.h")
    }

}
