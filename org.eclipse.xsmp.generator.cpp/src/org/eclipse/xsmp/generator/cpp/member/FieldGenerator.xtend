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

import com.google.inject.Inject
import org.eclipse.xsmp.util.XsmpUtil
import org.eclipse.xsmp.xcatalogue.Component
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.PrimitiveType
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

class FieldGenerator extends AbstractMemberGenerator<Field> {

	@Inject
	XsmpUtil xsmpUtil

	override declare(NamedElementWithMembers type, Field element) {
	}

	override define(NamedElementWithMembers type, Field element) {
	}

	override declareGen(NamedElementWithMembers type, Field element, boolean useGenPattern) {

		if (element.isStatic)
			'''
				«element.comment»
				static «IF element.isMutable»mutable «ENDIF»::«element.type.fqn.toString("::")» «element.name»;
			'''
		else
			'''
				«element.comment»
				«IF element.isMutable»mutable «ENDIF»::«element.type.fqn.toString("::")» «element.name»«IF element.^default !==null» «element.^default.generateExpression(element.type, type)»«ELSEIF element.eContainer instanceof Component»{}«ENDIF»;
			'''
	}

	override defineGen(NamedElementWithMembers type, Field element, boolean useGenPattern) {
		if (element.isStatic)
			'''
				«element.type.fqn.toString("::")» «type.name(useGenPattern)»::«element.name»«IF element.^default !==null» «element.^default.generateExpression(element.type, type)»«ELSE»{}«ENDIF»;
			'''
	}

	override collectIncludes(Field element, IncludeAcceptor acceptor) {
		super.collectIncludes(element, acceptor)
		acceptor.include(element.type)
		element.^default?.include(acceptor)
	}

	override initialize(NamedElementWithMembers container, Field member, boolean useGenPattern) {
	}

	override finalize(Field element) {
	}

	override Publish(Field element) {
		if (element.type instanceof PrimitiveType)
			switch (xsmpUtil.getPrimitiveType(element.type as PrimitiveType)) {
				case BOOL,
				case CHAR8,
				case FLOAT32,
				case FLOAT64,
				case INT16,
				case INT32,
				case INT64,
				case INT8,
				case UINT16,
				case UINT32,
				case UINT64,
				case UINT8: {
					'''
						// Publish field «element.name»
						receiver->PublishField("«element.name»", «element.description()», &«element.name»,  «element.viewKind», «!element.transient», «element.input», «element.output»);
					'''
				}
				case STRING8: {
					'''
						// Publish field «element.name»
						/* There is no publishing call for String8 as it relies on dynamically allocated memory areas, hence cannot be published like the other primitive types. */
					'''
				}
				default: {
					'''
						// Publish field «element.name»
						receiver->PublishField("«element.name»", «element.description()», &«element.name», «element.type.uuidQfn», «element.viewKind», «!element.transient», «element.input», «element.output»);
					'''
				}
			}
		else
			'''
				// Publish field «element.name»
				receiver->PublishField("«element.name»", «element.description()», &«element.name», «element.type.uuidQfn», «element.viewKind», «!element.transient», «element.input», «element.output»);
			'''
	}
}
