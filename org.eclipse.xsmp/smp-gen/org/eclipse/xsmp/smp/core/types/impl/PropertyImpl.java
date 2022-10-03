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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xsmp.smp.core.types.AccessKind;
import org.eclipse.xsmp.smp.core.types.ElementReference;
import org.eclipse.xsmp.smp.core.types.Field;
import org.eclipse.xsmp.smp.core.types.LanguageType;
import org.eclipse.xsmp.smp.core.types.Property;
import org.eclipse.xsmp.smp.core.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.PropertyImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.PropertyImpl#getAttachedField <em>Attached Field</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.PropertyImpl#getGetRaises <em>Get Raises</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.PropertyImpl#getSetRaises <em>Set Raises</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.PropertyImpl#getAccess <em>Access</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.PropertyImpl#getCategory <em>Category</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropertyImpl extends VisibilityElementImpl implements Property
{
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected ElementReference<LanguageType> type;

	/**
	 * The cached value of the '{@link #getAttachedField() <em>Attached Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttachedField()
	 * @generated
	 * @ordered
	 */
	protected ElementReference<Field> attachedField;

	/**
	 * The cached value of the '{@link #getGetRaises() <em>Get Raises</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGetRaises()
	 * @generated
	 * @ordered
	 */
	protected EList<ElementReference<org.eclipse.xsmp.smp.core.types.Exception>> getRaises;

	/**
	 * The cached value of the '{@link #getSetRaises() <em>Set Raises</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSetRaises()
	 * @generated
	 * @ordered
	 */
	protected EList<ElementReference<org.eclipse.xsmp.smp.core.types.Exception>> setRaises;

	/**
	 * The default value of the '{@link #getAccess() <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccess()
	 * @generated
	 * @ordered
	 */
	protected static final AccessKind ACCESS_EDEFAULT = AccessKind.READ_WRITE;

	/**
	 * The cached value of the '{@link #getAccess() <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccess()
	 * @generated
	 * @ordered
	 */
	protected AccessKind access = ACCESS_EDEFAULT;

	/**
	 * This is true if the Access attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean accessESet;

	/**
	 * The default value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected static final String CATEGORY_EDEFAULT = "Properties";

	/**
	 * The cached value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected String category = CATEGORY_EDEFAULT;

	/**
	 * This is true if the Category attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean categoryESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyImpl()
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
		return TypesPackage.Literals.PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementReference<LanguageType> getType()
	{
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetType(ElementReference<LanguageType> newType, NotificationChain msgs)
	{
		ElementReference<LanguageType> oldType = type;
		type = newType;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.PROPERTY__TYPE, oldType, newType);
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
	public void setType(ElementReference<LanguageType> newType)
	{
		if (newType != type)
		{
			NotificationChain msgs = null;
			if (type != null)
				msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.PROPERTY__TYPE, null, msgs);
			if (newType != null)
				msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.PROPERTY__TYPE, null, msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.PROPERTY__TYPE, newType, newType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementReference<Field> getAttachedField()
	{
		return attachedField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttachedField(ElementReference<Field> newAttachedField, NotificationChain msgs)
	{
		ElementReference<Field> oldAttachedField = attachedField;
		attachedField = newAttachedField;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.PROPERTY__ATTACHED_FIELD, oldAttachedField, newAttachedField);
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
	public void setAttachedField(ElementReference<Field> newAttachedField)
	{
		if (newAttachedField != attachedField)
		{
			NotificationChain msgs = null;
			if (attachedField != null)
				msgs = ((InternalEObject)attachedField).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.PROPERTY__ATTACHED_FIELD, null, msgs);
			if (newAttachedField != null)
				msgs = ((InternalEObject)newAttachedField).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.PROPERTY__ATTACHED_FIELD, null, msgs);
			msgs = basicSetAttachedField(newAttachedField, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.PROPERTY__ATTACHED_FIELD, newAttachedField, newAttachedField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ElementReference<org.eclipse.xsmp.smp.core.types.Exception>> getGetRaises()
	{
		if (getRaises == null)
		{
			getRaises = new EObjectContainmentEList<ElementReference<org.eclipse.xsmp.smp.core.types.Exception>>(ElementReference.class, this, TypesPackage.PROPERTY__GET_RAISES);
		}
		return getRaises;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ElementReference<org.eclipse.xsmp.smp.core.types.Exception>> getSetRaises()
	{
		if (setRaises == null)
		{
			setRaises = new EObjectContainmentEList<ElementReference<org.eclipse.xsmp.smp.core.types.Exception>>(ElementReference.class, this, TypesPackage.PROPERTY__SET_RAISES);
		}
		return setRaises;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AccessKind getAccess()
	{
		return access;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAccess(AccessKind newAccess)
	{
		AccessKind oldAccess = access;
		access = newAccess == null ? ACCESS_EDEFAULT : newAccess;
		boolean oldAccessESet = accessESet;
		accessESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.PROPERTY__ACCESS, oldAccess, access, !oldAccessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetAccess()
	{
		AccessKind oldAccess = access;
		boolean oldAccessESet = accessESet;
		access = ACCESS_EDEFAULT;
		accessESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.PROPERTY__ACCESS, oldAccess, ACCESS_EDEFAULT, oldAccessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetAccess()
	{
		return accessESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCategory()
	{
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCategory(String newCategory)
	{
		String oldCategory = category;
		category = newCategory;
		boolean oldCategoryESet = categoryESet;
		categoryESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.PROPERTY__CATEGORY, oldCategory, category, !oldCategoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetCategory()
	{
		String oldCategory = category;
		boolean oldCategoryESet = categoryESet;
		category = CATEGORY_EDEFAULT;
		categoryESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.PROPERTY__CATEGORY, oldCategory, CATEGORY_EDEFAULT, oldCategoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetCategory()
	{
		return categoryESet;
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
			case TypesPackage.PROPERTY__TYPE:
				return basicSetType(null, msgs);
			case TypesPackage.PROPERTY__ATTACHED_FIELD:
				return basicSetAttachedField(null, msgs);
			case TypesPackage.PROPERTY__GET_RAISES:
				return ((InternalEList<?>)getGetRaises()).basicRemove(otherEnd, msgs);
			case TypesPackage.PROPERTY__SET_RAISES:
				return ((InternalEList<?>)getSetRaises()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.PROPERTY__TYPE:
				return getType();
			case TypesPackage.PROPERTY__ATTACHED_FIELD:
				return getAttachedField();
			case TypesPackage.PROPERTY__GET_RAISES:
				return getGetRaises();
			case TypesPackage.PROPERTY__SET_RAISES:
				return getSetRaises();
			case TypesPackage.PROPERTY__ACCESS:
				return getAccess();
			case TypesPackage.PROPERTY__CATEGORY:
				return getCategory();
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
			case TypesPackage.PROPERTY__TYPE:
				setType((ElementReference<LanguageType>)newValue);
				return;
			case TypesPackage.PROPERTY__ATTACHED_FIELD:
				setAttachedField((ElementReference<Field>)newValue);
				return;
			case TypesPackage.PROPERTY__GET_RAISES:
				getGetRaises().clear();
				getGetRaises().addAll((Collection<? extends ElementReference<org.eclipse.xsmp.smp.core.types.Exception>>)newValue);
				return;
			case TypesPackage.PROPERTY__SET_RAISES:
				getSetRaises().clear();
				getSetRaises().addAll((Collection<? extends ElementReference<org.eclipse.xsmp.smp.core.types.Exception>>)newValue);
				return;
			case TypesPackage.PROPERTY__ACCESS:
				setAccess((AccessKind)newValue);
				return;
			case TypesPackage.PROPERTY__CATEGORY:
				setCategory((String)newValue);
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
			case TypesPackage.PROPERTY__TYPE:
				setType((ElementReference<LanguageType>)null);
				return;
			case TypesPackage.PROPERTY__ATTACHED_FIELD:
				setAttachedField((ElementReference<Field>)null);
				return;
			case TypesPackage.PROPERTY__GET_RAISES:
				getGetRaises().clear();
				return;
			case TypesPackage.PROPERTY__SET_RAISES:
				getSetRaises().clear();
				return;
			case TypesPackage.PROPERTY__ACCESS:
				unsetAccess();
				return;
			case TypesPackage.PROPERTY__CATEGORY:
				unsetCategory();
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
			case TypesPackage.PROPERTY__TYPE:
				return type != null;
			case TypesPackage.PROPERTY__ATTACHED_FIELD:
				return attachedField != null;
			case TypesPackage.PROPERTY__GET_RAISES:
				return getRaises != null && !getRaises.isEmpty();
			case TypesPackage.PROPERTY__SET_RAISES:
				return setRaises != null && !setRaises.isEmpty();
			case TypesPackage.PROPERTY__ACCESS:
				return isSetAccess();
			case TypesPackage.PROPERTY__CATEGORY:
				return isSetCategory();
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
		result.append(" (access: ");
		if (accessESet) result.append(access); else result.append("<unset>");
		result.append(", category: ");
		if (categoryESet) result.append(category); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //PropertyImpl
