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
import org.eclipse.xsmp.model.xsmp.ValueReference

class ValueReferenceGenerator extends AbstractTypeGenerator<ValueReference> {

    override protected generateHeaderBody(ValueReference it) {
        '''
            «comment()»
            using «name» = «nameGen»;
        '''
    }

    override protected generateHeaderGenBody(ValueReference it, boolean useGenPattern) {
        '''
            «comment»
             using «name(useGenPattern)» = «type.id» *;
             «uuidDeclaration»
        '''
    }

    override protected generateSourceGenBody(ValueReference it, boolean useGenPattern) {
        '''
            «uuidDefinition»
        '''
    }

    override collectIncludes(ValueReference it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)
        acceptor.include(type)
    }

}
