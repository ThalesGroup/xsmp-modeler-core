/*******************************************************************************
 * Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.member

import org.eclipse.xsmp.generator.cpp.member.EntryPointGenerator
import org.eclipse.xsmp.model.xsmp.EntryPoint
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class XsmpSdkEntryPointGenerator extends EntryPointGenerator {

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.userHeader("Xsmp/EntryPoint.h")
    }

    override initialize(NamedElementWithMembers parent, EntryPoint it, boolean useGenPattern) {
        '''
            // EntryPoint: «name»
            «name» { "«name»", «description()», this, std::bind(&«parent.name(useGenPattern)»::_«name», this) }
        '''
    }

    override declareGen(NamedElementWithMembers parent, EntryPoint it, boolean useGenPattern) {
        '''
            «comment»
            ::Xsmp::EntryPoint «name»; 
            virtual void _«name»()«IF useGenPattern» = 0«ENDIF»;
        '''
    }

    override finalize(EntryPoint it) {
    }
}
