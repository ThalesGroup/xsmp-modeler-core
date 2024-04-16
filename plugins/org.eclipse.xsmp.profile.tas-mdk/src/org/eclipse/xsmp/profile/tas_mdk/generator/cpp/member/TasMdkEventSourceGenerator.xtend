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
package org.eclipse.xsmp.profile.tas_mdk.generator.cpp.member

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.generator.cpp.member.EventSourceGenerator
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.model.xsmp.EventSource

class TasMdkEventSourceGenerator extends EventSourceGenerator {

	override declareGen(NamedElementWithMembers type, EventSource element, boolean useGenPattern) {
		'''
			«element.comment»
			::TasMdk::EventSource *«element.name»;
		'''
	}
	
	override collectIncludes(EventSource element, IncludeAcceptor acceptor) {
		super.collectIncludes(element, acceptor)
		
		acceptor.userHeader("TasMdk/EventSource.h")
	}
		override initialize(NamedElementWithMembers container, EventSource element, boolean useGenPattern) {
		'''      
			// Event Source: «element.name»
			«element.name» { new ::TasMdk::EventSource(
			    "«element.name»",  // Name
			    «element.description()»,  // Description
			    this,
			    _event_sources)}
			    
		'''
	}
}
