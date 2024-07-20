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
package org.eclipse.xsmp.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xsmp.XsmpConstants;
import org.eclipse.xtext.resource.impl.DefaultResourceServiceProvider;

public class XsmpprojectResourceServiceProvider extends DefaultResourceServiceProvider
{
  @Override
  public boolean canHandle(URI uri)
  {
    return XsmpConstants.XSMP_PROJECT_FILENAME.equals(uri.lastSegment());
  }
}
