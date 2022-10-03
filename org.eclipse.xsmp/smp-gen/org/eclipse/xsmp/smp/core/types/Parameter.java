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

import org.eclipse.xsmp.smp.core.elements.NamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Parameter has a Type and a Direction, where the direction may have the values in, out, inout or return (see ParameterDirectionKind).
 * When referencing a value type, a parameter may have an additional Default value, which can be used by languages that support default values for parameters.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Parameter#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Parameter#getDefault <em>Default</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Parameter#getDirection <em>Direction</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getParameter()
 * @model extendedMetaData="name='Parameter' kind='elementOnly'"
 * @generated
 */
public interface Parameter extends NamedElement
{
	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Type of the parameter.
	 * Link destination type: Types:LanguageType
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(ElementReference)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getParameter_Type()
	 * @model containment="true" required="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:LanguageType'"
	 *        extendedMetaData="kind='element' name='Type'"
	 * @generated
	 */
	ElementReference<LanguageType> getType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Parameter#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ElementReference<LanguageType> value);

	/**
	 * Returns the value of the '<em><b>Default</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Default value of the parameter.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Default</em>' containment reference.
	 * @see #setDefault(Value)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getParameter_Default()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Default'"
	 * @generated
	 */
	Value getDefault();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Parameter#getDefault <em>Default</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default</em>' containment reference.
	 * @see #getDefault()
	 * @generated
	 */
	void setDefault(Value value);

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The default value is <code>"in"</code>.
	 * The literals are from the enumeration {@link org.eclipse.xsmp.smp.core.types.ParameterDirectionKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Direction of the parameter.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see org.eclipse.xsmp.smp.core.types.ParameterDirectionKind
	 * @see #isSetDirection()
	 * @see #unsetDirection()
	 * @see #setDirection(ParameterDirectionKind)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getParameter_Direction()
	 * @model default="in" unsettable="true"
	 *        extendedMetaData="kind='attribute' name='Direction'"
	 * @generated
	 */
	ParameterDirectionKind getDirection();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Parameter#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see org.eclipse.xsmp.smp.core.types.ParameterDirectionKind
	 * @see #isSetDirection()
	 * @see #unsetDirection()
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(ParameterDirectionKind value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Parameter#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDirection()
	 * @see #getDirection()
	 * @see #setDirection(ParameterDirectionKind)
	 * @generated
	 */
	void unsetDirection();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Parameter#getDirection <em>Direction</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Direction</em>' attribute is set.
	 * @see #unsetDirection()
	 * @see #getDirection()
	 * @see #setDirection(ParameterDirectionKind)
	 * @generated
	 */
	boolean isSetDirection();

} // Parameter
