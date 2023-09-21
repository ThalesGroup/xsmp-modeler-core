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

import org.eclipse.xsmp.generator.cpp.member.EventSinkGenerator
import org.eclipse.xsmp.xcatalogue.EventSink
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class XsmpSdkEventSinkGenerator extends EventSinkGenerator {

    protected override collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.userSource("Xsmp/EventSink.h")
    }

    override initialize(NamedElementWithMembers parent, EventSink it, boolean useGenPattern) {
        val arg = eventType
        
        if (arg !==null)
        '''
            // Event Sink: «name»
            «name»{new ::Xsmp::EventSink<«arg.id»>("«name»", «description()», this, std::bind(&«parent.name(useGenPattern)»::_«name», this, std::placeholders::_1, std::placeholders::_2), «arg.generatePrimitiveKind»)}
        '''
        else
        '''
            // Event Sink: «name»
            «name»{new ::Xsmp::EventSink<>("«name»", «description()», this, std::bind(&«parent.name(useGenPattern)»::_«name», this, std::placeholders::_1))}
        '''
    }

}
