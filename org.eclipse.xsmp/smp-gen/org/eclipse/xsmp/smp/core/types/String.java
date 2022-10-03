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
 * A representation of the model object '<em><b>String</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A String type represents fixed Length string values base on Char8. The String language element defines an Array of Char8 values, but allows a more natural handling of it, e.g. by storing a string value as one string, not as an array of individual characters.
 * As with arrays, SMDL does not allow defining variable-sized strings, as these have the same problems as dynamic arrays (e.g. their size is not know up-front, and their use requires memory allocation).
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.String#getLength <em>Length</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getString()
 * @model extendedMetaData="name='String' kind='elementOnly'"
 * @generated
 */
public interface String extends SimpleType
{
	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Maximum length of the string.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #isSetLength()
	 * @see #unsetLength()
	 * @see #setLength(long)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getString_Length()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
	 *        extendedMetaData="kind='attribute' name='Length'"
	 * @generated
	 */
	long getLength();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.String#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #isSetLength()
	 * @see #unsetLength()
	 * @see #getLength()
	 * @generated
	 */
	void setLength(long value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.String#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLength()
	 * @see #getLength()
	 * @see #setLength(long)
	 * @generated
	 */
	void unsetLength();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.String#getLength <em>Length</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Length</em>' attribute is set.
	 * @see #unsetLength()
	 * @see #getLength()
	 * @see #setLength(long)
	 * @generated
	 */
	boolean isSetLength();

} // String
