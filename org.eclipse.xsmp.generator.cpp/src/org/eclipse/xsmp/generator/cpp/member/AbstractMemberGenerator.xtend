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
import org.eclipse.emf.ecore.EObject
import org.eclipse.xsmp.generator.cpp.ExpressionGenerator
import org.eclipse.xsmp.generator.cpp.GeneratorExtension
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.util.ElementUtil
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers

abstract class AbstractMemberGenerator<T extends EObject> {

	@Inject
	protected extension GeneratorExtension
	@Inject
	protected extension ExpressionGenerator
	@Inject
	protected extension ElementUtil

	/**
	 * Declare a member it its container (INCLUDE)
	 */
	def abstract CharSequence declare(NamedElementWithMembers container, T member)

	/**
	 * Define a member it its container (SOURCE)
	 */
	def abstract CharSequence define(NamedElementWithMembers container, T member)

	/**
	 * Declare a member it its container (INCLUDE-GEN)
	 */
	def abstract CharSequence declareGen(NamedElementWithMembers container, T member, boolean useGenPattern)

	/**
	 * Define a member it its container (SOURCE-GEN)
	 */
	def abstract CharSequence defineGen(NamedElementWithMembers container, T member, boolean useGenPattern)

	/**
	 * Collect all includes required by this member
	 */
	def void collectIncludes(T element, IncludeAcceptor acceptor) {
		collectIncludes(acceptor)
	}

	protected def void collectIncludes(IncludeAcceptor acceptor) {
	}

	def abstract CharSequence initialize(NamedElementWithMembers container, T member, boolean useGenPattern)

	def abstract CharSequence finalize(T element)

	def abstract CharSequence Publish(T element)
}
