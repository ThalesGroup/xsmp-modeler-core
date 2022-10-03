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
 * A representation of the model object '<em><b>Usage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A ConfigurationUsage allows to include another SMDL Configuration document. The external configuration document is referenced via the Configuration link. The Path specifies the (optional) prefix that shall be applied to all paths in the external configuration document. This allows pre-defined configuration documents to be re-used for the configuration of components in various places in the model hierarchy.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.ConfigurationUsage#getConfiguration <em>Configuration</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.ConfigurationUsage#getPath <em>Path</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getConfigurationUsage()
 * @model extendedMetaData="name='ConfigurationUsage' kind='elementOnly'"
 * @generated
 */
public interface ConfigurationUsage extends EObject
{
	/**
	 * Returns the value of the '<em><b>Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Configuration that the usage points to.
	 * Link destination type: Configuration:Configuration
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Configuration</em>' containment reference.
	 * @see #setConfiguration(ConfigurationOfConfigurationUsage)
	 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getConfigurationUsage_Configuration()
	 * @model containment="true" required="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Configuration:Configuration'"
	 *        extendedMetaData="kind='element' name='Configuration'"
	 * @generated
	 */
	ConfigurationOfConfigurationUsage getConfiguration();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.configuration.ConfigurationUsage#getConfiguration <em>Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration</em>' containment reference.
	 * @see #getConfiguration()
	 * @generated
	 */
	void setConfiguration(ConfigurationOfConfigurationUsage value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies the (optional) prefix that shall be applied to all paths in the external configuration document. This allows pre-defined configuration documents to be re-used for the configuration of components in various places in the model hierarchy.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getConfigurationUsage_Path()
	 * @model dataType="org.eclipse.xsmp.smp.core.elements.Path"
	 *        extendedMetaData="kind='attribute' name='Path'"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.configuration.ConfigurationUsage#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

} // ConfigurationUsage
