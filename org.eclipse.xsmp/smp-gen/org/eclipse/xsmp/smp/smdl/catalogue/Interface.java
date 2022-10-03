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

import org.eclipse.xsmp.smp.core.types.ElementReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An Interface is a reference type that serves as a contract in a loosely coupled architecture. It has the ability to contain constants, properties and operations (from ReferenceType). An Interface may inherit from other interfaces (interface inheritance), which is represented via the Base links.
 * 
 *               
 *   <xhtml:p xmlns:xhtml="http://www.w3.org/1999/xhtml">
 *                    
 *     <xhtml:i>
 *                         
 *       <xhtml:b> Remark </xhtml:b>
 *                      
 *     </xhtml:i>
 *      : It is strongly recommended to only use value types, references and other interfaces in the properties and operations of an interface (i.e. not to use models). Otherwise, a dependency between a model implementing the interface, and other models referenced by this interface is introduced, which is against the idea of interface-based or component-based design. 
 *   </xhtml:p>
 *            
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.smdl.catalogue.Interface#getBase <em>Base</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getInterface()
 * @model extendedMetaData="name='Interface' kind='elementOnly'"
 * @generated
 */
public interface Interface extends ReferenceType
{
	/**
	 * Returns the value of the '<em><b>Base</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.xsmp.smp.core.types.ElementReference}<code>&lt;org.eclipse.xsmp.smp.smdl.catalogue.Interface&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Collection of base interfaces.
	 * Link destination type: Catalogue:Interface
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Base</em>' containment reference list.
	 * @see org.eclipse.xsmp.smp.smdl.catalogue.CataloguePackage#getInterface_Base()
	 * @model containment="true"
	 *        annotation="http://www.w3.org/1999/xlink appinfo='Catalogue:Interface'"
	 *        extendedMetaData="kind='element' name='Base'"
	 * @generated
	 */
	EList<ElementReference<Interface>> getBase();

} // Interface
