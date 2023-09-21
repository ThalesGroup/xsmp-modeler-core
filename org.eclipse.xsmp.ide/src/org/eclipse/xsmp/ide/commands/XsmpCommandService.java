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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.xsmp.XsmpcatStandaloneSetupGenerated;
import org.eclipse.xsmp.profile.esa_cdk.EsaCdkStandaloneSetup;
import org.eclipse.xsmp.profile.xsmp_sdk.XsmpSdkStandaloneSetup;
import org.eclipse.xsmp.tool.python.PythonStandaloneSetup;
import org.eclipse.xsmp.tool.smp.SmpStandaloneSetup;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.commands.IExecutableCommandService;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.collect.Lists;
import com.google.gson.JsonPrimitive;

public class XsmpCommandService implements IExecutableCommandService
{
  private static final String GENERATE_SMP_CMD = "xsmp.generator.smp";

  private static final String GENERATE_PYTHON_CMD = "xsmp.generator.python";

  private static final String GENERATE_ESA_CDK_CMD = "xsmp.generator.esa-cdk";

  private static final String GENERATE_XSMP_SDK_CMD = "xsmp.generator.xsmp-sdk";

  @SuppressWarnings("unused")
  private static final String IMPORT_SMPCAT_CMD = "xsmp.import.smpcat";

  protected static class Command
  {
    public Command(XsmpcatStandaloneSetupGenerated setup)
    {
      final var injector = setup.createInjector();
      generator = injector.getInstance(IGenerator2.class);
      fileAccess = injector.getInstance(JavaIoFileSystemAccess.class);
      final var outputConfigurationProvider = injector
              .getInstance(IOutputConfigurationProvider.class);
      outputConfigurationProvider.getOutputConfigurations()
              .forEach(o -> fileAccess.getOutputConfigurations().put(o.getName(), o));

    }

    public void execute(Resource resource, IGeneratorContext context)
    {
      generator.doGenerate(resource, fileAccess, context);
    }

    private final IGenerator2 generator;

    private final JavaIoFileSystemAccess fileAccess;

  }

  protected Map<String, Command> commands = new HashMap<>();

  protected XsmpCommandService()
  {

    commands.put(GENERATE_SMP_CMD, new Command(new SmpStandaloneSetup()));
    commands.put(GENERATE_PYTHON_CMD, new Command(new PythonStandaloneSetup()));
    commands.put(GENERATE_ESA_CDK_CMD, new Command(new EsaCdkStandaloneSetup()));
    commands.put(GENERATE_XSMP_SDK_CMD, new Command(new XsmpSdkStandaloneSetup()));

  }

  @Override
  public List<String> initialize()
  {
    return Lists.newArrayList(GENERATE_SMP_CMD, GENERATE_PYTHON_CMD, GENERATE_ESA_CDK_CMD,
            GENERATE_XSMP_SDK_CMD);
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

    if (!(params.getArguments().get(0) instanceof final JsonPrimitive value))
    {
      access.getLanguageClient().showMessage(new MessageParams(MessageType.Error,
              "Unsupported argument: " + params.getArguments().get(0)));
      return false;
    }
    final var uri = value.getAsString();

    if (uri == null)
    {
      access.getLanguageClient()
              .showMessage(new MessageParams(MessageType.Error, "The fileUri cannot be null"));
      return false;
    }

    final var command = commands.get(params.getCommand());
    if (command == null)
    {
      access.getLanguageClient().showMessage(
              new MessageParams(MessageType.Error, "Unknown command: " + params.getCommand()));
      return false;
    }

    try
    {
      access.doRead(uri, (var it) -> {
        command.execute(it.getResource(), context);
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
