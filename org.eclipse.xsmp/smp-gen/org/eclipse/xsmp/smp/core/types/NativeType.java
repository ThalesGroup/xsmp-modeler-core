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
 * A representation of the model object '<em><b>Native Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A NativeType specifies a type with any number of platform mappings. It is used to anchor existing or user-defined types into different target platforms. This mechanism is used within the specification to define the SMDL primitive types with respect to the Metamodel, but it can also be used to define native types within an arbitrary SMDL catalogue for use by models. In the latter case, native types are typically used to bind a model to some external library or existing Application Programming Interface (API).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.NativeType#getPlatform <em>Platform</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getNativeType()
 * @model extendedMetaData="name='NativeType' kind='elementOnly'"
 * @generated
 */
public interface NativeType extends LanguageType
{
	/**
	 * Returns the value of the '<em><b>Platform</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.PlatformMapping}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of platform mappings for the native type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Platform</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getNativeType_Platform()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Platform'"
	 * @generated
	 */
	EList<PlatformMapping> getPlatform();

} // NativeType
