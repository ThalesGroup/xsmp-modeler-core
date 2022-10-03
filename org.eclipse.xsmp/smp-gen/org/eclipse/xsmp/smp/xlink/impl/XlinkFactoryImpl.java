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
package org.eclipse.xsmp.smp.xlink.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.xsmp.smp.xlink.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class XlinkFactoryImpl extends EFactoryImpl implements XlinkFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XlinkFactory init()
	{
		try
		{
			XlinkFactory theXlinkFactory = (XlinkFactory)EPackage.Registry.INSTANCE.getEFactory(XlinkPackage.eNS_URI);
			if (theXlinkFactory != null)
			{
				return theXlinkFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new XlinkFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XlinkFactoryImpl()
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
			case XlinkPackage.SIMPLE_LINK: return createSimpleLink();
			case XlinkPackage.SIMPLE_LINK_REF: return createSimpleLinkRef();
			case XlinkPackage.XLINK_DOCUMENT: return createXlinkDocument();
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
	public Object createFromString(EDataType eDataType, String initialValue)
	{
		switch (eDataType.getClassifierID())
		{
			case XlinkPackage.ACTUATE_KIND:
				return createActuateKindFromString(eDataType, initialValue);
			case XlinkPackage.SHOW_KIND:
				return createShowKindFromString(eDataType, initialValue);
			case XlinkPackage.TYPE_KIND:
				return createTypeKindFromString(eDataType, initialValue);
			case XlinkPackage.ACTUATE_KIND_OBJECT:
				return createActuateKindObjectFromString(eDataType, initialValue);
			case XlinkPackage.SHOW_KIND_OBJECT:
				return createShowKindObjectFromString(eDataType, initialValue);
			case XlinkPackage.TYPE_KIND_OBJECT:
				return createTypeKindObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue)
	{
		switch (eDataType.getClassifierID())
		{
			case XlinkPackage.ACTUATE_KIND:
				return convertActuateKindToString(eDataType, instanceValue);
			case XlinkPackage.SHOW_KIND:
				return convertShowKindToString(eDataType, instanceValue);
			case XlinkPackage.TYPE_KIND:
				return convertTypeKindToString(eDataType, instanceValue);
			case XlinkPackage.ACTUATE_KIND_OBJECT:
				return convertActuateKindObjectToString(eDataType, instanceValue);
			case XlinkPackage.SHOW_KIND_OBJECT:
				return convertShowKindObjectToString(eDataType, instanceValue);
			case XlinkPackage.TYPE_KIND_OBJECT:
				return convertTypeKindObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleLink createSimpleLink()
	{
		SimpleLinkImpl simpleLink = new SimpleLinkImpl();
		return simpleLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleLinkRef createSimpleLinkRef()
	{
		SimpleLinkRefImplCustom simpleLinkRef = new SimpleLinkRefImplCustom();
		return simpleLinkRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XlinkDocument createXlinkDocument()
	{
		XlinkDocumentImpl xlinkDocument = new XlinkDocumentImpl();
		return xlinkDocument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActuateKind createActuateKindFromString(EDataType eDataType, String initialValue)
	{
		ActuateKind result = ActuateKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertActuateKindToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShowKind createShowKindFromString(EDataType eDataType, String initialValue)
	{
		ShowKind result = ShowKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertShowKindToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeKind createTypeKindFromString(EDataType eDataType, String initialValue)
	{
		TypeKind result = TypeKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTypeKindToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActuateKind createActuateKindObjectFromString(EDataType eDataType, String initialValue)
	{
		return createActuateKindFromString(XlinkPackage.Literals.ACTUATE_KIND, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertActuateKindObjectToString(EDataType eDataType, Object instanceValue)
	{
		return convertActuateKindToString(XlinkPackage.Literals.ACTUATE_KIND, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShowKind createShowKindObjectFromString(EDataType eDataType, String initialValue)
	{
		return createShowKindFromString(XlinkPackage.Literals.SHOW_KIND, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertShowKindObjectToString(EDataType eDataType, Object instanceValue)
	{
		return convertShowKindToString(XlinkPackage.Literals.SHOW_KIND, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeKind createTypeKindObjectFromString(EDataType eDataType, String initialValue)
	{
		return createTypeKindFromString(XlinkPackage.Literals.TYPE_KIND, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTypeKindObjectToString(EDataType eDataType, Object instanceValue)
	{
		return convertTypeKindToString(XlinkPackage.Literals.TYPE_KIND, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XlinkPackage getXlinkPackage()
	{
		return (XlinkPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static XlinkPackage getPackage()
	{
		return XlinkPackage.eINSTANCE;
	}

} //XlinkFactoryImpl
