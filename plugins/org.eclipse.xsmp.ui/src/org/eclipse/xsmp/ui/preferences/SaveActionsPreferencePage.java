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
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.ui.editor.preferences.PreferenceStoreAccessImpl;
import org.eclipse.xtext.ui.preferences.PropertyAndPreferencePage;

import com.google.inject.Inject;
import com.google.inject.name.Named;

@SuppressWarnings("restriction")
public class SaveActionsPreferencePage extends PropertyAndPreferencePage
{

  @Inject
  protected PreferenceStoreAccessImpl preferenceStoreAccessImpl;

  @Inject
  private SaveActionsConfigurationBlock validatorConfigurationBlock;

  @Inject
  @Named(Constants.LANGUAGE_NAME)
  private String languageName;

  @Override
  public void createControl(Composite parent)
  {
    final var container = (IWorkbenchPreferenceContainer) getContainer();
    final var preferenceStore = preferenceStoreAccessImpl.getWritablePreferenceStore(getProject());
    validatorConfigurationBlock.setProject(getProject());
    validatorConfigurationBlock.setPreferenceStore(preferenceStore);
    validatorConfigurationBlock.setWorkbenchPreferenceContainer(container);
    validatorConfigurationBlock.setStatusChangeListener(getNewStatusChangedListener());
    super.createControl(parent);
  }

  @Override
  protected Control createPreferenceContent(Composite composite,
          IPreferencePageContainer preferencePageContainer)
  {
    return validatorConfigurationBlock.createContents(composite);
  }

  @Override
  protected boolean hasProjectSpecificOptions(IProject project)
  {
    return validatorConfigurationBlock.hasProjectSpecificOptions(project);
  }

  @Override
  protected String getPreferencePageID()
  {
    return languageName + ".saveaction.preferencePage";
  }

  @Override
  protected String getPropertyPageID()
  {
    return languageName + ".saveaction.propertyPage";
  }

  @Override
  public void dispose()
  {
    if (validatorConfigurationBlock != null)
    {
      validatorConfigurationBlock.dispose();
    }
    super.dispose();
  }

  @Override
  protected void enableProjectSpecificSettings(boolean useProjectSpecificSettings)
  {
    super.enableProjectSpecificSettings(useProjectSpecificSettings);
    if (validatorConfigurationBlock != null)
    {
      validatorConfigurationBlock.useProjectSpecificSettings(useProjectSpecificSettings);
    }
  }

  @Override
  protected void performDefaults()
  {
    super.performDefaults();
    if (validatorConfigurationBlock != null)
    {
      validatorConfigurationBlock.performDefaults();
    }
  }

  @Override
  public boolean performOk()
  {
    if (validatorConfigurationBlock != null && !validatorConfigurationBlock.performOk())
    {
      return false;
    }
    return super.performOk();
  }

  @Override
  public void performApply()
  {
    if (validatorConfigurationBlock != null)
    {
      validatorConfigurationBlock.performApply();
    }
  }

  @Override
  public void setElement(IAdaptable element)
  {
    super.setElement(element);
    setDescription(null); // no description for property page
  }

}
