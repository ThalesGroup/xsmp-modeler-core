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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.DocumentSymbol;
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

  @Override
  public List<DocumentSymbol> getSymbols(XtextResource resource, CancelIndicator cancelIndicator)
  {
    final Map<EObject, DocumentSymbol> allSymbols = new HashMap<>();
    final List<DocumentSymbol> rootSymbols = new ArrayList<>();
    final var itr = getAllContents(resource);
    while (itr.hasNext())
    {
      operationCanceledManager.checkCanceled(cancelIndicator);
      final var next = toEObject(itr.next());
      if (next.isPresent())
      {
        final var object = next.get();
        final var symbol = symbolMapper.toDocumentSymbol(object);
        if (isValid(symbol) && symbol.getName() != null && !symbol.getName().isEmpty())
        {
          allSymbols.put(object, symbol);
          var parent = object.eContainer();
          if (parent == null)
          {
            rootSymbols.add(symbol);
          }
          else
          {
            var parentSymbol = allSymbols.get(parent);
            while (parentSymbol == null && parent != null)
            {
              parent = parent.eContainer();
              parentSymbol = allSymbols.get(parent);
            }
            if (parentSymbol == null)
            {
              rootSymbols.add(symbol);
            }
            else
            {
              parentSymbol.getChildren().add(symbol);
            }
          }
        }
      }
    }
    return rootSymbols;
  }

}
