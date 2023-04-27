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
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
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

abstract class AbstractTypeWithMembersGenerator<T extends NamedElementWithMembers> extends AbstractFileGenerator<T> {

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

    protected def dispatch collectMemberIncludes(EObject c, IncludeAcceptor acceptor) {
    }

    protected def dispatch collectMemberIncludes(Constant c, IncludeAcceptor acceptor) {
        constantGenerator.collectIncludes(c, acceptor)
    }

    protected def dispatch collectMemberIncludes(Property c, IncludeAcceptor acceptor) {
        propertyGenerator.collectIncludes(c, acceptor)
    }

    protected def dispatch collectMemberIncludes(Operation c, IncludeAcceptor acceptor) {
        operationGenerator.collectIncludes(c, acceptor)
    }

    protected def dispatch collectMemberIncludes(EntryPoint c, IncludeAcceptor acceptor) {
        entryPointGenerator.collectIncludes(c, acceptor)
    }

    protected def dispatch collectMemberIncludes(EventSink c, IncludeAcceptor acceptor) {
        eventSinkGenerator.collectIncludes(c, acceptor)
    }

    protected def dispatch collectMemberIncludes(EventSource c, IncludeAcceptor acceptor) {
        eventSourceGenerator.collectIncludes(c, acceptor)
    }

    protected def dispatch collectMemberIncludes(Field c, IncludeAcceptor acceptor) {
        fieldGenerator.collectIncludes(c, acceptor)
    }

    protected def dispatch collectMemberIncludes(Association c, IncludeAcceptor acceptor) {
        associationGenerator.collectIncludes(c, acceptor)
    }

    protected def dispatch collectMemberIncludes(Container c, IncludeAcceptor acceptor) {
        containerGenerator.collectIncludes(c, acceptor)
    }

    protected def dispatch collectMemberIncludes(Reference c, IncludeAcceptor acceptor) {
        referenceGenerator.collectIncludes(c, acceptor)
    }

    /* declare */
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

    protected def dispatch construct(T type, EObject c, boolean useGenPattern) {
    }

    protected def dispatch construct(T type, Constant c, boolean useGenPattern) {
        constantGenerator.construct(type, c, useGenPattern)
    }

    protected def dispatch construct(T type, Property c, boolean useGenPattern) {
        propertyGenerator.construct(type, c, useGenPattern)
    }

    protected def dispatch construct(T type, Operation c, boolean useGenPattern) {
        operationGenerator.construct(type, c, useGenPattern)
    }

    protected def dispatch construct(T type, EntryPoint c, boolean useGenPattern) {
        entryPointGenerator.construct(type, c, useGenPattern)
    }

    protected def dispatch construct(T type, EventSink c, boolean useGenPattern) {
        eventSinkGenerator.construct(type, c, useGenPattern)
    }

    protected def dispatch construct(T type, EventSource c, boolean useGenPattern) {
        eventSourceGenerator.construct(type, c, useGenPattern)
    }

    protected def dispatch construct(T type, Field c, boolean useGenPattern) {
        fieldGenerator.construct(type, c, useGenPattern)
    }

    protected def dispatch construct(T type, Association c, boolean useGenPattern) {
        associationGenerator.construct(type, c, useGenPattern)
    }

    protected def dispatch construct(T type, Container c, boolean useGenPattern) {
        containerGenerator.construct(type, c, useGenPattern)
    }

    protected def dispatch construct(T type, Reference c, boolean useGenPattern) {
        referenceGenerator.construct(type, c, useGenPattern)
    }

    def declare(EObject o, VisibilityKind initialVisibility, CharSequence declaration, StringBuilder buffer) {
        var visibility = initialVisibility

        if (declaration !== null) {
            visibility = o.visibility
            buffer.append(
            '''
                «IF visibility !== initialVisibility»
                    «visibility.literal»:
                «ENDIF»
                «declaration»
            ''');
        }
        return visibility
    }

    /* requires gen patterm */
    protected def dispatch boolean memberRequiresGenPattern(EObject c) {
        false
    }

    protected def dispatch memberRequiresGenPattern(Constant c) {
        constantGenerator.requiresGenPattern(c)
    }

