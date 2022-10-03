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
package org.eclipse.xsmp.smp.smdl.package_;

import org.eclipse.emf.common.util.EList;

import org.eclipse.xsmp.smp.core.elements.Document;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Package is a Document that references an arbitrary number of types for Implementation. Each of these types (defined in a catalogue) shall be implemented by the package to make it available for use in a simulation.
 * In addition, a package may reference other packages as a Dependency. This indicates that a type referenced from an implementation in the package requires a type implemented in the referenced package.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.package_.Package#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.package_.Package#getDependency <em>Dependency</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.package_.PackagePackage#getPackage()
 * @model extendedMetaData="name='Package' kind='elementOnly'"
 * @generated
 */
public interface Package extends Document
{
	/**
	 * Returns the value of the '<em><b>Implementation</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.smdl.package_.ImplementationOfPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of types implemented by the package.
	 * Link destination type: Types:Type
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Implementation</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.package_.PackagePackage#getPackage_Implementation()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Types:Type'"
	 *        extendedMetaData="kind='element' name='Implementation'"
	 * @generated
	 */
	EList<ImplementationOfPackage> getImplementation();

	/**
	 * Returns the value of the '<em><b>Dependency</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.smdl.package_.DependencyOfPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of dependencies to other packages.
	 * Link destination type: Package:Package
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Dependency</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.package_.PackagePackage#getPackage_Dependency()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Package:Package'"
	 *        extendedMetaData="kind='element' name='Dependency'"
	 * @generated
	 */
	EList<DependencyOfPackage> getDependency();

} // Package
