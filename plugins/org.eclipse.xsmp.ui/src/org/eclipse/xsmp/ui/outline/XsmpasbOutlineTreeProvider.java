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
package org.eclipse.xsmp.ui.outline;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.model.xsmp.Assembly;
import org.eclipse.xsmp.model.xsmp.Container;
import org.eclipse.xsmp.model.xsmp.Expression;
import org.eclipse.xsmp.model.xsmp.InstanceDeclaration;
import org.eclipse.xsmp.model.xsmp.Metadatum;
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers;
import org.eclipse.xsmp.model.xsmp.Path;
import org.eclipse.xsmp.model.xsmp.TypeReference;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;

/**
 * Customization of the default outline structure. See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#outline
 */
public class XsmpasbOutlineTreeProvider extends DefaultOutlineTreeProvider
{
  protected void _createChildren(DocumentRootNode parentNode, Assembly doc)
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

  protected void _createNode(IOutlineNode parent, Expression modelElement)
  {
    // hide Expression
  }

  protected void _createNode(IOutlineNode parent, Container modelElement)
  {
    // hide Container
  }

  protected void _createNode(IOutlineNode parent, Path modelElement)
  {
    // hide Path
  }

  protected void _createNode(IOutlineNode parent, TypeReference modelElement)
  {
    // hide TypeReference
  }

  protected void _createNode(IOutlineNode parent, InstanceDeclaration modelElement)
  {
    _createNode(parent, modelElement.getInstance());
  }

  // feature nodes are leafs and not expandable
  protected boolean _isLeaf(NamedElementWithMembers type)
  {
    return type == null || type.getMember().isEmpty();

  }

  @Override
  protected boolean _isLeaf(EObject cfg)
  {
    return true;
  }

}
