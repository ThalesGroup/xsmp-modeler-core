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
package com.thalesaleniaspace.compilechain;

import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;

import org.eclipse.cdt.managedbuilder.language.settings.providers.GCCBuiltinSpecsDetector;
import org.w3c.dom.Element;

public class CompileChainSpecsDetector extends GCCBuiltinSpecsDetector
{

  private static final String ATTR_ENV_HASH = "env-hash"; //$NON-NLS-1$

  private static final String ATTR_STORE_ENTRIES_WITH_PROJECT = "store-entries-with-project"; //$NON-NLS-1$

  private long envPathHash = -1;

  @Override
  public Element serializeAttributes(Element parentElement)
  {
    final var elementProvider = super.serializeAttributes(parentElement);

    // do not store env-hash in xml file
    elementProvider.removeAttribute(ATTR_ENV_HASH);
    // do not store entries with project
    elementProvider.removeAttribute(ATTR_STORE_ENTRIES_WITH_PROJECT);

    return elementProvider;
  }

  @Override
  public String getCommand()
  {
    // force the make CMD
    return "make BUILD_TYPE=${config_name:${ProjName}} -C \"${workspace_loc:/${ProjName}}\" print_compiler_settings \"${INPUTS}\"";
  }

  @Override
  protected String getCompilerCommand(String languageId)
  {
    return getCommand();
  }

  @Override
  public CompileChainSpecsDetector cloneShallow() throws CloneNotSupportedException
  {
    return (CompileChainSpecsDetector) super.cloneShallow();
  }

  @Override
  public CompileChainSpecsDetector clone() throws CloneNotSupportedException
  {
    return (CompileChainSpecsDetector) super.clone();
  }

  /**
   * Calculate hash code from the Makefile and the component.conf Any change to one of these file
   * will trigger a rebuild of the index
   */
  private long calculateEnvHash()
  {

    try
    {
      // compute env hash from Makefile and component.conf
      final var project = currentProject != null ? currentProject
              : currentCfgDescription.getProjectDescription().getProject();
      var initialized = false;

      final var md = MessageDigest.getInstance("MD5");
      final String[] deps = {"Makefile", "component.conf" };

      for (final String dep : deps)
      {
        final var file = project.getFile(dep);
        if (file.exists())
        {
          try (var is = file.getContents(); var dis = new DigestInputStream(is, md))
          {
            dis.readAllBytes();
            initialized = true;
          }
        }
      }

      if (initialized)
      {
        return new BigInteger(1, md.digest()).longValue();
      }
    }
    catch (final Exception e)
    {
      e.printStackTrace();
    }

    return -1;
  }

  @Override
  protected boolean validateEnvironment()
  {

    final var envHashNew = calculateEnvHash();
    if (envHashNew != envPathHash)
    {
      envPathHash = envHashNew;
      return false;
    }

    return true;
  }

}
