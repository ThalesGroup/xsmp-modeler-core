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

    protected def eventType(EventSource it) {
        return (type as EventType).eventArgs as SimpleType
    }

    override declareGen(NamedElementWithMembers parent, EventSource it, boolean useGenPattern) {
        '''
            «comment»
            ::Smp::IEventSource* «name»;
        '''
    }

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.userHeader("Smp/IEventSource.h")
    }

    override finalize(EventSource it) {
        '''  
            delete «name»;
            «name» = nullptr;
        '''
    }

}
