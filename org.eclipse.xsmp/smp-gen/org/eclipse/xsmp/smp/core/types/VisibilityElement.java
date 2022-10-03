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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Visibility Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A VisibilityElement is a named element that can be assigned a Visibility attribute to limit its scope of visibility. The visibility may be global (public), local to the parent (private) or local to the parent and derived types thereof (protected).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.VisibilityElement#getVisibility <em>Visibility</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getVisibilityElement()
 * @model abstract="true"
 *        extendedMetaData="name='VisibilityElement' kind='elementOnly'"
 * @generated
 */
public interface VisibilityElement extends NamedElement
{
	/**
	 * Returns the value of the '<em><b>Visibility</b></em>' attribute.
	 * The default value is <code>"private"</code>.
	 * The literals are from the enumeration {@link org.eclipse.xsmp.smp.core.types.VisibilityKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Visibility of the element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Visibility</em>' attribute.
	 * @see org.eclipse.xsmp.smp.core.types.VisibilityKind
	 * @see #isSetVisibility()
	 * @see #unsetVisibility()
	 * @see #setVisibility(VisibilityKind)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getVisibilityElement_Visibility()
	 * @model default="private" unsettable="true"
	 *        extendedMetaData="kind='attribute' name='Visibility'"
	 * @generated
	 */
	VisibilityKind getVisibility();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.VisibilityElement#getVisibility <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visibility</em>' attribute.
	 * @see org.eclipse.xsmp.smp.core.types.VisibilityKind
	 * @see #isSetVisibility()
	 * @see #unsetVisibility()
	 * @see #getVisibility()
	 * @generated
	 */
	void setVisibility(VisibilityKind value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.core.types.VisibilityElement#getVisibility <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetVisibility()
	 * @see #getVisibility()
	 * @see #setVisibility(VisibilityKind)
	 * @generated
	 */
	void unsetVisibility();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.core.types.VisibilityElement#getVisibility <em>Visibility</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Visibility</em>' attribute is set.
	 * @see #unsetVisibility()
	 * @see #getVisibility()
	 * @see #setVisibility(VisibilityKind)
	 * @generated
	 */
	boolean isSetVisibility();

} // VisibilityElement
