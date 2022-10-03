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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.xsmp.smp.smdl.configuration.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConfigurationFactoryImpl extends EFactoryImpl implements ConfigurationFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConfigurationFactory init()
	{
		try
		{
			ConfigurationFactory theConfigurationFactory = (ConfigurationFactory)EPackage.Registry.INSTANCE.getEFactory(ConfigurationPackage.eNS_URI);
			if (theConfigurationFactory != null)
			{
				return theConfigurationFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ConfigurationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationFactoryImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass)
	{
		switch (eClass.getClassifierID())
		{
			case ConfigurationPackage.COMPONENT_CONFIGURATION: return createComponentConfiguration();
			case ConfigurationPackage.CONFIGURATION: return createConfiguration();
			case ConfigurationPackage.CONFIGURATION_DOCUMENT_ROOT: return createConfigurationDocumentRoot();
			case ConfigurationPackage.CONFIGURATION_OF_CONFIGURATION_USAGE: return createConfigurationOfConfigurationUsage();
			case ConfigurationPackage.CONFIGURATION_USAGE: return createConfigurationUsage();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComponentConfiguration createComponentConfiguration()
	{
		ComponentConfigurationImpl componentConfiguration = new ComponentConfigurationImpl();
		return componentConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Configuration createConfiguration()
	{
		ConfigurationImpl configuration = new ConfigurationImpl();
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConfigurationDocumentRoot createConfigurationDocumentRoot()
	{
		ConfigurationDocumentRootImpl configurationDocumentRoot = new ConfigurationDocumentRootImpl();
		return configurationDocumentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConfigurationOfConfigurationUsage createConfigurationOfConfigurationUsage()
	{
		ConfigurationOfConfigurationUsageImpl configurationOfConfigurationUsage = new ConfigurationOfConfigurationUsageImpl();
		return configurationOfConfigurationUsage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConfigurationUsage createConfigurationUsage()
	{
		ConfigurationUsageImpl configurationUsage = new ConfigurationUsageImpl();
		return configurationUsage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConfigurationPackage getConfigurationPackage()
	{
		return (ConfigurationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ConfigurationPackage getPackage()
	{
		return ConfigurationPackage.eINSTANCE;
	}

} //ConfigurationFactoryImpl
