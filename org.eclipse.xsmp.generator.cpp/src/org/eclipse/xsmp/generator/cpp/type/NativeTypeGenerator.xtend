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
import org.eclipse.xsmp.xcatalogue.NativeType

class NativeTypeGenerator extends AbstractTypeFileGenerator<NativeType> {

    override protected generateHeaderBody(NativeType t) {
        '''
            «t.comment()»
            using «t.name» = «t.genName»;
        '''
    }

    override protected generateHeaderGenBody(NativeType type, boolean useGenPattern) {

        val platform = type.platform.findFirst["cpp" == it.name]
        if (platform !== null && platform.type !== null) {
            '''
                «type.comment»
                 using «type.name(useGenPattern)» = ::«IF platform.namespace !== null && !platform.namespace.empty»«platform.namespace»::«ENDIF»«platform.type»;
                 «type.uuidDeclaration»
            '''
        }

    }

    override protected generateSourceGenBody(NativeType type, boolean useGenPattern) {
        '''
            «type.uuidDefinition»
        '''
    }

    override collectIncludes(NativeType type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)
        val platform = type.platform.findFirst["cpp" == it.name]
        if (platform !== null && platform.location !== null) {
            acceptor.systemHeader(platform.location)
        }
    }

}
