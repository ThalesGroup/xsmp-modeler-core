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
import org.eclipse.xsmp.generator.cpp.member.EventSinkGenerator
import org.eclipse.xsmp.xcatalogue.EventSink
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers

class EsaCdkEventSinkGenerator extends EventSinkGenerator {

    override collectIncludes(EventSink it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)

        if (eventType !== null)
            acceptor.userSource("esa/ecss/smp/cdk/EventSinkArg.h")
        else
            acceptor.userSource("esa/ecss/smp/cdk/EventSinkVoid.h")
    }

    override initialize(NamedElementWithMembers parent, EventSink it, boolean useGenPattern) {

        if (eventType !== null)
            '''
                // Event Sink: «name»
                «name»{new ::esa::ecss::smp::cdk::EventSinkArg<«eventType.id»>("«name»", «description()», this, simulator, &«parent.name(useGenPattern)»::_«name»)}
            '''
        else
            '''
                // Event Sink: «name»
                «name»{new ::esa::ecss::smp::cdk::EventSinkVoid("«name»", «description()», this, simulator, &«parent.name(useGenPattern)»::_«name»)}
            '''
    }

    override construct(NamedElementWithMembers parent, EventSink it, boolean useGenPattern) {
        '''
            // Add event sink «name»
            this->AddEventSink(«name»);
        '''
    }
}
