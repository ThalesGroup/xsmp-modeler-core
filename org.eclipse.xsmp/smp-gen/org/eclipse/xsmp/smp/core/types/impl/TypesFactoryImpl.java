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
package org.eclipse.xsmp.smp.core.types.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.xsmp.smp.core.elements.NamedElement;

import org.eclipse.xsmp.smp.core.types.AccessKind;
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
import org.eclipse.xsmp.smp.core.types.NativeType;
import org.eclipse.xsmp.smp.core.types.Operation;
import org.eclipse.xsmp.smp.core.types.Parameter;
import org.eclipse.xsmp.smp.core.types.ParameterDirectionKind;
import org.eclipse.xsmp.smp.core.types.PlatformMapping;
import org.eclipse.xsmp.smp.core.types.PrimitiveType;
import org.eclipse.xsmp.smp.core.types.Property;
import org.eclipse.xsmp.smp.core.types.String8ArrayValue;
import org.eclipse.xsmp.smp.core.types.String8Value;
import org.eclipse.xsmp.smp.core.types.Structure;
import org.eclipse.xsmp.smp.core.types.StructureValue;
import org.eclipse.xsmp.smp.core.types.TypesFactory;
import org.eclipse.xsmp.smp.core.types.TypesPackage;
import org.eclipse.xsmp.smp.core.types.UInt16ArrayValue;
import org.eclipse.xsmp.smp.core.types.UInt16Value;
import org.eclipse.xsmp.smp.core.types.UInt32ArrayValue;
import org.eclipse.xsmp.smp.core.types.UInt32Value;
import org.eclipse.xsmp.smp.core.types.UInt64ArrayValue;
import org.eclipse.xsmp.smp.core.types.UInt64Value;
import org.eclipse.xsmp.smp.core.types.UInt8ArrayValue;
import org.eclipse.xsmp.smp.core.types.UInt8Value;
import org.eclipse.xsmp.smp.core.types.ValueReference;
import org.eclipse.xsmp.smp.core.types.VisibilityKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypesFactoryImpl extends EFactoryImpl implements TypesFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TypesFactory init()
	{
		try
		{
			TypesFactory theTypesFactory = (TypesFactory)EPackage.Registry.INSTANCE.getEFactory(TypesPackage.eNS_URI);
			if (theTypesFactory != null)
			{
				return theTypesFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TypesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesFactoryImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass)
	{
		switch (eClass.getClassifierID())
		{
			case TypesPackage.ARRAY: return createArray();
			case TypesPackage.ARRAY_VALUE: return createArrayValue();
			case TypesPackage.ASSOCIATION: return createAssociation();
			case TypesPackage.ATTRIBUTE: return createAttribute();
			case TypesPackage.ATTRIBUTE_TYPE: return createAttributeType();
			case TypesPackage.BOOL_ARRAY_VALUE: return createBoolArrayValue();
			case TypesPackage.BOOL_VALUE: return createBoolValue();
			case TypesPackage.CHAR8_ARRAY_VALUE: return createChar8ArrayValue();
			case TypesPackage.CHAR8_VALUE: return createChar8Value();
			case TypesPackage.CLASS: return createClass();
			case TypesPackage.CONSTANT: return createConstant();
			case TypesPackage.DATE_TIME_ARRAY_VALUE: return createDateTimeArrayValue();
			case TypesPackage.DATE_TIME_VALUE: return createDateTimeValue();
			case TypesPackage.DURATION_ARRAY_VALUE: return createDurationArrayValue();
			case TypesPackage.DURATION_VALUE: return createDurationValue();
			case TypesPackage.ENUMERATION: return createEnumeration();
			case TypesPackage.ENUMERATION_ARRAY_VALUE: return createEnumerationArrayValue();
			case TypesPackage.ENUMERATION_LITERAL: return createEnumerationLiteral();
			case TypesPackage.ENUMERATION_VALUE: return createEnumerationValue();
			case TypesPackage.EXCEPTION: return createException();
			case TypesPackage.FIELD: return createField();
			case TypesPackage.FLOAT: return createFloat();
			case TypesPackage.FLOAT32_ARRAY_VALUE: return createFloat32ArrayValue();
			case TypesPackage.FLOAT32_VALUE: return createFloat32Value();
			case TypesPackage.FLOAT64_ARRAY_VALUE: return createFloat64ArrayValue();
			case TypesPackage.FLOAT64_VALUE: return createFloat64Value();
			case TypesPackage.INT16_ARRAY_VALUE: return createInt16ArrayValue();
			case TypesPackage.INT16_VALUE: return createInt16Value();
			case TypesPackage.INT32_ARRAY_VALUE: return createInt32ArrayValue();
			case TypesPackage.INT32_VALUE: return createInt32Value();
			case TypesPackage.INT64_ARRAY_VALUE: return createInt64ArrayValue();
			case TypesPackage.INT64_VALUE: return createInt64Value();
			case TypesPackage.INT8_ARRAY_VALUE: return createInt8ArrayValue();
			case TypesPackage.INT8_VALUE: return createInt8Value();
			case TypesPackage.INTEGER: return createInteger();
			case TypesPackage.NATIVE_TYPE: return createNativeType();
			case TypesPackage.OPERATION: return createOperation();
			case TypesPackage.PARAMETER: return createParameter();
			case TypesPackage.PLATFORM_MAPPING: return createPlatformMapping();
			case TypesPackage.PRIMITIVE_TYPE: return createPrimitiveType();
			case TypesPackage.PROPERTY: return createProperty();
			case TypesPackage.STRING: return createString();
			case TypesPackage.STRING8_ARRAY_VALUE: return createString8ArrayValue();
			case TypesPackage.STRING8_VALUE: return createString8Value();
			case TypesPackage.STRUCTURE: return createStructure();
			case TypesPackage.STRUCTURE_VALUE: return createStructureValue();
			case TypesPackage.ELEMENT_REFERENCE: return createElementReference();
			case TypesPackage.UINT16_ARRAY_VALUE: return createUInt16ArrayValue();
			case TypesPackage.UINT16_VALUE: return createUInt16Value();
			case TypesPackage.UINT32_ARRAY_VALUE: return createUInt32ArrayValue();
			case TypesPackage.UINT32_VALUE: return createUInt32Value();
			case TypesPackage.UINT64_ARRAY_VALUE: return createUInt64ArrayValue();
			case TypesPackage.UINT64_VALUE: return createUInt64Value();
			case TypesPackage.UINT8_ARRAY_VALUE: return createUInt8ArrayValue();
			case TypesPackage.UINT8_VALUE: return createUInt8Value();
			case TypesPackage.VALUE_REFERENCE: return createValueReference();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue)
	{
		switch (eDataType.getClassifierID())
		{
			case TypesPackage.ACCESS_KIND:
				return createAccessKindFromString(eDataType, initialValue);
			case TypesPackage.PARAMETER_DIRECTION_KIND:
				return createParameterDirectionKindFromString(eDataType, initialValue);
			case TypesPackage.VISIBILITY_KIND:
				return createVisibilityKindFromString(eDataType, initialValue);
			case TypesPackage.ACCESS_KIND_OBJECT:
				return createAccessKindObjectFromString(eDataType, initialValue);
			case TypesPackage.PARAMETER_DIRECTION_KIND_OBJECT:
				return createParameterDirectionKindObjectFromString(eDataType, initialValue);
			case TypesPackage.VISIBILITY_KIND_OBJECT:
				return createVisibilityKindObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue)
	{
		switch (eDataType.getClassifierID())
		{
			case TypesPackage.ACCESS_KIND:
				return convertAccessKindToString(eDataType, instanceValue);
			case TypesPackage.PARAMETER_DIRECTION_KIND:
				return convertParameterDirectionKindToString(eDataType, instanceValue);
			case TypesPackage.VISIBILITY_KIND:
				return convertVisibilityKindToString(eDataType, instanceValue);
			case TypesPackage.ACCESS_KIND_OBJECT:
				return convertAccessKindObjectToString(eDataType, instanceValue);
			case TypesPackage.PARAMETER_DIRECTION_KIND_OBJECT:
				return convertParameterDirectionKindObjectToString(eDataType, instanceValue);
			case TypesPackage.VISIBILITY_KIND_OBJECT:
				return convertVisibilityKindObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Array createArray()
	{
		ArrayImpl array = new ArrayImpl();
		return array;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ArrayValue createArrayValue()
	{
		ArrayValueImpl arrayValue = new ArrayValueImpl();
		return arrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Association createAssociation()
	{
		AssociationImpl association = new AssociationImpl();
		return association;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Attribute createAttribute()
	{
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AttributeType createAttributeType()
	{
		AttributeTypeImpl attributeType = new AttributeTypeImpl();
		return attributeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BoolArrayValue createBoolArrayValue()
	{
		BoolArrayValueImpl boolArrayValue = new BoolArrayValueImpl();
		return boolArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BoolValue createBoolValue()
	{
		BoolValueImpl boolValue = new BoolValueImpl();
		return boolValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Char8ArrayValue createChar8ArrayValue()
	{
		Char8ArrayValueImpl char8ArrayValue = new Char8ArrayValueImpl();
		return char8ArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Char8Value createChar8Value()
	{
		Char8ValueImpl char8Value = new Char8ValueImpl();
		return char8Value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.xsmp.smp.core.types.Class createClass()
	{
		ClassImpl class_ = new ClassImpl();
		return class_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Constant createConstant()
	{
		ConstantImpl constant = new ConstantImpl();
		return constant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DateTimeArrayValue createDateTimeArrayValue()
	{
		DateTimeArrayValueImpl dateTimeArrayValue = new DateTimeArrayValueImpl();
		return dateTimeArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DateTimeValue createDateTimeValue()
	{
		DateTimeValueImpl dateTimeValue = new DateTimeValueImpl();
		return dateTimeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DurationArrayValue createDurationArrayValue()
	{
		DurationArrayValueImpl durationArrayValue = new DurationArrayValueImpl();
		return durationArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DurationValue createDurationValue()
	{
		DurationValueImpl durationValue = new DurationValueImpl();
		return durationValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Enumeration createEnumeration()
	{
		EnumerationImpl enumeration = new EnumerationImpl();
		return enumeration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnumerationArrayValue createEnumerationArrayValue()
	{
		EnumerationArrayValueImpl enumerationArrayValue = new EnumerationArrayValueImpl();
		return enumerationArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnumerationLiteral createEnumerationLiteral()
	{
		EnumerationLiteralImpl enumerationLiteral = new EnumerationLiteralImpl();
		return enumerationLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnumerationValue createEnumerationValue()
	{
		EnumerationValueImpl enumerationValue = new EnumerationValueImpl();
		return enumerationValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.xsmp.smp.core.types.Exception createException()
	{
		ExceptionImpl exception = new ExceptionImpl();
		return exception;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Field createField()
	{
		FieldImpl field = new FieldImpl();
		return field;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.xsmp.smp.core.types.Float createFloat()
	{
		FloatImpl float_ = new FloatImpl();
		return float_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Float32ArrayValue createFloat32ArrayValue()
	{
		Float32ArrayValueImpl float32ArrayValue = new Float32ArrayValueImpl();
		return float32ArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Float32Value createFloat32Value()
	{
		Float32ValueImpl float32Value = new Float32ValueImpl();
		return float32Value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Float64ArrayValue createFloat64ArrayValue()
	{
		Float64ArrayValueImpl float64ArrayValue = new Float64ArrayValueImpl();
		return float64ArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Float64Value createFloat64Value()
	{
		Float64ValueImpl float64Value = new Float64ValueImpl();
		return float64Value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Int16ArrayValue createInt16ArrayValue()
	{
		Int16ArrayValueImpl int16ArrayValue = new Int16ArrayValueImpl();
		return int16ArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Int16Value createInt16Value()
	{
		Int16ValueImpl int16Value = new Int16ValueImpl();
		return int16Value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Int32ArrayValue createInt32ArrayValue()
	{
		Int32ArrayValueImpl int32ArrayValue = new Int32ArrayValueImpl();
		return int32ArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Int32Value createInt32Value()
	{
		Int32ValueImpl int32Value = new Int32ValueImpl();
		return int32Value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Int64ArrayValue createInt64ArrayValue()
	{
		Int64ArrayValueImpl int64ArrayValue = new Int64ArrayValueImpl();
		return int64ArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Int64Value createInt64Value()
	{
		Int64ValueImpl int64Value = new Int64ValueImpl();
		return int64Value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Int8ArrayValue createInt8ArrayValue()
	{
		Int8ArrayValueImpl int8ArrayValue = new Int8ArrayValueImpl();
		return int8ArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Int8Value createInt8Value()
	{
		Int8ValueImpl int8Value = new Int8ValueImpl();
		return int8Value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.xsmp.smp.core.types.Integer createInteger()
	{
		IntegerImpl integer = new IntegerImpl();
		return integer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NativeType createNativeType()
	{
		NativeTypeImpl nativeType = new NativeTypeImpl();
		return nativeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Operation createOperation()
	{
		OperationImpl operation = new OperationImpl();
		return operation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Parameter createParameter()
	{
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PlatformMapping createPlatformMapping()
	{
		PlatformMappingImpl platformMapping = new PlatformMappingImpl();
		return platformMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PrimitiveType createPrimitiveType()
	{
		PrimitiveTypeImpl primitiveType = new PrimitiveTypeImpl();
		return primitiveType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Property createProperty()
	{
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.xsmp.smp.core.types.String createString()
	{
		StringImpl string = new StringImpl();
		return string;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String8ArrayValue createString8ArrayValue()
	{
		String8ArrayValueImpl string8ArrayValue = new String8ArrayValueImpl();
		return string8ArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String8Value createString8Value()
	{
		String8ValueImpl string8Value = new String8ValueImpl();
		return string8Value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Structure createStructure()
	{
		StructureImpl structure = new StructureImpl();
		return structure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructureValue createStructureValue()
	{
		StructureValueImpl structureValue = new StructureValueImpl();
		return structureValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public <T extends NamedElement> ElementReference<T> createElementReference()
	{
		ElementReferenceImplCustom<T> elementReference = new ElementReferenceImplCustom<T>();
		return elementReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UInt16ArrayValue createUInt16ArrayValue()
	{
		UInt16ArrayValueImpl uInt16ArrayValue = new UInt16ArrayValueImpl();
		return uInt16ArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UInt16Value createUInt16Value()
	{
		UInt16ValueImpl uInt16Value = new UInt16ValueImpl();
		return uInt16Value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UInt32ArrayValue createUInt32ArrayValue()
	{
		UInt32ArrayValueImpl uInt32ArrayValue = new UInt32ArrayValueImpl();
		return uInt32ArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UInt32Value createUInt32Value()
	{
		UInt32ValueImpl uInt32Value = new UInt32ValueImpl();
		return uInt32Value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UInt64ArrayValue createUInt64ArrayValue()
	{
		UInt64ArrayValueImpl uInt64ArrayValue = new UInt64ArrayValueImpl();
		return uInt64ArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UInt64Value createUInt64Value()
	{
		UInt64ValueImpl uInt64Value = new UInt64ValueImpl();
		return uInt64Value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UInt8ArrayValue createUInt8ArrayValue()
	{
		UInt8ArrayValueImpl uInt8ArrayValue = new UInt8ArrayValueImpl();
		return uInt8ArrayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UInt8Value createUInt8Value()
	{
		UInt8ValueImpl uInt8Value = new UInt8ValueImpl();
		return uInt8Value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ValueReference createValueReference()
	{
		ValueReferenceImpl valueReference = new ValueReferenceImpl();
		return valueReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AccessKind createAccessKindFromString(EDataType eDataType, String initialValue)
	{
		AccessKind result = AccessKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAccessKindToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterDirectionKind createParameterDirectionKindFromString(EDataType eDataType, String initialValue)
	{
		ParameterDirectionKind result = ParameterDirectionKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertParameterDirectionKindToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VisibilityKind createVisibilityKindFromString(EDataType eDataType, String initialValue)
	{
		VisibilityKind result = VisibilityKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVisibilityKindToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AccessKind createAccessKindObjectFromString(EDataType eDataType, String initialValue)
	{
		return createAccessKindFromString(TypesPackage.Literals.ACCESS_KIND, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAccessKindObjectToString(EDataType eDataType, Object instanceValue)
	{
		return convertAccessKindToString(TypesPackage.Literals.ACCESS_KIND, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterDirectionKind createParameterDirectionKindObjectFromString(EDataType eDataType, String initialValue)
	{
		return createParameterDirectionKindFromString(TypesPackage.Literals.PARAMETER_DIRECTION_KIND, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertParameterDirectionKindObjectToString(EDataType eDataType, Object instanceValue)
	{
		return convertParameterDirectionKindToString(TypesPackage.Literals.PARAMETER_DIRECTION_KIND, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VisibilityKind createVisibilityKindObjectFromString(EDataType eDataType, String initialValue)
	{
		return createVisibilityKindFromString(TypesPackage.Literals.VISIBILITY_KIND, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVisibilityKindObjectToString(EDataType eDataType, Object instanceValue)
	{
		return convertVisibilityKindToString(TypesPackage.Literals.VISIBILITY_KIND, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypesPackage getTypesPackage()
	{
		return (TypesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TypesPackage getPackage()
	{
		return TypesPackage.eINSTANCE;
	}

} //TypesFactoryImpl
