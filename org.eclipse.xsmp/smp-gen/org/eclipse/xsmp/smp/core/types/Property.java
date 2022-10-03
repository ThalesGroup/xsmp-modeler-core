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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Property has a similar syntax as a Field: It is a feature that references a language type. However, the semantics is different in that a property does not represent a state and that it can be assigned an Access attribute to specify how the property can be accessed (either readWrite, readOnly, or writeOnly, see AccessKind).
 * Furthermore, a property can be assigned a Category attribute to help grouping the properties within its owning type, and a property may specify an arbitrary number of exceptions that it can raise in its getter (GetRaises) and/or setter (SetRaises).
 * 
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
 *                    
 *     <xhtml:b>
 *                         
 *       <xhtml:i> Remark </xhtml:i>
 *                      
 *     </xhtml:b>
 *      : The category can be used in applications as ordering or filtering criterion, for example in a property grid. The term "property" used here closely corresponds in its semantics to the same term in the Java Beans specification and in the Microsoft .NET framework. That is, a property formally represents a "getter" or a "setter" operation or both which allow accessing state or configuration information (or derived information thereof) in a controlled way and which can also be exposed via interfaces (in contrast to fields). 
 *   </xhtml:p>
 *            
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Property#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Property#getAttachedField <em>Attached Field</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Property#getGetRaises <em>Get Raises</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Property#getSetRaises <em>Set Raises</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Property#getAccess <em>Access</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Property#getCategory <em>Category</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getProperty()
 * @model extendedMetaData="name='Property' kind='elementOnly'"
 * @generated
 */
public interface Property extends VisibilityElement
{
	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Type of the property.
	 * Link destination type: Types:LanguageType
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(ElementReference)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getProperty_Type()
	 * @model containment="true" required="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:LanguageType'"
	 *        extendedMetaData="kind='element' name='Type'"
	 * @generated
	 */
	ElementReference<LanguageType> getType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Property#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ElementReference<LanguageType> value);

	/**
	 * Returns the value of the '<em><b>Attached Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Attached field of the property, in case that the property shadows a field.
	 * Link destination type: Types:Field
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Attached Field</em>' containment reference.
	 * @see #setAttachedField(ElementReference)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getProperty_AttachedField()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:Field'"
	 *        extendedMetaData="kind='element' name='AttachedField'"
	 * @generated
	 */
	ElementReference<Field> getAttachedField();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Property#getAttachedField <em>Attached Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attached Field</em>' containment reference.
	 * @see #getAttachedField()
	 * @generated
	 */
	void setAttachedField(ElementReference<Field> value);

	/**
	 * Returns the value of the '<em><b>Get Raises</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.ElementReference}<code>&lt;org.eclipse.xsmp.smp.core.types.Exception&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of exceptions raised by the property getter.
	 * Link destination type: Types:Exception
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Get Raises</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getProperty_GetRaises()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:Exception'"
	 *        extendedMetaData="kind='element' name='GetRaises'"
	 * @generated
	 */
	EList<ElementReference<org.eclipse.xsmp.smp.core.types.Exception>> getGetRaises();

	/**
	 * Returns the value of the '<em><b>Set Raises</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.ElementReference}<code>&lt;org.eclipse.xsmp.smp.core.types.Exception&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of exceptions raised by the property setter.
	 * Link destination type: Types:Exception
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Set Raises</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getProperty_SetRaises()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:Exception'"
	 *        extendedMetaData="kind='element' name='SetRaises'"
	 * @generated
	 */
	EList<ElementReference<org.eclipse.xsmp.smp.core.types.Exception>> getSetRaises();

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The default value is <code>"readWrite"</code>.
	 * The literals are from the enumeration {@link org.eclipse.xsmp.smp.core.types.AccessKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Access kind of the property.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.xsmp.smp.core.types.AccessKind
	 * @see #isSetAccess()
	 * @see #unsetAccess()
	 * @see #setAccess(AccessKind)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getProperty_Access()
	 * @model default="readWrite" unsettable="true"
	 *        extendedMetaData="kind='attribute' name='Access'"
	 * @generated
	 */
	AccessKind getAccess();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Property#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.xsmp.smp.core.types.AccessKind
	 * @see #isSetAccess()
	 * @see #unsetAccess()
	 * @see #getAccess()
	 * @generated
	 */
	void setAccess(AccessKind value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Property#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAccess()
	 * @see #getAccess()
	 * @see #setAccess(AccessKind)
	 * @generated
	 */
	void unsetAccess();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Property#getAccess <em>Access</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Access</em>' attribute is set.
	 * @see #unsetAccess()
	 * @see #getAccess()
	 * @see #setAccess(AccessKind)
	 * @generated
	 */
	boolean isSetAccess();

	/**
	 * Returns the value of the '<em><b>Category</b></em>' attribute.
	 * The default value is <code>"Properties"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Category  of the property, which can be used for grouping.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Category</em>' attribute.
	 * @see #isSetCategory()
	 * @see #unsetCategory()
	 * @see #setCategory(String)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getProperty_Category()
	 * @model default="Properties" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Category'"
	 * @generated
	 */
	String getCategory();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Property#getCategory <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category</em>' attribute.
	 * @see #isSetCategory()
	 * @see #unsetCategory()
	 * @see #getCategory()
	 * @generated
	 */
	void setCategory(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.Property#getCategory <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCategory()
	 * @see #getCategory()
	 * @see #setCategory(String)
	 * @generated
	 */
	void unsetCategory();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.Property#getCategory <em>Category</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Category</em>' attribute is set.
	 * @see #unsetCategory()
	 * @see #getCategory()
	 * @see #setCategory(String)
	 * @generated
	 */
	boolean isSetCategory();

} // Property
