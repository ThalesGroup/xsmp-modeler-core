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

import org.eclipse.xsmp.xcatalogue.EntryPoint
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class EntryPointGenerator extends AbstractMemberGenerator<EntryPoint> {

	override declare(NamedElementWithMembers type, EntryPoint element) {
		'''
			virtual void _«element.name»() override;
		'''
	}

	override define(NamedElementWithMembers type, EntryPoint element) {
		'''
			void «type.name»::_«element.name»() {
			}
		'''
	}

	override declareGen(NamedElementWithMembers type, EntryPoint element, boolean useGenPattern) {
		'''
			«element.comment»
			::Smp::IEntryPoint* «element.name»; 
			virtual void _«element.name»()«IF useGenPattern» = 0«ENDIF»;
		'''
	}

	override defineGen(NamedElementWithMembers type, EntryPoint element, boolean useGenPattern) {
		if (!useGenPattern)
			'''
				void «type.name(useGenPattern)»::_«element.name»() {
				}
			'''
	}

	protected override collectIncludes(IncludeAcceptor acceptor) {
		acceptor.mdkHeader("Smp/IEntryPoint.h")
	}

	override initialize(NamedElementWithMembers container, EntryPoint member, boolean useGenPattern) {
	}

	override finalize(EntryPoint element) {
		'''  
			delete «element.name»;
			«element.name» = nullptr;
		'''
	}

	override Publish(EntryPoint element) {
		// Publish EntryPoint «element.name»
		// receiver->PublishOperation("«element.name»", «element.description()», «element.viewKind»);
	}

}
