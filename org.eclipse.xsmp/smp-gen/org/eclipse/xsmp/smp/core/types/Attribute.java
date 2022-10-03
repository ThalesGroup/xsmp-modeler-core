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

import org.eclipse.xsmp.smp.core.elements.Metadata;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An Attribute element holds name-value pairs allowing to attach user-defined metadata to any Element.
 * 
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
 *                    
 *     <xhtml:b>
 *                         
 *       <xhtml:i> Remark </xhtml:i>
 *                      
 *     </xhtml:b>
 *      : This provides a similar mechanism as tagged values in UML, xsd:appinfo in XML Schema, annotations in Java 5.0 or attributes in the .NET framework. 
 *   </xhtml:p>
 *            
 * 
 * 
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
 *                    
 *     <xhtml:b>
 *                         
 *       <xhtml:i> Remark </xhtml:i>
 *                      
 *     </xhtml:b>
 *      : A possible application of using attributes could be to decorate an SMDL model with information needed to guide a code generator, for example to tailor the mapping to C++. 
 *   </xhtml:p>
 *            
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Attribute#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Attribute#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getAttribute()
 * @model extendedMetaData="name='Attribute' kind='elementOnly'"
 * @generated
 */
public interface Attribute extends Metadata
{
	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Type of the attribute.
	 * Link destination type: Types:AttributeType
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(ElementReference)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getAttribute_Type()
	 * @model containment="true" required="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:AttributeType'"
	 *        extendedMetaData="kind='element' name='Type'"
	 * @generated
	 */
	ElementReference<AttributeType> getType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Attribute#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ElementReference<AttributeType> value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Value of the attribute.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(Value)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getAttribute_Value()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Value'"
	 * @generated
	 */
	Value getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Attribute#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Value value);

} // Attribute
