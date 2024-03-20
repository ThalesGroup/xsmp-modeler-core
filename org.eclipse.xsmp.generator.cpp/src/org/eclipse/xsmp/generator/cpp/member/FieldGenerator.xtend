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
import org.eclipse.xsmp.model.xsmp.Array
import org.eclipse.xsmp.model.xsmp.CollectionLiteral
import org.eclipse.xsmp.model.xsmp.Expression
import org.eclipse.xsmp.model.xsmp.Field
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers
import org.eclipse.xsmp.model.xsmp.PrimitiveType
import org.eclipse.xsmp.model.xsmp.SimpleType
import org.eclipse.xsmp.model.xsmp.Structure
import org.eclipse.xtext.naming.QualifiedName

class FieldGenerator extends AbstractMemberGenerator<Field> {

    override declareGen(NamedElementWithMembers parent, Field it, boolean useGenPattern) {
        '''
            «comment»
            «IF isStatic»static «ENDIF»«IF isMutable»mutable «ENDIF»«type.id» «name»;
        '''
    }

    override defineGen(NamedElementWithMembers parent, Field it, boolean useGenPattern) {
        if (isStatic)
            '''
                // «name» initialization
                «type.id» «parent.name(useGenPattern)»::«name»«IF ^default !==null» «^default.generateExpression()»«ELSE»{}«ENDIF»;
            '''
    }

    override collectIncludes(Field it, IncludeAcceptor acceptor) {
        super.collectIncludes(it, acceptor)
        acceptor.include(type)
        ^default?.include(acceptor)
    }

    /** we can use direct initialization if the field is not static and does not have a default value or if the type is a Simple Type */
    protected def boolean isDirectListInitialization(Field it) {
        !isStatic && (^default === null || type instanceof SimpleType)
    }

    override initialize(NamedElementWithMembers parent, Field it, boolean useGenPattern) {
        if (isDirectListInitialization)
            '''
                // «name» initialization
                «name» «IF ^default !== null» «^default.generateExpression()»«ELSE»{}«ENDIF»
            '''
    }

    protected def CharSequence construct(QualifiedName name, Expression expression) {

        val type = expression.type

        val field = expression.field
        val fqn = field !== null ? name.append(field.name) : name

        if (type instanceof SimpleType)
            '''
                «fqn.toString» = «expression.generateExpression()»;
            '''
        else if (expression instanceof CollectionLiteral) {
            if (type instanceof Structure)
                '''
                    «FOR e : expression.elements»
                        «construct(fqn, e)»
                    «ENDFOR»
                '''
            else if (type instanceof Array) {
                val last = fqn.lastSegment
                val base = fqn.skipLast(1)
                '''
                    «FOR i : 0 ..< expression.elements.size»
                        «construct(base.append(last+"[" + i + "]"), expression.elements.get(i))»
                    «ENDFOR»
                '''

            }
        }
    }

    override construct(NamedElementWithMembers parent, Field it, boolean useGenPattern) {
        if (^default !== null && !isStatic && !(type instanceof SimpleType))
            '''
                // «name» initialization
                «construct(QualifiedName.EMPTY, ^default)»
            '''
    }

    override Publish(Field it) {
        if (type instanceof PrimitiveType)
            switch (type.primitiveTypeKind) {
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
                    // Publish directly with address of the field (not valid for DateTime and Duration that are identical to Int64)
                    return '''
                        // Publish field «name»
                        receiver->PublishField("«name»", «description()», &«name»,  «viewKind», «!transient», «input», «output»);
                    '''
                }
                case STRING8: {
                    return '''
                        // Do not Publish field «name» of type Smp::String8
                    '''
                }
                default: {
                }
            }
        // Generic Publish with type UUID
        '''
            // Publish field «name»
            receiver->PublishField("«name»", «description()», &«name», «type.uuid()», «viewKind», «!transient», «input», «output»);
        '''
    }
}
