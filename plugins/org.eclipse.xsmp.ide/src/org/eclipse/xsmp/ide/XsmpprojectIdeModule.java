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
package org.eclipse.xsmp.ide;

import org.eclipse.xsmp.extension.IExtensionManager;
import org.eclipse.xsmp.ide.containers.XsmpprojectContainerManager;
import org.eclipse.xsmp.ide.contentassist.IReferenceFilter;
import org.eclipse.xsmp.ide.contentassist.XsmpIdeContentProposalProvider;
import org.eclipse.xsmp.ide.contentassist.XsmpprojectIdeCrossrefProposalProvider;
import org.eclipse.xsmp.ide.contentassist.XsmpprojectReferenceFilter;
import org.eclipse.xsmp.ide.extension.ExtensionManager;
import org.eclipse.xsmp.ide.hover.IKeywordHovers;
import org.eclipse.xsmp.ide.hover.XsmpHoverService;
import org.eclipse.xsmp.ide.hover.XsmpprojectKeywordHovers;
import org.eclipse.xsmp.ide.symbol.XsmpDocumentSymbolDeprecationInfoProvider;
import org.eclipse.xsmp.ide.symbol.XsmpDocumentSymbolDetailsProvider;
import org.eclipse.xsmp.ide.symbol.XsmpDocumentSymbolKindProvider;
import org.eclipse.xsmp.ide.symbol.XsmpDocumentSymbolNameProvider;
import org.eclipse.xsmp.ide.symbol.XsmpHierarchicalDocumentSymbolService;
import org.eclipse.xsmp.ide.validation.XsmpprojectIdeValidator;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.ide.editor.contentassist.IdeCrossrefProposalProvider;
import org.eclipse.xtext.ide.server.hover.IHoverService;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolDeprecationInfoProvider;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolDetailsProvider;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolKindProvider;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolNameProvider;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.service.SingletonBinding;

/**
 * Use this class to register ide components.
 */
public class XsmpprojectIdeModule extends AbstractXsmpprojectIdeModule
{

  public Class< ? extends IHoverService> bindIHoverService()
  {
    return XsmpHoverService.class;
  }

  public Class< ? extends IKeywordHovers> bindIKeywordHovers()
  {
    return XsmpprojectKeywordHovers.class;
  }

  public Class< ? extends IExtensionManager> bindIExtensionManager()
  {
    return ExtensionManager.class;
  }

  public Class< ? extends IReferenceFilter> bindIReferenceFilter()
  {
    return XsmpprojectReferenceFilter.class;
  }

  public Class< ? extends IdeContentProposalProvider> bindIdeContentProposalProvider()
  {
    return XsmpIdeContentProposalProvider.class;
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

  public Class< ? extends IContainer.Manager> bindIContainerManager()
  {
    return XsmpprojectContainerManager.class;
  }

  public Class< ? extends IdeCrossrefProposalProvider> bindIdeCrossrefProposalProvider()
  {
    return XsmpprojectIdeCrossrefProposalProvider.class;
  }

  @SingletonBinding(eager = true)
  public Class< ? extends XsmpprojectIdeValidator> bindXsmpprojectIdeValidator()
  {
    return XsmpprojectIdeValidator.class;
  }
}
