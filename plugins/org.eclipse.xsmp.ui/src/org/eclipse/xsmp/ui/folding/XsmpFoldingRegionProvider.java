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
package org.eclipse.xsmp.ui.folding;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.model.xsmp.Metadatum;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.folding.DefaultFoldingRegionProvider;
import org.eclipse.xtext.ui.editor.folding.IFoldingRegionAcceptor;
import org.eclipse.xtext.ui.editor.folding.IFoldingRegionAcceptorExtension;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.TextRegion;

public class XsmpFoldingRegionProvider extends DefaultFoldingRegionProvider
{
  /**
   * {@inheritDoc}
   */
  @Override
  protected void computeObjectFolding(EObject eObject,
          IFoldingRegionAcceptor<ITextRegion> foldingRegionAcceptor, boolean initiallyFolded)
  {

    if (eObject instanceof final Metadatum metadatum)
    {
      computeObjectFolding(metadatum, foldingRegionAcceptor, initiallyFolded);
    }
    else if (eObject instanceof final NamedElement elem)
    {
      computeObjectFolding(elem, foldingRegionAcceptor, initiallyFolded);
    }
    else
    {
      super.computeObjectFolding(eObject, foldingRegionAcceptor, initiallyFolded);
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
          IFoldingRegionAcceptor<ITextRegion> foldingRegionAcceptor, boolean initiallyFolded)
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
        ((IFoldingRegionAcceptorExtension<ITextRegion>) foldingRegionAcceptor).accept(offset,
                length, initiallyFolded, significant);
      }
      else
      {
        ((IFoldingRegionAcceptorExtension<ITextRegion>) foldingRegionAcceptor).accept(offset,
                length, initiallyFolded);
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
          IFoldingRegionAcceptor<ITextRegion> foldingRegionAcceptor, boolean initiallyFolded)
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
      ((IFoldingRegionAcceptorExtension<ITextRegion>) foldingRegionAcceptor).accept(offset, length,
              initiallyFolded, significant);

    }

  }

}
