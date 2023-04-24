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

import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.ANNOTATION_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.BUILT_IN_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.DEPRECATED_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.DOCUMENTATION_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.DOCUMENTATION_TAG_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.FIELD_ID;
import static org.eclipse.xsmp.ui.highlighting.XsmpcatHighlightingStyles.PARAMETER_ID;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class XsmpcatHighlightingConfiguration extends DefaultHighlightingConfiguration
{

  /**
   * {@inheritDoc}
   */
  @Override
  public void configure(IHighlightingConfigurationAcceptor acceptor)
  {
    acceptor.acceptDefaultHighlighting(DOCUMENTATION_ID, "Documentation", documentationTextStyle());
    acceptor.acceptDefaultHighlighting(DOCUMENTATION_TAG_ID, "DocumentationTag",
            documentationTagTextStyle());

    acceptor.acceptDefaultHighlighting(FIELD_ID, "Field", localElementStyle());
    acceptor.acceptDefaultHighlighting(PARAMETER_ID, "Parameter", parameterStyle());

    acceptor.acceptDefaultHighlighting(ANNOTATION_ID, "Annotation", attributeStyle());

    acceptor.acceptDefaultHighlighting(BUILT_IN_ID, "BuiltIn", builtInStyle());

    acceptor.acceptDefaultHighlighting(DEPRECATED_ID, "Deprecated", deprecatedTextStyle());
    super.configure(acceptor);
  }

  public TextStyle deprecatedTextStyle()
  {
    final var textStyle = defaultTextStyle().copy();
    textStyle.setStyle(TextAttribute.STRIKETHROUGH);
    return textStyle;
  }

  public TextStyle documentationTagTextStyle()
  {
    final var textStyle = defaultTextStyle().copy();
    textStyle.setStyle(SWT.BOLD);
    textStyle.setColor(new RGB(127, 159, 191));
    return textStyle;
  }

  public TextStyle documentationTextStyle()
  {
    final var textStyle = defaultTextStyle().copy();
    textStyle.setColor(new RGB(63, 95, 191));
    return textStyle;
  }

  public TextStyle localElementStyle()
  {
    final var textStyle = defaultTextStyle().copy();
    textStyle.setColor(new RGB(0, 0, 192));
    return textStyle;
  }

  public TextStyle parameterStyle()
  {
    final var textStyle = defaultTextStyle().copy();
    textStyle.setColor(new RGB(106, 62, 62));
    return textStyle;
  }

  public TextStyle attributeStyle()
  {
    final var textStyle = defaultTextStyle().copy();
    textStyle.setColor(new RGB(100, 100, 100));
    return textStyle;
  }

  public TextStyle builtInStyle()
  {
    final var textStyle = defaultTextStyle().copy();
    textStyle.setColor(new RGB(245, 121, 0));
    return textStyle;
  }

  @Override
  public TextStyle numberTextStyle()
  {
    return defaultTextStyle().copy();
  }
}
