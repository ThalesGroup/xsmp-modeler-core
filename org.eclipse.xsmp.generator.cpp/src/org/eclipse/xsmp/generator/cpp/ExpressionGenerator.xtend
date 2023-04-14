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
import com.google.inject.Singleton
import java.time.Duration
import java.time.Instant
import java.util.stream.Collectors
import org.eclipse.emf.ecore.EObject
import org.eclipse.xsmp.util.XsmpUtil
import org.eclipse.xsmp.xcatalogue.Array
import org.eclipse.xsmp.xcatalogue.BinaryOperation
import org.eclipse.xsmp.xcatalogue.BooleanLiteral
import org.eclipse.xsmp.xcatalogue.BuiltInConstant
import org.eclipse.xsmp.xcatalogue.BuiltInFunction
import org.eclipse.xsmp.xcatalogue.CharacterLiteral
import org.eclipse.xsmp.xcatalogue.CollectionLiteral
import org.eclipse.xsmp.xcatalogue.EnumerationLiteralReference
import org.eclipse.xsmp.xcatalogue.Expression
import org.eclipse.xsmp.xcatalogue.FloatingLiteral
import org.eclipse.xsmp.xcatalogue.IntegerLiteral
import org.eclipse.xsmp.xcatalogue.KeywordExpression
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.ParenthesizedExpression
import org.eclipse.xsmp.xcatalogue.StringLiteral
import org.eclipse.xsmp.xcatalogue.Structure
import org.eclipse.xsmp.xcatalogue.Type
import org.eclipse.xsmp.xcatalogue.UnaryOperation
import org.eclipse.xsmp.xcatalogue.ValueType
import org.eclipse.xsmp.xcatalogue.Operation
import org.eclipse.xsmp.xcatalogue.BuiltInExpression

@Singleton
class ExpressionGenerator {

    @Inject
    protected extension XsmpUtil

    def dispatch CharSequence doGenerateExpression(EnumerationLiteralReference e, Type expectedType,
        NamedElement context) {

        var fqn = e.value.fqn;

        var contextfqn = context.fqn

        if (fqn.startsWith(contextfqn) && contextfqn.getSegmentCount() + 1 == fqn.getSegmentCount()) {
            return '''«e.value.name»'''
        }
        return '''::«fqn.toString("::")»'''
    }

    def dispatch CharSequence doGenerateExpression(IntegerLiteral t, Type expectedType, NamedElement context) {
        '''«t.text.replace("'","")»'''
    }

    def dispatch CharSequence doGenerateExpression(FloatingLiteral t, Type expectedType, NamedElement context) {
        '''«t.text.replace("'","")»'''
    }

    def dispatch CharSequence doGenerateExpression(BuiltInFunction t, Type expectedType, NamedElement context) {
        '''«t.name»(«FOR p : t.parameter SEPARATOR ", "»«p.doGenerateExpression(null, context)»«ENDFOR»)'''
    }

    def dispatch CharSequence doGenerateExpression(BuiltInConstant t, Type expectedType, NamedElement context) {
        switch (t.name) {
            case "PI":
                "M_PI"
            case "E":
                "M_E"
        }
    }

    def dispatch CharSequence doGenerateExpression(BooleanLiteral t, Type expectedType, NamedElement context) {
        '''«t.isTrue»'''
    }

    def dispatch CharSequence doGenerateExpression(StringLiteral t, Type expectedType, NamedElement context) {
        switch (expectedType.primitiveType ) {
            // convert DateTime and Duration to a number of ns
            case DATE_TIME: {
                val i = Instant.parse(XsmpUtil.getString(t))
                '''«i.epochSecond * 1_000_000_000 + i.nano»UL'''
            }
            case DURATION: {
                val i = Duration.parse(XsmpUtil.getString(t))
                '''«i.seconds * 1_000_000_000 + i.nano»UL'''
            }
            default: {
                if (expectedType instanceof ValueType)
                    t.value
                else
                    XsmpUtil.getString(t)
            }
        }

    }

    def dispatch CharSequence doGenerateExpression(CharacterLiteral t, Type expectedType, NamedElement context) {
        t.value
    }

