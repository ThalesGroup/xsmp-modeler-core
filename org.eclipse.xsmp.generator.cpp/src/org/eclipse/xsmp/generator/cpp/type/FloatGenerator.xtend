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
import org.eclipse.xsmp.xcatalogue.Float

class FloatGenerator extends AbstractTypeFileGenerator<Float> {

    override protected generateHeaderBody(Float t) {
        '''
            «t.comment()»
            using «t.name» = «t.genName»;
        '''
    }

    override protected generateHeaderGenBody(Float t, boolean useGenPattern) {
        '''
            «t.uuidDeclaration»
            «t.comment»
            using «t.name(useGenPattern)» = «IF t.primitiveType !== null»::«t.primitiveType.fqn.toString("::")»«ELSE»::Smp::Float64«ENDIF»;
            void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry);
        '''
    }

    override protected generateSourceGenBody(Float t, boolean useGenPattern) {
        '''
            void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry) {
                registry->AddFloatType(
                    "«t.name»", //Name
                    «t.description()», //description
                    «t.uuidQfn», //UUID
                «IF t.minimum !== null»«t.minimum.doGenerateExpression()»«ELSE»std::numeric_limits<«IF t.primitiveType !== null»::«t.primitiveType.fqn.toString("::")»«ELSE»::Smp::Float64«ENDIF»>::min()«ENDIF», //minimum
                «IF t.maximum !== null»«t.maximum.doGenerateExpression()»«ELSE»std::numeric_limits<«IF t.primitiveType !== null»::«t.primitiveType.fqn.toString("::")»«ELSE»::Smp::Float64«ENDIF»>::max()«ENDIF», //maximum
                «t.minInclusive»,// Minimm inclusive
                «t.maxInclusive»,// Maximim inclusive
                "«t.unit»", //unit
                «t.generatePrimitiveKind»);  
            }
            «t.uuidDefinition»
        '''
    }

    override collectIncludes(Float type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)

        if (type.minimum === null || type.maximum === null)
            acceptor.systemHeader("limits")

        type.minimum?.include(acceptor)
        type.maximum?.include(acceptor)
    }

    override protected collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.mdkHeader("Smp/PrimitiveTypes.h")
    }

}
