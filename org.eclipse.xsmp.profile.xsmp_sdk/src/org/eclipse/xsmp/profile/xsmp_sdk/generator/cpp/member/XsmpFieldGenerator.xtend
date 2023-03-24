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
package org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.member

import com.google.inject.Inject
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.generator.cpp.member.FieldGenerator
import org.eclipse.xsmp.profile.xsmp_sdk.generator.cpp.FieldHelper

class XsmpFieldGenerator extends FieldGenerator {

	@Inject extension FieldHelper

	override collectIncludes(Field element, IncludeAcceptor acceptor) {
		super.collectIncludes(element, acceptor)

		if (element.isMdkField)
			acceptor.mdkHeader("Xsmp/Field.h")
	}

	override Publish(Field element) {
		if (element.isMdkField)
			'''
				// Publish field «element.name»
				receiver->PublishField(&«element.name»);
			'''
		else
			super.Publish(element)
	}

	override initialize(NamedElementWithMembers container, Field f, boolean useGenPattern) {
		if (f.isMdkField)
			'''
				// «f.name» initialization
				«f.name»{this, «f.type.uuidQfn», "«f.name»", «f.description()», «f.viewKind»«IF f.^default !== null», «f.^default.generateExpression(f.type, container)»«ENDIF»}
			'''
		else
			super.initialize(container, f, useGenPattern)
	}

}
