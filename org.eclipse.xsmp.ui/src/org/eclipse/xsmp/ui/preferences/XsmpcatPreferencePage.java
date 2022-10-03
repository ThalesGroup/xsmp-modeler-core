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
package org.eclipse.xsmp.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.IPreferencePageContainer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.builder.DerivedResourceCleanerJob;
import org.eclipse.xtext.builder.EclipseOutputConfigurationProvider;
import org.eclipse.xtext.ui.editor.preferences.PreferenceStoreAccessImpl;
import org.eclipse.xtext.ui.preferences.PropertyAndPreferencePage;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

@SuppressWarnings("restriction")
public class XsmpcatPreferencePage extends PropertyAndPreferencePage
{

  @Inject
  protected PreferenceStoreAccessImpl preferenceStoreAccessImpl;

  @Inject
  private MdkConfigurationBlock mdkConfigurationBlock;

  private String languageName;

  private Provider<DerivedResourceCleanerJob> cleanerProvider;

  @Inject
  public void setCleanerProvider(Provider<DerivedResourceCleanerJob> cleanerProvider)
  {
    this.cleanerProvider = cleanerProvider;
  }

  @Inject
  public void setLanguageName(@Named(Constants.LANGUAGE_NAME) String languageName)
  {
    this.languageName = languageName;
  }

  @Override
  protected Control createPreferenceContent(Composite composite,
          IPreferencePageContainer preferencePageContainer)
  {
    return mdkConfigurationBlock.createContents(composite);
  }

  @Override
  public void createControl(Composite parent)
  {
    final var container = (IWorkbenchPreferenceContainer) getContainer();
    mdkConfigurationBlock.setProject(getProject());
    mdkConfigurationBlock.setWorkbenchPreferenceContainer(container);
    mdkConfigurationBlock.setStatusChangeListener(getNewStatusChangedListener());
    super.createControl(parent);
  }

  @Override
  protected Label createDescriptionLabel(Composite parent)
  {
    return null;
  }

  @Override
  protected boolean hasProjectSpecificOptions(IProject project)
  {
    return mdkConfigurationBlock.hasProjectSpecificOptions(project);
  }

  @Override
  protected String getPreferencePageID()
  {
    return languageName + ".mdk.preferencePage";
  }

  @Override
  protected String getPropertyPageID()
  {
    return languageName + ".mdk.propertyPage";
  }

  @Override
  public void dispose()
  {
    if (mdkConfigurationBlock != null)
    {
      mdkConfigurationBlock.dispose();
    }
    super.dispose();
  }

  @Override
  protected void enableProjectSpecificSettings(boolean useProjectSpecificSettings)
  {
    // super.enableProjectSpecificSettings(useProjectSpecificSettings);
    if (mdkConfigurationBlock != null)
    {
      mdkConfigurationBlock.useProjectSpecificSettings(useProjectSpecificSettings);
    }
  }

  @Override
  protected void performDefaults()
  {
    super.performDefaults();
    if (mdkConfigurationBlock != null)
    {
      mdkConfigurationBlock.performDefaults();
    }
  }

  @Override
  public boolean performOk()
  {
    if (mdkConfigurationBlock != null)
    {
      scheduleCleanerJobIfNecessary(getContainer());
      if (!mdkConfigurationBlock.performOk())
      {
        return false;
      }
    }
    return super.performOk();
  }

  @Override
  public void performApply()
  {
    if (mdkConfigurationBlock != null)
    {
      scheduleCleanerJobIfNecessary(null);
      mdkConfigurationBlock.performApply();
    }
  }

  @Override
  public void setElement(IAdaptable element)
  {
    super.setElement(element);
    setDescription(null); // no description for property page
  }

  private void scheduleCleanerJobIfNecessary(IPreferencePageContainer preferencePageContainer)
  {
    final var changes = mdkConfigurationBlock.getPreferenceChanges();
    for (final String key : changes.keySet())
    {
      if (key.matches("^" + EclipseOutputConfigurationProvider.OUTPUT_PREFERENCE_TAG + "\\.\\w+\\."
              + EclipseOutputConfigurationProvider.OUTPUT_DIRECTORY + "$"))
      {
        final var difference = changes.get(key);
        scheduleCleanerJob(preferencePageContainer, difference.rightValue());
      }
    }
  }

  private void scheduleCleanerJob(IPreferencePageContainer preferencePageContainer,
          String folderNameToClean)
  {
    final var derivedResourceCleanerJob = cleanerProvider.get();
    derivedResourceCleanerJob.setUser(true);
    derivedResourceCleanerJob.initialize(getProject(), folderNameToClean);
    if (preferencePageContainer != null)
    {
      final var container = (IWorkbenchPreferenceContainer) getContainer();
      container.registerUpdateJob(derivedResourceCleanerJob);
    }
    else
    {
      derivedResourceCleanerJob.schedule();
    }
  }
}
