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

public class TextElement
{

  private final int startPosition;

  private final String text;

  public TextElement(TextElement other)
  {
    startPosition = other.startPosition;
    text = other.text;
  }

  public TextElement(int startPosition, String text)
  {
    this.startPosition = startPosition;
    this.text = text;
  }

  public String getText()
  {
    return text;
  }

  public int getStartPosition()
  {
    return startPosition;
  }

  public int getLength()
  {
    return text.length();
  }

  @Override
  public String toString()
  {
    return getText();
  }
}
