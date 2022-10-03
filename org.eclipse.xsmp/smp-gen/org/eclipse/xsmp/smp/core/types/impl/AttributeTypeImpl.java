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

import java.lang.String;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

import org.eclipse.xsmp.smp.core.types.AttributeType;
import org.eclipse.xsmp.smp.core.types.ElementReference;
import org.eclipse.xsmp.smp.core.types.TypesPackage;
import org.eclipse.xsmp.smp.core.types.Value;
import org.eclipse.xsmp.smp.core.types.ValueType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.AttributeTypeImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.AttributeTypeImpl#getDefault <em>Default</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.AttributeTypeImpl#getUsage <em>Usage</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.AttributeTypeImpl#isAllowMultiple <em>Allow Multiple</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeTypeImpl extends TypeImpl implements AttributeType
{
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected ElementReference<ValueType> type;

	/**
	 * The cached value of the '{@link #getDefault() <em>Default</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefault()
	 * @generated
	 * @ordered
	 */
	protected Value default_;

	/**
	 * The cached value of the '{@link #getUsage() <em>Usage</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsage()
	 * @generated
	 * @ordered
	 */
	protected EList<String> usage;

	/**
	 * The default value of the '{@link #isAllowMultiple() <em>Allow Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllowMultiple()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALLOW_MULTIPLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAllowMultiple() <em>Allow Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllowMultiple()
	 * @generated
	 * @ordered
	 */
	protected boolean allowMultiple = ALLOW_MULTIPLE_EDEFAULT;

	/**
	 * This is true if the Allow Multiple attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean allowMultipleESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeTypeImpl()
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
		return TypesPackage.Literals.ATTRIBUTE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementReference<ValueType> getType()
	{
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetType(ElementReference<ValueType> newType, NotificationChain msgs)
	{
		ElementReference<ValueType> oldType = type;
		type = newType;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.ATTRIBUTE_TYPE__TYPE, oldType, newType);
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
	public void setType(ElementReference<ValueType> newType)
	{
		if (newType != type)
		{
			NotificationChain msgs = null;
			if (type != null)
				msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.ATTRIBUTE_TYPE__TYPE, null, msgs);
			if (newType != null)
				msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.ATTRIBUTE_TYPE__TYPE, null, msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ATTRIBUTE_TYPE__TYPE, newType, newType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Value getDefault()
	{
		return default_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefault(Value newDefault, NotificationChain msgs)
	{
		Value oldDefault = default_;
		default_ = newDefault;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.ATTRIBUTE_TYPE__DEFAULT, oldDefault, newDefault);
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
	public void setDefault(Value newDefault)
	{
		if (newDefault != default_)
		{
			NotificationChain msgs = null;
			if (default_ != null)
				msgs = ((InternalEObject)default_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.ATTRIBUTE_TYPE__DEFAULT, null, msgs);
			if (newDefault != null)
				msgs = ((InternalEObject)newDefault).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.ATTRIBUTE_TYPE__DEFAULT, null, msgs);
			msgs = basicSetDefault(newDefault, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ATTRIBUTE_TYPE__DEFAULT, newDefault, newDefault));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getUsage()
	{
		if (usage == null)
		{
			usage = new EDataTypeEList<String>(String.class, this, TypesPackage.ATTRIBUTE_TYPE__USAGE);
		}
		return usage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAllowMultiple()
	{
		return allowMultiple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAllowMultiple(boolean newAllowMultiple)
	{
		boolean oldAllowMultiple = allowMultiple;
		allowMultiple = newAllowMultiple;
		boolean oldAllowMultipleESet = allowMultipleESet;
		allowMultipleESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ATTRIBUTE_TYPE__ALLOW_MULTIPLE, oldAllowMultiple, allowMultiple, !oldAllowMultipleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetAllowMultiple()
	{
		boolean oldAllowMultiple = allowMultiple;
		boolean oldAllowMultipleESet = allowMultipleESet;
		allowMultiple = ALLOW_MULTIPLE_EDEFAULT;
		allowMultipleESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.ATTRIBUTE_TYPE__ALLOW_MULTIPLE, oldAllowMultiple, ALLOW_MULTIPLE_EDEFAULT, oldAllowMultipleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetAllowMultiple()
	{
		return allowMultipleESet;
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
			case TypesPackage.ATTRIBUTE_TYPE__TYPE:
				return basicSetType(null, msgs);
			case TypesPackage.ATTRIBUTE_TYPE__DEFAULT:
				return basicSetDefault(null, msgs);
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
			case TypesPackage.ATTRIBUTE_TYPE__TYPE:
				return getType();
			case TypesPackage.ATTRIBUTE_TYPE__DEFAULT:
				return getDefault();
			case TypesPackage.ATTRIBUTE_TYPE__USAGE:
				return getUsage();
			case TypesPackage.ATTRIBUTE_TYPE__ALLOW_MULTIPLE:
				return isAllowMultiple();
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
			case TypesPackage.ATTRIBUTE_TYPE__TYPE:
				setType((ElementReference<ValueType>)newValue);
				return;
			case TypesPackage.ATTRIBUTE_TYPE__DEFAULT:
				setDefault((Value)newValue);
				return;
			case TypesPackage.ATTRIBUTE_TYPE__USAGE:
				getUsage().clear();
				getUsage().addAll((Collection<? extends String>)newValue);
				return;
			case TypesPackage.ATTRIBUTE_TYPE__ALLOW_MULTIPLE:
				setAllowMultiple((Boolean)newValue);
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
			case TypesPackage.ATTRIBUTE_TYPE__TYPE:
				setType((ElementReference<ValueType>)null);
				return;
			case TypesPackage.ATTRIBUTE_TYPE__DEFAULT:
				setDefault((Value)null);
				return;
			case TypesPackage.ATTRIBUTE_TYPE__USAGE:
				getUsage().clear();
				return;
			case TypesPackage.ATTRIBUTE_TYPE__ALLOW_MULTIPLE:
				unsetAllowMultiple();
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
			case TypesPackage.ATTRIBUTE_TYPE__TYPE:
				return type != null;
			case TypesPackage.ATTRIBUTE_TYPE__DEFAULT:
				return default_ != null;
			case TypesPackage.ATTRIBUTE_TYPE__USAGE:
				return usage != null && !usage.isEmpty();
			case TypesPackage.ATTRIBUTE_TYPE__ALLOW_MULTIPLE:
				return isSetAllowMultiple();
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
		result.append(" (usage: ");
		result.append(usage);
		result.append(", allowMultiple: ");
		if (allowMultipleESet) result.append(allowMultiple); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //AttributeTypeImpl
