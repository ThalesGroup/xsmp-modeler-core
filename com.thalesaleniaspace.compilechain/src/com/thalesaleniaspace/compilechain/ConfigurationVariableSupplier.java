package com.thalesaleniaspace.compilechain;

import org.eclipse.cdt.core.envvar.IEnvironmentVariable;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.envvar.IBuildEnvironmentVariable;
import org.eclipse.cdt.managedbuilder.envvar.IConfigurationEnvironmentVariableSupplier;
import org.eclipse.cdt.managedbuilder.envvar.IEnvironmentVariableProvider;
import org.eclipse.cdt.managedbuilder.internal.envvar.BuildEnvVar;

/**
 * Overload environment variable for CompileChain such as LD_LIBRARY_PATH
 *
 * @author daveluy
 */
@SuppressWarnings("restriction")
public class ConfigurationVariableSupplier implements IConfigurationEnvironmentVariableSupplier
{

  private static final String LD_LIBRARY_PATH = "LD_LIBRARY_PATH";

  private static final String BUILD_TYPE = "BUILD_TYPE";

  @Override
  public IBuildEnvironmentVariable getVariable(String variableName, IConfiguration configuration,
          IEnvironmentVariableProvider provider)
  {
    switch (variableName)
    {
      case LD_LIBRARY_PATH:
        return getLdLibraryPath(configuration);
      case BUILD_TYPE:
        return getBuildType(configuration);
      default:
        final var envVar = provider.getVariable(variableName, configuration, false);
        if (envVar != null)
        {
          return new BuildEnvVar(envVar);
        }
        return null;
    }
  }

  @Override
  public IBuildEnvironmentVariable[] getVariables(IConfiguration configuration,
          IEnvironmentVariableProvider provider)
  {
    return new IBuildEnvironmentVariable[]{
      getLdLibraryPath(configuration),
      getBuildType(configuration) };
  }

  protected BuildEnvVar getBuildType(IConfiguration configuration)
  {
    return new BuildEnvVar(BUILD_TYPE, configuration.getName(),
            IEnvironmentVariable.ENVVAR_REPLACE);
  }

  protected BuildEnvVar getLdLibraryPath(IConfiguration configuration)
  {

    if ("Default".equals(configuration.getName()))
    {
      return new BuildEnvVar(LD_LIBRARY_PATH,
              "${ROOT_OBJ}/" + configuration.getOwner().getName() + "/SO",
              IEnvironmentVariable.ENVVAR_PREPEND);
    }
    return new BuildEnvVar(LD_LIBRARY_PATH, "${ROOT_OBJ}/" + configuration.getOwner().getName()
            + "/" + configuration.getName() + "/SO", IEnvironmentVariable.ENVVAR_PREPEND);
  }

}
