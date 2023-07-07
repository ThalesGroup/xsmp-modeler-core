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
package org.eclipse.xsmp.forms.service;

import static java.util.Collections.singleton;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizardFactory;
import org.eclipse.emf.ecp.ui.view.swt.reference.AttachmentStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.OpenInNewContextStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.SelectionCompositeStrategy;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xsmp.ide.contentassist.IReferenceFilter;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.scoping.IScopeProvider;

import com.google.common.collect.Iterables;

public class XsmpReferenceService implements org.eclipse.emf.ecp.edit.spi.ReferenceService
{

  private static final CreateNewModelElementStrategy createNewModelElementStrategy = CreateNewModelElementStrategy.DEFAULT;

  private static final AttachmentStrategy attachmentStrategy = AttachmentStrategy.DEFAULT;

  private static final ReferenceStrategy referenceStrategy = ReferenceStrategy.DEFAULT;

  private static final OpenInNewContextStrategy openInNewContextStrategy = OpenInNewContextStrategy.DEFAULT;

  private static final SelectionCompositeStrategy selectionCompositeStrategy = SelectionCompositeStrategy.DEFAULT;

  @Override
  public void instantiate(ViewModelContext context)
  {
    // Nothing to do
  }

  @Override
  public void dispose()
  {
    // Nothing to do
  }

  @Override
  public int getPriority()
  {
    return 2;
  }

  @Override
  public void addNewModelElements(EObject eObject, EReference eReference)
  {
    addNewModelElements(eObject, eReference, true);
  }

  /**
   * @since 1.17
   */
  @Override
  public Optional<EObject> addNewModelElements(EObject eObject, EReference eReference,
          boolean openInNewContext)
  {
    if (eReference.isContainer())
    {
      MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", //$NON-NLS-1$
              "Operation not permitted for container references!");//$NON-NLS-1$
      return Optional.empty();
    }

    final var newMEInstanceOptional = createNewModelElementStrategy.createNewModelElement(eObject,
            eReference);

    if (!newMEInstanceOptional.isPresent())
    {
      return Optional.empty();
    }
    final var newMEInstance = newMEInstanceOptional.get();

    if (!eReference.isContainment())
    {
      attachmentStrategy.addElementToModel(eObject, eReference, newMEInstance);
    }

    referenceStrategy.addElementsToReference(eObject, eReference, singleton(newMEInstance));

    if (openInNewContext)
    {
      openInNewContext(newMEInstance);
    }

    return newMEInstanceOptional;
  }

  @Override
  public void openInNewContext(final EObject eObject)
  {
    final var owner = eObject.eContainer();
    final var reference = eObject.eContainmentFeature();
    openInNewContextStrategy.openInNewContext(owner, reference, eObject);
  }

  @Override
  public void addExistingModelElements(EObject eObject, EReference eReference)
  {
    final var resource = (XtextResource) eObject.eResource();
    final var provider = resource.getResourceServiceProvider().get(IScopeProvider.class);
    final var filter = resource.getResourceServiceProvider().get(IReferenceFilter.class);

    final var scope = provider.getScope(eObject, eReference);
    final var filteredCandidates = Iterables.filter(scope.getAllElements(),
            filter.getFilter(eObject, eReference));

    final Set<EObject> elements = new LinkedHashSet<>();

    Iterables.transform(filteredCandidates, p -> EcoreUtil.resolve(p.getEObjectOrProxy(), eObject))
            .forEach(elements::add);

    final var selectionComposite = selectionCompositeStrategy.getSelectionViewer(eObject,
            eReference, elements);

    final Set<EObject> addedElements;
    if (selectionComposite == null)
    {
      addedElements = SelectModelElementWizardFactory.openModelElementSelectionDialog(elements,
              eReference.isMany());
    }
    else
    {
      addedElements = SelectModelElementWizardFactory
              .openModelElementSelectionDialog(selectionComposite);
    }

    // Don't invoke the Bazaar machinery to find a strategy just to add no elements
    if (!addedElements.isEmpty())
    {
      referenceStrategy.addElementsToReference(eObject, eReference, addedElements);
    }
  }

}
