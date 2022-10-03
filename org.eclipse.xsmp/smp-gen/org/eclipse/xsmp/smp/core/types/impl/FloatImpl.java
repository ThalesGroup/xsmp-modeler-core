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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.xsmp.smp.core.types.ElementReference;
import org.eclipse.xsmp.smp.core.types.PrimitiveType;
import org.eclipse.xsmp.smp.core.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Float</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.FloatImpl#getPrimitiveType <em>Primitive Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.FloatImpl#getMaximum <em>Maximum</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.FloatImpl#isMinInclusive <em>Min Inclusive</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.FloatImpl#getMinimum <em>Minimum</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.FloatImpl#isMaxInclusive <em>Max Inclusive</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.FloatImpl#getUnit <em>Unit</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FloatImpl extends SimpleTypeImpl implements org.eclipse.xsmp.smp.core.types.Float
{
	/**
	 * The cached value of the '{@link #getPrimitiveType() <em>Primitive Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimitiveType()
	 * @generated
	 * @ordered
	 */
	protected ElementReference<PrimitiveType> primitiveType;

	/**
	 * The default value of the '{@link #getMaximum() <em>Maximum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaximum()
	 * @generated
	 * @ordered
	 */
	protected static final double MAXIMUM_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMaximum() <em>Maximum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaximum()
	 * @generated
	 * @ordered
	 */
	protected double maximum = MAXIMUM_EDEFAULT;

	/**
	 * This is true if the Maximum attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean maximumESet;

	/**
	 * The default value of the '{@link #isMinInclusive() <em>Min Inclusive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMinInclusive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MIN_INCLUSIVE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isMinInclusive() <em>Min Inclusive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMinInclusive()
	 * @generated
	 * @ordered
	 */
	protected boolean minInclusive = MIN_INCLUSIVE_EDEFAULT;

	/**
	 * This is true if the Min Inclusive attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean minInclusiveESet;

	/**
	 * The default value of the '{@link #getMinimum() <em>Minimum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinimum()
	 * @generated
	 * @ordered
	 */
	protected static final double MINIMUM_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMinimum() <em>Minimum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinimum()
	 * @generated
	 * @ordered
	 */
	protected double minimum = MINIMUM_EDEFAULT;

	/**
	 * This is true if the Minimum attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean minimumESet;

	/**
	 * The default value of the '{@link #isMaxInclusive() <em>Max Inclusive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMaxInclusive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MAX_INCLUSIVE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isMaxInclusive() <em>Max Inclusive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMaxInclusive()
	 * @generated
	 * @ordered
	 */
	protected boolean maxInclusive = MAX_INCLUSIVE_EDEFAULT;

	/**
	 * This is true if the Max Inclusive attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean maxInclusiveESet;

	/**
	 * The default value of the '{@link #getUnit() <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnit()
	 * @generated
	 * @ordered
	 */
	protected static final String UNIT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUnit() <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnit()
	 * @generated
	 * @ordered
	 */
	protected String unit = UNIT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FloatImpl()
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
		return TypesPackage.Literals.FLOAT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementReference<PrimitiveType> getPrimitiveType()
	{
		return primitiveType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrimitiveType(ElementReference<PrimitiveType> newPrimitiveType, NotificationChain msgs)
	{
		ElementReference<PrimitiveType> oldPrimitiveType = primitiveType;
		primitiveType = newPrimitiveType;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.FLOAT__PRIMITIVE_TYPE, oldPrimitiveType, newPrimitiveType);
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
	public void setPrimitiveType(ElementReference<PrimitiveType> newPrimitiveType)
	{
		if (newPrimitiveType != primitiveType)
		{
			NotificationChain msgs = null;
			if (primitiveType != null)
				msgs = ((InternalEObject)primitiveType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.FLOAT__PRIMITIVE_TYPE, null, msgs);
			if (newPrimitiveType != null)
				msgs = ((InternalEObject)newPrimitiveType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.FLOAT__PRIMITIVE_TYPE, null, msgs);
			msgs = basicSetPrimitiveType(newPrimitiveType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FLOAT__PRIMITIVE_TYPE, newPrimitiveType, newPrimitiveType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getMaximum()
	{
		return maximum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMaximum(double newMaximum)
	{
		double oldMaximum = maximum;
		maximum = newMaximum;
		boolean oldMaximumESet = maximumESet;
		maximumESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FLOAT__MAXIMUM, oldMaximum, maximum, !oldMaximumESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetMaximum()
	{
		double oldMaximum = maximum;
		boolean oldMaximumESet = maximumESet;
		maximum = MAXIMUM_EDEFAULT;
		maximumESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.FLOAT__MAXIMUM, oldMaximum, MAXIMUM_EDEFAULT, oldMaximumESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetMaximum()
	{
		return maximumESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isMinInclusive()
	{
		return minInclusive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMinInclusive(boolean newMinInclusive)
	{
		boolean oldMinInclusive = minInclusive;
		minInclusive = newMinInclusive;
		boolean oldMinInclusiveESet = minInclusiveESet;
		minInclusiveESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FLOAT__MIN_INCLUSIVE, oldMinInclusive, minInclusive, !oldMinInclusiveESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetMinInclusive()
	{
		boolean oldMinInclusive = minInclusive;
		boolean oldMinInclusiveESet = minInclusiveESet;
		minInclusive = MIN_INCLUSIVE_EDEFAULT;
		minInclusiveESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.FLOAT__MIN_INCLUSIVE, oldMinInclusive, MIN_INCLUSIVE_EDEFAULT, oldMinInclusiveESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetMinInclusive()
	{
		return minInclusiveESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getMinimum()
	{
		return minimum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMinimum(double newMinimum)
	{
		double oldMinimum = minimum;
		minimum = newMinimum;
		boolean oldMinimumESet = minimumESet;
		minimumESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FLOAT__MINIMUM, oldMinimum, minimum, !oldMinimumESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetMinimum()
	{
		double oldMinimum = minimum;
		boolean oldMinimumESet = minimumESet;
		minimum = MINIMUM_EDEFAULT;
		minimumESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.FLOAT__MINIMUM, oldMinimum, MINIMUM_EDEFAULT, oldMinimumESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetMinimum()
	{
		return minimumESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isMaxInclusive()
	{
		return maxInclusive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMaxInclusive(boolean newMaxInclusive)
	{
		boolean oldMaxInclusive = maxInclusive;
		maxInclusive = newMaxInclusive;
		boolean oldMaxInclusiveESet = maxInclusiveESet;
		maxInclusiveESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FLOAT__MAX_INCLUSIVE, oldMaxInclusive, maxInclusive, !oldMaxInclusiveESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetMaxInclusive()
	{
		boolean oldMaxInclusive = maxInclusive;
		boolean oldMaxInclusiveESet = maxInclusiveESet;
		maxInclusive = MAX_INCLUSIVE_EDEFAULT;
		maxInclusiveESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.FLOAT__MAX_INCLUSIVE, oldMaxInclusive, MAX_INCLUSIVE_EDEFAULT, oldMaxInclusiveESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetMaxInclusive()
	{
		return maxInclusiveESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUnit()
	{
		return unit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUnit(String newUnit)
	{
		String oldUnit = unit;
		unit = newUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FLOAT__UNIT, oldUnit, unit));
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
			case TypesPackage.FLOAT__PRIMITIVE_TYPE:
				return basicSetPrimitiveType(null, msgs);
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
			case TypesPackage.FLOAT__PRIMITIVE_TYPE:
				return getPrimitiveType();
			case TypesPackage.FLOAT__MAXIMUM:
				return getMaximum();
			case TypesPackage.FLOAT__MIN_INCLUSIVE:
				return isMinInclusive();
			case TypesPackage.FLOAT__MINIMUM:
				return getMinimum();
			case TypesPackage.FLOAT__MAX_INCLUSIVE:
				return isMaxInclusive();
			case TypesPackage.FLOAT__UNIT:
				return getUnit();
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
			case TypesPackage.FLOAT__PRIMITIVE_TYPE:
				setPrimitiveType((ElementReference<PrimitiveType>)newValue);
				return;
			case TypesPackage.FLOAT__MAXIMUM:
				setMaximum((Double)newValue);
				return;
			case TypesPackage.FLOAT__MIN_INCLUSIVE:
				setMinInclusive((Boolean)newValue);
				return;
			case TypesPackage.FLOAT__MINIMUM:
				setMinimum((Double)newValue);
				return;
			case TypesPackage.FLOAT__MAX_INCLUSIVE:
				setMaxInclusive((Boolean)newValue);
				return;
			case TypesPackage.FLOAT__UNIT:
				setUnit((String)newValue);
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
			case TypesPackage.FLOAT__PRIMITIVE_TYPE:
				setPrimitiveType((ElementReference<PrimitiveType>)null);
				return;
			case TypesPackage.FLOAT__MAXIMUM:
				unsetMaximum();
				return;
			case TypesPackage.FLOAT__MIN_INCLUSIVE:
				unsetMinInclusive();
				return;
			case TypesPackage.FLOAT__MINIMUM:
				unsetMinimum();
				return;
			case TypesPackage.FLOAT__MAX_INCLUSIVE:
				unsetMaxInclusive();
				return;
			case TypesPackage.FLOAT__UNIT:
				setUnit(UNIT_EDEFAULT);
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
			case TypesPackage.FLOAT__PRIMITIVE_TYPE:
				return primitiveType != null;
			case TypesPackage.FLOAT__MAXIMUM:
				return isSetMaximum();
			case TypesPackage.FLOAT__MIN_INCLUSIVE:
				return isSetMinInclusive();
			case TypesPackage.FLOAT__MINIMUM:
				return isSetMinimum();
			case TypesPackage.FLOAT__MAX_INCLUSIVE:
				return isSetMaxInclusive();
			case TypesPackage.FLOAT__UNIT:
				return UNIT_EDEFAULT == null ? unit != null : !UNIT_EDEFAULT.equals(unit);
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
		result.append(" (maximum: ");
		if (maximumESet) result.append(maximum); else result.append("<unset>");
		result.append(", minInclusive: ");
		if (minInclusiveESet) result.append(minInclusive); else result.append("<unset>");
		result.append(", minimum: ");
		if (minimumESet) result.append(minimum); else result.append("<unset>");
		result.append(", maxInclusive: ");
		if (maxInclusiveESet) result.append(maxInclusive); else result.append("<unset>");
		result.append(", unit: ");
		result.append(unit);
		result.append(')');
		return result.toString();
	}

} //FloatImpl
