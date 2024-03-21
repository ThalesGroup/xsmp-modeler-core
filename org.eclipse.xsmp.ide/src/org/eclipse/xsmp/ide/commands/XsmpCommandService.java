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
package org.eclipse.xsmp.ide.commands;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.xsmp.ide.generator.XsmpGenerator;
import org.eclipse.xsmp.ide.generator.XsmpOutputConfigurationProvider;
import org.eclipse.xsmp.ide.generator.XsmpURIBasedFileSystemAccess;
import org.eclipse.xsmp.ide.workspace.XsmpProjectConfigProvider;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.IFilePostProcessor;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.trace.TraceFileNameProvider;
import org.eclipse.xtext.generator.trace.TraceRegionSerializer;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.commands.IExecutableCommandService;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.collect.Lists;
import com.google.gson.JsonPrimitive;
import com.google.inject.Inject;

public class XsmpCommandService implements IExecutableCommandService
{
  private static final String GENERATE_CMD = "xsmp.generate";

  @SuppressWarnings("unused")
  private static final String IMPORT_SMPCAT_CMD = "xsmp.import.smpcat";

  @Inject
  XsmpGenerator generator;

  @Inject
  XsmpOutputConfigurationProvider outputConfigurationProvider;

  @Inject
  private XsmpProjectConfigProvider projectConfigProvider;

  @Inject
  private IFilePostProcessor postProcessor;

  @Inject(optional = true)
  private IEncodingProvider encodingProvider;

  @Inject
  private TraceFileNameProvider traceFileNameProvider;

  @Inject
  private TraceRegionSerializer traceRegionSerializer;

  public XsmpURIBasedFileSystemAccess newFileSystemAccess(Resource resource)
  {
    final var uriBasedFileSystemAccess = new XsmpURIBasedFileSystemAccess();
    uriBasedFileSystemAccess.setOutputConfigurations(
            IterableExtensions.toMap(outputConfigurationProvider.getOutputConfigurations(resource),
                    OutputConfiguration::getName));
    uriBasedFileSystemAccess.setPostProcessor(postProcessor);
    if (encodingProvider != null)
    {
      uriBasedFileSystemAccess.setEncodingProvider(encodingProvider);
    }
    uriBasedFileSystemAccess.setTraceFileNameProvider(traceFileNameProvider);
    uriBasedFileSystemAccess.setTraceRegionSerializer(traceRegionSerializer);
    uriBasedFileSystemAccess.setGenerateTraces(true);

    final var projectConfig = projectConfigProvider.getProjectConfig(resource.getResourceSet());
    if (projectConfig != null)
    {
      uriBasedFileSystemAccess.setBaseDir(projectConfig.getPath());
      final var sourceFolder = projectConfig.findSourceFolderContaining(resource.getURI());
      if (sourceFolder != null)
      {
        uriBasedFileSystemAccess.setCurrentSource(sourceFolder.getName());
      }
    }
    uriBasedFileSystemAccess.setConverter(resource.getResourceSet().getURIConverter());
    return uriBasedFileSystemAccess;
  }

  public void execute(Resource resource, IGeneratorContext context)
  {
    final var fileAccess = newFileSystemAccess(resource);

    generator.doGenerate(resource, fileAccess, context);
  }

  @Override
  public List<String> initialize()
  {
    return Lists.newArrayList(GENERATE_CMD);
  }

  @Override
  public Object execute(ExecuteCommandParams params, ILanguageServerAccess access,
          CancelIndicator cancelIndicator)
  {
    final var context = new GeneratorContext();
    context.setCancelIndicator(cancelIndicator);

    if (params.getArguments().size() != 1)
    {
      access.getLanguageClient()
              .showMessage(new MessageParams(MessageType.Error, "Invalid number of arguments."));
      return false;
    }

    if (!(params.getArguments().get(0) instanceof JsonPrimitive))
    {
      access.getLanguageClient().showMessage(new MessageParams(MessageType.Error,
              "Unsupported argument: " + params.getArguments().get(0)));
      return false;
    }
    final var value = (JsonPrimitive) params.getArguments().get(0);
    final var uri = value.getAsString();

    if (uri == null)
    {
      access.getLanguageClient()
              .showMessage(new MessageParams(MessageType.Error, "The fileUri cannot be null"));
      return false;
    }
    access.getLanguageClient().workspaceFolders();
    try
    {
      access.doRead(uri, (var it) -> {
        execute(it.getResource(), context);
        return true;
      }).get();
    }
    catch (InterruptedException | ExecutionException e)
    {
      access.getLanguageClient().showMessage(new MessageParams(MessageType.Error, e.getMessage()));
      Thread.currentThread().interrupt();
      return false;
    }

    return true;
  }

}
