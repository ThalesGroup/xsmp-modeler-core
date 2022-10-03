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
 * A representation of the model object '<em><b>Structure Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A StructureValue holds field values for all fields of the corresponding structure type. Thereby, the Field attribute of each contained value specifies the local field name within the structure.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.StructureValue#getFieldValue <em>Field Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getStructureValue()
 * @model extendedMetaData="name='StructureValue' kind='elementOnly'"
 * @generated
 */
public interface StructureValue extends Value
{
	/**
	 * Returns the value of the '<em><b>Field Value</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Value}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of field values of the individual structure fields.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Field Value</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getStructureValue_FieldValue()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='FieldValue'"
	 * @generated
	 */
	EList<Value> getFieldValue();

} // StructureValue
