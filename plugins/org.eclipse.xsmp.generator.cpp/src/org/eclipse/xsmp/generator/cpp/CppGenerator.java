/*******************************************************************************
* Copyright (C) 2020-2024 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.generator.cpp;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.generator.ClangFormatter;
import org.eclipse.xsmp.generator.IXsmpFileSystemAccess;
import org.eclipse.xsmp.generator.XsmpGenerator;
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
import org.eclipse.xsmp.generator.cpp.type.ValueReferenceGenerator;
import org.eclipse.xsmp.model.xsmp.Catalogue;
import org.eclipse.xsmp.model.xsmp.Namespace;
import org.eclipse.xsmp.model.xsmp.ReferenceType;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.model.xsmp.XsmpPackage;

import com.google.inject.Inject;

/**
 * @author daveluy
 */
public class CppGenerator extends XsmpGenerator
{

  @Inject
  protected GeneratorUtil ext;

  @Override
  public void generate(Resource input, IXsmpFileSystemAccess fsa)
  {
    generateCatalogue((Catalogue) input.getContents().get(0), fsa);
  }

  /**
   * Generate package and catalogue
   *
   * @param date
   */
  protected void generateCatalogue(Catalogue cat, IXsmpFileSystemAccess fsa)
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

    for (final var member : cat.getMember())
    {
      if (member instanceof final Namespace ns)
      {
        generateNamespace(ns, fsa, cat);
      }
    }
  }

  protected void generateNamespace(Namespace ns, IXsmpFileSystemAccess fsa, Catalogue cat)
  {
    for (final var member : ns.getMember())
    {
      if (member instanceof final Type type)
      {
        generateType(type, fsa, cat);
      }
      else if (member instanceof final Namespace nns)
      {
        generateNamespace(nns, fsa, cat);
      }
    }
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
  protected void generateType(Type type, IXsmpFileSystemAccess fsa, Catalogue cat)
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
    generateFile(fsa, ext.fqn(type, useGenerationGapPattern).toString("/") + ".cpp",
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

  @Inject
  protected ValueReferenceGenerator valueReferenceGenerator;

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
      case XsmpPackage.VALUE_REFERENCE -> valueReferenceGenerator;
      default -> null;
    };
  }

  @Inject
  private ClangFormatter formatter;

  protected void generateFile(IXsmpFileSystemAccess fsa, String fileName,
          String outputConfigurationName, CharSequence contents)
  {
    if (contents != null)
    {
      fsa.generateFile(fileName, outputConfigurationName, contents, formatter);
    }
  }

}
