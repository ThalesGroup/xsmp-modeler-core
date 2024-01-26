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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xsmp.services.XsmpcatGrammarAccess;
import org.eclipse.xsmp.ui.highlighting.XsmpHighlightingConfiguration;
import org.eclipse.xsmp.util.PrimitiveTypeKind;
import org.eclipse.xsmp.util.QualifiedNames;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.AttributeType;
import org.eclipse.xsmp.xcatalogue.BuiltInConstant;
import org.eclipse.xsmp.xcatalogue.BuiltInFunction;
import org.eclipse.xsmp.xcatalogue.Class;
import org.eclipse.xsmp.xcatalogue.Component;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Document;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.EventSource;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Metadatum;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.NativeType;
import org.eclipse.xsmp.xcatalogue.PrimitiveType;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;
import org.eclipse.xtext.ui.label.StylerFactory;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#content-assist
 * on how to customize the content assistant.
 */
public class XsmpcatProposalProvider extends AbstractXsmpcatProposalProvider
{
  @Inject
  public XsmpcatProposalProvider(XsmpUtil xsmpUtil)
  {
    this.xsmpUtil = xsmpUtil;
    builtInConstants = xsmpUtil.getSolver().constantMappings.keySet().stream().map(s -> {
      final var cst = XcatalogueFactory.eINSTANCE.createBuiltInConstant();
      cst.setName(s);
      return cst;
    }).toList();
    builtInFunctions = xsmpUtil.getSolver().functionMappings.keySet().stream().map(s -> {
      final var cst = XcatalogueFactory.eINSTANCE.createBuiltInFunction();
      cst.setName(s);
      return cst;
    }).toList();
  }

  protected final List<BuiltInConstant> builtInConstants;

  protected final List<BuiltInFunction> builtInFunctions;

  @Inject
  protected XsmpcatGrammarAccess grammarAccess;

  @Inject
  protected StylerFactory stylerFactory;

  @Inject
  protected XsmpHighlightingConfiguration highlightingConfiguration;

  protected XsmpUtil xsmpUtil;

  protected static final Set<String> FILTERED_KEYWORDS = Sets.newHashSet("true", "false", "$",
          "struct", "model", "service", "array", "using", "string", "integer", "float", "interface",
          "class", "exception", "public", "private", "protected", "field", "constant", "def",
          "reference", "container", "entrypoint", "native", "primitive", "readOnly", "readWrite",
          "writeOnly", "input", "output", "transient", "abstract", "enum", "event", "attribute",
          "eventsink", "eventsource", "namespace", "association", "property",
          /* "{", "}", */ "nullptr", "default", "typename");

