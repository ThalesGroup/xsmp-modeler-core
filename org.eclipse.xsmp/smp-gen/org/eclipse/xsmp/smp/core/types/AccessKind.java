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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Access Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * This enumeration defines how a property can be accessed.
 * <!-- end-model-doc -->
 * @see org.eclipse.xsmp.smp.core.types.TypesPackage#getAccessKind()
 * @model extendedMetaData="name='AccessKind'"
 * @generated
 */
public enum AccessKind implements Enumerator
{
	/**
	 * The '<em><b>Read Write</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies a property, which has both a getter and a setter.
	 * <!-- end-model-doc -->
	 * @see #READ_WRITE_VALUE
	 * @generated
	 * @ordered
	 */
	READ_WRITE(0, "readWrite", "readWrite"),

	/**
	 * The '<em><b>Read Only</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies a property, which only has a getter.
	 * <!-- end-model-doc -->
	 * @see #READ_ONLY_VALUE
	 * @generated
	 * @ordered
	 */
	READ_ONLY(1, "readOnly", "readOnly"),

	/**
	 * The '<em><b>Write Only</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies a property, which only has a setter.
	 * <!-- end-model-doc -->
	 * @see #WRITE_ONLY_VALUE
	 * @generated
	 * @ordered
	 */
	WRITE_ONLY(2, "writeOnly", "writeOnly");

	/**
	 * The '<em><b>Read Write</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies a property, which has both a getter and a setter.
	 * <!-- end-model-doc -->
	 * @see #READ_WRITE
	 * @model name="readWrite"
	 * @generated
	 * @ordered
	 */
	public static final int READ_WRITE_VALUE = 0;

	/**
	 * The '<em><b>Read Only</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies a property, which only has a getter.
	 * <!-- end-model-doc -->
	 * @see #READ_ONLY
	 * @model name="readOnly"
	 * @generated
	 * @ordered
	 */
	public static final int READ_ONLY_VALUE = 1;

	/**
	 * The '<em><b>Write Only</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies a property, which only has a setter.
	 * <!-- end-model-doc -->
	 * @see #WRITE_ONLY
	 * @model name="writeOnly"
	 * @generated
	 * @ordered
	 */
	public static final int WRITE_ONLY_VALUE = 2;

	/**
	 * An array of all the '<em><b>Access Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final AccessKind[] VALUES_ARRAY =
		new AccessKind[]
		{
			READ_WRITE,
			READ_ONLY,
			WRITE_ONLY,
		};

	/**
	 * A public read-only list of all the '<em><b>Access Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<AccessKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Access Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AccessKind get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			AccessKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Access Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AccessKind getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			AccessKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Access Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static AccessKind get(int value)
	{
		switch (value)
		{
			case READ_WRITE_VALUE: return READ_WRITE;
			case READ_ONLY_VALUE: return READ_ONLY;
			case WRITE_ONLY_VALUE: return WRITE_ONLY;
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
	private AccessKind(int value, String name, String literal)
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
	
} //AccessKind
