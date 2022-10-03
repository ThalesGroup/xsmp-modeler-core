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
import org.eclipse.xsmp.smp.smdl.catalogue.Component;
import org.eclipse.xsmp.smp.smdl.catalogue.ReferenceType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ContainerImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ContainerImpl#getDefaultComponent <em>Default Component</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ContainerImpl#getLower <em>Lower</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ContainerImpl#getUpper <em>Upper</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContainerImpl extends NamedElementImpl implements org.eclipse.xsmp.smp.smdl.catalogue.Container
{
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected ElementReference<ReferenceType> type;

	/**
	 * The cached value of the '{@link #getDefaultComponent() <em>Default Component</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultComponent()
	 * @generated
	 * @ordered
	 */
	protected ElementReference<Component> defaultComponent;

	/**
	 * The default value of the '{@link #getLower() <em>Lower</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLower()
	 * @generated
	 * @ordered
	 */
	protected static final long LOWER_EDEFAULT = 1L;

	/**
	 * The cached value of the '{@link #getLower() <em>Lower</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLower()
	 * @generated
	 * @ordered
	 */
	protected long lower = LOWER_EDEFAULT;

	/**
	 * This is true if the Lower attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean lowerESet;

	/**
	 * The default value of the '{@link #getUpper() <em>Upper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpper()
	 * @generated
	 * @ordered
	 */
	protected static final long UPPER_EDEFAULT = 1L;

	/**
	 * The cached value of the '{@link #getUpper() <em>Upper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpper()
	 * @generated
	 * @ordered
	 */
	protected long upper = UPPER_EDEFAULT;

	/**
	 * This is true if the Upper attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean upperESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContainerImpl()
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
		return CataloguePackage.Literals.CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementReference<ReferenceType> getType()
	{
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetType(ElementReference<ReferenceType> newType, NotificationChain msgs)
	{
		ElementReference<ReferenceType> oldType = type;
		type = newType;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CataloguePackage.CONTAINER__TYPE, oldType, newType);
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
	public void setType(ElementReference<ReferenceType> newType)
	{
		if (newType != type)
		{
			NotificationChain msgs = null;
			if (type != null)
				msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CataloguePackage.CONTAINER__TYPE, null, msgs);
			if (newType != null)
				msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CataloguePackage.CONTAINER__TYPE, null, msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CataloguePackage.CONTAINER__TYPE, newType, newType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementReference<Component> getDefaultComponent()
	{
		return defaultComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefaultComponent(ElementReference<Component> newDefaultComponent, NotificationChain msgs)
	{
		ElementReference<Component> oldDefaultComponent = defaultComponent;
		defaultComponent = newDefaultComponent;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CataloguePackage.CONTAINER__DEFAULT_COMPONENT, oldDefaultComponent, newDefaultComponent);
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
	public void setDefaultComponent(ElementReference<Component> newDefaultComponent)
	{
		if (newDefaultComponent != defaultComponent)
		{
			NotificationChain msgs = null;
			if (defaultComponent != null)
				msgs = ((InternalEObject)defaultComponent).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CataloguePackage.CONTAINER__DEFAULT_COMPONENT, null, msgs);
			if (newDefaultComponent != null)
				msgs = ((InternalEObject)newDefaultComponent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CataloguePackage.CONTAINER__DEFAULT_COMPONENT, null, msgs);
			msgs = basicSetDefaultComponent(newDefaultComponent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CataloguePackage.CONTAINER__DEFAULT_COMPONENT, newDefaultComponent, newDefaultComponent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getLower()
	{
		return lower;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLower(long newLower)
	{
		long oldLower = lower;
		lower = newLower;
		boolean oldLowerESet = lowerESet;
		lowerESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CataloguePackage.CONTAINER__LOWER, oldLower, lower, !oldLowerESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetLower()
	{
		long oldLower = lower;
		boolean oldLowerESet = lowerESet;
		lower = LOWER_EDEFAULT;
		lowerESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CataloguePackage.CONTAINER__LOWER, oldLower, LOWER_EDEFAULT, oldLowerESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetLower()
	{
		return lowerESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getUpper()
	{
		return upper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUpper(long newUpper)
	{
		long oldUpper = upper;
		upper = newUpper;
		boolean oldUpperESet = upperESet;
		upperESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CataloguePackage.CONTAINER__UPPER, oldUpper, upper, !oldUpperESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetUpper()
	{
		long oldUpper = upper;
		boolean oldUpperESet = upperESet;
		upper = UPPER_EDEFAULT;
		upperESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CataloguePackage.CONTAINER__UPPER, oldUpper, UPPER_EDEFAULT, oldUpperESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetUpper()
	{
		return upperESet;
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
			case CataloguePackage.CONTAINER__TYPE:
				return basicSetType(null, msgs);
			case CataloguePackage.CONTAINER__DEFAULT_COMPONENT:
				return basicSetDefaultComponent(null, msgs);
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
			case CataloguePackage.CONTAINER__TYPE:
				return getType();
			case CataloguePackage.CONTAINER__DEFAULT_COMPONENT:
				return getDefaultComponent();
			case CataloguePackage.CONTAINER__LOWER:
				return getLower();
			case CataloguePackage.CONTAINER__UPPER:
				return getUpper();
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
			case CataloguePackage.CONTAINER__TYPE:
				setType((ElementReference<ReferenceType>)newValue);
				return;
			case CataloguePackage.CONTAINER__DEFAULT_COMPONENT:
				setDefaultComponent((ElementReference<Component>)newValue);
				return;
			case CataloguePackage.CONTAINER__LOWER:
				setLower((Long)newValue);
				return;
			case CataloguePackage.CONTAINER__UPPER:
				setUpper((Long)newValue);
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
			case CataloguePackage.CONTAINER__TYPE:
				setType((ElementReference<ReferenceType>)null);
				return;
			case CataloguePackage.CONTAINER__DEFAULT_COMPONENT:
				setDefaultComponent((ElementReference<Component>)null);
				return;
			case CataloguePackage.CONTAINER__LOWER:
				unsetLower();
				return;
			case CataloguePackage.CONTAINER__UPPER:
				unsetUpper();
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
			case CataloguePackage.CONTAINER__TYPE:
				return type != null;
			case CataloguePackage.CONTAINER__DEFAULT_COMPONENT:
				return defaultComponent != null;
			case CataloguePackage.CONTAINER__LOWER:
				return isSetLower();
			case CataloguePackage.CONTAINER__UPPER:
				return isSetUpper();
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
		result.append(" (lower: ");
		if (lowerESet) result.append(lower); else result.append("<unset>");
		result.append(", upper: ");
		if (upperESet) result.append(upper); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ContainerImpl
