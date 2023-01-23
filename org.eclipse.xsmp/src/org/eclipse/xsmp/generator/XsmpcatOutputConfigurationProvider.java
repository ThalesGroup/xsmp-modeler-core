package org.eclipse.xsmp.generator;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;

public class XsmpcatOutputConfigurationProvider extends OutputConfigurationProvider
{
  @Override
  public Set<OutputConfiguration> getOutputConfigurations()
  {
    final var result = new HashSet<OutputConfiguration>();
    final var defaultOutput = new OutputConfiguration(IFileSystemAccess.DEFAULT_OUTPUT);
    defaultOutput.setDescription("Root Folder");
    defaultOutput.setOutputDirectory("./");
    defaultOutput.setOverrideExistingResources(false);
    defaultOutput.setCreateOutputDirectory(false);
    defaultOutput.setCleanUpDerivedResources(false);
    defaultOutput.setSetDerivedProperty(false);
    result.add(defaultOutput);
    return result;
  }

}
