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
package org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.member

import org.eclipse.xsmp.generator.cpp.member.ContainerGenerator
import org.eclipse.xsmp.xcatalogue.Container
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class XsmpSdkContainerGenerator extends ContainerGenerator {

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.mdkHeader("Xsmp/Container.h")
    }

    override declareGen(NamedElementWithMembers type, Container element, boolean useGenPattern) {
        '''
            «element.comment»
            ::Xsmp::Container<::«element.type.fqn.toString("::")»>* «element.name»;
        '''
    }

    override initialize(NamedElementWithMembers container, Container element, boolean useGenPattern) {

        '''
            // Container: «element.name»
            «element.name» { new ::Xsmp::Container<::«element.type.fqn.toString("::")»>("«element.name»", «element.description()», this, «element.generateLower», «element.generateUpper») }
        '''
    }

}
