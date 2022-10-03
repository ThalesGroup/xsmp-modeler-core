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
 * A representation of the model object '<em><b>Int16 Array Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The Int16ArrayValue holds an array of Int16Value items for an array of type Int16.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Int16ArrayValue#getItemValue <em>Item Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getInt16ArrayValue()
 * @model extendedMetaData="name='Int16ArrayValue' kind='elementOnly'"
 * @generated
 */
public interface Int16ArrayValue extends SimpleArrayValue
{
	/**
	 * Returns the value of the '<em><b>Item Value</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Int16Value}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of item values.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Item Value</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getInt16ArrayValue_ItemValue()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ItemValue'"
	 * @generated
	 */
	EList<Int16Value> getItemValue();

} // Int16ArrayValue
