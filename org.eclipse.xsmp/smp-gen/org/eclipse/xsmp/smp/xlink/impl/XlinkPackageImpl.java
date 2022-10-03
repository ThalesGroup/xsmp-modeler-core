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
package org.eclipse.xsmp.smp.xlink.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.xsmp.smp.core.elements.ElementsPackage;

import org.eclipse.xsmp.smp.core.elements.impl.ElementsPackageImpl;

import org.eclipse.xsmp.smp.core.types.TypesPackage;

import org.eclipse.xsmp.smp.core.types.impl.TypesPackageImpl;

import org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage;

import org.eclipse.xsmp.smp.smdl.catalogue.impl.CataloguePackageImpl;

import org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage;

import org.eclipse.xsmp.smp.smdl.configuration.impl.ConfigurationPackageImpl;

import org.eclipse.xsmp.smp.smdl.package_.PackagePackage;

import org.eclipse.xsmp.smp.smdl.package_.impl.PackagePackageImpl;

import org.eclipse.xsmp.smp.xlink.ActuateKind;
import org.eclipse.xsmp.smp.xlink.ShowKind;
import org.eclipse.xsmp.smp.xlink.SimpleLink;
import org.eclipse.xsmp.smp.xlink.SimpleLinkBase;
import org.eclipse.xsmp.smp.xlink.SimpleLinkRef;
import org.eclipse.xsmp.smp.xlink.TypeKind;
import org.eclipse.xsmp.smp.xlink.XlinkDocument;
import org.eclipse.xsmp.smp.xlink.XlinkFactory;
import org.eclipse.xsmp.smp.xlink.XlinkPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class XlinkPackageImpl extends EPackageImpl implements XlinkPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleLinkBaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleLinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleLinkRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xlinkDocumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum actuateKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum showKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum typeKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType actuateKindObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType showKindObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType typeKindObjectEDataType = null;

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
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private XlinkPackageImpl()
	{
		super(eNS_URI, XlinkFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link XlinkPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static XlinkPackage init()
	{
		if (isInited) return (XlinkPackage)EPackage.Registry.INSTANCE.getEPackage(XlinkPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredXlinkPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		XlinkPackageImpl theXlinkPackage = registeredXlinkPackage instanceof XlinkPackageImpl ? (XlinkPackageImpl)registeredXlinkPackage : new XlinkPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ElementsPackage.eNS_URI);
		ElementsPackageImpl theElementsPackage = (ElementsPackageImpl)(registeredPackage instanceof ElementsPackageImpl ? registeredPackage : ElementsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		TypesPackageImpl theTypesPackage = (TypesPackageImpl)(registeredPackage instanceof TypesPackageImpl ? registeredPackage : TypesPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(CataloguePackage.eNS_URI);
		CataloguePackageImpl theCataloguePackage = (CataloguePackageImpl)(registeredPackage instanceof CataloguePackageImpl ? registeredPackage : CataloguePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ConfigurationPackage.eNS_URI);
		ConfigurationPackageImpl theConfigurationPackage = (ConfigurationPackageImpl)(registeredPackage instanceof ConfigurationPackageImpl ? registeredPackage : ConfigurationPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PackagePackage.eNS_URI);
		PackagePackageImpl thePackagePackage = (PackagePackageImpl)(registeredPackage instanceof PackagePackageImpl ? registeredPackage : PackagePackage.eINSTANCE);

		// Create package meta-data objects
		theXlinkPackage.createPackageContents();
		theElementsPackage.createPackageContents();
		theTypesPackage.createPackageContents();
		theCataloguePackage.createPackageContents();
		theConfigurationPackage.createPackageContents();
		thePackagePackage.createPackageContents();

		// Initialize created meta-data
		theXlinkPackage.initializePackageContents();
		theElementsPackage.initializePackageContents();
		theTypesPackage.initializePackageContents();
		theCataloguePackage.initializePackageContents();
		theConfigurationPackage.initializePackageContents();
		thePackagePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theXlinkPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(XlinkPackage.eNS_URI, theXlinkPackage);
		return theXlinkPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSimpleLinkBase()
	{
		return simpleLinkBaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSimpleLinkBase_Actuate()
	{
		return (EAttribute)simpleLinkBaseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSimpleLinkBase_Arcrole()
	{
		return (EAttribute)simpleLinkBaseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSimpleLinkBase_Role()
	{
		return (EAttribute)simpleLinkBaseEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSimpleLinkBase_Show()
	{
		return (EAttribute)simpleLinkBaseEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSimpleLinkBase_Title()
	{
		return (EAttribute)simpleLinkBaseEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSimpleLinkBase_LinkType()
	{
		return (EAttribute)simpleLinkBaseEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSimpleLink()
	{
		return simpleLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSimpleLink_Href()
	{
		return (EAttribute)simpleLinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSimpleLinkRef()
	{
		return simpleLinkRefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSimpleLinkRef_Reference()
	{
		return (EReference)simpleLinkRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getXlinkDocument()
	{
		return xlinkDocumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXlinkDocument_Mixed()
	{
		return (EAttribute)xlinkDocumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXlinkDocument_XMLNSPrefixMap()
	{
		return (EReference)xlinkDocumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXlinkDocument_XSISchemaLocation()
	{
		return (EReference)xlinkDocumentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXlinkDocument_Actuate()
	{
		return (EAttribute)xlinkDocumentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXlinkDocument_Arcrole()
	{
		return (EAttribute)xlinkDocumentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXlinkDocument_From()
	{
		return (EAttribute)xlinkDocumentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXlinkDocument_Href()
	{
		return (EAttribute)xlinkDocumentEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXlinkDocument_Label()
	{
		return (EAttribute)xlinkDocumentEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXlinkDocument_Role()
	{
		return (EAttribute)xlinkDocumentEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXlinkDocument_Show()
	{
		return (EAttribute)xlinkDocumentEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXlinkDocument_Title()
	{
		return (EAttribute)xlinkDocumentEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXlinkDocument_To()
	{
		return (EAttribute)xlinkDocumentEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getXlinkDocument_Type()
	{
		return (EAttribute)xlinkDocumentEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getActuateKind()
	{
		return actuateKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getShowKind()
	{
		return showKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getTypeKind()
	{
		return typeKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getActuateKindObject()
	{
		return actuateKindObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getShowKindObject()
	{
		return showKindObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getTypeKindObject()
	{
		return typeKindObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XlinkFactory getXlinkFactory()
	{
		return (XlinkFactory)getEFactoryInstance();
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
		simpleLinkBaseEClass = createEClass(SIMPLE_LINK_BASE);
		createEAttribute(simpleLinkBaseEClass, SIMPLE_LINK_BASE__ACTUATE);
		createEAttribute(simpleLinkBaseEClass, SIMPLE_LINK_BASE__ARCROLE);
		createEAttribute(simpleLinkBaseEClass, SIMPLE_LINK_BASE__ROLE);
		createEAttribute(simpleLinkBaseEClass, SIMPLE_LINK_BASE__SHOW);
		createEAttribute(simpleLinkBaseEClass, SIMPLE_LINK_BASE__TITLE);
		createEAttribute(simpleLinkBaseEClass, SIMPLE_LINK_BASE__LINK_TYPE);

		simpleLinkEClass = createEClass(SIMPLE_LINK);
		createEAttribute(simpleLinkEClass, SIMPLE_LINK__HREF);

		simpleLinkRefEClass = createEClass(SIMPLE_LINK_REF);
		createEReference(simpleLinkRefEClass, SIMPLE_LINK_REF__REFERENCE);

		xlinkDocumentEClass = createEClass(XLINK_DOCUMENT);
		createEAttribute(xlinkDocumentEClass, XLINK_DOCUMENT__MIXED);
		createEReference(xlinkDocumentEClass, XLINK_DOCUMENT__XMLNS_PREFIX_MAP);
		createEReference(xlinkDocumentEClass, XLINK_DOCUMENT__XSI_SCHEMA_LOCATION);
		createEAttribute(xlinkDocumentEClass, XLINK_DOCUMENT__ACTUATE);
		createEAttribute(xlinkDocumentEClass, XLINK_DOCUMENT__ARCROLE);
		createEAttribute(xlinkDocumentEClass, XLINK_DOCUMENT__FROM);
		createEAttribute(xlinkDocumentEClass, XLINK_DOCUMENT__HREF);
		createEAttribute(xlinkDocumentEClass, XLINK_DOCUMENT__LABEL);
		createEAttribute(xlinkDocumentEClass, XLINK_DOCUMENT__ROLE);
		createEAttribute(xlinkDocumentEClass, XLINK_DOCUMENT__SHOW);
		createEAttribute(xlinkDocumentEClass, XLINK_DOCUMENT__TITLE);
		createEAttribute(xlinkDocumentEClass, XLINK_DOCUMENT__TO);
		createEAttribute(xlinkDocumentEClass, XLINK_DOCUMENT__TYPE);

		// Create enums
		actuateKindEEnum = createEEnum(ACTUATE_KIND);
		showKindEEnum = createEEnum(SHOW_KIND);
		typeKindEEnum = createEEnum(TYPE_KIND);

		// Create data types
		actuateKindObjectEDataType = createEDataType(ACTUATE_KIND_OBJECT);
		showKindObjectEDataType = createEDataType(SHOW_KIND_OBJECT);
		typeKindObjectEDataType = createEDataType(TYPE_KIND_OBJECT);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		simpleLinkEClass.getESuperTypes().add(this.getSimpleLinkBase());
		simpleLinkRefEClass.getESuperTypes().add(this.getSimpleLinkBase());

		// Initialize classes and features; add operations and parameters
		initEClass(simpleLinkBaseEClass, SimpleLinkBase.class, "SimpleLinkBase", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimpleLinkBase_Actuate(), this.getActuateKind(), "actuate", null, 0, 1, SimpleLinkBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleLinkBase_Arcrole(), theXMLTypePackage.getAnyURI(), "arcrole", null, 0, 1, SimpleLinkBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleLinkBase_Role(), theXMLTypePackage.getAnyURI(), "role", null, 0, 1, SimpleLinkBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleLinkBase_Show(), this.getShowKind(), "show", null, 0, 1, SimpleLinkBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleLinkBase_Title(), theXMLTypePackage.getString(), "title", null, 0, 1, SimpleLinkBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleLinkBase_LinkType(), this.getTypeKind(), "linkType", "simple", 0, 1, SimpleLinkBase.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(simpleLinkBaseEClass, ecorePackage.getEString(), "getHref", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(simpleLinkEClass, SimpleLink.class, "SimpleLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimpleLink_Href(), theXMLTypePackage.getAnyURI(), "href", null, 1, 1, SimpleLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simpleLinkRefEClass, SimpleLinkRef.class, "SimpleLinkRef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSimpleLinkRef_Reference(), ecorePackage.getEObject(), null, "reference", null, 1, 1, SimpleLinkRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(simpleLinkRefEClass, ecorePackage.getEString(), "getHref", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(xlinkDocumentEClass, XlinkDocument.class, "XlinkDocument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getXlinkDocument_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXlinkDocument_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getXlinkDocument_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXlinkDocument_Actuate(), this.getActuateKind(), "actuate", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXlinkDocument_Arcrole(), theXMLTypePackage.getAnyURI(), "arcrole", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXlinkDocument_From(), theXMLTypePackage.getNMTOKEN(), "from", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXlinkDocument_Href(), theXMLTypePackage.getAnyURI(), "href", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXlinkDocument_Label(), theXMLTypePackage.getNMTOKEN(), "label", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXlinkDocument_Role(), theXMLTypePackage.getAnyURI(), "role", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXlinkDocument_Show(), this.getShowKind(), "show", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXlinkDocument_Title(), theXMLTypePackage.getString(), "title", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXlinkDocument_To(), theXMLTypePackage.getNMTOKEN(), "to", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getXlinkDocument_Type(), this.getTypeKind(), "type", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(actuateKindEEnum, ActuateKind.class, "ActuateKind");
		addEEnumLiteral(actuateKindEEnum, ActuateKind.ON_LOAD);
		addEEnumLiteral(actuateKindEEnum, ActuateKind.ON_REQUEST);
		addEEnumLiteral(actuateKindEEnum, ActuateKind.OTHER);
		addEEnumLiteral(actuateKindEEnum, ActuateKind.NONE);

		initEEnum(showKindEEnum, ShowKind.class, "ShowKind");
		addEEnumLiteral(showKindEEnum, ShowKind.NEW);
		addEEnumLiteral(showKindEEnum, ShowKind.REPLACE);
		addEEnumLiteral(showKindEEnum, ShowKind.EMBED);
		addEEnumLiteral(showKindEEnum, ShowKind.OTHER);
		addEEnumLiteral(showKindEEnum, ShowKind.NONE);

		initEEnum(typeKindEEnum, TypeKind.class, "TypeKind");
		addEEnumLiteral(typeKindEEnum, TypeKind.SIMPLE);
		addEEnumLiteral(typeKindEEnum, TypeKind.EXTENDED);
		addEEnumLiteral(typeKindEEnum, TypeKind.TITLE);
		addEEnumLiteral(typeKindEEnum, TypeKind.ARC);
		addEEnumLiteral(typeKindEEnum, TypeKind.RESOURCE);
		addEEnumLiteral(typeKindEEnum, TypeKind.LOCATOR);
		addEEnumLiteral(typeKindEEnum, TypeKind.NONE);

		// Initialize data types
		initEDataType(actuateKindObjectEDataType, ActuateKind.class, "ActuateKindObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(showKindObjectEDataType, ShowKind.class, "ShowKindObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(typeKindObjectEDataType, TypeKind.class, "TypeKindObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
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
		  (actuateKindEEnum,
		   source,
		   new String[]
		   {
			   "name", "ActuateKind"
		   });
		addAnnotation
		  (actuateKindObjectEDataType,
		   source,
		   new String[]
		   {
			   "name", "ActuateKind:Object",
			   "baseType", "ActuateKind"
		   });
		addAnnotation
		  (showKindEEnum,
		   source,
		   new String[]
		   {
			   "name", "ShowKind"
		   });
		addAnnotation
		  (showKindObjectEDataType,
		   source,
		   new String[]
		   {
			   "name", "ShowKind:Object",
			   "baseType", "ShowKind"
		   });
		addAnnotation
		  (simpleLinkBaseEClass,
		   source,
		   new String[]
		   {
			   "name", "SimpleLinkBase",
			   "kind", "empty"
		   });
		addAnnotation
		  (getSimpleLinkBase_Actuate(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "actuate",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimpleLinkBase_Arcrole(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "arcrole",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimpleLinkBase_Role(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "role",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimpleLinkBase_Show(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "show",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimpleLinkBase_Title(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "title",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimpleLinkBase_LinkType(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "type",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (simpleLinkEClass,
		   source,
		   new String[]
		   {
			   "name", "SimpleLink",
			   "kind", "empty"
		   });
		addAnnotation
		  (getSimpleLink_Href(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "href",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (simpleLinkRefEClass,
		   source,
		   new String[]
		   {
			   "name", "SimpleLinkRef",
			   "kind", "empty"
		   });
		addAnnotation
		  (getSimpleLinkRef_Reference(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "href",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (typeKindEEnum,
		   source,
		   new String[]
		   {
			   "name", "TypeKind"
		   });
		addAnnotation
		  (typeKindObjectEDataType,
		   source,
		   new String[]
		   {
			   "name", "TypeKind:Object",
			   "baseType", "TypeKind"
		   });
		addAnnotation
		  (xlinkDocumentEClass,
		   source,
		   new String[]
		   {
			   "name", "",
			   "kind", "mixed"
		   });
		addAnnotation
		  (getXlinkDocument_Mixed(),
		   source,
		   new String[]
		   {
			   "kind", "elementWildcard",
			   "name", ":mixed"
		   });
		addAnnotation
		  (getXlinkDocument_XMLNSPrefixMap(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "xmlns:prefix"
		   });
		addAnnotation
		  (getXlinkDocument_XSISchemaLocation(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "xsi:schemaLocation"
		   });
		addAnnotation
		  (getXlinkDocument_Actuate(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "actuate",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getXlinkDocument_Arcrole(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "arcrole",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getXlinkDocument_From(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "from",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getXlinkDocument_Href(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "href",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getXlinkDocument_Label(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "label",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getXlinkDocument_Role(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "role",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getXlinkDocument_Show(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "show",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getXlinkDocument_Title(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "title",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getXlinkDocument_To(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "to",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getXlinkDocument_Type(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "type",
			   "namespace", "##targetNamespace"
		   });
	}

} //XlinkPackageImpl
