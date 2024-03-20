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
import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.generator.cpp.GeneratorUtil

abstract class AbstractMemberGenerator<T extends EObject> {

    @Inject
    protected extension GeneratorUtil

    /**
     * Declare a member it its parent (INCLUDE)
     */
    def CharSequence declare(NamedElementWithMembers parent, T member) {}

    /**
     * Define a member it its parent (SOURCE)
     */
    def CharSequence define(NamedElementWithMembers parent, T member, boolean useGenPattern) {}

    /**
     * Declare a member it its parent (INCLUDE-GEN)
     */
    def CharSequence declareGen(NamedElementWithMembers parent, T member, boolean useGenPattern) {}

    /**
     * Define a member it its parent (SOURCE-GEN)
     */
    def CharSequence defineGen(NamedElementWithMembers parent, T member, boolean useGenPattern) {}

    /**
     * Collect all includes required by this member
     */
    def void collectIncludes(T element, IncludeAcceptor acceptor) {
        collectIncludes(acceptor)
    }

    protected def void collectIncludes(IncludeAcceptor acceptor) {
    }

    /**
     * The direct list initialization of the member in the parent constructor initializer-list
     */
    def CharSequence initialize(NamedElementWithMembers parent, T member, boolean useGenPattern) {}

    /**
     * Construction of the member in the parent constructor body
     */
    def CharSequence construct(NamedElementWithMembers parent, T member, boolean useGenPattern) {}

    /**
     * The finalization of the member in the parent destructor
     */
    def CharSequence finalize(T element) {}

    def CharSequence Publish(T element) {}

}
