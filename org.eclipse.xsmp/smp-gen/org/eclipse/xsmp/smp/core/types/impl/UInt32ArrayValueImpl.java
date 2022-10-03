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
package org.eclipse.xsmp.smp.core.types.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xsmp.smp.core.types.TypesPackage;
import org.eclipse.xsmp.smp.core.types.UInt32ArrayValue;
import org.eclipse.xsmp.smp.core.types.UInt32Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>UInt32 Array Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.UInt32ArrayValueImpl#getItemValue <em>Item Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UInt32ArrayValueImpl extends SimpleArrayValueImpl implements UInt32ArrayValue
{
	/**
	 * The cached value of the '{@link #getItemValue() <em>Item Value</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItemValue()
	 * @generated
	 * @ordered
	 */
	protected EList<UInt32Value> itemValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UInt32ArrayValueImpl()
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
		return TypesPackage.Literals.UINT32_ARRAY_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<UInt32Value> getItemValue()
	{
		if (itemValue == null)
		{
			itemValue = new EObjectContainmentEList<UInt32Value>(UInt32Value.class, this, TypesPackage.UINT32_ARRAY_VALUE__ITEM_VALUE);
		}
		return itemValue;
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
			case TypesPackage.UINT32_ARRAY_VALUE__ITEM_VALUE:
				return ((InternalEList<?>)getItemValue()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.UINT32_ARRAY_VALUE__ITEM_VALUE:
				return getItemValue();
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
			case TypesPackage.UINT32_ARRAY_VALUE__ITEM_VALUE:
				getItemValue().clear();
				getItemValue().addAll((Collection<? extends UInt32Value>)newValue);
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
			case TypesPackage.UINT32_ARRAY_VALUE__ITEM_VALUE:
				getItemValue().clear();
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
			case TypesPackage.UINT32_ARRAY_VALUE__ITEM_VALUE:
				return itemValue != null && !itemValue.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //UInt32ArrayValueImpl
