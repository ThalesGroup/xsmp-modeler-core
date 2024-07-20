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
package org.eclipse.xsmp.tool.smp.tests;

import org.eclipse.xsmp.tool.smp.SmpRuntimeModule;
import org.eclipse.xsmp.tool.smp.SmpStandaloneSetup;
import org.eclipse.xtext.testing.GlobalRegistries;
import org.eclipse.xtext.testing.GlobalRegistries.GlobalStateMemento;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.IRegistryConfigurator;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class SmpInjectorProvider implements IInjectorProvider, IRegistryConfigurator
{

  protected GlobalStateMemento stateBeforeInjectorCreation;

  protected GlobalStateMemento stateAfterInjectorCreation;

  protected Injector injector;

  static
  {
    GlobalRegistries.initializeDefaults();
  }

  @Override
  public Injector getInjector()
  {
    if (injector == null)
    {
      this.injector = internalCreateInjector();
      stateAfterInjectorCreation = GlobalRegistries.makeCopyOfGlobalState();
    }
    return injector;
  }

  protected Injector internalCreateInjector()
  {
    return new SmpStandaloneSetup() {
      @Override
      public Injector createInjector()
      {
        return Guice.createInjector(createRuntimeModule());
      }
    }.createInjectorAndDoEMFRegistration();
  }

  protected SmpRuntimeModule createRuntimeModule()
  {
    // make it work also with Maven/Tycho and OSGI
    // see https://bugs.eclipse.org/bugs/show_bug.cgi?id=493672
    return new SmpRuntimeModule() {
      @Override
      public ClassLoader bindClassLoaderToInstance()
      {
        return SmpInjectorProvider.class.getClassLoader();
      }
    };
  }

  @Override
  public void restoreRegistry()
  {
    stateBeforeInjectorCreation.restoreGlobalState();
    stateBeforeInjectorCreation = null;
  }

  @Override
  public void setupRegistry()
  {
    stateBeforeInjectorCreation = GlobalRegistries.makeCopyOfGlobalState();
    if (injector == null)
    {
      getInjector();
    }
    stateAfterInjectorCreation.restoreGlobalState();
  }
}
