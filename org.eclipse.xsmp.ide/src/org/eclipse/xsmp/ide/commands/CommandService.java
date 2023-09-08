package org.eclipse.xsmp.ide.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.xsmp.profile.esa_cdk.EsaCdkStandaloneSetup;
import org.eclipse.xsmp.profile.xsmp_sdk.XsmpSdkStandaloneSetup;
import org.eclipse.xsmp.tool.python.PythonStandaloneSetup;
import org.eclipse.xsmp.tool.smp.SmpStandaloneSetup;
import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.IGenerator2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.commands.IExecutableCommandService;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.collect.Lists;
import com.google.gson.JsonPrimitive;

public class CommandService implements IExecutableCommandService
{
  private static final String SMDL_CMD = "xsmp.smdl";

  private static final String PYTHON_CMD = "xsmp.python";

  private static final String ESA_CDK_CMD = "xsmp.esa_cdk";

  private static final String XSMP_SDK_CMD = "xsmp.xsmp_sdk";

  protected static class Command
  {
    public Command(ISetup setup)
    {
      final var injector = setup.createInjectorAndDoEMFRegistration();
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

  protected CommandService()
  {
    final var languageName = "xsmpcat";
    // store ResourceFactory & ServiceProvider
    final var resourceFactory = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
            .get(languageName);
    final var serviceProvider = IResourceServiceProvider.Registry.INSTANCE
            .getExtensionToFactoryMap().get(languageName);

    commands.put(SMDL_CMD, new Command(new SmpStandaloneSetup()));
    commands.put(PYTHON_CMD, new Command(new PythonStandaloneSetup()));
    commands.put(ESA_CDK_CMD, new Command(new EsaCdkStandaloneSetup()));
    commands.put(XSMP_SDK_CMD, new Command(new XsmpSdkStandaloneSetup()));

    // restore ResourceFactory & ServiceProvider
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(languageName,
            resourceFactory);
    IResourceServiceProvider.Registry.INSTANCE.getExtensionToFactoryMap().put(languageName,
            serviceProvider);
  }

  @Override
  public List<String> initialize()
  {
    return Lists.newArrayList(SMDL_CMD, PYTHON_CMD, ESA_CDK_CMD, XSMP_SDK_CMD);
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
