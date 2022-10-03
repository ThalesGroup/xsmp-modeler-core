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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.xsmp.smp.core.types.ElementReference;
import org.eclipse.xsmp.smp.core.types.SimpleType;

import org.eclipse.xsmp.smp.core.types.impl.TypeImpl;

import org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage;
import org.eclipse.xsmp.smp.smdl.catalogue.EventType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.EventTypeImpl#getEventArgs <em>Event Args</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EventTypeImpl extends TypeImpl implements EventType
{
	/**
	 * The cached value of the '{@link #getEventArgs() <em>Event Args</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventArgs()
	 * @generated
	 * @ordered
	 */
	protected ElementReference<SimpleType> eventArgs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventTypeImpl()
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
		return CataloguePackage.Literals.EVENT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementReference<SimpleType> getEventArgs()
	{
		return eventArgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEventArgs(ElementReference<SimpleType> newEventArgs, NotificationChain msgs)
	{
		ElementReference<SimpleType> oldEventArgs = eventArgs;
		eventArgs = newEventArgs;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CataloguePackage.EVENT_TYPE__EVENT_ARGS, oldEventArgs, newEventArgs);
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
	public void setEventArgs(ElementReference<SimpleType> newEventArgs)
	{
		if (newEventArgs != eventArgs)
		{
			NotificationChain msgs = null;
			if (eventArgs != null)
				msgs = ((InternalEObject)eventArgs).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CataloguePackage.EVENT_TYPE__EVENT_ARGS, null, msgs);
			if (newEventArgs != null)
				msgs = ((InternalEObject)newEventArgs).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CataloguePackage.EVENT_TYPE__EVENT_ARGS, null, msgs);
			msgs = basicSetEventArgs(newEventArgs, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CataloguePackage.EVENT_TYPE__EVENT_ARGS, newEventArgs, newEventArgs));
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
			case CataloguePackage.EVENT_TYPE__EVENT_ARGS:
				return basicSetEventArgs(null, msgs);
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
			case CataloguePackage.EVENT_TYPE__EVENT_ARGS:
				return getEventArgs();
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
			case CataloguePackage.EVENT_TYPE__EVENT_ARGS:
				setEventArgs((ElementReference<SimpleType>)newValue);
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
			case CataloguePackage.EVENT_TYPE__EVENT_ARGS:
				setEventArgs((ElementReference<SimpleType>)null);
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
			case CataloguePackage.EVENT_TYPE__EVENT_ARGS:
				return eventArgs != null;
		}
		return super.eIsSet(featureID);
	}

} //EventTypeImpl
