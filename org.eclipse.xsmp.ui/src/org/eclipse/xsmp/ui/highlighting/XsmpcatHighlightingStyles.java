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

/**
 * Specific styles
 */
public interface XsmpcatHighlightingStyles extends HighlightingStyles
{

  /** style for documentation */
  String DOCUMENTATION_ID = "Documentation";

  /** style for documentation tag */
  String DOCUMENTATION_TAG_ID = "DocumentationTag";

  /** style for fields */
  String FIELD_ID = "Field";

  /** style for parameters */
  String PARAMETER_ID = "Parameter";

  /** style for attributes */
  String ANNOTATION_ID = "Annotation";

  /** style for BuiltIn constant/functions */
  String BUILT_IN_ID = "BuiltIn";

  /** tag an element to be deprecated */
  String DEPRECATED_ID = "Deprecated";

}
