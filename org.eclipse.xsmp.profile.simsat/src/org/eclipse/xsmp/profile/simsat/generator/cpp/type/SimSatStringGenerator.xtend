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
package org.eclipse.xsmp.profile.simsat.generator.cpp.type

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.generator.cpp.type.StringGenerator
import org.eclipse.xsmp.xcatalogue.String

class SimSatStringGenerator extends StringGenerator {

    override protected generateHeaderGenBody(String it, boolean useGenPattern) {
        '''
            «comment»
            using «name(useGenPattern)» = ::esa::ecss::smp::cdk::String<«length.doGenerateExpression()»>;
            
            «uuidDeclaration»
            
            void _Register_«name»(::Smp::Publication::ITypeRegistry* registry);
        '''
    }

    override collectIncludes(String it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)

        acceptor.userHeader("esa/ecss/smp/cdk/String.h")
    }
}
