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
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xsmp.util.XsmpUtil
import org.eclipse.xsmp.xcatalogue.Constant
import org.eclipse.xsmp.xcatalogue.Document
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.NamedElementWithMultiplicity
import org.eclipse.xsmp.xcatalogue.Operation
import org.eclipse.xsmp.xcatalogue.SimpleType
import org.eclipse.xsmp.xcatalogue.Type
import org.eclipse.xsmp.xcatalogue.ValueType
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName

@Singleton
class GeneratorExtension extends XsmpUtil{

    static final QualifiedName _View = QualifiedName.create("Attributes", "View")


    @Inject
    extension ExpressionGenerator
    @Inject CommentProvider commentProvider

    final String generatedFilePattern = "%Gen"

    public String newLine = "\n"

    @Inject
    protected IQualifiedNameProvider qualifiedNameProvider

    def String getGenName(NamedElement elem) {
        return generatedFilePattern.replaceFirst("%", elem.getName())
    }

    def String name(NamedElement elem, boolean useGenPattern) {
        if (useGenPattern)
            elem.genName
        else
            elem.name
    }

    def String name(Document elem) {
        elem.name
    }

    def CharSequence description(NamedElement n) {
        if (n.description === null)
            "\"\""
        else
            '''«FOR s : n.description.split("\n") SEPARATOR '\n'»"«s.replace("\\","\\\\").replace("\"","\\\"")»"«ENDFOR»'''
    }

    def CharSequence comment(NamedElement elem) {
        commentProvider.get(elem)

    }

    def QualifiedName fqn(NamedElement type, boolean useGenPattern) {
        var qfn = type.fqn
        if (useGenPattern)
            qfn = qfn.skipLast(1).append(type.genName)
        return qfn
    }


    val includes = ImmutableMap.<QualifiedName, String>builder() // map default Smp types
    .put(QualifiedName.create("Smp", "Char8"), "Smp/PrimitiveTypes") // Char8
    .put(QualifiedName.create("Smp", "String8"), "Smp/PrimitiveTypes") // String8
    .put(QualifiedName.create("Smp", "Float32"), "Smp/PrimitiveTypes") // Float32
    .put(QualifiedName.create("Smp", "Float64"), "Smp/PrimitiveTypes") // Float64
    .put(QualifiedName.create("Smp", "Int8"), "Smp/PrimitiveTypes") // Int8
    .put(QualifiedName.create("Smp", "UInt8"), "Smp/PrimitiveTypes") // UInt8
    .put(QualifiedName.create("Smp", "Int16"), "Smp/PrimitiveTypes") // Int16
    .put(QualifiedName.create("Smp", "UInt16"), "Smp/PrimitiveTypes") // UInt16
    .put(QualifiedName.create("Smp", "Int32"), "Smp/PrimitiveTypes") // Int32
    .put(QualifiedName.create("Smp", "UInt32"), "Smp/PrimitiveTypes") // UInt32
    .put(QualifiedName.create("Smp", "Int64"), "Smp/PrimitiveTypes") // Int64
    .put(QualifiedName.create("Smp", "UInt64"), "Smp/PrimitiveTypes") // UInt64
    .put(QualifiedName.create("Smp", "Bool"), "Smp/PrimitiveTypes") // Bool
    .put(QualifiedName.create("Smp", "DateTime"), "Smp/PrimitiveTypes") // DateTime
    .put(QualifiedName.create("Smp", "Duration"), "Smp/PrimitiveTypes") // Duration
    .put(QualifiedName.create("Smp", "PrimitiveTypeKind"), "Smp/PrimitiveTypes") // PrimitiveTypeKind
    .put(QualifiedName.create("Smp", "EventSourceCollection"), "Smp/IEventSource") // EventSourceCollection
    .put(QualifiedName.create("Smp", "EntryPointCollection"), "Smp/IEntryPoint") // EntryPointCollection
    .put(QualifiedName.create("Smp", "FactoryCollection"), "Smp/IFactory") // FactoryCollection
    .put(QualifiedName.create("Smp", "FailureCollection"), "Smp/IFailure") // FailureCollection
    .put(QualifiedName.create("Smp", "FieldCollection"), "Smp/IField") // FieldCollection
    .put(QualifiedName.create("Smp", "ComponentCollection"), "Smp/IComponent") // ComponentCollection
    .put(QualifiedName.create("Smp", "OperationCollection"), "Smp/IOperation") // OperationCollection
    .put(QualifiedName.create("Smp", "ParameterCollection"), "Smp/IParameter") // ParameterCollection
    .put(QualifiedName.create("Smp", "PropertyCollection"), "Smp/IProperty") // PropertyCollection
    .put(QualifiedName.create("Smp", "AnySimpleArray"), "Smp/AnySimple") // AnySimpleArray
    .put(QualifiedName.create("Smp", "ModelCollection"), "Smp/IModel") // ModelCollection
    .put(QualifiedName.create("Smp", "ServiceCollection"), "Smp/IService") // ServiceCollection
    .put(QualifiedName.create("Smp", "ReferenceCollection"), "Smp/IReference") // ReferenceCollection
    .put(QualifiedName.create("Smp", "ContainerCollection"), "Smp/IContainer") // ContainerCollection
    .put(QualifiedName.create("Smp", "EventSinkCollection"), "Smp/IEventSink") // EventSinkCollection
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

