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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage
 * @generated
 */
public class TypesAdapterFactory extends AdapterFactoryImpl
{
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TypesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesAdapterFactory()
	{
		if (modelPackage == null)
		{
			modelPackage = TypesPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object)
	{
		if (object == modelPackage)
		{
			return true;
		}
		if (object instanceof EObject)
		{
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypesSwitch<Adapter> modelSwitch =
		new TypesSwitch<Adapter>()
		{
			@Override
			public Adapter caseArray(Array object)
			{
				return createArrayAdapter();
			}
			@Override
			public Adapter caseArrayValue(ArrayValue object)
			{
				return createArrayValueAdapter();
			}
			@Override
			public Adapter caseAssociation(Association object)
			{
				return createAssociationAdapter();
			}
			@Override
			public Adapter caseAttribute(Attribute object)
			{
				return createAttributeAdapter();
			}
			@Override
			public Adapter caseAttributeType(AttributeType object)
			{
				return createAttributeTypeAdapter();
			}
			@Override
			public Adapter caseBoolArrayValue(BoolArrayValue object)
			{
				return createBoolArrayValueAdapter();
			}
			@Override
			public Adapter caseBoolValue(BoolValue object)
			{
				return createBoolValueAdapter();
			}
			@Override
			public Adapter caseChar8ArrayValue(Char8ArrayValue object)
			{
				return createChar8ArrayValueAdapter();
			}
			@Override
			public Adapter caseChar8Value(Char8Value object)
			{
				return createChar8ValueAdapter();
			}
			@Override
			public Adapter caseClass(org.eclipse.xsmp.smp.core.types.Class object)
			{
				return createClassAdapter();
			}
			@Override
			public Adapter caseConstant(Constant object)
			{
				return createConstantAdapter();
			}
			@Override
			public Adapter caseDateTimeArrayValue(DateTimeArrayValue object)
			{
				return createDateTimeArrayValueAdapter();
			}
			@Override
			public Adapter caseDateTimeValue(DateTimeValue object)
			{
				return createDateTimeValueAdapter();
			}
			@Override
			public Adapter caseDurationArrayValue(DurationArrayValue object)
			{
				return createDurationArrayValueAdapter();
			}
			@Override
			public Adapter caseDurationValue(DurationValue object)
			{
				return createDurationValueAdapter();
			}
			@Override
			public Adapter caseEnumeration(Enumeration object)
			{
				return createEnumerationAdapter();
			}
			@Override
			public Adapter caseEnumerationArrayValue(EnumerationArrayValue object)
			{
				return createEnumerationArrayValueAdapter();
			}
			@Override
			public Adapter caseEnumerationLiteral(EnumerationLiteral object)
			{
				return createEnumerationLiteralAdapter();
			}
			@Override
			public Adapter caseEnumerationValue(EnumerationValue object)
			{
				return createEnumerationValueAdapter();
			}
			@Override
			public Adapter caseException(org.eclipse.xsmp.smp.core.types.Exception object)
			{
				return createExceptionAdapter();
			}
			@Override
			public Adapter caseField(Field object)
			{
				return createFieldAdapter();
			}
			@Override
			public Adapter caseFloat(org.eclipse.xsmp.smp.core.types.Float object)
			{
				return createFloatAdapter();
			}
			@Override
			public Adapter caseFloat32ArrayValue(Float32ArrayValue object)
			{
				return createFloat32ArrayValueAdapter();
			}
			@Override
			public Adapter caseFloat32Value(Float32Value object)
			{
				return createFloat32ValueAdapter();
			}
			@Override
			public Adapter caseFloat64ArrayValue(Float64ArrayValue object)
			{
				return createFloat64ArrayValueAdapter();
			}
			@Override
			public Adapter caseFloat64Value(Float64Value object)
			{
				return createFloat64ValueAdapter();
			}
			@Override
			public Adapter caseInt16ArrayValue(Int16ArrayValue object)
			{
				return createInt16ArrayValueAdapter();
			}
			@Override
			public Adapter caseInt16Value(Int16Value object)
			{
				return createInt16ValueAdapter();
			}
			@Override
			public Adapter caseInt32ArrayValue(Int32ArrayValue object)
			{
				return createInt32ArrayValueAdapter();
			}
			@Override
			public Adapter caseInt32Value(Int32Value object)
			{
				return createInt32ValueAdapter();
			}
			@Override
			public Adapter caseInt64ArrayValue(Int64ArrayValue object)
			{
				return createInt64ArrayValueAdapter();
			}
			@Override
			public Adapter caseInt64Value(Int64Value object)
			{
				return createInt64ValueAdapter();
			}
			@Override
			public Adapter caseInt8ArrayValue(Int8ArrayValue object)
			{
				return createInt8ArrayValueAdapter();
			}
			@Override
			public Adapter caseInt8Value(Int8Value object)
			{
				return createInt8ValueAdapter();
			}
			@Override
			public Adapter caseInteger(org.eclipse.xsmp.smp.core.types.Integer object)
			{
				return createIntegerAdapter();
			}
			@Override
			public Adapter caseLanguageType(LanguageType object)
			{
				return createLanguageTypeAdapter();
			}
			@Override
			public Adapter caseNativeType(NativeType object)
			{
				return createNativeTypeAdapter();
			}
			@Override
			public Adapter caseOperation(Operation object)
			{
				return createOperationAdapter();
			}
			@Override
			public Adapter caseParameter(Parameter object)
			{
				return createParameterAdapter();
			}
			@Override
			public Adapter casePlatformMapping(PlatformMapping object)
			{
				return createPlatformMappingAdapter();
			}
			@Override
			public Adapter casePrimitiveType(PrimitiveType object)
			{
				return createPrimitiveTypeAdapter();
			}
			@Override
			public Adapter caseProperty(Property object)
			{
				return createPropertyAdapter();
			}
			@Override
			public Adapter caseSimpleArrayValue(SimpleArrayValue object)
			{
				return createSimpleArrayValueAdapter();
			}
			@Override
			public Adapter caseSimpleType(SimpleType object)
			{
				return createSimpleTypeAdapter();
			}
			@Override
			public Adapter caseSimpleValue(SimpleValue object)
			{
				return createSimpleValueAdapter();
			}
			@Override
			public Adapter caseString(org.eclipse.xsmp.smp.core.types.String object)
			{
				return createStringAdapter();
			}
			@Override
			public Adapter caseString8ArrayValue(String8ArrayValue object)
			{
				return createString8ArrayValueAdapter();
			}
			@Override
			public Adapter caseString8Value(String8Value object)
			{
				return createString8ValueAdapter();
			}
			@Override
			public Adapter caseStructure(Structure object)
			{
				return createStructureAdapter();
			}
			@Override
			public Adapter caseStructureValue(StructureValue object)
			{
				return createStructureValueAdapter();
			}
			@Override
			public Adapter caseType(Type object)
			{
				return createTypeAdapter();
			}
			@Override
			public <T extends NamedElement> Adapter caseElementReference(ElementReference<T> object)
			{
				return createElementReferenceAdapter();
			}
			@Override
			public Adapter caseUInt16ArrayValue(UInt16ArrayValue object)
			{
				return createUInt16ArrayValueAdapter();
			}
			@Override
			public Adapter caseUInt16Value(UInt16Value object)
			{
				return createUInt16ValueAdapter();
			}
			@Override
			public Adapter caseUInt32ArrayValue(UInt32ArrayValue object)
			{
				return createUInt32ArrayValueAdapter();
			}
			@Override
			public Adapter caseUInt32Value(UInt32Value object)
			{
				return createUInt32ValueAdapter();
			}
			@Override
			public Adapter caseUInt64ArrayValue(UInt64ArrayValue object)
			{
				return createUInt64ArrayValueAdapter();
			}
			@Override
			public Adapter caseUInt64Value(UInt64Value object)
			{
				return createUInt64ValueAdapter();
			}
			@Override
			public Adapter caseUInt8ArrayValue(UInt8ArrayValue object)
			{
				return createUInt8ArrayValueAdapter();
			}
			@Override
			public Adapter caseUInt8Value(UInt8Value object)
			{
				return createUInt8ValueAdapter();
			}
			@Override
			public Adapter caseValue(Value object)
			{
				return createValueAdapter();
			}
			@Override
			public Adapter caseValueReference(ValueReference object)
			{
				return createValueReferenceAdapter();
			}
			@Override
			public Adapter caseValueType(ValueType object)
			{
				return createValueTypeAdapter();
			}
			@Override
			public Adapter caseVisibilityElement(VisibilityElement object)
			{
				return createVisibilityElementAdapter();
			}
			@Override
			public Adapter caseNamedElement(NamedElement object)
			{
				return createNamedElementAdapter();
			}
			@Override
			public Adapter caseMetadata(Metadata object)
			{
				return createMetadataAdapter();
			}
			@Override
			public Adapter caseSimpleLinkBase(SimpleLinkBase object)
			{
				return createSimpleLinkBaseAdapter();
			}
			@Override
			public Adapter caseSimpleLinkRef(SimpleLinkRef object)
			{
				return createSimpleLinkRefAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object)
			{
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target)
	{
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Array <em>Array</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Array
	 * @generated
	 */
	public Adapter createArrayAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.ArrayValue <em>Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.ArrayValue
	 * @generated
	 */
	public Adapter createArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Association <em>Association</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Association
	 * @generated
	 */
	public Adapter createAssociationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Attribute
	 * @generated
	 */
	public Adapter createAttributeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.AttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.AttributeType
	 * @generated
	 */
	public Adapter createAttributeTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.BoolArrayValue <em>Bool Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.BoolArrayValue
	 * @generated
	 */
	public Adapter createBoolArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.BoolValue <em>Bool Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.BoolValue
	 * @generated
	 */
	public Adapter createBoolValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Char8ArrayValue <em>Char8 Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Char8ArrayValue
	 * @generated
	 */
	public Adapter createChar8ArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Char8Value <em>Char8 Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Char8Value
	 * @generated
	 */
	public Adapter createChar8ValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Class <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Class
	 * @generated
	 */
	public Adapter createClassAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Constant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Constant
	 * @generated
	 */
	public Adapter createConstantAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.DateTimeArrayValue <em>Date Time Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.DateTimeArrayValue
	 * @generated
	 */
	public Adapter createDateTimeArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.DateTimeValue <em>Date Time Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.DateTimeValue
	 * @generated
	 */
	public Adapter createDateTimeValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.DurationArrayValue <em>Duration Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.DurationArrayValue
	 * @generated
	 */
	public Adapter createDurationArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.DurationValue <em>Duration Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.DurationValue
	 * @generated
	 */
	public Adapter createDurationValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Enumeration <em>Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Enumeration
	 * @generated
	 */
	public Adapter createEnumerationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.EnumerationArrayValue <em>Enumeration Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.EnumerationArrayValue
	 * @generated
	 */
	public Adapter createEnumerationArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.EnumerationLiteral <em>Enumeration Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.EnumerationLiteral
	 * @generated
	 */
	public Adapter createEnumerationLiteralAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.EnumerationValue <em>Enumeration Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.EnumerationValue
	 * @generated
	 */
	public Adapter createEnumerationValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Exception <em>Exception</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Exception
	 * @generated
	 */
	public Adapter createExceptionAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Field <em>Field</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Field
	 * @generated
	 */
	public Adapter createFieldAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Float <em>Float</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Float
	 * @generated
	 */
	public Adapter createFloatAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Float32ArrayValue <em>Float32 Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Float32ArrayValue
	 * @generated
	 */
	public Adapter createFloat32ArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Float32Value <em>Float32 Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Float32Value
	 * @generated
	 */
	public Adapter createFloat32ValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Float64ArrayValue <em>Float64 Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Float64ArrayValue
	 * @generated
	 */
	public Adapter createFloat64ArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Float64Value <em>Float64 Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Float64Value
	 * @generated
	 */
	public Adapter createFloat64ValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Int16ArrayValue <em>Int16 Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Int16ArrayValue
	 * @generated
	 */
	public Adapter createInt16ArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Int16Value <em>Int16 Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Int16Value
	 * @generated
	 */
	public Adapter createInt16ValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Int32ArrayValue <em>Int32 Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Int32ArrayValue
	 * @generated
	 */
	public Adapter createInt32ArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Int32Value <em>Int32 Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Int32Value
	 * @generated
	 */
	public Adapter createInt32ValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Int64ArrayValue <em>Int64 Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Int64ArrayValue
	 * @generated
	 */
	public Adapter createInt64ArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Int64Value <em>Int64 Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Int64Value
	 * @generated
	 */
	public Adapter createInt64ValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Int8ArrayValue <em>Int8 Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Int8ArrayValue
	 * @generated
	 */
	public Adapter createInt8ArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Int8Value <em>Int8 Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Int8Value
	 * @generated
	 */
	public Adapter createInt8ValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Integer <em>Integer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Integer
	 * @generated
	 */
	public Adapter createIntegerAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.LanguageType <em>Language Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.LanguageType
	 * @generated
	 */
	public Adapter createLanguageTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.NativeType <em>Native Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.NativeType
	 * @generated
	 */
	public Adapter createNativeTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Operation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Operation
	 * @generated
	 */
	public Adapter createOperationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Parameter
	 * @generated
	 */
	public Adapter createParameterAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.PlatformMapping <em>Platform Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.PlatformMapping
	 * @generated
	 */
	public Adapter createPlatformMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.PrimitiveType <em>Primitive Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.PrimitiveType
	 * @generated
	 */
	public Adapter createPrimitiveTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Property
	 * @generated
	 */
	public Adapter createPropertyAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.SimpleArrayValue <em>Simple Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.SimpleArrayValue
	 * @generated
	 */
	public Adapter createSimpleArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.SimpleType <em>Simple Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.SimpleType
	 * @generated
	 */
	public Adapter createSimpleTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.SimpleValue <em>Simple Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.SimpleValue
	 * @generated
	 */
	public Adapter createSimpleValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.String <em>String</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.String
	 * @generated
	 */
	public Adapter createStringAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.String8ArrayValue <em>String8 Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.String8ArrayValue
	 * @generated
	 */
	public Adapter createString8ArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.String8Value <em>String8 Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.String8Value
	 * @generated
	 */
	public Adapter createString8ValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Structure <em>Structure</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Structure
	 * @generated
	 */
	public Adapter createStructureAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.StructureValue <em>Structure Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.StructureValue
	 * @generated
	 */
	public Adapter createStructureValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Type
	 * @generated
	 */
	public Adapter createTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.ElementReference <em>Element Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.ElementReference
	 * @generated
	 */
	public Adapter createElementReferenceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.UInt16ArrayValue <em>UInt16 Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.UInt16ArrayValue
	 * @generated
	 */
	public Adapter createUInt16ArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.UInt16Value <em>UInt16 Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.UInt16Value
	 * @generated
	 */
	public Adapter createUInt16ValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.UInt32ArrayValue <em>UInt32 Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.UInt32ArrayValue
	 * @generated
	 */
	public Adapter createUInt32ArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.UInt32Value <em>UInt32 Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.UInt32Value
	 * @generated
	 */
	public Adapter createUInt32ValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.UInt64ArrayValue <em>UInt64 Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.UInt64ArrayValue
	 * @generated
	 */
	public Adapter createUInt64ArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.UInt64Value <em>UInt64 Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.UInt64Value
	 * @generated
	 */
	public Adapter createUInt64ValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.UInt8ArrayValue <em>UInt8 Array Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.UInt8ArrayValue
	 * @generated
	 */
	public Adapter createUInt8ArrayValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.UInt8Value <em>UInt8 Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.UInt8Value
	 * @generated
	 */
	public Adapter createUInt8ValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.Value
	 * @generated
	 */
	public Adapter createValueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.ValueReference <em>Value Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.ValueReference
	 * @generated
	 */
	public Adapter createValueReferenceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.ValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.ValueType
	 * @generated
	 */
	public Adapter createValueTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.types.VisibilityElement <em>Visibility Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.types.VisibilityElement
	 * @generated
	 */
	public Adapter createVisibilityElementAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.elements.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.elements.NamedElement
	 * @generated
	 */
	public Adapter createNamedElementAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.elements.Metadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.elements.Metadata
	 * @generated
	 */
	public Adapter createMetadataAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.xlink.SimpleLinkBase <em>Simple Link Base</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.xlink.SimpleLinkBase
	 * @generated
	 */
	public Adapter createSimpleLinkBaseAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.xlink.SimpleLinkRef <em>Simple Link Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.xlink.SimpleLinkRef
	 * @generated
	 */
	public Adapter createSimpleLinkRefAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter()
	{
		return null;
	}

} //TypesAdapterFactory
