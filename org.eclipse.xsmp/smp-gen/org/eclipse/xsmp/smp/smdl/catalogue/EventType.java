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
package org.eclipse.xsmp.smp.smdl.catalogue;

import org.eclipse.xsmp.smp.core.types.ElementReference;
import org.eclipse.xsmp.smp.core.types.SimpleType;
import org.eclipse.xsmp.smp.core.types.Type;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An EventType is used to specify the type of an event. This can be used not only to give a meaningful name to an event type, but also to link it to an existing simple type (via the EventArgs attribute) that is passed as an argument with every invocation of the event.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.EventType#getEventArgs <em>Event Args</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getEventType()
 * @model extendedMetaData="name='EventType' kind='elementOnly'"
 * @generated
 */
public interface EventType extends Type
{
	/**
	 * Returns the value of the '<em><b>Event Args</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Type of the event argument, or none for a void event.
	 * Link destination type: Types:SimpleType
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Event Args</em>' containment reference.
	 * @see #setEventArgs(ElementReference)
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getEventType_EventArgs()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:SimpleType'"
	 *        extendedMetaData="kind='element' name='EventArgs'"
	 * @generated
	 */
	ElementReference<SimpleType> getEventArgs();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.EventType#getEventArgs <em>Event Args</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event Args</em>' containment reference.
	 * @see #getEventArgs()
	 * @generated
	 */
	void setEventArgs(ElementReference<SimpleType> value);

} // EventType
