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
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.util.Tuples;

public class XsmpcatKeywordAtOffsetHelper
{
  public Pair<EObject, ITextRegion> resolveKeywordAt(XtextResource resource, int offset)
  {
    final var parseResult = resource.getParseResult();
    if (parseResult != null)
    {
      var leaf = NodeModelUtils.findLeafNodeAtOffset(parseResult.getRootNode(), offset);
      if (leaf != null && leaf.isHidden() && leaf.getOffset() == offset)
      {
        leaf = NodeModelUtils.findLeafNodeAtOffset(parseResult.getRootNode(), offset - 1);
      }
      if (leaf != null && leaf.getGrammarElement() instanceof Keyword)
      {
        final var keyword = (Keyword) leaf.getGrammarElement();
        return Tuples.create((EObject) keyword,
                (ITextRegion) new TextRegion(leaf.getOffset(), leaf.getLength()));
      }
    }
    return null;
  }
}