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
package org.eclipse.xsmp.generator.cpp.member

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.model.xsmp.EntryPoint
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers

class EntryPointGenerator extends AbstractMemberGenerator<EntryPoint> {

    override declare(NamedElementWithMembers parent, EntryPoint it) {
        '''
            void _«name»() override;
        '''
    }

    override define(NamedElementWithMembers parent, EntryPoint it, boolean useGenPattern) {
        '''
            void «parent.name»::_«name»() {
                // TODO implement EntryPoint «name»
                «comment»
            }
        '''
    }

    override declareGen(NamedElementWithMembers parent, EntryPoint it, boolean useGenPattern) {
        '''
            «comment»
            ::Smp::IEntryPoint* «name»; 
            virtual void _«name»()«IF useGenPattern» = 0«ENDIF»;
        '''
    }

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.userHeader("Smp/IEntryPoint.h")
    }

    override finalize(EntryPoint it) {
        '''  
            delete «name»;
            «name» = nullptr;
        '''
    }

}
