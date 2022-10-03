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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.xsmp.smp.core.elements.ElementsPackage;

import org.eclipse.xsmp.smp.core.elements.impl.ElementsPackageImpl;

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
import org.eclipse.xsmp.smp.core.types.LanguageType;
import org.eclipse.xsmp.smp.core.types.NativeType;
import org.eclipse.xsmp.smp.core.types.Operation;
import org.eclipse.xsmp.smp.core.types.Parameter;
import org.eclipse.xsmp.smp.core.types.ParameterDirectionKind;
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
import org.eclipse.xsmp.smp.core.types.Value;
import org.eclipse.xsmp.smp.core.types.ValueReference;
import org.eclipse.xsmp.smp.core.types.ValueType;
import org.eclipse.xsmp.smp.core.types.VisibilityElement;
import org.eclipse.xsmp.smp.core.types.VisibilityKind;

import org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage;

import org.eclipse.xsmp.smp.smdl.catalogue.impl.CataloguePackageImpl;

import org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage;

import org.eclipse.xsmp.smp.smdl.configuration.impl.ConfigurationPackageImpl;

import org.eclipse.xsmp.smp.smdl.package_.PackagePackage;

import org.eclipse.xsmp.smp.smdl.package_.impl.PackagePackageImpl;

import org.eclipse.xsmp.smp.xlink.XlinkPackage;

