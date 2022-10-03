/*******************************************************************************
* Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.xsmp.extension.IExtensionConfigurationProvider;
import org.eclipse.xsmp.mdk.IMdkConfigurationProvider;
import org.eclipse.xtext.validation.AbstractInjectableValidator;

import com.google.inject.Inject;

/**
 * This class contains custom validation rules. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */

public final class XsmpcatValidator extends AbstractInjectableValidator
{

  static final String VALIDATOR_KEY = "XsmpcatValidator.key";

  @Inject
  private final DefaultValidator delegate = new DefaultValidator();

  @Inject(optional = true)
  private IMdkConfigurationProvider mdkProvider;

  @Inject(optional = true)
  private IExtensionConfigurationProvider extensionProvider;

  @Override
  protected List<EPackage> getEPackages()
  {
    return delegate.getEPackages();
  }

  @Override
  protected boolean internalValidate(EClass eClass, EObject eObject, DiagnosticChain diagnostics,
          Map<Object, Object> context)
  {
    @SuppressWarnings("unchecked")
    final var validators = (List<EValidator>) context.computeIfAbsent(VALIDATOR_KEY, k -> {

      final var result = new ArrayList<EValidator>();
      final var resource = eObject.eResource();
      if (mdkProvider != null)
      {
        final var mdkValidator = mdkProvider.getInstance(resource, DefaultValidator.class);
        if (mdkValidator != null)
        {
          result.add(mdkValidator);
        }
      }
      if (result.isEmpty())
      {
        result.add(delegate);
      }
      if (extensionProvider != null)
      {
        result.addAll(extensionProvider.getValidators(resource));
      }

      return result;
    });

    var isValid = true;
    for (final var validator : validators)
    {
      isValid &= validator.validate(eClass, eObject, diagnostics, context);
    }

    return isValid;
  }

}
