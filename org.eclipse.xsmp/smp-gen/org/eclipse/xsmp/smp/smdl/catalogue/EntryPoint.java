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

import org.eclipse.xsmp.smp.core.elements.NamedElement;

import org.eclipse.xsmp.smp.core.types.ElementReference;
import org.eclipse.xsmp.smp.core.types.Field;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entry Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An EntryPoint is a named element of a Component (Model or Service). It corresponds to a void operation taking no parameters that can be called from an external client (e.g. the Scheduler or Event Manager services). An Entry Point can reference both Input fields (which must have their Input attribute set to true) and Output fields (which must have their Output attribute set to true). These links can be used to ensure that all component output fields are updated before the entry point is called, or that all input fields can be used after the entry point has been called.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.EntryPoint#getInput <em>Input</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.EntryPoint#getOutput <em>Output</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getEntryPoint()
 * @model extendedMetaData="name='EntryPoint' kind='elementOnly'"
 * @generated
 */
public interface EntryPoint extends NamedElement
{
	/**
	 * Returns the value of the '<em><b>Input</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.ElementReference}<code>&lt;org.eclipse.xsmp.smp.core.types.Field&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of input fields of components that are updated by calls to this entry point, and hence can be used after calling the entry point.
	 * Link destination type: Types:Field
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Input</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getEntryPoint_Input()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:Field'"
	 *        extendedMetaData="kind='element' name='Input'"
	 * @generated
	 */
	EList<ElementReference<Field>> getInput();

	/**
	 * Returns the value of the '<em><b>Output</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.ElementReference}<code>&lt;org.eclipse.xsmp.smp.core.types.Field&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of output fields of components that are read during calls to this entry point, and hence have to be updated prior to calling the entry point.
	 * Link destination type: Types:Field
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Output</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getEntryPoint_Output()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:Field'"
	 *        extendedMetaData="kind='element' name='Output'"
	 * @generated
	 */
	EList<ElementReference<Field>> getOutput();

} // EntryPoint
