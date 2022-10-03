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
 * A representation of the model object '<em><b>Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An Operation may have an arbitrary number of parameters, where at most one of the parameters may be of Direction = ParameterDirectionKind.return. If such a parameter is absent, the operation is a void function (procedure) without return value.
 * An Operation may specify an arbitrary number of exceptions that it can raise (RaisedException).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Operation#getParameter <em>Parameter</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Operation#getRaisedException <em>Raised Exception</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getOperation()
 * @model extendedMetaData="name='Operation' kind='elementOnly'"
 * @generated
 */
public interface Operation extends VisibilityElement
{
	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Parameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of parameters of the operation.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Parameter</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getOperation_Parameter()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Parameter'"
	 * @generated
	 */
	EList<Parameter> getParameter();

	/**
	 * Returns the value of the '<em><b>Raised Exception</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.ElementReference}<code>&lt;org.eclipse.xsmp.smp.core.types.Exception&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of exceptions that may be raised by the operation.
	 * Link destination type: Types:Exception
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Raised Exception</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getOperation_RaisedException()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:Exception'"
	 *        extendedMetaData="kind='element' name='RaisedException'"
	 * @generated
	 */
	EList<ElementReference<org.eclipse.xsmp.smp.core.types.Exception>> getRaisedException();

} // Operation
