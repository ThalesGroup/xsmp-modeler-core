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
package org.eclipse.xsmp.ui.autoedit;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.ui.editor.autoedit.MultiLineTerminalsEditStrategy;

import com.google.inject.Inject;
import com.google.inject.MembersInjector;

/**
 * This strategy applies auto edits when typing a newline character within a block (denoted by a
 * start and end terminal).
 */
public class XsmpcatMultiLineTerminalEditStrategy extends MultiLineTerminalsEditStrategy
{

  /**
   *
   */
  public static class Factory extends MultiLineTerminalsEditStrategy.Factory
  {
    @Inject
    private IIndentationInformation indentationInformation;

    @Inject
    private MembersInjector<XsmpcatMultiLineTerminalEditStrategy> injector;

    /**
     * {@inheritDoc}
     */
    @Override
    public XsmpcatMultiLineTerminalEditStrategy newInstance(String leftTerminal,
            String indentationString, String rightTerminal)
    {
      return newInstance(leftTerminal, indentationString, rightTerminal, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public XsmpcatMultiLineTerminalEditStrategy newInstance(String leftTerminal,
            String indentationString, String rightTerminal, boolean nested)
    {
      indentationString = indentationString == null ? indentationInformation.getIndentString()
              : indentationString;
      final var strategy = new XsmpcatMultiLineTerminalEditStrategy(leftTerminal, indentationString,
              rightTerminal, nested);
      injector.injectMembers(strategy);
      return strategy;
    }
  }

  /**
   * @param leftTerminal
   * @param indentationString
   * @param rightTerminal
   * @param nested
   */
  public XsmpcatMultiLineTerminalEditStrategy(String leftTerminal, String indentationString,
          String rightTerminal, boolean nested)
  {
    super(leftTerminal, indentationString, rightTerminal, nested);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IRegion findStartTerminal(IDocument document, int offset) throws BadLocationException
  {
    final var documentText = document.get();
    var stopOffset = offset;
    var startOffset = offset;
    while (true)
    {
      final var start = util.searchBackwardsInSamePartition(getLeftTerminal(), documentText,
              document, startOffset);
      if (start == null)
      {
        return null;
      }
      final var stop = util.searchBackwardsInSamePartition(getRightTerminal().trim(), documentText,
              document, stopOffset);
      if (stop == null || stop.getOffset() < start.getOffset())
      {
        return start;
      }
      stopOffset = stop.getOffset();
      startOffset = start.getOffset();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IRegion findStopTerminal(IDocument document, int offset) throws BadLocationException
  {
    final var documentText = document.get();
    var stopOffset = offset;
    var startOffset = offset;
    while (true)
    {
      final var stop = util.searchInSamePartition(getRightTerminal(), documentText, document,
              stopOffset);
      if (stop == null)
      {
        return null;
      }
      final var start = util.searchInSamePartition(getLeftTerminal(), documentText, document,
              startOffset);
      if (start == null || start.getOffset() > stop.getOffset())
      {
        return stop;
      }
      stopOffset = util.findNextOffSetInPartition(document, stopOffset,
              stop.getOffset() + stop.getLength());
      startOffset = util.findNextOffSetInPartition(document, startOffset,
              start.getOffset() + start.getLength());
    }
  }

}