    protected def dispatch memberRequiresGenPattern(Property c) {
        propertyGenerator.requiresGenPattern(c)
    }

    protected def dispatch memberRequiresGenPattern(Operation c) {
        operationGenerator.requiresGenPattern(c)
    }

    protected def dispatch memberRequiresGenPattern(EntryPoint c) {
        entryPointGenerator.requiresGenPattern(c)
    }

    protected def dispatch memberRequiresGenPattern(EventSink c) {
        eventSinkGenerator.requiresGenPattern(c)
    }

    protected def dispatch memberRequiresGenPattern(EventSource c) {
        eventSourceGenerator.requiresGenPattern(c)
    }

    protected def dispatch memberRequiresGenPattern(Field c) {
        fieldGenerator.requiresGenPattern(c)
    }

    protected def dispatch memberRequiresGenPattern(Association c) {
        associationGenerator.requiresGenPattern(c)
    }

    protected def dispatch memberRequiresGenPattern(Container c) {
        containerGenerator.requiresGenPattern(c)
    }

    protected def dispatch memberRequiresGenPattern(Reference c) {
        referenceGenerator.requiresGenPattern(c)
    }

    override boolean requiresGenPattern(T container) {
        container.member.exists[memberRequiresGenPattern]
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

    def CharSequence defineMembers(T container, boolean useGenPattern) {
        '''
            «FOR m : container.member»
                «define(container, m, useGenPattern)»
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

    /* define */
    protected def dispatch define(T type, EObject c, boolean useGenPattern) {
    }

    protected def dispatch define(T type, Constant c, boolean useGenPattern) {
        constantGenerator.define(type, c, useGenPattern)
    }

    protected def dispatch define(T type, Property c, boolean useGenPattern) {
        propertyGenerator.define(type, c, useGenPattern)
    }

    protected def dispatch define(T type, Operation c, boolean useGenPattern) {
        operationGenerator.define(type, c, useGenPattern)
    }

    protected def dispatch define(T type, EntryPoint c, boolean useGenPattern) {
        entryPointGenerator.define(type, c, useGenPattern)
    }

    protected def dispatch define(T type, EventSink c, boolean useGenPattern) {
        eventSinkGenerator.define(type, c, useGenPattern)
    }

    protected def dispatch define(T type, EventSource c, boolean useGenPattern) {
        eventSourceGenerator.define(type, c, useGenPattern)
    }

    protected def dispatch define(T type, Field c, boolean useGenPattern) {
        fieldGenerator.define(type, c, useGenPattern)
    }

    protected def dispatch define(T type, Association c, boolean useGenPattern) {
        associationGenerator.define(type, c, useGenPattern)
    }

    protected def dispatch define(T type, Container c, boolean useGenPattern) {
        containerGenerator.define(type, c, useGenPattern)
    }

    protected def dispatch define(T type, Reference c, boolean useGenPattern) {
        referenceGenerator.define(type, c, useGenPattern)
    }

    /* define Gen*/
    protected def dispatch defineGen(T type, EObject c, boolean useGenPattern) {
    }

    protected def dispatch defineGen(T type, Constant c, boolean useGenPattern) {
        constantGenerator.defineGen(type, c, useGenPattern)
    }

    protected def dispatch defineGen(T type, Property c, boolean useGenPattern) {
        propertyGenerator.defineGen(type, c, useGenPattern)
    }

    protected def dispatch defineGen(T type, Operation c, boolean useGenPattern) {
        operationGenerator.defineGen(type, c, useGenPattern)
    }

    protected def dispatch defineGen(T type, EntryPoint c, boolean useGenPattern) {
        entryPointGenerator.defineGen(type, c, useGenPattern)
    }

    protected def dispatch defineGen(T type, EventSink c, boolean useGenPattern) {
        eventSinkGenerator.defineGen(type, c, useGenPattern)
    }

    protected def dispatch defineGen(T type, EventSource c, boolean useGenPattern) {
        eventSourceGenerator.defineGen(type, c, useGenPattern)
    }

    protected def dispatch defineGen(T type, Field c, boolean useGenPattern) {
        fieldGenerator.defineGen(type, c, useGenPattern)
    }

    protected def dispatch defineGen(T type, Association c, boolean useGenPattern) {
        associationGenerator.defineGen(type, c, useGenPattern)
    }

    protected def dispatch defineGen(T type, Container c, boolean useGenPattern) {
        containerGenerator.defineGen(type, c, useGenPattern)
    }

    protected def dispatch defineGen(T type, Reference c, boolean useGenPattern) {
        referenceGenerator.defineGen(type, c, useGenPattern)
    }

    /* finalize */
    protected def dispatch finalize(EObject c) {
    }

    protected def dispatch finalize(Constant c) {
        constantGenerator.finalize(c)
    }

    protected def dispatch finalize(Property c) {
        propertyGenerator.finalize(c)
    }

    protected def dispatch finalize(Operation c) {
        operationGenerator.finalize(c)
    }

    protected def dispatch finalize(EntryPoint c) {
        entryPointGenerator.finalize(c)
    }

    protected def dispatch finalize(EventSink c) {
        eventSinkGenerator.finalize(c)
    }

    protected def dispatch finalize(EventSource c) {
        eventSourceGenerator.finalize(c)
    }

    protected def dispatch finalize(Field c) {
        fieldGenerator.finalize(c)
    }

    protected def dispatch finalize(Association c) {
        associationGenerator.finalize(c)
    }

    protected def dispatch finalize(Container c) {
        containerGenerator.finalize(c)
    }

    protected def dispatch finalize(Reference c) {
        referenceGenerator.finalize(c)
    }

    /* Publish */
    protected def dispatch Publish(EObject c) {
    }

    protected def dispatch Publish(Constant c) {
        constantGenerator.Publish(c)
    }

    protected def dispatch Publish(Property c) {
        propertyGenerator.Publish(c)
    }

    protected def dispatch Publish(Operation c) {
        operationGenerator.Publish(c)
    }

    protected def dispatch Publish(EntryPoint c) {
        entryPointGenerator.Publish(c)
    }

    protected def dispatch Publish(EventSink c) {
        eventSinkGenerator.Publish(c)
    }

    protected def dispatch Publish(EventSource c) {
        eventSourceGenerator.Publish(c)
    }

    protected def dispatch Publish(Field c) {
        fieldGenerator.Publish(c)
    }

    protected def dispatch Publish(Association c) {
        associationGenerator.Publish(c)
    }

    protected def dispatch Publish(Container c) {
        containerGenerator.Publish(c)
    }

    protected def dispatch Publish(Reference c) {
        referenceGenerator.Publish(c)
    }

    def CharSequence generateStaticAsserts(T container) {
        '''
            «FOR m : container.member»
                «staticAssert(container,m)»
            «ENDFOR»
        '''
    }

    protected def dispatch staticAssert(T type, EObject c) {
    }

    protected def dispatch staticAssert(T type, Constant c) {
        constantGenerator.staticAssert(type, c)
    }

    protected def dispatch staticAssert(T type, Property c) {
        propertyGenerator.staticAssert(type, c)
    }

    protected def dispatch staticAssert(T type, Operation c) {
        operationGenerator.staticAssert(type, c)
    }

    protected def dispatch staticAssert(T type, EntryPoint c) {
        entryPointGenerator.staticAssert(type, c)
    }

    protected def dispatch staticAssert(T type, EventSink c) {
        eventSinkGenerator.staticAssert(type, c)
    }

    protected def dispatch staticAssert(T type, EventSource c) {
        eventSourceGenerator.staticAssert(type, c)
    }

    protected def dispatch staticAssert(T type, Field c) {
        fieldGenerator.staticAssert(type, c)
    }

    protected def dispatch staticAssert(T type, Association c) {
        associationGenerator.staticAssert(type, c)
    }

    protected def dispatch staticAssert(T type, Container c) {
        containerGenerator.staticAssert(type, c)
    }

    protected def dispatch staticAssert(T type, Reference c) {
        referenceGenerator.staticAssert(type, c)
    }

}
