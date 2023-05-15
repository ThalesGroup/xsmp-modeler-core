/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.tool.smp.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xsmp.cli.XsmpcatCli;
import org.eclipse.xsmp.tool.smp.SmpStandaloneSetup;
import org.eclipse.xsmp.tool.smp.importer.SmpImporter;
import org.eclipse.xsmp.tool.smp.util.SmpResourceSet;
import org.eclipse.xsmp.validation.XsmpcatValidator;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SmpCli extends XsmpcatCli
{
  // inject the default validator to register it
  @Inject
  XsmpcatValidator validator;

  @Inject
  SmpcatResourceServiceProvider smpcatResourceServiceProvider;

  @Inject
  private SmpImporter importer;

  @Inject
  private Provider<SmpResourceSet> smpResourceSetProvider;

  private static final String IMPORT_OPTION = "import";

  public static void main(String[] args) throws ParseException
  {
    final var injector = new SmpStandaloneSetup().createInjectorAndDoEMFRegistration();
    final var main = injector.getInstance(SmpCli.class);

    main.execute(args);
  }

  @Override
  protected Options getOptions()
  {
    final var options = super.getOptions();

    options.addOption("i", IMPORT_OPTION, false, "Import smpcat file");

    return options;
  }

  @Override
  protected ResourceSet createResourceSet(CommandLine cmd)
  {
    if (cmd.hasOption(IMPORT_OPTION))
    {

      XsmpcatCli.LOG.info("Registering ECSS SMP library ... ");

      final var rs = smpResourceSetProvider.get();

      // replace the resource service provider to handle smpcat files
      resourceServiceProvider = smpcatResourceServiceProvider;
      // replace the generator with the smpcat impoter
      generator = importer;
      fileAccess.setOutputPath("smdl");
      XsmpcatCli.LOG.info("Done.");
      return rs;
    }
    return super.createResourceSet(cmd);
  }

}
