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
 * A representation of the model object '<em><b>Primitive Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A number of pre-defined types are needed in order to bootstrap the type system. These pre-defined value types are represented by instances of the metaclass PrimitiveType.
 * This mechanism is only used in order to bootstrap the type system and may not be used to define new types for modelling. This is an important restriction, as all values of primitive types may be held in a SimpleValue. The metaclasses derived from SimpleValue, however, are pre-defined and cannot be extended.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getPrimitiveType()
 * @model extendedMetaData="name='PrimitiveType' kind='elementOnly'"
 * @generated
 */
public interface PrimitiveType extends SimpleType
{
} // PrimitiveType
