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
package org.eclipse.xsmp.smp.core.types.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.xsmp.smp.core.elements.Metadata;
import org.eclipse.xsmp.smp.core.elements.NamedElement;

import org.eclipse.xsmp.smp.core.types.Array;
import org.eclipse.xsmp.smp.core.types.ArrayValue;
import org.eclipse.xsmp.smp.core.types.Association;
import org.eclipse.xsmp.smp.core.types.Attribute;
import org.eclipse.xsmp.smp.core.types.AttributeType;
import org.eclipse.xsmp.smp.core.types.BoolArrayValue;
import org.eclipse.xsmp.smp.core.types.BoolValue;
import org.eclipse.xsmp.smp.core.types.Char8ArrayValue;
import org.eclipse.xsmp.smp.core.types.Char8Value;
import org.eclipse.xsmp.smp.core.types.Constant;
import org.eclipse.xsmp.smp.core.types.DateTimeArrayValue;
import org.eclipse.xsmp.smp.core.types.DateTimeValue;
import org.eclipse.xsmp.smp.core.types.DurationArrayValue;
import org.eclipse.xsmp.smp.core.types.DurationValue;
import org.eclipse.xsmp.smp.core.types.ElementReference;
import org.eclipse.xsmp.smp.core.types.Enumeration;
import org.eclipse.xsmp.smp.core.types.EnumerationArrayValue;
import org.eclipse.xsmp.smp.core.types.EnumerationLiteral;
import org.eclipse.xsmp.smp.core.types.EnumerationValue;
import org.eclipse.xsmp.smp.core.types.Field;
import org.eclipse.xsmp.smp.core.types.Float32ArrayValue;
import org.eclipse.xsmp.smp.core.types.Float32Value;
import org.eclipse.xsmp.smp.core.types.Float64ArrayValue;
import org.eclipse.xsmp.smp.core.types.Float64Value;
import org.eclipse.xsmp.smp.core.types.Int16ArrayValue;
import org.eclipse.xsmp.smp.core.types.Int16Value;
import org.eclipse.xsmp.smp.core.types.Int32ArrayValue;
import org.eclipse.xsmp.smp.core.types.Int32Value;
import org.eclipse.xsmp.smp.core.types.Int64ArrayValue;
import org.eclipse.xsmp.smp.core.types.Int64Value;
import org.eclipse.xsmp.smp.core.types.Int8ArrayValue;
import org.eclipse.xsmp.smp.core.types.Int8Value;
import org.eclipse.xsmp.smp.core.types.LanguageType;
import org.eclipse.xsmp.smp.core.types.NativeType;
import org.eclipse.xsmp.smp.core.types.Operation;
import org.eclipse.xsmp.smp.core.types.Parameter;
import org.eclipse.xsmp.smp.core.types.PlatformMapping;
import org.eclipse.xsmp.smp.core.types.PrimitiveType;
import org.eclipse.xsmp.smp.core.types.Property;
import org.eclipse.xsmp.smp.core.types.SimpleArrayValue;
import org.eclipse.xsmp.smp.core.types.SimpleType;
import org.eclipse.xsmp.smp.core.types.SimpleValue;
import org.eclipse.xsmp.smp.core.types.String8ArrayValue;
import org.eclipse.xsmp.smp.core.types.String8Value;
import org.eclipse.xsmp.smp.core.types.Structure;
import org.eclipse.xsmp.smp.core.types.StructureValue;
import org.eclipse.xsmp.smp.core.types.Type;
import org.eclipse.xsmp.smp.core.types.TypesPackage;
import org.eclipse.xsmp.smp.core.types.UInt16ArrayValue;
import org.eclipse.xsmp.smp.core.types.UInt16Value;
import org.eclipse.xsmp.smp.core.types.UInt32ArrayValue;
import org.eclipse.xsmp.smp.core.types.UInt32Value;
import org.eclipse.xsmp.smp.core.types.UInt64ArrayValue;
import org.eclipse.xsmp.smp.core.types.UInt64Value;
import org.eclipse.xsmp.smp.core.types.UInt8ArrayValue;
import org.eclipse.xsmp.smp.core.types.UInt8Value;
import org.eclipse.xsmp.smp.core.types.Value;
import org.eclipse.xsmp.smp.core.types.ValueReference;
import org.eclipse.xsmp.smp.core.types.ValueType;
import org.eclipse.xsmp.smp.core.types.VisibilityElement;

