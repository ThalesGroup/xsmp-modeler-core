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
 * A representation of the model object '<em><b>Float</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Float type represents floating-point values with a given range of valid values (via the Minimum and Maximum attributes). The MinInclusive and MaxInclusive attributes determine whether the boundaries are included in the range or not. The Unit element can hold a physical unit that can be used by applications to ensure physical unit integrity across models.
 * Optionally, the PrimitiveType used to encode the floating-point value may be specified (one of Float32 or Float64, where the default is Float64).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Float#getPrimitiveType <em>Primitive Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Float#getMaximum <em>Maximum</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Float#isMinInclusive <em>Min Inclusive</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Float#getMinimum <em>Minimum</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Float#isMaxInclusive <em>Max Inclusive</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Float#getUnit <em>Unit</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getFloat()
 * @model extendedMetaData="name='Float' kind='elementOnly'"
 * @generated
 */
public interface Float extends SimpleType
{
	/**
	 * Returns the value of the '<em><b>Primitive Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Primitive type of the float type.
	 * Link destination type: Types:PrimitiveType
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Primitive Type</em>' containment reference.
	 * @see #setPrimitiveType(ElementReference)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getFloat_PrimitiveType()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:PrimitiveType'"
	 *        extendedMetaData="kind='element' name='PrimitiveType'"
	 * @generated
	 */
	ElementReference<PrimitiveType> getPrimitiveType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#getPrimitiveType <em>Primitive Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primitive Type</em>' containment reference.
	 * @see #getPrimitiveType()
	 * @generated
	 */
	void setPrimitiveType(ElementReference<PrimitiveType> value);

	/**
	 * Returns the value of the '<em><b>Maximum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Maximum value of the float type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Maximum</em>' attribute.
	 * @see #isSetMaximum()
	 * @see #unsetMaximum()
	 * @see #setMaximum(double)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getFloat_Maximum()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='Maximum'"
	 * @generated
	 */
	double getMaximum();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#getMaximum <em>Maximum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Maximum</em>' attribute.
	 * @see #isSetMaximum()
	 * @see #unsetMaximum()
	 * @see #getMaximum()
	 * @generated
	 */
	void setMaximum(double value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#getMaximum <em>Maximum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMaximum()
	 * @see #getMaximum()
	 * @see #setMaximum(double)
	 * @generated
	 */
	void unsetMaximum();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#getMaximum <em>Maximum</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Maximum</em>' attribute is set.
	 * @see #unsetMaximum()
	 * @see #getMaximum()
	 * @see #setMaximum(double)
	 * @generated
	 */
	boolean isSetMaximum();

	/**
	 * Returns the value of the '<em><b>Min Inclusive</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * True if the minimum value is a valid value, false otherwise.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Min Inclusive</em>' attribute.
	 * @see #isSetMinInclusive()
	 * @see #unsetMinInclusive()
	 * @see #setMinInclusive(boolean)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getFloat_MinInclusive()
	 * @model default="true" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='MinInclusive'"
	 * @generated
	 */
	boolean isMinInclusive();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#isMinInclusive <em>Min Inclusive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Inclusive</em>' attribute.
	 * @see #isSetMinInclusive()
	 * @see #unsetMinInclusive()
	 * @see #isMinInclusive()
	 * @generated
	 */
	void setMinInclusive(boolean value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#isMinInclusive <em>Min Inclusive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMinInclusive()
	 * @see #isMinInclusive()
	 * @see #setMinInclusive(boolean)
	 * @generated
	 */
	void unsetMinInclusive();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#isMinInclusive <em>Min Inclusive</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Min Inclusive</em>' attribute is set.
	 * @see #unsetMinInclusive()
	 * @see #isMinInclusive()
	 * @see #setMinInclusive(boolean)
	 * @generated
	 */
	boolean isSetMinInclusive();

	/**
	 * Returns the value of the '<em><b>Minimum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Minimum value of the float type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Minimum</em>' attribute.
	 * @see #isSetMinimum()
	 * @see #unsetMinimum()
	 * @see #setMinimum(double)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getFloat_Minimum()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='Minimum'"
	 * @generated
	 */
	double getMinimum();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#getMinimum <em>Minimum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minimum</em>' attribute.
	 * @see #isSetMinimum()
	 * @see #unsetMinimum()
	 * @see #getMinimum()
	 * @generated
	 */
	void setMinimum(double value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#getMinimum <em>Minimum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMinimum()
	 * @see #getMinimum()
	 * @see #setMinimum(double)
	 * @generated
	 */
	void unsetMinimum();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#getMinimum <em>Minimum</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Minimum</em>' attribute is set.
	 * @see #unsetMinimum()
	 * @see #getMinimum()
	 * @see #setMinimum(double)
	 * @generated
	 */
	boolean isSetMinimum();

	/**
	 * Returns the value of the '<em><b>Max Inclusive</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * True if the maximum value is a valid value, false otherwise.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Inclusive</em>' attribute.
	 * @see #isSetMaxInclusive()
	 * @see #unsetMaxInclusive()
	 * @see #setMaxInclusive(boolean)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getFloat_MaxInclusive()
	 * @model default="true" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='MaxInclusive'"
	 * @generated
	 */
	boolean isMaxInclusive();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#isMaxInclusive <em>Max Inclusive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Inclusive</em>' attribute.
	 * @see #isSetMaxInclusive()
	 * @see #unsetMaxInclusive()
	 * @see #isMaxInclusive()
	 * @generated
	 */
	void setMaxInclusive(boolean value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#isMaxInclusive <em>Max Inclusive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMaxInclusive()
	 * @see #isMaxInclusive()
	 * @see #setMaxInclusive(boolean)
	 * @generated
	 */
	void unsetMaxInclusive();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#isMaxInclusive <em>Max Inclusive</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Max Inclusive</em>' attribute is set.
	 * @see #unsetMaxInclusive()
	 * @see #isMaxInclusive()
	 * @see #setMaxInclusive(boolean)
	 * @generated
	 */
	boolean isSetMaxInclusive();

	/**
	 * Returns the value of the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unit of the float type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Unit</em>' attribute.
	 * @see #setUnit(String)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getFloat_Unit()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Unit'"
	 * @generated
	 */
	String getUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Float#getUnit <em>Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' attribute.
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(String value);

} // Float
