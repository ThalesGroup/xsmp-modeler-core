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

import org.eclipse.xsmp.extension.IExtensionManager;
import org.eclipse.xsmp.ide.commands.XsmpCommandService;
import org.eclipse.xsmp.ide.contentassist.IReferenceFilter;
import org.eclipse.xsmp.ide.contentassist.XsmpcatIdeContentProposalProvider;
import org.eclipse.xsmp.ide.contentassist.XsmpcatReferenceFilter;
import org.eclipse.xsmp.ide.extension.ExtensionManager;
import org.eclipse.xsmp.ide.folding.XsmpFoldingRangeProvider;
import org.eclipse.xsmp.ide.generator.XsmpGeneratorDelegate;
import org.eclipse.xsmp.ide.generator.XsmpOutputConfigurationProvider;
import org.eclipse.xsmp.ide.hover.IKeywordHovers;
import org.eclipse.xsmp.ide.hover.XsmpHoverService;
import org.eclipse.xsmp.ide.hover.XsmpcatKeywordHovers;
import org.eclipse.xsmp.ide.quickfix.XsmpcatIdeQuickfixProvider;
import org.eclipse.xsmp.ide.symbol.XsmpDocumentSymbolDeprecationInfoProvider;
import org.eclipse.xsmp.ide.symbol.XsmpDocumentSymbolDetailsProvider;
import org.eclipse.xsmp.ide.symbol.XsmpDocumentSymbolKindProvider;
import org.eclipse.xsmp.ide.symbol.XsmpDocumentSymbolNameProvider;
import org.eclipse.xsmp.ide.symbol.XsmpDocumentSymbolRangeProvider;
import org.eclipse.xsmp.ide.symbol.XsmpHierarchicalDocumentSymbolService;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.ide.editor.folding.IFoldingRangeProvider;
import org.eclipse.xtext.ide.editor.quickfix.IQuickFixProvider;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.ide.server.codeActions.QuickFixCodeActionService;
import org.eclipse.xtext.ide.server.commands.IExecutableCommandService;
import org.eclipse.xtext.ide.server.hover.IHoverService;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolDeprecationInfoProvider;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolDetailsProvider;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolKindProvider;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolNameProvider;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolRangeProvider;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;

/**
 * Use this class to register ide components.
 */
public class XsmpcatIdeModule extends AbstractXsmpcatIdeModule
{

  public Class< ? extends IExecutableCommandService> bindIExecutableCommandService()
  {
    return XsmpCommandService.class;
  }

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

  public Class< ? extends IHoverService> bindIHoverService()
  {
    return XsmpHoverService.class;
  }

  public Class< ? extends GeneratorDelegate> bindGeneratorDelegate()
  {
    return XsmpGeneratorDelegate.class;
  }

  public Class< ? extends OutputConfigurationProvider> bindOutputConfigurationProvider()
  {
    return XsmpOutputConfigurationProvider.class;
  }

  public Class< ? extends IKeywordHovers> bindIKeywordHovers()
  {
    return XsmpcatKeywordHovers.class;
  }

  public Class< ? extends IFoldingRangeProvider> bindIFoldingRangeProvider()
  {
    return XsmpFoldingRangeProvider.class;
  }

  public Class< ? extends IExtensionManager> bindIExtensionManager()
  {
    return ExtensionManager.class;
  }

  public Class< ? extends IReferenceFilter> bindIReferenceFilter()
  {
    return XsmpcatReferenceFilter.class;
  }

  public Class< ? extends HierarchicalDocumentSymbolService> bindHierarchicalDocumentSymbolService()
  {
    return XsmpHierarchicalDocumentSymbolService.class;
  }

  public Class< ? extends DocumentSymbolDeprecationInfoProvider> bindDocumentSymbolDeprecationInfoProvider()
  {
    return XsmpDocumentSymbolDeprecationInfoProvider.class;
  }

  public Class< ? extends DocumentSymbolKindProvider> bindDocumentSymbolKindProvider()
  {
    return XsmpDocumentSymbolKindProvider.class;
  }

  public Class< ? extends DocumentSymbolNameProvider> bindDocumentSymbolNameProvider()
  {
    return XsmpDocumentSymbolNameProvider.class;
  }

  public Class< ? extends DocumentSymbolDetailsProvider> bindDocumentSymbolDetailsProvider()
  {
    return XsmpDocumentSymbolDetailsProvider.class;
  }

  public Class< ? extends DocumentSymbolRangeProvider> bindDocumentSymbolRangeProvider()
  {
    return XsmpDocumentSymbolRangeProvider.class;
  }
}