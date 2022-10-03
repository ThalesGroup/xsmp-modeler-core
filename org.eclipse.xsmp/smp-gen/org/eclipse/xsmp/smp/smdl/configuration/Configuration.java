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

import org.eclipse.emf.common.util.EList;

import org.eclipse.xsmp.smp.core.elements.Document;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Configuration acts as the root element (top-level node) in an SMDL Configuration document. A configuration contains a tree of component configurations that define field values of component instances (model or service instances). Further, a configuration may include other configuration documents via the Include element.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.Configuration#getInclude <em>Include</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.Configuration#getComponent <em>Component</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getConfiguration()
 * @model extendedMetaData="name='Configuration' kind='elementOnly'"
 * @generated
 */
public interface Configuration extends Document
{
	/**
	 * Returns the value of the '<em><b>Include</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.smdl.configuration.ConfigurationUsage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of configuration usages from external files.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Include</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getConfiguration_Include()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Include'"
	 * @generated
	 */
	EList<ConfigurationUsage> getInclude();

	/**
	 * Returns the value of the '<em><b>Component</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.smdl.configuration.ComponentConfiguration}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of component configurations to support a hierarchy.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Component</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getConfiguration_Component()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Component'"
	 * @generated
	 */
	EList<ComponentConfiguration> getComponent();

} // Configuration
