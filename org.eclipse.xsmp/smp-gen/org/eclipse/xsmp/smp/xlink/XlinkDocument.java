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
package org.eclipse.xsmp.smp.xlink;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getMixed <em>Mixed</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getActuate <em>Actuate</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getArcrole <em>Arcrole</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getFrom <em>From</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getHref <em>Href</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getRole <em>Role</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getShow <em>Show</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getTo <em>To</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface XlinkDocument extends EObject
{
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Actuate</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.xsmp.smp.xlink.ActuateKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The actuate attribute is used to communicate the desired timing of traversal from the starting resource to the ending resource.
	 * 
	 * Conforming XLink applications should apply the specified treatment for the actuate values (see ActuateKind).
	 * 
	 * Background:
	 * The behavior attributes are show and actuate. They may be used on the simple- and arc-type elements. When used on a simple-type element, they signal behavior intentions for traversal to that link's single remote ending resource. When they are used on an arc-type element, they signal behavior intentions for traversal to whatever ending resources (local or remote) are specified by that arc.
	 * 
	 * The show and actuate attributes are not required. When they are used, conforming XLink applications should give them the treatment specified in this section. There is no hard requirement ("must") for this treatment because what makes sense for an interactive application, such as a browser, is unlikely to make sense for a noninteractive application, such as a robot. However, all applications should take into account the full implications of ignoring the specified behavior before choosing a different course.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Actuate</em>' attribute.
	 * @see org.eclipse.xsmp.smp.xlink.ActuateKind
	 * @see #isSetActuate()
	 * @see #unsetActuate()
	 * @see #setActuate(ActuateKind)
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_Actuate()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='actuate' namespace='##targetNamespace'"
	 * @generated
	 */
	ActuateKind getActuate();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getActuate <em>Actuate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actuate</em>' attribute.
	 * @see org.eclipse.xsmp.smp.xlink.ActuateKind
	 * @see #isSetActuate()
	 * @see #unsetActuate()
	 * @see #getActuate()
	 * @generated
	 */
	void setActuate(ActuateKind value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getActuate <em>Actuate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetActuate()
	 * @see #getActuate()
	 * @see #setActuate(ActuateKind)
	 * @generated
	 */
	void unsetActuate();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getActuate <em>Actuate</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Actuate</em>' attribute is set.
	 * @see #unsetActuate()
	 * @see #getActuate()
	 * @see #setActuate(ActuateKind)
	 * @generated
	 */
	boolean isSetActuate();

	/**
	 * Returns the value of the '<em><b>Arcrole</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The attributes that describe the meaning of resources within the context of a link are role, arcrole, and title.
	 * 
	 * The arcrole attribute may be used on arc- and simple-type elements.
	 * 
	 * The URI reference identifies some resource that describes the intended property. When no value is supplied, no particular role value is to be inferred.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Arcrole</em>' attribute.
	 * @see #setArcrole(String)
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_Arcrole()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='arcrole' namespace='##targetNamespace'"
	 * @generated
	 */
	String getArcrole();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getArcrole <em>Arcrole</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arcrole</em>' attribute.
	 * @see #getArcrole()
	 * @generated
	 */
	void setArcrole(String value);

	/**
	 * Returns the value of the '<em><b>From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The traversal attributes are label, from, and to.
	 * 
	 * The from and to attributes may be used on the arc-type element.
	 * 
	 * Constraint: If a value is supplied for a from or to attribute, it must correspond to the same value for some label attribute on a locator- or resource-type element that appears as a direct child inside the same extended-type element as does the arc-type element.
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>From</em>' attribute.
	 * @see #setFrom(String)
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_From()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='from' namespace='##targetNamespace'"
	 * @generated
	 */
	String getFrom();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getFrom <em>From</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' attribute.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(String value);

	/**
	 * Returns the value of the '<em><b>Href</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The href attribute supplies the data that allows an XLink application to find a remote resource (or resource fragment). It may be used on simple-type elements, and must be used on locator-type elements.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Href</em>' attribute.
	 * @see #setHref(String)
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_Href()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='href' namespace='##targetNamespace'"
	 * @generated
	 */
	String getHref();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getHref <em>Href</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Href</em>' attribute.
	 * @see #getHref()
	 * @generated
	 */
	void setHref(String value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The traversal attributes are label, from, and to.
	 * 
	 * The label attribute may be used on the resource- and locator-type elements. 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_Label()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='label' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Role</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The attributes that describe the meaning of resources within the context of a link are role, arcrole, and title.
	 * 
	 * The role attribute may be used on extended-, simple-, locator-, and resource-type elements.
	 * 
	 * The URI reference identifies some resource that describes the intended property. When no value is supplied, no particular role value is to be inferred.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Role</em>' attribute.
	 * @see #setRole(String)
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_Role()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='role' namespace='##targetNamespace'"
	 * @generated
	 */
	String getRole();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getRole <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Role</em>' attribute.
	 * @see #getRole()
	 * @generated
	 */
	void setRole(String value);

	/**
	 * Returns the value of the '<em><b>Show</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.xsmp.smp.xlink.ShowKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The show attribute is used to communicate the desired presentation of the ending resource on traversal from the starting resource.
	 * 
	 * Conforming XLink applications should apply the specified treatment for the show values (see ShowKind).
	 * 
	 * Background:
	 * The behavior attributes are show and actuate. They may be used on the simple- and arc-type elements. When used on a simple-type element, they signal behavior intentions for traversal to that link's single remote ending resource. When they are used on an arc-type element, they signal behavior intentions for traversal to whatever ending resources (local or remote) are specified by that arc.
	 * 
	 * The show and actuate attributes are not required. When they are used, conforming XLink applications should give them the treatment specified in this section. There is no hard requirement ("must") for this treatment because what makes sense for an interactive application, such as a browser, is unlikely to make sense for a noninteractive application, such as a robot. However, all applications should take into account the full implications of ignoring the specified behavior before choosing a different course.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Show</em>' attribute.
	 * @see org.eclipse.xsmp.smp.xlink.ShowKind
	 * @see #isSetShow()
	 * @see #unsetShow()
	 * @see #setShow(ShowKind)
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_Show()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='show' namespace='##targetNamespace'"
	 * @generated
	 */
	ShowKind getShow();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getShow <em>Show</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show</em>' attribute.
	 * @see org.eclipse.xsmp.smp.xlink.ShowKind
	 * @see #isSetShow()
	 * @see #unsetShow()
	 * @see #getShow()
	 * @generated
	 */
	void setShow(ShowKind value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getShow <em>Show</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShow()
	 * @see #getShow()
	 * @see #setShow(ShowKind)
	 * @generated
	 */
	void unsetShow();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getShow <em>Show</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show</em>' attribute is set.
	 * @see #unsetShow()
	 * @see #getShow()
	 * @see #setShow(ShowKind)
	 * @generated
	 */
	boolean isSetShow();

	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The attributes that describe the meaning of resources within the context of a link are role, arcrole, and title.
	 * 
	 * The title attribute may be used on extended-, simple-, locator-, resource-, and arc-type elements.
	 * 
	 * The title attribute is used to describe the meaning of a link or resource in a human-readable fashion, along the same lines as the role or arcrole attribute. A value is optional; if a value is supplied, it should contain a string that describes the resource. The use of this information is highly dependent on the type of processing being done. It may be used, for example, to make titles available to applications used by visually impaired users, or to create a table of links, or to present help text that appears when a user lets a mouse pointer hover over a starting resource.
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_Title()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='title' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The traversal attributes are label, from, and to.
	 * 
	 * The from and to attributes may be used on the arc-type element.
	 * 
	 * Constraint: If a value is supplied for a from or to attribute, it must correspond to the same value for some label attribute on a locator- or resource-type element that appears as a direct child inside the same extended-type element as does the arc-type element.
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>To</em>' attribute.
	 * @see #setTo(String)
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_To()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
	 *        extendedMetaData="kind='attribute' name='to' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTo();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getTo <em>To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' attribute.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.xsmp.smp.xlink.TypeKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The type attribute identifies XLink element types.
	 * 
	 * The value of the type attribute must be supplied. The value must be one of "simple", "extended", "locator", "arc", "resource", "title", or "none".
	 * 
	 * When the value of the type attribute is "none", the element has no XLink-specified meaning, and any XLink-related content or attributes have no XLink-specified relationship to the element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.xsmp.smp.xlink.TypeKind
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #setType(TypeKind)
	 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getXlinkDocument_Type()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='attribute' name='type' namespace='##targetNamespace'"
	 * @generated
	 */
	TypeKind getType();

	/**
	 * Sets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.xsmp.smp.xlink.TypeKind
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #getType()
	 * @generated
	 */
	void setType(TypeKind value);

	/**
	 * Unsets the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetType()
	 * @see #getType()
	 * @see #setType(TypeKind)
	 * @generated
	 */
	void unsetType();

	/**
	 * Returns whether the value of the '{@link org.eclipse.xsmp.smp.xlink.XlinkDocument#getType <em>Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Type</em>' attribute is set.
	 * @see #unsetType()
	 * @see #getType()
	 * @see #setType(TypeKind)
	 * @generated
	 */
	boolean isSetType();

} // XlinkDocument
