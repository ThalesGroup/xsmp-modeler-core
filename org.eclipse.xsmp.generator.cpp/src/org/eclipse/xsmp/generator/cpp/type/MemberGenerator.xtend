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
package org.eclipse.xsmp.generator.cpp.type

import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xsmp.generator.cpp.AbstractFileGenerator
import org.eclipse.xsmp.generator.cpp.GeneratorExtension
import org.eclipse.xsmp.generator.cpp.member.AbstractMemberGenerator
import org.eclipse.xsmp.generator.cpp.member.AssociationGenerator
import org.eclipse.xsmp.generator.cpp.member.ConstantGenerator
import org.eclipse.xsmp.generator.cpp.member.ContainerGenerator
import org.eclipse.xsmp.generator.cpp.member.EntryPointGenerator
import org.eclipse.xsmp.generator.cpp.member.EventSinkGenerator
import org.eclipse.xsmp.generator.cpp.member.EventSourceGenerator
import org.eclipse.xsmp.generator.cpp.member.FieldGenerator
import org.eclipse.xsmp.generator.cpp.member.OperationGenerator
import org.eclipse.xsmp.generator.cpp.member.PropertyGenerator
import org.eclipse.xsmp.generator.cpp.member.ReferenceGenerator
import org.eclipse.xsmp.util.ElementUtil
import org.eclipse.xsmp.xcatalogue.Association
import org.eclipse.xsmp.xcatalogue.Constant
import org.eclipse.xsmp.xcatalogue.Container
import org.eclipse.xsmp.xcatalogue.EntryPoint
import org.eclipse.xsmp.xcatalogue.EventSink
import org.eclipse.xsmp.xcatalogue.EventSource
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.Operation
import org.eclipse.xsmp.xcatalogue.Property
import org.eclipse.xsmp.xcatalogue.Reference
import org.eclipse.xsmp.xcatalogue.VisibilityKind
import org.eclipse.xsmp.xcatalogue.XcataloguePackage
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor

abstract class MemberGenerator<T extends NamedElementWithMembers> extends AbstractFileGenerator<T> {

	@Inject
	protected extension ElementUtil

	@Inject
	protected extension GeneratorExtension

	@Inject
	AssociationGenerator associationGenerator

	@Inject
	ConstantGenerator constantGenerator

	@Inject
	ContainerGenerator containerGenerator

	@Inject
	EntryPointGenerator entryPointGenerator

	@Inject
	EventSinkGenerator eventSinkGenerator

	@Inject
	EventSourceGenerator eventSourceGenerator

	@Inject
	FieldGenerator fieldGenerator

	@Inject
	OperationGenerator operationGenerator

	@Inject
	PropertyGenerator propertyGenerator

	@Inject
	ReferenceGenerator referenceGenerator

	override void collectIncludes(T type, IncludeAcceptor acceptor) {

		super.collectIncludes(type, acceptor)
		// all types must include the ITypeRegistry
		acceptor.mdkHeader("Smp/Publication/ITypeRegistry.h")
		
		type.member.forEach[it.collectMemberIncludes(acceptor)]
	}

	protected def declare(T type, Constant c) {
		constantGenerator.declare(type, c)
	}

	protected def declare(T type, Property c) {
		propertyGenerator.declare(type, c)
	}

	protected def declare(T type, Operation c) {
		operationGenerator.declare(type, c)
	}

	protected def declare(T type, EntryPoint c) {
		entryPointGenerator.declare(type, c)
	}

	protected def declare(T type, EventSink c) {
		eventSinkGenerator.declare(type, c)
	}

	protected def declare(T type, EventSource c) {
		eventSourceGenerator.declare(type, c)
	}

	protected def declare(T type, Field c) {
		fieldGenerator.declare(type, c)
	}

	protected def declare(T type, Association c) {
		associationGenerator.declare(type, c)
	}

	protected def declare(T type, Container c) {
		containerGenerator.declare(type, c)
	}

	protected def declare(T type, Reference c) {
		referenceGenerator.declare(type, c)
	}

	protected def declareGen(T type, Constant c, boolean useGenPattern) {
		constantGenerator.declareGen(type, c, useGenPattern)
	}

	protected def declareGen(T type, Property c, boolean useGenPattern) {
		propertyGenerator.declareGen(type, c, useGenPattern)
	}

