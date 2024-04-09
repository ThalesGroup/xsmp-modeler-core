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

import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;

/**
 * Generates code from your model files on save. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */

public abstract class XsmpGenerator extends AbstractGenerator
{

  /**
   * Generate a file with support for merging and formatting.
   * If the file does dot exist, the content is generated as is.
   * Otherwise, use the merger to merge the content of existing file with the new content.
   *
   * @param fsa
   *          the file system access
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
  protected void generateFile(IFileSystemAccess2 fsa, String fileName,
          String outputConfigurationName, CharSequence content, IFileMerger fileMerger,
          IFileFormatter fileFormatter)
  {
    if (fsa.isFile(fileName, outputConfigurationName))
    {
      content = fileMerger.merge(fsa, fileName, outputConfigurationName, content);
    }
    content = fileFormatter.format(fsa, fileName, outputConfigurationName, content);
    fsa.generateFile(fileName, outputConfigurationName, content);
  }

  /**
   * Generate a file with support for merging.
   * If the file does dot exist, the content is generated as is.
   * Otherwise, use the merger to merge the content of existing file with the new content.
   *
   * @param fsa
   *          the file system access
   * @param fileName
   *          the file name
   * @param outputConfigurationName
   *          the output configuration name
   * @param content
   *          the file content
   * @param fileMerger
   *          the merger used to merge content with existing file
   */
  protected void generateFile(IFileSystemAccess2 fsa, String fileName,
          String outputConfigurationName, CharSequence content, IFileMerger fileMerger)
  {
    if (fsa.isFile(fileName, outputConfigurationName))
    {
      content = fileMerger.merge(fsa, fileName, outputConfigurationName, content);
    }

    fsa.generateFile(fileName, outputConfigurationName, content);
  }

  /**
   * Generate a file with support for formatting.
   *
   * @param fsa
   *          the file system access
   * @param fileName
   *          the file name
   * @param outputConfigurationName
   *          the output configuration name
   * @param content
   *          the file content
   * @param fileFormatter
   *          the formatter used to format the contents
   */
  protected void generateFile(IFileSystemAccess2 fsa, String fileName,
          String outputConfigurationName, CharSequence content, IFileFormatter fileFormatter)
  {

    content = fileFormatter.format(fsa, fileName, outputConfigurationName, content);

    fsa.generateFile(fileName, outputConfigurationName, content);
  }
}