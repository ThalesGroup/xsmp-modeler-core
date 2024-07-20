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
package org.eclipse.xsmp.ui.contentassist;

import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xsmp.ide.contentassist.DefaultValueProvider;
import org.eclipse.xsmp.model.xsmp.Array;
import org.eclipse.xsmp.model.xsmp.Metadatum;
import org.eclipse.xsmp.model.xsmp.Structure;
import org.eclipse.xsmp.services.XsmpcoreGrammarAccess;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ITemplateAcceptor;
import org.eclipse.xtext.ui.editor.templates.ContextTypeIdHelper;
import org.eclipse.xtext.ui.editor.templates.DefaultTemplateProposalProvider;
import org.eclipse.xtext.ui.editor.templates.XtextTemplateContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@SuppressWarnings("deprecation")
@Singleton
public class XsmpTemplateProposalProvider extends DefaultTemplateProposalProvider
{

  @Inject
  private DefaultValueProvider defaultValueProvider;

  @Inject
  private XsmpcoreGrammarAccess grammar;

  @Inject
  private IImageHelper imageHelper;

  @Inject
  protected XsmpUtil xsmpUtil;

  @Inject
  public XsmpTemplateProposalProvider(TemplateStore templateStore, ContextTypeRegistry registry,
          ContextTypeIdHelper helper)
  {
    super(templateStore, registry, helper);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createTemplates(TemplateContext templateContext, ContentAssistContext context,
          ITemplateAcceptor acceptor)
  {
    super.createTemplates(templateContext, context, acceptor);

    // add a template proposal for the default value
    final var name = grammar.getExpressionAccess().getRule().getName();
    if (name.equals(templateContext.getContextType().getName())
    /* && !(context.getCurrentModel() instanceof Expression) */)
    {
      final var type = xsmpUtil.getType(context.getCurrentNode(), context.getCurrentModel());
      if (type instanceof Structure || type instanceof Array)
      {

        final var defaultValue = defaultValueProvider.getDefautValue(type);
        if (defaultValue != null)
        {
          final var template = new Template("DefaultValue", "Generate the Default Value",
                  "DefaultValue", defaultValue, true);

          acceptor.accept(createProposal(template, templateContext, context, getImage(template),
                  getRelevance(template)));
        }
      }
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Image getImage(Template template)
  {

    var image = imageHelper.getImage("template_" + template.getName() + ".png");

    if (image == null)
    {
      image = imageHelper.getImage(
              "platform:/plugin/org.eclipse.xsmp.ui/template_" + template.getName() + ".png");
    }
    if (image == null)
    {
      image = super.getImage(template);
    }

    return image;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getRelevance(Template template)
  {
    return 1000;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean validate(Template template, TemplateContext context)
  {

    final var valid = super.validate(template, context);
    if (valid && context instanceof final XtextTemplateContext xtextContext)
    {
      final var ctx = xtextContext.getContentAssistContext();

      // do not provide templates between the documentation and the element
      if ("@".equals(ctx.getPrefix()) || ctx.getCurrentModel() instanceof Metadatum)
      {
        return false;
      }

    }
    return valid;
  }

}
