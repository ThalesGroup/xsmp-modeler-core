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
import org.eclipse.xsmp.xcatalogue.Integer

class IntegerGenerator extends AbstractTypeFileGenerator<Integer> {

    override protected generateHeaderBody(Integer t) {
        '''
            «t.comment()»
            using «t.name» = «t.genName»;
        '''
    }

    override protected generateHeaderGenBody(Integer t, boolean useGenPattern) {
        '''
            «t.uuidDeclaration»
            «t.comment»
            using «t.name(useGenPattern)» = «IF t.primitiveType !== null»::«t.primitiveType.fqn.toString("::")»«ELSE»::Smp::Int32«ENDIF»;
            void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry);
        '''
    }

    override protected generateSourceGenBody(Integer t, boolean useGenPattern) {
        '''
            void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry) {
                registry->AddIntegerType(
                    "«t.name»", //Name
                    «t.description()», //description
                    «t.uuidQfn», //UUID
                «IF t.minimum !== null»«t.minimum.doGenerateExpression(t, t)»«ELSE»std::numeric_limits<«IF t.primitiveType !== null»::«t.primitiveType.fqn.toString("::")»«ELSE»::Smp::Int32«ENDIF»>::min()«ENDIF», //minimum
                «IF t.maximum !== null»«t.maximum.doGenerateExpression(t, t)»«ELSE»std::numeric_limits<«IF t.primitiveType !== null»::«t.primitiveType.fqn.toString("::")»«ELSE»::Smp::Int32«ENDIF»>::max()«ENDIF», //maximum
                "«t.unit»", //unit
                «t.generatePrimitiveKind»);  
            }
            «t.uuidDefinition»
        '''
    }

    override collectIncludes(Integer type, IncludeAcceptor acceptor) {
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
