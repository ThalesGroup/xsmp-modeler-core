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
import org.eclipse.xsmp.xcatalogue.Association
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers

class AssociationGenerator extends AbstractMemberGenerator<Association> {

	override declare(NamedElementWithMembers type, Association element) {
	}

	override define(NamedElementWithMembers type, Association element) {
	}

	override declareGen(NamedElementWithMembers type, Association element, boolean useGenPattern) {
		'''    
			«element.comment»
			«IF element.isConst»const «ENDIF»«IF element.isStatic»static «ENDIF»«IF element.isMutable»mutable «ENDIF»::«element.type.fqn.toString("::")»«IF element.isByPointer»*«ENDIF» «element.name»;
		'''
	}

	override defineGen(NamedElementWithMembers type, Association element, boolean useGenPattern) {
	}

	override collectIncludes(Association element, IncludeAcceptor acceptor) {
		super.collectIncludes(element, acceptor)
		acceptor.include(element.type)
	}

	override initialize(NamedElementWithMembers container, Association member, boolean useGenPattern) {
	}

	override finalize(Association element) {
	}

	override Publish(Association element) {
	}
}
