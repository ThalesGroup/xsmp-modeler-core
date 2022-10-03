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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The Model metaclass is a component and hence inherits all component mechanisms.
 * These mechanisms allow using various different modelling approaches.
 * For a class-based design, a Model may provide a collection of Field elements to define its internal state. For scheduling and global events, a Model may provide a collection of EntryPoint elements that can be registered with the Scheduler or EventManager services of a Simulation Environment.
 * For an interface-based design, a Model may provide (i.e. implement) an arbitrary number of interfaces, which is represented via the Interface links.
 * For a component-based design, a Model may provide Container elements to contain other models (composition), and Reference elements to reference other components (aggregation). These components can either be models or services.
 * For an event-based design, a Model may support inter-model events via the EventSink and EventSource elements.
 * For a dataflow-based design, the fields of a Model can be tagged as Input or Output fields.
 * In addition, a Model may have Association elements to express associations to other models or fields of other models.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getModel()
 * @model extendedMetaData="name='Model' kind='elementOnly'"
 * @generated
 */
public interface Model extends Component
{
} // Model
