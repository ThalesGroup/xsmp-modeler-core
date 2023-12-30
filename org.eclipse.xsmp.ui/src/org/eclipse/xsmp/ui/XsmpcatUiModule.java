/*******************************************************************************
* Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ui;

import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xsmp.service.IXsmpServiceProvider;
import org.eclipse.xsmp.ui.autoedit.XsmpAutoEditStrategyProvider;
import org.eclipse.xsmp.ui.autoedit.XsmpMultiLineTerminalEditStrategy;
import org.eclipse.xsmp.ui.builder.XsmpBuilderParticipant;
import org.eclipse.xsmp.ui.configuration.XsmpServiceUIProvider;
import org.eclipse.xsmp.ui.contentassist.XsmpcatReferenceProposalCreator;
import org.eclipse.xsmp.ui.contentassist.XsmpTemplateContextType;
import org.eclipse.xsmp.ui.contentassist.XsmpTemplateProposalProvider;
import org.eclipse.xsmp.ui.editor.model.XsmpPreferenceAccess;
import org.eclipse.xsmp.ui.editor.model.XsmpTerminalsTokenTypeToPartitionMapper;
import org.eclipse.xsmp.ui.editor.model.XsmpDocumentProvider;
import org.eclipse.xsmp.ui.folding.XsmpFoldingRegionProvider;
import org.eclipse.xsmp.ui.highlighting.XsmpAntlrTokenToAttributeIdMapper;
import org.eclipse.xsmp.ui.highlighting.XsmpHighlightingConfiguration;
import org.eclipse.xsmp.ui.highlighting.XsmpSemanticHighlightingCalculator;
import org.eclipse.xsmp.ui.hover.IKeywordHovers;
import org.eclipse.xsmp.ui.hover.XsmpDispatchingEObjectTextHover;
import org.eclipse.xsmp.ui.hover.XsmpEObjectHoverProvider;
import org.eclipse.xsmp.ui.hover.XsmpcatKeywordHovers;
import org.eclipse.xsmp.ui.outline.XsmpcatOutlineTreeProvider;
import org.eclipse.xsmp.ui.quickfix.XsmpTextEditComposer;
import org.eclipse.xsmp.ui.resource.XsmpResourceUIServiceProvider;
import org.eclipse.xsmp.ui.template.XsmpcatCrossReferenceTemplateVariableResolver;
import org.eclipse.xsmp.ui.template.XsmpTemplateStore;
import org.eclipse.xsmp.ui.validation.XsmpValidatorConfigurationBlock;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider;
import org.eclipse.xtext.ui.editor.autoedit.MultiLineTerminalsEditStrategy;
import org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider.ReferenceProposalCreator;
import org.eclipse.xtext.ui.editor.contentassist.ITemplateProposalProvider;
import org.eclipse.xtext.ui.editor.contentassist.XtextContentAssistProcessor;
import org.eclipse.xtext.ui.editor.folding.IFoldingRegionProvider;
import org.eclipse.xtext.ui.editor.hover.IEObjectHover;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.ui.editor.model.edit.ITextEditComposer;
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.templates.CrossReferenceTemplateVariableResolver;
import org.eclipse.xtext.ui.editor.templates.XtextTemplateContextType;
import org.eclipse.xtext.ui.resource.IResourceUIServiceProvider;
import org.eclipse.xtext.ui.shared.Access;
import org.eclipse.xtext.ui.validation.AbstractValidatorConfigurationBlock;

import com.google.inject.Binder;
import com.google.inject.Provider;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
public class XsmpcatUiModule extends AbstractXsmpcatUiModule
{

  public XsmpcatUiModule(AbstractUIPlugin plugin)
  {
    super(plugin);
  }

  public Class< ? extends IFoldingRegionProvider> bindIFoldingRegionProvider()
  {
    return XsmpFoldingRegionProvider.class;
  }

  public Class< ? extends IHighlightingConfiguration> bindIHighlightingConfiguration()
  {
    return XsmpHighlightingConfiguration.class;
  }

  public Class< ? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator()
  {
    return XsmpSemanticHighlightingCalculator.class;
  }

  public Class< ? extends AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper()
  {
    return XsmpAntlrTokenToAttributeIdMapper.class;
  }

  public Class< ? extends ITextEditComposer> bindITextEditComposer()
  {
    return XsmpTextEditComposer.class;
  }

  public Class< ? extends MultiLineTerminalsEditStrategy.Factory> bindMultilineTerminalEditStrategyFactory()
  {
    return XsmpMultiLineTerminalEditStrategy.Factory.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void configure(Binder binder)
  {
    super.configure(binder);
    binder.bind(XtextTemplateContextType.class).to(XsmpTemplateContextType.class);

    binder.bind(String.class)
            .annotatedWith(
                    Names.named(XtextContentAssistProcessor.COMPLETION_AUTO_ACTIVATION_CHARS))
            .toInstance(".,@");
  }

  public void configureSaveActionsPreferenceStoreInitializer(Binder binder)
  {
    binder.bind(IPreferenceStoreInitializer.class)
            .annotatedWith(Names.named("saveActionsPreferenceInitializer"))
            .to(XsmpPreferenceAccess.Initializer.class);
  }

  public void configureExtensionName(Binder binder)
  {
    binder.bind(String.class).annotatedWith(Names.named(XsmpConstants.EXTENSION_NAME))
            .toInstance(XsmpConstants.DEFAULT_PROFILE_NAME);
  }

  @Override
  public Provider<IAllContainersState> provideIAllContainersState()
  {
    return Access.getStrictJavaProjectsState();

  }

  public Class< ? extends AbstractValidatorConfigurationBlock> bindAbstractValidatorConfigurationBlock()
  {
    return XsmpValidatorConfigurationBlock.class;
  }

  @Override
  public Class< ? extends IOutlineTreeProvider> bindIOutlineTreeProvider()
  {
    return XsmpcatOutlineTreeProvider.class;
  }

  public Class< ? extends IXsmpServiceProvider> bindIXsmpServiceProvider()
  {
    return XsmpServiceUIProvider.class;
  }

  @Override
  public Class< ? extends ITemplateProposalProvider> bindITemplateProposalProvider()
  {
    return XsmpTemplateProposalProvider.class;
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
    return XsmpcatKeywordHovers.class;
  }

  public Class< ? extends ReferenceProposalCreator> bindReferenceProposalCreator()
  {
    return XsmpcatReferenceProposalCreator.class;
  }

  public Class< ? extends CrossReferenceTemplateVariableResolver> bindCrossReferenceTemplateVariableResolver()
  {
    return XsmpcatCrossReferenceTemplateVariableResolver.class;
  }

  public Class< ? extends IResourceUIServiceProvider> bindIResourceUIServiceProvider()
  {
    return XsmpResourceUIServiceProvider.class;
  }

  @Override
  public Class< ? extends AbstractEditStrategyProvider> bindAbstractEditStrategyProvider()
  {
    return XsmpAutoEditStrategyProvider.class;
  }

  public Class< ? extends TerminalsTokenTypeToPartitionMapper> bindTerminalsTokenTypeToPartitionMapper()
  {
    return XsmpTerminalsTokenTypeToPartitionMapper.class;
  }

  public Class< ? extends XtextDocumentProvider> bindXtextDocumentProvider()
  {
    return XsmpDocumentProvider.class;
  }

  @Override
  public Class< ? extends TemplateStore> bindTemplateStore()
  {
    return XsmpTemplateStore.class;
  }

  @Override
  public Class< ? extends IXtextBuilderParticipant> bindIXtextBuilderParticipant()
  {
    return XsmpBuilderParticipant.class;
  }
}
