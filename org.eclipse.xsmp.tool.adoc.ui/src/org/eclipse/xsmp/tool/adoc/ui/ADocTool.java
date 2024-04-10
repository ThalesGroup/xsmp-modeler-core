package org.eclipse.xsmp.tool.adoc.ui;

import org.eclipse.xsmp.tool.adoc.AbstractADocTool;

import com.google.inject.Injector;

public class ADocTool extends AbstractADocTool
{

  @Override
  public Injector getInjector(String languageName)
  {
    final var activator = ADocActivator.getInstance();
    return activator != null ? activator.getInjector(languageName) : null;
  }
}