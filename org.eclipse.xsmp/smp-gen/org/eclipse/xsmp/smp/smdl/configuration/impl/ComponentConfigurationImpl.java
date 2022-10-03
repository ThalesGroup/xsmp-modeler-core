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
package org.eclipse.xsmp.smp.smdl.configuration.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xsmp.smp.core.types.Value;

import org.eclipse.xsmp.smp.smdl.configuration.ComponentConfiguration;
import org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage;
import org.eclipse.xsmp.smp.smdl.configuration.ConfigurationUsage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.impl.ComponentConfigurationImpl#getInclude <em>Include</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.impl.ComponentConfigurationImpl#getComponent <em>Component</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.impl.ComponentConfigurationImpl#getFieldValue <em>Field Value</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.impl.ComponentConfigurationImpl#getPath <em>Path</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComponentConfigurationImpl extends MinimalEObjectImpl.Container implements ComponentConfiguration
{
	/**
	 * The cached value of the '{@link #getInclude() <em>Include</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInclude()
	 * @generated
	 * @ordered
	 */
	protected EList<ConfigurationUsage> include;

	/**
	 * The cached value of the '{@link #getComponent() <em>Component</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponent()
	 * @generated
	 * @ordered
	 */
	protected EList<ComponentConfiguration> component;

	/**
	 * The cached value of the '{@link #getFieldValue() <em>Field Value</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFieldValue()
	 * @generated
	 * @ordered
	 */
	protected EList<Value> fieldValue;

	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentConfigurationImpl()
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
		return ConfigurationPackage.Literals.COMPONENT_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ConfigurationUsage> getInclude()
	{
		if (include == null)
		{
			include = new EObjectContainmentEList<ConfigurationUsage>(ConfigurationUsage.class, this, ConfigurationPackage.COMPONENT_CONFIGURATION__INCLUDE);
		}
		return include;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ComponentConfiguration> getComponent()
	{
		if (component == null)
		{
			component = new EObjectContainmentEList<ComponentConfiguration>(ComponentConfiguration.class, this, ConfigurationPackage.COMPONENT_CONFIGURATION__COMPONENT);
		}
		return component;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Value> getFieldValue()
	{
		if (fieldValue == null)
		{
			fieldValue = new EObjectContainmentEList<Value>(Value.class, this, ConfigurationPackage.COMPONENT_CONFIGURATION__FIELD_VALUE);
		}
		return fieldValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPath()
	{
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPath(String newPath)
	{
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.COMPONENT_CONFIGURATION__PATH, oldPath, path));
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
			case ConfigurationPackage.COMPONENT_CONFIGURATION__INCLUDE:
				return ((InternalEList<?>)getInclude()).basicRemove(otherEnd, msgs);
			case ConfigurationPackage.COMPONENT_CONFIGURATION__COMPONENT:
				return ((InternalEList<?>)getComponent()).basicRemove(otherEnd, msgs);
			case ConfigurationPackage.COMPONENT_CONFIGURATION__FIELD_VALUE:
				return ((InternalEList<?>)getFieldValue()).basicRemove(otherEnd, msgs);
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
			case ConfigurationPackage.COMPONENT_CONFIGURATION__INCLUDE:
				return getInclude();
			case ConfigurationPackage.COMPONENT_CONFIGURATION__COMPONENT:
				return getComponent();
			case ConfigurationPackage.COMPONENT_CONFIGURATION__FIELD_VALUE:
				return getFieldValue();
			case ConfigurationPackage.COMPONENT_CONFIGURATION__PATH:
				return getPath();
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
			case ConfigurationPackage.COMPONENT_CONFIGURATION__INCLUDE:
				getInclude().clear();
				getInclude().addAll((Collection<? extends ConfigurationUsage>)newValue);
				return;
			case ConfigurationPackage.COMPONENT_CONFIGURATION__COMPONENT:
				getComponent().clear();
				getComponent().addAll((Collection<? extends ComponentConfiguration>)newValue);
				return;
			case ConfigurationPackage.COMPONENT_CONFIGURATION__FIELD_VALUE:
				getFieldValue().clear();
				getFieldValue().addAll((Collection<? extends Value>)newValue);
				return;
			case ConfigurationPackage.COMPONENT_CONFIGURATION__PATH:
				setPath((String)newValue);
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
			case ConfigurationPackage.COMPONENT_CONFIGURATION__INCLUDE:
				getInclude().clear();
				return;
			case ConfigurationPackage.COMPONENT_CONFIGURATION__COMPONENT:
				getComponent().clear();
				return;
			case ConfigurationPackage.COMPONENT_CONFIGURATION__FIELD_VALUE:
				getFieldValue().clear();
				return;
			case ConfigurationPackage.COMPONENT_CONFIGURATION__PATH:
				setPath(PATH_EDEFAULT);
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
			case ConfigurationPackage.COMPONENT_CONFIGURATION__INCLUDE:
				return include != null && !include.isEmpty();
			case ConfigurationPackage.COMPONENT_CONFIGURATION__COMPONENT:
				return component != null && !component.isEmpty();
			case ConfigurationPackage.COMPONENT_CONFIGURATION__FIELD_VALUE:
				return fieldValue != null && !fieldValue.isEmpty();
			case ConfigurationPackage.COMPONENT_CONFIGURATION__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
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
		result.append(" (path: ");
		result.append(path);
		result.append(')');
		return result.toString();
	}

} //ComponentConfigurationImpl
