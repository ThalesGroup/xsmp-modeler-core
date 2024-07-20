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
package org.eclipse.xsmp.ui.highlighting;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class XsmpprojectHighlightingConfiguration extends XsmpHighlightingConfiguration
{

  @Override
  public TextStyle deprecatedTextStyle()
  {
    final var textStyle = stringTextStyle().copy();
    textStyle.setStyle(TextAttribute.STRIKETHROUGH);
    return textStyle;
  }

}
