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

import com.google.common.collect.ImmutableMap
import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.Collections
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xsmp.util.QualifiedNames
import org.eclipse.xsmp.util.XsmpUtil
import org.eclipse.xsmp.xcatalogue.Array
import org.eclipse.xsmp.xcatalogue.BinaryOperation
import org.eclipse.xsmp.xcatalogue.BooleanLiteral
import org.eclipse.xsmp.xcatalogue.BuiltInConstant
import org.eclipse.xsmp.xcatalogue.BuiltInExpression
import org.eclipse.xsmp.xcatalogue.BuiltInFunction
import org.eclipse.xsmp.xcatalogue.CharacterLiteral
import org.eclipse.xsmp.xcatalogue.CollectionLiteral
import org.eclipse.xsmp.xcatalogue.Constant
import org.eclipse.xsmp.xcatalogue.DesignatedInitializer
import org.eclipse.xsmp.xcatalogue.Document
import org.eclipse.xsmp.xcatalogue.EmptyExpression
import org.eclipse.xsmp.xcatalogue.Enumeration
import org.eclipse.xsmp.xcatalogue.Expression
import org.eclipse.xsmp.xcatalogue.FloatingLiteral
import org.eclipse.xsmp.xcatalogue.IntegerLiteral
import org.eclipse.xsmp.xcatalogue.KeywordExpression
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.NamedElementReference
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.NamedElementWithMultiplicity
import org.eclipse.xsmp.xcatalogue.ParenthesizedExpression
import org.eclipse.xsmp.xcatalogue.StringLiteral
import org.eclipse.xsmp.xcatalogue.Structure
import org.eclipse.xsmp.xcatalogue.Type
import org.eclipse.xsmp.xcatalogue.UnaryOperation
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName

@Singleton
class GeneratorUtil extends XsmpUtil {

    @Inject CommentProvider commentProvider

    final String generatedFilePattern = "%Gen"

    def String nameGen(NamedElement it) {
        return generatedFilePattern.replaceFirst("%", name)
    }

    def QualifiedName fqnGen(NamedElement it) {
        fqn.skipLast(1).append(nameGen)
    }

    def QualifiedName fqn(NamedElement it, boolean useGenPattern) {
        if (useGenPattern)
            fqnGen
        else
            fqn
    }

    def CharSequence id(NamedElement it) {
        '''::«fqn.toString("::")»'''
    }

    def CharSequence idGen(NamedElement it) {
        '''::«fqnGen.toString("::")»'''
    }

    def CharSequence id(NamedElement it, boolean useGenPattern) {
        if (useGenPattern)
            idGen
        else
            id
    }

    def dispatch CharSequence doGenerateExpression(NamedElementReference it) {
        val context = EcoreUtil2.getContainerOfType(it, NamedElementWithMembers)
        var fqn = value.fqn;

        var contextfqn = context.fqn

        if (fqn.startsWith(contextfqn) && contextfqn.getSegmentCount() + 1 == fqn.getSegmentCount())
            '''«value.name»'''
        else
            value.id
    }

    def dispatch CharSequence doGenerateExpression(IntegerLiteral it) {
        if (type instanceof Enumeration)
            '''«getEnumerationLiteral().id»'''
        else
            '''«text.replace("'","")»'''
    }

    def dispatch CharSequence doGenerateExpression(FloatingLiteral it) {
        '''«text.replace("'","")»'''
    }

    def dispatch CharSequence doGenerateExpression(BuiltInFunction it) {
        '''«name»(«FOR p : parameter SEPARATOR ", "»«p.doGenerateExpression()»«ENDFOR»)'''
    }

    def dispatch CharSequence doGenerateExpression(BuiltInConstant it) {
        switch (name) {
            case "PI":
                "M_PI"
            case "E":
                "M_E"
        }
    }

    def dispatch CharSequence doGenerateExpression(BooleanLiteral it) {
        '''«isTrue»'''
    }

    def dispatch CharSequence doGenerateExpression(StringLiteral it) {
        val expectedType = type
        switch (expectedType.primitiveTypeKind ) {
            // convert DateTime and Duration to a number of ns
            case DATE_TIME: {
                '''«getValue(it).dateTimeValue.value»L'''
            }
            case DURATION: {
                '''«getValue(it).durationValue.value»L'''
            }
            default: {
                '''«FOR v : value»«v»«ENDFOR»'''
            }
        }

    }

    def dispatch CharSequence doGenerateExpression(CharacterLiteral it) {
        value
    }

    def dispatch CharSequence doGenerateExpression(CollectionLiteral it) {
        val expectedType = type
        if (expectedType instanceof Array) {
            if (expectedType.itemType instanceof Array)
                '''{«FOR l : elements SEPARATOR ', '»{«l.doGenerateExpression()»}«ENDFOR»}'''
            else
                '''{«FOR l : elements SEPARATOR ', '»«l.doGenerateExpression()»«ENDFOR»}'''
        } else if (expectedType instanceof Structure) {
            '''{«FOR l : elements SEPARATOR ', '»«l.generateStructMember()»«ENDFOR»}'''

        } else
            '''{«FOR l : elements SEPARATOR ', '»«l.doGenerateExpression()»«ENDFOR»}'''

    }

