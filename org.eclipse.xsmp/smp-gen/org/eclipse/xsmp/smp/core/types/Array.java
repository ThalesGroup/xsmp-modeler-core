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
 * A representation of the model object '<em><b>Array</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An Array type defines a fixed-size array of identically typed elements, where ItemType defines the type of the array items, and Size defines the number of array items.
 * Multi-dimensional arrays are defined when ItemType is an Array type as well.
 * Dynamic arrays are not supported by SMDL, as they are not supported by some potential target platforms, and introduce various difficulties in memory management. 
 * 
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
 *                    
 *     <xhtml:b>
 *                         
 *       <xhtml:i> Remarks </xhtml:i>
 *                      
 *     </xhtml:b>
 *      : Nevertheless, specific mechanisms are available to allow dynamic collections of components, either for containment (composition) or references (aggregation). 
 *   </xhtml:p>
 *            
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Array#getItemType <em>Item Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Array#getSize <em>Size</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getArray()
 * @model extendedMetaData="name='Array' kind='elementOnly'"
 * @generated
 */
public interface Array extends ValueType
{
	/**
	 * Returns the value of the '<em><b>Item Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Type of each item of the array.
	 * Link destination type: Types:ValueType
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Item Type</em>' containment reference.
	 * @see #setItemType(ElementReference)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getArray_ItemType()
	 * @model containment="true" required="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:ValueType'"
	 *        extendedMetaData="kind='element' name='ItemType'"
	 * @generated
	 */
	ElementReference<ValueType> getItemType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Array#getItemType <em>Item Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Item Type</em>' containment reference.
	 * @see #getItemType()
	 * @generated
	 */
	void setItemType(ElementReference<ValueType> value);

	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Size of the array.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see #isSetSize()
	 * @see #unsetSize()
	 * @see #setSize(long)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getArray_Size()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long" required="true"
	 *        extendedMetaData="kind='attribute' name='Size'"
	 * @generated
	 */
	long getSize();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Array#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see #isSetSize()
	 * @see #unsetSize()
	 * @see #getSize()
	 * @generated
	 */
	void setSize(long value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Array#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSize()
	 * @see #getSize()
	 * @see #setSize(long)
	 * @generated
	 */
	void unsetSize();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Array#getSize <em>Size</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Size</em>' attribute is set.
	 * @see #unsetSize()
	 * @see #getSize()
	 * @see #setSize(long)
	 * @generated
	 */
	boolean isSetSize();

} // Array
