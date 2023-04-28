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

    override protected generateHeaderBody(Array it) {
        '''
            «comment()»
            using «name» = «nameGen»;
        '''
    }


    override protected generateHeaderGenBody(Array it, boolean useGenPattern) {
        '''
            «comment»
            struct «name(useGenPattern)» 
            { 
                «itemType.id» internalArray[«size.doGenerateExpression()»];
            };
            
            «uuidDeclaration»
            
            void _Register_«name»(::Smp::Publication::ITypeRegistry* registry);
        '''
    }

    override protected generateSourceGenBody(Array it, boolean useGenPattern) {
        '''
            void _Register_«name»(::Smp::Publication::ITypeRegistry* registry) {
                    registry->AddArrayType(
                        "«name»", // Name
                        «description()», // Description
                        «uuid()», // UUID
                        «itemType.uuid()», // Item Type UUID
                        sizeof(«itemType.id»), // Item Type size
                        «size.doGenerateExpression()», // size of the array
                        «isSimpleArray» // is simple array
                    );
                }
                «uuidDefinition»
        '''
    }

    override collectIncludes(Array type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)
        acceptor.include(type.itemType)
        type.size.include(acceptor)
    }

    override protected collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.userHeader("Smp/PrimitiveTypes.h")
    }

}
