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
package org.eclipse.xsmp.smp.core.types;

import java.lang.String;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Platform Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml"> A PlatformMapping defines the mapping of a native type into a target platform. The Name attribute specifies the platform name (see below), the Type attribute specifies the type name on the platform, the Namespace attribute specifies the type's namespace (if any) on the target platform, and the Location attribute specifies where the type is located. Note that the interpretation of these values is platform specific. </xhtml:p>
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml"> The platform name shall be specified using the pattern &lt;language&gt;&lt;environment&gt;, where the environment is optional and may further detail the platform. Some examples are: </xhtml:p>
 *               
 *   <xhtml:ul xmlns:xhtml="http://www.w3.org/1999/xhtml">
 *                    
 *     <xhtml:li> cpp: Standard ANSI/ISO C++ (for all environments) </xhtml:li>
 *                    
 *     <xhtml:li> cpp__linux__: C++ under Linux Operating System environment </xhtml:li>
 *                    
 *     <xhtml:li> idl: CORBA IDL </xhtml:li>
 *                    
 *     <xhtml:li> xsd: XML Schema </xhtml:li>
 *                    
 *     <xhtml:li> java: Java language </xhtml:li>
 *                 
 *   </xhtml:ul>
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
 *      Basically, any platform mapping may be specified in SMDL as long as the tools - typically code generators working on SMDL Catalogue(s) - have an understanding of their meaning. 
 *     <xhtml:br/>
 *                    
 *     <xhtml:br/>
 *      The interpretation of the 
 *   </xhtml:p>
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml"> &lt;environment&gt; </xhtml:p>
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml"> string may vary between different platforms, and is detailed in each platform mapping document. </xhtml:p>
 *            
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.PlatformMapping#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.PlatformMapping#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.PlatformMapping#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.core.types.PlatformMapping#getLocation <em>Location</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getPlatformMapping()
 * @model extendedMetaData="name='PlatformMapping' kind='empty'"
 * @generated
 */
public interface PlatformMapping extends EObject
{
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Name of the platform using the following pattern:
	 * <language>_<environment>, where the environment may be split into <os>_<compiler>. Examples are:
	 * cpp_windows_vc71 - C++ using Microsoft VC++ 7.1 under Windows
	 * cpp_linux_gcc33  - C++ using GNU gcc 3.3 under Linux
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getPlatformMapping_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.PlatformMapping#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Name of the type on the platform.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getPlatformMapping_Type()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Type'"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.PlatformMapping#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Namespace on the platform.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Namespace</em>' attribute.
	 * @see #setNamespace(String)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getPlatformMapping_Namespace()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Namespace'"
	 * @generated
	 */
	String getNamespace();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.PlatformMapping#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(String value);

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Location on the platform.
	 * - In C++, this may be a required include file.
	 * - In Java, this may be a jar file to reference.
	 * - In C#, this may be an assembly to reference.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(String)
	 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getPlatformMapping_Location()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Location'"
	 * @generated
	 */
	String getLocation();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.core.types.PlatformMapping#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(String value);

} // PlatformMapping
