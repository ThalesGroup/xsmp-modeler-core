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
 * A representation of the model object '<em><b>Date Time Array Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The DateTimeArrayValue holds an array of DateTimeValue items for an array of type DateTime.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.DateTimeArrayValue#getItemValue <em>Item Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getDateTimeArrayValue()
 * @model extendedMetaData="name='DateTimeArrayValue' kind='elementOnly'"
 * @generated
 */
public interface DateTimeArrayValue extends SimpleArrayValue
{
	/**
	 * Returns the value of the '<em><b>Item Value</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.DateTimeValue}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of item values.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Item Value</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getDateTimeArrayValue_ItemValue()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ItemValue'"
	 * @generated
	 */
	EList<DateTimeValue> getItemValue();

} // DateTimeArrayValue
