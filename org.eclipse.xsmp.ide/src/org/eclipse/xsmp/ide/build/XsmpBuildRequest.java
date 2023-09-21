/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.ide.build;

import org.eclipse.xtext.build.BuildRequest;

public class XsmpBuildRequest extends BuildRequest
{
  private boolean shouldGenerate;

  public boolean shouldGenerate()
  {
    return shouldGenerate;
  }

  public void setShouldGenerate(boolean shouldGenerate)
  {
    this.shouldGenerate = shouldGenerate;
  }

}
