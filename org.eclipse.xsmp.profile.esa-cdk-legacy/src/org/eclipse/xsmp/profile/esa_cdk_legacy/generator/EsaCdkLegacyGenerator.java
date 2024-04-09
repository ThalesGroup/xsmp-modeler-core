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
package org.eclipse.xsmp.profile.esa_cdk_legacy.generator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.generator.ClangFormatter;
import org.eclipse.xsmp.generator.DefaultFileMerger;
import org.eclipse.xsmp.generator.IFileMerger;
import org.eclipse.xsmp.generator.XsmpGenerator;
import org.eclipse.xsmp.model.xsmp.Catalogue;
import org.eclipse.xsmp.model.xsmp.Namespace;
import org.eclipse.xsmp.model.xsmp.Type;
import org.eclipse.xsmp.util.XsmpUtil;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Generates code from your model files on save. See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
@Singleton
public final class EsaCdkLegacyGenerator extends XsmpGenerator
{

  private final IFileMerger cppMerger = new DefaultFileMerger("//\\s*MARKER\\s*:([^:]+):\\s*START",
          "//\\s*MARKER\\s*:([^:]+):\\s*END");

  @Inject
  private ClangFormatter formatter;

  @Inject
  protected XsmpUtil ext;

  @Inject
  private Generator generator;

  @Override
  public void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
  {

    final var catalogue = (Catalogue) input.getContents().get(0);

    generateCatalogue(catalogue, fsa);

  }

  /**
   * Generate package and catalogue
   *
   * @param date
   */
  protected void generateCatalogue(Catalogue catalogue, IFileSystemAccess2 fsa)
  {
    generateFile(fsa, catalogue.getName() + ".cpp", EsaCdkLegacyOutputConfigurationProvider.SRC,
            generator.generateSource(catalogue), cppMerger, formatter);
    generateFile(fsa, catalogue.getName() + ".hpp", EsaCdkLegacyOutputConfigurationProvider.SRC,
            generator.generateInclude(catalogue), cppMerger, formatter);

    catalogue.getMember().stream().filter(Namespace.class::isInstance)
            .forEach(ns -> generateNamespace((Namespace) ns, fsa, catalogue));
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

  protected void generateType(Type type, IFileSystemAccess2 fsa, Catalogue cat)
  {

    final var path = ext.fqn(type).toString("/");

    generateFile(fsa, path + ".cpp", EsaCdkLegacyOutputConfigurationProvider.SRC,
            generator.generateSource(type), cppMerger, formatter);
    generateFile(fsa, path + ".hpp", EsaCdkLegacyOutputConfigurationProvider.SRC,
            generator.generateInclude(type), cppMerger, formatter);

  }

}