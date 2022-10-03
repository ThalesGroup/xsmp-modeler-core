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
package org.eclipse.xsmp.smp.xlink;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Link Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * SimpleLinkRef represents a simple XML link pointing from a local resource to a remote resource, with a required xlink:href attribute that links to another object, and an xlink:title attribute that must hold the name of the link target.
 * Furthermore, other standard xlink attributes may be specified.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.SimpleLinkRef#getReference <em>Reference</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getSimpleLinkRef()
 * @model extendedMetaData="name='SimpleLinkRef' kind='empty'"
 * @generated
 */
public interface SimpleLinkRef extends SimpleLinkBase
{
	/**
	 * Returns the value of the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The href attribute supplies the data that allows an XLink application to find a remote resource (or resource fragment). It may be used on simple-type elements, and must be used on locator-type elements.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Reference</em>' reference.
	 * @see #setReference(EObject)
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getSimpleLinkRef_Reference()
	 * @model required="true"
	 *        extendedMetaData="kind='attribute' name='href' namespace='##targetNamespace'"
	 * @generated
	 */
	EObject getReference();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.xlink.SimpleLinkRef#getReference <em>Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference</em>' reference.
	 * @see #getReference()
	 * @generated
	 */
	void setReference(EObject value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The href attribute supplies the data that allows an XLink application to find a remote resource (or resource fragment). It may be used on simple-type elements, and must be used on locator-type elements.
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getHref();

} // SimpleLinkRef
