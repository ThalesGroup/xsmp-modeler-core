package com.thalesaleniaspace.compilechain.builder;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.cdt.managedbuilder.core.ManagedBuilderCorePlugin;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;

public class CompileChainNature implements IProjectNature
{

  /**
   * ID of this project nature
   */
  public static final String NATURE_ID = "com.thalesaleniaspace.compilechain.compileChainNature"; //$NON-NLS-1$

  public static final String BUILDER_NAME = "compilechainbuilder"; //$NON-NLS-1$

  public static final String BUILDER_ID = ManagedBuilderCorePlugin.getUniqueIdentifier() + "." //$NON-NLS-1$
          + BUILDER_NAME;

  private IProject project;

  public static void addManagedBuilder(IProject project, IProgressMonitor monitor)
    throws CoreException
  {
    // Add the builder to the project
    final var description = project.getDescription();
    final var commands = description.getBuildSpec();

    var found = false;
    // See if the builder is already there
    for (final ICommand command : commands)
    {
      if (command.getBuilderName().equals(getBuilderID()))
      {
        found = true;
        break;
      }
    }
    if (!found)
    {
      // add builder to project
      final var command = description.newCommand();
      command.setBuilderName(getBuilderID());
      final var newCommands = new ICommand[commands.length + 1];
      // Add it before other builders.
      System.arraycopy(commands, 0, newCommands, 1, commands.length);
      newCommands[0] = command;
      description.setBuildSpec(newCommands);
      project.setDescription(description, null);
    }
  }

  /**
   * Get the correct builderID
   */
  public static String getBuilderID()
  {
    ManagedBuilderCorePlugin.getDefault();
    if (Platform.getExtensionRegistry().getExtension(BUILDER_NAME) != null)
    {
      return ManagedBuilderCorePlugin.getUniqueIdentifier() + "." + BUILDER_NAME; //$NON-NLS-1$
    }
    return BUILDER_ID;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.resources.IProjectNature#configure()
   */
  @Override
  public void configure() throws CoreException
  {
    addManagedBuilder(project, new NullProgressMonitor());
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.resources.IProjectNature#deconfigure()
   */
  @Override
  public void deconfigure() throws CoreException
  {
    // Remove the builder to the project
    final var description = project.getDescription();
    final var commands = description.getBuildSpec();

    for (var i = 0; i < commands.length; i++)
    {
      final var command = commands[i];
      if (getBuilderID().equals(command.getBuilderName()))
      {
        // Remove the command
        final var vec = new ArrayList<>(Arrays.asList(commands));
        vec.remove(i);
        description.setBuildSpec(vec.toArray(new ICommand[vec.size()]));
        break;
      }
    }

    project.setDescription(description, null);
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.resources.IProjectNature#getProject()
   */
  @Override
  public IProject getProject()
  {
    // Just return the project associated with the nature
    return project;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
   */
  @Override
  public void setProject(IProject project)
  {
    // Set the project for the nature
    this.project = project;
  }

}
