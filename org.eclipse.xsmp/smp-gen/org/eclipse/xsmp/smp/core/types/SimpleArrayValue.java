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
 * A representation of the model object '<em><b>Simple Array Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A SimpleArrayValue represents an array of values that are of (the same) simple type.
 * To ensure type safety, specific sub-metaclasses are introduced, which specify the type of the contained ItemValue elements.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getSimpleArrayValue()
 * @model abstract="true"
 *        extendedMetaData="name='SimpleArrayValue' kind='empty'"
 * @generated
 */
public interface SimpleArrayValue extends Value
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true" many="false"
	 * @generated
	 */
	EList<? extends SimpleValue> getItemValue();

} // SimpleArrayValue
