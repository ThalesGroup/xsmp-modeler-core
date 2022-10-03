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
package org.eclipse.xsmp.smp.smdl.catalogue;

import org.eclipse.emf.common.util.EList;

import org.eclipse.xsmp.smp.core.types.Constant;
import org.eclipse.xsmp.smp.core.types.LanguageType;
import org.eclipse.xsmp.smp.core.types.Operation;
import org.eclipse.xsmp.smp.core.types.Property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
 *      ReferenceType serves as an abstract base metaclass for Interface and Component. An instance of a ReferenceType is identified by a reference to it - as opposed to an instance of a ValueType which is identified by its value. A reference type may have various optional elements: 
 *     <xhtml:ul>
 *                         
 *       <xhtml:li> Constant elements specify constants defined in the reference type's name scope; </xhtml:li>
 *                         
 *       <xhtml:li> Property elements specify the reference type's properties; and </xhtml:li>
 *                         
 *       <xhtml:li> Operation elements specify the reference type's operations. </xhtml:li>
 *                      
 *     </xhtml:ul>
 *                 
 *   </xhtml:p>
 *            
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.ReferenceType#getConstant <em>Constant</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.ReferenceType#getProperty <em>Property</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.ReferenceType#getOperation <em>Operation</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getReferenceType()
 * @model abstract="true"
 *        extendedMetaData="name='ReferenceType' kind='elementOnly'"
 * @generated
 */
public interface ReferenceType extends LanguageType
{
	/**
	 * Returns the value of the '<em><b>Constant</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Constant}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of constants.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Constant</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getReferenceType_Constant()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Constant'"
	 * @generated
	 */
	EList<Constant> getConstant();

	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Property}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of properties.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getReferenceType_Property()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Property'"
	 * @generated
	 */
	EList<Property> getProperty();

	/**
	 * Returns the value of the '<em><b>Operation</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Operation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of operations.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Operation</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getReferenceType_Operation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Operation'"
	 * @generated
	 */
	EList<Operation> getOperation();

} // ReferenceType
