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
package org.eclipse.xsmp.profile.simsat.generator.cpp.member

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.generator.cpp.member.ContainerGenerator
import org.eclipse.xsmp.xcatalogue.Container
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers

class SimSatContainerGenerator extends ContainerGenerator {

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.mdkHeader("esa/ecss/smp/cdk/Container.h")
    }

    override declareGen(NamedElementWithMembers type, Container element, boolean useGenPattern) {
        '''
            «element.comment»
            ::esa::ecss::smp::cdk::Container<::«element.type.fqn.toString("::")»>* «element.name»;
        '''
    }

    override initialize(NamedElementWithMembers container, Container element, boolean useGenPattern) {

        '''
            // Container: «element.name»
            «element.name» { new ::esa::ecss::smp::cdk::Container<::«element.type.fqn.toString("::")»>("«element.name»", «element.description()», this, simulator, «element.generateLower», «element.generateUpper») }
        '''
    }

}
