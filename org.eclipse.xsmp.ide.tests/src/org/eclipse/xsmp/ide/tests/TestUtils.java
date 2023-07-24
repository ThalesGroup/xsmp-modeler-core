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
package org.eclipse.xsmp.ide.tests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.eclipse.xtext.testing.TextDocumentPositionConfiguration;

import com.google.common.collect.ImmutableMap;

public class TestUtils
{
  public static void loadEcssSmpLibrary(TextDocumentPositionConfiguration it)
  {
    final var inputStream = TestUtils.class
            .getResourceAsStream("/org/eclipse/xsmp/lib/ecss.smp.xsmpcat");

    try
    {
      it.setFilesInScope(ImmutableMap.<String, CharSequence> builder()
              .put("ecss.smp.xsmpcat", IOUtils.toString(inputStream, StandardCharsets.UTF_8))
              .build());
    }
    catch (final IOException e)
    {
      e.printStackTrace();
    }
  }
}
