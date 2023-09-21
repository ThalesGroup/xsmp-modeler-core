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

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.ide.generator.XsmpURIBasedFileSystemAccess;
import org.eclipse.xtext.build.BuildContext;
import org.eclipse.xtext.build.BuildRequest;
import org.eclipse.xtext.build.IncrementalBuilder;
import org.eclipse.xtext.build.IndexState;
import org.eclipse.xtext.build.Source2GeneratedMapping;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.IContextualOutputConfigurationProvider;
import org.eclipse.xtext.generator.IFilePostProcessor;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.URIBasedFileSystemAccess;
import org.eclipse.xtext.generator.trace.TraceFileNameProvider;
import org.eclipse.xtext.generator.trace.TraceRegionSerializer;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.clustering.DisabledClusteringPolicy;
import org.eclipse.xtext.resource.clustering.IResourceClusteringPolicy;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.RuntimeIOException;
import org.eclipse.xtext.workspace.IProjectConfigProvider;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

public class XsmpIncrementalBuilder
{
  public static class XsmpInternalStatefulIncrementalBuilder
          extends IncrementalBuilder.InternalStatefulIncrementalBuilder
  {

    @Singleton
    public static class XsmpURIBasedFileSystemAccessFactory
    {
      @Inject
      private IContextualOutputConfigurationProvider outputConfigurationProvider;

      @Inject
      private IFilePostProcessor postProcessor;

      @Inject(optional = true)
      private IEncodingProvider encodingProvider;

      @Inject
      private TraceFileNameProvider traceFileNameProvider;

      @Inject
      private TraceRegionSerializer traceRegionSerializer;

      @Inject(optional = true)
      private IProjectConfigProvider projectConfigProvider;

      public XsmpURIBasedFileSystemAccess newFileSystemAccess(Resource resource,
              BuildRequest request)
      {
        final var uriBasedFileSystemAccess = new XsmpURIBasedFileSystemAccess();
        uriBasedFileSystemAccess.setOutputConfigurations(IterableExtensions.toMap(
                outputConfigurationProvider.getOutputConfigurations(resource),
                OutputConfiguration::getName));
        uriBasedFileSystemAccess.setPostProcessor(postProcessor);
        if (encodingProvider != null)
        {
          uriBasedFileSystemAccess.setEncodingProvider(encodingProvider);
        }
        uriBasedFileSystemAccess.setTraceFileNameProvider(traceFileNameProvider);
        uriBasedFileSystemAccess.setTraceRegionSerializer(traceRegionSerializer);
        uriBasedFileSystemAccess.setGenerateTraces(true);
        uriBasedFileSystemAccess.setBaseDir(request.getBaseDir());
        if (projectConfigProvider != null)
        {
          final var projectConfig = projectConfigProvider
                  .getProjectConfig(resource.getResourceSet());
          if (projectConfig != null)
          {
            final var sourceFolder = projectConfig.findSourceFolderContaining(resource.getURI());
            if (sourceFolder != null)
            {
              uriBasedFileSystemAccess.setCurrentSource(sourceFolder.getName());
            }
          }
        }
        uriBasedFileSystemAccess.setConverter(resource.getResourceSet().getURIConverter());
        return uriBasedFileSystemAccess;
      }
    }

    private void deleteDir(File file)
    {
      final var contents = file.listFiles();
      if (contents != null)
      {
        for (final File f : contents)
        {
          deleteDir(f);
        }
      }
      file.delete();
    }

    @Override
    protected void generate(Resource resource, BuildRequest request,
            Source2GeneratedMapping newMappings)
    {
      if (!((XsmpBuildRequest) request).shouldGenerate())
      {
        return;
      }
      final var serviceProvider = getResourceServiceProvider(resource);
      final var previous = newMappings.deleteSourceAndGetOutputConfigs(resource.getURI());
      final var fileSystemAccess = createFileSystemAccess(serviceProvider, resource);
      fileSystemAccess.setBeforeWrite((uri, outputCfgName, contents) -> {
        newMappings.addSource2Generated(resource.getURI(), uri, outputCfgName);
        previous.remove(uri);
        request.getAfterGenerateFile().apply(resource.getURI(), uri);
        return contents;
      });
      fileSystemAccess.setBeforeDelete(uri -> {
        newMappings.deleteGenerated(uri);
        request.getAfterDeleteFile().apply(uri);
        return true;
      });
      fileSystemAccess.setContext(resource);
      if (request.isWriteStorageResources())
      {
        storeBinaryResource(resource, fileSystemAccess);
      }
      for (final var cfg : fileSystemAccess.getOutputConfigurations().values())
      {
        if (cfg.isCanClearOutputDirectory())
        {
          deleteDir(new File(
                  fileSystemAccess.getBaseDir().toFileString() + cfg.getOutputDirectory()));

        }
      }
      final var generatorContext = new GeneratorContext();
      generatorContext.setCancelIndicator(request.getCancelIndicator());
      final var generator = serviceProvider.get(GeneratorDelegate.class);
      generator.generate(resource, fileSystemAccess, generatorContext);

      final var resourceSet = request.getResourceSet();
      for (final Entry<URI, String> entry : previous.entrySet())
      {
        final var noLongerCreated = entry.getKey();
        final var outputConfiguration = fileSystemAccess.getOutputConfigurations()
                .get(entry.getValue());
        if (outputConfiguration != null && outputConfiguration.isCleanUpDerivedResources()
                && !outputConfiguration.isCanClearOutputDirectory())
        {
          try
          {
            resourceSet.getURIConverter().delete(noLongerCreated, Collections.emptyMap());
            request.getAfterDeleteFile().apply(noLongerCreated);
          }
          catch (final IOException e)
          {
            throw new RuntimeIOException(e);
          }
        }
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

    @Override
    protected URIBasedFileSystemAccess createFileSystemAccess(
            IResourceServiceProvider serviceProvider, Resource resource)
    {
      return serviceProvider.get(XsmpURIBasedFileSystemAccessFactory.class)
              .newFileSystemAccess(resource, getRequest());
    }

  }

  @Inject
  private Provider<XsmpInternalStatefulIncrementalBuilder> provider;

  @Inject
  private OperationCanceledManager operationCanceledManager;

  /**
   * Run the build without clustering.
   */
  public IncrementalBuilder.Result build(XsmpBuildRequest request,
          Function1< ? super URI, ? extends IResourceServiceProvider> languages)
  {
    return build(request, languages, new DisabledClusteringPolicy());
  }

  public IncrementalBuilder.Result build(XsmpBuildRequest request,
          Function1< ? super URI, ? extends IResourceServiceProvider> languages,
          IResourceClusteringPolicy clusteringPolicy)
  {
    final var resourceSet = request.getResourceSet();
    final var oldState = new IndexState(request.getState().getResourceDescriptions().copy(),
            request.getState().getFileMappings().copy());
    final var context = new BuildContext(languages, resourceSet, oldState, clusteringPolicy,
            request.getCancelIndicator());
    final var builder = provider.get();
    builder.setContext(context);
    builder.setRequest(request);

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
