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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dependency Of Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.package_.DependencyOfPackage#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.package_.DependencyOfPackage#getPackage <em>Package</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.package_.PackagePackage#getDependencyOfPackage()
 * @model extendedMetaData="name='Dependency_._type' kind='empty'"
 * @generated
 */
public interface DependencyOfPackage extends EObject
{
	/**
	 * Returns the value of the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Name</em>' attribute.
	 * @see #setPackageName(String)
	 * @see org.eclipse.xsmp.smp.smdl.package_.PackagePackage#getDependencyOfPackage_PackageName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='title' namespace='http://www.w3.org/1999/xlink'"
	 * @generated
	 */
	String getPackageName();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.package_.DependencyOfPackage#getPackageName <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Name</em>' attribute.
	 * @see #getPackageName()
	 * @generated
	 */
	void setPackageName(String value);

	/**
	 * Returns the value of the '<em><b>Package</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' reference.
	 * @see #setPackage(org.eclipse.xsmp.smp.smdl.package_.Package)
	 * @see org.eclipse.xsmp.smp.smdl.package_.PackagePackage#getDependencyOfPackage_Package()
	 * @model required="true"
	 *        extendedMetaData="kind='attribute' name='href' namespace='http://www.w3.org/1999/xlink'"
	 * @generated
	 */
	org.eclipse.xsmp.smp.smdl.package_.Package getPackage();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.smdl.package_.DependencyOfPackage#getPackage <em>Package</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' reference.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(org.eclipse.xsmp.smp.smdl.package_.Package value);

} // DependencyOfPackage
