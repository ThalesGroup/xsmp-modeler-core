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
 * A representation of the model object '<em><b>Exception</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An Exception represents a non-recoverable error that can occur when calling into an Operation or Property getter/setter (within an Operation this is represented by the RaisedException links and within a Property this is represented by the GetRaises and SetRaises links, respectively).
 * An Exception can contain constants and fields (from Structure) as well as operations, properties and associations (from Class). The fields represent the state variables of the exception which carry additional information when the exception is raised.
 * Furthermore, an Exception may be Abstract (from Class), and it may inherit from a single base exception (implementation inheritance), which is represented by the Base link (from Class).
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getException()
 * @model extendedMetaData="name='Exception' kind='elementOnly'"
 * @generated
 */
public interface Exception extends org.eclipse.xsmp.smp.core.types.Class
{
} // Exception