    private def CharSequence generateStructMember(Expression it) {
        val expectedType = type
        if (expectedType instanceof Array)
            '''{«doGenerateExpression()»}'''
        else
            doGenerateExpression()
    }

    def dispatch CharSequence doGenerateExpression(UnaryOperation it) {
        '''«feature»«operand.doGenerateExpression()»'''
    }

    def dispatch CharSequence doGenerateExpression(Expression it) {
        '''/*unsupported expression: «toString»*/'''
    }

    def dispatch CharSequence doGenerateExpression(BinaryOperation it) {
        '''«leftOperand.doGenerateExpression()»«feature»«rightOperand.doGenerateExpression()»'''
    }

    def dispatch CharSequence doGenerateExpression(ParenthesizedExpression it) {
        '''(«expr.doGenerateExpression()»)'''
    }

    def dispatch CharSequence doGenerateExpression(DesignatedInitializer it) {
        '''/* .«field.name» = */«expr.doGenerateExpression()»'''
    }

    def dispatch CharSequence doGenerateExpression(KeywordExpression it) {
        '''«name»'''
    }

    def dispatch CharSequence doGenerateExpression(EmptyExpression it) {
    }

    def CharSequence generateExpression(Expression it) {
        val expectedType = type

        if (expectedType instanceof Structure) // do not add square brackets around struct init
            doGenerateExpression()
        else if (expectedType instanceof Array) // array requires an additional square bracket level
            '''{«doGenerateExpression()»}'''
        else if (it instanceof CollectionLiteral) // simple type, e.g: field Smp.Bool b = {true} --> Smp::Bool b {true}
            doGenerateExpression()
        else
            '''{«doGenerateExpression()»}'''
    }

    /**
     * Collect all required includes from an Expression
     */
    def include(Expression it, IncludeAcceptor acceptor) {
        doInclude(acceptor)
        eAllContents.forEach[doInclude(acceptor)]
    }

    def protected dispatch doInclude(EObject it, IncludeAcceptor acceptor) {
    }

    def protected dispatch doInclude(BuiltInExpression it, IncludeAcceptor acceptor) {
        acceptor.systemHeader("math.h")
    }

    def protected dispatch doInclude(NamedElementReference it, IncludeAcceptor acceptor) {
        val parent = value.eContainer
        if (parent instanceof NamedElement)
            acceptor.include(parent)
    }

    def String name(NamedElement it, boolean useGenPattern) {
        if (useGenPattern)
            nameGen
        else
            name
    }

    def String name(Document elem) {
        elem.name
    }

    def CharSequence description(NamedElement n) {
        if (n.description === null)
            '''""'''
        else
            '''«FOR s : n.description.split("\n") SEPARATOR '\n'»"«s.replace("\\","\\\\").replace("\"","\\\"")»"«ENDFOR»'''
    }

    def CharSequence comment(NamedElement elem) {
        commentProvider.get(elem)

    }

    val includes = ImmutableMap.<QualifiedName, String>builder() // map default Smp types
    .put(QualifiedNames.Smp_Char8, "Smp/PrimitiveTypes") // Char8
    .put(QualifiedNames.Smp_String8, "Smp/PrimitiveTypes") // String8
    .put(QualifiedNames.Smp_Float32, "Smp/PrimitiveTypes") // Float32
    .put(QualifiedNames.Smp_Float64, "Smp/PrimitiveTypes") // Float64
    .put(QualifiedNames.Smp_Int8, "Smp/PrimitiveTypes") // Int8
    .put(QualifiedNames.Smp_UInt8, "Smp/PrimitiveTypes") // UInt8
    .put(QualifiedNames.Smp_Int16, "Smp/PrimitiveTypes") // Int16
    .put(QualifiedNames.Smp_UInt16, "Smp/PrimitiveTypes") // UInt16
    .put(QualifiedNames.Smp_Int32, "Smp/PrimitiveTypes") // Int32
    .put(QualifiedNames.Smp_UInt32, "Smp/PrimitiveTypes") // UInt32
    .put(QualifiedNames.Smp_Int64, "Smp/PrimitiveTypes") // Int64
    .put(QualifiedNames.Smp_UInt64, "Smp/PrimitiveTypes") // UInt64
    .put(QualifiedNames.Smp_Bool, "Smp/PrimitiveTypes") // Bool
    .put(QualifiedNames.Smp_DateTime, "Smp/PrimitiveTypes") // DateTime
    .put(QualifiedNames.Smp_Duration, "Smp/PrimitiveTypes") // Duration
    .put(QualifiedNames.Smp_PrimitiveTypeKind, "Smp/PrimitiveTypes") // PrimitiveTypeKind
    .put(QualifiedNames.Smp_EventSourceCollection, "Smp/IEventSource") // EventSourceCollection
    .put(QualifiedNames.Smp_EntryPointCollection, "Smp/IEntryPoint") // EntryPointCollection
    .put(QualifiedNames.Smp_FactoryCollection, "Smp/IFactory") // FactoryCollection
    .put(QualifiedNames.Smp_FailureCollection, "Smp/IFailure") // FailureCollection
    .put(QualifiedNames.Smp_FieldCollection, "Smp/IField") // FieldCollection
    .put(QualifiedNames.Smp_ComponentCollection, "Smp/IComponent") // ComponentCollection
    .put(QualifiedNames.Smp_OperationCollection, "Smp/IOperation") // OperationCollection
    .put(QualifiedNames.Smp_ParameterCollection, "Smp/IParameter") // ParameterCollection
    .put(QualifiedNames.Smp_PropertyCollection, "Smp/IProperty") // PropertyCollection
    .put(QualifiedNames.Smp_AnySimpleArray, "Smp/AnySimple") // AnySimpleArray
    .put(QualifiedNames.Smp_ModelCollection, "Smp/IModel") // ModelCollection
    .put(QualifiedNames.Smp_ServiceCollection, "Smp/IService") // ServiceCollection
    .put(QualifiedNames.Smp_ReferenceCollection, "Smp/IReference") // ReferenceCollection
    .put(QualifiedNames.Smp_ContainerCollection, "Smp/IContainer") // ContainerCollection
    .put(QualifiedNames.Smp_EventSinkCollection, "Smp/IEventSink") // EventSinkCollection
    .build()

