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

import org.eclipse.xtext.formatting2.IAutowrapFormatter;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatter;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatting;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegion;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegionPart;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;
import org.eclipse.xtext.xbase.lib.Extension;

public class IndentOnceAutowrapFormatter implements IAutowrapFormatter
{
  private final IHiddenRegion last;

  private boolean hasWrapped = false;

  public IndentOnceAutowrapFormatter(IHiddenRegion last)
  {
    this.last = last;
  }

  @Override
  public void format(ITextSegment region, IHiddenRegionFormatting wrapped,
          @Extension IFormattableDocument document)
  {
    if (!hasWrapped)
    {
      IHiddenRegion hiddenRegion = null;
      if (region instanceof final IHiddenRegion hr)
      {
        hiddenRegion = hr;
      }
      else if (region instanceof final IHiddenRegionPart hrp)
      {
        hiddenRegion = hrp.getHiddenRegion();
      }
      document.set(hiddenRegion, last, IHiddenRegionFormatter::indent);
      hasWrapped = true;
    }
  }

}
