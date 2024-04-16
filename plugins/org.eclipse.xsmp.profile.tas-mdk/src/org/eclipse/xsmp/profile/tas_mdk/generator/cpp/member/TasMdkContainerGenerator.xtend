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
import org.eclipse.xsmp.generator.cpp.member.ContainerGenerator
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.model.xsmp.Container
import org.eclipse.xsmp.generator.cpp.GeneratorUtil
import com.google.inject.Inject

class TasMdkContainerGenerator extends ContainerGenerator {
    
    @Inject extension GeneratorUtil

	override declareGen(NamedElementWithMembers type, Container element, boolean useGenPattern) {
		'''
			«element.comment»
			::TasMdk::Container<«element.type.id»>* «element.name»;
		'''
	}

	override collectIncludes(Container element, IncludeAcceptor acceptor) {
		super.collectIncludes(element, acceptor)

		acceptor.userHeader("TasMdk/Container.h")

	}

	override initialize(NamedElementWithMembers container, Container element, boolean useGenPattern) {
		'''      
			// Container: «element.name»
			«element.name» {new ::TasMdk::Container<«element.type.id»>(
			    "«element.name»",
			    «element.description()»,
			    this,
			    _containers,
			    «element.lower()»,
			    «element.upper()»)}
			    
		'''
	}

}
