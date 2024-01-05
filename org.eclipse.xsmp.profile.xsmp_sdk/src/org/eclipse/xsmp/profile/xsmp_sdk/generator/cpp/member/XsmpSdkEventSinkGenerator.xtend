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

import org.eclipse.xsmp.generator.cpp.member.EventSinkGenerator
import org.eclipse.xsmp.xcatalogue.EventSink
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class XsmpSdkEventSinkGenerator extends EventSinkGenerator {

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.userHeader("Xsmp/EventSink.h")
    }

    override declareGen(NamedElementWithMembers parent, EventSink it, boolean useGenPattern) {
        val arg = eventType
        '''
            «comment()»
            ::Xsmp::EventSink<«arg?.id»> «name»;
            virtual void _«name»(«eventArgs»)«IF useGenPattern» = 0«ENDIF»;
        '''
    }

    override initialize(NamedElementWithMembers parent, EventSink it, boolean useGenPattern) {
        val arg = eventType

        if (arg !== null)
            '''
                // Event Sink: «name»
                «name»{"«name»", «description()», this, std::bind(&«parent.name(useGenPattern)»::_«name», this, std::placeholders::_1, std::placeholders::_2), «arg.generatePrimitiveKind» }
            '''
        else
            '''
                // Event Sink: «name»
                «name»{"«name»", «description()», this, std::bind(&«parent.name(useGenPattern)»::_«name», this, std::placeholders::_1) }
            '''
    }

    override finalize(EventSink it) {
    }
}
