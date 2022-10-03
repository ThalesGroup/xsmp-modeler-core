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

import javax.xml.datatype.Duration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Duration Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A DurationValue holds a value for an item of type Duration.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.DurationValue#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getDurationValue()
 * @model extendedMetaData="name='DurationValue' kind='empty'"
 * @generated
 */
public interface DurationValue extends SimpleValue
{
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Value of type Duration.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Duration)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getDurationValue_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Duration" required="true"
	 *        extendedMetaData="kind='attribute' name='Value'"
	 * @generated
	 */
	Duration getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.DurationValue#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Duration value);

} // DurationValue
