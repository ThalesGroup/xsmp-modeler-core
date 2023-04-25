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
import org.eclipse.xsmp.generator.cpp.member.ReferenceGenerator
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.Reference

class SimSatReferenceGenerator extends ReferenceGenerator {

    override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.mdkHeader("esa/ecss/smp/cdk/Reference.h")
    }

    override declareGen(NamedElementWithMembers type, Reference element, boolean useGenPattern) {
        '''
            «element.comment»
            ::esa::ecss::smp::cdk::Reference<::«element.interface.fqn.toString("::")»>* «element.name»;
        '''
    }

    override initialize(NamedElementWithMembers container, Reference element, boolean useGenPattern) {
        '''
            // Reference: «element.name»
            «element.name»{new ::esa::ecss::smp::cdk::Reference<::«element.interface.fqn.toString("::")»>("«element.name»", «element.description()», this, simulator, «element.generateLower», «element.generateUpper»)}
        '''
    }

    override construct(NamedElementWithMembers container, Reference element, boolean useGenPattern) {
        '''
            // Add reference «element.name»
            this->AddReference(«element.name»);
        '''
    }
    
}
