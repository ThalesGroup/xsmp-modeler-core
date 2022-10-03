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

import org.eclipse.xsmp.smp.core.elements.impl.NamedElementImpl;

import org.eclipse.xsmp.smp.core.types.ElementReference;

import org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage;
import org.eclipse.xsmp.smp.smdl.catalogue.EventSource;
import org.eclipse.xsmp.smp.smdl.catalogue.EventType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.EventSourceImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.EventSourceImpl#isMulticast <em>Multicast</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EventSourceImpl extends NamedElementImpl implements EventSource
{
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected ElementReference<EventType> type;

	/**
	 * The default value of the '{@link #isMulticast() <em>Multicast</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMulticast()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MULTICAST_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isMulticast() <em>Multicast</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMulticast()
	 * @generated
	 * @ordered
	 */
	protected boolean multicast = MULTICAST_EDEFAULT;

	/**
	 * This is true if the Multicast attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean multicastESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventSourceImpl()
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
		return CataloguePackage.Literals.EVENT_SOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementReference<EventType> getType()
	{
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetType(ElementReference<EventType> newType, NotificationChain msgs)
	{
		ElementReference<EventType> oldType = type;
		type = newType;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CataloguePackage.EVENT_SOURCE__TYPE, oldType, newType);
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
	public void setType(ElementReference<EventType> newType)
	{
		if (newType != type)
		{
			NotificationChain msgs = null;
			if (type != null)
				msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CataloguePackage.EVENT_SOURCE__TYPE, null, msgs);
			if (newType != null)
				msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CataloguePackage.EVENT_SOURCE__TYPE, null, msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CataloguePackage.EVENT_SOURCE__TYPE, newType, newType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isMulticast()
	{
		return multicast;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMulticast(boolean newMulticast)
	{
		boolean oldMulticast = multicast;
		multicast = newMulticast;
		boolean oldMulticastESet = multicastESet;
		multicastESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CataloguePackage.EVENT_SOURCE__MULTICAST, oldMulticast, multicast, !oldMulticastESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetMulticast()
	{
		boolean oldMulticast = multicast;
		boolean oldMulticastESet = multicastESet;
		multicast = MULTICAST_EDEFAULT;
		multicastESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CataloguePackage.EVENT_SOURCE__MULTICAST, oldMulticast, MULTICAST_EDEFAULT, oldMulticastESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetMulticast()
	{
		return multicastESet;
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
			case CataloguePackage.EVENT_SOURCE__TYPE:
				return basicSetType(null, msgs);
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
			case CataloguePackage.EVENT_SOURCE__TYPE:
				return getType();
			case CataloguePackage.EVENT_SOURCE__MULTICAST:
				return isMulticast();
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
			case CataloguePackage.EVENT_SOURCE__TYPE:
				setType((ElementReference<EventType>)newValue);
				return;
			case CataloguePackage.EVENT_SOURCE__MULTICAST:
				setMulticast((Boolean)newValue);
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
			case CataloguePackage.EVENT_SOURCE__TYPE:
				setType((ElementReference<EventType>)null);
				return;
			case CataloguePackage.EVENT_SOURCE__MULTICAST:
				unsetMulticast();
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
			case CataloguePackage.EVENT_SOURCE__TYPE:
				return type != null;
			case CataloguePackage.EVENT_SOURCE__MULTICAST:
				return isSetMulticast();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (multicast: ");
		if (multicastESet) result.append(multicast); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //EventSourceImpl
