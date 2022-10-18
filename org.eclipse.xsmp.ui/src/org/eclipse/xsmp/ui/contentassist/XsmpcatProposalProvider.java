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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xsmp.util.Solver;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xsmp.xcatalogue.Array;
import org.eclipse.xsmp.xcatalogue.Attribute;
import org.eclipse.xsmp.xcatalogue.AttributeType;
import org.eclipse.xsmp.xcatalogue.BuiltInConstant;
import org.eclipse.xsmp.xcatalogue.BuiltInFunction;
import org.eclipse.xsmp.xcatalogue.CollectionLiteral;
import org.eclipse.xsmp.xcatalogue.Constant;
import org.eclipse.xsmp.xcatalogue.Document;
import org.eclipse.xsmp.xcatalogue.Enumeration;
import org.eclipse.xsmp.xcatalogue.EnumerationLiteral;
import org.eclipse.xsmp.xcatalogue.EventSource;
import org.eclipse.xsmp.xcatalogue.Field;
import org.eclipse.xsmp.xcatalogue.Metadatum;
import org.eclipse.xsmp.xcatalogue.NamedElement;
import org.eclipse.xsmp.xcatalogue.NativeType;
import org.eclipse.xsmp.xcatalogue.Parameter;
import org.eclipse.xsmp.xcatalogue.PrimitiveType;
import org.eclipse.xsmp.xcatalogue.Property;
import org.eclipse.xsmp.xcatalogue.Structure;
import org.eclipse.xsmp.xcatalogue.Type;
import org.eclipse.xsmp.xcatalogue.VisibilityElement;
import org.eclipse.xsmp.xcatalogue.XcatalogueFactory;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#content-assist on how to
 * customize the content assistant.
 */
public class XsmpcatProposalProvider extends AbstractXsmpcatProposalProvider
{

