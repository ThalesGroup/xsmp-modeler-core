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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.xsmp.smp.smdl.catalogue.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CatalogueFactoryImpl extends EFactoryImpl implements CatalogueFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CatalogueFactory init()
	{
		try
		{
			CatalogueFactory theCatalogueFactory = (CatalogueFactory)EPackage.Registry.INSTANCE.getEFactory(CataloguePackage.eNS_URI);
			if (theCatalogueFactory != null)
			{
				return theCatalogueFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CatalogueFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CatalogueFactoryImpl()
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
			case CataloguePackage.CATALOGUE: return createCatalogue();
			case CataloguePackage.CATALOGUE_DOCUMENT_ROOT: return createCatalogueDocumentRoot();
			case CataloguePackage.CONTAINER: return createContainer();
			case CataloguePackage.ENTRY_POINT: return createEntryPoint();
			case CataloguePackage.EVENT_SINK: return createEventSink();
			case CataloguePackage.EVENT_SOURCE: return createEventSource();
			case CataloguePackage.EVENT_TYPE: return createEventType();
			case CataloguePackage.INTERFACE: return createInterface();
			case CataloguePackage.MODEL: return createModel();
			case CataloguePackage.NAMESPACE: return createNamespace();
			case CataloguePackage.REFERENCE: return createReference();
			case CataloguePackage.SERVICE: return createService();
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
	public Catalogue createCatalogue()
	{
		CatalogueImpl catalogue = new CatalogueImpl();
		return catalogue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CatalogueDocumentRoot createCatalogueDocumentRoot()
	{
		CatalogueDocumentRootImpl catalogueDocumentRoot = new CatalogueDocumentRootImpl();
		return catalogueDocumentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.xsmp.smp.smdl.catalogue.Container createContainer()
	{
		ContainerImpl container = new ContainerImpl();
		return container;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EntryPoint createEntryPoint()
	{
		EntryPointImpl entryPoint = new EntryPointImpl();
		return entryPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EventSink createEventSink()
	{
		EventSinkImpl eventSink = new EventSinkImpl();
		return eventSink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EventSource createEventSource()
	{
		EventSourceImpl eventSource = new EventSourceImpl();
		return eventSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EventType createEventType()
	{
		EventTypeImpl eventType = new EventTypeImpl();
		return eventType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Interface createInterface()
	{
		InterfaceImpl interface_ = new InterfaceImpl();
		return interface_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Model createModel()
	{
		ModelImpl model = new ModelImpl();
		return model;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Namespace createNamespace()
	{
		NamespaceImpl namespace = new NamespaceImpl();
		return namespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Reference createReference()
	{
		ReferenceImpl reference = new ReferenceImpl();
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Service createService()
	{
		ServiceImpl service = new ServiceImpl();
		return service;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CataloguePackage getCataloguePackage()
	{
		return (CataloguePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CataloguePackage getPackage()
	{
		return CataloguePackage.eINSTANCE;
	}

} //CatalogueFactoryImpl
