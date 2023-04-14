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

    override declareGen(NamedElementWithMembers type, Container element, boolean useGenPattern) {
        '''
            «element.comment»
            ::Smp::IContainer* «element.name»;
        '''
    }

    override defineGen(NamedElementWithMembers type, Container element, boolean useGenPattern) {
    }

    override collectIncludes(Container element, IncludeAcceptor acceptor) {
        super.collectIncludes(element, acceptor)
        acceptor.include(element.type)

        if (element.defaultComponent !== null)
            acceptor.include(element.defaultComponent)

        element.multiplicity?.lower?.include(acceptor)
        element.multiplicity?.upper?.include(acceptor)
    }

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.mdkHeader("Smp/IContainer.h")
    }

    override finalize(Container element) {
        '''  
            delete «element.name»;
            «element.name» = nullptr;
        '''
    }

}
