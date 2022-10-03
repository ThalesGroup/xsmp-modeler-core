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

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Date Time Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A DateTimeValue holds a value for an item of type DateTime.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.DateTimeValue#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getDateTimeValue()
 * @model extendedMetaData="name='DateTimeValue' kind='empty'"
 * @generated
 */
public interface DateTimeValue extends SimpleValue
{
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Value of type DateTime.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(XMLGregorianCalendar)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getDateTimeValue_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime" required="true"
	 *        extendedMetaData="kind='attribute' name='Value'"
	 * @generated
	 */
	XMLGregorianCalendar getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.DateTimeValue#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(XMLGregorianCalendar value);

} // DateTimeValue
