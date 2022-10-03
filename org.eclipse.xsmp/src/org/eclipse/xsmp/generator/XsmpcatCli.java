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
package org.eclipse.xsmp.generator;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xsmp.XsmpcatStandaloneSetup;
import org.eclipse.xsmp.lib.ECSSModelLibrary;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class XsmpcatCli
{
  static
  {
    @SuppressWarnings("unused")
    final Class< ? >[] classes = {
      org.apache.log4j.ConsoleAppender.class,
      org.apache.log4j.DailyRollingFileAppender.class,
      org.apache.log4j.PatternLayout.class };
  }

  public static void main(String[] args) throws ParseException
  {
    final var injector = new XsmpcatStandaloneSetup().createInjectorAndDoEMFRegistration();
    final var main = injector.getInstance(XsmpcatCli.class);
    main.execute(args);
  }

  @Inject
  private Provider<ResourceSet> resourceSetProvider;

  @Inject
  private IResourceValidator validator;

  @Inject
  private GeneratorDelegate generator;

  @Inject
  private JavaIoFileSystemAccess fileAccess;

  @Inject
  private IResourceServiceProvider resourceServiceProvider;

  @Inject
  private IOutputConfigurationProvider outputConfigurationProvider;

  @Inject
  private IResourceFactory resourceFactory;

  protected Options getOptions()
  {
    final var options = new Options();
    options.addOption("s", "smdl_dir", true,
            "The relative directory containing the xsmpcat files to validate/generate (default ./smdl)");

    options.addOption("v", "validate", false, "Validate files");
    options.addOption("g", "generate", false, "Run generation");
    options.addOption("h", "help", false, "Show this help");

    final var context = new Option("c", "context", true,
            "The context directories/files used as dependencies.");
    context.setArgs(Option.UNLIMITED_VALUES);
    options.addOption(context);
    return options;
  }

  /**
   * Load the ECSS SMP library
   *
   * @param rs
   *          the resource set to use
   */
  protected void loadEcssSmpLibrary(ResourceSet rs)
  {

    final var url = ECSSModelLibrary.XSMPCAT_URL;

    URI uri;

    // for rsrc, convert the opaque part to fragment
    if ("rsrc".equals(url.getProtocol()))
    {
      uri = URI.createHierarchicalURI(url.getProtocol(), null, null, url.getFile().split("/"), null,
              null);
    }
    else
    {
      uri = URI.createURI(url.toString());
    }

    final var resource = resourceFactory.createResource(uri);
    try
    {
      resource.load(url.openStream(), null);
      rs.getResources().add(resource);
    }
    catch (final IOException e)
    {
      e.printStackTrace();
    }

  }

  protected void printHelp(Options options)
  {
    final var formatter = new HelpFormatter();
    formatter.printHelp("java -jar <archive>.jar",
            "Xsmpcat generator/validator Command Line Interface", options, null, true);
  }

  protected void execute(String[] args)
  {
    final var ns = System.nanoTime();
    final var options = getOptions();

    // Create a parser
    final CommandLineParser parser = new DefaultParser();

    // parse the options passed as command line arguments
    final CommandLine cmd;
    try
    {
      cmd = parser.parse(options, args);
    }
    catch (final ParseException e)
    {
      System.err.println(e.getLocalizedMessage());
      printHelp(options);
      return;
    }
    if (cmd.hasOption("h"))
    {
      printHelp(options);
      return;
    }
    final var rs = resourceSetProvider.get();

    System.out.print("Loading ECSS SMP library ... ");
    loadEcssSmpLibrary(rs);
    System.out.println("Done.");

    // load files from context
    final var context = cmd.getOptionValues("context");
    if (context != null)
    {
      for (final String ctx : context)
      {
        try
        {
          Files.walkFileTree(Paths.get(ctx), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
              throws IOException
            {
              final var uri = URI.createFileURI(file.toFile().getCanonicalPath());
              if (resourceServiceProvider.canHandle(uri))
              {
                // Load the context resource
                System.out.print("Loading " + uri.toFileString() + " ... ");
                rs.getResource(uri, true);
                System.out.println("Done.");
              }
              return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
              throws IOException
            {
              if (attrs.isSymbolicLink())
              {
                return FileVisitResult.SKIP_SUBTREE;
              }
              return super.preVisitDirectory(dir, attrs);
            }
          });
        }
        catch (final IOException e)
        {
          System.err.println("Invalid context:" + e.getLocalizedMessage());
        }
      }
    }

    // Configure output configurations
    // TODO handle cmd line parameter to configure ?
    outputConfigurationProvider.getOutputConfigurations()
            .forEach(o -> fileAccess.getOutputConfigurations().put(o.getName(), o));

    var smdl_dir = cmd.getOptionValue("smdl_dir");
    if (smdl_dir == null)
    {
      smdl_dir = "./smdl";
    }

    final var validate = cmd.hasOption("validate") || !cmd.hasOption("generate");
    final var generate = cmd.hasOption("generate") || !cmd.hasOption("validate");

    // generate / validate files in smdl_dir
    try
    {
      Files.walkFileTree(Paths.get(smdl_dir), new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
        {
          final var uri = URI.createFileURI(file.toFile().getCanonicalPath());
          if (resourceServiceProvider.canHandle(uri))
          {
            // Load the resource
            final var resource = rs.getResource(uri, true);
            // Validate the resource
            if (validate)
            {
              System.out.print("Validating " + uri.toFileString() + " ... ");
              final var list = validator.validate(resource, CheckMode.ALL,
                      CancelIndicator.NullImpl);
              if (!list.isEmpty())
              {
                System.out.println("Failed.");
                for (final Issue issue : list)
                {
                  System.err.println(issue);
                }
                return FileVisitResult.CONTINUE;
              }
              System.out.println("Done.");
            }
            // start the generator
            if (generate)
            {
              System.out.print("Generating " + uri.toFileString() + " ... ");
              final var context = new GeneratorContext();
              context.setCancelIndicator(CancelIndicator.NullImpl);
              generator.generate(resource, fileAccess, context);
              System.out.println("Done.");
            }
          }
          return super.visitFile(file, attrs);
        }
      });
    }
    catch (final IOException e)
    {
      e.printStackTrace();
    }
    final var df = new DecimalFormat("##.##");
    df.setRoundingMode(RoundingMode.DOWN);

    System.out.println("Executed in " + df.format((System.nanoTime() - ns) / 1000000000.) + " s");
  }
}
