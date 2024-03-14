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
package org.eclipse.xsmp.ui;

import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xsmp.ide.contentassist.IReferenceFilter;
import org.eclipse.xsmp.ide.contentassist.XsmpcatReferenceFilter;
import org.eclipse.xsmp.ide.hover.IKeywordHovers;
import org.eclipse.xsmp.ide.hover.XsmpasbKeywordHovers;
import org.eclipse.xsmp.ui.autoedit.XsmpAutoEditStrategyProvider;
import org.eclipse.xsmp.ui.autoedit.XsmpMultiLineTerminalEditStrategy;
import org.eclipse.xsmp.ui.containers.XsmpProjectsState;
import org.eclipse.xsmp.ui.contentassist.XsmpTemplateContextType;
import org.eclipse.xsmp.ui.contentassist.XsmpTemplateProposalProvider;
import org.eclipse.xsmp.ui.editor.model.XsmpDocumentProvider;
import org.eclipse.xsmp.ui.editor.model.XsmpPreferenceAccess;
import org.eclipse.xsmp.ui.editor.model.XsmpTerminalsTokenTypeToPartitionMapper;
import org.eclipse.xsmp.ui.folding.XsmpFoldingRegionProvider;
import org.eclipse.xsmp.ui.highlighting.XsmpAntlrTokenToAttributeIdMapper;
import org.eclipse.xsmp.ui.highlighting.XsmpHighlightingConfiguration;
import org.eclipse.xsmp.ui.highlighting.XsmpSemanticHighlightingCalculator;
import org.eclipse.xsmp.ui.hover.XsmpDispatchingEObjectTextHover;
import org.eclipse.xsmp.ui.hover.XsmpEObjectHoverProvider;
import org.eclipse.xsmp.ui.quickfix.XsmpTextEditComposer;
import org.eclipse.xsmp.ui.resource.XsmpResourceUIServiceProvider;
import org.eclipse.xsmp.ui.template.XsmpTemplateStore;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider;
import org.eclipse.xtext.ui.editor.autoedit.MultiLineTerminalsEditStrategy;
import org.eclipse.xtext.ui.editor.contentassist.ITemplateProposalProvider;
import org.eclipse.xtext.ui.editor.contentassist.XtextContentAssistProcessor;
import org.eclipse.xtext.ui.editor.folding.IFoldingRegionProvider;
import org.eclipse.xtext.ui.editor.hover.IEObjectHover;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.model.IResourceForEditorInputFactory;
import org.eclipse.xtext.ui.editor.model.ResourceForIEditorInputFactory;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.ui.editor.model.edit.ITextEditComposer;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreInitializer;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.templates.XtextTemplateContextType;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.resource.IResourceUIServiceProvider;
import org.eclipse.xtext.ui.resource.SimpleResourceSetProvider;
import org.eclipse.xtext.ui.shared.Access;

import com.google.inject.Binder;
import com.google.inject.Provider;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
public class XsmpasbUiModule extends AbstractXsmpasbUiModule
{

  public XsmpasbUiModule(AbstractUIPlugin plugin)
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
            .toInstance(".,@:");
  }

  public void configureSaveActionsPreferenceStoreInitializer(Binder binder)
  {
    binder.bind(IPreferenceStoreInitializer.class)
            .annotatedWith(Names.named("saveActionsPreferenceInitializer"))
            .to(XsmpPreferenceAccess.Initializer.class);
  }

  @Override
  public Provider<IAllContainersState> provideIAllContainersState()
  {
    return Access.<IAllContainersState> contributedProvider(XsmpProjectsState.class);
  }

  @Override
  public Class< ? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory()
  {
    return ResourceForIEditorInputFactory.class;
  }

  @Override
  public Class< ? extends IResourceSetProvider> bindIResourceSetProvider()
  {
    return SimpleResourceSetProvider.class;
  }

  public Class< ? extends IReferenceFilter> bindIReferenceFilter()
  {
    return XsmpcatReferenceFilter.class;
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
    return XsmpasbKeywordHovers.class;
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
}
