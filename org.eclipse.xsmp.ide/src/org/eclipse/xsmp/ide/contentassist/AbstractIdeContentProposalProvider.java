/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.contentassist;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.PolymorphicDispatcher.ErrorHandler;
import org.eclipse.xtext.util.PolymorphicDispatcher.WarningErrorHandler;
import org.eclipse.xtext.xtext.CurrentTypeFinder;

import com.google.inject.Inject;

public class AbstractIdeContentProposalProvider extends IdeContentProposalProvider
{
  @Inject
  protected XsmpcatReferenceFilter filter;

  @Inject
  private CurrentTypeFinder currentTypeFinder;

  private final Map<String, PolymorphicDispatcher<Void>> dispatchers;

  private final static Logger log = Logger.getLogger(AbstractIdeContentProposalProvider.class);

  protected AbstractIdeContentProposalProvider()
  {
    dispatchers = new HashMap<>();
  }

  @Override
  protected void _createProposals(Keyword keyword, ContentAssistContext context,
          IIdeContentProposalAcceptor acceptor)
  {
    final var method = "complete_" + keyword.getValue();
    invokeMethod(method, acceptor, context);

    super._createProposals(keyword, context, acceptor);
  }

  protected void invokeMethod(String methodName, IIdeContentProposalAcceptor acceptor,
          ContentAssistContext context)
  {
    var dispatcher = dispatchers.get(methodName);
    if (dispatcher == null)
    {
      final ErrorHandler<Void> errorHandler = WarningErrorHandler.get(log);
      dispatcher = new PolymorphicDispatcher<>(methodName, 2, 2, Collections.singletonList(this),
              errorHandler) {
        @Override
        public Class< ? > getDefaultClass(int paramIndex)
        {
          if (paramIndex == 0)
          {
            return EObject.class;
          }
          return super.getDefaultClass(paramIndex);
        }
      };
      dispatchers.put(methodName, dispatcher);
    }
    dispatcher.invoke(context, acceptor);
  }

  @Override
  protected void _createProposals(CrossReference reference, ContentAssistContext context,
          IIdeContentProposalAcceptor acceptor)
  {
    final var type = currentTypeFinder.findCurrentTypeAfter(reference);
    if (type instanceof EClass)
    {
      final var eReference = GrammarUtil.getReference(reference, (EClass) type);
      final var currentModel = context.getCurrentModel();
      if (eReference != null && currentModel != null)
      {
        final var scope = getScopeProvider().getScope(currentModel, eReference);
        getCrossrefProposalProvider().lookupCrossReference(scope, reference, context, acceptor,
                filter.getFilter(currentModel, eReference));
      }
    }
  }
}
