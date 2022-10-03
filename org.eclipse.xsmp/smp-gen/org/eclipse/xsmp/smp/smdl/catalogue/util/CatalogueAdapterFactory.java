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
package org.eclipse.xsmp.smp.smdl.catalogue.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.xsmp.smp.core.elements.Document;
import org.eclipse.xsmp.smp.core.elements.NamedElement;

import org.eclipse.xsmp.smp.core.types.LanguageType;
import org.eclipse.xsmp.smp.core.types.Type;
import org.eclipse.xsmp.smp.core.types.VisibilityElement;

import org.eclipse.xsmp.smp.smdl.catalogue.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage
 * @generated
 */
public class CatalogueAdapterFactory extends AdapterFactoryImpl
{
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CataloguePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CatalogueAdapterFactory()
	{
		if (modelPackage == null)
		{
			modelPackage = CataloguePackage.eINSTANCE;
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
	protected CatalogueSwitch<Adapter> modelSwitch =
		new CatalogueSwitch<Adapter>()
		{
			@Override
			public Adapter caseCatalogue(Catalogue object)
			{
				return createCatalogueAdapter();
			}
			@Override
			public Adapter caseCatalogueDocumentRoot(CatalogueDocumentRoot object)
			{
				return createCatalogueDocumentRootAdapter();
			}
			@Override
			public Adapter caseComponent(Component object)
			{
				return createComponentAdapter();
			}
			@Override
			public Adapter caseContainer(Container object)
			{
				return createContainerAdapter();
			}
			@Override
			public Adapter caseEntryPoint(EntryPoint object)
			{
				return createEntryPointAdapter();
			}
			@Override
			public Adapter caseEventSink(EventSink object)
			{
				return createEventSinkAdapter();
			}
			@Override
			public Adapter caseEventSource(EventSource object)
			{
				return createEventSourceAdapter();
			}
			@Override
			public Adapter caseEventType(EventType object)
			{
				return createEventTypeAdapter();
			}
			@Override
			public Adapter caseInterface(Interface object)
			{
				return createInterfaceAdapter();
			}
			@Override
			public Adapter caseModel(Model object)
			{
				return createModelAdapter();
			}
			@Override
			public Adapter caseNamespace(Namespace object)
			{
				return createNamespaceAdapter();
			}
			@Override
			public Adapter caseReference(Reference object)
			{
				return createReferenceAdapter();
			}
			@Override
			public Adapter caseReferenceType(ReferenceType object)
			{
				return createReferenceTypeAdapter();
			}
			@Override
			public Adapter caseService(Service object)
			{
				return createServiceAdapter();
			}
			@Override
			public Adapter caseNamedElement(NamedElement object)
			{
				return createNamedElementAdapter();
			}
			@Override
			public Adapter caseDocument(Document object)
			{
				return createDocumentAdapter();
			}
			@Override
			public Adapter caseVisibilityElement(VisibilityElement object)
			{
				return createVisibilityElementAdapter();
			}
			@Override
			public Adapter caseType(Type object)
			{
				return createTypeAdapter();
			}
			@Override
			public Adapter caseLanguageType(LanguageType object)
			{
				return createLanguageTypeAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.Catalogue <em>Catalogue</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.Catalogue
	 * @generated
	 */
	public Adapter createCatalogueAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.CatalogueDocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CatalogueDocumentRoot
	 * @generated
	 */
	public Adapter createCatalogueDocumentRootAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.Component <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.Component
	 * @generated
	 */
	public Adapter createComponentAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.Container <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.Container
	 * @generated
	 */
	public Adapter createContainerAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.EntryPoint <em>Entry Point</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.EntryPoint
	 * @generated
	 */
	public Adapter createEntryPointAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.EventSink <em>Event Sink</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.EventSink
	 * @generated
	 */
	public Adapter createEventSinkAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.EventSource <em>Event Source</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.EventSource
	 * @generated
	 */
	public Adapter createEventSourceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.EventType <em>Event Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.EventType
	 * @generated
	 */
	public Adapter createEventTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.Interface <em>Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.Interface
	 * @generated
	 */
	public Adapter createInterfaceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.Model
	 * @generated
	 */
	public Adapter createModelAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.Namespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.Namespace
	 * @generated
	 */
	public Adapter createNamespaceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.Reference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.Reference
	 * @generated
	 */
	public Adapter createReferenceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.ReferenceType <em>Reference Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.ReferenceType
	 * @generated
	 */
	public Adapter createReferenceTypeAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.smdl.catalogue.Service <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.Service
	 * @generated
	 */
	public Adapter createServiceAdapter()
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
	 * Creates a new adapter for an object of class '{@link org.eclipse.xsmp.smp.core.elements.Document <em>Document</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.xsmp.smp.core.elements.Document
	 * @generated
	 */
	public Adapter createDocumentAdapter()
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

} //CatalogueAdapterFactory
