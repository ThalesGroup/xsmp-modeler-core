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

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

public class Documentation
{

  private final EList<TagElement> tags = new BasicEList<>();

  public Documentation(Documentation other)
  {
    other.tags.forEach(t -> tags.add(new TagElement(t)));
  }

  public Documentation(String text)
  {
    if (text == null || text.isEmpty())
    {
      return;
    }

    var offset = text.indexOf("/**");
    if (offset == -1)
    {
      return;
    }
    offset += 3; // skip "/**"
    offset = skipWSOrNewLine(text, offset);
    TagElement tag = null;
    while (true)
    {
      if (isNewTag(text.charAt(offset)))
      {
        final var startPosition = offset;
        offset = consumeToken(text, offset);
        tag = new TagElement(startPosition, text.substring(startPosition, offset));
        tags.add(tag);

        offset = skipWS(text, offset);
        if (isNewLine(text.charAt(offset)))
        {
          offset = skipWSOrNewLine(text, offset);
          continue;
        }
        offset = skipWSOrNewLine(text, offset);
        if ("@param".equals(tag.getTagName()))
        {

          final var lastToken = consumeToken(text, offset);

          if (offset != lastToken)
          {
            tag.fragments().add(new TextElement(offset, text.substring(offset, lastToken)));
          }
          offset = skipWS(text, lastToken);
          if (isNewLine(text.charAt(offset)))
          {
            offset = skipWSOrNewLine(text, offset);
            continue;
          }
          offset = skipWSOrNewLine(text, offset);
        }

      }
      if (isEnd(text, offset))
      {
        break;

      }
      if (tag == null)
      {
        tag = new TagElement(offset, null);
        tags.add(tag);
      }

      var lastToken = offset;
      final var startPosition = offset;
      // read all token until
      while (true)
      {
        offset = lastToken = consumeToken(text, offset);

        offset = skipWS(text, offset);
        if (isEnd(text, offset) || isNewLine(text.charAt(offset)))
        {
          break;
        }

      }

      tag.fragments().add(new TextElement(startPosition, text.substring(startPosition, lastToken)));

      offset = skipWSOrNewLine(text, offset);
      if (isEnd(text, offset))
      {
        break;
      }
    }
  }

  // skip chars until a space or new line or the comment end
  int consumeToken(String text, int offset)
  {
    while (!isSpace(text.charAt(offset)) && !isEnd(text, offset) && !isNewLine(text.charAt(offset)))
    {
      offset++;
    }
    return offset;
  }

  public EList<TagElement> tags()
  {
    return tags;
  }

  private static boolean isEnd(String text, int offset)
  {
    var star = false;
    while (text.charAt(offset) == '*')
    {
      star = true;

      offset++;
    }

    return star && text.charAt(offset) == '/';
  }

  private static boolean isNewLine(char c)
  {
    return c == '\n';
  }

  private static boolean isNewTag(char c)
  {
    return c == '@';
  }

  private static boolean isSpace(char c)
  {
    return c == ' ' || c == '\t' || c == '\r';
  }

  private static int skipWS(String text, int offset)
  {
    while (isSpace(text.charAt(offset)))
    {
      offset++;
    }
    return offset;
  }

  private static int skipWSOrNewLine(String text, int offset)
  {
    var newLine = false;
    var skipStar = false;
    var star = false;
    while (isSpace(text.charAt(offset))
            || (star = skipStar && text.charAt(offset) == '*' && !isEnd(text, offset))
            || (newLine = isNewLine(text.charAt(offset))))
    {

      skipStar |= newLine;

      skipStar &= !star;
      offset++;

    }
    return offset;
  }

  @Override
  public String toString()
  {

    final var sb = new StringBuilder();
    final var newLine = System.lineSeparator() + " * ";
    if (tags.isEmpty())
    {
      return "";
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

    sb.append(firstTag);

    // add an empty line between description and first tag
    if (firstTag.getTagName() == null && it.hasNext())
    {
      sb.append(newLine);
    }
    while (it.hasNext())
    {
      sb.append(newLine);
      sb.append(it.next());
    }

    if (multiline)
    {
      sb.append(System.lineSeparator());
    }
    sb.append(" */");

    return sb.toString();
  }
}
