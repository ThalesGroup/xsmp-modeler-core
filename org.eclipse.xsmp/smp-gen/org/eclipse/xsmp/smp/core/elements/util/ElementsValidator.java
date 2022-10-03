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
package org.eclipse.xsmp.smp.core.elements.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.emf.ecore.xml.type.util.XMLTypeUtil;
import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

import org.eclipse.xsmp.smp.core.elements.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.xsmp.smp.core.elements.ElementsPackage
 * @generated
 */
public class ElementsValidator extends EObjectValidator
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ElementsValidator INSTANCE = new ElementsValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.xsmp.smp.core.elements";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XMLTypeValidator xmlTypeValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementsValidator()
	{
		super();
		xmlTypeValidator = XMLTypeValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage()
	{
	  return ElementsPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		switch (classifierID)
		{
			case ElementsPackage.COMMENT:
				return validateComment((Comment)value, diagnostics, context);
			case ElementsPackage.DOCUMENT:
				return validateDocument((Document)value, diagnostics, context);
			case ElementsPackage.DOCUMENTATION:
				return validateDocumentation((Documentation)value, diagnostics, context);
			case ElementsPackage.METADATA:
				return validateMetadata((Metadata)value, diagnostics, context);
			case ElementsPackage.NAMED_ELEMENT:
				return validateNamedElement((NamedElement)value, diagnostics, context);
			case ElementsPackage.DESCRIPTION:
				return validateDescription((String)value, diagnostics, context);
			case ElementsPackage.IDENTIFIER:
				return validateIdentifier((String)value, diagnostics, context);
			case ElementsPackage.NAME:
				return validateName((String)value, diagnostics, context);
			case ElementsPackage.PATH:
				return validatePath((String)value, diagnostics, context);
			case ElementsPackage.UUID:
				return validateUUID((String)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateComment(Comment comment, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(comment, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDocument(Document document, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(document, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDocumentation(Documentation documentation, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(documentation, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetadata(Metadata metadata, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(metadata, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedElement(NamedElement namedElement, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(namedElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDescription(String description, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIdentifier(String identifier, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		boolean result = xmlTypeValidator.validateNCName_Pattern(identifier, diagnostics, context);
		if (result || diagnostics != null) result &= validateIdentifier_MinLength(identifier, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MinLength constraint of '<em>Identifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIdentifier_MinLength(String identifier, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		int length = identifier.length();
		boolean result = length >= 1;
		if (!result && diagnostics != null)
			reportMinLengthViolation(ElementsPackage.Literals.IDENTIFIER, identifier, length, 1, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateName(String name, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		boolean result = validateName_Pattern(name, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @see #validateName_Pattern
	 */
	public static final  PatternMatcher [][] NAME__PATTERN__VALUES =
		new PatternMatcher [][]
		{
			new PatternMatcher []
			{
				XMLTypeUtil.createPatternMatcher("[a-zA-Z][a-zA-Z0-9_]*")
			}
		};

	/**
	 * Validates the Pattern constraint of '<em>Name</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateName_Pattern(String name, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validatePattern(ElementsPackage.Literals.NAME, name, NAME__PATTERN__VALUES, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePath(String path, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		boolean result = validatePath_Pattern(path, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @see #validatePath_Pattern
	 */
	public static final  PatternMatcher [][] PATH__PATTERN__VALUES =
		new PatternMatcher [][]
		{
			new PatternMatcher []
			{
				XMLTypeUtil.createPatternMatcher("[a-zA-Z\\./][a-zA-Z0-9_\\./\\[\\]]*")
			}
		};

	/**
	 * Validates the Pattern constraint of '<em>Path</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePath_Pattern(String path, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validatePattern(ElementsPackage.Literals.PATH, path, PATH__PATTERN__VALUES, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUUID(String uuid, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		boolean result = validateUUID_Pattern(uuid, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @see #validateUUID_Pattern
	 */
	public static final  PatternMatcher [][] UUID__PATTERN__VALUES =
		new PatternMatcher [][]
		{
			new PatternMatcher []
			{
				XMLTypeUtil.createPatternMatcher("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")
			}
		};

	/**
	 * Validates the Pattern constraint of '<em>UUID</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUUID_Pattern(String uuid, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validatePattern(ElementsPackage.Literals.UUID, uuid, UUID__PATTERN__VALUES, diagnostics, context);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator()
	{
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //ElementsValidator
