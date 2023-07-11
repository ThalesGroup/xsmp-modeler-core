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
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.hover.HoverContext;
import org.eclipse.xtext.ide.server.hover.HoverService;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

public class XsmpcatHoverService extends HoverService
{
  @Inject
  private XsmpcatKeywordHovers keywordHovers;

  @Inject
  private XsmpcatKeywordAtOffsetHelper keywordHelper;

  @Override
  public String getContents(EObject element)
  {
    if (element instanceof Keyword)
    {
      return keywordHovers.hoverText((Keyword) element);
    }
    return super.getContents(element);
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

}
