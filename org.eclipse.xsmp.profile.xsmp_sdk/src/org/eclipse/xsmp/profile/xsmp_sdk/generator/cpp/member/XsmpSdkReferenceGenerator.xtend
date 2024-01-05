/*******************************************************************************
 * Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.member

import org.eclipse.xsmp.generator.cpp.member.ReferenceGenerator
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.Reference
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class XsmpSdkReferenceGenerator extends ReferenceGenerator {

    override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.userHeader("Xsmp/Reference.h")
    }

    override declareGen(NamedElementWithMembers parent, Reference it, boolean useGenPattern) {
        '''
            «comment»
            ::Xsmp::Reference<«interface.id»> «name»;
        '''
    }

    override initialize(NamedElementWithMembers parent, Reference it, boolean useGenPattern) {
        '''
            // Reference: «name»
            «name» { "«name»", «description()», this, «lower()», «upper()» }
        '''
    }

    override finalize(Reference it) {
    }
}
