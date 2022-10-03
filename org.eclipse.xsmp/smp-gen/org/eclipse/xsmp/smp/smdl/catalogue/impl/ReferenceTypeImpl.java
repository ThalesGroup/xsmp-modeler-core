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

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xsmp.smp.core.types.Constant;
import org.eclipse.xsmp.smp.core.types.Operation;
import org.eclipse.xsmp.smp.core.types.Property;

import org.eclipse.xsmp.smp.core.types.impl.LanguageTypeImpl;

import org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage;
import org.eclipse.xsmp.smp.smdl.catalogue.ReferenceType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ReferenceTypeImpl#getConstant <em>Constant</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ReferenceTypeImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.impl.ReferenceTypeImpl#getOperation <em>Operation</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ReferenceTypeImpl extends LanguageTypeImpl implements ReferenceType
{
	/**
	 * The cached value of the '{@link #getConstant() <em>Constant</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstant()
	 * @generated
	 * @ordered
	 */
	protected EList<Constant> constant;

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> property;

	/**
	 * The cached value of the '{@link #getOperation() <em>Operation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperation()
	 * @generated
	 * @ordered
	 */
	protected EList<Operation> operation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenceTypeImpl()
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
		return CataloguePackage.Literals.REFERENCE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Constant> getConstant()
	{
		if (constant == null)
		{
			constant = new EObjectContainmentEList<Constant>(Constant.class, this, CataloguePackage.REFERENCE_TYPE__CONSTANT);
		}
		return constant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Property> getProperty()
	{
		if (property == null)
		{
			property = new EObjectContainmentEList<Property>(Property.class, this, CataloguePackage.REFERENCE_TYPE__PROPERTY);
		}
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Operation> getOperation()
	{
		if (operation == null)
		{
			operation = new EObjectContainmentEList<Operation>(Operation.class, this, CataloguePackage.REFERENCE_TYPE__OPERATION);
		}
		return operation;
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
			case CataloguePackage.REFERENCE_TYPE__CONSTANT:
				return ((InternalEList<?>)getConstant()).basicRemove(otherEnd, msgs);
			case CataloguePackage.REFERENCE_TYPE__PROPERTY:
				return ((InternalEList<?>)getProperty()).basicRemove(otherEnd, msgs);
			case CataloguePackage.REFERENCE_TYPE__OPERATION:
				return ((InternalEList<?>)getOperation()).basicRemove(otherEnd, msgs);
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
			case CataloguePackage.REFERENCE_TYPE__CONSTANT:
				return getConstant();
			case CataloguePackage.REFERENCE_TYPE__PROPERTY:
				return getProperty();
			case CataloguePackage.REFERENCE_TYPE__OPERATION:
				return getOperation();
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
			case CataloguePackage.REFERENCE_TYPE__CONSTANT:
				getConstant().clear();
				getConstant().addAll((Collection<? extends Constant>)newValue);
				return;
			case CataloguePackage.REFERENCE_TYPE__PROPERTY:
				getProperty().clear();
				getProperty().addAll((Collection<? extends Property>)newValue);
				return;
			case CataloguePackage.REFERENCE_TYPE__OPERATION:
				getOperation().clear();
				getOperation().addAll((Collection<? extends Operation>)newValue);
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
			case CataloguePackage.REFERENCE_TYPE__CONSTANT:
				getConstant().clear();
				return;
			case CataloguePackage.REFERENCE_TYPE__PROPERTY:
				getProperty().clear();
				return;
			case CataloguePackage.REFERENCE_TYPE__OPERATION:
				getOperation().clear();
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
			case CataloguePackage.REFERENCE_TYPE__CONSTANT:
				return constant != null && !constant.isEmpty();
			case CataloguePackage.REFERENCE_TYPE__PROPERTY:
				return property != null && !property.isEmpty();
			case CataloguePackage.REFERENCE_TYPE__OPERATION:
				return operation != null && !operation.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ReferenceTypeImpl
