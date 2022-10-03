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
 * A representation of the model object '<em><b>Structure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Structure type collects an arbitrary number of Fields representing the state of the structure. 
 * Within a structure, each field needs to be given a unique name. In order to arrive at semantically correct (data) type definitions, a structure type may not be recursive, i.e. a structure may not have a field that is typed by the structure itself.
 * A structure can also serve as a namespace to define an arbitrary number of Constants.
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Structure#getConstant <em>Constant</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Structure#getField <em>Field</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getStructure()
 * @model extendedMetaData="name='Structure' kind='elementOnly'"
 * @generated
 */
public interface Structure extends ValueType
{
	/**
	 * Returns the value of the '<em><b>Constant</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Constant}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of constants of the structure.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Constant</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getStructure_Constant()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Constant'"
	 * @generated
	 */
	EList<Constant> getConstant();

	/**
	 * Returns the value of the '<em><b>Field</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Field}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of fields of the structure.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Field</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getStructure_Field()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Field'"
	 * @generated
	 */
	EList<Field> getField();

} // Structure
