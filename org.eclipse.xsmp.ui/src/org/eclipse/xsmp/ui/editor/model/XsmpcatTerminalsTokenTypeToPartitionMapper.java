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
 * Handle Xsmpcat terminal rules properly
 */
@Singleton
public class XsmpcatTerminalsTokenTypeToPartitionMapper extends TerminalsTokenTypeToPartitionMapper
{

  @Override
  protected String calculateId(String tokenName, int tokenType)
  {
    switch (tokenName)
    {
      case "RULE_ML_COMMENT":
      case "RULE_ML_DOCUMENTATION":
        return COMMENT_PARTITION;
      case "RULE_SL_COMMENT":
        return SL_COMMENT_PARTITION;
      case "RULE_STRING_LITERAL":
      case "RULE_CHARACTER_LITERAL":
      case "RULE_INTEGER_LITERAL":
      case "RULE_FLOATING_LITERAL":
        return STRING_LITERAL_PARTITION;
      default:
        return IDocument.DEFAULT_CONTENT_TYPE;
    }

  }
}
