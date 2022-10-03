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
package org.eclipse.xsmp.smp.smdl.package_.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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

import org.eclipse.xsmp.smp.smdl.package_.DependencyOfPackage;
import org.eclipse.xsmp.smp.smdl.package_.ImplementationOfPackage;
import org.eclipse.xsmp.smp.smdl.package_.PackageDocumentRoot;
import org.eclipse.xsmp.smp.smdl.package_.PackageFactory;
import org.eclipse.xsmp.smp.smdl.package_.PackagePackage;

import org.eclipse.xsmp.smp.xlink.XlinkPackage;

import org.eclipse.xsmp.smp.xlink.impl.XlinkPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PackagePackageImpl extends EPackageImpl implements PackagePackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dependencyOfPackageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass implementationOfPackageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass packageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass packageDocumentRootEClass = null;

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
	 * @see org.eclipse.xsmp.smp.smdl.package_.PackagePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PackagePackageImpl()
	{
		super(eNS_URI, PackageFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link PackagePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PackagePackage init()
	{
		if (isInited) return (PackagePackage)EPackage.Registry.INSTANCE.getEPackage(PackagePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredPackagePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		PackagePackageImpl thePackagePackage = registeredPackagePackage instanceof PackagePackageImpl ? (PackagePackageImpl)registeredPackagePackage : new PackagePackageImpl();

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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(XlinkPackage.eNS_URI);
		XlinkPackageImpl theXlinkPackage = (XlinkPackageImpl)(registeredPackage instanceof XlinkPackageImpl ? registeredPackage : XlinkPackage.eINSTANCE);

		// Create package meta-data objects
		thePackagePackage.createPackageContents();
		theElementsPackage.createPackageContents();
		theTypesPackage.createPackageContents();
		theCataloguePackage.createPackageContents();
		theConfigurationPackage.createPackageContents();
		theXlinkPackage.createPackageContents();

		// Initialize created meta-data
		thePackagePackage.initializePackageContents();
		theElementsPackage.initializePackageContents();
		theTypesPackage.initializePackageContents();
		theCataloguePackage.initializePackageContents();
		theConfigurationPackage.initializePackageContents();
		theXlinkPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePackagePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PackagePackage.eNS_URI, thePackagePackage);
		return thePackagePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDependencyOfPackage()
	{
		return dependencyOfPackageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDependencyOfPackage_PackageName()
	{
		return (EAttribute)dependencyOfPackageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDependencyOfPackage_Package()
	{
		return (EReference)dependencyOfPackageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getImplementationOfPackage()
	{
		return implementationOfPackageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getImplementationOfPackage_TypeName()
	{
		return (EAttribute)implementationOfPackageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getImplementationOfPackage_Type()
	{
		return (EReference)implementationOfPackageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPackage()
	{
		return packageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPackage_Implementation()
	{
		return (EReference)packageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPackage_Dependency()
	{
		return (EReference)packageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPackageDocumentRoot()
	{
		return packageDocumentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPackageDocumentRoot_Mixed()
	{
		return (EAttribute)packageDocumentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPackageDocumentRoot_XMLNSPrefixMap()
	{
		return (EReference)packageDocumentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPackageDocumentRoot_XSISchemaLocation()
	{
		return (EReference)packageDocumentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPackageDocumentRoot_Package()
	{
		return (EReference)packageDocumentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PackageFactory getPackageFactory()
	{
		return (PackageFactory)getEFactoryInstance();
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
		dependencyOfPackageEClass = createEClass(DEPENDENCY_OF_PACKAGE);
		createEAttribute(dependencyOfPackageEClass, DEPENDENCY_OF_PACKAGE__PACKAGE_NAME);
		createEReference(dependencyOfPackageEClass, DEPENDENCY_OF_PACKAGE__PACKAGE);

		implementationOfPackageEClass = createEClass(IMPLEMENTATION_OF_PACKAGE);
		createEAttribute(implementationOfPackageEClass, IMPLEMENTATION_OF_PACKAGE__TYPE_NAME);
		createEReference(implementationOfPackageEClass, IMPLEMENTATION_OF_PACKAGE__TYPE);

		packageEClass = createEClass(PACKAGE);
		createEReference(packageEClass, PACKAGE__IMPLEMENTATION);
		createEReference(packageEClass, PACKAGE__DEPENDENCY);

		packageDocumentRootEClass = createEClass(PACKAGE_DOCUMENT_ROOT);
		createEAttribute(packageDocumentRootEClass, PACKAGE_DOCUMENT_ROOT__MIXED);
		createEReference(packageDocumentRootEClass, PACKAGE_DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(packageDocumentRootEClass, PACKAGE_DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(packageDocumentRootEClass, PACKAGE_DOCUMENT_ROOT__PACKAGE);
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
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		ElementsPackage theElementsPackage = (ElementsPackage)EPackage.Registry.INSTANCE.getEPackage(ElementsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		packageEClass.getESuperTypes().add(theElementsPackage.getDocument());

		// Initialize classes and features; add operations and parameters
		initEClass(dependencyOfPackageEClass, DependencyOfPackage.class, "DependencyOfPackage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDependencyOfPackage_PackageName(), theXMLTypePackage.getString(), "packageName", null, 0, 1, DependencyOfPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDependencyOfPackage_Package(), this.getPackage(), null, "package", null, 1, 1, DependencyOfPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(implementationOfPackageEClass, ImplementationOfPackage.class, "ImplementationOfPackage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImplementationOfPackage_TypeName(), theXMLTypePackage.getString(), "typeName", null, 0, 1, ImplementationOfPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getImplementationOfPackage_Type(), theTypesPackage.getType(), null, "type", null, 1, 1, ImplementationOfPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(packageEClass, org.eclipse.xsmp.smp.smdl.package_.Package.class, "Package", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPackage_Implementation(), this.getImplementationOfPackage(), null, "implementation", null, 0, -1, org.eclipse.xsmp.smp.smdl.package_.Package.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackage_Dependency(), this.getDependencyOfPackage(), null, "dependency", null, 0, -1, org.eclipse.xsmp.smp.smdl.package_.Package.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(packageDocumentRootEClass, PackageDocumentRoot.class, "PackageDocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPackageDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackageDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackageDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPackageDocumentRoot_Package(), this.getPackage(), null, "package", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

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
		  (dependencyOfPackageEClass,
		   source,
		   new String[]
		   {
			   "name", "Dependency_._type",
			   "kind", "empty"
		   });
		addAnnotation
		  (getDependencyOfPackage_PackageName(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "title",
			   "namespace", "http://www.w3.org/1999/xlink"
		   });
		addAnnotation
		  (getDependencyOfPackage_Package(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "href",
			   "namespace", "http://www.w3.org/1999/xlink"
		   });
		addAnnotation
		  (implementationOfPackageEClass,
		   source,
		   new String[]
		   {
			   "name", "Implementation_._type",
			   "kind", "empty"
		   });
		addAnnotation
		  (getImplementationOfPackage_TypeName(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "title",
			   "namespace", "http://www.w3.org/1999/xlink"
		   });
		addAnnotation
		  (getImplementationOfPackage_Type(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "href",
			   "namespace", "http://www.w3.org/1999/xlink"
		   });
		addAnnotation
		  (packageEClass,
		   source,
		   new String[]
		   {
			   "name", "Package",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getPackage_Implementation(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Implementation"
		   });
		addAnnotation
		  (getPackage_Dependency(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Dependency"
		   });
		addAnnotation
		  (packageDocumentRootEClass,
		   source,
		   new String[]
		   {
			   "name", "",
			   "kind", "mixed"
		   });
		addAnnotation
		  (getPackageDocumentRoot_Mixed(),
		   source,
		   new String[]
		   {
			   "kind", "elementWildcard",
			   "name", ":mixed"
		   });
		addAnnotation
		  (getPackageDocumentRoot_XMLNSPrefixMap(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "xmlns:prefix"
		   });
		addAnnotation
		  (getPackageDocumentRoot_XSISchemaLocation(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "xsi:schemaLocation"
		   });
		addAnnotation
		  (getPackageDocumentRoot_Package(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Package",
			   "namespace", "##targetNamespace"
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
		  (getPackage_Implementation(),
		   source,
		   new String[]
		   {
			   "appinfo", "Types:Type"
		   });
		addAnnotation
		  (getPackage_Dependency(),
		   source,
		   new String[]
		   {
			   "appinfo", "Package:Package"
		   });
	}

} //PackagePackageImpl