    def dispatch String include(NamedElement type) {

        val fqn = type.fqn
        var include = includes.get(fqn)
        if (include === null)
            include = fqn.toString("/")

        '''#include "«include».h"'''
    }

    def dispatch String include(Document type) {
        '''#include "«type.name()».h"'''
    }

    def CharSequence viewKind(NamedElement t) {

        var value = t.attributeValue(QualifiedNames.Attributes_View)
        if (value !== null)
            '''«value.doGenerateExpression()»'''
        else
            '''::Smp::ViewKind::VK_None'''
    }

    protected def CharSequence generateUUID(Type t) {
        '''{ «t.uuid.trim.split("-").map(it | "0x" + it + "U").join(", ")» }'''
    }

    def CharSequence uuidDeclaration(Type it) {
        ''' 
            /// Universally unique identifier of type «name».
            static constexpr ::Smp::Uuid Uuid_«name» «generateUUID()»;
        '''
    }

    def CharSequence uuidDefinition(Type it) {
    }

    def CharSequence generatePrimitiveKind(Type it) {
        '''::Smp::PrimitiveTypeKind::PTK_«primitiveTypeKind.label»'''
    }

    def CharSequence uuid(Type it) {
        val fqn = it.fqn

        // In Smp, Uuids are in a separate namespace ::Smp::Uuids::
        if (fqn.startsWith(QualifiedNames.Smp))
            '''::Smp::Uuids::Uuid_«name»'''
        else
            '''«(eContainer as NamedElement).id»::Uuid_«name»'''
    }

    def CharSequence lower(NamedElementWithMultiplicity it) {
        if (isOptional)
            return '''0'''
        if (multiplicity === null)
            return '''1'''

        if (multiplicity.lower === null && multiplicity.upper === null)
            if (multiplicity.aux)
                return '''1'''
            else
                return '''0'''
        return multiplicity.lower.doGenerateExpression()

    }

    def CharSequence upper(NamedElementWithMultiplicity it) {

        if (multiplicity === null || isOptional)
            return '''1'''
        if (multiplicity.lower === null && multiplicity.upper === null)
            return "-1"

        if (multiplicity.upper === null)
            if (multiplicity.aux)
                return "-1"
            else
                return multiplicity.lower.doGenerateExpression()

        return multiplicity.upper.doGenerateExpression()
    }

    /**
     * Return the List of sorted Constant from an ItemWithMembers
     * Try to keep natural order.
     * Move constants in case of dependencies
     */
    def sortedConstants(NamedElementWithMembers c) {
        // collect all constants
        val constants = c.member.filter(Constant).toList

        val deps = <Constant, List<Constant>>newLinkedHashMap
        // compute the list of dependencies for each Constant
        constants.forEach [ cst |
            deps.put(cst,
                EcoreUtil.CrossReferencer.find(Collections.singleton(cst.value)).keySet().filter(Constant).filter [
                    constants.contains(it)
                ].toList)
        ]

        var result = <Constant>newArrayList

        while (!deps.empty) {
            var iterator = deps.entrySet.iterator
            while (iterator.hasNext) {
                var dep = iterator.next
                // check that all deps are already contained
                if (result.containsAll(dep.value)) {
                    result.add(dep.key)
                    // remove the current entry
                    iterator.remove
                }
            }
        }
        return result
    }
}
