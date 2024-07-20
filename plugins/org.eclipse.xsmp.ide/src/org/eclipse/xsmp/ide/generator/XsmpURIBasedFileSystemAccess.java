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
package org.eclipse.xsmp.ide.generator;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

import org.eclipse.xtext.generator.URIBasedFileSystemAccess;

public class XsmpURIBasedFileSystemAccess extends URIBasedFileSystemAccess
{

  @Override
  public void generateFile(String fileName, String outputCfgName, CharSequence contents)
  {
    try
    {
      final var uri = getURI(fileName, outputCfgName);
      final var encoding = getEncoding(uri);
      final var postProcessed = postProcess(fileName, outputCfgName, contents, encoding);

      final var content = new ByteArrayInputStream(postProcessed.toString().getBytes(encoding));
      if (!getOutputConfig(outputCfgName).isOverrideExistingResources()
              && getConverter().exists(uri, Collections.emptyMap()))
      {
        getBeforeWrite().beforeWrite(uri, outputCfgName, content);
        return;
      }

      generateTrace(fileName, outputCfgName, postProcessed);
      generateFile(fileName, outputCfgName, content);

    }
    catch (final UnsupportedEncodingException e)
    {
      throw new RuntimeException(e);
    }
  }
}
