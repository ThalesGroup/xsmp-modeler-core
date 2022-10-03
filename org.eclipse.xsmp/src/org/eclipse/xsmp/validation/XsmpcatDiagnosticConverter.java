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
package org.eclipse.xsmp.validation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xsmp.xcatalogue.Component;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.DiagnosticConverterImpl;

import com.google.inject.Singleton;

@Singleton
public class XsmpcatDiagnosticConverter extends DiagnosticConverterImpl
{

  /**
   * {@inheritDoc}
   */
  @Override
  protected IssueLocation getLocationData(EObject obj, EStructuralFeature structuralFeature,
          int index)
  {

    if (structuralFeature == XcataloguePackage.Literals.VISIBILITY_ELEMENT__VISIBILITY)
    {
      final var elem = (VisibilityElement) obj;

      return getLocationData(obj, XcataloguePackage.Literals.VISIBILITY_ELEMENT__MODIFIERS,
              elem.getModifiers().indexOf(elem.getRealVisibility().getLiteral()));
    }
    if (structuralFeature == XcataloguePackage.Literals.COMPONENT__BASE)
    {
      final var elem = (Component) obj;
      if (elem.getBase() == null)
      {
        return getLocationData(obj, XcataloguePackage.Literals.NAMED_ELEMENT__NAME);
      }
    }
    if (structuralFeature == XcataloguePackage.Literals.CLASS__ABSTRACT)
    {
      final var elem = (VisibilityElement) obj;

      return getLocationData(obj, XcataloguePackage.Literals.VISIBILITY_ELEMENT__MODIFIERS,
              elem.getModifiers().indexOf("abstract"));
    }
    if (structuralFeature == XcataloguePackage.Literals.PROPERTY__ACCESS)
    {
      final var elem = (Property) obj;

      return getLocationData(obj, XcataloguePackage.Literals.VISIBILITY_ELEMENT__MODIFIERS,
              elem.getModifiers().indexOf(elem.getAccess().getLiteral()));
    }
    if (structuralFeature == XcataloguePackage.Literals.FIELD__INPUT)
    {
      final var elem = (VisibilityElement) obj;

      return getLocationData(obj, XcataloguePackage.Literals.VISIBILITY_ELEMENT__MODIFIERS,
              elem.getModifiers().indexOf("input"));
    }
    if (structuralFeature == XcataloguePackage.Literals.FIELD__OUTPUT)
    {
      final var elem = (VisibilityElement) obj;

      return getLocationData(obj, XcataloguePackage.Literals.VISIBILITY_ELEMENT__MODIFIERS,
              elem.getModifiers().indexOf("output"));
    }
    if (structuralFeature == XcataloguePackage.Literals.FIELD__TRANSIENT)
    {
      final var elem = (VisibilityElement) obj;

      return getLocationData(obj, XcataloguePackage.Literals.VISIBILITY_ELEMENT__MODIFIERS,
              elem.getModifiers().indexOf("transient"));
    }
    if (obj instanceof NamedElement && structuralFeature != null)
    {
      final var elem = (NamedElement) obj;
      for (final INode node : NodeModelUtils.findNodesForFeature(elem.getMetadatum(),
              XcataloguePackage.Literals.METADATUM__DOCUMENTATION))
      {
        final var xsmpcatdoc = elem.getMetadatum().getXsmpcatdoc();

        final var tagName = "@" + structuralFeature.getName();

        final var tags = xsmpcatdoc.tags();
        final var tag = tags.stream().filter(t -> tagName.equals(t.getTagName()))
                .skip(index > 0 ? index : 0).findFirst().orElseGet(() -> null);
        if (tag == null)
        {
          return super.getLocationData(obj, structuralFeature, index);
        }
        final var offset = tag.getStartPosition() + node.getTotalOffset();
        final var length = tag.getLength();

        final var result = new IssueLocation();
        result.offset = offset;
        result.length = length;

        final var lineAndColumnStart = NodeModelUtils.getLineAndColumn(node, result.offset);
        result.lineNumber = lineAndColumnStart.getLine();
        result.column = lineAndColumnStart.getColumn();

        final var lineAndColumnEnd = NodeModelUtils.getLineAndColumn(node,
                result.offset + result.length);
        result.lineNumberEnd = lineAndColumnEnd.getLine();
        result.columnEnd = lineAndColumnEnd.getColumn();
        return result;
      }
    }
    return super.getLocationData(obj, structuralFeature, index);
  }

}