	protected def declareGen(T type, Operation c, boolean useGenPattern) {
		operationGenerator.declareGen(type, c, useGenPattern)
	}

	protected def declareGen(T type, EntryPoint c, boolean useGenPattern) {
		entryPointGenerator.declareGen(type, c, useGenPattern)
	}

	protected def declareGen(T type, EventSink c, boolean useGenPattern) {
		eventSinkGenerator.declareGen(type, c, useGenPattern)
	}

	protected def declareGen(T type, EventSource c, boolean useGenPattern) {
		eventSourceGenerator.declareGen(type, c, useGenPattern)
	}

	protected def declareGen(T type, Field c, boolean useGenPattern) {
		fieldGenerator.declareGen(type, c, useGenPattern)
	}

	protected def declareGen(T type, Association c, boolean useGenPattern) {
		associationGenerator.declareGen(type, c, useGenPattern)
	}

	protected def declareGen(T type, Container c, boolean useGenPattern) {
		containerGenerator.declareGen(type, c, useGenPattern)
	}

	protected def declareGen(T type, Reference c, boolean useGenPattern) {
		referenceGenerator.declareGen(type, c, useGenPattern)
	}



	protected def initialize(T type, Constant c, boolean useGenPattern) {
		constantGenerator.initialize(type, c, useGenPattern)
	}

	protected def initialize(T type, Property c, boolean useGenPattern) {
		propertyGenerator.initialize(type, c, useGenPattern)
	}

	protected def initialize(T type, Operation c, boolean useGenPattern) {
		operationGenerator.initialize(type, c, useGenPattern)
	}

	protected def initialize(T type, EntryPoint c, boolean useGenPattern) {
		entryPointGenerator.initialize(type, c, useGenPattern)
	}

	protected def initialize(T type, EventSink c, boolean useGenPattern) {
		eventSinkGenerator.initialize(type, c, useGenPattern)
	}

	protected def initialize(T type, EventSource c, boolean useGenPattern) {
		eventSourceGenerator.initialize(type, c, useGenPattern)
	}

	protected def initialize(T type, Field c, boolean useGenPattern) {
		fieldGenerator.initialize(type, c, useGenPattern)
	}

	protected def initialize(T type, Association c, boolean useGenPattern) {
		associationGenerator.initialize(type, c, useGenPattern)
	}

	protected def initialize(T type, Container c, boolean useGenPattern) {
		containerGenerator.initialize(type, c, useGenPattern)
	}

	protected def initialize(T type, Reference c, boolean useGenPattern) {
		referenceGenerator.initialize(type, c, useGenPattern)
	}
	
	
	
	def declare(EObject o, VisibilityKind initialVisibility, CharSequence declaration, StringBuilder buffer) {
		val visibility = o.visibility()

		if (declaration !== null)
			buffer.append(
			'''
				«IF visibility !== initialVisibility»
					«visibility.literal»:
				«ENDIF»
				«declaration»
			''');

		return visibility
	}

	def CharSequence declareMembers(T container, VisibilityKind initialVisibility) {
		var buffer = new StringBuilder
		var current = initialVisibility
		for (c : container.sortedConstants)
			current = c.declare(current, container.declare(c), buffer)
		for (c : container.member.filter(Property))
			current = c.declare(current, container.declare(c), buffer)
		for (c : container.member.filter(Operation))
			current = c.declare(current, container.declare(c), buffer)
		for (c : container.member.filter(EntryPoint))
			current = c.declare(current, container.declare(c), buffer)
		for (c : container.member.filter(EventSink))
			current = c.declare(current, container.declare(c), buffer)
		for (c : container.member.filter(EventSource))
			current = c.declare(current, container.declare(c), buffer)
		for (c : container.member.filter(Field))
			current = c.declare(current, container.declare(c), buffer)
		for (c : container.member.filter(Association))
			current = c.declare(current, container.declare(c), buffer)
		for (c : container.member.filter(Container))
			current = c.declare(current, container.declare(c), buffer)
		for (c : container.member.filter(Reference))
			current = c.declare(current, container.declare(c), buffer)

		return buffer
	}

