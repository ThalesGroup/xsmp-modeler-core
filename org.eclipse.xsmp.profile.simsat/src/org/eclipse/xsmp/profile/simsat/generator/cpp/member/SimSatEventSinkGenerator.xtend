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
package org.eclipse.xsmp.profile.simsat.generator.cpp.member

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.generator.cpp.member.EventSinkGenerator
import org.eclipse.xsmp.xcatalogue.EventSink
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers

class SimSatEventSinkGenerator extends EventSinkGenerator {

    protected override collectIncludes(IncludeAcceptor acceptor) {
        super.collectIncludes(acceptor)
        acceptor.mdkSource("esa/ecss/smp/cdk/EventSink.h")
    }

    override initialize(NamedElementWithMembers container, EventSink element, boolean useGenPattern) {

        '''
            // Event Sink: «element.name»
            «element.name»{new ::esa::ecss::smp::cdk::EventSink("«element.name»", «element.description()», this, simulator)}
        '''
    }

}
