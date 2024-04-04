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
package org.eclipse.xsmp.tool.python.validation;

import org.eclipse.xsmp.tool.python.AbstractPythonTool;
import org.eclipse.xsmp.validation.AbstractXsmpContextValidator;
import org.eclipse.xsmp.workspace.IXsmpProjectConfig;

import com.google.inject.Singleton;

/**
 * This class contains custom validation rules. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@Singleton
public class PythonValidator extends AbstractXsmpContextValidator
{
  @Override
  protected boolean isEnabledFor(IXsmpProjectConfig config)
  {
    return config.getTools().contains(AbstractPythonTool.TOOL_ID);
  }
}
