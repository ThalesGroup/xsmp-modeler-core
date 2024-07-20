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
package org.eclipse.xsmp.ui.highlighting;

import org.eclipse.xtext.ide.editor.syntaxcoloring.HighlightingStyles;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;

import com.google.inject.Singleton;

@Singleton
public class XsmpAntlrTokenToAttributeIdMapper extends DefaultAntlrTokenToAttributeIdMapper
{

  @Override
  protected String calculateId(String tokenName, int tokenType)
  {
    return switch (tokenName)
    {
      case "RULE_ML_DOCUMENTATION" -> XsmpcatHighlightingStyles.DOCUMENTATION_ID;
      case "'@'" -> XsmpcatHighlightingStyles.ANNOTATION_ID;
      case "RULE_INTEGER_LITERAL", "RULE_FLOATING_LITERAL" -> HighlightingStyles.NUMBER_ID;
      case "RULE_CHARACTER_LITERAL", "RULE_STRING_LITERAL", "RULE_STRING" -> HighlightingStyles.STRING_ID;
      default -> super.calculateId(tokenName, tokenType);
    };

  }

}
