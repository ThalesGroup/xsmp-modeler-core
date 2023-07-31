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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TagElement
{

  private final List<TextElement> fragments = new ArrayList<>();

  private final int startPosition;

  private final String tagName;

  public TagElement(TagElement other)
  {
    startPosition = other.startPosition;
    tagName = other.tagName;
    for (final var fragment : other.fragments)
    {
      fragments.add(new TextElement(fragment));
    }
  }

  public TagElement(int startPosition, String tagName)
  {
    this.startPosition = startPosition;
    this.tagName = tagName;
  }

  public String getTagName()
  {
    return tagName;
  }

  public List<TextElement> fragments()
  {
    return fragments;
  }

  public int getStartPosition()
  {
    return startPosition;
  }

  public int getLength()
  {
    if (fragments.isEmpty())
    {
      // tagName should not be null
      return tagName.length();
    }

    final var lastFragment = fragments.get(fragments.size() - 1);
    return lastFragment.getStartPosition() + lastFragment.getLength() - startPosition;
  }

  @Override
  public String toString()
  {

    if (tagName != null)
    {

      final var sb = new StringBuilder();
      sb.append(tagName);
      if (!fragments.isEmpty())
      {
        sb.append(" ");
        sb.append(fragments.stream().map(Object::toString)
                .collect(Collectors.joining(System.lineSeparator() + " *       ")));
      }
      return sb.toString();
    }
    return fragments.stream().map(Object::toString)
            .collect(Collectors.joining(System.lineSeparator() + " * "));
  }
}
