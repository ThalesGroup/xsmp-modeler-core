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
import org.eclipse.xsmp.xcatalogue.EventSink
import org.eclipse.xsmp.xcatalogue.EventType
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.SimpleType

class EventSinkGenerator extends AbstractMemberGenerator<EventSink> {

    protected def eventType(EventSink it) {
        return (type as EventType).eventArgs as SimpleType
    }
    
    protected def eventArgs(EventSink it) {
        val argType = eventType
        '''::Smp::IObject* sender«IF argType !== null», «argType.id» value«ENDIF»'''
    }

    override declare(NamedElementWithMembers parent, EventSink it) {
        '''
            virtual void _«name»(«eventArgs») override;
        '''
    }

    override define(NamedElementWithMembers parent, EventSink it, boolean useGenPattern) {
        '''
            void «parent.name»::_«name»(«eventArgs») {
                // TODO implement EventSink «name»
                «comment»
            }
        '''
    }

    override declareGen(NamedElementWithMembers parent, EventSink it, boolean useGenPattern) {
        '''
            «comment()»
            ::Smp::IEventSink* «name»;
            virtual void _«name»(«eventArgs»)«IF useGenPattern» = 0«ENDIF»;
        '''
    }


    override collectIncludes(EventSink it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)
        val eventArg = eventType
        if (eventArg !== null) {
            acceptor.include(eventArg)
        }
    }

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.userHeader("Smp/IEventSink.h")
    }

    override finalize(EventSink it) {
        '''  
            delete «name»;
            «name» = nullptr;
        '''
    }
}
