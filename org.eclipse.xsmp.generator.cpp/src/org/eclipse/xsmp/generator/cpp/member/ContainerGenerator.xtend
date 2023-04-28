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
import org.eclipse.xsmp.xcatalogue.Container
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers

class ContainerGenerator extends AbstractMemberGenerator<Container> {

    override declareGen(NamedElementWithMembers parent, Container it, boolean useGenPattern) {
        '''
            «comment»
            ::Smp::IContainer* «name»;
        '''
    }

    override collectIncludes(Container it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)
        acceptor.include(type)

        if (defaultComponent !== null)
            acceptor.include(defaultComponent)

        multiplicity?.lower?.include(acceptor)
        multiplicity?.upper?.include(acceptor)
    }

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.userHeader("Smp/IContainer.h")
    }

    override finalize(Container it) {
        '''  
            delete «name»;
            «name» = nullptr;
        '''
    }

}
