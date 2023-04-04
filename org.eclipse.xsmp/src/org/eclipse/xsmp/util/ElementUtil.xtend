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
package org.eclipse.xsmp.util

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.Optional
import org.eclipse.emf.ecore.EObject
import org.eclipse.xsmp.xcatalogue.Array
import org.eclipse.xsmp.xcatalogue.Association
import org.eclipse.xsmp.xcatalogue.Attribute
import org.eclipse.xsmp.xcatalogue.AttributeType
import org.eclipse.xsmp.xcatalogue.Expression
import org.eclipse.xsmp.xcatalogue.Field
import org.eclipse.xsmp.xcatalogue.Interface
import org.eclipse.xsmp.xcatalogue.NamedElement
import org.eclipse.xsmp.xcatalogue.NativeType
import org.eclipse.xsmp.xcatalogue.Operation
import org.eclipse.xsmp.xcatalogue.Parameter
import org.eclipse.xsmp.xcatalogue.Property
import org.eclipse.xsmp.xcatalogue.ReferenceType
import org.eclipse.xsmp.xcatalogue.SimpleType
import org.eclipse.xsmp.xcatalogue.Type
import org.eclipse.xsmp.xcatalogue.ValueType
import org.eclipse.xsmp.xcatalogue.VisibilityElement
import org.eclipse.xsmp.xcatalogue.VisibilityKind
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName

@Singleton
class ElementUtil {
    static final QualifiedName _Static = QualifiedName.create("Attributes", "Static")
    static final QualifiedName _Const = QualifiedName.create("Attributes", "Const")
    static final QualifiedName _Mutable = QualifiedName.create("Attributes", "Mutable")
    static final QualifiedName _ByPointer = QualifiedName.create("Attributes", "ByPointer")
    static final QualifiedName _ByReference = QualifiedName.create("Attributes", "ByReference")
    static final QualifiedName _Abstract = QualifiedName.create("Attributes", "Abstract")
    static final QualifiedName _Virtual = QualifiedName.create("Attributes", "Virtual")
    static final QualifiedName _Constructor = QualifiedName.create("Attributes", "Constructor")
    static final QualifiedName _Forcible = QualifiedName.create("Attributes", "Forcible")
    static final QualifiedName _Failure = QualifiedName.create("Attributes", "Failure")
    static final QualifiedName _ConstGetter = QualifiedName.create("Attributes", "ConstGetter")
    static final QualifiedName _NoConstructor = QualifiedName.create("Attributes", "NoConstructor")
    static final QualifiedName _NoDestructor = QualifiedName.create("Attributes", "NoDestructor")
    static final QualifiedName _SimpleArray = QualifiedName.create("Attributes", "SimpleArray")
    @Inject
    IQualifiedNameProvider qualifiedNameProvider

    def Attribute attribute(NamedElement o, QualifiedName id) {

        o.metadatum.metadata.findFirst [
            it.type !== null && id.equals(qualifiedNameProvider.getFullyQualifiedName(it.type))
        ]

    }

    def Expression attributeValue(NamedElement o, QualifiedName id) {

        var attribute = attribute(o, id)
        if (attribute === null)
            null
        else if (attribute.value === null)
            (attribute.type as AttributeType).^default
        else
            attribute.value
    }

    def Optional<Boolean> attributeBoolValue(NamedElement o, QualifiedName id) {

        Solver.INSTANCE.getBoolean(o.attributeValue(id))
    }

    def boolean isStatic(NamedElement o) {
        o.attributeBoolValue(_Static).orElse(false)
    }

    def boolean isConst(NamedElement o) {
        o.attributeBoolValue(_Const).orElse(false)
    }

    def boolean isMutable(NamedElement o) {
        o.attributeBoolValue(_Mutable).orElse(false)
    }

    def boolean isByPointer(NamedElement o) {
        o.attributeBoolValue(_ByPointer).orElse(false)
    }

    def boolean isByReference(NamedElement o) {
        o.attributeBoolValue(_ByReference).orElse(false)
    }

    def boolean isAbstract(NamedElement o) {
        o.attributeBoolValue(_Abstract).orElse(o.eContainer instanceof Interface)
    }

    def boolean isVirtual(NamedElement o) {
        o.attributeBoolValue(_Virtual).orElse(o.eContainer instanceof ReferenceType)

    }

    def boolean isConstructor(Operation o) {
        o.attributeBoolValue(_Constructor).orElse(false)
    }

    def boolean isByPointer(Parameter o) {

        o.kind === ArgKind.BY_PTR
    }

    def boolean isByReference(Parameter o) {

        o.kind === ArgKind.BY_REF
    }

    def boolean isForcible(Field o) {
        o.attributeBoolValue(_Forcible).orElse(false)
    }

    def boolean isFailure(Field o) {
        o.attributeBoolValue(_Failure).orElse(false)
    }

    def boolean isConstGetter(Property o) {
        o.attributeBoolValue(_ConstGetter).orElse(false)
    }

    def boolean isNoConstructor(Type o) {
        o.attributeBoolValue(_NoConstructor).orElse(false)
    }

    def boolean isNoDestructor(Type o) {
        o.attributeBoolValue(_NoDestructor).orElse(false)
    }

    def boolean isSimpleArray(Array o) {
        o.attributeBoolValue(_SimpleArray).orElse(false)
    }

    def boolean isConst(Parameter o) {

        var isConst = (o as NamedElement).attributeBoolValue(_Const)

        if (isConst.empty) {
            isConst = switch (o.direction) {
                case IN:
                    return !(o.type instanceof ValueType)
                case RETURN,
                case OUT,
                case INOUT:
                    return false
            }
        }
        return isConst.get
    }

    def boolean isByPointer(Association o) {
        o.attributeBoolValue(_ByPointer).orElse(o.type instanceof ReferenceType)
    }

    def ArgKind kind(Parameter o) {

        val type = o.type

        var kind = if (type instanceof ReferenceType)
                switch (o.direction) {
                    case IN: ArgKind.BY_REF
                    case RETURN,
                    case OUT,
                    case INOUT: ArgKind.BY_PTR
                }
            else if (type instanceof NativeType || type instanceof ValueType)
                switch (o.direction) {
                    case IN,
                    case RETURN: ArgKind.BY_VALUE
                    case OUT,
                    case INOUT: ArgKind.BY_PTR
                }
            else
                ArgKind.BY_VALUE

        val byPtr = o.attributeBoolValue(_ByPointer)
        val byReference = o.attributeBoolValue(_ByReference)

        if (!byPtr.empty) {
            if (byPtr.get)
                kind = ArgKind.BY_PTR
            else if (kind == ArgKind.BY_PTR)
                kind = ArgKind.BY_VALUE

        }
        if (!byReference.empty) {
            if (byReference.get)
                kind = ArgKind.BY_REF
            else if (kind == ArgKind.BY_REF)
                kind = ArgKind.BY_VALUE
        }
        return kind
    }

    enum ArgKind {
        BY_VALUE,
        BY_PTR,
        BY_REF
    }

    def dispatch VisibilityKind visibility(EObject t) {
        VisibilityKind.PUBLIC
    }

    def dispatch VisibilityKind visibility(VisibilityElement t) {
        t.realVisibility
    }

    def boolean isSimpleType(Type t) {
        return t instanceof SimpleType
    }
}
