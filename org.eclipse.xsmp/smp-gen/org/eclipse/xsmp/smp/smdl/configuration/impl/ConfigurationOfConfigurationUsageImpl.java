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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.xsmp.smp.smdl.configuration.Configuration;
import org.eclipse.xsmp.smp.smdl.configuration.ConfigurationOfConfigurationUsage;
import org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Of Configuration Usage</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.impl.ConfigurationOfConfigurationUsageImpl#getConfigurationName <em>Configuration Name</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.impl.ConfigurationOfConfigurationUsageImpl#getConfiguration <em>Configuration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConfigurationOfConfigurationUsageImpl extends MinimalEObjectImpl.Container implements ConfigurationOfConfigurationUsage
{
	/**
	 * The default value of the '{@link #getConfigurationName() <em>Configuration Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurationName()
	 * @generated
	 * @ordered
	 */
	protected static final String CONFIGURATION_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConfigurationName() <em>Configuration Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurationName()
	 * @generated
	 * @ordered
	 */
	protected String configurationName = CONFIGURATION_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConfiguration() <em>Configuration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfiguration()
	 * @generated
	 * @ordered
	 */
	protected Configuration configuration;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationOfConfigurationUsageImpl()
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
		return ConfigurationPackage.Literals.CONFIGURATION_OF_CONFIGURATION_USAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getConfigurationName()
	{
		return configurationName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConfigurationName(String newConfigurationName)
	{
		String oldConfigurationName = configurationName;
		configurationName = newConfigurationName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION_NAME, oldConfigurationName, configurationName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Configuration getConfiguration()
	{
		if (configuration != null && configuration.eIsProxy())
		{
			InternalEObject oldConfiguration = (InternalEObject)configuration;
			configuration = (Configuration)eResolveProxy(oldConfiguration);
			if (configuration != oldConfiguration)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConfigurationPackage.CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION, oldConfiguration, configuration));
			}
		}
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Configuration basicGetConfiguration()
	{
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConfiguration(Configuration newConfiguration)
	{
		Configuration oldConfiguration = configuration;
		configuration = newConfiguration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION, oldConfiguration, configuration));
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
			case ConfigurationPackage.CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION_NAME:
				return getConfigurationName();
			case ConfigurationPackage.CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION:
				if (resolve) return getConfiguration();
				return basicGetConfiguration();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case ConfigurationPackage.CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION_NAME:
				setConfigurationName((String)newValue);
				return;
			case ConfigurationPackage.CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION:
				setConfiguration((Configuration)newValue);
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
			case ConfigurationPackage.CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION_NAME:
				setConfigurationName(CONFIGURATION_NAME_EDEFAULT);
				return;
			case ConfigurationPackage.CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION:
				setConfiguration((Configuration)null);
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
			case ConfigurationPackage.CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION_NAME:
				return CONFIGURATION_NAME_EDEFAULT == null ? configurationName != null : !CONFIGURATION_NAME_EDEFAULT.equals(configurationName);
			case ConfigurationPackage.CONFIGURATION_OF_CONFIGURATION_USAGE__CONFIGURATION:
				return configuration != null;
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
		result.append(" (configurationName: ");
		result.append(configurationName);
		result.append(')');
		return result.toString();
	}

} //ConfigurationOfConfigurationUsageImpl
