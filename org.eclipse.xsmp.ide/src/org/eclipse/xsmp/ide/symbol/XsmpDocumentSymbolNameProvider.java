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
import org.eclipse.xsmp.ide.labeling.XsmpLabelProvider;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolNameProvider;

import com.google.inject.Inject;

public class XsmpDocumentSymbolNameProvider extends DocumentSymbolNameProvider
{
  @Inject
  private XsmpLabelProvider labelProvider;

  @Override
  public String getName(EObject object)
  {
    return labelProvider.getLabel(object);
  }

}
