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
package org.eclipse.xsmp.scoping;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSetWithExpectedSize;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsmp.util.QualifiedNames;
import org.eclipse.xsmp.xcatalogue.ImportDeclaration;
import org.eclipse.xsmp.xcatalogue.ImportSection;
import org.eclipse.xsmp.xcatalogue.XcataloguePackage;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

/**
 * Language dependent configuration for the 'import' related things.
 */
public class XsmpcatImportsConfiguration
{

  @Inject
  private IGrammarAccess grammarAccess;

  protected List<EObject> findPathToImportSection()
  {
    final var rules = grammarAccess.getGrammar().getRules();
    if (!rules.isEmpty() && rules.get(0) instanceof ParserRule)
    {
      final LinkedList<EObject> pathToImportSection = newLinkedList();
      if (internalFindPathToImportSection(pathToImportSection, new HashSet<ParserRule>(),
              rules.get(0)))
      {
        return pathToImportSection;
      }
    }
    return null;
  }

  protected INode findPreviousNode(ICompositeNode node, List<EObject> pathToImportSection)
  {
    if (pathToImportSection.isEmpty() || node.getGrammarElement() != pathToImportSection.get(0))
    {
      return null;
    }
    INode currentNode = null;
    var currentParentNode = node;
    var currentDepth = 0;
    OUTER: while (currentDepth < pathToImportSection.size() - 1)
    {
      ++currentDepth;
      final var expectedRuleCall = pathToImportSection.get(currentDepth);
      final var ruleInGrammar = GrammarUtil.containingRule(expectedRuleCall);
      for (final INode childNode : currentParentNode.getChildren())
      {
        final var elementInNode = childNode.getGrammarElement();
        if (elementInNode != null)
        {
          for (final Iterator<EObject> i = ruleInGrammar.eAllContents(); i.hasNext();)
          {
            final var nextInGrammar = i.next();
            // check for type of childNode, otherwise we get a ClassCastException
            // see https://bugs.eclipse.org/bugs/show_bug.cgi?id=407390
            if (nextInGrammar == expectedRuleCall && childNode instanceof ICompositeNode)
            {
              currentParentNode = (ICompositeNode) childNode;
              continue OUTER;
            }
            if (nextInGrammar == elementInNode)
            {
              break;
            }
          }
        }
        currentNode = childNode;
      }
    }
    return currentNode;
  }

  /**
   * @param resource
   * @return the implicitly imported packages
   */
  public Set<String> getImplicitlyImportedPackages(XtextResource resource)
  {
    final Set<String> implicitlyImportedPackages = newHashSetWithExpectedSize(2);

    implicitlyImportedPackages.add(QualifiedNames._Smp.toString());
    implicitlyImportedPackages.add(QualifiedNames._Attributes.toString());
    return implicitlyImportedPackages;
  }

  /**
   * @param resource
   * @return the import section
   */
  public ImportSection getImportSection(XtextResource resource)
  {
    final var contents = resource.getContents();
    if (!contents.isEmpty())
    {
      for (final Iterator<EObject> i = contents.get(0).eAllContents(); i.hasNext();)
      {
        final var next = i.next();
        if (next instanceof ImportSection)
        {
          return (ImportSection) next;
        }
      }
    }
    return null;
  }

  /**
   * @param resource
   * @return the offset of the import section
   */
  public int getImportSectionOffset(XtextResource resource)
  {
    if (resource.getParseResult() != null && resource.getParseResult().getRootNode() != null)
    {
      final var pathToImportSection = findPathToImportSection();
      if (pathToImportSection != null)
      {
        final var node = findPreviousNode(resource.getParseResult().getRootNode(),
                pathToImportSection);
        if (node != null)
        {
          return node.getTotalEndOffset();
        }
      }
    }
    return 0;
  }

  /**
   * @param importDeclaration
   * @return the legacy sintax of the import declaration
   */

  public String getLegacyImportSyntax(ImportDeclaration importDeclaration)
  {
    /*
     * We cannot just use importDeclaration.getImportedTypeName since that would return the name
     * from the resolved type rather than the concrete syntax.
     */
    final var list = NodeModelUtils.findNodesForFeature(importDeclaration,
            XcataloguePackage.Literals.IMPORT_DECLARATION__IMPORTED_TYPE);
    if (list.isEmpty())
    {
      return null;
    }
    final var singleNode = list.get(0);
    if (singleNode.getText().indexOf('$') < 0)
    {
      return null;
    }
    final var sb = new StringBuilder();
    for (final ILeafNode node : singleNode.getLeafNodes())
    {
      if (!node.isHidden())
      {
        sb.append(node.getText().replace("^", ""));
      }
    }
    return sb.toString();
  }

  protected boolean internalFindPathToImportSection(LinkedList<EObject> pathToImportSection,
          Set<ParserRule> seenRules, EObject ruleOrRuleCall)
  {
    ParserRule rule = null;
    EClassifier returnType;
    if (ruleOrRuleCall instanceof ParserRule)
    {
      rule = (ParserRule) ruleOrRuleCall;
    }
    else
    {
      rule = (ParserRule) ((RuleCall) ruleOrRuleCall).getRule();
    }
    if (seenRules.contains(rule))
    {
      return false;
    }
    seenRules.add(rule);
    pathToImportSection.addLast(ruleOrRuleCall);
    returnType = rule.getType().getClassifier();
    if (returnType instanceof EClass
            && XcataloguePackage.Literals.IMPORT_SECTION.isSuperTypeOf((EClass) returnType))
    {
      return true;
    }
    for (final RuleCall containedRuleCall : GrammarUtil.containedRuleCalls(rule))
    {
      if (containedRuleCall.getRule() instanceof ParserRule
              && internalFindPathToImportSection(pathToImportSection, seenRules, containedRuleCall))
      {
        return true;
      }
    }
    pathToImportSection.removeLast();
    return false;
  }
}
