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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Type Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Enumeration defining the possible values for the type attribute.
 * <!-- end-model-doc -->
 * @see org.eclipse.xsmp.smp.xlink.XlinkPackage#getTypeKind()
 * @model extendedMetaData="name='TypeKind'"
 * @generated
 */
public enum TypeKind implements Enumerator
{
	/**
	 * The '<em><b>Simple</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is a simple link.
	 * <!-- end-model-doc -->
	 * @see #SIMPLE_VALUE
	 * @generated
	 * @ordered
	 */
	SIMPLE(0, "simple", "simple"),

	/**
	 * The '<em><b>Extended</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is an extended link.
	 * <!-- end-model-doc -->
	 * @see #EXTENDED_VALUE
	 * @generated
	 * @ordered
	 */
	EXTENDED(1, "extended", "extended"),

	/**
	 * The '<em><b>Title</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is a title.
	 * <!-- end-model-doc -->
	 * @see #TITLE_VALUE
	 * @generated
	 * @ordered
	 */
	TITLE(2, "title", "title"),

	/**
	 * The '<em><b>Arc</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is an arc.
	 * <!-- end-model-doc -->
	 * @see #ARC_VALUE
	 * @generated
	 * @ordered
	 */
	ARC(3, "arc", "arc"),

	/**
	 * The '<em><b>Resource</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is a resource.
	 * <!-- end-model-doc -->
	 * @see #RESOURCE_VALUE
	 * @generated
	 * @ordered
	 */
	RESOURCE(4, "resource", "resource"),

	/**
	 * The '<em><b>Locator</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is a locator.
	 * <!-- end-model-doc -->
	 * @see #LOCATOR_VALUE
	 * @generated
	 * @ordered
	 */
	LOCATOR(5, "locator", "locator"),

	/**
	 * The '<em><b>None</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is not a known XLink element.
	 * <!-- end-model-doc -->
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(6, "none", "none");

	/**
	 * The '<em><b>Simple</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is a simple link.
	 * <!-- end-model-doc -->
	 * @see #SIMPLE
	 * @model name="simple"
	 * @generated
	 * @ordered
	 */
	public static final int SIMPLE_VALUE = 0;

	/**
	 * The '<em><b>Extended</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is an extended link.
	 * <!-- end-model-doc -->
	 * @see #EXTENDED
	 * @model name="extended"
	 * @generated
	 * @ordered
	 */
	public static final int EXTENDED_VALUE = 1;

	/**
	 * The '<em><b>Title</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is a title.
	 * <!-- end-model-doc -->
	 * @see #TITLE
	 * @model name="title"
	 * @generated
	 * @ordered
	 */
	public static final int TITLE_VALUE = 2;

	/**
	 * The '<em><b>Arc</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is an arc.
	 * <!-- end-model-doc -->
	 * @see #ARC
	 * @model name="arc"
	 * @generated
	 * @ordered
	 */
	public static final int ARC_VALUE = 3;

	/**
	 * The '<em><b>Resource</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is a resource.
	 * <!-- end-model-doc -->
	 * @see #RESOURCE
	 * @model name="resource"
	 * @generated
	 * @ordered
	 */
	public static final int RESOURCE_VALUE = 4;

	/**
	 * The '<em><b>Locator</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is a locator.
	 * <!-- end-model-doc -->
	 * @see #LOCATOR
	 * @model name="locator"
	 * @generated
	 * @ordered
	 */
	public static final int LOCATOR_VALUE = 5;

	/**
	 * The '<em><b>None</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element is not a known XLink element.
	 * <!-- end-model-doc -->
	 * @see #NONE
	 * @model name="none"
	 * @generated
	 * @ordered
	 */
	public static final int NONE_VALUE = 6;

	/**
	 * An array of all the '<em><b>Type Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TypeKind[] VALUES_ARRAY =
		new TypeKind[]
		{
			SIMPLE,
			EXTENDED,
			TITLE,
			ARC,
			RESOURCE,
			LOCATOR,
			NONE,
		};

	/**
	 * A public read-only list of all the '<em><b>Type Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TypeKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Type Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TypeKind get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			TypeKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Type Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TypeKind getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			TypeKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Type Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TypeKind get(int value)
	{
		switch (value)
		{
			case SIMPLE_VALUE: return SIMPLE;
			case EXTENDED_VALUE: return EXTENDED;
			case TITLE_VALUE: return TITLE;
			case ARC_VALUE: return ARC;
			case RESOURCE_VALUE: return RESOURCE;
			case LOCATOR_VALUE: return LOCATOR;
			case NONE_VALUE: return NONE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private TypeKind(int value, String name, String literal)
	{
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue()
	{
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName()
	{
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral()
	{
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		return literal;
	}
	
} //TypeKind
