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
package org.eclipse.xsmp.generator.cpp.member

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.xcatalogue.EventSource
import org.eclipse.xsmp.xcatalogue.EventType
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.SimpleType

class EventSourceGenerator extends AbstractMemberGenerator<EventSource> {

    protected def getEventArgType(EventSource element) {
        val eventType = element.type as EventType
        return eventType.eventArgs as SimpleType
    }

    override declare(NamedElementWithMembers type, EventSource element) {
    }

    override define(NamedElementWithMembers type, EventSource element) {
    }

    override declareGen(NamedElementWithMembers type, EventSource element, boolean useGenPattern) {
        '''
            «element.comment»
            ::Smp::IEventSource* «element.name»;
        '''
    }

    override defineGen(NamedElementWithMembers type, EventSource element, boolean useGenPattern) {
    }

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.mdkHeader("Smp/IEventSource.h")
    }

    override initialize(NamedElementWithMembers container, EventSource member, boolean useGenPattern) {
    }

    override finalize(EventSource element) {
        '''  
            delete «element.name»;
            «element.name» = nullptr;
        '''
    }

    override Publish(EventSource element) {
    }
}
