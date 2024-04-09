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
package org.eclipse.xsmp.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.generator.IFileSystemAccess2;

import com.google.inject.Singleton;

@Singleton
public class ClangFormatter implements IFileFormatter
{
  /**
   * Convert an URI to a file string
   * This function only handle File URI, it should be overridden to handle platform URI for example
   *
   * @param uri
   *          the input URI
   * @return the file path
   */
  protected String toFileString(URI uri)
  {
    if (uri.isFile())
    {
      return uri.toFileString();
    }
    // fallback on file name
    return uri.lastSegment();
  }

  private static String os = System.getProperty("os.name").toLowerCase();

  private static boolean isWindows()
  {
    return os.contains("win");
  }

  /**
   * Format the content with clang-format
   *
   * @param filename
   *          the file name of the formatted file
   * @param content
   *          content to format
   * @return formatted content
   */
  protected CharSequence format(String filename, CharSequence content)
  {
    try
    {
      final var pb = new ProcessBuilder(isWindows() ? "clang-format.exe" : "clang-format",
              "-assume-filename=" + filename);

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

  @Override
  public CharSequence format(IFileSystemAccess2 fsa, String fileName,
          String outputConfigurationName, CharSequence contents)
  {
    return format(toFileString(fsa.getURI(fileName, outputConfigurationName)), contents);
  }
}