  /**
   * {@inheritDoc}
   */
  @Override
  public void complete_ClassModifiers(EObject model, RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    final var cur = NodeModelUtils.findActualSemanticObjectFor(context.getCurrentNode());
    if (cur instanceof final Class cls && !cls.isAbstract()
            || cur instanceof final Component cmp && !cmp.isAbstract())
    {
      acceptor.accept(createKeywordCompletionProposal("abstract", context));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void complete_FieldModifiers(EObject model, RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    final var cur = NodeModelUtils.findActualSemanticObjectFor(context.getCurrentNode());
    if (cur instanceof final Field field)
    {
      if (!field.isInput())
      {
        acceptor.accept(createKeywordCompletionProposal("input", context));
      }
      if (!field.isOutput())
      {
        acceptor.accept(createKeywordCompletionProposal("output", context));
      }
      if (!field.isTransient())
      {
        acceptor.accept(createKeywordCompletionProposal("transient", context));
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void complete_PropertyModifiers(EObject model, RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    final var cur = NodeModelUtils.findActualSemanticObjectFor(context.getCurrentNode());
    if (cur instanceof final Property p && !p.isSetAccess())
    {
      acceptor.accept(createKeywordCompletionProposal("readOnly", context));
      acceptor.accept(createKeywordCompletionProposal("writeOnly", context));
      acceptor.accept(createKeywordCompletionProposal("readWrite", context));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void complete_VisibilityModifiers(EObject model, org.eclipse.xtext.RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    final var cur = NodeModelUtils.findActualSemanticObjectFor(context.getCurrentNode());
    if (cur instanceof final VisibilityElement elem && !elem.isSetVisibility()
            && elem.isUseVisibility())
    {

      acceptor.accept(createKeywordCompletionProposal(
              grammarAccess.getVisibilityModifiersAccess().getPrivateKeyword_0().getValue(),
              context));
      acceptor.accept(createKeywordCompletionProposal(
              grammarAccess.getVisibilityModifiersAccess().getProtectedKeyword_1().getValue(),
              context));
      acceptor.accept(createKeywordCompletionProposal(
              grammarAccess.getVisibilityModifiersAccess().getPublicKeyword_2().getValue(),
              context));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void completeKeyword(Keyword keyword, ContentAssistContext contentAssistContext,
          ICompletionProposalAcceptor acceptor)
  {
    if (FILTERED_KEYWORDS.contains(keyword.getValue()))
    {
      return;
    }

    final var proposal = createCompletionProposal(keyword.getValue(),
            getKeywordDisplayString(keyword), getImage(keyword), contentAssistContext);
    getPriorityHelper().adjustKeywordPriority(proposal, contentAssistContext.getPrefix());

    if (proposal instanceof final ConfigurableCompletionProposal p)
    {
      p.setAdditionalProposalInfo(keyword);
    }
    acceptor.accept(proposal);
  }

  @Override
  protected StyledString getKeywordDisplayString(Keyword keyword)
  {
    final var string = keyword.getValue();
    if (string.matches("[A-Za-z0-9 ]+"))
    {
      return stylerFactory.createFromXtextStyle(string,
              highlightingConfiguration.keywordTextStyle());
    }
    return new StyledString(string);
  }

  protected void commentProposal(EObject obj, ContentAssistContext context,
          ICompletionProposalAcceptor acceptor)
  {

    if (obj instanceof NamedElement)
    {
      acceptor.accept(createTagProposal("@deprecated", context));
    }
    if (obj instanceof Document)
    {
      final var creator = System.getProperty("user.name");
      if (!((List< ? >) obj.eGet(XcataloguePackage.Literals.DOCUMENT__CREATOR)).contains(creator))
      {
        acceptor.accept(createTagProposal("@creator " + creator, context));
      }

      if (!obj.eIsSet(XcataloguePackage.Literals.DOCUMENT__DATE))
      {
        acceptor.accept(createTagProposal("@date " + Instant.now().truncatedTo(ChronoUnit.SECONDS),
                context));
      }
      if (!obj.eIsSet(XcataloguePackage.Literals.DOCUMENT__TITLE))
      {
        acceptor.accept(createTagProposal(
                "@title " + obj.eGet(XcataloguePackage.Literals.NAMED_ELEMENT__NAME), context));
      }

      if (!obj.eIsSet(XcataloguePackage.Literals.DOCUMENT__VERSION))
      {
        acceptor.accept(createTagProposal("@version 0.0.1", context));
      }
    }
    else if (obj instanceof Type)
    {
      if (!obj.eIsSet(XcataloguePackage.Literals.TYPE__UUID))
      {
        acceptor.accept(createTagProposal("@uuid " + UUID.randomUUID().toString(), context));
      }
      if (obj instanceof AttributeType)
      {
        if (!obj.eIsSet(XcataloguePackage.Literals.ATTRIBUTE_TYPE__ALLOW_MULTIPLE))
        {
          acceptor.accept(createTagProposal("@allowMultiple", context));
        }

        if (!obj.eIsSet(XcataloguePackage.Literals.ATTRIBUTE_TYPE__USAGE))
        {
          acceptor.accept(createTagProposal("@usage ", context));
        }
      }
      else if (obj instanceof org.eclipse.xsmp.xcatalogue.Float
              && !obj.eIsSet(XcataloguePackage.Literals.FLOAT__UNIT)
              || obj instanceof org.eclipse.xsmp.xcatalogue.Integer
                      && !obj.eIsSet(XcataloguePackage.Literals.INTEGER__UNIT))
      {
        acceptor.accept(createTagProposal("@unit ", context));
      }
      else if (obj instanceof NativeType)
      {
        if (!obj.eIsSet(XcataloguePackage.Literals.NATIVE_TYPE__TYPE))
        {
          acceptor.accept(createTagProposal("@type ", context));
        }
        if (!obj.eIsSet(XcataloguePackage.Literals.NATIVE_TYPE__NAMESPACE))
        {
          acceptor.accept(createTagProposal("@namespace ", context));
        }
        if (!obj.eIsSet(XcataloguePackage.Literals.NATIVE_TYPE__LOCATION))
        {
          acceptor.accept(createTagProposal("@location ", context));
        }
      }
      else
      {
        // ignore
      }
    }
    else if (obj instanceof Property)
    {
      if (!obj.eIsSet(XcataloguePackage.Literals.PROPERTY__CATEGORY))
      {
        acceptor.accept(createTagProposal("@category ", context));
      }
    }
    else if (obj instanceof EventSource)
    {
      if (!obj.eIsSet(XcataloguePackage.Literals.EVENT_SOURCE__SINGLECAST))
      {
        acceptor.accept(createTagProposal("@singlecast ", context));
      }
    }
    else
    {
      // ignore
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createProposals(ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {

    final var elem = context.getCurrentNode().getSemanticElement();
    final var gr = context.getCurrentNode().getGrammarElement();
    if (gr instanceof final RuleCall r)
    {
      final var rule = r.getRule();

      if (elem instanceof Metadatum && rule == grammarAccess.getML_DOCUMENTATIONRule())
      {
        NodeModelUtils
                .findNodesForFeature(elem, XcataloguePackage.Literals.METADATUM__DOCUMENTATION)
                .forEach(node -> commentProposal(elem.eContainer(), context, acceptor));
      }
    }
    super.createProposals(context, acceptor);

  }

  ConfigurableCompletionProposal createTagProposal(String proposal, ContentAssistContext context)
  {
    final var p = doCreateProposal(proposal, null, null, context.getOffset(), proposal.length());
    p.setAutoInsertable(false);
    p.setMatcher(context.getMatcher());
    p.setReplaceContextLength(getReplacementContextLength(context));
    return p;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ConfigurableCompletionProposal doCreateProposal(String proposal,
          StyledString displayString, Image image, int replacementOffset, int replacementLength)
  {
    final var result = super.doCreateProposal(proposal, displayString, image, replacementOffset,
            replacementLength);
    result.setHover(getHover());

    return result;
  }

  @Override
  protected Function<IEObjectDescription, ICompletionProposal> getProposalFactory(String ruleName,
          ContentAssistContext contentAssistContext)
  {
    return new XsmpProposalCreator(contentAssistContext, ruleName, getQualifiedNameConverter());
  }

  protected class XsmpProposalCreator extends DefaultProposalCreator
  {

    public XsmpProposalCreator(ContentAssistContext contentAssistContext, String ruleName,
            IQualifiedNameConverter qualifiedNameConverter)
    {
      super(contentAssistContext, ruleName, qualifiedNameConverter);
    }

    @Override
    public ICompletionProposal apply(IEObjectDescription candidate)
    {
      final var proposal = super.apply(candidate);

      if (proposal instanceof final ConfigurableCompletionProposal cproposal)
      {
        // increase the priority for primitive types
        if (candidate.getEObjectOrProxy() instanceof PrimitiveType)
        {
          cproposal.setPriority(cproposal.getPriority() + 10);
        }
        // decrease the priority for Smp types
        else if (candidate.getQualifiedName().startsWith(QualifiedNames.Smp))
        {
          cproposal.setPriority(cproposal.getPriority() - 10);
        }
        if (xsmpUtil.isDeprecated(candidate))
        {
          cproposal.setPriority(cproposal.getPriority() - 15);
        }
      }

      return proposal;
    }
  }

  protected ICompletionProposal createCompletionProposal(String proposal,
          ContentAssistContext contentAssistContext, EObject obj)
  {
    final var p = createCompletionProposal(proposal, contentAssistContext);

    if (p instanceof final ConfigurableCompletionProposal cfg)
    {
      cfg.setAdditionalProposalInfo(obj);

      if (obj instanceof BuiltInConstant)
      {
        ((ConfigurableCompletionProposal) p)
                .setPriority(getPriorityHelper().getDefaultPriority() + 10);
      }
    }
    return p;
  }

  @Override
  public void complete_BuiltInConstant(EObject model, RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    switch (xsmpUtil.getPrimitiveTypeKind(xsmpUtil.getType(context.getCurrentNode(), model)))
    {
      case FLOAT32, FLOAT64:
        builtInConstants.forEach(cst -> acceptor
                .accept(createCompletionProposal("$" + cst.getName(), context, cst)));
        break;
      default:
        break;
    }
  }

  @Override
  public void completeBuiltInConstant_Name(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    builtInConstants
            .forEach(cst -> acceptor.accept(createCompletionProposal(cst.getName(), context, cst)));
  }

  @Override
  public void complete_BuiltInFunction(EObject model, RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    switch (xsmpUtil.getPrimitiveTypeKind(xsmpUtil.getType(context.getCurrentNode(), model)))
    {
      case FLOAT32, FLOAT64:
        builtInFunctions.forEach(cst -> acceptor
                .accept(createCompletionProposal("$" + cst.getName(), context, cst)));
        break;
      default:
        break;
    }

  }

  @Override
  public void completeBuiltInFunction_Name(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    builtInFunctions
            .forEach(cst -> acceptor.accept(createCompletionProposal(cst.getName(), context, cst)));
  }

  @Override
  public void completeNamedElementReference_Value(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    final var expectedType = xsmpUtil.getType(context.getCurrentNode(), model);

    final Predicate<IEObjectDescription> filter;
    if (expectedType == null)
    {
      filter = Predicates.<IEObjectDescription> alwaysFalse();
    }
    else
    {
      filter = d -> {
        final var obj = EcoreUtil.resolve(d.getEObjectOrProxy(), model);
        final Type type;
        if (obj instanceof final Constant cst && xsmpUtil.isVisibleFrom(d, model))
        {
          type = cst.getType();
        }
        else if (obj instanceof EnumerationLiteral)
        {
          type = (Type) obj.eContainer();
        }
        else
        {
          type = null;
        }
        return expectedType == type;
      };
    }
    lookupCrossReference((CrossReference) assignment.getTerminal(), context, acceptor, filter);
  }

  @Override
  public void complete_IntegerLiteral(EObject model, RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {

    switch (xsmpUtil.getPrimitiveTypeKind(xsmpUtil.getType(context.getCurrentNode(), model)))
    {
      case INT8, INT16, INT32:
        acceptor.accept(createCompletionProposal("0", context));
        break;
      case INT64, DATE_TIME, DURATION:
        acceptor.accept(createCompletionProposal("0L", context));
        break;
      case UINT8, UINT16, UINT32:
        acceptor.accept(createCompletionProposal("0U", context));
        break;
      case UINT64:
        acceptor.accept(createCompletionProposal("0UL", context));
        break;
      default:
        break;
    }

  }

  @Override
  public void complete_FloatingLiteral(EObject model, RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {

    final var primitiveType = xsmpUtil
            .getPrimitiveTypeKind(xsmpUtil.getType(context.getCurrentNode(), model));
    if (primitiveType == PrimitiveTypeKind.FLOAT32)
    {
      acceptor.accept(createCompletionProposal("0.0f", context));
    }
    else if (primitiveType == PrimitiveTypeKind.FLOAT64)
    {
      acceptor.accept(createCompletionProposal("0.0", context));
    }

  }

  @Override
  public void complete_StringLiteral(EObject model, RuleCall ruleCall, ContentAssistContext context,
          ICompletionProposalAcceptor acceptor)
  {
    if (xsmpUtil.getPrimitiveTypeKind(
            xsmpUtil.getType(context.getCurrentNode(), model)) == PrimitiveTypeKind.STRING8)
    {
      acceptor.accept(createCompletionProposal("\"\"", context));
    }
  }

  @Override
  public void complete_CharacterLiteral(EObject model, RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    if (xsmpUtil.getPrimitiveTypeKind(
            xsmpUtil.getType(context.getCurrentNode(), model)) == PrimitiveTypeKind.CHAR8)
    {
      acceptor.accept(createCompletionProposal("'\\0'", context));
    }
  }

  @Override
  public void complete_BooleanLiteral(EObject model, RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    if (xsmpUtil.getPrimitiveTypeKind(
            xsmpUtil.getType(context.getCurrentNode(), model)) == PrimitiveTypeKind.BOOL)
    {
      acceptor.accept(createKeywordCompletionProposal("false", context));
      acceptor.accept(createKeywordCompletionProposal("true", context));
    }
  }

  protected ICompletionProposal createKeywordCompletionProposal(String string,
          ContentAssistContext context)
  {
    final var styledString = stylerFactory.createFromXtextStyle(string,
            highlightingConfiguration.keywordTextStyle());

    return createCompletionProposal(string, styledString, null, context);
  }

  @Override
  protected Image getImage(IEObjectDescription description)
  {

    final var object = description.getEObjectOrProxy();
    if (object.eIsProxy() && object instanceof final NamedElement elem)
    {
      elem.setDeprecated(xsmpUtil.isDeprecated(description));
      if (object instanceof final VisibilityElement v)
      {
        v.setVisibility(xsmpUtil.getVisibility(description));
      }
    }
    return getImage(object);
  }

}
