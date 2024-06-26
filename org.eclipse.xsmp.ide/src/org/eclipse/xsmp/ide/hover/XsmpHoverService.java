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
package org.eclipse.xsmp.ide.hover;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.hover.HoverContext;
import org.eclipse.xtext.ide.server.hover.HoverService;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpHoverService extends HoverService
{
  @Inject
  private XsmpKeywordAtOffsetHelper keywordHelper;

  @Inject
  private IKeywordHovers keywordHovers;

  @Override
  public String getContents(EObject element)
  {
    if (element instanceof final Keyword kw)
    {
      return keywordHovers.hoverText(kw);
    }

    final var documentation = super.getContents(element);

    if (element instanceof final NamedElement ne)
    {
      final var buffer = new StringBuilder();
      buffer.append(element.eClass().getName());
      final var label = ne.getName();
      if (label != null)
      {
        buffer.append(" <b>").append(label).append("</b><br />");
      }
      buffer.append(documentation);
      return buffer.toString();
    }

    return documentation;
  }

  @Override
  protected HoverContext createContext(Document document, XtextResource resource, int offset)
  {
    // Looking for a keyword
    final var result = keywordHelper.resolveKeywordAt(resource, offset);
    if (result != null)
    {
      return new HoverContext(document, resource, offset, result.getSecond(), result.getFirst());
    }

    return super.createContext(document, resource, offset);
  }

  @Override
  public Hover hover(Document document, XtextResource resource, HoverParams params,
          CancelIndicator cancelIndicator)
  {
    try
    {
      return super.hover(document, resource, params, cancelIndicator);
    }
    catch (final IndexOutOfBoundsException e)
    {
      return null;
    }
  }

}
