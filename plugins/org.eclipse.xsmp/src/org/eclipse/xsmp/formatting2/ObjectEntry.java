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
package org.eclipse.xsmp.formatting2;

import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;
import org.eclipse.xtext.formatting2.regionaccess.internal.TextSegment;

public class ObjectEntry<T extends Object, R extends ITextSegment> extends Entry<T, R>
{
  private final SeparatorRegions<T, R> list;

  private T object;

  public ITextSegment getRegion()
  {
    final var prev = getLeadingSeparator();
    final var trail = getTrailingSeparator();
    final int offset;
    if (prev != null)
    {
      offset = prev.getSeparator().getEndOffset();
    }
    else
    {
      offset = list.getRoot().getOffset();
    }
    final int endOffset;
    if (trail != null)
    {
      endOffset = trail.getSeparator().getOffset();
    }
    else
    {
      endOffset = list.getRoot().getEndOffset();
    }
    return new TextSegment(list.getRoot().getTextRegionAccess(), offset, endOffset - offset);
  }

  @Override
  public ObjectEntry<T, R> getLeadingObject()
  {
    Entry<T, R> result = null;
    if (previous != null)
    {
      result = previous.previous;
    }
    return (ObjectEntry<T, R>) result;
  }

  @Override
  public ObjectEntry<T, R> getTrailingObject()
  {
    Entry<T, R> result = null;
    if (next != null)
    {
      result = next.next;
    }
    return (ObjectEntry<T, R>) result;
  }

  @Override
  public SeparatorEntry<T, R> getLeadingSeparator()
  {
    return (SeparatorEntry<T, R>) previous;
  }

  @Override
  public SeparatorEntry<T, R> getTrailingSeparator()
  {
    return (SeparatorEntry<T, R>) next;
  }

  @Override
  public String toString()
  {

    final var reg = getRegion();

    return "\"" + reg.getText() + "\" at offset=" + reg.getOffset() + " lenght=" + reg.getLength()
            + " (" + object.getClass().getSimpleName() + ")";
  }

  public ObjectEntry(SeparatorRegions<T, R> list)
  {
    this.list = list;
  }

  public SeparatorRegions<T, R> getList()
  {
    return list;
  }

  public T getObject()
  {
    return object;
  }

  public void setObject(T object)
  {
    this.object = object;
  }
}
