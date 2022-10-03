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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xsmp.smp.core.types.Association;
import org.eclipse.xsmp.smp.core.types.ElementReference;
import org.eclipse.xsmp.smp.core.types.Field;

import org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage;
import org.eclipse.xsmp.smp.smdl.catalogue.Component;
import org.eclipse.xsmp.smp.smdl.catalogue.EntryPoint;
import org.eclipse.xsmp.smp.smdl.catalogue.EventSink;
import org.eclipse.xsmp.smp.smdl.catalogue.EventSource;
import org.eclipse.xsmp.smp.smdl.catalogue.Interface;
import org.eclipse.xsmp.smp.smdl.catalogue.Reference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ComponentImpl#getBase <em>Base</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ComponentImpl#getInterface <em>Interface</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ComponentImpl#getEntryPoint <em>Entry Point</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ComponentImpl#getEventSink <em>Event Sink</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ComponentImpl#getEventSource <em>Event Source</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ComponentImpl#getField <em>Field</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ComponentImpl#getAssociation <em>Association</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ComponentImpl#getContainer <em>Container</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ComponentImpl#getReference <em>Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ComponentImpl extends ReferenceTypeImpl implements Component
{
	/**
	 * The cached value of the '{@link #getBase() <em>Base</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase()
	 * @generated
	 * @ordered
	 */
	protected ElementReference<Component> base;

	/**
	 * The cached value of the '{@link #getInterface() <em>Interface</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterface()
	 * @generated
	 * @ordered
	 */
	protected EList<ElementReference<Interface>> interface_;

	/**
	 * The cached value of the '{@link #getEntryPoint() <em>Entry Point</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntryPoint()
	 * @generated
	 * @ordered
	 */
	protected EList<EntryPoint> entryPoint;

	/**
	 * The cached value of the '{@link #getEventSink() <em>Event Sink</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventSink()
	 * @generated
	 * @ordered
	 */
	protected EList<EventSink> eventSink;

	/**
	 * The cached value of the '{@link #getEventSource() <em>Event Source</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventSource()
	 * @generated
	 * @ordered
	 */
	protected EList<EventSource> eventSource;

	/**
	 * The cached value of the '{@link #getField() <em>Field</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getField()
	 * @generated
	 * @ordered
	 */
	protected EList<Field> field;

	/**
	 * The cached value of the '{@link #getAssociation() <em>Association</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssociation()
	 * @generated
	 * @ordered
	 */
	protected EList<Association> association;

	/**
	 * The cached value of the '{@link #getContainer() <em>Container</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainer()
	 * @generated
	 * @ordered
	 */
	protected EList<org.eclipse.xsmp.smp.smdl.catalogue.Container> container;

	/**
	 * The cached value of the '{@link #getReference() <em>Reference</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReference()
	 * @generated
	 * @ordered
	 */
	protected EList<Reference> reference;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return CataloguePackage.Literals.COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementReference<Component> getBase()
	{
		return base;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBase(ElementReference<Component> newBase, NotificationChain msgs)
	{
		ElementReference<Component> oldBase = base;
		base = newBase;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CataloguePackage.COMPONENT__BASE, oldBase, newBase);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBase(ElementReference<Component> newBase)
	{
		if (newBase != base)
		{
			NotificationChain msgs = null;
			if (base != null)
				msgs = ((InternalEObject)base).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CataloguePackage.COMPONENT__BASE, null, msgs);
			if (newBase != null)
				msgs = ((InternalEObject)newBase).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CataloguePackage.COMPONENT__BASE, null, msgs);
			msgs = basicSetBase(newBase, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CataloguePackage.COMPONENT__BASE, newBase, newBase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ElementReference<Interface>> getInterface()
	{
		if (interface_ == null)
		{
			interface_ = new EObjectContainmentEList<ElementReference<Interface>>(ElementReference.class, this, CataloguePackage.COMPONENT__INTERFACE);
		}
		return interface_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EntryPoint> getEntryPoint()
	{
		if (entryPoint == null)
		{
			entryPoint = new EObjectContainmentEList<EntryPoint>(EntryPoint.class, this, CataloguePackage.COMPONENT__ENTRY_POINT);
		}
		return entryPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EventSink> getEventSink()
	{
		if (eventSink == null)
		{
			eventSink = new EObjectContainmentEList<EventSink>(EventSink.class, this, CataloguePackage.COMPONENT__EVENT_SINK);
		}
		return eventSink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EventSource> getEventSource()
	{
		if (eventSource == null)
		{
			eventSource = new EObjectContainmentEList<EventSource>(EventSource.class, this, CataloguePackage.COMPONENT__EVENT_SOURCE);
		}
		return eventSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Field> getField()
	{
		if (field == null)
		{
			field = new EObjectContainmentEList<Field>(Field.class, this, CataloguePackage.COMPONENT__FIELD);
		}
		return field;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Association> getAssociation()
	{
		if (association == null)
		{
			association = new EObjectContainmentEList<Association>(Association.class, this, CataloguePackage.COMPONENT__ASSOCIATION);
		}
		return association;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<org.eclipse.xsmp.smp.smdl.catalogue.Container> getContainer()
	{
		if (container == null)
		{
			container = new EObjectContainmentEList<org.eclipse.xsmp.smp.smdl.catalogue.Container>(org.eclipse.xsmp.smp.smdl.catalogue.Container.class, this, CataloguePackage.COMPONENT__CONTAINER);
		}
		return container;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Reference> getReference()
	{
		if (reference == null)
		{
			reference = new EObjectContainmentEList<Reference>(Reference.class, this, CataloguePackage.COMPONENT__REFERENCE);
		}
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case CataloguePackage.COMPONENT__BASE:
				return basicSetBase(null, msgs);
			case CataloguePackage.COMPONENT__INTERFACE:
				return ((InternalEList<?>)getInterface()).basicRemove(otherEnd, msgs);
			case CataloguePackage.COMPONENT__ENTRY_POINT:
				return ((InternalEList<?>)getEntryPoint()).basicRemove(otherEnd, msgs);
			case CataloguePackage.COMPONENT__EVENT_SINK:
				return ((InternalEList<?>)getEventSink()).basicRemove(otherEnd, msgs);
			case CataloguePackage.COMPONENT__EVENT_SOURCE:
				return ((InternalEList<?>)getEventSource()).basicRemove(otherEnd, msgs);
			case CataloguePackage.COMPONENT__FIELD:
				return ((InternalEList<?>)getField()).basicRemove(otherEnd, msgs);
			case CataloguePackage.COMPONENT__ASSOCIATION:
				return ((InternalEList<?>)getAssociation()).basicRemove(otherEnd, msgs);
			case CataloguePackage.COMPONENT__CONTAINER:
				return ((InternalEList<?>)getContainer()).basicRemove(otherEnd, msgs);
			case CataloguePackage.COMPONENT__REFERENCE:
				return ((InternalEList<?>)getReference()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case CataloguePackage.COMPONENT__BASE:
				return getBase();
			case CataloguePackage.COMPONENT__INTERFACE:
				return getInterface();
			case CataloguePackage.COMPONENT__ENTRY_POINT:
				return getEntryPoint();
			case CataloguePackage.COMPONENT__EVENT_SINK:
				return getEventSink();
			case CataloguePackage.COMPONENT__EVENT_SOURCE:
				return getEventSource();
			case CataloguePackage.COMPONENT__FIELD:
				return getField();
			case CataloguePackage.COMPONENT__ASSOCIATION:
				return getAssociation();
			case CataloguePackage.COMPONENT__CONTAINER:
				return getContainer();
			case CataloguePackage.COMPONENT__REFERENCE:
				return getReference();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case CataloguePackage.COMPONENT__BASE:
				setBase((ElementReference<Component>)newValue);
				return;
			case CataloguePackage.COMPONENT__INTERFACE:
				getInterface().clear();
				getInterface().addAll((Collection<? extends ElementReference<Interface>>)newValue);
				return;
			case CataloguePackage.COMPONENT__ENTRY_POINT:
				getEntryPoint().clear();
				getEntryPoint().addAll((Collection<? extends EntryPoint>)newValue);
				return;
			case CataloguePackage.COMPONENT__EVENT_SINK:
				getEventSink().clear();
				getEventSink().addAll((Collection<? extends EventSink>)newValue);
				return;
			case CataloguePackage.COMPONENT__EVENT_SOURCE:
				getEventSource().clear();
				getEventSource().addAll((Collection<? extends EventSource>)newValue);
				return;
			case CataloguePackage.COMPONENT__FIELD:
				getField().clear();
				getField().addAll((Collection<? extends Field>)newValue);
				return;
			case CataloguePackage.COMPONENT__ASSOCIATION:
				getAssociation().clear();
				getAssociation().addAll((Collection<? extends Association>)newValue);
				return;
			case CataloguePackage.COMPONENT__CONTAINER:
				getContainer().clear();
				getContainer().addAll((Collection<? extends org.eclipse.xsmp.smp.smdl.catalogue.Container>)newValue);
				return;
			case CataloguePackage.COMPONENT__REFERENCE:
				getReference().clear();
				getReference().addAll((Collection<? extends Reference>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case CataloguePackage.COMPONENT__BASE:
				setBase((ElementReference<Component>)null);
				return;
			case CataloguePackage.COMPONENT__INTERFACE:
				getInterface().clear();
				return;
			case CataloguePackage.COMPONENT__ENTRY_POINT:
				getEntryPoint().clear();
				return;
			case CataloguePackage.COMPONENT__EVENT_SINK:
				getEventSink().clear();
				return;
			case CataloguePackage.COMPONENT__EVENT_SOURCE:
				getEventSource().clear();
				return;
			case CataloguePackage.COMPONENT__FIELD:
				getField().clear();
				return;
			case CataloguePackage.COMPONENT__ASSOCIATION:
				getAssociation().clear();
				return;
			case CataloguePackage.COMPONENT__CONTAINER:
				getContainer().clear();
				return;
			case CataloguePackage.COMPONENT__REFERENCE:
				getReference().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case CataloguePackage.COMPONENT__BASE:
				return base != null;
			case CataloguePackage.COMPONENT__INTERFACE:
				return interface_ != null && !interface_.isEmpty();
			case CataloguePackage.COMPONENT__ENTRY_POINT:
				return entryPoint != null && !entryPoint.isEmpty();
			case CataloguePackage.COMPONENT__EVENT_SINK:
				return eventSink != null && !eventSink.isEmpty();
			case CataloguePackage.COMPONENT__EVENT_SOURCE:
				return eventSource != null && !eventSource.isEmpty();
			case CataloguePackage.COMPONENT__FIELD:
				return field != null && !field.isEmpty();
			case CataloguePackage.COMPONENT__ASSOCIATION:
				return association != null && !association.isEmpty();
			case CataloguePackage.COMPONENT__CONTAINER:
				return container != null && !container.isEmpty();
			case CataloguePackage.COMPONENT__REFERENCE:
				return reference != null && !reference.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ComponentImpl
