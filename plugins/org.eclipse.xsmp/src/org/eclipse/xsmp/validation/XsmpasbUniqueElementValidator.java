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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.CancelableDiagnostician;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.DefaultUniqueNameContext;
import org.eclipse.xtext.validation.DefaultUniqueNameContext.Global;
import org.eclipse.xtext.validation.DefaultUniqueNameContext.VisibleContainers;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 * check that elements are unique
 */
public class XsmpasbUniqueElementValidator extends AbstractDeclarativeValidator
{

  @Inject
  private final Global globalContextProvider = new DefaultUniqueNameContext.Global();

  @Inject
  private final VisibleContainers visibleContextProvider = new DefaultUniqueNameContext.VisibleContainers();

  @Inject
  private UuidsAreUniqueValidationHelper uuidHelper;

  @Inject
  private ServiceNameAreUniqueValidationHelper serviceHelper;

  @Inject
  private CatalogueNameAreUniqueValidationHelper catalogueHelper;

  @Inject
  private SmpNamesAreUniqueValidationHelper nameHelper;

  /**
   * Check unicity in the resource
   *
   * @param eObject
   */
  @Check
  public void checkResourceOf(EObject eObject)
  {
    if (eObject.eContainer() != null)
    {
      return;
    }

    final var resource = eObject.eResource();
    if (resource == null)
    {
      return;
    }
    CancelIndicator cancelIndicator = null;
    final var context = getContext();
    if (context != null)
    {
      if (context.containsKey(resource))
      {
        return; // resource was already validated
      }
      context.put(resource, this);
      cancelIndicator = (CancelIndicator) context.get(CancelableDiagnostician.CANCEL_INDICATOR);
    }
    final var validationContext = visibleContextProvider.tryGetContext(resource, cancelIndicator);
    if (validationContext != null)
    {
      // Check that qualified names are unique in visible containers
      nameHelper.checkUniqueNames(validationContext, this);
    }
    final var globalValidationContext = globalContextProvider.tryGetContext(resource,
            cancelIndicator);
    if (globalValidationContext != null)
    {
      // Check that uuids are unique in whole index
      uuidHelper.checkUniqueNames(globalValidationContext, this);
      // Check that service name are unique in whole index
      serviceHelper.checkUniqueNames(globalValidationContext, this);
      // Check that catalogue name are unique in whole index
      catalogueHelper.checkUniqueNames(globalValidationContext, this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void register(EValidatorRegistrar registrar)
  {
    // validator is not registered for a specific language
  }

}