import org.eclipse.xsmp.smp.xlink.impl.XlinkPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypesPackageImpl extends EPackageImpl implements TypesPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass boolArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass boolValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass char8ArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass char8ValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateTimeArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateTimeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass durationArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass durationValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumerationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumerationArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumerationLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumerationValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exceptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass floatEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass float32ArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass float32ValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass float64ArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass float64ValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass int16ArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass int16ValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass int32ArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass int32ValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass int64ArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass int64ValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass int8ArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass int8ValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass languageTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nativeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass platformMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass primitiveTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass string8ArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass string8ValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structureValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uInt16ArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uInt16ValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uInt32ArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uInt32ValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uInt64ArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uInt64ValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uInt8ArrayValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uInt8ValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass visibilityElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum accessKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum parameterDirectionKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum visibilityKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType accessKindObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType parameterDirectionKindObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType visibilityKindObjectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TypesPackageImpl()
	{
		super(eNS_URI, TypesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link TypesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TypesPackage init()
	{
		if (isInited) return (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTypesPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		TypesPackageImpl theTypesPackage = registeredTypesPackage instanceof TypesPackageImpl ? (TypesPackageImpl)registeredTypesPackage : new TypesPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ElementsPackage.eNS_URI);
		ElementsPackageImpl theElementsPackage = (ElementsPackageImpl)(registeredPackage instanceof ElementsPackageImpl ? registeredPackage : ElementsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(CataloguePackage.eNS_URI);
		CataloguePackageImpl theCataloguePackage = (CataloguePackageImpl)(registeredPackage instanceof CataloguePackageImpl ? registeredPackage : CataloguePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ConfigurationPackage.eNS_URI);
		ConfigurationPackageImpl theConfigurationPackage = (ConfigurationPackageImpl)(registeredPackage instanceof ConfigurationPackageImpl ? registeredPackage : ConfigurationPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PackagePackage.eNS_URI);
		PackagePackageImpl thePackagePackage = (PackagePackageImpl)(registeredPackage instanceof PackagePackageImpl ? registeredPackage : PackagePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(XlinkPackage.eNS_URI);
		XlinkPackageImpl theXlinkPackage = (XlinkPackageImpl)(registeredPackage instanceof XlinkPackageImpl ? registeredPackage : XlinkPackage.eINSTANCE);

		// Create package meta-data objects
		theTypesPackage.createPackageContents();
		theElementsPackage.createPackageContents();
		theCataloguePackage.createPackageContents();
		theConfigurationPackage.createPackageContents();
		thePackagePackage.createPackageContents();
		theXlinkPackage.createPackageContents();

		// Initialize created meta-data
		theTypesPackage.initializePackageContents();
		theElementsPackage.initializePackageContents();
		theCataloguePackage.initializePackageContents();
		theConfigurationPackage.initializePackageContents();
		thePackagePackage.initializePackageContents();
		theXlinkPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTypesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TypesPackage.eNS_URI, theTypesPackage);
		return theTypesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArray()
	{
		return arrayEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArray_ItemType()
	{
		return (EReference)arrayEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getArray_Size()
	{
		return (EAttribute)arrayEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getArrayValue()
	{
		return arrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getArrayValue_ItemValue()
	{
		return (EReference)arrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAssociation()
	{
		return associationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAssociation_Type()
	{
		return (EReference)associationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAttribute()
	{
		return attributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAttribute_Type()
	{
		return (EReference)attributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAttribute_Value()
	{
		return (EReference)attributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAttributeType()
	{
		return attributeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAttributeType_Type()
	{
		return (EReference)attributeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAttributeType_Default()
	{
		return (EReference)attributeTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttributeType_Usage()
	{
		return (EAttribute)attributeTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttributeType_AllowMultiple()
	{
		return (EAttribute)attributeTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBoolArrayValue()
	{
		return boolArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBoolArrayValue_ItemValue()
	{
		return (EReference)boolArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBoolValue()
	{
		return boolValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBoolValue_Value()
	{
		return (EAttribute)boolValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getChar8ArrayValue()
	{
		return char8ArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChar8ArrayValue_ItemValue()
	{
		return (EReference)char8ArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getChar8Value()
	{
		return char8ValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChar8Value_Value()
	{
		return (EAttribute)char8ValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getClass_()
	{
		return classEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getClass_Base()
	{
		return (EReference)classEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getClass_Property()
	{
		return (EReference)classEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getClass_Operation()
	{
		return (EReference)classEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getClass_Association()
	{
		return (EReference)classEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getClass_Abstract()
	{
		return (EAttribute)classEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConstant()
	{
		return constantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConstant_Type()
	{
		return (EReference)constantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConstant_Value()
	{
		return (EReference)constantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDateTimeArrayValue()
	{
		return dateTimeArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDateTimeArrayValue_ItemValue()
	{
		return (EReference)dateTimeArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDateTimeValue()
	{
		return dateTimeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDateTimeValue_Value()
	{
		return (EAttribute)dateTimeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDurationArrayValue()
	{
		return durationArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDurationArrayValue_ItemValue()
	{
		return (EReference)durationArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDurationValue()
	{
		return durationValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDurationValue_Value()
	{
		return (EAttribute)durationValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEnumeration()
	{
		return enumerationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEnumeration_Literal()
	{
		return (EReference)enumerationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEnumerationArrayValue()
	{
		return enumerationArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEnumerationArrayValue_ItemValue()
	{
		return (EReference)enumerationArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEnumerationLiteral()
	{
		return enumerationLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEnumerationLiteral_Value()
	{
		return (EAttribute)enumerationLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEnumerationValue()
	{
		return enumerationValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEnumerationValue_Value()
	{
		return (EAttribute)enumerationValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEnumerationValue_Literal()
	{
		return (EAttribute)enumerationValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getException()
	{
		return exceptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getField()
	{
		return fieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getField_Type()
	{
		return (EReference)fieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getField_Default()
	{
		return (EReference)fieldEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getField_State()
	{
		return (EAttribute)fieldEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getField_Input()
	{
		return (EAttribute)fieldEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getField_Output()
	{
		return (EAttribute)fieldEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFloat()
	{
		return floatEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFloat_PrimitiveType()
	{
		return (EReference)floatEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFloat_Maximum()
	{
		return (EAttribute)floatEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFloat_MinInclusive()
	{
		return (EAttribute)floatEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFloat_Minimum()
	{
		return (EAttribute)floatEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFloat_MaxInclusive()
	{
		return (EAttribute)floatEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFloat_Unit()
	{
		return (EAttribute)floatEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFloat32ArrayValue()
	{
		return float32ArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFloat32ArrayValue_ItemValue()
	{
		return (EReference)float32ArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFloat32Value()
	{
		return float32ValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFloat32Value_Value()
	{
		return (EAttribute)float32ValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFloat64ArrayValue()
	{
		return float64ArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFloat64ArrayValue_ItemValue()
	{
		return (EReference)float64ArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFloat64Value()
	{
		return float64ValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFloat64Value_Value()
	{
		return (EAttribute)float64ValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInt16ArrayValue()
	{
		return int16ArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInt16ArrayValue_ItemValue()
	{
		return (EReference)int16ArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInt16Value()
	{
		return int16ValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInt16Value_Value()
	{
		return (EAttribute)int16ValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInt32ArrayValue()
	{
		return int32ArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInt32ArrayValue_ItemValue()
	{
		return (EReference)int32ArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInt32Value()
	{
		return int32ValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInt32Value_Value()
	{
		return (EAttribute)int32ValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInt64ArrayValue()
	{
		return int64ArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInt64ArrayValue_ItemValue()
	{
		return (EReference)int64ArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInt64Value()
	{
		return int64ValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInt64Value_Value()
	{
		return (EAttribute)int64ValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInt8ArrayValue()
	{
		return int8ArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInt8ArrayValue_ItemValue()
	{
		return (EReference)int8ArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInt8Value()
	{
		return int8ValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInt8Value_Value()
	{
		return (EAttribute)int8ValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInteger()
	{
		return integerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInteger_PrimitiveType()
	{
		return (EReference)integerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteger_Minimum()
	{
		return (EAttribute)integerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteger_Maximum()
	{
		return (EAttribute)integerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInteger_Unit()
	{
		return (EAttribute)integerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLanguageType()
	{
		return languageTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNativeType()
	{
		return nativeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNativeType_Platform()
	{
		return (EReference)nativeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOperation()
	{
		return operationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getOperation_Parameter()
	{
		return (EReference)operationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getOperation_RaisedException()
	{
		return (EReference)operationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getParameter()
	{
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getParameter_Type()
	{
		return (EReference)parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getParameter_Default()
	{
		return (EReference)parameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getParameter_Direction()
	{
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPlatformMapping()
	{
		return platformMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPlatformMapping_Name()
	{
		return (EAttribute)platformMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPlatformMapping_Type()
	{
		return (EAttribute)platformMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPlatformMapping_Namespace()
	{
		return (EAttribute)platformMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPlatformMapping_Location()
	{
		return (EAttribute)platformMappingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPrimitiveType()
	{
		return primitiveTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getProperty()
	{
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProperty_Type()
	{
		return (EReference)propertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProperty_AttachedField()
	{
		return (EReference)propertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProperty_GetRaises()
	{
		return (EReference)propertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProperty_SetRaises()
	{
		return (EReference)propertyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProperty_Access()
	{
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProperty_Category()
	{
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSimpleArrayValue()
	{
		return simpleArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSimpleType()
	{
		return simpleTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSimpleValue()
	{
		return simpleValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getString()
	{
		return stringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getString_Length()
	{
		return (EAttribute)stringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getString8ArrayValue()
	{
		return string8ArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getString8ArrayValue_ItemValue()
	{
		return (EReference)string8ArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getString8Value()
	{
		return string8ValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getString8Value_Value()
	{
		return (EAttribute)string8ValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStructure()
	{
		return structureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructure_Constant()
	{
		return (EReference)structureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructure_Field()
	{
		return (EReference)structureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStructureValue()
	{
		return structureValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructureValue_FieldValue()
	{
		return (EReference)structureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getType()
	{
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getType_Uuid()
	{
		return (EAttribute)typeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getElementReference()
	{
		return elementReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getElementReference_Ref()
	{
		return (EReference)elementReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUInt16ArrayValue()
	{
		return uInt16ArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUInt16ArrayValue_ItemValue()
	{
		return (EReference)uInt16ArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUInt16Value()
	{
		return uInt16ValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUInt16Value_Value()
	{
		return (EAttribute)uInt16ValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUInt32ArrayValue()
	{
		return uInt32ArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUInt32ArrayValue_ItemValue()
	{
		return (EReference)uInt32ArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUInt32Value()
	{
		return uInt32ValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUInt32Value_Value()
	{
		return (EAttribute)uInt32ValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUInt64ArrayValue()
	{
		return uInt64ArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUInt64ArrayValue_ItemValue()
	{
		return (EReference)uInt64ArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUInt64Value()
	{
		return uInt64ValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUInt64Value_Value()
	{
		return (EAttribute)uInt64ValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUInt8ArrayValue()
	{
		return uInt8ArrayValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUInt8ArrayValue_ItemValue()
	{
		return (EReference)uInt8ArrayValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUInt8Value()
	{
		return uInt8ValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUInt8Value_Value()
	{
		return (EAttribute)uInt8ValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValue()
	{
		return valueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValue_Field()
	{
		return (EAttribute)valueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValueReference()
	{
		return valueReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getValueReference_Type()
	{
		return (EReference)valueReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValueType()
	{
		return valueTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVisibilityElement()
	{
		return visibilityElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVisibilityElement_Visibility()
	{
		return (EAttribute)visibilityElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getAccessKind()
	{
		return accessKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getParameterDirectionKind()
	{
		return parameterDirectionKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getVisibilityKind()
	{
		return visibilityKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getAccessKindObject()
	{
		return accessKindObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getParameterDirectionKindObject()
	{
		return parameterDirectionKindObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getVisibilityKindObject()
	{
		return visibilityKindObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypesFactory getTypesFactory()
	{
		return (TypesFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents()
	{
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		arrayEClass = createEClass(ARRAY);
		createEReference(arrayEClass, ARRAY__ITEM_TYPE);
		createEAttribute(arrayEClass, ARRAY__SIZE);

		arrayValueEClass = createEClass(ARRAY_VALUE);
		createEReference(arrayValueEClass, ARRAY_VALUE__ITEM_VALUE);

		associationEClass = createEClass(ASSOCIATION);
		createEReference(associationEClass, ASSOCIATION__TYPE);

		attributeEClass = createEClass(ATTRIBUTE);
		createEReference(attributeEClass, ATTRIBUTE__TYPE);
		createEReference(attributeEClass, ATTRIBUTE__VALUE);

		attributeTypeEClass = createEClass(ATTRIBUTE_TYPE);
		createEReference(attributeTypeEClass, ATTRIBUTE_TYPE__TYPE);
		createEReference(attributeTypeEClass, ATTRIBUTE_TYPE__DEFAULT);
		createEAttribute(attributeTypeEClass, ATTRIBUTE_TYPE__USAGE);
		createEAttribute(attributeTypeEClass, ATTRIBUTE_TYPE__ALLOW_MULTIPLE);

		boolArrayValueEClass = createEClass(BOOL_ARRAY_VALUE);
		createEReference(boolArrayValueEClass, BOOL_ARRAY_VALUE__ITEM_VALUE);

		boolValueEClass = createEClass(BOOL_VALUE);
		createEAttribute(boolValueEClass, BOOL_VALUE__VALUE);

		char8ArrayValueEClass = createEClass(CHAR8_ARRAY_VALUE);
		createEReference(char8ArrayValueEClass, CHAR8_ARRAY_VALUE__ITEM_VALUE);

		char8ValueEClass = createEClass(CHAR8_VALUE);
		createEAttribute(char8ValueEClass, CHAR8_VALUE__VALUE);

		classEClass = createEClass(CLASS);
		createEReference(classEClass, CLASS__BASE);
		createEReference(classEClass, CLASS__PROPERTY);
		createEReference(classEClass, CLASS__OPERATION);
		createEReference(classEClass, CLASS__ASSOCIATION);
		createEAttribute(classEClass, CLASS__ABSTRACT);

		constantEClass = createEClass(CONSTANT);
		createEReference(constantEClass, CONSTANT__TYPE);
		createEReference(constantEClass, CONSTANT__VALUE);

		dateTimeArrayValueEClass = createEClass(DATE_TIME_ARRAY_VALUE);
		createEReference(dateTimeArrayValueEClass, DATE_TIME_ARRAY_VALUE__ITEM_VALUE);

		dateTimeValueEClass = createEClass(DATE_TIME_VALUE);
		createEAttribute(dateTimeValueEClass, DATE_TIME_VALUE__VALUE);

		durationArrayValueEClass = createEClass(DURATION_ARRAY_VALUE);
		createEReference(durationArrayValueEClass, DURATION_ARRAY_VALUE__ITEM_VALUE);

		durationValueEClass = createEClass(DURATION_VALUE);
		createEAttribute(durationValueEClass, DURATION_VALUE__VALUE);

		enumerationEClass = createEClass(ENUMERATION);
		createEReference(enumerationEClass, ENUMERATION__LITERAL);

		enumerationArrayValueEClass = createEClass(ENUMERATION_ARRAY_VALUE);
		createEReference(enumerationArrayValueEClass, ENUMERATION_ARRAY_VALUE__ITEM_VALUE);

		enumerationLiteralEClass = createEClass(ENUMERATION_LITERAL);
		createEAttribute(enumerationLiteralEClass, ENUMERATION_LITERAL__VALUE);

		enumerationValueEClass = createEClass(ENUMERATION_VALUE);
		createEAttribute(enumerationValueEClass, ENUMERATION_VALUE__VALUE);
		createEAttribute(enumerationValueEClass, ENUMERATION_VALUE__LITERAL);

		exceptionEClass = createEClass(EXCEPTION);

		fieldEClass = createEClass(FIELD);
		createEReference(fieldEClass, FIELD__TYPE);
		createEReference(fieldEClass, FIELD__DEFAULT);
		createEAttribute(fieldEClass, FIELD__STATE);
		createEAttribute(fieldEClass, FIELD__INPUT);
		createEAttribute(fieldEClass, FIELD__OUTPUT);

		floatEClass = createEClass(FLOAT);
		createEReference(floatEClass, FLOAT__PRIMITIVE_TYPE);
		createEAttribute(floatEClass, FLOAT__MAXIMUM);
		createEAttribute(floatEClass, FLOAT__MIN_INCLUSIVE);
		createEAttribute(floatEClass, FLOAT__MINIMUM);
		createEAttribute(floatEClass, FLOAT__MAX_INCLUSIVE);
		createEAttribute(floatEClass, FLOAT__UNIT);

		float32ArrayValueEClass = createEClass(FLOAT32_ARRAY_VALUE);
		createEReference(float32ArrayValueEClass, FLOAT32_ARRAY_VALUE__ITEM_VALUE);

		float32ValueEClass = createEClass(FLOAT32_VALUE);
		createEAttribute(float32ValueEClass, FLOAT32_VALUE__VALUE);

		float64ArrayValueEClass = createEClass(FLOAT64_ARRAY_VALUE);
		createEReference(float64ArrayValueEClass, FLOAT64_ARRAY_VALUE__ITEM_VALUE);

		float64ValueEClass = createEClass(FLOAT64_VALUE);
		createEAttribute(float64ValueEClass, FLOAT64_VALUE__VALUE);

		int16ArrayValueEClass = createEClass(INT16_ARRAY_VALUE);
		createEReference(int16ArrayValueEClass, INT16_ARRAY_VALUE__ITEM_VALUE);

		int16ValueEClass = createEClass(INT16_VALUE);
		createEAttribute(int16ValueEClass, INT16_VALUE__VALUE);

		int32ArrayValueEClass = createEClass(INT32_ARRAY_VALUE);
		createEReference(int32ArrayValueEClass, INT32_ARRAY_VALUE__ITEM_VALUE);

		int32ValueEClass = createEClass(INT32_VALUE);
		createEAttribute(int32ValueEClass, INT32_VALUE__VALUE);

		int64ArrayValueEClass = createEClass(INT64_ARRAY_VALUE);
		createEReference(int64ArrayValueEClass, INT64_ARRAY_VALUE__ITEM_VALUE);

		int64ValueEClass = createEClass(INT64_VALUE);
		createEAttribute(int64ValueEClass, INT64_VALUE__VALUE);

		int8ArrayValueEClass = createEClass(INT8_ARRAY_VALUE);
		createEReference(int8ArrayValueEClass, INT8_ARRAY_VALUE__ITEM_VALUE);

		int8ValueEClass = createEClass(INT8_VALUE);
		createEAttribute(int8ValueEClass, INT8_VALUE__VALUE);

		integerEClass = createEClass(INTEGER);
		createEReference(integerEClass, INTEGER__PRIMITIVE_TYPE);
		createEAttribute(integerEClass, INTEGER__MINIMUM);
		createEAttribute(integerEClass, INTEGER__MAXIMUM);
		createEAttribute(integerEClass, INTEGER__UNIT);

		languageTypeEClass = createEClass(LANGUAGE_TYPE);

		nativeTypeEClass = createEClass(NATIVE_TYPE);
		createEReference(nativeTypeEClass, NATIVE_TYPE__PLATFORM);

		operationEClass = createEClass(OPERATION);
		createEReference(operationEClass, OPERATION__PARAMETER);
		createEReference(operationEClass, OPERATION__RAISED_EXCEPTION);

		parameterEClass = createEClass(PARAMETER);
		createEReference(parameterEClass, PARAMETER__TYPE);
		createEReference(parameterEClass, PARAMETER__DEFAULT);
		createEAttribute(parameterEClass, PARAMETER__DIRECTION);

		platformMappingEClass = createEClass(PLATFORM_MAPPING);
		createEAttribute(platformMappingEClass, PLATFORM_MAPPING__NAME);
		createEAttribute(platformMappingEClass, PLATFORM_MAPPING__TYPE);
		createEAttribute(platformMappingEClass, PLATFORM_MAPPING__NAMESPACE);
		createEAttribute(platformMappingEClass, PLATFORM_MAPPING__LOCATION);

		primitiveTypeEClass = createEClass(PRIMITIVE_TYPE);

		propertyEClass = createEClass(PROPERTY);
		createEReference(propertyEClass, PROPERTY__TYPE);
		createEReference(propertyEClass, PROPERTY__ATTACHED_FIELD);
		createEReference(propertyEClass, PROPERTY__GET_RAISES);
		createEReference(propertyEClass, PROPERTY__SET_RAISES);
		createEAttribute(propertyEClass, PROPERTY__ACCESS);
		createEAttribute(propertyEClass, PROPERTY__CATEGORY);

		simpleArrayValueEClass = createEClass(SIMPLE_ARRAY_VALUE);

		simpleTypeEClass = createEClass(SIMPLE_TYPE);

		simpleValueEClass = createEClass(SIMPLE_VALUE);

		stringEClass = createEClass(STRING);
		createEAttribute(stringEClass, STRING__LENGTH);

		string8ArrayValueEClass = createEClass(STRING8_ARRAY_VALUE);
		createEReference(string8ArrayValueEClass, STRING8_ARRAY_VALUE__ITEM_VALUE);

		string8ValueEClass = createEClass(STRING8_VALUE);
		createEAttribute(string8ValueEClass, STRING8_VALUE__VALUE);

		structureEClass = createEClass(STRUCTURE);
		createEReference(structureEClass, STRUCTURE__CONSTANT);
		createEReference(structureEClass, STRUCTURE__FIELD);

		structureValueEClass = createEClass(STRUCTURE_VALUE);
		createEReference(structureValueEClass, STRUCTURE_VALUE__FIELD_VALUE);

		typeEClass = createEClass(TYPE);
		createEAttribute(typeEClass, TYPE__UUID);

		elementReferenceEClass = createEClass(ELEMENT_REFERENCE);
		createEReference(elementReferenceEClass, ELEMENT_REFERENCE__REF);

		uInt16ArrayValueEClass = createEClass(UINT16_ARRAY_VALUE);
		createEReference(uInt16ArrayValueEClass, UINT16_ARRAY_VALUE__ITEM_VALUE);

		uInt16ValueEClass = createEClass(UINT16_VALUE);
		createEAttribute(uInt16ValueEClass, UINT16_VALUE__VALUE);

		uInt32ArrayValueEClass = createEClass(UINT32_ARRAY_VALUE);
		createEReference(uInt32ArrayValueEClass, UINT32_ARRAY_VALUE__ITEM_VALUE);

		uInt32ValueEClass = createEClass(UINT32_VALUE);
		createEAttribute(uInt32ValueEClass, UINT32_VALUE__VALUE);

		uInt64ArrayValueEClass = createEClass(UINT64_ARRAY_VALUE);
		createEReference(uInt64ArrayValueEClass, UINT64_ARRAY_VALUE__ITEM_VALUE);

		uInt64ValueEClass = createEClass(UINT64_VALUE);
		createEAttribute(uInt64ValueEClass, UINT64_VALUE__VALUE);

		uInt8ArrayValueEClass = createEClass(UINT8_ARRAY_VALUE);
		createEReference(uInt8ArrayValueEClass, UINT8_ARRAY_VALUE__ITEM_VALUE);

		uInt8ValueEClass = createEClass(UINT8_VALUE);
		createEAttribute(uInt8ValueEClass, UINT8_VALUE__VALUE);

		valueEClass = createEClass(VALUE);
		createEAttribute(valueEClass, VALUE__FIELD);

		valueReferenceEClass = createEClass(VALUE_REFERENCE);
		createEReference(valueReferenceEClass, VALUE_REFERENCE__TYPE);

		valueTypeEClass = createEClass(VALUE_TYPE);

		visibilityElementEClass = createEClass(VISIBILITY_ELEMENT);
		createEAttribute(visibilityElementEClass, VISIBILITY_ELEMENT__VISIBILITY);

		// Create enums
		accessKindEEnum = createEEnum(ACCESS_KIND);
		parameterDirectionKindEEnum = createEEnum(PARAMETER_DIRECTION_KIND);
		visibilityKindEEnum = createEEnum(VISIBILITY_KIND);

		// Create data types
		accessKindObjectEDataType = createEDataType(ACCESS_KIND_OBJECT);
		parameterDirectionKindObjectEDataType = createEDataType(PARAMETER_DIRECTION_KIND_OBJECT);
		visibilityKindObjectEDataType = createEDataType(VISIBILITY_KIND_OBJECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents()
	{
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
		ElementsPackage theElementsPackage = (ElementsPackage)EPackage.Registry.INSTANCE.getEPackage(ElementsPackage.eNS_URI);
		XlinkPackage theXlinkPackage = (XlinkPackage)EPackage.Registry.INSTANCE.getEPackage(XlinkPackage.eNS_URI);

		// Create type parameters
		ETypeParameter elementReferenceEClass_T = addETypeParameter(elementReferenceEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(theElementsPackage.getNamedElement());
		elementReferenceEClass_T.getEBounds().add(g1);

		// Add supertypes to classes
		arrayEClass.getESuperTypes().add(this.getValueType());
		arrayValueEClass.getESuperTypes().add(this.getValue());
		associationEClass.getESuperTypes().add(this.getVisibilityElement());
		attributeEClass.getESuperTypes().add(theElementsPackage.getMetadata());
		attributeTypeEClass.getESuperTypes().add(this.getType());
		boolArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		boolValueEClass.getESuperTypes().add(this.getSimpleValue());
		char8ArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		char8ValueEClass.getESuperTypes().add(this.getSimpleValue());
		classEClass.getESuperTypes().add(this.getStructure());
		constantEClass.getESuperTypes().add(this.getVisibilityElement());
		dateTimeArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		dateTimeValueEClass.getESuperTypes().add(this.getSimpleValue());
		durationArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		durationValueEClass.getESuperTypes().add(this.getSimpleValue());
		enumerationEClass.getESuperTypes().add(this.getSimpleType());
		enumerationArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		enumerationLiteralEClass.getESuperTypes().add(theElementsPackage.getNamedElement());
		enumerationValueEClass.getESuperTypes().add(this.getSimpleValue());
		exceptionEClass.getESuperTypes().add(this.getClass_());
		fieldEClass.getESuperTypes().add(this.getVisibilityElement());
		floatEClass.getESuperTypes().add(this.getSimpleType());
		float32ArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		float32ValueEClass.getESuperTypes().add(this.getSimpleValue());
		float64ArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		float64ValueEClass.getESuperTypes().add(this.getSimpleValue());
		int16ArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		int16ValueEClass.getESuperTypes().add(this.getSimpleValue());
		int32ArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		int32ValueEClass.getESuperTypes().add(this.getSimpleValue());
		int64ArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		int64ValueEClass.getESuperTypes().add(this.getSimpleValue());
		int8ArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		int8ValueEClass.getESuperTypes().add(this.getSimpleValue());
		integerEClass.getESuperTypes().add(this.getSimpleType());
		languageTypeEClass.getESuperTypes().add(this.getType());
		nativeTypeEClass.getESuperTypes().add(this.getLanguageType());
		operationEClass.getESuperTypes().add(this.getVisibilityElement());
		parameterEClass.getESuperTypes().add(theElementsPackage.getNamedElement());
		primitiveTypeEClass.getESuperTypes().add(this.getSimpleType());
		propertyEClass.getESuperTypes().add(this.getVisibilityElement());
		simpleArrayValueEClass.getESuperTypes().add(this.getValue());
		simpleTypeEClass.getESuperTypes().add(this.getValueType());
		simpleValueEClass.getESuperTypes().add(this.getValue());
		stringEClass.getESuperTypes().add(this.getSimpleType());
		string8ArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		string8ValueEClass.getESuperTypes().add(this.getSimpleValue());
		structureEClass.getESuperTypes().add(this.getValueType());
		structureValueEClass.getESuperTypes().add(this.getValue());
		typeEClass.getESuperTypes().add(this.getVisibilityElement());
		elementReferenceEClass.getESuperTypes().add(theXlinkPackage.getSimpleLinkRef());
		uInt16ArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		uInt16ValueEClass.getESuperTypes().add(this.getSimpleValue());
		uInt32ArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		uInt32ValueEClass.getESuperTypes().add(this.getSimpleValue());
		uInt64ArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		uInt64ValueEClass.getESuperTypes().add(this.getSimpleValue());
		uInt8ArrayValueEClass.getESuperTypes().add(this.getSimpleArrayValue());
		uInt8ValueEClass.getESuperTypes().add(this.getSimpleValue());
		valueReferenceEClass.getESuperTypes().add(this.getLanguageType());
		valueTypeEClass.getESuperTypes().add(this.getLanguageType());
		visibilityElementEClass.getESuperTypes().add(theElementsPackage.getNamedElement());

		// Initialize classes and features; add operations and parameters
		initEClass(arrayEClass, Array.class, "Array", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getElementReference());
		EGenericType g2 = createEGenericType(this.getValueType());
		g1.getETypeArguments().add(g2);
		initEReference(getArray_ItemType(), g1, null, "itemType", null, 1, 1, Array.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArray_Size(), theXMLTypePackage.getLong(), "size", null, 1, 1, Array.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(arrayValueEClass, ArrayValue.class, "ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArrayValue_ItemValue(), this.getValue(), null, "itemValue", null, 0, -1, ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(associationEClass, Association.class, "Association", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getLanguageType());
		g1.getETypeArguments().add(g2);
		initEReference(getAssociation_Type(), g1, null, "type", null, 1, 1, Association.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeEClass, Attribute.class, "Attribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getAttributeType());
		g1.getETypeArguments().add(g2);
		initEReference(getAttribute_Type(), g1, null, "type", null, 1, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttribute_Value(), this.getValue(), null, "value", null, 1, 1, Attribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeTypeEClass, AttributeType.class, "AttributeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getValueType());
		g1.getETypeArguments().add(g2);
		initEReference(getAttributeType_Type(), g1, null, "type", null, 1, 1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeType_Default(), this.getValue(), null, "default", null, 1, 1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeType_Usage(), theXMLTypePackage.getString(), "usage", null, 0, -1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeType_AllowMultiple(), theXMLTypePackage.getBoolean(), "allowMultiple", "false", 0, 1, AttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(boolArrayValueEClass, BoolArrayValue.class, "BoolArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBoolArrayValue_ItemValue(), this.getBoolValue(), null, "itemValue", null, 0, -1, BoolArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(boolValueEClass, BoolValue.class, "BoolValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBoolValue_Value(), theXMLTypePackage.getBoolean(), "value", null, 1, 1, BoolValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(char8ArrayValueEClass, Char8ArrayValue.class, "Char8ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChar8ArrayValue_ItemValue(), this.getChar8Value(), null, "itemValue", null, 0, -1, Char8ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(char8ValueEClass, Char8Value.class, "Char8Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getChar8Value_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, Char8Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(classEClass, org.eclipse.xsmp.smp.core.types.Class.class, "Class", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getClass_());
		g1.getETypeArguments().add(g2);
		initEReference(getClass_Base(), g1, null, "base", null, 0, 1, org.eclipse.xsmp.smp.core.types.Class.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getClass_Property(), this.getProperty(), null, "property", null, 0, -1, org.eclipse.xsmp.smp.core.types.Class.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getClass_Operation(), this.getOperation(), null, "operation", null, 0, -1, org.eclipse.xsmp.smp.core.types.Class.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getClass_Association(), this.getAssociation(), null, "association", null, 0, -1, org.eclipse.xsmp.smp.core.types.Class.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getClass_Abstract(), theXMLTypePackage.getBoolean(), "abstract", "false", 0, 1, org.eclipse.xsmp.smp.core.types.Class.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(constantEClass, Constant.class, "Constant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getSimpleType());
		g1.getETypeArguments().add(g2);
		initEReference(getConstant_Type(), g1, null, "type", null, 1, 1, Constant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstant_Value(), this.getSimpleValue(), null, "value", null, 1, 1, Constant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dateTimeArrayValueEClass, DateTimeArrayValue.class, "DateTimeArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDateTimeArrayValue_ItemValue(), this.getDateTimeValue(), null, "itemValue", null, 0, -1, DateTimeArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dateTimeValueEClass, DateTimeValue.class, "DateTimeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDateTimeValue_Value(), theXMLTypePackage.getDateTime(), "value", null, 1, 1, DateTimeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(durationArrayValueEClass, DurationArrayValue.class, "DurationArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDurationArrayValue_ItemValue(), this.getDurationValue(), null, "itemValue", null, 0, -1, DurationArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(durationValueEClass, DurationValue.class, "DurationValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDurationValue_Value(), theXMLTypePackage.getDuration(), "value", null, 1, 1, DurationValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enumerationEClass, Enumeration.class, "Enumeration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEnumeration_Literal(), this.getEnumerationLiteral(), null, "literal", null, 1, -1, Enumeration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enumerationArrayValueEClass, EnumerationArrayValue.class, "EnumerationArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEnumerationArrayValue_ItemValue(), this.getEnumerationValue(), null, "itemValue", null, 0, -1, EnumerationArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enumerationLiteralEClass, EnumerationLiteral.class, "EnumerationLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnumerationLiteral_Value(), theXMLTypePackage.getInt(), "value", null, 1, 1, EnumerationLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enumerationValueEClass, EnumerationValue.class, "EnumerationValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnumerationValue_Value(), theXMLTypePackage.getInt(), "value", null, 1, 1, EnumerationValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnumerationValue_Literal(), theXMLTypePackage.getString(), "literal", null, 0, 1, EnumerationValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exceptionEClass, org.eclipse.xsmp.smp.core.types.Exception.class, "Exception", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(fieldEClass, Field.class, "Field", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getValueType());
		g1.getETypeArguments().add(g2);
		initEReference(getField_Type(), g1, null, "type", null, 1, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getField_Default(), this.getValue(), null, "default", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getField_State(), theXMLTypePackage.getBoolean(), "state", "true", 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getField_Input(), theXMLTypePackage.getBoolean(), "input", "false", 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getField_Output(), theXMLTypePackage.getBoolean(), "output", "false", 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(floatEClass, org.eclipse.xsmp.smp.core.types.Float.class, "Float", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getPrimitiveType());
		g1.getETypeArguments().add(g2);
		initEReference(getFloat_PrimitiveType(), g1, null, "primitiveType", null, 0, 1, org.eclipse.xsmp.smp.core.types.Float.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFloat_Maximum(), theXMLTypePackage.getDouble(), "maximum", null, 0, 1, org.eclipse.xsmp.smp.core.types.Float.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFloat_MinInclusive(), theXMLTypePackage.getBoolean(), "minInclusive", "true", 0, 1, org.eclipse.xsmp.smp.core.types.Float.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFloat_Minimum(), theXMLTypePackage.getDouble(), "minimum", null, 0, 1, org.eclipse.xsmp.smp.core.types.Float.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFloat_MaxInclusive(), theXMLTypePackage.getBoolean(), "maxInclusive", "true", 0, 1, org.eclipse.xsmp.smp.core.types.Float.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFloat_Unit(), theXMLTypePackage.getString(), "unit", null, 0, 1, org.eclipse.xsmp.smp.core.types.Float.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(float32ArrayValueEClass, Float32ArrayValue.class, "Float32ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFloat32ArrayValue_ItemValue(), this.getFloat32Value(), null, "itemValue", null, 0, -1, Float32ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(float32ValueEClass, Float32Value.class, "Float32Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFloat32Value_Value(), theXMLTypePackage.getFloat(), "value", null, 1, 1, Float32Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(float64ArrayValueEClass, Float64ArrayValue.class, "Float64ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFloat64ArrayValue_ItemValue(), this.getFloat64Value(), null, "itemValue", null, 0, -1, Float64ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(float64ValueEClass, Float64Value.class, "Float64Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFloat64Value_Value(), theXMLTypePackage.getDouble(), "value", null, 1, 1, Float64Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(int16ArrayValueEClass, Int16ArrayValue.class, "Int16ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInt16ArrayValue_ItemValue(), this.getInt16Value(), null, "itemValue", null, 0, -1, Int16ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(int16ValueEClass, Int16Value.class, "Int16Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInt16Value_Value(), theXMLTypePackage.getShort(), "value", null, 1, 1, Int16Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(int32ArrayValueEClass, Int32ArrayValue.class, "Int32ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInt32ArrayValue_ItemValue(), this.getInt32Value(), null, "itemValue", null, 0, -1, Int32ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(int32ValueEClass, Int32Value.class, "Int32Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInt32Value_Value(), theXMLTypePackage.getInt(), "value", null, 1, 1, Int32Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(int64ArrayValueEClass, Int64ArrayValue.class, "Int64ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInt64ArrayValue_ItemValue(), this.getInt64Value(), null, "itemValue", null, 0, -1, Int64ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(int64ValueEClass, Int64Value.class, "Int64Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInt64Value_Value(), theXMLTypePackage.getLong(), "value", null, 1, 1, Int64Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(int8ArrayValueEClass, Int8ArrayValue.class, "Int8ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInt8ArrayValue_ItemValue(), this.getInt8Value(), null, "itemValue", null, 0, -1, Int8ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(int8ValueEClass, Int8Value.class, "Int8Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInt8Value_Value(), theXMLTypePackage.getByte(), "value", null, 1, 1, Int8Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(integerEClass, org.eclipse.xsmp.smp.core.types.Integer.class, "Integer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getPrimitiveType());
		g1.getETypeArguments().add(g2);
		initEReference(getInteger_PrimitiveType(), g1, null, "primitiveType", null, 0, 1, org.eclipse.xsmp.smp.core.types.Integer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInteger_Minimum(), theXMLTypePackage.getLong(), "minimum", null, 0, 1, org.eclipse.xsmp.smp.core.types.Integer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInteger_Maximum(), theXMLTypePackage.getLong(), "maximum", null, 0, 1, org.eclipse.xsmp.smp.core.types.Integer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInteger_Unit(), theXMLTypePackage.getString(), "unit", null, 0, 1, org.eclipse.xsmp.smp.core.types.Integer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(languageTypeEClass, LanguageType.class, "LanguageType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nativeTypeEClass, NativeType.class, "NativeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNativeType_Platform(), this.getPlatformMapping(), null, "platform", null, 0, -1, NativeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(operationEClass, Operation.class, "Operation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOperation_Parameter(), this.getParameter(), null, "parameter", null, 0, -1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getException());
		g1.getETypeArguments().add(g2);
		initEReference(getOperation_RaisedException(), g1, null, "raisedException", null, 0, -1, Operation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getLanguageType());
		g1.getETypeArguments().add(g2);
		initEReference(getParameter_Type(), g1, null, "type", null, 1, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParameter_Default(), this.getValue(), null, "default", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Direction(), this.getParameterDirectionKind(), "direction", "in", 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(platformMappingEClass, PlatformMapping.class, "PlatformMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPlatformMapping_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, PlatformMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlatformMapping_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, PlatformMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlatformMapping_Namespace(), theXMLTypePackage.getString(), "namespace", null, 0, 1, PlatformMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlatformMapping_Location(), theXMLTypePackage.getString(), "location", null, 0, 1, PlatformMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(primitiveTypeEClass, PrimitiveType.class, "PrimitiveType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getLanguageType());
		g1.getETypeArguments().add(g2);
		initEReference(getProperty_Type(), g1, null, "type", null, 1, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getField());
		g1.getETypeArguments().add(g2);
		initEReference(getProperty_AttachedField(), g1, null, "attachedField", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getException());
		g1.getETypeArguments().add(g2);
		initEReference(getProperty_GetRaises(), g1, null, "getRaises", null, 0, -1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getException());
		g1.getETypeArguments().add(g2);
		initEReference(getProperty_SetRaises(), g1, null, "setRaises", null, 0, -1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_Access(), this.getAccessKind(), "access", "readWrite", 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_Category(), theXMLTypePackage.getString(), "category", "Properties", 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simpleArrayValueEClass, SimpleArrayValue.class, "SimpleArrayValue", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		EOperation op = addEOperation(simpleArrayValueEClass, null, "getItemValue", 1, 1, IS_UNIQUE, IS_ORDERED);
		g1 = createEGenericType(ecorePackage.getEEList());
		g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		EGenericType g3 = createEGenericType(this.getSimpleValue());
		g2.setEUpperBound(g3);
		initEOperation(op, g1);

		initEClass(simpleTypeEClass, SimpleType.class, "SimpleType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(simpleValueEClass, SimpleValue.class, "SimpleValue", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(stringEClass, org.eclipse.xsmp.smp.core.types.String.class, "String", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getString_Length(), theXMLTypePackage.getLong(), "length", null, 1, 1, org.eclipse.xsmp.smp.core.types.String.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(string8ArrayValueEClass, String8ArrayValue.class, "String8ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getString8ArrayValue_ItemValue(), this.getString8Value(), null, "itemValue", null, 0, -1, String8ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(string8ValueEClass, String8Value.class, "String8Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getString8Value_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, String8Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(structureEClass, Structure.class, "Structure", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStructure_Constant(), this.getConstant(), null, "constant", null, 0, -1, Structure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStructure_Field(), this.getField(), null, "field", null, 0, -1, Structure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(structureValueEClass, StructureValue.class, "StructureValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStructureValue_FieldValue(), this.getValue(), null, "fieldValue", null, 0, -1, StructureValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeEClass, Type.class, "Type", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getType_Uuid(), theElementsPackage.getUUID(), "uuid", null, 1, 1, Type.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(elementReferenceEClass, ElementReference.class, "ElementReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(elementReferenceEClass_T);
		initEReference(getElementReference_Ref(), g1, null, "ref", null, 1, 1, ElementReference.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(uInt16ArrayValueEClass, UInt16ArrayValue.class, "UInt16ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUInt16ArrayValue_ItemValue(), this.getUInt16Value(), null, "itemValue", null, 0, -1, UInt16ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uInt16ValueEClass, UInt16Value.class, "UInt16Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUInt16Value_Value(), theXMLTypePackage.getUnsignedShort(), "value", null, 1, 1, UInt16Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uInt32ArrayValueEClass, UInt32ArrayValue.class, "UInt32ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUInt32ArrayValue_ItemValue(), this.getUInt32Value(), null, "itemValue", null, 0, -1, UInt32ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uInt32ValueEClass, UInt32Value.class, "UInt32Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUInt32Value_Value(), theXMLTypePackage.getUnsignedInt(), "value", null, 1, 1, UInt32Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uInt64ArrayValueEClass, UInt64ArrayValue.class, "UInt64ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUInt64ArrayValue_ItemValue(), this.getUInt64Value(), null, "itemValue", null, 0, -1, UInt64ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uInt64ValueEClass, UInt64Value.class, "UInt64Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUInt64Value_Value(), theXMLTypePackage.getUnsignedLong(), "value", null, 1, 1, UInt64Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uInt8ArrayValueEClass, UInt8ArrayValue.class, "UInt8ArrayValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUInt8ArrayValue_ItemValue(), this.getUInt8Value(), null, "itemValue", null, 0, -1, UInt8ArrayValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uInt8ValueEClass, UInt8Value.class, "UInt8Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUInt8Value_Value(), theXMLTypePackage.getUnsignedByte(), "value", null, 1, 1, UInt8Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueEClass, Value.class, "Value", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValue_Field(), theXMLTypePackage.getString(), "field", null, 0, 1, Value.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueReferenceEClass, ValueReference.class, "ValueReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(this.getElementReference());
		g2 = createEGenericType(this.getValueType());
		g1.getETypeArguments().add(g2);
		initEReference(getValueReference_Type(), g1, null, "type", null, 1, 1, ValueReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueTypeEClass, ValueType.class, "ValueType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(visibilityElementEClass, VisibilityElement.class, "VisibilityElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVisibilityElement_Visibility(), this.getVisibilityKind(), "visibility", "private", 0, 1, VisibilityElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(accessKindEEnum, AccessKind.class, "AccessKind");
		addEEnumLiteral(accessKindEEnum, AccessKind.READ_WRITE);
		addEEnumLiteral(accessKindEEnum, AccessKind.READ_ONLY);
		addEEnumLiteral(accessKindEEnum, AccessKind.WRITE_ONLY);

		initEEnum(parameterDirectionKindEEnum, ParameterDirectionKind.class, "ParameterDirectionKind");
		addEEnumLiteral(parameterDirectionKindEEnum, ParameterDirectionKind.IN);
		addEEnumLiteral(parameterDirectionKindEEnum, ParameterDirectionKind.OUT);
		addEEnumLiteral(parameterDirectionKindEEnum, ParameterDirectionKind.INOUT);
		addEEnumLiteral(parameterDirectionKindEEnum, ParameterDirectionKind.RETURN);

		initEEnum(visibilityKindEEnum, VisibilityKind.class, "VisibilityKind");
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PUBLIC);
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PRIVATE);
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PROTECTED);

		// Initialize data types
		initEDataType(accessKindObjectEDataType, AccessKind.class, "AccessKindObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(parameterDirectionKindObjectEDataType, ParameterDirectionKind.class, "ParameterDirectionKindObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(visibilityKindObjectEDataType, VisibilityKind.class, "VisibilityKindObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
		// http://www.w3.org/1999/xlink
		createXlinkAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations()
	{
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
		addAnnotation
		  (accessKindEEnum,
		   source,
		   new String[]
		   {
			   "name", "AccessKind"
		   });
		addAnnotation
		  (accessKindObjectEDataType,
		   source,
		   new String[]
		   {
			   "name", "AccessKind:Object",
			   "baseType", "AccessKind"
		   });
		addAnnotation
		  (arrayEClass,
		   source,
		   new String[]
		   {
			   "name", "Array",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getArray_ItemType(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemType"
		   });
		addAnnotation
		  (getArray_Size(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Size"
		   });
		addAnnotation
		  (arrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (associationEClass,
		   source,
		   new String[]
		   {
			   "name", "Association",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getAssociation_Type(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Type"
		   });
		addAnnotation
		  (attributeEClass,
		   source,
		   new String[]
		   {
			   "name", "Attribute",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getAttribute_Type(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Type"
		   });
		addAnnotation
		  (getAttribute_Value(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Value"
		   });
		addAnnotation
		  (attributeTypeEClass,
		   source,
		   new String[]
		   {
			   "name", "AttributeType",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getAttributeType_Type(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Type"
		   });
		addAnnotation
		  (getAttributeType_Default(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Default"
		   });
		addAnnotation
		  (getAttributeType_Usage(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Usage"
		   });
		addAnnotation
		  (getAttributeType_AllowMultiple(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "AllowMultiple"
		   });
		addAnnotation
		  (boolArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "BoolArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getBoolArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (boolValueEClass,
		   source,
		   new String[]
		   {
			   "name", "BoolValue",
			   "kind", "empty"
		   });
		addAnnotation
		  (getBoolValue_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (char8ArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Char8ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getChar8ArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (char8ValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Char8Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getChar8Value_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (classEClass,
		   source,
		   new String[]
		   {
			   "name", "Class",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getClass_Base(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Base"
		   });
		addAnnotation
		  (getClass_Property(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Property"
		   });
		addAnnotation
		  (getClass_Operation(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Operation"
		   });
		addAnnotation
		  (getClass_Association(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Association"
		   });
		addAnnotation
		  (getClass_Abstract(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Abstract"
		   });
		addAnnotation
		  (constantEClass,
		   source,
		   new String[]
		   {
			   "name", "Constant",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getConstant_Type(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Type"
		   });
		addAnnotation
		  (getConstant_Value(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Value"
		   });
		addAnnotation
		  (dateTimeArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "DateTimeArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getDateTimeArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (dateTimeValueEClass,
		   source,
		   new String[]
		   {
			   "name", "DateTimeValue",
			   "kind", "empty"
		   });
		addAnnotation
		  (getDateTimeValue_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (durationArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "DurationArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getDurationArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (durationValueEClass,
		   source,
		   new String[]
		   {
			   "name", "DurationValue",
			   "kind", "empty"
		   });
		addAnnotation
		  (getDurationValue_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (enumerationEClass,
		   source,
		   new String[]
		   {
			   "name", "Enumeration",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getEnumeration_Literal(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Literal"
		   });
		addAnnotation
		  (enumerationArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "EnumerationArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getEnumerationArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (enumerationLiteralEClass,
		   source,
		   new String[]
		   {
			   "name", "EnumerationLiteral",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getEnumerationLiteral_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (enumerationValueEClass,
		   source,
		   new String[]
		   {
			   "name", "EnumerationValue",
			   "kind", "empty"
		   });
		addAnnotation
		  (getEnumerationValue_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (getEnumerationValue_Literal(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Literal"
		   });
		addAnnotation
		  (exceptionEClass,
		   source,
		   new String[]
		   {
			   "name", "Exception",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (fieldEClass,
		   source,
		   new String[]
		   {
			   "name", "Field",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getField_Type(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Type"
		   });
		addAnnotation
		  (getField_Default(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Default"
		   });
		addAnnotation
		  (getField_State(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "State"
		   });
		addAnnotation
		  (getField_Input(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Input"
		   });
		addAnnotation
		  (getField_Output(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Output"
		   });
		addAnnotation
		  (floatEClass,
		   source,
		   new String[]
		   {
			   "name", "Float",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getFloat_PrimitiveType(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "PrimitiveType"
		   });
		addAnnotation
		  (getFloat_Maximum(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Maximum"
		   });
		addAnnotation
		  (getFloat_MinInclusive(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "MinInclusive"
		   });
		addAnnotation
		  (getFloat_Minimum(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Minimum"
		   });
		addAnnotation
		  (getFloat_MaxInclusive(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "MaxInclusive"
		   });
		addAnnotation
		  (getFloat_Unit(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Unit"
		   });
		addAnnotation
		  (float32ArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Float32ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getFloat32ArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (float32ValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Float32Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getFloat32Value_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (float64ArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Float64ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getFloat64ArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (float64ValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Float64Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getFloat64Value_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (int16ArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Int16ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getInt16ArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (int16ValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Int16Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getInt16Value_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (int32ArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Int32ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getInt32ArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (int32ValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Int32Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getInt32Value_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (int64ArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Int64ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getInt64ArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (int64ValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Int64Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getInt64Value_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (int8ArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Int8ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getInt8ArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (int8ValueEClass,
		   source,
		   new String[]
		   {
			   "name", "Int8Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getInt8Value_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (integerEClass,
		   source,
		   new String[]
		   {
			   "name", "Integer",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getInteger_PrimitiveType(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "PrimitiveType"
		   });
		addAnnotation
		  (getInteger_Minimum(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Minimum"
		   });
		addAnnotation
		  (getInteger_Maximum(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Maximum"
		   });
		addAnnotation
		  (getInteger_Unit(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Unit"
		   });
		addAnnotation
		  (languageTypeEClass,
		   source,
		   new String[]
		   {
			   "name", "LanguageType",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (nativeTypeEClass,
		   source,
		   new String[]
		   {
			   "name", "NativeType",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getNativeType_Platform(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Platform"
		   });
		addAnnotation
		  (operationEClass,
		   source,
		   new String[]
		   {
			   "name", "Operation",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getOperation_Parameter(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Parameter"
		   });
		addAnnotation
		  (getOperation_RaisedException(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "RaisedException"
		   });
		addAnnotation
		  (parameterEClass,
		   source,
		   new String[]
		   {
			   "name", "Parameter",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getParameter_Type(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Type"
		   });
		addAnnotation
		  (getParameter_Default(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Default"
		   });
		addAnnotation
		  (getParameter_Direction(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Direction"
		   });
		addAnnotation
		  (parameterDirectionKindEEnum,
		   source,
		   new String[]
		   {
			   "name", "ParameterDirectionKind"
		   });
		addAnnotation
		  (parameterDirectionKindObjectEDataType,
		   source,
		   new String[]
		   {
			   "name", "ParameterDirectionKind:Object",
			   "baseType", "ParameterDirectionKind"
		   });
		addAnnotation
		  (platformMappingEClass,
		   source,
		   new String[]
		   {
			   "name", "PlatformMapping",
			   "kind", "empty"
		   });
		addAnnotation
		  (getPlatformMapping_Name(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Name"
		   });
		addAnnotation
		  (getPlatformMapping_Type(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Type"
		   });
		addAnnotation
		  (getPlatformMapping_Namespace(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Namespace"
		   });
		addAnnotation
		  (getPlatformMapping_Location(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Location"
		   });
		addAnnotation
		  (primitiveTypeEClass,
		   source,
		   new String[]
		   {
			   "name", "PrimitiveType",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (propertyEClass,
		   source,
		   new String[]
		   {
			   "name", "Property",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getProperty_Type(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Type"
		   });
		addAnnotation
		  (getProperty_AttachedField(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "AttachedField"
		   });
		addAnnotation
		  (getProperty_GetRaises(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "GetRaises"
		   });
		addAnnotation
		  (getProperty_SetRaises(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "SetRaises"
		   });
		addAnnotation
		  (getProperty_Access(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Access"
		   });
		addAnnotation
		  (getProperty_Category(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Category"
		   });
		addAnnotation
		  (simpleArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "SimpleArrayValue",
			   "kind", "empty"
		   });
		addAnnotation
		  (simpleTypeEClass,
		   source,
		   new String[]
		   {
			   "name", "SimpleType",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (simpleValueEClass,
		   source,
		   new String[]
		   {
			   "name", "SimpleValue",
			   "kind", "empty"
		   });
		addAnnotation
		  (stringEClass,
		   source,
		   new String[]
		   {
			   "name", "String",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getString_Length(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Length"
		   });
		addAnnotation
		  (string8ArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "String8ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getString8ArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (string8ValueEClass,
		   source,
		   new String[]
		   {
			   "name", "String8Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getString8Value_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (structureEClass,
		   source,
		   new String[]
		   {
			   "name", "Structure",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getStructure_Constant(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Constant"
		   });
		addAnnotation
		  (getStructure_Field(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Field"
		   });
		addAnnotation
		  (structureValueEClass,
		   source,
		   new String[]
		   {
			   "name", "StructureValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getStructureValue_FieldValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "FieldValue"
		   });
		addAnnotation
		  (typeEClass,
		   source,
		   new String[]
		   {
			   "name", "Type",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getType_Uuid(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Uuid"
		   });
		addAnnotation
		  (getElementReference_Ref(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "href",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (uInt16ArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "UInt16ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getUInt16ArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (uInt16ValueEClass,
		   source,
		   new String[]
		   {
			   "name", "UInt16Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getUInt16Value_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (uInt32ArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "UInt32ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getUInt32ArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (uInt32ValueEClass,
		   source,
		   new String[]
		   {
			   "name", "UInt32Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getUInt32Value_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (uInt64ArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "UInt64ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getUInt64ArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (uInt64ValueEClass,
		   source,
		   new String[]
		   {
			   "name", "UInt64Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getUInt64Value_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (uInt8ArrayValueEClass,
		   source,
		   new String[]
		   {
			   "name", "UInt8ArrayValue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getUInt8ArrayValue_ItemValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "ItemValue"
		   });
		addAnnotation
		  (uInt8ValueEClass,
		   source,
		   new String[]
		   {
			   "name", "UInt8Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getUInt8Value_Value(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Value"
		   });
		addAnnotation
		  (valueEClass,
		   source,
		   new String[]
		   {
			   "name", "Value",
			   "kind", "empty"
		   });
		addAnnotation
		  (getValue_Field(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Field"
		   });
		addAnnotation
		  (valueReferenceEClass,
		   source,
		   new String[]
		   {
			   "name", "ValueReference",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getValueReference_Type(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Type"
		   });
		addAnnotation
		  (valueTypeEClass,
		   source,
		   new String[]
		   {
			   "name", "ValueType",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (visibilityElementEClass,
		   source,
		   new String[]
		   {
			   "name", "VisibilityElement",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getVisibilityElement_Visibility(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Visibility"
		   });
		addAnnotation
		  (visibilityKindEEnum,
		   source,
		   new String[]
		   {
			   "name", "VisibilityKind"
		   });
		addAnnotation
		  (visibilityKindObjectEDataType,
		   source,
		   new String[]
		   {
			   "name", "VisibilityKind:Object",
			   "baseType", "VisibilityKind"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.w3.org/1999/xlink</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createXlinkAnnotations()
	{
		String source = "http://www.w3.org/1999/xlink";
		addAnnotation
		  (getArray_ItemType(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:ValueType"
		   });
		addAnnotation
		  (getAssociation_Type(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:LanguageType"
		   });
		addAnnotation
		  (getAttribute_Type(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:AttributeType"
		   });
		addAnnotation
		  (getAttributeType_Type(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:ValueType"
		   });
		addAnnotation
		  (getClass_Base(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:Class"
		   });
		addAnnotation
		  (getConstant_Type(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:SimpleType"
		   });
		addAnnotation
		  (getField_Type(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:ValueType"
		   });
		addAnnotation
		  (getFloat_PrimitiveType(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:PrimitiveType"
		   });
		addAnnotation
		  (getInteger_PrimitiveType(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:PrimitiveType"
		   });
		addAnnotation
		  (getOperation_RaisedException(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:Exception"
		   });
		addAnnotation
		  (getParameter_Type(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:LanguageType"
		   });
		addAnnotation
		  (getProperty_Type(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:LanguageType"
		   });
		addAnnotation
		  (getProperty_AttachedField(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:Field"
		   });
		addAnnotation
		  (getProperty_GetRaises(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:Exception"
		   });
		addAnnotation
		  (getProperty_SetRaises(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:Exception"
		   });
		addAnnotation
		  (getValueReference_Type(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:ValueType"
		   });
	}

} //TypesPackageImpl