        var value = t.attributeValue(_View)
        if (value !== null)
            '''«value.doGenerateExpression(null, t.eContainer as NamedElement)»'''
        else
            '''::Smp::ViewKind::VK_None'''
    }

    def CharSequence uuidDeclaration(Type t) {
        ''' 
            /// Universally unique identifier of type «t.name».
            static constexpr ::Smp::Uuid Uuid_«t.name»{«t.uuid.trim.split("-").map(it | "0x" + it + "U").join(", ")»};
        '''
    }

    def CharSequence uuidDefinition(Type t) {
        '''
            /// Definition of the Uuid constexpr for «t.name»
            constexpr Smp::Uuid Uuid_«t.name»;
        '''
    }

    def CharSequence generatePrimitiveKind(Type t) {
        '''::Smp::PrimitiveTypeKind::PTK_«t.primitiveType.label»'''
    }

    def CharSequence uuidQfn(Type t) {
        val fqn = qualifiedNameProvider.getFullyQualifiedName(t)

        // In Smp, Uuids are in a separate namespace ::Smp::Uuids::
        if (fqn.firstSegment == "Smp")
            '''::Smp::Uuids::Uuid_«t.name»'''
        else
            '''::«(t.eContainer as NamedElement).fqn.toString("::")»::Uuid_«t.name»'''
    }

    def CharSequence generateLower(NamedElementWithMultiplicity t) {
        if (t.isOptional)
            return '''0'''
        if (t.multiplicity === null)
            return '''1'''

        if (t.multiplicity.lower === null && t.multiplicity.upper === null)
            if (t.multiplicity.aux)
                return '''1'''
            else
                return '''0'''
        return t.multiplicity.lower.doGenerateExpression(null, t.eContainer as NamedElement)

    }

    def CharSequence generateUpper(NamedElementWithMultiplicity t) {

        if (t.multiplicity === null || t.isOptional)
            return '''1'''
        if (t.multiplicity.lower === null && t.multiplicity.upper === null)
            return "-1"

        if (t.multiplicity.upper === null)
            if (t.multiplicity.aux)
                return "-1"
            else
                return t.multiplicity.lower.doGenerateExpression(null, t.eContainer as NamedElement)

        return t.multiplicity.upper.doGenerateExpression(null, t.eContainer as NamedElement)
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

    def isInvokable(Operation op) {
        if (op.returnParameter !== null && !(op.returnParameter.type instanceof SimpleType))
            return false

        for (param : op.parameter)
            if (!(param.type instanceof ValueType))
                return false

        return true
    }
}
