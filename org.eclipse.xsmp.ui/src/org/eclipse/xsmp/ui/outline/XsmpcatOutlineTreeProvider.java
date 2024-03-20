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

import static org.eclipse.xsmp.model.xsmp.XsmpPackage.CATALOGUE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.CLASS;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.ENUMERATION;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.EXCEPTION;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.INTERFACE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.MODEL;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.NAMESPACE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.SERVICE;
import static org.eclipse.xsmp.model.xsmp.XsmpPackage.STRUCTURE;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.model.xsmp.Catalogue;
import org.eclipse.xsmp.model.xsmp.Enumeration;
import org.eclipse.xsmp.model.xsmp.Metadatum;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
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

    createEStructuralFeatureNode(parentNode, doc, XsmpPackage.Literals.NAMED_ELEMENT__NAME,
            imageDispatcher.invoke(doc), textDispatcher.invoke(doc), true);

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
    return switch (type.eClass().getClassifierID())
    {
      case STRUCTURE, EXCEPTION, CLASS, INTERFACE, SERVICE, NAMESPACE, CATALOGUE, MODEL -> ((NamedElementWithMembers) type)
              .getMember().isEmpty();
      case ENUMERATION -> ((Enumeration) type).getLiteral().isEmpty();
      default -> true;
    };
  }
}
