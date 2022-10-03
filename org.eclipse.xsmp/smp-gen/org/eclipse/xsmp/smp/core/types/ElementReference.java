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

import org.eclipse.xsmp.smp.core.elements.NamedElement;

import org.eclipse.xsmp.smp.xlink.SimpleLinkRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.ElementReference#getRef <em>Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getElementReference()
 * @model
 * @generated
 */
public interface ElementReference<T extends NamedElement> extends SimpleLinkRef
{
	/**
	 * Returns the value of the '<em><b>Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The href attribute supplies the data that allows an XLink application to find a remote resource (or resource fragment). It may be used on simple-type elements, and must be used on locator-type elements.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ref</em>' reference.
	 * @see #setRef(NamedElement)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getElementReference_Ref()
	 * @model resolveProxies="false" required="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='attribute' name='href' namespace='##targetNamespace'"
	 * @generated
	 */
	T getRef();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.ElementReference#getRef <em>Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ref</em>' reference.
	 * @see #getRef()
	 * @generated
	 */
	void setRef(T value);

} // ElementReference
