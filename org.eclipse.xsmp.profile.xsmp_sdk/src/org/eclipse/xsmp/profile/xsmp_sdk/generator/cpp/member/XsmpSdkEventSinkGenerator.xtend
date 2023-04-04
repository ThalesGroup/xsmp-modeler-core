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
        acceptor.mdkSource("Xsmp/EventSink.h")
    }

    override initialize(NamedElementWithMembers container, EventSink element, boolean useGenPattern) {
        val arg = element.eventArgType
        '''
            // Event Sink: «element.name»
            «element.name»{new ::Xsmp::EventSink<«IF arg !== null»::«arg.fqn.toString("::")»«ENDIF»>("«element.name»", «element.description()»,  this, std::bind(&«container.name(useGenPattern)»::_«element.name», this, std::placeholders::_1«IF arg !==null», std::placeholders::_2«ENDIF»)«IF arg !==null», «arg.generatePrimitiveKind»«ENDIF»)}
        '''
    }

}
