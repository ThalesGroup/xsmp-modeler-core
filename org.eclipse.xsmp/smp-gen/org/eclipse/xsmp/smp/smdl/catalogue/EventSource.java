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
 * A representation of the model object '<em><b>Event Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An EventSource is used to specify that a Component publishes a specific event under a given name. The Multicast attribute can be used to specify whether any number of sinks can connect to the source (the default), or only a single sink can connect (Multicast=false).
 * 
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
 *                    
 *     <xhtml:b>
 *                         
 *       <xhtml:i> Remark </xhtml:i>
 *                      
 *     </xhtml:b>
 *      : A tool for model integration can use the Multicast attribute to configure the user interface accordingly to ease the specification of event links. 
 *   </xhtml:p>
 *            
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.EventSource#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.EventSource#isMulticast <em>Multicast</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getEventSource()
 * @model extendedMetaData="name='EventSource' kind='elementOnly'"
 * @generated
 */
public interface EventSource extends NamedElement
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
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getEventSource_Type()
	 * @model containment="true" required="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Catalogue:EventType'"
	 *        extendedMetaData="kind='element' name='Type'"
	 * @generated
	 */
	ElementReference<EventType> getType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.EventSource#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ElementReference<EventType> value);

	/**
	 * Returns the value of the '<em><b>Multicast</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * True if the event source supports multi-cast, false otherwise.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Multicast</em>' attribute.
	 * @see #isSetMulticast()
	 * @see #unsetMulticast()
	 * @see #setMulticast(boolean)
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getEventSource_Multicast()
	 * @model default="true" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='Multicast'"
	 * @generated
	 */
	boolean isMulticast();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.EventSource#isMulticast <em>Multicast</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multicast</em>' attribute.
	 * @see #isSetMulticast()
	 * @see #unsetMulticast()
	 * @see #isMulticast()
	 * @generated
	 */
	void setMulticast(boolean value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.EventSource#isMulticast <em>Multicast</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMulticast()
	 * @see #isMulticast()
	 * @see #setMulticast(boolean)
	 * @generated
	 */
	void unsetMulticast();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.EventSource#isMulticast <em>Multicast</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Multicast</em>' attribute is set.
	 * @see #unsetMulticast()
	 * @see #isMulticast()
	 * @see #setMulticast(boolean)
	 * @generated
	 */
	boolean isSetMulticast();

} // EventSource
