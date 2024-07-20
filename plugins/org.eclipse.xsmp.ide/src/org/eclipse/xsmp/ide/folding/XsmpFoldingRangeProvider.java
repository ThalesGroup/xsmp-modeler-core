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
package org.eclipse.xsmp.ide.folding;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.model.xsmp.Metadatum;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.ide.editor.folding.DefaultFoldingRangeProvider;
import org.eclipse.xtext.ide.editor.folding.FoldingRangeKind;
import org.eclipse.xtext.ide.editor.folding.IFoldingRangeAcceptor;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.TextRegion;

public class XsmpFoldingRangeProvider extends DefaultFoldingRangeProvider
{

  /**
   * {@inheritDoc}
   */
  @Override
  protected void acceptObjectFolding(EObject eObject, IFoldingRangeAcceptor foldingRegionAcceptor)
  {

    if (eObject instanceof final Metadatum metadatum)
    {
      computeObjectFolding(metadatum, foldingRegionAcceptor);
    }
    else if (eObject instanceof final NamedElement elem)
    {
      computeObjectFolding(elem, foldingRegionAcceptor);
    }
    else
    {
      super.acceptObjectFolding(eObject, foldingRegionAcceptor);
    }

  }

  /**
   * Find the first comment line to use as significant region
   *
   * @param metadatum
   *          the metadatum
   * @param foldingRegionAcceptor
   *          the acceptor
   * @param initiallyFolded
   *          true if initially folded
   */
  protected void computeObjectFolding(Metadatum metadatum,
          IFoldingRangeAcceptor foldingRegionAcceptor)
  {

    for (final INode node : NodeModelUtils.findNodesForFeature(metadatum,
            XsmpPackage.Literals.METADATUM__DOCUMENTATION))
    {
      final var offset = node.getOffset();
      final var length = node.getLength();
      final var matcher = getTextPatternInComment().matcher(node.getText());
      if (matcher.find())
      {
        final var significant = new TextRegion(offset + matcher.start(), 0);

        foldingRegionAcceptor.accept(offset, length, significant);
      }
      else
      {
        foldingRegionAcceptor.accept(offset, length, FoldingRangeKind.COMMENT);
      }

    }

  }

  /**
   * Do not include the documentation
   *
   * @param element
   *          the element to fold
   * @param foldingRegionAcceptor
   *          the acceptor
   * @param initiallyFolded
   *          true if initially folded
   */
  protected void computeObjectFolding(NamedElement element,
          IFoldingRangeAcceptor foldingRegionAcceptor)
  {

    final var region = getLocationInFileProvider().getFullTextRegion(element);
    if (region != null)
    {
      final var significant = getLocationInFileProvider().getSignificantTextRegion(element,
              XsmpPackage.Literals.NAMED_ELEMENT__NAME, -1);
      if (significant == null)
      {
        throw new NullPointerException("significant region may not be null");
      }
      final var offset = significant.getOffset();
      final var length = region.getLength() - offset + region.getOffset();
      foldingRegionAcceptor.accept(offset, length, significant);

    }

  }
}
