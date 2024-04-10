/*******************************************************************************
* Copyright (C) 2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.tool.adoc.tests;

import org.eclipse.xsmp.tool.adoc.ADocRuntimeModule;
import org.eclipse.xsmp.tool.adoc.ADocStandaloneSetup;
import org.eclipse.xtext.testing.GlobalRegistries;
import org.eclipse.xtext.testing.GlobalRegistries.GlobalStateMemento;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.IRegistryConfigurator;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ADocInjectorProvider implements IInjectorProvider, IRegistryConfigurator
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
    return new ADocStandaloneSetup() {
      @Override
      public Injector createInjector()
      {
        return Guice.createInjector(createRuntimeModule());
      }
    }.createInjectorAndDoEMFRegistration();
  }

  protected ADocRuntimeModule createRuntimeModule()
  {
    // make it work also with Maven/Tycho and OSGI
    // see https://bugs.eclipse.org/bugs/show_bug.cgi?id=493672
    return new ADocRuntimeModule() {
      @Override
      public ClassLoader bindClassLoaderToInstance()
      {
        return ADocInjectorProvider.class.getClassLoader();
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
