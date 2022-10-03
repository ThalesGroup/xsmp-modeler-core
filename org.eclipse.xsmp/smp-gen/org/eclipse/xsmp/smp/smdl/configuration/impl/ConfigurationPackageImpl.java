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
package org.eclipse.xsmp.smp.smdl.configuration.impl;

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

import org.eclipse.xsmp.smp.smdl.configuration.ComponentConfiguration;
import org.eclipse.xsmp.smp.smdl.configuration.Configuration;
import org.eclipse.xsmp.smp.smdl.configuration.ConfigurationDocumentRoot;
import org.eclipse.xsmp.smp.smdl.configuration.ConfigurationFactory;
import org.eclipse.xsmp.smp.smdl.configuration.ConfigurationOfConfigurationUsage;
import org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage;
import org.eclipse.xsmp.smp.smdl.configuration.ConfigurationUsage;

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
public class ConfigurationPackageImpl extends EPackageImpl implements ConfigurationPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configurationDocumentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configurationOfConfigurationUsageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configurationUsageEClass = null;

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
	 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ConfigurationPackageImpl()
	{
		super(eNS_URI, ConfigurationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ConfigurationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ConfigurationPackage init()
	{
		if (isInited) return (ConfigurationPackage)EPackage.Registry.INSTANCE.getEPackage(ConfigurationPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredConfigurationPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		ConfigurationPackageImpl theConfigurationPackage = registeredConfigurationPackage instanceof ConfigurationPackageImpl ? (ConfigurationPackageImpl)registeredConfigurationPackage : new ConfigurationPackageImpl();

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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PackagePackage.eNS_URI);
		PackagePackageImpl thePackagePackage = (PackagePackageImpl)(registeredPackage instanceof PackagePackageImpl ? registeredPackage : PackagePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(XlinkPackage.eNS_URI);
		XlinkPackageImpl theXlinkPackage = (XlinkPackageImpl)(registeredPackage instanceof XlinkPackageImpl ? registeredPackage : XlinkPackage.eINSTANCE);

		// Create package meta-data objects
		theConfigurationPackage.createPackageContents();
		theElementsPackage.createPackageContents();
		theTypesPackage.createPackageContents();
		theCataloguePackage.createPackageContents();
		thePackagePackage.createPackageContents();
		theXlinkPackage.createPackageContents();

		// Initialize created meta-data
		theConfigurationPackage.initializePackageContents();
		theElementsPackage.initializePackageContents();
		theTypesPackage.initializePackageContents();
		theCataloguePackage.initializePackageContents();
		thePackagePackage.initializePackageContents();
		theXlinkPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theConfigurationPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ConfigurationPackage.eNS_URI, theConfigurationPackage);
		return theConfigurationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getComponentConfiguration()
	{
		return componentConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponentConfiguration_Include()
	{
		return (EReference)componentConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponentConfiguration_Component()
	{
		return (EReference)componentConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComponentConfiguration_FieldValue()
	{
		return (EReference)componentConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getComponentConfiguration_Path()
	{
		return (EAttribute)componentConfigurationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConfiguration()
	{
		return configurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConfiguration_Include()
	{
		return (EReference)configurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConfiguration_Component()
	{
		return (EReference)configurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConfigurationDocumentRoot()
	{
		return configurationDocumentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConfigurationDocumentRoot_Mixed()
	{
		return (EAttribute)configurationDocumentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConfigurationDocumentRoot_XMLNSPrefixMap()
	{
		return (EReference)configurationDocumentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConfigurationDocumentRoot_XSISchemaLocation()
	{
		return (EReference)configurationDocumentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConfigurationDocumentRoot_Configuration()
	{
		return (EReference)configurationDocumentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConfigurationOfConfigurationUsage()
	{
		return configurationOfConfigurationUsageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConfigurationOfConfigurationUsage_ConfigurationName()
	{
		return (EAttribute)configurationOfConfigurationUsageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConfigurationOfConfigurationUsage_Configuration()
	{
		return (EReference)configurationOfConfigurationUsageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConfigurationUsage()
	{
		return configurationUsageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConfigurationUsage_Configuration()
	{
		return (EReference)configurationUsageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConfigurationUsage_Path()
	{
		return (EAttribute)configurationUsageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConfigurationFactory getConfigurationFactory()
	{
		return (ConfigurationFactory)getEFactoryInstance();
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
		componentConfigurationEClass = createEClass(COMPONENT_CONFIGURATION);
		createEReference(componentConfigurationEClass, COMPONENT_CONFIGURATION__INCLUDE);
		createEReference(componentConfigurationEClass, COMPONENT_CONFIGURATION__COMPONENT);
		createEReference(componentConfigurationEClass, COMPONENT_CONFIGURATION__FIELD_VALUE);
		createEAttribute(componentConfigurationEClass, COMPONENT_CONFIGURATION__PATH);

		configurationEClass = createEClass(CONFIGURATION);
		createEReference(configurationEClass, CONFIGURATION__INCLUDE);
		createEReference(configurationEClass, CONFIGURATION__COMPONENT);

		configurationDocumentRootEClass = createEClass(CONFIGURATION_DOCUMENT_ROOT);
		createEAttribute(configurationDocumentRootEClass, CONFIGURATION_DOCUMENT_ROOT__MIXED);
		createEReference(configurationDocumentRootEClass, CONFIGURATION_DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(configurationDocumentRootEClass, CONFIGURATION_DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(configurationDocumentRootEClass, CONFIGURATION_DOCUMENT_ROOT__CONFIGURATION);

		configurationOfConfigurationUsageEClass = createEClass(CONFIGURATION_OF_CONFIGURATION_USAGE);
		createEAttribute(configurationOfConfigurationUsageEClass, CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION_NAME);
		createEReference(configurationOfConfigurationUsageEClass, CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION);

		configurationUsageEClass = createEClass(CONFIGURATION_USAGE);
		createEReference(configurationUsageEClass, CONFIGURATION_USAGE__CONFIGURATION);
		createEAttribute(configurationUsageEClass, CONFIGURATION_USAGE__PATH);
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
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		ElementsPackage theElementsPackage = (ElementsPackage)EPackage.Registry.INSTANCE.getEPackage(ElementsPackage.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		configurationEClass.getESuperTypes().add(theElementsPackage.getDocument());

		// Initialize classes and features; add operations and parameters
		initEClass(componentConfigurationEClass, ComponentConfiguration.class, "ComponentConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComponentConfiguration_Include(), this.getConfigurationUsage(), null, "include", null, 0, -1, ComponentConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentConfiguration_Component(), this.getComponentConfiguration(), null, "component", null, 0, -1, ComponentConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComponentConfiguration_FieldValue(), theTypesPackage.getValue(), null, "fieldValue", null, 0, -1, ComponentConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentConfiguration_Path(), theElementsPackage.getPath(), "path", null, 1, 1, ComponentConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(configurationEClass, Configuration.class, "Configuration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConfiguration_Include(), this.getConfigurationUsage(), null, "include", null, 0, -1, Configuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfiguration_Component(), this.getComponentConfiguration(), null, "component", null, 0, -1, Configuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(configurationDocumentRootEClass, ConfigurationDocumentRoot.class, "ConfigurationDocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConfigurationDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfigurationDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfigurationDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfigurationDocumentRoot_Configuration(), this.getConfiguration(), null, "configuration", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(configurationOfConfigurationUsageEClass, ConfigurationOfConfigurationUsage.class, "ConfigurationOfConfigurationUsage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConfigurationOfConfigurationUsage_ConfigurationName(), theXMLTypePackage.getString(), "configurationName", null, 0, 1, ConfigurationOfConfigurationUsage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfigurationOfConfigurationUsage_Configuration(), this.getConfiguration(), null, "configuration", null, 1, 1, ConfigurationOfConfigurationUsage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(configurationUsageEClass, ConfigurationUsage.class, "ConfigurationUsage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConfigurationUsage_Configuration(), this.getConfigurationOfConfigurationUsage(), null, "configuration", null, 1, 1, ConfigurationUsage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfigurationUsage_Path(), theElementsPackage.getPath(), "path", null, 0, 1, ConfigurationUsage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		  (componentConfigurationEClass,
		   source,
		   new String[]
		   {
			   "name", "ComponentConfiguration",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getComponentConfiguration_Include(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Include"
		   });
		addAnnotation
		  (getComponentConfiguration_Component(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Component"
		   });
		addAnnotation
		  (getComponentConfiguration_FieldValue(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "FieldValue"
		   });
		addAnnotation
		  (getComponentConfiguration_Path(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Path"
		   });
		addAnnotation
		  (configurationEClass,
		   source,
		   new String[]
		   {
			   "name", "Configuration",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getConfiguration_Include(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Include"
		   });
		addAnnotation
		  (getConfiguration_Component(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Component"
		   });
		addAnnotation
		  (configurationDocumentRootEClass,
		   source,
		   new String[]
		   {
			   "name", "",
			   "kind", "mixed"
		   });
		addAnnotation
		  (getConfigurationDocumentRoot_Mixed(),
		   source,
		   new String[]
		   {
			   "kind", "elementWildcard",
			   "name", ":mixed"
		   });
		addAnnotation
		  (getConfigurationDocumentRoot_XMLNSPrefixMap(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "xmlns:prefix"
		   });
		addAnnotation
		  (getConfigurationDocumentRoot_XSISchemaLocation(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "xsi:schemaLocation"
		   });
		addAnnotation
		  (getConfigurationDocumentRoot_Configuration(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Configuration",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (configurationOfConfigurationUsageEClass,
		   source,
		   new String[]
		   {
			   "name", "Configuration_._type",
			   "kind", "empty"
		   });
		addAnnotation
		  (getConfigurationOfConfigurationUsage_ConfigurationName(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "title",
			   "namespace", "http://www.w3.org/1999/xlink"
		   });
		addAnnotation
		  (getConfigurationOfConfigurationUsage_Configuration(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "href",
			   "namespace", "http://www.w3.org/1999/xlink"
		   });
		addAnnotation
		  (configurationUsageEClass,
		   source,
		   new String[]
		   {
			   "name", "ConfigurationUsage",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getConfigurationUsage_Configuration(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Configuration"
		   });
		addAnnotation
		  (getConfigurationUsage_Path(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Path"
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
		  (getConfigurationUsage_Configuration(),
		   source,
		   new String[]
		   {
			   "appinfo", "Configuration:Configuration"
		   });
	}

} //ConfigurationPackageImpl
