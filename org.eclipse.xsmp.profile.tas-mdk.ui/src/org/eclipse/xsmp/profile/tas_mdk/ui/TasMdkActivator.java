/**
 *-------------------------------------------------------------------------
 * Copyright (C) 2021 THALES ALENIA SPACE FRANCE. All rights reserved
 *-------------------------------------------------------------------------
 */
package org.eclipse.xsmp.profile.tas_mdk.ui;

import org.eclipse.xsmp.profile.tas_mdk.TasMdkRuntimeModule;
import org.eclipse.xsmp.ui.AbstractXsmpActivator;
import org.osgi.framework.BundleContext;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass.
 */
public class TasMdkActivator extends AbstractXsmpActivator
{
  public static final String PLUGIN_ID = "org.eclipse.xsmp.profile.tas_mdk.ui";

  private static TasMdkActivator instance;

  private static void setInstance(TasMdkActivator instance)
  {
    TasMdkActivator.instance = instance;
  }

  public static TasMdkActivator getInstance()
  {
    return instance;
  }

  @Override
  public void start(BundleContext context) throws Exception
  {
    super.start(context);
    setInstance(this);
  }

  @Override
  public void stop(BundleContext context) throws Exception
  {
    setInstance(null);
    super.stop(context);
  }

  @Override
  protected com.google.inject.Module getRuntimeModule(String grammar)
  {
    if (ORG_ECLIPSE_XSMP_XSMPCAT.equals(grammar))
    {
      return new TasMdkRuntimeModule();
    }
    return super.getRuntimeModule(grammar);
  }

  @Override
  protected com.google.inject.Module getUiModule(String grammar)
  {
    if (ORG_ECLIPSE_XSMP_XSMPCAT.equals(grammar))
    {
      return new TasMdkUiModule(this);
    }
    return super.getUiModule(grammar);
  }
}
