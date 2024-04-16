/**
 *-------------------------------------------------------------------------
 * Copyright (C) 2021 THALES ALENIA SPACE FRANCE. All rights reserved
 *-------------------------------------------------------------------------
 */
package org.eclipse.xsmp.profile.tas_mdk.ui;

import org.eclipse.xsmp.ui.internal.XsmpActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.google.inject.Injector;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass.
 */
public class TasMdkExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory
{

  @Override
  protected Bundle getBundle()
  {
    return FrameworkUtil.getBundle(TasMdkActivator.class);
  }

  @Override
  protected Injector getInjector()
  {
    final var activator = TasMdkActivator.getInstance();
    return activator != null ? activator.getInjector(XsmpActivator.ORG_ECLIPSE_XSMP_XSMPCAT) : null;
  }

}
