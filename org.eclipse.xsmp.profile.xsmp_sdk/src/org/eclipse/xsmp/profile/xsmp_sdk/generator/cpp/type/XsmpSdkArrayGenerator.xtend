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
package org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.type

import org.eclipse.xsmp.generator.cpp.type.ArrayGenerator
import org.eclipse.xsmp.xcatalogue.Array
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class XsmpSdkArrayGenerator extends ArrayGenerator {

    override protected generateHeaderGenBody(Array it, boolean useGenPattern) {
        '''
            «comment»
            using «name(useGenPattern)»= ::Xsmp::Array<«itemType.id», «size.doGenerateExpression()»«IF isSimpleArray», true«ENDIF»>;
            
            «uuidDeclaration»
            
            void _Register_«name»(::Smp::Publication::ITypeRegistry* registry);
        '''
    }

    override collectIncludes(Array it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)

        acceptor.userHeader("Xsmp/Array.h")
    }
}
