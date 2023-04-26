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
import org.eclipse.emf.ecore.EObject
import org.eclipse.xsmp.util.XsmpUtil
import org.eclipse.xsmp.xcatalogue.Array
import org.eclipse.xsmp.xcatalogue.BinaryOperation
import org.eclipse.xsmp.xcatalogue.BooleanLiteral
import org.eclipse.xsmp.xcatalogue.BuiltInConstant
import org.eclipse.xsmp.xcatalogue.BuiltInExpression
import org.eclipse.xsmp.xcatalogue.BuiltInFunction
import org.eclipse.xsmp.xcatalogue.CharacterLiteral
import org.eclipse.xsmp.xcatalogue.CollectionLiteral
import org.eclipse.xsmp.xcatalogue.DesignatedInitializer
import org.eclipse.xsmp.xcatalogue.EmptyExpression
import org.eclipse.xsmp.xcatalogue.Enumeration
import org.eclipse.xsmp.xcatalogue.Expression
import org.eclipse.xsmp.xcatalogue.FloatingLiteral
import org.eclipse.xsmp.xcatalogue.IntegerLiteral
import org.eclipse.xsmp.xcatalogue.KeywordExpression
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.NamedElementReference
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.ParenthesizedExpression
import org.eclipse.xsmp.xcatalogue.StringLiteral
import org.eclipse.xsmp.xcatalogue.Structure
import org.eclipse.xsmp.xcatalogue.UnaryOperation
import org.eclipse.xtext.EcoreUtil2

@Singleton
class ExpressionGenerator {

    @Inject
    protected extension XsmpUtil
    

    def dispatch CharSequence doGenerateExpression(NamedElementReference e) {
        val context = EcoreUtil2.getContainerOfType(e, NamedElementWithMembers)
        var fqn = e.value.fqn;

        var contextfqn = context.fqn

        if (fqn.startsWith(contextfqn) && contextfqn.getSegmentCount() + 1 == fqn.getSegmentCount()) {
            return '''«e.value.name»'''
        }
        return '''::«fqn.toString("::")»'''
    }

    def dispatch CharSequence doGenerateExpression(IntegerLiteral t) {
        val expectedType = t.type
        if (expectedType instanceof Enumeration) {
            '''::«t.getEnumerationLiteral().fqn.toString("::")»'''
        } else
            '''«t.text.replace("'","")»'''
    }

    def dispatch CharSequence doGenerateExpression(FloatingLiteral t) {
        '''«t.text.replace("'","")»'''
    }

    def dispatch CharSequence doGenerateExpression(BuiltInFunction t) {
        '''«t.name»(«FOR p : t.parameter SEPARATOR ", "»«p.doGenerateExpression()»«ENDFOR»)'''
    }

    def dispatch CharSequence doGenerateExpression(BuiltInConstant t) {
        switch (t.name) {
            case "PI":
                "M_PI"
            case "E":
                "M_E"
        }
    }

    def dispatch CharSequence doGenerateExpression(BooleanLiteral t) {
        '''«t.isTrue»'''
    }

    def dispatch CharSequence doGenerateExpression(StringLiteral t) {
        val expectedType = t.type
        switch (expectedType.primitiveType ) {
            // convert DateTime and Duration to a number of ns
            case DATE_TIME: {
                val i = Instant.parse(XsmpUtil.getString(t))
                '''«i.epochSecond * 1_000_000_000 + i.nano»L'''
            }
            case DURATION: {
                val i = Duration.parse(XsmpUtil.getString(t))
                '''«i.seconds * 1_000_000_000 + i.nano»L'''
            }
            default: {
                t.value
            }
        }

    }

    def dispatch CharSequence doGenerateExpression(CharacterLiteral t) {
        t.value
    }

    def dispatch CharSequence doGenerateExpression(CollectionLiteral t) {
        val expectedType = t.type
        if (expectedType instanceof Array) {
            if (expectedType.itemType instanceof Array)
                '''{«FOR l : t.elements SEPARATOR ', '»{«l.doGenerateExpression()»}«ENDFOR»}'''
            else
                '''{«FOR l : t.elements SEPARATOR ', '»«l.doGenerateExpression()»«ENDFOR»}'''
        } else if (expectedType instanceof Structure) {
            '''{«FOR l : t.elements SEPARATOR ', '»«l.generateStructMember()»«ENDFOR»}'''

        } else
            '''{«FOR l : t.elements SEPARATOR ', '»«l.doGenerateExpression()»«ENDFOR»}'''

    }

    def CharSequence generateStructMember(Expression t) {
        val expectedType = t.type
        if (expectedType instanceof Array)
            '''{«t.doGenerateExpression()»}'''
        else
            t.doGenerateExpression()
    }

    def dispatch CharSequence doGenerateExpression(UnaryOperation t) {
        '''«t.feature»«t.operand.doGenerateExpression()»'''
    }

    def dispatch CharSequence doGenerateExpression(Expression t) {
        '''/*unsupported expression: «t.toString»*/'''
    }

    def dispatch CharSequence doGenerateExpression(BinaryOperation t) {
        '''«t.leftOperand.doGenerateExpression()»«t.feature»«t.rightOperand.doGenerateExpression()»'''
    }

    def dispatch CharSequence doGenerateExpression(ParenthesizedExpression t) {
        '''(«t.expr.doGenerateExpression()»)'''
    }

    def dispatch CharSequence doGenerateExpression(DesignatedInitializer t) {
        '''/* .«t.field.name» = */«t.expr.doGenerateExpression()»'''
    }

    def dispatch CharSequence doGenerateExpression(KeywordExpression t) {
        '''«t.name»'''
    }

    def dispatch CharSequence doGenerateExpression(EmptyExpression t) {
    }

    def CharSequence generateExpression(Expression t) {
        val expectedType = t.type

        if (expectedType instanceof Structure) // do not add square brackets around struct init
            t.doGenerateExpression()
        else if (expectedType instanceof Array) // array requires an additional square bracket level
            '''{«t.doGenerateExpression()»}'''
        else if (t instanceof CollectionLiteral) // simple type, e.g: field Smp.Bool b = {true} --> Smp::Bool b {true}
            t.doGenerateExpression()
        else
            '''{«t.doGenerateExpression()»}'''
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

    def protected dispatch doInclude(NamedElementReference expr, IncludeAcceptor acceptor) {
        val parent = expr.value.eContainer
        if (parent instanceof NamedElement)
            acceptor.include(parent)
    }
}
