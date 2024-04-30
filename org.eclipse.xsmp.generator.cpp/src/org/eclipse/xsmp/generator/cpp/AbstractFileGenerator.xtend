/*******************************************************************************
 * Copyright (C) 2020-2023 THALES ALENIA SPACE FRANCE.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.xsmp.generator.cpp

import com.google.inject.Inject
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.xsmp.model.xsmp.Catalogue
import org.eclipse.xsmp.model.xsmp.NamedElement
import org.eclipse.xsmp.model.xsmp.Namespace
import org.eclipse.xtext.EcoreUtil2

abstract class AbstractFileGenerator<T extends NamedElement> {

    @Inject
    protected CppCopyrightNoticeProvider copyright

    @Inject
    protected extension GeneratorUtil

    @Inject
    IProtectionGuardProvider guardProvider;

    def protected CharSequence namespace(EObject object, CharSequence body) {
        val namespace = EcoreUtil2.getContainerOfType(object, Namespace)
        if (namespace !== null)
            if (cxxStandard >= CxxStandard.CXX_STD_17)
                '''
                    «namespace.comment()»
                    namespace «namespace.fqn.toString("::")»
                    {
                        «body»
                    } // namespace «namespace.fqn.toString("::")»
                '''
            else
                namespace.eContainer.namespace(
                '''
                    «namespace.comment()»
                    namespace «namespace.name»
                    {
                        «body»
                    } // namespace «namespace.name»
                ''')
        else
            body

    }

    def protected CharSequence generateHeaderGenBody(T type, boolean useGenPattern) {}

    def protected CharSequence generateHeaderBody(T type) {}

    def protected CharSequence generateSourceGenBody(T type, boolean useGenPattern) {}

    def protected CharSequence generateSourceBody(T type, boolean useGenPattern) {}

    def void collectIncludes(T type, IncludeAcceptor acceptor) {
        collectIncludes(acceptor)
    }

    def protected void collectIncludes(IncludeAcceptor acceptor) {
    }

    /** Generate a header in include-gen directory with or without gen pattern */
    def CharSequence generateHeaderGen(T type, boolean useGenPattern, IncludeAcceptor acceptor, Catalogue cat) {

        val fqn = type.fqn(useGenPattern);
        val body = type.generateHeaderGenBody(useGenPattern)
        if (body !== null && body.length > 0)
            '''
                «copyright.getHeaderText(type, useGenPattern, cat)»
                
                «guardProvider.getStartGuard(fqn)»
                
                «type.generateHeaderGenIncludes(useGenPattern, acceptor, cat)»
                
                // ----------------------------------------------------------------------------
                // ------------------------ Types and Interfaces ------------------------
                // ----------------------------------------------------------------------------
                
                «namespace(type, body)»
                
                «guardProvider.getEndGuard(fqn)»
            '''

    }

    /** generate a user header in include directory */
    def CharSequence generateHeader(T type, Catalogue cat) {
        val fqn = type.fqn;
        val body = type.generateHeaderBody()
        if (body !== null && body.length > 0)
            '''
                «copyright.getHeaderText(type, cat)»
                
                «guardProvider.getStartGuard(fqn)»
                
                «type.generateHeaderIncludes»
                
                // ----------------------------------------------------------------------------
                // ------------------------ Types and Interfaces ------------------------
                // ----------------------------------------------------------------------------
                
                «namespace(type, body)»
                
                «guardProvider.getEndGuard(fqn)»
            '''

    }

    def protected CharSequence generateHeaderGenIncludes(T type, boolean useGenPattern, IncludeAcceptor acceptor,
        Catalogue cat) {
        val Set<String> includeList = newHashSet

        // does not include ourself
        acceptor.includedTypes.remove(type)

        if (!useGenPattern)
            acceptor.forwardedTypes.remove(type)

        acceptor.includedTypes.forEach[includeList.add(it.include())]
        acceptor.userTypesHeader.forEach[it|includeList.add(it.include())]
        acceptor.systemTypesHeader.forEach[it|includeList.add(it.include())]

        '''
            // ----------------------------------------------------------------------------
            // ---------------------------- Include Header Files --------------------
            // ----------------------------------------------------------------------------
            
            «FOR include : includeList.filterNull.sort»
                «include»
            «ENDFOR»
            
            «FOR t : acceptor.forwardedTypes»
                «t.namespace('''class «t.name»;''')»
            «ENDFOR»
        '''

    }

    def protected CharSequence generateHeaderIncludes(T type) {
        val header = type.fqnGen.toString("/") + ".h"
        '''
            // Include the generated header file
            «header.include()»
        '''
    }

    def CharSequence generateSourceGen(T type, boolean useGenPattern, IncludeAcceptor acceptor, Catalogue cat) {
        val body = type.generateSourceGenBody(useGenPattern)
        if (body !== null && body.length > 0)
            '''
                «copyright.getSourceText(type, useGenPattern, cat)»
                
                «type.generateSourceIncludes(acceptor)»
                
                «namespace(type, body)»
            '''
        else
            null
    }

    def CharSequence generateSource(T type, boolean useGenPattern, Catalogue cat) {
        val body = type.generateSourceBody(useGenPattern)
        if (body !== null && body.length > 0)
            '''
                «copyright.getSourceText(type, cat)»
                
                «type.include»
                
                «namespace(type, body)»
            '''
    }

    def protected CharSequence generateSourceIncludes(T type, IncludeAcceptor acceptor) {
        val Set<String> includeList = newHashSet

        acceptor.userTypesSource.forEach[it|includeList.add(it.include())]
        acceptor.systemTypesSource.forEach[it|includeList.add(it.include())]

        acceptor.forwardedTypes.forEach[includeList.add(it.include())]

        acceptor.sourceTypes.remove(type)
        acceptor.sourceTypes.forEach[includeList.add(it.include())]

        includeList.add(type.include())

        '''
            // ----------------------------------------------------------------------------
            // ---------------------------- Include Header Files --------------------
            // ----------------------------------------------------------------------------
            
            «FOR include : includeList.filterNull.sort»
                «include»
            «ENDFOR»
        '''
    }

}
