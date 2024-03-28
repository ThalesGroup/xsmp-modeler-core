/*******************************************************************************
* Copyright (C) 2023-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.contentassist;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.PolymorphicDispatcher.ErrorHandler;
import org.eclipse.xtext.util.PolymorphicDispatcher.WarningErrorHandler;
import org.eclipse.xtext.util.TextRegion;

import com.google.inject.Inject;

public class XsmpIdeContentProposalProvider extends IdeContentProposalProvider
{
  @Inject
  protected IReferenceFilter filter;

  private final Map<String, PolymorphicDispatcher<Void>> dispatchers;

  private static final Logger log = Logger.getLogger(XsmpIdeContentProposalProvider.class);

  protected XsmpIdeContentProposalProvider()
  {
    dispatchers = new HashMap<>();
  }

  @Override
  protected void _createProposals(Assignment assignment, ContentAssistContext context,
          IIdeContentProposalAcceptor acceptor)
  {
    final var terminal = assignment.getTerminal();
    if (terminal instanceof CrossReference)
    {
      createProposals(terminal, context, acceptor);
    }
    else if (terminal instanceof RuleCall)
    {
      final var rule = ((RuleCall) terminal).getRule();
      if ("ML_DOCUMENTATION".equals(rule.getName()))
      {
        return;
      }
      if (rule instanceof TerminalRule && context.getPrefix().isEmpty())
      {
        final String proposal;
        if ("STRING_LITERAL".equals(rule.getName()))
        {
          proposal = "\"" + assignment.getFeature() + "\"";
        }
        else
        {
          proposal = assignment.getFeature();
        }
        final var entry = getProposalCreator().createProposal(proposal, context,
                (ContentAssistEntry it) -> {
                  if ("STRING_LITERAL".equals(rule.getName()))
                  {
                    it.getEditPositions()
                            .add(new TextRegion(context.getOffset() + 1, proposal.length() - 2));
                    it.setKind(ContentAssistEntry.KIND_TEXT);
                  }
                  else
                  {
                    it.getEditPositions()
                            .add(new TextRegion(context.getOffset(), proposal.length()));
                    it.setKind(ContentAssistEntry.KIND_VALUE);
                  }
                  it.setDescription(rule.getName());
                });
        acceptor.accept(entry, getProposalPriorities().getDefaultPriority(entry));
      }
    }
  }

  @Override
  protected void _createProposals(Keyword keyword, ContentAssistContext context,
          IIdeContentProposalAcceptor acceptor)
  {
    final var method = "complete_" + keyword.getValue();
    if (methodExists(getClass(), method))
    {
      invokeMethod(method, acceptor, context);
    }

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
    final var containingParserRule = GrammarUtil.containingParserRule(reference);
    if (!GrammarUtil.isDatatypeRule(containingParserRule))
    {
      EReference ref;
      if (containingParserRule.isWildcard())
      {
        ref = GrammarUtil.getReference(reference, context.getCurrentModel().eClass());
      }
      else
      {
        ref = GrammarUtil.getReference(reference);
      }
      final var currentModel = context.getCurrentModel();
      if (ref != null && currentModel != null)
      {
        final var scope = getScopeProvider().getScope(currentModel, ref);
        getCrossrefProposalProvider().lookupCrossReference(scope, reference, context, acceptor,
                filter.getFilter(currentModel, ref));
      }
    }
  }

  protected static boolean methodExists(Class< ? > clazz, String methodName)
  {
    final var methods = clazz.getDeclaredMethods();
    for (final Method method : methods)
    {
      if (method.getName().equals(methodName))
      {
        return true;
      }
    }
    return false;
  }
}
