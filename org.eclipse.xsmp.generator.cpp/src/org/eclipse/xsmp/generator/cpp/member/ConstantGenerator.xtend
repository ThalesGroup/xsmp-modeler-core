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
import org.eclipse.xsmp.xcatalogue.Constant
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers

class ConstantGenerator extends AbstractMemberGenerator<Constant> {

    override declareGen(NamedElementWithMembers parent, Constant element, boolean useGenPattern) {
        '''
            «element.comment»
            static constexpr ::«element.type.fqn.toString("::")» «element.name» «element.value.generateExpression()»;
        '''
    }

    override defineGen(NamedElementWithMembers parent, Constant element, boolean useGenPattern) {
        '''
            constexpr ::«element.type.fqn.toString("::")» «parent.name(useGenPattern)»::«element.name»;
        '''
    }

    override collectIncludes(Constant element, IncludeAcceptor acceptor) {
        super.collectIncludes(element, acceptor)
        acceptor.include(element.type)
        element.value.include(acceptor)
    }

}
