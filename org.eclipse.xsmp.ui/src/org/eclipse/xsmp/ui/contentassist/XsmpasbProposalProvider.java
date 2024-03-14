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
package org.eclipse.xsmp.ui.contentassist;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xsmp.model.xsmp.Path;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.common.base.Predicate;

/**
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#content-assist
 * on how to customize the content assistant.
 */
public class XsmpasbProposalProvider extends AbstractXsmpasbProposalProvider
{

  EReference getPathContainmentFeature(EObject path)
  {
    var parent = path.eContainer();
    while (parent instanceof final Path p)
    {
      path = p;
      parent = path.eContainer();
    }

    return path.eContainmentFeature();
  }

  Predicate<IEObjectDescription> connectionFromFilter = o -> o
          .getEClass() == XsmpPackage.Literals.INSTANCE
          || o.getEClass() == XsmpPackage.Literals.FIELD
          /* && Boolean.getBoolean(o.getUserData("output")) */
          || o.getEClass() == XsmpPackage.Literals.EVENT_SOURCE
          || o.getEClass() == XsmpPackage.Literals.REFERENCE;

  @Override
  public void completeConnection_From(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {

    lookupPathCrossReference(model, context, acceptor, connectionFromFilter);

  }

  Predicate<IEObjectDescription> connectionToFilter = o -> o
          .getEClass() == XsmpPackage.Literals.INSTANCE
          || o.getEClass() == XsmpPackage.Literals.FIELD
          /* && Boolean.getBoolean(o.getUserData("input")) */
          || o.getEClass() == XsmpPackage.Literals.EVENT_SINK;

  @Override
  public void completeConnection_To(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    lookupPathCrossReference(model, context, acceptor, connectionToFilter);

  }

  Predicate<IEObjectDescription> configurationFieldFilter = o -> o
          .getEClass() == XsmpPackage.Literals.INSTANCE
          || o.getEClass() == XsmpPackage.Literals.FIELD
          || o.getEClass() == XsmpPackage.Literals.PROPERTY;

  @Override
  public void completeConfiguration_Field(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    lookupPathCrossReference(model, context, acceptor, configurationFieldFilter);
  }

  Predicate<IEObjectDescription> containerFilter = o -> o
          .getEClass() == XsmpPackage.Literals.CONTAINER
          || o.getEClass() == XsmpPackage.Literals.INSTANCE;

  Predicate<IEObjectDescription> referenceFilter = o -> o
          .getEClass() == XsmpPackage.Literals.REFERENCE
          || o.getEClass() == XsmpPackage.Literals.INSTANCE;

  @Override
  public void completeMember_Container(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    lookupPathCrossReference(model, context, acceptor, containerFilter);

  }

  @Override
  public void complete_Path(EObject model, RuleCall ruleCall, ContentAssistContext context,
          ICompletionProposalAcceptor acceptor)
  {

    final var feature = getPathContainmentFeature(model);

    if (feature == XsmpPackage.Literals.INSTANCE_DECLARATION__CONTAINER)
    {
      lookupPathCrossReference(model, context, acceptor, containerFilter);
    }
    else if (feature == XsmpPackage.Literals.CONFIGURATION__FIELD)
    {
      lookupPathCrossReference(model, context, acceptor, configurationFieldFilter);
    }
    else if (feature == XsmpPackage.Literals.CONNECTION__FROM)
    {
      lookupPathCrossReference(model, context, acceptor, connectionFromFilter);
    }
    else if (feature == XsmpPackage.Literals.CONNECTION__TO)
    {
      lookupPathCrossReference(model, context, acceptor, connectionToFilter);
    }
    else if (feature == XsmpPackage.Literals.SCHEDULE__ENTRY_POINT
            || feature == XsmpPackage.Literals.INIT_ENTRY_POINT__ENTRY_POINT
            || feature == XsmpPackage.Literals.SUBSCRIBTION__ENTRY_POINT)
    {
      lookupPathCrossReference(model, context, acceptor, entryPointFilter);
    }
    else if (feature == XsmpPackage.Literals.CALL__ELEMENT)
    {
      lookupPathCrossReference(model, context, acceptor, callFilter);
    }
    else if (feature == XsmpPackage.Literals.PUSH__FIELD
            || feature == XsmpPackage.Literals.TRANSFER__FROM
            || feature == XsmpPackage.Literals.TRANSFER__TO
            || feature == XsmpPackage.Literals.FAILURE_ACTION__FAILURE
            || feature == XsmpPackage.Literals.FORCIBLE_ACTION__FIELD)
    {
      lookupPathCrossReference(model, context, acceptor, fieldFilter);
    }

  }

  Predicate<IEObjectDescription> entryPointFilter = o -> o
          .getEClass() == XsmpPackage.Literals.INSTANCE
          || o.getEClass() == XsmpPackage.Literals.TASK
          || o.getEClass() == XsmpPackage.Literals.ENTRY_POINT;

  @Override
  public void completeSchedule_EntryPoint(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    lookupPathCrossReference(model, context, acceptor, entryPointFilter);
  }

  @Override
  public void completeInitEntryPoint_EntryPoint(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    lookupPathCrossReference(model, context, acceptor, entryPointFilter);
  }

  @Override
  public void completeSubscribtion_EntryPoint(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    lookupPathCrossReference(model, context, acceptor, entryPointFilter);
  }

  Predicate<IEObjectDescription> callFilter = o -> o.getEClass() == XsmpPackage.Literals.INSTANCE
          || o.getEClass() == XsmpPackage.Literals.OPERATION
          || o.getEClass() == XsmpPackage.Literals.ENTRY_POINT
          || o.getEClass() == XsmpPackage.Literals.EVENT_SINK;

  @Override
  public void completeCall_Element(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    lookupPathCrossReference(model, context, acceptor, callFilter);
  }

  Predicate<IEObjectDescription> fieldFilter = o -> o.getEClass() == XsmpPackage.Literals.INSTANCE
          || o.getEClass() == XsmpPackage.Literals.FIELD;

  @Override
  public void completePush_Field(EObject model, Assignment assignment, ContentAssistContext context,
          ICompletionProposalAcceptor acceptor)
  {
    lookupPathCrossReference(model, context, acceptor, fieldFilter);
  }

  @Override
  public void completeTransfer_From(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    lookupPathCrossReference(model, context, acceptor, fieldFilter);
  }

  @Override
  public void completeTransfer_To(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    lookupPathCrossReference(model, context, acceptor, fieldFilter);
  }

  @Override
  public void completeFailureAction_Failure(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    lookupPathCrossReference(model, context, acceptor, fieldFilter);
  }

  @Override
  public void completeForcibleAction_Field(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    lookupPathCrossReference(model, context, acceptor, fieldFilter);
  }

  protected void lookupPathCrossReference(EObject model, ContentAssistContext context,
          ICompletionProposalAcceptor acceptor, Predicate<IEObjectDescription> filter)
  {
    lookupCrossReference(model, XsmpPackage.Literals.PATH__SEGMENT, acceptor, filter,
            getProposalFactory("ResolvableID", context));
  }

  @Override
  public void completePath_Segment(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    // ignore
  }

  @Override
  public void completeSubscribtion_Event(EObject model, Assignment assignment,
          ContentAssistContext context, ICompletionProposalAcceptor acceptor)
  {
    // completeRuleCall(((RuleCall)assignment.getTerminal()), context, acceptor);
    acceptor.accept(createCompletionProposal("\"eventName\"", context));
  }

}
