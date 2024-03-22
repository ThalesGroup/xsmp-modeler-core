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
package org.eclipse.xsmp.tool.adoc.cli;

import org.apache.commons.cli.ParseException;
import org.eclipse.xsmp.cli.XsmpCli;
import org.eclipse.xsmp.tool.adoc.ADocStandaloneSetup;

public class ADocCli extends XsmpCli
{
  public static void main(String[] args) throws ParseException
  {
    final var injector = new ADocStandaloneSetup().createInjectorAndDoEMFRegistration();
    final var main = injector.getInstance(ADocCli.class);
    main.execute(args);
  }

}
