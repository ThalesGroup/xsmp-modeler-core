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
import org.eclipse.xsmp.smp.smdl.catalogue.Interface;
import org.eclipse.xsmp.smp.smdl.catalogue.Reference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ReferenceImpl#getInterface <em>Interface</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ReferenceImpl#getLower <em>Lower</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ReferenceImpl#getUpper <em>Upper</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenceImpl extends NamedElementImpl implements Reference
{
	/**
	 * The cached value of the '{@link #getInterface() <em>Interface</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterface()
	 * @generated
	 * @ordered
	 */
	protected ElementReference<Interface> interface_;

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
	protected ReferenceImpl()
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
		return CataloguePackage.Literals.REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementReference<Interface> getInterface()
	{
		return interface_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInterface(ElementReference<Interface> newInterface, NotificationChain msgs)
	{
		ElementReference<Interface> oldInterface = interface_;
		interface_ = newInterface;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CataloguePackage.REFERENCE__INTERFACE, oldInterface, newInterface);
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
	public void setInterface(ElementReference<Interface> newInterface)
	{
		if (newInterface != interface_)
		{
			NotificationChain msgs = null;
			if (interface_ != null)
				msgs = ((InternalEObject)interface_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CataloguePackage.REFERENCE__INTERFACE, null, msgs);
			if (newInterface != null)
				msgs = ((InternalEObject)newInterface).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CataloguePackage.REFERENCE__INTERFACE, null, msgs);
			msgs = basicSetInterface(newInterface, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CataloguePackage.REFERENCE__INTERFACE, newInterface, newInterface));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CataloguePackage.REFERENCE__LOWER, oldLower, lower, !oldLowerESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, CataloguePackage.REFERENCE__LOWER, oldLower, LOWER_EDEFAULT, oldLowerESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CataloguePackage.REFERENCE__UPPER, oldUpper, upper, !oldUpperESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, CataloguePackage.REFERENCE__UPPER, oldUpper, UPPER_EDEFAULT, oldUpperESet));
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
			case CataloguePackage.REFERENCE__INTERFACE:
				return basicSetInterface(null, msgs);
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
			case CataloguePackage.REFERENCE__INTERFACE:
				return getInterface();
			case CataloguePackage.REFERENCE__LOWER:
				return getLower();
			case CataloguePackage.REFERENCE__UPPER:
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
			case CataloguePackage.REFERENCE__INTERFACE:
				setInterface((ElementReference<Interface>)newValue);
				return;
			case CataloguePackage.REFERENCE__LOWER:
				setLower((Long)newValue);
				return;
			case CataloguePackage.REFERENCE__UPPER:
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
			case CataloguePackage.REFERENCE__INTERFACE:
				setInterface((ElementReference<Interface>)null);
				return;
			case CataloguePackage.REFERENCE__LOWER:
				unsetLower();
				return;
			case CataloguePackage.REFERENCE__UPPER:
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
			case CataloguePackage.REFERENCE__INTERFACE:
				return interface_ != null;
			case CataloguePackage.REFERENCE__LOWER:
				return isSetLower();
			case CataloguePackage.REFERENCE__UPPER:
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

} //ReferenceImpl
