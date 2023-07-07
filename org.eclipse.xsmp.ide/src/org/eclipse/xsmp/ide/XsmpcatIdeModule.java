/*******************************************************************************
* Copyright (C) 2020-2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide;

import org.eclipse.xsmp.ide.contentassist.XsmpcatIdeContentProposalProvider;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.ide.editor.quickfix.IQuickFixProvider;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.ide.server.codeActions.QuickFixCodeActionService;

/**
 * Use this class to register ide components.
 */
public class XsmpcatIdeModule extends AbstractXsmpcatIdeModule
{
  public Class< ? extends IQuickFixProvider> bindIQuickFixProvider()
  {
    return XsmpcatIdeQuickfixProvider.class;
  }

  public Class< ? extends ICodeActionService2> bindICodeActionService2()
  {
    return QuickFixCodeActionService.class;
  }

  public Class< ? extends IdeContentProposalProvider> bindIdeContentProposalProvider()
  {
    return XsmpcatIdeContentProposalProvider.class;
  }
}