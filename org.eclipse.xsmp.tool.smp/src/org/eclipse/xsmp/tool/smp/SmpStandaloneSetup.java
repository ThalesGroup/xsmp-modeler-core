/*******************************************************************************
* Copyright (C) 2020-2022 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.tool.smp;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.XsmpcatStandaloneSetup;
import org.eclipse.xsmp.tool.smp.util.SmpResourceFactoryImpl;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class SmpStandaloneSetup extends XsmpcatStandaloneSetup
{

  /**
   * perform initialization
   */
  public static void doSetup()
  {
    new SmpStandaloneSetup().createInjectorAndDoEMFRegistration();
  }

  @Override
  public void register(Injector injector)
  {
    super.register(injector);

    // Add resource factory for smpcat/smppkg/smpcfg files
    final var resourceFactory = injector.getInstance(SmpResourceFactoryImpl.class);
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("smpcat", resourceFactory);
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("smppkg", resourceFactory);
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("smpcfg", resourceFactory);
  }

  @Override
  public Injector createInjector()
  {
    return Guice.createInjector(new SmpRuntimeModule());
  }

}
