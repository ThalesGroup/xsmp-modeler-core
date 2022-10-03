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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enumeration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An Enumeration type represents one of a number of pre-defined enumeration literals. The Enumeration language element can be used to create user-defined enumeration types. An enumeration must always contain at least one EnumerationLiteral, each having a name and an integer Value attached to it.
 * All enumeration literals of an enumeration type must have unique names and values, respectively.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Enumeration#getLiteral <em>Literal</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getEnumeration()
 * @model extendedMetaData="name='Enumeration' kind='elementOnly'"
 * @generated
 */
public interface Enumeration extends SimpleType
{
	/**
	 * Returns the value of the '<em><b>Literal</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.EnumerationLiteral}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of enumeration literals.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Literal</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getEnumeration_Literal()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Literal'"
	 * @generated
	 */
	EList<EnumerationLiteral> getLiteral();

} // Enumeration
