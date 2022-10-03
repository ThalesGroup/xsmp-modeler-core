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

import org.eclipse.xsmp.smp.core.elements.NamedElement;

import org.eclipse.xsmp.smp.core.types.ElementReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Sink</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An EventSink is used to specify that a Component can receive a specific event using a given name. An EventSink can be connected to any number of EventSource instances.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.EventSink#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getEventSink()
 * @model extendedMetaData="name='EventSink' kind='elementOnly'"
 * @generated
 */
public interface EventSink extends NamedElement
{
	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Type of the event.
	 * Link destination type: Catalogue:EventType
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(ElementReference)
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getEventSink_Type()
	 * @model containment="true" required="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Catalogue:EventType'"
	 *        extendedMetaData="kind='element' name='Type'"
	 * @generated
	 */
	ElementReference<EventType> getType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.EventSink#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ElementReference<EventType> value);

} // EventSink
