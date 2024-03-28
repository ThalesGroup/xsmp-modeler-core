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
import org.eclipse.xsmp.generator.cpp.member.ReferenceGenerator
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.model.xsmp.Reference

class EsaCdkReferenceGenerator extends ReferenceGenerator {

    override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.userHeader("esa/ecss/smp/cdk/Reference.h")
    }

    override declareGen(NamedElementWithMembers parent, Reference it, boolean useGenPattern) {
        '''
            «comment»
            ::esa::ecss::smp::cdk::Reference<«interface.id»>* «name»;
        '''
    }

    override initialize(NamedElementWithMembers parent, Reference it, boolean useGenPattern) {
        '''
            // Reference: «name»
            «name» { new ::esa::ecss::smp::cdk::Reference<«interface.id»>("«name»", «description()», this, simulator, «lower()», «upper()») }
        '''
    }

    override construct(NamedElementWithMembers parent, Reference it, boolean useGenPattern) {
        '''
            // Add reference «name»
            this->AddReference(«name»);
        '''
    }
    
}
