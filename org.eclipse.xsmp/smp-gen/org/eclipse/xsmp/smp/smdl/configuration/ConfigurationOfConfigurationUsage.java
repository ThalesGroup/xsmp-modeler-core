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
package org.eclipse.xsmp.smp.smdl.configuration;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Of Configuration Usage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.ConfigurationOfConfigurationUsage#getConfigurationName <em>Configuration Name</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.ConfigurationOfConfigurationUsage#getConfiguration <em>Configuration</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getConfigurationOfConfigurationUsage()
 * @model extendedMetaData="name='Configuration_._type' kind='empty'"
 * @generated
 */
public interface ConfigurationOfConfigurationUsage extends EObject
{
	/**
	 * Returns the value of the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration Name</em>' attribute.
	 * @see #setConfigurationName(String)
	 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getConfigurationOfConfigurationUsage_ConfigurationName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='title' namespace='http://www.w3.org/1999/xlink'"
	 * @generated
	 */
	String getConfigurationName();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.configuration.ConfigurationOfConfigurationUsage#getConfigurationName <em>Configuration Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration Name</em>' attribute.
	 * @see #getConfigurationName()
	 * @generated
	 */
	void setConfigurationName(String value);

	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration</em>' reference.
	 * @see #setConfiguration(Configuration)
	 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getConfigurationOfConfigurationUsage_Configuration()
	 * @model required="true"
	 *        extendedMetaData="kind='attribute' name='href' namespace='http://www.w3.org/1999/xlink'"
	 * @generated
	 */
	Configuration getConfiguration();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.configuration.ConfigurationOfConfigurationUsage#getConfiguration <em>Configuration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration</em>' reference.
	 * @see #getConfiguration()
	 * @generated
	 */
	void setConfiguration(Configuration value);

} // ConfigurationOfConfigurationUsage
