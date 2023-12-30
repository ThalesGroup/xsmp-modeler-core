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

import org.eclipse.jface.text.IDocument;
import org.eclipse.xtext.ui.editor.autoedit.DefaultAutoEditStrategyProvider;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;

/**
 * @author daveluy
 */
public class XsmpAutoEditStrategyProvider extends DefaultAutoEditStrategyProvider
{

  @Override
  protected void configureMultilineComments(IEditStrategyAcceptor acceptor)
  {
    // specialization for multi line comments (/* ... */) and documentation (/** ...
    // */)
    acceptor.accept(singleLineTerminals.newInstance("/*", " */"), IDocument.DEFAULT_CONTENT_TYPE);
    acceptor.accept(multiLineTerminals.newInstance("/**", " * ", " */"),
            IDocument.DEFAULT_CONTENT_TYPE);
    acceptor.accept(multiLineTerminals.newInstance("/**", " * ", " */"),
            TerminalsTokenTypeToPartitionMapper.COMMENT_PARTITION);
    acceptor.accept(multiLineTerminals.newInstance("/*", " * ", " */"),
            IDocument.DEFAULT_CONTENT_TYPE);
    acceptor.accept(multiLineTerminals.newInstance("/*", " * ", " */"),
            TerminalsTokenTypeToPartitionMapper.COMMENT_PARTITION);
  }

}
