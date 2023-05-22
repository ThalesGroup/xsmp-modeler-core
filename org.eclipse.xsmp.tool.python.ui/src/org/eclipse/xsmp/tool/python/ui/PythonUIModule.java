package org.eclipse.xsmp.tool.python.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xsmp.ui.XsmpcatConstants;
import org.eclipse.xsmp.ui.XsmpcatUiModule;

import com.google.inject.Binder;
import com.google.inject.name.Names;

public class PythonUIModule extends XsmpcatUiModule
{

  public PythonUIModule(AbstractUIPlugin plugin)
  {
    super(plugin);
  }

  @Override
  public void configureExtensionName(Binder binder)
  {
    binder.bind(String.class).annotatedWith(Names.named(XsmpcatConstants.EXTENSION_NAME))
            .toInstance("org.eclipse.xsmp.tool.python");
  }
}
