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

import org.eclipse.xsmp.generator.cpp.IncludeAcceptor
import org.eclipse.xsmp.xcatalogue.Array
import org.eclipse.xsmp.xcatalogue.Class
import org.eclipse.xsmp.xcatalogue.CollectionLiteral
import org.eclipse.xsmp.xcatalogue.Expression
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.PrimitiveType
import org.eclipse.xsmp.xcatalogue.SimpleType
import org.eclipse.xsmp.xcatalogue.Structure
import org.eclipse.xsmp.xcatalogue.Type
import org.eclipse.xtext.naming.QualifiedName

class FieldGenerator extends AbstractMemberGenerator<Field> {

    override declareGen(NamedElementWithMembers type, Field element, boolean useGenPattern) {
        '''
            «element.comment»
            «IF element.isStatic»static «ENDIF»«IF element.isMutable»mutable «ENDIF»::«element.type.fqn.toString("::")» «element.name»;
        '''

    }

    override defineGen(NamedElementWithMembers type, Field element, boolean useGenPattern) {
        if (element.isStatic)
            '''
                // «element.name» initialization
                «element.type.fqn.toString("::")» «type.name(useGenPattern)»::«element.name»«IF element.^default !==null» «element.^default.generateExpression(element.type, type)»«ELSE»{}«ENDIF»;
            '''
    }

    override collectIncludes(Field element, IncludeAcceptor acceptor) {
        super.collectIncludes(element, acceptor)
        acceptor.include(element.type)
        element.^default?.include(acceptor)
    }

    protected def dispatch boolean isDirectListInitializationType(Type type) {
        true
    }

    protected def dispatch boolean isDirectListInitializationType(Structure type) {
        type.member.filter(Field).forall[it.static || it.^default === null]
    }

    protected def dispatch boolean isDirectListInitializationType(Array type) {
        type.itemType.isDirectListInitializationType
    }

    protected def dispatch boolean isDirectListInitializationType(Class type) {
        false
    }

    protected def boolean isDirectListInitialization(Field element) {
        element.^default === null || element.type.isDirectListInitializationType
    }

    override initialize(NamedElementWithMembers container, Field element, boolean useGenPattern) {
        if (!element.static && element.isDirectListInitialization)
            '''
                // «element.name» initialization
                «element.name» «IF element.^default !== null» «element.^default.generateExpression(element.type, container)»«ELSE»{}«ENDIF»
            '''
    }

    protected def dispatch CharSequence construct(QualifiedName name, Type type, Expression expression,
        NamedElement context) {
    }

    protected def dispatch CharSequence construct(QualifiedName name, SimpleType type, Expression expression,
        NamedElement context) {
        '''
            «name.toString» = «expression.generateExpression(type, context)»;
        '''
    }

    protected def dispatch CharSequence construct(QualifiedName name, Array type, CollectionLiteral expression,
        NamedElement context) {

        val last = name.lastSegment
        val base = name.skipLast(1)
        '''
            «FOR i : 0 ..< expression.elements.size»
                «construct(base.append(last+"["+i+"]"), type.itemType, expression.elements.get(i),context)»
            «ENDFOR»
        '''

    }

    protected def dispatch CharSequence construct(QualifiedName name, Structure type, CollectionLiteral expression,
        NamedElement context) {

        val fields = type.assignableFields

        '''
            «FOR i : 0 ..< expression.elements.size»
                «construct(name.append(fields.get(i).name), fields.get(i).type, expression.elements.get(i),context)»
            «ENDFOR»
        '''

    }

    override construct(NamedElementWithMembers container, Field element, boolean useGenPattern) {
        if (!element.static && !element.isDirectListInitialization)
            '''
                // «element.name» initialization
                «construct(QualifiedName.create(element.name), element.type, element.^default, container)»
            '''
    }

    override Publish(Field element) {
        if (element.type instanceof PrimitiveType)
            switch (element.type.primitiveType) {
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
