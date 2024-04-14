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

public class TextElement
{

  private final int startPosition;

  private final int endPosition;

  public TextElement(int startPosition, int endPosition)
  {
    this.startPosition = startPosition;
    this.endPosition = endPosition;
  }

  public String getText(Documentation documentation)
  {
    return documentation.getText(startPosition, endPosition);
  }

  public int getStartPosition()
  {
    return startPosition;
  }

  public int getLength()
  {
    return endPosition - startPosition;
  }

}
