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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.xsmp.smp.core.types.ElementReference;
import org.eclipse.xsmp.smp.core.types.Field;
import org.eclipse.xsmp.smp.core.types.TypesPackage;
import org.eclipse.xsmp.smp.core.types.Value;
import org.eclipse.xsmp.smp.core.types.ValueType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.FieldImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.FieldImpl#getDefault <em>Default</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.FieldImpl#isState <em>State</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.FieldImpl#isInput <em>Input</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.impl.FieldImpl#isOutput <em>Output</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FieldImpl extends VisibilityElementImpl implements Field
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
	 * The default value of the '{@link #isState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isState()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STATE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isState()
	 * @generated
	 * @ordered
	 */
	protected boolean state = STATE_EDEFAULT;

	/**
	 * This is true if the State attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean stateESet;

	/**
	 * The default value of the '{@link #isInput() <em>Input</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInput()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INPUT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInput() <em>Input</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInput()
	 * @generated
	 * @ordered
	 */
	protected boolean input = INPUT_EDEFAULT;

	/**
	 * This is true if the Input attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean inputESet;

	/**
	 * The default value of the '{@link #isOutput() <em>Output</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOutput()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OUTPUT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOutput() <em>Output</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOutput()
	 * @generated
	 * @ordered
	 */
	protected boolean output = OUTPUT_EDEFAULT;

	/**
	 * This is true if the Output attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean outputESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FieldImpl()
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
		return TypesPackage.Literals.FIELD;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.FIELD__TYPE, oldType, newType);
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
				msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.FIELD__TYPE, null, msgs);
			if (newType != null)
				msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.FIELD__TYPE, null, msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FIELD__TYPE, newType, newType));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.FIELD__DEFAULT, oldDefault, newDefault);
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
				msgs = ((InternalEObject)default_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.FIELD__DEFAULT, null, msgs);
			if (newDefault != null)
				msgs = ((InternalEObject)newDefault).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.FIELD__DEFAULT, null, msgs);
			msgs = basicSetDefault(newDefault, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FIELD__DEFAULT, newDefault, newDefault));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isState()
	{
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setState(boolean newState)
	{
		boolean oldState = state;
		state = newState;
		boolean oldStateESet = stateESet;
		stateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FIELD__STATE, oldState, state, !oldStateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetState()
	{
		boolean oldState = state;
		boolean oldStateESet = stateESet;
		state = STATE_EDEFAULT;
		stateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.FIELD__STATE, oldState, STATE_EDEFAULT, oldStateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetState()
	{
		return stateESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isInput()
	{
		return input;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInput(boolean newInput)
	{
		boolean oldInput = input;
		input = newInput;
		boolean oldInputESet = inputESet;
		inputESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FIELD__INPUT, oldInput, input, !oldInputESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetInput()
	{
		boolean oldInput = input;
		boolean oldInputESet = inputESet;
		input = INPUT_EDEFAULT;
		inputESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.FIELD__INPUT, oldInput, INPUT_EDEFAULT, oldInputESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetInput()
	{
		return inputESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isOutput()
	{
		return output;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOutput(boolean newOutput)
	{
		boolean oldOutput = output;
		output = newOutput;
		boolean oldOutputESet = outputESet;
		outputESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.FIELD__OUTPUT, oldOutput, output, !oldOutputESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetOutput()
	{
		boolean oldOutput = output;
		boolean oldOutputESet = outputESet;
		output = OUTPUT_EDEFAULT;
		outputESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, TypesPackage.FIELD__OUTPUT, oldOutput, OUTPUT_EDEFAULT, oldOutputESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetOutput()
	{
		return outputESet;
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
			case TypesPackage.FIELD__TYPE:
				return basicSetType(null, msgs);
			case TypesPackage.FIELD__DEFAULT:
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
			case TypesPackage.FIELD__TYPE:
				return getType();
			case TypesPackage.FIELD__DEFAULT:
				return getDefault();
			case TypesPackage.FIELD__STATE:
				return isState();
			case TypesPackage.FIELD__INPUT:
				return isInput();
			case TypesPackage.FIELD__OUTPUT:
				return isOutput();
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
			case TypesPackage.FIELD__TYPE:
				setType((ElementReference<ValueType>)newValue);
				return;
			case TypesPackage.FIELD__DEFAULT:
				setDefault((Value)newValue);
				return;
			case TypesPackage.FIELD__STATE:
				setState((Boolean)newValue);
				return;
			case TypesPackage.FIELD__INPUT:
				setInput((Boolean)newValue);
				return;
			case TypesPackage.FIELD__OUTPUT:
				setOutput((Boolean)newValue);
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
			case TypesPackage.FIELD__TYPE:
				setType((ElementReference<ValueType>)null);
				return;
			case TypesPackage.FIELD__DEFAULT:
				setDefault((Value)null);
				return;
			case TypesPackage.FIELD__STATE:
				unsetState();
				return;
			case TypesPackage.FIELD__INPUT:
				unsetInput();
				return;
			case TypesPackage.FIELD__OUTPUT:
				unsetOutput();
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
			case TypesPackage.FIELD__TYPE:
				return type != null;
			case TypesPackage.FIELD__DEFAULT:
				return default_ != null;
			case TypesPackage.FIELD__STATE:
				return isSetState();
			case TypesPackage.FIELD__INPUT:
				return isSetInput();
			case TypesPackage.FIELD__OUTPUT:
				return isSetOutput();
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
		result.append(" (state: ");
		if (stateESet) result.append(state); else result.append("<unset>");
		result.append(", input: ");
		if (inputESet) result.append(input); else result.append("<unset>");
		result.append(", output: ");
		if (outputESet) result.append(output); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //FieldImpl
