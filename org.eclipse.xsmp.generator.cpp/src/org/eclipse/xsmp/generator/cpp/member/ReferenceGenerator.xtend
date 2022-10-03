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

import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.Reference
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class ReferenceGenerator extends AbstractMemberGenerator<Reference> {

	override declare(NamedElementWithMembers type, Reference element) {
	}

	override define(NamedElementWithMembers type, Reference element) {
	}

	override declareGen(NamedElementWithMembers type, Reference element, boolean useGenPattern) {
		'''
			«element.comment»
			::Smp::IReference* «element.name»;
		'''
	}

	override defineGen(NamedElementWithMembers type, Reference element, boolean useGenPattern) {
	}

	override collectIncludes(Reference element, IncludeAcceptor acceptor) {
		super.collectIncludes(element, acceptor)
		acceptor.include(element.interface)

		element.multiplicity?.lower?.include(acceptor)
		element.multiplicity?.upper?.include(acceptor)
	}

	protected override collectIncludes(IncludeAcceptor acceptor) {
		acceptor.mdkHeader("Smp/IReference.h")
	}

	override initialize(NamedElementWithMembers container, Reference member, boolean useGenPattern) {
	}

	override finalize(Reference element) {
		'''  
			delete «element.name»;
			«element.name» = nullptr;
		'''
	}

	override Publish(Reference element) {
	}
}
