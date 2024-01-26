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
package org.eclipse.xsmp.ui.editor.model;

import org.eclipse.jface.text.IDocument;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;

import com.google.inject.Singleton;

/**
 * Handle Xsmp terminal rules properly
 */
@Singleton
public class XsmpTerminalsTokenTypeToPartitionMapper extends TerminalsTokenTypeToPartitionMapper
{

  @Override
  protected String calculateId(String tokenName, int tokenType)
  {
    return switch (tokenName)
    {
      case "RULE_ML_COMMENT", "RULE_ML_DOCUMENTATION" -> COMMENT_PARTITION;
      case "RULE_SL_COMMENT" -> SL_COMMENT_PARTITION;
      case "RULE_STRING_LITERAL", "RULE_CHARACTER_LITERAL", "RULE_INTEGER_LITERAL", "RULE_FLOATING_LITERAL" -> STRING_LITERAL_PARTITION;
      default -> IDocument.DEFAULT_CONTENT_TYPE;
    };

  }
}
