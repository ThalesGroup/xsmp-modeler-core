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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * SimpleLink represents a simple XML link pointing from a local resource to a remote resource, with a required xlink:href attribute that links to an arbitrary URI, and an optional xlink:title attribute that may hold the name of the link target.
 * Furthermore, other standard xlink attributes may be specified.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.SimpleLink#getHref <em>Href</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getSimpleLink()
 * @model extendedMetaData="name='SimpleLink' kind='empty'"
 * @generated
 */
public interface SimpleLink extends SimpleLinkBase
{
	/**
	 * Returns the value of the '<em><b>Href</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The href attribute supplies the data that allows an XLink application to find a remote resource (or resource fragment). It may be used on simple-type elements, and must be used on locator-type elements.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Href</em>' attribute.
	 * @see #setHref(String)
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getSimpleLink_Href()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
	 *        extendedMetaData="kind='attribute' name='href' namespace='##targetNamespace'"
	 * @generated
	 */
	String getHref();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.xlink.SimpleLink#getHref <em>Href</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Href</em>' attribute.
	 * @see #getHref()
	 * @generated
	 */
	void setHref(String value);

} // SimpleLink
