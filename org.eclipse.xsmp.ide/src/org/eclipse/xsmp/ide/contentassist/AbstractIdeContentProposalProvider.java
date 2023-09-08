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

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.PolymorphicDispatcher.ErrorHandler;
import org.eclipse.xtext.util.PolymorphicDispatcher.WarningErrorHandler;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

public class AbstractIdeContentProposalProvider extends IdeContentProposalProvider
{
  @Inject
  protected XsmpcatReferenceFilter filter;

  private final Map<String, PolymorphicDispatcher<Void>> dispatchers;

  private static final Logger log = Logger.getLogger(AbstractIdeContentProposalProvider.class);

  protected AbstractIdeContentProposalProvider()
  {
    dispatchers = new HashMap<>();
  }

  protected static final Set<String> FILTERED_KEYWORDS = Sets.newHashSet("true", "false", "$", "@",
          "struct", "model", "service", "array", "using", "string", "integer", "float", "interface",
          "class", "exception", "public", "private", "protected", "field", "constant", "def",
          "reference", "container", "entrypoint", "native", "primitive", "readOnly", "readWrite",
          "writeOnly", "input", "output", "transient", "abstract", "enum", "event", "attribute",
          "eventsink", "eventsource", "namespace", "association", "property", "nullptr", "default",
          "typename");

  @Override
  protected void _createProposals(Keyword keyword, ContentAssistContext context,
          IIdeContentProposalAcceptor acceptor)
  {
    final var method = "complete_" + keyword.getValue();
    if (methodExists(getClass(), method))
    {
      invokeMethod(method, acceptor, context);
    }
    if (!FILTERED_KEYWORDS.contains(keyword.getValue()))
    {
      super._createProposals(keyword, context, acceptor);
    }
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
