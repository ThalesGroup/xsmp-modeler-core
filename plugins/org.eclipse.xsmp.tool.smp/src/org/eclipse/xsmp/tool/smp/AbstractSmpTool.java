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
package org.eclipse.xsmp.tool.smp;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xsmp.extension.Tool;

public abstract class AbstractSmpTool extends Tool
{
  @Override
  public String getId()
  {
    return "org.eclipse.xsmp.tool.smp";
  }

  @Override
  public String getDescription()
  {
    return "SMP legacy Tool";
  }

  @Override
  public Collection<String> getSupportedLanguages()
  {
    return Collections.singleton(XsmpConstants.ORG_ECLIPSE_XSMP_XSMPCAT);
  }
}
