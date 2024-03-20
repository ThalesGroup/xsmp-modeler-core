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
import org.eclipse.xsmp.generator.cpp.member.ContainerGenerator
import org.eclipse.xsmp.model.xsmp.Container
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers

class EsaCdkContainerGenerator extends ContainerGenerator {

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.userHeader("esa/ecss/smp/cdk/Container.h")
    }

    override declareGen(NamedElementWithMembers parent, Container it, boolean useGenPattern) {
        '''
            «comment»
            ::esa::ecss::smp::cdk::Container<«type.id»>* «name»;
        '''
    }

    override initialize(NamedElementWithMembers parent, Container it, boolean useGenPattern) {
        '''
            // Container: «name»
            «name» { new ::esa::ecss::smp::cdk::Container<«type.id»>("«name»", «description()», this, simulator, «lower()», «upper()») }
        '''
    }

    override construct(NamedElementWithMembers parent, Container it, boolean useGenPattern) {
        '''
            // Add container «name»
            this->AddContainer(«name»);
        '''
    }
}
