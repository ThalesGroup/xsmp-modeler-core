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
package org.eclipse.xsmp.formatting2;

import java.util.stream.Collectors;

import org.eclipse.xsmp.documentation.Documentation;
import org.eclipse.xsmp.documentation.TagElement;
import org.eclipse.xsmp.model.xsmp.Metadatum;
import org.eclipse.xtext.formatting2.ITextReplacer;
import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;

/**
 * Customized multiline formatter (inspired from
 * org.eclipse.xtext.formatting2.internal.MultilineCommentReplacer) that takes an ISemanticRegion as
 * insput instead of an IComment
 *
 * @author daveluy
 */
public class DocumentationReplacer implements ITextReplacer
{

  private final ISemanticRegion comment;

  /**
   * Default constructor
   *
   * @param comment
   *          the region to format
   */
  public DocumentationReplacer(ISemanticRegion comment)
  {
    this.comment = comment;
  }

  protected void append(Documentation doc, TagElement tag, StringBuilder sb,
          ITextReplacerContext context)
  {
    final var tagName = tag.getTagName(doc);

    if (tagName != null)
    {

      sb.append(tagName);
      if (!tag.fragments().isEmpty())
      {
        sb.append(" ");
        sb.append(tag.fragments().stream().map(e -> e.getText(doc)).collect(Collectors
                .joining(System.lineSeparator() + context.getIndentationString() + " *       ")));
      }
    }
    else
    {
      sb.append(tag.fragments().stream().map(e -> e.getText(doc)).collect(
              Collectors.joining(System.lineSeparator() + context.getIndentationString() + " * ")));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ITextReplacerContext createReplacements(ITextReplacerContext context)
  {
    final var elem = (Metadatum) comment.getSemanticElement();
    final var doc = elem.getXsmpcatdoc();
    final var tags = doc.tags();

    final var indentation = context.getIndentationString();
    final var newLine = System.lineSeparator() + indentation + " * ";
    final var sb = new StringBuilder();

    if (tags.isEmpty())
    {
      return context;
    }
    sb.append("/**");
    final var multiline = tags.size() > 1 || tags.get(0).fragments().size() > 1;
    if (multiline)
    {
      sb.append(newLine);
    }
    else
    {
      sb.append(" ");
    }
    final var it = tags.iterator();

    final var firstTag = it.next();

    append(doc, firstTag, sb, context);

    // add an empty line between description and first tag
    if (firstTag.getTagName(doc) == null && it.hasNext())
    {
      sb.append(newLine);
    }
    while (it.hasNext())
    {
      sb.append(newLine);
      append(doc, it.next(), sb, context);
    }

    if (multiline)
    {
      sb.append(System.lineSeparator());
      sb.append(indentation);
    }

    sb.append(" */");

    final var result = sb.toString();
    final var current = comment.getText();
    if (!result.equals(current))
    {
      context.addReplacement(comment.replaceWith(result));
    }

    return context;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ITextSegment getRegion()
  {
    return comment;
  }

}
