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

import java.lang.String;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Integer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An Integer type represents integer values with a given range of valid values (via the Minimum and Maximum attributes). The Unit element can hold a physical unit that can be used by applications to ensure physical unit integrity across models.
 * Optionally, the PrimitiveType used to encode the integer value may be specified (one of Int8, Int16, Int32, Int64, UIn8, UInt16, UInt32, UInt64, where the default is Int32).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Integer#getPrimitiveType <em>Primitive Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Integer#getMinimum <em>Minimum</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Integer#getMaximum <em>Maximum</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Integer#getUnit <em>Unit</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getInteger()
 * @model extendedMetaData="name='Integer' kind='elementOnly'"
 * @generated
 */
public interface Integer extends SimpleType
{
	/**
	 * Returns the value of the '<em><b>Primitive Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Primitive type of the integer type.
	 * Link destination type: Types:PrimitiveType
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Primitive Type</em>' containment reference.
	 * @see #setPrimitiveType(ElementReference)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getInteger_PrimitiveType()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:PrimitiveType'"
	 *        extendedMetaData="kind='element' name='PrimitiveType'"
	 * @generated
	 */
	ElementReference<PrimitiveType> getPrimitiveType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Integer#getPrimitiveType <em>Primitive Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primitive Type</em>' containment reference.
	 * @see #getPrimitiveType()
	 * @generated
	 */
	void setPrimitiveType(ElementReference<PrimitiveType> value);

	/**
	 * Returns the value of the '<em><b>Minimum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Minimum value of the integer type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Minimum</em>' attribute.
	 * @see #isSetMinimum()
	 * @see #unsetMinimum()
	 * @see #setMinimum(long)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getInteger_Minimum()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
	 *        extendedMetaData="kind='attribute' name='Minimum'"
	 * @generated
	 */
	long getMinimum();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Integer#getMinimum <em>Minimum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minimum</em>' attribute.
	 * @see #isSetMinimum()
	 * @see #unsetMinimum()
	 * @see #getMinimum()
	 * @generated
	 */
	void setMinimum(long value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Integer#getMinimum <em>Minimum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMinimum()
	 * @see #getMinimum()
	 * @see #setMinimum(long)
	 * @generated
	 */
	void unsetMinimum();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Integer#getMinimum <em>Minimum</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Minimum</em>' attribute is set.
	 * @see #unsetMinimum()
	 * @see #getMinimum()
	 * @see #setMinimum(long)
	 * @generated
	 */
	boolean isSetMinimum();

	/**
	 * Returns the value of the '<em><b>Maximum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Maximum value of the integer type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Maximum</em>' attribute.
	 * @see #isSetMaximum()
	 * @see #unsetMaximum()
	 * @see #setMaximum(long)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getInteger_Maximum()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
	 *        extendedMetaData="kind='attribute' name='Maximum'"
	 * @generated
	 */
	long getMaximum();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Integer#getMaximum <em>Maximum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Maximum</em>' attribute.
	 * @see #isSetMaximum()
	 * @see #unsetMaximum()
	 * @see #getMaximum()
	 * @generated
	 */
	void setMaximum(long value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Integer#getMaximum <em>Maximum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMaximum()
	 * @see #getMaximum()
	 * @see #setMaximum(long)
	 * @generated
	 */
	void unsetMaximum();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Integer#getMaximum <em>Maximum</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Maximum</em>' attribute is set.
	 * @see #unsetMaximum()
	 * @see #getMaximum()
	 * @see #setMaximum(long)
	 * @generated
	 */
	boolean isSetMaximum();

	/**
	 * Returns the value of the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unit of the integer type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Unit</em>' attribute.
	 * @see #setUnit(String)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getInteger_Unit()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Unit'"
	 * @generated
	 */
	String getUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Integer#getUnit <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' attribute.
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(String value);

} // Integer
