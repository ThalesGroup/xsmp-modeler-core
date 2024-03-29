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
import org.eclipse.xsmp.ide.hover.IKeywordHovers;
import org.eclipse.xsmp.model.xsmp.Expression;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;

import com.google.inject.Inject;

/**
 * returns documentation with support for keywords
 */
public class XsmpEObjectHoverProvider extends DefaultEObjectHoverProvider
{

  /** Utility mapping keywords and hovertext. */
  @Inject
  private IKeywordHovers keywordHovers;

  @Override
  protected String getHoverInfoAsHtml(EObject o)
  {
    if (o instanceof final Keyword kw)
    {
      return keywordHovers.hoverText(kw);
    }

    return super.getHoverInfoAsHtml(o);
  }

  @Override
  protected boolean hasHover(EObject o)
  {
    return super.hasHover(o) || o instanceof Expression;
  }
}
