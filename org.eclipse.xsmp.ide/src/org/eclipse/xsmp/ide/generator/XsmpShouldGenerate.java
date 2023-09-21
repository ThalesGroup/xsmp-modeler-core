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
package org.eclipse.xsmp.ide.generator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.ide.workspace.XsmpProjectConfigProvider;
import org.eclipse.xtext.generator.IShouldGenerate.OnlyWithoutErrors;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class XsmpShouldGenerate extends OnlyWithoutErrors
{

  @Inject
  private XsmpProjectConfigProvider configProvider;

  @Override
  public boolean shouldGenerate(Resource resource, CancelIndicator cancelIndicator)
  {
    final var config = configProvider.getProjectConfig(resource.getResourceSet());

    return config != null && config.shouldGenerate()
            && super.shouldGenerate(resource, cancelIndicator);
  }
}
