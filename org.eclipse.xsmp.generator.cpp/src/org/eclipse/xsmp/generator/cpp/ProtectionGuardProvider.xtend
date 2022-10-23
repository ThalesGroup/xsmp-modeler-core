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
package org.eclipse.xsmp.generator.cpp

import org.eclipse.xtext.naming.QualifiedName

class ProtectionGuardProvider implements IProtectionGuardProvider {
	package boolean usePragmaOnce = false

	override CharSequence getStartGuard(QualifiedName qfn) {
		if (usePragmaOnce) {
			return "#pragma once"
		}
		val guard = qfn.guard()
		'''
			#ifndef «guard»
			#define «guard»
		'''
	}

	def guard(QualifiedName qfn) {
		'''«qfn.toString("_").toUpperCase()»_H_'''
	}

	override CharSequence getEndGuard(QualifiedName qfn) {
		if (usePragmaOnce)
			return ""

		return '''#endif // «qfn.guard»'''
	}
}
