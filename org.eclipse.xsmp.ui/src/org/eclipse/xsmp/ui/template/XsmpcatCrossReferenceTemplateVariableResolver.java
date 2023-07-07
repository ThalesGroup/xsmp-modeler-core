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
package org.eclipse.xsmp.ui.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.xsmp.ide.contentassist.IReferenceFilter;
import org.eclipse.xsmp.util.QualifiedNames;
import org.eclipse.xsmp.xcatalogue.PrimitiveType;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.ui.editor.templates.CrossReferenceTemplateVariableResolver;
import org.eclipse.xtext.ui.editor.templates.XtextTemplateContext;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpcatCrossReferenceTemplateVariableResolver
        extends CrossReferenceTemplateVariableResolver
{
  private static final Logger log = Logger
          .getLogger(XsmpcatCrossReferenceTemplateVariableResolver.class);

  @Inject
  private IQualifiedNameConverter qualifiedNameConverter;

  @Inject
  private IGlobalScopeProvider globalScopeProvider;

  @Inject
  private IReferenceFilter referenceFilter;

  @Override
  public List<String> resolveValues(TemplateVariable variable, XtextTemplateContext castedContext)
  {
    final var abbreviatedCrossReference = variable.getVariableType().getParams().iterator().next();
    final var dotIndex = abbreviatedCrossReference.lastIndexOf('.');
    if (dotIndex <= 0)
    {
      log.error("CrossReference '" + abbreviatedCrossReference + "' could not be resolved."); //$NON-NLS-1$ //$NON-NLS-2$
      return Collections.emptyList();
    }
    final String[] classReferencePair = {
      abbreviatedCrossReference.substring(0, dotIndex),
      abbreviatedCrossReference.substring(dotIndex + 1) };
    final var grammar = getGrammar(castedContext);
    if (grammar == null)
    {
      return Collections.emptyList();
    }
    final var reference = getReference(classReferencePair[0], classReferencePair[1], grammar);
    if (reference == null)
    {
      log.debug("CrossReference to class '" + classReferencePair[0] + "' and reference '" //$NON-NLS-1$ //$NON-NLS-2$
              + classReferencePair[1] + "' could not be resolved."); //$NON-NLS-1$
      return Collections.emptyList();
    }
    IScope scope = null;
    final var currentModel = castedContext.getContentAssistContext().getCurrentModel();
    if (currentModel == null)
    {
      scope = globalScopeProvider.getScope(castedContext.getContentAssistContext().getResource(),
              reference, null);
    }
    else
    {

      scope = castedContext.getScopeProvider().getScope(currentModel, reference);
    }
    var linkingCandidates = queryScope(scope);

    final var filter = referenceFilter.getFilter(currentModel, reference);

    if (filter != null)
    {
      linkingCandidates = Iterables.filter(linkingCandidates, filter);
    }

    final List<String> names = new ArrayList<>();

    final List<IEObjectDescription> candidates = new ArrayList<>();

    linkingCandidates.forEach(candidates::add);
    // put primitive types first, then user types then smp types
    candidates.sort((l, r) -> {
      if (l.getEObjectOrProxy() instanceof PrimitiveType
              && !(r.getEObjectOrProxy() instanceof PrimitiveType))
      {
        return -1;
      }

      if (r.getEObjectOrProxy() instanceof PrimitiveType
              && !(l.getEObjectOrProxy() instanceof PrimitiveType))
      {
        return 1;
      }

      if (r.getQualifiedName().startsWith(QualifiedNames.Smp)
              && !l.getQualifiedName().startsWith(QualifiedNames.Smp))
      {
        return -1;
      }
      if (l.getQualifiedName().startsWith(QualifiedNames.Smp)
              && !r.getQualifiedName().startsWith(QualifiedNames.Smp))
      {
        return 1;
      }
      return l.getQualifiedName().compareTo(r.getQualifiedName());
    });

    final Set<EObject> added = new HashSet<>();
    for (final IEObjectDescription eObjectDescription : candidates)
    {
      // do not duplicate EObjects
      if (added.add(eObjectDescription.getEObjectOrProxy()))
      {
        names.add(qualifiedNameConverter.toString(eObjectDescription.getName()));
      }
    }

    return names;
  }

}
