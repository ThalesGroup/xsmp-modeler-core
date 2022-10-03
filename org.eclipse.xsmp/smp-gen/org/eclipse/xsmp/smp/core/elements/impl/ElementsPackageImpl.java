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
package org.eclipse.xsmp.smp.core.elements.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.xsmp.smp.core.elements.Comment;
import org.eclipse.xsmp.smp.core.elements.Document;
import org.eclipse.xsmp.smp.core.elements.Documentation;
import org.eclipse.xsmp.smp.core.elements.ElementsFactory;
import org.eclipse.xsmp.smp.core.elements.ElementsPackage;
import org.eclipse.xsmp.smp.core.elements.Metadata;
import org.eclipse.xsmp.smp.core.elements.NamedElement;

import org.eclipse.xsmp.smp.core.elements.util.ElementsValidator;

import org.eclipse.xsmp.smp.core.types.TypesPackage;

import org.eclipse.xsmp.smp.core.types.impl.TypesPackageImpl;

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
public class ElementsPackageImpl extends EPackageImpl implements ElementsPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metadataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType descriptionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType identifierEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType nameEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pathEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType uuidEDataType = null;

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
	 * @see org.eclipse.xsmp.smp.core.elements.ElementsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ElementsPackageImpl()
	{
		super(eNS_URI, ElementsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ElementsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ElementsPackage init()
	{
		if (isInited) return (ElementsPackage)EPackage.Registry.INSTANCE.getEPackage(ElementsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredElementsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		ElementsPackageImpl theElementsPackage = registeredElementsPackage instanceof ElementsPackageImpl ? (ElementsPackageImpl)registeredElementsPackage : new ElementsPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		TypesPackageImpl theTypesPackage = (TypesPackageImpl)(registeredPackage instanceof TypesPackageImpl ? registeredPackage : TypesPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(CataloguePackage.eNS_URI);
		CataloguePackageImpl theCataloguePackage = (CataloguePackageImpl)(registeredPackage instanceof CataloguePackageImpl ? registeredPackage : CataloguePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ConfigurationPackage.eNS_URI);
		ConfigurationPackageImpl theConfigurationPackage = (ConfigurationPackageImpl)(registeredPackage instanceof ConfigurationPackageImpl ? registeredPackage : ConfigurationPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PackagePackage.eNS_URI);
		PackagePackageImpl thePackagePackage = (PackagePackageImpl)(registeredPackage instanceof PackagePackageImpl ? registeredPackage : PackagePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(XlinkPackage.eNS_URI);
		XlinkPackageImpl theXlinkPackage = (XlinkPackageImpl)(registeredPackage instanceof XlinkPackageImpl ? registeredPackage : XlinkPackage.eINSTANCE);

		// Create package meta-data objects
		theElementsPackage.createPackageContents();
		theTypesPackage.createPackageContents();
		theCataloguePackage.createPackageContents();
		theConfigurationPackage.createPackageContents();
		thePackagePackage.createPackageContents();
		theXlinkPackage.createPackageContents();

		// Initialize created meta-data
		theElementsPackage.initializePackageContents();
		theTypesPackage.initializePackageContents();
		theCataloguePackage.initializePackageContents();
		theConfigurationPackage.initializePackageContents();
		thePackagePackage.initializePackageContents();
		theXlinkPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theElementsPackage,
			 new EValidator.Descriptor()
			 {
				 @Override
				 public EValidator getEValidator()
				 {
					 return ElementsValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theElementsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ElementsPackage.eNS_URI, theElementsPackage);
		return theElementsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getComment()
	{
		return commentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDocument()
	{
		return documentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocument_Title()
	{
		return (EAttribute)documentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocument_Date()
	{
		return (EAttribute)documentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocument_Creator()
	{
		return (EAttribute)documentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDocument_Version()
	{
		return (EAttribute)documentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDocumentation()
	{
		return documentationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDocumentation_Resource()
	{
		return (EReference)documentationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMetadata()
	{
		return metadataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNamedElement()
	{
		return namedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_Description()
	{
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNamedElement_Metadata()
	{
		return (EReference)namedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_Id()
	{
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_Name()
	{
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDescription()
	{
		return descriptionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getIdentifier()
	{
		return identifierEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getName_()
	{
		return nameEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getPath()
	{
		return pathEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getUUID()
	{
		return uuidEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementsFactory getElementsFactory()
	{
		return (ElementsFactory)getEFactoryInstance();
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
		commentEClass = createEClass(COMMENT);

		documentEClass = createEClass(DOCUMENT);
		createEAttribute(documentEClass, DOCUMENT__TITLE);
		createEAttribute(documentEClass, DOCUMENT__DATE);
		createEAttribute(documentEClass, DOCUMENT__CREATOR);
		createEAttribute(documentEClass, DOCUMENT__VERSION);

		documentationEClass = createEClass(DOCUMENTATION);
		createEReference(documentationEClass, DOCUMENTATION__RESOURCE);

		metadataEClass = createEClass(METADATA);

		namedElementEClass = createEClass(NAMED_ELEMENT);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__DESCRIPTION);
		createEReference(namedElementEClass, NAMED_ELEMENT__METADATA);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__ID);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);

		// Create data types
		descriptionEDataType = createEDataType(DESCRIPTION);
		identifierEDataType = createEDataType(IDENTIFIER);
		nameEDataType = createEDataType(NAME);
		pathEDataType = createEDataType(PATH);
		uuidEDataType = createEDataType(UUID);
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
		XlinkPackage theXlinkPackage = (XlinkPackage)EPackage.Registry.INSTANCE.getEPackage(XlinkPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		commentEClass.getESuperTypes().add(this.getMetadata());
		documentEClass.getESuperTypes().add(this.getNamedElement());
		documentationEClass.getESuperTypes().add(this.getMetadata());
		metadataEClass.getESuperTypes().add(this.getNamedElement());

		// Initialize classes and features; add operations and parameters
		initEClass(commentEClass, Comment.class, "Comment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(documentEClass, Document.class, "Document", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocument_Title(), theXMLTypePackage.getString(), "title", null, 0, 1, Document.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocument_Date(), theXMLTypePackage.getDateTime(), "date", null, 0, 1, Document.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocument_Creator(), theXMLTypePackage.getString(), "creator", null, 0, 1, Document.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocument_Version(), theXMLTypePackage.getString(), "version", null, 0, 1, Document.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentationEClass, Documentation.class, "Documentation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDocumentation_Resource(), theXlinkPackage.getSimpleLink(), null, "resource", null, 0, -1, Documentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metadataEClass, Metadata.class, "Metadata", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(namedElementEClass, NamedElement.class, "NamedElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedElement_Description(), this.getDescription(), "description", null, 0, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNamedElement_Metadata(), this.getMetadata(), null, "metadata", null, 0, -1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedElement_Id(), this.getIdentifier(), "id", null, 1, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getNamedElement_Name(), this.getName_(), "name", null, 1, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(descriptionEDataType, String.class, "Description", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(identifierEDataType, String.class, "Identifier", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(nameEDataType, String.class, "Name", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pathEDataType, String.class, "Path", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(uuidEDataType, String.class, "UUID", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

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
		  (commentEClass,
		   source,
		   new String[]
		   {
			   "name", "Comment",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (descriptionEDataType,
		   source,
		   new String[]
		   {
			   "name", "Description",
			   "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });
		addAnnotation
		  (documentEClass,
		   source,
		   new String[]
		   {
			   "name", "Document",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getDocument_Title(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Title"
		   });
		addAnnotation
		  (getDocument_Date(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Date"
		   });
		addAnnotation
		  (getDocument_Creator(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Creator"
		   });
		addAnnotation
		  (getDocument_Version(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Version"
		   });
		addAnnotation
		  (documentationEClass,
		   source,
		   new String[]
		   {
			   "name", "Documentation",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getDocumentation_Resource(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Resource"
		   });
		addAnnotation
		  (identifierEDataType,
		   source,
		   new String[]
		   {
			   "name", "Identifier",
			   "baseType", "http://www.eclipse.org/emf/2003/XMLType#ID",
			   "minLength", "1"
		   });
		addAnnotation
		  (metadataEClass,
		   source,
		   new String[]
		   {
			   "name", "Metadata",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (nameEDataType,
		   source,
		   new String[]
		   {
			   "name", "Name",
			   "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
			   "pattern", "[a-zA-Z][a-zA-Z0-9_]*"
		   });
		addAnnotation
		  (namedElementEClass,
		   source,
		   new String[]
		   {
			   "name", "NamedElement",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getNamedElement_Description(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Description"
		   });
		addAnnotation
		  (getNamedElement_Metadata(),
		   source,
		   new String[]
		   {
			   "kind", "element",
			   "name", "Metadata"
		   });
		addAnnotation
		  (getNamedElement_Id(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Id"
		   });
		addAnnotation
		  (getNamedElement_Name(),
		   source,
		   new String[]
		   {
			   "kind", "attribute",
			   "name", "Name"
		   });
		addAnnotation
		  (pathEDataType,
		   source,
		   new String[]
		   {
			   "name", "Path",
			   "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
			   "pattern", "[a-zA-Z\\./][a-zA-Z0-9_\\./\\[\\]]*"
		   });
		addAnnotation
		  (uuidEDataType,
		   source,
		   new String[]
		   {
			   "name", "UUID",
			   "baseType", "http://www.eclipse.org/emf/2003/XMLType#string",
			   "pattern", "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"
		   });
	}

} //ElementsPackageImpl
