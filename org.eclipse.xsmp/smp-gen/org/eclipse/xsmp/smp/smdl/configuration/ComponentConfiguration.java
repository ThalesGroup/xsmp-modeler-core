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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.xsmp.smp.core.types.Value;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
 *      A ComponentConfiguration defines field values of a component instance (model or service instance). The component instance is specified via the Path attribute, which can be 
 *     <xhtml:ul>
 *                         
 *       <xhtml:li> absolute, i.e. the path starts with a slash "/", and at the root of the simulation tree (e.g. Path="/Spacecraft/AOCS" or Path="/Logger"), or </xhtml:li>
 *                         
 *       <xhtml:li> relative, i.e. the path does not start with a slash "/" and is appended to the path of the parent component configuration. </xhtml:li>
 *                      
 *     </xhtml:ul>
 *      Each FieldValue is an instance of one of the available Value metaclasses. A FieldValue has to reference the corresponding field of the component via its Field attribute which specifies the field's local name/path (e.g. Field="field1" or Field="struct1.structField2"). In addition to the ability to define a hierarchy of component configurations via the Component element, the Include element enables to include another Configuration file using a relative or absolute Path for it. 
 *   </xhtml:p>
 *            
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.ComponentConfiguration#getInclude <em>Include</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.ComponentConfiguration#getComponent <em>Component</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.ComponentConfiguration#getFieldValue <em>Field Value</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.configuration.ComponentConfiguration#getPath <em>Path</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getComponentConfiguration()
 * @model extendedMetaData="name='ComponentConfiguration' kind='elementOnly'"
 * @generated
 */
public interface ComponentConfiguration extends EObject
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
	 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getComponentConfiguration_Include()
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
	 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getComponentConfiguration_Component()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Component'"
	 * @generated
	 */
	EList<ComponentConfiguration> getComponent();

	/**
	 * Returns the value of the '<em><b>Field Value</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Value}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of field values.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Field Value</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getComponentConfiguration_FieldValue()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='FieldValue'"
	 * @generated
	 */
	EList<Value> getFieldValue();

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Path to the component to configure, in the format accepted by the Resolver service.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see org.eclipse.xsmp.smp.smdl.configuration.ConfigurationPackage#getComponentConfiguration_Path()
	 * @model dataType="org.eclipse.xsmp.smp.core.elements.Path" required="true"
	 *        extendedMetaData="kind='attribute' name='Path'"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.configuration.ComponentConfiguration#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

} // ComponentConfiguration
