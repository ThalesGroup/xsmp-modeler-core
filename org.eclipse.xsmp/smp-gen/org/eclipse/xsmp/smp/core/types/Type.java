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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Type is the abstract base metaclass for all type definition constructs specified by SMDL. A type must have a Uuid attribute representing a Universally Unique Identifier (UUID) as defined above. This is needed such that implementations may reference back to their specification without the need to directly reference an XML element in the catalogue.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.Type#getUuid <em>Uuid</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getType()
 * @model abstract="true"
 *        extendedMetaData="name='Type' kind='elementOnly'"
 * @generated
 */
public interface Type extends VisibilityElement
{
	/**
	 * Returns the value of the '<em><b>Uuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Universally unique identifier of the type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Uuid</em>' attribute.
	 * @see #setUuid(String)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getType_Uuid()
	 * @model dataType="org.eclipse.xsmp.smp.core.elements.UUID" required="true"
	 *        extendedMetaData="kind='attribute' name='Uuid'"
	 * @generated
	 */
	String getUuid();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.Type#getUuid <em>Uuid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uuid</em>' attribute.
	 * @see #getUuid()
	 * @generated
	 */
	void setUuid(String value);

} // Type
