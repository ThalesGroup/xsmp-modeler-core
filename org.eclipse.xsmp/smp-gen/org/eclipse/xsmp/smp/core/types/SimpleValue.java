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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A SimpleValue represents a value that is of simple type (this includes all SMP primitive types as well as user-defined Integer, Float, String and Enumeration types).
 * To ensure type safety, specific sub-metaclasses are introduced, which specify the type of the Value attribute.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getSimpleValue()
 * @model abstract="true"
 *        extendedMetaData="name='SimpleValue' kind='empty'"
 * @generated
 */
public interface SimpleValue extends Value
{
} // SimpleValue
