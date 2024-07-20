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
package org.eclipse.xsmp.server.build;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.build.BuildContext;
import org.eclipse.xtext.build.BuildRequest;
import org.eclipse.xtext.build.IncrementalBuilder;
import org.eclipse.xtext.build.IndexState;
import org.eclipse.xtext.build.Source2GeneratedMapping;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.clustering.DisabledClusteringPolicy;
import org.eclipse.xtext.resource.clustering.IResourceClusteringPolicy;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.xbase.lib.Functions.Function1;

import com.google.inject.Inject;
import com.google.inject.Provider;

/***
 * An incremental builder with support for generation when document is saved
 */
public class XsmpIncrementalBuilder
{
  private static class XsmpInternalStatefulIncrementalBuilder
          extends IncrementalBuilder.InternalStatefulIncrementalBuilder
  {

    private boolean shouldGenerate;

    @Override
    protected void generate(Resource resource, BuildRequest request,
            Source2GeneratedMapping newMappings)
    {
      if (shouldGenerate)
      {
        super.generate(resource, request, newMappings);
      }
    }

    @Override
    protected void setContext(BuildContext context)
    {
      super.setContext(context);
    }

    @Override
    protected void setRequest(BuildRequest request)
    {
      super.setRequest(request);
    }

    public void setShouldGenerate(boolean shouldGenerate)
    {
      this.shouldGenerate = shouldGenerate;
    }
  }

  @Inject
  private Provider<XsmpInternalStatefulIncrementalBuilder> provider;

  @Inject
  private OperationCanceledManager operationCanceledManager;

  /**
   * Run the build without clustering.
   *
   * @param shouldGenerate
   */
  public IncrementalBuilder.Result build(BuildRequest request,
          Function1< ? super URI, ? extends IResourceServiceProvider> languages,
          boolean shouldGenerate)
  {
    return build(request, languages, new DisabledClusteringPolicy(), shouldGenerate);
  }

  public IncrementalBuilder.Result build(BuildRequest request,
          Function1< ? super URI, ? extends IResourceServiceProvider> languages,
          IResourceClusteringPolicy clusteringPolicy, boolean shouldGenerate)
  {
    final var resourceSet = request.getResourceSet();
    final var oldState = new IndexState(request.getState().getResourceDescriptions().copy(),
            request.getState().getFileMappings().copy());
    final var context = new BuildContext(languages, resourceSet, oldState, clusteringPolicy,
            request.getCancelIndicator());
    final var builder = provider.get();
    builder.setContext(context);
    builder.setRequest(request);
    builder.setShouldGenerate(shouldGenerate);
    try
    {
      return builder.launch();
    }
    catch (final Exception t)
    {
      operationCanceledManager.propagateIfCancelException(t);
      throw t;
    }
  }

}
