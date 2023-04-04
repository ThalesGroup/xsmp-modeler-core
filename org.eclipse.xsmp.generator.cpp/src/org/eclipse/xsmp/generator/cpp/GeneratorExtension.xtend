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
import java.util.Collections
import java.util.List
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xsmp.util.ElementUtil
import org.eclipse.xsmp.util.XsmpUtil
import org.eclipse.xsmp.xcatalogue.Constant
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers
import org.eclipse.xsmp.xcatalogue.NamedElementWithMultiplicity
import org.eclipse.xsmp.xcatalogue.NativeType
import org.eclipse.xsmp.xcatalogue.PrimitiveType
import org.eclipse.xsmp.xcatalogue.Type
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName

@Singleton
class GeneratorExtension {

    @Inject
    extension XsmpUtil
    @Inject
    extension ElementUtil

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

    def dispatch QualifiedName fqn(NamedElement type) {
        return qualifiedNameProvider.getFullyQualifiedName(type)

    }

    def dispatch QualifiedName fqn(NativeType type) {

        val platform = type.platform.findFirst["cpp" == it.name]
        if (platform !== null) {
            return (platform.namespace !== null
                ? QualifiedName.create(platform.namespace.split("::"))
                : QualifiedName.EMPTY).append(platform.type)
        }

        return qualifiedNameProvider.getFullyQualifiedName(type)

    }

    def dispatch include(NamedElement type) {
        "#include \"" + type.fqn.toString("/") + ".h\""
    }

    def dispatch include(PrimitiveType type) {
        "#include \"Smp/PrimitiveTypes.h\""
    }

    def dispatch include(NativeType type) {
        val platform = type.platform.findFirst["cpp" == it.name]
        if (platform !== null && platform.location !== null) {
            return "#include \"" + platform.location + "\""
        }
    }

    def CharSequence viewKind(NamedElement t) {

        var value = t.attributeValue(QualifiedName.create("Attributes", "View"))
        if (value !== null)
            '''«value.doGenerateExpression(null, t.eContainer as NamedElement)»'''
        else
            '''::Smp::ViewKind::VK_None'''
    }

    def CharSequence uuidDeclaration(Type t) {

        ''' 
            /// Universally unique identifier of type «t.name».
            /// @return Universally Unique Identifier of «t.name».
            static constexpr ::Smp::Uuid Uuid_«t.name»{«t.uuid.trim.split("-").map(it | "0x" + it + "U").join(", ")»};
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

}
