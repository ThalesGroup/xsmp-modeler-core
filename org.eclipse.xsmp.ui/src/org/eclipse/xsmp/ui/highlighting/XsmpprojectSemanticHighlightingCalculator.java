/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ui.highlighting;

import static org.eclipse.xsmp.model.xsmp.XsmpPackage.Literals.ATTRIBUTE__TYPE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.Literals.METADATUM__DOCUMENTATION;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.Literals.NAMED_ELEMENT__NAME;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.ANNOTATION_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.DEPRECATED_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.DOCUMENTATION_TAG_ID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.documentation.TagElement;
import org.eclipse.xsmp.model.xsmp.Metadatum;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.CancelIndicator;

/**
 * Custom semantic highlighting calculator
 *
 * @author daveluy
 */
public class XsmpprojectSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator
{

  @Override
  protected boolean highlightElement(EObject object, IHighlightedPositionAcceptor acceptor,
          CancelIndicator cancelIndicator)
  {
    if (object instanceof final NamedElement elem)
    {
      highlightMetadatum(elem.getMetadatum(), acceptor);
      if (elem.isDeprecated())
      {
        highlightFeature(acceptor, object, NAMED_ELEMENT__NAME, DEPRECATED_ID);
      }
    }
    return true;
  }

  /**
   * highlight a metadatum
   *
   * @param object
   *          the input NamedElement
   * @param acceptor
   *          the acceptor
   */
  private void highlightMetadatum(Metadatum object, IHighlightedPositionAcceptor acceptor)
  {
    // override tag in the description
    final var xsmpcatdoc = object.getXsmpcatdoc();
    if (xsmpcatdoc != null)
    {
      for (final INode node : NodeModelUtils.findNodesForFeature(object, METADATUM__DOCUMENTATION))
      {
        for (final TagElement tag : xsmpcatdoc.tags())
        {
          if (tag.getTagName(xsmpcatdoc) != null)
          {
            acceptor.addPosition(node.getTotalOffset() + tag.getStartPosition(),
                    tag.getTagName(xsmpcatdoc).length(), DOCUMENTATION_TAG_ID);
          }
        }
      }
    }

    // for attribute color the type
    object.getMetadata()
            .forEach(m -> highlightFeature(acceptor, m, ATTRIBUTE__TYPE, ANNOTATION_ID));

  }
}
