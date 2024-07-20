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

import org.eclipse.xsmp.generator.cpp.member.EventSourceGenerator
import org.eclipse.xsmp.model.xsmp.EventSource
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class XsmpSdkEventSourceGenerator extends EventSourceGenerator {

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.userHeader("Xsmp/EventSource.h")
    }

    override declareGen(NamedElementWithMembers parent, EventSource it, boolean useGenPattern) {
        val arg = eventType
        '''
            «comment»
            ::Xsmp::EventSource<«arg?.id»> «name»;
        '''
    }

    override initialize(NamedElementWithMembers parent, EventSource it, boolean useGenPattern) {
        val arg = eventType
        '''
            // Event Source: «name»
            «name» { "«name»", «description()», this«IF arg !==null», «arg.generatePrimitiveKind»«ENDIF» }
        '''
    }

    override finalize(EventSource it) {
    }
}
