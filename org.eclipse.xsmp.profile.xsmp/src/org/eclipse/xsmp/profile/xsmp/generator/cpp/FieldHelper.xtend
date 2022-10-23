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
package org.eclipse.xsmp.profile.xsmp.generator.cpp

import com.google.inject.Inject
import org.eclipse.xsmp.util.ElementUtil
import org.eclipse.xsmp.xcatalogue.Array
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.Structure
import org.eclipse.xsmp.xcatalogue.Type

class FieldHelper {

	@Inject
	extension ElementUtil

/**
 * Mdk field are output or failure or forcible
 */
	def boolean isMdkField(Field f) {
		return f.output || f.isFailure || f.isForcible || f.type.isMdkFieldType
	}

	private def dispatch boolean isMdkFieldType(Type t) {
		return false
	}

	private def dispatch boolean isMdkFieldType(Structure t) {
		return t.member.filter(Field).exists[it.isMdkField]
	}

	private def dispatch boolean isMdkFieldType(Array t) {
		return t.itemType.isMdkFieldType
	}
	
	
	def boolean isFailureField(Field f) {
		return f.isFailure || f.type.isFailureFieldType
	}

	private def dispatch boolean isFailureFieldType(Type t) {
		return false
	}

	private def dispatch boolean isFailureFieldType(Structure t) {
		return t.member.filter(Field).exists[it.isFailureField]
	}

	private def dispatch boolean isFailureFieldType(Array t) {
		return t.itemType.isFailureFieldType
	}

}
