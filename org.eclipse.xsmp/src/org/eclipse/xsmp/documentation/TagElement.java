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
package org.eclipse.xsmp.documentation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TagElement
{

  private final List<TextElement> fragments = new ArrayList<>();

  private final int startPosition;

  private final int endPosition;

  public TagElement(int startPosition, int endPosition)
  {
    this.startPosition = startPosition;
    this.endPosition = endPosition;
  }

  public String getTagName(Documentation documentation)
  {
    if (startPosition == endPosition)
    {
      return null;
    }
    return documentation.getText(startPosition, endPosition);
  }

  public int getTagLength()
  {
    return endPosition - startPosition;
  }

  public List<TextElement> fragments()
  {
    return fragments;
  }

  public int getStartPosition()
  {
    return startPosition;
  }

  public int getEndPosition()
  {
    return endPosition;
  }

  public int getTotalLength()
  {
    if (fragments.isEmpty())
    {
      return endPosition - startPosition;
    }
    final var lastFragment = fragments.get(fragments.size() - 1);
    return lastFragment.getStartPosition() + lastFragment.getLength() - startPosition;
  }

  public String getText(Documentation doc)
  {
    final var tagName = getTagName(doc);
    if (tagName != null)
    {

      final var sb = new StringBuilder();
      sb.append(tagName);
      if (!fragments.isEmpty())
      {
        sb.append(" ");
        sb.append(fragments.stream().map(e -> e.getText(doc))
                .collect(Collectors.joining(System.lineSeparator() + " *       ")));
      }
      return sb.toString();
    }
    return fragments.stream().map(e -> e.getText(doc))
            .collect(Collectors.joining(System.lineSeparator() + " * "));
  }
}
