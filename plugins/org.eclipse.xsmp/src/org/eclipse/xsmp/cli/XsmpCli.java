/*******************************************************************************
* Copyright (C) 2020-2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.xsmp.cli;

import static com.google.common.collect.Maps.uniqueIndex;

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
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xsmp.XsmpcatStandaloneSetup;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class XsmpCli
{
  protected static final Logger LOG = Logger.getLogger(XsmpCli.class);

  public static final String VALIDATE_OPTION = "validate";

  public static final String GENERATE_OPTION = "generate";

  public static final String SMDL_DIR_OPTION = "smdl_dir";

  public static final String CONTEXT_OPTION = "context";

  public static final String HELP_OPTION = "help";

  public static final int SUCCESS_EXIT = 0;

  public static final int FAILURE_EXIT = 1;

  private int exitStatus = SUCCESS_EXIT;

  protected void setExitStatus(int status)
  {
    exitStatus = status;
  }

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
    final var main = injector.getInstance(XsmpCli.class);
    main.execute(args);
  }

  @Inject
  protected Provider<ResourceSet> resourceSetProvider;

  @Inject
  private IResourceValidator validator;

  @Inject
  protected GeneratorDelegate generator;

  @Inject
  protected JavaIoFileSystemAccess fileAccess;

  @Inject
  protected IResourceServiceProvider resourceServiceProvider;

  @Inject
  private IOutputConfigurationProvider outputConfigurationProvider;

  protected ResourceSet resourceSet;

  protected Options getOptions()
  {
    final var options = new Options();
    options.addOption("s", SMDL_DIR_OPTION, true,
            "Absolute or relative directory containing the input files (default ./smdl)");

    options.addOption("v", VALIDATE_OPTION, false, "Validate files");
    options.addOption("g", GENERATE_OPTION, false, "Run generation");
    options.addOption("h", HELP_OPTION, false, "Show this help");

    final var context = new Option("c", CONTEXT_OPTION, true,
            "The context directories/files used as dependencies.");
    context.setArgs(Option.UNLIMITED_VALUES);
    options.addOption(context);
    return options;
  }

  protected void printHelp(Options options)
  {
    final var formatter = new HelpFormatter();
    formatter.printHelp("java -jar <archive>.jar",
            "Xsmpcat generator/validator Command Line Interface", options, null, true);
  }

  protected final void execute(String[] args) throws ParseException
  {
    final var ns = System.nanoTime();
    final var options = getOptions();
    try
    {
      // Create a parser
      final CommandLineParser parser = new DefaultParser();

      // parse the options passed as command line arguments
      final var cmd = parser.parse(options, args);

      if (cmd.hasOption("h"))
      {
        printHelp(options);
        return;
      }

      doExecute(cmd);
    }
    catch (final ParseException e)
    {
      printHelp(options);
      LOG.fatal(e.getLocalizedMessage());
      setExitStatus(FAILURE_EXIT);
    }
    catch (final Exception e)
    {
      LOG.fatal("Unexpected error", e);
      setExitStatus(FAILURE_EXIT);
    }
    finally
    {
      final var df = new DecimalFormat("##.##");
      df.setRoundingMode(RoundingMode.DOWN);
      LOG.info("Executed in " + df.format((System.nanoTime() - ns) / 1000000000.) + " s");
      System.exit(exitStatus);
    }
  }

  protected ResourceSet createResourceSet(CommandLine cmd)
  {
    return resourceSetProvider.get();
  }

  protected void doExecute(CommandLine cmd)
  {
    resourceSet = createResourceSet(cmd);

    final var context = cmd.getOptionValues(CONTEXT_OPTION);
    if (context != null)
    {
      for (final String ctx : context)
      {
        try
        {
          Files.walkFileTree(Paths.get(ctx), new FileLoader());
        }
        catch (final IOException e)
        {
          LOG.error("Invalid context:" + e.getLocalizedMessage());
          setExitStatus(FAILURE_EXIT);
        }
      }
    }

    // Configure output configurations
    fileAccess.setOutputConfigurations(
            uniqueIndex(outputConfigurationProvider.getOutputConfigurations(),
                    (Function<OutputConfiguration, String>) OutputConfiguration::getName));

    final var smdlDir = cmd.getOptionValue(SMDL_DIR_OPTION, "./smdl");

    final var validate = cmd.hasOption(VALIDATE_OPTION) || !cmd.hasOption(GENERATE_OPTION);
    final var generate = cmd.hasOption(GENERATE_OPTION) || !cmd.hasOption(VALIDATE_OPTION);

    // generate / validate files in smdl_dir
    try
    {
      Files.walkFileTree(Paths.get(smdlDir), new FileVisitor(validate, generate));
    }
    catch (final IOException e)
    {
      LOG.fatal(e);
      setExitStatus(FAILURE_EXIT);
    }

  }

  protected void load(URI uri)
  {
    // Load the context resource
    LOG.info("Loading " + uri.toFileString() + " ... ");
    resourceSet.getResource(uri, true);
    LOG.info("Done.");
  }

  /**
   * Validate a resource
   *
   * @param resource
   *          the resource to validate
   * @return true if the resource has error(s)
   */
  protected boolean validate(Resource resource)
  {
    LOG.info("Validating " + resource.getURI().toFileString() + " ... ");
    final var list = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
    var hasError = false;
    for (final Issue issue : list)
    {
      if (issue.getSeverity() == Severity.ERROR)
      {
        LOG.error(createIssueMessage(issue));
        hasError = true;
      }
      else
      {
        LOG.info(createIssueMessage(issue));
      }

    }

    if (hasError)
    {
      LOG.error("Failed.");
      setExitStatus(FAILURE_EXIT);
    }
    else
    {
      LOG.info("Done.");
    }

    return hasError;
  }

  /**
   * Generate the resource
   *
   * @param resource
   *          the resource to generate
   */
  protected void generate(Resource resource)
  {
    LOG.info("Generating " + resource.getURI().toFileString() + " ... ");
    final var context = new GeneratorContext();
    context.setCancelIndicator(CancelIndicator.NullImpl);
    try
    {
      generator.generate(resource, fileAccess, context);
      LOG.info("Done.");
    }
    catch (final Exception e)
    {
      LOG.error("Failed: " + e.getMessage());
      setExitStatus(FAILURE_EXIT);
    }
  }

  private String createIssueMessage(Issue issue)
  {
    final var result = new StringBuilder(issue.getMessage());
    result.append(" (");
    if (issue.getUriToProblem() != null)
    {
      result.append(issue.getUriToProblem().path());
    }
    result.append(":").append(issue.getLineNumber()).append(":").append(issue.getColumn())
            .append(")");
    return result.toString();
  }

  protected class FileLoader extends SimpleFileVisitor<Path>
  {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
    {
      final var uri = URI.createFileURI(file.toFile().getCanonicalPath());
      if (resourceServiceProvider.canHandle(uri))
      {
        load(uri);
      }
      return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
    {
      if (attrs.isSymbolicLink())
      {
        return FileVisitResult.SKIP_SUBTREE;
      }
      return super.preVisitDirectory(dir, attrs);
    }
  }

  protected class FileVisitor extends SimpleFileVisitor<Path>
  {

    private final boolean validate;

    private final boolean generate;

    public FileVisitor(boolean validate, boolean generate)
    {
      this.validate = validate;
      this.generate = generate;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
    {
      final var uri = URI.createFileURI(file.toFile().getCanonicalPath());
      if (resourceServiceProvider.canHandle(uri))
      {
        // Load the resource
        LOG.info("Loading " + uri.toFileString() + " ... ");
        final var resource = resourceSet.getResource(uri, true);
        LOG.info("Done.");
        // Validate the resource
        if (validate && validate(resource))
        {
          // return in case of validation error
          return FileVisitResult.CONTINUE;
        }
        // start the generator
        if (generate)
        {
          generate(resource);
        }
      }
      return super.visitFile(file, attrs);
    }
  }
}
