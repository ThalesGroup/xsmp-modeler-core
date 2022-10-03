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

import org.eclipse.emf.common.util.EList;

import org.eclipse.xsmp.smp.core.types.Association;
import org.eclipse.xsmp.smp.core.types.ElementReference;
import org.eclipse.xsmp.smp.core.types.Field;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
 *      A Component is a reference type and hence inherits the ability to hold constants, properties and operations. As a Component semantically forms a deployable unit, it may use the available object and component mechanisms of the standard. Apart from the ability to specify a Base component (single implementation inheritance), a component may have various optional elements: 
 *     <xhtml:ul>
 *                         
 *       <xhtml:li> Interface links specify interfaces that the component provides (in SMP this implies that the component implements these interfaces); </xhtml:li>
 *                         
 *       <xhtml:li> EntryPoint elements allow the component to be scheduled (via the Scheduler service) and to listen to global events (via the EventManager service); </xhtml:li>
 *                         
 *       <xhtml:li> EventSink elements specify which events the component can receive (these may be registered with other components' event sources); </xhtml:li>
 *                         
 *       <xhtml:li> EventSource elements specify events that the component can emit (other components may register their associated event sink(s) with these); </xhtml:li>
 *                         
 *       <xhtml:li> Field elements define a component's internal state; and </xhtml:li>
 *                         
 *       <xhtml:li> Association elements express associations to other components or fields of other components. </xhtml:li>
 *                         
 *       <xhtml:li> Container elements define containment of other components (composition). </xhtml:li>
 *                         
 *       <xhtml:li> Reference elements define reference to other components (aggregation). </xhtml:li>
 *                      
 *     </xhtml:ul>
 *                 
 *   </xhtml:p>
 *            
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Component#getBase <em>Base</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Component#getInterface <em>Interface</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Component#getEntryPoint <em>Entry Point</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Component#getEventSink <em>Event Sink</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Component#getEventSource <em>Event Source</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Component#getField <em>Field</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Component#getAssociation <em>Association</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Component#getContainer <em>Container</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Component#getReference <em>Reference</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getComponent()
 * @model abstract="true"
 *        extendedMetaData="name='Component' kind='elementOnly'"
 * @generated
 */
public interface Component extends ReferenceType
{
	/**
	 * Returns the value of the '<em><b>Base</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Base component, which is optional.
	 * Link destination type: Catalogue:Component
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Base</em>' containment reference.
	 * @see #setBase(ElementReference)
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getComponent_Base()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Catalogue:Component'"
	 *        extendedMetaData="kind='element' name='Base'"
	 * @generated
	 */
	ElementReference<Component> getBase();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.catalogue.Component#getBase <em>Base</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base</em>' containment reference.
	 * @see #getBase()
	 * @generated
	 */
	void setBase(ElementReference<Component> value);

	/**
	 * Returns the value of the '<em><b>Interface</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.ElementReference}<code>&lt;org.eclipse.xsmp.smp.smdl.catalogue.Interface&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of provided interfaces.
	 * Link destination type: Catalogue:Interface
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Interface</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getComponent_Interface()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Catalogue:Interface'"
	 *        extendedMetaData="kind='element' name='Interface'"
	 * @generated
	 */
	EList<ElementReference<Interface>> getInterface();

	/**
	 * Returns the value of the '<em><b>Entry Point</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.smdl.catalogue.EntryPoint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of entry points.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Entry Point</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getComponent_EntryPoint()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='EntryPoint'"
	 * @generated
	 */
	EList<EntryPoint> getEntryPoint();

	/**
	 * Returns the value of the '<em><b>Event Sink</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.smdl.catalogue.EventSink}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of event sinks.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Event Sink</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getComponent_EventSink()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='EventSink'"
	 * @generated
	 */
	EList<EventSink> getEventSink();

	/**
	 * Returns the value of the '<em><b>Event Source</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.smdl.catalogue.EventSource}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of event sources.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Event Source</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getComponent_EventSource()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='EventSource'"
	 * @generated
	 */
	EList<EventSource> getEventSource();

	/**
	 * Returns the value of the '<em><b>Field</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Field}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of fields.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Field</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getComponent_Field()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Field'"
	 * @generated
	 */
	EList<Field> getField();

	/**
	 * Returns the value of the '<em><b>Association</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Association}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of associations.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Association</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getComponent_Association()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Association'"
	 * @generated
	 */
	EList<Association> getAssociation();

	/**
	 * Returns the value of the '<em><b>Container</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.smdl.catalogue.Container}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of containers.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Container</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getComponent_Container()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Container'"
	 * @generated
	 */
	EList<Container> getContainer();

	/**
	 * Returns the value of the '<em><b>Reference</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.smdl.catalogue.Reference}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of references.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Reference</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getComponent_Reference()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Reference'"
	 * @generated
	 */
	EList<Reference> getReference();

} // Component
