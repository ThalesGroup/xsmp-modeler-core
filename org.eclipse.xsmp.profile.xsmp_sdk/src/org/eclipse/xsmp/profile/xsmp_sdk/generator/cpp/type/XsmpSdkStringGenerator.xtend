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

import org.eclipse.xsmp.generator.cpp.type.StringGenerator
import org.eclipse.xsmp.xcatalogue.String
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class XsmpSdkStringGenerator extends StringGenerator {

    override protected generateHeaderGenBody(String t, boolean useGenPattern) {
        '''
            «t.comment»
            using «t.name(useGenPattern)» = ::Xsmp::String<«t.length.doGenerateExpression()»>;
            
            «t.uuidDeclaration»
            
            void _Register_«t.name»(::Smp::Publication::ITypeRegistry* registry);
        '''
    }

    override collectIncludes(String type, IncludeAcceptor acceptor) {
        super.collectIncludes(type, acceptor)

        acceptor.mdkHeader("Xsmp/String.h")
    }
}
