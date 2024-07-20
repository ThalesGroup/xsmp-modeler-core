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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.util.Ranges;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolRangeProvider;

public class XsmpDocumentSymbolRangeProvider extends DocumentSymbolRangeProvider
{

  @Override
  public Range getSelectionRange(EObject object)
  {
    final var range = getRange(object);
    if (range == null)
    {
      return null;
    }
    final var selectionRange = super.getSelectionRange(object);
    // sometimes during edition the selection range is invalid and is outside the range.
    // in this case return null to avoid error in vs code
    return selectionRange != null && Ranges.containsRange(range, selectionRange) ? selectionRange
            : null;
  }
}
