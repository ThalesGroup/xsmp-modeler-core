package org.eclipse.xsmp.tool.adoc.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xsmp.ui.XsmpConstants;
import org.eclipse.xsmp.ui.XsmpcatUiModule;

import com.google.inject.Binder;
import com.google.inject.name.Names;

public class ADocUIModule extends XsmpcatUiModule
{

  public ADocUIModule(AbstractUIPlugin plugin)
  {
    super(plugin);
  }

  @Override
  public void configureExtensionName(Binder binder)
  {
    binder.bind(String.class).annotatedWith(Names.named(XsmpConstants.EXTENSION_NAME))
            .toInstance("org.eclipse.xsmp.tool.adoc");
  }
}