    def dispatch CharSequence doGenerateExpression(CollectionLiteral t, Type expectedType, NamedElement context) {
        if (expectedType instanceof Array) {
            if (expectedType.itemType instanceof Array)
                '''{«FOR l : t.elements SEPARATOR ', '»{«l.doGenerateExpression(expectedType.itemType, context)»}«ENDFOR»}'''
            else
                '''{«FOR l : t.elements SEPARATOR ', '»«l.doGenerateExpression(expectedType.itemType, context)»«ENDFOR»}'''
        } else if (expectedType instanceof Structure) {
            val fields = expectedType.assignableFields.collect(Collectors.toList)
            if (fields.size >= t.elements.size)
                '''{«FOR i : 0 ..< t.elements.size SEPARATOR ', '»«t.elements.get(i).generateStructMember(fields.get(i).type, context)»«ENDFOR»}'''
            else // TODO find constructor
                '''{«FOR l : t.elements SEPARATOR ', '»«l.doGenerateExpression(expectedType, context)»«ENDFOR»}'''
        } else
            '''{«FOR l : t.elements SEPARATOR ', '»«l.doGenerateExpression(expectedType, context)»«ENDFOR»}'''

    }

    def CharSequence generateStructMember(Expression t, Type expectedType, NamedElement context) {
        if (expectedType instanceof Array)
            '''{«t.doGenerateExpression(expectedType, context)»}'''
        else
            t.doGenerateExpression(expectedType, context)
    }

    def dispatch CharSequence doGenerateExpression(UnaryOperation t, Type expectedType, NamedElement context) {
        '''«t.feature»«t.operand.doGenerateExpression(expectedType, context)»'''
    }

    def dispatch CharSequence doGenerateExpression(Expression t, Type expectedType, NamedElement context) {
        '''/*unsupported expression: «t.toString»*/'''
    }

    def dispatch CharSequence doGenerateExpression(BinaryOperation t, Type expectedType, NamedElement context) {
        '''«t.leftOperand.doGenerateExpression(expectedType, context)»«t.feature»«t.rightOperand.doGenerateExpression(expectedType, context)»'''
    }

    def dispatch CharSequence doGenerateExpression(ParenthesizedExpression t, Type expectedType, NamedElement context) {
        '''(«t.expr.doGenerateExpression(expectedType, context)»)'''
    }

    def dispatch CharSequence doGenerateExpression(KeywordExpression t, Type expectedType, NamedElement context) {
        '''«t.name»'''
    }

    def dispatch CharSequence doGenerateExpression(CollectionLiteral t, Operation constructor, NamedElement context) {
        '''{«FOR i : 0 ..< t.elements.size SEPARATOR ', '»«t.elements.get(i).doGenerateExpression(constructor.parameter.get(i).type, context)»«ENDFOR»}'''
    }

    def CharSequence generateExpression(Expression t, Type expectedType, NamedElement context) {
        // find a matching constructor if any
        val constructor = expectedType.findConstructor(t);
        if (constructor !== null)
            t.doGenerateExpression(constructor, context)
        else if (expectedType instanceof Structure)
            t.doGenerateExpression(expectedType, context)
        else if (expectedType instanceof Array)
            '''{«t.doGenerateExpression(expectedType, context)»}'''
        else if (t instanceof CollectionLiteral)
            t.doGenerateExpression(expectedType, context)
        else
            '''{«t.doGenerateExpression(expectedType, context)»}'''
    }

    /**
     * Collect all required includes from an Expression
     */
    def include(Expression expr, IncludeAcceptor acceptor) {
        expr.doInclude(acceptor)
        expr.eAllContents.forEach[it.doInclude(acceptor)]
    }

    def protected dispatch doInclude(EObject expr, IncludeAcceptor acceptor) {
    }

    def protected dispatch doInclude(BuiltInExpression expr, IncludeAcceptor acceptor) {
        acceptor.systemHeader("math.h")
    }

    def protected dispatch doInclude(EnumerationLiteralReference expr, IncludeAcceptor acceptor) {
        val parent = expr.value.eContainer
        if (parent instanceof NamedElement)
            acceptor.include(parent)
    }
}
