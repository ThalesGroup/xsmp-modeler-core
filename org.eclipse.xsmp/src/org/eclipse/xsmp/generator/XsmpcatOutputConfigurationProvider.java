package org.eclipse.xsmp.generator;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.generator.OutputConfigurationProvider;

public class XsmpcatOutputConfigurationProvider extends OutputConfigurationProvider
{
  @Override
  public Set<OutputConfiguration> getOutputConfigurations()
  {
    return newHashSet();
  }

}
