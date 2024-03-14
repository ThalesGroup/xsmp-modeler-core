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

import org.eclipse.xsmp.ide.commands.XsmpCommandService;
import org.eclipse.xsmp.ide.contentassist.XsmpcatIdeContentProposalProvider;
import org.eclipse.xsmp.ide.generator.XsmpGenerator;
import org.eclipse.xsmp.ide.generator.XsmpOutputConfigurationProvider;
import org.eclipse.xsmp.ide.generator.XsmpShouldGenerate;
import org.eclipse.xsmp.ide.hover.IKeywordHovers;
import org.eclipse.xsmp.ide.hover.XsmpHoverService;
import org.eclipse.xsmp.ide.hover.XsmpcatKeywordHovers;
import org.eclipse.xsmp.ide.quickfix.XsmpcatIdeQuickfixProvider;
import org.eclipse.xsmp.ide.workspace.XsmpProjectConfigProvider;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IShouldGenerate;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.ide.editor.quickfix.IQuickFixProvider;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.ide.server.codeActions.QuickFixCodeActionService;
import org.eclipse.xtext.ide.server.commands.IExecutableCommandService;
import org.eclipse.xtext.ide.server.hover.IHoverService;
import org.eclipse.xtext.workspace.IProjectConfigProvider;

/**
 * Use this class to register ide components.
 */
public class XsmpcatIdeModule extends AbstractXsmpcatIdeModule
{
  static
  {
    @SuppressWarnings("unused")
    final Class< ? >[] classes = {
      org.apache.log4j.ConsoleAppender.class,
      org.apache.log4j.DailyRollingFileAppender.class,
      org.apache.log4j.PatternLayout.class };
  }

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

  public Class< ? extends IGenerator2> bindIGenerator2()
  {
    return XsmpGenerator.class;
  }

  public Class< ? extends OutputConfigurationProvider> bindOutputConfigurationProvider()
  {
    return XsmpOutputConfigurationProvider.class;
  }

  @Override
  public Class< ? extends IProjectConfigProvider> bindIProjectConfigProvider()
  {
    return XsmpProjectConfigProvider.class;
  }

  public Class< ? extends IShouldGenerate> bindIShouldGenerate()
  {
    return XsmpShouldGenerate.class;
  }

  public Class< ? extends IKeywordHovers> bindIKeywordHovers()
  {
    return XsmpcatKeywordHovers.class;
  }
}