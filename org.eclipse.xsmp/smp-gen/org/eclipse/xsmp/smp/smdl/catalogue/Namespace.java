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

import org.eclipse.xsmp.smp.core.types.Type;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Namespace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Namespace is a primary ordering mechanism. A namespace may contain other namespaces (nested namespaces), and does typically contain types. In SMDL, namespaces are contained within a Catalogue (either directly, or within another namespace in a catalogue).
 * All sub-elements of a namespace (namespaces and types) must have unique names.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Namespace#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Namespace#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getNamespace()
 * @model extendedMetaData="name='Namespace' kind='elementOnly'"
 * @generated
 */
public interface Namespace extends NamedElement
{
	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.smdl.catalogue.Namespace}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of nested namespaces of the namespace.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Namespace</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getNamespace_Namespace()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Namespace'"
	 * @generated
	 */
	EList<Namespace> getNamespace();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.Type}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of types defined within the namespace.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getNamespace_Type()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Type'"
	 * @generated
	 */
	EList<Type> getType();

} // Namespace
