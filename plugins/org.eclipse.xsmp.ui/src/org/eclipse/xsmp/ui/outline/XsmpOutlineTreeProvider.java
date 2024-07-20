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
import org.eclipse.xsmp.model.xsmp.Enumeration;
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.BackgroundOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;

/**
 * Xsmp Outline Tree provider that run on background
 */
public class XsmpOutlineTreeProvider extends BackgroundOutlineTreeProvider
{
  @Override
  protected void internalCreateChildren(DocumentRootNode parentNode, EObject modelElement)
  {
    // first node with document
    getOutlineNodeFactory().createEStructuralFeatureNode(parentNode, modelElement,
            XsmpPackage.Literals.NAMED_ELEMENT__NAME, getImageDescriptor(modelElement),
            getText(modelElement), true);
    // siblings with members
    internalCreateChildren((IOutlineNode) parentNode, modelElement);
  }

  @Override
  protected void internalCreateChildren(IOutlineNode parentNode, EObject modelElement)
  {
    // we are only interested by members of NamedElementWithMembers
    if (modelElement instanceof final NamedElementWithMembers parent)
    {
      parent.getMember().forEach(child -> createNode(parentNode, child));
    }
    // and enumerations
    else if (modelElement instanceof final Enumeration enumeration)
    {
      enumeration.getLiteral().forEach(child -> createNode(parentNode, child));
    }
  }

  @Override
  protected boolean isLeaf(final EObject modelElement)
  {
    return switch (modelElement.eClass().getClassifierID())
    {
      case STRUCTURE, EXCEPTION, CLASS, INTERFACE, SERVICE, NAMESPACE, CATALOGUE, MODEL -> ((NamedElementWithMembers) modelElement)
              .getMember().isEmpty();
      case ENUMERATION -> ((Enumeration) modelElement).getLiteral().isEmpty();
      default -> true;
    };
  }

}
