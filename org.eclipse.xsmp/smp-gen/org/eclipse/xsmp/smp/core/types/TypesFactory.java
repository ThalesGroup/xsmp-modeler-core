/**
 * *******************************************************************************
 * * Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
 * *
 * * All rights reserved. This program and the accompanying materials
 * * are made available under the terms of the Eclipse Public License 2.0
 * * which accompanies this distribution, and is available at
 * * https://www.eclipse.org/legal/epl-2.0/
 * *
 * * SPDX-License-Identifier: EPL-2.0
 * ******************************************************************************
 */
package org.eclipse.xsmp.smp.core.types;

import org.eclipse.emf.ecore.EFactory;

import org.eclipse.xsmp.smp.core.elements.NamedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage
 * @generated
 */
public interface TypesFactory extends EFactory
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TypesFactory eINSTANCE = org.eclipse.xsmp.smp.core.types.impl.TypesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Array</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array</em>'.
	 * @generated
	 */
	Array createArray();

	/**
	 * Returns a new object of class '<em>Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array Value</em>'.
	 * @generated
	 */
	ArrayValue createArrayValue();

	/**
	 * Returns a new object of class '<em>Association</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Association</em>'.
	 * @generated
	 */
	Association createAssociation();

	/**
	 * Returns a new object of class '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute</em>'.
	 * @generated
	 */
	Attribute createAttribute();

	/**
	 * Returns a new object of class '<em>Attribute Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Type</em>'.
	 * @generated
	 */
	AttributeType createAttributeType();

	/**
	 * Returns a new object of class '<em>Bool Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bool Array Value</em>'.
	 * @generated
	 */
	BoolArrayValue createBoolArrayValue();

	/**
	 * Returns a new object of class '<em>Bool Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bool Value</em>'.
	 * @generated
	 */
	BoolValue createBoolValue();

	/**
	 * Returns a new object of class '<em>Char8 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Char8 Array Value</em>'.
	 * @generated
	 */
	Char8ArrayValue createChar8ArrayValue();

	/**
	 * Returns a new object of class '<em>Char8 Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Char8 Value</em>'.
	 * @generated
	 */
	Char8Value createChar8Value();

	/**
	 * Returns a new object of class '<em>Class</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Class</em>'.
	 * @generated
	 */
	Class createClass();

	/**
	 * Returns a new object of class '<em>Constant</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constant</em>'.
	 * @generated
	 */
	Constant createConstant();

	/**
	 * Returns a new object of class '<em>Date Time Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Date Time Array Value</em>'.
	 * @generated
	 */
	DateTimeArrayValue createDateTimeArrayValue();

	/**
	 * Returns a new object of class '<em>Date Time Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Date Time Value</em>'.
	 * @generated
	 */
	DateTimeValue createDateTimeValue();

	/**
	 * Returns a new object of class '<em>Duration Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Duration Array Value</em>'.
	 * @generated
	 */
	DurationArrayValue createDurationArrayValue();

	/**
	 * Returns a new object of class '<em>Duration Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Duration Value</em>'.
	 * @generated
	 */
	DurationValue createDurationValue();

	/**
	 * Returns a new object of class '<em>Enumeration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enumeration</em>'.
	 * @generated
	 */
	Enumeration createEnumeration();

	/**
	 * Returns a new object of class '<em>Enumeration Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enumeration Array Value</em>'.
	 * @generated
	 */
	EnumerationArrayValue createEnumerationArrayValue();

	/**
	 * Returns a new object of class '<em>Enumeration Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enumeration Literal</em>'.
	 * @generated
	 */
	EnumerationLiteral createEnumerationLiteral();

	/**
	 * Returns a new object of class '<em>Enumeration Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enumeration Value</em>'.
	 * @generated
	 */
	EnumerationValue createEnumerationValue();

	/**
	 * Returns a new object of class '<em>Exception</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exception</em>'.
	 * @generated
	 */
	Exception createException();

	/**
	 * Returns a new object of class '<em>Field</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Field</em>'.
	 * @generated
	 */
	Field createField();

	/**
	 * Returns a new object of class '<em>Float</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Float</em>'.
	 * @generated
	 */
	Float createFloat();

	/**
	 * Returns a new object of class '<em>Float32 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Float32 Array Value</em>'.
	 * @generated
	 */
	Float32ArrayValue createFloat32ArrayValue();

	/**
	 * Returns a new object of class '<em>Float32 Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Float32 Value</em>'.
	 * @generated
	 */
	Float32Value createFloat32Value();

	/**
	 * Returns a new object of class '<em>Float64 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Float64 Array Value</em>'.
	 * @generated
	 */
	Float64ArrayValue createFloat64ArrayValue();

	/**
	 * Returns a new object of class '<em>Float64 Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Float64 Value</em>'.
	 * @generated
	 */
	Float64Value createFloat64Value();

	/**
	 * Returns a new object of class '<em>Int16 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Int16 Array Value</em>'.
	 * @generated
	 */
	Int16ArrayValue createInt16ArrayValue();

	/**
	 * Returns a new object of class '<em>Int16 Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Int16 Value</em>'.
	 * @generated
	 */
	Int16Value createInt16Value();

	/**
	 * Returns a new object of class '<em>Int32 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Int32 Array Value</em>'.
	 * @generated
	 */
	Int32ArrayValue createInt32ArrayValue();

	/**
	 * Returns a new object of class '<em>Int32 Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Int32 Value</em>'.
	 * @generated
	 */
	Int32Value createInt32Value();

	/**
	 * Returns a new object of class '<em>Int64 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Int64 Array Value</em>'.
	 * @generated
	 */
	Int64ArrayValue createInt64ArrayValue();

	/**
	 * Returns a new object of class '<em>Int64 Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Int64 Value</em>'.
	 * @generated
	 */
	Int64Value createInt64Value();

	/**
	 * Returns a new object of class '<em>Int8 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Int8 Array Value</em>'.
	 * @generated
	 */
	Int8ArrayValue createInt8ArrayValue();

	/**
	 * Returns a new object of class '<em>Int8 Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Int8 Value</em>'.
	 * @generated
	 */
	Int8Value createInt8Value();

	/**
	 * Returns a new object of class '<em>Integer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer</em>'.
	 * @generated
	 */
	Integer createInteger();

	/**
	 * Returns a new object of class '<em>Native Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Native Type</em>'.
	 * @generated
	 */
	NativeType createNativeType();

	/**
	 * Returns a new object of class '<em>Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation</em>'.
	 * @generated
	 */
	Operation createOperation();

	/**
	 * Returns a new object of class '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter</em>'.
	 * @generated
	 */
	Parameter createParameter();

	/**
	 * Returns a new object of class '<em>Platform Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Platform Mapping</em>'.
	 * @generated
	 */
	PlatformMapping createPlatformMapping();

	/**
	 * Returns a new object of class '<em>Primitive Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Primitive Type</em>'.
	 * @generated
	 */
	PrimitiveType createPrimitiveType();

	/**
	 * Returns a new object of class '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property</em>'.
	 * @generated
	 */
	Property createProperty();

	/**
	 * Returns a new object of class '<em>String</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String</em>'.
	 * @generated
	 */
	String createString();

	/**
	 * Returns a new object of class '<em>String8 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String8 Array Value</em>'.
	 * @generated
	 */
	String8ArrayValue createString8ArrayValue();

	/**
	 * Returns a new object of class '<em>String8 Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String8 Value</em>'.
	 * @generated
	 */
	String8Value createString8Value();

	/**
	 * Returns a new object of class '<em>Structure</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Structure</em>'.
	 * @generated
	 */
	Structure createStructure();

	/**
	 * Returns a new object of class '<em>Structure Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Structure Value</em>'.
	 * @generated
	 */
	StructureValue createStructureValue();

	/**
	 * Returns a new object of class '<em>Element Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Element Reference</em>'.
	 * @generated
	 */
	<T extends NamedElement> ElementReference<T> createElementReference();

	/**
	 * Returns a new object of class '<em>UInt16 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>UInt16 Array Value</em>'.
	 * @generated
	 */
	UInt16ArrayValue createUInt16ArrayValue();

	/**
	 * Returns a new object of class '<em>UInt16 Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>UInt16 Value</em>'.
	 * @generated
	 */
	UInt16Value createUInt16Value();

	/**
	 * Returns a new object of class '<em>UInt32 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>UInt32 Array Value</em>'.
	 * @generated
	 */
	UInt32ArrayValue createUInt32ArrayValue();

	/**
	 * Returns a new object of class '<em>UInt32 Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>UInt32 Value</em>'.
	 * @generated
	 */
	UInt32Value createUInt32Value();

	/**
	 * Returns a new object of class '<em>UInt64 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>UInt64 Array Value</em>'.
	 * @generated
	 */
	UInt64ArrayValue createUInt64ArrayValue();

	/**
	 * Returns a new object of class '<em>UInt64 Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>UInt64 Value</em>'.
	 * @generated
	 */
	UInt64Value createUInt64Value();

	/**
	 * Returns a new object of class '<em>UInt8 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>UInt8 Array Value</em>'.
	 * @generated
	 */
	UInt8ArrayValue createUInt8ArrayValue();

	/**
	 * Returns a new object of class '<em>UInt8 Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>UInt8 Value</em>'.
	 * @generated
	 */
	UInt8Value createUInt8Value();

	/**
	 * Returns a new object of class '<em>Value Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Value Reference</em>'.
	 * @generated
	 */
	ValueReference createValueReference();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TypesPackage getTypesPackage();

} //TypesFactory
