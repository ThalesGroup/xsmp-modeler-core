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

import org.eclipse.xsmp.smp.core.elements.NamedElement;

import org.eclipse.xsmp.smp.core.types.ElementReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Container defines the rules of composition (containment of children) for a Component.
 * The type of components that can be contained is specified via the Type link.
 * The Lower and Upper attributes specify the multiplicity, i.e. the number of possibly stored components. Therein the upper bound may be unlimited, which is represented by Upper=-1. 
 * Furthermore, a container may specify a default implementation of the container type via the DefaultComponentl link.
 * 
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
 *                    
 *     <xhtml:b>
 *                         
 *       <xhtml:i> Remark </xhtml:i>
 *                      
 *     </xhtml:b>
 *      : SMDL support tools may use this during instantiation (i.e. during model integration) to select an initial implementation for newly created contained components. 
 *   </xhtml:p>
 *            
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Container#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Container#getDefaultComponent <em>Default Component</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Container#getLower <em>Lower</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Container#getUpper <em>Upper</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getContainer()
 * @model extendedMetaData="name='Container' kind='elementOnly'"
 * @generated
 */
public interface Container extends NamedElement
{
	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Type that all components in this container have to be of, or be derived of.
	 * Link destination type: Catalogue:ReferenceType
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(ElementReference)
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getContainer_Type()
	 * @model containment="true" required="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Catalogue:ReferenceType'"
	 *        extendedMetaData="kind='element' name='Type'"
	 * @generated
	 */
	ElementReference<ReferenceType> getType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Container#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ElementReference<ReferenceType> value);

	/**
	 * Returns the value of the '<em><b>Default Component</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Default component for a child of the container.
	 * Link destination type: Catalogue:Component
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Default Component</em>' containment reference.
	 * @see #setDefaultComponent(ElementReference)
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getContainer_DefaultComponent()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Catalogue:Component'"
	 *        extendedMetaData="kind='element' name='DefaultComponent'"
	 * @generated
	 */
	ElementReference<Component> getDefaultComponent();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Container#getDefaultComponent <em>Default Component</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Component</em>' containment reference.
	 * @see #getDefaultComponent()
	 * @generated
	 */
	void setDefaultComponent(ElementReference<Component> value);

	/**
	 * Returns the value of the '<em><b>Lower</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Lower limit of components in the container.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Lower</em>' attribute.
	 * @see #isSetLower()
	 * @see #unsetLower()
	 * @see #setLower(long)
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getContainer_Lower()
	 * @model default="1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
	 *        extendedMetaData="kind='attribute' name='Lower'"
	 * @generated
	 */
	long getLower();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Container#getLower <em>Lower</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower</em>' attribute.
	 * @see #isSetLower()
	 * @see #unsetLower()
	 * @see #getLower()
	 * @generated
	 */
	void setLower(long value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Container#getLower <em>Lower</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLower()
	 * @see #getLower()
	 * @see #setLower(long)
	 * @generated
	 */
	void unsetLower();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Container#getLower <em>Lower</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Lower</em>' attribute is set.
	 * @see #unsetLower()
	 * @see #getLower()
	 * @see #setLower(long)
	 * @generated
	 */
	boolean isSetLower();

	/**
	 * Returns the value of the '<em><b>Upper</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Upper limit of components in the container.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Upper</em>' attribute.
	 * @see #isSetUpper()
	 * @see #unsetUpper()
	 * @see #setUpper(long)
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getContainer_Upper()
	 * @model default="1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
	 *        extendedMetaData="kind='attribute' name='Upper'"
	 * @generated
	 */
	long getUpper();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Container#getUpper <em>Upper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper</em>' attribute.
	 * @see #isSetUpper()
	 * @see #unsetUpper()
	 * @see #getUpper()
	 * @generated
	 */
	void setUpper(long value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Container#getUpper <em>Upper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUpper()
	 * @see #getUpper()
	 * @see #setUpper(long)
	 * @generated
	 */
	void unsetUpper();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Container#getUpper <em>Upper</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Upper</em>' attribute is set.
	 * @see #unsetUpper()
	 * @see #getUpper()
	 * @see #setUpper(long)
	 * @generated
	 */
	boolean isSetUpper();

} // Container
