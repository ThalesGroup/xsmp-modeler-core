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

import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xsmp.util.XsmpExecutorServiceProvider;
import org.eclipse.xtext.generator.AbstractGenerator;
import org.eclipse.xtext.generator.IFileSystemAccess2;
import org.eclipse.xtext.generator.IGeneratorContext;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.RuntimeIOException;

import com.google.inject.Inject;

/**
 * A generator with support for formatting and merging.
 * Formatting tasks are processed in parallel and writes to file system access are done sequentially
 */
public abstract class XsmpGenerator extends AbstractGenerator
{
  @Inject
  private XsmpExecutorServiceProvider executorServiceProvider;

  private static class FileSystemAccess implements IFileSystemAccess2, IXsmpFileSystemAccess
  {
    private final IFileSystemAccess2 delegate;

    private final Queue<Runnable> queue = new LinkedBlockingQueue<>();

    ExecutorService executorService;

    private final CancelIndicator cancelIndicator;

    FileSystemAccess(IFileSystemAccess2 delegate, CancelIndicator cancelIndicator,
            ExecutorService executorService)
    {
      this.delegate = delegate;
      this.executorService = executorService;
      this.cancelIndicator = cancelIndicator;
    }

    void postProcess()
    {
      Runnable runnable;
      while ((runnable = queue.poll()) != null)
      {
        runnable.run();
      }
    }

    @Override
    public void generateFile(String fileName, CharSequence contents)
    {
      delegate.generateFile(fileName, contents);
    }

    @Override
    public void generateFile(String fileName, String outputConfigurationName, CharSequence contents)
    {
      delegate.generateFile(fileName, outputConfigurationName, contents);
    }

    @Override
    public void deleteFile(String fileName)
    {
      delegate.deleteFile(fileName);
    }

    @Override
    public void deleteFile(String fileName, String outputConfigurationName)
    {
      delegate.deleteFile(fileName, outputConfigurationName);
    }

    @Override
    public URI getURI(String path, String outputConfiguration)
    {
      return delegate.getURI(path, outputConfiguration);
    }

    @Override
    public URI getURI(String path)
    {
      return delegate.getURI(path);
    }

    @Override
    public void generateFile(String fileName, String outputCfgName, InputStream content)
      throws RuntimeIOException
    {
      delegate.generateFile(fileName, outputCfgName, content);
    }

    @Override
    public void generateFile(String fileName, InputStream content) throws RuntimeIOException
    {
      delegate.generateFile(fileName, content);
    }

    @Override
    public InputStream readBinaryFile(String fileName, String outputCfgName)
      throws RuntimeIOException
    {
      return delegate.readBinaryFile(fileName, outputCfgName);
    }

    @Override
    public InputStream readBinaryFile(String fileName) throws RuntimeIOException
    {
      return delegate.readBinaryFile(fileName);
    }

    @Override
    public CharSequence readTextFile(String fileName, String outputCfgName)
      throws RuntimeIOException
    {
      return delegate.readTextFile(fileName, outputCfgName);
    }

    @Override
    public CharSequence readTextFile(String fileName) throws RuntimeIOException
    {
      return delegate.readTextFile(fileName);
    }

    @Override
    public boolean isFile(String path, String outputConfigurationName) throws RuntimeIOException
    {
      return delegate.isFile(path, outputConfigurationName);
    }

    @Override
    public boolean isFile(String path) throws RuntimeIOException
    {
      return delegate.isFile(path);
    }

    @Override
    public void generateFile(String fileName, String outputConfigurationName,
            Future<CharSequence> content)
    {
      queue.add(() -> {
        try
        {
          delegate.generateFile(fileName, outputConfigurationName, content.get());
        }
        catch (final ExecutionException e)
        {
          throw new CancellationException(e.getMessage());
        }
        catch (final InterruptedException e)
        {
          Thread.currentThread().interrupt();
          throw new CancellationException(e.getMessage());
        }
      });
    }

    @Override
    public void generateFile(String fileName, String outputConfigurationName, CharSequence content,
            IFileMerger fileMerger, IFileFormatter fileFormatter)
    {
      if (!cancelIndicator.isCanceled())
      {
        final var mergedContent = isFile(fileName, outputConfigurationName)
                ? fileMerger.merge(this, fileName, outputConfigurationName, content)
                : content;
        generateFile(fileName, outputConfigurationName, executorService.submit(() -> fileFormatter
                .format(this, fileName, outputConfigurationName, mergedContent)));
      }
    }

    @Override
    public void generateFile(String fileName, String outputConfigurationName, CharSequence content,
            IFileMerger fileMerger)
    {
      if (!cancelIndicator.isCanceled())
      {
        if (isFile(fileName, outputConfigurationName))
        {
          generateFile(fileName, outputConfigurationName,
                  fileMerger.merge(this, fileName, outputConfigurationName, content));
        }
        else
        {
          generateFile(fileName, outputConfigurationName, content);
        }
      }

    }

    @Override
    public void generateFile(String fileName, String outputConfigurationName, CharSequence content,
            IFileFormatter fileFormatter)
    {
      if (!cancelIndicator.isCanceled())
      {
        generateFile(fileName, outputConfigurationName, executorService.submit(
                () -> fileFormatter.format(this, fileName, outputConfigurationName, content)));
      }
    }
  }

  @Override
  public final void doGenerate(Resource input, IFileSystemAccess2 fsa, IGeneratorContext context)
  {
    // final var start = System.nanoTime()
    final var executorService = executorServiceProvider.get(XsmpGenerator.class.getName());
    final var fs = new FileSystemAccess(fsa, context.getCancelIndicator(), executorService);
    try
    {
      generate(input, fs);
    }
    finally
    {
      fs.postProcess();
    }
    // System.out.println(getClass().getSimpleName() + ": " + input.getURI().lastSegment()
    // + " generated in " + (System.nanoTime() - start) / 1000000. + "ms")
  }

  protected abstract void generate(Resource input, IXsmpFileSystemAccess fsa);

}