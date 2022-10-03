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
public class XsmpcatAntlrTokenToAttributeIdMapper extends DefaultAntlrTokenToAttributeIdMapper
{

  @Override
  protected String calculateId(String tokenName, int tokenType)
  {
    switch (tokenName)
    {
      case "RULE_ML_DOCUMENTATION":
        return XsmpcatHighlightingStyles.DOCUMENTATION_ID;
      case "'@'":
        return XsmpcatHighlightingStyles.ANNOTATION_ID;
      case "RULE_INTEGER_LITERAL":
      case "RULE_FLOATING_LITERAL":
        return HighlightingStyles.NUMBER_ID;
      case "RULE_CHARACTER_LITERAL":
      case "RULE_STRING_LITERAL":
        return HighlightingStyles.STRING_ID;
      default:
        return super.calculateId(tokenName, tokenType);
    }

  }

}
