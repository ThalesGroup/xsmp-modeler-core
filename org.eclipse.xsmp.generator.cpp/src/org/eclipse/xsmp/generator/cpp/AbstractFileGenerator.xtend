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

import com.google.inject.Inject
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.xsmp.util.ElementUtil
import org.eclipse.xsmp.xcatalogue.Catalogue
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.Namespace
import org.eclipse.xtext.EcoreUtil2

abstract class AbstractFileGenerator<T extends NamedElement> {

    @Inject
    protected CopyrightProvider copyright

    @Inject
    protected extension GeneratorExtension

    @Inject
    protected extension ExpressionGenerator

    @Inject
    protected extension ElementUtil

    @Inject
    protected IProtectionGuardProvider guardProvider;

    def protected CharSequence namespace(EObject object, CharSequence body) {
        val namespace = EcoreUtil2.getContainerOfType(object, Namespace)
        if (namespace !== null)
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

    def protected abstract CharSequence generateHeaderGenBody(T type, boolean useGenPattern)

    def protected abstract CharSequence generateHeaderBody(T type)

    def protected abstract CharSequence generateSourceGenBody(T type, boolean useGenPattern)

    def protected abstract CharSequence generateSourceBody(T type)

    def void collectIncludes(T type, IncludeAcceptor acceptor) {
        collectIncludes(acceptor)
    }

    def protected void collectIncludes(IncludeAcceptor acceptor) {
    }

    /** Generate a header in include-gen directory with or without gen pattern */
    def CharSequence generateHeaderGen(T type, boolean useGenPattern, IncludeAcceptor acceptor, Catalogue cat) {

        val fqn = type.fqn(useGenPattern);
        val body = type.generateHeaderGenBody(useGenPattern)
        if (body !== null)
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
        val fqn = type.fqn(false);
        val body = type.generateHeaderBody()
        if (body !== null)
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

        acceptor.includedTypes.remove(type)
        acceptor.includedTypes.forEach[includeList.add(it.include())]
        acceptor.mdkTypesHeader.forEach[it|includeList.add("#include <" + it + ">")]
        acceptor.systemTypesHeader.forEach[it|includeList.add("#include <" + it + ">")]

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
        '''
            /* Include the generated header file */
            #include <«type.fqn(true).toString("/")».h>
        '''
    }

    def CharSequence generateSourceGen(T type, boolean useGenPattern, IncludeAcceptor acceptor, Catalogue cat) {
        val body = type.generateSourceGenBody(useGenPattern)
        if (body !== null && body.length > 0)
            '''
                «copyright.getSourceText(type, useGenPattern, cat)»
                
                «type.generateSourceGenIncludes(useGenPattern,acceptor, cat)»
                
                «namespace(type, body)»
            '''
        else
            null
    }

    def CharSequence generateSource(T type, Catalogue cat) {
        val body = type.generateSourceBody
        if (body !== null)
            '''
                «copyright.getSourceText(type, cat)»
                
                «type.generateSourceIncludes»
                
                «namespace(type, body)»
            '''
    }

    def protected CharSequence generateSourceIncludes(T type) {
        '''
            #include <«type.fqn(false).toString("/")».h>
        '''
    }

    def protected CharSequence generateSourceGenIncludes(T type, boolean useGenPattern, IncludeAcceptor acceptor,
        Catalogue cat) {
        val Set<String> includeList = newHashSet

        acceptor.mdkTypesSource.forEach[it|includeList.add("#include <" + it + ">")]
        acceptor.systemTypesSource.forEach[it|includeList.add("#include <" + it + ">")]

        acceptor.forwardedTypes.forEach[includeList.add(it.include())]

        acceptor.sourceTypes.remove(type)
        acceptor.sourceTypes.forEach[includeList.add(it.include())]
        // if (useGenPattern)
        // includeList.add("#include <" + type.fqn(true).toString("/") + ".h>")
        // include the final file (possible to customize the generated class)
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