  /**
   * {@inheritDoc}
   */
  @Override
  public void complete_ClassModifiers(EObject model, RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    createModifierProposal("abstract", context, acceptor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void complete_FieldModifiers(EObject model, RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    createModifierProposal("input", context, acceptor);
    createModifierProposal("output", context, acceptor);
    createModifierProposal("transient", context, acceptor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void complete_PropertyModifiers(EObject model, RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    createModifierProposal("readOnly", context, acceptor);
    createModifierProposal("writeOnly", context, acceptor);
    createModifierProposal("readWrite", context, acceptor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void complete_VisibilityModifiers(EObject model, org.eclipse.xtext.RuleCall ruleCall,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    createModifierProposal("public", context, acceptor);
    createModifierProposal("protected", context, acceptor);
    createModifierProposal("private", context, acceptor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void completeKeyword(Keyword keyword, ContentAssistContext contentAssistContext,
          ICompletionProposalAcceptor acceptor)
  {

    final var proposal = createCompletionProposal(keyword.getValue(),
            getKeywordDisplayString(keyword), getImage(keyword), contentAssistContext);
    getPriorityHelper().adjustKeywordPriority(proposal, contentAssistContext.getPrefix());

    if (proposal instanceof ConfigurableCompletionProposal)
    {
      ((ConfigurableCompletionProposal) proposal).setAdditionalProposalInfo(keyword);
    }
    acceptor.accept(proposal);
  }

  private void createModifierProposal(String proposal, ContentAssistContext context,
          ICompletionProposalAcceptor acceptor)
  {
    acceptor.accept(
            createCompletionProposal(proposal, null, null, 200, context.getPrefix(), context));
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
        acceptor.accept(createTagProposal(
                "@platform name=\"cpp\", type=\"typeName\", namespace=\"ns::nns\", location=\"ns/nns/typeName.h\"",
                context));
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

    if (elem instanceof Metadatum)
    {
      NodeModelUtils.findNodesForFeature(elem, XcataloguePackage.Literals.METADATUM__DOCUMENTATION)
              .forEach(node -> commentProposal(elem.eContainer(), context, acceptor));
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

      if (proposal instanceof ConfigurableCompletionProposal)
      {
        final var cproposal = (ConfigurableCompletionProposal) proposal;
        // increase the priority for primitive types
        if (candidate.getEObjectOrProxy() instanceof PrimitiveType)
        {
          cproposal.setPriority(cproposal.getPriority() + 10);
        }
        // decrease the priority for Smp types
        else if (candidate.getQualifiedName().startsWith(QualifiedName.create("Smp")))
        {
          cproposal.setPriority(cproposal.getPriority() - 10);
        }
        if (XsmpUtil.isDeprecated(candidate))
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

    if (p instanceof ConfigurableCompletionProposal)
    {
      ((ConfigurableCompletionProposal) p).setAdditionalProposalInfo(obj);

      if (obj instanceof BuiltInConstant)
      {
        ((ConfigurableCompletionProposal) p)
                .setPriority(getPriorityHelper().getDefaultPriority() + 10);
      }
    }
    return p;
  }

  private static final List<BuiltInConstant> builtInConstants = Solver.constantMappings.keySet()
          .stream().map(s -> {
            final var cst = XcatalogueFactory.eINSTANCE.createBuiltInConstant();
            cst.setName(s);
            return cst;
          }).collect(Collectors.toList());

  private static final List<BuiltInFunction> builtInFunctions = Solver.functionMappings.keySet()
          .stream().map(s -> {
            final var cst = XcatalogueFactory.eINSTANCE.createBuiltInFunction();
            cst.setName(s);
            return cst;
          }).collect(Collectors.toList());

  @Override
  public void completeBuiltInConstant_Name(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    builtInConstants
            .forEach(cst -> acceptor.accept(createCompletionProposal(cst.getName(), context, cst)));
  }

  @Override
  public void completeBuiltInFunction_Name(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    builtInFunctions
            .forEach(cst -> acceptor.accept(createCompletionProposal(cst.getName(), context, cst)));
  }

  @Override
  public void completeEnumerationLiteralReference_Value(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {

    Type expectedType = null;

    final List<EObject> parents = new ArrayList<>();

    var ctx = model;
    if (EcoreUtil.isAncestor(ctx, context.getPreviousModel()))
    {
      ctx = context.getPreviousModel();
    }
    while (expectedType == null && ctx != null)
    {
      switch (ctx.eClass().getClassifierID())
      {
        case XcataloguePackage.CONSTANT:
          expectedType = ((Constant) ctx).getType();
          break;
        case XcataloguePackage.FIELD:
          expectedType = ((Field) ctx).getType();
          break;
        case XcataloguePackage.PARAMETER:
          expectedType = ((Parameter) ctx).getType();
          break;
        case XcataloguePackage.ATTRIBUTE:
          expectedType = ((AttributeType) ((Attribute) ctx).getType()).getType();
          break;
        default:

          parents.add(ctx);
          break;
      }
      ctx = ctx.eContainer();
    }
    if (expectedType == null)
    {
      return;
    }
    for (var i = parents.size() - 1; i >= 0; i--)
    {
      final var parent = parents.get(i);
      switch (expectedType.eClass().getClassifierID())
      {
        case XcataloguePackage.STRUCTURE:
          final var structure = (Structure) expectedType;
          var index = 0;

          if (parent instanceof CollectionLiteral)
          {
            final var collection = (CollectionLiteral) parent;
            final var cur = NodeModelUtils.findActualSemanticObjectFor(context.getCurrentNode());
            index = collection.getElements().indexOf(cur);
            if (index == -1)
            {
              index = ((CollectionLiteral) parent).getElements().size();
            }
          }
          final var field = structure.getMember().stream().filter(Field.class::isInstance)
                  .map(Field.class::cast).skip(index).findFirst().orElse(null);
          if (field != null)
          {
            expectedType = field.getType();
          }
          break;
        case XcataloguePackage.ARRAY:
          expectedType = ((Array) expectedType).getItemType();
          break;
        default:
          break;
      }
    }
    final Predicate<IEObjectDescription> filter;
    if (expectedType instanceof Enumeration)
    {
      final var enumeration = (Enumeration) expectedType;
      // provide only literals for the enumeration
      filter = d -> enumeration.getLiteral()
              .contains(EcoreUtil.resolve(d.getEObjectOrProxy(), enumeration));
    }
    else
    {
      // remove enumeration literals
      filter = d -> !(d.getEObjectOrProxy() instanceof EnumerationLiteral);
    }

    lookupCrossReference((CrossReference) assignment.getTerminal(), context, acceptor, filter);
  }

  @Override
  protected Image getImage(IEObjectDescription description)
  {

    final var object = description.getEObjectOrProxy();
    if (object.eIsProxy() && object instanceof NamedElement)
    {
      ((NamedElement) object).setDeprecated(XsmpUtil.isDeprecated(description));
      if (object instanceof VisibilityElement)
      {
        ((VisibilityElement) object).setVisibility(XsmpUtil.getVisibility(description));
      }
    }
    return getImage(object);
  }

}
