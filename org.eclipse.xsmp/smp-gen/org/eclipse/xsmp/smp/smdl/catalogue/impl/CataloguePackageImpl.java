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
package org.eclipse.xsmp.smp.smdl.catalogue.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.xsmp.smp.core.elements.ElementsPackage;

import org.eclipse.xsmp.smp.core.elements.impl.ElementsPackageImpl;

import org.eclipse.xsmp.smp.core.types.TypesPackage;

import org.eclipse.xsmp.smp.core.types.impl.TypesPackageImpl;

import org.eclipse.xsmp.smp.smdl.catalogue.Catalogue;
import org.eclipse.xsmp.smp.smdl.catalogue.CatalogueDocumentRoot;
import org.eclipse.xsmp.smp.smdl.catalogue.CatalogueFactory;
import org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage;
import org.eclipse.xsmp.smp.smdl.catalogue.Component;
import org.eclipse.xsmp.smp.smdl.catalogue.EntryPoint;
import org.eclipse.xsmp.smp.smdl.catalogue.EventSink;
import org.eclipse.xsmp.smp.smdl.catalogue.EventSource;
import org.eclipse.xsmp.smp.smdl.catalogue.EventType;
import org.eclipse.xsmp.smp.smdl.catalogue.Interface;
import org.eclipse.xsmp.smp.smdl.catalogue.Model;
import org.eclipse.xsmp.smp.smdl.catalogue.Namespace;
import org.eclipse.xsmp.smp.smdl.catalogue.Reference;
import org.eclipse.xsmp.smp.smdl.catalogue.ReferenceType;
import org.eclipse.xsmp.smp.smdl.catalogue.Service;

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
public class CataloguePackageImpl extends EPackageImpl implements CataloguePackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass catalogueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass catalogueDocumentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass containerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entryPointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventSinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass interfaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namespaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referenceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass serviceEClass = null;

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
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CataloguePackageImpl()
	{
		super(eNS_URI, CatalogueFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CataloguePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CataloguePackage init()
	{
		if (isInited) return (CataloguePackage)EPackage.Registry.INSTANCE.getEPackage(CataloguePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCataloguePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CataloguePackageImpl theCataloguePackage = registeredCataloguePackage instanceof CataloguePackageImpl ? (CataloguePackageImpl)registeredCataloguePackage : new CataloguePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ElementsPackage.eNS_URI);
		ElementsPackageImpl theElementsPackage = (ElementsPackageImpl)(registeredPackage instanceof ElementsPackageImpl ? registeredPackage : ElementsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		TypesPackageImpl theTypesPackage = (TypesPackageImpl)(registeredPackage instanceof TypesPackageImpl ? registeredPackage : TypesPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ConfigurationPackage.eNS_URI);
		ConfigurationPackageImpl theConfigurationPackage = (ConfigurationPackageImpl)(registeredPackage instanceof ConfigurationPackageImpl ? registeredPackage : ConfigurationPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PackagePackage.eNS_URI);
		PackagePackageImpl thePackagePackage = (PackagePackageImpl)(registeredPackage instanceof PackagePackageImpl ? registeredPackage : PackagePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(XlinkPackage.eNS_URI);
		XlinkPackageImpl theXlinkPackage = (XlinkPackageImpl)(registeredPackage instanceof XlinkPackageImpl ? registeredPackage : XlinkPackage.eINSTANCE);

		// Create package meta-data objects
		theCataloguePackage.createPackageContents();
		theElementsPackage.createPackageContents();
		theTypesPackage.createPackageContents();
		theConfigurationPackage.createPackageContents();
		thePackagePackage.createPackageContents();
		theXlinkPackage.createPackageContents();

		// Initialize created meta-data
		theCataloguePackage.initializePackageContents();
		theElementsPackage.initializePackageContents();
		theTypesPackage.initializePackageContents();
		theConfigurationPackage.initializePackageContents();
		thePackagePackage.initializePackageContents();
		theXlinkPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCataloguePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CataloguePackage.eNS_URI, theCataloguePackage);
		return theCataloguePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCatalogue()
	{
		return catalogueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCatalogue_Namespace()
	{
		return (EReference)catalogueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCatalogueDocumentRoot()
	{
		return catalogueDocumentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCatalogueDocumentRoot_Mixed()
	{
		return (EAttribute)catalogueDocumentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCatalogueDocumentRoot_XMLNSPrefixMap()
	{
		return (EReference)catalogueDocumentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCatalogueDocumentRoot_XSISchemaLocation()
	{
		return (EReference)catalogueDocumentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCatalogueDocumentRoot_Catalogue()
	{
		return (EReference)catalogueDocumentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getComponent()
	{
		return componentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponent_Base()
	{
		return (EReference)componentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponent_Interface()
	{
		return (EReference)componentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponent_EntryPoint()
	{
		return (EReference)componentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponent_EventSink()
	{
		return (EReference)componentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponent_EventSource()
	{
		return (EReference)componentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponent_Field()
	{
		return (EReference)componentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponent_Association()
	{
		return (EReference)componentEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponent_Container()
	{
		return (EReference)componentEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponent_Reference()
	{
		return (EReference)componentEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContainer()
	{
		return containerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContainer_Type()
	{
		return (EReference)containerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContainer_DefaultComponent()
	{
		return (EReference)containerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContainer_Lower()
	{
		return (EAttribute)containerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContainer_Upper()
	{
		return (EAttribute)containerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEntryPoint()
	{
		return entryPointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEntryPoint_Input()
	{
		return (EReference)entryPointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEntryPoint_Output()
	{
		return (EReference)entryPointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEventSink()
	{
		return eventSinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEventSink_Type()
	{
		return (EReference)eventSinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEventSource()
	{
		return eventSourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEventSource_Type()
	{
		return (EReference)eventSourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEventSource_Multicast()
	{
		return (EAttribute)eventSourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEventType()
	{
		return eventTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEventType_EventArgs()
	{
		return (EReference)eventTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInterface()
	{
		return interfaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInterface_Base()
	{
		return (EReference)interfaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getModel()
	{
		return modelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNamespace()
	{
		return namespaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNamespace_Namespace()
	{
		return (EReference)namespaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNamespace_Type()
	{
		return (EReference)namespaceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReference()
	{
		return referenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReference_Interface()
	{
		return (EReference)referenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReference_Lower()
	{
		return (EAttribute)referenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getReference_Upper()
	{
		return (EAttribute)referenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReferenceType()
	{
		return referenceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReferenceType_Constant()
	{
		return (EReference)referenceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReferenceType_Property()
	{
		return (EReference)referenceTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getReferenceType_Operation()
	{
		return (EReference)referenceTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getService()
	{
		return serviceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CatalogueFactory getCatalogueFactory()
	{
		return (CatalogueFactory)getEFactoryInstance();
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
		catalogueEClass = createEClass(CATALOGUE);
		createEReference(catalogueEClass, CATALOGUE__NAMESPACE);

		catalogueDocumentRootEClass = createEClass(CATALOGUE_DOCUMENT_ROOT);
		createEAttribute(catalogueDocumentRootEClass, CATALOGUE_DOCUMENT_ROOT__MIXED);
		createEReference(catalogueDocumentRootEClass, CATALOGUE_DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(catalogueDocumentRootEClass, CATALOGUE_DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(catalogueDocumentRootEClass, CATALOGUE_DOCUMENT_ROOT__CATALOGUE);

		componentEClass = createEClass(COMPONENT);
		createEReference(componentEClass, COMPONENT__BASE);
		createEReference(componentEClass, COMPONENT__INTERFACE);
		createEReference(componentEClass, COMPONENT__ENTRY_POINT);
		createEReference(componentEClass, COMPONENT__EVENT_SINK);
		createEReference(componentEClass, COMPONENT__EVENT_SOURCE);
		createEReference(componentEClass, COMPONENT__FIELD);
		createEReference(componentEClass, COMPONENT__ASSOCIATION);
		createEReference(componentEClass, COMPONENT__CONTAINER);
		createEReference(componentEClass, COMPONENT__REFERENCE);

		containerEClass = createEClass(CONTAINER);
		createEReference(containerEClass, CONTAINER__TYPE);
		createEReference(containerEClass, CONTAINER__DEFAULT_COMPONENT);
		createEAttribute(containerEClass, CONTAINER__LOWER);
		createEAttribute(containerEClass, CONTAINER__UPPER);

		entryPointEClass = createEClass(ENTRY_POINT);
		createEReference(entryPointEClass, ENTRY_POINT__INPUT);
		createEReference(entryPointEClass, ENTRY_POINT__OUTPUT);

		eventSinkEClass = createEClass(EVENT_SINK);
		createEReference(eventSinkEClass, EVENT_SINK__TYPE);

		eventSourceEClass = createEClass(EVENT_SOURCE);
		createEReference(eventSourceEClass, EVENT_SOURCE__TYPE);
		createEAttribute(eventSourceEClass, EVENT_SOURCE__MULTICAST);

		eventTypeEClass = createEClass(EVENT_TYPE);
		createEReference(eventTypeEClass, EVENT_TYPE__EVENT_ARGS);

		interfaceEClass = createEClass(INTERFACE);
		createEReference(interfaceEClass, INTERFACE__BASE);

		modelEClass = createEClass(MODEL);

		namespaceEClass = createEClass(NAMESPACE);
		createEReference(namespaceEClass, NAMESPACE__NAMESPACE);
		createEReference(namespaceEClass, NAMESPACE__TYPE);

		referenceEClass = createEClass(REFERENCE);
		createEReference(referenceEClass, REFERENCE__INTERFACE);
		createEAttribute(referenceEClass, REFERENCE__LOWER);
		createEAttribute(referenceEClass, REFERENCE__UPPER);

		referenceTypeEClass = createEClass(REFERENCE_TYPE);
		createEReference(referenceTypeEClass, REFERENCE_TYPE__CONSTANT);
		createEReference(referenceTypeEClass, REFERENCE_TYPE__PROPERTY);
		createEReference(referenceTypeEClass, REFERENCE_TYPE__OPERATION);

		serviceEClass = createEClass(SERVICE);
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
		ElementsPackage theElementsPackage = (ElementsPackage)EPackage.Registry.INSTANCE.getEPackage(ElementsPackage.eNS_URI);
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		catalogueEClass.getESuperTypes().add(theElementsPackage.getDocument());
		componentEClass.getESuperTypes().add(this.getReferenceType());
		containerEClass.getESuperTypes().add(theElementsPackage.getNamedElement());
		entryPointEClass.getESuperTypes().add(theElementsPackage.getNamedElement());
		eventSinkEClass.getESuperTypes().add(theElementsPackage.getNamedElement());
		eventSourceEClass.getESuperTypes().add(theElementsPackage.getNamedElement());
		eventTypeEClass.getESuperTypes().add(theTypesPackage.getType());
		interfaceEClass.getESuperTypes().add(this.getReferenceType());
		modelEClass.getESuperTypes().add(this.getComponent());
		namespaceEClass.getESuperTypes().add(theElementsPackage.getNamedElement());
		referenceEClass.getESuperTypes().add(theElementsPackage.getNamedElement());
		referenceTypeEClass.getESuperTypes().add(theTypesPackage.getLanguageType());
		serviceEClass.getESuperTypes().add(this.getComponent());

		// Initialize classes and features; add operations and parameters
		initEClass(catalogueEClass, Catalogue.class, "Catalogue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCatalogue_Namespace(), this.getNamespace(), null, "namespace", null, 0, -1, Catalogue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(catalogueDocumentRootEClass, CatalogueDocumentRoot.class, "CatalogueDocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCatalogueDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, CatalogueDocumentRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCatalogueDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, CatalogueDocumentRoot.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCatalogueDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, CatalogueDocumentRoot.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCatalogueDocumentRoot_Catalogue(), this.getCatalogue(), null, "catalogue", null, 0, -2, CatalogueDocumentRoot.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(componentEClass, Component.class, "Component", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		EGenericType g1 = createEGenericType(theTypesPackage.getElementReference());
		EGenericType g2 = createEGenericType(this.getComponent());
		g1.getETypeArguments().add(g2);
		initEReference(getComponent_Base(), g1, null, "base", null, 0, 1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theTypesPackage.getElementReference());
		g2 = createEGenericType(this.getInterface());
		g1.getETypeArguments().add(g2);
		initEReference(getComponent_Interface(), g1, null, "interface", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_EntryPoint(), this.getEntryPoint(), null, "entryPoint", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_EventSink(), this.getEventSink(), null, "eventSink", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_EventSource(), this.getEventSource(), null, "eventSource", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_Field(), theTypesPackage.getField(), null, "field", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_Association(), theTypesPackage.getAssociation(), null, "association", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_Container(), this.getContainer(), null, "container", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponent_Reference(), this.getReference(), null, "reference", null, 0, -1, Component.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(containerEClass, org.eclipse.xsmp.smp.smdl.catalogue.Container.class, "Container", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theTypesPackage.getElementReference());
		g2 = createEGenericType(this.getReferenceType());
		g1.getETypeArguments().add(g2);
		initEReference(getContainer_Type(), g1, null, "type", null, 1, 1, org.eclipse.xsmp.smp.smdl.catalogue.Container.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theTypesPackage.getElementReference());
		g2 = createEGenericType(this.getComponent());
		g1.getETypeArguments().add(g2);
		initEReference(getContainer_DefaultComponent(), g1, null, "defaultComponent", null, 0, 1, org.eclipse.xsmp.smp.smdl.catalogue.Container.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContainer_Lower(), theXMLTypePackage.getLong(), "lower", "1", 0, 1, org.eclipse.xsmp.smp.smdl.catalogue.Container.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContainer_Upper(), theXMLTypePackage.getLong(), "upper", "1", 0, 1, org.eclipse.xsmp.smp.smdl.catalogue.Container.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entryPointEClass, EntryPoint.class, "EntryPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theTypesPackage.getElementReference());
		g2 = createEGenericType(theTypesPackage.getField());
		g1.getETypeArguments().add(g2);
		initEReference(getEntryPoint_Input(), g1, null, "input", null, 0, -1, EntryPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(theTypesPackage.getElementReference());
		g2 = createEGenericType(theTypesPackage.getField());
		g1.getETypeArguments().add(g2);
		initEReference(getEntryPoint_Output(), g1, null, "output", null, 0, -1, EntryPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventSinkEClass, EventSink.class, "EventSink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theTypesPackage.getElementReference());
		g2 = createEGenericType(this.getEventType());
		g1.getETypeArguments().add(g2);
		initEReference(getEventSink_Type(), g1, null, "type", null, 1, 1, EventSink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventSourceEClass, EventSource.class, "EventSource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theTypesPackage.getElementReference());
		g2 = createEGenericType(this.getEventType());
		g1.getETypeArguments().add(g2);
		initEReference(getEventSource_Type(), g1, null, "type", null, 1, 1, EventSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEventSource_Multicast(), theXMLTypePackage.getBoolean(), "multicast", "true", 0, 1, EventSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventTypeEClass, EventType.class, "EventType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theTypesPackage.getElementReference());
		g2 = createEGenericType(theTypesPackage.getSimpleType());
		g1.getETypeArguments().add(g2);
		initEReference(getEventType_EventArgs(), g1, null, "eventArgs", null, 0, 1, EventType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(interfaceEClass, Interface.class, "Interface", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theTypesPackage.getElementReference());
		g2 = createEGenericType(this.getInterface());
		g1.getETypeArguments().add(g2);
		initEReference(getInterface_Base(), g1, null, "base", null, 0, -1, Interface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(modelEClass, Model.class, "Model", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(namespaceEClass, Namespace.class, "Namespace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNamespace_Namespace(), this.getNamespace(), null, "namespace", null, 0, -1, Namespace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNamespace_Type(), theTypesPackage.getType(), null, "type", null, 0, -1, Namespace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referenceEClass, Reference.class, "Reference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(theTypesPackage.getElementReference());
		g2 = createEGenericType(this.getInterface());
		g1.getETypeArguments().add(g2);
		initEReference(getReference_Interface(), g1, null, "interface", null, 1, 1, Reference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReference_Lower(), theXMLTypePackage.getLong(), "lower", "1", 0, 1, Reference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReference_Upper(), theXMLTypePackage.getLong(), "upper", "1", 0, 1, Reference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referenceTypeEClass, ReferenceType.class, "ReferenceType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReferenceType_Constant(), theTypesPackage.getConstant(), null, "constant", null, 0, -1, ReferenceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getReferenceType_Property(), theTypesPackage.getProperty(), null, "property", null, 0, -1, ReferenceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getReferenceType_Operation(), theTypesPackage.getOperation(), null, "operation", null, 0, -1, ReferenceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(serviceEClass, Service.class, "Service", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

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
		  (catalogueEClass,
		   source,
		   new String[]
		   {
			   "name", "Catalogue",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getCatalogue_Namespace(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Namespace"
		   });
		addAnnotation
		  (catalogueDocumentRootEClass,
		   source,
		   new String[]
		   {
			   "name", "Catalogue",
			   "kind", "mixed"
		   });
		addAnnotation
		  (getCatalogueDocumentRoot_Mixed(),
		   source,
		   new String[]
		   {
			   "kind", "elementWildcard",
			   "name", ":mixed"
		   });
		addAnnotation
		  (getCatalogueDocumentRoot_XMLNSPrefixMap(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "xmlns:prefix"
		   });
		addAnnotation
		  (getCatalogueDocumentRoot_XSISchemaLocation(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "xsi:schemaLocation"
		   });
		addAnnotation
		  (getCatalogueDocumentRoot_Catalogue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Catalogue",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (componentEClass,
		   source,
		   new String[]
		   {
			   "name", "Component",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getComponent_Base(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Base"
		   });
		addAnnotation
		  (getComponent_Interface(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Interface"
		   });
		addAnnotation
		  (getComponent_EntryPoint(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "EntryPoint"
		   });
		addAnnotation
		  (getComponent_EventSink(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "EventSink"
		   });
		addAnnotation
		  (getComponent_EventSource(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "EventSource"
		   });
		addAnnotation
		  (getComponent_Field(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Field"
		   });
		addAnnotation
		  (getComponent_Association(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Association"
		   });
		addAnnotation
		  (getComponent_Container(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Container"
		   });
		addAnnotation
		  (getComponent_Reference(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Reference"
		   });
		addAnnotation
		  (containerEClass,
		   source,
		   new String[]
		   {
			   "name", "Container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getContainer_Type(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Type"
		   });
		addAnnotation
		  (getContainer_DefaultComponent(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "DefaultComponent"
		   });
		addAnnotation
		  (getContainer_Lower(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Lower"
		   });
		addAnnotation
		  (getContainer_Upper(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Upper"
		   });
		addAnnotation
		  (entryPointEClass,
		   source,
		   new String[]
		   {
			   "name", "EntryPoint",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getEntryPoint_Input(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Input"
		   });
		addAnnotation
		  (getEntryPoint_Output(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Output"
		   });
		addAnnotation
		  (eventSinkEClass,
		   source,
		   new String[]
		   {
			   "name", "EventSink",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getEventSink_Type(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Type"
		   });
		addAnnotation
		  (eventSourceEClass,
		   source,
		   new String[]
		   {
			   "name", "EventSource",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getEventSource_Type(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Type"
		   });
		addAnnotation
		  (getEventSource_Multicast(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Multicast"
		   });
		addAnnotation
		  (eventTypeEClass,
		   source,
		   new String[]
		   {
			   "name", "EventType",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getEventType_EventArgs(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "EventArgs"
		   });
		addAnnotation
		  (interfaceEClass,
		   source,
		   new String[]
		   {
			   "name", "Interface",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getInterface_Base(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Base"
		   });
		addAnnotation
		  (modelEClass,
		   source,
		   new String[]
		   {
			   "name", "Model",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (namespaceEClass,
		   source,
		   new String[]
		   {
			   "name", "Namespace",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getNamespace_Namespace(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Namespace"
		   });
		addAnnotation
		  (getNamespace_Type(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Type"
		   });
		addAnnotation
		  (referenceEClass,
		   source,
		   new String[]
		   {
			   "name", "Reference",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getReference_Interface(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Interface"
		   });
		addAnnotation
		  (getReference_Lower(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Lower"
		   });
		addAnnotation
		  (getReference_Upper(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Upper"
		   });
		addAnnotation
		  (referenceTypeEClass,
		   source,
		   new String[]
		   {
			   "name", "ReferenceType",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getReferenceType_Constant(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Constant"
		   });
		addAnnotation
		  (getReferenceType_Property(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Property"
		   });
		addAnnotation
		  (getReferenceType_Operation(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Operation"
		   });
		addAnnotation
		  (serviceEClass,
		   source,
		   new String[]
		   {
			   "name", "Service",
			   "kind", "elementOnly"
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
		  (getComponent_Base(),
		   source,
		   new String[]
		   {
			   "appinfo", "Catalogue:Component"
		   });
		addAnnotation
		  (getComponent_Interface(),
		   source,
		   new String[]
		   {
			   "appinfo", "Catalogue:Interface"
		   });
		addAnnotation
		  (getContainer_Type(),
		   source,
		   new String[]
		   {
			   "appinfo", "Catalogue:ReferenceType"
		   });
		addAnnotation
		  (getContainer_DefaultComponent(),
		   source,
		   new String[]
		   {
			   "appinfo", "Catalogue:Component"
		   });
		addAnnotation
		  (getEntryPoint_Input(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:Field"
		   });
		addAnnotation
		  (getEntryPoint_Output(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:Field"
		   });
		addAnnotation
		  (getEventSink_Type(),
		   source,
		   new String[]
		   {
			   "appinfo", "Catalogue:EventType"
		   });
		addAnnotation
		  (getEventSource_Type(),
		   source,
		   new String[]
		   {
			   "appinfo", "Catalogue:EventType"
		   });
		addAnnotation
		  (getEventType_EventArgs(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:SimpleType"
		   });
		addAnnotation
		  (getInterface_Base(),
		   source,
		   new String[]
		   {
			   "appinfo", "Catalogue:Interface"
		   });
		addAnnotation
		  (getReference_Interface(),
		   source,
		   new String[]
		   {
			   "appinfo", "Catalogue:Interface"
		   });
	}

} //CataloguePackageImpl
