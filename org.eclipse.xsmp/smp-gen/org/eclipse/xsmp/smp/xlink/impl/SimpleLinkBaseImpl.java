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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.xsmp.smp.xlink.ActuateKind;
import org.eclipse.xsmp.smp.xlink.ShowKind;
import org.eclipse.xsmp.smp.xlink.SimpleLinkBase;
import org.eclipse.xsmp.smp.xlink.TypeKind;
import org.eclipse.xsmp.smp.xlink.XlinkPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Link Base</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.impl.SimpleLinkBaseImpl#getActuate <em>Actuate</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.impl.SimpleLinkBaseImpl#getArcrole <em>Arcrole</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.impl.SimpleLinkBaseImpl#getRole <em>Role</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.impl.SimpleLinkBaseImpl#getShow <em>Show</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.impl.SimpleLinkBaseImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.xsmp.smp.xlink.impl.SimpleLinkBaseImpl#getLinkType <em>Link Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SimpleLinkBaseImpl extends MinimalEObjectImpl.Container implements SimpleLinkBase
{
	/**
	 * The default value of the '{@link #getActuate() <em>Actuate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActuate()
	 * @generated
	 * @ordered
	 */
	protected static final ActuateKind ACTUATE_EDEFAULT = ActuateKind.ON_LOAD;

	/**
	 * The cached value of the '{@link #getActuate() <em>Actuate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActuate()
	 * @generated
	 * @ordered
	 */
	protected ActuateKind actuate = ACTUATE_EDEFAULT;

	/**
	 * This is true if the Actuate attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean actuateESet;

	/**
	 * The default value of the '{@link #getArcrole() <em>Arcrole</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArcrole()
	 * @generated
	 * @ordered
	 */
	protected static final String ARCROLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getArcrole() <em>Arcrole</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArcrole()
	 * @generated
	 * @ordered
	 */
	protected String arcrole = ARCROLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getRole() <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected static final String ROLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRole() <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected String role = ROLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getShow() <em>Show</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShow()
	 * @generated
	 * @ordered
	 */
	protected static final ShowKind SHOW_EDEFAULT = ShowKind.NEW;

	/**
	 * The cached value of the '{@link #getShow() <em>Show</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShow()
	 * @generated
	 * @ordered
	 */
	protected ShowKind show = SHOW_EDEFAULT;

	/**
	 * This is true if the Show attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showESet;

	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLinkType() <em>Link Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkType()
	 * @generated
	 * @ordered
	 */
	protected static final TypeKind LINK_TYPE_EDEFAULT = TypeKind.SIMPLE;

	/**
	 * The cached value of the '{@link #getLinkType() <em>Link Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinkType()
	 * @generated
	 * @ordered
	 */
	protected TypeKind linkType = LINK_TYPE_EDEFAULT;

	/**
	 * This is true if the Link Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean linkTypeESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimpleLinkBaseImpl()
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
		return XlinkPackage.Literals.SIMPLE_LINK_BASE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ActuateKind getActuate()
	{
		return actuate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActuate(ActuateKind newActuate)
	{
		ActuateKind oldActuate = actuate;
		actuate = newActuate == null ? ACTUATE_EDEFAULT : newActuate;
		boolean oldActuateESet = actuateESet;
		actuateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XlinkPackage.SIMPLE_LINK_BASE__ACTUATE, oldActuate, actuate, !oldActuateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetActuate()
	{
		ActuateKind oldActuate = actuate;
		boolean oldActuateESet = actuateESet;
		actuate = ACTUATE_EDEFAULT;
		actuateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, XlinkPackage.SIMPLE_LINK_BASE__ACTUATE, oldActuate, ACTUATE_EDEFAULT, oldActuateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetActuate()
	{
		return actuateESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getArcrole()
	{
		return arcrole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setArcrole(String newArcrole)
	{
		String oldArcrole = arcrole;
		arcrole = newArcrole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XlinkPackage.SIMPLE_LINK_BASE__ARCROLE, oldArcrole, arcrole));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRole()
	{
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRole(String newRole)
	{
		String oldRole = role;
		role = newRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XlinkPackage.SIMPLE_LINK_BASE__ROLE, oldRole, role));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ShowKind getShow()
	{
		return show;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setShow(ShowKind newShow)
	{
		ShowKind oldShow = show;
		show = newShow == null ? SHOW_EDEFAULT : newShow;
		boolean oldShowESet = showESet;
		showESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XlinkPackage.SIMPLE_LINK_BASE__SHOW, oldShow, show, !oldShowESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetShow()
	{
		ShowKind oldShow = show;
		boolean oldShowESet = showESet;
		show = SHOW_EDEFAULT;
		showESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, XlinkPackage.SIMPLE_LINK_BASE__SHOW, oldShow, SHOW_EDEFAULT, oldShowESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetShow()
	{
		return showESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTitle()
	{
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTitle(String newTitle)
	{
		String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XlinkPackage.SIMPLE_LINK_BASE__TITLE, oldTitle, title));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeKind getLinkType()
	{
		return linkType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLinkType(TypeKind newLinkType)
	{
		TypeKind oldLinkType = linkType;
		linkType = newLinkType == null ? LINK_TYPE_EDEFAULT : newLinkType;
		boolean oldLinkTypeESet = linkTypeESet;
		linkTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, XlinkPackage.SIMPLE_LINK_BASE__LINK_TYPE, oldLinkType, linkType, !oldLinkTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetLinkType()
	{
		TypeKind oldLinkType = linkType;
		boolean oldLinkTypeESet = linkTypeESet;
		linkType = LINK_TYPE_EDEFAULT;
		linkTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, XlinkPackage.SIMPLE_LINK_BASE__LINK_TYPE, oldLinkType, LINK_TYPE_EDEFAULT, oldLinkTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetLinkType()
	{
		return linkTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getHref()
	{
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
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
			case XlinkPackage.SIMPLE_LINK_BASE__ACTUATE:
				return getActuate();
			case XlinkPackage.SIMPLE_LINK_BASE__ARCROLE:
				return getArcrole();
			case XlinkPackage.SIMPLE_LINK_BASE__ROLE:
				return getRole();
			case XlinkPackage.SIMPLE_LINK_BASE__SHOW:
				return getShow();
			case XlinkPackage.SIMPLE_LINK_BASE__TITLE:
				return getTitle();
			case XlinkPackage.SIMPLE_LINK_BASE__LINK_TYPE:
				return getLinkType();
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
			case XlinkPackage.SIMPLE_LINK_BASE__ACTUATE:
				setActuate((ActuateKind)newValue);
				return;
			case XlinkPackage.SIMPLE_LINK_BASE__ARCROLE:
				setArcrole((String)newValue);
				return;
			case XlinkPackage.SIMPLE_LINK_BASE__ROLE:
				setRole((String)newValue);
				return;
			case XlinkPackage.SIMPLE_LINK_BASE__SHOW:
				setShow((ShowKind)newValue);
				return;
			case XlinkPackage.SIMPLE_LINK_BASE__TITLE:
				setTitle((String)newValue);
				return;
			case XlinkPackage.SIMPLE_LINK_BASE__LINK_TYPE:
				setLinkType((TypeKind)newValue);
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
			case XlinkPackage.SIMPLE_LINK_BASE__ACTUATE:
				unsetActuate();
				return;
			case XlinkPackage.SIMPLE_LINK_BASE__ARCROLE:
				setArcrole(ARCROLE_EDEFAULT);
				return;
			case XlinkPackage.SIMPLE_LINK_BASE__ROLE:
				setRole(ROLE_EDEFAULT);
				return;
			case XlinkPackage.SIMPLE_LINK_BASE__SHOW:
				unsetShow();
				return;
			case XlinkPackage.SIMPLE_LINK_BASE__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case XlinkPackage.SIMPLE_LINK_BASE__LINK_TYPE:
				unsetLinkType();
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
			case XlinkPackage.SIMPLE_LINK_BASE__ACTUATE:
				return isSetActuate();
			case XlinkPackage.SIMPLE_LINK_BASE__ARCROLE:
				return ARCROLE_EDEFAULT == null ? arcrole != null : !ARCROLE_EDEFAULT.equals(arcrole);
			case XlinkPackage.SIMPLE_LINK_BASE__ROLE:
				return ROLE_EDEFAULT == null ? role != null : !ROLE_EDEFAULT.equals(role);
			case XlinkPackage.SIMPLE_LINK_BASE__SHOW:
				return isSetShow();
			case XlinkPackage.SIMPLE_LINK_BASE__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case XlinkPackage.SIMPLE_LINK_BASE__LINK_TYPE:
				return isSetLinkType();
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
		result.append(" (actuate: ");
		if (actuateESet) result.append(actuate); else result.append("<unset>");
		result.append(", arcrole: ");
		result.append(arcrole);
		result.append(", role: ");
		result.append(role);
		result.append(", show: ");
		if (showESet) result.append(show); else result.append("<unset>");
		result.append(", title: ");
		result.append(title);
		result.append(", linkType: ");
		if (linkTypeESet) result.append(linkType); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SimpleLinkBaseImpl
