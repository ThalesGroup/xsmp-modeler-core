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
package org.eclipse.xsmp.formatting2;

import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.ASSOCIATION__TYPE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.ATTRIBUTE_TYPE__TYPE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.BINARY_OPERATION__FEATURE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.BUILT_IN_EXPRESSION__NAME;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.CLASS__BASE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.COMPONENT__BASE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.COMPONENT__INTERFACE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.CONSTANT__TYPE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.CONTAINER__DEFAULT_COMPONENT;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.CONTAINER__TYPE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.EVENT_SINK__TYPE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.EVENT_TYPE__EVENT_ARGS;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.FIELD__TYPE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.FLOAT__PRIMITIVE_TYPE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.FLOAT__RANGE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.INTEGER__PRIMITIVE_TYPE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.INTERFACE__BASE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.METADATUM__DOCUMENTATION;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.NAMED_ELEMENT_WITH_MULTIPLICITY__OPTIONAL;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.NAMED_ELEMENT__NAME;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.OPERATION__RAISED_EXCEPTION;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.PROPERTY__GET_RAISES;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.PROPERTY__SET_RAISES;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.PROPERTY__TYPE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.REFERENCE__INTERFACE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.UNARY_OPERATION__FEATURE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.VALUE_REFERENCE__TYPE;
import static org.eclipse.xsmp.xcatalogue.XcataloguePackage.Literals.VISIBILITY_ELEMENT__MODIFIERS;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.services.XsmpcatGrammarAccess;
import org.eclipse.xsmp.xcatalogue.Array;
import org.eclipse.xsmp.xcatalogue.Association;
import org.eclipse.xsmp.xcatalogue.Attribute;
import org.eclipse.xsmp.xcatalogue.AttributeType;
import org.eclipse.xsmp.xcatalogue.BinaryOperation;
import org.eclipse.xsmp.xcatalogue.BuiltInConstant;
import org.eclipse.xsmp.xcatalogue.BuiltInFunction;
import org.eclipse.xsmp.xcatalogue.Catalogue;
import org.eclipse.xsmp.xcatalogue.Class;
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Container;
import org.eclipse.xsmp.xcatalogue.DesignatedInitializer;
import org.eclipse.xsmp.xcatalogue.EntryPoint;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.EventSink;
import org.eclipse.xsmp.xcatalogue.EventSource;
import org.eclipse.xsmp.xcatalogue.EventType;
import org.eclipse.xsmp.xcatalogue.Exception;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Float;
import org.eclipse.xsmp.xcatalogue.ImportDeclaration;
import org.eclipse.xsmp.xcatalogue.ImportSection;
import org.eclipse.xsmp.xcatalogue.Integer;
import org.eclipse.xsmp.xcatalogue.Interface;
import org.eclipse.xsmp.xcatalogue.Metadatum;
import org.eclipse.xsmp.xcatalogue.Model;
import org.eclipse.xsmp.xcatalogue.Multiplicity;
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers;
import org.eclipse.xsmp.xcatalogue.Namespace;
import org.eclipse.xsmp.xcatalogue.NativeType;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.ParenthesizedExpression;
import org.eclipse.xsmp.xcatalogue.PrimitiveType;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.Reference;
import org.eclipse.xsmp.xcatalogue.Service;
import org.eclipse.xsmp.xcatalogue.String;
import org.eclipse.xsmp.xcatalogue.Structure;
import org.eclipse.xsmp.xcatalogue.UnaryOperation;
import org.eclipse.xsmp.xcatalogue.ValueReference;
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

/**
 * Code formatter class for Xsmpcat files
 *
 * @author daveluy
 */
