/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xsmp.extension.IExtensionManager;
import org.eclipse.xsmp.ide.contentassist.IReferenceFilter;
import org.eclipse.xsmp.ide.contentassist.XsmpprojectReferenceFilter;
import org.eclipse.xsmp.ide.hover.IKeywordHovers;
import org.eclipse.xsmp.ide.hover.XsmpprojectKeywordHovers;
import org.eclipse.xsmp.ui.builder.XsmpprojectBuilderParticipant;
import org.eclipse.xsmp.ui.containers.XsmpprojectProjectsState;
import org.eclipse.xsmp.ui.contentassist.XsmpReferenceProposalCreator;
import org.eclipse.xsmp.ui.editor.XsmpprojectEditor;
import org.eclipse.xsmp.ui.editor.model.XsmpDocumentProvider;
import org.eclipse.xsmp.ui.extension.ExtensionManager;
import org.eclipse.xsmp.ui.highlighting.XsmpAntlrTokenToAttributeIdMapper;
import org.eclipse.xsmp.ui.highlighting.XsmpHighlightingConfiguration;
import org.eclipse.xsmp.ui.highlighting.XsmpSemanticHighlightingCalculator;
import org.eclipse.xsmp.ui.hover.XsmpDispatchingEObjectTextHover;
import org.eclipse.xsmp.ui.hover.XsmpEObjectHoverProvider;
import org.eclipse.xsmp.ui.resource.XsmpprojectResourceUIServiceProvider;
import org.eclipse.xsmp.ui.validation.XsmpprojectUIValidator;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider.ReferenceProposalCreator;
import org.eclipse.xtext.ui.editor.hover.IEObjectHover;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.resource.IResourceUIServiceProvider;
import org.eclipse.xtext.ui.shared.Access;

import com.google.inject.Provider;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
public class XsmpprojectUiModule extends AbstractXsmpprojectUiModule
{

  public XsmpprojectUiModule(AbstractUIPlugin plugin)
  {
    super(plugin);
  }

  public Class< ? extends IResourceUIServiceProvider> bindIResourceUIServiceProvider()
  {
    return XsmpprojectResourceUIServiceProvider.class;
  }

  @Override
  public Class< ? extends IXtextBuilderParticipant> bindIXtextBuilderParticipant()
  {
    return XsmpprojectBuilderParticipant.class;
  }

  @Override
  public Provider<IAllContainersState> provideIAllContainersState()
  {
    return Access.contributedProvider(XsmpprojectProjectsState.class);
  }

  @SingletonBinding(eager = true)
  public Class< ? extends XsmpprojectUIValidator> bindXsmpprojectUIValidator()
  {
    return XsmpprojectUIValidator.class;
  }

  public Class< ? extends AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper()
  {
    return XsmpAntlrTokenToAttributeIdMapper.class;
  }

  public Class< ? extends IHighlightingConfiguration> bindIHighlightingConfiguration()
  {
    return XsmpHighlightingConfiguration.class;
  }

  public Class< ? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator()
  {
    return XsmpSemanticHighlightingCalculator.class;
  }

  @Override
  public Class< ? extends IEObjectHover> bindIEObjectHover()
  {
    return XsmpDispatchingEObjectTextHover.class;
  }

  public Class< ? extends IEObjectHoverProvider> bindIEObjectHoverProvider()
  {
    return XsmpEObjectHoverProvider.class;
  }

  public Class< ? extends IKeywordHovers> bindIKeywordHovers()
  {
    return XsmpprojectKeywordHovers.class;
  }

  public Class< ? extends IExtensionManager> bindIExtensionManager()
  {
    return ExtensionManager.class;
  }

  public Class< ? extends XtextEditor> bindXtextEditor()
  {
    return XsmpprojectEditor.class;
  }

  public Class< ? extends IReferenceFilter> bindIReferenceFilter()
  {
    return XsmpprojectReferenceFilter.class;
  }

  public Class< ? extends ReferenceProposalCreator> bindReferenceProposalCreator()
  {
    return XsmpReferenceProposalCreator.class;
  }

  public Class< ? extends XtextDocumentProvider> bindXtextDocumentProvider()
  {
    return XsmpDocumentProvider.class;
  }
}
