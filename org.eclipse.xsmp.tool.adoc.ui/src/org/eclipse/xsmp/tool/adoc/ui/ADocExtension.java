package org.eclipse.xsmp.tool.adoc.ui;

import org.eclipse.xsmp.ui.extension.ExtensionProvider;

import com.google.inject.Injector;

public class ADocExtension implements ExtensionProvider
{

  @Override
  public Injector getInjector(String languageName)
  {
    final var activator = ADocActivator.getInstance();
    return activator != null ? activator.getInjector(languageName) : null;
  }
}