	def CharSequence declareMembersGen(T container, boolean useGenPattern, VisibilityKind initialVisibility) {
		var buffer = new StringBuilder
		var current = initialVisibility
		for (c : container.sortedConstants)
			current = c.declare(current, container.declareGen(c, useGenPattern), buffer)
		for (c : container.member.filter(Property))
			current = c.declare(current, container.declareGen(c, useGenPattern), buffer)
		for (c : container.member.filter(Operation))
			current = c.declare(current, container.declareGen(c, useGenPattern), buffer)
		for (c : container.member.filter(EntryPoint))
			current = c.declare(current, container.declareGen(c, useGenPattern), buffer)
		for (c : container.member.filter(EventSink))
			current = c.declare(current, container.declareGen(c, useGenPattern), buffer)
		for (c : container.member.filter(EventSource))
			current = c.declare(current, container.declareGen(c, useGenPattern), buffer)
		for (c : container.member.filter(Field))
			current = c.declare(current, container.declareGen(c, useGenPattern), buffer)
		for (c : container.member.filter(Association))
			current = c.declare(current, container.declareGen(c, useGenPattern), buffer)
		for (c : container.member.filter(Container))
			current = c.declare(current, container.declareGen(c, useGenPattern), buffer)
		for (c : container.member.filter(Reference))
			current = c.declare(current, container.declareGen(c, useGenPattern), buffer)

		return buffer
	}

	def CharSequence defineMembers(T container) {
		'''
			«FOR m : container.member»
				«define(container, m)»
			«ENDFOR»
		'''
	}


	def CharSequence defineMembersGen(T container, boolean useGenPattern) {
		'''
			«FOR m : container.member»
				«defineGen(container,m, useGenPattern)»
			«ENDFOR»
		'''
	}

	protected def Iterable<CharSequence> initializerList(T container, boolean useGenPattern) {
		var List<CharSequence> list = newArrayList

		for (c : container.sortedConstants)
			list += container.initialize(c, useGenPattern)
		for (c : container.member.filter(Property))
			list += container.initialize(c, useGenPattern)
		for (c : container.member.filter(Operation))
			list += container.initialize(c, useGenPattern)
		for (c : container.member.filter(EntryPoint))
			list += container.initialize(c, useGenPattern)
		for (c : container.member.filter(EventSink))
			list += container.initialize(c, useGenPattern)
		for (c : container.member.filter(EventSource))
			list += container.initialize(c, useGenPattern)
		for (c : container.member.filter(Field))
			list += container.initialize(c, useGenPattern)
		for (c : container.member.filter(Association))
			list += container.initialize(c, useGenPattern)
		for (c : container.member.filter(Container))
			list += container.initialize(c, useGenPattern)
		for (c : container.member.filter(Reference))
			list += container.initialize(c, useGenPattern)

		return list.filterNull
	}

	@SuppressWarnings("rawtypes")
	protected def AbstractMemberGenerator getGenerator(EObject member) {
		switch (member.eClass().getClassifierID()) {
			case XcataloguePackage.ASSOCIATION:
				return associationGenerator
			case XcataloguePackage.CONSTANT:
				return constantGenerator
			case XcataloguePackage.CONTAINER:
				return containerGenerator
			case XcataloguePackage.ENTRY_POINT:
				return entryPointGenerator
			case XcataloguePackage.EVENT_SINK:
				return eventSinkGenerator
			case XcataloguePackage.EVENT_SOURCE:
				return eventSourceGenerator
			case XcataloguePackage.FIELD:
				return fieldGenerator
			case XcataloguePackage.OPERATION:
				return operationGenerator
			case XcataloguePackage.PROPERTY:
				return propertyGenerator
			case XcataloguePackage.REFERENCE:
				return referenceGenerator
			default:
				return null
		}
	}


	/* defineUser */
	def CharSequence define(T container, EObject member) {
		member.generator?.define(container, member)
	}


	/* define */
	def CharSequence defineGen(T container, EObject member, boolean useGenPattern) {
		member.generator?.defineGen(container, member, useGenPattern)
	}

	/* include */
	protected def void collectMemberIncludes(EObject member, IncludeAcceptor acceptor) {
		member.generator?.collectIncludes(member, acceptor)
	}

	/* initialize
	def CharSequence initialize(T container, EObject member, boolean useGenPattern) {
		member.generator?.initialize(container, member, useGenPattern)
	} */

	/* finalize */
	def CharSequence finalize(EObject member) {
		member.generator?.finalize(member)
	}

	/* Publish */
	def CharSequence Publish(EObject member) {
		member.generator?.Publish(member)
	}


}
