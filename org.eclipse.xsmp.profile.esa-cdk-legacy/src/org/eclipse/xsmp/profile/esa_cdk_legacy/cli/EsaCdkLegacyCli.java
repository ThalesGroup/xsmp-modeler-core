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
package org.eclipse.xsmp.profile.esa_cdk_legacy.cli;

import org.apache.commons.cli.ParseException;
import org.eclipse.xsmp.cli.XsmpCli;
import org.eclipse.xsmp.profile.esa_cdk_legacy.EsaCdkLegacyStandaloneSetup;

public class EsaCdkLegacyCli extends XsmpCli
{

  public static void main(String[] args) throws ParseException
  {
    final var injector = new EsaCdkLegacyStandaloneSetup().createInjectorAndDoEMFRegistration();
    final var main = injector.getInstance(EsaCdkLegacyCli.class);
    main.execute(args);
  }

}
