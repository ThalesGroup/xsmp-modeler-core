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
package org.eclipse.xsmp.profile.esa_cdk.generator.cpp.member

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.generator.cpp.member.EntryPointGenerator
import org.eclipse.xsmp.xcatalogue.EntryPoint
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers

class EsaCdkEntryPointGenerator extends EntryPointGenerator {

    protected override collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.userSource("esa/ecss/smp/cdk/EntryPoint.h")
    }

    override initialize(NamedElementWithMembers parent, EntryPoint it, boolean useGenPattern) {
        '''
            // EntryPoint: «name»
            «name» { new ::esa::ecss::smp::cdk::EntryPoint( "«name»", «description()», this, simulator, &«parent.name(useGenPattern)»::_«name») }
        '''
    }

    override construct(NamedElementWithMembers parent, EntryPoint it, boolean useGenPattern) {
        '''
            if (!this->GetEntryPoint("«name»"))
            {
                // Use existing implementation to manage Entry Points
                this->AddEntryPoint(«name»);
            }
            else
            {
                Log(Smp::Services::ILogger::LMK_Error, "EntryPoint «name» redeclared");
            }
        '''
    }
}
