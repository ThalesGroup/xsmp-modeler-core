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
package org.eclipse.xsmp.ui.contentassist;

import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xsmp.services.XsmpcatGrammarAccess;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Metadatum;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
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
public class XsmpcatTemplateProposalProvider extends DefaultTemplateProposalProvider
{

  @Inject
  private DefaultValueProvider defaultValueProvider;

  @Inject
  private XsmpcatGrammarAccess grammar;

  @Inject
  private IImageHelper imageHelper;

  @Inject
  public XsmpcatTemplateProposalProvider(TemplateStore templateStore, ContextTypeRegistry registry,
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
    if (name.equals(templateContext.getContextType().getName()))
    {

      final var type = findTypeFromContext(context);
      if (type != null)
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

  Type findTypeFromContext(ContentAssistContext context)
  {
    final var currentModel = context.getCurrentModel();

    switch (currentModel.eClass().getClassifierID())
    {
      case XcataloguePackage.FIELD:
        return ((Field) currentModel).getType();
      case XcataloguePackage.CONSTANT:
        return ((Constant) currentModel).getType();
      case XcataloguePackage.PARAMETER:
        return ((Parameter) currentModel).getType();

      default:
        return null;
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
    return 301;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean validate(Template template, TemplateContext context)
  {

    final var valid = super.validate(template, context);
    if (valid && context instanceof XtextTemplateContext)
    {
      final var ctx = ((XtextTemplateContext) context).getContentAssistContext();

      // do not provide templates between the documentation and the element
      if ("@".equals(ctx.getPrefix()) || ctx.getCurrentModel() instanceof Metadatum)
      {
        return false;
      }

    }
    return valid;
  }

}
