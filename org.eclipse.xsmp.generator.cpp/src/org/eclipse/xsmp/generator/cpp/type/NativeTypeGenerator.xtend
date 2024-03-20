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
import org.eclipse.xsmp.model.xsmp.NativeType

class NativeTypeGenerator extends AbstractTypeGenerator<NativeType> {

    override protected generateHeaderBody(NativeType it) {
        '''
            «comment()»
            using «name» = «nameGen»;
        '''
    }

    override protected generateHeaderGenBody(NativeType it, boolean useGenPattern) {

        '''
            «comment»
             using «name(useGenPattern)» = ::«IF namespace !== null && !namespace.empty»«namespace»::«ENDIF»«type»;
             «uuidDeclaration»
        '''

    }

    override protected generateSourceGenBody(NativeType it, boolean useGenPattern) {
        '''
            «uuidDefinition»
        '''
    }

    override collectIncludes(NativeType it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)
        if (location !== null) {
            acceptor.systemHeader(location)
        }
    }

}
