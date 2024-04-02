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
package org.eclipse.xsmp.generator.cpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.generator.cpp.type.ArrayGenerator;
import org.eclipse.xsmp.generator.cpp.type.ClassGenerator;
import org.eclipse.xsmp.generator.cpp.type.ComponentGenerator;
import org.eclipse.xsmp.generator.cpp.type.EnumerationGenerator;
import org.eclipse.xsmp.generator.cpp.type.ExceptionGenerator;
import org.eclipse.xsmp.generator.cpp.type.FloatGenerator;
import org.eclipse.xsmp.generator.cpp.type.IntegerGenerator;
import org.eclipse.xsmp.generator.cpp.type.InterfaceGenerator;
import org.eclipse.xsmp.generator.cpp.type.NativeTypeGenerator;
import org.eclipse.xsmp.generator.cpp.type.StringGenerator;
import org.eclipse.xsmp.generator.cpp.type.StructureGenerator;
import org.eclipse.xsmp.model.xsmp.Catalogue;
import org.eclipse.xsmp.model.xsmp.Namespace;
import org.eclipse.xsmp.model.xsmp.ReferenceType;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

import com.google.inject.Inject;

/**
 * @author daveluy
 */
public class CppGenerator extends AbstractGenerator
{

  @Inject
  protected GeneratorUtil ext;

  @Override
  public void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
  {
    generateCatalogue((Catalogue) input.getContents().get(0), fsa);
  }

  /**
   * Generate package and catalogue
   *
   * @param date
   */
  protected void generateCatalogue(Catalogue cat, IFileSystemAccess2 fsa)
  {
    final var acceptor = new IncludeAcceptor();
    catalogueGenerator.collectIncludes(cat, acceptor);

    final var name = ext.name(cat);

    generateFile(fsa, name + ".cpp", CppOutputConfigurationProvider.SRC_GEN,
            catalogueGenerator.generateSourceGen(cat, false, acceptor, cat));

    generateFile(fsa, name + ".h", CppOutputConfigurationProvider.INCLUDE_GEN,
            catalogueGenerator.generateHeaderGen(cat, false, acceptor, cat));

    generateFile(fsa, name + ".pkg.cpp", CppOutputConfigurationProvider.SRC_GEN,
            catalogueGenerator.generatePkgFile(cat, false, acceptor, cat));

    cat.getMember().stream().filter(Namespace.class::isInstance)
            .forEach(ns -> generateNamespace((Namespace) ns, fsa, cat));
  }

  protected void generateNamespace(Namespace ns, IFileSystemAccess2 fsa, Catalogue cat)
  {
    // generate types
    ns.getMember().stream().filter(Type.class::isInstance)
            .forEach(type -> generateType((Type) type, fsa, cat));

    // generate nested Namespaces
    ns.getMember().stream().filter(Namespace.class::isInstance)
            .forEach(nns -> generateNamespace((Namespace) nns, fsa, cat));
  }

  protected boolean useGenerationGapPattern(EObject obj)
  {
    return obj instanceof ReferenceType;
  }

  /**
   * Generate all types
   *
   * @param cat
   */
  @SuppressWarnings("unchecked")
  protected void generateType(Type type, IFileSystemAccess2 fsa, Catalogue cat)
  {

    final var typeGenerator = getGenerator(type);
    if (typeGenerator == null)
    {
      return;
    }

    final var acceptor = new IncludeAcceptor();
    typeGenerator.collectIncludes(type, acceptor);

    // include path of user code
    final var includePath = ext.fqn(type).toString("/") + ".h";

    // if the file already exist in include directories or the type requires the generation gap
    // pattern, use the generation gap pattern
    final var useGenerationGapPattern = fsa.isFile(includePath,
            CppOutputConfigurationProvider.INCLUDE) || useGenerationGapPattern(type);

    // generate header in include directory
    if (useGenerationGapPattern)
    {
      generateFile(fsa, includePath, CppOutputConfigurationProvider.INCLUDE,
              typeGenerator.generateHeader(type, cat));
    }

    // generate source in src directory if file does not exist
    final var sourcePath = ext.fqn(type).toString("/") + ".cpp";

    generateFile(fsa, sourcePath, CppOutputConfigurationProvider.SRC,
            typeGenerator.generateSource(type, useGenerationGapPattern, cat));

    // generate header in include-gen directory
    generateFile(fsa, useGenerationGapPattern ? ext.fqnGen(type).toString("/") + ".h" : includePath,
            CppOutputConfigurationProvider.INCLUDE_GEN,
            typeGenerator.generateHeaderGen(type, useGenerationGapPattern, acceptor, cat));

    // generate source in src-gen directory
    generateFile(fsa, ext.fqnGen(type).toString("/") + ".cpp",
            CppOutputConfigurationProvider.SRC_GEN,
            typeGenerator.generateSourceGen(type, useGenerationGapPattern, acceptor, cat));
  }

  @Inject
  protected CatalogueGenerator catalogueGenerator;

  @Inject
  protected ArrayGenerator arrayGenerator;

  @Inject
  protected EnumerationGenerator enumerationGenerator;

  @Inject
  protected FloatGenerator floatGenerator;

  @Inject
  protected IntegerGenerator integerGenerator;

  @Inject
  protected StringGenerator stringGenerator;

  @Inject
  protected StructureGenerator structureGenerator;

  @Inject
  protected ClassGenerator classGenerator;

  @Inject
  protected ExceptionGenerator exceptionGenerator;

  @Inject
  protected InterfaceGenerator interfaceGenerator;

  @Inject
  protected ComponentGenerator componentGenerator;

  @Inject
  protected NativeTypeGenerator nativeTypeGenerator;

  @SuppressWarnings("rawtypes")
  protected AbstractFileGenerator getGenerator(Type t)
  {
    return switch (t.eClass().getClassifierID())
    {
      case XsmpPackage.ARRAY -> arrayGenerator;
      case XsmpPackage.ENUMERATION -> enumerationGenerator;
      case XsmpPackage.FLOAT -> floatGenerator;
      case XsmpPackage.INTEGER -> integerGenerator;
      case XsmpPackage.STRING -> stringGenerator;
      case XsmpPackage.STRUCTURE -> structureGenerator;
      case XsmpPackage.CLASS -> classGenerator;
      case XsmpPackage.EXCEPTION -> exceptionGenerator;
      case XsmpPackage.INTERFACE -> interfaceGenerator;
      case XsmpPackage.MODEL, XsmpPackage.SERVICE -> componentGenerator;
      case XsmpPackage.NATIVE_TYPE -> nativeTypeGenerator;
      default -> null;
    };
  }

  /**
   * Format the content with the CDT formatter
   *
   * @param fileName
   *          the file name of the generated file
   * @param content
   *          content to format
   * @return formatted content
   */
  protected CharSequence format(String fileName, CharSequence content)
  {
    try
    {
      final var pb = new ProcessBuilder("clang-format", "-style=LLVM",
              "-assume-filename=" + fileName);

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

  protected void generateFile(IFileSystemAccess2 fsa, String fileName,
          String outputConfigurationName, CharSequence contents)
  {
    if (contents != null)
    {
      fsa.generateFile(fileName, outputConfigurationName, format(fileName, contents));
    }
  }

}
