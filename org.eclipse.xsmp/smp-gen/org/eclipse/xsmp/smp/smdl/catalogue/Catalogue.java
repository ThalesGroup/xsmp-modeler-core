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

import org.eclipse.xsmp.smp.core.elements.Document;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Catalogue</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Catalogue is a document that defines types. It contains namespaces as a primary ordering mechanism. The names of these namespaces need to be unique within the catalogue.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Catalogue#getNamespace <em>Namespace</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getCatalogue()
 * @model extendedMetaData="name='Catalogue' kind='elementOnly'"
 * @generated
 */
public interface Catalogue extends Document
{
	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.smdl.catalogue.Namespace}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of namespaces of the catalogue.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Namespace</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getCatalogue_Namespace()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Namespace'"
	 * @generated
	 */
	EList<Namespace> getNamespace();

} // Catalogue
