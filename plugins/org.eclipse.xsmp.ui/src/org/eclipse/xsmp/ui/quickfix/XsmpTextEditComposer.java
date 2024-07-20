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
package org.eclipse.xsmp.ui.quickfix;

import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.ui.editor.model.edit.DefaultTextEditComposer;

public class XsmpTextEditComposer extends DefaultTextEditComposer
{
  /**
   * {@inheritDoc}
   */
  @Override
  protected SaveOptions getSaveOptions()
  {
    return SaveOptions.newBuilder().format().getOptions();
  }
}
