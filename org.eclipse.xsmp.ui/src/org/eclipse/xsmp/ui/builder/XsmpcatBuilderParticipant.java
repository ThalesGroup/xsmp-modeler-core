package org.eclipse.xsmp.ui.builder;

import static com.google.common.collect.Sets.newLinkedHashSet;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.xsmp.ui.configuration.IXsmpcatServiceUIProvider;
import org.eclipse.xtext.builder.BuilderParticipant;
import org.eclipse.xtext.builder.EclipseResourceFileSystemAccess2;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.resource.IResourceDescription.Delta;

import com.google.inject.Inject;

/**
 * A specialized builder participant that check that the builder is enabled for the provided project
 *
 * @author yannick
 */
public class XsmpcatBuilderParticipant extends BuilderParticipant
{

  @Inject
  protected IXsmpcatServiceUIProvider serviceProvider;

  @Override
  protected boolean isEnabled(IBuildContext context)
  {
    return super.isEnabled(context) && serviceProvider.isEnabledFor(context.getBuiltProject());
  }

  @SuppressWarnings("restriction")
  @Override
  protected void cleanDerivedResources(Delta delta, Set<IFile> derivedResources,
          IBuildContext context, EclipseResourceFileSystemAccess2 access,
          IProgressMonitor deleteMonitor)
    throws CoreException
  {

    final var outputConfigurations = getOutputConfigurationProvider()
            .getOutputConfigurations(context.getBuiltProject());
    final var uri = delta.getUri().toString();

    final var derivedResourceMarkers = getDerivedResourceMarkers();

    for (final IFile iFile : newLinkedHashSet(derivedResources))
    {
      if (deleteMonitor.isCanceled())
      {
        throw new OperationCanceledException();
      }
      final var marker = derivedResourceMarkers.findDerivedResourceMarker(iFile, uri);
      if (marker != null)
      {
        marker.delete();
      }
      if (derivedResourceMarkers.findDerivedResourceMarkers(iFile).length == 0)
      {
        for (final OutputConfiguration config : outputConfigurations)
        {
          final var parent = context.getBuiltProject().getFile(config.getOutputDirectory());
          if (parent.getFullPath().isPrefixOf(iFile.getFullPath()))
          {
            access.deleteFile(iFile, config.getName(), deleteMonitor);
            context.needRebuild(iFile.getProject());
            break;
          }
        }
      }
    }

  }

}
