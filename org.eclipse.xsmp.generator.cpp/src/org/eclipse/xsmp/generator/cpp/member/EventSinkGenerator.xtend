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

    protected def getEventArgType(EventSink element) {
        val eventType = element.type as EventType
        return eventType.eventArgs as SimpleType
    }

    protected def args(EventSink element) {
        val argType = element.eventArgType
        '''::Smp::IObject* sender«IF argType !== null», ::«argType.fqn.toString("::")» value«ENDIF»'''
    }

    override declare(NamedElementWithMembers type, EventSink element) {
        '''
            virtual void _«element.name»(«element.args») override;
        '''
    }

    override define(NamedElementWithMembers type, EventSink element, boolean useGenPattern) {
        '''
            void «type.name»::_«element.name»(«element.args») {
            }
        '''
    }

    override declareGen(NamedElementWithMembers type, EventSink element, boolean useGenPattern) {
        '''
            «element.comment()»
            ::Smp::IEventSink* «element.name»;
            virtual void _«element.name»(«element.args»)«IF useGenPattern» = 0«ENDIF»;
        '''
    }

    override defineGen(NamedElementWithMembers type, EventSink element, boolean useGenPattern) {
        if (!useGenPattern)
            '''
                void «type.name(useGenPattern)»::_«element.name»(«element.args») {
                }
            '''
    }

    override collectIncludes(EventSink element, IncludeAcceptor acceptor) {
        super.collectIncludes(element, acceptor)
        val eventArg = element.eventArgType
        if (eventArg !== null) {
            acceptor.include(eventArg)
        }
    }

    protected override collectIncludes(IncludeAcceptor acceptor) {
        acceptor.mdkHeader("Smp/IEventSink.h")
    }

    override finalize(EventSink element) {
        '''  
            delete «element.name»;
            «element.name» = nullptr;
        '''
    }

}
