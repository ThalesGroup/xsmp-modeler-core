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
package org.eclipse.xsmp.ui.contentassist;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.xsmp.ide.contentassist.IReferenceFilter;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider.ReferenceProposalCreator;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

public class XsmpcatReferenceProposalCreator extends ReferenceProposalCreator
{

  @Inject
  private IReferenceFilter referenceFilter;

  /**
   * {@inheritDoc}
   */
  @Override
  public void lookupCrossReference(IScope scope, EObject model, EReference reference,
          ICompletionProposalAcceptor acceptor, Predicate<IEObjectDescription> filter,
          Function<IEObjectDescription, ICompletionProposal> proposalFactory)
  {

    super.lookupCrossReference(scope, model, reference, acceptor,
            Predicates.and(filter, referenceFilter.getFilter(model, reference)), proposalFactory);
  }

  public Iterable<EObject> getReachableObjects(EObject model, EReference reference)
  {
    if (model != null)
    {
      final var scope = getScopeProvider().getScope(model, reference);
      final var filteredCandidates = Iterables.filter(scope.getAllElements(),
              referenceFilter.getFilter(model, reference));
      return Iterables.transform(filteredCandidates,
              p -> EcoreUtil.resolve(p.getEObjectOrProxy(), model));
    }
    return Collections.emptyList();
  }

}
