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

import java.math.BigInteger;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>UInt64 Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A UInt64Value holds a value for an item of type UInt64.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.UInt64Value#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getUInt64Value()
 * @model extendedMetaData="name='UInt64Value' kind='empty'"
 * @generated
 */
public interface UInt64Value extends SimpleValue
{
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Value of type UInt64.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(BigInteger)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getUInt64Value_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.UnsignedLong" required="true"
	 *        extendedMetaData="kind='attribute' name='Value'"
	 * @generated
	 */
	BigInteger getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.UInt64Value#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(BigInteger value);

} // UInt64Value
