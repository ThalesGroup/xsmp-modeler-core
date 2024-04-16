package com.thalesaleniaspace.compilechain.builder;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddRemoveCompileChainNatureHandler extends AbstractHandler
{

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException
  {
    final var selection = HandlerUtil.getCurrentSelection(event);
    //
    if (selection instanceof IStructuredSelection)
    {
      for (final Object element : (IStructuredSelection) selection)
      {
        IProject project = null;
        if (element instanceof IProject)
        {
          project = (IProject) element;
        }
        else if (element instanceof IAdaptable)
        {
          project = ((IAdaptable) element).getAdapter(IProject.class);
        }
        if (project != null)
        {
          try
          {
            toggleNature(project);
          }
          catch (final CoreException e)
          {
            throw new ExecutionException("Failed to toggle nature", e);
          }
        }
      }
    }

    return null;
  }

  /**
   * Toggles sample nature on a project
   *
   * @param project
   *          to have sample nature added or removed
   */
  private void toggleNature(IProject project) throws CoreException
  {
    final var description = project.getDescription();
    final var natures = description.getNatureIds();

    for (var i = 0; i < natures.length; ++i)
    {
      if (CompileChainNature.NATURE_ID.equals(natures[i]))
      {
        // Remove the nature
        final var newNatures = new String[natures.length - 1];
        System.arraycopy(natures, 0, newNatures, 0, i);
        System.arraycopy(natures, i + 1, newNatures, i, natures.length - i - 1);
        description.setNatureIds(newNatures);
        project.setDescription(description, null);
        return;
      }
    }

    // Add the nature
    final var newNatures = new String[natures.length + 1];
    System.arraycopy(natures, 0, newNatures, 0, natures.length);
    newNatures[natures.length] = CompileChainNature.NATURE_ID;
    description.setNatureIds(newNatures);
    project.setDescription(description, null);
  }
}