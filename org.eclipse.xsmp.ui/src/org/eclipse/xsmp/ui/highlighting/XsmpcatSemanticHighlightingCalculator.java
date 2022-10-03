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
package org.eclipse.xsmp.ui.highlighting;

import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.ANNOTATION_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.BUILT_IN_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.DEPRECATED_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.DOCUMENTATION_TAG_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.FIELD_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.PARAMETER_ID;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.ATTRIBUTE__TYPE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.BUILT_IN_EXPRESSION__NAME;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.ENUMERATION_LITERAL_REFERENCE__VALUE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.METADATUM__DOCUMENTATION;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.NAMED_ELEMENT__NAME;
import static org.eclipse.xtext.ide.editor.syntaxcoloring.HighlightingStyles.DEFAULT_ID;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.documentation.TagElement;
import org.eclipse.xsmp.xcatalogue.Metadatum;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.GrammarUtil;
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
public class XsmpcatSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator
{

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean highlightElement(EObject object, IHighlightedPositionAcceptor acceptor,
          CancelIndicator cancelIndicator)
  {

    // all references with default style
    object.eClass().getEAllReferences().stream().filter(r -> !r.isContainment())
            .forEach(feature -> highlightCrossReference(acceptor, object, feature));
    switch (object.eClass().getClassifierID())
    {
      case XcataloguePackage.METADATUM:
        highlightMetadatum((Metadatum) object, acceptor);
        break;
      case XcataloguePackage.PARAMETER:
        highlightFeature(acceptor, object, NAMED_ELEMENT__NAME, PARAMETER_ID);
        break;
      case XcataloguePackage.FIELD:
      case XcataloguePackage.ASSOCIATION:
      case XcataloguePackage.CONSTANT:
      case XcataloguePackage.ENTRY_POINT:
      case XcataloguePackage.EVENT_SINK:
      case XcataloguePackage.EVENT_SOURCE:
      case XcataloguePackage.REFERENCE:
      case XcataloguePackage.CONTAINER:
      case XcataloguePackage.PROPERTY:
      case XcataloguePackage.ATTRIBUTE_TYPE:
        highlightFeature(acceptor, object, NAMED_ELEMENT__NAME, FIELD_ID);
        break;
      case XcataloguePackage.ENUMERATION_LITERAL_REFERENCE:
        highlightLiteralReference(object, acceptor);
        break;
      case XcataloguePackage.BUILT_IN_CONSTANT:
      case XcataloguePackage.BUILT_IN_FUNCTION:
        highlightFeature(acceptor, object, BUILT_IN_EXPRESSION__NAME, BUILT_IN_ID);
        break;
      default:

        break;
    }
    if (object instanceof NamedElement)
    {
      final var elem = (NamedElement) object;

      highlightFeature(acceptor, object, NAMED_ELEMENT__NAME,
              elem.isDeprecated() ? DEPRECATED_ID : DEFAULT_ID);

    }
    return false;
  }

  protected void highlightCrossReference(IHighlightedPositionAcceptor acceptor, EObject object,
          EReference feature)
  {
    if (XcataloguePackage.Literals.NAMED_ELEMENT.isSuperTypeOf(feature.getEReferenceType()))
    {
      try
      {
        for (final INode node : NodeModelUtils.findNodesForFeature(object, feature))
        {
          final var ref = (NamedElement) resolveCrossReferencedElement(node);
          highlightNode(acceptor, node, ref.isDeprecated() ? DEPRECATED_ID : DEFAULT_ID);
        }
      }
      catch (final Exception e)
      {
      }
    }
  }

  protected EObject resolveCrossReferencedElement(INode node)
  {
    final var referenceOwner = NodeModelUtils.findActualSemanticObjectFor(node);
    if (referenceOwner != null)
    {
      final var crossReference = GrammarUtil.getReference((CrossReference) node.getGrammarElement(),
              referenceOwner.eClass());
      if (!crossReference.isMany())
      {
        return (EObject) referenceOwner.eGet(crossReference);
      }
      final List< ? > listValue = (List< ? >) referenceOwner.eGet(crossReference);
      final var nodesForFeature = NodeModelUtils.findNodesForFeature(referenceOwner,
              crossReference);
      var currentIndex = 0;
      for (final INode nodeForFeature : nodesForFeature)
      {
        if (currentIndex >= listValue.size())
        {
          return null;
        }
        if (nodeForFeature.getTotalOffset() <= node.getTotalOffset()
                && nodeForFeature.getTotalEndOffset() >= node.getTotalEndOffset())
        {
          return (EObject) listValue.get(currentIndex);
        }
        currentIndex++;
      }
    }
    return null;
  }

  /**
   * highlight a literal reference
   *
   * @param object
   *          the input object
   * @param acceptor
   *          the acceptor
   */
  private static void highlightLiteralReference(EObject object,
          IHighlightedPositionAcceptor acceptor)
  {
    for (final INode node : NodeModelUtils.findNodesForFeature(object,
            ENUMERATION_LITERAL_REFERENCE__VALUE))
    {
      if (node != null)
      {
        final var text = node.getText();
        final var index = text.lastIndexOf('.');
        if (index == -1)
        {
          acceptor.addPosition(node.getOffset(), node.getLength(), FIELD_ID);
        }
        else
        {
          acceptor.addPosition(node.getOffset() + index, node.getLength() - index, FIELD_ID);
        }
      }
    }
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
          if (tag.getTagName() != null)
          {
            acceptor.addPosition(node.getTotalOffset() + tag.getStartPosition(),
                    tag.getTagName().length(), DOCUMENTATION_TAG_ID);
          }
        }
      }
    }

    // for attribute color the type
    object.getMetadata()
            .forEach(m -> highlightFeature(acceptor, m, ATTRIBUTE__TYPE, ANNOTATION_ID));

  }
}
