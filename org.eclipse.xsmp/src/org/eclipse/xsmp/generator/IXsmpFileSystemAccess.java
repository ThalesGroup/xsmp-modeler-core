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

import java.util.concurrent.Future;

import org.eclipse.xtext.generator.IFileSystemAccess2;

/**
 * A File System Access with support for merging and formatting
 */
public interface IXsmpFileSystemAccess extends IFileSystemAccess2
{
  /**
   * Generate a file from a Future content
   *
   * @param fileName
   *          the file name
   * @param outputConfigurationName
   *          the output configuration name
   * @param content
   *          the future content
   */
  void generateFile(String fileName, String outputConfigurationName, Future<CharSequence> content);

  /**
   * Generate a file with support for merging and formatting.
   * If the file does dot exist, the content is generated as is.
   * Otherwise, use the merger to merge the content of existing file with the new content.
   *
   * @param fileName
   *          the file name
   * @param outputConfigurationName
   *          the output configuration name
   * @param content
   *          the file content
   * @param fileMerger
   *          the merger used to merge content with existing file
   * @param fileFormatter
   *          the formatter used to format the contents
   */
  void generateFile(String fileName, String outputConfigurationName, CharSequence content,
          IFileMerger fileMerger, IFileFormatter fileFormatter);

  /**
   * Generate a file with support for merging.
   * If the file does dot exist, the content is generated as is.
   * Otherwise, use the merger to merge the content of existing file with the new content.
   *
   * @param fileName
   *          the file name
   * @param outputConfigurationName
   *          the output configuration name
   * @param content
   *          the file content
   * @param fileMerger
   *          the merger used to merge content with existing file
   */
  void generateFile(String fileName, String outputConfigurationName, CharSequence content,
          IFileMerger fileMerger);

  /**
   * Generate a file with support for formatting.
   *
   * @param fileName
   *          the file name
   * @param outputConfigurationName
   *          the output configuration name
   * @param content
   *          the file content
   * @param fileFormatter
   *          the formatter used to format the contents
   */
  void generateFile(String fileName, String outputConfigurationName, CharSequence content,
          IFileFormatter fileFormatter);
}
