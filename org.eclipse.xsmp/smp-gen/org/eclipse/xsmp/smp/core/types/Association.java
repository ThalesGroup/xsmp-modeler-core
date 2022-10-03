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
 * A representation of the model object '<em><b>Association</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An Association is a feature that is typed by a language type (Type link). An association always expresses a reference to an instance of the referenced language type. This reference is either another model (if the Type link refers to a Model or Interface), or it is a field contained in another model (if the Type link refers to a ValueType).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Association#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getAssociation()
 * @model extendedMetaData="name='Association' kind='elementOnly'"
 * @generated
 */
public interface Association extends VisibilityElement
{
	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Type that the association points to.
	 * Link destination type: Types:LanguageType
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(ElementReference)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getAssociation_Type()
	 * @model containment="true" required="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:LanguageType'"
	 *        extendedMetaData="kind='element' name='Type'"
	 * @generated
	 */
	ElementReference<LanguageType> getType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Association#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ElementReference<LanguageType> value);

} // Association
