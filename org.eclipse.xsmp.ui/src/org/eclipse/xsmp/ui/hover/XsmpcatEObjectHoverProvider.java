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
package org.eclipse.xsmp.ui.hover;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.ui.cdt.SourceHovers;
import org.eclipse.xsmp.xcatalogue.Expression;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xtext.Keyword;

import com.google.inject.Inject;

public class XsmpcatEObjectHoverProvider
        extends org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider
{

  /** Utility mapping keywords and hovertext. */
  @Inject
  private XsmpcatKeywordHovers keywordHovers;

  @Inject
  private SourceHovers sourceHovers;

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getHoverInfoAsHtml(EObject o)
  {
    if (o instanceof Keyword)
    {
      final var hover = keywordHovers.hoverText((Keyword) o);

      if (hover != null)
      {
        return hover;
      }
    }

    final var hover = super.getHoverInfoAsHtml(o);

    if (o instanceof NamedElement)
    {
      final var sourceHover = sourceHovers.hoverText((NamedElement) o);

      if (sourceHover != null)
      {
        return hover + "<br><br>" + sourceHover;
      }
    }
    return hover;
  }

  @Override
  protected boolean hasHover(EObject o)
  {
    return super.hasHover(o) || o instanceof Expression;
  }
}
