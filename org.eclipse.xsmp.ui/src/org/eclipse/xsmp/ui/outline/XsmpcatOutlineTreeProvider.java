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
package org.eclipse.xsmp.ui.outline;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.xcatalogue.Catalogue;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.NamedElementWithMembers;
import org.eclipse.xsmp.xcatalogue.Metadatum;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;

/**
 * Customization of the default outline structure. See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#outline
 */
public class XsmpcatOutlineTreeProvider extends DefaultOutlineTreeProvider
{

  protected void _createChildren(DocumentRootNode parentNode, Catalogue doc)
  {

    createEStructuralFeatureNode(parentNode, doc, XcataloguePackage.Literals.NAMED_ELEMENT__NAME,
            imageDispatcher.invoke(doc), textDispatcher.invoke(doc), true);

    /*
     * do not show imports in outline final ImportSection imports = doc.getImportSection(); if
     * (imports != null) { for (final EObject m : imports.getImportDeclarations()) {
     * createNode(parentNode, m); } }
     */

    for (final EObject m : doc.getMember())
    {
      createNode(parentNode, m);
    }
  }

  protected void _createNode(IOutlineNode parent, Metadatum modelElement)
  {
    // hide Metadatum
  }

  // feature nodes are leafs and not expandable
  protected boolean _isLeaf(NamedElement type)
  {

    switch (type.eClass().getClassifierID())
    {

      case XcataloguePackage.STRUCTURE:
      case XcataloguePackage.EXCEPTION:
      case XcataloguePackage.CLASS:
      case XcataloguePackage.INTERFACE:
      case XcataloguePackage.SERVICE:
      case XcataloguePackage.NAMESPACE:
      case XcataloguePackage.CATALOGUE:
      case XcataloguePackage.MODEL:
        return ((NamedElementWithMembers) type).getMember().isEmpty();
      case XcataloguePackage.ENUMERATION:
        return ((Enumeration) type).getLiteral().isEmpty();

      default:
        return true;

    }
  }
}
