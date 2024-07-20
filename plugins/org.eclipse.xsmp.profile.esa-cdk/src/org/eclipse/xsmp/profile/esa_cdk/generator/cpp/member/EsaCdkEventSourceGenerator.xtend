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
import org.eclipse.xsmp.generator.cpp.member.EventSourceGenerator
import org.eclipse.xsmp.model.xsmp.EventSource
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers

class EsaCdkEventSourceGenerator extends EventSourceGenerator {

    override collectIncludes(EventSource it, IncludeAcceptor acceptor) {
        if (eventType !== null)
            acceptor.userHeader("esa/ecss/smp/cdk/EventSourceArg.h")
        else
            acceptor.userHeader("esa/ecss/smp/cdk/EventSource.h")
    }

    override declareGen(NamedElementWithMembers parent, EventSource it, boolean useGenPattern) {
        val arg = eventType
        if (arg !== null)
            '''
                «comment»
                ::esa::ecss::smp::cdk::EventSourceArg<«arg.id»> *«name»;
            '''
        else
            '''
                «comment»
                ::esa::ecss::smp::cdk::EventSource *«name»;
            '''
    }

    override initialize(NamedElementWithMembers parent, EventSource it, boolean useGenPattern) {
        val arg = eventType
        if (arg !== null)
            '''
                // Event Source: «name»
                «name»{new ::esa::ecss::smp::cdk::EventSourceArg<«arg.id»>( "«name»", «description()», this, simulator)}
            '''
        else
            '''
                // Event Source: «name»
                «name»{new ::esa::ecss::smp::cdk::EventSource( "«name»", «description()», this, simulator)}
            '''

    }

    override construct(NamedElementWithMembers parent, EventSource it, boolean useGenPattern) {
        '''
            // Add event source «name»
            this->AddEventSource(«name»);
        '''
    }

}
