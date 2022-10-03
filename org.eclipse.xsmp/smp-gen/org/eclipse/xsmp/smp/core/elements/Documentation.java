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
package org.eclipse.xsmp.smp.core.elements;

import org.eclipse.emf.common.util.EList;

import org.eclipse.xsmp.smp.xlink.SimpleLink;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Documentation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Documentation element holds additional documentation, possibly together with links to external resources. This is done via the Resource element (e.g. holding links to external documentation, 3d animations, technical drawings, CAD models, etc.), based on the XML linking language.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.elements.Documentation#getResource <em>Resource</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.elements.ElementsPackage#getDocumentation()
 * @model extendedMetaData="name='Documentation' kind='elementOnly'"
 * @generated
 */
public interface Documentation extends Metadata
{
	/**
	 * Returns the value of the '<em><b>Resource</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.xlink.SimpleLink}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Resource of the documentation as a link.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Resource</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.core.elements.ElementsPackage#getDocumentation_Resource()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Resource'"
	 * @generated
	 */
	EList<SimpleLink> getResource();

} // Documentation
