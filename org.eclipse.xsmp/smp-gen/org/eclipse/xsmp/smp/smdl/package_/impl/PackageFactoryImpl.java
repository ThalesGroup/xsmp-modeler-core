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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.xsmp.smp.smdl.package_.DependencyOfPackage;
import org.eclipse.xsmp.smp.smdl.package_.ImplementationOfPackage;
import org.eclipse.xsmp.smp.smdl.package_.PackageDocumentRoot;
import org.eclipse.xsmp.smp.smdl.package_.PackageFactory;
import org.eclipse.xsmp.smp.smdl.package_.PackagePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PackageFactoryImpl extends EFactoryImpl implements PackageFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PackageFactory init()
	{
		try
		{
			PackageFactory thePackageFactory = (PackageFactory)EPackage.Registry.INSTANCE.getEFactory(PackagePackage.eNS_URI);
			if (thePackageFactory != null)
			{
				return thePackageFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PackageFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PackageFactoryImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass)
	{
		switch (eClass.getClassifierID())
		{
			case PackagePackage.DEPENDENCY_OF_PACKAGE: return createDependencyOfPackage();
			case PackagePackage.IMPLEMENTATION_OF_PACKAGE: return createImplementationOfPackage();
			case PackagePackage.PACKAGE: return createPackage();
			case PackagePackage.PACKAGE_DOCUMENT_ROOT: return createPackageDocumentRoot();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DependencyOfPackage createDependencyOfPackage()
	{
		DependencyOfPackageImpl dependencyOfPackage = new DependencyOfPackageImpl();
		return dependencyOfPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ImplementationOfPackage createImplementationOfPackage()
	{
		ImplementationOfPackageImpl implementationOfPackage = new ImplementationOfPackageImpl();
		return implementationOfPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.xsmp.smp.smdl.package_.Package createPackage()
	{
		PackageImpl package_ = new PackageImpl();
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PackageDocumentRoot createPackageDocumentRoot()
	{
		PackageDocumentRootImpl packageDocumentRoot = new PackageDocumentRootImpl();
		return packageDocumentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PackagePackage getPackagePackage()
	{
		return (PackagePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PackagePackage getPackage()
	{
		return PackagePackage.eINSTANCE;
	}

} //PackageFactoryImpl
