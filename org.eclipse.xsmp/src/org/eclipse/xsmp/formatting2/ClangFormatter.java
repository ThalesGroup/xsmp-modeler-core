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
package org.eclipse.xsmp.formatting2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.eclipse.emf.common.util.URI;

import com.google.inject.Singleton;

@Singleton
public class ClangFormatter
{

  protected String toFileString(URI uri)
  {
    if (uri.isFile())
    {
      return uri.toFileString();
    }

    return uri.lastSegment();
  }

  /**
   * Format the content with clang-format
   *
   * @param uri
   *          the file URI of the generated file
   * @param content
   *          content to format
   * @return formatted content
   */
  public CharSequence format(URI uri, CharSequence content)
  {
    try
    {
      final var pb = new ProcessBuilder("clang-format", "-assume-filename=" + toFileString(uri));

      final var process = pb.start();

      final var writer = new PrintWriter(
              new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8), true);
      writer.append(content).close();

      final var reader = new BufferedReader(
              new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
      final var result = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null)
      {
        result.append(line).append(System.lineSeparator());
      }
      reader.close();

      final var exitCode = process.waitFor();
      if (exitCode == 0)
      {
        return result;
      }
    }
    catch (final IOException e)
    {
      // ignore
    }
    catch (final InterruptedException e)
    {
      Thread.currentThread().interrupt();
    }
    return content;
  }
}
