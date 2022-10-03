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
package org.eclipse.xsmp.ui.builder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xsmp.ui.mdk.IMdkConfigurationUIProvider;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Specialized builder participant that retrieve the builder defined in the MDK
 */
@Singleton
public class XsmpcatBuilderParticipant implements IXtextBuilderParticipant
{

  @Inject
  private IMdkConfigurationUIProvider mdkProvider;

  @Inject
  private IXtextBuilderParticipant delegate;

  @Override
  public void build(IBuildContext context, IProgressMonitor monitor) throws CoreException
  {

    final var participant = mdkProvider.getInstance(context.getBuiltProject(),
            IXtextBuilderParticipant.class);

    if (participant != null)
    {
      participant.build(context, monitor);
    }
    else
    {
      delegate.build(context, monitor);
    }
  }
}
