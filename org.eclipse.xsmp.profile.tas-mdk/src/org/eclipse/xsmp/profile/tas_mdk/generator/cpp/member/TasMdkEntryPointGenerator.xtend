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
import org.eclipse.xsmp.generator.cpp.member.EntryPointGenerator
import org.eclipse.xsmp.model.xsmp.EntryPoint
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers

class TasMdkEntryPointGenerator extends EntryPointGenerator {

	override collectIncludes(EntryPoint element, IncludeAcceptor acceptor) {
		super.collectIncludes(element, acceptor)
		
		acceptor.userSource("TasMdk/EntryPoint.h")
	}
		override initialize(NamedElementWithMembers container, EntryPoint element, boolean useGenPattern) {
		'''      
			// EntryPoint: «element.name»
			«element.name» {new ::TasMdk::EntryPoint(
			    "«element.name»",  // Name
			    «element.description()»,  // Description
			    this,
			    _entrypoints,
			    std::bind(&«container.name(useGenPattern)»::_«element.name», this))}
			    
		'''
	}
}
