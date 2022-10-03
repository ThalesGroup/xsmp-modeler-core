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
 * A representation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Reference defines the rules of aggregation (links to components) for a Component.
 * The type of components (models or services) that can be referenced is specified by the Interface link. Thereby, a service reference is characterized by an interface that is derived from Smp::IService.
 * The Lower and Upper attributes specify the multiplicity, i.e. the number of possibly held references to components implementing this interface. Therein the upper bound may be unlimited, which is represented by Upper=-1.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Reference#getInterface <em>Interface</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Reference#getLower <em>Lower</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Reference#getUpper <em>Upper</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getReference()
 * @model extendedMetaData="name='Reference' kind='elementOnly'"
 * @generated
 */
public interface Reference extends NamedElement
{
	/**
	 * Returns the value of the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Interface that all components linked to from this reference have to implement.
	 * Link destination type: Catalogue:Interface
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Interface</em>' containment reference.
	 * @see #setInterface(ElementReference)
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getReference_Interface()
	 * @model containment="true" required="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Catalogue:Interface'"
	 *        extendedMetaData="kind='element' name='Interface'"
	 * @generated
	 */
	ElementReference<Interface> getInterface();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Reference#getInterface <em>Interface</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interface</em>' containment reference.
	 * @see #getInterface()
	 * @generated
	 */
	void setInterface(ElementReference<Interface> value);

	/**
	 * Returns the value of the '<em><b>Lower</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Lower limit of component links in the reference.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Lower</em>' attribute.
	 * @see #isSetLower()
	 * @see #unsetLower()
	 * @see #setLower(long)
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getReference_Lower()
	 * @model default="1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
	 *        extendedMetaData="kind='attribute' name='Lower'"
	 * @generated
	 */
	long getLower();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Reference#getLower <em>Lower</em>}' attribute.
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
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Reference#getLower <em>Lower</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLower()
	 * @see #getLower()
	 * @see #setLower(long)
	 * @generated
	 */
	void unsetLower();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Reference#getLower <em>Lower</em>}' attribute is set.
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
	 * Upper limit of component links in the reference.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Upper</em>' attribute.
	 * @see #isSetUpper()
	 * @see #unsetUpper()
	 * @see #setUpper(long)
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getReference_Upper()
	 * @model default="1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
	 *        extendedMetaData="kind='attribute' name='Upper'"
	 * @generated
	 */
	long getUpper();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Reference#getUpper <em>Upper</em>}' attribute.
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
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Reference#getUpper <em>Upper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUpper()
	 * @see #getUpper()
	 * @see #setUpper(long)
	 * @generated
	 */
	void unsetUpper();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Reference#getUpper <em>Upper</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Upper</em>' attribute is set.
	 * @see #unsetUpper()
	 * @see #getUpper()
	 * @see #setUpper(long)
	 * @generated
	 */
	boolean isSetUpper();

} // Reference
