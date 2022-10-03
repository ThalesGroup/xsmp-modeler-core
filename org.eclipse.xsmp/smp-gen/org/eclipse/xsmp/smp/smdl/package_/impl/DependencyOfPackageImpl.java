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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.xsmp.smp.smdl.package_.DependencyOfPackage;
import org.eclipse.xsmp.smp.smdl.package_.PackagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dependency Of Package</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.package_.impl.DependencyOfPackageImpl#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.package_.impl.DependencyOfPackageImpl#getPackage <em>Package</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DependencyOfPackageImpl extends MinimalEObjectImpl.Container implements DependencyOfPackage
{
	/**
	 * The default value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected String packageName = PACKAGE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected org.eclipse.xsmp.smp.smdl.package_.Package package_;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DependencyOfPackageImpl()
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
		return PackagePackage.Literals.DEPENDENCY_OF_PACKAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPackageName()
	{
		return packageName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPackageName(String newPackageName)
	{
		String oldPackageName = packageName;
		packageName = newPackageName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PackagePackage.DEPENDENCY_OF_PACKAGE__PACKAGE_NAME, oldPackageName, packageName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.xsmp.smp.smdl.package_.Package getPackage()
	{
		if (package_ != null && package_.eIsProxy())
		{
			InternalEObject oldPackage = (InternalEObject)package_;
			package_ = (org.eclipse.xsmp.smp.smdl.package_.Package)eResolveProxy(oldPackage);
			if (package_ != oldPackage)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PackagePackage.DEPENDENCY_OF_PACKAGE__PACKAGE, oldPackage, package_));
			}
		}
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.xsmp.smp.smdl.package_.Package basicGetPackage()
	{
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPackage(org.eclipse.xsmp.smp.smdl.package_.Package newPackage)
	{
		org.eclipse.xsmp.smp.smdl.package_.Package oldPackage = package_;
		package_ = newPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PackagePackage.DEPENDENCY_OF_PACKAGE__PACKAGE, oldPackage, package_));
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
			case PackagePackage.DEPENDENCY_OF_PACKAGE__PACKAGE_NAME:
				return getPackageName();
			case PackagePackage.DEPENDENCY_OF_PACKAGE__PACKAGE:
				if (resolve) return getPackage();
				return basicGetPackage();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case PackagePackage.DEPENDENCY_OF_PACKAGE__PACKAGE_NAME:
				setPackageName((String)newValue);
				return;
			case PackagePackage.DEPENDENCY_OF_PACKAGE__PACKAGE:
				setPackage((org.eclipse.xsmp.smp.smdl.package_.Package)newValue);
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
			case PackagePackage.DEPENDENCY_OF_PACKAGE__PACKAGE_NAME:
				setPackageName(PACKAGE_NAME_EDEFAULT);
				return;
			case PackagePackage.DEPENDENCY_OF_PACKAGE__PACKAGE:
				setPackage((org.eclipse.xsmp.smp.smdl.package_.Package)null);
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
			case PackagePackage.DEPENDENCY_OF_PACKAGE__PACKAGE_NAME:
				return PACKAGE_NAME_EDEFAULT == null ? packageName != null : !PACKAGE_NAME_EDEFAULT.equals(packageName);
			case PackagePackage.DEPENDENCY_OF_PACKAGE__PACKAGE:
				return package_ != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (packageName: ");
		result.append(packageName);
		result.append(')');
		return result.toString();
	}

} //DependencyOfPackageImpl
