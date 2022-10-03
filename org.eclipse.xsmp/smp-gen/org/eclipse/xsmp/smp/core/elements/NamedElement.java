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
package org.eclipse.xsmp.smp.core.elements;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The metaclass NamedElement is the common base for most other language elements. A named element has an Id attribute for unique identification in an XML file, a Name attribute holding a human-readable name to be used in applications, and a Description element holding a human-readable description. Furthermore, a named element can hold an arbitrary number of metadata children.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.elements.NamedElement#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.elements.NamedElement#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.elements.NamedElement#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.elements.NamedElement#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.elements.ElementsPackage#getNamedElement()
 * @model extendedMetaData="name='NamedElement' kind='elementOnly'"
 * @generated
 */
public interface NamedElement extends EObject
{
	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The description of the element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.xsmp.smp.core.elements.ElementsPackage#getNamedElement_Description()
	 * @model dataType="org.eclipse.xsmp.smp.core.elements.Description"
	 *        extendedMetaData="kind='element' name='Description'"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.elements.NamedElement#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Metadata</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.elements.Metadata}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of optional metadata of the element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Metadata</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.elements.ElementsPackage#getNamedElement_Metadata()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Metadata'"
	 * @generated
	 */
	EList<Metadata> getMetadata();

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The unique identifier of the named element.
	 * This is typically a machine-readable identification of the element that can be used for referencing the element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.xsmp.smp.core.elements.ElementsPackage#getNamedElement_Id()
	 * @model id="true" dataType="org.eclipse.xsmp.smp.core.elements.Identifier" required="true" derived="true"
	 *        extendedMetaData="kind='attribute' name='Id'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.elements.NamedElement#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name of the named element that is suitable for use in programming languages such as C++, Java, or CORBA IDL.
	 * This is the element's name represented with only a limited character set specified by the Name type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.xsmp.smp.core.elements.ElementsPackage#getNamedElement_Name()
	 * @model dataType="org.eclipse.xsmp.smp.core.elements.Name" required="true"
	 *        extendedMetaData="kind='attribute' name='Name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.elements.NamedElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // NamedElement
