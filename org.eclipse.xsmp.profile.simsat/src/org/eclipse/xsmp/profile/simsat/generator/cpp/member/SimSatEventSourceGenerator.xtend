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

import org.eclipse.xsmp.xcatalogue.EventSource
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.generator.cpp.member.EventSourceGenerator
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class SimSatEventSourceGenerator extends EventSourceGenerator {

	 override collectIncludes(EventSource element, IncludeAcceptor acceptor) {
	    if (element.eventArgType !== null)
		acceptor.mdkHeader("esa/ecss/smp/cdk/EventSourceArg.h")
		else
		
        acceptor.mdkHeader("esa/ecss/smp/cdk/EventSource.h")
	}

	override declareGen(NamedElementWithMembers type, EventSource element, boolean useGenPattern) {
		val arg = element.eventArgType
		'''
			«element.comment»
			::esa::ecss::smp::cdk::EventSource«IF arg !== null»Arg<::«arg.fqn.toString("::")»>«ENDIF» *«element.name»;
		'''
	}

	override initialize(NamedElementWithMembers container, EventSource element, boolean useGenPattern) {
		val arg = element.eventArgType
		'''      
			// Event Source: «element.name»
			«element.name»{new ::esa::ecss::smp::cdk::EventSource«IF arg !== null»Arg<::«arg.fqn.toString("::")»>«ENDIF»( "«element.name»",  «element.description()», this, simulator)}
		'''
	}
}
