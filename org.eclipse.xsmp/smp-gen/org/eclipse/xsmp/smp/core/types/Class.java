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
 * A representation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The Class metaclass is derived from Structure. A class may be abstract (attribute Abstract), and it may inherit from a single base class (implementation inheritance), which is represented by the Base link.
 * As the Class metaclass is derived from Structure it can contain constants and fields. Further, it can have arbitrary numbers of properties (Property elements), operations (Operation elements), and associations (Association elements).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Class#getBase <em>Base</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Class#getProperty <em>Property</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Class#getOperation <em>Operation</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Class#getAssociation <em>Association</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Class#isAbstract <em>Abstract</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getClass_()
 * @model extendedMetaData="name='Class' kind='elementOnly'"
 * @generated
 */
public interface Class extends Structure
{
	/**
	 * Returns the value of the '<em><b>Base</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Optional base class of the class, to support single inheritance.
	 * Link destination type: Types:Class
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Base</em>' containment reference.
	 * @see #setBase(ElementReference)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getClass_Base()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:Class'"
	 *        extendedMetaData="kind='element' name='Base'"
	 * @generated
	 */
	ElementReference<Class> getBase();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Class#getBase <em>Base</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base</em>' containment reference.
	 * @see #getBase()
	 * @generated
	 */
	void setBase(ElementReference<Class> value);

	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Property}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of properties.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getClass_Property()
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
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getClass_Operation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Operation'"
	 * @generated
	 */
	EList<Operation> getOperation();

	/**
	 * Returns the value of the '<em><b>Association</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Association}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of associations.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Association</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getClass_Association()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Association'"
	 * @generated
	 */
	EList<Association> getAssociation();

	/**
	 * Returns the value of the '<em><b>Abstract</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Flag whether the class is abstract.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Abstract</em>' attribute.
	 * @see #isSetAbstract()
	 * @see #unsetAbstract()
	 * @see #setAbstract(boolean)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getClass_Abstract()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='Abstract'"
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Class#isAbstract <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract</em>' attribute.
	 * @see #isSetAbstract()
	 * @see #unsetAbstract()
	 * @see #isAbstract()
	 * @generated
	 */
	void setAbstract(boolean value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Class#isAbstract <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAbstract()
	 * @see #isAbstract()
	 * @see #setAbstract(boolean)
	 * @generated
	 */
	void unsetAbstract();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Class#isAbstract <em>Abstract</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Abstract</em>' attribute is set.
	 * @see #unsetAbstract()
	 * @see #isAbstract()
	 * @see #setAbstract(boolean)
	 * @generated
	 */
	boolean isSetAbstract();

} // Class