@SuppressWarnings("restriction")
public class XsmpcatFormatter extends AbstractJavaFormatter
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
  private XsmpcatGrammarAccess ga;

  public ITextReplacer createDocumentationReplacer(ISemanticRegion description)
  {
    return new DocumentationReplacer(description);
  }

  protected void format(Catalogue catalogue, IFormattableDocument doc)
  {
    format(catalogue.getMetadatum(), doc, false);
    final var parentRegion = regionFor(catalogue);
    final var kw = parentRegion.keyword(ga.getCatalogueAccess().getCatalogueKeyword_1());
    doc.prepend(kw, this::newLine);
    doc.append(kw, this::oneSpace);

    final var name = parentRegion.feature(NAMED_ELEMENT__NAME);
    doc.prepend(name, this::oneSpace);
    doc.append(name, it -> {
      it.setNewLines(3, 3, 3);
      it.lowPriority();
    });

    if (catalogue.getImportSection() != null)
    {
      doc.format(catalogue.getImportSection());
    }

    for (final EObject eObject : catalogue.getMember())
    {
      doc.format(eObject);
      doc.append(eObject, it -> {
        it.setNewLines(2, 3, 3);
        it.lowPriority();
      });
    }
  }

  protected void format(ImportSection parent, IFormattableDocument doc)
  {
    doc.prepend(parent, it -> {
      it.setNewLines(2, 2, 2);
      it.highPriority();
    });

    parent.getImportDeclarations().forEach(doc::format);

    doc.append(parent, it -> {
      it.setNewLines(3, 3, 3);
      it.highPriority();
    });
  }

  protected void format(ImportDeclaration parent, IFormattableDocument doc)
  {
    final var importDeclarationAccess = ga.getImportDeclarationAccess();
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(importDeclarationAccess.getImportKeyword_0()), this::oneSpace);
    doc.surround(parentRegion.keyword(importDeclarationAccess.getNamespaceKeyword_1_1_0()),
            this::oneSpace);
    doc.append(parent, this::newLine);
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

  protected void format(Namespace parent, IFormattableDocument doc)
  {

    format(parent.getMetadatum(), doc, false);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getNamespaceKeyword_3_0_1()),
            this::oneSpace);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
    doc.append(parentRegion.feature(NAMED_ELEMENT__NAME), this::newLine);

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

  protected void format(Structure parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getStructKeyword_3_1_2()),
            this::oneSpace);
    doc.append(parentRegion.feature(NAMED_ELEMENT__NAME), this::newLine);
    formatBody(parent, doc);
  }

  protected void format(Class parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getClassKeyword_3_2_2()),
            this::oneSpace);

    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getExtendsKeyword_3_2_4_0()),
            this::oneSpace);
    doc.prepend(parentRegion.feature(CLASS__BASE), this::oneSpace);

    formatBody(parent, doc);
  }

  protected void format(Exception parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getExceptionKeyword_3_3_2()),
            this::oneSpace);

    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getExtendsKeyword_3_3_4_0()),
            this::oneSpace);
    doc.prepend(parentRegion.feature(CLASS__BASE), this::oneSpace);

    formatBody(parent, doc);
  }

  protected void format(Interface parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getInterfaceKeyword_3_4_2()),
            this::oneSpace);
    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getExtendsKeyword_3_4_4_0()),
            this::oneSpace);

    formatList(parent, INTERFACE__BASE, doc,
            ga.getNamespaceMemberAccess().getCommaKeyword_3_4_4_2_0());

    formatBody(parent, doc);
  }

  protected void format(Model parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getModelKeyword_3_5_2()),
            this::oneSpace);

    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getExtendsKeyword_3_5_4_0()),
            this::oneSpace);
    doc.prepend(parentRegion.feature(COMPONENT__BASE), this::oneSpace);

    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getImplementsKeyword_3_5_5_0()),
            this::oneSpace);

    formatList(parent, COMPONENT__INTERFACE, doc,
            ga.getNamespaceMemberAccess().getCommaKeyword_3_5_5_2_0());

    formatBody(parent, doc);
  }

  protected void format(Service parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getServiceKeyword_3_6_2()),
            this::oneSpace);

    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getExtendsKeyword_3_6_4_0()),
            this::oneSpace);
    doc.prepend(parentRegion.feature(COMPONENT__BASE), this::oneSpace);

    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getImplementsKeyword_3_6_5_0()),
            this::oneSpace);

    formatList(parent, COMPONENT__INTERFACE, doc,
            ga.getNamespaceMemberAccess().getCommaKeyword_3_6_5_2_0());

    formatBody(parent, doc);
  }

  protected void format(AttributeType parent, IFormattableDocument doc)
  {

    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);

    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getAttributeKeyword_3_15_2()),
            this::oneSpace);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);

    doc.surround(parentRegion.feature(ATTRIBUTE_TYPE__TYPE), this::oneSpace);

    doc.surround(
            parentRegion.keyword(ga.getNamespaceMemberAccess().getEqualsSignKeyword_3_15_5_0()),
            this::oneSpace);
    doc.format(parent.getDefault());
  }

  protected void format(Enumeration parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getEnumKeyword_3_16_2()),
            this::oneSpace);

    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
    doc.append(parentRegion.feature(NAMED_ELEMENT__NAME), this::newLine);

    final var open = parentRegion
            .keyword(ga.getNamespaceMemberAccess().getLeftCurlyBracketKeyword_3_16_4());
    final var close = parentRegion
            .keyword(ga.getNamespaceMemberAccess().getRightCurlyBracketKeyword_3_16_6());

    doc.append(open, this::newLine);

    doc.prepend(close, this::newLine);
    doc.append(close, it -> {
      it.setNewLines(2, 3, 3);
      it.highPriority();
    });

    doc.interior(open, close, this::indent);

    for (final var eObject : parent.getLiteral())
    {
      doc.prepend(eObject, this::newLine);
      doc.append(eObject, this::noSpace);
      doc.format(eObject);
    }
  }

  protected void format(EnumerationLiteral parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    final var parentRegion = regionFor(parent);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::newLine);
    doc.surround(parentRegion.keyword(ga.getEnumerationLiteralAccess().getEqualsSignKeyword_2_0()),
            this::oneSpace);
    doc.format(parent.getValue());
  }

  protected void format(Field parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getFieldDeclarationAccess().getFieldKeyword_1()),
            this::oneSpace);
    doc.append(parentRegion.feature(FIELD__TYPE), this::oneSpace);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
    doc.surround(parentRegion.keyword(ga.getFieldDeclarationAccess().getEqualsSignKeyword_4_0()),
            this::oneSpace);
    doc.format(parent.getDefault());
  }

  protected void format(Constant parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getConstantDeclarationAccess().getConstantKeyword_1()),
            this::oneSpace);
    doc.surround(parentRegion.feature(CONSTANT__TYPE), this::oneSpace);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
    doc.surround(parentRegion.keyword(ga.getConstantDeclarationAccess().getEqualsSignKeyword_4_0()),
            this::oneSpace);
    doc.format(parent.getValue());
  }

  protected void format(Association parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getAssociationDeclarationAccess().getAssociationKeyword_1()),
            this::oneSpace);
    doc.surround(parentRegion.feature(ASSOCIATION__TYPE), this::oneSpace);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
    doc.surround(
            parentRegion.keyword(ga.getAssociationDeclarationAccess().getEqualsSignKeyword_4_0()),
            this::oneSpace);
    doc.format(parent.getDefault());
  }

  protected void format(Property parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getPropertyDeclarationAccess().getPropertyKeyword_1()),
            this::oneSpace);
    doc.surround(parentRegion.feature(PROPERTY__TYPE), this::oneSpace);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);

    doc.surround(parentRegion.keyword(ga.getPropertyDeclarationAccess().getGetKeyword_4_0()),
            this::oneSpace);
    doc.surround(parentRegion.keyword(ga.getPropertyDeclarationAccess().getThrowsKeyword_4_1()),
            this::oneSpace);
    doc.surround(parentRegion.keyword(ga.getPropertyDeclarationAccess().getSetKeyword_5_0()),
            this::oneSpace);
    doc.surround(parentRegion.keyword(ga.getPropertyDeclarationAccess().getThrowsKeyword_5_1()),
            this::oneSpace);

    formatList(parent, PROPERTY__GET_RAISES, doc,
            ga.getPropertyDeclarationAccess().getCommaKeyword_4_3_0());
    formatList(parent, PROPERTY__SET_RAISES, doc,
            ga.getPropertyDeclarationAccess().getCommaKeyword_5_3_0());

    doc.surround(
            parentRegion.keyword(
                    ga.getPropertyDeclarationAccess().getHyphenMinusGreaterThanSignKeyword_6_0()),
            this::oneSpace);
  }

  protected void format(Container parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getContainerDeclarationAccess().getContainerKeyword_0()),
            this::oneSpace);
    doc.prepend(parentRegion.feature(CONTAINER__TYPE), this::oneSpace);

    doc.prepend(parent.getMultiplicity(), this::noSpace);
    doc.format(parent.getMultiplicity());
    doc.prepend(parentRegion.feature(NAMED_ELEMENT_WITH_MULTIPLICITY__OPTIONAL), this::noSpace);

    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
    doc.surround(
            parentRegion.keyword(ga.getContainerDeclarationAccess().getEqualsSignKeyword_4_0()),
            this::oneSpace);
    doc.prepend(parentRegion.feature(CONTAINER__DEFAULT_COMPONENT), this::oneSpace);
  }

  protected void format(Reference parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getReferenceDeclarationAccess().getReferenceKeyword_0()),
            this::oneSpace);

    doc.prepend(parent.getMultiplicity(), this::noSpace);
    doc.format(parent.getMultiplicity());
    doc.prepend(parentRegion.feature(NAMED_ELEMENT_WITH_MULTIPLICITY__OPTIONAL), this::noSpace);

    doc.prepend(parentRegion.feature(REFERENCE__INTERFACE), this::oneSpace);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
  }

  protected void format(EntryPoint parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getEntryPointDeclarationAccess().getEntrypointKeyword_0()),
            this::oneSpace);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);

    final var open = parentRegion
            .keyword(ga.getEntryPointDeclarationAccess().getLeftCurlyBracketKeyword_2_0());
    final var close = parentRegion
            .keyword(ga.getEntryPointDeclarationAccess().getRightCurlyBracketKeyword_2_2());
    doc.surround(open, this::newLine);
    doc.prepend(close, this::newLine);

    doc.interior(open, close, this::indent);

    final var allRegions = allRegionsFor(parent);

    allRegions.keywords(ga.getEntryPointDeclarationAccess().getOutKeyword_2_1_1_0(),
            ga.getEntryPointDeclarationAccess().getInKeyword_2_1_0_0()).forEach(kw -> {
              doc.prepend(kw, this::newLine);
              doc.append(kw, this::oneSpace);
            });

  }

  protected void format(EventSink parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getEventSinkDeclarationAccess().getEventsinkKeyword_0()),
            this::oneSpace);
    doc.surround(parentRegion.feature(EVENT_SINK__TYPE), this::oneSpace);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
  }

  protected void format(EventSource parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getEventSourceDeclarationAccess().getEventsourceKeyword_0()),
            this::oneSpace);
    doc.surround(parentRegion.feature(EVENT_TYPE__EVENT_ARGS), this::oneSpace);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
  }

  protected void format(Operation parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getOperationDeclarationAccess().getDefKeyword_1()),
            this::oneSpace);

    doc.surround(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
    final var open = parentRegion
            .keyword(ga.getOperationDeclarationAccess().getLeftParenthesisKeyword_4());
    final var close = parentRegion
            .keyword(ga.getOperationDeclarationAccess().getRightParenthesisKeyword_6());
    doc.append(open, this::noSpace);
    doc.prepend(open, this::oneSpace);
    doc.prepend(close, this::noSpace);
    doc.interior(open, close, this::indent);

    formatCollection(parent.getParameter(), open, close, doc,
            ga.getOperationDeclarationAccess().getCommaKeyword_5_1_0());

    doc.surround(parentRegion.keyword(ga.getOperationDeclarationAccess().getThrowsKeyword_7_0()),
            this::oneSpace);
    formatList(parent, OPERATION__RAISED_EXCEPTION, doc,
            ga.getOperationDeclarationAccess().getCommaKeyword_7_2_0());

    if (parent.getReturnParameter() != null)
    {
      doc.format(parent.getReturnParameter());
    }

  }

  protected void format(Parameter parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, true);
    final var parentRegion = regionFor(parent);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);

    doc.surround(parentRegion.keyword(ga.getParameterAccess().getEqualsSignKeyword_3_0()),
            this::oneSpace);

    if (parent.getDefault() != null)
    {
      doc.format(parent.getDefault());
    }
  }

  protected void format(PrimitiveType parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);

    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getPrimitiveKeyword_3_13_2()),
            this::oneSpace);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
  }

  protected void format(NativeType parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);

    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getNativeKeyword_3_14_2()),
            this::oneSpace);
    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
  }

  protected void format(Array parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getUsingKeyword_3_8_2()),
            this::oneSpace);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getArrayKeyword_3_7_2_0_0()),
            this::oneSpace);

    doc.surround(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getEqualsSignKeyword_3_7_2_2()),
            this::oneSpace);

    doc.surround(
            parentRegion
                    .keyword(ga.getNamespaceMemberAccess().getLeftSquareBracketKeyword_3_7_2_4()),
            this::noSpace);
    doc.format(parent.getSize());

    doc.prepend(
            parentRegion
                    .keyword(ga.getNamespaceMemberAccess().getRightSquareBracketKeyword_3_7_4()),
            this::noSpace);
  }

  protected void format(EventType parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getEventKeyword_3_11_2()),
            this::oneSpace);

    doc.surround(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getExtendsKeyword_3_11_4_0()),
            this::oneSpace);

    doc.prepend(parentRegion.feature(EVENT_TYPE__EVENT_ARGS), this::oneSpace);
  }

  protected void format(Float parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getFloatKeyword_3_10_2()),
            this::oneSpace);

    doc.surround(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getExtendsKeyword_3_10_4_0()),
            this::oneSpace);

    doc.prepend(parentRegion.feature(FLOAT__PRIMITIVE_TYPE), this::oneSpace);

    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getInKeyword_3_10_5_0()),
            this::oneSpace);

    if (parent.getMinimum() != null)
    {
      doc.surround(parent.getMinimum(), this::oneSpace);
    }

    doc.surround(parentRegion.feature(FLOAT__RANGE), this::oneSpace);

    if (parent.getMaximum() != null)
    {
      doc.surround(parent.getMaximum(), this::oneSpace);
    }
  }

  protected void format(Integer parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getIntegerKeyword_3_9_2()),
            this::oneSpace);

    doc.surround(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getExtendsKeyword_3_9_4_0()),
            this::oneSpace);

    doc.prepend(parentRegion.feature(INTEGER__PRIMITIVE_TYPE), this::oneSpace);

    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getInKeyword_3_9_5_0()),
            this::oneSpace);

    if (parent.getMinimum() != null)
    {
      doc.surround(parent.getMinimum(), this::oneSpace);
    }

    doc.surround(
            parentRegion.keyword(
                    ga.getNamespaceMemberAccess().getFullStopFullStopFullStopKeyword_3_9_5_2()),
            this::oneSpace);

    if (parent.getMaximum() != null)
    {
      doc.surround(parent.getMaximum(), this::oneSpace);
    }
  }

  protected void format(ValueReference parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getUsingKeyword_3_8_2()),
            this::oneSpace);

    doc.surround(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);
    doc.surround(parentRegion.keyword(ga.getNamespaceMemberAccess().getEqualsSignKeyword_3_8_4()),
            this::oneSpace);
    final var r = parentRegion.feature(VALUE_REFERENCE__TYPE);
    doc.prepend(r, this::oneSpace);
    doc.append(r, this::noSpace);
  }

  protected void format(String parent, IFormattableDocument doc)
  {
    format(parent.getMetadatum(), doc, false);
    formatModifiers(parent, doc);
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getNamespaceMemberAccess().getStringKeyword_3_12_2()),
            this::oneSpace);

    doc.prepend(parentRegion.feature(NAMED_ELEMENT__NAME), this::oneSpace);

    doc.append(
            parentRegion
                    .keyword(ga.getNamespaceMemberAccess().getLeftSquareBracketKeyword_3_12_4_0()),
            this::noSpace);
    doc.format(parent.getLength());
    doc.prepend(
            parentRegion
                    .keyword(ga.getNamespaceMemberAccess().getRightSquareBracketKeyword_3_12_4_2()),
            this::noSpace);
  }

  protected void format(Multiplicity parent, IFormattableDocument doc)
  {
    final var parentRegion = regionFor(parent);
    doc.append(parentRegion.keyword(ga.getMultiplicityAccess().getLeftSquareBracketKeyword_1()),
            this::noSpace);

    doc.prepend(parentRegion.keyword(ga.getMultiplicityAccess().getRightSquareBracketKeyword_3()),
            this::noSpace);

    doc.surround(
            parentRegion.keyword(
                    ga.getMultiplicityAccess().getFullStopFullStopFullStopKeyword_2_0_1_0()),
            this::oneSpace);
    doc.format(parent.getLower());
    doc.format(parent.getUpper());
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

  protected void formatModifiers(EObject parent, IFormattableDocument doc)
  {
    allRegionsFor(parent).features(VISIBILITY_ELEMENT__MODIFIERS)
            .forEach(m -> doc.append(m, this::oneSpace));
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

}
