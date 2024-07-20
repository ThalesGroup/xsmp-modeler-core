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
package org.eclipse.xsmp.ide.symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.xsmp.model.xsmp.Document;
import org.eclipse.xsmp.model.xsmp.NamedElementWithMembers;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;

public class XsmpHierarchicalDocumentSymbolService extends HierarchicalDocumentSymbolService
{
  @Inject
  private DocumentSymbolMapper symbolMapper;

  @Inject
  private OperationCanceledManager operationCanceledManager;

  private void collectSymbols(EObject eObject, List<DocumentSymbol> symbols,
          CancelIndicator cancelIndicator)
  {
    operationCanceledManager.checkCanceled(cancelIndicator);

    final var symbol = symbolMapper.toDocumentSymbol(eObject);
    if (isValid(symbol) && symbol.getName() != null && !symbol.getName().isEmpty())
    {
      symbols.add(symbol);
      if (eObject instanceof final NamedElementWithMembers ctn)
      {
        for (final var member : ctn.getMember())
        {
          collectSymbols(member, symbol.getChildren(), cancelIndicator);
        }
      }
    }

  }

  @Override
  public List<DocumentSymbol> getSymbols(XtextResource resource, CancelIndicator cancelIndicator)
  {
    if (resource.getContents().isEmpty())
    {
      return Collections.emptyList();
    }

    final List<DocumentSymbol> rootSymbols = new ArrayList<>();
    if (resource.getContents().get(0) instanceof final Document doc)
    {
      operationCanceledManager.checkCanceled(cancelIndicator);
      final var symbol = symbolMapper.toDocumentSymbol(doc);
      if (isValid(symbol) && symbol.getName() != null && !symbol.getName().isEmpty())
      {
        rootSymbols.add(symbol);
      }
      if (doc instanceof final NamedElementWithMembers elem)
      {
        for (final var member : elem.getMember())
        {
          collectSymbols(member, rootSymbols, cancelIndicator);
        }
      }
    }
    return rootSymbols;
  }

}
