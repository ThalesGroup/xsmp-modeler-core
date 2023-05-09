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
package org.eclipse.xsmp.ui.codemining;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.codemining.ICodeMining;
import org.eclipse.xsmp.services.XsmpcatGrammarAccess;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.Association;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.codemining.AbstractXtextCodeMiningProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IAcceptor;

import com.google.inject.Inject;

/**
 * @author daveluy
 */
@SuppressWarnings("restriction")
public class XsmpcatCodeMiningProvider extends AbstractXtextCodeMiningProvider
{

  @Inject
  private XsmpUtil xsmpUtil;

  @Inject
  private XsmpcatGrammarAccess ga;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createCodeMinings(IDocument document, XtextResource resource,
          CancelIndicator indicator, IAcceptor< ? super ICodeMining> acceptor)
    throws BadLocationException
  {
    if (resource.getContents().isEmpty())
    {
      return;
    }

    final var it = resource.getAllContents();

    while (it.hasNext())
    {
      final var obj = it.next();

      switch (obj.eClass().getClassifierID())
      {
        case XcataloguePackage.ASSOCIATION:
          createCodeMinings((Association) obj, acceptor);
          it.prune();
          break;
        case XcataloguePackage.OPERATION:
          createCodeMinings((Operation) obj, acceptor);
          it.prune();
          break;
        case XcataloguePackage.PROPERTY:
          createCodeMinings((Property) obj, acceptor);
          it.prune();
          break;
        default:
          break;
      }
    }

  }

  protected void createCodeMinings(Association p, IAcceptor< ? super ICodeMining> acceptor)
  {
    for (final INode node : NodeModelUtils.findNodesForFeature(p,
            XcataloguePackage.Literals.ASSOCIATION__TYPE))
    {
      if (xsmpUtil.isByPointer(p))
      {
        acceptor.accept(createNewLineContentCodeMining(node.getEndOffset(), "*"));
      }
    }
  }

  protected void addVirtualCodeMining(INode node, String kw,
          IAcceptor< ? super ICodeMining> acceptor)
  {
    for (final INode n : node.getAsTreeIterable())
    {
      final var ge = n.getGrammarElement();
      if (ge instanceof Keyword && kw.equals(((Keyword) ge).getValue()))
      {
        acceptor.accept(createNewLineContentCodeMining(n.getEndOffset() + 1, "virtual "));
        break;
      }
    }
  }

  protected void createCodeMinings(Operation o, IAcceptor< ? super ICodeMining> acceptor)
  {
    final INode node = NodeModelUtils.findActualNodeFor(o);

    if (xsmpUtil.isVirtual(o))
    {
      addVirtualCodeMining(node, ga.getOperationDeclarationAccess().getDefKeyword_1().getValue(),
              acceptor);
    }

    var m = "";
    if (xsmpUtil.isConst(o))
    {
      m += " const";
    }

    if (xsmpUtil.isAbstract(o))
    {
      m += " = 0";
    }
    if (!m.isEmpty())
    {

      acceptor.accept(createNewLineContentCodeMining(node.getEndOffset(), m));
    }

    final var returnParameter = o.getReturnParameter();
    if (returnParameter != null)
    {
      createCodeMinings(returnParameter, acceptor);
    }

    o.getParameter().forEach(p -> createCodeMinings(p, acceptor));
  }

  protected void createCodeMinings(Property o, IAcceptor< ? super ICodeMining> acceptor)
  {
    final INode node = NodeModelUtils.findActualNodeFor(o);

    if (xsmpUtil.isVirtual(o))
    {
      addVirtualCodeMining(node,
              ga.getPropertyDeclarationAccess().getPropertyKeyword_1().getValue(), acceptor);
    }
    if (xsmpUtil.isByPointer(o) || xsmpUtil.isByReference(o))
    {
      for (final INode n : NodeModelUtils.findNodesForFeature(o,
              XcataloguePackage.Literals.PROPERTY__TYPE))
      {

        if (xsmpUtil.isByPointer(o))
        {
          acceptor.accept(createNewLineContentCodeMining(n.getEndOffset(), "*"));
        }
        else if (xsmpUtil.isByReference(o))
        {
          acceptor.accept(createNewLineContentCodeMining(n.getEndOffset(), "&"));
        }
      }
    }

    var m = "";
    if (xsmpUtil.isConst(o) || xsmpUtil.isConstGetter(o))
    {
      m += " const";
    }

    if (xsmpUtil.isAbstract(o))
    {
      m += " = 0";
    }
    if (!m.isEmpty())
    {
      acceptor.accept(createNewLineContentCodeMining(node.getEndOffset(), m));
    }

  }

  protected void createCodeMinings(Parameter p, IAcceptor< ? super ICodeMining> acceptor)
  {
    if (xsmpUtil.isConst(p) || xsmpUtil.isByPointer(p) || xsmpUtil.isByReference(p))
    {
      for (final INode node : NodeModelUtils.findNodesForFeature(p,
              XcataloguePackage.Literals.PARAMETER__TYPE))
      {
        if (xsmpUtil.isConst(p))
        {
          acceptor.accept(createNewLineContentCodeMining(node.getOffset(), "const "));
        }
        if (xsmpUtil.isByPointer(p))
        {
          acceptor.accept(createNewLineContentCodeMining(node.getEndOffset(), "*"));
        }
        else if (xsmpUtil.isByReference(p))
        {
          acceptor.accept(createNewLineContentCodeMining(node.getEndOffset(), "&"));
        }
      }
    }
  }

}
