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
package org.eclipse.xsmp.smp.core.types;

import java.lang.String;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An AttributeType defines a new type available for adding attributes to elements. The AllowMultiple attribute specifies if a corresponding Attribute may be attached more than once to a language element, while the Usage element defines to which language elements attributes of this type can be attached. An attribute type always references a value type, and specifies a Default value.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.AttributeType#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.AttributeType#getDefault <em>Default</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.AttributeType#getUsage <em>Usage</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.AttributeType#isAllowMultiple <em>Allow Multiple</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getAttributeType()
 * @model extendedMetaData="name='AttributeType' kind='elementOnly'"
 * @generated
 */
public interface AttributeType extends Type
{
	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Type of the attribute.
	 * Link destination type: Types:ValueType
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(ElementReference)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getAttributeType_Type()
	 * @model containment="true" required="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:ValueType'"
	 *        extendedMetaData="kind='element' name='Type'"
	 * @generated
	 */
	ElementReference<ValueType> getType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.AttributeType#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ElementReference<ValueType> value);

	/**
	 * Returns the value of the '<em><b>Default</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Default value of the attribute.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Default</em>' containment reference.
	 * @see #setDefault(Value)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getAttributeType_Default()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Default'"
	 * @generated
	 */
	Value getDefault();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.AttributeType#getDefault <em>Default</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default</em>' containment reference.
	 * @see #getDefault()
	 * @generated
	 */
	void setDefault(Value value);

	/**
	 * Returns the value of the '<em><b>Usage</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of metaclasses the attribute can be applied to.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Usage</em>' attribute list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getAttributeType_Usage()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='Usage'"
	 * @generated
	 */
	EList<String> getUsage();

	/**
	 * Returns the value of the '<em><b>Allow Multiple</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * True if the attribute can be applied multiple times, false otherwise.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Allow Multiple</em>' attribute.
	 * @see #isSetAllowMultiple()
	 * @see #unsetAllowMultiple()
	 * @see #setAllowMultiple(boolean)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getAttributeType_AllowMultiple()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='AllowMultiple'"
	 * @generated
	 */
	boolean isAllowMultiple();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.AttributeType#isAllowMultiple <em>Allow Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Allow Multiple</em>' attribute.
	 * @see #isSetAllowMultiple()
	 * @see #unsetAllowMultiple()
	 * @see #isAllowMultiple()
	 * @generated
	 */
	void setAllowMultiple(boolean value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.AttributeType#isAllowMultiple <em>Allow Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAllowMultiple()
	 * @see #isAllowMultiple()
	 * @see #setAllowMultiple(boolean)
	 * @generated
	 */
	void unsetAllowMultiple();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.AttributeType#isAllowMultiple <em>Allow Multiple</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Allow Multiple</em>' attribute is set.
	 * @see #unsetAllowMultiple()
	 * @see #isAllowMultiple()
	 * @see #setAllowMultiple(boolean)
	 * @generated
	 */
	boolean isSetAllowMultiple();

} // AttributeType
