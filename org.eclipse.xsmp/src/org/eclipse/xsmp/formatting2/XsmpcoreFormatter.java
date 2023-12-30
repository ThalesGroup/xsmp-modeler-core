/*******************************************************************************
* Copyright (C) 2020-2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.formatting2;

import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.BINARY_OPERATION__FEATURE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.BUILT_IN_EXPRESSION__NAME;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.METADATUM__DOCUMENTATION;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.NAMED_ELEMENT__NAME;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.UNARY_OPERATION__FEATURE;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.services.XsmpcoreGrammarAccess;
import org.eclipse.xsmp.xcatalogue.Attribute;
import org.eclipse.xsmp.xcatalogue.BinaryOperation;
import org.eclipse.xsmp.xcatalogue.BuiltInConstant;
import org.eclipse.xsmp.xcatalogue.BuiltInFunction;
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.DesignatedInitializer;
import org.eclipse.xsmp.xcatalogue.Metadatum;
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers;
import org.eclipse.xsmp.xcatalogue.Namespace;
import org.eclipse.xsmp.xcatalogue.ParenthesizedExpression;
import org.eclipse.xsmp.xcatalogue.UnaryOperation;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.formatting2.AbstractJavaFormatter;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.ITextReplacer;
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;
import org.eclipse.xtext.formatting2.regionaccess.internal.TextSegment;
import org.eclipse.xtext.xbase.formatting2.IndentOnceAutowrapFormatter;
import org.eclipse.xtext.xbase.formatting2.ObjectEntry;
import org.eclipse.xtext.xbase.formatting2.SeparatorRegions;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class XsmpcoreFormatter extends AbstractJavaFormatter
{
  public static class IndentationInformation implements IIndentationInformation
  {
    @Override
    public java.lang.String getIndentString()
    {
      return "    ";
    }
  }

  @Inject
  private XsmpcoreGrammarAccess ga;

  public ITextReplacer createDocumentationReplacer(ISemanticRegion description)
  {
    return new DocumentationReplacer(description);
  }

  protected void format(Namespace parent, IFormattableDocument doc)
  {

    format(parent.getMetadatum(), doc, false);
    final var parentRegion = regionFor(parent);

    doc.append(parentRegion.keyword("namespace"), this::oneSpace);

    doc.surround(parentRegion.keyword(ga.getNestedNamespaceAccess().getColonColonKeyword_1()),
            this::noSpace);

    formatBody(parent, doc);
  }

  protected void formatBody(Namespace parent, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(parent);
    final var open = parentRegion.keyword("{");
    final var close = parentRegion.keyword("}");
    doc.surround(open, this::newLine);
    doc.prepend(close, this::newLine);
    doc.interior(open, close, this::indent);
    for (final EObject eObject : parent.getMember())
    {
      doc.format(eObject);
      if (eObject != Iterables.getLast(parent.getMember()))
      {
        doc.append(eObject, it -> {
          it.setNewLines(2, 3, 3);
          it.lowPriority();
        });
      }
    }
  }

  protected void format(Metadatum parent, IFormattableDocument doc, boolean inLine)
  {
    final var parentRegion = regionFor(parent);
    final var description = parentRegion.feature(METADATUM__DOCUMENTATION);
    if (inLine)
    {
      doc.append(description, this::oneSpace);
    }
    else
    {
      doc.append(description, this::newLine);
    }
    if (description != null)
    {
      doc.addReplacer(createDocumentationReplacer(description));
    }

    for (final Attribute t : parent.getMetadata())
    {
      final var attrRegion = regionFor(t);
      doc.append(attrRegion.keyword(ga.getAttributeDeclarationAccess().getCommercialAtKeyword_0()),
              this::noSpace);

      doc.prepend(attrRegion.feature(NAMED_ELEMENT__NAME), this::noSpace);
      final var open = attrRegion.keyword(ga.getAttributeAccess().getLeftParenthesisKeyword_1_0());
      doc.surround(open, this::noSpace);
      final var close = attrRegion
              .keyword(ga.getAttributeAccess().getRightParenthesisKeyword_1_2());

      doc.prepend(close, this::noSpace);
      doc.format(t.getValue());

      if (inLine)
      {
        doc.append(t, this::oneSpace);
      }
      else
      {
        doc.append(t, this::newLine);
      }
    }

  }

  protected void formatBody(NamedElementWithMembers parent, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(parent);
    final var open = parentRegion.keyword("{");
    final var close = parentRegion.keyword("}");
    doc.surround(open, this::newLine);
    doc.prepend(close, this::newLine);

    doc.interior(open, close, this::indent);
    for (final EObject eObject : parent.getMember())
    {
      doc.format(eObject);

      if (eObject != Iterables.getLast(parent.getMember()))
      {
        doc.append(eObject, it -> {
          it.setNewLines(1, 2, 3);
          it.lowPriority();
        });
      }
    }

  }

  protected void format(CollectionLiteral parent, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(parent);
    final var open = parentRegion
            .keyword(ga.getCollectionLiteralAccess().getLeftCurlyBracketKeyword_1());
    final var close = parentRegion
            .keyword(ga.getCollectionLiteralAccess().getRightCurlyBracketKeyword_4());
    formatCollection(parent.getElements(), open, close, doc,
            ga.getCollectionLiteralAccess().getCommaKeyword_3_0());

  }

  protected void format(UnaryOperation parent, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.feature(UNARY_OPERATION__FEATURE), this::noSpace);
    doc.format(parent.getOperand());
  }

  protected void format(BuiltInConstant parent, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(parent);
    doc.prepend(parentRegion.feature(BUILT_IN_EXPRESSION__NAME), this::noSpace);
  }

  protected void format(BuiltInFunction parent, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(parent);
    doc.surround(parentRegion.feature(BUILT_IN_EXPRESSION__NAME), this::noSpace);

    final var open = parentRegion
            .keyword(ga.getBuiltInFunctionAccess().getLeftParenthesisKeyword_3());
    final var close = parentRegion
            .keyword(ga.getBuiltInFunctionAccess().getRightParenthesisKeyword_5());
    doc.append(open, this::noSpace);

    doc.prepend(close, this::noSpace);
    doc.interior(open, close, this::indent);

    formatCollection(parent.getParameter(), open, close, doc,
            ga.getBuiltInFunctionAccess().getCommaKeyword_4_1_0());
  }

  protected void format(BinaryOperation parent, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(parent);
    doc.surround(parentRegion.feature(BINARY_OPERATION__FEATURE), this::oneSpace);

    doc.format(parent.getLeftOperand());

    doc.format(parent.getRightOperand());
  }

  protected void format(ParenthesizedExpression parent, IFormattableDocument doc)
  {
    final var region = regionFor(parent);
    doc.append(region.keyword(ga.getParenthesizedExpressionAccess().getLeftParenthesisKeyword_1()),
            this::noSpace);
    doc.prepend(
            region.keyword(ga.getParenthesizedExpressionAccess().getRightParenthesisKeyword_3()),
            this::noSpace);
    doc.format(parent.getExpr());
  }

  protected void format(DesignatedInitializer parent, IFormattableDocument doc)
  {
    final var region = regionFor(parent);
    doc.append(region.keyword(ga.getDesignatedInitializerAccess().getFullStopKeyword_1()),
            this::noSpace);
    doc.surround(region.keyword(ga.getDesignatedInitializerAccess().getEqualsSignKeyword_3_0()),
            this::oneSpace);
    doc.format(parent.getExpr());
  }

  protected void formatCollection(Collection< ? extends EObject> elements, ISemanticRegion open,
          ISemanticRegion close, IFormattableDocument doc, Keyword separator)
  {
    if (close == null || open == null)
    {
      // broken, do nothing
    }
    else if (elements.isEmpty())
    {
      doc.append(open, this::noSpace);
    }
    else if (close.getPreviousHiddenRegion().isMultiline())
    {
      doc.append(open, this::newLine);

      for (final EObject elem : elements)
      {
        doc.format(elem);

        doc.prepend(immediatelyFollowing(elem).keyword(separator), this::noSpace);
        doc.append(immediatelyFollowing(elem).keyword(separator), this::newLine);
      }

      doc.append(Iterables.getLast(elements), this::newLine);
      doc.interior(open, close, this::indent);
    }
    else
    {
      final var indent = new IndentOnceAutowrapFormatter(close.getPreviousHiddenRegion());
      final var region = new TextSegment(getTextRegionAccess(), open.getEndOffset(),
              close.getOffset() - open.getEndOffset());
      final var items = new SeparatorRegions<EObject, ISemanticRegion>(region);

      for (final EObject ele : elements)
      {
        items.appendWithTrailingSeparator(ele, immediatelyFollowing(ele).keyword(separator));
      }

      for (final ObjectEntry<EObject, ISemanticRegion> ele : items)
      {
        ISemanticRegion sep = null;

        if (ele.getLeadingSeparator() != null)
        {
          sep = ele.getLeadingSeparator().getSeparator();
        }

        if (sep == null)
        {
          doc.append(open, it -> {
            it.noSpace();
            it.autowrap(ele.getRegion().getLength());
            it.setOnAutowrap(indent);
          });
        }
        else
        {
          doc.append(sep, it -> {
            it.oneSpace();
            it.autowrap(ele.getRegion().getLength());
            it.setOnAutowrap(indent);
          });
        }

        doc.prepend(sep, this::noSpace);
        doc.format(ele.getObject());
      }
      doc.prepend(close, it -> {
        it.noSpace();
        it.lowPriority();
      });
    }
  }

  protected void formatList(EObject isr, EReference eref, IFormattableDocument doc,
          Keyword separator)
  {
    for (final var i : allRegionsFor(isr).features(eref))
    {

      final var kw = i.immediatelyFollowing().keyword(separator);
      doc.prepend(kw, this::noSpace);
      doc.append(kw, this::oneSpace);
    }
  }
}
