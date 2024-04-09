/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.profile.esa_cdk_legacy.generator

import org.eclipse.xsmp.model.xsmp.NamedElement

class Generator {

	def CharSequence generateSource(NamedElement elem) {
		'''
			// Generator for «elem.eClass.name» «elem.name» not yet implemented
			
			// MARKER: MARKER 1: START
			// CODE HERE WILL NOT BE DELETED
			// MARKER: MARKER 1: END
			
			// MARKER: MARKER 2: START
			// CODE HERE WILL NOT BE DELETED
			// MARKER: MARKER 2: END
			
			// a duplicated MARKER identifier
			// MARKER: MARKER 1: START
			// CODE HERE WILL NOT BE DELETED
			// MARKER: MARKER 1: END
		'''
	}
	
	def CharSequence generateInclude(NamedElement elem) {
		'''
			// Generator for «elem.eClass.name» «elem.name» not yet implemented
			
			// MARKER: MARKER 1: START
			// CODE HERE WILL NOT BE DELETED
			// MARKER: MARKER 1: END
			
			// MARKER: MARKER 2: START
			// CODE HERE WILL NOT BE DELETED
			// MARKER: MARKER 2: END
			
			// a duplicated MARKER identifier
			// MARKER: MARKER 1: START
			// CODE HERE WILL NOT BE DELETED
			// MARKER: MARKER 1: END
		'''
	}
}
