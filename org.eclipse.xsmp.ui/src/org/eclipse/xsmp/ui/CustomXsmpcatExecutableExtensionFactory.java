package org.eclipse.xsmp.ui;

import org.eclipse.xsmp.ui.internal.XsmpActivator;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.google.inject.Injector;

public class CustomXsmpcatExecutableExtensionFactory extends XsmpcatExecutableExtensionFactory
{
  @Override
  protected Bundle getBundle()
  {
    return FrameworkUtil.getBundle(XsmpUIPlugin.class);
  }

  @Override
  protected Injector getInjector()
  {
    final var activator = XsmpUIPlugin.getInstance();
    return activator != null ? activator.getInjector(XsmpActivator.ORG_ECLIPSE_XSMP_XSMPCAT) : null;
  }

}
