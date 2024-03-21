/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.server.symbol;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.model.xsmp.NamedElement;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolDeprecationInfoProvider;
import org.eclipse.xtext.resource.IEObjectDescription;

public class XsmpDocumentSymbolDeprecationInfoProvider extends DocumentSymbolDeprecationInfoProvider
{
  @Override
  public boolean isDeprecated(EObject object)
  {
    if (object instanceof NamedElement)
    {
      return ((NamedElement) object).isDeprecated();
    }
    return false;
  }

  @Override
  public boolean isDeprecated(IEObjectDescription description)
  {
    final var data = description.getUserData("deprecated");
    if (data != null)
    {
      return Boolean.parseBoolean(data);
    }
    return false;
  }
}
