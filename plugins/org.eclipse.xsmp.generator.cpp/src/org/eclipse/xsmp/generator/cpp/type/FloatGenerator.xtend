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
import org.eclipse.xsmp.model.xsmp.Float

class FloatGenerator extends AbstractTypeGenerator<Float> {

    override protected generateHeaderBody(Float it) {
        '''
            «comment()»
            using «name» = «nameGen»;
        '''
    }

    override protected generateHeaderGenBody(Float it, boolean useGenPattern) {
        '''
            «uuidDeclaration»
            «comment»
            using «name(useGenPattern)» = «IF primitiveType !== null»«primitiveType.id»«ELSE»::Smp::Float64«ENDIF»;
            void _Register_«name»(::Smp::Publication::ITypeRegistry* registry);
        '''
    }

    override protected generateSourceGenBody(Float it, boolean useGenPattern) {
        '''
            void _Register_«name»(::Smp::Publication::ITypeRegistry* registry) {
                registry->AddFloatType(
                    "«name»", //Name
                    «description()», //description
                    «uuid()», //UUID
                «IF minimum !== null»«minimum.doGenerateExpression()»«ELSE»std::numeric_limits<«IF primitiveType !== null»«primitiveType.id»«ELSE»::Smp::Float64«ENDIF»>::min()«ENDIF», // Minimum
                «IF maximum !== null»«maximum.doGenerateExpression()»«ELSE»std::numeric_limits<«IF primitiveType !== null»«primitiveType.id»«ELSE»::Smp::Float64«ENDIF»>::max()«ENDIF», // Maximum
                «minInclusive», // Minimm inclusive
                «maxInclusive», // Maximim inclusive
                "«unit»", // Unit
                «generatePrimitiveKind» // Primitive Type Kind
                );  
            }
            «uuidDefinition»
        '''
    }

    override collectIncludes(Float it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)

        if (minimum === null || maximum === null)
            acceptor.systemHeader("limits")

        minimum?.include(acceptor)
        maximum?.include(acceptor)
    }

    override protected collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.userHeader("Smp/PrimitiveTypes.h")
    }

}
