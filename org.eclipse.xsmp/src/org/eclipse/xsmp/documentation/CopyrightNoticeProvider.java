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
package org.eclipse.xsmp.documentation;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.services.XsmpcoreGrammarAccess;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Provide the copyright notice from a Resource
 */
@Singleton
public class CopyrightNoticeProvider
{
  @Inject
  private IResourceScopeCache cache;

  @Inject
  private XsmpcoreGrammarAccess grammar;

  public String getCopyrightNotice(final Resource resource)
  {
    if (resource instanceof final XtextResource xtextResource)
    {
      return cache.get(CopyrightNoticeProvider.class, resource,
              () -> computeCopyrightNotice(xtextResource));
    }
    return null;
  }

  public String getCopyrightNotice(final Resource resource, String prefix)
  {
    return cache.get(Tuples.pair(CopyrightNoticeProvider.class, prefix), resource,
            () -> computeCopyrightNotice(resource, prefix));
  }

  protected String computeCopyrightNotice(Resource resource, String prefix)
  {
    final var text = getCopyrightNotice(resource);
    if (text != null)
    {
      return prefix + text.replace(System.lineSeparator(), System.lineSeparator() + prefix);
    }
    return null;
  }

  private static final Pattern slPattern = Pattern.compile("^// ?");

  private static final Pattern mlEndsPattern = Pattern.compile("(?:^/\\*+)|(?:\\*+/$)");

  private static final Pattern mlMiddlePattern = Pattern.compile("\\r?\\n ?\\* ?");

  protected String computeCopyrightNotice(XtextResource resource)
  {

    final var node = resource.getParseResult().getRootNode();

    if (node != null)
    {
      for (final ILeafNode leafNode : node.getLeafNodes())
      {
        if (!leafNode.isHidden())
        {
          break;
        }
        if (grammar.getML_COMMENTRule().equals(leafNode.getGrammarElement()))
        {
          final var comment = leafNode.getText();
          return processVariables(
                  mlMiddlePattern.matcher(mlEndsPattern.matcher(comment).replaceAll(""))
                          .replaceAll(System.lineSeparator()).strip());
        }
        if (grammar.getSL_COMMENTRule().equals(leafNode.getGrammarElement()))
        {
          final var comment = new StringBuilder()
                  .append(slPattern.matcher(leafNode.getText()).replaceAll(""));

          var sibling = leafNode.getNextSibling();
          while (sibling != null && grammar.getSL_COMMENTRule().equals(sibling.getGrammarElement()))
          {
            comment.append(slPattern.matcher(sibling.getText()).replaceAll(""));
            sibling = sibling.getNextSibling();
          }

          return processVariables(comment.toString().trim());
        }
      }
    }

    return null;
  }

  String processVariables(String input)
  {
    final var date = new java.util.Date();
    return input.replace("${year}", Integer.toString(Calendar.getInstance().get(Calendar.YEAR)))
            .replace("${user}", System.getProperty("user.name"))
            .replace("${time}", DateFormat.getTimeInstance().format(date))
            .replace("${date}", DateFormat.getDateInstance().format(date));

  }

}
