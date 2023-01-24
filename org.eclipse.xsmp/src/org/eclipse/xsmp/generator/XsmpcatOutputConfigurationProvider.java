package org.eclipse.xsmp.generator;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;

public class XsmpcatOutputConfigurationProvider extends OutputConfigurationProvider
{
  @Override
  public Set<OutputConfiguration> getOutputConfigurations()
  {
    return new HashSet<>();
  }

}
