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
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.DesignatedInitializer;
import org.eclipse.xsmp.xcatalogue.Operation;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.Structure;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.nodemodel.ILeafNode;
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
  private XsmpUtil elementUtil;

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
        case XcataloguePackage.COLLECTION_LITERAL:
          createCodeMinings((CollectionLiteral) obj, acceptor);
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
      if (elementUtil.isByPointer(p))
      {
        acceptor.accept(createNewLineContentCodeMining(node.getEndOffset(), "*"));
      }
    }
  }

  protected void createCodeMinings(Operation o, IAcceptor< ? super ICodeMining> acceptor)
  {
    if (elementUtil.isVirtual(o))
    {
      final var defKeyword = ga.getOperationDeclarationAccess().getDefKeyword_1();
      for (final ILeafNode node : NodeModelUtils.getNode(o).getLeafNodes())
      {
        if (defKeyword == node.getGrammarElement())
        {
          acceptor.accept(createNewLineContentCodeMining(node.getEndOffset() + 1, "virtual "));
          break;
        }
      }
    }

    var m = "";
    if (elementUtil.isConst(o))
    {
      m += " const";
    }

    if (elementUtil.isAbstract(o))
    {
      m += " = 0";
    }
    if (!m.isEmpty())
    {
      final INode node = NodeModelUtils.getNode(o);

      acceptor.accept(createNewLineContentCodeMining(node.getEndOffset(), m));
    }

    final var returnParameter = o.getReturnParameter();
    if (returnParameter != null)
    {
      createCodeMinings(returnParameter, acceptor);
    }

    o.getParameter().forEach(p -> createCodeMinings(p, acceptor));
  }

  protected void createCodeMinings(Parameter p, IAcceptor< ? super ICodeMining> acceptor)
  {
    for (final INode node : NodeModelUtils.findNodesForFeature(p,
            XcataloguePackage.Literals.PARAMETER__TYPE))
    {
      if (elementUtil.isConst(p))
      {
        acceptor.accept(createNewLineContentCodeMining(node.getOffset(), "const "));
      }
      switch (elementUtil.kind(p))
      {
        case BY_PTR:
          acceptor.accept(createNewLineContentCodeMining(node.getEndOffset(), "*"));
          break;
        case BY_REF:
          acceptor.accept(createNewLineContentCodeMining(node.getEndOffset(), "&"));
          break;
        default:
          break;
      }
    }
  }

  protected void createCodeMinings(CollectionLiteral obj, IAcceptor< ? super ICodeMining> acceptor)
  {
    final var type = elementUtil.getType(obj);
    if (type instanceof Structure)
    {
      final var fields = elementUtil.getAssignableFields((Structure) type);

      final var elems = obj.getElements();
      final var size = Math.min(fields.size(), elems.size());
      for (var i = 0; i < size; ++i)
      {
        final var elem = elems.get(i);
        if (!(elem instanceof DesignatedInitializer))
        {
          final var node = NodeModelUtils.getNode(elem);
          if (node != null)
          {
            acceptor.accept(createNewLineContentCodeMining(node.getOffset(),
                    "." + fields.get(i).getName() + " = "));
          }
        }
      }
    }
  }
}
