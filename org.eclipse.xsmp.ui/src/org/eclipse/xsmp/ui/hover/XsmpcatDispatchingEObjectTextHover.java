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
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.xsmp.xcatalogue.BuiltInExpression;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hover.DispatchingEObjectTextHover;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.util.Pair;

import com.google.inject.Inject;

public class XsmpcatDispatchingEObjectTextHover extends DispatchingEObjectTextHover
{

  @Inject
  private IEObjectHoverProvider hoverProvider;

  @Inject
  private XsmpcatKeywordAtOffsetHelper keywordAtOffsetHelper;

  /**
   * {@inheritDoc}
   */
  @Override
  public IInformationControlCreator getHoverControlCreator()
  {
    return lastCreatorProvider == null ? super.getHoverControlCreator()
            : lastCreatorProvider.getHoverControlCreator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getHoverInfo(EObject first, ITextViewer textViewer, IRegion hoverRegion)
  {
    if (first instanceof Keyword || first instanceof BuiltInExpression)
    {
      lastCreatorProvider = hoverProvider.getHoverInfo(first, textViewer, hoverRegion);
      return lastCreatorProvider == null ? null : lastCreatorProvider.getInfo();
    }
    lastCreatorProvider = null;
    return super.getHoverInfo(first, textViewer, hoverRegion);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Pair<EObject, IRegion> getXtextElementAt(XtextResource resource, final int offset)
  {
    var result = super.getXtextElementAt(resource, offset);
    if (result == null)
    {
      result = keywordAtOffsetHelper.resolveKeywordAt(resource, offset);
    }
    return result;
  }
}