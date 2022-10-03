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

import org.eclipse.xsmp.smp.core.types.ElementReference;
import org.eclipse.xsmp.smp.core.types.Operation;
import org.eclipse.xsmp.smp.core.types.Parameter;
import org.eclipse.xsmp.smp.core.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.OperationImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.OperationImpl#getRaisedException <em>Raised Exception</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationImpl extends VisibilityElementImpl implements Operation
{
	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameter;

	/**
	 * The cached value of the '{@link #getRaisedException() <em>Raised Exception</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRaisedException()
	 * @generated
	 * @ordered
	 */
	protected EList<ElementReference<org.eclipse.xsmp.smp.core.types.Exception>> raisedException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationImpl()
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
		return TypesPackage.Literals.OPERATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Parameter> getParameter()
	{
		if (parameter == null)
		{
			parameter = new EObjectContainmentEList<Parameter>(Parameter.class, this, TypesPackage.OPERATION__PARAMETER);
		}
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ElementReference<org.eclipse.xsmp.smp.core.types.Exception>> getRaisedException()
	{
		if (raisedException == null)
		{
			raisedException = new EObjectContainmentEList<ElementReference<org.eclipse.xsmp.smp.core.types.Exception>>(ElementReference.class, this, TypesPackage.OPERATION__RAISED_EXCEPTION);
		}
		return raisedException;
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
			case TypesPackage.OPERATION__PARAMETER:
				return ((InternalEList<?>)getParameter()).basicRemove(otherEnd, msgs);
			case TypesPackage.OPERATION__RAISED_EXCEPTION:
				return ((InternalEList<?>)getRaisedException()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.OPERATION__PARAMETER:
				return getParameter();
			case TypesPackage.OPERATION__RAISED_EXCEPTION:
				return getRaisedException();
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
			case TypesPackage.OPERATION__PARAMETER:
				getParameter().clear();
				getParameter().addAll((Collection<? extends Parameter>)newValue);
				return;
			case TypesPackage.OPERATION__RAISED_EXCEPTION:
				getRaisedException().clear();
				getRaisedException().addAll((Collection<? extends ElementReference<org.eclipse.xsmp.smp.core.types.Exception>>)newValue);
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
			case TypesPackage.OPERATION__PARAMETER:
				getParameter().clear();
				return;
			case TypesPackage.OPERATION__RAISED_EXCEPTION:
				getRaisedException().clear();
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
			case TypesPackage.OPERATION__PARAMETER:
				return parameter != null && !parameter.isEmpty();
			case TypesPackage.OPERATION__RAISED_EXCEPTION:
				return raisedException != null && !raisedException.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //OperationImpl