import org.eclipse.xsmp.smp.xlink.SimpleLinkBase;
import org.eclipse.xsmp.smp.xlink.SimpleLinkRef;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage
 * @generated
 */
public class TypesSwitch<T1> extends Switch<T1>
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TypesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesSwitch()
	{
		if (modelPackage == null)
		{
			modelPackage = TypesPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage)
	{
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T1 doSwitch(int classifierID, EObject theEObject)
	{
		switch (classifierID)
		{
			case TypesPackage.ARRAY:
			{
				Array array = (Array)theEObject;
				T1 result = caseArray(array);
				if (result == null) result = caseValueType(array);
				if (result == null) result = caseLanguageType(array);
				if (result == null) result = caseType(array);
				if (result == null) result = caseVisibilityElement(array);
				if (result == null) result = caseNamedElement(array);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ARRAY_VALUE:
			{
				ArrayValue arrayValue = (ArrayValue)theEObject;
				T1 result = caseArrayValue(arrayValue);
				if (result == null) result = caseValue(arrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ASSOCIATION:
			{
				Association association = (Association)theEObject;
				T1 result = caseAssociation(association);
				if (result == null) result = caseVisibilityElement(association);
				if (result == null) result = caseNamedElement(association);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ATTRIBUTE:
			{
				Attribute attribute = (Attribute)theEObject;
				T1 result = caseAttribute(attribute);
				if (result == null) result = caseMetadata(attribute);
				if (result == null) result = caseNamedElement(attribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ATTRIBUTE_TYPE:
			{
				AttributeType attributeType = (AttributeType)theEObject;
				T1 result = caseAttributeType(attributeType);
				if (result == null) result = caseType(attributeType);
				if (result == null) result = caseVisibilityElement(attributeType);
				if (result == null) result = caseNamedElement(attributeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.BOOL_ARRAY_VALUE:
			{
				BoolArrayValue boolArrayValue = (BoolArrayValue)theEObject;
				T1 result = caseBoolArrayValue(boolArrayValue);
				if (result == null) result = caseSimpleArrayValue(boolArrayValue);
				if (result == null) result = caseValue(boolArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.BOOL_VALUE:
			{
				BoolValue boolValue = (BoolValue)theEObject;
				T1 result = caseBoolValue(boolValue);
				if (result == null) result = caseSimpleValue(boolValue);
				if (result == null) result = caseValue(boolValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.CHAR8_ARRAY_VALUE:
			{
				Char8ArrayValue char8ArrayValue = (Char8ArrayValue)theEObject;
				T1 result = caseChar8ArrayValue(char8ArrayValue);
				if (result == null) result = caseSimpleArrayValue(char8ArrayValue);
				if (result == null) result = caseValue(char8ArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.CHAR8_VALUE:
			{
				Char8Value char8Value = (Char8Value)theEObject;
				T1 result = caseChar8Value(char8Value);
				if (result == null) result = caseSimpleValue(char8Value);
				if (result == null) result = caseValue(char8Value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.CLASS:
			{
				org.eclipse.xsmp.smp.core.types.Class class_ = (org.eclipse.xsmp.smp.core.types.Class)theEObject;
				T1 result = caseClass(class_);
				if (result == null) result = caseStructure(class_);
				if (result == null) result = caseValueType(class_);
				if (result == null) result = caseLanguageType(class_);
				if (result == null) result = caseType(class_);
				if (result == null) result = caseVisibilityElement(class_);
				if (result == null) result = caseNamedElement(class_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.CONSTANT:
			{
				Constant constant = (Constant)theEObject;
				T1 result = caseConstant(constant);
				if (result == null) result = caseVisibilityElement(constant);
				if (result == null) result = caseNamedElement(constant);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DATE_TIME_ARRAY_VALUE:
			{
				DateTimeArrayValue dateTimeArrayValue = (DateTimeArrayValue)theEObject;
				T1 result = caseDateTimeArrayValue(dateTimeArrayValue);
				if (result == null) result = caseSimpleArrayValue(dateTimeArrayValue);
				if (result == null) result = caseValue(dateTimeArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DATE_TIME_VALUE:
			{
				DateTimeValue dateTimeValue = (DateTimeValue)theEObject;
				T1 result = caseDateTimeValue(dateTimeValue);
				if (result == null) result = caseSimpleValue(dateTimeValue);
				if (result == null) result = caseValue(dateTimeValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DURATION_ARRAY_VALUE:
			{
				DurationArrayValue durationArrayValue = (DurationArrayValue)theEObject;
				T1 result = caseDurationArrayValue(durationArrayValue);
				if (result == null) result = caseSimpleArrayValue(durationArrayValue);
				if (result == null) result = caseValue(durationArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.DURATION_VALUE:
			{
				DurationValue durationValue = (DurationValue)theEObject;
				T1 result = caseDurationValue(durationValue);
				if (result == null) result = caseSimpleValue(durationValue);
				if (result == null) result = caseValue(durationValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ENUMERATION:
			{
				Enumeration enumeration = (Enumeration)theEObject;
				T1 result = caseEnumeration(enumeration);
				if (result == null) result = caseSimpleType(enumeration);
				if (result == null) result = caseValueType(enumeration);
				if (result == null) result = caseLanguageType(enumeration);
				if (result == null) result = caseType(enumeration);
				if (result == null) result = caseVisibilityElement(enumeration);
				if (result == null) result = caseNamedElement(enumeration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ENUMERATION_ARRAY_VALUE:
			{
				EnumerationArrayValue enumerationArrayValue = (EnumerationArrayValue)theEObject;
				T1 result = caseEnumerationArrayValue(enumerationArrayValue);
				if (result == null) result = caseSimpleArrayValue(enumerationArrayValue);
				if (result == null) result = caseValue(enumerationArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ENUMERATION_LITERAL:
			{
				EnumerationLiteral enumerationLiteral = (EnumerationLiteral)theEObject;
				T1 result = caseEnumerationLiteral(enumerationLiteral);
				if (result == null) result = caseNamedElement(enumerationLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ENUMERATION_VALUE:
			{
				EnumerationValue enumerationValue = (EnumerationValue)theEObject;
				T1 result = caseEnumerationValue(enumerationValue);
				if (result == null) result = caseSimpleValue(enumerationValue);
				if (result == null) result = caseValue(enumerationValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.EXCEPTION:
			{
				org.eclipse.xsmp.smp.core.types.Exception exception = (org.eclipse.xsmp.smp.core.types.Exception)theEObject;
				T1 result = caseException(exception);
				if (result == null) result = caseClass(exception);
				if (result == null) result = caseStructure(exception);
				if (result == null) result = caseValueType(exception);
				if (result == null) result = caseLanguageType(exception);
				if (result == null) result = caseType(exception);
				if (result == null) result = caseVisibilityElement(exception);
				if (result == null) result = caseNamedElement(exception);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.FIELD:
			{
				Field field = (Field)theEObject;
				T1 result = caseField(field);
				if (result == null) result = caseVisibilityElement(field);
				if (result == null) result = caseNamedElement(field);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.FLOAT:
			{
				org.eclipse.xsmp.smp.core.types.Float float_ = (org.eclipse.xsmp.smp.core.types.Float)theEObject;
				T1 result = caseFloat(float_);
				if (result == null) result = caseSimpleType(float_);
				if (result == null) result = caseValueType(float_);
				if (result == null) result = caseLanguageType(float_);
				if (result == null) result = caseType(float_);
				if (result == null) result = caseVisibilityElement(float_);
				if (result == null) result = caseNamedElement(float_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.FLOAT32_ARRAY_VALUE:
			{
				Float32ArrayValue float32ArrayValue = (Float32ArrayValue)theEObject;
				T1 result = caseFloat32ArrayValue(float32ArrayValue);
				if (result == null) result = caseSimpleArrayValue(float32ArrayValue);
				if (result == null) result = caseValue(float32ArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.FLOAT32_VALUE:
			{
				Float32Value float32Value = (Float32Value)theEObject;
				T1 result = caseFloat32Value(float32Value);
				if (result == null) result = caseSimpleValue(float32Value);
				if (result == null) result = caseValue(float32Value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.FLOAT64_ARRAY_VALUE:
			{
				Float64ArrayValue float64ArrayValue = (Float64ArrayValue)theEObject;
				T1 result = caseFloat64ArrayValue(float64ArrayValue);
				if (result == null) result = caseSimpleArrayValue(float64ArrayValue);
				if (result == null) result = caseValue(float64ArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.FLOAT64_VALUE:
			{
				Float64Value float64Value = (Float64Value)theEObject;
				T1 result = caseFloat64Value(float64Value);
				if (result == null) result = caseSimpleValue(float64Value);
				if (result == null) result = caseValue(float64Value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INT16_ARRAY_VALUE:
			{
				Int16ArrayValue int16ArrayValue = (Int16ArrayValue)theEObject;
				T1 result = caseInt16ArrayValue(int16ArrayValue);
				if (result == null) result = caseSimpleArrayValue(int16ArrayValue);
				if (result == null) result = caseValue(int16ArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INT16_VALUE:
			{
				Int16Value int16Value = (Int16Value)theEObject;
				T1 result = caseInt16Value(int16Value);
				if (result == null) result = caseSimpleValue(int16Value);
				if (result == null) result = caseValue(int16Value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INT32_ARRAY_VALUE:
			{
				Int32ArrayValue int32ArrayValue = (Int32ArrayValue)theEObject;
				T1 result = caseInt32ArrayValue(int32ArrayValue);
				if (result == null) result = caseSimpleArrayValue(int32ArrayValue);
				if (result == null) result = caseValue(int32ArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INT32_VALUE:
			{
				Int32Value int32Value = (Int32Value)theEObject;
				T1 result = caseInt32Value(int32Value);
				if (result == null) result = caseSimpleValue(int32Value);
				if (result == null) result = caseValue(int32Value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INT64_ARRAY_VALUE:
			{
				Int64ArrayValue int64ArrayValue = (Int64ArrayValue)theEObject;
				T1 result = caseInt64ArrayValue(int64ArrayValue);
				if (result == null) result = caseSimpleArrayValue(int64ArrayValue);
				if (result == null) result = caseValue(int64ArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INT64_VALUE:
			{
				Int64Value int64Value = (Int64Value)theEObject;
				T1 result = caseInt64Value(int64Value);
				if (result == null) result = caseSimpleValue(int64Value);
				if (result == null) result = caseValue(int64Value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INT8_ARRAY_VALUE:
			{
				Int8ArrayValue int8ArrayValue = (Int8ArrayValue)theEObject;
				T1 result = caseInt8ArrayValue(int8ArrayValue);
				if (result == null) result = caseSimpleArrayValue(int8ArrayValue);
				if (result == null) result = caseValue(int8ArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INT8_VALUE:
			{
				Int8Value int8Value = (Int8Value)theEObject;
				T1 result = caseInt8Value(int8Value);
				if (result == null) result = caseSimpleValue(int8Value);
				if (result == null) result = caseValue(int8Value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.INTEGER:
			{
				org.eclipse.xsmp.smp.core.types.Integer integer = (org.eclipse.xsmp.smp.core.types.Integer)theEObject;
				T1 result = caseInteger(integer);
				if (result == null) result = caseSimpleType(integer);
				if (result == null) result = caseValueType(integer);
				if (result == null) result = caseLanguageType(integer);
				if (result == null) result = caseType(integer);
				if (result == null) result = caseVisibilityElement(integer);
				if (result == null) result = caseNamedElement(integer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.LANGUAGE_TYPE:
			{
				LanguageType languageType = (LanguageType)theEObject;
				T1 result = caseLanguageType(languageType);
				if (result == null) result = caseType(languageType);
				if (result == null) result = caseVisibilityElement(languageType);
				if (result == null) result = caseNamedElement(languageType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.NATIVE_TYPE:
			{
				NativeType nativeType = (NativeType)theEObject;
				T1 result = caseNativeType(nativeType);
				if (result == null) result = caseLanguageType(nativeType);
				if (result == null) result = caseType(nativeType);
				if (result == null) result = caseVisibilityElement(nativeType);
				if (result == null) result = caseNamedElement(nativeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.OPERATION:
			{
				Operation operation = (Operation)theEObject;
				T1 result = caseOperation(operation);
				if (result == null) result = caseVisibilityElement(operation);
				if (result == null) result = caseNamedElement(operation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.PARAMETER:
			{
				Parameter parameter = (Parameter)theEObject;
				T1 result = caseParameter(parameter);
				if (result == null) result = caseNamedElement(parameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.PLATFORM_MAPPING:
			{
				PlatformMapping platformMapping = (PlatformMapping)theEObject;
				T1 result = casePlatformMapping(platformMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.PRIMITIVE_TYPE:
			{
				PrimitiveType primitiveType = (PrimitiveType)theEObject;
				T1 result = casePrimitiveType(primitiveType);
				if (result == null) result = caseSimpleType(primitiveType);
				if (result == null) result = caseValueType(primitiveType);
				if (result == null) result = caseLanguageType(primitiveType);
				if (result == null) result = caseType(primitiveType);
				if (result == null) result = caseVisibilityElement(primitiveType);
				if (result == null) result = caseNamedElement(primitiveType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.PROPERTY:
			{
				Property property = (Property)theEObject;
				T1 result = caseProperty(property);
				if (result == null) result = caseVisibilityElement(property);
				if (result == null) result = caseNamedElement(property);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.SIMPLE_ARRAY_VALUE:
			{
				SimpleArrayValue simpleArrayValue = (SimpleArrayValue)theEObject;
				T1 result = caseSimpleArrayValue(simpleArrayValue);
				if (result == null) result = caseValue(simpleArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.SIMPLE_TYPE:
			{
				SimpleType simpleType = (SimpleType)theEObject;
				T1 result = caseSimpleType(simpleType);
				if (result == null) result = caseValueType(simpleType);
				if (result == null) result = caseLanguageType(simpleType);
				if (result == null) result = caseType(simpleType);
				if (result == null) result = caseVisibilityElement(simpleType);
				if (result == null) result = caseNamedElement(simpleType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.SIMPLE_VALUE:
			{
				SimpleValue simpleValue = (SimpleValue)theEObject;
				T1 result = caseSimpleValue(simpleValue);
				if (result == null) result = caseValue(simpleValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.STRING:
			{
				org.eclipse.xsmp.smp.core.types.String string = (org.eclipse.xsmp.smp.core.types.String)theEObject;
				T1 result = caseString(string);
				if (result == null) result = caseSimpleType(string);
				if (result == null) result = caseValueType(string);
				if (result == null) result = caseLanguageType(string);
				if (result == null) result = caseType(string);
				if (result == null) result = caseVisibilityElement(string);
				if (result == null) result = caseNamedElement(string);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.STRING8_ARRAY_VALUE:
			{
				String8ArrayValue string8ArrayValue = (String8ArrayValue)theEObject;
				T1 result = caseString8ArrayValue(string8ArrayValue);
				if (result == null) result = caseSimpleArrayValue(string8ArrayValue);
				if (result == null) result = caseValue(string8ArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.STRING8_VALUE:
			{
				String8Value string8Value = (String8Value)theEObject;
				T1 result = caseString8Value(string8Value);
				if (result == null) result = caseSimpleValue(string8Value);
				if (result == null) result = caseValue(string8Value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.STRUCTURE:
			{
				Structure structure = (Structure)theEObject;
				T1 result = caseStructure(structure);
				if (result == null) result = caseValueType(structure);
				if (result == null) result = caseLanguageType(structure);
				if (result == null) result = caseType(structure);
				if (result == null) result = caseVisibilityElement(structure);
				if (result == null) result = caseNamedElement(structure);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.STRUCTURE_VALUE:
			{
				StructureValue structureValue = (StructureValue)theEObject;
				T1 result = caseStructureValue(structureValue);
				if (result == null) result = caseValue(structureValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.TYPE:
			{
				Type type = (Type)theEObject;
				T1 result = caseType(type);
				if (result == null) result = caseVisibilityElement(type);
				if (result == null) result = caseNamedElement(type);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.ELEMENT_REFERENCE:
			{
				ElementReference<?> elementReference = (ElementReference<?>)theEObject;
				T1 result = caseElementReference(elementReference);
				if (result == null) result = caseSimpleLinkRef(elementReference);
				if (result == null) result = caseSimpleLinkBase(elementReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.UINT16_ARRAY_VALUE:
			{
				UInt16ArrayValue uInt16ArrayValue = (UInt16ArrayValue)theEObject;
				T1 result = caseUInt16ArrayValue(uInt16ArrayValue);
				if (result == null) result = caseSimpleArrayValue(uInt16ArrayValue);
				if (result == null) result = caseValue(uInt16ArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.UINT16_VALUE:
			{
				UInt16Value uInt16Value = (UInt16Value)theEObject;
				T1 result = caseUInt16Value(uInt16Value);
				if (result == null) result = caseSimpleValue(uInt16Value);
				if (result == null) result = caseValue(uInt16Value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.UINT32_ARRAY_VALUE:
			{
				UInt32ArrayValue uInt32ArrayValue = (UInt32ArrayValue)theEObject;
				T1 result = caseUInt32ArrayValue(uInt32ArrayValue);
				if (result == null) result = caseSimpleArrayValue(uInt32ArrayValue);
				if (result == null) result = caseValue(uInt32ArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.UINT32_VALUE:
			{
				UInt32Value uInt32Value = (UInt32Value)theEObject;
				T1 result = caseUInt32Value(uInt32Value);
				if (result == null) result = caseSimpleValue(uInt32Value);
				if (result == null) result = caseValue(uInt32Value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.UINT64_ARRAY_VALUE:
			{
				UInt64ArrayValue uInt64ArrayValue = (UInt64ArrayValue)theEObject;
				T1 result = caseUInt64ArrayValue(uInt64ArrayValue);
				if (result == null) result = caseSimpleArrayValue(uInt64ArrayValue);
				if (result == null) result = caseValue(uInt64ArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.UINT64_VALUE:
			{
				UInt64Value uInt64Value = (UInt64Value)theEObject;
				T1 result = caseUInt64Value(uInt64Value);
				if (result == null) result = caseSimpleValue(uInt64Value);
				if (result == null) result = caseValue(uInt64Value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.UINT8_ARRAY_VALUE:
			{
				UInt8ArrayValue uInt8ArrayValue = (UInt8ArrayValue)theEObject;
				T1 result = caseUInt8ArrayValue(uInt8ArrayValue);
				if (result == null) result = caseSimpleArrayValue(uInt8ArrayValue);
				if (result == null) result = caseValue(uInt8ArrayValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.UINT8_VALUE:
			{
				UInt8Value uInt8Value = (UInt8Value)theEObject;
				T1 result = caseUInt8Value(uInt8Value);
				if (result == null) result = caseSimpleValue(uInt8Value);
				if (result == null) result = caseValue(uInt8Value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.VALUE:
			{
				Value value = (Value)theEObject;
				T1 result = caseValue(value);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.VALUE_REFERENCE:
			{
				ValueReference valueReference = (ValueReference)theEObject;
				T1 result = caseValueReference(valueReference);
				if (result == null) result = caseLanguageType(valueReference);
				if (result == null) result = caseType(valueReference);
				if (result == null) result = caseVisibilityElement(valueReference);
				if (result == null) result = caseNamedElement(valueReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.VALUE_TYPE:
			{
				ValueType valueType = (ValueType)theEObject;
				T1 result = caseValueType(valueType);
				if (result == null) result = caseLanguageType(valueType);
				if (result == null) result = caseType(valueType);
				if (result == null) result = caseVisibilityElement(valueType);
				if (result == null) result = caseNamedElement(valueType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TypesPackage.VISIBILITY_ELEMENT:
			{
				VisibilityElement visibilityElement = (VisibilityElement)theEObject;
				T1 result = caseVisibilityElement(visibilityElement);
				if (result == null) result = caseNamedElement(visibilityElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Array</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Array</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseArray(Array object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseArrayValue(ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Association</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Association</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAssociation(Association object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAttribute(Attribute object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseAttributeType(AttributeType object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bool Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bool Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseBoolArrayValue(BoolArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bool Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bool Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseBoolValue(BoolValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Char8 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Char8 Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseChar8ArrayValue(Char8ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Char8 Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Char8 Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseChar8Value(Char8Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseClass(org.eclipse.xsmp.smp.core.types.Class object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Constant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseConstant(Constant object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Time Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Time Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDateTimeArrayValue(DateTimeArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Date Time Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Date Time Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDateTimeValue(DateTimeValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Duration Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Duration Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDurationArrayValue(DurationArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Duration Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Duration Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDurationValue(DurationValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enumeration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enumeration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEnumeration(Enumeration object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enumeration Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enumeration Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEnumerationArrayValue(EnumerationArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enumeration Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enumeration Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEnumerationLiteral(EnumerationLiteral object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enumeration Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enumeration Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEnumerationValue(EnumerationValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Exception</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Exception</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseException(org.eclipse.xsmp.smp.core.types.Exception object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Field</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Field</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseField(Field object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Float</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Float</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFloat(org.eclipse.xsmp.smp.core.types.Float object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Float32 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Float32 Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFloat32ArrayValue(Float32ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Float32 Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Float32 Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFloat32Value(Float32Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Float64 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Float64 Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFloat64ArrayValue(Float64ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Float64 Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Float64 Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFloat64Value(Float64Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Int16 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Int16 Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseInt16ArrayValue(Int16ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Int16 Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Int16 Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseInt16Value(Int16Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Int32 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Int32 Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseInt32ArrayValue(Int32ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Int32 Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Int32 Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseInt32Value(Int32Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Int64 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Int64 Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseInt64ArrayValue(Int64ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Int64 Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Int64 Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseInt64Value(Int64Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Int8 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Int8 Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseInt8ArrayValue(Int8ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Int8 Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Int8 Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseInt8Value(Int8Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseInteger(org.eclipse.xsmp.smp.core.types.Integer object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Language Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Language Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseLanguageType(LanguageType object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Native Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Native Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseNativeType(NativeType object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseOperation(Operation object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseParameter(Parameter object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Platform Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Platform Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 casePlatformMapping(PlatformMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Primitive Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Primitive Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 casePrimitiveType(PrimitiveType object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseProperty(Property object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSimpleArrayValue(SimpleArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSimpleType(SimpleType object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSimpleValue(SimpleValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseString(org.eclipse.xsmp.smp.core.types.String object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String8 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String8 Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseString8ArrayValue(String8ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String8 Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String8 Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseString8Value(String8Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Structure</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Structure</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseStructure(Structure object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Structure Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Structure Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseStructureValue(StructureValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseType(Type object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Element Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends NamedElement> T1 caseElementReference(ElementReference<T> object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UInt16 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UInt16 Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseUInt16ArrayValue(UInt16ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UInt16 Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UInt16 Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseUInt16Value(UInt16Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UInt32 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UInt32 Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseUInt32ArrayValue(UInt32ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UInt32 Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UInt32 Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseUInt32Value(UInt32Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UInt64 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UInt64 Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseUInt64ArrayValue(UInt64ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UInt64 Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UInt64 Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseUInt64Value(UInt64Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UInt8 Array Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UInt8 Array Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseUInt8ArrayValue(UInt8ArrayValue object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>UInt8 Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>UInt8 Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseUInt8Value(UInt8Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseValue(Value object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseValueReference(ValueReference object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseValueType(ValueType object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Visibility Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Visibility Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseVisibilityElement(VisibilityElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseNamedElement(NamedElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metadata</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metadata</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseMetadata(Metadata object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Link Base</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Link Base</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSimpleLinkBase(SimpleLinkBase object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Link Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Link Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSimpleLinkRef(SimpleLinkRef object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T1 defaultCase(EObject object)
	{
		return null;
	}

} //TypesSwitch
