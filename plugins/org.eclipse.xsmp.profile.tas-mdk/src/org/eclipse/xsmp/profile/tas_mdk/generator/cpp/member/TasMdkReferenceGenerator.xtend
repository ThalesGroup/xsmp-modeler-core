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
import org.eclipse.xsmp.generator.cpp.member.ReferenceGenerator
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.model.xsmp.Reference
import org.eclipse.xsmp.generator.cpp.GeneratorUtil
import com.google.inject.Inject

class TasMdkReferenceGenerator extends ReferenceGenerator {
    
    @Inject extension GeneratorUtil

	override declareGen(NamedElementWithMembers type, Reference element, boolean useGenPattern) {
		'''
			«element.comment»
			::TasMdk::Reference<«element.interface.id»>* «element.name»;
		'''
	}

	override initialize(NamedElementWithMembers container, Reference element, boolean useGenPattern) {
		'''      
			// Reference: «element.name»
			«element.name» {new ::TasMdk::Reference<«element.interface.id»>(
			    "«element.name»",
			    «element.description()»,
			    this,
			    _references,
			    «element.lower()»,
			    «element.upper()»)}
			    
		'''
	}

	override collectIncludes(Reference element, IncludeAcceptor acceptor) {
		super.collectIncludes(element, acceptor)
		acceptor.userHeader("TasMdk/Reference_tpl.h")
	}

}
