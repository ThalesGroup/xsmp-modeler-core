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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Field is a feature that is typed by any value type but String8, and that may have a Default value.
 * The State attribute defines how the field is published to the simulation environment. Only fields with a State of true are stored using external persistence. The visibility to the user within the simulation environment can be controlled via the standard SMP attribute "View". By default, the State flag is set to true and the View attribute defaults to "None" when not applied.
 * The Input and Output attributes define whether the field value is an input for internal calculations (i.e. needed in order to perform these calculations), or an output of internal calculations (i.e. modified when performing these calculations). These flags default to false, but can be changed from their default value to support dataflow-based design.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Field#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Field#getDefault <em>Default</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Field#isState <em>State</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Field#isInput <em>Input</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Field#isOutput <em>Output</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getField()
 * @model extendedMetaData="name='Field' kind='elementOnly'"
 * @generated
 */
public interface Field extends VisibilityElement
{
	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Type of the field, which has to be a value type.
	 * Link destination type: Types:ValueType
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(ElementReference)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getField_Type()
	 * @model containment="true" required="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:ValueType'"
	 *        extendedMetaData="kind='element' name='Type'"
	 * @generated
	 */
	ElementReference<ValueType> getType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Field#getType <em>Type</em>}' containment reference.
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
	 * Default value of the field.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Default</em>' containment reference.
	 * @see #setDefault(Value)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getField_Default()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Default'"
	 * @generated
	 */
	Value getDefault();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Field#getDefault <em>Default</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default</em>' containment reference.
	 * @see #getDefault()
	 * @generated
	 */
	void setDefault(Value value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * State flag of the field.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>State</em>' attribute.
	 * @see #isSetState()
	 * @see #unsetState()
	 * @see #setState(boolean)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getField_State()
	 * @model default="true" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='State'"
	 * @generated
	 */
	boolean isState();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Field#isState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' attribute.
	 * @see #isSetState()
	 * @see #unsetState()
	 * @see #isState()
	 * @generated
	 */
	void setState(boolean value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Field#isState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetState()
	 * @see #isState()
	 * @see #setState(boolean)
	 * @generated
	 */
	void unsetState();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Field#isState <em>State</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>State</em>' attribute is set.
	 * @see #unsetState()
	 * @see #isState()
	 * @see #setState(boolean)
	 * @generated
	 */
	boolean isSetState();

	/**
	 * Returns the value of the '<em><b>Input</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Input flag of the field.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Input</em>' attribute.
	 * @see #isSetInput()
	 * @see #unsetInput()
	 * @see #setInput(boolean)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getField_Input()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='Input'"
	 * @generated
	 */
	boolean isInput();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Field#isInput <em>Input</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input</em>' attribute.
	 * @see #isSetInput()
	 * @see #unsetInput()
	 * @see #isInput()
	 * @generated
	 */
	void setInput(boolean value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Field#isInput <em>Input</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetInput()
	 * @see #isInput()
	 * @see #setInput(boolean)
	 * @generated
	 */
	void unsetInput();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Field#isInput <em>Input</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Input</em>' attribute is set.
	 * @see #unsetInput()
	 * @see #isInput()
	 * @see #setInput(boolean)
	 * @generated
	 */
	boolean isSetInput();

	/**
	 * Returns the value of the '<em><b>Output</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Output flag of the field.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Output</em>' attribute.
	 * @see #isSetOutput()
	 * @see #unsetOutput()
	 * @see #setOutput(boolean)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getField_Output()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='Output'"
	 * @generated
	 */
	boolean isOutput();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Field#isOutput <em>Output</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output</em>' attribute.
	 * @see #isSetOutput()
	 * @see #unsetOutput()
	 * @see #isOutput()
	 * @generated
	 */
	void setOutput(boolean value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Field#isOutput <em>Output</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOutput()
	 * @see #isOutput()
	 * @see #setOutput(boolean)
	 * @generated
	 */
	void unsetOutput();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Field#isOutput <em>Output</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Output</em>' attribute is set.
	 * @see #unsetOutput()
	 * @see #isOutput()
	 * @see #setOutput(boolean)
	 * @generated
	 */
	boolean isSetOutput();

} // Field
