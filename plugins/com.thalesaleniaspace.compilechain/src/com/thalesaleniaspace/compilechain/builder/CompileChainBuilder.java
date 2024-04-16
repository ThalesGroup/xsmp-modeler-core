package com.thalesaleniaspace.compilechain.builder;

import java.util.Map;

import org.eclipse.cdt.managedbuilder.core.ManagedBuilderCorePlugin;
import org.eclipse.cdt.managedbuilder.internal.core.CommonBuilder;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Specialization of CommonBuilder to run clean/build only on the requested project
 *
 * @author daveluy
 */
@SuppressWarnings("restriction")
public class CompileChainBuilder extends CommonBuilder
{
  public static final String BUILDER_ID = ManagedBuilderCorePlugin.getUniqueIdentifier()
          + ".compilechainbuilder"; //$NON-NLS-1$

  @Override
  public void clean(Map<String, String> args, IProgressMonitor monitor) throws CoreException
  {

    final var ctx = getContext();
    for (final IBuildConfiguration cfg : ctx.getRequestedConfigs())
    {
      if (getBuildConfig() == cfg)
      {
        super.clean(args, monitor);
        break;
      }
    }
  }

  @Override
  protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map argsMap,
          IProgressMonitor monitor)
    throws CoreException
  {
    final var ctx = getContext();
    for (final IBuildConfiguration cfg : ctx.getRequestedConfigs())
    {
      if (getBuildConfig() == cfg)
      {
        if (kind == INCREMENTAL_BUILD)
        {
          kind = FULL_BUILD;
        }
        super.build(kind, argsMap, monitor);
        break;
      }
    }
    return new IProject[0];
  }

}
