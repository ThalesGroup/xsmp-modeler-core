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
package org.eclipse.xsmp.smp.smdl.package_.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xsmp.smp.core.elements.impl.DocumentImpl;

import org.eclipse.xsmp.smp.smdl.package_.DependencyOfPackage;
import org.eclipse.xsmp.smp.smdl.package_.ImplementationOfPackage;
import org.eclipse.xsmp.smp.smdl.package_.PackagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Package</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.package_.impl.PackageImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.package_.impl.PackageImpl#getDependency <em>Dependency</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PackageImpl extends DocumentImpl implements org.eclipse.xsmp.smp.smdl.package_.Package
{
	/**
	 * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementation()
	 * @generated
	 * @ordered
	 */
	protected EList<ImplementationOfPackage> implementation;

	/**
	 * The cached value of the '{@link #getDependency() <em>Dependency</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependency()
	 * @generated
	 * @ordered
	 */
	protected EList<DependencyOfPackage> dependency;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PackageImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return PackagePackage.Literals.PACKAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ImplementationOfPackage> getImplementation()
	{
		if (implementation == null)
		{
			implementation = new EObjectContainmentEList<ImplementationOfPackage>(ImplementationOfPackage.class, this, PackagePackage.PACKAGE__IMPLEMENTATION);
		}
		return implementation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DependencyOfPackage> getDependency()
	{
		if (dependency == null)
		{
			dependency = new EObjectContainmentEList<DependencyOfPackage>(DependencyOfPackage.class, this, PackagePackage.PACKAGE__DEPENDENCY);
		}
		return dependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case PackagePackage.PACKAGE__IMPLEMENTATION:
				return ((InternalEList<?>)getImplementation()).basicRemove(otherEnd, msgs);
			case PackagePackage.PACKAGE__DEPENDENCY:
				return ((InternalEList<?>)getDependency()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case PackagePackage.PACKAGE__IMPLEMENTATION:
				return getImplementation();
			case PackagePackage.PACKAGE__DEPENDENCY:
				return getDependency();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case PackagePackage.PACKAGE__IMPLEMENTATION:
				getImplementation().clear();
				getImplementation().addAll((Collection<? extends ImplementationOfPackage>)newValue);
				return;
			case PackagePackage.PACKAGE__DEPENDENCY:
				getDependency().clear();
				getDependency().addAll((Collection<? extends DependencyOfPackage>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case PackagePackage.PACKAGE__IMPLEMENTATION:
				getImplementation().clear();
				return;
			case PackagePackage.PACKAGE__DEPENDENCY:
				getDependency().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case PackagePackage.PACKAGE__IMPLEMENTATION:
				return implementation != null && !implementation.isEmpty();
			case PackagePackage.PACKAGE__DEPENDENCY:
				return dependency != null && !dependency.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PackageImpl
