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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.xtext.generator.IFileSystemAccess2;

public class DefaultFileMerger implements IFileMerger
{

  private final Pattern startPattern;

  private final Pattern endPattern;

  /**
   * The group 1 shall return the identifier of the marker
   *
   * @param startPattern
   * @param endPattern
   */
  public DefaultFileMerger(String startPattern, String endPattern)
  {
    this(Pattern.compile(startPattern), Pattern.compile(endPattern));
  }

  protected DefaultFileMerger(Pattern startPattern, Pattern endPattern)
  {
    this.startPattern = startPattern;
    this.endPattern = endPattern;
  }

  @Override
  public CharSequence merge(IFileSystemAccess2 fsa, String fileName, String outputConfigurationName,
          CharSequence newContent)
  {

    final var userCode = readProtectedAreaContent(
            fsa.readTextFile(fileName, outputConfigurationName));

    final var startMatcher = startPattern.matcher(newContent);
    final var endMatcher = endPattern.matcher(newContent);
    final var result = new StringBuilder();
    var end = 0;
    while (startMatcher.find(end))
    {
      result.append(newContent.subSequence(end, startMatcher.end()));

      final var id = normalizeId(startMatcher.group(1));
      end = startMatcher.end();

      // Find the end of the protected area
      while (endMatcher.find(end))
      {
        end = endMatcher.end();
        if (id.equals(normalizeId(endMatcher.group(1))))
        {
          break;
        }
      }

      final var areas = userCode.get(id);
      if (areas == null || areas.isEmpty())
      {
        result.append(newContent.subSequence(startMatcher.end(), end));
      }
      else
      {
        result.append(areas.remove(0));
        result.append(newContent.subSequence(endMatcher.start(), end));
        if (areas.isEmpty())
        {
          userCode.remove(id);
        }
      }
    }
    // append remaining text
    result.append(newContent.subSequence(end, newContent.length()));

    // backup remaining markers
    generateBackups(fsa, fileName, outputConfigurationName, userCode);
    return result;
  }

  protected void generateBackups(IFileSystemAccess2 fsa, String fileName,
          String outputConfigurationName, Map<String, List<CharSequence>> userCode)
  {

    final var lastIndex = fileName.lastIndexOf('.');
    var extension = "";

    if (lastIndex > 0)
    {
      extension = fileName.substring(lastIndex);
      fileName = fileName.substring(0, lastIndex);
    }

    for (final var areas : userCode.entrySet())
    {

      final var backupBaseName = fileName + "@" + areas.getKey();
      final var size = areas.getValue().size();
      if (size == 1)
      {
        generateBackup(fsa, backupBaseName, extension, outputConfigurationName,
                areas.getValue().get(0));
      }
      else
      {
        for (var i = 0; i < size; ++i)
        {
          generateBackup(fsa, backupBaseName + "[" + i + "]", extension, outputConfigurationName,
                  areas.getValue().get(i));
        }
      }
    }
  }

  protected void generateBackup(IFileSystemAccess2 fsa, String fileName, String extension,
          String outputConfigurationName, CharSequence content)
  {
    var i = 0;
    while (fsa.isFile(fileName + "#" + i + extension, outputConfigurationName))
    {
      ++i;
    }
    fsa.generateFile(fileName + "#" + i + extension, outputConfigurationName, content);
  }

  protected String normalizeId(String id)
  {
    return id.trim().replaceAll("\\s\\s*", "-");
  }

  protected Map<String, List<CharSequence>> readProtectedAreaContent(CharSequence content)
  {
    final Map<String, List<CharSequence>> protectedAreas = new LinkedHashMap<>();
    final var startMatcher = startPattern.matcher(content);
    final var endMatcher = endPattern.matcher(content);
    var end = 0;
    while (startMatcher.find(end))
    {

      final var id = normalizeId(startMatcher.group(1));
      end = startMatcher.end();

      // Find the end of the protected area
      while (endMatcher.find(end))
      {
        end = endMatcher.start();
        if (id.equals(normalizeId(endMatcher.group(1))))
        {
          break;
        }
      }

      if (end == startMatcher.end())
      {
        end = content.length();
      }

      protectedAreas.computeIfAbsent(id, i -> new ArrayList<>())
              .add(content.subSequence(startMatcher.end(), end));
    }
    return protectedAreas;
  }
}